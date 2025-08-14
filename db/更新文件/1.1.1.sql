-- 版本：1.1.1
-- 修改目的：初始化資料庫架構，建立 key_values 系統配置表和 customers 客戶資料表
-- 修改日期：2025-08-05

-- 使用 meow_db 數據庫
USE meow_db;

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

-- 插入 DBversion 記錄
INSERT INTO key_values (`key`, `value`, `description`)
VALUES ('DBversion', '1.1.1', '資料庫架構版本');
