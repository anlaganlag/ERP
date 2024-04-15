package com.tadpole.cloud.externalSystem.modular.mabang.model.params;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
    * K3采购入库单
    * </p>
*
* @author S20190161
* @since 2023-05-17
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("K3_PURCHASE_IN_STOCK_ORDER")
public class K3PurchaseInStockOrderParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID */
   @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 单据编号 */
    @ApiModelProperty("单据编号")
    private String fBillNo;

    /** 单据类型 */
    @ApiModelProperty("单据类型")
    private String fBillTypeId;

    /** 入库日期 */
    @ApiModelProperty("入库日期")
    private Date fDate;

    /** 收料组织 */
    @ApiModelProperty("收料组织")
    private String fStockOrgId;

    /** 采购组织 */
    @ApiModelProperty("采购组织")
    private String fPurchaseOrgId;

    /** 供应商 */
    @ApiModelProperty("供应商")
    private String fSupplierId;

    /** 货主类型 */
    @ApiModelProperty("货主类型")
    private String fOwnerTypeIdHead;

    /** 货主 */
    @ApiModelProperty("货主")
    private String fOwnerIdHead;

    /** 结算组织 */
    @ApiModelProperty("结算组织")
    private String fSettleOrgId;

    /** 结算币别 */
    @ApiModelProperty("结算币别")
    private String fSettleCurrId;

    /** 定价时点 */
    @ApiModelProperty("定价时点")
    private String fPriceTimePoint;

    /** 同步方式 */
    @ApiModelProperty("同步方式")
    private String syncType;

    /** 同步时间 */
    @ApiModelProperty("同步时间")
    private Date syncTime;

    /** 同步状态;同步状态(-1：初始化，0 ：同步失败,1：同步成功) */
    @ApiModelProperty("同步状态;同步状态(-1：初始化，0 ：同步失败,1：同步成功)")
    private String syncStatus;

    /** 同步请求参数 */
    @ApiModelProperty("同步请求参数")
    private String syncRequstPar;

    /** 同步结果消息内容 */
    @ApiModelProperty("同步结果消息内容")
    private String syncResultMsg;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createdBy;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createdTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updatedBy;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updatedTime;

   /** 需求TEAM */
   @ApiModelProperty("需求TEAM")
   private String fPaezBase2;

   @ApiModelProperty("仓库编码")
   private String fStockId;


   /** 物料编码 */
   @ApiModelProperty("物料编码")
   private String fMaterialId;

   /** K3采购入库单创建日期开始时间 */
   @ApiModelProperty("K3销售出库单创建日期开始时间")
   private String startCreateTime;

   /** K3采购入库单创建日期结束时间 */
   @ApiModelProperty("K3销售出库单创建日期结束时间")
   private String endCreateTime;

}
