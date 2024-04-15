package com.tadpole.cloud.product.api.productproposal.model.params;

import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;

/**
 * <p>
 * 提案-提案管理 入参类
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
@TableName("RD_PROPOSAL")
public class RdProposalParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** TA-[年](4位)[月](2位)[流水号](3位) */
   @TableId(value = "SYS_TA_CODE", type = IdType.ASSIGN_ID)
    private String sysTaCode;

    /** 系统信息-创建时间 */
    @ApiModelProperty("系统信息-创建时间")
    private Date sysCDate;

    /** 系统信息-最后更新时间 */
    @ApiModelProperty("系统信息-最后更新时间")
    private Date sysLDate;

    /** 系统信息-部门编号 */
    @ApiModelProperty("系统信息-部门编号")
    private String sysDeptCode;

    /** 系统信息-部门名称 */
    @ApiModelProperty("系统信息-部门名称")
    private String sysDeptName;

    /** 系统信息-员工编号 */
    @ApiModelProperty("系统信息-员工编号")
    private String sysPerCode;

    /** 系统信息-员工姓名 */
    @ApiModelProperty("系统信息-员工姓名")
    private String sysPerName;

    /** 系统信息-提案状态 值域{"新提案","已归档"} */
    @ApiModelProperty("系统信息-提案状态 值域{'新提案','已归档'}")
    private String sysTaStatus;

    /** 系统信息-提案进度 值域{“设计中”,"拿样中","定品中","已归档"} */
    @ApiModelProperty("系统信息-提案进度 值域{'设计中','拿样中','定品中','已归档'}")
    private String sysTaProcess;

    /** 单据联系-产品线编号 */
    @ApiModelProperty("单据联系-产品线编号")
    private String sysPlCode;

    /** 单据联系-SPU */
    @ApiModelProperty("单据联系-SPU")
    private String sysSpu;

    /** 单据联系-提案来源 */
    @ApiModelProperty("单据联系-提案来源")
    private String sysTaSource;

    /** 单据联系-提案来源编号 */
    @ApiModelProperty("单据联系-提案来源编号")
    private String sysTaSourceId;

    /** 立项信息-开发方式 */
    @ApiModelProperty("立项信息-开发方式")
    private String sysDevMethond;

    /** 立项信息-老品产品定义书编号 */
    @ApiModelProperty("立项信息-老品产品定义书编号")
    private String sysOldPdNum;

    /** 立项信息-老品产品定义书版本 */
    @ApiModelProperty("立项信息-老品产品定义书版本")
    private String sysOldPdVersion;

    /** 立项信息-版本 */
    @ApiModelProperty("立项信息-版本")
    private String sysVersion;

    /** 立项信息-上架时间要求 */
    @ApiModelProperty("立项信息-上架时间要求")
    private String sysTaLtrDate;

    /** 立项信息-提案等级 值域{"S","A","B","C","D"} */
    @ApiModelProperty("立项信息-提案等级 值域{'S','A','B','C','D'}")
    private String sysTaLevel;

    /** 立项信息-提案提案立项日期 */
    @ApiModelProperty("立项信息-提案提案立项日期")
    private Date sysTaPaDate;

    /** 调研信息-调研分析文档 */
    @ApiModelProperty("调研信息-调研分析文档")
    private String sysTaSurveyDoc;

    /** 调研信息-季节标签 */
    @ApiModelProperty("调研信息-季节标签")
    private String sysTaSeasonLabel;

    /** 调研信息-节日标签 */
    @ApiModelProperty("调研信息-节日标签")
    private String sysTaFestivalLabel;

    /** 调研信息-主攻市场 值域{"","北美","欧洲","英国","日本"} */
    @ApiModelProperty("调研信息-主攻市场 值域{'','北美','欧洲','英国','日本'}")
    private String sysTaMainMarket;

    /** 产品设计信息-使用场景 */
    @ApiModelProperty("产品设计信息-使用场景")
    private String sysTaUsageScenario;

    /** 产品设计信息-使用人群 */
    @ApiModelProperty("产品设计信息-使用人群")
    private String sysTaUserGroup;

    /** 产品设计信息-卖点说明 */
    @ApiModelProperty("产品设计信息-卖点说明")
    private String sysTaSellPointDesc;

    /** 销售规划信息-销售规划文档 */
    @ApiModelProperty("销售规划信息-销售规划文档")
    private String sysTaSpDoc;

    /** 系统追加-累积研发费 */
    @ApiModelProperty("系统追加-累积研发费")
    private BigDecimal sysTaAccuRdFee;

    /** 系统追加-累积拿样超时(天) */
    @ApiModelProperty("系统追加-累积拿样超时(天)")
    private BigDecimal sysTaAccuSampTod;

    /** 归档信息-归档类型 值域{"正常完结","利润低","无合适样品","误操作","其它"} */
    @ApiModelProperty("归档信息-归档类型 值域{'正常完结','利润低','无合适样品','误操作','其它'}")
    private String sysTaArchType;

    /** 归档信息-归档备注 */
    @ApiModelProperty("归档信息-归档备注")
    private String sysTaArchRemarks;

    /** 归档信息-归档时间 */
    @ApiModelProperty("归档信息-归档时间")
    private Date sysTaArchDate;

    /** 归档信息-归档员工姓名 */
    @ApiModelProperty("归档信息-归档员工姓名")
    private String sysTaArchPerName;

    /** 归档信息-归档员工编号 */
    @ApiModelProperty("归档信息-归档员工编号")
    private String sysTaArchPerCode;

    /** 界面操作-新增/编辑 */
    @ApiModelProperty("界面操作-新增/编辑")
    private String sysPageOpr;

}
