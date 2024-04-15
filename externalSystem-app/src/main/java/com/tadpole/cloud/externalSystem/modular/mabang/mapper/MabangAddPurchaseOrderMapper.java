package com.tadpole.cloud.externalSystem.modular.mabang.mapper;

import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangAddPurchaseOrder;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangAddPurchaseOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3TransferMabangItemResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangAddPurchaseOrder2Result;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangAddPurchaseOrderResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* <p>
    * 马帮新增采购订单 Mapper 接口
    * </p>
*
* @author lsy
* @since 2022-06-15
*/
public interface MabangAddPurchaseOrderMapper extends BaseMapper<MabangAddPurchaseOrder> {


    Page<MabangAddPurchaseOrderResult> list(@Param("page") Page page, @Param("paramCondition") MabangAddPurchaseOrderParam paramCondition);

    @Select("SELECT * FROM  K3_TRANSFER_MABANG_ITEM T WHERE T.BILL_NO = #{billNo} AND T.GROUP_ID=#{groupId} ")
    Page<K3TransferMabangItemResult> display(@Param("page") Page page,  String billNo, String groupId);

    Page<MabangAddPurchaseOrder2Result> mergeList(@Param("page") Page page, @Param("paramCondition") MabangAddPurchaseOrderParam paramCondition);
}
