package com.tadpole.cloud.product.modular.productplan.Controller;


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
@ApiResource(name = "需求预案", path = "/demandPlan")
@Api(tags = "需求预案")
public class DemandPlanController {

    @Autowired
    private IRdPreProposalService rdPreProposalService;

    @PostResource(name = "需求预案-列表查询", path = "/listPage", menuFlag = true)
    @ApiOperation(value = "需求预案-列表查询")
    @BusinessLog(title = "需求预案-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listPage(@RequestBody RdPreProposalParam param) {
        param.setSysYaScene(RdPreProposalEnum.YA_SCENE_OPR.getName());
        return ResponseData.success(this.rdPreProposalService.listPage(param));
    }

    @PostResource(name = "需求预案-创建/编辑预案", path = "/addOrEdit")
    @ApiOperation(value = "需求预案-创建/编辑预案")
    @BusinessLog(title = "需求预案-创建/编辑预案",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData addOrEdit(@RequestBody RdPreProposalParam param) {
        param.setSysYaScene(RdPreProposalEnum.YA_SCENE_OPR.getName());
        return this.rdPreProposalService.addOrEdit(param);
    }

    @PostResource(name = "需求预案-详情", path = "/detail")
    @ApiOperation(value = "需求预案-详情")
    @BusinessLog(title = "需求预案-详情",opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@RequestBody RdPreProposalParam param) {
        return ResponseData.success(this.rdPreProposalService.detail(param));
    }

    @PostResource(name = "需求预案-申请", path = "/apply")
    @ApiOperation(value = "需求预案-申请")
    @BusinessLog(title = "需求预案-申请",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData apply(@RequestBody RdPreProposalParam param) {
        param.setSysYaScene(RdPreProposalEnum.YA_SCENE_OPR.getName());
        return this.rdPreProposalService.apply(param);
    }

    @PostResource(name = "需求预案-归档", path = "/archive")
    @ApiOperation(value = "需求预案-归档")
    @BusinessLog(title = "需求预案-归档",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData archive(@RequestBody RdPreProposalParam param) {
        return this.rdPreProposalService.archive(param);
    }

    @PostResource(name = "需求预案-撤销", path = "/revoke")
    @ApiOperation(value = "需求预案-撤销")
    @BusinessLog(title = "需求预案-撤销",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData revoke(@RequestBody RdPreProposalParam param) {
        return this.rdPreProposalService.revoke(param);
    }
}
