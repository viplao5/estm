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
import com.mzt.logapi.service.impl.DiffParseFunction;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;
import static cn.iocoder.yudao.module.bus.enums.LogRecordConstants.*;

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
    @LogRecord(type = BUS_PROJECT_TYPE, subType = BUS_PROJECT_CREATE_SUB_TYPE, bizNo = "{{#project.id}}",
            success = BUS_PROJECT_CREATE_SUCCESS)
    public Long createProject(ResearchProjectSaveReqVO createReqVO) {
        ResearchProjectDO project = BeanUtils.toBean(createReqVO, ResearchProjectDO.class);
        projectMapper.insert(project);
        
        // 保存关联关系
        saveProjectRelations(project.getId(), createReqVO.getPlatformIds(), createReqVO.getMemberIds());
        
        // 记录操作日志上下文
        LogRecordContext.putVariable("project", project);
        return project.getId();
    }




    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = BUS_PROJECT_TYPE, subType = BUS_PROJECT_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = BUS_PROJECT_UPDATE_SUCCESS)
    public void updateProject(ResearchProjectSaveReqVO updateReqVO) {
        ResearchProjectDO project = validateProjectExists(updateReqVO.getId());
        
        // 记录操作日志上下文 - 旧对象
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(project, ResearchProjectSaveReqVO.class));
        
        ResearchProjectDO updateObj = BeanUtils.toBean(updateReqVO, ResearchProjectDO.class);
        projectMapper.updateById(updateObj);
        
        // 更新关联关系：先删除再重刷
        projectPlatformMapper.deleteByProjectId(updateReqVO.getId());
        achievementStaffMapper.deleteByAchievementIdAndType(updateReqVO.getId(), "PROJECT");
        saveProjectRelations(updateReqVO.getId(), updateReqVO.getPlatformIds(), updateReqVO.getMemberIds());

        // 记录操作日志上下文 - 新对象（实际上 diff 是跟 updateReqVO 比，这里 context 放 project 主要是为了 success 模版里的 {{#project.name}}）
        LogRecordContext.putVariable("project", project);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = BUS_PROJECT_TYPE, subType = BUS_PROJECT_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = BUS_PROJECT_DELETE_SUCCESS)
    public void deleteProject(Long id) {
        ResearchProjectDO project = validateProjectExists(id);
        projectMapper.deleteById(id);
        
        // 删除关联关系
        projectPlatformMapper.deleteByProjectId(id);
        achievementStaffMapper.deleteByAchievementIdAndType(id, "PROJECT");

        // 记录操作日志上下文
        LogRecordContext.putVariable("project", project);
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


    private ResearchProjectDO validateProjectExists(Long id) {
        ResearchProjectDO project = projectMapper.selectById(id);
        if (project == null) {
            throw exception(RESEARCH_PROJECT_NOT_EXISTS);
        }
        return project;
    }

    @Override
    @LogRecord(type = BUS_PROJECT_TYPE, subType = BUS_PROJECT_VIEW_SUB_TYPE, bizNo = "{{#id}}",
            success = BUS_PROJECT_VIEW_SUCCESS)
    public ResearchProjectDO getProject(Long id) {
        ResearchProjectDO project = projectMapper.selectById(id);
        if (project != null) {
            LogRecordContext.putVariable("project", project);
        }
        return project;
    }

    @Override
    public List<ResearchProjectDO> getProjectList(Collection<Long> ids) {
        return projectMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ResearchProjectDO> getProjectPage(ResearchProjectPageReqVO pageReqVO) {
        // 如果有 staffId，先查询关联的项目 ID
        if (pageReqVO.getStaffId() != null) {
            List<AchievementStaffDO> relations = achievementStaffMapper.selectByStaffIdAndType(pageReqVO.getStaffId(), "PROJECT");
            if (cn.hutool.core.collection.CollUtil.isEmpty(relations)) {
                return PageResult.empty();
            }
            // 提取项目ID列表
            List<Long> projectIds = cn.hutool.core.collection.CollUtil.map(relations, AchievementStaffDO::getAchievementId, true);
            // 这里实际上应该取交集，但 PageParam 通常没有 ids 字段供前端直接传。
            // Yudao 的 PageParam 本身没有 ids，但 Mybatis-Plus 这里的 wrapper 可以加 in
            // 我们需要 modify ResearchProjectMapper.selectPage to accept ids or modify reqVO to have ids (BaseMapperX usually handles collection in wrapper if we pass it)
            // 但是这里 selectPage 是自定义的 default 方法 (BaseMapperX 扩展)，它接受 reqVO。
            // 简单做法：我们修改 ResearchProjectMapper 的 selectPage 方法，但这比较麻烦。
            // 更好的做法：我们给 ResearchProjectPageReqVO 加一个 ids 字段（虽然前端不传，但 Service 层可以 set），
            // 然后在 Mapper 里处理 ids in。
            // 让我们看看 ResearchProjectPageReqVO 是否有 ids。它继承 PageParam。
            // 让我们给 ResearchProjectPageReqVO 加个 ids 字段 (Hidden for frontend usually, but internal use)
            // 或者直接用 LambdaQueryWrapperX 在这里构建，不用 default selectPage.
            // 考虑到 consistency，我们在 VO 加个 ids 字段，然后在 Mapper 加 inQuery.
            // 但现在不能改 VO 的 ids 字段（它是通用的吗？）。
            // 让我们先看看 ResearchProjectPageReqVO.java (已阅，没有 ids)。
            // 我们可以直接在这里用 mapper.selectPage(reqVO, wrapper) 的形式，重写 wrapper 逻辑。
            
            // 重新构建 wrapper
            return projectMapper.selectPage(pageReqVO, new cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX<ResearchProjectDO>()
                    .likeIfPresent(ResearchProjectDO::getName, pageReqVO.getName())
                    .eqIfPresent(ResearchProjectDO::getCategory, pageReqVO.getCategory())
                    .eqIfPresent(ResearchProjectDO::getStatus, pageReqVO.getStatus())
                    .eqIfPresent(ResearchProjectDO::getLeaderUserId, pageReqVO.getLeaderUserId())
                    .betweenIfPresent(ResearchProjectDO::getStartDate, pageReqVO.getStartDate())
                    .in(ResearchProjectDO::getId, projectIds) // 核心过滤
                    .orderByDesc(ResearchProjectDO::getId));
        }
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

