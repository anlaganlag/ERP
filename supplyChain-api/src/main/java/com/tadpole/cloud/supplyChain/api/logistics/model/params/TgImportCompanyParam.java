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
 * 进口商 入参类
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
@TableName("TG_IMPORT_COMPANY")
public class TgImportCompanyParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 公司名称（中文） */
    @ApiModelProperty("公司名称（中文）")
    private String companyNameCn;

    /** 公司名称（英文） */
    @ApiModelProperty("公司名称（英文）")
    private String companyNameEn;

    /** 地址（中文） */
    @ApiModelProperty("地址（中文）")
    private String addrCn;

    /** 地址（英文） */
    @ApiModelProperty("地址（英文）")
    private String addrEn;

    /** 税号 */
    @ApiModelProperty("税号")
    private String taxNum;

    /** 清关号 */
    @ApiModelProperty("清关号")
    private String customsNum;

    /** 运抵国家编码 */
    @ApiModelProperty("运抵国家编码")
    private String arrivalCountryCode;

    /** 运抵国家名称 */
    @ApiModelProperty("运抵国家名称")
    private String arrivalCountryName;

    /** 是否包税 0：否，1：是 */
    @ApiModelProperty("是否包税 0：否，1：是")
    private String includeTax;

    /** 联系方式 */
    @ApiModelProperty("联系方式")
    private String contactInfo;

    /** 合约账号 */
    @ApiModelProperty("合约账号")
    private String contractNo;

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

    /** 公司名称（中文）集合 */
    @ApiModelProperty("公司名称（中文）集合")
    private List<String> companyNameCnList;

    /** 运抵国家编码集合 */
    @ApiModelProperty("运抵国家编码集合")
    private List<String> arrivalCountryCodeList;

}
