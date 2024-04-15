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
 * 发货公司 入参类
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
@TableName("TG_POST_COMPANY")
public class TgPostCompanyParam extends BaseRequest implements Serializable, BaseValidatingParam {

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

    /** 海关编号 */
    @ApiModelProperty("海关编号")
    private String customsNum;

    /** 统一信用代码 */
    @ApiModelProperty("统一信用代码")
    private String companyNum;

    /** 联系方式 */
    @ApiModelProperty("联系方式")
    private String contactInfo;

    /** 传真 */
    @ApiModelProperty("传真")
    private String fax;

    /** 联系人 */
    @ApiModelProperty("联系人")
    private String contactUser;

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

}
