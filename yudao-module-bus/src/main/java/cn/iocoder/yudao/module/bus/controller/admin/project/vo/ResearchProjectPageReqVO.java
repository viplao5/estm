package cn.iocoder.yudao.module.bus.controller.admin.project.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 研发项目分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResearchProjectPageReqVO extends PageParam {

    @Schema(description = "项目名称", example = "智能制造")
    private String name;

    @Schema(description = "项目类别", example = "key_rd")
    private String category;

    @Schema(description = "项目状态", example = "in_progress")
    private String status;

    @Schema(description = "项目总负责人ID", example = "1")
    private Long leaderUserId;

    @Schema(description = "立项日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startDate;

    @Schema(description = "关联技术人员ID")
    private Long staffId;

}
