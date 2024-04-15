package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

 /**
 * 物流供应商;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流供应商",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsProviderParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** 物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    private String lpCode ;
 
    /** 物流商名称 */
    @ApiModelProperty(value = "物流商名称")
    private String lpName ;
 
    /** 物流商简称 */
    @ApiModelProperty(value = "物流商简称")
    private String lpSimpleName ;
 
    /** 通讯地址 */
    @ApiModelProperty(value = "通讯地址")
    private String lpAddress ;
 
    /** 统一社会信用代码 */
    @ApiModelProperty(value = "统一社会信用代码")
    private String lpUniSocCreCode ;
 
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    private Date sysUpdDatetime ;
 
    /** 启用禁用状态;A:启用，B:禁用 */
    @ApiModelProperty(value = "启用禁用状态")
    private String forbidStatus ;

    /** 物流单链接模板 */
    @ApiModelProperty(value = "物流单链接模板")
    private String logListLinkTemp ;


}