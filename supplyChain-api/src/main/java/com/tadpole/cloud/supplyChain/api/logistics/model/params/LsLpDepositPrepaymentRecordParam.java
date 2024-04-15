package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
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
 * 物流商押金&预付操作记录 入参类
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
@TableName("LS_LP_DEPOSIT_PREPAYMENT_RECORD")
public class LsLpDepositPrepaymentRecordParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 物流商押金&预付id */
    @ApiModelProperty("物流商押金&预付id")
    private BigDecimal pid;

    /** 物流商编码 */
    @ApiModelProperty("物流商编码")
    private String lpCode;

    /** 操作类型：付款，编辑 */
    @ApiModelProperty("操作类型：付款，编辑")
    private String optType;

    /** 操作详情 */
    @ApiModelProperty("操作详情")
    private String optDetail;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updateUser;

}
