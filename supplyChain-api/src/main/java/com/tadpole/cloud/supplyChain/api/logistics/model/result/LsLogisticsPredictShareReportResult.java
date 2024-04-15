package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *  物流投入预估分摊报表出参类
 * </p>
 *
 * @author ty
 * @since 2023-12-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("LS_LOGISTICS_PREDICT_SHARE_REPORT")
public class LsLogisticsPredictShareReportResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ExcelProperty(value ="发货日期")
    @ApiModelProperty("发货日期")
    private Date shipmentDate;

    @ApiModelProperty("发货批次号")
    private String shipmentNum;

    @ExcelProperty(value ="平台")
    @ApiModelProperty("平台")
    private String platform;

    @ExcelProperty(value ="账号")
    @ApiModelProperty("账号")
    private String sysShopsName;

    @ExcelProperty(value ="站点")
    @ApiModelProperty("站点")
    private String sysSite;

    @ExcelProperty(value ="需求部门")
    @ApiModelProperty("需求部门")
    private String department;

    @ExcelProperty(value ="需求Team")
    @ApiModelProperty("需求Team")
    private String team;

    @ExcelProperty(value ="发货方式1")
    @ApiModelProperty("发货方式1")
    private String freightCompany;

    @ExcelProperty(value ="运输方式")
    @ApiModelProperty("运输方式")
    private String transportType;

    @ExcelProperty(value ="收获仓")
    @ApiModelProperty("收获仓")
    private String receiveWarehouse;

    @ExcelProperty(value ="单据编号")
    @ApiModelProperty("单据编号 ")
    private String orderNo;

    @ExcelProperty(value ="物流单号")
    @ApiModelProperty("物流单号")
    private String logisticsNo;

    @ExcelProperty(value ="物料编码")
    @ApiModelProperty("物料编码")
    private String materialCode;

    @ExcelProperty(value ="SKU")
    @ApiModelProperty("SKU")
    private String sku;

    @ExcelProperty(value ="FNSKU")
    @ApiModelProperty("FNSKU")
    private String fnsku;

    @ExcelProperty(value ="运营大类")
    @ApiModelProperty("运营大类")
    private String productType;

    @ExcelProperty(value ="产品名称")
    @ApiModelProperty("产品名称")
    private String productName;

    @ExcelProperty(value ="款式/尺码")
    @ApiModelProperty("款式")
    private String style;

    @ExcelProperty(value ="公司品牌")
    @ApiModelProperty("公司品牌")
    private String companyBrand;

    @ExcelProperty(value ="适用品牌/对象")
    @ApiModelProperty("适用品牌及对象")
    private String fitBrand;

    @ExcelProperty(value ="适用机型/机型")
    @ApiModelProperty("适用机型")
    private String type;

    @ExcelProperty(value ="颜色")
    @ApiModelProperty("颜色")
    private String color;

    @ExcelProperty(value ="调拨单号")
    @ApiModelProperty("调拨单号")
    private String packDirectCode;

    @ExcelProperty(value ="数量")
    @ApiModelProperty("数量")
    private Long quantity;

    @ExcelProperty(value ="排序")
    @ApiModelProperty("排序")
    private Long orderNum;

    @ExcelProperty(value ="运费")
    @ApiModelProperty("运费")
    private BigDecimal transportCost;

    @ExcelProperty(value ="预估税费")
    @ApiModelProperty("预估税费")
    private BigDecimal predictTaxFee;

    @ExcelProperty(value ="包装体积cm")
    @ApiModelProperty("包装体积cm³")
    private BigDecimal matBoxVolume;

    @ExcelProperty(value ="体积重g")
    @ApiModelProperty("体积重g")
    private BigDecimal volumeWeight;

    @ExcelProperty(value ="毛重g")
    @ApiModelProperty("毛重g")
    private BigDecimal packageWeight;

    @ExcelProperty(value ="计费重g")
    @ApiModelProperty("计费重g")
    private BigDecimal countFee;

    @ExcelProperty(value ="总计费重KG")
    @ApiModelProperty("总计费重KG")
    private BigDecimal totalCountFee;

    /** 物流单维度总计费重KG合计 */
    @ApiModelProperty("物流单维度总计费重KG合计")
    private BigDecimal sumTotalCountFee;

    @ExcelProperty(value ="费用分摊（单价）")
    @ApiModelProperty("费用分摊（单价）")
    private BigDecimal unitPriceCostShare;

    @ExcelProperty(value ="费用分摊/个")
    @ApiModelProperty("费用分摊/个")
    private BigDecimal costShare;

    @ExcelProperty(value ="总额")
    @ApiModelProperty("总额")
    private BigDecimal totalAmt;

    @ApiModelProperty("数据来源：BTB、EBMS")
    private String dataType;

    @ApiModelProperty("预估税费来源 0：系统，1：导入")
    private String predictTaxFeeType;

    @ApiModelProperty("数据月份")
    private String dataMonth;

    @ApiModelProperty("币别")
    private String currency;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

}
