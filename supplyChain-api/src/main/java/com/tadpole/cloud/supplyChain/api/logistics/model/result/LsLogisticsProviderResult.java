package com.tadpole.cloud.supplyChain.api.logistics.model.result;

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
 * 物流商管理 出参类
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
@ExcelIgnoreUnannotated
@TableName("LS_LOGISTICS_PROVIDER")
public class LsLogisticsProviderResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty("物流商编码")
    private String lpCode;

    @ApiModelProperty("物流商名称")
    private String lpName;

    @ApiModelProperty("物流商简称")
    private String lpSimpleName;

    @ApiModelProperty("通讯地址")
    private String lpAddress;

    @ApiModelProperty("统一社会信用代码")
    private String lpUniSocCreCode;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

}
