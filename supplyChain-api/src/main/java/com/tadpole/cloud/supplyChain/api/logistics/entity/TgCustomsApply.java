package com.tadpole.cloud.supplyChain.api.logistics.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 报关单 实体类
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
@TableName("TG_CUSTOMS_APPLY")
@ExcelIgnoreUnannotated
public class TgCustomsApply implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 发货公司名称 */
    @TableField("POST_COMPANY_NAME_CN")
    private String postCompanyNameCn;

    /** 发货公司地址 */
    @TableField("POST_ADDR_CN")
    private String postAddrCn;

    /** 发货公司联系方式 */
    @TableField("POST_CONTACT_INFO")
    private String postContactInfo;

    /** 发货公司传真 */
    @TableField("POST_FAX")
    private String postFax;

    /** 发货公司统一信用代码 */
    @TableField("POST_COMPANY_NUM")
    private String postCompanyNum;

    /** 经营单位 */
    @TableField("OPERATE_COMPANY")
    private String operateCompany;

    /** 海关编码 */
    @TableField("CUSTOMS_NUM")
    private String customsNum;

    /** 运抵国编码 */
    @TableField("ARRIVAL_COUNTRY_CODE")
    private String arrivalCountryCode;

    /** 运抵国 */
    @TableField("ARRIVAL_COUNTRY_NAME")
    private String arrivalCountryName;

    /** 境外收货人 */
    @TableField("RECEIVE_CONTACT_USER")
    private String receiveContactUser;

    /** 境外收货人地址 */
    @TableField("RECEIVE_ADDR_CN")
    private String receiveAddrCn;

    /** 境外收货人联系方式 */
    @TableField("RECEIVE_PHONE")
    private String receivePhone;

    /** 签约地点 */
    @TableField("SIGNING_ADDR")
    private String signingAddr;

    /** 合同协议号 */
    @TableField("SIGNING_NO")
    private String signingNo;

    /** 申报日期 */
    @TableField("APPLY_DATE")
    private Date applyDate;

    /** 出口日期 */
    @TableField("EXPORT_DATE")
    private Date exportDate;

    /** 指运港 */
    @TableField("DIRECT_PORT")
    private String directPort;

    /** 贸易方式 */
    @TableField("TRADE_MODE")
    private String tradeMode;

    /** 免征性质 */
    @TableField("EXEMPTION_NATURE")
    private String exemptionNature;

    /** 结汇方式 */
    @TableField("SETTLEMENT_TYPE")
    private String settlementType;

    /** 成交方式 */
    @TableField("TRADING_TYPE")
    private String tradingType;

    /** 包装种类 */
    @TableField("PACKAGE_TYPE")
    private String packageType;

    /** 箱子个数 */
    @TableField("BOX_NUM")
    private BigDecimal boxNum;

    /** 毛重KG */
    @TableField("PACKAGE_WEIGHT")
    private BigDecimal packageWeight;

    /** 境内货源地 */
    @TableField("SOURCE_ADDR")
    private String sourceAddr;

    /** 是否正式报关 */
    @TableField("CUSTOMS_APPLY")
    private String customsApply;

    /** 数据类型 0：导入，1：关联 */
    @TableField("DATA_TYPE")
    private String dataType;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @TableField("CREATE_USER")
    private String createUser;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @TableField("UPDATE_USER")
    private String updateUser;

    /**
     * 申报日期（年月日）
     */
    @TableField(exist = false)
    private String applyDateStr;

    /**
     * 出口日期（年月日）
     */
    @TableField(exist = false)
    private String exportDateStr;

    /**
     * 报关单净重
     */
    @TableField(exist = false)
    private BigDecimal totalNetWeight;

    /**
     * 国内报关单sheet0合计
     */
    @TableField(exist = false)
    private String export0TotalAmountStr;

    /**
     * 国内报关单sheet1总值
     */
    @TableField(exist = false)
    private String export1TotalAmount0;

    /**
     * 国内报关单sheet1合同总值（大写）
     */
    @TableField(exist = false)
    private String export1TotalAmount1;

    /**
     * 国内报关单sheet1装运期
     */
    @TableField(exist = false)
    private String export1PackageDate;

    /**
     * 国内报关单sheet2总净重
     */
    @TableField(exist = false)
    private String export2TotalNetWeight;

    /**
     * 国内报关单sheet2总毛重
     */
    @TableField(exist = false)
    private String export2TotalGrossWeight;

    /**
     * 国内报关单sheet2总数量
     */
    @TableField(exist = false)
    private String export2TotalQuantity;

    /**
     * 国内报关单sheet3总金额
     */
    @TableField(exist = false)
    private String export3TotalAmount;

    /**
     * 报关单净重（四舍五入取整数）
     */
    @TableField(exist = false)
    private BigDecimal totalNetWeightStr;

    /** 毛重KG（四舍五入取整数） */
    @TableField(exist = false)
    private BigDecimal packageWeightStr;

}