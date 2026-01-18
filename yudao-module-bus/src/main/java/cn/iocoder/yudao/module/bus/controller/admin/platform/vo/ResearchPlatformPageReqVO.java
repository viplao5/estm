package cn.iocoder.yudao.module.bus.controller.admin.platform.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 科研平台分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResearchPlatformPageReqVO extends PageParam {

    @Schema(description = "平台名称", example = "国家重点实验室")
    private String name;

    @Schema(description = "平台级别", example = "national")
    private String level;

    @Schema(description = "认定单位", example = "科技部")
    private String certUnit;

    @Schema(description = "认定日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] certDate;

}
