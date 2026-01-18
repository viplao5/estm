-- =============================================
-- ESTM 企业科技成果管理系统 - 菜单初始化
-- 当前菜单表最大ID为5010，从5011开始
-- =============================================

-- =============================================
-- 1. 一级目录: 科技成果管理 (ID: 5011)
-- =============================================
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) 
VALUES (5011, '科技成果管理', '', 1, 5, 0, '/bus', 'ep:medal', NULL, NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- =============================================
-- 2. 二级菜单: 10个业务模块
-- =============================================

-- 2.1 核心技术人员 (ID: 5012, parent: 5011)
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) 
VALUES (5012, '核心技术人员', 'bus:technical-staff:list', 2, 1, 5011, 'staff', 'ep:user', 'bus/staff/index', 'BusTechnicalStaff', 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 2.2 科研平台 (ID: 5016, parent: 5011)
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) 
VALUES (5016, '科研平台', 'bus:research-platform:list', 2, 2, 5011, 'platform', 'ep:office-building', 'bus/platform/index', 'BusResearchPlatform', 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 2.3 研发项目 (ID: 5020, parent: 5011)
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) 
VALUES (5020, '研发项目', 'bus:research-project:list', 2, 3, 5011, 'project', 'ep:folder', 'bus/project/index', 'BusResearchProject', 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 2.4 知识产权 (ID: 5024, parent: 5011)
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) 
VALUES (5024, '知识产权', 'bus:intellectual-property:list', 2, 4, 5011, 'ip', 'ep:key', 'bus/ip/index', 'BusIntellectualProperty', 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 2.5 技术秘密 (ID: 5028, parent: 5011)
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) 
VALUES (5028, '技术秘密', 'bus:technical-secret:list', 2, 5, 5011, 'secret', 'ep:lock', 'bus/secret/index', 'BusTechnicalSecret', 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 2.6 论文与著作 (ID: 5032, parent: 5011)
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) 
VALUES (5032, '论文与著作', 'bus:paper-work:list', 2, 6, 5011, 'paper', 'ep:document', 'bus/paper/index', 'BusPaperWork', 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 2.7 标准管理 (ID: 5036, parent: 5011)
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) 
VALUES (5036, '标准管理', 'bus:standard:list', 2, 7, 5011, 'standard', 'ep:notebook', 'bus/standard/index', 'BusStandard', 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 2.8 科技奖励 (ID: 5040, parent: 5011)
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) 
VALUES (5040, '科技奖励', 'bus:award:list', 2, 8, 5011, 'award', 'ep:trophy', 'bus/award/index', 'BusAward', 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 2.9 产品与服务 (ID: 5044, parent: 5011)
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) 
VALUES (5044, '产品与服务', 'bus:product-service:list', 2, 9, 5011, 'product', 'ep:goods', 'bus/product/index', 'BusProductService', 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 2.10 资质管理 (ID: 5048, parent: 5011)
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) 
VALUES (5048, '资质管理', 'bus:qualification:list', 2, 10, 5011, 'qualification', 'ep:postcard', 'bus/qualification/index', 'BusQualification', 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- =============================================
-- 3. 三级按钮: 每个模块的增删改查按钮
-- =============================================

-- 3.1 核心技术人员按钮 (parent: 5012)
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
(5013, '技术人员查询', 'bus:technical-staff:query', 3, 1, 5012, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5014, '技术人员新增', 'bus:technical-staff:create', 3, 2, 5012, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5015, '技术人员修改', 'bus:technical-staff:update', 3, 3, 5012, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5052, '技术人员删除', 'bus:technical-staff:delete', 3, 4, 5012, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 3.2 科研平台按钮 (parent: 5016)
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
(5017, '科研平台查询', 'bus:research-platform:query', 3, 1, 5016, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5018, '科研平台新增', 'bus:research-platform:create', 3, 2, 5016, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5019, '科研平台修改', 'bus:research-platform:update', 3, 3, 5016, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5053, '科研平台删除', 'bus:research-platform:delete', 3, 4, 5016, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 3.3 研发项目按钮 (parent: 5020)
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
(5021, '研发项目查询', 'bus:research-project:query', 3, 1, 5020, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5022, '研发项目新增', 'bus:research-project:create', 3, 2, 5020, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5023, '研发项目修改', 'bus:research-project:update', 3, 3, 5020, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5054, '研发项目删除', 'bus:research-project:delete', 3, 4, 5020, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 3.4 知识产权按钮 (parent: 5024)
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
(5025, '知识产权查询', 'bus:intellectual-property:query', 3, 1, 5024, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5026, '知识产权新增', 'bus:intellectual-property:create', 3, 2, 5024, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5027, '知识产权修改', 'bus:intellectual-property:update', 3, 3, 5024, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5055, '知识产权删除', 'bus:intellectual-property:delete', 3, 4, 5024, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 3.5 技术秘密按钮 (parent: 5028)
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
(5029, '技术秘密查询', 'bus:technical-secret:query', 3, 1, 5028, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5030, '技术秘密新增', 'bus:technical-secret:create', 3, 2, 5028, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5031, '技术秘密修改', 'bus:technical-secret:update', 3, 3, 5028, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5056, '技术秘密删除', 'bus:technical-secret:delete', 3, 4, 5028, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 3.6 论文与著作按钮 (parent: 5032)
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
(5033, '论文著作查询', 'bus:paper-work:query', 3, 1, 5032, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5034, '论文著作新增', 'bus:paper-work:create', 3, 2, 5032, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5035, '论文著作修改', 'bus:paper-work:update', 3, 3, 5032, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5057, '论文著作删除', 'bus:paper-work:delete', 3, 4, 5032, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 3.7 标准管理按钮 (parent: 5036)
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
(5037, '标准查询', 'bus:standard:query', 3, 1, 5036, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5038, '标准新增', 'bus:standard:create', 3, 2, 5036, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5039, '标准修改', 'bus:standard:update', 3, 3, 5036, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5058, '标准删除', 'bus:standard:delete', 3, 4, 5036, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 3.8 科技奖励按钮 (parent: 5040)
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
(5041, '科技奖励查询', 'bus:award:query', 3, 1, 5040, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5042, '科技奖励新增', 'bus:award:create', 3, 2, 5040, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5043, '科技奖励修改', 'bus:award:update', 3, 3, 5040, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5059, '科技奖励删除', 'bus:award:delete', 3, 4, 5040, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 3.9 产品与服务按钮 (parent: 5044)
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
(5045, '产品服务查询', 'bus:product-service:query', 3, 1, 5044, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5046, '产品服务新增', 'bus:product-service:create', 3, 2, 5044, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5047, '产品服务修改', 'bus:product-service:update', 3, 3, 5044, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5060, '产品服务删除', 'bus:product-service:delete', 3, 4, 5044, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 3.10 资质管理按钮 (parent: 5048)
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
(5049, '资质查询', 'bus:qualification:query', 3, 1, 5048, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5050, '资质新增', 'bus:qualification:create', 3, 2, 5048, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5051, '资质修改', 'bus:qualification:update', 3, 3, 5048, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'),
(5061, '资质删除', 'bus:qualification:delete', 3, 4, 5048, '', '', '', NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- =============================================
-- 菜单汇总说明:
-- 一级目录: 5011 (科技成果管理)
-- 二级菜单: 5012, 5016, 5020, 5024, 5028, 5032, 5036, 5040, 5044, 5048
-- 三级按钮: 5013-5015, 5017-5019, 5021-5023, 5025-5027, 5029-5031, 
--          5033-5035, 5037-5039, 5041-5043, 5045-5047, 5049-5051, 5052-5061
-- 总计: 1个目录 + 10个菜单 + 40个按钮 = 51条记录
-- ID范围: 5011 - 5061
-- =============================================
