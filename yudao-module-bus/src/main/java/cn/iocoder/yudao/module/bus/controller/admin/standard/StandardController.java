package cn.iocoder.yudao.module.bus.controller.admin.standard;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.encrypt.core.annotation.ApiEncrypt;
import cn.iocoder.yudao.module.bus.controller.admin.standard.vo.*;
import cn.iocoder.yudao.module.bus.dal.dataobject.standard.StandardDO;
import cn.iocoder.yudao.module.bus.service.standard.StandardService;
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

@Tag(name = "管理后台 - 标准")
@RestController
@RequestMapping("/bus/standard")
@Validated
public class StandardController {

    @Resource
    private StandardService standardService;


    @PostMapping("/create")
    @Operation(summary = "创建标准")
    @PreAuthorize("@ss.hasPermission('bus:standard:create')")
    public CommonResult<Long> createStandard(@Valid @RequestBody StandardSaveReqVO createReqVO) {
        return success(standardService.createStandard(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新标准")
    @PreAuthorize("@ss.hasPermission('bus:standard:update')")
    public CommonResult<Boolean> updateStandard(@Valid @RequestBody StandardSaveReqVO updateReqVO) {
        standardService.updateStandard(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除标准")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:standard:delete')")
    public CommonResult<Boolean> deleteStandard(@RequestParam("id") Long id) {
        standardService.deleteStandard(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "批量删除标准")
    @Parameter(name = "ids", description = "编号列表", required = true)
    @PreAuthorize("@ss.hasPermission('bus:standard:delete')")
    public CommonResult<Boolean> deleteStandardList(@RequestParam("ids") List<Long> ids) {
        standardService.deleteStandardList(ids);
        return success(true);
    }

    @ApiEncrypt(request = true, response = true)
    @GetMapping("/get")
    @Operation(summary = "获得标准")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:standard:query')")
    public CommonResult<StandardRespVO> getStandard(@RequestParam("id") Long id) {
        StandardDO standard = standardService.getStandard(id);
        StandardRespVO respVO = BeanUtils.toBean(standard, StandardRespVO.class);
        if (respVO != null) {
            respVO.setStaffIds(standardService.getStandardStaffIds(id));
            respVO.setProjectIds(standardService.getStandardProjectIds(id));
        }
        return success(respVO);
    }


    @ApiEncrypt(request = true, response = true)
    @GetMapping("/page")
    @Operation(summary = "获得标准分页")
    @PreAuthorize("@ss.hasPermission('bus:standard:query')")
    public CommonResult<PageResult<StandardRespVO>> getStandardPage(@Valid StandardPageReqVO pageVO) {
        PageResult<StandardDO> pageResult = standardService.getStandardPage(pageVO);
        return success(BeanUtils.toBean(pageResult, StandardRespVO.class));
    }

    @ApiEncrypt(request = true, response = true)
    @GetMapping("/simple-list")
    @Operation(summary = "获取标准精简列表", description = "用于下拉选择")
    public CommonResult<List<StandardSimpleRespVO>> getStandardSimpleList() {
        List<StandardDO> list = standardService.getStandardSimpleList();
        return success(BeanUtils.toBean(list, StandardSimpleRespVO.class));
    }
}
