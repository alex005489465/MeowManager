#!/bin/bash

# 等待 MySQL 完全啟動
echo "等待 MySQL 服務啟動..."
until mysqladmin ping -h localhost --silent; do
    sleep 1
done

echo "開始執行掛載目錄中的 SQL 檔案..."

# 切換到掛載的 SQL 檔案目錄
cd /var/lib/mysql-files

# 先執行 schema.sql
sql_file="schema.sql"
if [ -f "$sql_file" ]; then
    echo "執行檔案: $sql_file"
    mysql -u root -p"$MYSQL_ROOT_PASSWORD" "$MYSQL_DATABASE" < "$sql_file"
    if [ $? -eq 0 ]; then
        echo "✓ $sql_file 執行成功"
    else
        echo "✗ $sql_file 執行失敗"
        exit 1
    fi
else
    echo "✗ 找不到檔案: $sql_file"
    exit 1
fi

# 再執行 test_data.sql
sql_file="test_data.sql"
if [ -f "$sql_file" ]; then
    echo "執行檔案: $sql_file"
    mysql -u root -p"$MYSQL_ROOT_PASSWORD" "$MYSQL_DATABASE" < "$sql_file"
    if [ $? -eq 0 ]; then
        echo "✓ $sql_file 執行成功"
    else
        echo "✗ $sql_file 執行失敗"
        exit 1
    fi
else
    echo "✗ 找不到檔案: $sql_file"
    exit 1
fi

echo "所有 SQL 檔案執行完成！"
