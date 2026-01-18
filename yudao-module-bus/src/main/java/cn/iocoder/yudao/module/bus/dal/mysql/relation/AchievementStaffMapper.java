package cn.iocoder.yudao.module.bus.dal.mysql.relation;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.bus.dal.dataobject.relation.AchievementStaffDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface AchievementStaffMapper extends BaseMapperX<AchievementStaffDO> {

    default List<AchievementStaffDO> selectByAchievementIdAndType(Long achievementId, String achievementType) {
        return selectList(new LambdaQueryWrapperX<AchievementStaffDO>()
                .eq(AchievementStaffDO::getAchievementId, achievementId)
                .eq(AchievementStaffDO::getAchievementType, achievementType));
    }

    default List<AchievementStaffDO> selectByStaffId(Long staffId) {
        return selectList(new LambdaQueryWrapperX<AchievementStaffDO>()
                .eq(AchievementStaffDO::getStaffId, staffId));
    }

    default void deleteByAchievementIdAndType(Long achievementId, String achievementType) {
        delete(new LambdaQueryWrapperX<AchievementStaffDO>()
                .eq(AchievementStaffDO::getAchievementId, achievementId)
                .eq(AchievementStaffDO::getAchievementType, achievementType));
    }

}
