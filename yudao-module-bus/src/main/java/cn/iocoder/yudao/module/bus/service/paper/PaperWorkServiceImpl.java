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


import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;
import com.mzt.logapi.service.impl.DiffParseFunction;
import static cn.iocoder.yudao.module.bus.enums.LogRecordConstants.*;

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
    @LogRecord(type = BUS_PAPER_TYPE, subType = BUS_PAPER_CREATE_SUB_TYPE, bizNo = "{{#paper.id}}",
            success = BUS_PAPER_CREATE_SUCCESS)
    public Long createPaper(PaperWorkSaveReqVO createReqVO) {
        PaperWorkDO paper = BeanUtils.toBean(createReqVO, PaperWorkDO.class);
        paperMapper.insert(paper);
        
        // 保存作者关联
        savePaperAuthors(paper.getId(), createReqVO.getAuthorIds());
        // 保存关联项目
        savePaperProjects(paper.getId(), createReqVO.getProjectIds());
        
        // 记录操作日志上下文
        LogRecordContext.putVariable("paper", paper);
        return paper.getId();
    }




    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = BUS_PAPER_TYPE, subType = BUS_PAPER_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = BUS_PAPER_UPDATE_SUCCESS)
    public void updatePaper(PaperWorkSaveReqVO updateReqVO) {
        PaperWorkDO paper = validatePaperExists(updateReqVO.getId());
        
        // 记录操作日志上下文 - 旧对象
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(paper, PaperWorkSaveReqVO.class));
        
        PaperWorkDO updateObj = BeanUtils.toBean(updateReqVO, PaperWorkDO.class);
        paperMapper.updateById(updateObj);
        
        // 更新作者关联
        achievementStaffMapper.deleteByAchievementIdAndType(updateReqVO.getId(), "PAPER");
        savePaperAuthors(updateReqVO.getId(), updateReqVO.getAuthorIds());
        
        // 更新关联项目
        projectAchievementMapper.deleteByAchievementIdAndType(updateReqVO.getId(), "PAPER");
        savePaperProjects(updateReqVO.getId(), updateReqVO.getProjectIds());

        // 记录操作日志上下文
        LogRecordContext.putVariable("paper", paper);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = BUS_PAPER_TYPE, subType = BUS_PAPER_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = BUS_PAPER_DELETE_SUCCESS)
    public void deletePaper(Long id) {
        PaperWorkDO paper = validatePaperExists(id);
        paperMapper.deleteById(id);
        
        // 删除作者关联
        achievementStaffMapper.deleteByAchievementIdAndType(id, "PAPER");
        // 删除关联项目
        projectAchievementMapper.deleteByAchievementIdAndType(id, "PAPER");

        // 记录操作日志上下文
        LogRecordContext.putVariable("paper", paper);
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


    private PaperWorkDO validatePaperExists(Long id) {
        PaperWorkDO paper = paperMapper.selectById(id);
        if (paper == null) {
            throw exception(PAPER_WORK_NOT_EXISTS);
        }
        return paper;
    }

    @Override
    @LogRecord(type = BUS_PAPER_TYPE, subType = BUS_PAPER_VIEW_SUB_TYPE, bizNo = "{{#id}}",
            success = BUS_PAPER_VIEW_SUCCESS)
    public PaperWorkDO getPaper(Long id) {
        PaperWorkDO paper = paperMapper.selectById(id);
        if (paper != null) {
            LogRecordContext.putVariable("paper", paper);
        }
        return paper;
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

