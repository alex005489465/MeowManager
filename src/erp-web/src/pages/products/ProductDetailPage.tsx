import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { 
  Card, 
  Descriptions, 
  Button, 
  Tag, 
  Typography, 
  Space, 
  message,
  Spin
} from 'antd';
import { 
  EditOutlined, 
  ArrowLeftOutlined,
  ShoppingOutlined
} from '@ant-design/icons';
import ProductsLayout from '../../features/products/components/ProductsLayout';
import { ProductApiService } from '@features/products';
import type { Product, ProductStatus, ProductType } from '@features/products/types/product';

const { Title, Text } = Typography;

const ProductDetailPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [product, setProduct] = useState<Product | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadProduct = async () => {
      if (!id) {
        message.error('產品ID無效');
        navigate('/products');
        return;
      }

      try {
        setLoading(true);
        const response = await ProductApiService.getProductById({ id: parseInt(id) });
        
        if (response.success && response.data) {
          setProduct(response.data);
        } else {
          message.error('載入產品資料失敗');
          navigate('/products');
        }
      } catch (error) {
        console.error('載入產品資料錯誤:', error);
        message.error('載入產品資料時發生錯誤');
        navigate('/products');
      } finally {
        setLoading(false);
      }
    };

    loadProduct();
  }, [id, navigate]);

  // 產品狀態標籤
  const getStatusTag = (status: ProductStatus) => {
    const configs = {
      'ACTIVE': { color: 'success', text: '啟用' },
      'INACTIVE': { color: 'default', text: '停用' },
      'DISCONTINUED': { color: 'error', text: '停產' }
    };
    const config = configs[status] || { color: 'default', text: status };
    return <Tag color={config.color}>{config.text}</Tag>;
  };

  // 產品類型標籤
  const getTypeTag = (type: ProductType) => {
    const configs = {
      'PHYSICAL': { color: 'blue', text: '實體商品' },
      'DIGITAL': { color: 'cyan', text: '數位商品' },
      'SERVICE': { color: 'green', text: '服務' }
    };
    const config = configs[type] || { color: 'default', text: type };
    return <Tag color={config.color}>{config.text}</Tag>;
  };

  if (loading) {
    return (
      <ProductsLayout>
        <div style={{ 
          display: 'flex', 
          justifyContent: 'center', 
          alignItems: 'center', 
          height: '100%' 
        }}>
          <Spin size="large" />
        </div>
      </ProductsLayout>
    );
  }

  if (!product) {
    return (
      <ProductsLayout>
        <div style={{ padding: '24px' }}>
          <Text>產品不存在</Text>
        </div>
      </ProductsLayout>
    );
  }

  return (
    <ProductsLayout>
      <div style={{ padding: '24px' }}>
        <Space direction="vertical" size="large" style={{ width: '100%' }}>
          {/* 頁面標題和操作按鈕 */}
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div>
              <Button 
                icon={<ArrowLeftOutlined />} 
                onClick={() => navigate('/products')}
                style={{ marginRight: 16 }}
              >
                返回
              </Button>
              <Title level={2} style={{ margin: 0, display: 'inline' }}>
                <ShoppingOutlined /> {product.name}
              </Title>
            </div>
            <Button 
              type="primary" 
              icon={<EditOutlined />}
              onClick={() => navigate(`/products/${product.id}/edit`)}
            >
              編輯產品
            </Button>
          </div>

          {/* 產品基本資訊 */}
          <Card title="基本資訊" style={{ width: '100%' }}>
            <Descriptions column={2} bordered>
              <Descriptions.Item label="產品名稱" span={2}>
                {product.name}
              </Descriptions.Item>
              <Descriptions.Item label="產品描述" span={2}>
                {product.description || '無描述'}
              </Descriptions.Item>
              <Descriptions.Item label="價格">
                NT$ {product.price.toLocaleString()}
              </Descriptions.Item>
              <Descriptions.Item label="狀態">
                {getStatusTag(product.status)}
              </Descriptions.Item>
              <Descriptions.Item label="類型">
                {getTypeTag(product.type)}
              </Descriptions.Item>
              {product.stock !== undefined && (
                <Descriptions.Item label="庫存">
                  {product.stock}
                </Descriptions.Item>
              )}
              <Descriptions.Item label="創建時間">
                {product.createdAt ? new Date(product.createdAt).toLocaleString('zh-TW') : '無資料'}
              </Descriptions.Item>
              <Descriptions.Item label="更新時間">
                {product.updatedAt ? new Date(product.updatedAt).toLocaleString('zh-TW') : '無資料'}
              </Descriptions.Item>
            </Descriptions>
          </Card>
        </Space>
      </div>
    </ProductsLayout>
  );
};

export default ProductDetailPage;