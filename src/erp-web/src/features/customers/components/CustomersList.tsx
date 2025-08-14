import React, { useState, useEffect } from 'react';
import { 
  Card, 
  List, 
  Button, 
  Tag, 
  message, 
  Modal, 
  Typography,
  Spin,
  Pagination
} from 'antd';
import { 
  EditOutlined, 
  PoweroffOutlined,
  CheckCircleOutlined,
  StopOutlined,
  ExclamationCircleOutlined,
  PlusOutlined
} from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import CustomerService from '@features/customers/services/customerService';
import type { Customer, CustomerStatus } from '@features/customers/types/customer';

const { Text, Title } = Typography;
const { confirm } = Modal;

interface CustomersListProps {
  onEditCustomer?: (customerId: number) => void;
  onShowCustomerInfo?: (customerId: number) => void;  // 新增
}

const CustomersList: React.FC<CustomersListProps> = ({ 
  onEditCustomer, 
  onShowCustomerInfo  // 新增
}) => {
  const navigate = useNavigate();
  const [customers, setCustomers] = useState<Customer[]>([]);
  const [loading, setLoading] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalElements, setTotalElements] = useState(0);
  const pageSize = 5;

  // 載入客戶資料
  const loadCustomers = async (page: number = currentPage) => {
    try {
      setLoading(true);
      const response = await CustomerService.getCustomersPage(page, pageSize);
      if (response.content) {
        setCustomers(response.content);
        setTotalElements(response.totalElements);
        setCurrentPage(page);
      } else {
        message.error('載入客戶資料失敗');
      }
    } catch (error) {
      console.error('載入客戶資料錯誤:', error);
      message.error('載入客戶資料時發生錯誤');
    } finally {
      setLoading(false);
    }
  };

  // 組件掛載時載入資料
  useEffect(() => {
    loadCustomers();
  }, []);

  // 處理頁面變更
  const handlePageChange = (page: number) => {
    loadCustomers(page);
  };

  // 狀態顯示組件
  const StatusTag: React.FC<{ status: CustomerStatus }> = ({ status }) => {
    const getStatusConfig = (status: CustomerStatus) => {
      switch (status) {
        case 'ACTIVE':
          return { color: 'success', text: '啟用', icon: <CheckCircleOutlined /> };
        case 'SUSPENDED':
          return { color: 'warning', text: '暫停', icon: <StopOutlined /> };
        case 'BLACKLIST':
          return { color: 'error', text: '黑名單', icon: <ExclamationCircleOutlined /> };
        default:
          return { color: 'default', text: status, icon: null };
      }
    };

    const config = getStatusConfig(status);
    return (
      <Tag color={config.color} icon={config.icon}>
        {config.text}
      </Tag>
    );
  };

  // 處理編輯客戶
  const handleEdit = (customer: Customer) => {
    if (onEditCustomer) {
      onEditCustomer(customer.id);
    } else {
      message.info(`編輯客戶: ${customer.name}`);
      // TODO: 開啟編輯對話框或導航到編輯頁面
    }
  };

  // 處理顯示客戶資訊
  const handleShowCustomerInfo = (customer: Customer) => {
    if (onShowCustomerInfo) {
      onShowCustomerInfo(customer.id);
    } else {
      message.info(`查看客戶資訊: ${customer.name}`);
    }
  };

  // 處理狀態切換
  const handleStatusToggle = (customer: Customer) => {
    const newStatus: CustomerStatus = customer.status === 'ACTIVE' ? 'SUSPENDED' : 'ACTIVE';
    const actionText = newStatus === 'ACTIVE' ? '啟用' : '禁用';
    
    confirm({
      title: `確認${actionText}客戶`,
      content: `您確定要${actionText}客戶「${customer.name}」嗎？`,
      icon: <ExclamationCircleOutlined />,
      okText: '確認',
      cancelText: '取消',
      maskClosable: true,
      onOk: async () => {
        try {
          const response = await CustomerService.updateCustomerStatus(customer.id, newStatus);
          if (response) {
            message.success(`客戶已${actionText}`);
            loadCustomers(); // 重新載入資料
          } else {
            message.error(`${actionText}失敗`);
          }
        } catch (error) {
          console.error('更新狀態錯誤:', error);
          message.error(`${actionText}時發生錯誤`);
        }
      }
    });
  };

  // 新增：處理新增客戶按鈕點擊
  const handleAddCustomer = () => {
    navigate('/customers/new');
  };

    return (
    <Card 
      title={
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <Title level={4} style={{ margin: 0 }}>客戶列表</Title>
          <Button 
            type="primary" 
            size="small"
            icon={<PlusOutlined />}
            onClick={handleAddCustomer}
          >
            新增客戶
          </Button>
        </div>
      }
      size="small"
      style={{ height: '100%' }}
    >
      <Spin spinning={loading}>
        <div style={{ 
          display: 'flex', 
          flexDirection: 'column', 
          height: 'calc(100vh - 200px)', // 固定總高度
          minHeight: '400px' 
        }}>
          {/* 列表內容區域 */}
          <div style={{ flex: 1, overflow: 'auto' }}>
            <List
              dataSource={customers}
              renderItem={(customer) => (
                <List.Item
                  key={customer.id}
                  style={{ 
                    padding: '16px 0',
                    borderBottom: '1px solid #f0f0f0',
                    minHeight: '80px',
                    cursor: 'pointer'
                  }}
                  onClick={() => handleShowCustomerInfo(customer)}
                >
                  <div style={{ 
                    width: '100%',
                    display: 'flex',
                    flexDirection: 'column',
                    gap: '8px'
                  }}>
                    {/* 第一行：名稱和電話 */}
                    <div style={{
                      display: 'flex',
                      alignItems: 'center',
                      gap: '16px'
                    }}>
                      {/* 名稱 */}
                      <div style={{ flex: '0 0 100px', textAlign: 'center' }}>
                        <Text strong style={{ fontSize: '14px' }}>
                          {customer.name}
                        </Text>
                      </div>

                      {/* 電話 */}
                      <div style={{ flex: '0 0 110px', textAlign: 'center' }}>
                        <Text style={{ fontSize: '14px' }}>
                          {customer.phone}
                        </Text>
                      </div>
                    </div>

                    {/* 第二行：狀態和操作按鈕 */}
                    <div style={{
                      display: 'flex',
                      alignItems: 'center',
                      gap: '16px'
                    }}>
                      {/* 狀態 */}
                      <div style={{ flex: '0 0 100px', textAlign: 'center' }}>
                        <StatusTag status={customer.status} />
                      </div>

                      {/* 操作按鈕 */}
                      <div style={{ flex: '0 0 110px', display: 'flex', gap: '8px', justifyContent: 'center' }}>
                        <Button
                          type="text"
                          size="small"
                          icon={<EditOutlined />}
                          onClick={(e) => {
                            e.stopPropagation();
                            handleEdit(customer);
                          }}
                        >
                          編輯
                        </Button>
                        <Button
                          type="text"
                          size="small"
                          icon={<PoweroffOutlined />}
                          onClick={(e) => {
                            e.stopPropagation();
                            handleStatusToggle(customer);
                          }}
                          danger={customer.status === 'ACTIVE'}
                          style={{
                            color: customer.status === 'ACTIVE' ? '#ff4d4f' : '#52c41a'
                          }}
                        >
                          {customer.status === 'ACTIVE' ? '禁用' : '啟用'}
                        </Button>
                      </div>
                    </div>
                  </div>
                </List.Item>
              )}
              locale={{
                emptyText: '暫無客戶資料'
              }}
            />
          </div>
          
          {/* 固定在底部的分頁控制 */}
          {totalElements > 0 && (
            <div style={{ 
              paddingTop: '16px', 
              borderTop: '1px solid #f0f0f0',
              textAlign: 'center'
            }}>
              {/* 第一行：顯示記錄範圍和總數 */}
              <div style={{ marginBottom: '8px', fontSize: '14px', color: '#666' }}>
                第 {((currentPage - 1) * pageSize) + 1}-{Math.min(currentPage * pageSize, totalElements)} 個，共 {totalElements} 個客戶
              </div>
              {/* 第二行：顯示頁碼 */}
              <Pagination
                current={currentPage}
                total={totalElements}
                pageSize={pageSize}
                onChange={handlePageChange}
                showSizeChanger={false}
                showQuickJumper={false}
                // showTotal={(total, [start, end]) => `第 ${start}-${end} 條，共 ${total} 條`}
              />
            </div>
          )}
        </div>
      </Spin>
    </Card>
  );
};

export default CustomersList;