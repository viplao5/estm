package cn.iocoder.yudao.module.bus.controller.admin.paper.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 论文与著作分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PaperWorkPageReqVO extends PageParam {
    @Schema(description = "论文/专著标题")
    private String title;
    @Schema(description = "期刊/会议名称")
    private String publication;
    @Schema(description = "收录情况", example = "sci")
    private String indexing;
    @Schema(description = "发表年份", example = "2023")
    private Integer pubYear;
    @Schema(description = "关联研发项目ID")
    private Long projectId;

    @Schema(description = "关联作者ID")
    private Long staffId;
}
