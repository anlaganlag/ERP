package com.tadpole.cloud.product.modular.productlanding.Controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productlanding.model.params.RdProductDefineBookParam;
import com.tadpole.cloud.product.api.productlanding.model.result.RdProductDefineBookExtentResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
* 产品内推
* @author AmteMa
* @date 2022/4/15
*/
@RestController
@ApiResource(name = "产品定义书", path = "/productDefineBook")
@Api(tags = "产品定义书")
public class ProductDefineBookController {

    @PostResource(name = "产品定义书-列表查询", path = "/listPage", menuFlag = true)
    @ApiOperation(value = "产品定义书-列表查询")
    @BusinessLog(title = "产品定义书-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listPage() {
        return ResponseData.success();
    }

    @PostResource(name = "产品定义书-产品定义书记录", path = "/list")
    @ApiOperation(value = "产品定义书-产品定义书记录")
    @BusinessLog(title = "产品定义书-产品定义书记录",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(@RequestBody RdProductDefineBookParam param) {
        List<RdProductDefineBookExtentResult> list =new ArrayList<>();
        RdProductDefineBookExtentResult model = new RdProductDefineBookExtentResult();
        model.setSysOldProDefineNum("product230704");
        model.setSysOldProDefineVersion("V1");
        model.setSysOldProDefineType("产品定义书");
        model.setSysFinalDate(new Date());
        model.setSysPlCode("2023-042");
        model.setSysPlName("宠物玩具");
        model.setSysPmPerCode("S20200013");
        model.setSysPmPerName("庄全发");
        model.setSysSpu("2023120004");
        model.setSysProName("项圈");
        model.setSysStyle("术后项圈7瓣花款");
        model.setSysMainMaterial("头层牛皮");
        model.setSysBrand("宠物通用");
        model.setSysModel("-");
        list.add(model);
        return ResponseData.success(list);
    }
}
