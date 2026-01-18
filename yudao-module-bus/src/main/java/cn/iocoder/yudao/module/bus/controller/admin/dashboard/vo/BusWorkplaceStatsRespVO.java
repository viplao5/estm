package cn.iocoder.yudao.module.bus.controller.admin.dashboard.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 科技成果工作台统计 Response VO")
@Data
public class BusWorkplaceStatsRespVO {

    @Schema(description = "研发项目数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Long projectCount;

    @Schema(description = "知识产权数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    private Long ipCount;

    @Schema(description = "技术秘密数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    private Long secretCount;

    @Schema(description = "论文著作数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "8")
    private Long paperCount;

    @Schema(description = "标准数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Long standardCount;

    @Schema(description = "科技奖励数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long awardCount;

    @Schema(description = "产品与服务数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "4")
    private Long productCount;

    @Schema(description = "科研平台数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "6")
    private Long platformCount;

    @Schema(description = "技术人员数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "20")
    private Long staffCount;

    @Schema(description = "资质数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "7")
    private Long qualificationCount;

}
