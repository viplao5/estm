package cn.iocoder.yudao.module.bus.dal.mysql.product;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.bus.controller.admin.product.vo.ProductServicePageReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.product.ProductServiceDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductServiceMapper extends BaseMapperX<ProductServiceDO> {
    default PageResult<ProductServiceDO> selectPage(ProductServicePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProductServiceDO>()
                .likeIfPresent(ProductServiceDO::getName, reqVO.getName())
                .eqIfPresent(ProductServiceDO::getCategory, reqVO.getCategory())
                .eqIfPresent(ProductServiceDO::getStatus, reqVO.getStatus())
                .likeIfPresent(ProductServiceDO::getLeader, reqVO.getLeader())
                .orderByDesc(ProductServiceDO::getId));
    }
}
