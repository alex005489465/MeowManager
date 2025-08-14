# Nginx API Gateway
FROM nginx:1.25-alpine

# 安裝 OpenSSL 以生成自簽名證書
RUN apk add --no-cache openssl

# 創建 SSL 證書目錄
RUN mkdir -p /etc/nginx/ssl

# 生成自簽名證書
RUN openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
    -keyout /etc/nginx/ssl/server.key \
    -out /etc/nginx/ssl/server.crt \
    -subj "/C=TW/ST=Taiwan/L=Taipei/O=MeowManager/CN=localhost"

# 複製自定義配置
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 設定時區
ENV TZ=Asia/Taipei
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 暴露端口
EXPOSE 80 443

# 使用默認的 Nginx 啟動命令
CMD ["nginx", "-g", "daemon off;"]
