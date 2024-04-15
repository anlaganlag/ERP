package com.tadpole.cloud.externalSystem.modular.lingxing.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.product.ProductReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.product.BrandResp;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.product.CategoryResp;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.product.LinXingCategoryAddResp;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.LingXingProductService;
import com.tadpole.cloud.externalSystem.modular.lingxing.utils.LingXingReqByLoginUtil;
import com.tadpole.cloud.externalSystem.modular.lingxing.utils.LingXingReqInfoUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class LingXingProductServiceImpl implements LingXingProductService {

    @Autowired
    private LingXingReqInfoUtil lingXingReqInfoUtil;

    @Autowired
    LingXingReqByLoginUtil lingXingReqByLoginUtil;




    @Override
    public LinXingCategoryAddResp addCategory(Integer parentCid, String title) {
        return lingXingReqByLoginUtil.addCategory(parentCid, title);
    }

    @Override
    public LingXingBaseRespData addProduct(ProductReq productReq) {
        String bizUrl = "/routing/storage/product/set";
        try {
            LingXingBaseRespData lingXingBaseRespData = lingXingReqInfoUtil.doPostReq(productReq, bizUrl);
            return lingXingBaseRespData;
        } catch (Exception e) {
            log.info("零星erp添加产品异常：{}",JSON.toJSONString(e));
            LingXingBaseRespData respData = new LingXingBaseRespData();
            respData.setCode(500);
            respData.setMessage("零星erp添加产品异常-->"+ JSON.toJSONString(e));
            return respData;
        }
    }




    @Override
    public List<CategoryResp> getAllCategory() {

        Integer pageSize=1000;
        List<CategoryResp> categoryList = new ArrayList<>();

        Map<String, Object> parMap1 = new HashMap<>();
        parMap1.put("offset",0);
        parMap1.put("length",pageSize);
        String bizUrl = "/routing/data/local_inventory/category";
        try {
            LingXingBaseRespData lingXingBaseRespData = lingXingReqInfoUtil.doGetReq(parMap1, bizUrl);

            if (lingXingBaseRespData.getCode()==0 && lingXingBaseRespData.getTotal()>0) {
                Integer pageCount= lingXingBaseRespData.getTotal() % pageSize >0 ? lingXingBaseRespData.getTotal() / pageSize + 1 : lingXingBaseRespData.getTotal() / pageSize;
                log.info("领星erp分类获取成功,总共【{}】条数据，需要分页【{}】次查询",lingXingBaseRespData.getTotal(),pageCount);
                for (Integer i = 0; i < pageCount; i++) {
                    Thread.sleep(1000);
                    Map<String, Object> parMap = new HashMap<>();
                    parMap.put("length",pageSize);
                    parMap.put("offset",pageSize * i);
                    LingXingBaseRespData pageResult = lingXingReqInfoUtil.doGetReq(parMap, bizUrl);
                    if (pageResult.getCode()==0 && ObjectUtil.isNotEmpty(pageResult.getData())) {
                        List<CategoryResp> pageResultCategoryList = JSON.parseArray(JSON.toJSONString(pageResult.getData()), CategoryResp.class);
                        categoryList.addAll(pageResultCategoryList);
                    }
                }
            }
            log.info("领星erp分类获取成功,总共【{}】条数据，分页查询汇总【{}】条",lingXingBaseRespData.getTotal(),categoryList.size());
            return categoryList;
        } catch (Exception e) {
            log.error("获取零星erp所有分类数据异常：{}",e.getMessage());
            return null;
        }
    }

    @Override
    public List<BrandResp> getAllBrand() {
        Integer pageSize=1000;
        List<BrandResp> brandList = new ArrayList<>();

        Map<String, Object> parMap1 = new HashMap<>();
        parMap1.put("offset",0);
        parMap1.put("length",pageSize);
        String bizUrl = "/data/local_inventory/brand";
        try {

            LingXingBaseRespData lingXingBaseRespData = lingXingReqInfoUtil.doPostReq(parMap1, bizUrl);

            if (lingXingBaseRespData.getCode()==0 ) {
                if (lingXingBaseRespData.getTotal() <= 0) {
                    List<BrandResp> pageResultBrandList = JSON.parseArray(JSON.toJSONString(lingXingBaseRespData.getData()), BrandResp.class);
                    brandList.addAll(pageResultBrandList);
                } else {
                    Integer pageCount= lingXingBaseRespData.getTotal() % pageSize >0 ? lingXingBaseRespData.getTotal() / pageSize + 1 : lingXingBaseRespData.getTotal() / pageSize;
                    log.info("领星erp所有品牌获取成功,总共【{}】条数据，需要分页【{}】次查询",lingXingBaseRespData.getTotal(),pageCount);
                    for (Integer i = 0; i < pageCount; i++) {
                        Thread.sleep(1000);
                        Map<String, Object> parMap = new HashMap<>();
                        parMap.put("length",pageSize);
                        parMap.put("offset",pageSize * i);
                        LingXingBaseRespData pageResult = lingXingReqInfoUtil.doGetReq(parMap, bizUrl);
                        if (pageResult.getCode()==0 && ObjectUtil.isNotEmpty(pageResult.getData())) {
                            List<BrandResp> pageResultBrandList = JSON.parseArray(JSON.toJSONString(pageResult.getData()), BrandResp.class);
                            brandList.addAll(pageResultBrandList);
                        }
                    }
                }

            }
            log.info("领星erp所有品牌获取成功,总共【{}】条数据，分页查询汇总【{}】条",lingXingBaseRespData.getTotal(),brandList.size());
            return brandList;
        } catch (Exception e) {
            log.info("获取零星erp所有品牌数据异常：{}",e.getMessage());
            return null;
        }
    }

}
