package cn.iocoder.yudao.module.bus.dal.mysql.paper;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.bus.controller.admin.paper.vo.PaperWorkPageReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.paper.PaperWorkDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaperWorkMapper extends BaseMapperX<PaperWorkDO> {

    default PageResult<PaperWorkDO> selectPage(PaperWorkPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PaperWorkDO>()
                .likeIfPresent(PaperWorkDO::getTitle, reqVO.getTitle())
                .likeIfPresent(PaperWorkDO::getPublication, reqVO.getPublication())
                .eqIfPresent(PaperWorkDO::getIndexing, reqVO.getIndexing())
                .eqIfPresent(PaperWorkDO::getPubYear, reqVO.getPubYear())
                .inSqlIfPresent(PaperWorkDO::getId, reqVO.getProjectId() != null ?
                        "SELECT achievement_id FROM bus_project_achievement WHERE achievement_type = 'PAPER' AND project_id = " + reqVO.getProjectId() : null)
                .orderByDesc(PaperWorkDO::getId));
    }

}
