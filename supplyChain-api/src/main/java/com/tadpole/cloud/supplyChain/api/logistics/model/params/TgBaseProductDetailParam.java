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

/**
 * <p>
 * 通关产品详细信息 入参类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@ExcelIgnoreUnannotated
@NoArgsConstructor
@TableName("TG_BASE_PRODUCT_DETAIL")
public class TgBaseProductDetailParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 通关产品基础信息ID */
    @ApiModelProperty("通关产品基础信息ID")
    private BigDecimal pid;

    /** 国家编码 */
    @ApiModelProperty("国家编码")
    private String countryCode;

    /** 国家名称 */
    @ApiModelProperty("国家名称")
    private String countryName;

    /** HSCode */
    @ApiModelProperty("HSCode")
    private String hsCode;

    /** 合并状态 0：未合并，1：已合并 */
    @ApiModelProperty("合并状态 0：未合并，1：已合并")
    private String mergeStatus;

    /** 合并ID：合并数据对应合并后的数据ID */
    @ApiModelProperty("合并ID：合并数据对应合并后的数据ID")
    private BigDecimal mergeId;

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

    /** 物料编码 */
    @ExcelProperty(value ="物料编码*")
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 主物料 */
    @ApiModelProperty("主物料")
    private String mainMaterialCode;

    /** 开票品名（英文） */
    @ExcelProperty(value ="开票英文品名*")
    @ApiModelProperty("开票品名（英文）")
    private String invoiceProNameEn;

    /** 报关材质（英文） */
    @ExcelProperty(value ="报关英文材质*")
    @ApiModelProperty("报关材质（英文）")
    private String clearMaterialEn;

    /** 是否带磁 0：否，1：是 */
    @ApiModelProperty("是否带磁 0：否，1：是")
    private String isMagnet;

    /** 美国(HSCode) */
    @ExcelProperty(value ="美国(HSCode)")
    @ApiModelProperty("美国(HSCode)")
    private String usHsCode;

    /** 英国(HSCode) */
    @ExcelProperty(value ="英国(HSCode)")
    @ApiModelProperty("英国(HSCode)")
    private String ukHsCode;

    /** 德国(HSCode) */
    @ExcelProperty(value ="德国(HSCode)")
    @ApiModelProperty("德国(HSCode)")
    private String deHsCode;

    /** 法国(HSCode) */
    @ExcelProperty(value ="法国(HSCode)")
    @ApiModelProperty("法国(HSCode)")
    private String frHsCode;

    /** 加拿大(HSCode) */
    @ExcelProperty(value ="加拿大(HSCode)")
    @ApiModelProperty("加拿大(HSCode)")
    private String caHsCode;

    /** 日本(HSCode) */
    @ExcelProperty(value ="日本(HSCode)")
    @ApiModelProperty("日本(HSCode)")
    private String jpHsCode;

    /** 导入异常信息备注 */
    @ExcelProperty(value ="导入异常信息备注")
    @ApiModelProperty("导入异常信息备注")
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 11)
    private String uploadRemark;

}
