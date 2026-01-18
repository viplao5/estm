package cn.iocoder.yudao.module.bus.dal.dataobject.ip;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 知识产权 DO
 */
@TableName("bus_intellectual_property")
@KeySequence("bus_intellectual_property_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class IntellectualPropertyDO extends TenantBaseDO {

    /**
     * 资产ID
     */
    @TableId
    private Long id;


    /**
     * 资产名称
     */
    private String name;

    /**
     * 申请号/登记号
     */
    private String appNumber;

    /**
     * 权利人
     */
    private String owner;

    /**
     * 代理机构
     */
    private String agency;

    /**
     * 资产类别（数据字典）
     */
    private String category;

    /**
     * 法律状态（数据字典）
     */
    private String status;

    /**
     * 来源（数据字典）
     */
    private String source;

    /**
     * 申请日
     */
    private LocalDateTime appDate;

    /**
     * 授权日/登记日
     */
    private LocalDateTime grantDate;

    /**
     * 下次年费缴纳日
     */
    private LocalDateTime nextFeeDate;

    /**
     * 官文扫描件URL
     */
    private String fileUrl;

}
