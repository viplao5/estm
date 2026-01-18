package cn.iocoder.yudao.module.bus.controller.admin.award;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.encrypt.core.annotation.ApiEncrypt;
import cn.iocoder.yudao.module.bus.controller.admin.award.vo.*;
import cn.iocoder.yudao.module.bus.dal.dataobject.award.AwardDO;
import cn.iocoder.yudao.module.bus.service.award.AwardService;
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

@Tag(name = "管理后台 - 科技奖励")
@RestController
@RequestMapping("/bus/award")
@Validated
public class AwardController {

    @Resource
    private AwardService awardService;

    @PostMapping("/create")
    @Operation(summary = "创建科技奖励")
    @PreAuthorize("@ss.hasPermission('bus:award:create')")
    public CommonResult<Long> createAward(@Valid @RequestBody AwardSaveReqVO createReqVO) {
        return success(awardService.createAward(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新科技奖励")
    @PreAuthorize("@ss.hasPermission('bus:award:update')")
    public CommonResult<Boolean> updateAward(@Valid @RequestBody AwardSaveReqVO updateReqVO) {
        awardService.updateAward(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除科技奖励")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:award:delete')")
    public CommonResult<Boolean> deleteAward(@RequestParam("id") Long id) {
        awardService.deleteAward(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "批量删除科技奖励")
    @Parameter(name = "ids", description = "编号列表", required = true)
    @PreAuthorize("@ss.hasPermission('bus:award:delete')")
    public CommonResult<Boolean> deleteAwardList(@RequestParam("ids") List<Long> ids) {
        awardService.deleteAwardList(ids);
        return success(true);
    }

    @ApiEncrypt(request = true, response = true)
    @GetMapping("/get")
    @Operation(summary = "获得科技奖励")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:award:query')")
    public CommonResult<AwardRespVO> getAward(@RequestParam("id") Long id) {
        AwardDO award = awardService.getAward(id);
        AwardRespVO respVO = BeanUtils.toBean(award, AwardRespVO.class);
        if (respVO != null) {
            respVO.setStaffIds(awardService.getAwardStaffIds(id));
            respVO.setProjectIds(awardService.getAwardProjectIds(id));
        }
        return success(respVO);
    }


    @ApiEncrypt(request = true, response = true)
    @GetMapping("/page")
    @Operation(summary = "获得科技奖励分页")
    @PreAuthorize("@ss.hasPermission('bus:award:query')")
    public CommonResult<PageResult<AwardRespVO>> getAwardPage(@Valid AwardPageReqVO pageVO) {
        PageResult<AwardDO> pageResult = awardService.getAwardPage(pageVO);
        return success(BeanUtils.toBean(pageResult, AwardRespVO.class));
    }

    @ApiEncrypt(request = true, response = true)
    @GetMapping("/simple-list")
    @Operation(summary = "获取科技奖励精简列表", description = "用于下拉选择")
    public CommonResult<List<AwardSimpleRespVO>> getAwardSimpleList() {
        List<AwardDO> list = awardService.getAwardSimpleList();
        return success(BeanUtils.toBean(list, AwardSimpleRespVO.class));
    }
}
