package com.tadpole.cloud.externalSystem.modular.ebms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
 * <p>
 *  实体类
 * </p>
 *
 * @author S20190109
 * @since 2023-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("TbComShop")
@ExcelIgnoreUnannotated
public class Tbcomshop implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 店铺收款银行主体 */
    @TableField("shopColAccBankMainBody")
    private String shopcolaccbankmainbody;

    /** 收款银行备注 */
    @TableField("shopColAccBankRemark")
    private String shopcolaccbankremark;

    /** 账户持有人 */
    @TableField("shopAccountHoldName")
    private String shopaccountholdname;

    /** 店铺扣款信用卡号 */
    @TableField("shopAccountNo")
    private String shopaccountno;

    /** 信用卡开户行 */
    @TableField("shopBankName")
    private String shopbankname;

    /** 有效期(开始) */
    @TableField("shopEffeRangeStart")
    private Date shopefferangestart;

    /** 有效期(结束) */
    @TableField("shopEffeRangeEnd")
    private Date shopefferangeend;

    /** 是否异常数据 */
    @TableField("shopDataState")
    private String shopdatastate;

    /** 组织编码 */
    @TableField("shopOrgCode")
    private String shoporgcode;

    /** 账号同步状态 */
    @TableField("shopSyncState")
    private String shopsyncstate;

    /** 账号同步时间 */
    @TableField("shopSyncTime")
    private Date shopsynctime;

    /** 店铺创建时间 */
    @TableField("shopCreateTime")
    private Date shopcreatetime;

    /** 店铺状态操作人编号 */
    @TableField("shopStateUpdatePersonCode")
    private String shopstateupdatepersoncode;

    /** 店铺状态操作人 */
    @TableField("shopStateUpdatePersonName")
    private String shopstateupdatepersonname;

    /** 店铺状态更新时间 */
    @TableField("shopStateUpdateTime")
    private Date shopstateupdatetime;

    /** 店铺API
0 未授权
1 授权正常
2 授权失效 */
    @TableField("authStatus")
    private Integer authstatus;

    @TableField("shopRoutingNumber")
    private String shoproutingnumber;

    @TableField("shopIBAN")
    private String shopiban;

    @TableField("elePlatformName")
    private String eleplatformname;

    @TableField("shopComAddrCN")
    private String shopcomaddrcn;

    @TableField("AWSAccessKeyID")
    private String awsaccesskeyid;

    @TableField("shopBenMail1")
    private String shopbenmail1;

    @TableField("shopComCity")
    private String shopcomcity;

    @TableField("shopBranchCode")
    private String shopbranchcode;

    @TableField("shopState")
    private String shopstate;

    @TableField("shopNamePlat")
    private String shopnameplat;

    @TableField("shopComDistrict")
    private String shopcomdistrict;

    @TableField("shopComNameEn")
    private String shopcomnameen;

    private String remark;

    @TableField("shopShipAddrEn2")
    private String shopshipaddren2;

    @TableField("shopRemLogAddress")
    private String shopremlogaddress;

    @TableField("countryCode")
    private String countrycode;

    @TableField("shopComRegCountry")
    private String shopcomregcountry;

    @TableField("shopMail")
    private String shopmail;

    @TableField("shopColCurrency")
    private String shopcolcurrency;

    @TableField("shopSortCode")
    private String shopsortcode;

    @TableField("shopComNameCN")
    private String shopcomnamecn;

    @TableField("shopIsExtPay")
    private String shopisextpay;

    @TableField("MarketplaceID")
    private String marketplaceid;

    @TableField("shopLegPersonEN")
    private String shoplegpersonen;

    @TableField("shopComAddrEn2")
    private String shopcomaddren2;

    @TableField("shopBanKCode")
    private String shopbankcode;

    @TableField("shopColAccNo")
    private String shopcolaccno;

    @TableField("shopMainBody")
    private String shopmainbody;

    @TableField("SellerID")
    private String sellerid;

    @TableField("shopTelephone")
    private String shoptelephone;

    @TableField("shopComState")
    private String shopcomstate;

    @TableField("shopBIC")
    private String shopbic;

    @TableField("shopNameSimple")
    private String shopnamesimple;

    private String identification;

    @TableField("shopLegPersonCN")
    private String shoplegpersoncn;

    @TableField("CID")
    private String cid;

    @TableField("shopColAccEmail")
    private String shopcolaccemail;

    @TableField("shopAccountType")
    private String shopaccounttype;

   @TableId(value = "shopName", type = IdType.AUTO)
    private String shopname;

    @TableField("shopComPosCode")
    private String shopcomposcode;

    @TableField("shopComAddrEn1")
    private String shopcomaddren1;

    @TableField("shopBSB")
    private String shopbsb;

    @TableField("shopShipAddrEn1")
    private String shopshipaddren1;

    @TableField("shopColAccBank")
    private String shopcolaccbank;

    @TableField("shopMailType")
    private String shopmailtype;

    @TableField("shopBenMail2")
    private String shopbenmail2;

    @TableField("shopComCountry")
    private String shopcomcountry;

}