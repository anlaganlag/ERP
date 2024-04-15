package com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

 /**
 * 物流投入预估分担报告-暂时不需要;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_input_pre_share_report")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsInputPreShareReport implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 物流价格明细编号 */
    @ApiModelProperty(value = "物流价格明细编号")
    @TableId (value = "pk_logisr_id", type = IdType.AUTO)
    @TableField("pk_logisr_id")
    private BigDecimal pkLogisrId ;
 
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
 
    /** 物料编码 */
    @ApiModelProperty(value = "物料编码")
    @TableField("bus_logisr_mat_code")
    private String busLogisrMatCode ;
 
    /** 运营大类 */
    @ApiModelProperty(value = "运营大类")
    @TableField("bus_logisr_mat_operate_cate")
    private String busLogisrMatOperateCate ;
 
    /** 数量 */
    @ApiModelProperty(value = "数量")
    @TableField("bus_logisr_qty")
    private Integer busLogisrQty ;
 
    /** 单价 */
    @ApiModelProperty(value = "单价")
    @TableField("bus_logisr_unit_price")
    private BigDecimal busLogisrUnitPrice ;
 
    /** 费用分摊 */
    @ApiModelProperty(value = "费用分摊")
    @TableField("bus_logisr_cost_share")
    private BigDecimal busLogisrCostShare ;
 
    /** 发货日期 */
    @ApiModelProperty(value = "发货日期")
    @TableField("bus_lhr_send_good_date")
    private Date busLhrSendGoodDate ;
 
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
 
    /** 计费币种 */
    @ApiModelProperty(value = "计费币种")
    @TableField("bus_log_comfirm_bill_currency")
    private String busLogComfirmBillCurrency ;


}