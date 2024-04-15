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
 * 物流投入预估分担报告-新-暂时不需要;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流投入预估分担报告-新-暂时不需要",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsInputPreShareNewReportParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** 物流价格明细编号 */
    @ApiModelProperty(value = "物流价格明细编号")
    private BigDecimal pkLogisrId ;
 
    /** 发货日期 */
    @ApiModelProperty(value = "发货日期")
    private Date busLogisrSendGoodDate ;
 
    /** 平台 */
    @ApiModelProperty(value = "平台")
    private String busLogisrPlatformName ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    private String busLogisrShopNameSimple ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    private String busLogisrCountryCode ;
 
    /** 需求部门 */
    @ApiModelProperty(value = "需求部门")
    private String busLogisrDepart ;
 
    /** 需求Team */
    @ApiModelProperty(value = "需求Team")
    private String busLogisrTeam ;
 
    /** 发货方式1 */
    @ApiModelProperty(value = "发货方式1")
    private String busLogisrTraMode1 ;
 
    /** 运输方式 */
    @ApiModelProperty(value = "运输方式")
    private String busLogisrTraMode2 ;
 
    /** 收获仓 */
    @ApiModelProperty(value = "收获仓")
    private String busLogisrStoreHouseName ;
 
    /** 出货清单号 */
    @ApiModelProperty(value = "出货清单号")
    private String busLogisrPackCode ;
 
    /** 物流单号 */
    @ApiModelProperty(value = "物流单号")
    private String busLogisrOddNumb ;
 
    /** 物料编码 */
    @ApiModelProperty(value = "物料编码")
    private String busLogisrMatCode ;
 
    /** SKU */
    @ApiModelProperty(value = "SKU")
    private String busLogisrSku ;
 
    /** FNSKU */
    @ApiModelProperty(value = "FNSKU")
    private String busLogisrFnsku ;
 
    /** 调拨申请单号 */
    @ApiModelProperty(value = "调拨申请单号")
    private String busLogisrPackDirectCode ;
 
    /** 数量 */
    @ApiModelProperty(value = "数量")
    private Integer busLogisrQty ;
 
    /** 排序 */
    @ApiModelProperty(value = "排序")
    private Integer busLogisrSort ;
 
    /** 运费 */
    @ApiModelProperty(value = "运费")
    private BigDecimal busLogisrTranFee ;
 
    /** 包装体积cm3 */
    @ApiModelProperty(value = "包装体积cm3")
    private BigDecimal busLogisrBoxVolume ;
 
    /** 体积重g */
    @ApiModelProperty(value = "体积重g")
    private BigDecimal busLogisrVolumeWeight ;
 
    /** 毛重g */
    @ApiModelProperty(value = "毛重g")
    private BigDecimal busLogisrBoxWeight2Org ;
 
    /** 计费重g */
    @ApiModelProperty(value = "计费重g")
    private BigDecimal busLogisrBillWeight ;
 
    /** 总计费重KG */
    @ApiModelProperty(value = "总计费重KG")
    private BigDecimal busLogisrBillWeightTotal ;
 
    /** 费用分摊(单价) */
    @ApiModelProperty(value = "费用分摊(单价)")
    private BigDecimal busLogisrUnitPrice ;
 
    /** 费用分摊/个 */
    @ApiModelProperty(value = "费用分摊/个")
    private BigDecimal busLogisrCostShare ;
 
    /** 总额 */
    @ApiModelProperty(value = "总额")
    private BigDecimal busLogisrTotalSum ;
 
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date sysCreateDate ;
 
    /** 创建人姓名 */
    @ApiModelProperty(value = "创建人姓名")
    private String sysPerName ;
 
    /** 创建人编号 */
    @ApiModelProperty(value = "创建人编号")
    private String sysPerCode ;


}