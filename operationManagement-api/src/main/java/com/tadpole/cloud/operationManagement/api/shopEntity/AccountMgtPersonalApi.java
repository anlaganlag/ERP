package com.tadpole.cloud.operationManagement.api.shopEntity;


import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.AccountMgtPersonal;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.AccountMgtPersonalParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.AccountFlowParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.AccountMgtPersonalResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityResult;
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
@RequestMapping("/accountMgtPersonalApi")
@RestController
public interface AccountMgtPersonalApi {

  @RequestMapping(value = "/listAccountMgtPersonal",method = RequestMethod.POST)
  @ApiOperation(value = "listAccountMgtPersonal")
  List<AccountMgtPersonal> listAccountMgtPersonal(@RequestBody AccountMgtPersonalParam param) throws Exception;

  @RequestMapping(value = "/addOutFlow",method = RequestMethod.POST)
  @ApiOperation(value = "addOutFlow")
  ResponseData addOutFlow(@RequestBody AccountFlowParam accountFlow) throws Exception;

  @RequestMapping(value = "/addInFlow",method = RequestMethod.POST)
  @ApiOperation(value = "addInFlow")
  ResponseData addInFlow(@RequestBody AccountFlowParam accountFlow) throws Exception;

  @RequestMapping(value = "/listCompanyAccount",method = RequestMethod.POST)
  @ApiOperation(value = "listCompanyAccount")
  List<TbComEntityResult> listCompanyAccount() throws Exception;


}