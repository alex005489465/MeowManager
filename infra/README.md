# 基礎設施文件夾 (infra)

## 概述
此文件夾包含了 MeowManager 專案在各種雲端平台的基礎設施即程式碼 (Infrastructure as Code) 配置文件，用於自動化部署與管理雲端資源。

## 文件夾結構

### aws/
Amazon Web Services (AWS) 相關的部署配置：
- `cloudfront-config.json` - CloudFront CDN 配置
- `deploy-images-to-ec2*.ps1/.sh` - EC2 實例部署腳本
- `deploy-lambda*.ps1` - Lambda 函數部署腳本
- `docker-compose.ec2.yml` - EC2 環境的 Docker Compose 配置
- `lambda/` - Lambda 函數程式碼
- `scripts/` - 輔助部署腳本

### azure/
Microsoft Azure 雲端平台相關配置：
- `*.bicep` - Azure Bicep 基礎設施範本
- `*.json` - ARM 範本參數文件
- `aci-*.yaml` - Azure Container Instances 配置
- `cloud-init.yaml` - 虛擬機初始化腳本
- `deploy-vm.*` - 虛擬機部署腳本

### test/
測試環境的部署腳本：
- `deploy.bat/.sh` - 跨平台測試環境部署腳本

## 使用說明
選擇對應的雲端平台文件夾，執行相關的部署腳本即可自動建立雲端基礎設施。
