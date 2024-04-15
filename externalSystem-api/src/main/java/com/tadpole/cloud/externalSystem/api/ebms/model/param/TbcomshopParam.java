package com.tadpole.cloud.externalSystem.api.ebms.model.param;

import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;

/**
 * <p>
 *  入参类
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
public class TbcomshopParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 店铺收款银行主体 */
    @ApiModelProperty("店铺收款银行主体")
    private String shopcolaccbankmainbody;

    /** 收款银行备注 */
    @ApiModelProperty("收款银行备注")
    private String shopcolaccbankremark;

    /** 账户持有人 */
    @ApiModelProperty("账户持有人")
    private String shopaccountholdname;

    /** 店铺扣款信用卡号 */
    @ApiModelProperty("店铺扣款信用卡号")
    private String shopaccountno;

    /** 信用卡开户行 */
    @ApiModelProperty("信用卡开户行")
    private String shopbankname;

    /** 有效期(开始) */
    @ApiModelProperty("有效期(开始)")
    private Date shopefferangestart;

    /** 有效期(结束) */
    @ApiModelProperty("有效期(结束)")
    private Date shopefferangeend;

    /** 是否异常数据 */
    @ApiModelProperty("是否异常数据")
    private String shopdatastate;

    /** 组织编码 */
    @ApiModelProperty("组织编码")
    private String shoporgcode;

    /** 账号同步状态 */
    @ApiModelProperty("账号同步状态")
    private String shopsyncstate;

    /** 账号同步时间 */
    @ApiModelProperty("账号同步时间")
    private Date shopsynctime;

    /** 店铺创建时间 */
    @ApiModelProperty("店铺创建时间")
    private Date shopcreatetime;

    /** 店铺状态操作人编号 */
    @ApiModelProperty("店铺状态操作人编号")
    private String shopstateupdatepersoncode;

    /** 店铺状态操作人 */
    @ApiModelProperty("店铺状态操作人")
    private String shopstateupdatepersonname;

    /** 店铺状态更新时间 */
    @ApiModelProperty("店铺状态更新时间")
    private Date shopstateupdatetime;

    /** 店铺API
0 未授权
1 授权正常
2 授权失效 */
    @ApiModelProperty("店铺API ")
    private Integer authstatus;

    @ApiModelProperty("")
    private String shoproutingnumber;

    @ApiModelProperty("")
    private String shopiban;

    @ApiModelProperty("")
    private String eleplatformname;

    @ApiModelProperty("")
    private String shopcomaddrcn;

    @ApiModelProperty("")
    private String awsaccesskeyid;

    @ApiModelProperty("")
    private String shopbenmail1;

    @ApiModelProperty("")
    private String shopcomcity;

    @ApiModelProperty("")
    private String shopbranchcode;

    @ApiModelProperty("")
    private String shopstate;

    @ApiModelProperty("")
    private String shopnameplat;

    @ApiModelProperty("")
    private String shopcomdistrict;

    @ApiModelProperty("")
    private String shopcomnameen;

    private String remark;

    @ApiModelProperty("")
    private String shopshipaddren2;

    @ApiModelProperty("")
    private String shopremlogaddress;

    @ApiModelProperty("")
    private String countrycode;

    @ApiModelProperty("")
    private String shopcomregcountry;

    @ApiModelProperty("")
    private String shopmail;

    @ApiModelProperty("")
    private String shopcolcurrency;

    @ApiModelProperty("")
    private String shopsortcode;

    @ApiModelProperty("")
    private String shopcomnamecn;

    @ApiModelProperty("")
    private String shopisextpay;

    @ApiModelProperty("")
    private String marketplaceid;

    @ApiModelProperty("")
    private String shoplegpersonen;

    @ApiModelProperty("")
    private String shopcomaddren2;

    @ApiModelProperty("")
    private String shopbankcode;

    @ApiModelProperty("")
    private String shopcolaccno;

    @ApiModelProperty("")
    private String shopmainbody;

    @ApiModelProperty("")
    private String sellerid;

    @ApiModelProperty("")
    private String shoptelephone;

    @ApiModelProperty("")
    private String shopcomstate;

    @ApiModelProperty("")
    private String shopbic;

    @ApiModelProperty("")
    private String shopnamesimple;

    private String identification;

    @ApiModelProperty("")
    private String shoplegpersoncn;

    @ApiModelProperty("")
    private String cid;

    @ApiModelProperty("")
    private String shopcolaccemail;

    @ApiModelProperty("")
    private String shopaccounttype;

   @TableId(value = "shopName", type = IdType.AUTO)
    private String shopname;

    @ApiModelProperty("")
    private String shopcomposcode;

    @ApiModelProperty("")
    private String shopcomaddren1;

    @ApiModelProperty("")
    private String shopbsb;

    @ApiModelProperty("")
    private String shopshipaddren1;

    @ApiModelProperty("")
    private String shopcolaccbank;

    @ApiModelProperty("")
    private String shopmailtype;

    @ApiModelProperty("")
    private String shopbenmail2;

    @ApiModelProperty("")
    private String shopcomcountry;

}
