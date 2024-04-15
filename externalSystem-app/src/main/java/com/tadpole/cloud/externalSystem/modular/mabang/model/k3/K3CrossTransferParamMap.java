package com.tadpole.cloud.externalSystem.modular.mabang.model.k3;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tadpole.cloud.externalSystem.api.k3.annotation.TransferSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
public class K3CrossTransferParamMap implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 数据id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 调用k3跨组织调拨接口返回的id */
    @ApiModelProperty("F_ID")
    @JSONField(name = "FID")
    private BigDecimal fId;


    /** 单据类型|默认值：ZJDB01_SYS 标准直接调拨单	关联K3【单据类型表】作业 */

    @JSONField(serializeUsing = TransferSerializer.class,name = "FBillTypeID",ordinal = 1)
    @ApiModelProperty("F_BILL_TYPE_ID")
    private String fBillTypeId;


 /** 调用k3跨组织调拨接口,保存时自动生成 */
    @ApiModelProperty("F_Bill_NO")
    @JSONField(name = "FBillNo")
    private String fBillNo;

    /** 业务类型|默认值：NORMAL 标准	枚举值：NORMAL 标准，CONSIGNMENT 寄售，OUTSOURCING委外，DRPTRANS 分销调拨，VMI VMI业务 */
    @JSONField(name = "FBizType",ordinal = 2)
    @ApiModelProperty("F_BIZ_TYPE")
    private String fBizType;

    /** 调拨方向|默认值：GENERAL 普通	枚举值：GENERAL 普通，RETURN 退货 */
    @ApiModelProperty("F_TRANSFER_DIRECT")
    @JSONField(name = "FTransferDirect",ordinal = 3)
    private String fTransferDirect;

    /** 调拨类型|默认值：OverOrgTransfer 跨组织调拨	枚举值：InnerOrgTransfer 组织内调拨，OverOrgTransfer 跨组织调拨，...，值比较多 */
    @ApiModelProperty("F_TRANSFER_BIZ_TYPE")
    @JSONField(name = "FTransferBizType",ordinal = 4)
    private String fTransferBizType;

    /** UW业务类型|默认值：B 小袋出货	枚举值：A 亚马逊出货，B 小袋出货，C B2B 出货，D 批量出货，E 滞销销毁出货 */
    @JSONField(name="F_UNW_BusinessType",ordinal = 13)
    @ApiModelProperty("F_UNW_BUSINESSTYPE")
    private String fUnwBusinesstype;

    /** 业务类型简称|默认值：XDS */
    @JSONField(name="F_UNW_TEXT",ordinal = 14)
    @ApiModelProperty("F_UNW_TEXT")
    private String fUnwText;

    /** 日期|传入马帮订单的发货日期	说明：每1条马帮已发货订单生成1-N个K3跨组织直接调拨单 */
    @ApiModelProperty("F_DATE")
    @JSONField(name = "FDATE",ordinal = 11)
    private String fDate;

    /** 调出库存组织|默认值：002 采购中心	关联K3【客户列表】作业 */
    @JSONField(name="FStockOutOrgId", serializeUsing = TransferSerializer.class,ordinal = 5)
    @ApiModelProperty("F_STOCK_OUTORG_ID")
    private String fStockOutorgId;

    /** 调出货主类型|默认值：业务组织 */
    @ApiModelProperty("F_OWNER_TYPE_OUT_ID_HEAD")
    @JSONField(name = "FOwnerTypeOutIdHead",ordinal = 6)
    private String fOwnerTypeOutIdHead;

    /** 调出货主|默认值：002 采购中心	关联K3【客户列表】作业 */
    @JSONField(serializeUsing =TransferSerializer.class,name = "FOwnerOutIdHead",ordinal = 7)
    @ApiModelProperty("F_OWNER_OUT_ID_HEAD")
    private String fOwnerOutIdHead;

    /** 调入库存组织|传入马帮订单关联的店铺的财务编码（又名：金蝶组织编码）	举例：1101 eBay_MK_ALL	关联K3【客户列表】作业 */
    @JSONField(serializeUsing =TransferSerializer.class,ordinal = 8,name = "FStockOrgId")
    @ApiModelProperty("F_STOCK_ORG_ID")
    private String fStockOrgId;

    /** 调入货主类型|默认值：业务组织 */
//    @JSONField(serializeUsing =TransferSerializer.class)
    @ApiModelProperty("F_OWNER_TYPE_ID_HEAD")
    @JSONField(name = "FOwnerTypeIdHead",ordinal = 9)
    private String fOwnerTypeIdHead;

    /** 调入货主|传入马帮订单关联的店铺的财务编码（又名：金蝶组织编码）	举例：1101 eBay_MK_ALL	关联K3【客户列表】作业 */
    @JSONField(name = "FOwnerIdHead",ordinal = 10,serializeUsing =TransferSerializer.class)
    @ApiModelProperty("F_OWNER_ID_HEAD")

    private String fOwnerIdHead;

    /** 备注|存放订单编号platformOrderId字段值，页面显示为【平台订单编号】 */
    @ApiModelProperty("F_NOTE")
    @JSONField(name = "FNote",ordinal = 12)
    private String fNote;

    /** 平台名称|用马帮订单字段shopId关联MCMS【马帮店铺列表】作业，返回平台名称 */
    @ApiModelProperty("PLATFORM_NAME")
    private String platformName;

    /** 店铺名称|用马帮订单字段shopId关联MCMS【马帮店铺列表】作业，返回店铺名称 */
    @ApiModelProperty("SHOP_NAME")
    private String shopName;

    /** 金蝶组织编码|用马帮订单字段shopId关联MCMS【马帮店铺列表】作业，返回金蝶组织编码（原财务编码字段） */
    @ApiModelProperty("FINANCE_CODE")
    private String financeCode;

    /** 是否删除:正常：0，删除：1 */
    @ApiModelProperty("IS_DELETE")
    private String isDelete;

    /** 同步方式(0 ：系统同步,1：手动人工同步) */
    @ApiModelProperty("SYNC_TYPE")
    private String syncType;

    /** 同步时间 */
    @ApiModelProperty("SYNC_TIME")
    private Date syncTime;

    /** 同步状态(0 ：同步失败,1：同步成功) */
    @ApiModelProperty("SYNC_STATUS")
    private String syncStatus;

    /** 同步成功次数 */
    @ApiModelProperty("SYNC_SUCCESS_TIMES")
    private BigDecimal syncSuccessTimes;

    /** 同步失败次数 */
    @ApiModelProperty("SYNC_FAIL_TIMES")
    private BigDecimal syncFailTimes;

    /** 同步结果消息内容 */
    @ApiModelProperty("SYNC_REQUST_PAR")
    private String syncRequstPar;

    /** 同步结果消息内容 */
    @ApiModelProperty("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

   @JSONField(name = "FBillEntry",ordinal = 15)
   private List<K3CrossTransferItemParamMap> fBillEntry;

}