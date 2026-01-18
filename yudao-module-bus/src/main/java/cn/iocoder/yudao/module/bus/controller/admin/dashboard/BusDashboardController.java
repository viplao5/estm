package cn.iocoder.yudao.module.bus.controller.admin.dashboard;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.encrypt.core.annotation.ApiEncrypt;
import cn.iocoder.yudao.module.bus.controller.admin.dashboard.vo.BusWorkplaceStatsRespVO;
import cn.iocoder.yudao.module.bus.dal.mysql.award.AwardMapper;
import cn.iocoder.yudao.module.bus.dal.mysql.ip.IntellectualPropertyMapper;
import cn.iocoder.yudao.module.bus.dal.mysql.paper.PaperWorkMapper;
import cn.iocoder.yudao.module.bus.dal.mysql.platform.ResearchPlatformMapper;
import cn.iocoder.yudao.module.bus.dal.mysql.product.ProductServiceMapper;
import cn.iocoder.yudao.module.bus.dal.mysql.project.ResearchProjectMapper;
import cn.iocoder.yudao.module.bus.dal.mysql.qualification.QualificationMapper;
import cn.iocoder.yudao.module.bus.dal.mysql.secret.TechnicalSecretMapper;
import cn.iocoder.yudao.module.bus.dal.mysql.staff.TechnicalStaffMapper;
import cn.iocoder.yudao.module.bus.dal.mysql.standard.StandardMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 科技成果工作台")
@RestController
@RequestMapping("/bus/dashboard")
@Validated
public class BusDashboardController {

    @Resource
    private ResearchProjectMapper projectMapper;
    @Resource
    private IntellectualPropertyMapper intellectualPropertyMapper;
    @Resource
    private TechnicalSecretMapper technicalSecretMapper;
    @Resource
    private PaperWorkMapper paperWorkMapper;
    @Resource
    private StandardMapper standardMapper;
    @Resource
    private AwardMapper awardMapper;
    @Resource
    private ProductServiceMapper productServiceMapper; // 产品与服务
    @Resource
    private ResearchPlatformMapper researchPlatformMapper;
    @Resource
    private TechnicalStaffMapper technicalStaffMapper;
    @Resource
    private QualificationMapper qualificationMapper;

    @ApiEncrypt(request = true, response = true)
    @GetMapping("/workplace-stats")
    @Operation(summary = "获得工作台统计信息")
    public CommonResult<BusWorkplaceStatsRespVO> getWorkplaceStats() {
        BusWorkplaceStatsRespVO stats = new BusWorkplaceStatsRespVO();
        
        stats.setProjectCount(projectMapper.selectCount());
        stats.setIpCount(intellectualPropertyMapper.selectCount());
        stats.setSecretCount(technicalSecretMapper.selectCount());
        stats.setPaperCount(paperWorkMapper.selectCount());
        stats.setStandardCount(standardMapper.selectCount());
        stats.setAwardCount(awardMapper.selectCount());
        
        // ProductAchievement 表包含 产品 和 服务，实际可能需要区分，或者统称为 "产品与服务"
        // 假设这里统称为产品与服务
        stats.setProductCount(productServiceMapper.selectCount());
        
        stats.setPlatformCount(researchPlatformMapper.selectCount());
        stats.setStaffCount(technicalStaffMapper.selectCount());
        stats.setQualificationCount(qualificationMapper.selectCount());

        return success(stats);
    }
}
