import { useNavigate, useLocation } from 'react-router-dom';
import { getRouteByPath } from '../routes/routeConfig';

export const useNavigation = () => {
  const navigate = useNavigate();
  const location = useLocation();

  // 獲取當前路由的選中鍵值
  const getSelectedKey = () => {
    const currentRoute = getRouteByPath(location.pathname);
    if (currentRoute) {
      return currentRoute.key;
    }
    return 'welcome';
  };

  // 導航到指定路由
  const navigateToRoute = (key: string) => {
    const routeMap: Record<string, string> = {
      'welcome': '/',
      'customers': '/customers',
      'products': '/products',
    };

    const path = routeMap[key];
    if (path) {
      navigate(path);
    }
  };

  return {
    getSelectedKey,
    navigateToRoute,
    currentPath: location.pathname,
  };
};