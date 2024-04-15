package com.tadpole.cloud.operationManagement.modular.shopEntity.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
* 资源-店铺;(Tb_Com_Shop)--控制层
* @author : LSY
* @date : 2023-7-28
*/
@Slf4j
@Api(tags = "Temu店铺接口")
@RestController
@ApiResource(name="Temu资源-店铺" , path="/TemuShops")
public class TemuShopController {

    public final String paginQueryFunName = "Temu店铺--分页查询";



   @Resource
   private TbComShopService tbComShopService;



   /**
    * 分页查询
    *
    * @param tbComShopParam 筛选条件
    * @return 查询结果
    */
   @ApiOperation(value =paginQueryFunName ,response =TbComShopResult.class)
   @PostResource(name = paginQueryFunName, path = "/List", requiredLogin = true, menuFlag = true)
   @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
   public ResponseData paginQuery(@RequestBody TbComShopParam tbComShopParam){
       //1.分页参数
       Page page = tbComShopParam.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
       tbComShopParam.setElePlatformName("Temu");
       //2.分页查询
       /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
       Page<TbComShopResult> pageResult = tbComShopService.paginQuery(tbComShopParam, current,size);
       //3. 分页结果组装
       return ResponseData.success(new PageResult<>(pageResult));
   }




}