package cn.iocoder.yudao.module.bus.controller.admin.ip.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 知识产权分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class IntellectualPropertyPageReqVO extends PageParam {

    @Schema(description = "资产名称", example = "智能控制")
    private String name;

    @Schema(description = "申请号/登记号", example = "CN2023")
    private String appNumber;

    @Schema(description = "资产类别", example = "invention")
    private String category;

    @Schema(description = "法律状态", example = "granted")
    private String status;

    @Schema(description = "来源", example = "self_rd")
    private String source;

    @Schema(description = "关联研发项目ID", example = "1")
    private Long projectId;

    @Schema(description = "申请日")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] appDate;

    @Schema(description = "ID列表")
    private java.util.Collection<Long> ids;

}
