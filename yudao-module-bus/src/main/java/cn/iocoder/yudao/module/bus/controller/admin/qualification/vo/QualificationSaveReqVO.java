package cn.iocoder.yudao.module.bus.controller.admin.qualification.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import com.mzt.logapi.starter.annotation.DiffLogField;

@Schema(description = "管理后台 - 资质创建/修改 Request VO")
@Data
public class QualificationSaveReqVO {
    @Schema(description = "资质ID")
    private Long id;
    @Schema(description = "资质名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "资质名称不能为空")
    @DiffLogField(name = "资质名称")
    private String name;
    @Schema(description = "认证单位")
    @DiffLogField(name = "认证单位")
    private String certUnit;
    @Schema(description = "资质证书编号")
    @DiffLogField(name = "证书编号")
    private String certNumber;
    @Schema(description = "生效日期")
    @DiffLogField(name = "生效日期")
    private LocalDateTime startDate;

    @Schema(description = "到期日期")
    @DiffLogField(name = "到期日期")
    private LocalDateTime endDate;
    @Schema(description = "证明材料扫描件URL")
    @DiffLogField(name = "扫描件")
    private String fileUrl;
    @Schema(description = "备注说明")
    @DiffLogField(name = "备注")
    private String remark;
}
