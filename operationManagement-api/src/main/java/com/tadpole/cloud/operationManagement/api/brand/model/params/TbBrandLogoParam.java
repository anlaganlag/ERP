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
* 品牌logo表
* </p>
* @author S20190161
* @since 2023-10-30
*/
@Data
public class TbBrandLogoParam implements Serializable {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("品牌主表主键")
    private Long bcId;

    @ApiModelProperty("logo版本")
    private String logoVersion;

    @ApiModelProperty("logo文件")
    private String logoFiles;

    @ApiModelProperty("构思说明")
    private String ideaThat;

    @ApiModelProperty("状态：1.启用;0.禁用;默认启用")
    private Integer isEnable;

    @ApiModelProperty("最后更新时间")
    private Date sysLastUpdDate;

    @ApiModelProperty("logo作者编号")
    private String sysPerCode;

    @ApiModelProperty("logo作者名称")
    private String createName;

}
