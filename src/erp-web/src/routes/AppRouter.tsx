import React, { Suspense } from 'react';
import { Routes, Route } from 'react-router-dom';
import { Spin } from 'antd';
import { routes } from './routeConfig';

const AppRouter: React.FC = () => {
  return (
    <Suspense fallback={
      <div style={{ 
        display: 'flex', 
        justifyContent: 'center', 
        alignItems: 'center', 
        height: '200px' 
      }}>
        <Spin size="large" />
      </div>
    }>
      <Routes>
        {routes.map((route) => (
          <Route
            key={route.key}
            path={route.path}
            element={<route.element />}
          />
        ))}
      </Routes>
    </Suspense>
  );
};

export default AppRouter;