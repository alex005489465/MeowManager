import React from 'react';
import { Card, Typography } from 'antd';

const { Title, Paragraph } = Typography;

const CustomerIntroduction: React.FC = () => {
  return (
    <div>
      {/* 介紹內容 */}
      <Card>
      <Title level={2}>客戶管理系統</Title>
      <Paragraph>
        歡迎使用客戶管理系統！這裡將會是主要的工作區域，您可以在右側的客戶列表中查看所有客戶資訊。
        未來這個區域將會包含詳細的客戶資訊展示、統計圖表、以及其他管理功能。
      </Paragraph>
      <Paragraph>
        目前系統支援的功能包括：
      </Paragraph>
      <ul>
        <li>查看客戶基本資訊（姓名、電話、狀態）</li>
        <li>編輯客戶資料</li>
        <li>啟用或禁用客戶帳戶</li>
        <li>客戶狀態管理</li>
      </ul>
    </Card>
    </div>
  );
};

export default CustomerIntroduction;