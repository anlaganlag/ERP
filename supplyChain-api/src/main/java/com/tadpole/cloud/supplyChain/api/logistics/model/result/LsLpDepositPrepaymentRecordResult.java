package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 物流商押金&预付操作记录 出参类
 * </p>
 *
 * @author ty
 * @since 2023-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("LS_LP_DEPOSIT_PREPAYMENT_RECORD")
public class LsLpDepositPrepaymentRecordResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty("物流商押金&预付id")
    private BigDecimal pid;

    @ApiModelProperty("物流商编码")
    private String lpCode;

    @ApiModelProperty("操作类型：付款，编辑")
    private String optType;

    @ApiModelProperty("操作详情")
    private String optDetail;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

}
