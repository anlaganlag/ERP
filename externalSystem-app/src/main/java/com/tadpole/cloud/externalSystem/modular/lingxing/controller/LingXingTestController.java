package com.tadpole.cloud.externalSystem.modular.lingxing.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.product.LinXingCategoryAddResp;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.externalSystem.modular.lingxing.utils.LingXingReqByLoginUtil;
import com.tadpole.cloud.externalSystem.modular.lingxing.utils.LingXingReqInfoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: ty
 * @description: 领星销售Controller类
 * @date: 2022/8/12
 */
@RestController
@ApiResource(name = "领星接口测试", path = "/lingXingTest")
@Api(tags = "领星接口测试")
public class LingXingTestController {

    @Autowired
    private LingXingReqInfoUtil lingXingReqInfoUtil;

    @Autowired
    private LingXingReqByLoginUtil reqByLoginUtil;


    @GetResource(name = "查询本地产品列表", path = "/routing/data/local_inventory/productList", requiredPermission = false, requiredLogin = false)
    @ApiOperation("查询本地产品列表")
    public LingXingBaseRespData productList(@RequestParam Integer offset, @RequestParam Integer length ) throws Exception {
        Map<String, Integer> parMap = new HashMap<>();
        parMap.put("offset",offset);
        parMap.put("length",length);
        String bizUrl = "/routing/data/local_inventory/productList";
        LingXingBaseRespData lingXingBaseRespData = lingXingReqInfoUtil.doPostReq(parMap, bizUrl);
//       lingXingReqInfoUtil.test();
        return lingXingBaseRespData;
    }



    @GetResource(name = "查询产品分类列表", path = "/routing/data/local_inventory/category", requiredPermission = false, requiredLogin = false)
    @ApiOperation("查询产品分类列表")
    public LingXingBaseRespData categoryList(@RequestParam Integer offset, @RequestParam Integer length ) throws Exception {
        Map<String, Object> parMap = new HashMap<>();
        parMap.put("offset",offset);
        parMap.put("length",length);
        String bizUrl = "/routing/data/local_inventory/category";
        LingXingBaseRespData lingXingBaseRespData = lingXingReqInfoUtil.doGetReq(parMap, bizUrl);
        return lingXingBaseRespData;
    }


    @GetResource(name = "添加产品分类", path = "/addCategory", requiredPermission = false, requiredLogin = false)
    @ApiOperation("添加产品分类")
    public LinXingCategoryAddResp addCategory(@RequestParam Integer parentCid, @RequestParam String title ) throws Exception {
       return reqByLoginUtil.addCategory(parentCid, title);
    }
}
