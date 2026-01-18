package cn.iocoder.yudao.module.bus.service.product;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.bus.controller.admin.product.vo.ProductServicePageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.product.vo.ProductServiceSaveReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.product.ProductServiceDO;
import cn.iocoder.yudao.module.bus.dal.dataobject.relation.AchievementStaffDO;
import cn.iocoder.yudao.module.bus.dal.dataobject.relation.ProductAchievementDO;
import cn.iocoder.yudao.module.bus.dal.mysql.product.ProductServiceMapper;
import cn.iocoder.yudao.module.bus.dal.mysql.relation.AchievementStaffMapper;
import cn.iocoder.yudao.module.bus.dal.mysql.relation.ProductAchievementMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Resource;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.bus.enums.ErrorCodeConstants.PRODUCT_SERVICE_NOT_EXISTS;

@Service
@Validated
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductServiceMapper productMapper;
    @Resource
    private AchievementStaffMapper achievementStaffMapper;
    @Resource
    private ProductAchievementMapper productAchievementMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createProduct(ProductServiceSaveReqVO createReqVO) {
        ProductServiceDO product = BeanUtils.toBean(createReqVO, ProductServiceDO.class);
        productMapper.insert(product);

        // 保存关联关系
        saveProductStaffs(product.getId(), createReqVO.getStaffIds());
        saveProductAchievements(product.getId(), createReqVO.getIpIds(), "IP");
        saveProductAchievements(product.getId(), createReqVO.getSecretIds(), "SECRET");

        return product.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProduct(ProductServiceSaveReqVO updateReqVO) {
        validateProductExists(updateReqVO.getId());
        ProductServiceDO updateObj = BeanUtils.toBean(updateReqVO, ProductServiceDO.class);
        productMapper.updateById(updateObj);

        // 更新人员关联
        achievementStaffMapper.deleteByAchievementIdAndType(updateReqVO.getId(), "PRODUCT");
        saveProductStaffs(updateReqVO.getId(), updateReqVO.getStaffIds());

        // 更新知识产权关联
        productAchievementMapper.deleteByProductIdAndType(updateReqVO.getId(), "IP");
        saveProductAchievements(updateReqVO.getId(), updateReqVO.getIpIds(), "IP");

        // 更新技术秘密关联
        productAchievementMapper.deleteByProductIdAndType(updateReqVO.getId(), "SECRET");
        saveProductAchievements(updateReqVO.getId(), updateReqVO.getSecretIds(), "SECRET");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProduct(Long id) {
        validateProductExists(id);
        productMapper.deleteById(id);

        // 删除关联关系
        achievementStaffMapper.deleteByAchievementIdAndType(id, "PRODUCT");
        productAchievementMapper.deleteByProductId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProductList(List<Long> ids) {
        productMapper.deleteBatchIds(ids);

        // 批量删除关联关系
        ids.forEach(id -> {
            achievementStaffMapper.deleteByAchievementIdAndType(id, "PRODUCT");
            productAchievementMapper.deleteByProductId(id);
        });
    }

    private void saveProductStaffs(Long productId, List<Long> staffIds) {
        if (cn.hutool.core.collection.CollUtil.isEmpty(staffIds)) {
            return;
        }
        List<AchievementStaffDO> list = cn.hutool.core.collection.CollUtil.map(staffIds, staffId -> {
            AchievementStaffDO staffDO = new AchievementStaffDO();
            staffDO.setStaffId(staffId);
            staffDO.setAchievementId(productId);
            staffDO.setAchievementType("PRODUCT");
            return staffDO;
        }, true);
        achievementStaffMapper.insertBatch(list);
    }

    private void saveProductAchievements(Long productId, List<Long> achievementIds, String achievementType) {
        if (cn.hutool.core.collection.CollUtil.isEmpty(achievementIds)) {
            return;
        }
        List<ProductAchievementDO> list = cn.hutool.core.collection.CollUtil.map(achievementIds, achievementId -> {
            ProductAchievementDO achievementDO = new ProductAchievementDO();
            achievementDO.setProductId(productId);
            achievementDO.setAchievementId(achievementId);
            achievementDO.setAchievementType(achievementType);
            return achievementDO;
        }, true);
        productAchievementMapper.insertBatch(list);
    }

    @Override
    public List<Long> getProductStaffIds(Long productId) {
        return cn.hutool.core.collection.CollUtil.map(achievementStaffMapper.selectByAchievementIdAndType(productId, "PRODUCT"), 
                AchievementStaffDO::getStaffId, true);
    }

    @Override
    public List<Long> getProductIpIds(Long productId) {
        return cn.hutool.core.collection.CollUtil.map(productAchievementMapper.selectListByProductIdAndType(productId, "IP"), 
                ProductAchievementDO::getAchievementId, true);
    }

    @Override
    public List<Long> getProductSecretIds(Long productId) {
        return cn.hutool.core.collection.CollUtil.map(productAchievementMapper.selectListByProductIdAndType(productId, "SECRET"), 
                ProductAchievementDO::getAchievementId, true);
    }

    private void validateProductExists(Long id) {
        if (productMapper.selectById(id) == null) {
            throw exception(PRODUCT_SERVICE_NOT_EXISTS);
        }
    }

    @Override
    public ProductServiceDO getProduct(Long id) {
        return productMapper.selectById(id);
    }

    @Override
    public PageResult<ProductServiceDO> getProductPage(ProductServicePageReqVO pageReqVO) {
        // 如果有关联IP ID，先查询关联的产品ID
        if (pageReqVO.getIpId() != null) {
            List<ProductAchievementDO> relations = productAchievementMapper.selectListByAchievementIdAndType(pageReqVO.getIpId(), "IP");
            if (cn.hutool.core.collection.CollUtil.isEmpty(relations)) {
                return PageResult.empty();
            }
            pageReqVO.setIds(cn.hutool.core.collection.CollUtil.map(relations, ProductAchievementDO::getProductId, true));
        }
        // 如果有关联技术秘密 ID，先查询关联的产品ID
        if (pageReqVO.getSecretId() != null) {
            List<ProductAchievementDO> relations = productAchievementMapper.selectListByAchievementIdAndType(pageReqVO.getSecretId(), "SECRET");
            if (cn.hutool.core.collection.CollUtil.isEmpty(relations)) {
                return PageResult.empty();
            }
            // 如果已有ids（比如同时传了ipId），则取交集，或者根据业务需求处理。这里简单覆盖或合并？
            // 通常不会同时传，这里做个简单处理：如果已有ID列表，则取交集
            List<Long> secretProductIds = cn.hutool.core.collection.CollUtil.map(relations, ProductAchievementDO::getProductId, true);
            if (pageReqVO.getIds() != null) {
                pageReqVO.setIds(cn.hutool.core.collection.CollUtil.intersection(pageReqVO.getIds(), secretProductIds));
                if (cn.hutool.core.collection.CollUtil.isEmpty(pageReqVO.getIds())) {
                     return PageResult.empty();
                }
            } else {
                pageReqVO.setIds(secretProductIds);
            }
        }
        return productMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ProductServiceDO> getProductSimpleList() {
        return productMapper.selectList();
    }
}
