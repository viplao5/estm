package cn.iocoder.yudao.module.bus.controller.admin.qualification;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.bus.controller.admin.qualification.vo.*;
import cn.iocoder.yudao.module.bus.dal.dataobject.qualification.QualificationDO;
import cn.iocoder.yudao.module.bus.service.qualification.QualificationService;
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

@Tag(name = "管理后台 - 资质")
@RestController
@RequestMapping("/bus/qualification")
@Validated
public class QualificationController {

    @Resource
    private QualificationService qualificationService;

    @PostMapping("/create")
    @Operation(summary = "创建资质")
    @PreAuthorize("@ss.hasPermission('bus:qualification:create')")
    public CommonResult<Long> createQualification(@Valid @RequestBody QualificationSaveReqVO createReqVO) {
        return success(qualificationService.createQualification(createReqVO));
    }

    @PostMapping("/create-batch")
    @Operation(summary = "批量创建资质")
    @PreAuthorize("@ss.hasPermission('bus:qualification:create')")
    public CommonResult<Boolean> createQualificationBatch(@Valid @RequestBody List<QualificationSaveReqVO> createReqVOs) {
        qualificationService.createQualificationBatch(createReqVOs);
        return success(true);
    }

    @PutMapping("/update")
    @Operation(summary = "更新资质")
    @PreAuthorize("@ss.hasPermission('bus:qualification:update')")
    public CommonResult<Boolean> updateQualification(@Valid @RequestBody QualificationSaveReqVO updateReqVO) {
        qualificationService.updateQualification(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除资质")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:qualification:delete')")
    public CommonResult<Boolean> deleteQualification(@RequestParam("id") Long id) {
        qualificationService.deleteQualification(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "批量删除资质")
    @Parameter(name = "ids", description = "编号列表", required = true)
    @PreAuthorize("@ss.hasPermission('bus:qualification:delete')")
    public CommonResult<Boolean> deleteQualificationList(@RequestParam("ids") List<Long> ids) {
        qualificationService.deleteQualificationList(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得资质")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:qualification:query')")
    public CommonResult<QualificationRespVO> getQualification(@RequestParam("id") Long id) {
        QualificationDO qualification = qualificationService.getQualification(id);
        return success(BeanUtils.toBean(qualification, QualificationRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得资质分页")
    @PreAuthorize("@ss.hasPermission('bus:qualification:query')")
    public CommonResult<PageResult<QualificationRespVO>> getQualificationPage(@Valid QualificationPageReqVO pageVO) {
        PageResult<QualificationDO> pageResult = qualificationService.getQualificationPage(pageVO);
        return success(BeanUtils.toBean(pageResult, QualificationRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获取资质精简列表", description = "用于下拉选择")
    public CommonResult<List<QualificationSimpleRespVO>> getQualificationSimpleList() {
        List<QualificationDO> list = qualificationService.getQualificationSimpleList();
        return success(BeanUtils.toBean(list, QualificationSimpleRespVO.class));
    }
}
