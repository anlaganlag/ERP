package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

 /**
 * 物流信息查询;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流信息查询",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsLinkParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    private BigDecimal id ;
 
    /** 添加时间 */
    @ApiModelProperty(value = "添加时间")
    private Date sysAddDate ;
 
    /** 操作人 */
    @ApiModelProperty(value = "操作人")
    private String sysPerName ;
 
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    private Date sysUpdDatetime ;
 
    /** 货运方式1 */
    @ApiModelProperty(value = "货运方式1--新平台 存的是 物流商名称")
    private String logTraMode1 ;
 
    /** 物流单链接模板 */
    @ApiModelProperty(value = "物流单链接模板")
    private String logListLinkTemp ;

}