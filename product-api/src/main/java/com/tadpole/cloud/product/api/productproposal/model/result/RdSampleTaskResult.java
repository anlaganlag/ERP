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
 * 提案-开发样任务 出参类
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
@TableName("RD_SAMPLE_TASK")
public class RdSampleTaskResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "SYS_TS_TASK_CODE", type = IdType.ASSIGN_ID)
    private String sysTsTaskCode;


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


    @ApiModelProperty("系统信息-拿样任务状态 值域{'待发布','拿样中','关闭'}")
    private String sysTsTaskStatus;


    @ApiModelProperty("系统信息-拿样任务进度 值域{'待发布','采购反馈中','定制申请中','合同处理中','待审批','拿样中','已关闭'}")
    private String sysTsTaskProgress;


    @ApiModelProperty("单据联系-产品线编号")
    private String sysPlCode;


    @ApiModelProperty("单据联系-SPU")
    private String sysSpu;


    @ApiModelProperty("单据联系-提案编号")
    private String sysTaCode;


    @ApiModelProperty("设定信息-拿样任务名称")
    private String sysTsTaskName;


    @ApiModelProperty("设定信息-拿样方式 值域{'现货拿样','定制拿样'}")
    private String sysTsSampleMethod;


    @ApiModelProperty("设定信息-反馈截止时间")
    private Date sysTsFebkDeadline;


    @ApiModelProperty("设定信息-任务截止时间")
    private Date sysTsDeadline;


    @ApiModelProperty("要求信息-款式要求")
    private String sysTsStyleReq;


    @ApiModelProperty("要求信息-适用品牌或对象要求")
    private String sysTsBrandReq;


    @ApiModelProperty("要求信息-材质要求")
    private String sysTsMaterialReq;


    @ApiModelProperty("要求信息-图案要求")
    private String sysTsPatternReq;


    @ApiModelProperty("要求信息-颜色要求")
    private String sysTsColorReq;


    @ApiModelProperty("要求信息-尺寸要求")
    private String sysTsSizeReq;


    @ApiModelProperty("要求信息-重量要求")
    private String sysTsWeightReq;


    @ApiModelProperty("要求信息-包装数量要求")
    private String sysTsPackQtyReq;


    @ApiModelProperty("要求信息-功能要求")
    private String sysTsFunctionReq;


    @ApiModelProperty("要求信息-配件要求")
    private String sysTsPartsReq;


    @ApiModelProperty("要求信息-包装要求")
    private String sysTsPackReq;


    @ApiModelProperty("要求信息-合规要求")
    private String sysTsComplianceReq;


    @ApiModelProperty("要求信息-认证要求")
    private String sysTsCertificationReq;


    @ApiModelProperty("要求信息-设计文档")
    private String sysTsDesignDoc;


    @ApiModelProperty("要求信息-底线采购价")
    private BigDecimal sysTsBotLinePurPrice;


    @ApiModelProperty("要求信息-补充说明")
    private String sysTsFootnote;


    @ApiModelProperty("要求信息-发布时间")
    private Date sysTsRelieaseDate;


    @ApiModelProperty("要求信息-提交员工姓名")
    private String sysTsSubPerName;


    @ApiModelProperty("要求信息-提交员工编号")
    private String sysTsSubPerCode;


    @ApiModelProperty("系统追加-拿样超时时长(天)")
    private BigDecimal sysTsSampTod;


    @ApiModelProperty("关闭信息-关闭方式 值域{'','自动关闭','手动关闭'}")
    private String sysTsCloseType;


    @ApiModelProperty("关闭信息-关闭原因")
    private String sysTsCloseReason;


    @ApiModelProperty("关闭信息-关闭时间")
    private Date sysTsCloseDate;


    @ApiModelProperty("关闭信息-关闭员工姓名")
    private String sysTsClosePerName;

    @ApiModelProperty("关闭信息-关闭员工编号")
    private String sysTsClosePerCode;

    @ApiModelProperty("设定信息-任务实际完成时间")
    private Date sysTsActCompleteDate;

    @ApiModelProperty("要求信息-Logo设计位置")
    private String sysTsLogoDesPos;

    @ApiModelProperty("设定信息-指定品牌")
    private String sysTsDevBrand;

    @ApiModelProperty("样品信息-登记时间")
    private Date sysKfySubDate;

    /** 系统信息-消息已读人 */
    @ApiModelProperty("系统信息-消息已读人")
    private String sysTsRead;
}
