package cn.iocoder.yudao.module.bus.controller.admin.qualification.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 资质精简信息 Response VO")
@Data
public class QualificationSimpleRespVO {
    @Schema(description = "资质ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    @Schema(description = "资质名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @Schema(description = "资质证书编号")
    private String certNumber;
}
