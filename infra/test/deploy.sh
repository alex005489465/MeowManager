#!/bin/bash

echo "=== MeowManager ERP 後端打包與部署腳本 ==="

# 設置顏色輸出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 函數：打印彩色消息
print_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# 檢查 Docker 是否運行
if ! docker info > /dev/null 2>&1; then
    print_error "Docker 未運行，請先啟動 Docker"
    exit 1
fi

# 切換到專案根目錄
cd "$(dirname "$0")"
PROJECT_ROOT=$(pwd)
print_info "專案根目錄: $PROJECT_ROOT"

# 選擇環境
echo ""
echo "請選擇要部署的環境:"
echo "1) 測試環境 (docker-compose.test.yml)"
echo "2) 正式環境 (docker-compose.yml)"
read -p "輸入選項 (1 或 2): " ENV_CHOICE

case $ENV_CHOICE in
    1)
        COMPOSE_FILE="docker-compose.test.yml"
        ENV_NAME="測試環境"
        ;;
    2)
        COMPOSE_FILE="docker-compose.yml"
        ENV_NAME="正式環境"
        ;;
    *)
        print_error "無效選項，使用預設的測試環境"
        COMPOSE_FILE="docker-compose.test.yml"
        ENV_NAME="測試環境"
        ;;
esac

print_info "選擇的環境: $ENV_NAME ($COMPOSE_FILE)"

# 切換到 docker 目錄
cd docker

# 停止現有服務
print_info "停止現有服務..."
docker-compose -f $COMPOSE_FILE down

# 清理舊的鏡像（可選）
read -p "是否要清理舊的 Docker 鏡像? (y/n): " CLEAN_IMAGES
if [[ $CLEAN_IMAGES =~ ^[Yy]$ ]]; then
    print_info "清理舊的鏡像..."
    docker image prune -f
    docker rmi $(docker images --filter "dangling=true" -q --no-trunc) 2>/dev/null || true
fi

# 構建並啟動服務
print_info "構建並啟動服務..."
docker-compose -f $COMPOSE_FILE up --build -d

# 檢查服務狀態
print_info "檢查服務狀態..."
sleep 5
docker-compose -f $COMPOSE_FILE ps

# 顯示服務 URL
echo ""
print_info "=== 服務已啟動，可透過以下 URL 存取 ==="

if [[ $COMPOSE_FILE == "docker-compose.test.yml" ]]; then
    echo "📊 ERP 後端 API: http://localhost:30318"
    echo "📊 Swagger UI: http://localhost:30318/swagger-ui.html"
    echo "🗄️ phpMyAdmin: http://localhost:30317"
    echo "🗄️ MySQL: localhost:30315"
else
    echo "📊 ERP 後端 API: http://localhost:30318"
    echo "📊 Swagger UI: http://localhost:30318/swagger-ui.html"
    echo "🗄️ phpMyAdmin: http://localhost:30317"
    echo "🗄️ MySQL: localhost:30315"
    echo "📦 Redis: localhost:30319"
fi

echo ""
print_info "=== 常用指令 ==="
echo "查看日誌: docker-compose -f $COMPOSE_FILE logs -f"
echo "停止服務: docker-compose -f $COMPOSE_FILE down"
echo "重啟服務: docker-compose -f $COMPOSE_FILE restart"
echo "查看狀態: docker-compose -f $COMPOSE_FILE ps"

echo ""
print_info "部署完成！"
