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
* 品牌sloga表
* </p>
*
* @author S20190161
* @since 2023-10-30
*/
@Data
@TableName("TB_BRAND_SLOGAN")
@ExcelIgnoreUnannotated
public class TbBrandSlogan implements Serializable {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /** 品牌主表主键 */
    @TableField("BC_ID")
    private Long bcId;

    /** slogan版本 */
    @TableField("SLOGAN_VERSION")
    private String sloganVersion;

    /** slogan(英文) */
    @TableField("SLOGAN_ENGLISH")
    private String sloganEnglish;

    /** slogan(中文) */
    @TableField("SLOGAN_CHINESE")
    private String sloganChinese;

    /** 构思说明 */
    @TableField("IDEA_THAT")
    private String ideaThat;

    /** 状态：1.启用;0.禁用;默认启用 */
    @TableField("IS_ENABLE")
    private Integer isEnable;

    /** slogan作者编号 */
    @TableField("SYS_PER_CODE")
    private String sysPerCode;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("CREATE_NAME")
    private String createName;

    @TableField("UPDATE_TIME")
    private Date updateTime;

    @TableField("UPDATE_NAME")
    private String updateName;

}
