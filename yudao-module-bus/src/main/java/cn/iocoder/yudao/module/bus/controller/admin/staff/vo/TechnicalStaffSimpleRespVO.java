package cn.iocoder.yudao.module.bus.controller.admin.staff.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 核心技术人员精简信息 Response VO")
@Data
public class TechnicalStaffSimpleRespVO {

    @Schema(description = "人员ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String name;

    @Schema(description = "岗位", example = "高级工程师")
    private String post;

    @Schema(description = "学历", example = "bachelor")
    private String eduDegree;

    @Schema(description = "职称", example = "associate")
    private String title;

}
