package cn.iocoder.yudao.module.bus.service.paper;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.bus.controller.admin.paper.vo.PaperWorkPageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.paper.vo.PaperWorkSaveReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.paper.PaperWorkDO;
import cn.iocoder.yudao.module.bus.dal.mysql.paper.PaperWorkMapper;
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
import static cn.iocoder.yudao.module.bus.enums.ErrorCodeConstants.PAPER_WORK_NOT_EXISTS;

@Service
@Validated
public class PaperWorkServiceImpl implements PaperWorkService {

    @Resource
    private PaperWorkMapper paperMapper;
    @Resource
    private AchievementStaffMapper achievementStaffMapper;
    @Resource
    private ProjectAchievementMapper projectAchievementMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPaper(PaperWorkSaveReqVO createReqVO) {
        PaperWorkDO paper = BeanUtils.toBean(createReqVO, PaperWorkDO.class);
        paperMapper.insert(paper);
        
        // 保存作者关联
        savePaperAuthors(paper.getId(), createReqVO.getAuthorIds());
        // 保存关联项目
        savePaperProjects(paper.getId(), createReqVO.getProjectIds());
        
        return paper.getId();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePaper(PaperWorkSaveReqVO updateReqVO) {
        validatePaperExists(updateReqVO.getId());
        PaperWorkDO updateObj = BeanUtils.toBean(updateReqVO, PaperWorkDO.class);
        paperMapper.updateById(updateObj);
        
        // 更新作者关联
        achievementStaffMapper.deleteByAchievementIdAndType(updateReqVO.getId(), "PAPER");
        savePaperAuthors(updateReqVO.getId(), updateReqVO.getAuthorIds());
        
        // 更新关联项目
        projectAchievementMapper.deleteByAchievementIdAndType(updateReqVO.getId(), "PAPER");
        savePaperProjects(updateReqVO.getId(), updateReqVO.getProjectIds());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePaper(Long id) {
        validatePaperExists(id);
        paperMapper.deleteById(id);
        
        // 删除作者关联
        achievementStaffMapper.deleteByAchievementIdAndType(id, "PAPER");
        // 删除关联项目
        projectAchievementMapper.deleteByAchievementIdAndType(id, "PAPER");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePaperList(List<Long> ids) {
        paperMapper.deleteBatchIds(ids);
        ids.forEach(id -> {
            achievementStaffMapper.deleteByAchievementIdAndType(id, "PAPER");
            projectAchievementMapper.deleteByAchievementIdAndType(id, "PAPER");
        });
    }


    private void validatePaperExists(Long id) {
        if (paperMapper.selectById(id) == null) {
            throw exception(PAPER_WORK_NOT_EXISTS);
        }
    }

    @Override
    public PaperWorkDO getPaper(Long id) {
        return paperMapper.selectById(id);
    }

    @Override
    public PageResult<PaperWorkDO> getPaperPage(PaperWorkPageReqVO pageReqVO) {
        return paperMapper.selectPage(pageReqVO);
    }

    @Override
    public List<PaperWorkDO> getPaperSimpleList() {
        return paperMapper.selectList();
    }

    @Override
    public List<Long> getPaperAuthorIds(Long paperId) {
        return achievementStaffMapper.selectByAchievementIdAndType(paperId, "PAPER").stream()
                .map(AchievementStaffDO::getStaffId).collect(Collectors.toList());
    }

    @Override
    public List<Long> getPaperProjectIds(Long paperId) {
        return projectAchievementMapper.selectByAchievementIdAndType(paperId, "PAPER").stream()
                .map(ProjectAchievementDO::getProjectId).collect(Collectors.toList());
    }

    private void savePaperAuthors(Long paperId, List<Long> authorIds) {
        if (cn.hutool.core.collection.CollUtil.isNotEmpty(authorIds)) {
            authorIds.forEach(staffId -> {
                AchievementStaffDO relation = new AchievementStaffDO();
                relation.setAchievementId(paperId);
                relation.setStaffId(staffId);
                relation.setAchievementType("PAPER");
                relation.setAuthorType("INTERNAL");
                achievementStaffMapper.insert(relation);
            });
        }
    }

    private void savePaperProjects(Long paperId, List<Long> projectIds) {
        if (cn.hutool.core.collection.CollUtil.isNotEmpty(projectIds)) {
            projectIds.forEach(projectId -> {
                ProjectAchievementDO relation = new ProjectAchievementDO();
                relation.setAchievementId(paperId);
                relation.setProjectId(projectId);
                relation.setAchievementType("PAPER");
                projectAchievementMapper.insert(relation);
            });
        }
    }

}

