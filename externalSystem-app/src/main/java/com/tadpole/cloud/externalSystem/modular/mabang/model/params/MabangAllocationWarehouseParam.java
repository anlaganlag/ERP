package com.tadpole.cloud.externalSystem.modular.mabang.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 马帮分仓调拨单
    * </p>
*
* @author lsy
* @since 2022-06-15
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("MABANG_ALLOCATION_WAREHOUSE")
public class MabangAllocationWarehouseParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 分仓调拨单数据记录id,可以采用马帮返回的批次号 */
   @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 同步概要记录id */
    @ApiModelProperty("同步概要记录id")
    private String summaryId;

    /** K3调拨单据编号 */
    @ApiModelProperty("K3调拨单据编号")
    private String billNo;

    /** 分仓调拨拆单总数，根据实际情况可以填写为P1,P2,P3,...，PN(N为拆分的单据数）,PN为分母，意味着总拆分订单数 */
    @ApiModelProperty("分仓调拨拆单总数，根据实际情况可以填写为P1,P2,P3,...，PN(N为拆分的单据数）,PN为分母，意味着总拆分订单数")
    private String splitTotal;

    /** 拆单编号，根据实际情况可以填写为P1,P2,P3,...，PN(N为拆分的单据数）,PN为分母，意味着总拆分订单数 */
    @ApiModelProperty("拆单编号，根据实际情况可以填写为P1,P2,P3,...，PN(N为拆分的单据数）,PN为分母，意味着总拆分订单数")
    private String splitNum;

    /** 批次号 */
    @ApiModelProperty("批次号")
    private String allocationCode;

    /** 该直接调拨单本次同步记录是否触发了反审核：0未触发，1已触发 */
    @TableField("ANTI_AUDIT")
    private int antiAudit;

    /** 反向创建分仓调拨单code */
    @ApiModelProperty("反向创建分仓调code")
    private String reverseAllocationCode;

    /** K3调拨单据状态 */
    @ApiModelProperty("K3调拨单据状态")
    private String documentStatus;

    /** K3单据创建日期 */
    @ApiModelProperty("K3单据创建日期")
    private Date createDate;

    /** K3单据审核日期 */
    @ApiModelProperty("K3单据审核日期")
    private Date approveDate;

    /** K3单据作废状态 */
    @ApiModelProperty("K3单据作废状态")
    private String cancelStatus;

    /** K3单据作废日期 */
    @ApiModelProperty("K3单据作废日期")
    private Date cancelDate;

    /** 起始仓库名称 */
    @ApiModelProperty("起始仓库名称")
    private String startWarehouseName;

    /** 目标仓库名称 */
    @ApiModelProperty("目标仓库名称")
    private String targetWarehouseName;

    /** 调拨商品清单 */
    @ApiModelProperty("调拨商品清单")
    private String sku;

    /** 起始仓库ID */
    @ApiModelProperty("起始仓库ID")
    private String startWarehouseId;

    /** 目标仓库ID */
    @ApiModelProperty("目标仓库ID")
    private String targetWarehouseId;

    /** 运输方式 1.陆地运输 2.空运 3.海运 */
    @ApiModelProperty("运输方式 1.陆地运输 2.空运 3.海运")
    private String transportType;

    /** 运费 */
    @ApiModelProperty("运费")
    private BigDecimal freight;

    /** 物流渠道 */
    @ApiModelProperty("物流渠道")
    private String channel;

    /** 物流单号 */
    @ApiModelProperty("物流单号")
    private String trackNumber;

    /** 运费分摊方式 1.重量,2体积重 */
    @ApiModelProperty("运费分摊方式 1.重量,2体积重")
    private String shareMethod;

    /** 备注 */
    @ApiModelProperty("备注")
    private String remark;

    /** 是否删除:正常：0，删除：1 */
    @ApiModelProperty("是否删除:正常：0，删除：1")
    private String isDelete;

    /** 同步方式(0 ：系统同步,1：手动人工同步) */
    @ApiModelProperty("同步方式(0 ：系统同步,1：手动人工同步)")
    private String syncType;

    /** 同步时间 */
    @ApiModelProperty("同步时间")
    private Date syncTime;

    /** 同步状态(0 ：同步失败,1：同步成功) */
    @ApiModelProperty("同步状态(0 ：同步失败,1：同步成功)")
    private String syncStatus;

    /** 同步成功次数(反审核通过情况次数增加) */
    @ApiModelProperty("同步成功次数(反审核通过情况次数增加)")
    private BigDecimal syncSuccessTimes;

    /** 同步失败次数 */
    @ApiModelProperty("同步失败次数")
    private BigDecimal syncFailTimes;

    /** 同步结果消息内容 */
    @ApiModelProperty("同步结果消息内容")
    private String syncResultMsg;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;


    @ApiModelProperty("分仓调拨单创建开始日期")
    private Date createStartDate;

    @ApiModelProperty("分仓调拨单创建结束日期")
    private Date createEndDate;

    @ApiModelProperty("推送马帮开始时间")
    private Date syncStartTime;

    @ApiModelProperty("推送马帮结束时间")
    private Date syncEndTime;
}