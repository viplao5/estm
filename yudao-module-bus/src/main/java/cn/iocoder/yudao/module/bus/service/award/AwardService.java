package cn.iocoder.yudao.module.bus.service.award;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bus.controller.admin.award.vo.AwardPageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.award.vo.AwardSaveReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.award.AwardDO;
import javax.validation.Valid;
import java.util.List;

public interface AwardService {
    Long createAward(@Valid AwardSaveReqVO createReqVO);
    void updateAward(@Valid AwardSaveReqVO updateReqVO);
    void deleteAward(Long id);
    void deleteAwardList(List<Long> ids);
    AwardDO getAward(Long id);
    PageResult<AwardDO> getAwardPage(AwardPageReqVO pageReqVO);
    List<AwardDO> getAwardSimpleList();

    List<Long> getAwardStaffIds(Long awardId);

    /**
     * 获取科技奖励关联的研发项目ID列表
     * @param awardId 科技奖励ID
     * @return 研发项目ID列表
     */
    List<Long> getAwardProjectIds(Long awardId);
}

