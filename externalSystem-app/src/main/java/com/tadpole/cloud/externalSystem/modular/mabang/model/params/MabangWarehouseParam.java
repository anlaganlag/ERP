package com.tadpole.cloud.externalSystem.modular.mabang.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
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
 * 马帮仓库列表
 * </p>
 *
 * @author lsy
 * @since 2022-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("MABANG_WAREHOUSE")
public class MabangWarehouseParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 仓库编号 */
    @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 名称 */
    @ApiModelProperty("NAME")
    private String name;

    /** 仓库名称-多选 */
    @ApiModelProperty("仓库名称-多选")
    private List<String> names;

    /** 1启用2停用 */
    @ApiModelProperty("STATUS")
    private String status;

    /** 1：自建仓库2：第三方仓库 */
    @ApiModelProperty("TYPE")
    private String type;

    /** 仓库类型-多选 */
    @ApiModelProperty("仓库类型-多选[1,2]")
    private List<String> types;


    /** 联系人 */
    @ApiModelProperty("CONTACT")
    private String contact;

    /** 国家 */
    @ApiModelProperty("COUNTRY_CODE")
    private String countryCode;

    /** 省 */
    @ApiModelProperty("PROVINCE")
    private String province;

    /** 市 */
    @ApiModelProperty("CITY")
    private String city;

    /** 区 */
    @ApiModelProperty("AREA")
    private String area;

    /** 地址 */
    @ApiModelProperty("ADDRESS")
    private String address;

    /** 邮编 */
    @ApiModelProperty("ZIPCODE")
    private String zipcode;

    /** 电话 */
    @ApiModelProperty("TELEPHONE")
    private String telephone;

    /** 手机号码 */
    @ApiModelProperty("PHONE")
    private String phone;

    /** 1是2否默认仓库 */
    @ApiModelProperty("IS_DEFAULT")
    private String isDefault;

    /** 仓库编码 */
    @ApiModelProperty("CODE")
    private String code;

    /** 财务编码 */
    @ApiModelProperty("FINANCE_CODE")
    private String financeCode;

    /** 同步方式（0 ：系统同步,1：手动人工同步） */
    @ApiModelProperty("SYNC_TYPE")
    private String syncType;

    /** 同步时间(本条记录值最后同步时间 */
    @ApiModelProperty("SYNC_TIME")
    private Date syncTime;

    /** 同步状态（） ：同步失败,1：同步成功 */
    @ApiModelProperty("SYNC_STATUS")
    private String syncStatus;

    /** 同步失败次数默认0次 */
    @ApiModelProperty("SYNC_FAIL_TIMES")
    private BigDecimal syncFailTimes;

    /** 同步结果消息内容 */
    @ApiModelProperty("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

}