package cn.iocoder.yudao.module.bus.dal.dataobject.relation;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 项目-成果关联中间表 DO
 */
@TableName("bus_project_achievement")
@KeySequence("bus_project_achievement_seq")
@Data
public class ProjectAchievementDO {

    /**
     * 自增主键
     */
    @TableId
    private Long id;

    /**
     * 研发项目ID
     */
    private Long projectId;

    /**
     * 成果ID
     */
    private Long achievementId;

    /**
     * 成果类型(IP/SECRET/PAPER/STANDARD/AWARD)
     */
    private String achievementType;

    /**
     * 租户编号
     */
    private Long tenantId;

}
