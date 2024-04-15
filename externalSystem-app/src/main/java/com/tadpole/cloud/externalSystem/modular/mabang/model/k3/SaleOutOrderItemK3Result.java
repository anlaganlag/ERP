package com.tadpole.cloud.externalSystem.modular.mabang.model.k3;


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
    * 根据K3仓库可用数量自动产生-销售出库单明细项
    * </p>
*
* @author lsy
* @since 2023-04-07
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("SALE_OUT_ORDER_ITEM_K3")
public class SaleOutOrderItemK3Result implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;


    @ApiModelProperty("销售出库单ID--销售出库单ID")
    private BigDecimal saleOutOrderId;


    @ApiModelProperty("调用k3接口传递字段FBillNo--调用k3接口传递字段FBillNo")
    private String fBillNo;


    @ApiModelProperty("物料编码--取值stockSku")
    private String fMaterialId;


    @ApiModelProperty("物料名称--调用k3接口传递字段FMaterialName--由物料编码带值")
    private String fMaterialName;


    @ApiModelProperty("规格型号--调用k3接口传递字段FMateriaModel--由物料编码带值")
    private String fMateriaModel;


    @ApiModelProperty("库存单位--由物料编码带值，默认值：Pcs")
    private String fUnitId;


    @ApiModelProperty("实发数量--调用k3接口传递字段FRealQty--取值outQuantity")
    private String fRealQty;


    @ApiModelProperty("仓库ID--调用k3接口传递字段FStockID--取值whCode")
    private String fStockId;


    @ApiModelProperty("仓库名称--")
    private String warehouseName;


    @ApiModelProperty("库存状态--默认值：KCZT01_SYS")
    private String fStockStatusId;


    @ApiModelProperty("开票品名--由物料编码带值")
    private String fPaezBaseProperty;


    @ApiModelProperty("开票规格型号--由物料编码带值")
    private String fPaezBaseProperty1;


    @ApiModelProperty("需求Team--默认值：平台发展组")
    private String fBscTeam;


    @ApiModelProperty("需求部门--由需求Team带值")
    private String fBscDept;


    @ApiModelProperty("F_BSC_SubRemark1--")
    private String fBscSubremark1;


    @ApiModelProperty("F_CustMat_ID--")
    private String fCustmatId;


    @ApiModelProperty("F_Customer_SKU--")
    private String fCustomerSku;


    @ApiModelProperty("F_Owner_Type_ID--")
    private String fOwnerTypeId;


    @ApiModelProperty("F_SALBASE_QTY--")
    private BigDecimal fSalbaseQty;


    @ApiModelProperty("F_PRICEBASE_QTY--")
    private BigDecimal fPricebaseQty;


    @ApiModelProperty("F_BASEUNIT_QTY--")
    private BigDecimal fBaseunitQty;


    @ApiModelProperty("调用马帮erp新增采购单返回的采购批次号--")
    private String groupId;


    @ApiModelProperty("是否删除--是否删除:正常：0，删除：1")
    private String isDelete;


    @ApiModelProperty("创建时间--创建时间")
    private Date createTime;


    @ApiModelProperty("更新时间--更新时间")
    private Date updateTime;

}
