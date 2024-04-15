package com.tadpole.cloud.operationManagement.api.brand.model.params;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
* <p>
* 品牌商标表
* </p>
* @author S20190161
* @since 2023-10-19
*/
@Data
public class TbBrandTrademarkParam  implements Serializable {

    private static final long serialVersionUID = 1L;



    private Long id;

    @ApiModelProperty("商标名称")
    @NotBlank(message = "商标名称不能为空")
    private String tradeName;


    @ApiModelProperty("商标类型：0.文字商标;1.图形商标;")
    private Long type;

    @ApiModelProperty("是否注册")
    private Integer isRegister;

    @ApiModelProperty("备注说明")
    private String remark;

    @ApiModelProperty("提案人编号")
    private String sysPerCode;

    @ApiModelProperty("提案人姓名")
    private String createName;


    @ApiModelProperty("提案时间")
    private Date createTime;
    @ApiModelProperty("修改人姓名")
    private String updateName;
    @ApiModelProperty("修改时间")
    private Date updateTime;

}
