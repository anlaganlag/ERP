package com.tadpole.cloud.externalSystem.modular.mabang.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 销售出库单
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
public class ExportSaleOutOrderResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** id */
    private BigDecimal id;

    /** K3销售出库单号,接口传递给k3 */
    @ExcelProperty(value ="K3销售出库单号")
    @ApiModelProperty("K3销售出库单号")
    private String billNo;

    /** 年份 */
    @ExcelProperty(value ="年份")
    @ApiModelProperty("年份")
    private String years;

    /** 月份 */
    @ExcelProperty(value ="月份")
    @ApiModelProperty("月份")
    private String month;

    /** 金蝶组织编码:马帮ERP中完整带小尾巴的金蝶组织编码 */
    @ExcelProperty(value ="金蝶组织编码")
    @ApiModelProperty("金蝶组织编码")
    private String financeCode;

    /** 销售组织:根据销售组织编码去K3客户列表作业查询返回销售组织名称，平台发展中心这块业务比较特殊，无法直接在MCMS中组装 */
    @ExcelProperty(value ="销售组织")
    @ApiModelProperty("销售组织")
    private String salOrgName;

    /** 销售组织编码:K3中约定客户编码（不带尾巴的金蝶组织编码）对应的仓库编码是一样 */
    @ExcelProperty(value ="销售组织编码")
    @ApiModelProperty("销售组织编码")
    private String salOrgCode;

    /** 物料编码:即库存SKU */
    @ExcelProperty(value ="物料编码")
    @ApiModelProperty("物料编码")
    private String stockSku;

    /** 销售出库数量累加 */
    @ApiModelProperty("销售出库数量累加")
    private BigDecimal outQtySum;

    /** 调用k3接口传递字段FBillTypeID--默认值：XSCKD01_SYS	默认值对应显示名称：标准销售出库单 */
    @ApiModelProperty("调用k3接口传递字段FBillTypeID--默认值：XSCKD01_SYS	默认值对应显示名称：标准销售出库单")
    private String fBillTypeId;

    /** 调用k3接口传递字段FBillNo */
    @ApiModelProperty("调用k3接口传递字段FBillNo")
    private String fBillNo;

    /** 调用k3接口传递字段FDate--年份+月份+1日 */
    @ApiModelProperty("调用k3接口传递字段FDate--年份+月份+1日")
    private String fDate;

    /** 调用k3接口传递字段FSaleOrgId--销售组织编码 */
    @ApiModelProperty("调用k3接口传递字段FSaleOrgId--销售组织编码")
    private String fSaleOrgId;

    /** 调用k3接口传递字段FCustomerID--默认值：店铺虚拟客户 */
    @ApiModelProperty("调用k3接口传递字段FCustomerID--默认值：店铺虚拟客户")
    private String fCustomerId;

    /** 调用k3接口传递字段F_BSC_type--枚举类型，默认值：0	默认值对应显示名称：低价销售在库产品(外） */
    @ApiModelProperty("调用k3接口传递字段F_BSC_type--枚举类型，默认值：0	默认值对应显示名称：低价销售在库产品(外）")
    private String fBscType;

    /** 调用k3接口传递字段FStockOrgId--库存组织编码 */
    @ApiModelProperty("调用k3接口传递字段FStockOrgId--库存组织编码")
    private String fStockOrgId;

    /** 调用k3接口传递字段FNote--默认值：id */
    @ApiModelProperty("调用k3接口传递字段FNote--默认值：id")
    private String fNote;

    /** 调用k3接口传递字段FSettleCurrID--默认值：PRE001	默认值对应显示名称：人民币 */
    @ApiModelProperty("调用k3接口传递字段FSettleCurrID--默认值：PRE001	默认值对应显示名称：人民币")
    private String fSettleCurrId;

    /** 调用k3接口传递字段FDocumentStatus--金蝶ERP单据上的系统字段 ：默认值：已审核 */
    @ApiModelProperty("调用k3接口传递字段FDocumentStatus--金蝶ERP单据上的系统字段 ：默认值：已审核")
    private String fDocumentStatus;

    /** 是否作废Y/N，默认N（代表所有item是否作废） */
    @ApiModelProperty("是否作废Y/N，默认N（代表所有item是否作废）")
    private String isInvalid;

    /** 同步方式（0 ：系统同步,1：手动人工同步） */
    @ApiModelProperty("同步方式（0 ：系统同步,1：手动人工同步）")
    private String syncType;

    /** 同步时间 */
    @ApiModelProperty("同步时间")
    private Date syncTime;

    /** 同步状态（-1：数据初始化，0 ：同步失败,1：同步成功） */
    @ApiModelProperty("同步状态（-1：数据初始化，0 ：同步失败,1：同步成功）")
    private String syncStatus;

    /** 同步状态中文 */
    @ApiModelProperty("同步状态中文")
    private String syncStatusTxt;

    /** 同步失败次数 */
    @ApiModelProperty("同步失败次数")
    private BigDecimal syncFailTimes;

    /** 同步请求消息内容 */
    @ApiModelProperty("同步请求消息内容")
    private String syncRequestMsg;

    /** 最后一次同步结果消息内容 */
    @ApiModelProperty("最后一次同步结果消息内容")
    private String syncResultMsg;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 销售出库单id */
    @ApiModelProperty("销售出库单id")
    private String saleOutOrderId;

    /** 马帮已完成订单明细项id */
    @ApiModelProperty("马帮已完成订单明细项id")
    private String maOrderItemId;

    /** 事业部 */
    @ExcelProperty(value ="事业部")
    @ApiModelProperty("事业部")
    private String department;

    /** Team */
    @ExcelProperty(value ="Team")
    @ApiModelProperty("TEAM")
    private String team;

    /** 平台名称 */
    @ExcelProperty(value ="平台名称")
    @ApiModelProperty("平台名称")
    private String platName;

    /** 店铺名称 */
    @ExcelProperty(value ="店铺名称")
    @ApiModelProperty("店铺名称")
    private String shopName;

    /** 站点:留3个字符位置，全球有一个国家简码是3个字符 */
    @ExcelProperty(value ="站点")
    @ApiModelProperty("站点")
    private String siteCode;

    /** 仓库名称:根据仓库编码去K3仓库列表作业查询返回仓库名称，平台发展中心这块业务比较特殊，无法直接在MCMS中组装 */
    @ExcelProperty(value ="仓库名称")
    @ApiModelProperty("仓库名称")
    private String warehouseName;

    /** 仓库编码:K3中约定客户编码（不带尾巴的金蝶组织编码）对应的仓库编码是一样 */
    @ExcelProperty(value ="仓库编码")
    @ApiModelProperty("仓库编码")
    private String warehouseId;

    /** 平台订单编号:传马帮已发货订单作业的platOrdId字段值 */
    @ExcelProperty(value ="平台订单编号")
    @ApiModelProperty("平台订单编号")
    private String platOrdId;

    /** 平台SKU */
    @ExcelProperty(value ="SKU")
    @ApiModelProperty("SKU")
    private String platSku;

    /** 发货时间:传马帮已发货订单作业的expressTime字段值 */
    @ExcelProperty(value ="发货时间")
    @ApiModelProperty("发货时间")
    private Date shippedTime;

    /** 销售出库数量:传马帮已发货订单作业的quantity字段值 */
    @ExcelProperty(value ="销售出库数量")
    @ApiModelProperty("销售出库数量")
    private BigDecimal outQty;

    /** 物料编码:调用k3接口传递字段FMaterialID--取值stockSku */
    @ApiModelProperty("物料编码:调用k3接口传递字段FMaterialID--取值stockSku")
    private String fMaterialId;

    /** 物料名称:调用k3接口传递字段FMaterialName--由物料编码带值 */
    @ApiModelProperty("物料名称:调用k3接口传递字段FMaterialName--由物料编码带值")
    private String fMaterialName;

    /** 规格型号:调用k3接口传递字段FMateriaModel--由物料编码带值 */
    @ApiModelProperty("规格型号:调用k3接口传递字段FMateriaModel--由物料编码带值")
    private String fMateriaModel;

    /** 库存单位:调用k3接口传递字段FUnitID--由物料编码带值，默认值：Pcs */
    @ApiModelProperty("库存单位:调用k3接口传递字段FUnitID--由物料编码带值，默认值：Pcs")
    private String fUnitId;

    /** 实发数量:调用k3接口传递字段FRealQty--取值outQuantity */
    @ApiModelProperty("实发数量:调用k3接口传递字段FRealQty--取值outQuantity")
    private BigDecimal fRealQty;

    /** 仓库:调用k3接口传递字段FStockID--取值whCode */
    @ApiModelProperty("仓库:调用k3接口传递字段FStockID--取值whCode")
    private String fStockId;

    /** 库存状态:调用k3接口传递字段FStockStatusID--由物料编码带值，默认值：KCZT01_SYS */
    @ApiModelProperty("库存状态:调用k3接口传递字段FStockStatusID--由物料编码带值，默认值：KCZT01_SYS")
    private String fStockStatusId;

    /** 开票品名:调用k3接口传递字段F_PAEZ_BaseProperty--由物料编码带值 */
    @ApiModelProperty("开票品名:调用k3接口传递字段F_PAEZ_BaseProperty--由物料编码带值")
    private String fPaezBaseProperty;

    /** 开票规格型号:调用k3接口传递字段F_PAEZ_BaseProperty1--由物料编码带值 */
    @ApiModelProperty("开票规格型号:调用k3接口传递字段F_PAEZ_BaseProperty1--由物料编码带值")
    private String fPaezBaseProperty1;

    /** 需求Team:调用k3接口传递字段F_BSC_Team--默认值：平台发展组 */
    @ApiModelProperty("需求Team:调用k3接口传递字段F_BSC_Team--默认值：平台发展组")
    private String fBscTeam;

    /** 需求部门:调用k3接口传递字段F_BSC_Dept--由需求Team带值 */
    @ApiModelProperty("需求部门:调用k3接口传递字段F_BSC_Dept--由需求Team带值")
    private String fBscDept;

}