package cn.iocoder.yudao.module.bus.controller.admin.secret.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 技术秘密精简信息 Response VO")
@Data
public class TechnicalSecretSimpleRespVO {

    @Schema(description = "成果ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "成果名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "核心算法技术")
    private String name;

    @Schema(description = "成果类型", example = "algorithm")
    private String type;

}
