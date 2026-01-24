package cn.iocoder.yudao.module.bus.controller.admin.secret.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 技术秘密分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TechnicalSecretPageReqVO extends PageParam {

    @Schema(description = "成果名称", example = "核心算法")
    private String name;

    @Schema(description = "成果类型", example = "algorithm")
    private String type;

    @Schema(description = "保密级别", example = "confidential")
    private String secretLevel;

    @Schema(description = "关联研发项目ID", example = "1")
    private Long projectId;

    @Schema(description = "关联人员ID")
    private Long staffId;

    @Schema(description = "ID列表")
    private java.util.List<Long> ids;
}
