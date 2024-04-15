package com.tadpole.cloud.operationManagement.api.brand.entity;

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
* 品牌logo表
* </p>
*
* @author S20190161
* @since 2023-10-30
*/
@Data
@TableName("TB_BRAND_LOGO")
@ExcelIgnoreUnannotated
public class TbBrandLogo implements Serializable {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /** 品牌主表主键 */
    @TableField("BC_ID")
    private Long bcId;

    /** logo版本 */
    @TableField("LOGO_VERSION")
    private String logoVersion;

    /** logo文件 */
    @TableField("LOGO_FILES")
    private String logoFiles;

    /** 构思说明 */
    @TableField("IDEA_THAT")
    private String ideaThat;

    /** 状态：1.启用;0.禁用;默认启用 */
    @TableField("IS_ENABLE")
    private Integer isEnable;

    /** 最后更新时间 */
    @TableField("SYS_LAST_UPD_DATE")
    private Date sysLastUpdDate;

    /** logo作者编号 */
    @TableField("SYS_PER_CODE")
    private String sysPerCode;

    /** logo作者名称 */
    @TableField("CREATE_NAME")
    private String createName;

}
