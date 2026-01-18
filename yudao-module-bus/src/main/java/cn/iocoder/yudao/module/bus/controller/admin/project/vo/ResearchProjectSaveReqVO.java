package cn.iocoder.yudao.module.bus.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.mzt.logapi.starter.annotation.DiffLogField;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 研发项目创建/修改 Request VO")
@Data
public class ResearchProjectSaveReqVO {

    @Schema(description = "项目ID", example = "1024")
    private Long id;

    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "智能制造关键技术研发")
    @NotBlank(message = "项目名称不能为空")
    @DiffLogField(name = "项目名称")
    private String name;

    @Schema(description = "项目预算", example = "1000000.00")
    @DiffLogField(name = "项目预算")
    private BigDecimal budget;

    @Schema(description = "项目总负责人ID", example = "1")
    @DiffLogField(name = "项目负责人")
    private Long leaderUserId;

    @Schema(description = "项目类别", example = "key_rd")
    @DiffLogField(name = "项目类别")
    private String category;

    @Schema(description = "项目状态", example = "in_progress")
    @DiffLogField(name = "项目状态")
    private String status;

    @Schema(description = "立项日期", example = "2023-01-01 00:00:00")
    @DiffLogField(name = "立项日期")
    private LocalDateTime startDate;

    @Schema(description = "结题日期", example = "2025-12-31 00:00:00")
    @DiffLogField(name = "结题日期")
    private LocalDateTime endDate;

    @Schema(description = "项目简介", example = "本项目旨在研发智能制造核心技术")
    @DiffLogField(name = "项目简介")
    private String intro;

    @Schema(description = "关联平台ID列表", example = "[1, 2, 3]")
    @DiffLogField(name = "关联平台")
    private List<Long> platformIds;

    @Schema(description = "项目成员ID列表", example = "[1, 2, 3]")
    @DiffLogField(name = "项目成员")
    private List<Long> memberIds;

}

