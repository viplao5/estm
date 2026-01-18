package cn.iocoder.yudao.module.bus.service.ip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.bus.controller.admin.ip.vo.IntellectualPropertyPageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.ip.vo.IntellectualPropertySaveReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.ip.IntellectualPropertyDO;
import cn.iocoder.yudao.module.bus.dal.mysql.ip.IntellectualPropertyMapper;
import cn.iocoder.yudao.module.bus.dal.dataobject.relation.AchievementStaffDO;
import cn.iocoder.yudao.module.bus.dal.mysql.relation.AchievementStaffMapper;
import cn.iocoder.yudao.module.bus.dal.dataobject.relation.ProjectAchievementDO;
import cn.iocoder.yudao.module.bus.dal.mysql.relation.ProjectAchievementMapper;
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
import static cn.iocoder.yudao.module.bus.enums.ErrorCodeConstants.INTELLECTUAL_PROPERTY_NOT_EXISTS;

/**
 * 知识产权 Service 实现类
 */
@Service
@Validated
public class IntellectualPropertyServiceImpl implements IntellectualPropertyService {

    @Resource
    private IntellectualPropertyMapper ipMapper;
    @Resource
    private AchievementStaffMapper achievementStaffMapper;
    @Resource
    private ProjectAchievementMapper projectAchievementMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = BUS_IP_TYPE, subType = BUS_IP_CREATE_SUB_TYPE, bizNo = "{{#ip.id}}",
            success = BUS_IP_CREATE_SUCCESS)
    public Long createIP(IntellectualPropertySaveReqVO createReqVO) {
        IntellectualPropertyDO ip = BeanUtils.toBean(createReqVO, IntellectualPropertyDO.class);
        ipMapper.insert(ip);
        
        // 保存发明人关联
        saveIPInventors(ip.getId(), createReqVO.getInventorIds());
        // 保存关联项目
        saveIPProjects(ip.getId(), createReqVO.getProjectIds());
        
        // 记录操作日志上下文
        LogRecordContext.putVariable("ip", ip);
        return ip.getId();
    }




    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = BUS_IP_TYPE, subType = BUS_IP_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = BUS_IP_UPDATE_SUCCESS)
    public void updateIP(IntellectualPropertySaveReqVO updateReqVO) {
        IntellectualPropertyDO ip = validateIPExists(updateReqVO.getId());
        
        // 记录操作日志上下文 - 旧对象
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(ip, IntellectualPropertySaveReqVO.class));
        
        IntellectualPropertyDO updateObj = BeanUtils.toBean(updateReqVO, IntellectualPropertyDO.class);
        ipMapper.updateById(updateObj);
        
        // 更新发明人关联
        achievementStaffMapper.deleteByAchievementIdAndType(updateReqVO.getId(), "IP");
        saveIPInventors(updateReqVO.getId(), updateReqVO.getInventorIds());
        
        // 更新关联项目
        projectAchievementMapper.deleteByAchievementIdAndType(updateReqVO.getId(), "IP");
        saveIPProjects(updateReqVO.getId(), updateReqVO.getProjectIds());

        // 记录操作日志上下文
        LogRecordContext.putVariable("ip", ip);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = BUS_IP_TYPE, subType = BUS_IP_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = BUS_IP_DELETE_SUCCESS)
    public void deleteIP(Long id) {
        IntellectualPropertyDO ip = validateIPExists(id);
        ipMapper.deleteById(id);
        
        // 删除发明人关联
        achievementStaffMapper.deleteByAchievementIdAndType(id, "IP");
        // 删除关联项目
        projectAchievementMapper.deleteByAchievementIdAndType(id, "IP");

        // 记录操作日志上下文
        LogRecordContext.putVariable("ip", ip);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteIPList(List<Long> ids) {
        ipMapper.deleteBatchIds(ids);
        ids.forEach(id -> {
            achievementStaffMapper.deleteByAchievementIdAndType(id, "IP");
            projectAchievementMapper.deleteByAchievementIdAndType(id, "IP");
        });
    }


    private IntellectualPropertyDO validateIPExists(Long id) {
        IntellectualPropertyDO ip = ipMapper.selectById(id);
        if (ip == null) {
            throw exception(INTELLECTUAL_PROPERTY_NOT_EXISTS);
        }
        return ip;
    }

    @Override
    @LogRecord(type = BUS_IP_TYPE, subType = BUS_IP_VIEW_SUB_TYPE, bizNo = "{{#id}}",
            success = BUS_IP_VIEW_SUCCESS)
    public IntellectualPropertyDO getIP(Long id) {
        IntellectualPropertyDO ip = ipMapper.selectById(id);
        if (ip != null) {
            LogRecordContext.putVariable("ip", ip);
        }
        return ip;
    }

    @Override
    public List<IntellectualPropertyDO> getIPList(Collection<Long> ids) {
        return ipMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<IntellectualPropertyDO> getIPPage(IntellectualPropertyPageReqVO pageReqVO) {
        return ipMapper.selectPage(pageReqVO);
    }

    @Override
    public List<IntellectualPropertyDO> getIPSimpleList() {
        return ipMapper.selectList();
    }

    @Override
    public List<Long> getIPInventorIds(Long ipId) {
        return achievementStaffMapper.selectByAchievementIdAndType(ipId, "IP").stream()
                .map(AchievementStaffDO::getStaffId).collect(Collectors.toList());
    }

    @Override
    public List<Long> getIPProjectIds(Long ipId) {
        return projectAchievementMapper.selectByAchievementIdAndType(ipId, "IP").stream()
                .map(ProjectAchievementDO::getProjectId).collect(Collectors.toList());
    }

    private void saveIPInventors(Long ipId, List<Long> inventorIds) {
        if (cn.hutool.core.collection.CollUtil.isNotEmpty(inventorIds)) {
            inventorIds.forEach(staffId -> {
                AchievementStaffDO relation = new AchievementStaffDO();
                relation.setAchievementId(ipId);
                relation.setStaffId(staffId);
                relation.setAchievementType("IP");
                relation.setAuthorType("INTERNAL");
                achievementStaffMapper.insert(relation);
            });
        }
    }

    private void saveIPProjects(Long ipId, List<Long> projectIds) {
        if (cn.hutool.core.collection.CollUtil.isNotEmpty(projectIds)) {
            projectIds.forEach(projectId -> {
                ProjectAchievementDO relation = new ProjectAchievementDO();
                relation.setAchievementId(ipId);
                relation.setProjectId(projectId);
                relation.setAchievementType("IP");
                projectAchievementMapper.insert(relation);
            });
        }
    }

}

