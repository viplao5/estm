package cn.iocoder.yudao.module.bus.controller.admin.platform.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 科研平台创建/修改 Request VO")
@Data
public class ResearchPlatformSaveReqVO {

    @Schema(description = "平台ID", example = "1024")
    private Long id;

    @Schema(description = "平台名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "国家重点实验室")
    @NotBlank(message = "平台名称不能为空")
    private String name;

    @Schema(description = "平台级别", example = "national")
    private String level;

    @Schema(description = "认定单位", example = "科技部")
    private String certUnit;

    @Schema(description = "认定日期", example = "2023-01-01 00:00:00")
    private LocalDateTime certDate;

    @Schema(description = "有效期-开始", example = "2023-01-01 00:00:00")
    private LocalDateTime startDate;

    @Schema(description = "有效期-结束", example = "2028-01-01 00:00:00")
    private LocalDateTime endDate;

    @Schema(description = "核心技术人员", example = "[1, 2, 3]")
    private List<Long> staffIds;

}
