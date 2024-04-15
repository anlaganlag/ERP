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
 * K3跨组织直接调拨单明细项
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
public class ExportK3CrossTransferResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 数据id
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /**
     * 调用k3跨组织调拨接口,保存时自动生成
     */
    @ExcelProperty(value ="K3直接调拨单号")
    @ApiModelProperty("K3直接调拨单号")
    private String fbillNo;

    /**
     * 单据类型|默认值：ZJDB01_SYS 标准直接调拨单	关联K3【单据类型表】作业
     */
    @ApiModelProperty("单据类型|默认值：ZJDB01_SYS 标准直接调拨单	关联K3【单据类型表】作业")
    private String fBillTypeId;

    /**
     * 业务类型|默认值：NORMAL 标准	枚举值：NORMAL 标准，CONSIGNMENT 寄售，OUTSOURCING委外，DRPTRANS 分销调拨，VMI VMI业务
     */
    @ApiModelProperty("业务类型|默认值：NORMAL 标准	枚举值：NORMAL 标准，CONSIGNMENT 寄售，OUTSOURCING委外，DRPTRANS 分销调拨，VMI VMI业务")
    private String fBizType;

    /**
     * 调拨方向|默认值：GENERAL 普通	枚举值：GENERAL 普通，RETURN 退货
     */
    @ApiModelProperty("调拨方向|默认值：GENERAL 普通	枚举值：GENERAL 普通，RETURN 退货")
    private String fTransferDirect;

    /**
     * 调拨类型|默认值：OverOrgTransfer 跨组织调拨	枚举值：InnerOrgTransfer 组织内调拨，OverOrgTransfer 跨组织调拨，...，值比较多
     */
    @ExcelProperty(value ="调拨类型")
    @ApiModelProperty("调拨类型|默认值：OverOrgTransfer 跨组织调拨	枚举值：InnerOrgTransfer 组织内调拨，OverOrgTransfer 跨组织调拨，...，值比较多")
    private String ftransferBizType;

    /**
     * UW业务类型|默认值：B 小袋出货	枚举值：A 亚马逊出货，B 小袋出货，C B2B 出货，D 批量出货，E 滞销销毁出货
     */
    @ExcelProperty(value ="UW业务类型")
    @ApiModelProperty("UW业务类型|默认值：B 小袋出货	枚举值：A 亚马逊出货，B 小袋出货，C B2B 出货，D 批量出货，E 滞销销毁出货")
    private String funwBusinesstype;

    /** 调出库存组织|默认值：002 采购中心	关联K3【客户列表】作业 */
    @ApiModelProperty("F_STOCK_OUTORG_ID")
    private String fStockOutorgId;

    /** 调出货主类型|默认值：业务组织 */
    @ApiModelProperty("F_OWNER_TYPE_OUT_ID_HEAD")
    private String fOwnerTypeOutIdHead;

    /** 调出货主|默认值：002 采购中心	关联K3【客户列表】作业 */
    @ExcelProperty(value ="调出货主")
    @ApiModelProperty("F_OWNER_OUT_ID_HEAD 调出货主")
    private String fownerOutIdHead;

    /** 调入库存组织|传入马帮订单关联的店铺的财务编码（又名：金蝶组织编码）	举例：1101 eBay_MK_ALL	关联K3【客户列表】作业 */
    @ApiModelProperty("F_STOCK_ORG_ID")
    private String fStockOrgId;

    /** 调入货主类型|默认值：业务组织 */
    @ApiModelProperty("F_OWNER_TYPE_ID_HEAD")
    private String fOwnerTypeIdHead;

    /** 调入货主|传入马帮订单关联的店铺的财务编码（又名：金蝶组织编码）	举例：1101 eBay_MK_ALL	关联K3【客户列表】作业 */
    @ApiModelProperty("F_OWNER_ID_HEAD 调入货主")
    private String fownerIdHead;

    /** 调入货主|传入马帮订单关联的店铺的财务编码（又名：金蝶组织编码） 名称 */
    @ExcelProperty(value ="调入货主")
    @ApiModelProperty("F_OWNER_ID_HEAD_NAME 调入货主名称")
    private String fownerIdHeadName;

    /**
     * 平台名称
     */
    @ExcelProperty(value ="平台名称")
    @ApiModelProperty("平台名称")
    private String platformName;

    /**
     * 店铺名称
     */
    @ExcelProperty(value ="店铺名称")
    @ApiModelProperty("店铺名称")
    private String shopName;

    /** 金蝶组织编码|用马帮订单字段shopId关联MCMS【马帮店铺列表】作业，返回金蝶组织编码（原财务编码字段） */
    @ExcelProperty(value ="金蝶组织编码")
    @ApiModelProperty("FINANCE_CODE 金蝶组织编码")
    private String financeCode;

    /**
     * 平台订单编号
     */
    @ExcelProperty(value ="平台订单编号")
    @ApiModelProperty("平台订单编号")
    private String fnote;

    /**
     * 日期|传入马帮订单的发货日期	说明：每1条马帮已发货订单生成1-N个K3跨组织直接调拨单
     */
    @ExcelProperty(value ="订单发货时间")
    @ApiModelProperty("日期|传入马帮订单的发货日期	说明：每1条马帮已发货订单生成1-N个K3跨组织直接调拨单")
    private Date fdate;

    /** 同步状态(0 ：同步失败,1：同步成功) */
    @ExcelProperty(value ="推送K3状态")
    @ApiModelProperty("SYNC_STATUS 推送K3状态 -1：未推送 0：推送失败 1：推送成功 3：审核失败")
    private String syncStatusTxt;

    /**
     * SKU|
     */
    @ExcelProperty(value ="平台SKU")
    @ApiModelProperty(name = "fbscBase", value = "平台SKU")
    private String fbscBase;

    /**
     * 物料编码|
     */
    @ExcelProperty(value ="物料编码")
    @ApiModelProperty(name = "fmaterialId", value = "物料编码")
    private String fmaterialId;

    /**
     * 物料名称|根据物料编码，在K3【物料列表】作业中查询自动带出
     */
    @ExcelProperty(value ="物料名称")
    @ApiModelProperty("物料名称|根据物料编码，在K3【物料列表】作业中查询自动带出")
    private String fmaterialName;

    /**
     * 规格型号|根据物料编码，在K3【物料列表】作业中查询自动带出
     */
    @ApiModelProperty("规格型号|根据物料编码，在K3【物料列表】作业中查询自动带出")
    private String fModel;

    /**
     * 调拨数量|
     */
    @ExcelProperty(value ="调拨数量")
    @ApiModelProperty("调拨数量|")
    private Long fqty;

    /**
     * 单位|根据物料编码，在K3【物料列表】作业中查询自动带出
     */
    @ExcelProperty(value ="单位")
    @ApiModelProperty("单位|根据物料编码，在K3【物料列表】作业中查询自动带出")
    private String funitId;

    /**
     * 调出仓库|
     */
    @ApiModelProperty("调出仓库|")
    private String fSrcStockId;

    /**
     * 调入仓库|用马帮订单接口中shopId关联出财务编码，通过这个编码为K3【仓库列表】中使用组织为该财务编码的仓库编码，找出仓库Id字段填入
     */
    @ApiModelProperty("调入仓库|用马帮订单接口中shopId关联出财务编码，通过这个编码为K3【仓库列表】中使用组织为该财务编码的仓库编码，找出仓库Id字段填入")
    private String fDestStockId;

    /**
     * 调出库存状态|默认值：KCZT01_SYS可用
     */
    @ApiModelProperty("调出库存状态|默认值：KCZT01_SYS可用")
    private String fSrcStockStatusId;

    /**
     * 调入库存状态|默认值：KCZT01_SYS可用
     */
    @ApiModelProperty("调入库存状态|默认值：KCZT01_SYS可用")
    private String fDestStockStatusId;

    /**
     * 调出货主|默认值：同单头字段
     */
    @ApiModelProperty("调出货主|默认值：同单头字段")
    private String fOwnerOutId;

    /**
     * 调入货主|默认值：同单头字段
     */
    @ApiModelProperty("调入货主|默认值：同单头字段")
    private String fOwnerId;

    /**
     * 需求Team|默认值：平台发展组
     */
    @ApiModelProperty("需求Team|默认值：平台发展组")
    private String fBscTeam;

    /**
     * 是否删除:正常：0，删除：1
     */
    @ApiModelProperty("是否删除:正常：0，删除：1")
    private String isDelete;

    /**
     * 同步方式(0 ：系统同步,1：手动人工同步)
     */
    @ApiModelProperty("同步方式(0 ：系统同步,1：手动人工同步)")
    private String syncType;

    /**
     * 同步时间
     */
    @ApiModelProperty("同步时间")
    private Date syncTime;

    /**
     * 同步状态(0 ：同步失败,1：同步成功)
     */
    @ApiModelProperty("同步状态(0 ：同步失败,1：同步成功)")
    private String syncStatus;

    /**
     * 同步成功次数(反审核通过情况次数增加)
     */
    @ApiModelProperty("同步成功次数(反审核通过情况次数增加)")
    private BigDecimal syncSuccessTimes;

    /**
     * 同步结果消息内容
     */
    @ApiModelProperty("同步结果消息内容")
    private String syncRequstPar;

    /**
     * 同步结果消息内容
     */
    @ApiModelProperty("同步结果消息内容")
    private String syncResultMsg;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 马帮仓库名称
     */
    @ExcelProperty(value ="马帮仓库名称")
    @ApiModelProperty("马帮仓库名称")
    private String stockWarehouse;

    /**
     * 马帮仓库编号
     */
    @ExcelProperty(value ="马帮仓库编号")
    @ApiModelProperty("马帮仓库编号")
    private String stockWarehouseId;

}