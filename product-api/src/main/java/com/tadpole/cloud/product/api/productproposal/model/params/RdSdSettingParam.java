package com.tadpole.cloud.product.api.productproposal.model.params;

import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;

/**
 * <p>
 * 提案-设置-拿样任务时长设置 入参类
 * </p>
 *
 * @author S20190096
 * @since 2023-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_SD_SETTING")
public class RdSdSettingParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统编号 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 系统实时时间 */
    @ApiModelProperty("系统实时时间")
    private Date sysCDate;

    /** 系统实时时间 */
    @ApiModelProperty("系统实时时间")
    private Date sysLDate;

    /** 登陆用户员工编号 */
    @ApiModelProperty("登陆用户员工编号")
    private String sysPerCode;

    /** 登陆用户员工姓名 */
    @ApiModelProperty("登陆用户员工姓名")
    private String sysPerName;

    /** 样品类型 值域{“现货拿样”,"定制拿样"} */
    @ApiModelProperty("样品类型 值域{'现货拿样','定制拿样'}")
    private String sysSampleType;

    /** 反馈时长 */
    @ApiModelProperty("反馈时长")
    private BigDecimal sysFebkDuration;

    /** 拿样时长 */
    @ApiModelProperty("拿样时长")
    private BigDecimal sysSamplingDuration;

}
