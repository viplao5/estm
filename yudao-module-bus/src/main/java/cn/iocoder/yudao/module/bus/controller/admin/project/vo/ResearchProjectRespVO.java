package cn.iocoder.yudao.module.bus.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 研发项目 Response VO")
@Data
public class ResearchProjectRespVO {

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "智能制造关键技术研发")
    private String name;

    @Schema(description = "项目预算", example = "1000000.00")
    private BigDecimal budget;

    @Schema(description = "项目总负责人ID", example = "1")
    private Long leaderUserId;

    @Schema(description = "项目类别", example = "key_rd")
    private String category;

    @Schema(description = "项目状态", example = "in_progress")
    private String status;

    @Schema(description = "立项日期", example = "2023-01-01 00:00:00")
    private LocalDateTime startDate;

    @Schema(description = "结题日期", example = "2025-12-31 00:00:00")
    private LocalDateTime endDate;

    @Schema(description = "项目简介", example = "本项目旨在研发智能制造核心技术")
    private String intro;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "关联平台ID列表")
    private List<Long> platformIds;

    @Schema(description = "项目成员ID列表")
    private List<Long> memberIds;

}
