# 自訂 MySQL 映像，包含數據庫結構和測試資料
FROM mysql:8.4.5

# 設定環境變數
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=meow_db
ENV MYSQL_USER=meow_user
ENV MYSQL_PASSWORD=meow_password
ENV TZ=Asia/Taipei

# 複製 SQL 檔案到 Docker 初始化目錄
# Docker 會自動執行 /docker-entrypoint-initdb.d/ 目錄下的 .sql 檔案
COPY db/最新版/schema.sql /docker-entrypoint-initdb.d/01-schema.sql
COPY db/最新版/test_data.sql /docker-entrypoint-initdb.d/02-test_data.sql

# 複製自定義的 MySQL 配置文件
COPY docker/MySQL/my.cnf /etc/mysql/conf.d/custom.cnf

# 設定時區
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 暴露 MySQL 連接埠
EXPOSE 3306

# 使用預設的 MySQL 啟動命令
CMD ["mysqld"]
