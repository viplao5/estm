package cn.iocoder.yudao.module.bus.controller.admin.ip;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.bus.controller.admin.ip.vo.*;
import cn.iocoder.yudao.module.bus.dal.dataobject.ip.IntellectualPropertyDO;
import cn.iocoder.yudao.module.bus.service.ip.IntellectualPropertyService;
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

@Tag(name = "管理后台 - 知识产权")
@RestController
@RequestMapping("/bus/intellectual-property")
@Validated
public class IntellectualPropertyController {

    @Resource
    private IntellectualPropertyService ipService;

    @PostMapping("/create")
    @Operation(summary = "创建知识产权")
    @PreAuthorize("@ss.hasPermission('bus:intellectual-property:create')")
    public CommonResult<Long> createIP(@Valid @RequestBody IntellectualPropertySaveReqVO createReqVO) {
        return success(ipService.createIP(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新知识产权")
    @PreAuthorize("@ss.hasPermission('bus:intellectual-property:update')")
    public CommonResult<Boolean> updateIP(@Valid @RequestBody IntellectualPropertySaveReqVO updateReqVO) {
        ipService.updateIP(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除知识产权")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:intellectual-property:delete')")
    public CommonResult<Boolean> deleteIP(@RequestParam("id") Long id) {
        ipService.deleteIP(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "批量删除知识产权")
    @Parameter(name = "ids", description = "编号列表", required = true)
    @PreAuthorize("@ss.hasPermission('bus:intellectual-property:delete')")
    public CommonResult<Boolean> deleteIPList(@RequestParam("ids") List<Long> ids) {
        ipService.deleteIPList(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得知识产权")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:intellectual-property:query')")
    public CommonResult<IntellectualPropertyRespVO> getIP(@RequestParam("id") Long id) {
        IntellectualPropertyDO ip = ipService.getIP(id);
        IntellectualPropertyRespVO respVO = BeanUtils.toBean(ip, IntellectualPropertyRespVO.class);
        if (respVO != null) {
            respVO.setInventorIds(ipService.getIPInventorIds(id));
            respVO.setProjectIds(ipService.getIPProjectIds(id));
        }
        return success(respVO);
    }


    @GetMapping("/page")
    @Operation(summary = "获得知识产权分页")
    @PreAuthorize("@ss.hasPermission('bus:intellectual-property:query')")
    public CommonResult<PageResult<IntellectualPropertyRespVO>> getIPPage(@Valid IntellectualPropertyPageReqVO pageVO) {
        PageResult<IntellectualPropertyDO> pageResult = ipService.getIPPage(pageVO);
        return success(BeanUtils.toBean(pageResult, IntellectualPropertyRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获取知识产权精简列表", description = "用于下拉选择")
    public CommonResult<List<IntellectualPropertySimpleRespVO>> getIPSimpleList() {
        List<IntellectualPropertyDO> list = ipService.getIPSimpleList();
        return success(BeanUtils.toBean(list, IntellectualPropertySimpleRespVO.class));
    }

}
