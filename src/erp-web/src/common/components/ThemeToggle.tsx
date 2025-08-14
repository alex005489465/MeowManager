import React from 'react';
import { Button } from 'antd';
import { BulbOutlined, BulbFilled } from '@ant-design/icons';
import { useTheme } from '../../contexts/ThemeContext';

const ThemeToggle: React.FC = () => {
  const { theme, toggleTheme } = useTheme();

  return (
    <Button
      type="text"
      icon={theme === 'dark' ? <BulbOutlined /> : <BulbFilled />}
      onClick={toggleTheme}
      title={theme === 'dark' ? '切換到亮色模式' : '切換到暗色模式'}
      style={{
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
      }}
    >
      {theme === 'dark' ? '亮色' : '暗色'}
    </Button>
  );
};

export default ThemeToggle;