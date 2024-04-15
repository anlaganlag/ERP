package com.tadpole.cloud.externalSystem.modular.mabang.model.k3;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 根据K3仓库可用数量自动产生-销售出库单
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
public class SaleOutOrderK3QueryResult extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;


    @ApiModelProperty("调用k3接口传递字段FBillNo--调用k3接口传递字段FBillNo")
    private String fBillNo;


    @ApiModelProperty("调用k3接口传递字段FDate--年份+月份+1日--调用k3接口传递字段FDate--年份+月份+1日")
    private String fDate;


    @ApiModelProperty("F_CREATOR_ID--")
    private String fCreatorId;


    @ApiModelProperty("调用k3接口传递字段FSaleOrgId--销售组织编码--销售组织编码")
    private String fSaleOrgId;


    @ApiModelProperty("调用k3接口传递字段FCustomerID--默认值：店铺虚拟客户--默认值：店铺虚拟客户")
    private String fCustomerId;


    @ApiModelProperty("F_CORRESPOND_ORG_ID--")
    private String fCorrespondOrgId;


    @ApiModelProperty("F_PAYER_ID--")
    private String fPayerId;


    @ApiModelProperty("库存组织编码--调用k3接口传递字段FStockOrgId--库存组织编码")
    private String fStockOrgId;


    @ApiModelProperty("备注--调用k3接口传递字段FNote--默认值：id")
    private String fNote;


    @ApiModelProperty("是否创建了马帮采购单--默认值：-1数据初始化，0生成采购单失败，1生成采购单全部成功")
    private BigDecimal createPurchaseOrder;


    @ApiModelProperty("是否删除--是否删除:正常：0，删除：1")
    private String isDelete;


    @ApiModelProperty("同步方式--同步方式(0 ：系统同步,1：手动人工同步)")
    private String syncType;


    @ApiModelProperty("同步时间--同步时间")
    private Date syncTime;


    @ApiModelProperty("同步状态--同步状态(0 ：同步失败,1：同步成功)")
    private String syncStatus;


    @ApiModelProperty("同步请求参数--同步请求参数")
    private String syncRequstPar;


    @ApiModelProperty("同步结果消息内容--同步结果消息内容")
    private String syncResultMsg;


    @ApiModelProperty("创建时间--创建时间")
    private Date createTime;


    @ApiModelProperty("更新时间--更新时间")
    private Date updateTime;

//    ----------- item----------------


    @ApiModelProperty("销售出库单ID--销售出库单ID")
    private BigDecimal saleOutOrderId;


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


    @ApiModelProperty("销售价格SALE_PRICE")
    private BigDecimal salePrice;

    /** K3销售出库单创建日期开始时间 */
    @ApiModelProperty("K3销售出库单创建日期开始时间")
    private String startCreateTime;

    /** K3销售出库单创建日期结束时间 */
    @ApiModelProperty("K3销售出库单创建日期结束时间")
    private String endCreateTime;




}
