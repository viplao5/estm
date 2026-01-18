package cn.iocoder.yudao.module.bus.controller.admin.standard.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 标准精简信息 Response VO")
@Data
public class StandardSimpleRespVO {
    @Schema(description = "标准ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    @Schema(description = "标准名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @Schema(description = "标准类型")
    private String type;
}
