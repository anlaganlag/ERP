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
 *  出参类
 * </p>
 *
 * @author ty
 * @since 2023-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("LS_BTB_PACK_BOX_DETAIL")
public class LsBtbPackBoxDetailResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty("箱条码")
    private String boxBarcode;

    /** 商品名称 */
    @ApiModelProperty("商品名称")
    private String productName;

    @ExcelProperty(value ="SKU")
    @ApiModelProperty("sku")
    private String sku;

    @ExcelProperty(value ="适用机型或机型")
    @ApiModelProperty("适用机型或机型")
    private String type;

    @ExcelProperty(value ="款式或尺寸")
    @ApiModelProperty("款式或尺寸")
    private String style;

    @ExcelProperty(value ="数量")
    @ApiModelProperty("数量")
    private BigDecimal quantity;

    @ExcelProperty(value ="数量单位")
    @ApiModelProperty("数量单位")
    private String unit;

    @ExcelProperty(value ="套装属性")
    @ApiModelProperty("套装属性")
    private String attribute;

    @ExcelProperty(value ="币制")
    @ApiModelProperty("币制")
    private String currency;

    @ExcelProperty(value ="箱号")
    @ApiModelProperty("箱号")
    private String boxNo;

    @ExcelProperty(value ="分单号")
    @ApiModelProperty("分单号")
    private String splitOrder;

    @ExcelProperty(value ="公司品牌")
    @ApiModelProperty("公司品牌")
    private String companyBrand;

    @ExcelProperty(value ="开票品名")
    @ApiModelProperty("开票品名")
    private String invoiceProNameCn;

    @ExcelProperty(value ="物料编码")
    @ApiModelProperty("物料编码")
    private String materialCode;

    @ExcelProperty(value ="出货清单号")
    @ApiModelProperty("BTB订单号")
    private String orderNo;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

}
