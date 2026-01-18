package cn.iocoder.yudao.module.bus.controller.admin.paper.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 论文与著作精简信息 Response VO")
@Data
public class PaperWorkSimpleRespVO {
    @Schema(description = "论文专著ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    @Schema(description = "论文/专著标题", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;
    @Schema(description = "收录情况")
    private String indexing;
}
