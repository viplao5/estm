package cn.iocoder.yudao.module.bus.controller.admin.product.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;

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

    @Schema(description = "关联IP ID")
    private Long ipId;

    @Schema(description = "关联技术秘密 ID")
    private Long secretId;

    @Schema(description = "关联人员ID")
    private Long staffId;

    @Schema(description = "ID列表")
    private Collection<Long> ids;
}
