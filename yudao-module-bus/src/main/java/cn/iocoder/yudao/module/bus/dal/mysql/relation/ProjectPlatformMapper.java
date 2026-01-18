package cn.iocoder.yudao.module.bus.dal.mysql.relation;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.bus.dal.dataobject.relation.ProjectPlatformDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ProjectPlatformMapper extends BaseMapperX<ProjectPlatformDO> {

    default List<ProjectPlatformDO> selectByProjectId(Long projectId) {
        return selectList(new LambdaQueryWrapperX<ProjectPlatformDO>()
                .eq(ProjectPlatformDO::getProjectId, projectId));
    }

    default List<ProjectPlatformDO> selectByPlatformId(Long platformId) {
        return selectList(new LambdaQueryWrapperX<ProjectPlatformDO>()
                .eq(ProjectPlatformDO::getPlatformId, platformId));
    }

    default void deleteByProjectId(Long projectId) {
        delete(new LambdaQueryWrapperX<ProjectPlatformDO>()
                .eq(ProjectPlatformDO::getProjectId, projectId));
    }

}
