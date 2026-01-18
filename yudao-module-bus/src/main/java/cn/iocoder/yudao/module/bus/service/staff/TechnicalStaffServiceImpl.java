package cn.iocoder.yudao.module.bus.service.staff;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.bus.controller.admin.staff.vo.TechnicalStaffPageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.staff.vo.TechnicalStaffSaveReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.staff.TechnicalStaffDO;
import cn.iocoder.yudao.module.bus.dal.mysql.staff.TechnicalStaffMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.bus.enums.ErrorCodeConstants.TECHNICAL_STAFF_NOT_EXISTS;

/**
 * 核心技术人员 Service 实现类
 */
@Service
@Validated
public class TechnicalStaffServiceImpl implements TechnicalStaffService {

    @Resource
    private TechnicalStaffMapper staffMapper;

    @Override
    public Long createStaff(TechnicalStaffSaveReqVO createReqVO) {
        TechnicalStaffDO staff = BeanUtils.toBean(createReqVO, TechnicalStaffDO.class);
        staffMapper.insert(staff);
        return staff.getId();
    }

    @Override
    public void updateStaff(TechnicalStaffSaveReqVO updateReqVO) {
        // 校验存在
        validateStaffExists(updateReqVO.getId());
        // 更新
        TechnicalStaffDO updateObj = BeanUtils.toBean(updateReqVO, TechnicalStaffDO.class);
        staffMapper.updateById(updateObj);
    }

    @Override
    public void deleteStaff(Long id) {
        // 校验存在
        validateStaffExists(id);
        // 删除
        staffMapper.deleteById(id);
    }

    @Override
    public void deleteStaffList(List<Long> ids) {
        staffMapper.deleteBatchIds(ids);
    }

    private void validateStaffExists(Long id) {
        if (staffMapper.selectById(id) == null) {
            throw exception(TECHNICAL_STAFF_NOT_EXISTS);
        }
    }

    @Override
    public TechnicalStaffDO getStaff(Long id) {
        return staffMapper.selectById(id);
    }

    @Override
    public List<TechnicalStaffDO> getStaffList(Collection<Long> ids) {
        return staffMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<TechnicalStaffDO> getStaffPage(TechnicalStaffPageReqVO pageReqVO) {
        return staffMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TechnicalStaffDO> getStaffSimpleList() {
        return staffMapper.selectList();
    }

}
