package com.tadpole.cloud.product.modular.productplan.Controller;


import cn.hutool.core.util.ObjectUtil;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
* 预案指派
* @author lsy
* @date 2024/4/8
*/
@RestController
@ApiResource(name = "预案指派", path = "/planAppointPm")
@Api(tags = "预案指派")
public class PlanAppointPmController {

    @Resource
    private IRdPreProposalService rdPreProposalService;


    @PostResource(name = "预案指派-列表查询", path = "/listPage", menuFlag = true)
    @ApiOperation(value = "预案指派-列表查询")
    @BusinessLog(title = "预案指派-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listPage(@RequestBody RdPreProposalParam param) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
//        param.setSysApprPerCode(loginUser.getAccount());

//        需求预案和推样预案提交的时候
        List<String> sceneList = new ArrayList<>();
        sceneList.add(RdPreProposalEnum.YA_SCENE_OPR.getName());
        sceneList.add(RdPreProposalEnum.YA_SCENE_PUS.getName());
        param.setSysYaSceneList(sceneList);

//        开发方式为 全新品
        List<String> mehodList = new ArrayList<>();
        mehodList.add(RdPreProposalEnum.YA_DEV_METHOND_QX.getName());
        mehodList.add(RdPreProposalEnum.YA_DEV_METHOND_QD.getName());
        param.setSysDevMethondList(mehodList);

        //预案待反馈状态
        param.setSysYaStatus(RdPreProposalEnum.YA_STATE_WAIT_FEBK.getName());
        //还未指定产品经理
        param.setNoAppointPm(true);
        return ResponseData.success(this.rdPreProposalService.listPage(param));

    }


    @PostResource(name = "预案指派-指定产品经理", path = "/appointPm")
    @ApiOperation(value = "预案指派-指定产品经理")
    @BusinessLog(title = "预案指派-指定产品经理",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData appointPm(@RequestBody RdPreProposalParam param) {
        if (ObjectUtil.isEmpty(param.getSysPmPerCode()) || ObjectUtil.isEmpty(param.getSysPmPerName())) {
            return ResponseData.error("选择产品经理不能为空");
        }
        return this.rdPreProposalService.appointPm(param);

    }
}
