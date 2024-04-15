package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

 /**
 * 平台货件接收地址;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "平台货件接收地址",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsShipToRecResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    private BigDecimal lfrId ;
 
    /** 添加日期 */
    @ApiModelProperty(value = "添加日期")
    private Date sysAddDate ;
 
    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String sysPerName ;
 
    /** 最后更新日期 */
    @ApiModelProperty(value = "最后更新日期")
    private Date sysUpdDatetime ;
 
    /** 电商平台 */
    @ApiModelProperty(value = "电商平台")
    @ExcelProperty(value ="平台", index = 0)
    private String elePlatformName ;
 
    /** 国家/地区 */
    @ApiModelProperty(value = "国家/地区")
    @ExcelProperty(value ="站点", index = 1)
    private String countryCode ;
 
    /** 城市 */
    @ApiModelProperty(value = "城市")
    @ExcelProperty(value ="城市", index = 3)
    private String city ;
 
    /** 州/省/郡 */
    @ApiModelProperty(value = "州/省/郡")
    @ExcelProperty(value ="州", index = 4)
    private String state ;
 
    /** 收货地址1 */
    @ApiModelProperty(value = "收货地址1")
    @ExcelProperty(value ="收货地址1", index = 5)
    private String logRecAddress1 ;
 
    /** 收货地址2 */
    @ApiModelProperty(value = "收货地址2")
    @ExcelProperty(value ="收货地址2", index = 6)
    private String logRecAddress2 ;
 
    /** 收货地址3 */
    @ApiModelProperty(value = "收货地址3")
    @ExcelProperty(value ="收货地址3", index = 7)
    private String logRecAddress3 ;
 
    /** 邮政编码 */
    @ApiModelProperty(value = "邮政编码")
    @ExcelProperty(value ="邮政编码", index = 8)
    private String logRecZip ;
 
    /** 收件人 */
    @ApiModelProperty(value = "收件人")
    @ExcelProperty(value ="收件人", index = 9)
    private String logRecPerson ;
 
    /** 收件公司 */
    @ApiModelProperty(value = "收件公司")
    @ExcelProperty(value ="收件公司", index = 11)
    private String logRecCompany ;
 
    /** 收件电话 */
    @ApiModelProperty(value = "收件电话")
    @ExcelProperty(value ="收件电话", index = 10)
    private String logRecPersonTel ;
 
    /** 收货仓名称 */
    @ApiModelProperty(value = "收货仓名称")
    @ExcelProperty(value ="收货仓名称", index = 2)
    private String logRecHouseName ;


}