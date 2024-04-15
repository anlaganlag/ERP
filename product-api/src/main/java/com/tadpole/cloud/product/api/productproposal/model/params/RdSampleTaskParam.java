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
import java.util.List;

import lombok.*;

/**
 * <p>
 * 提案-开发样任务 入参类
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
public class RdSampleTaskParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统信息-拿样任务编号 */
   @TableId(value = "SYS_TS_TASK_CODE", type = IdType.ASSIGN_ID)
    private String sysTsTaskCode;

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

    /** 系统信息-拿样任务状态 值域{"待发布","拿样中","关闭"} */
    @ApiModelProperty("系统信息-拿样任务状态 值域{'待发布','拿样中','关闭'}")
    private String sysTsTaskStatus;

    /** 系统信息-拿样任务进度 值域{"待发布","采购反馈中","定制申请中","合同处理中","待审批","拿样中","已关闭"} */
    @ApiModelProperty("系统信息-拿样任务进度 值域{'待发布','采购反馈中','定制申请中','合同处理中','待审批','拿样中','已关闭'}")
    private String sysTsTaskProgress;

    /** 单据联系-产品线编号 */
    @ApiModelProperty("单据联系-产品线编号")
    private String sysPlCode;

    /** 单据联系-SPU */
    @ApiModelProperty("单据联系-SPU")
    private String sysSpu;

    /** 单据联系-提案编号 */
    @ApiModelProperty("单据联系-提案编号")
    private String sysTaCode;

    /** 设定信息-拿样任务名称 */
    @ApiModelProperty("设定信息-拿样任务名称")
    private String sysTsTaskName;

    /** 设定信息-拿样方式 值域{"现货拿样","定制拿样"} */
    @ApiModelProperty("设定信息-拿样方式 值域{'现货拿样','定制拿样'}")
    private String sysTsSampleMethod;

    /** 设定信息-反馈截止时间 */
    @ApiModelProperty("设定信息-反馈截止时间")
    private Date sysTsFebkDeadline;

    /** 设定信息-任务截止时间 */
    @ApiModelProperty("设定信息-任务截止时间")
    private Date sysTsDeadline;

    /** 要求信息-款式要求 */
    @ApiModelProperty("要求信息-款式要求")
    private String sysTsStyleReq;

    /** 要求信息-适用品牌或对象要求 */
    @ApiModelProperty("要求信息-适用品牌或对象要求")
    private String sysTsBrandReq;

    /** 要求信息-材质要求 */
    @ApiModelProperty("要求信息-材质要求")
    private String sysTsMaterialReq;

    /** 要求信息-图案要求 */
    @ApiModelProperty("要求信息-图案要求")
    private String sysTsPatternReq;

    /** 要求信息-颜色要求 */
    @ApiModelProperty("要求信息-颜色要求")
    private String sysTsColorReq;

    /** 要求信息-尺寸要求 */
    @ApiModelProperty("要求信息-尺寸要求")
    private String sysTsSizeReq;

    /** 要求信息-重量要求 */
    @ApiModelProperty("要求信息-重量要求")
    private String sysTsWeightReq;

    /** 要求信息-包装数量要求 */
    @ApiModelProperty("要求信息-包装数量要求")
    private String sysTsPackQtyReq;

    /** 要求信息-功能要求 */
    @ApiModelProperty("要求信息-功能要求")
    private String sysTsFunctionReq;

    /** 要求信息-配件要求 */
    @ApiModelProperty("要求信息-配件要求")
    private String sysTsPartsReq;

    /** 要求信息-包装要求 */
    @ApiModelProperty("要求信息-包装要求")
    private String sysTsPackReq;

    /** 要求信息-合规要求 */
    @ApiModelProperty("要求信息-合规要求")
    private String sysTsComplianceReq;

    /** 要求信息-认证要求 */
    @ApiModelProperty("要求信息-认证要求")
    private String sysTsCertificationReq;

    /** 要求信息-设计文档 */
    @ApiModelProperty("要求信息-设计文档")
    private String sysTsDesignDoc;

    /** 要求信息-底线采购价 */
    @ApiModelProperty("要求信息-底线采购价")
    private BigDecimal sysTsBotLinePurPrice;

    /** 要求信息-补充说明 */
    @ApiModelProperty("要求信息-补充说明")
    private String sysTsFootnote;

    /** 要求信息-发布时间 */
    @ApiModelProperty("要求信息-发布时间")
    private Date sysTsRelieaseDate;

    /** 要求信息-提交员工姓名 */
    @ApiModelProperty("要求信息-提交员工姓名")
    private String sysTsSubPerName;

    /** 要求信息-提交员工编号 */
    @ApiModelProperty("要求信息-提交员工编号")
    private String sysTsSubPerCode;

    /** 系统追加-拿样超时时长(天) */
    @ApiModelProperty("系统追加-拿样超时时长(天)")
    private BigDecimal sysTsSampTod;

    /** 关闭信息-关闭方式 值域{"","自动关闭","手动关闭"} */
    @ApiModelProperty("关闭信息-关闭方式 值域{'','自动关闭','手动关闭'}")
    private String sysTsCloseType;

    /** 关闭信息-关闭原因 */
    @ApiModelProperty("关闭信息-关闭原因")
    private String sysTsCloseReason;

    /** 关闭信息-关闭时间 */
    @ApiModelProperty("关闭信息-关闭时间")
    private Date sysTsCloseDate;

    /** 关闭信息-关闭员工姓名 */
    @ApiModelProperty("关闭信息-关闭员工姓名")
    private String sysTsClosePerName;

    /** 关闭信息-关闭员工编号 */
    @ApiModelProperty("关闭信息-关闭员工编号")
    private String sysTsClosePerCode;

    /** 设定信息-任务实际完成时间 */
    @ApiModelProperty("设定信息-任务实际完成时间")
    private Date sysTsActCompleteDate;

    /** 要求信息-Logo设计位置 值域{"产品主体","产品包装"} */
    @ApiModelProperty("要求信息-Logo设计位置")
    private String sysTsLogoDesPos;

    /** 设定信息-指定品牌 */
    @ApiModelProperty("设定信息-指定品牌")
    private String sysTsDevBrand;

    /** 功能操作-保存/发布/删除 */
    @ApiModelProperty("功能操作-保存/发布/删除")
    private String sysFuncOpr;

    /** 界面操作-新增/编辑 */
    @ApiModelProperty("界面操作-新增/编辑")
    private String sysPageOpr;

    /** 单据联系-提案编号集合 */
    @ApiModelProperty("单据联系-提案编号集合")
    private List<String> sysTaCodeList;

    /** 立项信息-产品经理编号 */
    @ApiModelProperty("立项信息-产品经理编号")
    private String sysPmPerCode;

    /** 立项信息-产品经理姓名 */
    @ApiModelProperty("立项信息-产品经理姓名")
    private String sysPmPerName;

    /** 立项信息-产品经理编号集合 */
    @ApiModelProperty("立项信息-产品经理编号集合")
    private List<String> sysPmPerCodeList;

    /** 立项信息-产品经理姓名集合 */
    @ApiModelProperty("立项信息-产品经理姓名集合")
    private List<String> sysPmPerNameList;

    /** 是否未完成工作 */
    @ApiModelProperty("是否未完成工作")
    private String sysInComplete;

    /** 是否返回结果 */
    @ApiModelProperty("是否返回结果")
    private String sysIsReturnResult;

    /** 系统信息-消息已读人 */
    @ApiModelProperty("系统信息-消息已读人")
    private String sysTsRead;
}
