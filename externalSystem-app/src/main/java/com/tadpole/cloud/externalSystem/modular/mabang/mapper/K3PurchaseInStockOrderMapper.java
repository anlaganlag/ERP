package com.tadpole.cloud.externalSystem.modular.mabang.mapper;

import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3PurchaseInStockOrder;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3PurchaseInStockOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3PurchaseInStockOrderResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
* <p>
    * K3采购入库单 Mapper 接口
    * </p>
*
* @author S20190161
* @since 2023-05-17
*/
public interface K3PurchaseInStockOrderMapper extends BaseMapper<K3PurchaseInStockOrder> {

    Page<K3PurchaseInStockOrderResult> findPageBySpec(@Param("page") Page page, @Param("paramCondition") K3PurchaseInStockOrderParam paramCondition);

}
