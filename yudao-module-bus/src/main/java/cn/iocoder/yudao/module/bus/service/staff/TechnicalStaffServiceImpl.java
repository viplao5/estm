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

import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;
import com.mzt.logapi.service.impl.DiffParseFunction;
import static cn.iocoder.yudao.module.bus.enums.LogRecordConstants.*;

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
    @LogRecord(type = BUS_STAFF_TYPE, subType = BUS_STAFF_CREATE_SUB_TYPE, bizNo = "{{#staff.id}}",
            success = BUS_STAFF_CREATE_SUCCESS)
    public Long createStaff(TechnicalStaffSaveReqVO createReqVO) {
        TechnicalStaffDO staff = BeanUtils.toBean(createReqVO, TechnicalStaffDO.class);
        staffMapper.insert(staff);
        
        // 记录操作日志上下文
        LogRecordContext.putVariable("staff", staff);
        return staff.getId();
    }



    @Override
    @LogRecord(type = BUS_STAFF_TYPE, subType = BUS_STAFF_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = BUS_STAFF_UPDATE_SUCCESS)
    public void updateStaff(TechnicalStaffSaveReqVO updateReqVO) {
        // 校验存在
        TechnicalStaffDO staff = validateStaffExists(updateReqVO.getId());
        
        // 记录操作日志上下文 - 旧对象
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(staff, TechnicalStaffSaveReqVO.class));
        
        // 更新
        TechnicalStaffDO updateObj = BeanUtils.toBean(updateReqVO, TechnicalStaffDO.class);
        staffMapper.updateById(updateObj);

        // 记录操作日志上下文
        LogRecordContext.putVariable("staff", staff);
    }

    @Override
    @LogRecord(type = BUS_STAFF_TYPE, subType = BUS_STAFF_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = BUS_STAFF_DELETE_SUCCESS)
    public void deleteStaff(Long id) {
        // 校验存在
        TechnicalStaffDO staff = validateStaffExists(id);
        // 删除
        staffMapper.deleteById(id);

        // 记录操作日志上下文
        LogRecordContext.putVariable("staff", staff);
    }

    @Override
    public void deleteStaffList(List<Long> ids) {
        staffMapper.deleteBatchIds(ids);
    }

    private TechnicalStaffDO validateStaffExists(Long id) {
        TechnicalStaffDO staff = staffMapper.selectById(id);
        if (staff == null) {
            throw exception(TECHNICAL_STAFF_NOT_EXISTS);
        }
        return staff;
    }

    @Override
    @LogRecord(type = BUS_STAFF_TYPE, subType = BUS_STAFF_VIEW_SUB_TYPE, bizNo = "{{#id}}",
            success = BUS_STAFF_VIEW_SUCCESS)
    public TechnicalStaffDO getStaff(Long id) {
        TechnicalStaffDO staff = staffMapper.selectById(id);
        if (staff != null) {
            LogRecordContext.putVariable("staff", staff);
        }
        return staff;
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
