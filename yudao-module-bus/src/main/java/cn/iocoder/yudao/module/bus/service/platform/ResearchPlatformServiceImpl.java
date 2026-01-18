package cn.iocoder.yudao.module.bus.service.platform;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.bus.controller.admin.platform.vo.ResearchPlatformPageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.platform.vo.ResearchPlatformSaveReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.platform.ResearchPlatformDO;
import cn.iocoder.yudao.module.bus.dal.dataobject.relation.AchievementStaffDO;
import cn.iocoder.yudao.module.bus.dal.mysql.platform.ResearchPlatformMapper;
import cn.iocoder.yudao.module.bus.dal.mysql.relation.AchievementStaffMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.bus.enums.ErrorCodeConstants.RESEARCH_PLATFORM_NOT_EXISTS;

/**
 * 科研平台 Service 实现类
 */
@Service
@Validated
public class ResearchPlatformServiceImpl implements ResearchPlatformService {

    @Resource
    private ResearchPlatformMapper platformMapper;
    @Resource
    private AchievementStaffMapper achievementStaffMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPlatform(ResearchPlatformSaveReqVO createReqVO) {
        ResearchPlatformDO platform = BeanUtils.toBean(createReqVO, ResearchPlatformDO.class);
        platformMapper.insert(platform);
        
        // 保存人员关联
        savePlatformStaff(platform.getId(), createReqVO.getStaffIds());
        
        return platform.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePlatform(ResearchPlatformSaveReqVO updateReqVO) {
        validatePlatformExists(updateReqVO.getId());
        ResearchPlatformDO updateObj = BeanUtils.toBean(updateReqVO, ResearchPlatformDO.class);
        platformMapper.updateById(updateObj);
        
        // 更新人员关联
        achievementStaffMapper.deleteByAchievementIdAndType(updateReqVO.getId(), "PLATFORM");
        savePlatformStaff(updateReqVO.getId(), updateReqVO.getStaffIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePlatform(Long id) {
        validatePlatformExists(id);
        platformMapper.deleteById(id);
        
        // 删除人员关联
        achievementStaffMapper.deleteByAchievementIdAndType(id, "PLATFORM");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePlatformList(List<Long> ids) {
        platformMapper.deleteBatchIds(ids);
        ids.forEach(id -> {
            achievementStaffMapper.deleteByAchievementIdAndType(id, "PLATFORM");
        });
    }

    private void validatePlatformExists(Long id) {
        if (platformMapper.selectById(id) == null) {
            throw exception(RESEARCH_PLATFORM_NOT_EXISTS);
        }
    }

    @Override
    public ResearchPlatformDO getPlatform(Long id) {
        return platformMapper.selectById(id);
    }

    @Override
    public List<ResearchPlatformDO> getPlatformList(Collection<Long> ids) {
        return platformMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ResearchPlatformDO> getPlatformPage(ResearchPlatformPageReqVO pageReqVO) {
        return platformMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ResearchPlatformDO> getPlatformSimpleList() {
        return platformMapper.selectList();
    }

    @Override
    public List<Long> getPlatformStaffIds(Long platformId) {
        return achievementStaffMapper.selectByAchievementIdAndType(platformId, "PLATFORM").stream()
                .map(AchievementStaffDO::getStaffId).collect(java.util.stream.Collectors.toList());
    }

    private void savePlatformStaff(Long platformId, List<Long> staffIds) {
        if (cn.hutool.core.collection.CollUtil.isNotEmpty(staffIds)) {
            staffIds.forEach(staffId -> {
                AchievementStaffDO relation = new AchievementStaffDO();
                relation.setAchievementId(platformId);
                relation.setStaffId(staffId);
                relation.setAchievementType("PLATFORM");
                relation.setAuthorType("INTERNAL");
                achievementStaffMapper.insert(relation);
            });
        }
    }

}
