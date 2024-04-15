package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;

 /**
 * 物流燃料费率;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流燃料费率",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsFuelRateParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** lfrID */
    @ApiModelProperty(value = "lfrID")
    private BigDecimal lfrId ;
 
    /** 添加时间 */
    @ApiModelProperty(value = "添加时间")
    private Date sysAddDate ;
 
    /** 物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    private String lpCode ;
 
    /** 燃油费率 */
    @ApiModelProperty(value = "燃油费率")
    private BigDecimal lfrRuelRate ;
 
    /** 生效日期 */
    @ApiModelProperty(value = "生效日期")
    private Date effectiveDate ;


}