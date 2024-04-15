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
 * 物流单尾程信息;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流单尾程信息",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsListToEndRouteParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    private BigDecimal id ;
 
    /** 发货批次号 */
    @ApiModelProperty(value = "发货批次号")
    private String lhrCode ;
 
    /** 头程物流单号 */
    @ApiModelProperty(value = "头程物流单号")
    private String lhrOddNumb ;
 
    /** 尾程物流单号 */
    @ApiModelProperty(value = "尾程物流单号")
    private String lerOddNumb ;
 
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
 
    /** 发货件数 */
    @ApiModelProperty(value = "发货件数")
    private Integer lerOutGoodNum ;
 
    /** 发货重量 */
    @ApiModelProperty(value = "发货重量")
    private BigDecimal lerOutGoodWeight ;
 
    /** 发货体积 */
    @ApiModelProperty(value = "发货体积")
    private BigDecimal lerOutGoodVolume ;
 
    /** 尾程单状态 */
    @ApiModelProperty(value = "尾程单状态")
    private String lerState ;
 
    /** 尾程单状态流程说明 */
    @ApiModelProperty(value = "尾程单状态流程说明")
    private String lerStateNote ;
 
    /** 货运方式1 */
    @ApiModelProperty(value = "货运方式1")
    private String logTraMode1 ;
 
    /** 尾程物流单链接 */
    @ApiModelProperty(value = "尾程物流单链接")
    private String logEndRouteLink ;
 
    /** 预估计费方式 */
    @ApiModelProperty(value = "预估计费方式")
    private String lerPreChargType ;
 
    /** 预估物流费 */
    @ApiModelProperty(value = "预估物流费")
    private BigDecimal lerPreLogFee ;
 
    /** 预估燃油附加费 */
    @ApiModelProperty(value = "预估燃油附加费")
    private BigDecimal lerPreLogFuelFee ;
 
    /** 预估物流附加费合计 */
    @ApiModelProperty(value = "预估物流附加费合计")
    private BigDecimal lerPreLogSurFeeTotal ;
 
    /** 预估物流附加费明细 */
    @ApiModelProperty(value = "预估物流附加费明细")
    private String lerPreLogSurFeeDet ;
 
    /** 预估税费合计 */
    @ApiModelProperty(value = "预估税费合计")
    private BigDecimal lerPreLogTaxFeeTotal ;
 
    /** 预估税费明细 */
    @ApiModelProperty(value = "预估税费明细")
    private String lerPreLogTaxFeeDet ;
 
    /** 预估总费用(系统计算) */
    @ApiModelProperty(value = "预估总费用(系统计算)")
    private BigDecimal lerPreLogFeeTotal ;
 
    /** 开始日期 */
    @ApiModelProperty(value = "开始日期")
    private Date lerBegDate ;
 
    /** 派送日期 */
    @ApiModelProperty(value = "派送日期")
    private Date lerSendDate ;
 
    /** 签收日期 */
    @ApiModelProperty(value = "签收日期")
    private Date lerSignDate ;
 
    /** 延期天数 */
    @ApiModelProperty(value = "延期天数")
    private Integer lerDelayDay ;
 
    /** 预估旺季附加费 */
    @ApiModelProperty(value = "预估旺季附加费")
    private BigDecimal lerPreLogBusySeasonAddFee ;
 
    /** 预估附加费及杂费 */
    @ApiModelProperty(value = "预估附加费及杂费")
    private BigDecimal lerPreLogAddAndSundryFee ;
 
    /** 预估报关费 */
    @ApiModelProperty(value = "预估报关费")
    private BigDecimal lerPreLogCustDlearanceFee ;
 
    /** 预估清关费 */
    @ApiModelProperty(value = "预估清关费")
    private BigDecimal lerPreLogCustClearanceFee ;
 
    /** 预估税费 */
    @ApiModelProperty(value = "预估税费")
    private BigDecimal lerPreLogTaxFee ;
 
    /** 预估总费用(人工维护) */
    @ApiModelProperty(value = "预估总费用(人工维护)")
    private BigDecimal lerPreLogFeeTotalManual ;
 
    /** 预估使用的计费量 */
    @ApiModelProperty(value = "预估使用的计费量")
    private BigDecimal lerLogFeeWeight ;
 
    /** 计费币种 */
    @ApiModelProperty(value = "计费币种")
    private String lerLogComfirmBillCurrency ;
 
    /** 预估单价 */
    @ApiModelProperty(value = "预估单价")
    private BigDecimal lerPreLogUnitPrice ;
 
    /** 预估单价类型 */
    @ApiModelProperty(value = "预估单价类型")
    private String lerPreLogUnitPriceType ;
 
    /** 预估附加费及杂费备注 */
    @ApiModelProperty(value = "预估附加费及杂费备注")
    private String lerPreLogAddAndSundryFeeRemark ;

    /** 单价 */
    @ApiModelProperty(value = "单价")
    private BigDecimal lerLogUnitPrice ;

    /** 燃油附加费 */
    @ApiModelProperty(value = "燃油附加费")
    private BigDecimal lerLogFuelFee ;

    /** 旺季附加费 */
    @ApiModelProperty(value = "旺季附加费")
    private BigDecimal lerLogBusySeasonAddFee ;

    /** 附加费及杂费 */
    @ApiModelProperty(value = "附加费及杂费")
    private BigDecimal lerLogAddAndSundryFee ;

    /** 报关费 */
    @ApiModelProperty(value = "报关费")
    private BigDecimal lerLogCustDlearanceFee ;

    /** 清关费 */
    @ApiModelProperty(value = "清关费")
    private BigDecimal lerLogCustClearanceFee ;

    /** 税费 */
    @ApiModelProperty(value = "税费")
    private BigDecimal lerLogTaxFee ;

    /** 物流费 */
    @ApiModelProperty(value = "物流费")
    private BigDecimal lerLogFee ;

    /** 预估总费用 */
    @ApiModelProperty(value = "预估总费用")
    private BigDecimal lerPreLogFeeTotalNew ;

    /** 总费用 */
    @ApiModelProperty(value = "总费用")
    private BigDecimal lerLogFeeTotalNew ;


}