package com.tadpole.cloud.product.api.productinterpolate.model.params;

import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;

/**
 * <p>
 * 产品线管理 入参类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_FO_REMINDER")
public class RdFoReminderParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统信息-产品线编号 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 系统信息-创建时间 */
    @ApiModelProperty("系统信息-创建时间")
    private Date sysCDate;

    /** 系统信息-最后更新时间 */
    @ApiModelProperty("系统信息-最后更新时间")
    private Date sysLDate;

    /** 系统信息-员工编号 */
    @ApiModelProperty("系统信息-员工编号")
    private String sysPerCode;

    /** 系统信息-员工姓名 */
    @ApiModelProperty("系统信息-员工姓名")
    private String sysPerName;

    /** 系统信息-提醒状态 值域{“待开放”,"提醒中","已完成"} */
    @ApiModelProperty("系统信息-提醒状态 值域{'待开放','提醒中','已完成'}")
    private String sysForStatus;

    /** 单据联系-预案编号 */
    @ApiModelProperty("单据联系-预案编号")
    private String sysYaCode;

    /** 提醒信息-提醒标题 */
    @ApiModelProperty("提醒信息-提醒标题")
    private String sysForTitle;

    /** 提醒信息-提醒对象 */
    @ApiModelProperty("提醒信息-提醒对象")
    private String sysForObject;

    /** 反馈信息-是否成功下单 值域{“是”,"否"} */
    @ApiModelProperty("反馈信息-是否成功下单 值域{'是','否'}")
    private String sysForIsOrderSuccess;

    /** 反馈信息-首单下单时间 */
    @ApiModelProperty("反馈信息-首单下单时间")
    private Date sysForOrderDate;

    /** 反馈信息-撤销下单原因 */
    @ApiModelProperty("反馈信息-撤销下单原因")
    private String sysForRevokeOrder;

    /** 反馈信息-撤销人编号 */
    @ApiModelProperty("反馈信息-撤销人编号")
    private String sysForRevokePerCode;

    /** 反馈信息-撤销人姓名 */
    @ApiModelProperty("反馈信息-撤销人姓名")
    private String sysForRevokePerName;

    /** 反馈信息-撤销操作时间 */
    @ApiModelProperty("反馈信息-撤销操作时间")
    private Date sysForRevokeDate;

}
