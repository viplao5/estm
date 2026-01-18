package cn.iocoder.yudao.module.bus.controller.admin.standard.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 标准分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StandardPageReqVO extends PageParam {
    @Schema(description = "标准名称")
    private String name;
    @Schema(description = "标准类型")
    private String type;
    @Schema(description = "发表状态")
    private String status;
    @Schema(description = "关联研发项目ID")
    private Long projectId;

    @Schema(description = "关联人员ID")
    private Long staffId;
}
