package cn.iocoder.yudao.module.bus.controller.admin.staff.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 核心技术人员分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TechnicalStaffPageReqVO extends PageParam {

    @Schema(description = "姓名", example = "张三")
    private String name;

    @Schema(description = "岗位", example = "高级工程师")
    private String post;

    @Schema(description = "学历", example = "bachelor")
    private String eduDegree;

    @Schema(description = "职称", example = "senior")
    private String title;

    @Schema(description = "是否在职", example = "true")
    private Boolean isActive;

    @Schema(description = "是否被认定为核心技术人员", example = "true")
    private Boolean isCertified;

    @Schema(description = "入职日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] entryDate;

}
