package com.tadpole.cloud.externalSystem.modular.ebms.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.ebms.service.ITbcommaterielService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author: ty
 * @description: 物料Controller类
 * @date: 2023/4/3
 */
@RestController
@ApiResource(name = "物料Controller类", path = "/tbcommateriel")
@Api(tags = "物料Controller类")
public class TbcommaterielController {

    @Autowired
    private ITbcommaterielService tbcommaterielService;

    @PostResource(name = "查询物料", path = "/getWaitMatList", requiredPermission = false, requiredLogin = false)
    @ApiOperation("查询物料")
    public ResponseData getWaitMatList(@RequestBody List<String> matList) {
        return ResponseData.success(tbcommaterielService.getWaitMatList(matList));
    }
}
