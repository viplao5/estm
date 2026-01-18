package cn.iocoder.yudao.module.bus.service.product;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bus.controller.admin.product.vo.ProductServicePageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.product.vo.ProductServiceSaveReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.product.ProductServiceDO;
import javax.validation.Valid;
import java.util.List;

public interface ProductService {
    Long createProduct(@Valid ProductServiceSaveReqVO createReqVO);
    void updateProduct(@Valid ProductServiceSaveReqVO updateReqVO);
    void deleteProduct(Long id);
    void deleteProductList(List<Long> ids);
    ProductServiceDO getProduct(Long id);
    PageResult<ProductServiceDO> getProductPage(ProductServicePageReqVO pageReqVO);
    List<ProductServiceDO> getProductSimpleList();

    /**
     * 获取产品的技术人员 ID 列表
     * @param productId 产品 ID
     * @return 技术人员 ID 列表
     */
    List<Long> getProductStaffIds(Long productId);

    /**
     * 获取产品的知识产权 ID 列表
     * @param productId 产品 ID
     * @return 知识产权 ID 列表
     */
    List<Long> getProductIpIds(Long productId);

    /**
     * 获取产品的技术秘密 ID 列表
     * @param productId 产品 ID
     * @return 技术秘密 ID 列表
     */
    List<Long> getProductSecretIds(Long productId);
}
