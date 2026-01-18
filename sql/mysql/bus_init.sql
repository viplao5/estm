CREATE TABLE `bus_technical_staff` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '人员ID',
  `name` varchar(64) NOT NULL COMMENT '姓名',
  `post` varchar(100) DEFAULT NULL COMMENT '岗位',
  `school` varchar(255) DEFAULT NULL COMMENT '毕业院校',
  `major` varchar(100) DEFAULT NULL COMMENT '专业',
  `edu_degree` varchar(32) DEFAULT NULL COMMENT '学历（数据字典）',
  `title` varchar(64) DEFAULT NULL COMMENT '职称（数据字典）',
  `entry_date` datetime DEFAULT NULL COMMENT '入职日期',
  `is_active` tinyint DEFAULT '1' COMMENT '是否在职（1在职 0离职）',
  `is_certified` tinyint DEFAULT '0' COMMENT '是否被认定为核心技术人员',
  `has_non_compete` tinyint DEFAULT '0' COMMENT '与前单位是否签署竞业禁止协议',
  `has_dispute` tinyint DEFAULT '0' COMMENT '与前单位是否存在潜在纠纷',
  `achievements` text COMMENT '突出成就',
  `background` text COMMENT '背景描述',
  `incentive_info` text COMMENT '其他激励措施',
  `confidential_file` varchar(512) DEFAULT NULL COMMENT '保密与知识产权协议扫描件URL',
  `invention_pledge_file` varchar(512) DEFAULT NULL COMMENT '职务发明承诺书扫描件URL',
  `has_equity_plan` tinyint DEFAULT '0' COMMENT '是否参与股权激励计划',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='核心技术人员表';

CREATE TABLE `bus_research_platform` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '平台ID',
  `name` varchar(255) NOT NULL COMMENT '平台名称',
  `level` varchar(64) DEFAULT NULL COMMENT '平台级别（数据字典）',
  `cert_unit` varchar(255) DEFAULT NULL COMMENT '认定单位',
  `cert_date` datetime DEFAULT NULL COMMENT '认定日期',
  `start_date` datetime DEFAULT NULL COMMENT '有效期-开始',
  `end_date` datetime DEFAULT NULL COMMENT '有效期-结束',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='科研平台表';


CREATE TABLE `bus_research_project` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '项目ID',
  `name` varchar(255) NOT NULL COMMENT '项目名称',
  `budget` decimal(18,2) DEFAULT '0.00' COMMENT '项目预算',
  `leader_user_id` bigint DEFAULT NULL COMMENT '项目总负责人ID',
  `category` varchar(64) DEFAULT NULL COMMENT '项目类别（数据字典）',
  `status` varchar(64) DEFAULT NULL COMMENT '项目状态（数据字典）',
  `start_date` datetime DEFAULT NULL COMMENT '立项日期',
  `end_date` datetime DEFAULT NULL COMMENT '结题日期',
  `intro` text COMMENT '项目简介',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='研发项目表';

CREATE TABLE `bus_intellectual_property` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '资产ID',
  `project_id` bigint DEFAULT NULL COMMENT '关联研发项目ID',
  `name` varchar(255) NOT NULL COMMENT '资产名称',
  `app_number` varchar(128) DEFAULT NULL COMMENT '申请号/登记号',
  `owner` varchar(255) DEFAULT NULL COMMENT '权利人',
  `agency` varchar(255) DEFAULT NULL COMMENT '代理机构',
  `category` varchar(64) DEFAULT NULL COMMENT '资产类别（数据字典）',
  `status` varchar(64) DEFAULT NULL COMMENT '法律状态（数据字典）',
  `source` varchar(64) DEFAULT NULL COMMENT '来源（数据字典）',
  `app_date` datetime DEFAULT NULL COMMENT '申请日',
  `grant_date` datetime DEFAULT NULL COMMENT '授权日/登记日',
  `next_fee_date` datetime DEFAULT NULL COMMENT '下次年费缴纳日',
  `file_url` varchar(512) DEFAULT NULL COMMENT '官文扫描件URL',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识产权表';

CREATE TABLE `bus_technical_secret` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '成果ID',
  `project_id` bigint DEFAULT NULL COMMENT '关联研发项目ID',
  `name` varchar(255) NOT NULL COMMENT '成果名称',
  `type` varchar(64) DEFAULT NULL COMMENT '成果类型',
  `secret_level` varchar(64) DEFAULT NULL COMMENT '保密级别',
  `finish_date` datetime DEFAULT NULL COMMENT '完成日期',
  `description` text COMMENT '成果描述',
  `tech_field` varchar(255) DEFAULT NULL COMMENT '所属技术领域',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='技术秘密表';

CREATE TABLE `bus_paper_work` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '论文专著ID',
  `project_id` bigint DEFAULT NULL COMMENT '关联研发项目ID',
  `title` varchar(255) NOT NULL COMMENT '论文/专著标题',
  `publication` varchar(255) DEFAULT NULL COMMENT '期刊/会议名称',
  `doi` varchar(128) DEFAULT NULL COMMENT 'DOI号',
  `indexing` varchar(64) DEFAULT NULL COMMENT '收录情况',
  `pub_year` int DEFAULT NULL COMMENT '发表年份',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='论文与著作表';


CREATE TABLE `bus_standard` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '标准ID',
  `project_id` bigint DEFAULT NULL COMMENT '关联研发项目ID',
  `name` varchar(255) NOT NULL COMMENT '标准名称',
  `type` varchar(64) DEFAULT NULL COMMENT '标准类型',
  `status` varchar(64) DEFAULT NULL COMMENT '发表状态',
  `company_role` varchar(64) DEFAULT NULL COMMENT '公司角色',
  `pub_date` datetime DEFAULT NULL COMMENT '发布/立项日期',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标准管理表';

CREATE TABLE `bus_award` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '科技奖励ID',
  `project_id` bigint DEFAULT NULL COMMENT '关联研发项目ID',
  `name` varchar(255) NOT NULL COMMENT '科技奖励名称',
  `grant_unit` varchar(255) DEFAULT NULL COMMENT '颁奖单位',
  `level` varchar(64) DEFAULT NULL COMMENT '奖励级别',
  `grade` varchar(64) DEFAULT NULL COMMENT '奖励等级',
  `award_date` datetime DEFAULT NULL COMMENT '获奖日期',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='科技奖励表';

CREATE TABLE `bus_product_service` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '产品ID',
  `name` varchar(255) NOT NULL COMMENT '产品名称',
  `leader` varchar(64) DEFAULT NULL COMMENT '产品负责人',
  `revenue` decimal(18,2) DEFAULT '0.00' COMMENT '最近一年销售收入',
  `profit` decimal(18,2) DEFAULT '0.00' COMMENT '最近一年销售毛利',
  `category` varchar(64) DEFAULT NULL COMMENT '产品类别',
  `status` varchar(64) DEFAULT NULL COMMENT '产品状态',
  `launch_date` datetime DEFAULT NULL COMMENT '上市日期',
  `intro` text COMMENT '产品简介',
  `key_customers` text COMMENT '重要客户名称',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品与服务表';

CREATE TABLE `bus_qualification` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '资质ID',
  `name` varchar(255) NOT NULL COMMENT '资质名称',
  `cert_unit` varchar(255) DEFAULT NULL COMMENT '认证单位',
  `cert_number` varchar(128) DEFAULT NULL COMMENT '资质证书编号',
  `start_date` datetime DEFAULT NULL COMMENT '生效日期',
  `end_date` datetime DEFAULT NULL COMMENT '到期日期',
  `file_url` varchar(512) DEFAULT NULL COMMENT '证明材料扫描件URL',
  `remark` text COMMENT '备注说明',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资质管理表';

CREATE TABLE `bus_achievement_staff` (
  `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `staff_id` bigint NOT NULL COMMENT '人员ID',
  `achievement_id` bigint NOT NULL COMMENT '成果/项目ID',
  `achievement_type` varchar(32) NOT NULL COMMENT '成果类型(PROJECT/IP/SECRET/PAPER/STANDARD/AWARD/PLATFORM/PRODUCT)',
  `author_type` varchar(32) DEFAULT 'INTERNAL' COMMENT '作者类型(INTERNAL内部, EXTERNAL外部)',
  `tenant_id` bigint NOT NULL DEFAULT '0'
) ENGINE=InnoDB COMMENT='成果-人员关联中间表';


CREATE TABLE `bus_product_achievement` (
  `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `achievement_id` bigint NOT NULL COMMENT '成果ID',
  `achievement_type` varchar(32) NOT NULL COMMENT '成果类型(IP/SECRET)',
  `tenant_id` bigint NOT NULL DEFAULT '0'
) ENGINE=InnoDB COMMENT='产品-成果关联中间表';

CREATE TABLE `bus_project_platform` (
  `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `project_id` bigint NOT NULL COMMENT '研发项目ID',
  `platform_id` bigint NOT NULL COMMENT '科研平台ID',
  `tenant_id` bigint NOT NULL DEFAULT '0'
) ENGINE=InnoDB COMMENT='项目-平台关联中间表';