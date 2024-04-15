package com.tadpole.cloud.operationManagement.api.brand.model.params;

import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.*;

/**
* <p>
* 品牌售后服务卡表
* </p>
* @author S20190161
* @since 2023-10-30
*/
@Data
public class TbBrandAfterSalesServiceCardParam implements Serializable {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("品牌主表主键")
    private Long bcId;

    @ApiModelProperty("服务卡版本")
    private String serviceCardVersion;

    @ApiModelProperty("服务卡文案")
    private String serviceCardCopy;

    @ApiModelProperty("服务卡文案作者编号")
    private String serviceCardCopyAuthorCode;

    @ApiModelProperty("服务卡文案作者名称")
    private String serviceCardCopyAuthorName;

    @ApiModelProperty("服务卡文案更新时间")
    private Date serviceCardCopyDate;

    @ApiModelProperty("服务卡图片")
    private String serviceCardPictures;

    @ApiModelProperty("服务卡图片作者编号")
    private String serviceCardPicturesAuthorCode;

    @ApiModelProperty("服务卡图片作者名称")
    private String serviceCardPicturesAuthorName;

    @ApiModelProperty("服务卡图片更新时间")
    private Date serviceCardPicturesDate;

    @ApiModelProperty("状态：1.启用;0.禁用;默认启用")
    private Integer isEnable;

}
