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
import java.util.List;

/**
 * <p>
 * 物流商押金&预付 入参类
 * </p>
 *
 * @author ty
 * @since 2023-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("LS_LP_DEPOSIT_PREPAYMENT")
public class LsLpDepositPrepaymentParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 物流商编码 */
    @ApiModelProperty("物流商编码")
    private String lpCode;

    @ApiModelProperty("物流商名称")
    private String lpName;

    @ApiModelProperty("物流商简称")
    private String lpSimpleName;

    /** 通讯地址 */
    @ApiModelProperty("通讯地址")
    private String lpAddress;

    @ApiModelProperty("统一社会信用代码")
    private String lpUniSocCreCode;

    /** 押金 */
    @ApiModelProperty("押金")
    private BigDecimal depositAmt;

    /** 押金币种 */
    @ApiModelProperty("押金币种")
    private String depositCurrency;

    /** 预付款 */
    @ApiModelProperty("预付款")
    private BigDecimal prepaymentAmt;

    /** 预付款币种 */
    @ApiModelProperty("预付款币种")
    private String paymentCurrency;

    /** 备注 */
    @ApiModelProperty("备注")
    private String remark;

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

    @ApiModelProperty("物流商编码集合")
    private List<String> lpCodeList;

    @ApiModelProperty("物流商名称集合")
    private List<String> lpNameList;

    @ApiModelProperty("物流商简称集合")
    private List<String> lpSimpleNameList;

    @ApiModelProperty("统一社会信用代码集合")
    private List<String> lpUniSocCreCodeList;

}
