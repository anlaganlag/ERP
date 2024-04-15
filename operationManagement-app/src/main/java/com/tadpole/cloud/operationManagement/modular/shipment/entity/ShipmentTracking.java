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
    * 发货追踪汇总表
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
@TableName("SHIPMENT_TRACKING")
@ExcelIgnoreUnannotated
public class ShipmentTracking implements Serializable {

    private static final long serialVersionUID = 1L;


    /** ID;明细ID */
    @TableId(type = IdType.AUTO)
    private BigDecimal id;

    /** 发货申请批次号;D6维度申请批次号 */
    @TableField("APPLY_BATCH_NO")
    private String applyBatchNo;

    /** 数据来源类型-BI推荐-手动选择-表格导入;数据来源类型-BI推荐-手动选择-表格导入 */
    @TableField("DATA_SOURCE_TYPE")
    private String dataSourceType;

    /** 申请日期 */
    @TableField("APPLY_DATE")
    private Date applyDate;

    /** 申请人 */
    @TableField("APLLY_PERSON")
    private String apllyPerson;

    /** 申请人编号 */
    @TableField("APLLY_PERSON_NO")
    private String apllyPersonNo;

    /** 平台 */
    @TableField("PLATFORM")
    private String platform;

    /** 区域 */
    @TableField("AREA")
    private String area;

    /** 部门 */
    @TableField("DEPARTMENT")
    private String department;

    /** Team */
    @TableField("TEAM")
    private String team;

    /** 物料编码 */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /** 运营大类 */
    @TableField("PRODUCT_TYPE")
    private String productType;

    /** 产品名称 */
    @TableField("PRODUCT_NAME")
    private String productName;

    /** ASIN */
    @TableField("ASIN")
    private String asin;

    /** 发货数量 */
    @TableField("SEND_QTY")
    private BigDecimal sendQty;

    /** 发货方式;运输方式 */
    @TableField("TRANSPORTATION_TYPE")
    private String transportationType;

    /** 申请状态;数据初始化为0--已提交1 */
    @TableField("APPLY_STATUS")
    private Integer applyStatus;

    /** 审核状态;数据初始化为0--审核通过1--不通过2 */
    @TableField("CHECK_STATUS")
    private Integer checkStatus;

    /** 审核原因 */
    @TableField("CHECK_REASON")
    private String checkReason;

    /** 审核日期 */
    @TableField("CHECK_DATE")
    private Date checkDate;

    /** 审核人编号 */
    @TableField("CHECK_PERSON_NO")
    private String checkPersonNo;

    /** 审核人 */
    @TableField("CHECK_PERSON")
    private String checkPerson;

    /** 跟踪状态 */
    @TableField("TRACKING_STATUS")
    private String trackingStatus;

    /** 实际发货数量合计 */
    @TableField("ACTUAL_SEND_QTY")
    private BigDecimal actualSendQty;

    /** 实际到货数量合计 */
    @TableField("ACTUAL_ARRIVAL_QTY")
    private BigDecimal actualArrivalQty;

    /** 同步时间 */
    @TableField("SYNC_TIME")
    private Date syncTime;

    /** 同步K3状态;数据初始化为0-调用K3接口成功1--失败2 */
    @TableField("SYNC_STATUS")
    private Integer syncStatus;

    /** 同步请求参数 */
    @TableField("SYNC_REQUEST_MSG")
    private String syncRequestMsg;

    /** 同步结果 */
    @TableField("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 是否需要追踪;0:需要追踪，1：不需要追踪 */
    @TableField("NEED_TRACK")
    private Integer needTrack;

    /** 创建人 */
    @TableField("CREATED_BY")
    private String createdBy;

    /** 创建时间 */
    @TableField("CREATED_TIME")
    private Date createdTime;

    /** 更新人 */
    @TableField("UPDATED_BY")
    private String updatedBy;

    /** 更新时间 */
    @TableField("UPDATED_TIME")
    private Date updatedTime;


    /** 物料信息;物料信息json字符串  */
    @TableField("MATERIAL_INFO")
    private String materialInfo;


}