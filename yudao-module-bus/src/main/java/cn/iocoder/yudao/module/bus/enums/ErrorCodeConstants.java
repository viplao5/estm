package cn.iocoder.yudao.module.bus.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * Bus 模块错误码枚举类
 *
 * bus 系统模块，使用 1-020-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== 核心技术人员 1-020-001-000 ==========
    ErrorCode TECHNICAL_STAFF_NOT_EXISTS = new ErrorCode(1_020_001_000, "核心技术人员不存在");

    // ========== 科研平台 1-020-002-000 ==========
    ErrorCode RESEARCH_PLATFORM_NOT_EXISTS = new ErrorCode(1_020_002_000, "科研平台不存在");

    // ========== 研发项目 1-020-003-000 ==========
    ErrorCode RESEARCH_PROJECT_NOT_EXISTS = new ErrorCode(1_020_003_000, "研发项目不存在");

    // ========== 知识产权 1-020-004-000 ==========
    ErrorCode INTELLECTUAL_PROPERTY_NOT_EXISTS = new ErrorCode(1_020_004_000, "知识产权不存在");

    // ========== 技术秘密 1-020-005-000 ==========
    ErrorCode TECHNICAL_SECRET_NOT_EXISTS = new ErrorCode(1_020_005_000, "技术秘密不存在");

    // ========== 论文与著作 1-020-006-000 ==========
    ErrorCode PAPER_WORK_NOT_EXISTS = new ErrorCode(1_020_006_000, "论文与著作不存在");

    // ========== 标准 1-020-007-000 ==========
    ErrorCode STANDARD_NOT_EXISTS = new ErrorCode(1_020_007_000, "标准不存在");

    // ========== 科技奖励 1-020-008-000 ==========
    ErrorCode AWARD_NOT_EXISTS = new ErrorCode(1_020_008_000, "科技奖励不存在");

    // ========== 产品与服务 1-020-009-000 ==========
    ErrorCode PRODUCT_SERVICE_NOT_EXISTS = new ErrorCode(1_020_009_000, "产品与服务不存在");

    // ========== 资质 1-020-010-000 ==========
    ErrorCode QUALIFICATION_NOT_EXISTS = new ErrorCode(1_020_010_000, "资质不存在");

}
