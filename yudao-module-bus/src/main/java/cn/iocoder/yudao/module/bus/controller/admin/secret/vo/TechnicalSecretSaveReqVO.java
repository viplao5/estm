package cn.iocoder.yudao.module.bus.controller.admin.secret.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 技术秘密创建/修改 Request VO")
@Data
public class TechnicalSecretSaveReqVO {

    @Schema(description = "成果ID", example = "1024")
    private Long id;

    @Schema(description = "关联研发项目ID列表", example = "[1, 2]")
    private java.util.List<Long> projectIds;

    @Schema(description = "成果名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "核心算法技术")
    @NotBlank(message = "成果名称不能为空")
    private String name;

    @Schema(description = "成果类型", example = "algorithm")
    private String type;

    @Schema(description = "保密级别", example = "confidential")
    private String secretLevel;

    @Schema(description = "完成日期", example = "2023-06-01 00:00:00")
    private LocalDateTime finishDate;

    @Schema(description = "成果描述", example = "本成果为自主研发的核心算法")
    private String description;

    @Schema(description = "所属技术领域", example = "人工智能")
    private String techField;

    @Schema(description = "相关人员ID列表", example = "[1, 2, 3]")
    private java.util.List<Long> staffIds;

}

