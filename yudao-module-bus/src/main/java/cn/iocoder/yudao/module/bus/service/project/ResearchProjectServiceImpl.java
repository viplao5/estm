package cn.iocoder.yudao.module.bus.service.project;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.bus.controller.admin.project.vo.ResearchProjectPageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.project.vo.ResearchProjectSaveReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.project.ResearchProjectDO;
import cn.iocoder.yudao.module.bus.dal.mysql.project.ResearchProjectMapper;
import cn.iocoder.yudao.module.bus.dal.dataobject.relation.AchievementStaffDO;
import cn.iocoder.yudao.module.bus.dal.dataobject.relation.ProjectPlatformDO;
import cn.iocoder.yudao.module.bus.dal.mysql.relation.AchievementStaffMapper;
import cn.iocoder.yudao.module.bus.dal.mysql.relation.ProjectPlatformMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.bus.enums.ErrorCodeConstants.RESEARCH_PROJECT_NOT_EXISTS;

/**
 * 研发项目 Service 实现类
 */
@Service
@Validated
public class ResearchProjectServiceImpl implements ResearchProjectService {

    @Resource
    private ResearchProjectMapper projectMapper;
    @Resource
    private ProjectPlatformMapper projectPlatformMapper;
    @Resource
    private AchievementStaffMapper achievementStaffMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createProject(ResearchProjectSaveReqVO createReqVO) {
        ResearchProjectDO project = BeanUtils.toBean(createReqVO, ResearchProjectDO.class);
        projectMapper.insert(project);
        
        // 保存关联关系
        saveProjectRelations(project.getId(), createReqVO.getPlatformIds(), createReqVO.getMemberIds());
        
        return project.getId();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProject(ResearchProjectSaveReqVO updateReqVO) {
        validateProjectExists(updateReqVO.getId());
        ResearchProjectDO updateObj = BeanUtils.toBean(updateReqVO, ResearchProjectDO.class);
        projectMapper.updateById(updateObj);
        
        // 更新关联关系：先删除再重刷
        projectPlatformMapper.deleteByProjectId(updateReqVO.getId());
        achievementStaffMapper.deleteByAchievementIdAndType(updateReqVO.getId(), "PROJECT");
        saveProjectRelations(updateReqVO.getId(), updateReqVO.getPlatformIds(), updateReqVO.getMemberIds());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProject(Long id) {
        validateProjectExists(id);
        projectMapper.deleteById(id);
        
        // 删除关联关系
        projectPlatformMapper.deleteByProjectId(id);
        achievementStaffMapper.deleteByAchievementIdAndType(id, "PROJECT");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProjectList(List<Long> ids) {
        projectMapper.deleteBatchIds(ids);
        ids.forEach(id -> {
            projectPlatformMapper.deleteByProjectId(id);
            achievementStaffMapper.deleteByAchievementIdAndType(id, "PROJECT");
        });
    }


    private void validateProjectExists(Long id) {
        if (projectMapper.selectById(id) == null) {
            throw exception(RESEARCH_PROJECT_NOT_EXISTS);
        }
    }

    @Override
    public ResearchProjectDO getProject(Long id) {
        return projectMapper.selectById(id);
    }

    @Override
    public List<ResearchProjectDO> getProjectList(Collection<Long> ids) {
        return projectMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ResearchProjectDO> getProjectPage(ResearchProjectPageReqVO pageReqVO) {
        return projectMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ResearchProjectDO> getProjectSimpleList() {
        return projectMapper.selectList();
    }

    @Override
    public List<Long> getProjectPlatformIds(Long projectId) {
        return projectPlatformMapper.selectByProjectId(projectId).stream()
                .map(ProjectPlatformDO::getPlatformId).collect(Collectors.toList());
    }

    @Override
    public List<Long> getProjectMemberIds(Long projectId) {
        return achievementStaffMapper.selectByAchievementIdAndType(projectId, "PROJECT").stream()
                .map(AchievementStaffDO::getStaffId).collect(Collectors.toList());
    }

    private void saveProjectRelations(Long projectId, List<Long> platformIds, List<Long> memberIds) {
        // 保存平台关联
        if (cn.hutool.core.collection.CollUtil.isNotEmpty(platformIds)) {
            platformIds.forEach(platformId -> {
                ProjectPlatformDO relation = new ProjectPlatformDO();
                relation.setProjectId(projectId);
                relation.setPlatformId(platformId);
                projectPlatformMapper.insert(relation);
            });
        }
        // 保存成员关联
        if (cn.hutool.core.collection.CollUtil.isNotEmpty(memberIds)) {
            memberIds.forEach(memberId -> {
                AchievementStaffDO relation = new AchievementStaffDO();
                relation.setAchievementId(projectId);
                relation.setStaffId(memberId);
                relation.setAchievementType("PROJECT");
                relation.setAuthorType("INTERNAL");
                achievementStaffMapper.insert(relation);
            });
        }
    }

}

