package com.tadpole.cloud.externalSystem.modular.mabang.mapper;

import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3CustMatMapping;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3CustMatMappingParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3CustMatMappingResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* <p>
    * K3客户物料对应表 Mapper 接口
    * </p>
*
* @author lsy
* @since 2022-06-20
*/
public interface K3CustMatMappingMapper extends BaseMapper<K3CustMatMapping> {


    Page<K3CustMatMappingResult> list(@Param("page") Page page, @Param("paramCondition") K3CustMatMappingParam paramCondition);

    List<K3CustMatMappingResult> exportList( @Param("paramCondition") K3CustMatMappingParam paramCondition);


    void createMat();

    List<K3CustMatMapping> getCreateMatList();


    void updateOrdersStatus();
    void updateSeaOrdersStatus();


    void updatePlatFormSku();


//    void refreshCustomerId( @Param("paramCondition") K3CustMatMappingParam paramCondition);


    List<K3CustMatMappingResult> getSyncList();

    @Select("SELECT * FROM K3_CUST_MAT_MAPPING WHERE SYNC_SUCCESS_TIMES = 0 AND FINANCE_CODE IS NOT NULL AND CUSTOMER_ID IS NOT NULL AND NAME IS NOT NULL AND CUST_MAT_NO IS NOT NULL AND MATERIAL_ID IS NOT NULL ORDER BY SYNC_FAIL_TIMES")
    List<K3CustMatMappingResult> getPushK3List();



    List<String> getFinCodeList();




    String getCustId(String finCode);

    @Update("update k3_cust_mat_mapping  set CUSTOMER_ID = #{custId} where FINANCE_CODE = #{finCode}")
    void updateCustId(String finCode,String custId);



    @Select("select top 1 FBILLNO BILL_CODE, FSaleOrgId SALE_ORG_ID  from  T_SAl_CUSTMATMAPPING   where FCUSTOMERID = #{custId} order by FID")
    K3CustMatMappingResult getInfo(String custId);



    void refreshInfo(@Param("paramCondition")  K3CustMatMappingResult paramCondition);


    @Update("update K3_CUST_MAT_MAPPING  SET SYNC_RESULT_MSG = '找不客户ID'  WHERE FINANCE_CODE = #{finCode}")
    void refreshNoCustId(String finCode);

    void beginProcedureSync(@Param("paramCondition")  K3CustMatMappingResult paramCondition);


     String getK3One ( @Param("paramCondition") K3CustMatMappingResult paramCondition);

    List<String> queryNames();

}
