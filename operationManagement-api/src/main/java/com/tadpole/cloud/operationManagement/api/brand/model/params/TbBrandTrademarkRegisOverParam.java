package com.tadpole.cloud.operationManagement.api.brand.model.params;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 品牌商标注册表 扩类
* </p>
* @author S20190161
* @since 2023-10-24
*/
@Data
public class TbBrandTrademarkRegisOverParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("品牌商标表主键")
    @NotNull(message = "品牌商标表主键不能为空")
    private Long btId;

    @ApiModelProperty("注册国家--取值于字典名称-【国家】")
    @NotNull(message = "注册国家不能为空")
    private String registerCountry;

    @ApiModelProperty("商标大类--取值于字典名称-【商标大类】")
    @NotNull(message = "商标大类不能为空")
    private String trademarkCategory;


    @ApiModelProperty("备注说明--扩类")
    private String remark;


}
