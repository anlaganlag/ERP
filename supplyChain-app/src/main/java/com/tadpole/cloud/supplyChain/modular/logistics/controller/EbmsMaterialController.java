package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.api.logistics.entity.EbmsMaterial;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IEbmsMaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * <p>
 * EBMS物料信息 前端控制器
 * </p>
 *
 * @author ty
 * @since 2022-12-22
 */
@RestController
@ApiResource(name = "EBMS物料信息", path = "/ebmsMaterial")
@Api(tags = "EBMS物料信息")
public class EbmsMaterialController {

    @Autowired
    private IEbmsMaterialService ebmsMaterialService;

    /**
     * 同步EBMS物料信息
     * @return
     */
    @GetResource(name = "同步EBMS物料信息", path = "/syncEbmsMaterial", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "同步EBMS物料信息")
    public ResponseData syncEbmsMaterial() {
        List<EbmsMaterial> ebmsMaterialList = ebmsMaterialService.getEbmsMaterial();
        return ebmsMaterialService.syncEbmsMaterial(ebmsMaterialList);
    }
}

