package com.tadpole.cloud.product.api.productinterpolate.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
 * <p>
 * 产品线管理 实体类
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
@ExcelIgnoreUnannotated
public class RdFoReminder implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统信息-产品线编号 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 系统信息-创建时间 */
    @TableField("SYS_C_DATE")
    private Date sysCDate;

    /** 系统信息-最后更新时间 */
    @TableField("SYS_L_DATE")
    private Date sysLDate;

    /** 系统信息-员工编号 */
    @TableField("SYS_PER_CODE")
    private String sysPerCode;

    /** 系统信息-员工姓名 */
    @TableField("SYS_PER_NAME")
    private String sysPerName;

    /** 系统信息-提醒状态 值域{“待开放”,"提醒中","已完成"} */
    @TableField("SYS_FOR_STATUS")
    private String sysForStatus;

    /** 单据联系-预案编号 */
    @TableField("SYS_YA_CODE")
    private String sysYaCode;

    /** 提醒信息-提醒标题 */
    @TableField("SYS_FOR_TITLE")
    private String sysForTitle;

    /** 提醒信息-提醒对象 */
    @TableField("SYS_FOR_OBJECT")
    private String sysForObject;

    /** 反馈信息-是否成功下单 值域{“是”,"否"} */
    @TableField("SYS_FOR_IS_ORDER_SUCCESS")
    private String sysForIsOrderSuccess;

    /** 反馈信息-首单下单时间 */
    @TableField("SYS_FOR_ORDER_DATE")
    private Date sysForOrderDate;

    /** 反馈信息-撤销下单原因 */
    @TableField("SYS_FOR_REVOKE_ORDER")
    private String sysForRevokeOrder;

    /** 反馈信息-撤销人编号 */
    @TableField("SYS_FOR_REVOKE_PER_CODE")
    private String sysForRevokePerCode;

    /** 反馈信息-撤销人姓名 */
    @TableField("SYS_FOR_REVOKE_PER_NAME")
    private String sysForRevokePerName;

    /** 反馈信息-撤销操作时间 */
    @TableField("SYS_FOR_REVOKE_DATE")
    private Date sysForRevokeDate;

}