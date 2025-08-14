import { BrowserRouter as Router } from 'react-router-dom';
import { ConfigProvider, theme, Layout } from 'antd';
import zhTW from 'antd/locale/zh_TW';
import { Navigation } from '@common/components';
import AppRouter from '@/routes/AppRouter';
import { ThemeProvider, useTheme } from '@/contexts/ThemeContext';
import '@/App.css';

const { Content } = Layout;

function AppContent() {
  const { theme: currentTheme } = useTheme();

  return (
    <ConfigProvider 
      locale={zhTW}
      theme={{
        algorithm: currentTheme === 'dark' ? theme.darkAlgorithm : theme.defaultAlgorithm,
      }}
    >
      <Router>
        <Layout style={{ 
          height: '100vh',  // 固定外層高度為視窗高度
          overflow: 'hidden'  // 禁止外層產生滾動條
        }}>
          <Navigation />
          <Content style={{
            flex: 1,  // 佔用剩餘空間
            overflow: 'auto',  // 內層負責滾動
            display: 'flex',
            flexDirection: 'column'
          }}>
            <AppRouter />
          </Content>
        </Layout>
      </Router>
    </ConfigProvider>
  );
}

function App() {
  return (
    <ThemeProvider>
      <AppContent />
    </ThemeProvider>
  );
}

export default App;
