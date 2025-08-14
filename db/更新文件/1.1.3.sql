-- 版本：1.1.3
-- 修改目的：建立庫存管理相關表，包含庫存快照表和庫存異動表
-- 修改日期：2025-08-10

-- 使用 meow_db 數據庫
USE meow_db;

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

-- 更新 DBversion 記錄
UPDATE key_values 
SET `value` = '1.1.3', `updated_at` = CURRENT_TIMESTAMP(3)
WHERE `key` = 'DBversion';