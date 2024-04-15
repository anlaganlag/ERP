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
 * 清关单明细 出参类
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
@TableName("TG_CUSTOMS_CLEARANCE_DETAIL")
public class TgCustomsClearanceDetailResult implements Serializable, BaseValidatingParam {

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

    @ApiModelProperty("清关品名")
    private String clearNameCn;

    @ApiModelProperty("开票英文品名")
    private String invoiceProNameEn;

    @ApiModelProperty("HSCode")
    private String hscode;

    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    @ApiModelProperty("销售价")
    private BigDecimal amazonSalePrice;

    @ApiModelProperty("清关单价")
    private BigDecimal customsUnitPrice;

    @ApiModelProperty("销售链接")
    private String amazonSaleLink;

    @ApiModelProperty("主图链接")
    private String amazonPictureLink;

    @ApiModelProperty("带电")
    private String isCharged;

    @ApiModelProperty("带磁")
    private String isMagnet;

    @ApiModelProperty("报关英文材质")
    private String clearMaterialEn;

    @ApiModelProperty("用途")
    private String fitBrand;

    @ApiModelProperty("SKU")
    private String sku;

    @ApiModelProperty("规格型号")
    private String type;

    @ApiModelProperty("开票规格型号")
    private String style;

    @ApiModelProperty("数量")
    private BigDecimal quantity;

    @ApiModelProperty("数量单位")
    private String unit;

    @ApiModelProperty("套装属性")
    private String attribute;

    @ApiModelProperty("分单号")
    private String splitOrder;

    @ApiModelProperty("公司品牌")
    private String companyBrand;

    @ApiModelProperty("开票品名")
    private String invoiceProNameCn;

    @ApiModelProperty("箱号上传名称")
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

    @ApiModelProperty("销售价编辑状态")
    private String editStatus;

    @ApiModelProperty("清关合并状态：已合并，未合并")
    private String clearMergeStatus;

    @ApiModelProperty("特殊清关合并标识")
    private String specialMergeSign;

    @ApiModelProperty("特殊清关合并主物料编码")
    private String specialMaterialCode;

    @ApiModelProperty("特殊清关合并主物料ID")
    private String specialMaterialId;

    @ApiModelProperty("主物料编码")
    private String mainMaterialCode;

    @ApiModelProperty("运抵国")
    private String arrivalCountryName;

    @ApiModelProperty("明细ID")
    private String detailIds;

    @ApiModelProperty("是否为清关合并主数据，false：否，true：是")
    private Boolean isSelect;

    @ApiModelProperty("汇率")
    private BigDecimal indirectRate;

}
