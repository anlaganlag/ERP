package com.tadpole.cloud.operationManagement.modular.stock.vo.req;

import com.tadpole.cloud.operationManagement.modular.stock.model.params.SaftyDaySummaryParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SaftyDayValueParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 安全天数设置vo
 */

@Api("安全天数设置vo")
@Data
public class SaftyDayVO {

    @ApiModelProperty("安全天数概要数据")
    @NotNull
    private SaftyDaySummaryParam saftyDaySummaryParam;

    @ApiModelProperty("安全天数具体值集合")
    private List<SaftyDayValueParam> saftyDayValueList=new ArrayList<>();
}
