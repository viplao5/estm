package cn.iocoder.yudao.module.bus.controller.admin.standard.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 标准 Response VO")
@Data
public class StandardRespVO {
    @Schema(description = "标准ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    @Schema(description = "关联研发项目ID列表", example = "[1, 2]")
    private java.util.List<Long> projectIds;
    @Schema(description = "标准名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @Schema(description = "标准类型")
    private String type;
    @Schema(description = "发表状态")
    private String status;
    @Schema(description = "公司角色")
    private String companyRole;
    @Schema(description = "发布/立项日期")
    private LocalDateTime pubDate;
    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "参与人员ID列表")
    private java.util.List<Long> staffIds;
}

