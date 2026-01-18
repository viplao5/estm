package cn.iocoder.yudao.module.bus.service.staff;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bus.controller.admin.staff.vo.TechnicalStaffPageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.staff.vo.TechnicalStaffSaveReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.staff.TechnicalStaffDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 核心技术人员 Service 接口
 */
public interface TechnicalStaffService {

    /**
     * 创建核心技术人员
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createStaff(@Valid TechnicalStaffSaveReqVO createReqVO);

    /**
     * 更新核心技术人员
     *
     * @param updateReqVO 更新信息
     */
    void updateStaff(@Valid TechnicalStaffSaveReqVO updateReqVO);

    /**
     * 删除核心技术人员
     *
     * @param id 编号
     */
    void deleteStaff(Long id);

    /**
     * 批量删除核心技术人员
     *
     * @param ids 编号列表
     */
    void deleteStaffList(List<Long> ids);

    /**
     * 获得核心技术人员
     *
     * @param id 编号
     * @return 核心技术人员
     */
    TechnicalStaffDO getStaff(Long id);

    /**
     * 获得核心技术人员列表
     *
     * @param ids 编号列表
     * @return 核心技术人员列表
     */
    List<TechnicalStaffDO> getStaffList(Collection<Long> ids);

    /**
     * 获得核心技术人员分页
     *
     * @param pageReqVO 分页查询
     * @return 核心技术人员分页
     */
    PageResult<TechnicalStaffDO> getStaffPage(TechnicalStaffPageReqVO pageReqVO);

    /**
     * 获得所有核心技术人员列表（用于下拉选择）
     *
     * @return 核心技术人员列表
     */
    List<TechnicalStaffDO> getStaffSimpleList();

}
