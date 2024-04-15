package com.tadpole.cloud.operationManagement.api.shopEntity;


import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopLogisticsReportResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 存货需求 服务类
 * </p>
 *
 * @author gal
 * @since 2022-03-03
 */
@RequestMapping("/logShopReportApi")
@RestController
public interface LogShopReportApi {




  @RequestMapping(value = "/logShopReportQuery",method = RequestMethod.POST)
  @ApiOperation(value = "logShopReportQuery")
  ResponseData logShopReportQuery(@RequestBody TbComShopParam tbComShopParam) throws Exception;


  @RequestMapping(value = "/export", method = RequestMethod.POST)
  @ApiOperation(value = "export")
  List<TbComShopLogisticsReportResult> export(TbComShopParam tbComShopParam);
}