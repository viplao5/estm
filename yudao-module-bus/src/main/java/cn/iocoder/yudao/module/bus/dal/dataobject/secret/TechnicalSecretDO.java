package cn.iocoder.yudao.module.bus.dal.dataobject.secret;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 技术秘密 DO
 */
@TableName("bus_technical_secret")
@KeySequence("bus_technical_secret_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class TechnicalSecretDO extends TenantBaseDO {

    @TableId
    private Long id;



    private String name;

    private String type;

    private String secretLevel;

    private LocalDateTime finishDate;

    private String description;

    private String techField;

}
