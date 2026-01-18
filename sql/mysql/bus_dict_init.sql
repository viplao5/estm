-- =============================================
-- ESTM 企业科技成果管理系统 - 字典数据初始化
-- =============================================

-- 删除已存在的字典类型（如果需要重新初始化)
-- DELETE FROM system_dict_type WHERE type LIKE 'bus_%';
-- DELETE FROM system_dict_data WHERE dict_type LIKE 'bus_%';

-- =============================================
-- 1. 字典类型定义 (system_dict_type)
-- =============================================

-- 学历
INSERT INTO system_dict_type (id, name, type, status, remark, creator, create_time, updater, update_time, deleted, deleted_time) 
VALUES (3001, '学历', 'bus_edu_degree', 0, '人员学历', '1', NOW(), '1', NOW(), 0, NULL);

-- 职称
INSERT INTO system_dict_type (id, name, type, status, remark, creator, create_time, updater, update_time, deleted, deleted_time) 
VALUES (3002, '职称', 'bus_title', 0, '人员职称', '1', NOW(), '1', NOW(), 0, NULL);

-- 知识产权类型
INSERT INTO system_dict_type (id, name, type, status, remark, creator, create_time, updater, update_time, deleted, deleted_time) 
VALUES (3003, '知识产权类型', 'bus_ip_category', 0, '专利/软著/商标等', '1', NOW(), '1', NOW(), 0, NULL);

-- 知识产权状态
INSERT INTO system_dict_type (id, name, type, status, remark, creator, create_time, updater, update_time, deleted, deleted_time) 
VALUES (3004, '知识产权状态', 'bus_ip_status', 0, '审查中/已授权等', '1', NOW(), '1', NOW(), 0, NULL);

-- 知识产权来源
INSERT INTO system_dict_type (id, name, type, status, remark, creator, create_time, updater, update_time, deleted, deleted_time) 
VALUES (3005, '知识产权来源', 'bus_ip_source', 0, '自主研发/合作开发等', '1', NOW(), '1', NOW(), 0, NULL);

-- 技术秘密类型
INSERT INTO system_dict_type (id, name, type, status, remark, creator, create_time, updater, update_time, deleted, deleted_time) 
VALUES (3006, '技术秘密类型', 'bus_secret_type', 0, '技术秘密成果类型', '1', NOW(), '1', NOW(), 0, NULL);

-- 保密级别
INSERT INTO system_dict_type (id, name, type, status, remark, creator, create_time, updater, update_time, deleted, deleted_time) 
VALUES (3007, '保密级别', 'bus_secret_level', 0, '保密级别', '1', NOW(), '1', NOW(), 0, NULL);

-- 研发项目类别
INSERT INTO system_dict_type (id, name, type, status, remark, creator, create_time, updater, update_time, deleted, deleted_time) 
VALUES (3008, '研发项目类别', 'bus_project_category', 0, '研发项目类别', '1', NOW(), '1', NOW(), 0, NULL);

-- 研发项目状态
INSERT INTO system_dict_type (id, name, type, status, remark, creator, create_time, updater, update_time, deleted, deleted_time) 
VALUES (3009, '研发项目状态', 'bus_project_status', 0, '研发项目状态', '1', NOW(), '1', NOW(), 0, NULL);

-- 科研平台级别
INSERT INTO system_dict_type (id, name, type, status, remark, creator, create_time, updater, update_time, deleted, deleted_time) 
VALUES (3010, '科研平台级别', 'bus_platform_level', 0, '科研平台级别', '1', NOW(), '1', NOW(), 0, NULL);

-- 论文收录情况
INSERT INTO system_dict_type (id, name, type, status, remark, creator, create_time, updater, update_time, deleted, deleted_time) 
VALUES (3011, '论文收录情况', 'bus_paper_indexing', 0, 'SCI/EI/CSSCI等', '1', NOW(), '1', NOW(), 0, NULL);

-- 标准类型
INSERT INTO system_dict_type (id, name, type, status, remark, creator, create_time, updater, update_time, deleted, deleted_time) 
VALUES (3012, '标准类型', 'bus_standard_type', 0, '国标/行标/企标等', '1', NOW(), '1', NOW(), 0, NULL);

-- 标准状态
INSERT INTO system_dict_type (id, name, type, status, remark, creator, create_time, updater, update_time, deleted, deleted_time) 
VALUES (3013, '标准状态', 'bus_standard_status', 0, '已发布/立项中等', '1', NOW(), '1', NOW(), 0, NULL);

-- 标准公司角色
INSERT INTO system_dict_type (id, name, type, status, remark, creator, create_time, updater, update_time, deleted, deleted_time) 
VALUES (3014, '标准公司角色', 'bus_standard_role', 0, '牵头/参与等', '1', NOW(), '1', NOW(), 0, NULL);

-- 奖励级别
INSERT INTO system_dict_type (id, name, type, status, remark, creator, create_time, updater, update_time, deleted, deleted_time) 
VALUES (3015, '奖励级别', 'bus_award_level', 0, '国家级/省部级/市级等', '1', NOW(), '1', NOW(), 0, NULL);

-- 奖励等级
INSERT INTO system_dict_type (id, name, type, status, remark, creator, create_time, updater, update_time, deleted, deleted_time) 
VALUES (3016, '奖励等级', 'bus_award_grade', 0, '一等奖/二等奖等', '1', NOW(), '1', NOW(), 0, NULL);

-- 产品类别
INSERT INTO system_dict_type (id, name, type, status, remark, creator, create_time, updater, update_time, deleted, deleted_time) 
VALUES (3017, '产品类别', 'bus_product_category', 0, '产品与服务类别', '1', NOW(), '1', NOW(), 0, NULL);

-- 产品状态
INSERT INTO system_dict_type (id, name, type, status, remark, creator, create_time, updater, update_time, deleted, deleted_time) 
VALUES (3018, '产品状态', 'bus_product_status', 0, '在售/停产等', '1', NOW(), '1', NOW(), 0, NULL);

-- =============================================
-- 2. 字典数据定义 (system_dict_data)
-- =============================================

-- 学历
INSERT INTO system_dict_data (id, sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES 
(30001, 1, '博士', 'doctor', 'bus_edu_degree', 0, 'primary', '', '', '1', NOW(), '1', NOW(), 0),
(30002, 2, '硕士', 'master', 'bus_edu_degree', 0, 'success', '', '', '1', NOW(), '1', NOW(), 0),
(30003, 3, '本科', 'bachelor', 'bus_edu_degree', 0, 'info', '', '', '1', NOW(), '1', NOW(), 0),
(30004, 4, '大专', 'college', 'bus_edu_degree', 0, 'warning', '', '', '1', NOW(), '1', NOW(), 0),
(30005, 5, '其他', 'other', 'bus_edu_degree', 0, '', '', '', '1', NOW(), '1', NOW(), 0);

-- 职称
INSERT INTO system_dict_data (id, sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES 
(30011, 1, '正高级', 'professor', 'bus_title', 0, 'primary', '', '', '1', NOW(), '1', NOW(), 0),
(30012, 2, '副高级', 'associate', 'bus_title', 0, 'success', '', '', '1', NOW(), '1', NOW(), 0),
(30013, 3, '中级', 'intermediate', 'bus_title', 0, 'info', '', '', '1', NOW(), '1', NOW(), 0),
(30014, 4, '初级', 'junior', 'bus_title', 0, 'warning', '', '', '1', NOW(), '1', NOW(), 0),
(30015, 5, '无', 'none', 'bus_title', 0, '', '', '', '1', NOW(), '1', NOW(), 0);

-- 知识产权类型
INSERT INTO system_dict_data (id, sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES 
(30021, 1, '发明专利', 'invention', 'bus_ip_category', 0, 'primary', '', '', '1', NOW(), '1', NOW(), 0),
(30022, 2, '实用新型', 'utility', 'bus_ip_category', 0, 'success', '', '', '1', NOW(), '1', NOW(), 0),
(30023, 3, '外观设计', 'design', 'bus_ip_category', 0, 'info', '', '', '1', NOW(), '1', NOW(), 0),
(30024, 4, '软件著作权', 'software', 'bus_ip_category', 0, 'warning', '', '', '1', NOW(), '1', NOW(), 0),
(30025, 5, '集成电路布图', 'ic_layout', 'bus_ip_category', 0, 'danger', '', '', '1', NOW(), '1', NOW(), 0),
(30026, 6, '植物新品种', 'plant', 'bus_ip_category', 0, '', '', '', '1', NOW(), '1', NOW(), 0),
(30027, 7, '商标', 'trademark', 'bus_ip_category', 0, '', '', '', '1', NOW(), '1', NOW(), 0);

-- 知识产权状态
INSERT INTO system_dict_data (id, sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES 
(30031, 1, '已授权', 'granted', 'bus_ip_status', 0, 'success', '', '', '1', NOW(), '1', NOW(), 0),
(30032, 2, '审查中', 'pending', 'bus_ip_status', 0, 'warning', '', '', '1', NOW(), '1', NOW(), 0),
(30033, 3, '已申请', 'applied', 'bus_ip_status', 0, 'info', '', '', '1', NOW(), '1', NOW(), 0),
(30034, 4, '已驳回', 'rejected', 'bus_ip_status', 0, 'danger', '', '', '1', NOW(), '1', NOW(), 0),
(30035, 5, '已失效', 'expired', 'bus_ip_status', 0, '', '', '', '1', NOW(), '1', NOW(), 0);

-- 知识产权来源
INSERT INTO system_dict_data (id, sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES 
(30041, 1, '自主研发', 'self', 'bus_ip_source', 0, 'primary', '', '', '1', NOW(), '1', NOW(), 0),
(30042, 2, '合作开发', 'cooperation', 'bus_ip_source', 0, 'success', '', '', '1', NOW(), '1', NOW(), 0),
(30043, 3, '委托开发', 'commission', 'bus_ip_source', 0, 'info', '', '', '1', NOW(), '1', NOW(), 0),
(30044, 4, '受让取得', 'transfer', 'bus_ip_source', 0, 'warning', '', '', '1', NOW(), '1', NOW(), 0),
(30045, 5, '其他', 'other', 'bus_ip_source', 0, '', '', '', '1', NOW(), '1', NOW(), 0);

-- 技术秘密类型
INSERT INTO system_dict_data (id, sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES 
(30051, 1, '技术方案', 'technical', 'bus_secret_type', 0, 'primary', '', '', '1', NOW(), '1', NOW(), 0),
(30052, 2, '工艺流程', 'process', 'bus_secret_type', 0, 'success', '', '', '1', NOW(), '1', NOW(), 0),
(30053, 3, '配方', 'formula', 'bus_secret_type', 0, 'info', '', '', '1', NOW(), '1', NOW(), 0),
(30054, 4, '算法模型', 'algorithm', 'bus_secret_type', 0, 'warning', '', '', '1', NOW(), '1', NOW(), 0),
(30055, 5, '其他', 'other', 'bus_secret_type', 0, '', '', '', '1', NOW(), '1', NOW(), 0);

-- 保密级别
INSERT INTO system_dict_data (id, sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES 
(30061, 1, '绝密', 'top_secret', 'bus_secret_level', 0, 'danger', '', '', '1', NOW(), '1', NOW(), 0),
(30062, 2, '机密', 'confidential', 'bus_secret_level', 0, 'warning', '', '', '1', NOW(), '1', NOW(), 0),
(30063, 3, '秘密', 'secret', 'bus_secret_level', 0, 'info', '', '', '1', NOW(), '1', NOW(), 0),
(30064, 4, '内部', 'internal', 'bus_secret_level', 0, 'success', '', '', '1', NOW(), '1', NOW(), 0);

-- 研发项目类别
INSERT INTO system_dict_data (id, sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES 
(30071, 1, '国家级项目', 'national', 'bus_project_category', 0, 'primary', '', '', '1', NOW(), '1', NOW(), 0),
(30072, 2, '省部级项目', 'provincial', 'bus_project_category', 0, 'success', '', '', '1', NOW(), '1', NOW(), 0),
(30073, 3, '市级项目', 'municipal', 'bus_project_category', 0, 'info', '', '', '1', NOW(), '1', NOW(), 0),
(30074, 4, '企业自立项目', 'enterprise', 'bus_project_category', 0, 'warning', '', '', '1', NOW(), '1', NOW(), 0),
(30075, 5, '横向合作项目', 'horizontal', 'bus_project_category', 0, '', '', '', '1', NOW(), '1', NOW(), 0);

-- 研发项目状态
INSERT INTO system_dict_data (id, sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES 
(30081, 1, '立项', 'approved', 'bus_project_status', 0, 'primary', '', '', '1', NOW(), '1', NOW(), 0),
(30082, 2, '进行中', 'ongoing', 'bus_project_status', 0, 'success', '', '', '1', NOW(), '1', NOW(), 0),
(30083, 3, '已结题', 'completed', 'bus_project_status', 0, 'info', '', '', '1', NOW(), '1', NOW(), 0),
(30084, 4, '已验收', 'accepted', 'bus_project_status', 0, 'warning', '', '', '1', NOW(), '1', NOW(), 0),
(30085, 5, '已终止', 'terminated', 'bus_project_status', 0, 'danger', '', '', '1', NOW(), '1', NOW(), 0);

-- 科研平台级别
INSERT INTO system_dict_data (id, sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES 
(30091, 1, '国家级', 'national', 'bus_platform_level', 0, 'primary', '', '', '1', NOW(), '1', NOW(), 0),
(30092, 2, '省部级', 'provincial', 'bus_platform_level', 0, 'success', '', '', '1', NOW(), '1', NOW(), 0),
(30093, 3, '市级', 'municipal', 'bus_platform_level', 0, 'info', '', '', '1', NOW(), '1', NOW(), 0),
(30094, 4, '企业级', 'enterprise', 'bus_platform_level', 0, 'warning', '', '', '1', NOW(), '1', NOW(), 0);

-- 论文收录情况
INSERT INTO system_dict_data (id, sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES 
(30101, 1, 'SCI', 'sci', 'bus_paper_indexing', 0, 'primary', '', '', '1', NOW(), '1', NOW(), 0),
(30102, 2, 'EI', 'ei', 'bus_paper_indexing', 0, 'success', '', '', '1', NOW(), '1', NOW(), 0),
(30103, 3, 'CSSCI', 'cssci', 'bus_paper_indexing', 0, 'info', '', '', '1', NOW(), '1', NOW(), 0),
(30104, 4, '核心期刊', 'core', 'bus_paper_indexing', 0, 'warning', '', '', '1', NOW(), '1', NOW(), 0),
(30105, 5, '普通期刊', 'normal', 'bus_paper_indexing', 0, '', '', '', '1', NOW(), '1', NOW(), 0),
(30106, 6, '会议论文', 'conference', 'bus_paper_indexing', 0, '', '', '', '1', NOW(), '1', NOW(), 0),
(30107, 7, '专著', 'book', 'bus_paper_indexing', 0, '', '', '', '1', NOW(), '1', NOW(), 0);

-- 标准类型
INSERT INTO system_dict_data (id, sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES 
(30111, 1, '国家标准', 'national', 'bus_standard_type', 0, 'primary', '', '', '1', NOW(), '1', NOW(), 0),
(30112, 2, '行业标准', 'industry', 'bus_standard_type', 0, 'success', '', '', '1', NOW(), '1', NOW(), 0),
(30113, 3, '地方标准', 'local', 'bus_standard_type', 0, 'info', '', '', '1', NOW(), '1', NOW(), 0),
(30114, 4, '团体标准', 'group', 'bus_standard_type', 0, 'warning', '', '', '1', NOW(), '1', NOW(), 0),
(30115, 5, '企业标准', 'enterprise', 'bus_standard_type', 0, '', '', '', '1', NOW(), '1', NOW(), 0);

-- 标准状态
INSERT INTO system_dict_data (id, sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES 
(30121, 1, '已发布', 'published', 'bus_standard_status', 0, 'success', '', '', '1', NOW(), '1', NOW(), 0),
(30122, 2, '立项中', 'approved', 'bus_standard_status', 0, 'warning', '', '', '1', NOW(), '1', NOW(), 0),
(30123, 3, '起草中', 'drafting', 'bus_standard_status', 0, 'info', '', '', '1', NOW(), '1', NOW(), 0),
(30124, 4, '审查中', 'reviewing', 'bus_standard_status', 0, 'primary', '', '', '1', NOW(), '1', NOW(), 0),
(30125, 5, '已废止', 'abolished', 'bus_standard_status', 0, 'danger', '', '', '1', NOW(), '1', NOW(), 0);

-- 标准公司角色
INSERT INTO system_dict_data (id, sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES 
(30131, 1, '牵头单位', 'lead', 'bus_standard_role', 0, 'primary', '', '', '1', NOW(), '1', NOW(), 0),
(30132, 2, '主要参与', 'main', 'bus_standard_role', 0, 'success', '', '', '1', NOW(), '1', NOW(), 0),
(30133, 3, '一般参与', 'participant', 'bus_standard_role', 0, 'info', '', '', '1', NOW(), '1', NOW(), 0);

-- 奖励级别
INSERT INTO system_dict_data (id, sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES 
(30141, 1, '国家级', 'national', 'bus_award_level', 0, 'primary', '', '', '1', NOW(), '1', NOW(), 0),
(30142, 2, '省部级', 'provincial', 'bus_award_level', 0, 'success', '', '', '1', NOW(), '1', NOW(), 0),
(30143, 3, '市厅级', 'municipal', 'bus_award_level', 0, 'info', '', '', '1', NOW(), '1', NOW(), 0),
(30144, 4, '行业协会', 'association', 'bus_award_level', 0, 'warning', '', '', '1', NOW(), '1', NOW(), 0),
(30145, 5, '企业内部', 'enterprise', 'bus_award_level', 0, '', '', '', '1', NOW(), '1', NOW(), 0);

-- 奖励等级
INSERT INTO system_dict_data (id, sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES 
(30151, 1, '特等奖', 'special', 'bus_award_grade', 0, 'primary', '', '', '1', NOW(), '1', NOW(), 0),
(30152, 2, '一等奖', 'first', 'bus_award_grade', 0, 'success', '', '', '1', NOW(), '1', NOW(), 0),
(30153, 3, '二等奖', 'second', 'bus_award_grade', 0, 'info', '', '', '1', NOW(), '1', NOW(), 0),
(30154, 4, '三等奖', 'third', 'bus_award_grade', 0, 'warning', '', '', '1', NOW(), '1', NOW(), 0),
(30155, 5, '优秀奖', 'excellent', 'bus_award_grade', 0, '', '', '', '1', NOW(), '1', NOW(), 0);

-- 产品类别
INSERT INTO system_dict_data (id, sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES 
(30161, 1, '硬件产品', 'hardware', 'bus_product_category', 0, 'primary', '', '', '1', NOW(), '1', NOW(), 0),
(30162, 2, '软件产品', 'software', 'bus_product_category', 0, 'success', '', '', '1', NOW(), '1', NOW(), 0),
(30163, 3, '技术服务', 'service', 'bus_product_category', 0, 'info', '', '', '1', NOW(), '1', NOW(), 0),
(30164, 4, '解决方案', 'solution', 'bus_product_category', 0, 'warning', '', '', '1', NOW(), '1', NOW(), 0),
(30165, 5, '其他', 'other', 'bus_product_category', 0, '', '', '', '1', NOW(), '1', NOW(), 0);

-- 产品状态
INSERT INTO system_dict_data (id, sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES 
(30171, 1, '在售', 'on_sale', 'bus_product_status', 0, 'success', '', '', '1', NOW(), '1', NOW(), 0),
(30172, 2, '研发中', 'developing', 'bus_product_status', 0, 'warning', '', '', '1', NOW(), '1', NOW(), 0),
(30173, 3, '已停产', 'discontinued', 'bus_product_status', 0, 'danger', '', '', '1', NOW(), '1', NOW(), 0),
(30174, 4, '待上市', 'coming_soon', 'bus_product_status', 0, 'info', '', '', '1', NOW(), '1', NOW(), 0);
