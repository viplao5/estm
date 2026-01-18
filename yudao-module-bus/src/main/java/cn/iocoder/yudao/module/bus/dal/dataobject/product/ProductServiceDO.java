package cn.iocoder.yudao.module.bus.dal.dataobject.product;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("bus_product_service")
@KeySequence("bus_product_service_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductServiceDO extends TenantBaseDO {
    @TableId
    private Long id;
    private String name;
    private String leader;
    private BigDecimal revenue;
    private BigDecimal profit;
    private String category;
    private String status;
    private LocalDateTime launchDate;
    private String intro;
    private String keyCustomers;
}
