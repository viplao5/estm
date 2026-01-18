package cn.iocoder.yudao.module.bus.service.project;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bus.controller.admin.project.vo.ResearchProjectPageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.project.vo.ResearchProjectSaveReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.project.ResearchProjectDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 研发项目 Service 接口
 */
public interface ResearchProjectService {

    Long createProject(@Valid ResearchProjectSaveReqVO createReqVO);

    void updateProject(@Valid ResearchProjectSaveReqVO updateReqVO);

    void deleteProject(Long id);

    void deleteProjectList(List<Long> ids);

    ResearchProjectDO getProject(Long id);

    List<ResearchProjectDO> getProjectList(Collection<Long> ids);

    PageResult<ResearchProjectDO> getProjectPage(ResearchProjectPageReqVO pageReqVO);

    List<ResearchProjectDO> getProjectSimpleList();

    /**
     * 获取项目关联的平台ID列表
     * @param projectId 项目ID
     * @return 平台ID列表
     */
    List<Long> getProjectPlatformIds(Long projectId);

    /**
     * 获取项目关联的成员ID列表
     * @param projectId 项目ID
     * @return 成员ID列表
     */
    List<Long> getProjectMemberIds(Long projectId);

}
