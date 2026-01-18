package cn.iocoder.yudao.module.bus.service.secret;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.bus.controller.admin.secret.vo.TechnicalSecretPageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.secret.vo.TechnicalSecretSaveReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.secret.TechnicalSecretDO;
import cn.iocoder.yudao.module.bus.dal.mysql.secret.TechnicalSecretMapper;
import cn.iocoder.yudao.module.bus.dal.dataobject.relation.AchievementStaffDO;
import cn.iocoder.yudao.module.bus.dal.mysql.relation.AchievementStaffMapper;
import cn.iocoder.yudao.module.bus.dal.dataobject.relation.ProjectAchievementDO;
import cn.iocoder.yudao.module.bus.dal.mysql.relation.ProjectAchievementMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.bus.enums.ErrorCodeConstants.TECHNICAL_SECRET_NOT_EXISTS;

@Service
@Validated
public class TechnicalSecretServiceImpl implements TechnicalSecretService {

    @Resource
    private TechnicalSecretMapper secretMapper;
    @Resource
    private AchievementStaffMapper achievementStaffMapper;
    @Resource
    private ProjectAchievementMapper projectAchievementMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSecret(TechnicalSecretSaveReqVO createReqVO) {
        TechnicalSecretDO secret = BeanUtils.toBean(createReqVO, TechnicalSecretDO.class);
        secretMapper.insert(secret);
        
        // 保存人员关联
        saveSecretStaff(secret.getId(), createReqVO.getStaffIds());
        // 保存关联项目
        saveSecretProjects(secret.getId(), createReqVO.getProjectIds());
        
        return secret.getId();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSecret(TechnicalSecretSaveReqVO updateReqVO) {
        validateSecretExists(updateReqVO.getId());
        TechnicalSecretDO updateObj = BeanUtils.toBean(updateReqVO, TechnicalSecretDO.class);
        secretMapper.updateById(updateObj);
        
        // 更新人员关联
        achievementStaffMapper.deleteByAchievementIdAndType(updateReqVO.getId(), "SECRET");
        saveSecretStaff(updateReqVO.getId(), updateReqVO.getStaffIds());
        
        // 更新关联项目
        projectAchievementMapper.deleteByAchievementIdAndType(updateReqVO.getId(), "SECRET");
        saveSecretProjects(updateReqVO.getId(), updateReqVO.getProjectIds());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSecret(Long id) {
        validateSecretExists(id);
        secretMapper.deleteById(id);
        
        // 删除人员关联
        achievementStaffMapper.deleteByAchievementIdAndType(id, "SECRET");
        // 删除关联项目
        projectAchievementMapper.deleteByAchievementIdAndType(id, "SECRET");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSecretList(List<Long> ids) {
        secretMapper.deleteBatchIds(ids);
        ids.forEach(id -> {
            achievementStaffMapper.deleteByAchievementIdAndType(id, "SECRET");
            projectAchievementMapper.deleteByAchievementIdAndType(id, "SECRET");
        });
    }


    private void validateSecretExists(Long id) {
        if (secretMapper.selectById(id) == null) {
            throw exception(TECHNICAL_SECRET_NOT_EXISTS);
        }
    }

    @Override
    public TechnicalSecretDO getSecret(Long id) {
        return secretMapper.selectById(id);
    }

    @Override
    public PageResult<TechnicalSecretDO> getSecretPage(TechnicalSecretPageReqVO pageReqVO) {
        return secretMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TechnicalSecretDO> getSecretSimpleList() {
        return secretMapper.selectList();
    }

    @Override
    public List<Long> getSecretStaffIds(Long secretId) {
        return achievementStaffMapper.selectByAchievementIdAndType(secretId, "SECRET").stream()
                .map(AchievementStaffDO::getStaffId).collect(Collectors.toList());
    }

    @Override
    public List<Long> getSecretProjectIds(Long secretId) {
        return projectAchievementMapper.selectByAchievementIdAndType(secretId, "SECRET").stream()
                .map(ProjectAchievementDO::getProjectId).collect(Collectors.toList());
    }

    private void saveSecretStaff(Long secretId, List<Long> staffIds) {
        if (cn.hutool.core.collection.CollUtil.isNotEmpty(staffIds)) {
            staffIds.forEach(staffId -> {
                AchievementStaffDO relation = new AchievementStaffDO();
                relation.setAchievementId(secretId);
                relation.setStaffId(staffId);
                relation.setAchievementType("SECRET");
                relation.setAuthorType("INTERNAL");
                achievementStaffMapper.insert(relation);
            });
        }
    }

    private void saveSecretProjects(Long secretId, List<Long> projectIds) {
        if (cn.hutool.core.collection.CollUtil.isNotEmpty(projectIds)) {
            projectIds.forEach(projectId -> {
                ProjectAchievementDO relation = new ProjectAchievementDO();
                relation.setAchievementId(secretId);
                relation.setProjectId(projectId);
                relation.setAchievementType("SECRET");
                projectAchievementMapper.insert(relation);
            });
        }
    }

}

