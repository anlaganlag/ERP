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
@TableName("MABANG_ADD_PURCHASE_ORDER")
@ExcelIgnoreUnannotated
public class MabangAddPurchaseOrder implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 新增采购订单数据ID */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 同步概要记录id */
    @TableField("SUMMARY_ID")
    private String summaryId;

    /** 该直接调拨单本次同步记录是否触发了反审核：0未触发，1已触发 */
    @TableField("ANTI_AUDIT")
    private int antiAudit;

    /** 马帮仓库名称 */
    @TableField("WAREHOUSE_NAME")
    private String warehouseName;

    /** 供应商名称 直接传值：虚拟供应商 */
    @TableField("PROVIDER_NAME")
    private String providerName;

    /** 员工名称  直接传值：虚拟采购员 */
    @TableField("EMPLOYEE_NAME")
    private String employeeName;

    /** 自定义单据号,最长字符20 */
    @TableField("ORDER_BILL_NO")
    private String orderBillNo;

    /** 采购运费 */
    @TableField("EXPRESS_MONEY")
    private String expressMoney;

    /** 到货天数 */
    @TableField("ESTIMATED_TIME")
    private String estimatedTime;

    /** 快递方式 */
    @TableField("EXPRESS_TYPE")
    private String expressType;

    /** 快递单号 */
    @TableField("EXPRESS_ID")
    private String expressId;

    /** 原马帮采购备注，此处名称为：K3直接调拨单号 */
    @TableField("CONTENT")
    private String content;

    /** 是否计算采购在途，1计算 2不计算 */
    @TableField("NOT_CALCULATE")
    private String notCalculate;

    /** 生成的采购单将自动提交采购 1自动 2不自动 2022-06-06方峰要求：1自动 */
    @TableField("IS_AUTO_SUBMIT_CHECK")
    private String isAutoSubmitCheck;

    /** 采购批次号 */
    @TableField("GROUP_ID")
    private String groupId;

    /** K3调拨单据编号 */
    @TableField("BILL_NO")
    private String billNo;

    /** 调拨单拆单总数，根据实际情况可以填写为P1,P2,P3,...，PN(N为拆分的单据数）,PN为分母，意味着总拆分订单数 */
    @TableField("SPLIT_TOTAL")
    private String splitTotal;

    /** 拆单编号，根据实际情况可以填写为P1,P2,P3,...，PN(N为拆分的单据数）,PN为分母，意味着总拆分订单数 */
    @TableField("SPLIT_NUM")
    private String splitNum;

    /** K3调拨单据状态 */
    @TableField("DOCUMENT_STATUS")
    private String documentStatus;

    /** K3单据创建日期 */
    @TableField("CREATE_DATE")
    private Date createDate;

    /** K3单据审核日期 */
    @TableField("APPROVE_DATE")
    private Date approveDate;

    /** K3单据作废状态 */
    @TableField("CANCEL_STATUS")
    private String cancelStatus;

    /** K3单据作废日期 */
    @TableField("CANCEL_DATE")
    private Date cancelDate;

    /** 商品信息list,商品项的json数据 */
    @TableField("STOCK_LIST")
    private String stockList;

    /** 是否删除:正常：0，删除：1 */
    @TableField("IS_DELETE")
    private String isDelete;

    /** 同步方式(0 ：系统同步,1：手动人工同步) */
    @TableField("SYNC_TYPE")
    private String syncType;

    /** 同步时间 */
    @TableField("SYNC_TIME")
    private Date syncTime;

    /** 同步状态(0 ：同步失败,1：同步成功) */
    @TableField("SYNC_STATUS")
    private String syncStatus;

    /** 同步成功次数(反审核通过情况次数增加) */
    @TableField("SYNC_SUCCESS_TIMES")
    private BigDecimal syncSuccessTimes;

    /** 同步失败次数 */
    @TableField("SYNC_FAIL_TIMES")
    private BigDecimal syncFailTimes;

    /** 同步请求消息内容 */
    @TableField("SYNC_REQUEST_MSG")
    private String syncRequestMsg;

    /** 同步结果消息内容 */
    @TableField("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

}