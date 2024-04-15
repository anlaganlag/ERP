package com.tadpole.cloud.operationManagement.api.shopEntity;


import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShop;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopResult;
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
@RequestMapping("/shopEntityApi")
public interface ShopEntityApi {

  @RequestMapping(value = "/queryById",method = RequestMethod.POST)
  @ApiOperation(value = "queryById")
  ResponseData queryById(@RequestParam String shopName) throws Exception;


  @RequestMapping(value = "/paginQuery",method = RequestMethod.POST)
  @ApiOperation(value = "paginQuery")
  ResponseData paginQuery(@RequestBody TbComShopParam tbComShopParam) throws Exception;


  @RequestMapping(value = "/logShopReportQuery",method = RequestMethod.POST)
  @ApiOperation(value = "logShopReportQuery")
  ResponseData logShopReportQuery(@RequestBody TbComShopParam tbComShopParam) throws Exception;


  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  @ApiOperation(value = "edit")
  ResponseData edit(@RequestBody TbComShop tbComShop);

  @RequestMapping(value = "/export", method = RequestMethod.POST)
  @ApiOperation(value = "export")
  List<TbComShopResult> export(@RequestBody TbComShopParam tbComShopParam);

  /**
   * 店铺账号下拉
   * @return
   */
  @RequestMapping(value = "/shopList", method = RequestMethod.GET)
  List<String> shopList();

  @RequestMapping(value = "/siteList", method = RequestMethod.GET)
  List<String> siteList();


  @RequestMapping(value = "/plaformList", method = RequestMethod.GET)
  List<String> plaformList();


  @RequestMapping(value = "/shopNameList", method = RequestMethod.GET)
  List<String> shopNameList();
}