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
@ExcelIgnoreUnannotated
@TableName("MABANG_RETURN_ORDER_ITEM")
public class MabangReturnOrderItemResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 主键=PLAT_ORD_ID+序号 */
   @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 复合订单编号：equals("Mercadolibre") ? Orders.getPlatformOrderId() : Orders.getSalesRecordNumber() */
    @ApiModelProperty("PLAT_ORD_ID")
    private String platOrdId;

    /** 订单编号 */
    @ApiModelProperty("PLATFORM_ORDER_ID")
    private String platformOrderId;

    /** 平台交易号 */
    @ApiModelProperty("SALES_RECORD_NUMBER")
    private String salesRecordNumber;

    /** 商品图片地址 */
    @ApiModelProperty("PICTUREURL")
    private String pictureurl;

    /** 产品单位 */
    @ApiModelProperty("PRODUCTUNIT")
    private String productunit;

    /** 买家购买数量 */
    @ApiModelProperty("QUANTITY")
    private BigDecimal quantity;

    /** 退货后实际入库数量 */
    @ApiModelProperty("QUANTITY1")
    private BigDecimal quantity1;

    /** 马帮返回字段，无说明用途 */
    @ApiModelProperty("QUANTITY2")
    private BigDecimal quantity2;

    /** 售价 */
    @ApiModelProperty("SELLPRICE")
    private BigDecimal sellprice;

    /** 多物品属性 */
    @ApiModelProperty("SPECIFICS")
    private String specifics;

    /** 状态1待处理2验货入库3自然耗损 */
    @ApiModelProperty("STATUS")
    private BigDecimal status;

    /** 仓位 */
    @ApiModelProperty("STOCKGRID")
    private String stockgrid;

    /** 库存SKU */
    @ApiModelProperty("STOCKSKU")
    private String stocksku;

    /** 仓库编号 */
    @ApiModelProperty("STOCKWAREHOUSEID")
    private String stockwarehouseid;

    /** 仓库名称 */
    @ApiModelProperty("STOCKWAREHOUSENAME")
    private String stockwarehousename;

    /** 商品名称 */
    @ApiModelProperty("TITLE")
    private String title;

    /** 马帮返回字段，无说明用途 */
    @ApiModelProperty("INSPECTION_TIME")
    private Date inspectionTime;

    /** 同步方式（0 ：系统同步,1：手动人工同步） */
    @ApiModelProperty("SYNC_TYPE")
    private String syncType;

    /** 同步时间 */
    @ApiModelProperty("SYNC_TIME")
    private Date syncTime;

    /** 同步状态（0 ：同步失败,1：同步成功） */
    @ApiModelProperty("SYNC_STATUS")
    private String syncStatus;

    /** 同步成功次数 */
    @ApiModelProperty("SYNC_SUCCESS_TIMES")
    private BigDecimal syncSuccessTimes;

    /** 同步失败次数 */
    @ApiModelProperty("SYNC_FAIL_TIMES")
    private BigDecimal syncFailTimes;

    /** 最后一次同步结果消息内容 */
    @ApiModelProperty("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

}