package cn.iocoder.yudao.module.bus.controller.admin.standard.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 标准创建/修改 Request VO")
@Data
public class StandardSaveReqVO {
    @Schema(description = "标准ID")
    private Long id;
    @Schema(description = "关联研发项目ID列表", example = "[1, 2]")
    private java.util.List<Long> projectIds;
    @Schema(description = "标准名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "标准名称不能为空")
    private String name;
    @Schema(description = "标准类型", example = "national")
    private String type;
    @Schema(description = "发表状态", example = "published")
    private String status;
    @Schema(description = "公司角色", example = "lead")
    private String companyRole;
    @Schema(description = "发布/立项日期")
    private LocalDateTime pubDate;

    @Schema(description = "参与人员ID列表", example = "[1, 2, 3]")
    private java.util.List<Long> staffIds;
}

