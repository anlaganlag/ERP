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
 * 发货公司 出参类
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
@TableName("TG_POST_COMPANY")
public class TgPostCompanyResult implements Serializable, BaseValidatingParam {

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

    @ExcelProperty(value ="海关编号")
    @ApiModelProperty("海关编号")
    private String customsNum;

    @ExcelProperty(value ="统一信用代码")
    @ApiModelProperty("统一信用代码")
    private String companyNum;

    @ExcelProperty(value ="联系方式")
    @ApiModelProperty("联系方式")
    private String contactInfo;

    @ExcelProperty(value ="传真")
    @ApiModelProperty("传真")
    private String fax;

    @ExcelProperty(value ="联系人")
    @ApiModelProperty("联系人")
    private String contactUser;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

}
