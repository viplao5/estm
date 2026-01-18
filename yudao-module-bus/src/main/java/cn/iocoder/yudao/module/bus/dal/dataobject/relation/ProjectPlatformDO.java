package cn.iocoder.yudao.module.bus.dal.dataobject.relation;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 项目-平台关联中间表 DO
 */
@TableName("bus_project_platform")
@KeySequence("bus_project_platform_seq")
@Data
public class ProjectPlatformDO {

    @TableId
    private Long id;

    /**
     * 研发项目ID
     */
    private Long projectId;

    /**
     * 科研平台ID
     */
    private Long platformId;

    /**
     * 租户编号
     */
    private Long tenantId;

}
