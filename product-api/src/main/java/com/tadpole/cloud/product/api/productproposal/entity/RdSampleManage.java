package com.tadpole.cloud.product.api.productproposal.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 提案-开发样管理 实体类
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
@ExcelIgnoreUnannotated
public class RdSampleManage implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统信息-开发样编号 */
    @TableId(value = "SYS_KFY_CODE", type = IdType.ASSIGN_ID)
    private String sysKfyCode;

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

    /** 系统信息-样品状态 值域{"待提交","待开发领用","测评中","待归还","待处置","已报失","已退货","已销毁","已封存"} */
    @TableField("SYS_KFY_STATUS")
    private String sysKfyStatus;

    /** 系统信息-标签状态 值域{"未打印","已打印"} */
    @TableField("SYS_KFY_LABLE_STATUS")
    private String sysKfyLableStatus;

    /** 单据联系-产品线编号 */
    @TableField("SYS_PL_CODE")
    private String sysPlCode;

    /** 单据联系-SPU */
    @TableField("SYS_SPU")
    private String sysSpu;

    /** 单据联系-提案编号 */
    @TableField("SYS_TA_CODE")
    private String sysTaCode;

    /** 单据联系-拿样任务编号 */
    @TableField("SYS_TS_TASK_CODE")
    private String sysTsTaskCode;

    /** 单据联系-费用申请来源 值域{"直接拿样","购样申请","定制申请"} */
    @TableField("SYS_KFY_FEE_SOURCE")
    private String sysKfyFeeSource;

    /** 单据联系-费用申请编号 */
    @TableField("SYS_KFY_FEE_CODE")
    private String sysKfyFeeCode;

    /** 样品信息-拿样渠道 值域{"供应商","1688网站","淘宝网站"} */
    @TableField("SYS_KFY_SC")
    private String sysKfySc;

    /** 样品信息-供应商编号 */
    @TableField("SYS_KFY_SUPPLIER_NUM")
    private String sysKfySupplierNum;

    /** 样品信息-供应商名称 */
    @TableField("SYS_KFY_SUPPLIER_NAME")
    private String sysKfySupplierName;

    /** 样品信息-商品购买页面地址 */
    @TableField("SYS_KFY_PRO_PUR_PAGE")
    private String sysKfyProPurPage;

    /** 样品信息-店铺名称 */
    @TableField("SYS_KFY_SHOP_NAME")
    private String sysKfyShopName;

    /** 样品信息-货源地 */
    @TableField("SYS_KFY_GOODS_SOURCE")
    private String sysKfyGoodsSource;

    /** 样品信息-样品名称 */
    @TableField("SYS_KFY_SAMPLE_NAME")
    private String sysKfySampleName;

    /** 样品信息-款式 */
    @TableField("SYS_KFY_STYLE")
    private String sysKfyStyle;

    /** 样品信息-适用品牌或对象 */
    @TableField("SYS_KFY_BRAND")
    private String sysKfyBrand;

    /** 样品信息-主材料 */
    @TableField("SYS_KFY_MAIN_MATERIAL")
    private String sysKfyMainMaterial;

    /** 样品信息-型号 */
    @TableField("SYS_KFY_MODEL")
    private String sysKfyModel;

    /** 样品信息-图案 */
    @TableField("SYS_KFY_PATTERN")
    private String sysKfyPattern;

    /** 样品信息-颜色 */
    @TableField("SYS_KFY_COLOR")
    private String sysKfyColor;

    /** 样品信息-包装数量 */
    @TableField("SYS_KFY_PACK_QTY")
    private String sysKfyPackQty;

    /** 样品信息-包装方式 */
    @TableField("SYS_KFY_PACK_TYPE")
    private String sysKfyPackType;

    /** 样品信息-样品尺寸(cm) */
    @TableField("SYS_KFY_SAMPLE_SIZE")
    private String sysKfySampleSize;

    /** 样品信息-样品包装尺寸(cm) */
    @TableField("SYS_KFY_SAMPLE_PACK_SIZE")
    private String sysKfySamplePackSize;

    /** 样品信息-净重(g) */
    @TableField("SYS_KFY_NET_WEIGHT")
    private BigDecimal sysKfyNetWeight;

    /** 样品信息-毛重(g) */
    @TableField("SYS_KFY_GROSS_WEIGHT")
    private BigDecimal sysKfyGrossWeight;

    /** 样品信息-样品费用(CNY) */
    @TableField("SYS_KFY_SAMPLE_FEE")
    private BigDecimal sysKfySampleFee;

    /** 样品信息-样品图片 */
    @TableField("SYS_KFY_SAMPLE_PIC")
    private String sysKfySamplePic;

    /** 样品信息-样品说明 */
    @TableField("SYS_KFY_SAMPLE_DESC")
    private String sysKfySampleDesc;

    /** 样品信息-初次报价(CNY) */
    @TableField("SYS_KFY_QUOTE_PRICE")
    private BigDecimal sysKfyQuotePrice;

    /** 样品信息-税率 */
    @TableField("SYS_KFY_TAX_RATE")
    private BigDecimal sysKfyTaxRate;

    /** 样品信息-起订量 */
    @TableField("SYS_KFY_MIN_ORDER_QTY")
    private BigDecimal sysKfyMinOrderQty;

    /** 样品信息-生产周期(天) */
    @TableField("SYS_KFY_PRODUCT_CYCLE")
    private BigDecimal sysKfyProductCycle;

    /** 样品信息-产品资质 */
    @TableField("SYS_KFY_PRODUCT_CRED")
    private String sysKfyProductCred;

    /** 样品信息-采购负责人编号 */
    @TableField("SYS_KFY_PUR_PC")
    private String sysKfyPurPc;

    /** 样品信息-采购负责人姓名 */
    @TableField("SYS_KFY_PUR_PN")
    private String sysKfyPurPn;

    /** 样品信息-登记时间 */
    @TableField("SYS_KFY_SUB_DATE")
    private Date sysKfySubDate;

    /** 样品信息-是否拿样超时 值域{"是","否"} */
    @TableField("SYS_KFY_IS_TAKE_TO")
    private String sysKfyIsTakeTo;

    /** 样品信息-拿样超时时(天) */
    @TableField("SYS_KFY_TAKE_TO_DAY")
    private BigDecimal sysKfyTakeToDay;

    /** 开发领用信息-领用时间 */
    @TableField("SYS_KFY_USE_DATE")
    private Date sysKfyUseDate;

    /** 开发反馈信息-是否有效样品 值域{"有效","无效"} */
    @TableField("SYS_KFY_FEBK_IS_VALID")
    private String sysKfyFebkIsValid;

    /** 开发反馈信息-样品测评结论 */
    @TableField("SYS_KFY_FEBK_INFO")
    private String sysKfyFebkInfo;

    /** 开发反馈信息-产品分类 */
    @TableField("SYS_KFY_PRODUCT_CLASSIFY")
    private String sysKfyProductClassify;

    /** 开发反馈信息-样品归还方式 值域{"正常","报损","报失"} */
    @TableField("SYS_KFY_REF_METHOD")
    private String sysKfyRefMethod;

    /** 开发反馈信息-反馈时间 */
    @TableField("SYS_KFY_FEBK_DATE")
    private Date sysKfyFebkDate;

    /** 开发归还信息-归还时间 */
    @TableField("SYS_KFY_REF_DATE")
    private Date sysKfyRefDate;

    /** 采购报失信息-报失操作时间 */
    @TableField("SYS_KFY_LOST_OP_DATE")
    private Date sysKfyLostOpDate;

    /** 采购销毁信息-销毁承接人编号 */
    @TableField("SYS_KFY_PUR_DEST_UPC")
    private String sysKfyPurDestUpc;

    /** 采购销毁信息-销毁承接人姓名 */
    @TableField("SYS_KFY_PUR_DEST_UPN")
    private String sysKfyPurDestUpn;

    /** 采购销毁信息-销毁操作时间 */
    @TableField("SYS_KFY_PUR_DEST_DATE")
    private Date sysKfyPurDestDate;

    /** 退货信息-退货日期 */
    @TableField("SYS_KFY_RG_DATE")
    private Date sysKfyRgDate;

    /** 退货信息-退货操作时间 */
    @TableField("SYS_KFY_RG_OP_DATE")
    private Date sysKfyRgOpDate;

    /** 退货信息-退货款编号 */
    @TableField("SYS_KFY_RG_CODE")
    private String sysKfyRgCode;

    /** 管理接收信息-接收样品方式 */
    @TableField("SYS_KFY_REC_SM")
    private String sysKfyRecSm;

    /** 管理接收信息-接收时间 */
    @TableField("SYS_KFY_REC_DATE")
    private Date sysKfyRecDate;

    /** 管理接收信息-样品管理员编号 */
    @TableField("SYS_KFY_REC_PC")
    private String sysKfyRecPc;

    /** 管理接收信息-样品管理员姓名 */
    @TableField("SYS_KFY_REC_PN")
    private String sysKfyRecPn;

    /** 管理封存信息-货架名称 */
    @TableField("SYS_KFY_SHELF_NAME")
    private String sysKfyShelfName;

    /** 管理封存信息-封存时间 */
    @TableField("SYS_KFY_SEAL_DATE")
    private Date sysKfySealDate;

    /** 管理领用信息-领用时间 */
    @TableField("SYS_KFY_COLLECT_DATE")
    private Date sysKfyCollectDate;

    /** 管理领用信息-领用员工编号 */
    @TableField("SYS_KFY_COLLECT_PC")
    private String sysKfyCollectPc;

    /** 管理领用信息-领用员工姓名 */
    @TableField("SYS_KFY_COLLECT_PN")
    private String sysKfyCollectPn;

    /** 管理报失时间-报失时间 */
    @TableField("SYS_KFY_LOST_DATE")
    private Date sysKfyLostDate;

    /** 管理销毁信息-销毁承接人编号 */
    @TableField("SYS_KFY_DEST_UPC")
    private String sysKfyDestUpc;

    /** 管理销毁信息-销毁时间 */
    @TableField("SYS_KFY_DEST_DATE")
    private Date sysKfyDestDate;

    /** 管理销毁信息-销毁承接人姓名 */
    @TableField("SYS_KFY_DEST_UPN")
    private String sysKfyDestUpn;

    /** 样品信息-样品来源 值域{"现货","定制"} */
    @TableField("SYS_KFY_SOURCE")
    private String sysKfySource;

    /** 系统信息-样品使用程度 值域{“未开封”,"已开封","已损坏"} */
    @TableField("SYS_KFY_SAMPLE_USE_LEVEL")
    private String sysKfySampleUseLevel;

    /** 样品信息-公司品牌 */
    @TableField("SYS_KFY_COM_BRAND")
    private String sysKfyComBrand;

    @TableField("SYS_KFY_SIZE")
    private String sysKfySize;

    @TableField("SYS_KFY_DIAMETER")
    private String sysKfyDiameter;

    @TableField("SYS_KFY_CAPACITY")
    private String sysKfyCapacity;

    @TableField("SYS_KFY_IS_CHARGED")
    private String sysKfyIsCharged;

    @TableField("SYS_KFY_IS_CE_CLASS")
    private String sysKfyIsCeClass;

    @TableField("SYS_KFY_CAPACITY_UNIT")
    private String sysKfyCapacityUnit;

    @TableField("SYS_KFY_WEIGHT_UNIT")
    private String sysKfyWeightUnit;

    @TableField("SYS_KFY_PRODUCT_SPECIFICATIONS")
    private String sysKfyProductSpecifications;

    @TableField("SYS_KFY_INV_STATUS")
    private String sysKfyInvStatus;

    @TableField("SYS_KFY_INV_CODE")
    private String sysKfyInvCode;
}