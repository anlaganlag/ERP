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
 * 进口商 出参类
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
@TableName("TG_IMPORT_COMPANY")
public class TgImportCompanyResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ExcelProperty(value ="公司名称（中文）")
    @ApiModelProperty("公司名称（中文）")
    private String companyNameCn;

    @ExcelProperty(value ="公司名称（英文）")
    @ApiModelProperty("公司名称（英文）")
    private String companyNameEn;

    @ExcelProperty(value ="地址（中文）")
    @ApiModelProperty("地址（中文）")
    private String addrCn;

    @ExcelProperty(value ="地址（英文）")
    @ApiModelProperty("地址（英文）")
    private String addrEn;

    @ExcelProperty(value ="税号")
    @ApiModelProperty("税号")
    private String taxNum;

    @ExcelProperty(value ="清关号")
    @ApiModelProperty("清关号")
    private String customsNum;

    @ApiModelProperty("运抵国家编码")
    private String arrivalCountryCode;

    @ExcelProperty(value ="运抵国")
    @ApiModelProperty("运抵国家名称")
    private String arrivalCountryName;

    @ApiModelProperty("是否包税 0：否，1：是")
    private String includeTax;

    @ExcelProperty(value ="是否包税")
    @ApiModelProperty("是否包税")
    private String includeTaxDesc;

    @ExcelProperty(value ="联系方式")
    @ApiModelProperty("联系方式")
    private String contactInfo;

    @ExcelProperty(value ="合约账号")
    @ApiModelProperty("合约账号")
    private String contractNo;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

}
