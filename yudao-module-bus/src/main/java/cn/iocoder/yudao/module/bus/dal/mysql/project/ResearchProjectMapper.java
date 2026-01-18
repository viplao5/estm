package cn.iocoder.yudao.module.bus.dal.mysql.project;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.bus.controller.admin.project.vo.ResearchProjectPageReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.project.ResearchProjectDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 研发项目 Mapper
 */
@Mapper
public interface ResearchProjectMapper extends BaseMapperX<ResearchProjectDO> {

    default PageResult<ResearchProjectDO> selectPage(ResearchProjectPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ResearchProjectDO>()
                .likeIfPresent(ResearchProjectDO::getName, reqVO.getName())
                .eqIfPresent(ResearchProjectDO::getCategory, reqVO.getCategory())
                .eqIfPresent(ResearchProjectDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ResearchProjectDO::getLeaderUserId, reqVO.getLeaderUserId())
                .betweenIfPresent(ResearchProjectDO::getStartDate, reqVO.getStartDate())
                .orderByDesc(ResearchProjectDO::getId));
    }

}
