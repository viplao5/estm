package cn.iocoder.yudao.module.bus.controller.admin.staff;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.bus.controller.admin.staff.vo.*;
import cn.iocoder.yudao.module.bus.dal.dataobject.staff.TechnicalStaffDO;
import cn.iocoder.yudao.module.bus.service.staff.TechnicalStaffService;
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

@Tag(name = "管理后台 - 核心技术人员")
@RestController
@RequestMapping("/bus/technical-staff")
@Validated
public class TechnicalStaffController {

    @Resource
    private TechnicalStaffService staffService;

    @PostMapping("/create")
    @Operation(summary = "创建核心技术人员")
    @PreAuthorize("@ss.hasPermission('bus:technical-staff:create')")
    public CommonResult<Long> createStaff(@Valid @RequestBody TechnicalStaffSaveReqVO createReqVO) {
        return success(staffService.createStaff(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新核心技术人员")
    @PreAuthorize("@ss.hasPermission('bus:technical-staff:update')")
    public CommonResult<Boolean> updateStaff(@Valid @RequestBody TechnicalStaffSaveReqVO updateReqVO) {
        staffService.updateStaff(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除核心技术人员")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:technical-staff:delete')")
    public CommonResult<Boolean> deleteStaff(@RequestParam("id") Long id) {
        staffService.deleteStaff(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "批量删除核心技术人员")
    @Parameter(name = "ids", description = "编号列表", required = true)
    @PreAuthorize("@ss.hasPermission('bus:technical-staff:delete')")
    public CommonResult<Boolean> deleteStaffList(@RequestParam("ids") List<Long> ids) {
        staffService.deleteStaffList(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得核心技术人员")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:technical-staff:query')")
    public CommonResult<TechnicalStaffRespVO> getStaff(@RequestParam("id") Long id) {
        TechnicalStaffDO staff = staffService.getStaff(id);
        return success(BeanUtils.toBean(staff, TechnicalStaffRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得核心技术人员分页")
    @PreAuthorize("@ss.hasPermission('bus:technical-staff:query')")
    public CommonResult<PageResult<TechnicalStaffRespVO>> getStaffPage(@Valid TechnicalStaffPageReqVO pageVO) {
        PageResult<TechnicalStaffDO> pageResult = staffService.getStaffPage(pageVO);
        return success(BeanUtils.toBean(pageResult, TechnicalStaffRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获取核心技术人员精简列表", description = "用于下拉选择")
    public CommonResult<List<TechnicalStaffSimpleRespVO>> getStaffSimpleList() {
        List<TechnicalStaffDO> list = staffService.getStaffSimpleList();
        return success(BeanUtils.toBean(list, TechnicalStaffSimpleRespVO.class));
    }

}
