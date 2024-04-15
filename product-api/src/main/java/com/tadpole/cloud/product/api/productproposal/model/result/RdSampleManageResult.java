package com.tadpole.cloud.product.api.productproposal.model.result;

import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 提案-开发样管理 出参类
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
@TableName("RD_SAMPLE_MANAGE")
public class RdSampleManageResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "SYS_KFY_CODE", type = IdType.AUTO)
    private String sysKfyCode;


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


    @ApiModelProperty("系统信息-样品状态 值域{'待提交','待开发领用','测评中','待归还','待处置','已报失','已退货','已销毁','已封存'}")
    private String sysKfyStatus;


    @ApiModelProperty("系统信息-标签状态 值域{'未打印','已打印'}")
    private String sysKfyLableStatus;


    @ApiModelProperty("单据联系-产品线编号")
    private String sysPlCode;


    @ApiModelProperty("单据联系-SPU")
    private String sysSpu;


    @ApiModelProperty("单据联系-提案编号")
    private String sysTaCode;


    @ApiModelProperty("单据联系-拿样任务编号")
    private String sysTsTaskCode;


    @ApiModelProperty("单据联系-费用申请来源 值域{'直接拿样','购样申请','定制申请'}")
    private String sysKfyFeeSource;


    @ApiModelProperty("单据联系-费用申请编号")
    private String sysKfyFeeCode;


    @ApiModelProperty("样品信息-拿样渠道 值域{'供应商','1688网站','淘宝网站'}")
    private String sysKfySc;


    @ApiModelProperty("样品信息-供应商编号")
    private String sysKfySupplierNum;


    @ApiModelProperty("样品信息-供应商名称")
    private String sysKfySupplierName;


    @ApiModelProperty("样品信息-商品购买页面地址")
    private String sysKfyProPurPage;


    @ApiModelProperty("样品信息-店铺名称")
    private String sysKfyShopName;


    @ApiModelProperty("样品信息-货源地")
    private String sysKfyGoodsSource;


    @ApiModelProperty("样品信息-样品名称")
    private String sysKfySampleName;


    @ApiModelProperty("样品信息-款式")
    private String sysKfyStyle;


    @ApiModelProperty("样品信息-适用品牌或对象")
    private String sysKfyBrand;


    @ApiModelProperty("样品信息-主材料")
    private String sysKfyMainMaterial;

    /** 样品信息-型号 */
    @ApiModelProperty("样品信息-型号")
    private String sysKfyModel;

    @ApiModelProperty("样品信息-图案")
    private String sysKfyPattern;


    @ApiModelProperty("样品信息-颜色")
    private String sysKfyColor;


    @ApiModelProperty("样品信息-包装数量")
    private String sysKfyPackQty;


    @ApiModelProperty("样品信息-包装方式")
    private String sysKfyPackType;


    @ApiModelProperty("样品信息-样品尺寸(cm)")
    private String sysKfySampleSize;


    @ApiModelProperty("样品信息-样品包装尺寸(cm)")
    private String sysKfySamplePackSize;


    @ApiModelProperty("样品信息-净重(g)")
    private BigDecimal sysKfyNetWeight;


    @ApiModelProperty("样品信息-毛重(g)")
    private BigDecimal sysKfyGrossWeight;


    @ApiModelProperty("样品信息-样品费用(CNY)")
    private BigDecimal sysKfySampleFee;


    @ApiModelProperty("样品信息-样品图片")
    private String sysKfySamplePic;


    @ApiModelProperty("样品信息-样品说明")
    private String sysKfySampleDesc;


    @ApiModelProperty("样品信息-初次报价(CNY)")
    private BigDecimal sysKfyQuotePrice;


    @ApiModelProperty("样品信息-税率")
    private BigDecimal sysKfyTaxRate;


    @ApiModelProperty("样品信息-起订量")
    private BigDecimal sysKfyMinOrderQty;


    @ApiModelProperty("样品信息-生产周期(天)")
    private BigDecimal sysKfyProductCycle;


    @ApiModelProperty("样品信息-产品资质")
    private String sysKfyProductCred;


    @ApiModelProperty("样品信息-采购负责人编号")
    private String sysKfyPurPc;


    @ApiModelProperty("样品信息-采购负责人姓名")
    private String sysKfyPurPn;


    @ApiModelProperty("样品信息-登记时间")
    private Date sysKfySubDate;


    @ApiModelProperty("样品信息-是否拿样超时 值域{'是','否'}")
    private String sysKfyIsTakeTo;


    @ApiModelProperty("样品信息-拿样超时时(天)")
    private BigDecimal sysKfyTakeToDay;


    @ApiModelProperty("开发领用信息-领用时间")
    private Date sysKfyUseDate;


    @ApiModelProperty("开发反馈信息-是否有效样品 值域{'有效','无效'}")
    private String sysKfyFebkIsValid;


    @ApiModelProperty("开发反馈信息-样品测评结论")
    private String sysKfyFebkInfo;


    @ApiModelProperty("开发反馈信息-产品分类")
    private String sysKfyProductClassify;


    @ApiModelProperty("开发反馈信息-样品归还方式 值域{'正常','报损','报失'}")
    private String sysKfyRefMethod;


    @ApiModelProperty("开发反馈信息-反馈时间")
    private Date sysKfyFebkDate;


    @ApiModelProperty("开发归还信息-归还时间")
    private Date sysKfyRefDate;


    @ApiModelProperty("采购报失信息-报失操作时间")
    private Date sysKfyLostOpDate;


    @ApiModelProperty("采购销毁信息-销毁承接人编号")
    private String sysKfyPurDestUpc;


    @ApiModelProperty("采购销毁信息-销毁承接人姓名")
    private String sysKfyPurDestUpn;


    @ApiModelProperty("采购销毁信息-销毁操作时间")
    private Date sysKfyPurDestDate;


    @ApiModelProperty("退货信息-退货日期")
    private Date sysKfyRgDate;


    @ApiModelProperty("退货信息-退货操作时间")
    private Date sysKfyRgOpDate;


    @ApiModelProperty("退货信息-退货款编号")
    private String sysKfyRgCode;


    @ApiModelProperty("管理接收信息-接收样品方式")
    private String sysKfyRecSm;


    @ApiModelProperty("管理接收信息-接收时间")
    private Date sysKfyRecDate;


    @ApiModelProperty("管理接收信息-样品管理员编号")
    private String sysKfyRecPc;


    @ApiModelProperty("管理接收信息-样品管理员姓名")
    private String sysKfyRecPn;


    @ApiModelProperty("管理封存信息-货架名称")
    private String sysKfyShelfName;


    @ApiModelProperty("管理封存信息-封存时间")
    private Date sysKfySealDate;


    @ApiModelProperty("管理领用信息-领用时间")
    private Date sysKfyCollectDate;


    @ApiModelProperty("管理领用信息-领用员工编号")
    private String sysKfyCollectPc;


    @ApiModelProperty("管理领用信息-领用员工姓名")
    private String sysKfyCollectPn;


    @ApiModelProperty("管理报失时间-报失时间")
    private Date sysKfyLostDate;


    @ApiModelProperty("管理销毁信息-销毁承接人编号")
    private String sysKfyDestUpc;


    @ApiModelProperty("管理销毁信息-销毁时间")
    private Date sysKfyDestDate;


    @ApiModelProperty("管理销毁信息-销毁承接人姓名")
    private String sysKfyDestUpn;


    @ApiModelProperty("样品信息-样品来源 值域{'现货','定制'}")
    private String sysKfySource;


    @ApiModelProperty("系统信息-样品使用程度")
    private String sysKfySampleUseLevel;

    @ApiModelProperty("样品信息-公司品牌")
    private String sysKfyComBrand;

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

    @ApiModelProperty("立项信息-开发方式")
    private String sysDevMethond;

    @ApiModelProperty("立项信息-提案等级 值域{'S','A','B','C','D'}")
    private String sysTaLevel;

    @ApiModelProperty("系统信息-提案状态 值域{'新提案','已归档'}")
    private String sysTaStatus;

    @ApiModelProperty("立项信息-提案提案立项日期")
    private Date sysTaPaDate;

    @ApiModelProperty("归档信息-归档时间")
    private Date sysTaArchDate;

    @ApiModelProperty("设定信息-拿样任务名称")
    private String sysTsTaskName;

    @ApiModelProperty("设定信息-拿样方式 值域{'现货拿样','定制拿样'}")
    private String sysTsSampleMethod;

    @ApiModelProperty("要求信息-Logo设计位置")
    private String sysTsLogoDesPos;

    @ApiModelProperty("设定信息-指定品牌")
    private String sysTsDevBrand;

    @ApiModelProperty("申请信息-申请费用")
    private BigDecimal sysFeeAppTotalFee;

    @ApiModelProperty("样品信息-尺码")
    private String sysKfySize;

    @ApiModelProperty("样品信息-直径")
    private String sysKfyDiameter;

    @ApiModelProperty("样品信息-容量")
    private String sysKfyCapacity;

    @ApiModelProperty("样品信息-是否带电 值域{\"是\",\"否\"}")
    private String sysKfyIsCharged;

    @ApiModelProperty("样品信息-是否带电 值域{\"是\",\"否\"}")
    private String sysKfyIsCeClass;

    @ApiModelProperty("样品信息-容量单位")
    private String sysKfyCapacityUnit;

    @ApiModelProperty("样品信息-重量单位")
    private String sysKfyWeightUnit;

    @TableField("样品信息-产品规格")
    private String sysKfyProductSpecifications;

    @ApiModelProperty("系统信息-盘点状态")
    private String sysKfyInvStatus;

    @ApiModelProperty("系统信息-当前盘点编码")
    private String sysKfyInvCode;
}
