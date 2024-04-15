package com.tadpole.cloud.externalSystem.modular.mabang.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("MABANG_ALLOCATION_WAREHOUSE")
public class MabangAllocationWarehouse2Result implements Serializable, BaseValidatingParam {
    private static final long serialVersionUID = 1L;


    /** 分仓调拨单数据记录id,可以采用马帮返回的批次号 */
    @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** K3直接调拨单号 */
    @ExcelProperty("K3直接调拨单号")
    @ApiModelProperty("BILL_NO")
    private String billNo;

    /** K3调拨单审核日期 */
    @ExcelProperty("K3调拨单审核日期")
    @ApiModelProperty("APPROVE_DATE")
    private Date approveDate;

    @ExcelProperty("调拨单拆分数")
    @ApiModelProperty("SPLIT_NUM")
    private String splitNum;

    @ExcelProperty("调出仓库名称")
    @ApiModelProperty("START_WAREHOUSE_NAME")
    private String startWarehouseName;

    @ExcelProperty("调入仓库名称")
    @ApiModelProperty("TARGET_WAREHOUSE_NAME")
    private String targetWarehouseName;

    @ExcelProperty("马帮分仓调拨单批次号")
    @ApiModelProperty("ALLOCATION_CODE")
    private String allocationCode;

    @ExcelProperty("马帮分仓调拨单创建日期")
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 同步状态(0 ：同步失败,1：同步成功) */
    @ExcelProperty("推送马帮状态")
    @ApiModelProperty("SYNC_STATUS")
    private String syncStatus;

    /** 同步时间*/
    @ExcelProperty("推送马帮时间")
    @ApiModelProperty("SYNC_TIME")
    private Date syncTime;

    /** 物料编码 */
    @ExcelProperty("物料编码")
    @ApiModelProperty("MATERIAL_CODE")
    private String materialCode;

    /** 物料名称 */
    @ExcelProperty("物料名称")
    @ApiModelProperty("MATERIAL_NAME")
    private String materialName;

    /** 调拨数量 */
    @ExcelProperty("商品数量")
    @ApiModelProperty("QTY")
    private BigDecimal qty;

    /** 单位 */
    @ExcelProperty("单位")
    @ApiModelProperty("PCS")
    private String pcs;

    /** 同步结果消息内容 */
    @ExcelIgnore
    @ApiModelProperty("SYNC_RESULT_MSG")
    private String syncResultMsg;
}