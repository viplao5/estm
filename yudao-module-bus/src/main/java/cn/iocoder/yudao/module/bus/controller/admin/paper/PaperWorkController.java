package cn.iocoder.yudao.module.bus.controller.admin.paper;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.bus.controller.admin.paper.vo.*;
import cn.iocoder.yudao.module.bus.dal.dataobject.paper.PaperWorkDO;
import cn.iocoder.yudao.module.bus.service.paper.PaperWorkService;
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

@Tag(name = "管理后台 - 论文与著作")
@RestController
@RequestMapping("/bus/paper-work")
@Validated
public class PaperWorkController {

    @Resource
    private PaperWorkService paperService;

    @PostMapping("/create")
    @Operation(summary = "创建论文与著作")
    @PreAuthorize("@ss.hasPermission('bus:paper-work:create')")
    public CommonResult<Long> createPaper(@Valid @RequestBody PaperWorkSaveReqVO createReqVO) {
        return success(paperService.createPaper(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新论文与著作")
    @PreAuthorize("@ss.hasPermission('bus:paper-work:update')")
    public CommonResult<Boolean> updatePaper(@Valid @RequestBody PaperWorkSaveReqVO updateReqVO) {
        paperService.updatePaper(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除论文与著作")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:paper-work:delete')")
    public CommonResult<Boolean> deletePaper(@RequestParam("id") Long id) {
        paperService.deletePaper(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "批量删除论文与著作")
    @Parameter(name = "ids", description = "编号列表", required = true)
    @PreAuthorize("@ss.hasPermission('bus:paper-work:delete')")
    public CommonResult<Boolean> deletePaperList(@RequestParam("ids") List<Long> ids) {
        paperService.deletePaperList(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得论文与著作")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:paper-work:query')")
    public CommonResult<PaperWorkRespVO> getPaper(@RequestParam("id") Long id) {
        PaperWorkDO paper = paperService.getPaper(id);
        PaperWorkRespVO respVO = BeanUtils.toBean(paper, PaperWorkRespVO.class);
        if (respVO != null) {
            respVO.setAuthorIds(paperService.getPaperAuthorIds(id));
            respVO.setProjectIds(paperService.getPaperProjectIds(id));
        }
        return success(respVO);
    }


    @GetMapping("/page")
    @Operation(summary = "获得论文与著作分页")
    @PreAuthorize("@ss.hasPermission('bus:paper-work:query')")
    public CommonResult<PageResult<PaperWorkRespVO>> getPaperPage(@Valid PaperWorkPageReqVO pageVO) {
        PageResult<PaperWorkDO> pageResult = paperService.getPaperPage(pageVO);
        return success(BeanUtils.toBean(pageResult, PaperWorkRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获取论文与著作精简列表", description = "用于下拉选择")
    public CommonResult<List<PaperWorkSimpleRespVO>> getPaperSimpleList() {
        List<PaperWorkDO> list = paperService.getPaperSimpleList();
        return success(BeanUtils.toBean(list, PaperWorkSimpleRespVO.class));
    }

}
