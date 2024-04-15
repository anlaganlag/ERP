package com.tadpole.cloud.product.modular.product.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 产品同款管理 实体类
 * </p>
 *
 * @author S20210221
 * @since 2023-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_PSS_MANAGE")
@ExcelIgnoreUnannotated
public class RdPssManage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    @TableField("ID")
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 系统信息-SPU */
    @TableField("SYS_SPU")
    @ApiModelProperty("SPU")
    private String sysSpu;

    /** 系统信息-产品线编号 */
    @TableField("SYS_PL_CODE")
    @ApiModelProperty("产品线编号")
    private String sysPlCode;

    /** 系统信息-创建时间 */
    @TableField("SYS_C_DATE")
    @ApiModelProperty("创建时间")
    private Date sysCDate;

    /** 系统信息-最后更新时间 */
    @TableField("SYS_L_DATE")
    @ApiModelProperty("最后更新时间")
    private Date sysLDate;

    /** 系统信息-状态 */
    @TableField("SYS_STATUS")
    @ApiModelProperty("状态")
    private String sysStatus;

    /** 同款设定-产品名称 */
    @TableField("SYS_PRO_NAME")
    @ApiModelProperty("产品名称")
    private String sysProName;

    /** 同款设定-款式 */
    @TableField("SYS_STYLE")
    @ApiModelProperty("款式")
    private String sysStyle;

    /** 同款设定-主材料 */
    @TableField("SYS_MAIN_MATERIAL")
    @ApiModelProperty("主材料")
    private String sysMainMaterial;

    /** 同款设定-适用品牌或对象 */
    @TableField("SYS_BRAND")
    @ApiModelProperty("适用品牌或对象")
    private String sysBrand;

    /** 同款设定-机型 */
    @TableField("SYS_MODEL")
    @ApiModelProperty("机型")
    private String sysModel;

    /** 系统信息-关闭时间 */
    @TableField("SYS_CLOSE_DATE")
    @ApiModelProperty("关闭时间")
    private Date sysCloseDate;

    /** 同款设定-当前应用版本 */
    @TableField("SYS_CUR_APP_VERSION")
    @ApiModelProperty("当前应用版本")
    private String sysCurAppVersion;

    /** 同款设定-当前迭代版本 */
    @TableField("SYS_CUR_ITE_VERSION")
    @ApiModelProperty("当前迭代版本")
    private String sysCurIteVersion;

}