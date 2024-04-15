package com.tadpole.cloud.externalSystem.modular.mabang.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 销售退货单明细项
    * </p>
*
* @author cyt
* @since 2022-08-24
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("SALE_RETURN_ORDER_ITEM")
public class SaleReturnOrderItemResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 销售出库单id */
    @ApiModelProperty("SALE_OUT_ORDER_ID")
    private String saleOutOrderId;

    /** 马帮退货订单明细项id */
    @ApiModelProperty("MA_RETURN_ORDER_ITEM_ID")
    private String maReturnOrderItemId;

    /** K3销售出库单号,接口传递给k3 */
    @ApiModelProperty("BILL_NO")
    private String billNo;

    /** 事业部 */
    @ApiModelProperty("DEPARTMENT")
    private String department;

    /** Team */
    @ApiModelProperty("TEAM")
    private String team;

    /** 平台名称 */
    @ApiModelProperty("PLAT_NAME")
    private String platName;

    /** 店铺名称 */
    @ApiModelProperty("SHOP_NAME")
    private String shopName;

    /** 站点:留3个字符位置，全球有一个国家简码是3个字符 */
    @ApiModelProperty("SITE_CODE")
    private String siteCode;

    /** 仓库名称:根据仓库编码去K3仓库列表作业查询返回仓库名称，平台发展中心这块业务比较特殊，无法直接在MCMS中组装 */
    @ApiModelProperty("WAREHOUSE_NAME")
    private String warehouseName;

    /** 仓库编码:K3中约定客户编码（不带尾巴的金蝶组织编码）对应的仓库编码是一样 */
    @ApiModelProperty("WAREHOUSE_ID")
    private String warehouseId;

    /** 平台订单编号:传马帮已发货订单作业的platOrdId字段值 */
    @ApiModelProperty("PLAT_ORD_ID")
    private String platOrdId;

    /** 平台SKU */
    @ApiModelProperty("PLAT_SKU")
    private String platSku;

    /** 马帮退货单创建时间:取值：传马帮已退货订单的马帮退货单创建时间字段 */
    @ApiModelProperty("SHIPPED_TIME")
    private Date shippedTime;

    /** 退货入库数量:传马帮已退货订单作业的inQuantity字段值 */
    @ApiModelProperty("RETURN_QTY")
    private BigDecimal returnQty;

    /** 物料入库状态:子单身物料编码的入库状态 */
    @ApiModelProperty("INBOUND_STATUS")
    private String inboundStatus;

    /** 退货单状态,取值：传马帮已退货订单的退货单状态字段 */
    @ApiModelProperty("RTN_ORD_STATUS")
    private String rtnOrdStatus;

    /** 登记人名称:传马帮已退货订单的登记人字段 */
    @ApiModelProperty("EMPLOYEE_NAME")
    private String employeeName;

    /** 是否作废Y/N，默认N（代表当前item是否作废） */
    @ApiModelProperty("IS_INVALID")
    private String isInvalid;

    /** 同步时间 */
    @ApiModelProperty("SYNC_TIME")
    private Date syncTime;

    /** 同步状态（-1:数据初始化，0 ：同步失败,1：同步成功） */
    @ApiModelProperty("SYNC_STATUS")
    private String syncStatus;

    /** 最后一次同步结果消息内容 */
    @ApiModelProperty("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

    /** 库存SKU */
    @ApiModelProperty("STOCK_SKU")
    private String stockSku;

}