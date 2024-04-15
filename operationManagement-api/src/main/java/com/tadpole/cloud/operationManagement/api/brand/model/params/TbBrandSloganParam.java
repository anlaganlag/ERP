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
* 品牌sloga表
* </p>
* @author S20190161
* @since 2023-10-30
*/
@Data
public class TbBrandSloganParam implements Serializable {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("品牌主表主键")
    private Long bcId;

    @ApiModelProperty("slogan版本")
    private String sloganVersion;

    @ApiModelProperty("slogan(英文)")
    private String sloganEnglish;

    @ApiModelProperty("slogan(中文)")
    private String sloganChinese;

    @ApiModelProperty("构思说明")
    private String ideaThat;

    @ApiModelProperty("状态：1.启用;0.禁用;默认启用")
    private Integer isEnable;

    @ApiModelProperty("slogan作者编号")
    private String sysPerCode;

    @ApiModelProperty("")
    private Date createTime;

    @ApiModelProperty("")
    private String createName;

    @ApiModelProperty("")
    private Date updateTime;

    @ApiModelProperty("")
    private String updateName;

}
