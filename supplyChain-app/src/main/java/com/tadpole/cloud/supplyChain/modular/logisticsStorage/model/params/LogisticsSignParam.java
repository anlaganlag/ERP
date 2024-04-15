package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 物流签收;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流签收",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsSignParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发货批次号码")
    private String lhrCode ;

    @ApiModelProperty(value = "物流单号")
    private String lhrOddNumb ;

    @ApiModelProperty(value = "签收日期")
    private Date lerSignDate;

}