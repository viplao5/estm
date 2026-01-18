package cn.iocoder.yudao.module.bus.dal.dataobject.relation;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 成果-人员关联中间表 DO
 */
@TableName("bus_achievement_staff")
@KeySequence("bus_achievement_staff_seq")
@Data
public class AchievementStaffDO {

    @TableId
    private Long id;

    /**
     * 人员ID
     */
    private Long staffId;

    /**
     * 成果/项目ID
     */
    private Long achievementId;

    /**
     * 成果类型(PROJECT/IP/SECRET/PAPER/STANDARD/AWARD/PLATFORM/PRODUCT)
     */
    private String achievementType;

    /**
     * 作者类型(INTERNAL内部, EXTERNAL外部)
     */
    private String authorType;

    /**
     * 租户编号
     */
    private Long tenantId;

}
