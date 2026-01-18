package cn.iocoder.yudao.module.bus.controller.admin.platform;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.bus.controller.admin.platform.vo.*;
import cn.iocoder.yudao.module.bus.dal.dataobject.platform.ResearchPlatformDO;
import cn.iocoder.yudao.module.bus.service.platform.ResearchPlatformService;
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

@Tag(name = "管理后台 - 科研平台")
@RestController
@RequestMapping("/bus/research-platform")
@Validated
public class ResearchPlatformController {

    @Resource
    private ResearchPlatformService platformService;

    @PostMapping("/create")
    @Operation(summary = "创建科研平台")
    @PreAuthorize("@ss.hasPermission('bus:research-platform:create')")
    public CommonResult<Long> createPlatform(@Valid @RequestBody ResearchPlatformSaveReqVO createReqVO) {
        return success(platformService.createPlatform(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新科研平台")
    @PreAuthorize("@ss.hasPermission('bus:research-platform:update')")
    public CommonResult<Boolean> updatePlatform(@Valid @RequestBody ResearchPlatformSaveReqVO updateReqVO) {
        platformService.updatePlatform(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除科研平台")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:research-platform:delete')")
    public CommonResult<Boolean> deletePlatform(@RequestParam("id") Long id) {
        platformService.deletePlatform(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "批量删除科研平台")
    @Parameter(name = "ids", description = "编号列表", required = true)
    @PreAuthorize("@ss.hasPermission('bus:research-platform:delete')")
    public CommonResult<Boolean> deletePlatformList(@RequestParam("ids") List<Long> ids) {
        platformService.deletePlatformList(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得科研平台")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:research-platform:query')")
    public CommonResult<ResearchPlatformRespVO> getPlatform(@RequestParam("id") Long id) {
        ResearchPlatformDO platform = platformService.getPlatform(id);
        ResearchPlatformRespVO respVO = BeanUtils.toBean(platform, ResearchPlatformRespVO.class);
        if (respVO != null) {
            respVO.setStaffIds(platformService.getPlatformStaffIds(id));
        }
        return success(respVO);
    }

    @GetMapping("/page")
    @Operation(summary = "获得科研平台分页")
    @PreAuthorize("@ss.hasPermission('bus:research-platform:query')")
    public CommonResult<PageResult<ResearchPlatformRespVO>> getPlatformPage(@Valid ResearchPlatformPageReqVO pageVO) {
        PageResult<ResearchPlatformDO> pageResult = platformService.getPlatformPage(pageVO);
        return success(BeanUtils.toBean(pageResult, ResearchPlatformRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获取科研平台精简列表", description = "用于下拉选择")
    public CommonResult<List<ResearchPlatformSimpleRespVO>> getPlatformSimpleList() {
        List<ResearchPlatformDO> list = platformService.getPlatformSimpleList();
        return success(BeanUtils.toBean(list, ResearchPlatformSimpleRespVO.class));
    }

}
