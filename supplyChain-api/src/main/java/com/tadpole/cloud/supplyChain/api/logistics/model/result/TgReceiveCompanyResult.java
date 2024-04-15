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
 * 收货公司 出参类
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
@TableName("TG_RECEIVE_COMPANY")
public class TgReceiveCompanyResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ExcelProperty(value ="公司名称")
    @ApiModelProperty("公司名称")
    private String companyNameCn;

    @ExcelProperty(value ="国家/地区")
    @ApiModelProperty("国家/地区")
    private String countryCode;

    @ExcelProperty(value ="城市")
    @ApiModelProperty("城市")
    private String city;

    @ExcelProperty(value ="州/省/郡")
    @ApiModelProperty("州/省/郡")
    private String state;

    @ExcelProperty(value ="公司地址")
    @ApiModelProperty("公司地址")
    private String addrCn;

    @ExcelProperty(value ="邮政编码")
    @ApiModelProperty("邮政编码")
    private String logRecZip;

    @ExcelProperty(value ="联系人")
    @ApiModelProperty("联系人")
    private String contactUser;

    @ExcelProperty(value ="邮箱")
    @ApiModelProperty("邮箱")
    private String email;

    @ExcelProperty(value ="电话")
    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

}
