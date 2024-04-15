package com.tadpole.cloud.externalSystem.modular.mabang.mapper;

import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3TransferItem;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3TransferItemParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3AvailableResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
* <p>
    * K3调拨单明细项 Mapper 接口
    * </p>
*
* @author lsy
* @since 2022-06-09
*/
public interface K3TransferItemMapper extends BaseMapper<K3TransferItem> {

    /**
     * K3cloud直接调拨接口
     */

    List<K3TransferItem> list(K3TransferItemParam param);


//    @Select("SELECT a.FNUMBER,b.FNAME FROM T_BD_Stock a,T_BD_STOCK_L b WHERE a.ISSPECIALWAREHOUSE= 1 AND a.FUSEORGID= 100269 AND a.FSTOCKID= b.FSTOCKID")
    List<Map<String, String>> k3MabangWarehouse();

    List<K3AvailableResult> queryK3AvailableQty(List<String> warehouseList);
}
