import React, { useState } from 'react';
import { Card, Typography, Form, Input, Select, Button, Space, message, DatePicker } from 'antd';
import CustomerService from '@features/customers/services/customerService';
import type { CustomerGender } from '@features/customers/types/customer';
import dayjs from 'dayjs';

const { Title } = Typography;
const { Option } = Select;
const { TextArea } = Input;

interface CustomerNewProps {
  onCancel?: () => void;
  onSuccess?: () => void;
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
}

const CustomerNew: React.FC<CustomerNewProps> = ({ 
  onCancel, 
  onSuccess 
}) => {
  const [form] = Form.useForm();
  const [submitting, setSubmitting] = useState(false);

  const handleSubmit = async (values: CustomerFormValues) => {
    try {
      setSubmitting(true);
      
      // 準備新增資料
      const createData = {
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

      // 調用後端API新增客戶
      const result = await CustomerService.addCustomer(createData);
      
      if (result) {
        message.success('客戶新增成功');
        form.resetFields();
        
        if (onSuccess) {
          onSuccess();
        }
      } else {
        message.error('新增客戶失敗');
      }
    } catch (error) {
      console.error('新增客戶錯誤:', error);
      message.error('新增客戶時發生錯誤');
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
      {/* 新增客戶表單 */}
      <Card>
        <Title level={2}>新增客戶</Title>
        
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

          <Form.Item>
            <Space>
              <Button 
                type="primary" 
                htmlType="submit" 
                loading={submitting}
              >
                新增
              </Button>
              <Button onClick={handleCancel}>
                取消
              </Button>
            </Space>
          </Form.Item>
        </Form>
      </Card>
    </div>
  );
};

export default CustomerNew;