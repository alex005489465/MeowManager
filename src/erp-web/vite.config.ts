import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react'
import path from 'node:path';
import { fileURLToPath } from 'node:url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);


// https://vite.dev/config/
export default defineConfig({
    plugins: [react()],
    base: '/',
    build: {
        outDir: 'dist',
        assetsDir: 'assets',
        sourcemap: false
    },
    resolve: {
        alias: {
            '@': path.resolve(__dirname, 'src'),
            '@features': path.resolve(__dirname, 'src/features'),
            '@common': path.resolve(__dirname, 'src/common'),
            '@config': path.resolve(__dirname, 'src/config'),
            '@hooks': path.resolve(__dirname, 'src/hooks'),
            '@pages': path.resolve(__dirname, 'src/pages')
        }
    },
    server: {
        //方案一：設置代理到後端 API
        proxy: {
            '/api': {
                //target: 'http://localhost:80', // 替換為您的後端伺服器地址
                changeOrigin: true,
                secure: false,
                // 如果後端 API 路徑不包含 /api 前綴，可以重寫路徑
                // rewrite: (path) => path.replace(/^\/api/, '')
            }
        },
        // cors: {
        //     origin: true, // 允許所有來源，生產環境建議設置具體域名
        //     credentials: true // 如果需要發送 cookies
        // },

    }


})
