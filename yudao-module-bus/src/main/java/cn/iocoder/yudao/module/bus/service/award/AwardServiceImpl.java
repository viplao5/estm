package cn.iocoder.yudao.module.bus.service.award;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.bus.controller.admin.award.vo.AwardPageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.award.vo.AwardSaveReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.award.AwardDO;
import cn.iocoder.yudao.module.bus.dal.mysql.award.AwardMapper;
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
import static cn.iocoder.yudao.module.bus.enums.ErrorCodeConstants.AWARD_NOT_EXISTS;

@Service
@Validated
public class AwardServiceImpl implements AwardService {
    @Resource
    private AwardMapper awardMapper;
    @Resource
    private AchievementStaffMapper achievementStaffMapper;
    @Resource
    private ProjectAchievementMapper projectAchievementMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createAward(AwardSaveReqVO createReqVO) {
        AwardDO award = BeanUtils.toBean(createReqVO, AwardDO.class);
        awardMapper.insert(award);
        
        // 保存人员关联
        saveAwardStaff(award.getId(), createReqVO.getStaffIds());
        // 保存关联项目
        saveAwardProjects(award.getId(), createReqVO.getProjectIds());
        
        return award.getId();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAward(AwardSaveReqVO updateReqVO) {
        validateAwardExists(updateReqVO.getId());
        AwardDO updateObj = BeanUtils.toBean(updateReqVO, AwardDO.class);
        awardMapper.updateById(updateObj);
        
        // 更新人员关联
        achievementStaffMapper.deleteByAchievementIdAndType(updateReqVO.getId(), "AWARD");
        saveAwardStaff(updateReqVO.getId(), updateReqVO.getStaffIds());
        
        // 更新关联项目
        projectAchievementMapper.deleteByAchievementIdAndType(updateReqVO.getId(), "AWARD");
        saveAwardProjects(updateReqVO.getId(), updateReqVO.getProjectIds());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAward(Long id) {
        validateAwardExists(id);
        awardMapper.deleteById(id);
        
        // 删除人员关联
        achievementStaffMapper.deleteByAchievementIdAndType(id, "AWARD");
        // 删除关联项目
        projectAchievementMapper.deleteByAchievementIdAndType(id, "AWARD");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAwardList(List<Long> ids) {
        awardMapper.deleteBatchIds(ids);
        ids.forEach(id -> {
            achievementStaffMapper.deleteByAchievementIdAndType(id, "AWARD");
            projectAchievementMapper.deleteByAchievementIdAndType(id, "AWARD");
        });
    }


    private void validateAwardExists(Long id) {
        if (awardMapper.selectById(id) == null) {
            throw exception(AWARD_NOT_EXISTS);
        }
    }

    @Override
    public AwardDO getAward(Long id) {
        return awardMapper.selectById(id);
    }

    @Override
    public PageResult<AwardDO> getAwardPage(AwardPageReqVO pageReqVO) {
        return awardMapper.selectPage(pageReqVO);
    }

    @Override
    public List<AwardDO> getAwardSimpleList() {
        return awardMapper.selectList();
    }
    @Override
    public List<Long> getAwardStaffIds(Long awardId) {
        return achievementStaffMapper.selectByAchievementIdAndType(awardId, "AWARD").stream()
                .map(AchievementStaffDO::getStaffId).collect(Collectors.toList());
    }

    @Override
    public List<Long> getAwardProjectIds(Long awardId) {
        return projectAchievementMapper.selectByAchievementIdAndType(awardId, "AWARD").stream()
                .map(ProjectAchievementDO::getProjectId).collect(Collectors.toList());
    }

    private void saveAwardStaff(Long awardId, List<Long> staffIds) {
        if (cn.hutool.core.collection.CollUtil.isNotEmpty(staffIds)) {
            staffIds.forEach(staffId -> {
                AchievementStaffDO relation = new AchievementStaffDO();
                relation.setAchievementId(awardId);
                relation.setStaffId(staffId);
                relation.setAchievementType("AWARD");
                relation.setAuthorType("INTERNAL");
                achievementStaffMapper.insert(relation);
            });
        }
    }

    private void saveAwardProjects(Long awardId, List<Long> projectIds) {
        if (cn.hutool.core.collection.CollUtil.isNotEmpty(projectIds)) {
            projectIds.forEach(projectId -> {
                ProjectAchievementDO relation = new ProjectAchievementDO();
                relation.setAchievementId(awardId);
                relation.setProjectId(projectId);
                relation.setAchievementType("AWARD");
                projectAchievementMapper.insert(relation);
            });
        }
    }
}

