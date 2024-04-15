package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsApplyDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: ty
 * @description: 报关单保存
 * @date: 2023/6/20
 */
@Data
public class TgCustomsApplySaveParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 发货公司名称 */
    @ApiModelProperty("发货公司名称")
    private String postCompanyNameCn;

    /** 发货公司地址 */
    @ApiModelProperty("发货公司地址")
    private String postAddrCn;

    /** 发货公司联系方式 */
    @ApiModelProperty("发货公司联系方式")
    private String postContactInfo;

    /** 发货公司传真 */
    @ApiModelProperty("发货公司传真")
    private String postFax;

    @ApiModelProperty("发货公司统一信用代码")
    private String postCompanyNum;

    /** 经营单位 */
    @ApiModelProperty("经营单位")
    private String operateCompany;

    /** 海关编码 */
    @ApiModelProperty("海关编码")
    private String customsNum;

    /** 运抵国编码 */
    @ApiModelProperty("运抵国编码")
    private String arrivalCountryCode;

    /** 运抵国 */
    @ApiModelProperty("运抵国")
    private String arrivalCountryName;

    /** 境外收货人 */
    @ApiModelProperty("境外收货人")
    private String receiveContactUser;

    /** 境外收货人地址 */
    @ApiModelProperty("境外收货人地址")
    private String receiveAddrCn;

    /** 境外收货人联系方式 */
    @ApiModelProperty("境外收货人联系方式")
    private String receivePhone;

    /** 签约地点 */
    @ApiModelProperty("签约地点")
    private String signingAddr;

    /** 合同协议号 */
    @ApiModelProperty("合同协议号")
    private String signingNo;

    /** 申报日期 */
    @ApiModelProperty("申报日期")
    private Date applyDate;

    /** 出口日期 */
    @ApiModelProperty("出口日期")
    private Date exportDate;

    /** 指运港 */
    @ApiModelProperty("指运港")
    private String directPort;

    /** 贸易方式 */
    @ApiModelProperty("贸易方式")
    private String tradeMode;

    /** 免征性质 */
    @ApiModelProperty("免征性质")
    private String exemptionNature;

    /** 结汇方式 */
    @ApiModelProperty("结汇方式")
    private String settlementType;

    /** 成交方式 */
    @ApiModelProperty("成交方式")
    private String tradingType;

    /** 包装种类 */
    @ApiModelProperty("包装种类")
    private String packageType;

    /** 箱子个数 */
    @ApiModelProperty("箱子个数")
    private BigDecimal boxNum;

    /** 毛重KG */
    @ApiModelProperty("毛重KG")
    private BigDecimal packageWeight;

    /** 境内货源地 */
    @ApiModelProperty("境内货源地")
    private String sourceAddr;

    /** 是否正式报关 */
    @ApiModelProperty("是否正式报关")
    private String customsApply;

    /** 数据类型 0：导入，1：关联 */
    @ApiModelProperty("数据类型 0：导入，1：关联")
    private String dataType;

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

    /** 报关明细集合 */
    @ApiModelProperty("报关明细集合")
    List<TgCustomsApplyDetail> applyDetails;

    /** 报关单编辑导入明细保存是否为新导入数据 0：否，1：是 */
    @ApiModelProperty("报关单编辑导入明细保存是否为新导入数据 0：否，1：是")
    private String importNew;

}
