import React from 'react';
import ProductsList from './ProductsList';
import { useNavigate } from 'react-router-dom';

interface ProductsLayoutProps {
  children: React.ReactNode;
}

const ProductsLayout: React.FC<ProductsLayoutProps> = ({ children }) => {
  const navigate = useNavigate();

  const handleEditProduct = (productId: number) => {
    navigate(`/products/${productId}/edit`);
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
        <div style={{ 
          flex: 1,
          minWidth: 0,
          height: '100%',
          overflow: 'auto'
        }}>
          {children}
        </div>
        
        <div style={{ 
          width: '350px',
          flexShrink: 0,
          height: '100%'
        }}>
          <ProductsList 
            onEditProduct={handleEditProduct}
          />
        </div>
      </div>
    </div>
  );
};

export default ProductsLayout;