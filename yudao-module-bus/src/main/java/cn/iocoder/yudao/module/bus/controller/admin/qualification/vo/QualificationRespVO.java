package cn.iocoder.yudao.module.bus.controller.admin.qualification.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资质 Response VO")
@Data
public class QualificationRespVO {
    @Schema(description = "资质ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    @Schema(description = "资质名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @Schema(description = "认证单位")
    private String certUnit;
    @Schema(description = "资质证书编号")
    private String certNumber;
    @Schema(description = "生效日期")
    private LocalDateTime startDate;
    @Schema(description = "到期日期")
    private LocalDateTime endDate;
    @Schema(description = "证明材料扫描件URL")
    private String fileUrl;
    @Schema(description = "备注说明")
    private String remark;
    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;
}
