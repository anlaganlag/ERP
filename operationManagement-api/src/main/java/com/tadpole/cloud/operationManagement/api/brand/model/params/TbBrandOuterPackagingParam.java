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
* 品牌外包装表
* </p>
* @author S20190161
* @since 2023-10-30
*/
@Data
public class TbBrandOuterPackagingParam implements Serializable {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("品牌主表主键")
    private Long bcId;

    @ApiModelProperty("包装版本")
    private String packagingVersion;

    @ApiModelProperty("包装文案")
    private String packagingCopy;

    @ApiModelProperty("包装文案作者编号")
    private String packagingCopyAuthorCode;

    @ApiModelProperty("")
    private String packagingCopyAuthorName;

    @ApiModelProperty("")
    private Date packagingCopyDate;

    @ApiModelProperty("")
    private String packagingPictures;

    @ApiModelProperty("")
    private String packagingPicturesAuthorCode;

    @ApiModelProperty("")
    private String packagingPicturesAuthorName;

    @ApiModelProperty("")
    private Date packagingPicturesDate;

    @ApiModelProperty("")
    private Integer isEnable;

}
