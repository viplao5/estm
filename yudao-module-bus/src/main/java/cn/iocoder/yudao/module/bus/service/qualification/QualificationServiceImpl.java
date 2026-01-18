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
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.bus.enums.ErrorCodeConstants.QUALIFICATION_NOT_EXISTS;

@Service
@Validated
public class QualificationServiceImpl implements QualificationService {
    @Resource
    private QualificationMapper qualificationMapper;

    @Override
    public Long createQualification(QualificationSaveReqVO createReqVO) {
        QualificationDO qualification = BeanUtils.toBean(createReqVO, QualificationDO.class);
        qualificationMapper.insert(qualification);
        return qualification.getId();
    }

    @Override
    public void createQualificationBatch(List<QualificationSaveReqVO> createReqVOs) {
        List<QualificationDO> qualifications = BeanUtils.toBean(createReqVOs, QualificationDO.class);
        qualifications.forEach(qualificationMapper::insert);
    }

    @Override
    public void updateQualification(QualificationSaveReqVO updateReqVO) {
        validateQualificationExists(updateReqVO.getId());
        QualificationDO updateObj = BeanUtils.toBean(updateReqVO, QualificationDO.class);
        qualificationMapper.updateById(updateObj);
    }

    @Override
    public void deleteQualification(Long id) {
        validateQualificationExists(id);
        qualificationMapper.deleteById(id);
    }

    @Override
    public void deleteQualificationList(List<Long> ids) {
        qualificationMapper.deleteBatchIds(ids);
    }

    private void validateQualificationExists(Long id) {
        if (qualificationMapper.selectById(id) == null) {
            throw exception(QUALIFICATION_NOT_EXISTS);
        }
    }

    @Override
    public QualificationDO getQualification(Long id) {
        return qualificationMapper.selectById(id);
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
