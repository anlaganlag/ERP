package com.tadpole.cloud.operationManagement.api.brand.model.params;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
* 商标证书管理表
* </p>
* @author S20190161
* @since 2023-10-24
*/
@Data
public class TbTrademarkCertificateUpdateParam {
    @TableId(value = "ID", type = IdType.AUTO)
    @NotNull(message = "主键ID不能为空")
    private Long id;
/*

    @ApiModelProperty("商标名称")
    @NotNull(message = "商标名称不能为空")
    private String tradeName;

    @ApiModelProperty("商标类型：0.文字商标;1.图形商标")
    private BigDecimal trademarkType;

    @ApiModelProperty("注册号")
    private String registerNumber;

    @ApiModelProperty("注册国家")
    private String registerCountry;

    @ApiModelProperty("商标大类--取值于字典名称-【商标大类】")
    private String trademarkCategory;

    @ApiModelProperty("商标小类")
    private String trademarkSubClass;

    @ApiModelProperty("商标权人 -注册公司")
    private String trademarkOwner;

    @ApiModelProperty("证书文件")
    private String categoryFile;

    @ApiModelProperty("证书文件名称")
    private String categoryFileName;

    @ApiModelProperty("有效期start")
    private Date trademarkValidityTermStart;

    @ApiModelProperty("有效期end")
    private Date trademarkValidityTermEnd;

    @ApiModelProperty("保管部门")
    private String custodyDepartment;

    @ApiModelProperty("资产编码")
    private String assetsNo;

    @ApiModelProperty("交接日期")
    private Date handoverDate;

    @ApiModelProperty("交接状态:0.待交接;1.待确认")
    private BigDecimal handoverStatus;

    @ApiModelProperty("提醒倒计时")
    private BigDecimal reminderCountdown;
    */


    @ApiModelProperty("补贴-是否可申请资助")
    @NotNull(message = "是否可申请资助不能为空")
    private Integer subsidyApply;

    @ApiModelProperty("补贴-资产编号")
    @NotNull(message = "资产编号不能为空")
    private String subsidyAssetNumber;

    @ApiModelProperty("补贴-资助资金")
    @NotNull(message = "资助资金不能为空")
    private BigDecimal subsidySupportAmount;

    @ApiModelProperty("补贴-到款日期")
    @NotNull(message = "到款日期不能为空")
    private Date subsidyDatePayment;

    @ApiModelProperty("补贴-资助情况")
    @NotNull(message = "资助情况不能为空")
    private String subsidyFinancing;

    @ApiModelProperty("补贴-办理日期")
    @NotNull(message = "办理日期不能为空")
    private Date subsidyHandleDate;

    @ApiModelProperty("补贴-办理人名称")
    @NotNull(message = "办理人名称不能为空")
    private String subsidyHandlePerName;


    @ApiModelProperty("延期-商标有效期开始")
    private Date renewalValidityTrademarkStart;

    @ApiModelProperty("延期-商标有效期结束")
    private Date renewalValidityTrademarkEnd;

    @ApiModelProperty("延期-续展文件")
    private String renewalFile;

    @ApiModelProperty("延期-续展费用")
    private BigDecimal renewalAmount;

    @ApiModelProperty("备注说明")
    private String remark;


    @ApiModelProperty("延期-续展文件")
    private String renewals;


}
