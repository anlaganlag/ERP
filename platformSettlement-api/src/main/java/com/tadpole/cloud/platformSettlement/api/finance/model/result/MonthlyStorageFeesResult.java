package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* <p>
* 月储存费用
* </p>
*
* @author S20190161
* @since 2022-10-12
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("MONTHLY_STORAGE_FEES")
public class MonthlyStorageFeesResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /** Asin */
    @ApiModelProperty("ASIN")
    @ExcelProperty(value="ASIN")
    private String asin;

    /** Fnsku */
    @ApiModelProperty("FNSKU")
    @ExcelProperty(value="FNSKU")
    private String fnsku;

    /** 最长边 */
    @ApiModelProperty("LONGEST_SIDE")
    @ExcelProperty(value="LONGEST_SIDE")
    private BigDecimal longestSide;

    /** 中长边 */
    @ApiModelProperty("MEDIAN_SIDE")
    @ExcelProperty(value="MEDIAN_SIDE")
    private BigDecimal medianSide;

    /** 最短边 */
    @ApiModelProperty("SHORTEST_SIDE")
    @ExcelProperty(value="SHORTEST_SIDE")
    private BigDecimal shortestSide;

    /** 计量单位 */
    @ApiModelProperty("MEASUREMENT_UNITS")
    @ExcelProperty(value="MEASUREMENT_UNITS")
    private String measurementUnits;

    /** 重量 */
    @ApiModelProperty("WEIGHT")
    @ExcelProperty(value="WEIGHT")
    private BigDecimal weight;

    /** 重量单位 */
    @ApiModelProperty("WEIGHT_UNITS")
    @ExcelProperty(value="WEIGHT_UNITS")
    private String weightUnits;

    /** 币种 */
    @ApiModelProperty("CURRENCY")
    @ExcelProperty(value="CURRENCY")
    private String currency;

    /** 商品体积 */
    @ApiModelProperty("ITEM_VOLUME")
    @ExcelProperty(value="ITEM_VOLUME")
    private BigDecimal itemVolume;

    /** 体积单位 */
    @ApiModelProperty("VOLUME_UNITS")
    @ExcelProperty(value="VOLUME_UNITS")
    private String volumeUnits;

    /** 商品类型 */
    @ApiModelProperty("PRODUCT_SIZE_TIER")
    @ExcelProperty(value="PRODUCT_SIZE_TIER")
    private String productSizeTier;

    /** 平均数量 */
    @ApiModelProperty("AVERAGE_QUANTITY_ON_HAND")
    @ExcelProperty(value="AVERAGE_QUANTITY_ON_HAND")
    private BigDecimal averageQuantityOnHand;

    /** 平均移除数量 */
    @ApiModelProperty("AVERAGE_QUANTITY_PENDING_REMOVAL")
    @ExcelProperty(value="AVERAGE_QUANTITY_PENDING_REMOVAL")
    private BigDecimal averageQuantityPendingRemoval;

    /** 总体积 */
    @ApiModelProperty("TOTAL_ITEM_VOLUME")
    @ExcelProperty(value="TOTAL_ITEM_VOLUME")
    private BigDecimal totalItemVolume;

    /** 费用月份 */
    @ApiModelProperty("MONTH_OF_CHARGE")
    @ExcelProperty(value="MONTH_OF_CHARGE")
    private String monthOfCharge;

    /** 存储费率 */
    @ApiModelProperty("STORAGE_RATE")
    @ExcelProperty(value="STORAGE_RATE")
    private BigDecimal storageRate;

    @ApiModelProperty("FULFILLMENT_CENTER")
    @ExcelProperty(value="FULFILLMENT_CENTER")
    private String fulfillmentCenter;

    /** 城市编号 */
    @ApiModelProperty("COUNTRY_CODE")
    @ExcelProperty(value="COUNTRY_CODE")
    private String countryCode;

    /** 预计的月仓储费 */
    @ApiModelProperty("ESTIMATED_MONTHLY_STORAGE_FEE")
    @ExcelProperty(value="ESTIMATED_MONTHLY_STORAGE_FEE")
    private BigDecimal estimatedMonthlyStorageFee;

    @ApiModelProperty("SKU")
    @ExcelProperty(value="SKU")
    private String sku;

    @ApiModelProperty("MATERIAL_CODE")
    @ExcelProperty(value="MATERIAL_CODE")
    private String materialCode;

    @ApiModelProperty("STORAGE_FEE")
    @ExcelProperty(value="STORAGE_FEE")
    private BigDecimal storageFee;

    @ApiModelProperty("STATUS")
    @ExcelProperty(value="STATUS")
    private BigDecimal status;

    @ApiModelProperty("ORIGINAL_TASK_NAME")
    @ExcelProperty(value="ORIGINAL_TASK_NAME")
    private String originalTaskName;

    @ApiModelProperty("FILE_PATH")
    @ExcelProperty(value="FILE_PATH")
    private String filePath;

    @ApiModelProperty("CREATE_TIME")
    @ExcelProperty(value="CREATE_TIME")
    private Date createTime;

    /** 报告文件上传日期(生成上传报告任务的日期) */
    @ApiModelProperty("UPLOAD_DATE")
    @ExcelProperty(value="UPLOAD_DATE")
    private Date uploadDate;

    @ApiModelProperty("SYS_SITE")
    @ExcelProperty(value="SYS_SITE")
    private String sysSite;

    /** 账号简称 */
    @ApiModelProperty("SYS_SHOPS_NAME")
    @ExcelProperty(value="SYS_SHOPS_NAME")
    private String sysShopsName;

    @ApiModelProperty("平均体积")
    @ExcelProperty(value="平均体积")
    private BigDecimal estimatedTotalItemVolume;

    @ApiModelProperty("ESTIMATED_MONTHLY_STORAGE_DETAIL_FEE")
    @ExcelProperty(value="仓储费扣费明细（原币）")
    private BigDecimal estimatedMonthlyStorageDetailFee;

    @ApiModelProperty("STORAGE_DETAIL_FEE")
    @ExcelProperty(value="仓储费扣费明细（美金）")
    private BigDecimal storageDetailFee;


    @ApiModelProperty("事业部")
    @ExcelProperty(value="事业部")
    private String department;

    @ApiModelProperty("Team")
    @ExcelProperty(value="Team")
    private String team;
}
