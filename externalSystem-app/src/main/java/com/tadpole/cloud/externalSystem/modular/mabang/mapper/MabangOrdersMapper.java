package com.tadpole.cloud.externalSystem.modular.mabang.mapper;

import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangOrders;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangOrdersParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangOrderItemResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangOrdersResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* <p>
    * 马帮订单列表 Mapper 接口
    * </p>
*
* @author lsy
* @since 2022-06-09
*/
public interface MabangOrdersMapper extends BaseMapper<MabangOrders> {


    Page<MabangOrdersResult> listOld(@Param("page") Page page, @Param("paramCondition") MabangOrdersParam paramCondition);
    Page<MabangOrdersResult> list(@Param("page") Page page, @Param("paramCondition") MabangOrdersParam paramCondition);

    List<MabangOrdersResult> exportList(@Param("paramCondition") MabangOrdersParam paramCondition);



    @Select("SELECT 1 FROM  MABANG_ORDERS  WHERE PLATFORM_ORDER_ID = #{id} AND ROWNUM = 1")
    Integer getByPlatformOrderId(String id);

    @Delete("delete  FROM  MABANG_ORDERS  WHERE PLATFORM_ORDER_ID = #{id}")
    void deleteByPlatformOrderId(String id);

    @Select("SELECT * FROM MABANG_ORDER_ITEM    WHERE SUBSTR(id,0,INSTR(id,'_')-1) = #{id}")
    Page<MabangOrderItemResult> display(@Param("page") Page page, String id);

    @Select("SELECT * FROM  MABANG_ORDERS  WHERE PLATFORM_ORDER_ID = #{platOrdId} ")
    MabangOrders getMabangOrderByPlatformOrderId(String platOrdId);
    List<MabangOrdersResult> getWaitCreatePurchaseOrder(MabangOrdersParam param);
}
