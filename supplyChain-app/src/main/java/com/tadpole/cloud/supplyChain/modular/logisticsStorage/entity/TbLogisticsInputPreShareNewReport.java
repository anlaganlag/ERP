package com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity;

import java.io.Serializable;
import java.lang.*;
import lombok.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

 /**
 * 物流投入预估分担报告-新-暂时不需要;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_input_pre_share_new_report")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsInputPreShareNewReport implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 物流价格明细编号 */
    @ApiModelProperty(value = "物流价格明细编号")
    @TableId (value = "pk_logisr_id", type = IdType.AUTO)
    @TableField("pk_logisr_id")
    private BigDecimal pkLogisrId ;
 
    /** 发货日期 */
    @ApiModelProperty(value = "发货日期")
    @TableField("bus_logisr_send_good_date")
    private Date busLogisrSendGoodDate ;
 
    /** 平台 */
    @ApiModelProperty(value = "平台")
    @TableField("bus_logisr_platform_name")
    private String busLogisrPlatformName ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    @TableField("bus_logisr_shop_name_simple")
    private String busLogisrShopNameSimple ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @TableField("bus_logisr_country_code")
    private String busLogisrCountryCode ;
 
    /** 需求部门 */
    @ApiModelProperty(value = "需求部门")
    @TableField("bus_logisr_depart")
    private String busLogisrDepart ;
 
    /** 需求Team */
    @ApiModelProperty(value = "需求Team")
    @TableField("bus_logisr_team")
    private String busLogisrTeam ;
 
    /** 发货方式1 */
    @ApiModelProperty(value = "发货方式1")
    @TableField("bus_logisr_tra_mode1")
    private String busLogisrTraMode1 ;
 
    /** 运输方式 */
    @ApiModelProperty(value = "运输方式")
    @TableField("bus_logisr_tra_mode2")
    private String busLogisrTraMode2 ;
 
    /** 收获仓 */
    @ApiModelProperty(value = "收获仓")
    @TableField("bus_logisr_store_house_name")
    private String busLogisrStoreHouseName ;
 
    /** 出货清单号 */
    @ApiModelProperty(value = "出货清单号")
    @TableField("bus_logisr_pack_code")
    private String busLogisrPackCode ;
 
    /** 物流单号 */
    @ApiModelProperty(value = "物流单号")
    @TableField("bus_logisr_odd_numb")
    private String busLogisrOddNumb ;
 
    /** 物料编码 */
    @ApiModelProperty(value = "物料编码")
    @TableField("bus_logisr_mat_code")
    private String busLogisrMatCode ;
 
    /** SKU */
    @ApiModelProperty(value = "SKU")
    @TableField("bus_logisr_sku")
    private String busLogisrSku ;
 
    /** FNSKU */
    @ApiModelProperty(value = "FNSKU")
    @TableField("bus_logisr_fnsku")
    private String busLogisrFnsku ;
 
    /** 调拨申请单号 */
    @ApiModelProperty(value = "调拨申请单号")
    @TableField("bus_logisr_pack_direct_code")
    private String busLogisrPackDirectCode ;
 
    /** 数量 */
    @ApiModelProperty(value = "数量")
    @TableField("bus_logisr_qty")
    private Integer busLogisrQty ;
 
    /** 排序 */
    @ApiModelProperty(value = "排序")
    @TableField("bus_logisr_sort")
    private Integer busLogisrSort ;
 
    /** 运费 */
    @ApiModelProperty(value = "运费")
    @TableField("bus_logisr_tran_fee")
    private BigDecimal busLogisrTranFee ;
 
    /** 包装体积cm3 */
    @ApiModelProperty(value = "包装体积cm3")
    @TableField("bus_logisr_box_volume")
    private BigDecimal busLogisrBoxVolume ;
 
    /** 体积重g */
    @ApiModelProperty(value = "体积重g")
    @TableField("bus_logisr_volume_weight")
    private BigDecimal busLogisrVolumeWeight ;
 
    /** 毛重g */
    @ApiModelProperty(value = "毛重g")
    @TableField("bus_logisr_box_weight2_org")
    private BigDecimal busLogisrBoxWeight2Org ;
 
    /** 计费重g */
    @ApiModelProperty(value = "计费重g")
    @TableField("bus_logisr_bill_weight")
    private BigDecimal busLogisrBillWeight ;
 
    /** 总计费重KG */
    @ApiModelProperty(value = "总计费重KG")
    @TableField("bus_logisr_bill_weight_total")
    private BigDecimal busLogisrBillWeightTotal ;
 
    /** 费用分摊(单价) */
    @ApiModelProperty(value = "费用分摊(单价)")
    @TableField("bus_logisr_unit_price")
    private BigDecimal busLogisrUnitPrice ;
 
    /** 费用分摊/个 */
    @ApiModelProperty(value = "费用分摊/个")
    @TableField("bus_logisr_cost_share")
    private BigDecimal busLogisrCostShare ;
 
    /** 总额 */
    @ApiModelProperty(value = "总额")
    @TableField("bus_logisr_total_sum")
    private BigDecimal busLogisrTotalSum ;
 
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @TableField("sys_create_date")
    private Date sysCreateDate ;
 
    /** 创建人姓名 */
    @ApiModelProperty(value = "创建人姓名")
    @TableField("sys_per_name")
    private String sysPerName ;
 
    /** 创建人编号 */
    @ApiModelProperty(value = "创建人编号")
    @TableField("sys_per_code")
    private String sysPerCode ;


}