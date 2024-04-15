package com.tadpole.cloud.externalSystem.modular.lingxing.controller;

import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.lingxing.entity.LxMatConfig;
import com.tadpole.cloud.externalSystem.api.lingxing.model.parm.LxMatConfigParm;
import com.tadpole.cloud.externalSystem.api.lingxing.model.parm.SwitchPlanParm;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.LxMatConfigService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 领星物料配置表;(LX_MAT_CONFIG)表控制层
 * @author : LSY
 * @date : 2022-12-5
 */
@Api(tags = "领星物料配置表对象功能接口")
@RestController
@ApiResource(name = "领星物料配置表对象功能接口", path = "/lxMatConfig")
public class LxMatConfigController{

    @Autowired
    private LxMatConfigService lxMatConfigService;


     /**
      * 分页查询
      *
      * @param lxMatConfigParm 筛选条件
      * @return 查询结果
      */
     @ApiOperation("物料配置分页查询")
     @PostResource(name = "物料配置分页查询", path = "/list", menuFlag = true, requiredPermission = false, requiredLogin = false)
     public ResponseData paginQuery(@RequestBody LxMatConfigParm lxMatConfigParm){
         //1.分页参数
         Page page = getPageContext();
         long current = page.getCurrent();
         long size = page.getSize();


         //2.分页查询
         PageResult<LxMatConfig> pageResult = lxMatConfigService.paginQuery(lxMatConfigParm, current,size);
         return ResponseData.success(pageResult);
     }


     /**
      * planName
      *
      * @param planName
      * @return 实例对象
      */
     @ApiOperation("获取所有配置方案")
     @GetResource(name = "获取所有配置方案", path = "/allPlan", requiredPermission = false, requiredLogin = false)
     public ResponseData allPlan(@RequestParam(required = false) String planName){
        return ResponseData.success(lxMatConfigService.allPlan(planName));

     }


     /**
      *
      *获取所有物料属性
      * @param
      * @return 实例对象
      */
     @ApiOperation("获取所有物料属性")
     @GetResource(name = "获取所有物料属性", path = "/matAllProperty", requiredPermission = false, requiredLogin = false)
     public ResponseData matAllProperty(){
         return lxMatConfigService.matAllProperty();

     }


     /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetResource(name = "通过ID查询单条数据", path = "/id", requiredPermission = false, requiredLogin = false)
    public ResponseData queryById(String id){
        return ResponseData.success(lxMatConfigService.queryById(id));
    }


    /**
     * 新增数据
     *
     * @param lxMatConfig 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增物料配置")
    @PostResource(name = "新增物料配置", path = "/add", requiredPermission = false, requiredLogin = false)
    public ResponseData add(@RequestBody LxMatConfig lxMatConfig){
        return lxMatConfigService.add(lxMatConfig);
    }

    /**
     * 更新数据
     *
     * @param lxMatConfig 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新物料配置")
    @PostResource(name = "更新物料配置", path = "/edit", requiredPermission = false, requiredLogin = false)
    public ResponseData edit(@RequestBody  LxMatConfig lxMatConfig){
        return lxMatConfigService.edit(lxMatConfig);
    }


    /**
     * 批量切换方案
     *
     * @param switchPlanParm 实例对象
     * @return 实例对象
     */
    @ApiOperation("切换物料配置方案")
    @PostResource(name = "切换物料配置方案", path = "/switchPlan", requiredPermission = false, requiredLogin = false)
    public ResponseData switchPlan(@Validated @RequestBody SwitchPlanParm switchPlanParm){
        return lxMatConfigService.switchPlan(switchPlanParm);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    @GetResource(name = "通过主键删除数据", path = "/deleteById", requiredPermission = false, requiredLogin = false)
    public ResponseData deleteById(String id){
        return ResponseData.success(lxMatConfigService.deleteById(id));
    }


    /**
     *获取分页查询参数
     */
    private Page getPageContext() {
        return PageFactory.defaultPage();
    }
}
