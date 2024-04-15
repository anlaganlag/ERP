package com.tadpole.cloud.externalSystem.modular.mabang.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
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
@ExcelIgnoreUnannotated
public class MabangReturnOrderResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 主键=PLAT_ORD_ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /**
     * 平台订单编号
     */
    @ApiModelProperty("平台订单编号")
    @ExcelProperty(value = "平台订单编号")
    private String platOrdId;


    /**
     * 退货单状态：1待处理2已退款3已重发4已完成5已作废
     */
    @ApiModelProperty("退货单状态原值")
    private String status;

    /**
     * 退货单状态文本：1待处理2已退款3已重发4已完成5已作废
     */
    @ApiModelProperty("退货单状态文本")
    @ExcelProperty(value = "退货单状态")
    private String statusTxt;





    /**
     * 马帮退货单创建时间
     */
    @ApiModelProperty("马帮退货单创建时间")
    @ExcelProperty(value = "马帮退货单创建时间")
    private Date createDate;


    /**
     * 平台名称
     */
    @ApiModelProperty("平台名称")
    @ExcelProperty(value = "平台名称")
    private String platformId;


    /**
     * 店铺编号
     */
    @ApiModelProperty("店铺编号")
    private BigDecimal shopId;

    /**
     * 店铺名称
     */
    @ApiModelProperty("店铺名称")
    @ExcelProperty(value = "店铺名称")
    private String shopName;

    /**
     * 站点
     */
    @ApiModelProperty("站点")
    @ExcelProperty(value = "站点")
    private String countryCode;


    /**
     * 金蝶组织编码
     */
    @ApiModelProperty("金蝶组织编码")
    @ExcelProperty(value = "金蝶组织编码")
    private String financeCode;


    /**
     * 登记人编号
     */
    @ApiModelProperty("登记人编号")
    private BigDecimal employeeId;

    /**
     * 登记人
     */
    @ApiModelProperty("登记人")
    @ExcelProperty(value = "登记人")
    private String employeeName;

    /**
     * 马帮退货单抓取时间
     */
    @ApiModelProperty("马帮退货单抓取时间")
    @ExcelProperty(value = "马帮退货单抓取时间")
    private Date createTime;

    /**
     * 物料编码
     */
    @ApiModelProperty("物料编码")
    @ExcelProperty(value = "物料编码")
    private String stocksku;


    /**
     * 同步方式（0 ：系统同步,1：手动人工同步）
     */
    @ApiModelProperty("同步方式")
    private String syncType;

    /**
     * 同步时间
     */
    @ApiModelProperty("同步时间")
    private Date syncTime;

    /**
     * 同步状态（0 ：同步失败,1：同步成功）
     */
    @ApiModelProperty("同步状态")
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
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;


    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    @ExcelProperty(value = "商品名称")
    private String title;

    /**
     * 退货入库数量
     */
    @ApiModelProperty("退货入库数量")
    @ExcelProperty(value = "退货入库数量")
    private String quantity1;

    /**
     * 仓库名称
     */
    @ApiModelProperty("仓库名称")
    @ExcelProperty(value = "仓库名称")
    private String stockwarehousename;

    /**
     * 物料入库状态
     */
    @ApiModelProperty("物料入库状态")
    @ExcelProperty(value = "物料入库状态")
    private String instatus;









}