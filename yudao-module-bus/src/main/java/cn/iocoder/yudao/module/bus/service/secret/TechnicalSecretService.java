package cn.iocoder.yudao.module.bus.service.secret;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bus.controller.admin.secret.vo.TechnicalSecretPageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.secret.vo.TechnicalSecretSaveReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.secret.TechnicalSecretDO;

import javax.validation.Valid;
import java.util.List;

public interface TechnicalSecretService {

    Long createSecret(@Valid TechnicalSecretSaveReqVO createReqVO);

    void updateSecret(@Valid TechnicalSecretSaveReqVO updateReqVO);

    void deleteSecret(Long id);

    void deleteSecretList(List<Long> ids);

    TechnicalSecretDO getSecret(Long id);

    List<Long> getSecretStaffIds(Long secretId);

    /**
     * 获取技术秘密关联的研发项目ID列表
     * @param secretId 技术秘密ID
     * @return 研发项目ID列表
     */
    List<Long> getSecretProjectIds(Long secretId);

    PageResult<TechnicalSecretDO> getSecretPage(TechnicalSecretPageReqVO pageReqVO);

    List<TechnicalSecretDO> getSecretSimpleList();

}
