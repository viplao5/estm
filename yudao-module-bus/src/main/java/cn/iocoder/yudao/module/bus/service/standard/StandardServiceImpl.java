package cn.iocoder.yudao.module.bus.service.standard;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.bus.controller.admin.standard.vo.StandardPageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.standard.vo.StandardSaveReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.standard.StandardDO;
import cn.iocoder.yudao.module.bus.dal.mysql.standard.StandardMapper;
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
import static cn.iocoder.yudao.module.bus.enums.ErrorCodeConstants.STANDARD_NOT_EXISTS;

@Service
@Validated
public class StandardServiceImpl implements StandardService {
    @Resource
    private StandardMapper standardMapper;
    @Resource
    private AchievementStaffMapper achievementStaffMapper;
    @Resource
    private ProjectAchievementMapper projectAchievementMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createStandard(StandardSaveReqVO createReqVO) {
        StandardDO standard = BeanUtils.toBean(createReqVO, StandardDO.class);
        standardMapper.insert(standard);
        
        // 保存人员关联
        saveStandardStaff(standard.getId(), createReqVO.getStaffIds());
        // 保存关联项目
        saveStandardProjects(standard.getId(), createReqVO.getProjectIds());
        
        return standard.getId();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStandard(StandardSaveReqVO updateReqVO) {
        validateStandardExists(updateReqVO.getId());
        StandardDO updateObj = BeanUtils.toBean(updateReqVO, StandardDO.class);
        standardMapper.updateById(updateObj);
        
        // 更新人员关联
        achievementStaffMapper.deleteByAchievementIdAndType(updateReqVO.getId(), "STANDARD");
        saveStandardStaff(updateReqVO.getId(), updateReqVO.getStaffIds());
        
        // 更新关联项目
        projectAchievementMapper.deleteByAchievementIdAndType(updateReqVO.getId(), "STANDARD");
        saveStandardProjects(updateReqVO.getId(), updateReqVO.getProjectIds());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteStandard(Long id) {
        validateStandardExists(id);
        standardMapper.deleteById(id);
        
        // 删除人员关联
        achievementStaffMapper.deleteByAchievementIdAndType(id, "STANDARD");
        // 删除关联项目
        projectAchievementMapper.deleteByAchievementIdAndType(id, "STANDARD");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteStandardList(List<Long> ids) {
        standardMapper.deleteBatchIds(ids);
        ids.forEach(id -> {
            achievementStaffMapper.deleteByAchievementIdAndType(id, "STANDARD");
            projectAchievementMapper.deleteByAchievementIdAndType(id, "STANDARD");
        });
    }


    private void validateStandardExists(Long id) {
        if (standardMapper.selectById(id) == null) {
            throw exception(STANDARD_NOT_EXISTS);
        }
    }

    @Override
    public StandardDO getStandard(Long id) {
        return standardMapper.selectById(id);
    }

    @Override
    public PageResult<StandardDO> getStandardPage(StandardPageReqVO pageReqVO) {
        return standardMapper.selectPage(pageReqVO);
    }

    @Override
    public List<StandardDO> getStandardSimpleList() {
        return standardMapper.selectList();
    }
    @Override
    public List<Long> getStandardStaffIds(Long standardId) {
        return achievementStaffMapper.selectByAchievementIdAndType(standardId, "STANDARD").stream()
                .map(AchievementStaffDO::getStaffId).collect(Collectors.toList());
    }

    @Override
    public List<Long> getStandardProjectIds(Long standardId) {
        return projectAchievementMapper.selectByAchievementIdAndType(standardId, "STANDARD").stream()
                .map(ProjectAchievementDO::getProjectId).collect(Collectors.toList());
    }

    private void saveStandardStaff(Long standardId, List<Long> staffIds) {
        if (cn.hutool.core.collection.CollUtil.isNotEmpty(staffIds)) {
            staffIds.forEach(staffId -> {
                AchievementStaffDO relation = new AchievementStaffDO();
                relation.setAchievementId(standardId);
                relation.setStaffId(staffId);
                relation.setAchievementType("STANDARD");
                relation.setAuthorType("INTERNAL");
                achievementStaffMapper.insert(relation);
            });
        }
    }

    private void saveStandardProjects(Long standardId, List<Long> projectIds) {
        if (cn.hutool.core.collection.CollUtil.isNotEmpty(projectIds)) {
            projectIds.forEach(projectId -> {
                ProjectAchievementDO relation = new ProjectAchievementDO();
                relation.setAchievementId(standardId);
                relation.setProjectId(projectId);
                relation.setAchievementType("STANDARD");
                projectAchievementMapper.insert(relation);
            });
        }
    }
}

