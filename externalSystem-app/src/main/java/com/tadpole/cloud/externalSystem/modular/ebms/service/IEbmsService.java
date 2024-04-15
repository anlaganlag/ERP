package com.tadpole.cloud.externalSystem.modular.ebms.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.api.ebms.model.resp.EbmsUserInfo;

public interface IEbmsService {
    ResponseData queryMaterialCategory();


    ResponseData queryMaterialOperationCategory(String categoryName);

    ResponseData getDeptList();

    ResponseData getSaleBrand();



    ResponseData getUserInfo(String perName, String sysComDeptCode);

    /**
     * 根据工号获取EBMS员工信息
     * @param account
     * @return
     */
    EbmsUserInfo getUserInfoByAccount(String account);

    ResponseData getMateriel(String [] matCodes);

    ResponseData queryMaterialCategoryTree();

    ResponseData queryComMatBrand();
    ResponseData queryComMatModel();
    ResponseData queryComMatColor();
    ResponseData queryComMatSize();
    ResponseData queryComMatPackQty();
    ResponseData queryComMatCompatibleModel();
    ResponseData queryComMatFestLabel();
    ResponseData queryComMaterielTree(String type,String value);
}
