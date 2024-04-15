package com.tadpole.cloud.externalSystem.modular.mabang.model.result;

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
    * K3调拨单明细项
    * </p>
*
* @author lsy
* @since 2022-06-09
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("K3_TRANSFER_ITEM")
public class K3TransferItemResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** K3调拨单明细项数据id，和ENTRY_ID保持一直 */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 调拨单据编号 */
    @ApiModelProperty("BILL_NO")
    private String billNo;

    /** 明细ID */
    @ApiModelProperty("ENTRY_ID")
    private BigDecimal entryId;

    /** 物料编码 */
    @ApiModelProperty("MATERIAL_CODE")
    private String materialCode;

    /** 物料名称 */
    @ApiModelProperty("MATERIAL_NAME")
    private String materialName;

    /** 调出仓库编码 */
    @ApiModelProperty("SRCSTOCK_CODE")
    private String srcstockCode;

    /** 调出仓库名称 */
    @ApiModelProperty("SRCSTOCK_NAME")
    private String srcstockName;

    /** 调入仓库编码 */
    @ApiModelProperty("DESTSTOCK_CODE")
    private String deststockCode;

    /** 调入仓库名称 */
    @ApiModelProperty("DESTSTOCK_NAME")
    private String deststockName;

    /** 调拨数量 */
    @ApiModelProperty("QTY")
    private BigDecimal qty;

    /** 单据状态 */
    @ApiModelProperty("DOCUMENT_STATUS")
    private String documentStatus;

    /** 单据创建日期 */
    @ApiModelProperty("CREATE_DATE")
    private Date createDate;

    /** 单据审核日期 */
    @ApiModelProperty("APPROVE_DATE")
    private Date approveDate;

    /** 单据作废状态 */
    @ApiModelProperty("CANCEL_STATUS")
    private String cancelStatus;

    /** 单据作废日期 */
    @ApiModelProperty("CANCEL_DATE")
    private Date cancelDate;

    /** 同步方式(0 ：系统同步,1：手动人工同步) */
    @ApiModelProperty("SYNC_TYPE")
    private String syncType;

    /** 同步时间 */
    @ApiModelProperty("SYNC_TIME")
    private Date syncTime;

    /** 同步状态(0 ：同步失败,1：同步成功) */
    @ApiModelProperty("SYNC_STATUS")
    private String syncStatus;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

}