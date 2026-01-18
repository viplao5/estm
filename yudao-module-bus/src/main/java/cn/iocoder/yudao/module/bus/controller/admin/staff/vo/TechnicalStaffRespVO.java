package cn.iocoder.yudao.module.bus.controller.admin.staff.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 核心技术人员 Response VO")
@Data
public class TechnicalStaffRespVO {

    @Schema(description = "人员ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String name;

    @Schema(description = "岗位", example = "高级工程师")
    private String post;

    @Schema(description = "毕业院校", example = "清华大学")
    private String school;

    @Schema(description = "专业", example = "计算机科学与技术")
    private String major;

    @Schema(description = "学历", example = "bachelor")
    private String eduDegree;

    @Schema(description = "职称", example = "senior")
    private String title;

    @Schema(description = "入职日期", example = "2020-01-01 00:00:00")
    private LocalDateTime entryDate;

    @Schema(description = "是否在职", example = "true")
    private Boolean isActive;

    @Schema(description = "是否被认定为核心技术人员", example = "true")
    private Boolean isCertified;

    @Schema(description = "与前单位是否签署竞业禁止协议", example = "false")
    private Boolean hasNonCompete;

    @Schema(description = "与前单位是否存在潜在纠纷", example = "false")
    private Boolean hasDispute;

    @Schema(description = "突出成就", example = "发表SCI论文10篇")
    private String achievements;

    @Schema(description = "背景描述", example = "具有丰富的研发经验")
    private String background;

    @Schema(description = "其他激励措施", example = "股权激励")
    private String incentiveInfo;

    @Schema(description = "保密与知识产权协议扫描件URL", example = "https://example.com/file.pdf")
    private String confidentialFile;

    @Schema(description = "是否已签署保密与知识产权协议", example = "true")
    private Boolean hasConfidentialAgreement;

    @Schema(description = "职务发明承诺书扫描件URL", example = "https://example.com/file.pdf")
    private String inventionPledgeFile;

    @Schema(description = "是否已签署职务发明承诺书", example = "true")
    private Boolean hasInventionPledge;

    @Schema(description = "是否参与股权激励计划", example = "true")
    private Boolean hasEquityPlan;

    @Schema(description = "股权激励计划文件URL", example = "https://example.com/file.pdf")
    private String equityPlanFile;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
