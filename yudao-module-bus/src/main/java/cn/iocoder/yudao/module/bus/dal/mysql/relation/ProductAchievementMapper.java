package cn.iocoder.yudao.module.bus.dal.mysql.relation;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.bus.dal.dataobject.relation.ProductAchievementDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ProductAchievementMapper extends BaseMapperX<ProductAchievementDO> {

    default List<ProductAchievementDO> selectByProductId(Long productId) {
        return selectList(new LambdaQueryWrapperX<ProductAchievementDO>()
                .eq(ProductAchievementDO::getProductId, productId));
    }

    default void deleteByProductId(Long productId) {
        delete(new LambdaQueryWrapperX<ProductAchievementDO>()
                .eq(ProductAchievementDO::getProductId, productId));
    }

    default List<ProductAchievementDO> selectListByProductIdAndType(Long productId, String type) {
        return selectList(new LambdaQueryWrapperX<ProductAchievementDO>()
                .eq(ProductAchievementDO::getProductId, productId)
                .eq(ProductAchievementDO::getAchievementType, type));
    }

    default void deleteByProductIdAndType(Long productId, String type) {
        delete(new LambdaQueryWrapperX<ProductAchievementDO>()
                .eq(ProductAchievementDO::getProductId, productId)
                .eq(ProductAchievementDO::getAchievementType, type));
    }

    default List<ProductAchievementDO> selectListByAchievementIdAndType(Long achievementId, String type) {
        return selectList(new LambdaQueryWrapperX<ProductAchievementDO>()
                .eq(ProductAchievementDO::getAchievementId, achievementId)
                .eq(ProductAchievementDO::getAchievementType, type));
    }

}
