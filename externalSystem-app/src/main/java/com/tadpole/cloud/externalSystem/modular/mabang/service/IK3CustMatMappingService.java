package com.tadpole.cloud.externalSystem.modular.mabang.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3CustMatMapping;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3CustMatMappingParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3CustMatMappingResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * K3客户物料对应表 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-06-20
 */
public interface IK3CustMatMappingService extends IService<K3CustMatMapping> {


    PageResult<K3CustMatMappingResult> list(K3CustMatMappingParam param);
    ResponseData createMat();
    ResponseData updateOrdersStatus();
    ResponseData updateSeaOrdersStatus();


    ResponseData updatePlatFormSku();


//    void refreshCustomerId(K3CustMatMappingParam param);


    List<K3CustMatMappingResult> getSyncList();
    List<K3CustMatMappingResult> getPushK3List();




    List<String> getFinCodeList();
    String getCustId(String fincode);
    void updateCustId(String finCode,String custId);
    K3CustMatMappingResult getInfo(String custId);
    void refreshInfo(K3CustMatMappingResult res);
    void refreshNoCustId(String finCode);



    void beginProcedureSync(K3CustMatMappingResult param);
    void syncFailedStatus(K3CustMatMappingResult param);
    void syncSuccessStatus(K3CustMatMappingResult param);



    String getK3One(K3CustMatMappingResult param);



    List<K3CustMatMappingResult> exportList(K3CustMatMappingParam param);


    ResponseData queryNames();













}
