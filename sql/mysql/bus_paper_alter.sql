-- 为论文专著表添加外部作者名单字段
ALTER TABLE `bus_paper_work` ADD COLUMN `external_authors` varchar(512) DEFAULT NULL COMMENT '外部作者名单' AFTER `pub_year`;
