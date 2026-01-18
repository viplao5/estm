package cn.iocoder.yudao.module.bus.controller.admin.award.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 科技奖励创建/修改 Request VO")
@Data
public class AwardSaveReqVO {
    @Schema(description = "科技奖励ID")
    private Long id;
    @Schema(description = "关联研发项目ID列表", example = "[1, 2]")
    private java.util.List<Long> projectIds;
    @Schema(description = "科技奖励名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "奖励名称不能为空")
    private String name;
    @Schema(description = "颁奖单位")
    private String grantUnit;
    @Schema(description = "奖励级别", example = "national")
    private String level;
    @Schema(description = "奖励等级", example = "first")
    private String grade;
    @Schema(description = "获奖日期")
    private LocalDateTime awardDate;

    @Schema(description = "获奖人员ID列表", example = "[1, 2, 3]")
    private java.util.List<Long> staffIds;
}

