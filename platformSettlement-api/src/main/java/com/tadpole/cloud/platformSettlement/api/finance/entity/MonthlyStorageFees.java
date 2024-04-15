package com.tadpole.cloud.platformSettlement.api.finance.entity;

import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
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
@TableName("MONTHLY_STORAGE_FEES")
@ExcelIgnoreUnannotated
public class MonthlyStorageFees implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    @ExcelIgnore
    private Long id;

    /** 最长边 */
    @TableField("LONGEST_SIDE")
    private BigDecimal longestSide;

    /** 中长边 */
    @TableField("MEDIAN_SIDE")
    private BigDecimal medianSide;

    /** 最短边 */
    @TableField("SHORTEST_SIDE")
    private BigDecimal shortestSide;

    /** 计量单位 */
    @TableField("MEASUREMENT_UNITS")
    private String measurementUnits;

    /** 重量 */
    @TableField("WEIGHT")
    private BigDecimal weight;

    /** 重量单位 */
    @TableField("WEIGHT_UNITS")
    private String weightUnits;

    /** 总体积 */
    @TableField("TOTAL_ITEM_VOLUME")
    private BigDecimal totalItemVolume;

    @TableField("FULFILLMENT_CENTER")
    private String fulfillmentCenter;

    @TableField("COUNTRY_CODE")
    private String countryCode;

    /** 报告文件上传日期(生成上传报告任务的日期) */
    @TableField("UPLOAD_DATE")
    private Date uploadDate;

    @TableField("STATUS")
    private BigDecimal status;

    @TableField("ORIGINAL_TASK_NAME")
    private String originalTaskName;

    @TableField("FILE_PATH")
    private String filePath;

    @TableField("CREATE_TIME")
    private Date createTime;

    /** 费用月份 */
    @ExcelProperty(value= "期间")
    @TableField("MONTH_OF_CHARGE")
    private String monthOfCharge;

    @ExcelProperty(value= "账号")
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    @ExcelProperty(value= "站点")
    @TableField("SYS_SITE")
    private String sysSite;

    @TableField("ASIN")
    @ExcelProperty(value= "Asin")
    private String asin;

    @TableField("FNSKU")
    @ExcelProperty(value= "Fnsku")
    private String fnsku;

    @ExcelProperty(value= "SKU")
    @TableField("SKU")
    private String sku;

    @ExcelProperty(value= "产品类型")
    @TableField("PRODUCT_SIZE_TIER")
    private String productSizeTier;



    @TableField("department")
    @ExcelProperty("事业部")
    private String department;

    @TableField("team")
    @ExcelProperty("Team")
    private String team;

    @ExcelProperty(value= "物料")
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /** 商品体积 */
    @ExcelProperty(value= "单个体积")
    @TableField("ITEM_VOLUME")
    private BigDecimal itemVolume;

    /** 体积单位 */
    @ExcelProperty(value= "体积单位")
    @TableField("VOLUME_UNITS")
    private String volumeUnits;

    @ExcelProperty(value= "平均在库数量")
    @TableField("AVERAGE_QUANTITY_ON_HAND")
    private BigDecimal averageQuantityOnHand;

    @ExcelProperty(value= "平均移除数量")
    @TableField("AVERAGE_QUANTITY_PENDING_REMOVAL")
    private BigDecimal averageQuantityPendingRemoval;

    @ExcelProperty(value= "平均体积")
    @TableField("ESTIMATED_TOTAL_ITEM_VOLUME")
    private BigDecimal estimatedTotalItemVolume;

    @ExcelProperty(value= "存储费率")
    @TableField("STORAGE_RATE")
    private BigDecimal storageRate;

    @ExcelProperty(value= "币别")
    @TableField("CURRENCY")
    private String currency;

    /** 预计的月仓储费 */
    @ExcelProperty(value= "仓储费原币")
    @TableField("ESTIMATED_MONTHLY_STORAGE_FEE")
    private BigDecimal estimatedMonthlyStorageFee;

    @ExcelProperty(value= "仓储费扣费明细（原币）")
    @TableField(exist = false)
    private BigDecimal estimatedMonthlyStorageDetailFee;

    @ExcelProperty(value= "仓储费美金")
    @TableField("STORAGE_FEE")
    private BigDecimal storageFee;

    @ExcelProperty(value= "仓储费扣费明细（美金）")
    @TableField("storage_detail_fee")
    private BigDecimal storageDetailFee;

    @TableField(exist = false)
    @ExcelProperty("状态")
    private String state;

}
