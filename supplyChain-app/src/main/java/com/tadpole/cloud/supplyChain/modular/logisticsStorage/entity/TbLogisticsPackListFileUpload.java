package com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity;

import java.io.Serializable;
import java.lang.*;
import lombok.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

 /**
 * 亚马逊货件后台生成的excel文件上传信息;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_pack_list_file_upload")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPackListFileUpload implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    @TableId (value = "pk_ship_id", type = IdType.AUTO)
    @TableField("pk_ship_id")
    private BigDecimal pkShipId ;
 
    /** Shipment ID */
    @ApiModelProperty(value = "Shipment ID")
    @TableField("bus_shipment_id")
    private String busShipmentId ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @TableField("bus_country_code")
    private String busCountryCode ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    @TableField("bus_shop_name_simple")
    private String busShopNameSimple ;
 
    /** Excel文件上传提交ID */
    @ApiModelProperty(value = "Excel文件上传提交ID")
    @TableField("bus_feed_excel_submission_id")
    private String busFeedExcelSubmissionId ;
 
    /** Excel处理状态 */
    @ApiModelProperty(value = "Excel处理状态")
    @TableField("bus_feed_excel_proces_status")
    private String busFeedExcelProcesStatus ;
 
    /** Excel处理开始时间 */
    @ApiModelProperty(value = "Excel处理开始时间")
    @TableField("bus_up_excel_file_time_b")
    private Date busUpExcelFileTimeB ;
 
    /** Excel处理结束时间 */
    @ApiModelProperty(value = "Excel处理结束时间")
    @TableField("bus_up_excel_file_time_e")
    private Date busUpExcelFileTimeE ;
 
    /** Excel处理结果 */
    @ApiModelProperty(value = "Excel处理结果")
    @TableField("bus_up_excel_result")
    private String busUpExcelResult ;
 
    /** Xml文件上传提交ID */
    @ApiModelProperty(value = "Xml文件上传提交ID")
    @TableField("bus_feed_xml_submission_id")
    private String busFeedXmlSubmissionId ;
 
    /** Xml处理状态 */
    @ApiModelProperty(value = "Xml处理状态")
    @TableField("bus_feed_xml_proces_status")
    private String busFeedXmlProcesStatus ;
 
    /** Xml处理开始时间 */
    @ApiModelProperty(value = "Xml处理开始时间")
    @TableField("bus_up_xml_file_time_b")
    private Date busUpXmlFileTimeB ;
 
    /** Xml处理结束时间 */
    @ApiModelProperty(value = "Xml处理结束时间")
    @TableField("bus_up_xml_file_time_e")
    private Date busUpXmlFileTimeE ;
 
    /** Xml处理结果 */
    @ApiModelProperty(value = "Xml处理结果")
    @TableField("bus_up_xml_result")
    private String busUpXmlResult ;
 
    /** Excel是否推送MQ */
    @ApiModelProperty(value = "Excel是否推送MQ")
    @TableField("bus_is_excel_push")
    private Integer busIsExcelPush ;
 
    /** Xml是否推送MQ */
    @ApiModelProperty(value = "Xml是否推送MQ")
    @TableField("bus_is_xml_push")
    private Integer busIsXmlPush ;


}