import React, { useState, useEffect } from 'react';
import { 
  Modal, 
  Form, 
  InputNumber, 
  Input, 
  Select, 
  Button, 
  message,
  Divider,
  Descriptions,
  Spin
} from 'antd';
import { PlusOutlined, MinusOutlined } from '@ant-design/icons';
import { StockApiService } from '../services/StockApiService';
import type { Stock, StockInboundRequest, StockOutboundRequest } from '../types/stock';

interface StockAdjustmentModalProps {
  visible: boolean;
  productId: number;
  productName: string;
  onClose: () => void;
  onSuccess?: () => void;
}

const StockAdjustmentModal: React.FC<StockAdjustmentModalProps> = ({
  visible,
  productId,
  productName,
  onClose,
  onSuccess
}) => {
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);
  const [stockInfo, setStockInfo] = useState<Stock | null>(null);
  const [loadingStock, setLoadingStock] = useState(false);

  // 載入庫存資訊
  const loadStockInfo = async () => {
    if (!productId || !visible) return;
    
    try {
      setLoadingStock(true);
      const response = await StockApiService.getStockByProduct(productId);
      if (response.success && response.data) {
        setStockInfo(response.data);
      }
    } catch (error) {
      console.error('載入庫存資訊錯誤:', error);
    } finally {
      setLoadingStock(false);
    }
  };

  useEffect(() => {
    loadStockInfo();
  }, [productId, visible]);

  const handleSubmit = async (values: {
    adjustmentType: 'IN' | 'OUT';
    qty: number;
    unitCost?: number;
    reason?: string;
  }) => {
    try {
      setLoading(true);
      const { adjustmentType, qty, unitCost, reason } = values;

      let response;
      if (adjustmentType === 'IN') {
        const request: StockInboundRequest = {
          productId,
          qty,
          unitCost,
          reason
        };
        response = await StockApiService.processInbound(request);
      } else {
        const request: StockOutboundRequest = {
          productId,
          qty,
          reason
        };
        response = await StockApiService.processOutbound(request);
      }

      if (response.success) {
        message.success('庫存調整成功');
        form.resetFields();
        onSuccess?.();
        onClose();
        loadStockInfo(); // 重新載入庫存資訊
      } else {
        //message.error('庫存調整失敗');
      }
    } catch (error) {
      console.error('庫存調整錯誤:', error);
      message.error('庫存調整時發生錯誤');
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = () => {
    form.resetFields();
    onClose();
  };

  return (
    <Modal
      title={`調整庫存 - ${productName}`}
      open={visible}
      onCancel={handleCancel}
      footer={null}
      width={600}
    >
      <Spin spinning={loadingStock}>
        {stockInfo && (
          <>
            <Descriptions title="目前庫存狀況" bordered column={2} size="small">
              <Descriptions.Item label="現有數量">{stockInfo.qty}</Descriptions.Item>
              <Descriptions.Item label="平均成本">NT$ {stockInfo.avgCost.toFixed(2)}</Descriptions.Item>
              <Descriptions.Item label="總成本">NT$ {stockInfo.totalCost.toFixed(2)}</Descriptions.Item>
              <Descriptions.Item label="最後更新">{new Date(stockInfo.updatedAt).toLocaleString('zh-TW')}</Descriptions.Item>
            </Descriptions>
            <Divider />
          </>
        )}

        <Form
          form={form}
          layout="vertical"
          onFinish={handleSubmit}
          initialValues={{
            adjustmentType: 'IN'
          }}
        >
          <Form.Item
            name="adjustmentType"
            label="調整類型"
            rules={[{ required: true, message: '請選擇調整類型' }]}
          >
            <Select>
              <Select.Option value="IN">
                <PlusOutlined /> 入庫
              </Select.Option>
              <Select.Option value="OUT">
                <MinusOutlined /> 出庫
              </Select.Option>
            </Select>
          </Form.Item>

          <Form.Item
            name="qty"
            label="調整數量"
            rules={[
              { required: true, message: '請輸入調整數量' },
              { type: 'number', min: 1, message: '數量必須大於0' }
            ]}
          >
            <InputNumber
              style={{ width: '100%' }}
              placeholder="請輸入調整數量"
              min={1}
            />
          </Form.Item>

          <Form.Item
            noStyle
            shouldUpdate={(prevValues, currentValues) => prevValues.adjustmentType !== currentValues.adjustmentType}
          >
            {({ getFieldValue }) =>
              getFieldValue('adjustmentType') === 'IN' ? (
                <Form.Item
                  name="unitCost"
                  label="單位成本"
                  rules={[{ type: 'number', min: 0, message: '成本不能為負數' }]}
                >
                  <InputNumber
                    style={{ width: '100%' }}
                    placeholder="請輸入單位成本"
                    min={0}
                    step={0.01}
                    precision={2}
                  />
                </Form.Item>
              ) : null
            }
          </Form.Item>

          <Form.Item
            name="reason"
            label="調整原因"
          >
            <Input.TextArea
              placeholder="請輸入調整原因（選填）"
              rows={3}
            />
          </Form.Item>

          <Form.Item style={{ marginBottom: 0, textAlign: 'right' }}>
            <Button onClick={handleCancel} style={{ marginRight: 8 }}>
              取消
            </Button>
            <Button type="primary" htmlType="submit" loading={loading}>
              確認調整
            </Button>
          </Form.Item>
        </Form>
      </Spin>
    </Modal>
  );
};

export default StockAdjustmentModal;