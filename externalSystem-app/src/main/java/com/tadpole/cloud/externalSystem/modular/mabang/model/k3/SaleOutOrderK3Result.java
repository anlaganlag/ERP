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
@TableName("SALE_OUT_ORDER_K3")
public class SaleOutOrderK3Result implements Serializable, BaseValidatingParam {

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

}
