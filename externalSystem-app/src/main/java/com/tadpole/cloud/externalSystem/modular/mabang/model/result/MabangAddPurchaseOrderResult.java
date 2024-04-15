package com.tadpole.cloud.externalSystem.modular.mabang.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
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
* <p>
    * 马帮新增采购订单
    * </p>
*
* @author lsy
* @since 2022-06-15
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("MABANG_ADD_PURCHASE_ORDER")
public class MabangAddPurchaseOrderResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 新增采购订单数据ID */
   @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 同步概要记录id */
    @ApiModelProperty("同步概要记录id")
    private String summaryId;

    /** 该直接调拨单本次同步记录是否触发了反审核：0未触发，1已触发 */
    @TableField("ANTI_AUDIT")
    private int antiAudit;

    /** 马帮仓库名称 */
    @ApiModelProperty("马帮仓库名称")
    private String warehouseName;

    /** 供应商名称 直接传值：虚拟供应商 */
    @ApiModelProperty("供应商名称 直接传值：虚拟供应商")
    private String providerName;

    /** 员工名称  直接传值：虚拟采购员 */
    @ApiModelProperty("员工名称  直接传值：虚拟采购员")
    private String employeeName;

    /** 自定义单据号,最长字符20 */
    @ApiModelProperty("自定义单据号,最长字符20")
    private String orderBillNo;

    /** 采购运费 */
    @ApiModelProperty("采购运费")
    private String expressMoney;

    /** 到货天数 */
    @ApiModelProperty("到货天数")
    private String estimatedTime;

    /** 快递方式 */
    @ApiModelProperty("快递方式")
    private String expressType;

    /** 快递单号 */
    @ApiModelProperty("快递单号")
    private String expressId;

    /** 原马帮采购备注，此处名称为：K3直接调拨单号 */
    @ApiModelProperty("原马帮采购备注，此处名称为：K3直接调拨单号")
    private String content;

    /** 是否计算采购在途，1计算 2不计算 */
    @ApiModelProperty("是否计算采购在途，1计算 2不计算")
    private String notCalculate;

    /** 生成的采购单将自动提交采购 1自动 2不自动 2022-06-06方峰要求：1自动 */
    @ApiModelProperty("生成的采购单将自动提交采购 1自动 2不自动 2022-06-06方峰要求：1自动")
    private String isAutoSubmitCheck;

    /** 采购批次号 */
    @ApiModelProperty("采购批次号")
    private String groupId;

    /** K3调拨单据编号 */
    @ApiModelProperty("K3调拨单据编号")
    private String billNo;

    /** 调拨单拆单总数，根据实际情况可以填写为P1,P2,P3,...，PN(N为拆分的单据数）,PN为分母，意味着总拆分订单数 */
    @ApiModelProperty("调拨单拆单总数，根据实际情况可以填写为P1,P2,P3,...，PN(N为拆分的单据数）,PN为分母，意味着总拆分订单数")
    private String splitTotal;

    /** 拆单编号，根据实际情况可以填写为P1,P2,P3,...，PN(N为拆分的单据数）,PN为分母，意味着总拆分订单数 */
    @ApiModelProperty("拆单编号，根据实际情况可以填写为P1,P2,P3,...，PN(N为拆分的单据数）,PN为分母，意味着总拆分订单数")
    private String splitNum;

    /** K3调拨单据状态 */
    @ApiModelProperty("K3调拨单据状态")
    private String documentStatus;

    /** K3单据创建日期 */
    @ApiModelProperty("K3单据创建日期")
    private Date createDate;

    /** K3单据审核日期 */
    @ApiModelProperty("K3单据审核日期")
    private Date approveDate;

    /** K3单据作废状态 */
    @ApiModelProperty("K3单据作废状态")
    private String cancelStatus;

    /** K3单据作废日期 */
    @ApiModelProperty("K3单据作废日期")
    private Date cancelDate;

    /** 商品信息list,商品项的json数据 */
    @ApiModelProperty("商品信息list,商品项的json数据")
    private String stockList;

    /** 是否删除:正常：0，删除：1 */
    @ApiModelProperty("是否删除:正常：0，删除：1")
    private String isDelete;

    /** 同步方式(0 ：系统同步,1：手动人工同步) */
    @ApiModelProperty("同步方式(0 ：系统同步,1：手动人工同步)")
    private String syncType;

    /** 同步时间 */
    @ApiModelProperty("同步时间")
    private Date syncTime;

    /** 同步状态(0 ：同步失败,1：同步成功) */
    @ApiModelProperty("同步状态(0 ：同步失败,1：同步成功)")
    private String syncStatus;

    /** 同步成功次数(反审核通过情况次数增加) */
    @ApiModelProperty("同步成功次数(反审核通过情况次数增加)")
    private BigDecimal syncSuccessTimes;

    /** 同步失败次数 */
    @ApiModelProperty("同步失败次数")
    private BigDecimal syncFailTimes;

    /** 同步请求消息内容 */
    @ApiModelProperty("同步请求消息内容")
    private String syncRequestMsg;

    /** 同步结果消息内容 */
    @ApiModelProperty("同步结果消息内容")
    private String syncResultMsg;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

}