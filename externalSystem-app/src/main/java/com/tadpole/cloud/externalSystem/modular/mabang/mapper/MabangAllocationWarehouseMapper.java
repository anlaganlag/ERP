package com.tadpole.cloud.externalSystem.modular.mabang.mapper;

import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangAllocationWarehouse;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangAllocationWarehouseParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3TransferMabangItemResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangAllocationWarehouse2Result;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangAllocationWarehouseResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* <p>
    * 马帮分仓调拨单 Mapper 接口
    * </p>
*
* @author lsy
* @since 2022-06-15
*/
public interface MabangAllocationWarehouseMapper extends BaseMapper<MabangAllocationWarehouse> {


    Page<MabangAllocationWarehouseResult> list(@Param("page") Page page, @Param("paramCondition") MabangAllocationWarehouseParam paramCondition);

    @Select("SELECT * FROM  K3_TRANSFER_MABANG_ITEM T WHERE T.BILL_NO = #{billNo} AND T.ALLOCATION_CODE=#{allocationCode} ")
    Page<K3TransferMabangItemResult> display(@Param("page") Page page,  String billNo, String allocationCode);


    @Select("SELECT ALLOCATION_CODE FROM  MABANG_ALLOCATION_WAREHOUSE  T WHERE T.BILL_NO = #{billCode} AND T.REVERSE_ALLOCATION_CODE=#{allocationCode} ")
    String getAllocationCode(String billCode,  String allocationCode);


    Page<MabangAllocationWarehouse2Result> mergeList(@Param("page") Page page, @Param("paramCondition") MabangAllocationWarehouseParam paramCondition);

}
