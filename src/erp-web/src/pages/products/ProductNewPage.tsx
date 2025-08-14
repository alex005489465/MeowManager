import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { 
  Card, 
  Form, 
  Input, 
  InputNumber, 
  Select, 
  Button, 
  Typography, 
  Space, 
  message
} from 'antd';
import { 
  SaveOutlined, 
  ArrowLeftOutlined,
  PlusOutlined
} from '@ant-design/icons';
import ProductsLayout from '../../features/products/components/ProductsLayout';
import { ProductApiService } from '@features/products';
import type { ProductCreateRequest } from '@features/products/types/product';

const { Title } = Typography;
const { TextArea } = Input;

interface ProductFormValues {
  name: string;
  description?: string;
  price: number;
  stock?: number;
  status: 'ACTIVE' | 'INACTIVE' | 'DISCONTINUED';
  type: 'PHYSICAL' | 'DIGITAL' | 'SERVICE';
}

const ProductNewPage: React.FC = () => {
  const navigate = useNavigate();
  const [form] = Form.useForm();
  const [saving, setSaving] = useState(false);

  const handleSave = async (values: ProductFormValues) => {
    try {
      setSaving(true);
      
      const createRequest: ProductCreateRequest = {
        name: values.name,
        description: values.description || '',
        price: values.price,
        stock: values.stock || 0,
        type: values.type
      };

      const response = await ProductApiService.createProduct(createRequest);
      
      if (response.success && response.data) {
        message.success('產品創建成功');
        navigate(`/products/${response.data.id}`);
      } else {
        message.error('產品創建失敗');
      }
    } catch (error) {
      console.error('創建產品錯誤:', error);
      message.error('創建產品時發生錯誤');
    } finally {
      setSaving(false);
    }
  };

  return (
    <ProductsLayout>
      <div style={{ padding: '24px' }}>
        <Space direction="vertical" size="large" style={{ width: '100%' }}>
          {/* 頁面標題 */}
          <div style={{ display: 'flex', alignItems: 'center' }}>
            <Button 
              icon={<ArrowLeftOutlined />} 
              onClick={() => navigate('/products')}
              style={{ marginRight: 16 }}
            >
              返回
            </Button>
            <Title level={2} style={{ margin: 0 }}>
              <PlusOutlined /> 新增產品
            </Title>
          </div>

          {/* 新增表單 */}
          <Card title="新增產品資訊" style={{ width: '100%' }}>
            <Form
              form={form}
              layout="vertical"
              onFinish={handleSave}
              style={{ maxWidth: 600 }}
              initialValues={{
                status: 'ACTIVE',
                type: 'PHYSICAL',
                stock: 0
              }}
            >
              <Form.Item
                label="產品名稱"
                name="name"
                rules={[
                  { required: true, message: '請輸入產品名稱' },
                  { min: 1, max: 100, message: '產品名稱長度應為1-100字符' }
                ]}
              >
                <Input placeholder="請輸入產品名稱" />
              </Form.Item>

              <Form.Item
                label="產品描述"
                name="description"
                rules={[
                  { max: 500, message: '產品描述不能超過500字符' }
                ]}
              >
                <TextArea 
                  placeholder="請輸入產品描述"
                  rows={4}
                />
              </Form.Item>


              <Form.Item
                label="價格"
                name="price"
                rules={[
                  { required: true, message: '請輸入產品價格' },
                  { type: 'number', min: 0, message: '價格必須大於等於0' }
                ]}
              >
                <InputNumber
                  placeholder="請輸入產品價格"
                  style={{ width: '100%' }}
                  formatter={value => `NT$ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
                  parser={value => value!.replace(/NT\$\s?|(,*)/g, '')}
                />
              </Form.Item>

              <Form.Item
                label="庫存"
                name="stock"
                rules={[
                  { type: 'number', min: 0, message: '庫存必須大於等於0' }
                ]}
              >
                <InputNumber
                  placeholder="請輸入庫存數量"
                  style={{ width: '100%' }}
                />
              </Form.Item>

              <Form.Item
                label="狀態"
                name="status"
                rules={[{ required: true, message: '請選擇產品狀態' }]}
              >
                <Select placeholder="請選擇產品狀態">
                  <Select.Option value="ACTIVE">啟用</Select.Option>
                  <Select.Option value="INACTIVE">停用</Select.Option>
                  <Select.Option value="DISCONTINUED">停產</Select.Option>
                </Select>
              </Form.Item>

              <Form.Item
                label="類型"
                name="type"
                rules={[{ required: true, message: '請選擇產品類型' }]}
              >
                <Select placeholder="請選擇產品類型">
                  <Select.Option value="PHYSICAL">實體商品</Select.Option>
                  <Select.Option value="DIGITAL">數位商品</Select.Option>
                  <Select.Option value="SERVICE">服務</Select.Option>
                </Select>
              </Form.Item>

              <Form.Item>
                <Space>
                  <Button 
                    type="primary" 
                    htmlType="submit" 
                    loading={saving}
                    icon={<SaveOutlined />}
                  >
                    {saving ? '創建中...' : '創建'}
                  </Button>
                  <Button 
                    onClick={() => navigate('/products')}
                  >
                    取消
                  </Button>
                </Space>
              </Form.Item>
            </Form>
          </Card>
        </Space>
      </div>
    </ProductsLayout>
  );
};

export default ProductNewPage;