package com.tadpole.cloud.product.api.productinterpolate.model.result;

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
 * 产品线管理 出参类
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
@ExcelIgnoreUnannotated
@TableName("RD_FO_REMINDER")
public class RdFoReminderResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;


    @ApiModelProperty("系统信息-创建时间")
    private Date sysCDate;


    @ApiModelProperty("系统信息-最后更新时间")
    private Date sysLDate;


    @ApiModelProperty("系统信息-员工编号")
    private String sysPerCode;


    @ApiModelProperty("系统信息-员工姓名")
    private String sysPerName;


    @ApiModelProperty("系统信息-提醒状态 值域{'待开放','提醒中','已完成'}")
    private String sysForStatus;


    @ApiModelProperty("单据联系-预案编号")
    private String sysYaCode;


    @ApiModelProperty("提醒信息-提醒标题")
    private String sysForTitle;


    @ApiModelProperty("提醒信息-提醒对象")
    private String sysForObject;


    @ApiModelProperty("反馈信息-是否成功下单 值域{'是','否'}")
    private String sysForIsOrderSuccess;


    @ApiModelProperty("反馈信息-首单下单时间")
    private Date sysForOrderDate;


    @ApiModelProperty("反馈信息-撤销下单原因")
    private String sysForRevokeOrder;


    @ApiModelProperty("反馈信息-撤销人编号")
    private String sysForRevokePerCode;


    @ApiModelProperty("反馈信息-撤销人姓名")
    private String sysForRevokePerName;


    @ApiModelProperty("反馈信息-撤销操作时间")
    private Date sysForRevokeDate;

}
