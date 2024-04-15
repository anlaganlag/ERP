package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.DisposeRemoveTrackHistory;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.DisposeRemoveTrackHistoryParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.DisposeRemoveTrackHistoryResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 销毁移除跟踪历史表 服务类
 * </p>
 *
 * @author ty
 * @since 2023-02-28
 */
public interface IDisposeRemoveTrackHistoryService extends IService<DisposeRemoveTrackHistory> {

    /**
     * 销毁移除跟踪历史表
     * @param param
     * @return
     */
    PageResult<DisposeRemoveTrackHistoryResult> trackHistoryList(DisposeRemoveTrackHistoryParam param);

    /**
     * 销毁移除跟踪历史表导出
     * @param param
     * @return
     */
    List<DisposeRemoveTrackHistoryResult> trackHistoryExport(DisposeRemoveTrackHistoryParam param);

    /**
     * 销毁移除跟踪历史表汇总数量
     * @param param
     * @return
     */
    ResponseData getTrackHistoryQuantity(DisposeRemoveTrackHistoryParam param);

    /**
     * 生成销毁移除跟踪历史表数据
     * @return
     */
    ResponseData generateHistoryTrack();
}
