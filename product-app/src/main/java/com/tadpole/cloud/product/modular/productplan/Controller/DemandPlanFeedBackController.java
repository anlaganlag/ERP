package com.tadpole.cloud.product.modular.productplan.Controller;


import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productplan.model.params.RdPreProposalParam;
import com.tadpole.cloud.product.modular.productplan.enums.RdPreProposalEnum;
import com.tadpole.cloud.product.modular.productplan.service.IRdPreProposalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
* 产品预案
* @author AmteMa
* @date 2022/4/15
*/
@RestController
@ApiResource(name = "预案反馈", path = "/demandPlanFeedBack")
@Api(tags = "预案反馈")
public class DemandPlanFeedBackController {

    @Autowired
    private IRdPreProposalService rdPreProposalService;

    @PostResource(name = "预案反馈-列表查询", path = "/listPage", menuFlag = true)
    @ApiOperation(value = "预案反馈-列表查询")
    @BusinessLog(title = "预案反馈-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listPage(@RequestBody RdPreProposalParam param) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        List<String> listSysScen = new ArrayList<>();
        listSysScen.add(RdPreProposalEnum.YA_SCENE_OPR.getName());
        listSysScen.add(RdPreProposalEnum.YA_SCENE_PUS.getName());
        param.setSysYaSceneList(listSysScen);
        param.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_FEBK.getName());
        param.setSysPmPerCode(loginUser.getAccount());

        return ResponseData.success(this.rdPreProposalService.listPageFebk(param));
    }

    @PostResource(name = "预案反馈-反馈", path = "/feedBack")
    @ApiOperation(value = "预案反馈-反馈")
    @BusinessLog(title = "预案反馈-反馈",opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData feedBack(@RequestBody RdPreProposalParam param) {
        return this.rdPreProposalService.feedBack(param);
    }



}
