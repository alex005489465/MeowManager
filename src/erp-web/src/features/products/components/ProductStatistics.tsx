import React, { useState, useEffect } from 'react';
import { Card, Row, Col, Statistic, Spin } from 'antd';
import { 
  ShoppingOutlined, 
  CheckCircleOutlined, 
  StopOutlined,
  DollarOutlined
} from '@ant-design/icons';
import { ProductApiService } from '@features/products';
import type { ProductStatistics } from '@features/products/types/product';

const ProductStatisticsComponent: React.FC = () => {
  const [statistics, setStatistics] = useState<ProductStatistics | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadStatistics = async () => {
      try {
        const response = await ProductApiService.getProductStatistics();
        if (response.success && response.data) {
          setStatistics(response.data);
        }
      } catch (error) {
        console.error('載入統計資料錯誤:', error);
      } finally {
        setLoading(false);
      }
    };

    loadStatistics();
  }, []);

  if (loading) {
    return <Spin size="large" />;
  }

  if (!statistics) {
    return null;
  }

  return (
    <Row gutter={16}>
      <Col span={6}>
        <Card>
          <Statistic
            title="總產品數"
            value={statistics.totalProducts}
            prefix={<ShoppingOutlined />}
          />
        </Card>
      </Col>
      <Col span={6}>
        <Card>
          <Statistic
            title="啟用產品"
            value={statistics.activeProducts}
            prefix={<CheckCircleOutlined />}
            valueStyle={{ color: '#3f8600' }}
          />
        </Card>
      </Col>
      <Col span={6}>
        <Card>
          <Statistic
            title="停用產品"
            value={statistics.inactiveProducts}
            prefix={<StopOutlined />}
            valueStyle={{ color: '#cf1322' }}
          />
        </Card>
      </Col>
      <Col span={6}>
        <Card>
          <Statistic
            title="總價值"
            value={statistics.totalValue}
            prefix={<DollarOutlined />}
            precision={2}
            valueStyle={{ color: '#1890ff' }}
          />
        </Card>
      </Col>
    </Row>
  );
};

export default ProductStatisticsComponent;