package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 通关产品基础信息 入参类
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
public class TgBaseProductParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 物料编码 */
    @ExcelProperty(value ="物料编码*")
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 物料编码集合 */
    @ApiModelProperty("物料编码集合")
    private List<String> materialCodeList;

    /** 主物料 */
    @ApiModelProperty("主物料")
    private String mainMaterialCode;

    /** 产品名称 */
    @ApiModelProperty("产品名称")
    private String productName;

    /** 产品名称集合 */
    @ApiModelProperty("产品名称集合")
    private List<String> productNameList;

    /** 开票品名（中文） */
    @ApiModelProperty("开票品名（中文）")
    private String invoiceProNameCn;

    /** 开票品名（中文）集合 */
    @ApiModelProperty("开票品名（中文）集合")
    private List<String> invoiceProNameCnList;

    /** 开票品名（英文） */
    @ApiModelProperty("开票品名（英文）")
    private String invoiceProNameEn;

    /** 开票品名（英文）集合 */
    @ApiModelProperty("开票品名（英文）集合")
    private List<String> invoiceProNameEnList;

    /** 报关材质（中文） */
    @ApiModelProperty("报关材质（中文）")
    private String clearMaterialCn;

    /** 报关材质（英文） */
    @ApiModelProperty("报关材质（英文）")
    private String clearMaterialEn;

    /** 海关编码 */
    @ExcelProperty(value ="海关编码*")
    @ApiModelProperty("海关编码")
    private String customsCode;

    /** 海关编码集合 */
    @ApiModelProperty("海关编码集合")
    private List<String> customsCodeList;

    /** 是否带电 0：否，1：是 */
    @ApiModelProperty("是否带电 0：否，1：是")
    private String isCharged;

    /** 是否带磁 0：否，1：是 */
    @ApiModelProperty("是否带磁 0：否，1：是")
    private String isMagnet;

    /** 要素 */
    @ExcelProperty(value ="要素*")
    @ApiModelProperty("要素")
    private String essentialFactor;

    @ApiModelProperty("不符合要素框架的部分")
    private String essentialFactorTemp;

    /** 审核状态 0：未审核，1：审核通过，2弃审 */
    @ApiModelProperty("审核状态 0：未审核，1：审核通过，2弃审")
    private String auditStatus;

    /** 审核人 */
    @ApiModelProperty("审核人")
    private String auditUser;

    /** 审核时间 */
    @ApiModelProperty("审核时间")
    private Date auditTime;

    /** 供应商 */
    @ApiModelProperty("供应商")
    private String adviceSupplier;

    /** 供应商集合 */
    @ApiModelProperty("供应商集合")
    private List<String> adviceSupplierList;

    /** 采购价 */
    @ApiModelProperty("采购价")
    private BigDecimal k3Price;

    /** 是否含税 0：否，1：是 */
    @ApiModelProperty("是否含税 0：否，1：是")
    private String includeTax;

    /** 能否开票 0：否，1：能 */
    @ApiModelProperty("能否开票 0：否，1：能")
    private String isMakeInvoice;

    @ExcelProperty(value ="退税率*")
    @ApiModelProperty("退税率")
    private BigDecimal taxRefund;

    @ExcelProperty(value ="毛利率*")
    @ApiModelProperty("毛利率")
    private BigDecimal grossProfitRate;

    /** 数据类型 0：未合并的数据，1：合并的数据 */
    @ApiModelProperty("数据类型 0：未合并的数据，1：合并的数据")
    private String dataType;

    /** 合并状态 0：未合并，1：已合并 */
    @ApiModelProperty("合并状态 0：未合并，1：已合并")
    private String mergeStatus;

    /** 合并ID：合并数据对应合并后的数据ID */
    @ApiModelProperty("合并ID：合并数据对应合并后的数据ID")
    private BigDecimal mergeId;

    /** 报关材质集合（中文） */
    @ApiModelProperty("报关材质集合（中文）")
    private List<String> clearMaterialCnList;

    /** 报关材质集合（英文） */
    @ApiModelProperty("报关材质集合（英文）")
    private List<String> clearMaterialEnList;

    /** 国家编码集合 */
    @ApiModelProperty("国家编码集合")
    private List<String> countryCodeList;

    /** 国家名称集合 */
    @ApiModelProperty("国家名称集合")
    private List<String> countryNamesList;

    @ApiModelProperty("国家编码")
    private String countryCode;

    @ApiModelProperty("国家名称")
    private String countryName;

    /** HSCode */
    @ApiModelProperty("HSCode")
    private String hsCode;

    /** HSCode集合 */
    @ApiModelProperty("HSCode集合")
    private List<String> hsCodeList;

    /** ID集合 */
    @ApiModelProperty("ID集合")
    private List<BigDecimal> idList;

    @ApiModelProperty("流转税率")
    private BigDecimal changeTaxRate;

    @ApiModelProperty("关税率")
    private BigDecimal taxRate;

    @ApiModelProperty("附加税率")
    private BigDecimal addTaxRate;

    /** 导入异常信息备注 */
    @ExcelProperty(value ="导入异常信息备注")
    @ApiModelProperty("导入异常信息备注")
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 11)
    private String uploadRemark;

    /** 未维护报关信息 1：是 */
    @ApiModelProperty("未维护报关信息 1：是")
    private String applyEmpty;

    /** 未维护清关信息 1：是 */
    @ApiModelProperty("未维护清关信息 1：是")
    private String clearanceEmpty;

    @ExcelProperty(value ="备注")
    @ApiModelProperty("备注")
    private String remark;
}
