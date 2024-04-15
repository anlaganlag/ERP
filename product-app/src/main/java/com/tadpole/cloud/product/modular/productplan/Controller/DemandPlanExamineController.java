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
@ApiResource(name = "预案审核", path = "/demandPlanExamine")
@Api(tags = "预案审核")
public class DemandPlanExamineController {
    @Autowired
    private IRdPreProposalService rdPreProposalService;

    @PostResource(name = "预案审核-列表查询", path = "/listPage", menuFlag = true)
    @ApiOperation(value = "预案审核-列表查询")
    @BusinessLog(title = "预案审核-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listPage(@RequestBody RdPreProposalParam param) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        param.setSysExamPerCode(loginUser.getAccount());
        param.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_EXAM.getName());

        return ResponseData.success(this.rdPreProposalService.listPageFebk(param));
    }

    @PostResource(name = "预案审核-审核", path = "/examine")
    @ApiOperation(value = "预案审核-审核")
    @BusinessLog(title = "预案审核-审核",opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData examine(@RequestBody RdPreProposalParam param) {
        return this.rdPreProposalService.examine(param);
    }
}
