package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;

 /**
 * 物流商的价格信息-明细;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流商的价格信息-明细",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsNewPriceDetParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** 物流价格明细编号 */
    @ApiModelProperty(value = "物流价格明细编号")
    private BigDecimal pkLogpDetId ;
 
    /** 物流价格编号 */
    @ApiModelProperty(value = "物流价格编号")
    private BigDecimal pkLogpId ;
 
    /** 创建日期 */
    @ApiModelProperty(value = "创建日期")
    private Date sysAddDate ;
 
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
 
    /** 重量KG(>) */
    @ApiModelProperty(value = "重量KG(>)")
    private BigDecimal busLogpDetWeightGreater ;
 
    /** 重量KG(<) */
    @ApiModelProperty(value = "重量KG(<)")
    private BigDecimal busLogpDetWeightLess ;
 
    /** 体积CBM(>) */
    @ApiModelProperty(value = "体积CBM(>)")
    private BigDecimal busLogpDetVolumeGreater ;
 
    /** 体积CBM(<) */
    @ApiModelProperty(value = "体积CBM(<)")
    private BigDecimal busLogpDetVolumeLess ;
 
    /** 单价费用 */
    @ApiModelProperty(value = "单价费用")
    private BigDecimal busLogpDetUnitPrice ;
 
    /** 报关费 */
    @ApiModelProperty(value = "报关费")
    private BigDecimal busLogpDetCustDlearanceFee ;
 
    /** 清关费 */
    @ApiModelProperty(value = "清关费")
    private BigDecimal busLogpDetCustClearanceFee ;
 
    /** 旺季附加费KG */
    @ApiModelProperty(value = "旺季附加费KG")
    private BigDecimal busLogpDetBusySeasonAddFee ;
 
    /** 燃油附加税率 */
    @ApiModelProperty(value = "燃油附加税率")
    private BigDecimal busLogpDetFuelFee ;
 
    /** 附加费及杂费KG */
    @ApiModelProperty(value = "附加费及杂费KG")
    private BigDecimal busLogpDetAddAndSundryFee ;
 
    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String busLogpDetRemark ;
 
    /** 适用开始日期 */
    @ApiModelProperty(value = "适用开始日期")
    private Date busLogpDetAppStartDate ;
 
    /** 适用结束日期 */
    @ApiModelProperty(value = "适用结束日期")
    private Date busLogpDetAppEndDate ;
 
    /** 价格明细状态(-1:失效,0:预备中,1:生效) */
    @ApiModelProperty(value = "价格明细状态(-1:失效,0:预备中,1:生效)")
    private Integer busLogpDetStatus ;
 
    /** 启用状态;'已启用,已禁用' */
    @ApiModelProperty(value = "启用状态")
    private String busLogpDetUseStatus ;


}