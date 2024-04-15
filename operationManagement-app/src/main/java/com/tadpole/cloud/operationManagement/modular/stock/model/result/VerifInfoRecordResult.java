package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
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
    * 审核记录信息
    * </p>
*
* @author lsy
* @since 2022-06-14
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("STOCK_VERIF_INFO_RECORD")
public class VerifInfoRecordResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 审核记录信息id */
   @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 采购申请单ID */
    @ApiModelProperty("PURCHASE_ORDER_ID")
    private String purchaseOrderId;

    /** 审核业务类型：事业部审核10，计划部审核20，PMC审核30  */
    @ApiModelProperty("VERIF_BIZ_TYPE")
    private BigDecimal verifBizType;

    /** 值域{"自动"/"人工"} */
    @ApiModelProperty("VERIF_TYPE")
    private String verifType;

    /** 审批人员工编号 */
    @ApiModelProperty("VERIF_PERSON_NO")
    private String verifPersonNo;

    /** 审批人姓名 */
    @ApiModelProperty("VERIF_PERSON_NAME")
    private String verifPersonName;

    /** 部审批日期 */
    @ApiModelProperty("VERIF_DATE")
    private Date verifDate;

    /** 审核说明,事业部审核会填写 */
    @ApiModelProperty("VERIF_REASON")
    private String verifReason;

    /** 备注 */
    @ApiModelProperty("REMARK")
    private String remark;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

    //同步时间
    @TableField("SYNC_TIME")
    private Date syncTime;

    //同步状态(0 ：同步失败,1：同步成功)
    @TableField("SYNC_STATUS")
    private String syncStatus;

    //同步请求消息
    @TableField("SYNC_REQUEST_MSG")
    private String syncRequestMsg;

    //同步请求结果
    @TableField("SYNC_RESULT_MSG")
    private String syncResultMsg;

}