package cn.iocoder.yudao.module.bus.service.platform;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bus.controller.admin.platform.vo.ResearchPlatformPageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.platform.vo.ResearchPlatformSaveReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.platform.ResearchPlatformDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 科研平台 Service 接口
 */
public interface ResearchPlatformService {

    Long createPlatform(@Valid ResearchPlatformSaveReqVO createReqVO);

    void updatePlatform(@Valid ResearchPlatformSaveReqVO updateReqVO);

    void deletePlatform(Long id);

    void deletePlatformList(List<Long> ids);

    ResearchPlatformDO getPlatform(Long id);

    List<ResearchPlatformDO> getPlatformList(Collection<Long> ids);

    PageResult<ResearchPlatformDO> getPlatformPage(ResearchPlatformPageReqVO pageReqVO);

    List<ResearchPlatformDO> getPlatformSimpleList();

    List<Long> getPlatformStaffIds(Long platformId);

}
