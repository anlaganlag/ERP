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


/**
* 产品预案
* @author AmteMa
* @date 2022/4/15
*/
@RestController
@ApiResource(name = "预案审批", path = "/demandPlanApprove")
@Api(tags = "预案审批")
public class DemandPlanApproveController {

    @Autowired
    private IRdPreProposalService rdPreProposalService;

    @PostResource(name = "预案审批-列表查询", path = "/listPage", menuFlag = true)
    @ApiOperation(value = "预案审批-列表查询")
    @BusinessLog(title = "预案审批-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listPage(@RequestBody RdPreProposalParam param) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        param.setSysApprPerCode(loginUser.getAccount());
        param.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_APPR.getName());

        return ResponseData.success(this.rdPreProposalService.listPageFebk(param));
    }

    @PostResource(name = "预案审批-审批", path = "/approve")
    @ApiOperation(value = "预案审批-审批")
    @BusinessLog(title = "预案审批-审批",opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData approve(@RequestBody RdPreProposalParam param) {
        return this.rdPreProposalService.approve(param);
    }
}
