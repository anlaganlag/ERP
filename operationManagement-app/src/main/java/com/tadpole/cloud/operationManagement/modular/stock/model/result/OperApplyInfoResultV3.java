package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 备货申请信息
 * </p>
 *
 * @author lsy
 * @since 2022-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("STOCK_OPER_APPLY_INFO")
public class OperApplyInfoResultV3 implements Serializable, BaseValidatingParam {

 private static final long serialVersionUID = 1L;




 /** 备货类型--备货类型:RCBH日常备货,JJBH紧急备货,XMBH项目备货,XPBH新品备货 */
 @ApiModelProperty("BILL_TYPE")
 private String billType;




 /** 推荐日期-按天，最新数据为当前日期 */
 @ApiModelProperty("BIZDATE")
 @ExcelProperty(value = "bizdate")
 @DateTimeFormat
 private Date bizdate;

 /** 区域-备货区域，备货区域和物理仓关联，目前合并的备货区域只有EU和UX，EU的不同站点间可以互相发货；
  墨西哥MX没有可用的物理仓，从US发货，所以就将US和MX合并为北美UX */
 @ApiModelProperty("AREA")
 @ExcelProperty(value = "area")
 private String area;

 /** 平台 */
 @ApiModelProperty("PLATFORM")
 @ExcelProperty(value = "platform")
 private String platform;



 /** 事业部 */
 @ApiModelProperty("DEPARTMENT")
 @ExcelProperty(value = "department")
 private String department;

 /** TEAM */
 @ApiModelProperty("TEAM")
 @ExcelProperty(value = "team")
 private String team;

 /** 物料编码 */
 @ApiModelProperty("MATERIAL_CODE")
 @ExcelProperty(value = "materialCode")
 private String materialCode;

 /** 物料创建日期 */
 @ApiModelProperty("CREATE_DATE")
 @ExcelProperty(value = "createDate")
 @DateTimeFormat
 private Date createDate;

 /** 推荐运输方式 */
 @ApiModelProperty("RECOM_DELIVERY_TYPE")
 @ExcelProperty(value = "recomDeliveryType")
 private String recomDeliveryType;

 /** 运营大类 */
 @ApiModelProperty("PRODUCT_TYPE")
 @ExcelProperty(value = "productType")
 private String productType;

 /** 产品名称 */
 @ApiModelProperty("PRODUCT_NAME")
 @ExcelProperty(value = "productName")
 private String productName;

 /** 款式 */
 @ApiModelProperty("STYLE")
 @ExcelProperty(value = "style")
 private String style;

 /** 主材料 */
 @ApiModelProperty("MAIN_MATERIAL")
 @ExcelProperty(value = "mainMaterial")
 private String mainMaterial;

 /** 图案 */
 @ApiModelProperty("PATTERN")
 @ExcelProperty(value = "pattern")
 private String pattern;

 /** 公司品牌 */
 @ApiModelProperty("COMPANY_BRAND")
 @ExcelProperty(value = "companyBrand")
 private String companyBrand;

 /** 适用品牌或对象 */
 @ApiModelProperty("BRAND")
 @ExcelProperty(value = "brand")
 private String brand;

 /** 型号 */
 @ApiModelProperty("MODEL")
 @ExcelProperty(value = "model")
 private String model;

 /** 颜色 */
 @ApiModelProperty("COLOR")
 @ExcelProperty(value = "color")
 private String color;

 /** 尺码 */
 @ApiModelProperty("SIZES")
 @ExcelProperty(value = "sizes")
 private String sizes;

 /** 包装数量 */
 @ApiModelProperty("PACKING")
 @ExcelProperty(value = "packing")
 private String packing;

 /** 版本描述 */
 @ApiModelProperty("VERSION_DES")
 @ExcelProperty(value = "versionDes")
 private String versionDes;

 /** 二级类目 */
 @ApiModelProperty("MATSTYLESECONDLABEL")
 @ExcelProperty(value = "matstylesecondlabel")
 private String matstylesecondlabel;

 /** 生产周期 */
 @ApiModelProperty("MATPROCYCLE")
 @ExcelProperty(value = "matprocycle")
 private Integer matprocycle;

 /** 适用机型 */
 @ApiModelProperty("TYPE")
 @ExcelProperty(value = "type")
 private String type;

 /** 采购起订量 */
 @ApiModelProperty("MINPOQTY")
 @ExcelProperty(value = "minpoqty")
 private Long minpoqty;

 /** SPU */
 @ApiModelProperty("SPU")
 @ExcelProperty(value = "spu")
 private String spu;

 /** NBDU */
 @ApiModelProperty("NBDU")
 @ExcelProperty(value = "nbdu")
 private String nbdu;

 /** 节日标签 */
 @ApiModelProperty("FESTIVAL_LABEL")
 @ExcelProperty(value = "festivalLabel")
 private String festivalLabel;


 /** 产品销售等级标签 */
 @ApiModelProperty("PRODUCT_SALE_LEVEL")
 @ExcelProperty(value = "productSaleLevel")
 private String productSaleLevel;

 /**
  * 产品销售等级标签-team维度
  */
 @ApiModelProperty("productSaleLevelTeam")
 @ExcelProperty("productSaleLevelTeam")
 private String productSaleLevelTeam;


 /** 季节标签 */
 @ApiModelProperty("SEASON_LABEL")
 @ExcelProperty(value = "seasonLabel")
 private String seasonLabel;

 /** VERSION */
 @ApiModelProperty("VERSION")
 @ExcelProperty(value = "version")
 private String version;

 /** FLAG */
 @ApiModelProperty("FLAG")
 @ExcelProperty(value = "flag")
 private BigDecimal flag;

 /** 可售库存供货天数 */
 @ApiModelProperty("SELLABLE_SUPPLYDAYS")
 @ExcelProperty(value = "sellableSupplydays")
 private BigDecimal sellableSupplydays;

 /** 类目 */
 @ApiModelProperty("CATEGORY")
 @ExcelProperty(value = "category")
 private String category;

 /** 采购起订量备注 */
 @ApiModelProperty("MINPOQTY_NOTES")
 @ExcelProperty(value = "minpoqtyNotes")
 private String minpoqtyNotes;

 /** 拼单起订量 */
 @ApiModelProperty("SPELL_ORDERNUM")
 @ExcelProperty("拼单起订量")
 private Long spellOrdernum;


 /** 拼单起订量备注 */
 @ApiModelProperty("SPELL_ORDERNUM_REMARK ")
 @ExcelProperty("拼单起订量备注")
 private String spellOrdernumRemark;



 /** FBA最早接收日期 */
 @ApiModelProperty("FBA_FIRST_RECEIVEDATE")
 @ExcelProperty(value = "fbaFirstReceivedate")
 @DateTimeFormat
 private Date fbaFirstReceivedate;

 /** 备注 */
 @ApiModelProperty("NOTE")
 @ExcelProperty(value = "note")
 private String note;


 /** 汇总数据id拼接*/
 @ApiModelProperty("汇总数据id拼接")
 private String ids;

 /** 合并asin数量 */
 @ApiModelProperty("合并asin数量")
 private Integer asinCount;

 /** 建议备货数量 */
 @ApiModelProperty("建议备货数量合计算")
 private BigDecimal recomPreQtySum;

 @ApiModelProperty("销售需求数量合计算")
 private BigDecimal salesDemandSum;

 @ApiModelProperty("申请区域备货数量合计算")
 private BigDecimal stockQtyAreaSum;

 @ApiModelProperty("预估销量合计算")
 private BigDecimal preSaleQtySum;




}