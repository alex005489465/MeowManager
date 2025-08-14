# 使用多階段構建來優化鏡像大小
# 第一階段：構建應用
FROM openjdk:21-jdk-slim AS builder

# 設置工作目錄
WORKDIR /app

# 複製 Maven 包裝器和 pom.xml
COPY mvnw mvnw.cmd pom.xml ./
COPY .mvn .mvn

# 設置 Maven 包裝器執行權限
RUN chmod +x mvnw

# 下載依賴（這一層會被緩存，除非 pom.xml 改變）
RUN ./mvnw dependency:go-offline -B

# 複製源代碼
COPY src ./src

# 構建應用
RUN ./mvnw clean package -DskipTests -B

# 第二階段：運行應用
FROM openjdk:21-jdk-slim

# 安裝 curl（用於健康檢查）
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# 設置工作目錄
WORKDIR /app

# 創建非 root 用戶來運行應用
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

# 從構建階段複製 JAR 文件
COPY --from=builder /app/target/*.jar app.jar

# 暴露端口
EXPOSE 8080

# 設置 JVM 參數
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -XX:+UseContainerSupport"

# 啟動應用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
