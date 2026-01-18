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

import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;
import static cn.iocoder.yudao.module.bus.enums.LogRecordConstants.*;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.bus.enums.ErrorCodeConstants.AWARD_NOT_EXISTS;
import com.mzt.logapi.service.impl.DiffParseFunction;

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
    @LogRecord(type = BUS_AWARD_TYPE, subType = BUS_AWARD_CREATE_SUB_TYPE, bizNo = "{{#award.id}}",
            success = BUS_AWARD_CREATE_SUCCESS)
    public Long createAward(AwardSaveReqVO createReqVO) {
        AwardDO award = BeanUtils.toBean(createReqVO, AwardDO.class);
        awardMapper.insert(award);
        
        // 保存人员关联
        saveAwardStaff(award.getId(), createReqVO.getStaffIds());
        // 保存关联项目
        saveAwardProjects(award.getId(), createReqVO.getProjectIds());
        
        // 记录操作日志上下文
        LogRecordContext.putVariable("award", award);
        return award.getId();
    }




    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = BUS_AWARD_TYPE, subType = BUS_AWARD_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = BUS_AWARD_UPDATE_SUCCESS)
    public void updateAward(AwardSaveReqVO updateReqVO) {
        AwardDO award = validateAwardExists(updateReqVO.getId());
        
        // 记录操作日志上下文 - 旧对象
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(award, AwardSaveReqVO.class));
        
        AwardDO updateObj = BeanUtils.toBean(updateReqVO, AwardDO.class);
        awardMapper.updateById(updateObj);
        
        // 更新人员关联
        achievementStaffMapper.deleteByAchievementIdAndType(updateReqVO.getId(), "AWARD");
        saveAwardStaff(updateReqVO.getId(), updateReqVO.getStaffIds());
        
        // 更新关联项目
        projectAchievementMapper.deleteByAchievementIdAndType(updateReqVO.getId(), "AWARD");
        saveAwardProjects(updateReqVO.getId(), updateReqVO.getProjectIds());

        // 记录操作日志上下文
        LogRecordContext.putVariable("award", award);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = BUS_AWARD_TYPE, subType = BUS_AWARD_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = BUS_AWARD_DELETE_SUCCESS)
    public void deleteAward(Long id) {
        AwardDO award = validateAwardExists(id);
        awardMapper.deleteById(id);
        
        // 删除人员关联
        achievementStaffMapper.deleteByAchievementIdAndType(id, "AWARD");
        // 删除关联项目
        projectAchievementMapper.deleteByAchievementIdAndType(id, "AWARD");

        // 记录操作日志上下文
        LogRecordContext.putVariable("award", award);
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


    private AwardDO validateAwardExists(Long id) {
        AwardDO award = awardMapper.selectById(id);
        if (award == null) {
            throw exception(AWARD_NOT_EXISTS);
        }
        return award;
    }

    @Override
    @LogRecord(type = BUS_AWARD_TYPE, subType = BUS_AWARD_VIEW_SUB_TYPE, bizNo = "{{#id}}",
            success = BUS_AWARD_VIEW_SUCCESS)
    public AwardDO getAward(Long id) {
        AwardDO award = awardMapper.selectById(id);
        if (award != null) {
            LogRecordContext.putVariable("award", award);
        }
        return award;
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

