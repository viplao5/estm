package cn.iocoder.yudao.module.bus.controller.admin.platform.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 科研平台精简信息 Response VO")
@Data
public class ResearchPlatformSimpleRespVO {

    @Schema(description = "平台ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "平台名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "国家重点实验室")
    private String name;

    @Schema(description = "平台级别", example = "national")
    private String level;

}
