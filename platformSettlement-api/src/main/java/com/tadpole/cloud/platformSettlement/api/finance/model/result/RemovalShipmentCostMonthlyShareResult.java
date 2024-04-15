package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
* 销毁移除成本月分摊表
* </p>
*
* @author ty
* @since 2022-05-19
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class RemovalShipmentCostMonthlyShareResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 订单ID */
    @ApiModelProperty("订单ID")
    @ExcelProperty(value ="订单ID")
    private String orderId;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    @ExcelProperty(value ="行ID")
    private BigDecimal id;

    /** 请求时间 */
    @ApiModelProperty("请求时间")
    private Date requestDate;

    /** 开始期间 */
    @ApiModelProperty("开始期间")
    @ExcelProperty(value ="开始期间")
    private String startDateFormat;

    /** 结束期间 */
    @ApiModelProperty("结束期间")
    @ExcelProperty(value ="结束期间")
    private String endDateFormat;

    /** 订单类型 */
    @ApiModelProperty("订单类型")
    @ExcelProperty(value ="订单类型")
    private String orderType;

    /** 账号 */
    @ApiModelProperty("账号")
    @ExcelProperty(value ="账号")
    private String sysShopsName;

    /** 站点 */
    @ApiModelProperty("站点")
    @ExcelProperty(value ="站点")
    private String sysSite;

    /** SKU */
    @ApiModelProperty("sku")
    @ExcelProperty(value ="sku")
    private String sku;

    /** 事业部 */
    @ApiModelProperty("事业部")
    @ExcelProperty(value ="事业部")
    private String department;

    /** Team */
    @ApiModelProperty("Team")
    @ExcelProperty(value ="Team")
    private String team;

    /** 运营大类 */
    @ApiModelProperty("运营大类")
    @ExcelProperty(value ="运营大类")
    private String productType;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    @ExcelProperty(value ="物料编码")
    private String materialCode;

    /** 类目 */
    @ApiModelProperty("类目")
    @ExcelProperty(value ="类目")
    private String category;

    /** 产品名称 */
    @ApiModelProperty("产品名称")
    @ExcelProperty(value ="产品名称")
    private String productName;

    /** 款式 */
    @ApiModelProperty("款式")
    @ExcelProperty(value ="款式")
    private String style;

    /** 主材料 */
    @ApiModelProperty("主材料")
    @ExcelProperty(value ="主材料")
    private String mainMaterial;

    /** 图案 */
    @ApiModelProperty("图案")
    @ExcelProperty(value ="图案")
    private String design;

    /** 适用品牌或对象 */
    @ApiModelProperty("适用品牌或对象")
    @ExcelProperty(value ="适用品牌或对象")
    private String fitBrand;

    /** 型号 */
    @ApiModelProperty("型号")
    @ExcelProperty(value ="型号")
    private String model;

    /** 公司品牌 */
    @ApiModelProperty("公司品牌")
    @ExcelProperty(value ="公司品牌")
    private String companyBrand;

    /** 颜色 */
    @ApiModelProperty("颜色")
    @ExcelProperty(value ="颜色")
    private String color;

    /** 尺码 */
    @ApiModelProperty("尺码")
    @ExcelProperty(value ="尺码")
    private String sizes;

    /** 包装数量 */
    @ApiModelProperty("包装数量")
    @ExcelProperty(value ="包装数量")
    private String packing;

    /** 版本 */
    @ApiModelProperty("版本")
    @ExcelProperty(value ="版本")
    private String version;

    /** 适用机型 */
    @ApiModelProperty("适用机型")
    @ExcelProperty(value ="适用机型")
    private String type;

    /** 销售品牌 */
    @ApiModelProperty("销售品牌")
    @ExcelProperty(value ="销售品牌")
    private String salesBrand;

    /** 二级标签 */
    @ApiModelProperty("二级标签")
    @ExcelProperty(value ="二级标签")
    private String styleSecondLabel;

    /** 币种 */
    @ApiModelProperty("币种")
    @ExcelProperty(value ="币种")
    private String currency;

    /** 摊销期 */
    @ApiModelProperty("摊销期")
    @ExcelProperty(value ="摊销期")
    private BigDecimal shareNum;

    /** 采购单价 */
    @ApiModelProperty("采购单价")
    @ExcelProperty(value ="采购单价")
    private BigDecimal purchaseUnitPrice;

    /** 物流单价 */
    @ApiModelProperty("物流单价")
    @ExcelProperty(value ="物流单价")
    private BigDecimal logisticsUnitPrice;

    /** 汇率 */
    @ApiModelProperty("汇率")
//    @ExcelProperty(value ="汇率")
    private BigDecimal directRate;

    /** 销毁数量 */
    @ApiModelProperty("销毁数量")
    @ExcelProperty(value ="销毁数量")
    private BigDecimal shippedQuantity;

    /** 总分摊额销毁成本-采购成本 */
    @ApiModelProperty("总分摊额销毁成本-采购成本")
    @ExcelProperty(value ="总分摊额销毁成本-采购成本")
    private BigDecimal allSharePurchaseCost;

    /** 总分摊额销毁成本-头程物流成本 */
    @ApiModelProperty("总分摊额销毁成本-头程物流成本")
    @ExcelProperty(value ="总分摊额销毁成本-头程物流成本")
    private BigDecimal allShareLogisticsCost;

    /** 累计分摊额销毁成本-采购成本 */
    @ApiModelProperty("累计分摊额销毁成本-采购成本")
    @ExcelProperty(value ="累计分摊额销毁成本-采购成本")
    private BigDecimal alreadySharePurchaseCost;

    /** 累计分摊额销毁成本-头程物流成本 */
    @ApiModelProperty("累计分摊额销毁成本-头程物流成本")
    @ExcelProperty(value ="累计分摊额销毁成本-头程物流成本")
    private BigDecimal alreadyShareLogisticsCost;

    /** 剩余分摊额销毁成本-采购成本 */
    @ApiModelProperty("剩余分摊额销毁成本-采购成本")
    @ExcelProperty(value ="剩余分摊额销毁成本-采购成本")
    private BigDecimal notSharePurchaseCost;

    /** 剩余分摊额销毁成本-头程物流成本 */
    @ApiModelProperty("剩余分摊额销毁成本-头程物流成本")
    @ExcelProperty(value ="剩余分摊额销毁成本-头程物流成本")
    private BigDecimal notShareLogisticsCost;

    /** 月摊额销毁成本-采购成本 */
    @ApiModelProperty("月摊额销毁成本-采购成本")
    @ExcelProperty(value ="月摊额销毁成本-采购成本")
    private BigDecimal monthlySharePurchaseCost;

    /** 月摊额销毁成本-头程物流成本 */
    @ApiModelProperty("月摊额销毁成本-头程物流成本")
    @ExcelProperty(value ="月摊额销毁成本-头程物流成本")
    private BigDecimal monthlyShareLogisticsCost;

    /** 本期摊额销毁成本-采购成本 */
    @ApiModelProperty("本期摊额销毁成本-采购成本")
    @ExcelProperty(value ="本期摊额销毁成本-采购成本")
    private BigDecimal nowSharePurchaseCost;

    /** 本期摊额销毁成本-头程物流成本 */
    @ApiModelProperty("本期摊额销毁成本-头程物流成本")
    @ExcelProperty(value ="本期摊额销毁成本-头程物流成本")
    private BigDecimal nowShareLogisticsCost;

    /** 确认状态 0：未确认，1：已确认 */
    @ApiModelProperty("确认状态 0：未确认，1：已确认，2：已作废")
    @ExcelProperty(value ="状态")
    private String confirmStatus;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createBy;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updateBy;

    /** 累计最新分摊年月 */
    @ApiModelProperty("累计最新分摊年月")
    private String lastShareDateFormat;

    /** 导入备注 */
    @ApiModelProperty("导入备注")
    @ExcelProperty(value ="导入备注")
    private String uploadRemark;

}