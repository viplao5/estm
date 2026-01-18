package cn.iocoder.yudao.module.bus.dal.mysql.relation;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.bus.dal.dataobject.relation.ProjectAchievementDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectAchievementMapper extends BaseMapperX<ProjectAchievementDO> {

    default List<ProjectAchievementDO> selectByAchievementIdAndType(Long achievementId, String achievementType) {
        return selectList(new LambdaQueryWrapper<ProjectAchievementDO>()
                .eq(ProjectAchievementDO::getAchievementId, achievementId)
                .eq(ProjectAchievementDO::getAchievementType, achievementType));
    }

    default void deleteByAchievementIdAndType(Long achievementId, String achievementType) {
        delete(new LambdaQueryWrapper<ProjectAchievementDO>()
                .eq(ProjectAchievementDO::getAchievementId, achievementId)
                .eq(ProjectAchievementDO::getAchievementType, achievementType));
    }

}
