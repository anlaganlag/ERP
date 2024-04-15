package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
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
 * 报关单 出参类
 * </p>
 *
 * @author ty
 * @since 2023-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("TG_CUSTOMS_APPLY")
public class TgCustomsApplyResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty("发货公司名称")
    private String postCompanyNameCn;

    @ApiModelProperty("发货公司地址")
    private String postAddrCn;

    @ApiModelProperty("发货公司联系方式")
    private String postContactInfo;

    @ApiModelProperty("发货公司传真")
    private String postFax;

    @ApiModelProperty("发货公司统一信用代码")
    private String postCompanyNum;

    @ApiModelProperty("经营单位")
    private String operateCompany;

    @ApiModelProperty("海关编码")
    private String customsNum;

    @ApiModelProperty("运抵国编码")
    private String arrivalCountryCode;

    @ApiModelProperty("运抵国")
    private String arrivalCountryName;

    @ApiModelProperty("境外收货人")
    private String receiveContactUser;

    @ApiModelProperty("境外收货人地址")
    private String receiveAddrCn;

    @ApiModelProperty("境外收货人联系方式")
    private String receivePhone;

    @ApiModelProperty("签约地点")
    private String signingAddr;

    @ApiModelProperty("合同协议号")
    private String signingNo;

    @ApiModelProperty("申报日期")
    private Date applyDate;

    @ApiModelProperty("申报日期（年月日）")
    private String applyDateStr;

    @ApiModelProperty("出口日期")
    private Date exportDate;

    @ApiModelProperty("出口日期（年月日）")
    private String exportDateStr;

    @ApiModelProperty("指运港")
    private String directPort;

    @ApiModelProperty("贸易方式")
    private String tradeMode;

    @ApiModelProperty("免征性质")
    private String exemptionNature;

    @ApiModelProperty("结汇方式")
    private String settlementType;

    @ApiModelProperty("成交方式")
    private String tradingType;

    @ApiModelProperty("包装种类")
    private String packageType;

    @ApiModelProperty("箱子个数")
    private BigDecimal boxNum;

    @ApiModelProperty("毛重KG")
    private BigDecimal packageWeight;

    @ApiModelProperty("境内货源地")
    private String sourceAddr;

    @ApiModelProperty("是否正式报关")
    private String customsApply;

    @ApiModelProperty("数据类型 0：导入，1：关联")
    private String dataType;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

}
