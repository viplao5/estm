package cn.iocoder.yudao.module.bus.dal.dataobject.qualification;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@TableName("bus_qualification")
@KeySequence("bus_qualification_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class QualificationDO extends TenantBaseDO {
    @TableId
    private Long id;
    private String name;
    private String certUnit;
    private String certNumber;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String fileUrl;
    private String remark;
}
