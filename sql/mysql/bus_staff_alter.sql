-- =============================================
-- ESTM 技术人员表结构更新
-- 添加协议签署相关字段
-- =============================================

-- 添加新字段
ALTER TABLE bus_technical_staff 
ADD COLUMN has_confidential_agreement TINYINT(1) DEFAULT 0 COMMENT '是否已签署保密与知识产权协议' AFTER incentive_info,
ADD COLUMN has_invention_pledge TINYINT(1) DEFAULT 0 COMMENT '是否已签署职务发明承诺书' AFTER confidential_file,
ADD COLUMN equity_plan_file VARCHAR(500) NULL COMMENT '股权激励计划文件URL' AFTER has_equity_plan;

-- 更新现有记录的默认值
UPDATE bus_technical_staff SET has_confidential_agreement = 0 WHERE has_confidential_agreement IS NULL;
UPDATE bus_technical_staff SET has_invention_pledge = 0 WHERE has_invention_pledge IS NULL;
