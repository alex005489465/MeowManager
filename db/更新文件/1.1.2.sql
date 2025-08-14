-- 版本：1.1.2
-- 修改目的：建立訂單系統相關表，包含商品表、訂單表、訂單明細表和訂單支付表
-- 修改日期：2025-08-06

-- 使用 meow_db 數據庫
USE meow_db;

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

-- 更新 DBversion 記錄
UPDATE key_values 
SET `value` = '1.1.2', `updated_at` = CURRENT_TIMESTAMP(3)
WHERE `key` = 'DBversion';
