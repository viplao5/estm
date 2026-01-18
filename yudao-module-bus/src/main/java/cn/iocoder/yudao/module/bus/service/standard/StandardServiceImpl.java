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

import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;
import static cn.iocoder.yudao.module.bus.enums.LogRecordConstants.*;

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
    @LogRecord(type = BUS_STANDARD_TYPE, subType = BUS_STANDARD_CREATE_SUB_TYPE, bizNo = "{{#standard.id}}",
            success = BUS_STANDARD_CREATE_SUCCESS)
    public Long createStandard(StandardSaveReqVO createReqVO) {
        StandardDO standard = BeanUtils.toBean(createReqVO, StandardDO.class);
        standardMapper.insert(standard);
        
        // 保存人员关联
        saveStandardStaff(standard.getId(), createReqVO.getStaffIds());
        // 保存关联项目
        saveStandardProjects(standard.getId(), createReqVO.getProjectIds());
        
        // 记录操作日志上下文
        LogRecordContext.putVariable("standard", standard);
        return standard.getId();
    }




    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = BUS_STANDARD_TYPE, subType = BUS_STANDARD_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = BUS_STANDARD_UPDATE_SUCCESS)
    public void updateStandard(StandardSaveReqVO updateReqVO) {
        StandardDO standard = validateStandardExists(updateReqVO.getId());
        
        // 记录操作日志上下文 - 旧对象
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(standard, StandardSaveReqVO.class));
        
        StandardDO updateObj = BeanUtils.toBean(updateReqVO, StandardDO.class);
        standardMapper.updateById(updateObj);
        
        // 更新人员关联
        achievementStaffMapper.deleteByAchievementIdAndType(updateReqVO.getId(), "STANDARD");
        saveStandardStaff(updateReqVO.getId(), updateReqVO.getStaffIds());
        
        // 更新关联项目
        projectAchievementMapper.deleteByAchievementIdAndType(updateReqVO.getId(), "STANDARD");
        saveStandardProjects(updateReqVO.getId(), updateReqVO.getProjectIds());

        // 记录操作日志上下文
        LogRecordContext.putVariable("standard", standard);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = BUS_STANDARD_TYPE, subType = BUS_STANDARD_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = BUS_STANDARD_DELETE_SUCCESS)
    public void deleteStandard(Long id) {
        StandardDO standard = validateStandardExists(id);
        standardMapper.deleteById(id);
        
        // 删除人员关联
        achievementStaffMapper.deleteByAchievementIdAndType(id, "STANDARD");
        // 删除关联项目
        projectAchievementMapper.deleteByAchievementIdAndType(id, "STANDARD");

        // 记录操作日志上下文
        LogRecordContext.putVariable("standard", standard);
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


    private StandardDO validateStandardExists(Long id) {
        StandardDO standard = standardMapper.selectById(id);
        if (standard == null) {
            throw exception(STANDARD_NOT_EXISTS);
        }
        return standard;
    }

    @Override
    @LogRecord(type = BUS_STANDARD_TYPE, subType = BUS_STANDARD_VIEW_SUB_TYPE, bizNo = "{{#id}}",
            success = BUS_STANDARD_VIEW_SUCCESS)
    public StandardDO getStandard(Long id) {
        StandardDO standard = standardMapper.selectById(id);
        if (standard != null) {
            LogRecordContext.putVariable("standard", standard);
        }
        return standard;
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

