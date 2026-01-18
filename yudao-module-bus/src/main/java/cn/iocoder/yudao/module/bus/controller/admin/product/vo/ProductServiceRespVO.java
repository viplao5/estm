package cn.iocoder.yudao.module.bus.controller.admin.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 产品与服务 Response VO")
@Data
public class ProductServiceRespVO {
    @Schema(description = "产品ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    @Schema(description = "产品名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @Schema(description = "产品负责人")
    private String leader;
    @Schema(description = "最近一年销售收入")
    private BigDecimal revenue;
    @Schema(description = "最近一年销售毛利")
    private BigDecimal profit;
    @Schema(description = "产品类别")
    private String category;
    @Schema(description = "产品状态")
    private String status;
    @Schema(description = "上市日期")
    private LocalDateTime launchDate;
    @Schema(description = "产品简介")
    private String intro;
    @Schema(description = "重要客户名称")
    private String keyCustomers;

    @Schema(description = "关联知识产权ID列表")
    private java.util.List<Long> ipIds;

    @Schema(description = "关联技术秘密ID列表")
    private java.util.List<Long> secretIds;

    @Schema(description = "关联技术人员ID列表")
    private java.util.List<Long> staffIds;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;
}
