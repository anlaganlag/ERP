package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
* 新核算库存平均单价表
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RP_NEW_AVERAGE_PRICE")
@ColumnWidth(15)
@ExcelIgnoreUnannotated
@HeadFontStyle(fontName = "宋体", fontHeightInPoints = 14, bold = true, color = -1)
public class NewAveragePrice implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 会计期间 */
    @TableField("FISCAL_PERIOD")
    @ExcelProperty(value= "会计期间")
    private String fiscalPeriod;

    /** 账号 */
    @TableField("SHOP_NAME")
    @ExcelProperty(value = "账号")
    private String shopName;

    /** 站点 */
    @TableField("SITE")
    @ExcelProperty(value= "站点")
    private String site;

    @TableField("MATERIAL_CODE")
    @ExcelProperty(value= "物料编码")
    private String materialCode;

    @TableField("LOGISTICS_UNIT_PRICE")
    @ExcelProperty(value= "物流单价（CNY）")
    private BigDecimal logisticsUnitPrice;

    @TableField("PURCHASE_UNIT_PRICE")
    @ExcelProperty(value= "采购单价（CNY）")
    private BigDecimal purchaseUnitPrice;

    @TableField("ADDITIONAL_UNIT_PRICE")
    @ExcelProperty(value= "附加单价（CNY）")
    private BigDecimal additionalUnitPrice;

    @TableField("CONFIRM_DATE")
    private Date confirmDate;

    @TableField("CONFIRM_BY")
    private String confirmBy;

    @TableField("CONFIRM_STATUS")
    private BigDecimal confirmStatus;

    /** 创建人 */
    @TableField("CREATE_BY")
    private String createBy;

    /** 创建时间 */
    @TableField("CREATE_AT")
    private Date createAt;

    @TableId(value="ID",type= IdType.AUTO)
    private BigDecimal id;

    @TableField(exist = false)
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
    @ExcelProperty(value= "导入错误备注")
    private String uploadRemark;
}