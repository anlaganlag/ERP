package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 亚马逊货件;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "亚马逊货件",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsPackListParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    private BigDecimal id ;
 
    /** 票单号 */
    @ApiModelProperty(value = "票单号")
    private String packCode ;
 
    /** PlanName */
    @ApiModelProperty(value = "PlanName")
    private String planName ;
 
    /** 模板上传日期 */
    @ApiModelProperty(value = "模板上传日期")
    private Date packTempUpDate ;
 
    /** 模板上传员工姓名 */
    @ApiModelProperty(value = "模板上传员工姓名")
    private String packTempUpPerName ;
 
    /** 模板文件名称 */
    @ApiModelProperty(value = "模板文件名称")
    private String packTempName ;
 
    /** Shipment ID */
    @ApiModelProperty(value = "Shipment ID")
    private String shipmentId ;
 
    /** Plan ID */
    @ApiModelProperty(value = "Plan ID")
    private String planId ;
 
    /** Ship To */
    @ApiModelProperty(value = "Ship To")
    private String shipTo ;
 
    /** ToTal SKUs */
    @ApiModelProperty(value = "ToTal SKUs")
    private Integer toTalSkus ;
 
    /** Total Units */
    @ApiModelProperty(value = "Total Units")
    private Integer totalUnits ;
 
    /** 文件生成日期 */
    @ApiModelProperty(value = "文件生成日期")
    private Date packGenDate ;
 
    /** 生成操作员工姓名 */
    @ApiModelProperty(value = "生成操作员工姓名")
    private String packGenPerName ;
 
    /** 是否上传 */
    @ApiModelProperty(value = "是否上传")
    private Integer isUpload ;
 
    /** Amazon接收状态 */
    @ApiModelProperty(value = "Amazon接收状态")
    private String packAmaRecState ;
 
    /** packAmaRecStatePerName */
    @ApiModelProperty(value = "packAmaRecStatePerName")
    private String packAmaRecStatePerName ;
 
    /** packAmaRecStateDatetime */
    @ApiModelProperty(value = "packAmaRecStateDatetime")
    private Date packAmaRecStateDatetime ;
 
    /** 国家编码 */
    @ApiModelProperty(value = "国家编码")
    private String countryCode ;
 
    /** 店铺简称 */
    @ApiModelProperty(value = "店铺简称")
    private String shopNameSimple ;
 
    /** 出货仓类型 */
    @ApiModelProperty(value = "出货仓类型")
    private String comWarehouseType ;
 
    /** 出货仓名称 */
    @ApiModelProperty(value = "出货仓名称")
    private String comWarehouseName ;
 
    /** MaxBoXNum */
    @ApiModelProperty(value = "MaxBoXNum")
    private Integer maxBoXnum ;
 
    /** 货件实际状态 */
    @ApiModelProperty(value = "货件实际状态")
    private String packShipmentRealStatus ;
 
    /** 备注(人工导入|系统生成) */
    @ApiModelProperty(value = "备注(人工导入|系统生成)")
    private String busPackRemark ;
 
    /** Shipment Name */
    @ApiModelProperty(value = "Shipment Name")
    private String busShipmentName ;
 
    /** API处理状态(1-上传excel,2-上传xml,3-完成创建,4-下载箱标,5-完成) */
    @ApiModelProperty(value = "API处理状态(1-上传excel,2-上传xml,3-完成创建,4-下载箱标,5-完成)")
    private Integer busApiProcessStatus ;
 
    /** 最后更新日期 */
    @ApiModelProperty(value = "最后更新日期")
    private Date sysUpdateDate ;
 
    /** 系统标识字段新版导入文件 */
    @ApiModelProperty(value = "系统标识字段新版导入文件")
    private String packListCode ;
 
    /** 亚马逊货件接收总数量 */
    @ApiModelProperty(value = "亚马逊货件接收总数量")
    private Integer busReceivedQty ;

    //查询条件参数

    @ApiModelProperty(value = "查询条件参数--物流状态")
    private String busLhrState ;

    @ApiModelProperty(value = "查询条件参数--发货方式")
    private String logTraMode2 ;

    @ApiModelProperty(value = "查询条件参数--签收日期开始")
    private Date lerSignDateStart ;

    @ApiModelProperty(value = "查询条件参数--签收日期结束")
    private Date lerSignDateEnd ;

    @ApiModelProperty(value = "查询条件参数--物流商名称集合")
    private List<String> lpNameList ;

   @ApiModelProperty(value = "查询条件参数--物流商名称")
   private String lpName ;

   @ApiModelProperty(value = "查询条件参数--物流单号")
   private String busLhrOddNumb ;

   @ApiModelProperty(value = "查询条件参数--物料编码")
   private String materialCode ;

   @ApiModelProperty(value = "查询条件参数--sku")
   private String sku ;

   @ApiModelProperty(value = "查询条件参数--asin")
   private String asin ;







}