package cn.iocoder.yudao.module.bus.controller.admin.secret;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.bus.controller.admin.secret.vo.*;
import cn.iocoder.yudao.module.bus.dal.dataobject.secret.TechnicalSecretDO;
import cn.iocoder.yudao.module.bus.service.secret.TechnicalSecretService;
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

@Tag(name = "管理后台 - 技术秘密")
@RestController
@RequestMapping("/bus/technical-secret")
@Validated
public class TechnicalSecretController {

    @Resource
    private TechnicalSecretService secretService;

    @PostMapping("/create")
    @Operation(summary = "创建技术秘密")
    @PreAuthorize("@ss.hasPermission('bus:technical-secret:create')")
    public CommonResult<Long> createSecret(@Valid @RequestBody TechnicalSecretSaveReqVO createReqVO) {
        return success(secretService.createSecret(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新技术秘密")
    @PreAuthorize("@ss.hasPermission('bus:technical-secret:update')")
    public CommonResult<Boolean> updateSecret(@Valid @RequestBody TechnicalSecretSaveReqVO updateReqVO) {
        secretService.updateSecret(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除技术秘密")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:technical-secret:delete')")
    public CommonResult<Boolean> deleteSecret(@RequestParam("id") Long id) {
        secretService.deleteSecret(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "批量删除技术秘密")
    @Parameter(name = "ids", description = "编号列表", required = true)
    @PreAuthorize("@ss.hasPermission('bus:technical-secret:delete')")
    public CommonResult<Boolean> deleteSecretList(@RequestParam("ids") List<Long> ids) {
        secretService.deleteSecretList(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得技术秘密")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:technical-secret:query')")
    public CommonResult<TechnicalSecretRespVO> getSecret(@RequestParam("id") Long id) {
        TechnicalSecretDO secret = secretService.getSecret(id);
        TechnicalSecretRespVO respVO = BeanUtils.toBean(secret, TechnicalSecretRespVO.class);
        if (respVO != null) {
            respVO.setStaffIds(secretService.getSecretStaffIds(id));
            respVO.setProjectIds(secretService.getSecretProjectIds(id));
        }
        return success(respVO);
    }


    @GetMapping("/page")
    @Operation(summary = "获得技术秘密分页")
    @PreAuthorize("@ss.hasPermission('bus:technical-secret:query')")
    public CommonResult<PageResult<TechnicalSecretRespVO>> getSecretPage(@Valid TechnicalSecretPageReqVO pageVO) {
        PageResult<TechnicalSecretDO> pageResult = secretService.getSecretPage(pageVO);
        return success(BeanUtils.toBean(pageResult, TechnicalSecretRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获取技术秘密精简列表", description = "用于下拉选择")
    public CommonResult<List<TechnicalSecretSimpleRespVO>> getSecretSimpleList() {
        List<TechnicalSecretDO> list = secretService.getSecretSimpleList();
        return success(BeanUtils.toBean(list, TechnicalSecretSimpleRespVO.class));
    }

}
