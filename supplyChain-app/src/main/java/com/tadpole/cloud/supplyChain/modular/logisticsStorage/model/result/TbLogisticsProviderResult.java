package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

 /**
 * 物流供应商;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流供应商",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsProviderResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    @ExcelProperty(value ="物流商编码")
    private String lpCode ;
 
    /** 物流商名称 */
    @ApiModelProperty(value = "物流商名称")
    @ExcelProperty(value ="物流商名称")
    private String lpName ;
 
    /** 物流商简称 */
    @ApiModelProperty(value = "物流商简称")
    @ExcelProperty(value ="物流商简称")
    private String lpSimpleName ;
 
    /** 通讯地址 */
    @ApiModelProperty(value = "通讯地址")
    @ExcelProperty(value ="通讯地址")
    private String lpAddress ;
 
    /** 统一社会信用代码 */
    @ApiModelProperty(value = "统一社会信用代码")
    @ExcelProperty(value ="统一社会信用代码")
    private String lpUniSocCreCode ;
 
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    @ExcelProperty(value ="更新时间")
    private Date sysUpdDatetime ;
 
    /** 启用禁用状态;A:启用，B:禁用 */
    @ApiModelProperty(value = "启用禁用状态A:启用，B:禁用")
    private String forbidStatus ;

    /** 启用禁用状态描述：启用，禁用 */
    @ApiModelProperty(value = "启用禁用状态描述：启用，禁用")
    @ExcelProperty(value ="启用禁用状态")
    private String forbidStatusDesc ;

    /** 物流单链接模板 */
    @ApiModelProperty(value = "物流单链接模板")
    @ExcelProperty(value ="物流单链接模板")
    private String logListLinkTemp ;

    /** 数据来源 */
    @ApiModelProperty(value = "数据来源")
    private String dataSource ;

}