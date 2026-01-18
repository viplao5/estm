package cn.iocoder.yudao.module.bus.controller.admin.award.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import com.mzt.logapi.starter.annotation.DiffLogField;

@Schema(description = "管理后台 - 科技奖励创建/修改 Request VO")
@Data
public class AwardSaveReqVO {
    @Schema(description = "科技奖励ID")
    private Long id;
    @Schema(description = "关联研发项目ID列表", example = "[1, 2]")
    @DiffLogField(name = "关联项目")
    private java.util.List<Long> projectIds;
    @Schema(description = "科技奖励名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "奖励名称不能为空")
    @DiffLogField(name = "科技奖励名称")
    private String name;
    @Schema(description = "颁奖单位")
    @DiffLogField(name = "颁奖单位")
    private String grantUnit;
    @Schema(description = "奖励级别", example = "national")
    @DiffLogField(name = "奖励级别")
    private String level;
    @Schema(description = "奖励等级", example = "first")
    @DiffLogField(name = "奖励等级")
    private String grade;
    @Schema(description = "获奖日期")
    @DiffLogField(name = "获奖日期")
    private LocalDateTime awardDate;

    @Schema(description = "获奖人员ID列表", example = "[1, 2, 3]")
    @DiffLogField(name = "获奖人员")
    private java.util.List<Long> staffIds;
}

