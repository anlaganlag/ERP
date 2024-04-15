package com.tadpole.cloud.product.api.productproposal.model.result;

import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.tadpole.cloud.product.api.productproposal.entity.RdSampleManage;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 提案-退货款记录 出参类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("RD_SAMPLE_RPR")
public class RdSampleRprResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "SYS_REF_CODE", type = IdType.ASSIGN_ID)
    private String sysRefCode;


    @ApiModelProperty("系统信息-创建时间")
    private Date sysCDate;


    @ApiModelProperty("系统信息-最后更新时间")
    private Date sysLDate;


    @ApiModelProperty("系统信息-部门编号")
    private String sysDeptCode;


    @ApiModelProperty("系统信息-部门名称")
    private String sysDeptName;


    @ApiModelProperty("系统信息-员工编号")
    private String sysPerCode;


    @ApiModelProperty("系统信息-员工姓名")
    private String sysPerName;


    @ApiModelProperty("退货申请信息-退货操作时间")
    private Date sysRefOpDate;


    @ApiModelProperty("退货申请信息-退货数量")
    private BigDecimal sysRefQty;


    @ApiModelProperty("退货申请信息-预计退款金额")
    private BigDecimal sysRefPreAmount;


    @ApiModelProperty("单据联系-产品线编号")
    private String sysPlCode;


    @ApiModelProperty("单据联系-SPU")
    private String sysSpu;


    @ApiModelProperty("单据联系-提案编号")
    private String sysTaCode;


    @ApiModelProperty("单据联系-拿样任务编号")
    private String sysTsTaskCode;


    @ApiModelProperty("单据联系-费用申请编号")
    private String sysFeeAppCode;


    @ApiModelProperty("单据联系-费用申请来源 值域{'购样申请','定制申请'}")
    private String sysFeeAppSource;


    @ApiModelProperty("退款信息-差额说明")
    private String sysRefVd;


    @ApiModelProperty("退款确认信息-支付宝账户")
    private String sysRefAlipayA;


    @ApiModelProperty("退款确认信息-支付宝账户户名")
    private String sysRefAlipayAn;


    @ApiModelProperty("退款确认信息-退款日期")
    private Date sysRefDate;


    @ApiModelProperty("退款确认信息-实际退款金额")
    private BigDecimal sysRefRealAmount;


    @ApiModelProperty("退款确认信息-退款登记时间")
    private Date sysRefRgDate;


    @ApiModelProperty("退款确认信息-退款确认人编号")
    private String sysRefRgPerCode;


    @ApiModelProperty("退款确认信息-退款确认人姓名")
    private String sysRefRgPerName;

    @ApiModelProperty("申请信息-拿样渠道 值域{'供应商','1688网站','淘宝网站'}")
    private String sysFeeAppSc;

    @ApiModelProperty("申请信息-店铺名称")
    private String sysFeeAppShopName;

    @ApiModelProperty("申请信息-商品购买页面")
    private String sysFeeAppProPurPage;

    @ApiModelProperty("申请信息-补充信息-订单号")
    private String sysFeeAppOrderNum;

    @ApiModelProperty("申请信息-补充信息-订单截图")
    private String sysFeeAppOrderPic;

    @ApiModelProperty("支付信息-支付宝账户")
    private String sysFeeAppAlipayAccount;

    @ApiModelProperty("支付信息-支付宝账户户名")
    private String sysFeeAppAlipayAn;

    @ApiModelProperty("退货清单")
    private List<RdSampleManage> sampleManageList;

    @ApiModelProperty("申请信息-采购负责人姓名")
    private String sysFeeAppPurPerName;

}
