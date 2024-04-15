package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnore;
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
 * 设置物流方式
 * </p>
 *
 * @author lsy
 * @since 2022-09-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("LOGISTICS_WAY")
public class LogisticsWayResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ExcelIgnore
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 物料编码 */
    @ExcelProperty("物料编码")
    @ApiModelProperty("MATERIAL_CODE")
    private String materialCode;

    /** 区域 */
    @ExcelProperty("区域")
    @ApiModelProperty("AREA")
    private String area;

    /** 物流方式 */
    @ExcelProperty("物流方式")
    @ApiModelProperty("WAY")
    private String way;

    /** 创建人 */
    @ExcelIgnore
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 修改人 */
    @ExcelIgnore
    @ApiModelProperty("UPDATE_BY")
    private String updateBy;

    /** 创建时间 */
    @ExcelIgnore
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ExcelIgnore
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

    @ExcelIgnore
    @ApiModelProperty("CATEGORY")
    private String category;

    /** 运营大类 */
    @ExcelProperty("运营大类")
    @ApiModelProperty("PRODUCT_TYPE")
    private String productType;

    /** 产品名称 */
    @ExcelProperty("产品名称")
    @ApiModelProperty("PRODUCT_NAME")
    private String productName;

    /** 款式 */
    @ExcelProperty("款式")
    @ApiModelProperty("STYLE")
    private String style;

    /** 主材料 */
    @ExcelProperty("主材料")
    @ApiModelProperty("MAIN_MATERIAL")
    private String mainMaterial;

    /** 图案 */
    @ExcelProperty("图案")
    @ApiModelProperty("DESIGN")
    private String design;

    /** 公司品牌 */
    @ExcelProperty("公司品牌")
    @ApiModelProperty("COMPANY_BRAND")
    private String companyBrand;

    /** 适用品牌或对象 */
    @ExcelProperty("适用品牌或对象")
    @ApiModelProperty("FIT_BRAND")
    private String fitBrand;

    /** 型号 */
    @ExcelProperty("型号")
    @ApiModelProperty("MODEL")
    private String model;

    /** 颜色 */
    @ExcelProperty("颜色")
    @ApiModelProperty("COLOR")
    private String color;

    /** 尺码 */
    @ExcelProperty("尺码")
    @ApiModelProperty("SIZES")
    private String sizes;

    /** 包装数量 */
    @ExcelProperty("包装数量")
    @ApiModelProperty("PACKING")
    private String packing;

    /** 版本 */
    @ExcelProperty("版本")
    @ApiModelProperty("VERSION")
    private String version;

    @ExcelIgnore
    @ApiModelProperty("TYPE")
    private String type;

    @ExcelIgnore
    @ApiModelProperty("BUYER")
    private String buyer;

    @ExcelIgnore
    @ApiModelProperty("DEVELOPER")
    private String developer;

    @ExcelIgnore
    @ApiModelProperty("FIRST_ORDER_DATE")
    private String firstOrderDate;

    @ExcelIgnore
    @ApiModelProperty("VOLUME")
    private String volume;

    @ExcelIgnore
    @ApiModelProperty("NETWEIGHT")
    private String netweight;

    @ExcelIgnore
    @ApiModelProperty("GROSSWEIGHT")
    private String grossweight;

    @ExcelIgnore
    @ApiModelProperty("STYLE_SECOND_LABEL")
    private String styleSecondLabel;

    @ExcelIgnore
    @ApiModelProperty("ACCOUNT_DATE")
    private String accountDate;

    @ExcelIgnore
    @ApiModelProperty("LOGISTICS_MODE")
    private String logisticsModel;

    @ExcelIgnore
    @ApiModelProperty("MATERIAL_NAME")
    private String materialName;

    @ExcelIgnore
    @ApiModelProperty("SEASON_LABEL")
    private String seasonLabel;

    @ExcelIgnore
    @ApiModelProperty("FESTIVAL_LABEL")
    private String festivalLabel;
}