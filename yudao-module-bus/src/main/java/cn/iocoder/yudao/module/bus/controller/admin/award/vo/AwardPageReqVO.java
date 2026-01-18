package cn.iocoder.yudao.module.bus.controller.admin.award.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 科技奖励分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AwardPageReqVO extends PageParam {
    @Schema(description = "科技奖励名称")
    private String name;
    @Schema(description = "奖励级别")
    private String level;
    @Schema(description = "奖励等级")
    private String grade;
    @Schema(description = "关联研发项目ID")
    private Long projectId;

    @Schema(description = "关联人员ID")
    private Long staffId;
}
