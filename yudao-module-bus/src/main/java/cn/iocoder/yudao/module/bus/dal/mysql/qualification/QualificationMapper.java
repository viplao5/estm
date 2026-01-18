package cn.iocoder.yudao.module.bus.dal.mysql.qualification;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.bus.controller.admin.qualification.vo.QualificationPageReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.qualification.QualificationDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QualificationMapper extends BaseMapperX<QualificationDO> {
    default PageResult<QualificationDO> selectPage(QualificationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<QualificationDO>()
                .likeIfPresent(QualificationDO::getName, reqVO.getName())
                .likeIfPresent(QualificationDO::getCertUnit, reqVO.getCertUnit())
                .likeIfPresent(QualificationDO::getCertNumber, reqVO.getCertNumber())
                .orderByDesc(QualificationDO::getId));
    }
}
