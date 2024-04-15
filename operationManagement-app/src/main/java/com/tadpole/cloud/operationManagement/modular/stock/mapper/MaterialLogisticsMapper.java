package com.tadpole.cloud.operationManagement.modular.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.stock.entity.EntMaterialLogistics;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SysMaterialParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SysMaterialResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物料运输方式 Mapper 接口
 *
 * @author gal
 * @since 2021-07-27
 */
public interface MaterialLogisticsMapper extends BaseMapper<EntMaterialLogistics> {

  Page<SysMaterialResult> customPageList(@Param("page") Page page, @Param("paramCondition") SysMaterialParam paramCondition);

  void insertOrUpdateBatchByCustom(@Param("paramCondition") SysMaterialParam paramCondition);

  void analysis(@Param("paramCondition") SysMaterialParam paramCondition);

  void deleteBatch (@Param("list") List<String> list);

}
