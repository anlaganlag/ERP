package com.tadpole.cloud.product.modular.product.model.params;

import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;

/**
 * <p>
 *  入参类
 * </p>
 *
 * @author S20210221
 * @since 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_MANAGE_UP_RECORD")
public class RdManageUpRecordParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("")
    private String sysType;

    @ApiModelProperty("")
    private Date sysLDate;

    @ApiModelProperty("")
    private String sysPerCode;

    @ApiModelProperty("")
    private String sysPerName;

    @ApiModelProperty("")
    private String code;

    @ApiModelProperty("")
    private String sysModifyRecordNum;

    @ApiModelProperty("")
    private String sysModifyContent;

}
