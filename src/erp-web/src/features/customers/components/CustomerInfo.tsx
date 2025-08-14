import React, { useState, useEffect } from 'react';
import { Card, Descriptions, Tag, Spin, message } from 'antd';
import CustomerService from '@features/customers/services/customerService';
import type { Customer, CustomerStatus } from '@features/customers/types/customer';

interface CustomerInfoProps {
  customerId?: number;
}

const CustomerInfo: React.FC<CustomerInfoProps> = ({ 
  customerId 
}) => {
  const [customer, setCustomer] = useState<Customer | null>(null);
  const [loading, setLoading] = useState(false);

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

  // 狀態顯示組件
  const getStatusTag = (status: CustomerStatus) => {
    switch (status) {
      case 'ACTIVE':
        return <Tag color="success">啟用</Tag>;
      case 'SUSPENDED':
        return <Tag color="warning">暫停</Tag>;
      case 'BLACKLIST':
        return <Tag color="error">黑名單</Tag>;
      default:
        return <Tag color="default">{status}</Tag>;
    }
  };

  return (
    <div>
      <Card title="客戶資訊">
        {!currentCustomerId ? (
          <div style={{ textAlign: 'center', padding: '50px' }}>
            請選擇一個客戶查看詳細資訊
          </div>
        ) : (
          <Spin spinning={loading}>
            {customer && (
              <Descriptions column={1} bordered>
                <Descriptions.Item label="客戶ID">
                  {customer.id}
                </Descriptions.Item>
                <Descriptions.Item label="客戶名稱">
                  {customer.name}
                </Descriptions.Item>
                <Descriptions.Item label="聯絡電話">
                  {customer.phone}
                </Descriptions.Item>
                <Descriptions.Item label="電子郵件">
                  {customer.email || '未提供'}
                </Descriptions.Item>
                <Descriptions.Item label="地址">
                  {customer.address || '未提供'}
                </Descriptions.Item>
                <Descriptions.Item label="狀態">
                  {getStatusTag(customer.status)}
                </Descriptions.Item>
                <Descriptions.Item label="備註">
                  {customer.note || '無備註'}
                </Descriptions.Item>
              </Descriptions>
            )}
          </Spin>
        )}
      </Card>
    </div>
  );
};

export default CustomerInfo;