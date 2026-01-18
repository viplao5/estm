CREATE TABLE `bus_project_achievement` (
  `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `project_id` bigint NOT NULL COMMENT '研发项目ID',
  `achievement_id` bigint NOT NULL COMMENT '成果ID',
  `achievement_type` varchar(32) NOT NULL COMMENT '成果类型(IP/SECRET/PAPER/STANDARD/AWARD)',
  `tenant_id` bigint NOT NULL DEFAULT '0'
) ENGINE=InnoDB COMMENT='项目-成果关联中间表';
