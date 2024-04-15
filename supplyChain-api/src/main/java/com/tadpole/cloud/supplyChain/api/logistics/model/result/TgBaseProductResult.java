package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 通关产品基础信息 出参类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("TG_BASE_PRODUCT")
public class TgBaseProductResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty("物料编码")
    private String materialCode;

    @ApiModelProperty("主物料")
    private String mainMaterialCode;

    @ApiModelProperty("转换前物料编码")
    private String preMaterialCode;

    @ApiModelProperty("物料类型：普通物料，转换物料，组合物料")
    private String materialType;

    @ExcelProperty(value ="物料编码")
    @ApiModelProperty("物料编码字符")
    private String materialCodeStr;

    @ExcelProperty(value ="产品名称")
    @ApiModelProperty("产品名称")
    private String productName;

    @ExcelProperty(value ="公司品牌")
    @ApiModelProperty("公司品牌")
    private String companyBrand;

    @ExcelProperty(value ="开票品名")
    @ApiModelProperty("开票品名（中文）")
    private String invoiceProNameCn;

    @ExcelProperty(value ="开票英文品名")
    @ApiModelProperty("开票品名（英文）")
    private String invoiceProNameEn;

    @ExcelProperty(value ="规格型号")
    @ApiModelProperty("规格型号")
    private String fitBrand;

    @ExcelProperty(value ="开票规格型号")
    @ApiModelProperty("开票规格型号")
    private String style;

    @ApiModelProperty("是否带电 0：否，1：是")
    private String isCharged;

    @ExcelProperty(value ="带电")
    @ApiModelProperty("带电")
    private String isChargedDesc;

    @ApiModelProperty("是否带磁 0：否，1：是")
    private String isMagnet;

    @ExcelProperty(value ="带磁")
    @ApiModelProperty("带磁")
    private String isMagnetDesc;

    @ExcelProperty(value ="报关材质")
    @ApiModelProperty("报关材质（中文）")
    private String clearMaterialCn;

    @ExcelProperty(value ="报关英文材质")
    @ApiModelProperty("报关材质（英文）")
    private String clearMaterialEn;

    @ExcelProperty(value ="海关编码")
    @ApiModelProperty("海关编码")
    private String customsCode;

    @ExcelProperty(value ="要素")
    @ApiModelProperty("要素")
    private String essentialFactor;

    @ApiModelProperty("不符合要素框架的部分")
    private String essentialFactorTemp;

    @ExcelProperty(value ="退税率")
    @ApiModelProperty("退税率")
    private BigDecimal taxRefund;

    @ExcelProperty(value ="毛利率")
    @ApiModelProperty("毛利率")
    private BigDecimal grossProfitRate;

    @ApiModelProperty("审核状态 0：未审核，1：审核通过，2弃审")
    private String auditStatus;

    @ExcelProperty(value ="审核状态")
    @ApiModelProperty("审核状态")
    private String auditStatusDesc;

    @ApiModelProperty("审核人")
    private String auditUser;

    @ApiModelProperty("审核时间")
    private Date auditTime;

    @ApiModelProperty("供应商")
    private String adviceSupplier;

    @ExcelProperty(value ="供应商")
    @ApiModelProperty("供应商")
    private String supplierName;

    @ExcelProperty(value ="采购价")
    @ApiModelProperty("采购价")
    private BigDecimal k3Price;

    @ApiModelProperty("是否含税 0：否，1：是")
    private String includeTax;

    @ExcelProperty(value ="是否含税")
    @ApiModelProperty("是否含税")
    private String includeTaxDesc;

    @ApiModelProperty("能否开票 0：否，1：能")
    private String isMakeInvoice;

    @ExcelProperty(value ="能否开票")
    @ApiModelProperty("能否开票")
    private String isMakeInvoiceDesc;

    @ExcelProperty(value ="重量")
    @ApiModelProperty("重量")
    private BigDecimal grossWeightOrg;

    @ExcelProperty(value ="重量单位")
    @ApiModelProperty("重量单位")
    private String weightUnitOrg;

    @ApiModelProperty("数据类型 0：未合并的数据，1：合并的数据")
    private String dataType;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

    @ApiModelProperty("国家编码")
    private String countryCode;

    @ExcelProperty(value ="国家")
    @ApiModelProperty("国家名称")
    private String countryName;

    @ExcelProperty(value ="HSCode")
    @ApiModelProperty("HSCode")
    private String hsCode;

    @ExcelProperty(value ="流转税率")
    @ApiModelProperty("流转税率")
    private BigDecimal changeTaxRate;

    @ExcelProperty(value ="关税率")
    @ApiModelProperty("关税率")
    private BigDecimal taxRate;

    @ExcelProperty(value ="附加税率")
    @ApiModelProperty("附加税率")
    private BigDecimal addTaxRate;

    @ExcelProperty(value ="备注")
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("ERP采购订单单据编号")
    private String fBillNo;

    @ApiModelProperty("ERP物料ID")
    private String fMaterialId;

}
