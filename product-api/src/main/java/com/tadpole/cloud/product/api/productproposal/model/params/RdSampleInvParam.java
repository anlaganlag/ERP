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
 * 提案-开发样盘点 入参类
 * </p>
 *
 * @author S20190096
 * @since 2024-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_SAMPLE_INV")
public class RdSampleInvParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统信息-盘点编号 */
    @ApiModelProperty("系统信息-盘点编号")
    @TableId(value = "SYS_INV_CODE", type = IdType.ASSIGN_ID)
    private String sysInvCode;

    /** 系统信息-创建时间 */
    @ApiModelProperty("系统信息-创建时间")
    private Date sysCDate;

    /** 系统信息-最后更新时间 */
    @ApiModelProperty("系统信息-最后更新时间")
    private Date sysLDate;

    /** 系统信息-部门编号 */
    @ApiModelProperty("系统信息-部门编号")
    private String sysDeptCode;

    /** 系统信息-部门名称 */
    @ApiModelProperty("系统信息-部门名称")
    private String sysDeptName;

    /** 系统信息-盘点开始时间 */
    @ApiModelProperty("系统信息-盘点开始时间")
    private Date sysInvStartDate;

    /** 系统信息-盘点结束时间 */
    @ApiModelProperty("系统信息-盘点结束时间")
    private Date sysInvEndDate;

    /** 系统信息-盘点负责人编号 */
    @ApiModelProperty("系统信息-盘点负责人编号")
    private String sysInvPerCode;

    /** 系统信息-盘点负责人姓名 */
    @ApiModelProperty("系统信息-盘点负责人姓名")
    private String sysInvPerName;

    /** 系统信息-盘点状态 值域{“盘点中”,"已完结"} */
    @ApiModelProperty("系统信息-盘点状态 值域{'盘点中','已完结'}")
    private String sysInvStatus;

}
