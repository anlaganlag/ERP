package com.tadpole.cloud.operationManagement.api.brand.model.params;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* <p>
* 品牌商标表
* </p>
* @author S20190161
* @since 2023-10-19
*/
@Data
public class TbBrandTrademarkPageParam extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("查询商标名称")
    private List<String> tradeNames;

    @ApiModelProperty("商标类型：0.文字商标;1.图形商标;")
    private Long type;

    @ApiModelProperty("是否注册")
    private Integer isRegister;

    @ApiModelProperty("备注说明")
    private String remark;


    @ApiModelProperty("提案人姓名")
    private String createName;

    @ApiModelProperty("商标状态")
    private Integer trademarkStatus;

}
