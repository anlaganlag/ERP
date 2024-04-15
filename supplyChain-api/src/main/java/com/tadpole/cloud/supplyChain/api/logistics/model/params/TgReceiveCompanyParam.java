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
 * 收货公司 入参类
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
@TableName("TG_RECEIVE_COMPANY")
public class TgReceiveCompanyParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 公司名称 */
    @ApiModelProperty("公司名称")
    private String companyNameCn;

    /** 公司地址 */
    @ApiModelProperty("公司地址")
    private String addrCn;

    /** 联系人 */
    @ApiModelProperty("联系人")
    private String contactUser;

    /** 邮箱 */
    @ApiModelProperty("邮箱")
    private String email;

    /** 电话 */
    @ApiModelProperty("电话")
    private String phone;

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

    /** 公司名称集合 */
    @ApiModelProperty("公司名称集合")
    private List<String> companyNameCnList;

    @ApiModelProperty("国家/地区")
    private String countryCode;

    @ApiModelProperty("州/省/郡")
    private String state;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("邮政编码")
    private String logRecZip;

}
