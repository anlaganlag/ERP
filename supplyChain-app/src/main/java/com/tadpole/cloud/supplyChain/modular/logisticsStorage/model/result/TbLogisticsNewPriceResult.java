package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

 /**
 * 物流商的价格信息;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流商的价格信息",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsNewPriceResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 物流价格编号 */
    @ApiModelProperty(value = "物流价格编号")
    @ExcelProperty(value ="物流价格编号")
    private BigDecimal pkLogpId ;

    /** 物流账号 */
    @ApiModelProperty(value = "物流账号")
    @ExcelProperty(value ="物流账号")
    private String busLcCode ;

    /** 物流商编码;物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    @ExcelProperty(value ="物流商编码")
    private String lpCode ;

    @ApiModelProperty(value = "物流商简称")
    @ExcelProperty(value ="物流商简称")
    private String lpSimpleName ;

    /** 物流商名称 */
    @ApiModelProperty(value = "物流商名称")
    @ExcelProperty(value ="物流商名称")
    private String lpName ;

    /** 物流单链接模板 */
    @ApiModelProperty(value = "物流单链接模板")
    @ExcelProperty(value ="物流单链接模板")
    private String logListLinkTemp ;
 
    /** 创建日期 */
    @ApiModelProperty(value = "创建日期")
    @ExcelProperty(value ="创建日期")
    private Date sysCreateDate ;
 
    /** 员工编号 */
    @ApiModelProperty(value = "员工编号")
    @ExcelProperty(value ="员工编号")
    private String sysPerCode ;
 
    /** 员工姓名 */
    @ApiModelProperty(value = "员工姓名")
    @ExcelProperty(value ="员工姓名")
    private String sysPerName ;
 
    /** 最后更新日期 */
    @ApiModelProperty(value = "最后更新日期")
    @ExcelProperty(value ="最后更新日期")
    private Date sysUpdDatetime ;
 
    /** 最后更新人编号 */
    @ApiModelProperty(value = "最后更新人编号")
    @ExcelProperty(value ="最后更新人编号")
    private String sysUpdPerCode ;
 
    /** 最后更新人姓名 */
    @ApiModelProperty(value = "最后更新人姓名")
    @ExcelProperty(value ="最后更新人姓名")
    private String sysUpdPerName ;
 

 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @ExcelProperty(value ="站点")
    private String busLpCountryCode ;
 
    /** 分区号 */
    @ApiModelProperty(value = "分区号--新平台叫国家地区")
    @ExcelProperty(value ="国家地区")
    private String busLspNum ;
 
    /** 货运方式1 */
    @ApiModelProperty(value = "货运方式1")
    @ExcelProperty(value ="货运方式1")
    private String busLogTraMode1 ;
 
    /** 运输方式 */
    @ApiModelProperty(value = "运输方式")
    @ExcelProperty(value ="运输方式")
    private String busLogTraMode2 ;
 
    /** 物流渠道 */
    @ApiModelProperty(value = "物流渠道")
    @ExcelProperty(value ="物流渠道")
    private String busLogSeaTraRoute ;
 
    /** 红蓝单 */
    @ApiModelProperty(value = "红蓝单")
    @ExcelProperty(value ="红蓝单")
    private String busLogRedOrBlueList ;
 
    /** 货物特性 */
    @ApiModelProperty(value = "货物特性")
    @ExcelProperty(value ="货物特性")
    private String busLogGoodCharacter ;
 
    /** 是否含税 */
    @ApiModelProperty(value = "是否含税")
    @ExcelProperty(value ="是否含税")
    private Integer busLogpIsIncTax ;
 
    /** 计费币种 */
    @ApiModelProperty(value = "计费币种")
    @ExcelProperty(value ="计费币种")
    private String busLogpChargCurrency ;
 
    /** 计费方式 */
    @ApiModelProperty(value = "计费方式")
    @ExcelProperty(value ="计费方式")
    private String busLogpChargType ;



    /** 价格唯一值 */
    @ApiModelProperty(value = "价格唯一值")
    private String olnyKey ;





 }