package ${package.Controller};

import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.*;
import org.springframework.web.bind.annotation.*;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import ${cfg.result}.*;
import ${cfg.params}.*;
import ${cfg.entity}.${entity};
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import ${package.Service}.${table.serviceName};
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
* <p>
* ${table.comment!} 前端控制器
* </p>
*
* @author ${author}
* @since ${date}
*/
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@Api(tags = "${table.comment!}")
@ApiResource(name = "${table.comment!}", path = "/${cfg.module}/${table.entityName? uncap_first}")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Autowired
    private ${table.serviceName} service;

    @ApiOperation(value ="获取${table.comment!}明细")
    @GetResource(name="获取${table.comment!}",path = "/detail")
    @BusinessLog(title ="获取${table.comment!}明细",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData detail(@RequestParam Long id)
    {
        return ResponseData.success( service.queryById(id));

    }
    @ApiOperation(value ="保存${table.comment!}")
    @PostResource(name="保存${table.comment!}",path = "/save")
    @BusinessLog(title ="保存${table.comment!}",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData save(@RequestBody @Valid ${entity}Param param)
    {
        service.save(param);
        return ResponseData.success();
    }
}
</#if>
