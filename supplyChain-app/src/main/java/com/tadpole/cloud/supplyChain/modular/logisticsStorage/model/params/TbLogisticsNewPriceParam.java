package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
public class TbLogisticsNewPriceParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** 物流价格编号 */
    @ApiModelProperty(value = "物流价格编号")
    private BigDecimal pkLogpId ;
 
    /** 创建日期 */
    @ApiModelProperty(value = "创建日期")
    private Date sysCreateDate ;
 
    /** 员工编号 */
    @ApiModelProperty(value = "员工编号")
    private String sysPerCode ;
 
    /** 员工姓名 */
    @ApiModelProperty(value = "员工姓名")
    private String sysPerName ;
 
    /** 最后更新日期 */
    @ApiModelProperty(value = "最后更新日期")
    private Date sysUpdDatetime ;
 
    /** 最后更新人编号 */
    @ApiModelProperty(value = "最后更新人编号")
    private String sysUpdPerCode ;
 
    /** 最后更新人姓名 */
    @ApiModelProperty(value = "最后更新人姓名")
    private String sysUpdPerName ;
 
    /** 物流账号 */
    @ExcelProperty(value ="物流账号*")
    @ApiModelProperty(value = "物流账号")
    private String busLcCode ;
 
    /** 站点 */
    @ExcelProperty(value ="站点*")
    @ApiModelProperty(value = "站点")
    private String busLpCountryCode ;
 
    /** 分区号 */
    @ExcelProperty(value ="国家地区*")
    @ApiModelProperty(value = "分区号--新平台国家地区 比如ONT8")
    private String busLspNum ;
 
    /** 货运方式1 */
    @ApiModelProperty(value = "货运方式1")
    private String busLogTraMode1 ;
 
    /** 发货方式 */
    @ExcelProperty(value ="发货方式*")
    @ApiModelProperty(value = "发货方式")
    private String busLogTraMode2 ;
 
    /** 物流渠道 */
    @ExcelProperty(value ="物流渠道*")
    @ApiModelProperty(value = "物流渠道")
    private String busLogSeaTraRoute ;
 
    /** 红蓝单 */
    @ExcelProperty(value ="红蓝单")
    @ApiModelProperty(value = "红蓝单")
    private String busLogRedOrBlueList ;
 
    /** 货物特性 */
    @ExcelProperty(value ="货物特性")
    @ApiModelProperty(value = "货物特性")
    private String busLogGoodCharacter ;
 
    /** 是否含税 */
    @ApiModelProperty(value = "是否含税")
    private Integer busLogpIsIncTax ;

    /** 是否含税 */
    @ExcelProperty(value ="是否含税*")
    @ApiModelProperty(value = "是否含税")
    private String busLogpIsIncTaxDesc ;
 
    /** 计费币种 */
    @ExcelProperty(value ="计费币种*")
    @ApiModelProperty(value = "计费币种")
    private String busLogpChargCurrency ;
 
    /** 计费方式 */
    @ExcelProperty(value ="计费方式*")
    @ApiModelProperty(value = "计费方式")
    private String busLogpChargType ;

    /** 物流商编码;物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    private String lpCode ;

    @ApiModelProperty(value = "物流商简称")
    private String lpSimpleName ;

    /** 重量KG(>) */
    @ExcelProperty(value ="重量KG(>)")
    @ApiModelProperty(value = "重量KG(>)")
    private BigDecimal busLogpDetWeightGreater ;

    /** 重量KG(<) */
    @ExcelProperty(value ="重量KG(<)")
    @ApiModelProperty(value = "重量KG(<)")
    private BigDecimal busLogpDetWeightLess ;

    /** 体积CBM(>) */
    @ExcelProperty(value ="体积CBM(>)")
    @ApiModelProperty(value = "体积CBM(>)")
    private BigDecimal busLogpDetVolumeGreater ;

    /** 体积CBM(<) */
    @ExcelProperty(value ="体积CBM(<)")
    @ApiModelProperty(value = "体积CBM(<)")
    private BigDecimal busLogpDetVolumeLess ;

    /** 单价费用 */
    @ExcelProperty(value ="单价费用*")
    @ApiModelProperty(value = "单价费用")
    private BigDecimal busLogpDetUnitPrice ;

    /** 报关费 */
    @ExcelProperty(value ="报关费")
    @ApiModelProperty(value = "报关费")
    private BigDecimal busLogpDetCustDlearanceFee ;

    /** 清关费 */
    @ExcelProperty(value ="清关费")
    @ApiModelProperty(value = "清关费")
    private BigDecimal busLogpDetCustClearanceFee ;

    /** 旺季附加费KG */
    @ExcelProperty(value ="旺季附加费KG")
    @ApiModelProperty(value = "旺季附加费KG")
    private BigDecimal busLogpDetBusySeasonAddFee ;

    /** 燃油附加税率 */
    @ExcelProperty(value ="燃油附加税率")
    @ApiModelProperty(value = "燃油附加税率")
    private BigDecimal busLogpDetFuelFee ;

    /** 附加费及杂费KG */
    @ExcelProperty(value ="附加费及杂费KG")
    @ApiModelProperty(value = "附加费及杂费KG")
    private BigDecimal busLogpDetAddAndSundryFee ;

    /** 备注 */
    @ExcelProperty(value ="备注")
    @ApiModelProperty(value = "备注")
    private String busLogpDetRemark ;

    /** 适用开始日期 */
    @ExcelProperty(value ="适用开始日期*")
    @ApiModelProperty(value = "适用开始日期")
    private Date busLogpDetAppStartDate ;

    /** 导入异常信息备注 */
    @ExcelProperty(value ="导入异常信息备注")
    @ApiModelProperty("导入异常信息备注")
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 11)
    private String uploadRemark;

    /** 价格主数据唯一值 */
    @ApiModelProperty(value = "价格主数据唯一值")
    private String olnyKey ;
}