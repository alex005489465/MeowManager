import React from 'react';
import CustomersList from './CustomersList';
import { useNavigate } from 'react-router-dom';

interface CustomersLayoutProps {
  children: React.ReactNode;
}

const CustomersLayout: React.FC<CustomersLayoutProps> = ({ children }) => {
  const navigate = useNavigate();

  // 處理編輯客戶
  const handleEditCustomer = (customerId: number) => {
    navigate(`/customers/${customerId}/edit`);
  };

  // 處理顯示客戶資訊
  const handleShowCustomerInfo = (customerId: number) => {
    navigate(`/customers/${customerId}`);
  };

  return (
    <div style={{ 
      padding: '24px',
      background: 'var(--ant-color-bg-base)',
      height: '100vh',
      display: 'flex',
      flexDirection: 'column',
      gap: '16px',
      overflow: 'hidden'
    }}>
      <div style={{ 
        display: 'flex',
        gap: '16px',
        flex: 1,
        minHeight: 0
      }}>
        {/* 左側內容區域 */}
        <div style={{ 
          flex: 1,
          minWidth: 0,
          height: '100%',
          overflow: 'auto'
        }}>
          {children}
        </div>
        
        {/* 右側列表區域 */}
        <div style={{ 
          width: '350px',
          flexShrink: 0,
          height: '100%'
        }}>
          <CustomersList 
            onEditCustomer={handleEditCustomer} 
            onShowCustomerInfo={handleShowCustomerInfo}
          />
        </div>
      </div>
    </div>
  );
};

export default CustomersLayout;