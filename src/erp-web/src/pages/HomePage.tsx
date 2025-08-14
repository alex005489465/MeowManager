import React from 'react';
import { Card, Typography, Button, Space } from 'antd';
import { UserOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';

const { Title, Paragraph } = Typography;

const HomePage: React.FC = () => {
  const navigate = useNavigate();

  const handleGoToCustomers = () => {
    navigate('/customers');
  };


  return (
    <div style={{ 
      padding: '48px',
      height: '100%',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center'
    }}>
      <Card style={{ maxWidth: '600px', textAlign: 'center' }}>
        <Space direction="vertical" size="large" style={{ width: '100%' }}>
          <UserOutlined style={{ fontSize: '64px', color: '#1890ff' }} />
          
          <Title level={1}>歡迎使用客戶管理系統</Title>
          
          <Paragraph style={{ fontSize: '16px', color: '#666' }}>
            這是一個簡單易用的客戶管理系統，幫助您有效管理客戶資訊、
            追蹤客戶狀態，並提供完整的客戶資料編輯功能。
          </Paragraph>
          
          <Button 
            type="primary" 
            size="large"
            icon={<UserOutlined />}
            onClick={handleGoToCustomers}
          >
            管理客戶
          </Button>
        </Space>
      </Card>
    </div>
  );
};

export default HomePage;