package com.tadpole.cloud.operationManagement.modular.stock.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 采购订单
 * </p>
 *
 * @author lsy
 * @since 2022-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("STOCK_PURCHASE_ORDERS")
public class PurchaseOrders2Param extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 数据记录ID */
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 采购申请单类型 */
    @ApiModelProperty("采购申请单类型List")
    private List<String> billTypeList;
    /** 事业部审核时间 */
    @ApiModelProperty("事业部审核时间")
    private Date pCreateTimeState;
    private Date pCreateTimeEnd;
    /** 最近一次下单时间 */
    @ApiModelProperty("ORDER_LAST_TIME")
    private Date orderLastTimeState;
    private Date orderLastTimeEnd;
    /** 事业部 */
    @ApiModelProperty("DEPARTMENT")
    private List<String> departmentList;
    /** Team组 */
    @ApiModelProperty("TEAM")
    private List<String> teamList;
    /** 物料编码 */
    @ApiModelProperty("MATERIAL_CODE")
    private List<String> materialCodeList;
    /** 运营大类 */
    @ApiModelProperty("PRODUCT_TYPE")
    private List<String> productTypeList;

    /** 产品名称 */
    private List<String> productNameList;



    /** 节日标签 */
    @ApiModelProperty("FESTIVAL_LABEL")
    private String festivalLabel;
    /** 季节标签 */
    @ApiModelProperty("SEASON_LABEL")
    private String seasonLabel;
    /** 国内可用库存 */
    @ApiModelProperty("CANUSEQTY")
    private BigDecimal canuseqtyMin;
    private BigDecimal canuseqtyMax;
    /** 采购未完成数量 */
    @ApiModelProperty("UNPURCHASE")
    private BigDecimal unpurchaseMin;
    private BigDecimal unpurchaseMax;
    /** 申请审核中数量 */
    @ApiModelProperty("APPROVE_QTY")
    private BigDecimal approveQtyMin;
    private BigDecimal approveQtyMax;
    /** AZ海外总库存 */
    @ApiModelProperty("TOTAL_VOLUME")
    private BigDecimal totalVolumeMin;
    private BigDecimal totalVolumeMax;
    /** 日均销量 */
    @ApiModelProperty("DAYAVGQTY")
    private BigDecimal dayavgqtyMin;
    private BigDecimal dayavgqtyMax;
    /** 采购申请数量 */
    @ApiModelProperty("PURCHASE_APPLY_QTY")
    private BigDecimal purchaseApplyQtyMin;
    private BigDecimal purchaseApplyQtyMax;
    /** 申请备货后周转天数 */
    @ApiModelProperty("TURNOVER_DAYS")
    private BigDecimal turnoverDaysMin;
    private BigDecimal turnoverDaysMax;
    /** 期望交期 */
    @ApiModelProperty("EXPECTED_DELIVERY_DATE")
    private Date expectedDeliveryDateState;
    private Date expectedDeliveryDateEnd;

}