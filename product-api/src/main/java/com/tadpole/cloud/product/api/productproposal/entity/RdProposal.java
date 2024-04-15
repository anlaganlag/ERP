package com.tadpole.cloud.product.api.productproposal.entity;

import java.math.BigDecimal;
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
 * 提案-提案管理 实体类
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
@ExcelIgnoreUnannotated
public class RdProposal implements Serializable {

    private static final long serialVersionUID = 1L;


    /** TA-[年](4位)[月](2位)[流水号](3位) */
   @TableId(value = "SYS_TA_CODE", type = IdType.ASSIGN_ID)
    private String sysTaCode;

    /** 系统信息-创建时间 */
    @TableField("SYS_C_DATE")
    private Date sysCDate;

    /** 系统信息-最后更新时间 */
    @TableField("SYS_L_DATE")
    private Date sysLDate;

    /** 系统信息-部门编号 */
    @TableField("SYS_DEPT_CODE")
    private String sysDeptCode;

    /** 系统信息-部门名称 */
    @TableField("SYS_DEPT_NAME")
    private String sysDeptName;

    /** 系统信息-员工编号 */
    @TableField("SYS_PER_CODE")
    private String sysPerCode;

    /** 系统信息-员工姓名 */
    @TableField("SYS_PER_NAME")
    private String sysPerName;

    /** 系统信息-提案状态 值域{"新提案","已归档"} */
    @TableField("SYS_TA_STATUS")
    private String sysTaStatus;

    /** 系统信息-提案进度 值域{“设计中”,"拿样中","定品中","已归档"} */
    @TableField("SYS_TA_PROCESS")
    private String sysTaProcess;

    /** 单据联系-产品线编号 */
    @TableField("SYS_PL_CODE")
    private String sysPlCode;

    /** 单据联系-SPU */
    @TableField("SYS_SPU")
    private String sysSpu;

    /** 单据联系-提案来源 */
    @TableField("SYS_TA_SOURCE")
    private String sysTaSource;

    /** 单据联系-提案来源编号 */
    @TableField("SYS_TA_SOURCE_ID")
    private String sysTaSourceId;

    /** 立项信息-开发方式 */
    @TableField("SYS_DEV_METHOND")
    private String sysDevMethond;

    /** 立项信息-老品产品定义书编号 */
    @TableField("SYS_OLD_PD_NUM")
    private String sysOldPdNum;

    /** 立项信息-老品产品定义书版本 */
    @TableField("SYS_OLD_PD_VERSION")
    private String sysOldPdVersion;

    /** 立项信息-版本 */
    @TableField("SYS_VERSION")
    private String sysVersion;

    /** 立项信息-上架时间要求 */
    @TableField("SYS_TA_LTR_DATE")
    private String sysTaLtrDate;

    /** 立项信息-提案等级 值域{"S","A","B","C","D"} */
    @TableField("SYS_TA_LEVEL")
    private String sysTaLevel;

    /** 立项信息-提案提案立项日期 */
    @TableField("SYS_TA_PA_DATE")
    private Date sysTaPaDate;

    /** 调研信息-调研分析文档 */
    @TableField("SYS_TA_SURVEY_DOC")
    private String sysTaSurveyDoc;

    /** 调研信息-季节标签 */
    @TableField("SYS_TA_SEASON_LABEL")
    private String sysTaSeasonLabel;

    /** 调研信息-节日标签 */
    @TableField("SYS_TA_FESTIVAL_LABEL")
    private String sysTaFestivalLabel;

    /** 调研信息-主攻市场 值域{"","北美","欧洲","英国","日本"} */
    @TableField("SYS_TA_MAIN_MARKET")
    private String sysTaMainMarket;

    /** 产品设计信息-使用场景 */
    @TableField("SYS_TA_USAGE_SCENARIO")
    private String sysTaUsageScenario;

    /** 产品设计信息-使用人群 */
    @TableField("SYS_TA_USER_GROUP")
    private String sysTaUserGroup;

    /** 产品设计信息-卖点说明 */
    @TableField("SYS_TA_SELL_POINT_DESC")
    private String sysTaSellPointDesc;

    /** 销售规划信息-销售规划文档 */
    @TableField("SYS_TA_SP_DOC")
    private String sysTaSpDoc;

    /** 系统追加-累积研发费 */
    @TableField("SYS_TA_ACCU_RD_FEE")
    private BigDecimal sysTaAccuRdFee;

    /** 系统追加-累积拿样超时(天) */
    @TableField("SYS_TA_ACCU_SAMP_TOD")
    private BigDecimal sysTaAccuSampTod;

    /** 归档信息-归档类型 值域{"正常完结","利润低","无合适样品","误操作","其它"} */
    @TableField("SYS_TA_ARCH_TYPE")
    private String sysTaArchType;

    /** 归档信息-归档备注 */
    @TableField("SYS_TA_ARCH_REMARKS")
    private String sysTaArchRemarks;

    /** 归档信息-归档时间 */
    @TableField("SYS_TA_ARCH_DATE")
    private Date sysTaArchDate;

    /** 归档信息-归档员工姓名 */
    @TableField("SYS_TA_ARCH_PER_NAME")
    private String sysTaArchPerName;

    /** 归档信息-归档员工编号 */
    @TableField("SYS_TA_ARCH_PER_CODE")
    private String sysTaArchPerCode;

}