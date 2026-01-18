package cn.iocoder.yudao.module.bus.service.qualification;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bus.controller.admin.qualification.vo.QualificationPageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.qualification.vo.QualificationSaveReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.qualification.QualificationDO;
import javax.validation.Valid;
import java.util.List;

public interface QualificationService {
    Long createQualification(@Valid QualificationSaveReqVO createReqVO);
    void createQualificationBatch(@Valid List<QualificationSaveReqVO> createReqVOs);
    void updateQualification(@Valid QualificationSaveReqVO updateReqVO);
    void deleteQualification(Long id);
    void deleteQualificationList(List<Long> ids);
    QualificationDO getQualification(Long id);
    PageResult<QualificationDO> getQualificationPage(QualificationPageReqVO pageReqVO);
    List<QualificationDO> getQualificationSimpleList();
}
