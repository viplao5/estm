package cn.iocoder.yudao.module.bus.dal.mysql.secret;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.bus.controller.admin.secret.vo.TechnicalSecretPageReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.secret.TechnicalSecretDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TechnicalSecretMapper extends BaseMapperX<TechnicalSecretDO> {

    default PageResult<TechnicalSecretDO> selectPage(TechnicalSecretPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TechnicalSecretDO>()
                .likeIfPresent(TechnicalSecretDO::getName, reqVO.getName())
                .eqIfPresent(TechnicalSecretDO::getType, reqVO.getType())
                .eqIfPresent(TechnicalSecretDO::getSecretLevel, reqVO.getSecretLevel())
                .inSqlIfPresent(TechnicalSecretDO::getId, reqVO.getProjectId() != null ?
                        "SELECT achievement_id FROM bus_project_achievement WHERE achievement_type = 'SECRET' AND project_id = " + reqVO.getProjectId() : null)
                .inIfPresent(TechnicalSecretDO::getId, reqVO.getIds())
                .orderByDesc(TechnicalSecretDO::getId));
    }

}
