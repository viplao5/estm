package cn.iocoder.yudao.module.bus.service.standard;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bus.controller.admin.standard.vo.StandardPageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.standard.vo.StandardSaveReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.standard.StandardDO;
import javax.validation.Valid;
import java.util.List;

public interface StandardService {
    Long createStandard(@Valid StandardSaveReqVO createReqVO);
    void updateStandard(@Valid StandardSaveReqVO updateReqVO);
    void deleteStandard(Long id);
    void deleteStandardList(List<Long> ids);
    StandardDO getStandard(Long id);
    PageResult<StandardDO> getStandardPage(StandardPageReqVO pageReqVO);
    List<StandardDO> getStandardSimpleList();

    List<Long> getStandardStaffIds(Long standardId);

    /**
     * 获取标准关联的研发项目ID列表
     * @param standardId 标准ID
     * @return 研发项目ID列表
     */
    List<Long> getStandardProjectIds(Long standardId);
}

