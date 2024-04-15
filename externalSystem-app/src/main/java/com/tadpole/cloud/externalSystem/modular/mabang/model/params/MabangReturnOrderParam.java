package com.tadpole.cloud.externalSystem.modular.mabang.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
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
public class MabangReturnOrderParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 主键=PLAT_ORD_ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /**
     * 平台订单编号：equals("Mercadolibre") ? Orders.getPlatformOrderId() : Orders.getSalesRecordNumber()
     */
    @ApiModelProperty("平台订单编号")
    private String platOrdId;


    /**
     * 店铺编号
     */
    @ApiModelProperty("店铺编号")
    private BigDecimal shopId;

    /**
     * 店铺名称
     */
    @ApiModelProperty("店铺名称")
    private String shopName;


    /**
     * 退货单状态：1待处理2已退款3已重发4已完成5已作废
     */
    @ApiModelProperty("退货单状态：1待处理2已退款3已重发4已完成5已作废")
    private String status;


    /**
     * 平台名称
     */
    @ApiModelProperty("平台名称")
    private String platformId;


    /**
     * 站点
     */
    @ApiModelProperty("站点")
    private String countryCode;


    /**
     * 马帮退货单创建时间
     */
    @ApiModelProperty("马帮退货单创建时间")
    private Date createDate;


    /**
     * return_type:注意json数据转换
     */
    @ApiModelProperty("return_type:注意json数据转换")
    private String returnType;

    /**
     * 创建销售退货单：0待创建，1：已创建，默认值：0
     */
    @ApiModelProperty("创建销售退货单：0待创建，1：已创建，默认值：0")
    private String createSaleReturnOrder;

    /**
     * 同步方式（0 ：系统同步,1：手动人工同步）
     */
    @ApiModelProperty("同步方式（0 ：系统同步,1：手动人工同步）")
    private String syncType;

    /**
     * 同步时间
     */
    @ApiModelProperty("同步时间")
    private Date syncTime;

    /**
     * 同步状态（0 ：同步失败,1：同步成功）
     */
    @ApiModelProperty("同步状态（0 ：同步失败,1：同步成功）")
    private String syncStatus;

    /**
     * 同步成功次数
     */
    @ApiModelProperty("同步成功次数")
    private BigDecimal syncSuccessTimes;

    /**
     * 同步失败次数
     */
    @ApiModelProperty("同步失败次数")
    private BigDecimal syncFailTimes;

    /**
     * 最后一次同步结果消息内容
     */
    @ApiModelProperty("最后一次同步结果消息内容")
    private String syncResultMsg;

    /**
     * 创建时间
     */
    @ApiModelProperty("马帮退货单抓取时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;


    /**
     * 金蝶组织编码
     */
    @ApiModelProperty("金蝶组织编码")
    private String financeCode;


    /**
     * 金蝶组织原始编码
     */
    @ApiModelProperty("金蝶组织原始编码")
    private String originalFinanceCode;


    /**
     * 物料编码
     */
    @ApiModelProperty("物料编码")
    private String stocksku;

    /**
     * 马帮退货单创建时间开始
     */
    @ApiModelProperty("马帮退货单创建时间开始")
    private String createDateStart;


    /**
     * 马帮退货单创建时间结束
     */
    @ApiModelProperty("马帮退货单创建时间结束")
    private String createDateEnd;

    /**
     * 马帮退货单抓取时间开始
     */
    @ApiModelProperty("马帮退货单抓取时间开始")
    private String createTimeStart;

    /**
     * 马帮退货单抓取时间结束
     */
    @ApiModelProperty("马帮退货单抓取时间结束")
    private String createTimeEnd;

    /**
     * 退货单状态列表
     */
    @ApiModelProperty("退货单状态列表")
    private List<String> statuss;


    /**
     * 平台名称列表
     */
    @ApiModelProperty("平台名称列表")
    private List<String> platformIds;

    /**
     * 店铺名称列表
     */
    @ApiModelProperty("店铺名称列表")
    private List<String> shopNames;


    /**
     * 站点列表
     */
    @ApiModelProperty("站点列表")
    private List<String> countryCodes;


}