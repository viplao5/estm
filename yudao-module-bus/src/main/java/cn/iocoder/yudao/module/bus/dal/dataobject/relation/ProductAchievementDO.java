package cn.iocoder.yudao.module.bus.dal.dataobject.relation;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 产品-成果关联中间表 DO
 */
@TableName("bus_product_achievement")
@KeySequence("bus_product_achievement_seq")
@Data
public class ProductAchievementDO {

    @TableId
    private Long id;

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * 成果ID
     */
    private Long achievementId;

    /**
     * 成果类型(IP/SECRET)
     */
    private String achievementType;

    /**
     * 租户编号
     */
    private Long tenantId;

}
