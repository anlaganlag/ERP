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
    * 马帮退货单列表
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
@TableName("MABANG_RETURN_ORDER")
@ExcelIgnoreUnannotated
public class MabangReturnOrder implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 主键=PLAT_ORD_ID */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 复合订单编号：equals("Mercadolibre") ? Orders.getPlatformOrderId() : Orders.getSalesRecordNumber() */
    @TableField("PLAT_ORD_ID")
    private String platOrdId;

    /** 订单编号 */
    @TableField("PLATFORM_ORDER_ID")
    private String platformOrderId;

    /** 店铺编号 */
    @TableField("SHOP_ID")
    private BigDecimal shopId;

    /** 店铺名称 */
    @TableField("SHOP_NAME")
    private String shopName;

    /** 付款时间 */
    @TableField("PAID_TIME")
    private Date paidTime;

    /** 发货时间 */
    @TableField("EXPRESS_TIME")
    private Date expressTime;

    /** 状态：1待处理2已退款3已重发4已完成5已作废 */
    @TableField("STATUS")
    private BigDecimal status;

    /** 平台交易号 */
    @TableField("SALES_RECORD_NUMBER")
    private String salesRecordNumber;

    /** 订单金额 */
    @TableField("ORDER_FEE")
    private BigDecimal orderFee;

    /** 订单重量 */
    @TableField("ORDER_WEIGHT")
    private BigDecimal orderWeight;

    /** 物流渠道编号 */
    @TableField("MY_LOGISTICS_CHANNEL_ID")
    private BigDecimal myLogisticsChannelId;

    /** 物流渠道名称 */
    @TableField("MY_LOGISTICS_CHANNEL_NAME")
    private String myLogisticsChannelName;

    /** 物流公司编号 */
    @TableField("MY_LOGISTICS_ID")
    private BigDecimal myLogisticsId;

    /** 物流公司名称 */
    @TableField("MY_LOGISTICS_NAME")
    private String myLogisticsName;

    /** 物流单号 */
    @TableField("TRACK_NUMBER")
    private String trackNumber;

    /** 平台编号 */
    @TableField("PLATFORM_ID")
    private String platformId;

    /** 退包类型1邮局退包2买家退包 其余为自定义分类 */
    @TableField("TYPE")
    private BigDecimal type;

    /** 国家二字码 */
    @TableField("COUNTRY_CODE")
    private String countryCode;

    /** 国家英文名称:注意json数据转换 */
    @TableField("COUNTRY_NAME_EN")
    private String countryNameEn;

    /** 国家中文名称:注意json数据转换 */
    @TableField("COUNTRY_NAME_CN")
    private String countryNameCn;

    /** 买家账号 */
    @TableField("BUYER_USER_ID")
    private String buyerUserId;

    /** 买家姓名 */
    @TableField("BUYER_NAME")
    private String buyerName;

    /** 登记人编号 */
    @TableField("EMPLOYEE_ID")
    private BigDecimal employeeId;

    /** 登记人名称 */
    @TableField("EMPLOYEE_NAME")
    private String employeeName;

    /** 备注 */
    @TableField("REMARK")
    private String remark;

    /** 创建时间 */
    @TableField("CREATE_DATE")
    private Date createDate;

    /** 退款时间 */
    @TableField("REFUND_TIME")
    private Date refundTime;

    /** 最近一次入库时间 */
    @TableField("IN_TIME")
    private Date inTime;

    /** 币种 */
    @TableField("CURRENCY_ID")
    private String currencyId;

    /** 汇率 */
    @TableField("CURRENCY_RATE")
    private String currencyRate;

    /** return_tracknumber:注意json数据转换 */
    @TableField("RETURN_TRACKNUMBER")
    private String returnTracknumber;

    /** return_type:注意json数据转换 */
    @TableField("RETURN_TYPE")
    private String returnType;

    /** 创建销售退货单：0待创建，1：已创建，默认值：0 */
    @TableField("CREATE_SALE_RETURN_ORDER")
    private String createSaleReturnOrder;

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


   @TableField(exist = false)
   private MabangReturnOrderItem [] item;

}