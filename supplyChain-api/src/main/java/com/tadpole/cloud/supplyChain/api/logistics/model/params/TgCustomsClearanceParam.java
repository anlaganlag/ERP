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
 * 清关单 入参类
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
@TableName("TG_CUSTOMS_CLEARANCE")
public class TgCustomsClearanceParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 发票号码 */
    @ApiModelProperty("发票号码")
    private String invoiceNo;

    /** 发货公司名称（英文） */
    @ApiModelProperty("发货公司名称（英文）")
    private String postCompanyNameEn;

    /** 发货公司联系方式 */
    @ApiModelProperty("发货公司联系方式")
    private String postContactInfo;

    /** 发货公司联系人 */
    @ApiModelProperty("发货公司联系人")
    private String postContactUser;

    /** 发货公司地址（英文） */
    @ApiModelProperty("发货公司地址（英文）")
    private String postAddrEn;

    /** 收货公司名称 */
    @ApiModelProperty("收货公司名称")
    private String receiveCompanyNameCn;

    /** 收货公司地址 */
    @ApiModelProperty("收货公司地址")
    private String receiveAddrCn;

    /** 收货公司联系人 */
    @ApiModelProperty("收货公司联系人")
    private String receiveContactUser;

    /** 收货公司邮箱 */
    @ApiModelProperty("收货公司邮箱")
    private String receiveEmail;

    /** 收货公司电话 */
    @ApiModelProperty("收货公司电话")
    private String receivePhone;

    /** 进口商公司名称（英文） */
    @ApiModelProperty("进口商公司名称（英文）")
    private String importCompanyNameEn;

    /** 进口商公司地址（英文） */
    @ApiModelProperty("进口商公司地址（英文）")
    private String importAddrEn;

    /** 税号 */
    @ApiModelProperty("税号")
    private String importTaxNum;

    /** 清关号 */
    @ApiModelProperty("清关号")
    private String importCustomsNum;

    /** 运抵国编码 */
    @ApiModelProperty("运抵国编码")
    private String arrivalCountryCode;

    /** 运抵国 */
    @ApiModelProperty("运抵国")
    private String arrivalCountryName;

    /** 是否包税 */
    @ApiModelProperty("是否包税")
    private String importIncludeTax;

    /** 进口商公司联系方式 */
    @ApiModelProperty("进口商公司联系方式")
    private String importContactInfo;

    /** 发货日期 */
    @ApiModelProperty("发货日期")
    private Date postDate;

    /** COUNTRY_OF_EXPORT */
    @ApiModelProperty("COUNTRY_OF_EXPORT")
    private String countryOfExport;

    /** DELIVERY_TERM */
    @ApiModelProperty("DELIVERY_TERM")
    private String deliveryTerm;

    /** PAYMENT_METHORD */
    @ApiModelProperty("PAYMENT_METHORD")
    private String paymentMethord;

    /** 运费 */
    @ApiModelProperty("运费")
    private BigDecimal transportCost;

    /** 保费 */
    @ApiModelProperty("保费")
    private BigDecimal insureCost;

    /** 币制 */
    @ApiModelProperty("币制")
    private String currency;

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

    /** 发货公司名称（英文）集合 */
    @ApiModelProperty("发货公司名称（英文）集合")
    private List<String> postCompanyNameEnList;

    /** 收货公司名称集合 */
    @ApiModelProperty("收货公司名称集合")
    private List<String> receiveCompanyNameCnList;

    /** 进口商公司名称（英文）集合 */
    @ApiModelProperty("进口商公司名称（英文）集合")
    private List<String> importCompanyNameEnList;

    /** 清关品名 */
    @ApiModelProperty("清关品名")
    private String clearNameCn;

    /** 清关品名集合*/
    @ApiModelProperty("清关品名集合")
    private List<String> clearNameCnList;

    /** SKU */
    @ApiModelProperty("SKU")
    private String sku;

    /** SKU集合 */
    @ApiModelProperty("SKU集合")
    private List<String> skuList;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 物料编码集合 */
    @ApiModelProperty("物料编码集合")
    private List<String> materialCodeList;

    /** 发票号码集合 */
    @ApiModelProperty("发票号码集合")
    private List<String> invoiceNoList;

    /** 发货日期开始时间 */
    @ApiModelProperty(value = "发货日期开始时间")
    private String postDateStart;

    /** 发货日期结束时间 */
    @ApiModelProperty(value = "发货日期结束时间")
    private String postDateEnd;

    /** 分单号 */
    @ApiModelProperty(hidden = true)
    private String splitOrder;

    /** 清关发票类型 0：最低值，1：平均值 */
    @ApiModelProperty(hidden = true)
    private String orderType;

    /** 清关合并ID（包含人工合并和系统合并，分别对应表的id） */
    @ApiModelProperty(hidden = true)
    private BigDecimal mergeId;
}
