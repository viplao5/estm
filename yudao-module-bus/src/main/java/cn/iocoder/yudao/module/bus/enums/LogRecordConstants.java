package cn.iocoder.yudao.module.bus.enums;

/**
 * 科技成果管理 操作日志枚举
 * 目的：统一管理，方便维护
 */
public interface LogRecordConstants {

    // ======================= 研发项目 =======================
    String BUS_PROJECT_TYPE = "研发项目";
    String BUS_PROJECT_CREATE_SUB_TYPE = "创建研发项目";
    String BUS_PROJECT_UPDATE_SUB_TYPE = "更新研发项目";
    String BUS_PROJECT_DELETE_SUB_TYPE = "删除研发项目";
    String BUS_PROJECT_VIEW_SUB_TYPE = "查看研发项目";
    String BUS_PROJECT_CREATE_SUCCESS = "创建了研发项目【{{#project.name}}】";
    String BUS_PROJECT_UPDATE_SUCCESS = "更新了研发项目【{{#project.name}}】: {_DIFF{#updateReqVO}}";
    String BUS_PROJECT_DELETE_SUCCESS = "删除了研发项目【{{#project.name}}】";
    String BUS_PROJECT_VIEW_SUCCESS = "查看了研发项目【{{#project.name}}】";

    // ======================= 知识产权 =======================
    String BUS_IP_TYPE = "知识产权";
    String BUS_IP_CREATE_SUB_TYPE = "创建知识产权";
    String BUS_IP_UPDATE_SUB_TYPE = "更新知识产权";
    String BUS_IP_DELETE_SUB_TYPE = "删除知识产权";
    String BUS_IP_VIEW_SUB_TYPE = "查看知识产权";
    String BUS_IP_CREATE_SUCCESS = "创建了知识产权【{{#ip.name}}】";
    String BUS_IP_UPDATE_SUCCESS = "更新了知识产权【{{#ip.name}}】: {_DIFF{#updateReqVO}}";
    String BUS_IP_DELETE_SUCCESS = "删除了知识产权【{{#ip.name}}】";
    String BUS_IP_VIEW_SUCCESS = "查看了知识产权【{{#ip.name}}】";

    // ======================= 技术秘密 =======================
    String BUS_SECRET_TYPE = "技术秘密";
    String BUS_SECRET_CREATE_SUB_TYPE = "创建技术秘密";
    String BUS_SECRET_UPDATE_SUB_TYPE = "更新技术秘密";
    String BUS_SECRET_DELETE_SUB_TYPE = "删除技术秘密";
    String BUS_SECRET_VIEW_SUB_TYPE = "查看技术秘密";
    String BUS_SECRET_CREATE_SUCCESS = "创建了技术秘密【{{#secret.name}}】";
    String BUS_SECRET_UPDATE_SUCCESS = "更新了技术秘密【{{#secret.name}}】: {_DIFF{#updateReqVO}}";
    String BUS_SECRET_DELETE_SUCCESS = "删除了技术秘密【{{#secret.name}}】";
    String BUS_SECRET_VIEW_SUCCESS = "查看了技术秘密【{{#secret.name}}】";

    // ======================= 论文著作 =======================
    String BUS_PAPER_TYPE = "论文著作";
    String BUS_PAPER_CREATE_SUB_TYPE = "创建论文著作";
    String BUS_PAPER_UPDATE_SUB_TYPE = "更新论文著作";
    String BUS_PAPER_DELETE_SUB_TYPE = "删除论文著作";
    String BUS_PAPER_VIEW_SUB_TYPE = "查看论文著作";
    String BUS_PAPER_CREATE_SUCCESS = "创建了论文著作【{{#paper.name}}】";
    String BUS_PAPER_UPDATE_SUCCESS = "更新了论文著作【{{#paper.name}}】: {_DIFF{#updateReqVO}}";
    String BUS_PAPER_DELETE_SUCCESS = "删除了论文著作【{{#paper.name}}】";
    String BUS_PAPER_VIEW_SUCCESS = "查看了论文著作【{{#paper.name}}】";

    // ======================= 标准管理 =======================
    String BUS_STANDARD_TYPE = "标准";
    String BUS_STANDARD_CREATE_SUB_TYPE = "创建标准";
    String BUS_STANDARD_UPDATE_SUB_TYPE = "更新标准";
    String BUS_STANDARD_DELETE_SUB_TYPE = "删除标准";
    String BUS_STANDARD_VIEW_SUB_TYPE = "查看标准";
    String BUS_STANDARD_CREATE_SUCCESS = "创建了标准【{{#standard.name}}】";
    String BUS_STANDARD_UPDATE_SUCCESS = "更新了标准【{{#standard.name}}】: {_DIFF{#updateReqVO}}";
    String BUS_STANDARD_DELETE_SUCCESS = "删除了标准【{{#standard.name}}】";
    String BUS_STANDARD_VIEW_SUCCESS = "查看了标准【{{#standard.name}}】";

    // ======================= 科技奖励 =======================
    String BUS_AWARD_TYPE = "科技奖励";
    String BUS_AWARD_CREATE_SUB_TYPE = "创建科技奖励";
    String BUS_AWARD_UPDATE_SUB_TYPE = "更新科技奖励";
    String BUS_AWARD_DELETE_SUB_TYPE = "删除科技奖励";
    String BUS_AWARD_VIEW_SUB_TYPE = "查看科技奖励";
    String BUS_AWARD_CREATE_SUCCESS = "创建了科技奖励【{{#award.name}}】";
    String BUS_AWARD_UPDATE_SUCCESS = "更新了科技奖励【{{#award.name}}】: {_DIFF{#updateReqVO}}";
    String BUS_AWARD_DELETE_SUCCESS = "删除了科技奖励【{{#award.name}}】";
    String BUS_AWARD_VIEW_SUCCESS = "查看了科技奖励【{{#award.name}}】";

    // ======================= 产品与服务 =======================
    String BUS_PRODUCT_TYPE = "产品与服务";
    String BUS_PRODUCT_CREATE_SUB_TYPE = "创建产品与服务";
    String BUS_PRODUCT_UPDATE_SUB_TYPE = "更新产品与服务";
    String BUS_PRODUCT_DELETE_SUB_TYPE = "删除产品与服务";
    String BUS_PRODUCT_VIEW_SUB_TYPE = "查看产品与服务";
    String BUS_PRODUCT_CREATE_SUCCESS = "创建了产品与服务【{{#product.name}}】";
    String BUS_PRODUCT_UPDATE_SUCCESS = "更新了产品与服务【{{#product.name}}】: {_DIFF{#updateReqVO}}";
    String BUS_PRODUCT_DELETE_SUCCESS = "删除了产品与服务【{{#product.name}}】";
    String BUS_PRODUCT_VIEW_SUCCESS = "查看了产品与服务【{{#product.name}}】";

    // ======================= 科研平台 =======================
    String BUS_PLATFORM_TYPE = "科研平台";
    String BUS_PLATFORM_CREATE_SUB_TYPE = "创建科研平台";
    String BUS_PLATFORM_UPDATE_SUB_TYPE = "更新科研平台";
    String BUS_PLATFORM_DELETE_SUB_TYPE = "删除科研平台";
    String BUS_PLATFORM_VIEW_SUB_TYPE = "查看科研平台";
    String BUS_PLATFORM_CREATE_SUCCESS = "创建了科研平台【{{#platform.name}}】";
    String BUS_PLATFORM_UPDATE_SUCCESS = "更新了科研平台【{{#platform.name}}】: {_DIFF{#updateReqVO}}";
    String BUS_PLATFORM_DELETE_SUCCESS = "删除了科研平台【{{#platform.name}}】";
    String BUS_PLATFORM_VIEW_SUCCESS = "查看了科研平台【{{#platform.name}}】";

    // ======================= 技术人员 =======================
    String BUS_STAFF_TYPE = "技术人员";
    String BUS_STAFF_CREATE_SUB_TYPE = "创建技术人员";
    String BUS_STAFF_UPDATE_SUB_TYPE = "更新技术人员";
    String BUS_STAFF_DELETE_SUB_TYPE = "删除技术人员";
    String BUS_STAFF_VIEW_SUB_TYPE = "查看技术人员";
    String BUS_STAFF_CREATE_SUCCESS = "创建了技术人员【{{#staff.name}}】";
    String BUS_STAFF_UPDATE_SUCCESS = "更新了技术人员【{{#staff.name}}】: {_DIFF{#updateReqVO}}";
    String BUS_STAFF_DELETE_SUCCESS = "删除了技术人员【{{#staff.name}}】";
    String BUS_STAFF_VIEW_SUCCESS = "查看了技术人员【{{#staff.name}}】";

    // ======================= 资质管理 =======================
    String BUS_QUALIFICATION_TYPE = "资质";
    String BUS_QUALIFICATION_CREATE_SUB_TYPE = "创建资质";
    String BUS_QUALIFICATION_UPDATE_SUB_TYPE = "更新资质";
    String BUS_QUALIFICATION_DELETE_SUB_TYPE = "删除资质";
    String BUS_QUALIFICATION_VIEW_SUB_TYPE = "查看资质";
    String BUS_QUALIFICATION_CREATE_SUCCESS = "创建了资质【{{#qualification.name}}】";
    String BUS_QUALIFICATION_UPDATE_SUCCESS = "更新了资质【{{#qualification.name}}】: {_DIFF{#updateReqVO}}";
    String BUS_QUALIFICATION_DELETE_SUCCESS = "删除了资质【{{#qualification.name}}】";
    String BUS_QUALIFICATION_VIEW_SUCCESS = "查看了资质【{{#qualification.name}}】";

}
