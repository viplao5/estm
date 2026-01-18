package cn.iocoder.yudao.module.bus.controller.admin.paper.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotBlank;

@Schema(description = "管理后台 - 论文与著作创建/修改 Request VO")
@Data
public class PaperWorkSaveReqVO {
    @Schema(description = "论文专著ID", example = "1024")
    private Long id;
    @Schema(description = "关联研发项目ID列表", example = "[1, 2]")
    private java.util.List<Long> projectIds;
    @Schema(description = "论文/专著标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "标题不能为空")
    private String title;
    @Schema(description = "期刊/会议名称")
    private String publication;
    @Schema(description = "DOI号")
    private String doi;
    @Schema(description = "收录情况", example = "sci")
    private String indexing;
    @Schema(description = "发表年份", example = "2023")
    private Integer pubYear;

    @Schema(description = "内部作者ID列表", example = "[1, 2, 3]")
    private java.util.List<Long> authorIds;

    @Schema(description = "外部作者名单", example = "张三, 李四")
    private String externalAuthors;
}

