package com.tadpole.cloud.product.modular.productproposal.Controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productproposal.entity.RdFarSetting;
import com.tadpole.cloud.product.api.productproposal.entity.RdSdSetting;
import com.tadpole.cloud.product.api.productproposal.entity.RdTlSetting;
import com.tadpole.cloud.product.api.productproposal.model.params.RdFarSettingParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSdSettingParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdTlSettingParam;
import com.tadpole.cloud.product.modular.productproposal.service.IRdFarSettingService;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSdSettingService;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSettingUpRecordService;
import com.tadpole.cloud.product.modular.productproposal.service.IRdTlSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* 产品提案
* @author AmteMa
* @date 2022/4/15
*/
@RestController
@ApiResource(name = "设置", path = "/paramsSetup")
@Api(tags = "设置")
public class ParamsSetupController {

    @Autowired
    private IRdTlSettingService rdTlSettingService;

    @Autowired
    private IRdSdSettingService rdSdSettingService;

    @Autowired
    private IRdFarSettingService rdFarSettingService;

    @Autowired
    private IRdSettingUpRecordService upRecordService;

    @PostResource(name = "设置-列表查询", path = "/listPage", menuFlag = true)
    @ApiOperation(value = "设置-列表查询")
    @BusinessLog(title = "设置-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listPage() {

        List<RdFarSetting> rdFarSettings = rdFarSettingService.listPage();
        List<RdSdSetting> rdSdSettings = rdSdSettingService.listPage();
        List<RdTlSetting> rdTlSettings = rdTlSettingService.listPage();

        Map map = new HashMap();
        map.put("RdFarSettings",rdFarSettings);
        map.put("RdSdSettings",rdSdSettings);
        map.put("RdTlSettings",rdTlSettings);

        return ResponseData.success(map);
    }

    @PostResource(name = "提案等级设置-提交",path = "/submitRdTlSetting")
    @ApiOperation(value = "提案等级设置-提交")
    @BusinessLog(title = "提案等级设置-提交",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData submitRdTlSetting(@RequestBody List<RdTlSettingParam> params){

        rdTlSettingService.save(params);

        return ResponseData.success();
    }

    @PostResource(name = "拿样任务时长设置-提交",path = "/submitRdSdSetting")
    @ApiOperation(value = "拿样任务时长设置-提交")
    @BusinessLog(title = "拿样任务时长设置-提交",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData submitRdSdSetting(@RequestBody List<RdSdSettingParam> params){

        rdSdSettingService.save(params);

        return ResponseData.success();
    }

    @PostResource(name = "研发费用自动过审设置-提交",path = "/submitRdFarSetting")
    @ApiOperation(value = "研发费用自动过审设置-提交")
    @BusinessLog(title = "研发费用自动过审设置-提交",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData submitRdFarSetting(@RequestBody List<RdFarSettingParam> params){

        rdFarSettingService.save(params);

        return ResponseData.success();
    }

    @PostResource(name = "提案等级设置-日志",path = "/listRdTlSettingLog")
    @ApiOperation(value = "提案等级设置-日志")
    @BusinessLog(title = "提案等级设置-日志",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listRdTlSettingLog(){

        return ResponseData.success(upRecordService.listRdTlSettingLog());
    }

    @PostResource(name = "拿样任务时长设置-日志",path = "/listRdSdSettingLog")
    @ApiOperation(value = "拿样任务时长设置-日志")
    @BusinessLog(title = "拿样任务时长设置-日志",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listRdSdSettingLog(){

        return ResponseData.success(upRecordService.listRdSdSettingLog());
    }

    @PostResource(name = "研发费用自动过审设置-日志",path = "/listRdFarSettingLog")
    @ApiOperation(value = "研发费用自动过审设置-日志")
    @BusinessLog(title = "研发费用自动过审设置-日志",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listRdFarSettingLog(){

        return ResponseData.success(upRecordService.listRdFarSettingLog());
    }

    @PostResource(name = "提案等级设置-提案等级记录",path = "/listRdTlSetting",  requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "提案等级设置-提案等级记录")
    @BusinessLog(title = "提案等级设置-提案等级记录",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listRdTlSetting(){
        List<RdTlSetting> rdTlSettings = rdTlSettingService.listPage();
        return ResponseData.success(rdTlSettings);
    }



}
