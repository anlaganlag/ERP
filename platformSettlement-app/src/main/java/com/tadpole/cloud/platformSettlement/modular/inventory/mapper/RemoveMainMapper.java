package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.DisposeRemoveDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.RemoveMain;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemoveMainParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.RemoveMainResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Amazon销毁移除主表 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
public interface RemoveMainMapper extends BaseMapper<RemoveMain> {

  Page<RemoveMainResult> customPageList(@Param("page") Page page, @Param("paramCondition") RemoveMainParam paramCondition);

  String getQuantity(@Param("paramCondition") RemoveMainParam paramCondition);

  String getMaterial(@Param("paramCondition") RemoveMainParam paramCondition);

  List<RemoveMainResult> getListHeader(@Param("paramCondition") RemoveMainParam paramCondition);

  List<DisposeRemoveDetail> getDetailList(@Param("paramCondition") RemoveMainResult paramCondition);

  void updateSrcDetailList(@Param("paramCondition") RemoveMainResult paramCondition);

  void updateDetailList();

  void updateFileDetailList();

  List<RemoveMainParam> orgList();

  Boolean verifyUpdateBatch(@Param("paramCondition") RemoveMainParam paramCondition);

  Boolean orgBatch(@Param("verifyList") List<RemoveMainParam> verifyList);

  void rejectBatch(@Param("paramCondition") RemoveMainParam paramCondition);
}
