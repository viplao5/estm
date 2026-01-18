package cn.iocoder.yudao.module.bus.controller.admin.paper.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 论文与著作 Response VO")
@Data
public class PaperWorkRespVO {
    @Schema(description = "论文专著ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    @Schema(description = "关联研发项目ID列表", example = "[1, 2]")
    private java.util.List<Long> projectIds;
    @Schema(description = "论文/专著标题", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;
    @Schema(description = "期刊/会议名称")
    private String publication;
    @Schema(description = "DOI号")
    private String doi;
    @Schema(description = "收录情况")
    private String indexing;
    @Schema(description = "发表年份")
    private Integer pubYear;
    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "内部作者ID列表")
    private java.util.List<Long> authorIds;

    @Schema(description = "外部作者名单")
    private String externalAuthors;
}

