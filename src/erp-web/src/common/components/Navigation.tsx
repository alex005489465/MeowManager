import React from 'react';
import { Menu, Layout, Space } from 'antd';
import { UserOutlined } from '@ant-design/icons';
import type { MenuProps } from 'antd';
import ThemeToggle from './ThemeToggle';
import { getMenuItems } from '../../routes/routeConfig';
import { useNavigation } from '../../hooks/useNavigation';

const { Header } = Layout;

const Navigation: React.FC = () => {
  const { getSelectedKey, navigateToRoute } = useNavigation();
  const menuItems = getMenuItems();

  const handleMenuClick: MenuProps['onClick'] = (e) => {
    navigateToRoute(e.key);
  };

  return (
    <Header style={{ 
      padding: '0 24px', 
      background: 'var(--ant-color-bg-container)',
      borderBottom: '1px solid var(--ant-color-border)',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'space-between'
    }}>
      <div style={{ 
        display: 'flex', 
        alignItems: 'center',
        fontSize: '18px',
        fontWeight: 'bold',
        color: 'var(--ant-color-text)'
      }}>
        <UserOutlined style={{ marginRight: '8px', fontSize: '20px' }} />
        客戶管理系統
      </div>
      
      <Space>
        <Menu
          mode="horizontal"
          selectedKeys={[getSelectedKey()]}
          items={menuItems}
          onClick={handleMenuClick}
          style={{ 
            border: 'none',
            background: 'transparent',
            minWidth: '200px'
          }}
        />
        <ThemeToggle />
      </Space>
    </Header>
  );
};

export default Navigation;