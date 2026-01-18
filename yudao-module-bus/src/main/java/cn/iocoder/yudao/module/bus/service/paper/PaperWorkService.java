package cn.iocoder.yudao.module.bus.service.paper;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bus.controller.admin.paper.vo.PaperWorkPageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.paper.vo.PaperWorkSaveReqVO;
import cn.iocoder.yudao.module.bus.dal.dataobject.paper.PaperWorkDO;

import javax.validation.Valid;
import java.util.List;

public interface PaperWorkService {
    Long createPaper(@Valid PaperWorkSaveReqVO createReqVO);
    void updatePaper(@Valid PaperWorkSaveReqVO updateReqVO);
    void deletePaper(Long id);
    void deletePaperList(List<Long> ids);
    PaperWorkDO getPaper(Long id);
    PageResult<PaperWorkDO> getPaperPage(PaperWorkPageReqVO pageReqVO);
    List<PaperWorkDO> getPaperSimpleList();

    /**
     * 获取论文关联的作者ID列表
     * @param paperId 论文ID
     * @return 作者ID列表
     */
    List<Long> getPaperAuthorIds(Long paperId);

    /**
     * 获取论文专著关联的研发项目ID列表
     * @param paperId 论文专著ID
     * @return 研发项目ID列表
     */
    List<Long> getPaperProjectIds(Long paperId);
}
