import React, { useState, useEffect } from 'react';
import { Card, Typography, Form, Input, Select, Button, Space, Spin, message, DatePicker } from 'antd';
import { useNavigate } from 'react-router-dom';
import CustomerService from '@features/customers/services/customerService';
import type { Customer, CustomerGender } from '@features/customers/types/customer';
import dayjs from 'dayjs';

const { Title } = Typography;
const { Option } = Select;
const { TextArea } = Input;

interface CustomerEditProps {
  customerId?: number;
  onCancel?: () => void;
  onSave?: () => void;
}

interface CustomerFormValues {
  name: string;
  nick?: string;
  gender: CustomerGender;
  birthDate?: dayjs.Dayjs;
  fbAccount?: string;
  lineAccount?: string;
  email?: string;
  phone: string;
  address: string;
  note?: string;
  status: string;
}

const CustomerEdit: React.FC<CustomerEditProps> = ({ 
  customerId, 
  onCancel, 
  onSave 
}) => {
  const [form] = Form.useForm();
  const navigate = useNavigate();
  const [, setCustomer] = useState<Customer | null>(null);
  const [loading, setLoading] = useState(false);
  const [submitting, setSubmitting] = useState(false);

  // 直接使用傳入的 customerId
  const currentCustomerId = customerId;

  // 載入客戶詳細資料
  const loadCustomerDetail = async () => {
    if (!currentCustomerId) return;
    
    try {
      setLoading(true);
      const response = await CustomerService.getCustomerById(currentCustomerId);
      if (response) {
        setCustomer(response);
        // 將資料填入表單
        form.setFieldsValue({
          name: response.name,
          nick: response.nick,
          gender: response.gender,
          birthDate: response.birthDate ? dayjs(response.birthDate) : null,
          fbAccount: response.fbAccount,
          lineAccount: response.lineAccount,
          email: response.email,
          phone: response.phone,
          address: response.address,
          note: response.note,
          status: response.status // 只顯示，不可編輯
        });
      } else {
        message.error('載入客戶資料失敗');
      }
    } catch (error) {
      console.error('載入客戶資料錯誤:', error);
      message.error('載入客戶資料時發生錯誤');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadCustomerDetail();
  }, [currentCustomerId]);

  const handleSubmit = async (values: CustomerFormValues) => {
    if (!currentCustomerId) return;
    
    try {
      setSubmitting(true);
      
      // 準備更新資料（排除狀態欄位）
      const updateData = {
        name: values.name,
        nick: values.nick,
        gender: values.gender,
        birthDate: values.birthDate ? values.birthDate.format('YYYY-MM-DD') : undefined,
        fbAccount: values.fbAccount,
        lineAccount: values.lineAccount,
        email: values.email,
        phone: values.phone,
        address: values.address,
        note: values.note
      };

      // 調用後端API更新資料
      const result = await CustomerService.updateCustomer(currentCustomerId, updateData);
      
      if (result) {
        message.success('客戶資料更新成功');
        
        // 成功後跳轉到客戶資訊頁面
        navigate(`/customers/${currentCustomerId}`);

        if (onSave) {
          onSave();
        }
      } else {
        message.error('更新客戶資料失敗');
      }
    } catch (error) {
      console.error('更新客戶資料錯誤:', error);
      message.error('更新客戶資料時發生錯誤');
    } finally {
      setSubmitting(false);
    }
  };

  const handleCancel = () => {
    form.resetFields();
    if (onCancel) {
      onCancel();
    }
  };

  return (
    <div>
      {/* 編輯客戶表單 */}
      <Card>
        <Title level={2}>編輯客戶</Title>
        
        {!currentCustomerId ? (
          <div style={{ textAlign: 'center', padding: '50px' }}>
            請選擇一個客戶進行編輯
          </div>
        ) : (
          <Spin spinning={loading}>
            <Form
              form={form}
              layout="vertical"
              onFinish={handleSubmit}
            >
              <Form.Item
                label="客戶姓名"
                name="name"
                rules={[{ required: true, message: '請輸入客戶姓名' }]}
              >
                <Input placeholder="請輸入客戶姓名" />
              </Form.Item>

              <Form.Item
                label="暱稱"
                name="nick"
              >
                <Input placeholder="請輸入暱稱" />
              </Form.Item>

              <Form.Item
                label="性別"
                name="gender"
                rules={[{ required: true, message: '請選擇性別' }]}
              >
                <Select placeholder="請選擇性別">
                  <Option value="MALE">男性</Option>
                  <Option value="FEMALE">女性</Option>
                  <Option value="OTHER">其他</Option>
                </Select>
              </Form.Item>

              <Form.Item
                label="生日"
                name="birthDate"
              >
                <DatePicker placeholder="請選擇生日" style={{ width: '100%' }} />
              </Form.Item>

              <Form.Item
                label="Facebook 帳號"
                name="fbAccount"
              >
                <Input placeholder="請輸入Facebook帳號" />
              </Form.Item>

              <Form.Item
                label="Line 帳號"
                name="lineAccount"
              >
                <Input placeholder="請輸入Line帳號" />
              </Form.Item>

              <Form.Item
                label="電子郵件"
                name="email"
                rules={[
                  { type: 'email', message: '請輸入有效的電子郵件格式' }
                ]}
              >
                <Input placeholder="請輸入電子郵件" />
              </Form.Item>

              <Form.Item
                label="聯絡電話"
                name="phone"
                rules={[{ required: true, message: '請輸入聯絡電話' }]}
              >
                <Input placeholder="請輸入聯絡電話" />
              </Form.Item>

              <Form.Item
                label="地址"
                name="address"
                rules={[{ required: true, message: '請輸入地址' }]}
              >
                <Input placeholder="請輸入地址" />
              </Form.Item>

              <Form.Item
                label="備註"
                name="note"
              >
                <TextArea rows={4} placeholder="請輸入備註" />
              </Form.Item>

              {/* 客戶狀態 - 只顯示不能編輯 */}
              <Form.Item
                label="客戶狀態"
                name="status"
              >
                <Select placeholder="客戶狀態" disabled>
                  <Option value="ACTIVE">啟用</Option>
                  <Option value="SUSPENDED">暫停</Option>
                  <Option value="BLACKLIST">黑名單</Option>
                </Select>
              </Form.Item>

              <Form.Item>
                <Space>
                  <Button 
                    type="primary" 
                    htmlType="submit" 
                    loading={submitting}
                  >
                    儲存
                  </Button>
                  <Button onClick={handleCancel}>
                    取消
                  </Button>
                </Space>
              </Form.Item>
            </Form>
          </Spin>
        )}
      </Card>
    </div>
  );
};

export default CustomerEdit;