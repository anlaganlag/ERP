package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.TableName;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* <p>
* FBA存货存储超额费用报告
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
@TableName("INVENTORY_STORAGE_OVERAGE_FEES")
public class InventoryStorageOverageFeesResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 收费日期 */
    @ApiModelProperty("CHARGED_DATE")
    private Date chargedDate;

    /** 国家代码 */
    @ApiModelProperty("COUNTRY_CODE")
    private String countryCode;

    /** 存储类型 */
    @ApiModelProperty("STORAGE_TYPE")
    private String storageType;

    /** 收费率 */
    @ApiModelProperty("CHARGE_RATE")
    private BigDecimal chargeRate;

    /** 使用量 */
    @ApiModelProperty("STORAGE_USAGE_VOLUME")
    private BigDecimal storageUsageVolume;

    /** 存储容量限制 */
    @ApiModelProperty("STORAGE_LIMIT_VOLUME")
    private BigDecimal storageLimitVolume;

    /** 溢出量 */
    @ApiModelProperty("OVERAGE_VOLUME")
    private BigDecimal overageVolume;

    /** 容量单位 */
    @ApiModelProperty("VOLUME_UNIT")
    private String volumeUnit;

    /** 收费金额 */
    @ApiModelProperty("CHARGED_FEE_AMOUNT")
    private BigDecimal chargedFeeAmount;

    /** 货币代码 */
    @ApiModelProperty("CURRENCY_CODE")
    private String currencyCode;

    /**
     * 存储费($) 根据【币别】取AZ结算报告-站内费用分摊-ERP即期汇率
     * 【直接汇率】 *【超库容费（原币
     */
    @ApiModelProperty("STORAGE_FEE")
    private BigDecimal storageFee;

    /** 1.未确认 2.已确认 */
    @ApiModelProperty("STATUS")
    private BigDecimal status;

    @ApiModelProperty("ORIGINAL_TASK_NAME")
    private String originalTaskName;

    @ApiModelProperty("FILE_PATH")
    private String filePath;

    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    @ApiModelProperty("UPLOAD_DATE")
    private Date uploadDate;

    @ApiModelProperty("SYS_SITE")
    private String sysSite;

    @ApiModelProperty("SYS_SHOPS_NAME")
    private String sysShopsName;

    @ApiModelProperty("扣费仓储费（美金")
    private BigDecimal storageDetailFee;

}
