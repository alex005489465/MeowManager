import React, { useState, useEffect } from 'react';
import { 
  Card, 
  List, 
  Button, 
  Tag, 
  message, 
  Typography,
  Spin,
  Pagination
} from 'antd';
import { 
  EditOutlined, 
  PlusOutlined,
  ShoppingOutlined,
  StockOutlined
} from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import { ProductApiService } from '@features/products';
import type { Product, ProductStatus, ProductType } from '@features/products/types/product';
import StockAdjustmentModal from '../../stock/components/StockAdjustmentModal';

const { Text } = Typography;

interface ProductsListProps {
  onEditProduct?: (productId: number) => void;
}

const ProductsList: React.FC<ProductsListProps> = ({ 
  onEditProduct
}) => {
  const navigate = useNavigate();
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalElements, setTotalElements] = useState(0);
  const [stockModalVisible, setStockModalVisible] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);
  const pageSize = 5;

  // 載入產品資料
  const loadProducts = async (page: number = currentPage) => {
    try {
      setLoading(true);
      const response = await ProductApiService.getAllProducts({ 
        page: page - 1, 
        size: pageSize 
      });
      
      if (response.success && response.data) {
        setProducts(response.data.content);
        setTotalElements(response.data.totalElements);
        setCurrentPage(page);
      } else {
        message.error('載入產品資料失敗');
      }
    } catch (error) {
      console.error('載入產品資料錯誤:', error);
      message.error('載入產品資料時發生錯誤');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadProducts();
  }, []);

  const handleStockAdjustment = (product: Product) => {
    setSelectedProduct(product);
    setStockModalVisible(true);
  };

  const handleStockModalClose = () => {
    setStockModalVisible(false);
    setSelectedProduct(null);
  };

  const handleStockAdjustmentSuccess = () => {
    loadProducts(); // 重新載入產品列表以更新庫存顯示
  };

  // 產品狀態標籤
  const ProductStatusTag: React.FC<{ status: ProductStatus }> = ({ status }) => {
    const getStatusConfig = (status: ProductStatus) => {
      switch (status) {
        case 'ACTIVE':
          return { color: 'success', text: '啟用' };
        case 'INACTIVE':
          return { color: 'default', text: '停用' };
        case 'DISCONTINUED':
          return { color: 'error', text: '停產' };
        default:
          return { color: 'default', text: status };
      }
    };

    const config = getStatusConfig(status);
    return <Tag color={config.color}>{config.text}</Tag>;
  };

  // 產品類型標籤
  const ProductTypeTag: React.FC<{ type: ProductType }> = ({ type }) => {
    const getTypeConfig = (type: ProductType) => {
      switch (type) {
        case 'PHYSICAL':
          return { color: 'blue', text: '實體商品' };
        case 'DIGITAL':
          return { color: 'cyan', text: '數位商品' };
        case 'SERVICE':
          return { color: 'green', text: '服務' };
        default:
          return { color: 'default', text: type };
      }
    };

    const config = getTypeConfig(type);
    return <Tag color={config.color}>{config.text}</Tag>;
  };

  return (
    <Card 
      title={
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <span><ShoppingOutlined /> 產品列表</span>
          <Button 
            type="primary" 
            icon={<PlusOutlined />}
            onClick={() => navigate('/products/new')}
          >
            新增產品
          </Button>
        </div>
      }
      style={{ height: '100%', display: 'flex', flexDirection: 'column' }}
      bodyStyle={{ flex: 1, display: 'flex', flexDirection: 'column', padding: 0 }}
    >
      <Spin spinning={loading} style={{ flex: 1 }}>
        <List
          itemLayout="vertical"
          dataSource={products}
          style={{ flex: 1, overflow: 'auto' }}
          renderItem={(product) => (
            <List.Item
              style={{ 
                padding: '12px 16px',
                borderBottom: '1px solid #f0f0f0',
                cursor: 'pointer'
              }}
              onClick={() => navigate(`/products/${product.id}`)}
              actions={[
                <Button 
                  type="text" 
                  icon={<EditOutlined />} 
                  onClick={(e) => {
                    e.stopPropagation();
                    onEditProduct?.(product.id);
                  }}
                >
                  編輯產品
                </Button>,
                <Button 
                  type="text" 
                  icon={<StockOutlined />} 
                  onClick={(e) => {
                    e.stopPropagation();
                    handleStockAdjustment(product);
                  }}
                >
                  調整庫存
                </Button>
              ]}
            >
              <List.Item.Meta
                title={
                  <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                    <div>
                      <Text strong>{product.name}</Text>
                      <Text style={{ marginLeft: 16, color: '#1890ff', fontWeight: 'bold' }}>
                        價格: NT$ {product.price.toLocaleString()}
                      </Text>
                    </div>
                  </div>
                }
                description={
                  <div style={{ display: 'flex', flexDirection: 'column', gap: 8 }}>
                    <div>
                      {product.stock !== undefined && (
                        <Text type="secondary">
                          庫存: {product.stock}
                        </Text>
                      )}
                    </div>
                    <div>
                      <ProductStatusTag status={product.status} />
                      <ProductTypeTag type={product.type} />
                    </div>
                  </div>
                }
              />
            </List.Item>
          )}
        />
      </Spin>
      
      <div style={{ padding: '16px', textAlign: 'center', borderTop: '1px solid #f0f0f0' }}>
        <Pagination
          current={currentPage}
          pageSize={pageSize}
          total={totalElements}
          onChange={setCurrentPage}
          showSizeChanger={false}
          showQuickJumper={false}
          showTotal={(total, range) => `${range[0]}-${range[1]} / 共 ${total} 項`}
        />
      </div>

      {/* 庫存調整模態框 */}
      {selectedProduct && (
        <StockAdjustmentModal
          visible={stockModalVisible}
          productId={selectedProduct.id}
          productName={selectedProduct.name}
          onClose={handleStockModalClose}
          onSuccess={handleStockAdjustmentSuccess}
        />
      )}
    </Card>
  );
};

export default ProductsList;