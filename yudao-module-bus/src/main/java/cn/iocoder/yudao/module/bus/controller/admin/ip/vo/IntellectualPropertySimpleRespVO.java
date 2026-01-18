package cn.iocoder.yudao.module.bus.controller.admin.ip.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 知识产权精简信息 Response VO")
@Data
public class IntellectualPropertySimpleRespVO {

    @Schema(description = "资产ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "资产名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "一种智能控制方法")
    private String name;

    @Schema(description = "资产类别", example = "invention")
    private String category;

}
