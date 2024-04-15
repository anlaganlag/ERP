package com.tadpole.cloud.operationManagement.modular.shipment.entity;

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
    * 发货申请明细项
    * </p>
*
* @author lsy
* @since 2023-02-02
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SHIPMENT_APPLY_ITEM")
@ExcelIgnoreUnannotated
public class ShipmentApplyItem implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * ID;明细ID
     */
    @TableId(type = IdType.AUTO)
    private BigDecimal id;

    /**
     * 批次号
     */
    @TableField("BATCH_NO")
    private String batchNo;

    /**
     * D6维度申请批次号
     */
    @TableField("APPLY_BATCH_NO")
    private String applyBatchNo;

    /**
     * 交货地点编号;交货地点编号
     */
    @TableField("DELIVERYPOINT_NO")
    private String deliverypointNo;

    /**
     * 交货地点;默认联泰发货，还可以供应商发货
     */
    @TableField("DELIVERYPOINT")
    private String deliverypoint;

    /**
     * 发货推荐数据快照ID;只有提交的数据才会触发保存推荐数据快照
     */
    @TableField("RECOM_SNAPSHOT_ID")
    private BigDecimal recomSnapshotId;


    /**
     * 组织编码   Amazon_账号_站点_仓库 对应的组织编号
     */
    @TableField("ORG_CODE")
    private String  orgCode;


    /**
     * 平台
     */
    @TableField("PLATFORM")
    private String platform;

    /**
     * 区域
     */
    @TableField("AREA")
    private String area;

    /**
     * 部门
     */
    @TableField("DEPARTMENT")
    private String department;

    /**
     * Team
     */
    @TableField("TEAM")
    private String team;

    /**
     * 物料编码
     */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /**
     * 物料信息;物料信息json字符串
     */
    @TableField("MATERIAL_INFO")
    private String materialInfo;

    /**
     * 产品名称
     */
    @TableField("PRODUCT_NAME")
    private String productName;

    /**
     * ASIN
     */
    @TableField("ASIN")
    private String asin;

    /**
     * 运营大类
     */
    @TableField("PRODUCT_TYPE")
    private String productType;

    /**
     * 店铺名称
     */
    @TableField("SHOP_NAME")
    private String shopName;

    /**
     * 站点
     */
    @TableField("SYS_SITE")
    private String sysSite;

    /**
     * SKU
     */
    @TableField("SKU")
    private String sku;

    /**
     * SKU标签类型
     */
    @TableField("SYS_LABEL_TYPE")
    private String sysLabelType;

    /**
     * 账号
     */
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    /**
     * FBA号
     */
    @TableField("FBA_NO")
    private String fbaNo;

    /**
     * 调入仓库;接收仓库
     */
    @TableField("RECEIVE_WAREHOUSE")
    private String receiveWarehouse;

    /**
     * 调入仓库code;接收仓库code
     */
    @TableField("RECEIVE_WAREHOUSE_CODE")
    private String receiveWarehouseCode;

    /**
     * 发货数量
     */
    @TableField("SEND_QTY")
    private BigDecimal sendQty;

    /**
     * 发货方式;运输方式
     */
    @TableField("TRANSPORTATION_TYPE")
    private String transportationType;

    /**
     * UNW类型
     */
    @TableField("UNW_TYPE")
    private String unwType;

    /**
     * 备注1
     */
    @TableField("REMARK1")
    private String remark1;

    /**
     * 备注2
     */
    @TableField("REMARK2")
    private String remark2;

    /**
     * 备注3
     */
    @TableField("REMARK3")
    private String remark3;

    /**
     * 数据来源类型-BI推荐-手动选择-表格导入;数据来源类型-BI推荐-手动选择-表格导入
     */
    @TableField("DATA_SOURCE_TYPE")
    private String dataSourceType;

    /**
     * 库存校验;按批次校验
     */
    @TableField("STOCK_CHECK")
    private String stockCheck;

    /**
     * 合理性判断;按批次分析
     */
    @TableField("REASONABLE_CHECK")
    private String reasonableCheck;

    /** 按批次分析发货后周转天数*/
    @TableField("TURNOVER_AFTER_SEND_DAYS")
    private BigDecimal turnoverAfterSendDays;



    /**
     * 申请日期
     */
    @TableField("APPLY_DATE")
    private Date applyDate;

    /**
     * 申请状态;数据初始化为0--已提交1
     */
    @TableField("APPLY_STATUS")
    private Integer applyStatus;

    /**
     * 申请人
     */
    @TableField("APLLY_PERSON")
    private String apllyPerson;

    /**
     * 申请人编号
     */
    @TableField("APLLY_PERSON_NO")
    private String apllyPersonNo;

    /**
     * 审核状态;数据初始化为0--审核通过1--不通过2
     */
    @TableField("CHECK_STATUS")
    private Integer checkStatus;

    /**
     * 审核原因
     */
    @TableField("CHECK_REASON")
    private String checkReason;

    /**
     * 审核日期
     */
    @TableField("CHECK_DATE")
    private Date checkDate;

    /**
     * 审核人编号
     */
    @TableField("CHECK_PERSON_NO")
    private String checkPersonNo;

    /**
     * 审核人
     */
    @TableField("CHECK_PERSON")
    private String checkPerson;

    /**
     * 调拨单号;同步到k3的调拨单号
     */
    @TableField("BILL_NO")
    private String billNo;

    /**
     * 同步时间
     */
    @TableField("SYNC_TIME")
    private Date syncTime;

    /**
     * 同步K3状态;数据初始化为0-调用K3接口成功1--失败2
     */
    @TableField("SYNC_STATUS")
    private Integer syncStatus;

    /**
     * 同步请求参数
     */
    @TableField("SYNC_REQUEST_MSG")
    private String syncRequestMsg;

    /**
     * 同步结果
     */
    @TableField("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /**
     * 创建人
     */
    @TableField("CREATED_BY")
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField("CREATED_TIME")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField("UPDATED_BY")
    private String updatedBy;

    /**
     * 更新时间
     */
    @TableField("UPDATED_TIME")
    private Date updatedTime;


    /**
     * 申请后国内可调拨库存
     */
    @TableField("COMMITED_AVAIL_QTY")
    private BigDecimal commitedAvailQty;




    public String getPk() {
        return platform == null ? "Amazon"  + area + department + team + materialCode + asin
                                : platform  + area + department + team + materialCode + asin;
    }
}