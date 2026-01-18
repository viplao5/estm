package cn.iocoder.yudao.module.bus.controller.admin.award.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 科技奖励 Response VO")
@Data
public class AwardRespVO {
    @Schema(description = "科技奖励ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    @Schema(description = "关联研发项目ID列表", example = "[1, 2]")
    private java.util.List<Long> projectIds;
    @Schema(description = "科技奖励名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @Schema(description = "颁奖单位")
    private String grantUnit;
    @Schema(description = "奖励级别")
    private String level;
    @Schema(description = "奖励等级")
    private String grade;
    @Schema(description = "获奖日期")
    private LocalDateTime awardDate;
    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "获奖人员ID列表")
    private java.util.List<Long> staffIds;
}

