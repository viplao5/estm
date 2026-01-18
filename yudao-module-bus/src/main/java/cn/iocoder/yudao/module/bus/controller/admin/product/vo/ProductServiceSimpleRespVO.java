package cn.iocoder.yudao.module.bus.controller.admin.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 产品与服务精简信息 Response VO")
@Data
public class ProductServiceSimpleRespVO {
    @Schema(description = "产品ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    @Schema(description = "产品名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @Schema(description = "产品类别")
    private String category;
}
