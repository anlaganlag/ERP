package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.DisposeRemove;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.DisposeRemoveDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.DisposeRemoveParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.DisposeRemoveResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 销毁移除列表 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2021-11-22
 */
public interface DisposeRemoveMapper extends BaseMapper<DisposeRemove> {

  Page<DisposeRemoveResult> customPageList(
      @Param("page") Page page, @Param("paramCondition") DisposeRemoveParam paramCondition);


  List<DisposeRemoveResult> emailList();



  String getQuantity(@Param("paramCondition") DisposeRemoveParam param);

  void rejectBatch(@Param("paramCondition") DisposeRemoveParam paramCondition);


  void syncReportReject(@Param("paramCondition") DisposeRemoveParam paramCondition);


  void syncReportBatchReject(@Param("paramCondition") List<DisposeRemoveParam> paramCondition);




  List<DisposeRemoveDetail> getSyncList(@Param("paramCondition") DisposeRemoveParam param);


  void updateSyncStatus();

  List<DisposeRemoveParam> normalList();

    void updateCanSyncNormal();
}
