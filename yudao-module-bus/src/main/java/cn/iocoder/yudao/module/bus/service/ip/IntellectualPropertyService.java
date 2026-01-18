package cn.iocoder.yudao.module.bus.service.ip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bus.controller.admin.ip.vo.IntellectualPropertyPageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.ip.vo.IntellectualPropertySaveReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.ip.IntellectualPropertyDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 知识产权 Service 接口
 */
public interface IntellectualPropertyService {

    Long createIP(@Valid IntellectualPropertySaveReqVO createReqVO);

    void updateIP(@Valid IntellectualPropertySaveReqVO updateReqVO);

    void deleteIP(Long id);

    void deleteIPList(List<Long> ids);

    IntellectualPropertyDO getIP(Long id);

    List<IntellectualPropertyDO> getIPList(Collection<Long> ids);

    PageResult<IntellectualPropertyDO> getIPPage(IntellectualPropertyPageReqVO pageReqVO);

    List<IntellectualPropertyDO> getIPSimpleList();

    /**
     * 获取知识产权关联的发明人ID列表
     * @param ipId 知识产权ID
     * @return 发明人ID列表
     */
    List<Long> getIPInventorIds(Long ipId);

    /**
     * 获取知识产权关联的研发项目ID列表
     * @param ipId 知识产权ID
     * @return 研发项目ID列表
     */
    List<Long> getIPProjectIds(Long ipId);

}

