package com.tadpole.cloud.product.api.productproposal.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tadpole.cloud.product.api.productplan.model.result.RdPreProposalUpResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 提案-提案管理 出参类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("RD_PROPOSAL")
public class RdProposalExtentResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    @TableId(value = "SYS_TA_CODE", type = IdType.ASSIGN_ID)
    private String sysTaCode;


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


    @ApiModelProperty("系统信息-提案状态 值域{'新提案','已归档'}")
    private String sysTaStatus;


    @ApiModelProperty("系统信息-提案进度 值域{'设计中','拿样中','定品中','已归档'}")
    private String sysTaProcess;


    @ApiModelProperty("单据联系-产品线编号")
    private String sysPlCode;


    @ApiModelProperty("单据联系-SPU")
    private String sysSpu;


    @ApiModelProperty("单据联系-提案来源")
    private String sysTaSource;


    @ApiModelProperty("单据联系-提案来源编号")
    private String sysTaSourceId;


    @ApiModelProperty("立项信息-开发方式")
    private String sysDevMethond;


    @ApiModelProperty("立项信息-老品产品定义书编号")
    private String sysOldPdNum;


    @ApiModelProperty("立项信息-老品产品定义书版本")
    private String sysOldPdVersion;


    @ApiModelProperty("立项信息-版本")
    private String sysVersion;


    @ApiModelProperty("立项信息-上架时间要求")
    private String sysTaLtrDate;


    @ApiModelProperty("立项信息-提案等级 值域{'S','A','B','C','D'}")
    private String sysTaLevel;


    @ApiModelProperty("立项信息-提案提案立项日期")
    private Date sysTaPaDate;


    @ApiModelProperty("调研信息-调研分析文档")
    private String sysTaSurveyDoc;


    @ApiModelProperty("调研信息-季节标签")
    private String sysTaSeasonLabel;


    @ApiModelProperty("调研信息-节日标签")
    private String sysTaFestivalLabel;


    @ApiModelProperty("调研信息-主攻市场 值域{'','北美','欧洲','英国','日本'}")
    private String sysTaMainMarket;


    @ApiModelProperty("产品设计信息-使用场景")
    private String sysTaUsageScenario;


    @ApiModelProperty("产品设计信息-使用人群")
    private String sysTaUserGroup;


    @ApiModelProperty("产品设计信息-卖点说明")
    private String sysTaSellPointDesc;


    @ApiModelProperty("销售规划信息-销售规划文档")
    private String sysTaSpDoc;


    @ApiModelProperty("系统追加-累积研发费")
    private BigDecimal sysTaAccuRdFee;


    @ApiModelProperty("系统追加-累积拿样超时(天)")
    private BigDecimal sysTaAccuSampTod;


    @ApiModelProperty("归档信息-归档类型 值域{'正常完结','利润低','无合适样品','误操作','其它'}")
    private String sysTaArchType;


    @ApiModelProperty("归档信息-归档备注")
    private String sysTaArchRemarks;


    @ApiModelProperty("归档信息-归档时间")
    private Date sysTaArchDate;


    @ApiModelProperty("归档信息-归档员工姓名")
    private String sysTaArchPerName;


    @ApiModelProperty("归档信息-归档员工编号")
    private String sysTaArchPerCode;

    @ApiModelProperty("立项信息-开发品牌")
    private String sysTaDevBrand;

    @ApiModelProperty("立项信息-Logo设计位置")
    private String sysTaLogoDesPos;

    @ApiModelProperty("立项信息-包装设计方式")
    private String sysTaPackDesMethod;

    @ApiModelProperty("立项信息-产品经理编号")
    private String sysPmPerCode;


    @ApiModelProperty("立项信息-产品经理姓名")
    private String sysPmPerName;

    /** 产品线设定-产品线名称 */
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

    /** 预案公共-产品名称 */
    @ApiModelProperty("预案公共-产品名称")
    private String sysProName;

    /** 预案公共-款式 */
    @ApiModelProperty("预案公共-款式")
    private String sysStyle;

    /** 预案公共-适用品牌或对象 */
    @ApiModelProperty("预案公共-适用品牌或对象")
    private String sysBrand;

    /** 预案公共-主材料 */
    @ApiModelProperty("预案公共-主材料")
    private String sysMainMaterial;

    /** 预案公共-型号 */
    @ApiModelProperty("预案公共-型号")
    private String sysModel;

    /** 改良信息 */
    @ApiModelProperty("改良信息")
    private List<RdPreProposalUpResult> rdPreProposalUpResultList;

    @ApiModelProperty("预案拓新-拓新类型")
    private String sysNewType;

    @ApiModelProperty("预案拓新-品牌拓新需求")
    private String sysBrandNewDemand;

    @ApiModelProperty("预案拓新-图案拓新需求")
    private String sysPatternNewDemand;

    @ApiModelProperty("预案拓新-图案拓新需求参考图片")
    private String sysPatternNewDemandPic;

    @ApiModelProperty("预案拓新-颜色拓新需求")
    private String sysColorNewDemand;

    @ApiModelProperty("预案拓新-颜色拓新需求参考图片")
    private String sysColorNewDemandPic;

    @ApiModelProperty("预案拓新-规格拓新需求 格式：长*宽*高")
    private String sysNormsNewDemand;

    @ApiModelProperty("预案拓新-包装数量拓新需求")
    private String sysPackageQtyNewDemand;

    @ApiModelProperty("预案反馈-首单采购数量评估")
    private BigDecimal sysFebkFirstOrderQtyEva;

    @ApiModelProperty("预案反馈-首单采购金额预估")
    private BigDecimal sysFebkFirstOrderAmounEva;

    @ApiModelProperty("预案反馈-提案等级")
    private String sysFebkProposalLevel;

    @ApiModelProperty("预案系统-预案编号")
    private String sysYaCode;

    /** 产品设计文档信息 */
    @ApiModelProperty("产品设计文档信息")
    private List<RdProposalDocResult> rdProposalDocResultList;

    @ApiModelProperty("提案信息-是否有效提案 值域{'是','否'}")
    private String sysIsValidProposal;

}
