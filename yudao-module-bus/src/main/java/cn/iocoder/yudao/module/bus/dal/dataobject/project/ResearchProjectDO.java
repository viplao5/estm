package cn.iocoder.yudao.module.bus.dal.dataobject.project;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 研发项目 DO
 */
@TableName("bus_research_project")
@KeySequence("bus_research_project_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class ResearchProjectDO extends TenantBaseDO {

    /**
     * 项目ID
     */
    @TableId
    private Long id;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目预算
     */
    private BigDecimal budget;

    /**
     * 项目总负责人ID
     */
    private Long leaderUserId;

    /**
     * 项目类别（数据字典）
     */
    private String category;

    /**
     * 项目状态（数据字典）
     */
    private String status;

    /**
     * 立项日期
     */
    private LocalDateTime startDate;

    /**
     * 结题日期
     */
    private LocalDateTime endDate;

    /**
     * 项目简介
     */
    private String intro;

}
