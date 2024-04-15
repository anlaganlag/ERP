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
 * 清关税率 入参类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("TG_CUSTOMS_TAX_RATE")
public class TgCustomsTaxRateParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 国家编码 */
    @ApiModelProperty("国家编码")
    private String countryCode;

    /** 国家名称 */
    @ExcelProperty(value ="国家")
    @ApiModelProperty("国家名称")
    private String countryName;

    /** HSCode */
    @ExcelProperty(value ="HSCode")
    @ApiModelProperty("HSCode")
    private String hsCode;

    /** 流转税率（欧洲VAT/加拿大GST/日本消费税） */
    @ExcelProperty(value ="流转税率（欧洲VAT/加拿大GST/日本消费税）")
    @ApiModelProperty("流转税率（欧洲VAT/加拿大GST/日本消费税）")
    private BigDecimal changeTaxRate;

    /** 关税率 */
    @ExcelProperty(value ="关税率")
    @ApiModelProperty("关税率")
    private BigDecimal taxRate;

    /** 附加税 */
    @ExcelProperty(value ="附加税")
    @ApiModelProperty("附加税")
    private BigDecimal addTaxRate;

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

    /** 导入异常信息备注 */
    @ExcelProperty(value ="导入异常信息备注")
    @ApiModelProperty("导入异常信息备注")
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 11)
    private String uploadRemark;

}
