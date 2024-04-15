package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
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
 * 清关单明细 入参类
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
@TableName("TG_CUSTOMS_CLEARANCE_DETAIL")
public class TgCustomsClearanceDetailParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 主表ID */
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

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 清关品名 */
    @ApiModelProperty("清关品名")
    private String clearNameCn;

    /** 开票英文品名 */
    @ApiModelProperty("开票英文品名")
    private String invoiceProNameEn;

    /** HSCode */
    @ApiModelProperty("HSCode")
    private String hscode;

    /** 单价 */
    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    /** 销售价 */
    @ApiModelProperty("销售价")
    private BigDecimal amazonSalePrice;

    /** 清关单价 */
    @ApiModelProperty("清关单价")
    private BigDecimal customsUnitPrice;

    /** 销售链接 */
    @ApiModelProperty("销售链接")
    private String amazonSaleLink;

    /** 主图链接 */
    @ApiModelProperty("主图链接")
    private String amazonPictureLink;

    /** 带电 */
    @ApiModelProperty("带电")
    private String isCharged;

    /** 带磁 */
    @ApiModelProperty("带磁")
    private String isMagnet;

    /** 报关英文材质 */
    @ApiModelProperty("报关英文材质")
    private String clearMaterialEn;

    /** 用途 */
    @ApiModelProperty("用途")
    private String fitBrand;

    /** SKU */
    @ApiModelProperty("SKU")
    private String sku;

    /** 规格型号 */
    @ApiModelProperty("规格型号")
    private String type;

    /** 开票规格型号 */
    @ApiModelProperty("开票规格型号")
    private String style;

    /** 数量 */
    @ApiModelProperty("数量")
    private BigDecimal quantity;

    /** 数量单位 */
    @ApiModelProperty("数量单位")
    private String unit;

    /** 套装属性 */
    @ApiModelProperty("套装属性")
    private String attribute;

    /** 分单号 */
    @ApiModelProperty("分单号")
    private String splitOrder;

    /** 公司品牌 */
    @ApiModelProperty("公司品牌")
    private String companyBrand;

    /** 开票品名 */
    @ApiModelProperty("开票品名")
    private String invoiceProNameCn;

    /** 箱号上传名称 */
    @ApiModelProperty("箱号上传名称")
    private String boxNoName;

    /** 箱型 */
    @ApiModelProperty("箱型")
    private String boxType;

    /** 重量 */
    @ApiModelProperty("重量")
    private BigDecimal weight;

    /** 长 */
    @ApiModelProperty("长")
    private BigDecimal goodsLong;

    /** 宽 */
    @ApiModelProperty("宽")
    private BigDecimal wide;

    /** 高 */
    @ApiModelProperty("高")
    private BigDecimal high;

    /** 数据类型 0：导入，1：关联 */
    @ApiModelProperty("数据类型 0：导入，1：关联")
    private String dataType;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updateUser;

    @ApiModelProperty("明细ID")
    private String detailIds;

    @ApiModelProperty("明细ID集合")
    private List<String> detailIdList;

}
