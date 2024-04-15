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
 * 亚马逊货件后台生成的excel文件上传信息;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "亚马逊货件后台生成的excel文件上传信息",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsPackListFileUploadParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    private BigDecimal pkShipId ;
 
    /** Shipment ID */
    @ApiModelProperty(value = "Shipment ID")
    private String busShipmentId ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    private String busCountryCode ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    private String busShopNameSimple ;
 
    /** Excel文件上传提交ID */
    @ApiModelProperty(value = "Excel文件上传提交ID")
    private String busFeedExcelSubmissionId ;
 
    /** Excel处理状态 */
    @ApiModelProperty(value = "Excel处理状态")
    private String busFeedExcelProcesStatus ;
 
    /** Excel处理开始时间 */
    @ApiModelProperty(value = "Excel处理开始时间")
    private Date busUpExcelFileTimeB ;
 
    /** Excel处理结束时间 */
    @ApiModelProperty(value = "Excel处理结束时间")
    private Date busUpExcelFileTimeE ;
 
    /** Excel处理结果 */
    @ApiModelProperty(value = "Excel处理结果")
    private String busUpExcelResult ;
 
    /** Xml文件上传提交ID */
    @ApiModelProperty(value = "Xml文件上传提交ID")
    private String busFeedXmlSubmissionId ;
 
    /** Xml处理状态 */
    @ApiModelProperty(value = "Xml处理状态")
    private String busFeedXmlProcesStatus ;
 
    /** Xml处理开始时间 */
    @ApiModelProperty(value = "Xml处理开始时间")
    private Date busUpXmlFileTimeB ;
 
    /** Xml处理结束时间 */
    @ApiModelProperty(value = "Xml处理结束时间")
    private Date busUpXmlFileTimeE ;
 
    /** Xml处理结果 */
    @ApiModelProperty(value = "Xml处理结果")
    private String busUpXmlResult ;
 
    /** Excel是否推送MQ */
    @ApiModelProperty(value = "Excel是否推送MQ")
    private Integer busIsExcelPush ;
 
    /** Xml是否推送MQ */
    @ApiModelProperty(value = "Xml是否推送MQ")
    private Integer busIsXmlPush ;


}