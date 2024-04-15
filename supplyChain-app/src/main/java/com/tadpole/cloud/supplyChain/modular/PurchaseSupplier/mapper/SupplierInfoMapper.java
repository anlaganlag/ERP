package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity.SupplierInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.params.SupplierInfoParam;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.result.SupplierInfoResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 供应商-供应商信息 Mapper接口
 * </p>
 *
 * @author S20190109
 * @since 2023-11-14
 */
public interface SupplierInfoMapper extends BaseMapper<SupplierInfo> {

    Page<SupplierInfoResult> profileList(@Param("page") Page page, @Param("paramCondition") SupplierInfoParam param);

    List<SupplierInfoResult> supplierList(@Param("paramCondition") SupplierInfoParam param);
}
