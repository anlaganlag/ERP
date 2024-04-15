package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
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
* @since 2022-09-09
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class OverseasReportResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 收货仓 */
    @ApiModelProperty("收货仓")
    @ExcelProperty(value ="收货仓")
    private String inWarehouseName;

    /** 发货日期 */
    @ApiModelProperty("发货日期")
    @ExcelProperty(value ="发货日期")
    private Date shipmentDate;

    /** 账号 */
    @ApiModelProperty("账号")
    @ExcelProperty(value ="账号")
    private String sysShopsName;

    /** 站点 */
    @ApiModelProperty("站点")
    @ExcelProperty(value ="站点")
    private String sysSite;

    /** FNSKU */
    @ApiModelProperty("FN_SKU")
    @ExcelProperty(value ="FNSKU")
    private String fnSku;

    /** 产品名称 */
    @ApiModelProperty("产品名称")
    @ExcelProperty(value ="产品名称")
    private String materialName;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    @ExcelProperty(value ="物料编码")
    private String materialCode;

    /** SKU */
    @ApiModelProperty("SKU")
    @ExcelProperty(value ="SKU")
    private String sku;

    /** 发货数量 */
    @ApiModelProperty("发货数量")
    @ExcelProperty(value ="发货数量")
    private BigDecimal quantity;

    /** 物流单号 */
    @ApiModelProperty("物流单号")
    @ExcelProperty(value ="物流单号")
    private String logisticsNum;

    /** 箱条码 */
    @ApiModelProperty("箱条码")
    @ExcelProperty(value ="箱条码")
    private String packageBarCode;

    /** 箱序号 */
    @ApiModelProperty("箱序号")
    @ExcelProperty(value ="箱序号")
    private BigDecimal packageNum;

    /** 长 */
    @ApiModelProperty("箱长")
    @ExcelProperty(value ="packDetBoxLength")
    private BigDecimal length;

    /** 宽 */
    @ApiModelProperty("箱宽")
    @ExcelProperty(value ="packDetBoxWidth")
    private BigDecimal width;

    /** 高 */
    @ApiModelProperty("箱高")
    @ExcelProperty(value ="packDetBoxHeight")
    private BigDecimal height;

    /** 重量 */
    @ApiModelProperty("重量")
    @ExcelProperty(value ="packDetBoxWeight")
    private BigDecimal weight;

    /** 出货清单号 */
    @ApiModelProperty("出货清单号")
    @ExcelProperty(value ="出货清单号")
    private String inOrder;

    /** 签收日期 */
    @ApiModelProperty("签收日期")
    @ExcelProperty(value ="签收日期")
    private Date confirmTime;

    /** 海外仓库存 */
    @ApiModelProperty("海外仓库存")
    @ExcelProperty(value ="海外仓库存")
    private BigDecimal inventoryQuantity;

    /** 海外仓来货 */
    @ApiModelProperty("海外仓来货")
    @ExcelProperty(value ="海外仓来货")
    private BigDecimal comeQuantity;

    /** 运输方式 */
    @ApiModelProperty("运输方式")
    @ExcelProperty(value ="运输方式")
    private String suggestTransType;

    /** 备注 */
    @ApiModelProperty("备注")
    @ExcelProperty(value ="备注")
    private String remark;

    /** 事业部 */
    @ApiModelProperty("事业部")
    @ExcelProperty(value ="事业部")
    private String department;

    /** Team组 */
    @ApiModelProperty("Team组")
    @ExcelProperty(value ="Team组")
    private String team;

    /** 申请人 */
    @ApiModelProperty("申请人")
    @ExcelProperty(value ="申请人")
    private String needsUser;

    /** 发货仓 */
    @ApiModelProperty("发货仓")
    @ExcelProperty(value ="发货仓")
    private String outWarehouseName;












}