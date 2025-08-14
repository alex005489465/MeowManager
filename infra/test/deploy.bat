@echo off
chcp 65001 >nul
setlocal enabledelayedexpansion

echo === MeowManager ERP 後端打包與部署腳本 ===
echo.

REM 檢查 Docker 是否運行
docker info >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Docker 未運行，請先啟動 Docker
    pause
    exit /b 1
)

REM 切換到專案根目錄
cd /d "%~dp0"
set PROJECT_ROOT=%cd%
echo [INFO] 專案根目錄: %PROJECT_ROOT%

REM 選擇環境
echo.
echo 請選擇要部署的環境:
echo 1^) 測試環境 ^(docker-compose.test.yml^)
echo 2^) 正式環境 ^(docker-compose.yml^)
set /p ENV_CHOICE=輸入選項 (1 或 2): 

if "%ENV_CHOICE%"=="1" (
    set COMPOSE_FILE=docker-compose.test.yml
    set ENV_NAME=測試環境
) else if "%ENV_CHOICE%"=="2" (
    set COMPOSE_FILE=docker-compose.yml
    set ENV_NAME=正式環境
) else (
    echo [WARNING] 無效選項，使用預設的測試環境
    set COMPOSE_FILE=docker-compose.test.yml
    set ENV_NAME=測試環境
)

echo [INFO] 選擇的環境: %ENV_NAME% (%COMPOSE_FILE%)

REM 切換到 docker 目錄
cd docker

REM 停止現有服務
echo [INFO] 停止現有服務...
docker-compose -f %COMPOSE_FILE% down

REM 清理舊的鏡像（可選）
set /p CLEAN_IMAGES=是否要清理舊的 Docker 鏡像? (y/n): 
if /i "%CLEAN_IMAGES%"=="y" (
    echo [INFO] 清理舊的鏡像...
    docker image prune -f
)

REM 構建並啟動服務
echo [INFO] 構建並啟動服務...
docker-compose -f %COMPOSE_FILE% up --build -d

REM 檢查服務狀態
echo [INFO] 檢查服務狀態...
timeout /t 5 >nul
docker-compose -f %COMPOSE_FILE% ps

REM 顯示服務 URL
echo.
echo [INFO] === 服務已啟動，可透過以下 URL 存取 ===

if "%COMPOSE_FILE%"=="docker-compose.test.yml" (
    echo 📊 ERP 後端 API: http://localhost:30318
    echo 📊 Swagger UI: http://localhost:30318/swagger-ui.html
    echo 🗄️ phpMyAdmin: http://localhost:30317
    echo 🗄️ MySQL: localhost:30315
) else (
    echo 📊 ERP 後端 API: http://localhost:30318
    echo 📊 Swagger UI: http://localhost:30318/swagger-ui.html
    echo 🗄️ phpMyAdmin: http://localhost:30317
    echo 🗄️ MySQL: localhost:30315
    echo 📦 Redis: localhost:30319
)

echo.
echo [INFO] === 常用指令 ===
echo 查看日誌: docker-compose -f %COMPOSE_FILE% logs -f
echo 停止服務: docker-compose -f %COMPOSE_FILE% down
echo 重啟服務: docker-compose -f %COMPOSE_FILE% restart
echo 查看狀態: docker-compose -f %COMPOSE_FILE% ps

echo.
echo [INFO] 部署完成！
pause
