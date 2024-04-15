package ${cfg.result};

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
<#list table.importPackages as pkg>
import ${pkg};
</#list>


/**
* <p>
* ${table.comment!}
* </p>
* @author ${author}
* @since ${date}
*/
@Data
public class ${entity}Result {
<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;
</#if>

<#if entityColumnConstant>
    <#list table.fields as field>
        public static final String ${field.name?upper_case} = "${field.name}";

    </#list>
</#if>
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
    <#if field.keyFlag>
    <#-- 主键 -->
     <#--   <#if field.keyIdentityFlag>
   @TableId(value = "${field.name}", type = IdType.AUTO)
        <#elseif idType??>
   @TableId(value = "${field.name}", type = IdType.${idType})
        <#elseif field.convert>
   @TableId("${field.name}")
        </#if>-->
    <#-- 普通字段 -->
    <#elseif field.fill??>
    <#-- -----   存在字段填充设置   ----->

        <#if field.convert>
    @ApiModelProperty(value = "${field.name}", fill = FieldFill.${field.fill})
        <#else>
    @ApiModelProperty(fill = FieldFill.${field.fill})</#if>
    <#elseif field.convert>
    @ApiModelProperty("${field.comment}")
<#--    @ExcelProperty(value ="${field.comment}")-->
    </#if>
<#-- 乐观锁注解 -->
    <#if (versionFieldName!"") == field.name>
        @Version
    </#if>
<#-- 逻辑删除注解 -->
    <#if (logicDeleteFieldName!"") == field.name>
        @TableLogic
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->

}
