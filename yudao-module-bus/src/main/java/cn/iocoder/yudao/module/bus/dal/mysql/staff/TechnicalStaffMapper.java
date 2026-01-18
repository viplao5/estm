package cn.iocoder.yudao.module.bus.dal.mysql.staff;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.bus.controller.admin.staff.vo.TechnicalStaffPageReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.staff.TechnicalStaffDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 核心技术人员 Mapper
 */
@Mapper
public interface TechnicalStaffMapper extends BaseMapperX<TechnicalStaffDO> {

    default PageResult<TechnicalStaffDO> selectPage(TechnicalStaffPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TechnicalStaffDO>()
                .likeIfPresent(TechnicalStaffDO::getName, reqVO.getName())
                .likeIfPresent(TechnicalStaffDO::getPost, reqVO.getPost())
                .eqIfPresent(TechnicalStaffDO::getEduDegree, reqVO.getEduDegree())
                .eqIfPresent(TechnicalStaffDO::getTitle, reqVO.getTitle())
                .eqIfPresent(TechnicalStaffDO::getIsActive, reqVO.getIsActive())
                .eqIfPresent(TechnicalStaffDO::getIsCertified, reqVO.getIsCertified())
                .betweenIfPresent(TechnicalStaffDO::getEntryDate, reqVO.getEntryDate())
                .orderByDesc(TechnicalStaffDO::getId));
    }

}
