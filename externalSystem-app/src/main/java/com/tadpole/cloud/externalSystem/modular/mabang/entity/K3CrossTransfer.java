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
    * K3跨组织直接调拨单主表
    * </p>
*
* @author lsy
* @since 2022-06-28
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("K3_CROSS_TRANSFER")
@ExcelIgnoreUnannotated
public class K3CrossTransfer implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 数据id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 调用k3跨组织调拨接口返回的id */
    @TableField("F_ID")
    private BigDecimal fId;

    /** 调用k3跨组织调拨接口,保存时自动生成 */
    @TableField("F_Bill_NO")
    private String fBillNo;

    /** 单据类型|默认值：ZJDB01_SYS 标准直接调拨单	关联K3【单据类型表】作业 */
    @TableField("F_BILL_TYPE_ID")
    private String fBillTypeId;

    /** 业务类型|默认值：NORMAL 标准	枚举值：NORMAL 标准，CONSIGNMENT 寄售，OUTSOURCING委外，DRPTRANS 分销调拨，VMI VMI业务 */
    @TableField("F_BIZ_TYPE")
    private String fBizType;

    /** 调拨方向|默认值：GENERAL 普通	枚举值：GENERAL 普通，RETURN 退货 */
    @TableField("F_TRANSFER_DIRECT")
    private String fTransferDirect;

    /** 调拨类型|默认值：OverOrgTransfer 跨组织调拨	枚举值：InnerOrgTransfer 组织内调拨，OverOrgTransfer 跨组织调拨，...，值比较多 */
    @TableField("F_TRANSFER_BIZ_TYPE")
    private String fTransferBizType;

    /** UW业务类型|默认值：B 小袋出货	枚举值：A 亚马逊出货，B 小袋出货，C B2B 出货，D 批量出货，E 滞销销毁出货 */
    @TableField("F_UNW_BUSINESSTYPE")
    private String fUnwBusinesstype;

    /** 业务类型简称|默认值：XDS */
    @TableField("F_UNW_TEXT")
    private String fUnwText;

    /** 日期|传入马帮订单的发货日期	说明：每1条马帮已发货订单生成1-N个K3跨组织直接调拨单 */
    @TableField("F_DATE")
    private Date fDate;

    /** 调出库存组织|默认值：002 采购中心	关联K3【客户列表】作业 */
    @TableField("F_STOCK_OUTORG_ID")
    private String fStockOutorgId;

    /** 调出货主类型|默认值：业务组织 */
    @TableField("F_OWNER_TYPE_OUT_ID_HEAD")
    private String fOwnerTypeOutIdHead;

    /** 调出货主|默认值：002 采购中心	关联K3【客户列表】作业 */
    @TableField("F_OWNER_OUT_ID_HEAD")
    private String fOwnerOutIdHead;

    /** 调入库存组织|传入马帮订单关联的店铺的财务编码（又名：金蝶组织编码）	举例：1101 eBay_MK_ALL	关联K3【客户列表】作业 */
    @TableField("F_STOCK_ORG_ID")
    private String fStockOrgId;

    /** 调入货主类型|默认值：业务组织 */
    @TableField("F_OWNER_TYPE_ID_HEAD")
    private String fOwnerTypeIdHead;

    /** 调入货主|传入马帮订单关联的店铺的财务编码（又名：金蝶组织编码）	举例：1101 eBay_MK_ALL	关联K3【客户列表】作业 */
    @TableField("F_OWNER_ID_HEAD")
    private String fOwnerIdHead;

    /** 备注|存放订单编号platformOrderId字段值，页面显示为【平台订单编号】 */
    @TableField("F_NOTE")
    private String fNote;

    /** 平台名称|用马帮订单字段shopId关联MCMS【马帮店铺列表】作业，返回平台名称 */
    @TableField("PLATFORM_NAME")
    private String platformName;

    /** 店铺名称|用马帮订单字段shopId关联MCMS【马帮店铺列表】作业，返回店铺名称 */
    @TableField("SHOP_NAME")
    private String shopName;

    /** 金蝶组织编码|用马帮订单字段shopId关联MCMS【马帮店铺列表】作业，返回金蝶组织编码（原财务编码字段） */
    @TableField("FINANCE_CODE")
    private String financeCode;

    /** 是否删除:正常：0，删除：1 */
    @TableField("IS_DELETE")
    private String isDelete;

    /** 同步方式(0 ：系统同步,1：手动人工同步) */
    @TableField("SYNC_TYPE")
    private String syncType;

    /** 同步时间 */
    @TableField("SYNC_TIME")
    private Date syncTime;

    /** 同步状态(-1：未推送 0：推送失败 1：推送成功 2：提交失败 3：审核失败  4: 其他状态 联泰已推拆单非联泰不推或非联泰发货) */
    @TableField("SYNC_STATUS")
    private String syncStatus;

    /** 同步成功次数 */
    @TableField("SYNC_SUCCESS_TIMES")
    private BigDecimal syncSuccessTimes;

    /** 同步失败次数 */
    @TableField("SYNC_FAIL_TIMES")
    private BigDecimal syncFailTimes;

    /** 同步结果消息内容 */
    @TableField("SYNC_REQUST_PAR")
    private String syncRequstPar;

    /** 同步结果消息内容 */
    @TableField("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 调入货主|传入马帮订单关联的店铺的财务编码（又名：金蝶组织编码） 名称 */
    @TableField("F_OWNER_ID_HEAD_NAME")
    private String fOwnerIdHeadName;

    /** 马帮店铺原始财务编码-拆分出K3财务编码 */
    @TableField("ORIGINAL_FINANCE_CODE")
    private String originalFinanceCode;

    /** MABANG_ORDERS表的数据id */
    @TableField("ORDER_ID")
    private String orderId;

   /** BIZ_TYPE 跨组织调拨单业务类型（YFHDD:已发货订单,XSTHDD销售退货订单） */
   @TableField("BIZ_TYPE")
   private String bizType;
}