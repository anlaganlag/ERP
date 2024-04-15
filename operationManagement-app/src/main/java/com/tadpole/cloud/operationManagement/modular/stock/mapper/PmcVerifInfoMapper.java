package com.tadpole.cloud.operationManagement.modular.stock.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.stock.entity.PmcVerifInfo;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PmcAutoAnalyzeParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PmcVerifInfoParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.PmcAutoAnalyzeResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.PmcVerifInfoResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SupplierInfoResult;
import feign.Param;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
* <p>
    * 审核记录信息 Mapper 接口
    * </p>
*
* @author cyt
* @since 2022-07-05
*/
public interface PmcVerifInfoMapper extends BaseMapper<PmcVerifInfo> {




   @Select("select s.FNUMBER adviceSupplierId,l.FNAME adviceSupplier from T_BD_SUPPLIER s left join T_BD_SUPPLIER_L l on  s.FSUPPLIERID=l.FSUPPLIERID where FUSEORGID=100269  and s.FFORBIDSTATUS='A' AND s.FDOCUMENTSTATUS='C'")
   List<Map<String,String>> getAllSupplier();

   Page<PmcVerifInfoResult> querypmcVerifList(@Param("page") Page  page, @Param("reqVO") PmcVerifInfoParam reqVO);



    List<SupplierInfoResult> getMatControlPersonBySupplier( String supplier ,String supplierCode);



    List<PmcVerifInfoResult> queryList(@Param("pmcParam") PmcVerifInfoParam pmcParam);

    /**
     * 根据部门，team，物料编码获取下单信息
     * @param autoParam
     * @return
     */
    List<PmcAutoAnalyzeResult> autoAnalyze(@Param("autoParam") PmcAutoAnalyzeParam autoParam);

    /**
     * 更加物料编码获取下单信息
     * @param autoParam
     * @return
     */
    List<PmcAutoAnalyzeResult> autoAnalyzeByMat( @Param("autoParam") PmcAutoAnalyzeParam autoParam);

    List<PmcVerifInfoResult> mergeDetails(@Param("pmcParam") PmcVerifInfoParam pmcParam);

    /**
     * 计划部当日已审批订单（事业部，team，物料）
     * @param param
     * @return
     */
    List<PmcVerifInfoResult> planApprovedOrder(PmcVerifInfoParam param);

    @Select("SELECT  MAT_MODE_SPEC   FROM PRODUCT.PROD_MATERIEL pm WHERE MAT_CODE =#{materialCode}")
    String getMatModeSpec(String materialCode);


}
