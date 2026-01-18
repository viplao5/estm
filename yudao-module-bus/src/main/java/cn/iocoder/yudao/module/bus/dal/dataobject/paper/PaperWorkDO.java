package cn.iocoder.yudao.module.bus.dal.dataobject.paper;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("bus_paper_work")
@KeySequence("bus_paper_work_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class PaperWorkDO extends TenantBaseDO {

    @TableId
    private Long id;

    private String title;
    private String publication;
    private String doi;
    private String indexing;
    private Integer pubYear;
    /**
     * 外部作者名单
     */
    private String externalAuthors;

}

