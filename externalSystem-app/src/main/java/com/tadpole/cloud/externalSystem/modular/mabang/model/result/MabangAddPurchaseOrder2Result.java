package com.tadpole.cloud.externalSystem.modular.mabang.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnore;
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
@TableName("MABANG_ADD_PURCHASE_ORDER")
public class MabangAddPurchaseOrder2Result implements Serializable, BaseValidatingParam {

    @TableId(value = "ID", type = IdType.AUTO)
    @ExcelIgnore
    private String id;

    /** K3调拨单据编号 */
    @ExcelProperty("K3调拨单编号")
    @ApiModelProperty("BILL_NO")
    private String billNo;

    /** K3单据审核日期 */
    @ExcelProperty("K3调拨单审核日期")
    @ApiModelProperty("APPROVE_DATE")
    private Date approveDate;

    /** 拆单编号，根据实际情况可以填写为P1,P2,P3,...，PN(N为拆分的单据数）,PN为分母，意味着总拆分订单数 */
    @ExcelProperty("调拨单拆分数")
    @ApiModelProperty("SPLIT_NUM")
    private String splitNum;

    /** 马帮仓库名称 */
    @ExcelProperty("仓库名称")
    @ApiModelProperty("WAREHOUSE_NAME")
    private String warehouseName;

    /** 同步状态(0 ：同步失败,1：同步成功) */
    @ExcelIgnore
    @ApiModelProperty("SYNC_STATUS")
    private String syncStatus;

    @ExcelProperty("推送马帮状态")
    @ApiModelProperty("syncStatusName")
    private String syncStatusName;

    /** 同步时间*/
    @ExcelProperty("推送马帮时间")
    @ApiModelProperty("SYNC_TIME")
    private Date syncTime;

    /** 采购批次号*/
    @ApiModelProperty("GROUP_ID")
    @ExcelProperty("马帮采购批次号")
    private String groupId;

    /** 同步记录明细id */
    @ExcelIgnore
    @ApiModelProperty("IID")
    private String iid;

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

    /** 调出仓库名称 */
    @ExcelProperty("调出仓库名称")
    @ApiModelProperty("SRCSTOCK_NAME")
    private String srcStockName;

    /** 调入仓库名称 */
    @ExcelProperty("调入仓库名称")
    @ApiModelProperty("DESTSTOCK_NAM")
    private String destStockNam;

    /** 同步结果消息内容 */
    @ExcelProperty("同步返回消息")
    @ApiModelProperty("SYNC_RESULT_MSG")
    private String syncResultMsg;
}
