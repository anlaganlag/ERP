package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
*
* </p>
*
* @author cyt
* @since 2022-07-19
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@ColumnWidth(15)
@HeadFontStyle(fontName = "宋体", fontHeightInPoints = 14, bold = true, color = -1)
public class RakutenOverseasShipmentResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    /*@ApiModelProperty("ID")
    private BigDecimal id;*/

    /** 平台 */
    @ApiModelProperty("平台")
    private String platform;

    /** 账号 */
    @ApiModelProperty("账号")
    private String shopName;

    /** 站点 */
    @ApiModelProperty("站点")
    private String site;

    /** 订单编号 */
    @ExcelProperty(value ="订单编号")
    @ApiModelProperty("订单编号")
    private String orderNo;

    /** 客户订单编号 */
    /*@ExcelProperty(value ="客户订单编号")
    @ApiModelProperty("客户订单编号")
    private String customerOrderNo;*/

    /** 订购时间 */
    /*@ExcelProperty(value ="订购时间")
    @ApiModelProperty("订购时间")
    private Date orderTime;*/

    /** 发货时间 */
    @ExcelProperty(value ="发货时间")
    @ApiModelProperty("发货时间")
    private Date deliveryTime;

    /** 客户商品编码 */
    @ExcelProperty(value ="客户商品编码")
    @ApiModelProperty("客户商品编码")
    private String customerGoodsCode;

    /** 商品名称 */
    /*@ExcelProperty(value ="商品名称")
    @ApiModelProperty("商品名称")
    private String materialName;*/

    /** 商品数量 */
    @ExcelProperty(value ="商品数量")
    @ApiModelProperty("商品数量")
    private BigDecimal orderQty;

    /** 现有库存 */
    @ExcelProperty(value ="现有库存")
    @ApiModelProperty("现有库存")
    private BigDecimal inventory;

    /** 是否已上传 */
    @ExcelProperty(value ="是否已上传")
    @ApiModelProperty("是否已上传")
    private String isUpload;

    /** 库存状态 */
    @ExcelProperty(value ="库存状态")
    @ApiModelProperty("库存状态")
    private String invStatus;

    /** 发件公司 */
    @ExcelProperty(value ="发件公司")
    @ApiModelProperty("发件公司")
    private String shipmentsCompany;

    /** team */
    @ApiModelProperty(hidden = true)
    private String team;

    /**
     * 调出组织编码
     */
    @ApiModelProperty(hidden = true)
    private String outOrg;

    /**
     * 调入组织编码
     */
    @ApiModelProperty(hidden = true)
    private String inOrg;

    @ExcelProperty(value ="导入错误备注")
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
    private String uploadRemark;

}