package cn.iocoder.yudao.module.bus.controller.admin.product;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.bus.controller.admin.product.vo.*;
import cn.iocoder.yudao.module.bus.dal.dataobject.product.ProductServiceDO;
import cn.iocoder.yudao.module.bus.service.product.ProductService;
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

@Tag(name = "管理后台 - 产品与服务")
@RestController
@RequestMapping("/bus/product-service")
@Validated
public class ProductServiceController {

    @Resource
    private ProductService productService;

    @PostMapping("/create")
    @Operation(summary = "创建产品与服务")
    @PreAuthorize("@ss.hasPermission('bus:product-service:create')")
    public CommonResult<Long> createProduct(@Valid @RequestBody ProductServiceSaveReqVO createReqVO) {
        return success(productService.createProduct(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新产品与服务")
    @PreAuthorize("@ss.hasPermission('bus:product-service:update')")
    public CommonResult<Boolean> updateProduct(@Valid @RequestBody ProductServiceSaveReqVO updateReqVO) {
        productService.updateProduct(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除产品与服务")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:product-service:delete')")
    public CommonResult<Boolean> deleteProduct(@RequestParam("id") Long id) {
        productService.deleteProduct(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "批量删除产品与服务")
    @Parameter(name = "ids", description = "编号列表", required = true)
    @PreAuthorize("@ss.hasPermission('bus:product-service:delete')")
    public CommonResult<Boolean> deleteProductList(@RequestParam("ids") List<Long> ids) {
        productService.deleteProductList(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得产品与服务")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:product-service:query')")
    public CommonResult<ProductServiceRespVO> getProduct(@RequestParam("id") Long id) {
        ProductServiceDO product = productService.getProduct(id);
        ProductServiceRespVO respVO = BeanUtils.toBean(product, ProductServiceRespVO.class);
        if (respVO != null) {
            respVO.setIpIds(productService.getProductIpIds(id));
            respVO.setSecretIds(productService.getProductSecretIds(id));
            respVO.setStaffIds(productService.getProductStaffIds(id));
        }
        return success(respVO);
    }

    @GetMapping("/page")
    @Operation(summary = "获得产品与服务分页")
    @PreAuthorize("@ss.hasPermission('bus:product-service:query')")
    public CommonResult<PageResult<ProductServiceRespVO>> getProductPage(@Valid ProductServicePageReqVO pageVO) {
        PageResult<ProductServiceDO> pageResult = productService.getProductPage(pageVO);
        return success(BeanUtils.toBean(pageResult, ProductServiceRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获取产品与服务精简列表", description = "用于下拉选择")
    public CommonResult<List<ProductServiceSimpleRespVO>> getProductSimpleList() {
        List<ProductServiceDO> list = productService.getProductSimpleList();
        return success(BeanUtils.toBean(list, ProductServiceSimpleRespVO.class));
    }
}
