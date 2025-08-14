import React from 'react';
import ProductsLayout from '../../features/products/components/ProductsLayout';
import ProductStatistics from '../../features/products/components/ProductStatistics';
import { Typography } from 'antd';

const { Title } = Typography;

const ProductsHomePage: React.FC = () => {
  return (
    <ProductsLayout>
      <div style={{ padding: '24px' }}>
        <Title level={2}>產品管理</Title>
        <ProductStatistics />
      </div>
    </ProductsLayout>
  );
};

export default ProductsHomePage;