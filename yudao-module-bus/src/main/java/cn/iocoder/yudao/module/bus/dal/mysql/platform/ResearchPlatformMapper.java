package cn.iocoder.yudao.module.bus.dal.mysql.platform;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.bus.controller.admin.platform.vo.ResearchPlatformPageReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.platform.ResearchPlatformDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 科研平台 Mapper
 */
@Mapper
public interface ResearchPlatformMapper extends BaseMapperX<ResearchPlatformDO> {

    default PageResult<ResearchPlatformDO> selectPage(ResearchPlatformPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ResearchPlatformDO>()
                .likeIfPresent(ResearchPlatformDO::getName, reqVO.getName())
                .eqIfPresent(ResearchPlatformDO::getLevel, reqVO.getLevel())
                .likeIfPresent(ResearchPlatformDO::getCertUnit, reqVO.getCertUnit())
                .betweenIfPresent(ResearchPlatformDO::getCertDate, reqVO.getCertDate())
                .orderByDesc(ResearchPlatformDO::getId));
    }

}
