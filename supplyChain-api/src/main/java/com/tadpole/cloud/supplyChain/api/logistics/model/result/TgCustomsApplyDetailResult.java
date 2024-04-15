package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
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
 * 报关单明细 出参类
 * </p>
 *
 * @author ty
 * @since 2023-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("TG_CUSTOMS_APPLY_DETAIL")
public class TgCustomsApplyDetailResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty("主表ID")
    private BigDecimal pid;

    /** 出货清单号 */
    @ApiModelProperty("出货清单号")
    private String packCode;

    /** 箱号 */
    @ApiModelProperty("箱号")
    private String boxNo;

    /** 调拨单号 */
    @ApiModelProperty("调拨单号")
    private String packDirectCode;

    @ApiModelProperty("物料编码")
    private String materialCode;

    @ApiModelProperty("海关编码")
    private String customsNum;

    @ApiModelProperty("要素")
    private String essentialFactor;

    @ApiModelProperty("SKU")
    private String sku;

    @ApiModelProperty("适用机型或机型")
    private String type;

    @ApiModelProperty("款式或尺寸")
    private String style;

    @ApiModelProperty("数量")
    private BigDecimal quantity;

    @ApiModelProperty("数量单位")
    private String unit;

    @ApiModelProperty("套装属性")
    private String attribute;

    @ApiModelProperty("币制")
    private String currency;

    @ApiModelProperty("公司品牌")
    private String companyBrand;

    @ApiModelProperty("开票品名")
    private String invoiceProNameCn;

    @ApiModelProperty("箱号上次名称")
    private String boxNoName;

    @ApiModelProperty("箱型")
    private String boxType;

    @ApiModelProperty("重量")
    private BigDecimal weight;

    @ApiModelProperty("长")
    private BigDecimal goodsLong;

    @ApiModelProperty("宽")
    private BigDecimal wide;

    @ApiModelProperty("高")
    private BigDecimal high;

    /** 数据类型 0：导入，1：关联 */
    @ApiModelProperty("数据类型 0：导入，1：关联")
    private String dataType;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

    /** MC是否存在此箱型 0：否，1：是 */
    @ApiModelProperty("MC是否存在此箱型 0：否，1：是")
    private Boolean hasBoxType;

}
