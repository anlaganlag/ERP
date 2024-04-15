package com.tadpole.cloud.product.api.productproposal.model.result;

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
import java.util.List;

/**
 * <p>
 * 提案-定制申请 出参类
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
public class RdSampleApproveResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("单据联系-产品线编号")
    private String sysPlCode;

    @ApiModelProperty("单据联系-SPU")
    private String sysSpu;

    @ApiModelProperty("单据联系-提案编号")
    private String sysTaCode;

    @ApiModelProperty("立项信息-产品经理编号")
    private String sysPmPerCode;

    @ApiModelProperty("立项信息-产品经理姓名")
    private String sysPmPerName;

    @ApiModelProperty("产品线名称")
    private String sysPlName;

    /** 产品线设定-产品线简码 */
    @ApiModelProperty("产品线简码")
    private String sysShortCode;

    /** 产品线设定-团队负责人编码 */
    @ApiModelProperty("产品线设定-团队负责人编码")
    private String sysPlPmPerCode;

    /** 产品线设定-团队负责人名称 */
    @ApiModelProperty("产品线设定-团队负责人名称")
    private String sysPlPmPerName;

    @ApiModelProperty("预案公共-产品名称")
    private String sysProName;

    @ApiModelProperty("预案公共-款式")
    private String sysStyle;

    @ApiModelProperty("预案公共-适用品牌或对象")
    private String sysBrand;

    @ApiModelProperty("预案公共-主材料")
    private String sysMainMaterial;

    @ApiModelProperty("预案公共-型号")
    private String sysModel;

    @ApiModelProperty("立项信息-提案等级 值域{'S','A','B','C','D'}")
    private String sysTaLevel;

    @ApiModelProperty("立项信息-版本")
    private String sysVersion;

    @ApiModelProperty("立项信息-提案提案立项日期")
    private Date sysTaPaDate;

    @ApiModelProperty("产品开发阶段")
    private String sysRdStage;

    @ApiModelProperty("申请费用类型")
    private String sysAppFeeType;

    @ApiModelProperty("购样费")
    private BigDecimal sysAppPaFee;

    @ApiModelProperty("打样费")
    private BigDecimal sysAppCaFee;

    @ApiModelProperty("检测费")
    private BigDecimal sysAppJcFee;

    @ApiModelProperty("耗材费")
    private BigDecimal sysAppHcFee;

    @ApiModelProperty("网版费")
    private BigDecimal sysAppWbFee;

    @ApiModelProperty("开模费")
    private BigDecimal sysAppCaMoFee;

    @ApiModelProperty("研发费合计")
    private BigDecimal sysAppTotalFee;

    @ApiModelProperty("打样申请明细")
    private List<RdSampleCaResult> rdSampleCaResults;

    @ApiModelProperty("开模申请明细")
    private List<RdSampleCaResult> rdSampleCaMoResults;

    @ApiModelProperty("购样申请明细")
    private List<RdSamplePaResult> rdSamplePaResults;

    @ApiModelProperty("网版申请明细")
    private List<RdSamplePaResult> rdSamplePaResults111;

    @ApiModelProperty("检测申请明细")
    private List<RdSamplePaResult> rdSamplePaResults222;

    @ApiModelProperty("耗材申请明细")
    private List<RdSamplePaResult> rdSamplePaResults333;



}
