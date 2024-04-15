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
 * 提案-开发样任务 实体类
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
@TableName("RD_SAMPLE_TASK")
@ExcelIgnoreUnannotated
public class RdSampleTask implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统信息-拿样任务编号 */
   @TableId(value = "SYS_TS_TASK_CODE", type = IdType.ASSIGN_ID)
    private String sysTsTaskCode;

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

    /** 系统信息-拿样任务状态 值域{"待发布","拿样中","关闭"} */
    @TableField("SYS_TS_TASK_STATUS")
    private String sysTsTaskStatus;

    /** 系统信息-拿样任务进度 值域{"待发布","采购反馈中","定制申请中","合同处理中","待审批","拿样中","已关闭"} */
    @TableField("SYS_TS_TASK_PROGRESS")
    private String sysTsTaskProgress;

    /** 单据联系-产品线编号 */
    @TableField("SYS_PL_CODE")
    private String sysPlCode;

    /** 单据联系-SPU */
    @TableField("SYS_SPU")
    private String sysSpu;

    /** 单据联系-提案编号 */
    @TableField("SYS_TA_CODE")
    private String sysTaCode;

    /** 设定信息-拿样任务名称 */
    @TableField("SYS_TS_TASK_NAME")
    private String sysTsTaskName;

    /** 设定信息-拿样方式 值域{"现货拿样","定制拿样"} */
    @TableField("SYS_TS_SAMPLE_METHOD")
    private String sysTsSampleMethod;

    /** 设定信息-反馈截止时间 */
    @TableField("SYS_TS_FEBK_DEADLINE")
    private Date sysTsFebkDeadline;

    /** 设定信息-任务截止时间 */
    @TableField("SYS_TS_DEADLINE")
    private Date sysTsDeadline;

    /** 要求信息-款式要求 */
    @TableField("SYS_TS_STYLE_REQ")
    private String sysTsStyleReq;

    /** 要求信息-适用品牌或对象要求 */
    @TableField("SYS_TS_BRAND_REQ")
    private String sysTsBrandReq;

    /** 要求信息-材质要求 */
    @TableField("SYS_TS_MATERIAL_REQ")
    private String sysTsMaterialReq;

    /** 要求信息-图案要求 */
    @TableField("SYS_TS_PATTERN_REQ")
    private String sysTsPatternReq;

    /** 要求信息-颜色要求 */
    @TableField("SYS_TS_COLOR_REQ")
    private String sysTsColorReq;

    /** 要求信息-尺寸要求 */
    @TableField("SYS_TS_SIZE_REQ")
    private String sysTsSizeReq;

    /** 要求信息-重量要求 */
    @TableField("SYS_TS_WEIGHT_REQ")
    private String sysTsWeightReq;

    /** 要求信息-包装数量要求 */
    @TableField("SYS_TS_PACK_QTY_REQ")
    private String sysTsPackQtyReq;

    /** 要求信息-功能要求 */
    @TableField("SYS_TS_FUNCTION_REQ")
    private String sysTsFunctionReq;

    /** 要求信息-配件要求 */
    @TableField("SYS_TS_PARTS_REQ")
    private String sysTsPartsReq;

    /** 要求信息-包装要求 */
    @TableField("SYS_TS_PACK_REQ")
    private String sysTsPackReq;

    /** 要求信息-合规要求 */
    @TableField("SYS_TS_COMPLIANCE_REQ")
    private String sysTsComplianceReq;

    /** 要求信息-认证要求 */
    @TableField("SYS_TS_CERTIFICATION_REQ")
    private String sysTsCertificationReq;

    /** 要求信息-设计文档 */
    @TableField("SYS_TS_DESIGN_DOC")
    private String sysTsDesignDoc;

    /** 要求信息-底线采购价 */
    @TableField("SYS_TS_BOT_LINE_PUR_PRICE")
    private BigDecimal sysTsBotLinePurPrice;

    /** 要求信息-补充说明 */
    @TableField("SYS_TS_FOOTNOTE")
    private String sysTsFootnote;

    /** 要求信息-发布时间 */
    @TableField("SYS_TS_RELIEASE_DATE")
    private Date sysTsRelieaseDate;

    /** 要求信息-提交员工姓名 */
    @TableField("SYS_TS_SUB_PER_NAME")
    private String sysTsSubPerName;

    /** 要求信息-提交员工编号 */
    @TableField("SYS_TS_SUB_PER_CODE")
    private String sysTsSubPerCode;

    /** 系统追加-拿样超时时长(天) */
    @TableField("SYS_TS_SAMP_TOD")
    private BigDecimal sysTsSampTod;

    /** 关闭信息-关闭方式 值域{"","自动关闭","手动关闭"} */
    @TableField("SYS_TS_CLOSE_TYPE")
    private String sysTsCloseType;

    /** 关闭信息-关闭原因 */
    @TableField("SYS_TS_CLOSE_REASON")
    private String sysTsCloseReason;

    /** 关闭信息-关闭时间 */
    @TableField("SYS_TS_CLOSE_DATE")
    private Date sysTsCloseDate;

    /** 关闭信息-关闭员工姓名 */
    @TableField("SYS_TS_CLOSE_PER_NAME")
    private String sysTsClosePerName;

    /** 关闭信息-关闭员工编号 */
    @TableField("SYS_TS_CLOSE_PER_CODE")
    private String sysTsClosePerCode;

    /** 设定信息-任务实际完成时间 */
    @TableField("SYS_TS_ACT_COMPLETE_DATE")
    private Date sysTsActCompleteDate;

    /** 要求信息-Logo设计位置 值域{"产品主体","产品包装"} */
    @TableField("SYS_TS_LOGO_DES_POS")
    private String sysTsLogoDesPos;

    /** 设定信息-指定品牌 */
    @TableField("SYS_TS_DEV_BRAND")
    private String sysTsDevBrand;

    /** 系统信息-消息已读人 */
    @TableField("SYS_TS_READ")
    private String sysTsRead;
}