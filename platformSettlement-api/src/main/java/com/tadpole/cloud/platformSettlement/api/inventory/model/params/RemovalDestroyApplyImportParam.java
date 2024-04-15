package com.tadpole.cloud.platformSettlement.api.inventory.model.params;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 库存移除至服务商申请导入
 * @date: 2022/12/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@HeadStyle(fillForegroundColor = 50)
@HeadFontStyle(fontName = "宋体", fontHeightInPoints = 14, bold = true, color = -1)
public class RemovalDestroyApplyImportParam extends BaseRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 账号 */
    @ColumnWidth(10)
    @ExcelProperty(value ="账号")
    @ApiModelProperty("账号")
    private String sysShopsName;

    /** 站点 */
    @ColumnWidth(10)
    @ExcelProperty(value ="站点")
    @ApiModelProperty("站点")
    private String sysSite;

    /** 商家SKU */
    @ColumnWidth(40)
    @ExcelProperty(value ="MerchantSKU")
    @ApiModelProperty("MerchantSKU")
    private String merchantSku;

    /** 事业部（无需填写） */
    @ColumnWidth(26)
    @ExcelProperty(value ="事业部（无需填写）")
    @ApiModelProperty("事业部（无需填写）")
    private String department;

    /** team（无需填写） */
    @ColumnWidth(22)
    @ExcelProperty(value ="team（无需填写）")
    @ApiModelProperty("team（无需填写）")
    private String team;

    /** 运营大类（无需填写） */
    @ColumnWidth(30)
    @ExcelProperty(value ="运营大类（无需填写）")
    @ApiModelProperty("运营大类（无需填写）")
    private String productType;

    /** Order ID */
    @ColumnWidth(15)
    @ExcelProperty(value ="order id")
    @ApiModelProperty("orderId")
    private String orderId;

    /** 申请数量 */
    @ColumnWidth(13)
    @ExcelProperty(value ="申请数量")
    @ApiModelProperty("申请数量")
    private Integer applyQuantity;

    /** 收购单价 */
    @ColumnWidth(13)
    @ExcelProperty(value ="收购单价")
    @ApiModelProperty("收购单价")
    private BigDecimal unitPrice;

    /** 总价 */
    @ColumnWidth(10)
    @ExcelProperty(value ="总价")
    @ApiModelProperty("总价")
    private BigDecimal totalPrice;

    /** 备注 */
    @ColumnWidth(25)
    @ExcelProperty(value ="备注")
    @ApiModelProperty("备注")
    private String remark;

    /** 导入异常备注 */
    @ColumnWidth(25)
    @ExcelProperty(value ="导入异常备注")
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 11)
    @ApiModelProperty(value = "导入异常备注")
    private String uploadRemark;
}
