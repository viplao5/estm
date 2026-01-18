package cn.iocoder.yudao.module.bus.dal.mysql.ip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.bus.controller.admin.ip.vo.IntellectualPropertyPageReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.ip.IntellectualPropertyDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 知识产权 Mapper
 */
@Mapper
public interface IntellectualPropertyMapper extends BaseMapperX<IntellectualPropertyDO> {

    default PageResult<IntellectualPropertyDO> selectPage(IntellectualPropertyPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<IntellectualPropertyDO>()
                .likeIfPresent(IntellectualPropertyDO::getName, reqVO.getName())
                .likeIfPresent(IntellectualPropertyDO::getAppNumber, reqVO.getAppNumber())
                .eqIfPresent(IntellectualPropertyDO::getCategory, reqVO.getCategory())
                .eqIfPresent(IntellectualPropertyDO::getStatus, reqVO.getStatus())
                .eqIfPresent(IntellectualPropertyDO::getSource, reqVO.getSource())
                .inSqlIfPresent(IntellectualPropertyDO::getId, reqVO.getProjectId() != null ?
                        "SELECT achievement_id FROM bus_project_achievement WHERE achievement_type = 'IP' AND project_id = " + reqVO.getProjectId() : null)
                .betweenIfPresent(IntellectualPropertyDO::getAppDate, reqVO.getAppDate())
                .orderByDesc(IntellectualPropertyDO::getId));
    }

}
