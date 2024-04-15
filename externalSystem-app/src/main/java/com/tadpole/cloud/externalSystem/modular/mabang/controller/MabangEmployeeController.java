package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.MabangResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.MabangEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * 马帮员工信息前端控制器
    * </p>
*
* @author lsy
* @since 2022-06-09
*/
@RestController
@ApiResource(name="马帮员工信息",path = "/mabangEmployee")
@Api(tags =  "马帮员工信息")
public class MabangEmployeeController {

    @Autowired
    private MabangEmployeeService service;

    @PostMapping("/sync")
    @ApiOperation(value = "同步马帮员工信息", response = ResponseData.class)
    public ResponseData sync(@RequestParam(required = false) Integer status){
        return  service.sync(status);
    }

    @GetResource(name = "员工第三方登录", path = "/ssoLogin")
    @ApiOperation(value = "员工第三方登录", response = ResponseData.class)
    public MabangResult ssoLogin(){
        MabangResult mabangResult = service.ssoLogin();
        return mabangResult;
    }

    @GetResource(name = "马帮员工列表", path = "/list")
    @ApiOperation(value = "马帮员工列表", response = ResponseData.class)
    @DataSource(name = "external")
    public ResponseData list(){
        return ResponseData.success(service.list());
    }

}
