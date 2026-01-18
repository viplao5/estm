package cn.iocoder.yudao.module.bus.dal.mysql.award;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.bus.controller.admin.award.vo.AwardPageReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.award.AwardDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AwardMapper extends BaseMapperX<AwardDO> {
    default PageResult<AwardDO> selectPage(AwardPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AwardDO>()
                .likeIfPresent(AwardDO::getName, reqVO.getName())
                .eqIfPresent(AwardDO::getLevel, reqVO.getLevel())
                .eqIfPresent(AwardDO::getGrade, reqVO.getGrade())
                .inSqlIfPresent(AwardDO::getId, reqVO.getProjectId() != null ?
                        "SELECT achievement_id FROM bus_project_achievement WHERE achievement_type = 'AWARD' AND project_id = " + reqVO.getProjectId() : null)
                .orderByDesc(AwardDO::getId));
    }
}
