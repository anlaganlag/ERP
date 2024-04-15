package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import com.alibaba.fastjson.JSON;
import com.tadpole.cloud.externalSystem.config.MabangConfig;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.MabangHeadParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.MabangResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangRequstService;
import com.tadpole.cloud.externalSystem.modular.mabang.utils.HMACSHA256;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class MabangRequstServiceImpl implements IMabangRequstService {

    @Autowired
    MabangConfig mabangConfig;

    @Autowired
    RestTemplate restTemplate;


    @Override
    public MabangResult getOrderList(MabangHeadParm mabangHeadParm) {
        return this.postRequset(mabangHeadParm);
    }

    @Override
    public MabangResult getOrderListNew(MabangHeadParm mabangHeadParm) {
        return this.postRequset(mabangHeadParm);
    }





    @Override
    public MabangResult getPurOrderList(MabangHeadParm mabangHeadParm) {
        return this.postRequset(mabangHeadParm);
    }


    @Override
    public MabangResult getSkuStockQty(MabangHeadParm mabangHeadParm) {
        return this.postRequset(mabangHeadParm);
    }




    @Override
    public MabangResult getShopList(MabangHeadParm mabangHeadParm) {
        return this.postRequset(mabangHeadParm);
    }

    @Override
    public MabangResult purchaseOrderAdd(MabangHeadParm mabangHeadParm) {
        return this.postRequset(mabangHeadParm);
    }

    @Override
    public MabangResult purchaseOrderModify(MabangHeadParm mabangHeadParm) {
        return this.postRequset(mabangHeadParm);
    }

    @Override
    public MabangResult warehouseList(MabangHeadParm mabangHeadParm) {
        return this.postRequset(mabangHeadParm);
    }

    @Override
    public MabangResult createAllocationWarehouse(MabangHeadParm mabangHeadParm) {
        return this.postRequset(mabangHeadParm);
    }

    @Override
    public MabangResult getPurchaseOrderList(MabangHeadParm mabangHeadParm) {
        return this.postRequset(mabangHeadParm);
    }

    @Override
    public MabangResult returnOrderList(MabangHeadParm mabangHeadParm) {
        return this.postRequset(mabangHeadParm);
    }

    @Override
    public MabangResult stockDoSearchSkuList(MabangHeadParm mabangHeadParm){
        return  this.postRequset(mabangHeadParm);
    }

    @Override
    public MabangResult stockDoAddStock(MabangHeadParm mabangHeadParm){
        return  this.postRequset(mabangHeadParm);
    }

    @Override
    public MabangResult stockDoChangeStock(MabangHeadParm mabangHeadParm){
        return  this.postRequset(mabangHeadParm);
    }

    @Override
    public MabangResult stockChangeGrid(MabangHeadParm mabangHeadParm) {

        return  this.postRequset(mabangHeadParm);
    }
    @Override
    public MabangResult sysGetEmployeeList(MabangHeadParm mabangHeadParm) {
        return  this.postRequset(mabangHeadParm);
    }

    @Override
    public MabangResult orderUpdate(MabangHeadParm mabangHeadParm) {
        return  this.postRequset(mabangHeadParm);
    }

    @Override
    public MabangResult bindEmployee(MabangHeadParm mabangHeadParm) {
        return this.postRequset(mabangHeadParm);
    }

    @Override
    public MabangResult postRequset(MabangHeadParm mabangHeadParm) {
        String jsonParm = JSON.toJSONString(mabangHeadParm);
        String encryptParm = null;
        try {
            encryptParm = HMACSHA256.encrypt2(jsonParm, mabangConfig.getSec());
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("请求马帮明文参数:["+jsonParm+"]-----加密后的参数:["+encryptParm+"]");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
        headers.add("Authorization",encryptParm);
        headers.add("X-Requested-With", "XMLHttpRequest");
        HttpEntity httpEntity = new HttpEntity<>(jsonParm, headers);
        //String jsonObject = restTemplate.postForObject(mabangConfig.getUrl(), httpEntity, String.class);
        ResponseEntity<MabangResult> restTemplateResult = restTemplate.exchange(mabangConfig.getUrl(), HttpMethod.POST, httpEntity, MabangResult.class);
        MabangResult mabangResult = restTemplateResult.getBody();
        log.debug("马帮响应结果:["+JSON.toJSONString(mabangResult)+"]");
        return mabangResult;
    }

}
