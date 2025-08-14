import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { 
  Card, 
  Form, 
  Input, 
  InputNumber, 
  Select, 
  Button, 
  Typography, 
  Space, 
  message,
  Spin
} from 'antd';
import { 
  SaveOutlined, 
  ArrowLeftOutlined,
  EditOutlined
} from '@ant-design/icons';
import ProductsLayout from '../../features/products/components/ProductsLayout';
import { ProductApiService } from '@features/products';
import type { Product, ProductUpdateRequest } from '@features/products/types/product';

const { Title } = Typography;
const { TextArea } = Input;

interface ProductFormValues {
  name: string;
  description?: string;
  price: number;
  stock?: number;
  status: 'ACTIVE' | 'INACTIVE' | 'DISCONTINUED';
  type: 'PHYSICAL' | 'DIGITAL' | 'SERVICE';
}

const ProductEditPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [form] = Form.useForm();
  const [product, setProduct] = useState<Product | null>(null);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);

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
          const productData = response.data;
          setProduct(productData);
          
          // 設定表單初始值
          form.setFieldsValue({
            name: productData.name,
            description: productData.description,
            price: productData.price,
            stock: productData.stock,
            status: productData.status,
            type: productData.type
          });
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
  }, [id, navigate, form]);

  const handleSave = async (values: ProductFormValues) => {
    if (!product) return;

    try {
      setSaving(true);
      
      const updateRequest: ProductUpdateRequest = {
        id: product.id,
        name: values.name,
        description: values.description || '',
        price: values.price,
        stock: values.stock,
        type: values.type
      };

      const response = await ProductApiService.updateProduct(updateRequest);
      
      if (response.success) {
        message.success('產品更新成功');
        navigate(`/products/${product.id}`);
      } else {
        message.error('產品更新失敗');
      }
    } catch (error) {
      console.error('更新產品錯誤:', error);
      message.error('更新產品時發生錯誤');
    } finally {
      setSaving(false);
    }
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
          <Typography.Text>產品不存在</Typography.Text>
        </div>
      </ProductsLayout>
    );
  }

  return (
    <ProductsLayout>
      <div style={{ padding: '24px' }}>
        <Space direction="vertical" size="large" style={{ width: '100%' }}>
          {/* 頁面標題 */}
          <div style={{ display: 'flex', alignItems: 'center' }}>
            <Button 
              icon={<ArrowLeftOutlined />} 
              onClick={() => navigate(`/products/${product.id}`)}
              style={{ marginRight: 16 }}
            >
              返回
            </Button>
            <Title level={2} style={{ margin: 0 }}>
              <EditOutlined /> 編輯產品 - {product.name}
            </Title>
          </div>

          {/* 編輯表單 */}
          <Card title="編輯產品資訊" style={{ width: '100%' }}>
            <Form
              form={form}
              layout="vertical"
              onFinish={handleSave}
              style={{ maxWidth: 600 }}
            >
              <Form.Item
                label="產品名稱"
                name="name"
                rules={[
                  { required: true, message: '請輸入產品名稱' },
                  { min: 1, max: 100, message: '產品名稱長度應為1-100字符' }
                ]}
              >
                <Input placeholder="請輸入產品名稱" />
              </Form.Item>

              <Form.Item
                label="產品描述"
                name="description"
                rules={[
                  { max: 500, message: '產品描述不能超過500字符' }
                ]}
              >
                <TextArea 
                  placeholder="請輸入產品描述"
                  rows={4}
                />
              </Form.Item>


              <Form.Item
                label="價格"
                name="price"
                rules={[
                  { required: true, message: '請輸入產品價格' },
                  { type: 'number', min: 0, message: '價格必須大於等於0' }
                ]}
              >
                <InputNumber
                  placeholder="請輸入產品價格"
                  style={{ width: '100%' }}
                  formatter={value => `NT$ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
                  parser={value => value!.replace(/NT\$\s?|(,*)/g, '')}
                />
              </Form.Item>

              <Form.Item
                label="庫存"
                name="stock"
                rules={[
                  { type: 'number', min: 0, message: '庫存必須大於等於0' }
                ]}
              >
                <InputNumber
                  placeholder="請輸入庫存數量"
                  style={{ width: '100%' }}
                />
              </Form.Item>

              <Form.Item
                label="狀態"
                name="status"
                rules={[{ required: true, message: '請選擇產品狀態' }]}
              >
                <Select placeholder="請選擇產品狀態">
                  <Select.Option value="ACTIVE">啟用</Select.Option>
                  <Select.Option value="INACTIVE">停用</Select.Option>
                  <Select.Option value="DISCONTINUED">停產</Select.Option>
                </Select>
              </Form.Item>

              <Form.Item
                label="類型"
                name="type"
                rules={[{ required: true, message: '請選擇產品類型' }]}
              >
                <Select placeholder="請選擇產品類型">
                  <Select.Option value="PHYSICAL">實體商品</Select.Option>
                  <Select.Option value="DIGITAL">數位商品</Select.Option>
                  <Select.Option value="SERVICE">服務</Select.Option>
                </Select>
              </Form.Item>

              <Form.Item>
                <Space>
                  <Button 
                    type="primary" 
                    htmlType="submit" 
                    loading={saving}
                    icon={<SaveOutlined />}
                  >
                    {saving ? '保存中...' : '保存'}
                  </Button>
                  <Button 
                    onClick={() => navigate(`/products/${product.id}`)}
                  >
                    取消
                  </Button>
                </Space>
              </Form.Item>
            </Form>
          </Card>
        </Space>
      </div>
    </ProductsLayout>
  );
};

export default ProductEditPage;