package com.tadpole.cloud.product.api.productproposal.model.params;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
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
 * 提案-开发样管理 入参类
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
@TableName("RD_SAMPLE_MANAGE")
public class RdSampleManageParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统信息-开发样编号 */
    @TableId(value = "SYS_KFY_CODE", type = IdType.ASSIGN_ID)
    private String sysKfyCode;

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

    /** 系统信息-样品状态 值域{"待提交","待开发领用","测评中","待归还","待处置","已报失","已退货","已销毁","已封存"} */
    @ApiModelProperty("系统信息-样品状态 值域{'待提交','待开发领用','测评中','待归还','待处置','已报失','已退货','已销毁','已封存'}")
    private String sysKfyStatus;

    /** 系统信息-标签状态 值域{"未打印","已打印"} */
    @ApiModelProperty("系统信息-标签状态 值域{'未打印','已打印'}")
    private String sysKfyLableStatus;

    /** 单据联系-产品线编号 */
    @ApiModelProperty("单据联系-产品线编号")
    private String sysPlCode;

    /** 单据联系-SPU */
    @ApiModelProperty("单据联系-SPU")
    private String sysSpu;

    /** 单据联系-提案编号 */
    @ApiModelProperty("单据联系-提案编号")
    private String sysTaCode;

    /** 单据联系-拿样任务编号 */
    @ApiModelProperty("单据联系-拿样任务编号")
    private String sysTsTaskCode;

    /** 单据联系-费用申请来源 值域{"直接拿样","购样申请","定制申请"} */
    @ApiModelProperty("单据联系-费用申请来源 值域{'直接拿样','购样申请','定制申请'}")
    private String sysKfyFeeSource;

    /** 单据联系-费用申请编号 */
    @ApiModelProperty("单据联系-费用申请编号")
    private String sysKfyFeeCode;

    /** 样品信息-拿样渠道 值域{"供应商","1688网站","淘宝网站"} */
    @ApiModelProperty("样品信息-拿样渠道 值域{'供应商','1688网站','淘宝网站'}")
    private String sysKfySc;

    /** 样品信息-供应商编号 */
    @ApiModelProperty("样品信息-供应商编号")
    private String sysKfySupplierNum;

    /** 样品信息-供应商名称 */
    @ApiModelProperty("样品信息-供应商名称")
    private String sysKfySupplierName;

    /** 样品信息-商品购买页面地址 */
    @ApiModelProperty("样品信息-商品购买页面地址")
    private String sysKfyProPurPage;

    /** 样品信息-店铺名称 */
    @ApiModelProperty("样品信息-店铺名称")
    private String sysKfyShopName;

    /** 样品信息-货源地 */
    @ApiModelProperty("样品信息-货源地")
    private String sysKfyGoodsSource;

    /** 样品信息-样品名称 */
    @ApiModelProperty("样品信息-样品名称")
    private String sysKfySampleName;

    /** 样品信息-款式 */
    @ApiModelProperty("样品信息-款式")
    private String sysKfyStyle;

    /** 样品信息-适用品牌或对象 */
    @ApiModelProperty("样品信息-适用品牌或对象")
    private String sysKfyBrand;

    /** 样品信息-主材料 */
    @ApiModelProperty("样品信息-主材料")
    private String sysKfyMainMaterial;

    /** 样品信息-型号 */
    @ApiModelProperty("样品信息-型号")
    private String sysKfyModel;

    /** 样品信息-图案 */
    @ApiModelProperty("样品信息-图案")
    private String sysKfyPattern;

    /** 样品信息-颜色 */
    @ApiModelProperty("样品信息-颜色")
    private String sysKfyColor;

    /** 样品信息-包装数量 */
    @ApiModelProperty("样品信息-包装数量")
    private String sysKfyPackQty;

    /** 样品信息-包装方式 */
    @ApiModelProperty("样品信息-包装方式")
    private String sysKfyPackType;

    /** 样品信息-样品尺寸(cm) */
    @ApiModelProperty("样品信息-样品尺寸(cm)")
    private String sysKfySampleSize;

    /** 样品信息-样品包装尺寸(cm) */
    @ApiModelProperty("样品信息-样品包装尺寸(cm)")
    private String sysKfySamplePackSize;

    /** 样品信息-净重(g) */
    @ApiModelProperty("样品信息-净重(g)")
    private BigDecimal sysKfyNetWeight;

    /** 样品信息-毛重(g) */
    @ApiModelProperty("样品信息-毛重(g)")
    private BigDecimal sysKfyGrossWeight;

    /** 样品信息-样品费用(CNY) */
    @ApiModelProperty("样品信息-样品费用(CNY)")
    private BigDecimal sysKfySampleFee;

    /** 样品信息-样品图片 */
    @ApiModelProperty("样品信息-样品图片")
    private String sysKfySamplePic;

    /** 样品信息-样品说明 */
    @ApiModelProperty("样品信息-样品说明")
    private String sysKfySampleDesc;

    /** 样品信息-初次报价(CNY) */
    @ApiModelProperty("样品信息-初次报价(CNY)")
    private BigDecimal sysKfyQuotePrice;

    /** 样品信息-税率 */
    @ApiModelProperty("样品信息-税率")
    private BigDecimal sysKfyTaxRate;

    /** 样品信息-起订量 */
    @ApiModelProperty("样品信息-起订量")
    private BigDecimal sysKfyMinOrderQty;

    /** 样品信息-生产周期(天) */
    @ApiModelProperty("样品信息-生产周期(天)")
    private BigDecimal sysKfyProductCycle;

    /** 样品信息-产品资质 */
    @ApiModelProperty("样品信息-产品资质")
    private String sysKfyProductCred;

    /** 样品信息-采购负责人编号 */
    @ApiModelProperty("样品信息-采购负责人编号")
    private String sysKfyPurPc;

    /** 样品信息-采购负责人姓名 */
    @ApiModelProperty("样品信息-采购负责人姓名")
    private String sysKfyPurPn;

    /** 样品信息-登记时间 */
    @ApiModelProperty("样品信息-登记时间")
    private Date sysKfySubDate;

    /** 样品信息-是否拿样超时 值域{"是","否"} */
    @ApiModelProperty("样品信息-是否拿样超时 值域{'是','否'}")
    private String sysKfyIsTakeTo;

    /** 样品信息-拿样超时时(天) */
    @ApiModelProperty("样品信息-拿样超时时(天)")
    private BigDecimal sysKfyTakeToDay;


    /** 开发领用信息-领用时间 */
    @ApiModelProperty("开发领用信息-领用时间")
    private Date sysKfyUseDate;

    /** 开发反馈信息-是否有效样品 值域{"有效","无效"} */
    @ApiModelProperty("开发反馈信息-是否有效样品 值域{'有效','无效'}")
    private String sysKfyFebkIsValid;

    /** 开发反馈信息-样品测评结论 */
    @ApiModelProperty("开发反馈信息-样品测评结论")
    private String sysKfyFebkInfo;

    /** 开发反馈信息-产品分类 */
    @ApiModelProperty("开发反馈信息-产品分类")
    private String sysKfyProductClassify;

    /** 开发反馈信息-样品归还方式 值域{"正常","报损","报失"} */
    @ApiModelProperty("开发反馈信息-样品归还方式 值域{'正常','报损','报失'}")
    private String sysKfyRefMethod;

    /** 开发反馈信息-反馈时间 */
    @ApiModelProperty("开发反馈信息-反馈时间")
    private Date sysKfyFebkDate;

    /** 开发归还信息-归还时间 */
    @ApiModelProperty("开发归还信息-归还时间")
    private Date sysKfyRefDate;

    /** 采购报失信息-报失操作时间 */
    @ApiModelProperty("采购报失信息-报失操作时间")
    private Date sysKfyLostOpDate;

    /** 采购销毁信息-销毁承接人编号 */
    @ApiModelProperty("采购销毁信息-销毁承接人编号")
    private String sysKfyPurDestUpc;

    /** 采购销毁信息-销毁承接人姓名 */
    @ApiModelProperty("采购销毁信息-销毁承接人姓名")
    private String sysKfyPurDestUpn;

    /** 采购销毁信息-销毁操作时间 */
    @ApiModelProperty("采购销毁信息-销毁操作时间")
    private Date sysKfyPurDestDate;

    /** 退货信息-退货日期 */
    @ApiModelProperty("退货信息-退货日期")
    private Date sysKfyRgDate;

    /** 退货信息-退货操作时间 */
    @ApiModelProperty("退货信息-退货操作时间")
    private Date sysKfyRgOpDate;

    /** 退货信息-退货款编号 */
    @ApiModelProperty("退货信息-退货款编号")
    private String sysKfyRgCode;

    /** 管理接收信息-接收样品方式 */
    @ApiModelProperty("管理接收信息-接收样品方式")
    private String sysKfyRecSm;

    /** 管理接收信息-接收时间 */
    @ApiModelProperty("管理接收信息-接收时间")
    private Date sysKfyRecDate;

    /** 管理接收信息-样品管理员编号 */
    @ApiModelProperty("管理接收信息-样品管理员编号")
    private String sysKfyRecPc;

    /** 管理接收信息-样品管理员姓名 */
    @ApiModelProperty("管理接收信息-样品管理员姓名")
    private String sysKfyRecPn;

    /** 管理封存信息-货架名称 */
    @ApiModelProperty("管理封存信息-货架名称")
    private String sysKfyShelfName;

    /** 管理封存信息-封存时间 */
    @ApiModelProperty("管理封存信息-封存时间")
    private Date sysKfySealDate;

    /** 管理领用信息-领用时间 */
    @ApiModelProperty("管理领用信息-领用时间")
    private Date sysKfyCollectDate;

    /** 管理领用信息-领用员工编号 */
    @ApiModelProperty("管理领用信息-领用员工编号")
    private String sysKfyCollectPc;

    /** 管理领用信息-领用员工姓名 */
    @ApiModelProperty("管理领用信息-领用员工姓名")
    private String sysKfyCollectPn;

    /** 管理报失时间-报失时间 */
    @ApiModelProperty("管理报失时间-报失时间")
    private Date sysKfyLostDate;

    /** 管理销毁信息-销毁承接人编号 */
    @ApiModelProperty("管理销毁信息-销毁承接人编号")
    private String sysKfyDestUpc;

    /** 管理销毁信息-销毁时间 */
    @ApiModelProperty("管理销毁信息-销毁时间")
    private Date sysKfyDestDate;

    /** 管理销毁信息-销毁承接人姓名 */
    @ApiModelProperty("管理销毁信息-销毁承接人姓名")
    private String sysKfyDestUpn;

    /** 样品信息-样品来源 值域{"现货","定制"} */
    @ApiModelProperty("样品信息-样品来源 值域{'现货','定制'}")
    private String sysKfySource;

    /** 系统信息-样品使用程度 值域{“未开封”,"已开封","已损坏"} */
    @ApiModelProperty("系统信息-样品使用程度")
    private String sysKfySampleUseLevel;

    /** 样品信息-公司品牌 */
    @ApiModelProperty("样品信息-公司品牌")
    private String sysKfyComBrand;

    /** 系统信息-开发样编号集合 */
    @ApiModelProperty("系统信息-开发样编号集合")
    private List<String> sysKfyCodeList;

    /** 功能操作-保存/提交/删除/打印/报失/销毁/退货 */
    @ApiModelProperty("功能操作-保存/提交/删除/打印/报失/销毁/退货/签收/反馈/收回/接收/")
    private String sysFuncOpr;

    /** 界面操作-新增/编辑 */
    @ApiModelProperty("界面操作-新增/编辑")
    private String sysPageOpr;

    /** 样品信息-拿样渠道集合 值域{"供应商","1688网站","淘宝网站"} */
    @ApiModelProperty("样品信息-拿样渠道集合 值域{'供应商','1688网站','淘宝网站'}")
    private List<String> sysKfyScList;

    /** 系统信息-样品状态集合 值域{"待提交","待开发领用","测评中","待归还","待处置","已报失","已退货","已销毁","已封存"} */
    @ApiModelProperty("系统信息-样品状态集合 值域{'待提交','待开发领用','测评中','待归还','待处置','已报失','已退货','已销毁','已封存'}")
    private List<String> sysKfyStatusList;

    /** 样品信息-样品名称集合 */
    @ApiModelProperty("样品信息-样品名称集合")
    private List<String> sysKfySampleNameList;


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

    @ApiModelProperty("样品信息-产品规格")
    private String sysKfyProductSpecifications;

    @ApiModelProperty("系统信息-盘点状态")
    private String sysKfyInvStatus;

    @ApiModelProperty("系统信息-当前盘点编码")
    private String sysKfyInvCode;

}
