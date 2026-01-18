package cn.iocoder.yudao.module.bus.dal.mysql.standard;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.bus.controller.admin.standard.vo.StandardPageReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.standard.StandardDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StandardMapper extends BaseMapperX<StandardDO> {
    default PageResult<StandardDO> selectPage(StandardPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<StandardDO>()
                .likeIfPresent(StandardDO::getName, reqVO.getName())
                .eqIfPresent(StandardDO::getType, reqVO.getType())
                .eqIfPresent(StandardDO::getStatus, reqVO.getStatus())
                .inSqlIfPresent(StandardDO::getId, reqVO.getProjectId() != null ?
                        "SELECT achievement_id FROM bus_project_achievement WHERE achievement_type = 'STANDARD' AND project_id = " + reqVO.getProjectId() : null)
                .orderByDesc(StandardDO::getId));
    }
}
