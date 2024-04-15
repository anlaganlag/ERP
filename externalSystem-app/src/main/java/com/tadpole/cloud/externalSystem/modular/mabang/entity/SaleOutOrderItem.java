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
    * 销售出库单明细项
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
@TableName("SALE_OUT_ORDER_ITEM")
@ExcelIgnoreUnannotated
public class SaleOutOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;


    /** id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 销售出库单id */
    @TableField("SALE_OUT_ORDER_ID")
    private String saleOutOrderId;

    /** 马帮已完成订单明细项id */
    @TableField("MA_ORDER_ITEM_ID")
    private String maOrderItemId;

    /** K3销售出库单号,接口传递给k3 */
    @TableField("BILL_NO")
    private String billNo;

    /** 事业部 */
    @TableField("DEPARTMENT")
    private String department;

    /** Team */
    @TableField("TEAM")
    private String team;

    /** 平台名称 */
    @TableField("PLAT_NAME")
    private String platName;

    /** 店铺名称 */
    @TableField("SHOP_NAME")
    private String shopName;

    /** 站点:留3个字符位置，全球有一个国家简码是3个字符 */
    @TableField("SITE_CODE")
    private String siteCode;

    /** 仓库名称:根据仓库编码去K3仓库列表作业查询返回仓库名称，平台发展中心这块业务比较特殊，无法直接在MCMS中组装 */
    @TableField("WAREHOUSE_NAME")
    private String warehouseName;

    /** 仓库编码:K3中约定客户编码（不带尾巴的金蝶组织编码）对应的仓库编码是一样 */
    @TableField("WAREHOUSE_ID")
    private String warehouseId;

    /** 平台订单编号:传马帮已发货订单作业的platOrdId字段值 */
    @TableField("PLAT_ORD_ID")
    private String platOrdId;

    /** 平台SKU */
    @TableField("PLAT_SKU")
    private String platSku;

    /** 发货时间:传马帮已发货订单作业的expressTime字段值 */
    @TableField("SHIPPED_TIME")
    private Date shippedTime;

    /** 销售出库数量:传马帮已发货订单作业的quantity字段值 */
    @TableField("OUT_QTY")
    private BigDecimal outQty;

    /** 物料编码:调用k3接口传递字段FMaterialID--取值stockSku */
    @TableField("F_MATERIAL_ID")
    private String fMaterialId;

    /** 物料名称:调用k3接口传递字段FMaterialName--由物料编码带值 */
    @TableField("F_MATERIAL_NAME")
    private String fMaterialName;

    /** 规格型号:调用k3接口传递字段FMateriaModel--由物料编码带值 */
    @TableField("F_MATERIA_MODEL")
    private String fMateriaModel;

    /** 库存单位:调用k3接口传递字段FUnitID--由物料编码带值，默认值：Pcs */
    @TableField("F_UNIT_ID")
    private String fUnitId;

    /** 实发数量:调用k3接口传递字段FRealQty--取值outQuantity */
    @TableField("F_REAL_QTY")
    private BigDecimal fRealQty;

    /** 仓库:调用k3接口传递字段FStockID--取值whCode */
    @TableField("F_STOCK_ID")
    private String fStockId;

    /** 库存状态:调用k3接口传递字段FStockStatusID--由物料编码带值，默认值：KCZT01_SYS */
    @TableField("F_STOCK_STATUS_ID")
    private String fStockStatusId;

    /** 开票品名:调用k3接口传递字段F_PAEZ_BaseProperty--由物料编码带值 */
    @TableField("F_PAEZ_BASE_PROPERTY")
    private String fPaezBaseProperty;

    /** 开票规格型号:调用k3接口传递字段F_PAEZ_BaseProperty1--由物料编码带值 */
    @TableField("F_PAEZ_BASE_PROPERTY1")
    private String fPaezBaseProperty1;

    /** 需求Team:调用k3接口传递字段F_BSC_Team--默认值：平台发展组 */
    @TableField("F_BSC_TEAM")
    private String fBscTeam;

    /** 需求部门:调用k3接口传递字段F_BSC_Dept--由需求Team带值 */
    @TableField("F_BSC_DEPT")
    private String fBscDept;

    /** 是否作废Y/N，默认N（代表当前item是否作废） */
    @TableField("IS_INVALID")
    private String isInvalid;

    /** 同步时间 */
    @TableField("SYNC_TIME")
    private Date syncTime;

    /** 同步状态（-1数据初始化，0 ：同步失败,1：同步成功） */
    @TableField("SYNC_STATUS")
    private String syncStatus;

    /** 最后一次同步结果消息内容 */
    @TableField("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

}