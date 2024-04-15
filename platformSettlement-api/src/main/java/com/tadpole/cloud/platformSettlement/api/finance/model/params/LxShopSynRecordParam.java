package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* <p>
*
* </p>
*
* @author ty
* @since 2022-05-17
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("LX_SHOP_SYN_RECORD")
public class LxShopSynRecordParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty("SID")
    private BigDecimal sid;

    @ApiModelProperty("SYN_TYPE")
    private String synType;

    @ApiModelProperty("SYN_DATE")
    private String synDate;

    @ApiModelProperty("SYN_STATUS")
    private String synStatus;

    @ApiModelProperty("CREATE_DATE")
    private Date createDate;

    @ApiModelProperty("UPDATE_DATE")
    private Date updateDate;

}