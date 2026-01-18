package cn.iocoder.yudao.module.bus.controller.admin.project;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.encrypt.core.annotation.ApiEncrypt;
import cn.iocoder.yudao.module.bus.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.bus.dal.dataobject.project.ResearchProjectDO;
import cn.iocoder.yudao.module.bus.service.project.ResearchProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 研发项目")
@RestController
@RequestMapping("/bus/research-project")
@Validated
public class ResearchProjectController {

    @Resource
    private ResearchProjectService projectService;

    @PostMapping("/create")
    @Operation(summary = "创建研发项目")
    @PreAuthorize("@ss.hasPermission('bus:research-project:create')")
    public CommonResult<Long> createProject(@Valid @RequestBody ResearchProjectSaveReqVO createReqVO) {
        return success(projectService.createProject(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新研发项目")
    @PreAuthorize("@ss.hasPermission('bus:research-project:update')")
    public CommonResult<Boolean> updateProject(@Valid @RequestBody ResearchProjectSaveReqVO updateReqVO) {
        projectService.updateProject(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除研发项目")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:research-project:delete')")
    public CommonResult<Boolean> deleteProject(@RequestParam("id") Long id) {
        projectService.deleteProject(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "批量删除研发项目")
    @Parameter(name = "ids", description = "编号列表", required = true)
    @PreAuthorize("@ss.hasPermission('bus:research-project:delete')")
    public CommonResult<Boolean> deleteProjectList(@RequestParam("ids") List<Long> ids) {
        projectService.deleteProjectList(ids);
        return success(true);
    }

    @ApiEncrypt(request = true, response = true)
    @GetMapping("/get")
    @Operation(summary = "获得研发项目")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:research-project:query')")
    public CommonResult<ResearchProjectRespVO> getProject(@RequestParam("id") Long id) {
        ResearchProjectDO project = projectService.getProject(id);
        ResearchProjectRespVO respVO = BeanUtils.toBean(project, ResearchProjectRespVO.class);
        // 补充关联信息
        if (respVO != null) {
            respVO.setPlatformIds(projectService.getProjectPlatformIds(id));
            respVO.setMemberIds(projectService.getProjectMemberIds(id));
        }
        return success(respVO);
    }


    @ApiEncrypt(request = true, response = true)
    @GetMapping("/page")
    @Operation(summary = "获得研发项目分页")
    @PreAuthorize("@ss.hasPermission('bus:research-project:query')")
    public CommonResult<PageResult<ResearchProjectRespVO>> getProjectPage(@Valid ResearchProjectPageReqVO pageVO) {
        PageResult<ResearchProjectDO> pageResult = projectService.getProjectPage(pageVO);
        return success(BeanUtils.toBean(pageResult, ResearchProjectRespVO.class));
    }

    @ApiEncrypt(request = true, response = true)
    @GetMapping("/simple-list")
    @Operation(summary = "获取研发项目精简列表", description = "用于下拉选择")
    public CommonResult<List<ResearchProjectSimpleRespVO>> getProjectSimpleList() {
        List<ResearchProjectDO> list = projectService.getProjectSimpleList();
        return success(BeanUtils.toBean(list, ResearchProjectSimpleRespVO.class));
    }

}
