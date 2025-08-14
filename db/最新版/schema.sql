-- MeowManager 資料庫架構檔案
-- 版本：1.1.3
-- 建立日期：2025-08-10
-- 說明：包含完整的資料庫架構，整合了版本 1.1.1 到 1.1.3 的所有變更

-- 設定字符集
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

-- 使用 meow_db 數據庫
USE meow_db;

-- ==========================================
-- 系統配置表
-- ==========================================

-- 創建 key_values 表 - 系統配置參數存儲表
CREATE TABLE key_values
(
    `id`          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主鍵',
    `key`         VARCHAR(255) NOT NULL COMMENT '配置名稱',
    `value`       TEXT NULL COMMENT '配置值',
    `description` VARCHAR(255) NULL COMMENT '說明',
    `created_at`  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '創建時間',
    `updated_at`  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新時間',
    INDEX         idx_key (`key`)
) COMMENT = '系統配置參數存儲表';

-- ==========================================
-- 客戶管理相關表
-- ==========================================

-- 創建 customers 表 - 客戶資料表
CREATE TABLE customers
(
    `id`          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '客戶ID',
    `name`        VARCHAR(100) NULL COMMENT '姓名',
    `nick`        VARCHAR(100) NULL COMMENT '暱稱',
    `gender`      TINYINT NULL COMMENT '性別：1=男、2=女、3=其他',
    `birth_date`  DATE NULL COMMENT '出生日期',
    `fb_account`  VARCHAR(100) NULL COMMENT 'Facebook帳號',
    `line_account` VARCHAR(100) NULL COMMENT 'LINE帳號',
    `email`       VARCHAR(100) NULL COMMENT 'Email',
    `phone`       VARCHAR(30) NULL COMMENT '手機',
    `address`     TEXT NULL COMMENT '地址',
    `note`        TEXT NULL COMMENT '備註',
    `status`      TINYINT NULL COMMENT '狀態(1=啟用、2=暫停、3=黑名單)',
    `created_at`  DATETIME(3) NULL COMMENT '建立時間',
    `updated_at`  DATETIME(3) NULL COMMENT '最後更新時間',
    INDEX         idx_name (`name`),
    INDEX         idx_phone (`phone`),
    INDEX         idx_email (`email`),
    INDEX         idx_status (`status`)
) COMMENT = '客戶資料表';

-- ==========================================
-- 商品管理相關表
-- ==========================================

-- 創建 products 表 - 商品表
CREATE TABLE products
(
    `id`          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '商品ID',
    `name`        VARCHAR(200) NULL COMMENT '商品名稱',
    `type`        TINYINT NULL COMMENT '商品類型：1=寵物食品、2=寵物服務、3=寵物用品',
    `price`       DECIMAL(10,2) NULL COMMENT '商品價格',
    `description` TEXT NULL COMMENT '商品描述',
    `status`      TINYINT NULL COMMENT '商品狀態：1=啟用，0=停用',
    `notes`       TEXT NULL COMMENT '商品備註',
    `created_at`  DATETIME(3) NULL COMMENT '建立時間',
    `updated_at`  DATETIME(3) NULL COMMENT '最後更新時間',
    INDEX         idx_product_name (`name`),
    INDEX         idx_product_type (`type`),
    INDEX         idx_product_status (`status`)
) COMMENT = '商品表';

-- ==========================================
-- 訂單管理相關表
-- ==========================================

-- 創建 orders 表 - 訂單表
CREATE TABLE orders
(
    `id`              BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '訂單ID',
    `no`              VARCHAR(50) NULL COMMENT '訂單編號（前台顯示用，可帶日期+流水號）',
    `customer_id`     BIGINT NULL COMMENT '客戶ID（對應 customers 表，可為 NULL 表示散客）',
    `order_time`      DATETIME(3) NULL COMMENT '下單時間',
    `status`          TINYINT NULL COMMENT '訂單狀態：0=草稿，1=已下單，2=已付款，3=已出貨，4=已完成，5=已取消',
    `total_amount`    DECIMAL(10,2) NULL COMMENT '訂單總額',
    `discount_amount` DECIMAL(10,2) NULL COMMENT '折扣金額',
    `final_amount`    DECIMAL(10,2) NULL COMMENT '實付金額',
    `payment_method`  TINYINT NULL COMMENT '支付方式：1=現金，2=信用卡，3=LINE Pay，4=銀行轉帳，5=行動支付，99=其他',
    `notes`           TEXT NULL COMMENT '備註（例如特殊需求、寵物名）',
    `created_at`      DATETIME(3) NULL COMMENT '建立時間',
    `updated_at`      DATETIME(3) NULL COMMENT '更新時間',
    INDEX             idx_order_no (`no`),
    INDEX             idx_customer_id (`customer_id`),
    INDEX             idx_order_time (`order_time`),
    INDEX             idx_status (`status`)
) COMMENT = '訂單表';

-- 創建 order_items 表 - 訂單明細表
CREATE TABLE order_items
(
    `id`           BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '明細ID',
    `order_id`     BIGINT NULL COMMENT '對應 orders.id',
    `product_id`   BIGINT NULL COMMENT '對應 products.id',
    `product_name` VARCHAR(200) NULL COMMENT '商品名稱（冗餘存，保留下單當時的名稱）',
    `product_type` TINYINT NULL COMMENT '商品類型：1=寵物食品、2=寵物服務、3=寵物用品',
    `qty`          INT NULL COMMENT '購買數量',
    `unit_price`   DECIMAL(10,2) NULL COMMENT '單價（下單當時）',
    `subtotal`     DECIMAL(10,2) NULL COMMENT '小計（qty * unit_price）',
    `notes`        TEXT NULL COMMENT '備註（例如毛色、生日、服務細節）',
    `created_at`   DATETIME(3) NULL COMMENT '建立時間',
    `updated_at`   DATETIME(3) NULL COMMENT '最後更新時間',
    INDEX          idx_order_id (`order_id`),
    INDEX          idx_product_id (`product_id`),
    INDEX          idx_product_type (`product_type`)
) COMMENT = '訂單明細表';

-- 創建 order_payments 表 - 訂單支付表
CREATE TABLE order_payments
(
    `id`             BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '支付記錄ID',
    `order_id`       BIGINT NULL COMMENT '對應 orders.id',
    `payment_method` TINYINT NULL COMMENT '支付方式：1=現金，2=信用卡，3=LINE Pay，4=銀行轉帳，5=行動支付，99=其他',
    `amount`         DECIMAL(10,2) NULL COMMENT '支付金額',
    `paid_time`      DATETIME(3) NULL COMMENT '支付時間',
    `status`         TINYINT NULL COMMENT '支付狀態：1=已支付，0=未支付',
    `notes`          TEXT NULL COMMENT '支付備註（例如支付平台交易號）',
    `created_at`     DATETIME(3) NULL COMMENT '建立時間',
    `updated_at`     DATETIME(3) NULL COMMENT '最後更新時間',
    INDEX            idx_order_id (`order_id`),
    INDEX            idx_payment_method (`payment_method`),
    INDEX            idx_paid_time (`paid_time`)
) COMMENT = '訂單支付表（支援多種支付方式）';

-- ==========================================
-- 庫存管理相關表
-- ==========================================

-- 創建 stock 表 - 庫存快照表
CREATE TABLE stock
(
    `id`         BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '庫存ID',
    `product_id` BIGINT NULL COMMENT '商品ID',
    `qty`        INT NULL COMMENT '庫存數量',
    `avg_cost`   DECIMAL(10,2) NULL COMMENT '平均成本',
    `total_cost` DECIMAL(10,2) NULL COMMENT '庫存價值',
    `created_at` DATETIME(3) NULL COMMENT '建立時間',
    `updated_at` DATETIME(3) NULL COMMENT '最後更新時間',
    INDEX        idx_stock_product (`product_id`)
) COMMENT = '庫存快照表';

-- 創建 stock_movements 表 - 庫存異動表
CREATE TABLE stock_movements
(
    `id`            BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '異動紀錄ID',
    `stock_id`      BIGINT NULL COMMENT '對應 stock.id',
    `product_id`    BIGINT NULL COMMENT '商品ID（冗餘欄位，方便查詢）',
    `movement_type` TINYINT NULL COMMENT '異動類型：1=入庫、2=出庫',
    `qty`           INT NULL COMMENT '異動數量（永遠正數）',
    `unit_cost`     DECIMAL(10,2) NULL COMMENT '單位成本',
    `total_cost`    DECIMAL(10,2) NULL COMMENT '異動總成本',
    `created_at`    DATETIME(3) NULL COMMENT '建立時間',
    `updated_at`    DATETIME(3) NULL COMMENT '最後更新時間',
    INDEX           idx_movement_stock (`stock_id`),
    INDEX           idx_movement_product (`product_id`),
    INDEX           idx_movement_type (`movement_type`),
    INDEX           idx_movement_created (`created_at`)
) COMMENT = '庫存異動表';

-- ==========================================
-- 初始化資料
-- ==========================================

-- 插入 DBversion 記錄
INSERT INTO key_values (`key`, `value`, `description`)
VALUES ('DBversion', '1.1.3', '資料庫架構版本');
