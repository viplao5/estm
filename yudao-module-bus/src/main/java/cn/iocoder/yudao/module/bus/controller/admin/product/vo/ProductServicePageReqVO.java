package cn.iocoder.yudao.module.bus.controller.admin.product.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 产品与服务分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductServicePageReqVO extends PageParam {
    @Schema(description = "产品名称")
    private String name;
    @Schema(description = "产品类别")
    private String category;
    @Schema(description = "产品状态")
    private String status;
    @Schema(description = "产品负责人")
    private String leader;
}
