# Docker 容器化文件夾 (docker)

## 概述
此文件夾包含了 MeowManager 專案的所有 Docker 容器化配置文件，用於建立開發、測試與生產環境的容器化部署。

## 文件結構

### 主要配置文件
- `docker-compose.yml` - 生產環境的 Docker Compose 配置
- `docker-compose.test.yml` - 測試環境的 Docker Compose 配置

### 服務配置文件夾
- `MySQL/` - MySQL 資料庫容器配置與初始化腳本
  - `Dockerfile` - MySQL 客製化鏡像建構文件
  - `my.cnf` - MySQL 配置文件
  - `data/` - 資料庫數據文件
  - `init/` - 資料庫初始化腳本

- `nginx/` - Nginx 反向代理伺服器配置
  - `Dockerfile` - Nginx 鏡像建構文件
  - `nginx.conf` - Nginx 配置文件

- `redis/` - Redis 快取服務配置
  - `redis.conf` - Redis 配置文件
  - `data/` - Redis 數據文件

### docker-images/
預建構的 Docker 鏡像文件，包含 ERP 後端、MySQL 和 Nginx 的打包鏡像，打包後上傳EC2的docker進行部署。
