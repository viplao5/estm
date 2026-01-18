package cn.iocoder.yudao.module.bus.controller.admin.ip.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 知识产权创建/修改 Request VO")
@Data
public class IntellectualPropertySaveReqVO {

    @Schema(description = "资产ID", example = "1024")
    private Long id;

    @Schema(description = "关联研发项目ID列表", example = "[1, 2]")
    private java.util.List<Long> projectIds;

    @Schema(description = "资产名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "一种智能控制方法")
    @NotBlank(message = "资产名称不能为空")
    private String name;

    @Schema(description = "申请号/登记号", example = "CN202310001234.5")
    private String appNumber;

    @Schema(description = "权利人", example = "XX科技有限公司")
    private String owner;

    @Schema(description = "代理机构", example = "北京XX专利代理事务所")
    private String agency;

    @Schema(description = "资产类别", example = "invention")
    private String category;

    @Schema(description = "法律状态", example = "granted")
    private String status;

    @Schema(description = "来源", example = "self_rd")
    private String source;

    @Schema(description = "申请日", example = "2023-01-01 00:00:00")
    private LocalDateTime appDate;

    @Schema(description = "授权日/登记日", example = "2024-06-01 00:00:00")
    private LocalDateTime grantDate;

    @Schema(description = "下次年费缴纳日", example = "2025-01-01 00:00:00")
    private LocalDateTime nextFeeDate;

    @Schema(description = "官文扫描件URL", example = "https://example.com/file.pdf")
    private String fileUrl;

    @Schema(description = "发明人ID列表", example = "[1, 2, 3]")
    private java.util.List<Long> inventorIds;

}

