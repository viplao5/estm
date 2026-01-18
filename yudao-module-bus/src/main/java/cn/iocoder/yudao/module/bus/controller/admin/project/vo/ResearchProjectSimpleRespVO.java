package cn.iocoder.yudao.module.bus.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 研发项目精简信息 Response VO")
@Data
public class ResearchProjectSimpleRespVO {

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "智能制造关键技术研发")
    private String name;

    @Schema(description = "项目状态", example = "in_progress")
    private String status;

    @Schema(description = "项目类别", example = "1")
    private String category;

}
