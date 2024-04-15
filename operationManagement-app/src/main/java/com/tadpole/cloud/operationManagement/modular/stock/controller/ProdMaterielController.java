package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.modular.stock.entity.ProdMaterielOprInfo;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TbComMateriel;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.ProdMaterielOprInfoParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.TbcommaterielParam;
import com.tadpole.cloud.operationManagement.modular.stock.service.IProdMaterielOprInfoService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IProdMaterielService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * 物料信息 前端控制器
    * </p>
*
* @author gal
* @since 2022-10-20
*/
@RestController
@ApiResource(name = "物料管理-物料信息", path = "/ProdMateriel", menuFlag = true)
@Api(tags = "物料管理-物料信息")
@Slf4j
public class ProdMaterielController {

    @Autowired
    private IProdMaterielService service;

    @Autowired
    private IProdMaterielOprInfoService oprInfoService;

    /**
     * 物料信息列表
     *
     */
    @PostResource(name = "物料信息列表", path = "/list", menuFlag = true)
    @ApiOperation(value = "物料信息列表", response = TbComMateriel.class)
    public ResponseData list(@RequestBody TbcommaterielParam param) {
        PageResult<TbComMateriel> pageBySpec = service.listBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "物料管理-查询运营信息", path = "/getMaterielOprInfo")
    @ApiOperation(value = "物料管理-查询运营信息", response = ProdMaterielOprInfo.class)
    public ResponseData getMaterielOprInfo(@RequestBody ProdMaterielOprInfoParam param) {
        return ResponseData.success(oprInfoService.getMaterielOprInfo(param));
    }


}
