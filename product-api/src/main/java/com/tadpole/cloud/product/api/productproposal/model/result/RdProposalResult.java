package com.tadpole.cloud.product.api.productproposal.model.result;

import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
public class RdProposalResult implements Serializable, BaseValidatingParam {

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

}
