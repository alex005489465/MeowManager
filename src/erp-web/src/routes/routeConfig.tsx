import { HomeOutlined, UserOutlined, ShoppingOutlined } from '@ant-design/icons';
import { lazy } from 'react';

// 懶加載頁面組件
const HomePage = lazy(() => import('../pages/HomePage'));
const CustomersHomePage = lazy(() => import('../pages/customers/CustomersHomePage'));
const CustomerDetailPage = lazy(() => import('../pages/customers/CustomerDetailPage'));
const CustomerEditPage = lazy(() => import('../pages/customers/CustomerEditPage'));
const CustomerNewPage = lazy(() => import('../pages/customers/CustomerNewPage'));
const ProductsHomePage = lazy(() => import('../pages/products/ProductsHomePage'));
const ProductDetailPage = lazy(() => import('../pages/products/ProductDetailPage'));
const ProductEditPage = lazy(() => import('../pages/products/ProductEditPage'));
const ProductNewPage = lazy(() => import('../pages/products/ProductNewPage'));

export interface RouteConfig {
  path: string;
  element: React.ComponentType;
  key: string;
  label?: string;
  icon?: React.ReactNode;
  showInMenu?: boolean;
  children?: RouteConfig[];
}

export const routes: RouteConfig[] = [
  {
    path: '/',
    element: HomePage,
    key: 'welcome',
    label: '首頁',
    icon: <HomeOutlined />,
    showInMenu: true,
  },
  {
    path: '/customers',
    element: CustomersHomePage,
    key: 'customers',
    label: '客戶管理',
    icon: <UserOutlined />,
    showInMenu: true,
  },
  {
    path: '/products',
    element: ProductsHomePage,
    key: 'products',
    label: '產品管理',
    icon: <ShoppingOutlined />,
    showInMenu: true,
  },
  // 客戶相關的子路由（不在選單中顯示）
  {
    path: '/customers/new',
    element: CustomerNewPage,
    key: 'customers-new',
    showInMenu: false,
  },
  {
    path: '/customers/:id',
    element: CustomerDetailPage,
    key: 'customers-detail',
    showInMenu: false,
  },
  {
    path: '/customers/:id/edit',
    element: CustomerEditPage,
    key: 'customers-edit',
    showInMenu: false,
  },
  // 產品相關的子路由（不在選單中顯示）
  {
    path: '/products/new',
    element: ProductNewPage,
    key: 'products-new',
    showInMenu: false,
  },
  {
    path: '/products/:id',
    element: ProductDetailPage,
    key: 'products-detail',
    showInMenu: false,
  },
  {
    path: '/products/:id/edit',
    element: ProductEditPage,
    key: 'products-edit',
    showInMenu: false,
  },
];

// 根據路徑獲取對應的路由配置
export const getRouteByPath = (pathname: string): RouteConfig | undefined => {
  return routes.find(route => {
    if (route.path.includes(':')) {
      // 處理動態路由 (如 /customer/edit/:id)
      const routePattern = route.path.replace(/:\w+/g, '\\w+');
      const regex = new RegExp(`^${routePattern}$`);
      return regex.test(pathname);
    }
    return route.path === pathname;
  });
};

// 獲取選單項目
export const getMenuItems = () => {
  return routes
    .filter(route => route.showInMenu)
    .map(route => ({
      key: route.key,
      icon: route.icon,
      label: route.label,
    }));
};