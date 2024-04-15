package com.tadpole.cloud.operationManagement.api.shopEntity;


import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComTaxNum;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComTaxAuditParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComTaxNumParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComTaxNumResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 存货需求 服务类
 * </p>
 *
 * @author gal
 * @since 2022-03-03
 */
@RequestMapping("/taxNumApi")
@RestController
public interface TaxNumApi {

  @RequestMapping(value = "/queryById",method = RequestMethod.POST)
  @ApiOperation(value = "queryById")
  ResponseData queryById(@RequestParam String id) throws Exception;


  @RequestMapping(value = "/paginQuery",method = RequestMethod.POST)
  @ApiOperation(value = "paginQuery")
  ResponseData paginQuery(@RequestBody TbComTaxNumParam tbComTaxNumParam) throws Exception;


  @RequestMapping(value = "/updateByShopName", method = RequestMethod.POST)
  @ApiOperation(value = "updateByShopName")
  ResponseData updateByShopName(@RequestBody TbComTaxNum TbComTaxNum) throws Exception;

  @RequestMapping(value = "/export", method = RequestMethod.POST)
  @ApiOperation(value = "export")
  List<TbComTaxNumResult> export(@RequestBody TbComTaxNumParam TbComTaxNumParam);

  @RequestMapping(value = "/cancelTaxCode", method = RequestMethod.POST)
  @ApiOperation(value = "cancelTaxCode")
  ResponseData cancelTaxCode(@RequestBody TbComTaxNum tbComTaxNum) throws Exception;

  @RequestMapping(value = "/updateTaxNumState", method = RequestMethod.GET)
  @ApiOperation(value = "updateTaxNumState")
  ResponseData updateTaxNumState(@RequestParam String shopName,@RequestParam String taxnState) ;


  @RequestMapping(value = "/addTaxAudit", method = RequestMethod.POST)
  @ApiOperation(value = "addTaxAudit")
  ResponseData addTaxAudit(@RequestBody TbComTaxAuditParam param) throws Exception;

}