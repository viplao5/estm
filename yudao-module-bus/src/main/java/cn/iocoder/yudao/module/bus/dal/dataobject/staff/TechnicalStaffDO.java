package cn.iocoder.yudao.module.bus.dal.dataobject.staff;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 核心技术人员 DO
 */
@TableName("bus_technical_staff")
@KeySequence("bus_technical_staff_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class TechnicalStaffDO extends TenantBaseDO {

    /**
     * 人员ID
     */
    @TableId
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 岗位
     */
    private String post;

    /**
     * 毕业院校
     */
    private String school;

    /**
     * 专业
     */
    private String major;

    /**
     * 学历（数据字典）
     */
    private String eduDegree;

    /**
     * 职称（数据字典）
     */
    private String title;

    /**
     * 入职日期
     */
    private LocalDateTime entryDate;

    /**
     * 是否在职
     */
    private Boolean isActive;

    /**
     * 是否被认定为核心技术人员
     */
    private Boolean isCertified;

    /**
     * 与前单位是否签署竞业禁止协议
     */
    private Boolean hasNonCompete;

    /**
     * 与前单位是否存在潜在纠纷
     */
    private Boolean hasDispute;

    /**
     * 突出成就
     */
    private String achievements;

    /**
     * 背景描述
     */
    private String background;

    /**
     * 其他激励措施
     */
    private String incentiveInfo;

    /**
     * 保密与知识产权协议扫描件URL
     */
    private String confidentialFile;

    /**
     * 是否已签署保密与知识产权协议
     */
    private Boolean hasConfidentialAgreement;

    /**
     * 职务发明承诺书扫描件URL
     */
    private String inventionPledgeFile;

    /**
     * 是否已签署职务发明承诺书
     */
    private Boolean hasInventionPledge;

    /**
     * 是否参与股权激励计划
     */
    private Boolean hasEquityPlan;

    /**
     * 股权激励计划文件URL
     */
    private String equityPlanFile;

}
