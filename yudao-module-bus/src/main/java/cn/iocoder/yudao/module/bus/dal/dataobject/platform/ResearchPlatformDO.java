package cn.iocoder.yudao.module.bus.dal.dataobject.platform;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 科研平台 DO
 */
@TableName("bus_research_platform")
@KeySequence("bus_research_platform_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class ResearchPlatformDO extends TenantBaseDO {

    /**
     * 平台ID
     */
    @TableId
    private Long id;

    /**
     * 平台名称
     */
    private String name;

    /**
     * 平台级别（数据字典）
     */
    private String level;

    /**
     * 认定单位
     */
    private String certUnit;

    /**
     * 认定日期
     */
    private LocalDateTime certDate;

    /**
     * 有效期-开始
     */
    private LocalDateTime startDate;

    /**
     * 有效期-结束
     */
    private LocalDateTime endDate;

}
