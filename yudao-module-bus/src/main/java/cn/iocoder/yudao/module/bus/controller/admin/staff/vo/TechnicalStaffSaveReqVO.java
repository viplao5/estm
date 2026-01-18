package cn.iocoder.yudao.module.bus.controller.admin.staff.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import com.mzt.logapi.starter.annotation.DiffLogField;

@Schema(description = "管理后台 - 核心技术人员创建/修改 Request VO")
@Data
public class TechnicalStaffSaveReqVO {

    @Schema(description = "人员ID", example = "1024")
    private Long id;

    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotBlank(message = "姓名不能为空")
    @DiffLogField(name = "姓名")
    private String name;

    @Schema(description = "岗位", example = "高级工程师")
    @DiffLogField(name = "岗位")
    private String post;

    @Schema(description = "毕业院校", example = "清华大学")
    @DiffLogField(name = "毕业院校")
    private String school;

    @Schema(description = "专业", example = "计算机科学与技术")
    @DiffLogField(name = "专业")
    private String major;

    @Schema(description = "学历", example = "bachelor")
    @DiffLogField(name = "学历")
    private String eduDegree;

    @Schema(description = "职称", example = "senior")
    @DiffLogField(name = "职称")
    private String title;

    @Schema(description = "入职日期", example = "2020-01-01 00:00:00")
    @DiffLogField(name = "入职日期")
    private LocalDateTime entryDate;

    @Schema(description = "是否在职", example = "true")
    @DiffLogField(name = "是否在职")
    private Boolean isActive;

    @Schema(description = "是否被认定为核心技术人员", example = "true")
    @DiffLogField(name = "核心人员")
    private Boolean isCertified;

    @Schema(description = "与前单位是否签署竞业禁止协议", example = "false")
    @DiffLogField(name = "竞业协议")
    private Boolean hasNonCompete;

    @Schema(description = "与前单位是否存在潜在纠纷", example = "false")
    @DiffLogField(name = "潜在纠纷")
    private Boolean hasDispute;

    @Schema(description = "突出成就", example = "发表SCI论文10篇")
    @DiffLogField(name = "突出成就")
    private String achievements;

    @Schema(description = "背景描述", example = "具有丰富的研发经验")
    @DiffLogField(name = "背景描述")
    private String background;

    @Schema(description = "其他激励措施", example = "股权激励")
    @DiffLogField(name = "激励措施")
    private String incentiveInfo;

    @Schema(description = "保密与知识产权协议扫描件URL", example = "https://example.com/file.pdf")
    @DiffLogField(name = "保密协议")
    private String confidentialFile;

    @Schema(description = "是否已签署保密与知识产权协议", example = "true")
    @DiffLogField(name = "已签署保密协议")
    private Boolean hasConfidentialAgreement;

    @Schema(description = "职务发明承诺书扫描件URL", example = "https://example.com/file.pdf")
    @DiffLogField(name = "发明承诺书")
    private String inventionPledgeFile;

    @Schema(description = "是否已签署职务发明承诺书", example = "true")
    @DiffLogField(name = "已签署发明承诺书")
    private Boolean hasInventionPledge;

    @Schema(description = "是否参与股权激励计划", example = "true")
    @DiffLogField(name = "股权激励")
    private Boolean hasEquityPlan;

    @Schema(description = "股权激励计划文件URL", example = "https://example.com/file.pdf")
    @DiffLogField(name = "股权文件")
    private String equityPlanFile;

}
