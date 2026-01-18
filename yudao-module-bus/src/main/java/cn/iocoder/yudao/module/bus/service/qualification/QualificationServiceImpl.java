package cn.iocoder.yudao.module.bus.service.qualification;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.bus.controller.admin.qualification.vo.QualificationPageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.qualification.vo.QualificationSaveReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.qualification.QualificationDO;
import cn.iocoder.yudao.module.bus.dal.mysql.qualification.QualificationMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Resource;
import java.util.List;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;
import com.mzt.logapi.service.impl.DiffParseFunction;
import static cn.iocoder.yudao.module.bus.enums.LogRecordConstants.*;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.bus.enums.ErrorCodeConstants.QUALIFICATION_NOT_EXISTS;

@Service
@Validated
public class QualificationServiceImpl implements QualificationService {
    @Resource
    private QualificationMapper qualificationMapper;

    @Override
    @LogRecord(type = BUS_QUALIFICATION_TYPE, subType = BUS_QUALIFICATION_CREATE_SUB_TYPE, bizNo = "{{#qualification.id}}",
            success = BUS_QUALIFICATION_CREATE_SUCCESS)
    public Long createQualification(QualificationSaveReqVO createReqVO) {
        QualificationDO qualification = BeanUtils.toBean(createReqVO, QualificationDO.class);
        qualificationMapper.insert(qualification);
        
        // 记录操作日志上下文
        LogRecordContext.putVariable("qualification", qualification);
        return qualification.getId();
    }

    @Override
    public void createQualificationBatch(List<QualificationSaveReqVO> createReqVOs) {
        List<QualificationDO> qualifications = BeanUtils.toBean(createReqVOs, QualificationDO.class);
        qualifications.forEach(qualificationMapper::insert);
    }



    @Override
    @LogRecord(type = BUS_QUALIFICATION_TYPE, subType = BUS_QUALIFICATION_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = BUS_QUALIFICATION_UPDATE_SUCCESS)
    public void updateQualification(QualificationSaveReqVO updateReqVO) {
        QualificationDO qualification = validateQualificationExists(updateReqVO.getId());
        
        // 记录操作日志上下文 - 旧对象
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(qualification, QualificationSaveReqVO.class));
        
        QualificationDO updateObj = BeanUtils.toBean(updateReqVO, QualificationDO.class);
        qualificationMapper.updateById(updateObj);

        // 记录操作日志上下文
        LogRecordContext.putVariable("qualification", qualification);
    }

    @Override
    @LogRecord(type = BUS_QUALIFICATION_TYPE, subType = BUS_QUALIFICATION_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = BUS_QUALIFICATION_DELETE_SUCCESS)
    public void deleteQualification(Long id) {
        QualificationDO qualification = validateQualificationExists(id);
        qualificationMapper.deleteById(id);

        // 记录操作日志上下文
        LogRecordContext.putVariable("qualification", qualification);
    }

    @Override
    public void deleteQualificationList(List<Long> ids) {
        qualificationMapper.deleteBatchIds(ids);
    }

    private QualificationDO validateQualificationExists(Long id) {
        QualificationDO qualification = qualificationMapper.selectById(id);
        if (qualification == null) {
            throw exception(QUALIFICATION_NOT_EXISTS);
        }
        return qualification;
    }

    @Override
    @LogRecord(type = BUS_QUALIFICATION_TYPE, subType = BUS_QUALIFICATION_VIEW_SUB_TYPE, bizNo = "{{#id}}",
            success = BUS_QUALIFICATION_VIEW_SUCCESS)
    public QualificationDO getQualification(Long id) {
        QualificationDO qualification = qualificationMapper.selectById(id);
        if (qualification != null) {
            LogRecordContext.putVariable("qualification", qualification);
        }
        return qualification;
    }

    @Override
    public PageResult<QualificationDO> getQualificationPage(QualificationPageReqVO pageReqVO) {
        return qualificationMapper.selectPage(pageReqVO);
    }

    @Override
    public List<QualificationDO> getQualificationSimpleList() {
        return qualificationMapper.selectList();
    }
}
