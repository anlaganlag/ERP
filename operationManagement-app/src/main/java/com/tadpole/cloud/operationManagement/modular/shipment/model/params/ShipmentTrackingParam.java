package com.tadpole.cloud.operationManagement.modular.shipment.model.params;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
public class ShipmentTrackingParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID;明细ID */
    @ApiModelProperty("ID;明细ID")
    private BigDecimal id;

    /** 发货申请批次号;D6维度申请批次号 */
    @ApiModelProperty("发货申请批次号;D6维度申请批次号")
    private String applyBatchNo;

    /** 数据来源类型-BI推荐-手动选择-表格导入;数据来源类型-BI推荐-手动选择-表格导入 */
    @ApiModelProperty("数据来源类型-BI推荐-手动选择-表格导入;数据来源类型-BI推荐-手动选择-表格导入")
    private String dataSourceType;

    /** 申请日期 */
    @ApiModelProperty("申请日期")
    private Date applyDate;

    /** 申请人 */
    @ApiModelProperty("申请人")
    private String apllyPerson;

    /** 申请人编号 */
    @ApiModelProperty("申请人编号")
    private String apllyPersonNo;

    /** 平台 */
    @ApiModelProperty("平台")
    private String platform;

    /** 区域 */
    @ApiModelProperty("区域")
    private String area;

    /** 部门 */
    @ApiModelProperty("部门")
    private String department;

    /** Team */
    @ApiModelProperty("Team")
    private String team;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 运营大类 */
    @ApiModelProperty("运营大类")
    private String productType;

    /** 产品名称 */
    @ApiModelProperty("产品名称")
    private String productName;

    /** ASIN */
    @ApiModelProperty("ASIN")
    private String asin;

    /** 发货数量 */
    @ApiModelProperty("发货数量")
    private BigDecimal sendQty;

    /** 发货方式;运输方式 */
    @ApiModelProperty("发货方式;运输方式")
    private String transportationType;

    /** 申请状态;数据初始化为0--已提交1 */
    @ApiModelProperty("申请状态;数据初始化为0--已提交1")
    private Integer applyStatus;

    /** 审核状态;数据初始化为0--审核通过1--不通过2 */
    @ApiModelProperty("审核状态;数据初始化为0--审核通过1--不通过2")
    private Integer checkStatus;

    /** 审核原因 */
    @ApiModelProperty("审核原因")
    private String checkReason;

    /** 审核日期 */
    @ApiModelProperty("审核日期")
    private Date checkDate;

    /** 审核人编号 */
    @ApiModelProperty("审核人编号")
    private String checkPersonNo;

    /** 审核人 */
    @ApiModelProperty("审核人")
    private String checkPerson;

    /** 跟踪状态 */
    @ApiModelProperty("跟踪状态")
    private String trackingStatus;

    /** 实际发货数量合计 */
    @ApiModelProperty("实际发货数量合计")
    private BigDecimal actualSendQty;

    /** 实际到货数量合计 */
    @ApiModelProperty("实际到货数量合计")
    private BigDecimal actualArrivalQty;

    /** 同步时间 */
    @ApiModelProperty("同步时间")
    private Date syncTime;

    /** 同步K3状态;数据初始化为0-调用K3接口成功1--失败2 */
    @ApiModelProperty("同步K3状态;数据初始化为0-调用K3接口成功1--失败2")
    private Integer syncStatus;

    /** 同步请求参数 */
    @ApiModelProperty("同步请求参数")
    private String syncRequestMsg;

    /** 同步结果 */
    @ApiModelProperty("同步结果")
    private String syncResultMsg;

    /** 是否需要追踪;0:需要追踪，1：不需要追踪 */
    @ApiModelProperty("是否需要追踪;0:需要追踪，1：不需要追踪")
    private Integer needTrack;

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


    /** 物料编码列表 */
    @ExcelProperty("物料编码列表")
    @ApiModelProperty("物料编码列表")
    private List<String> materialCodeList;


    /** asin列表 */
    @ExcelProperty("asin列表")
    @ApiModelProperty("asin列表")
    private List<String> asinList;


    /** 事业部列表 */
    @ExcelProperty("事业部列表")
    @ApiModelProperty("事业部列表")
    private List<String> departmentList;


    /** team列表 */
    @ExcelProperty("team列表")
    @ApiModelProperty("team列表")
    private List<String> teamList;


    /** 区域列表 */
    @ExcelProperty("区域列表")
    @ApiModelProperty("区域列表")
    private List<String> areaList;



}
