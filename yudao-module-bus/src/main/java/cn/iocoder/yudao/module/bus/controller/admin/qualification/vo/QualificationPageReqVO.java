package cn.iocoder.yudao.module.bus.controller.admin.qualification.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 资质分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QualificationPageReqVO extends PageParam {
    @Schema(description = "资质名称")
    private String name;
    @Schema(description = "认证单位")
    private String certUnit;
    @Schema(description = "资质证书编号")
    private String certNumber;
}
