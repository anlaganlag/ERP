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
* 品牌售后服务卡表
* </p>
*
* @author S20190161
* @since 2023-10-30
*/
@Data
@TableName("TB_BRAND_AFTER_SALES_SERVICE_CARD")
@ExcelIgnoreUnannotated
public class TbBrandAfterSalesServiceCard implements Serializable {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /** 品牌主表主键 */
    @TableField("BC_ID")
    private Long bcId;

    /** 服务卡版本 */
    @TableField("SERVICE_CARD_VERSION")
    private String serviceCardVersion;

    /** 服务卡文案 */
    @TableField("SERVICE_CARD_COPY")
    private String serviceCardCopy;

    /** 服务卡文案作者编号 */
    @TableField("SERVICE_CARD_COPY_AUTHOR_CODE")
    private String serviceCardCopyAuthorCode;

    /** 服务卡文案作者名称 */
    @TableField("SERVICE_CARD_COPY_AUTHOR_NAME")
    private String serviceCardCopyAuthorName;

    /** 服务卡文案更新时间 */
    @TableField("SERVICE_CARD_COPY_DATE")
    private Date serviceCardCopyDate;

    /** 服务卡图片 */
    @TableField("SERVICE_CARD_PICTURES")
    private String serviceCardPictures;

    /** 服务卡图片作者编号 */
    @TableField("SERVICE_CARD_PICTURES_AUTHOR_CODE")
    private String serviceCardPicturesAuthorCode;

    /** 服务卡图片作者名称 */
    @TableField("SERVICE_CARD_PICTURES_AUTHOR_NAME")
    private String serviceCardPicturesAuthorName;

    /** 服务卡图片更新时间 */
    @TableField("SERVICE_CARD_PICTURES_DATE")
    private Date serviceCardPicturesDate;

    /** 状态：1.启用;0.禁用;默认启用 */
    @TableField("IS_ENABLE")
    private Integer isEnable;

}
