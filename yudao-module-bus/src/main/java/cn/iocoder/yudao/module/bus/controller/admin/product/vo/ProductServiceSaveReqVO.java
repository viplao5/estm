package cn.iocoder.yudao.module.bus.controller.admin.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.mzt.logapi.starter.annotation.DiffLogField;

@Schema(description = "管理后台 - 产品与服务创建/修改 Request VO")
@Data
public class ProductServiceSaveReqVO {
    @Schema(description = "产品ID")
    private Long id;
    @Schema(description = "产品名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "产品名称不能为空")
    @DiffLogField(name = "产品名称")
    private String name;
    @Schema(description = "产品负责人")
    @DiffLogField(name = "产品负责人")
    private String leader;
    @Schema(description = "最近一年销售收入")
    @DiffLogField(name = "销售收入")
    private BigDecimal revenue;
    @Schema(description = "最近一年销售毛利")
    @DiffLogField(name = "销售毛利")
    private BigDecimal profit;
    @Schema(description = "产品类别")
    @DiffLogField(name = "产品类别")
    private String category;
    @Schema(description = "产品状态")
    @DiffLogField(name = "产品状态")
    private String status;
    @Schema(description = "上市日期")
    @DiffLogField(name = "上市日期")
    private LocalDateTime launchDate;
    @Schema(description = "产品简介")
    @DiffLogField(name = "产品简介")
    private String intro;
    @Schema(description = "重要客户名称")
    @DiffLogField(name = "重要客户")
    private String keyCustomers;

    @Schema(description = "关联知识产权ID列表")
    @DiffLogField(name = "关联知识产权")
    private java.util.List<Long> ipIds;

    @Schema(description = "关联技术秘密ID列表")
    @DiffLogField(name = "关联技术秘密")
    private java.util.List<Long> secretIds;

    @Schema(description = "关联技术人员ID列表")
    @DiffLogField(name = "关联技术人员")
    private java.util.List<Long> staffIds;
}
