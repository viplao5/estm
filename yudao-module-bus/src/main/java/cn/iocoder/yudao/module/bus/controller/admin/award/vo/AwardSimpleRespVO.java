package cn.iocoder.yudao.module.bus.controller.admin.award.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 科技奖励精简信息 Response VO")
@Data
public class AwardSimpleRespVO {
    @Schema(description = "科技奖励ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    @Schema(description = "科技奖励名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @Schema(description = "奖励级别")
    private String level;
}
