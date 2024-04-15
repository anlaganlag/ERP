package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
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
 * 物流商管理 入参类
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
@TableName("LS_LOGISTICS_PROVIDER")
public class LsLogisticsProviderParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 物流商编码 */
    @ApiModelProperty("物流商编码")
    private String lpCode;

    /** 物流商名称 */
    @ApiModelProperty("物流商名称")
    private String lpName;

    /** 物流商简称 */
    @ApiModelProperty("物流商简称")
    private String lpSimpleName;

    /** 通讯地址 */
    @ApiModelProperty("通讯地址")
    private String lpAddress;

    /** 统一社会信用代码 */
    @ApiModelProperty("统一社会信用代码")
    private String lpUniSocCreCode;

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
