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
* 品牌外包装表
* </p>
*
* @author S20190161
* @since 2023-10-30
*/
@Data
@TableName("TB_BRAND_OUTER_PACKAGING")
@ExcelIgnoreUnannotated
public class TbBrandOuterPackaging implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统主键 */
   @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /** 品牌主表主键 */
    @TableField("BC_ID")
    private Long bcId;

    /** 包装版本 */
    @TableField("PACKAGING_VERSION")
    private String packagingVersion;

    /** 包装文案 */
    @TableField("PACKAGING_COPY")
    private String packagingCopy;

    /** 包装文案作者编号 */
    @TableField("PACKAGING_COPY_AUTHOR_CODE")
    private String packagingCopyAuthorCode;

    @TableField("PACKAGING_COPY_AUTHOR_NAME")
    private String packagingCopyAuthorName;

    @TableField("PACKAGING_COPY_DATE")
    private Date packagingCopyDate;

    @TableField("PACKAGING_PICTURES")
    private String packagingPictures;

    @TableField("PACKAGING_PICTURES_AUTHOR_CODE")
    private String packagingPicturesAuthorCode;

    @TableField("PACKAGING_PICTURES_AUTHOR_NAME")
    private String packagingPicturesAuthorName;

    @TableField("PACKAGING_PICTURES_DATE")
    private Date packagingPicturesDate;

    @TableField("IS_ENABLE")
    private Integer isEnable;

}
