package cn.iocoder.yudao.module.bus.dal.dataobject.award;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@TableName("bus_award")
@KeySequence("bus_award_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class AwardDO extends TenantBaseDO {
    @TableId
    private Long id;

    private String name;
    private String grantUnit;
    private String level;
    private String grade;
    private LocalDateTime awardDate;
}
