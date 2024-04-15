package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: ty
 * @description: 清关产品合并
 * @date: 2023/5/29
 */
@Data
@ExcelIgnoreUnannotated
public class TgProductMergeResult implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private BigDecimal id;

    @ApiModelProperty("物料编码")
    private String materialCode;

    @ApiModelProperty("主物料")
    private String mainMaterialCode;

    @ApiModelProperty("转换前物料编码")
    private String preMaterialCode;

    @ExcelProperty(value ="物料编码", index = 2)
    @ApiModelProperty("物料编码字符")
    private String materialCodeStr;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("公司品牌")
    private String companyBrand;

    @ExcelProperty(value ="清关品名", index = 0)
    @ApiModelProperty("开票品名（中文）")
    private String invoiceProNameCn;

    @ExcelProperty(value ="开票英文品名", index = 1)
    @ApiModelProperty("开票品名（英文）")
    private String invoiceProNameEn;

    @ExcelProperty(value ="报关材质", index = 3)
    @ApiModelProperty("报关材质（中文）")
    private String clearMaterialCn;

    @ExcelProperty(value ="报关英文材质", index = 4)
    @ApiModelProperty("报关材质（英文）")
    private String clearMaterialEn;

    @ExcelProperty(value ="海关编码", index = 5)
    @ApiModelProperty("海关编码")
    private String customsCode;

    @ApiModelProperty("规格型号")
    private String fitBrand;

    @ApiModelProperty("开票规格型号")
    private String style;

    @ApiModelProperty("是否带电 0：否，1：是")
    private String isCharged;

    @ApiModelProperty("带电")
    private String isChargedDesc;

    @ApiModelProperty("是否带磁 0：否，1：是")
    private String isMagnet;

    @ApiModelProperty("带磁")
    private String isMagnetDesc;

    @ApiModelProperty("要素")
    private String essentialFactor;

    @ApiModelProperty("不符合要素框架的部分")
    private String essentialFactorTemp;

    @ApiModelProperty("审核状态 0：未审核，1：审核通过，2弃审")
    private String auditStatus;

    @ApiModelProperty("审核状态")
    private String auditStatusDesc;

    @ApiModelProperty("审核人")
    private String auditUser;

    @ApiModelProperty("审核时间")
    private Date auditTime;

    @ApiModelProperty("供应商")
    private String adviceSupplier;

    @ApiModelProperty("采购价")
    private BigDecimal k3Price;

    @ApiModelProperty("是否含税 0：否，1：是")
    private String includeTax;

    @ApiModelProperty("是否含税")
    private String includeTaxDesc;

    @ApiModelProperty("能否开票 0：否，1：能")
    private String isMakeInvoice;

    @ApiModelProperty("能否开票")
    private String isMakeInvoiceDesc;

    @ApiModelProperty("退税率")
    private BigDecimal taxRefund;

    @ApiModelProperty("毛利率")
    private BigDecimal grossProfitRate;

    @ApiModelProperty("重量")
    private BigDecimal grossWeightOrg;

    @ApiModelProperty("重量单位")
    private String weightUnitOrg;

    @ApiModelProperty("数据类型 0：未合并的数据，1：合并的数据")
    private String dataType;

    @ApiModelProperty("合并状态 0：未合并，1：已合并")
    private String mergeStatus;

    @ExcelProperty(value ="合并状态", index = 14)
    @ApiModelProperty("合并状态：未合并，已合并")
    private String mergeStatusDesc;

    @ApiModelProperty("合并ID：合并数据对应合并后的数据ID")
    private BigDecimal mergeId;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

    @ApiModelProperty("明细ID")
    private BigDecimal detailId;

    @ApiModelProperty("国家编码")
    private String countryCode;

    @ExcelProperty(value ="国家", index = 6)
    @ApiModelProperty("国家名称")
    private String countryName;

    @ExcelProperty(value ="HSCode", index = 7)
    @ApiModelProperty("HSCode")
    private String hsCode;

    @ExcelProperty(value ="流转税率", index = 8)
    @ApiModelProperty("流转税率")
    private BigDecimal changeTaxRate;

    @ExcelProperty(value ="关税率", index = 9)
    @ApiModelProperty("关税率")
    private BigDecimal taxRate;

    @ExcelProperty(value ="附加税率", index = 10)
    @ApiModelProperty("附加税率")
    private BigDecimal addTaxRate;

    @ExcelProperty(value ="Amazon销售价", index = 11)
    @ApiModelProperty("Amazon销售价")
    private BigDecimal amazonSalePrice;

    @ExcelProperty(value ="Amazon销售连接", index = 12)
    @ApiModelProperty("Amazon销售连接")
    private String amazonSaleLink;

    @ExcelProperty(value ="Amazon主图连接", index = 13)
    @ApiModelProperty("Amazon主图连接")
    private String amazonPictureLink;

    /** 是否选中 */
    @ApiModelProperty("是否选中 0：未选中，1：选中")
    private String isSelect;

    /** 站点 */
    @ApiModelProperty("站点")
    private String site;

    /** skus */
    @ApiModelProperty("skus")
    private String skus;

}
