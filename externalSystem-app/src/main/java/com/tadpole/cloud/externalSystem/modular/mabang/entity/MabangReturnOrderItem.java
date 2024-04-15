package com.tadpole.cloud.externalSystem.modular.mabang.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 马帮退货单明细项列表
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
@TableName("MABANG_RETURN_ORDER_ITEM")
@ExcelIgnoreUnannotated
public class MabangReturnOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 主键=PLAT_ORD_ID+序号 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 复合订单编号：equals("Mercadolibre") ? Orders.getPlatformOrderId() : Orders.getSalesRecordNumber() */
    @TableField("PLAT_ORD_ID")
    private String platOrdId;

    /** 订单编号 */
    @TableField("PLATFORM_ORDER_ID")
    private String platformOrderId;

    /** 平台交易号 */
    @TableField("SALES_RECORD_NUMBER")
    private String salesRecordNumber;

    /** 商品图片地址 */
    @TableField("PICTUREURL")
    private String pictureurl;

    /** 产品单位 */
    @TableField("PRODUCTUNIT")
    private String productunit;

    /** 买家购买数量 */
    @TableField("QUANTITY")
    private BigDecimal quantity;

    /** 退货后实际入库数量 */
    @TableField("QUANTITY1")
    private BigDecimal quantity1;

    /** 马帮返回字段，无说明用途 */
    @TableField("QUANTITY2")
    private BigDecimal quantity2;

    /** 售价 */
    @TableField("SELLPRICE")
    private BigDecimal sellprice;

    /** 多物品属性 */
    @TableField("SPECIFICS")
    private String specifics;

    /** 状态1待处理2验货入库3自然耗损 */
    @TableField("STATUS")
    private BigDecimal status;

    /** 仓位 */
    @TableField("STOCKGRID")
    private String stockgrid;

    /** 库存SKU */
    @TableField("STOCKSKU")
    private String stocksku;

    /** 仓库编号 */
    @TableField("STOCKWAREHOUSEID")
    private String stockwarehouseid;

    /** 仓库名称 */
    @TableField("STOCKWAREHOUSENAME")
    private String stockwarehousename;

    /** 商品名称 */
    @TableField("TITLE")
    private String title;

    /** 马帮返回字段，无说明用途 */
    @TableField("INSPECTION_TIME")
    private Date inspectionTime;

    /** 同步方式（0 ：系统同步,1：手动人工同步） */
    @TableField("SYNC_TYPE")
    private String syncType;

    /** 同步时间 */
    @TableField("SYNC_TIME")
    private Date syncTime;

    /** 同步状态（0 ：同步失败,1：同步成功） */
    @TableField("SYNC_STATUS")
    private String syncStatus;

    /** 同步成功次数 */
    @TableField("SYNC_SUCCESS_TIMES")
    private BigDecimal syncSuccessTimes;

    /** 同步失败次数 */
    @TableField("SYNC_FAIL_TIMES")
    private BigDecimal syncFailTimes;

    /** 最后一次同步结果消息内容 */
    @TableField("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

}