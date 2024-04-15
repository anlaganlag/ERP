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
    * 销售退货单
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
@TableName("SALE_RETURN_ORDER")
public class SaleReturnOrderResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 平台订单编号:传马帮已发货订单作业的platOrdId字段值 */
//    @ApiModelProperty("PLAT_ORD_ID")
//    private String platOrdId;

    /** k3退货单号,接口传递给k3 如：XSTHD-209-209-202112000 */
    @ApiModelProperty("BILL_NO")
    private String billNo;

    /** 年份 */
    @ApiModelProperty("YEARS")
    private String years;

    /** 月份 */
    @ApiModelProperty("MONTH")
    private String month;

    /** 金蝶组织编码:马帮ERP中完整带小尾巴的金蝶组织编码 */
    @ApiModelProperty("FINANCE_CODE")
    private String financeCode;

    /** 销售组织:根据销售组织编码去K3客户列表作业查询返回销售组织名称，平台发展中心这块业务比较特殊，无法直接在MCMS中组装 */
    @ApiModelProperty("SAL_ORG_NAME")
    private String salOrgName;

    /** 销售组织编码:K3中约定客户编码（不带尾巴的金蝶组织编码）对应的仓库编码是一样 */
    @ApiModelProperty("SAL_ORG_CODE")
    private String salOrgCode;

    /** 物料编码:即库存SKU */
    @ApiModelProperty("STOCK_SKU")
    private String stockSku;

    /** 退货数量求和 */
    @ApiModelProperty("QTY_SUM")
    private BigDecimal qtySum;

    /** 调用k3接口传递字段FDocumentStatus--金蝶ERP单据上的系统字段 ：默认值：已审核 */
    @ApiModelProperty("F_DOCUMENT_STATUS")
    private String fDocumentStatus;

    /** 默认值：店铺虚拟客户 */
    @ApiModelProperty("FRetcustId")
    private String fretcustid;

    /** 是否作废Y/N，默认N（代表所有item是否作废） */
    @ApiModelProperty("IS_INVALID")
    private String isInvalid;

    /** 同步方式（0 ：系统同步,1：手动人工同步） */
    @ApiModelProperty("SYNC_TYPE")
    private String syncType;

    /** 同步时间 */
    @ApiModelProperty("SYNC_TIME")
    private Date syncTime;

    /** 同步状态（-1：数据初始化，0 ：同步失败,1：同步成功） */
    @ApiModelProperty("SYNC_STATUS")
    private String syncStatus;

    /** 同步失败次数 */
    @ApiModelProperty("SYNC_FAIL_TIMES")
    private BigDecimal syncFailTimes;

    /** 同步请求消息内容 */
    @ApiModelProperty("SYNC_REQUEST_MSG")
    private String syncRequestMsg;

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