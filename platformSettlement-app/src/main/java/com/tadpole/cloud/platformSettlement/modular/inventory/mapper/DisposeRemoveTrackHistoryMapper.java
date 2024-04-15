package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.DisposeRemoveTrackHistory;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.DisposeRemoveTrackHistoryParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.DisposeRemoveTrackHistoryResult;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.DisposeRemoveTrackTotalResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 销毁移除跟踪历史表 Mapper 接口
 * </p>
 *
 * @author ty
 * @since 2023-02-28
 */
public interface DisposeRemoveTrackHistoryMapper extends BaseMapper<DisposeRemoveTrackHistory> {

    /**
     * 销毁移除跟踪历史表、导出
     * @param page
     * @param paramCondition
     * @return
     */
    Page<DisposeRemoveTrackHistoryResult> trackHistoryList(@Param("page") Page page, @Param("paramCondition") DisposeRemoveTrackHistoryParam paramCondition);

    /**
     * 销毁移除跟踪历史表汇总数量
     * @param paramCondition
     * @return
     */
    DisposeRemoveTrackTotalResult getTrackHistoryQuantity(@Param("paramCondition") DisposeRemoveTrackHistoryParam paramCondition);

    /**
     * 生成销毁移除跟踪历史表数据
     * @param dataMonth
     * @return
     */
    void generateHistoryTrack(@Param("dataMonth") String dataMonth);
}
