package cn.iocoder.yudao.module.bus.dal.dataobject.standard;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@TableName("bus_standard")
@KeySequence("bus_standard_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class StandardDO extends TenantBaseDO {
    @TableId
    private Long id;

    private String name;
    private String type;
    private String status;
    private String companyRole;
    private LocalDateTime pubDate;
}
