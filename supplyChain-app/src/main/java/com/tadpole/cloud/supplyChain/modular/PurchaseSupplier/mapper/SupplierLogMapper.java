package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity.SupplierLog;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.params.SupplierLogParam;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.result.SupplierLogResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 供应商-日志 Mapper接口
 * </p>
 *
 * @author S20190109
 * @since 2023-11-14
 */
public interface SupplierLogMapper extends BaseMapper<SupplierLog> {

    List<SupplierLogResult> listPage(@Param("paramCondition")  SupplierLogParam param);
}
