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
* 长期仓储费
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
@TableName("LONG_TERM_STORAGE_FEE_CHARGES")
public class LongTermStorageFeeChargesResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /** Sku */
    @ApiModelProperty("SKU")
    @ExcelProperty(value="SKU")
    private String sku;

    /** Fnsku */
    @ApiModelProperty("FNSKU")
    @ExcelProperty(value="FNSKU")
    private String fnsku;

    /** Asin */
    @ApiModelProperty("ASIN")
    @ExcelProperty(value="ASIN")
    private String asin;

    /** 币种 */
    @ApiModelProperty("CURRENCY")
    @ExcelProperty(value="CURRENCY")
    private String currency;

    /** 长期仓储费（12个月） */
    @ApiModelProperty("MO12_LONG_TERMS_STORAGE_FEE")
    @ExcelProperty(value="MO12_LONG_TERMS_STORAGE_FEE")
    private BigDecimal mo12LongTermsStorageFee;

    /** 长期仓储费（6个月） */
    @ApiModelProperty("MO6_LONG_TERMS_STORAGE_FEE")
    @ExcelProperty(value="MO6_LONG_TERMS_STORAGE_FEE")
    private BigDecimal mo6LongTermsStorageFee;

    /** 收费日期 */
    @ApiModelProperty("SNAPSHOT_DATE")
    @ExcelProperty(value="SNAPSHOT_DATE")
    private Date snapshotDate;

    @ApiModelProperty("TITLE")
    @ExcelProperty(value="TITLE")
    private String title;

    /** 状态 */
    @ApiModelProperty("CONDITION")
    @ExcelProperty(value="CONDITION")
    private String condition;

    /** 单位商品体积 */
    @ApiModelProperty("UNIT_VOLUME")
    @ExcelProperty(value="UNIT_VOLUME")
    private BigDecimal unitVolume;

    /** 收取12月长期仓储费的库存数量 */
    @ApiModelProperty("QTY_CHARGED_12_MO")
    @ExcelProperty(value="QTY_CHARGED_12_MO")
    private Long qtyCharged12Mo;

    /** 收取6月长期仓储费的库存数量 */
    @ApiModelProperty("QTY_CHARGED_6_MO")
    @ExcelProperty(value="QTY_CHARGED_6_MO")
    private Long qtyCharged6Mo;

    /** 体积单位 */
    @ApiModelProperty("VOLUME_UNIT")
    @ExcelProperty(value="VOLUME_UNIT")
    private String volumeUnit;

    /** 国家 */
    @ApiModelProperty("COUNTRY")
    @ExcelProperty(value="COUNTRY")
    private String country;

    /** 是否加入轻小商品计划 */
    @ApiModelProperty("ENROLLED_IN_SMALL_AND_LIGHT")
    @ExcelProperty(value="ENROLLED_IN_SMALL_AND_LIGHT")
    private String enrolledInSmallAndLight;

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

    @ApiModelProperty("仓储费原币 长期仓储费（6+12个月）两字段相加")
    @ExcelProperty(value="仓储费原币 长期仓储费（6+12个月）两字段相加")
    private BigDecimal longTermsStorageFee;

    @ApiModelProperty("仓储费扣费（美金）")
    @ExcelProperty(value="仓储费扣费（美金）")
    private BigDecimal storageDetailFee;

    @ApiModelProperty("事业部")
    @ExcelProperty(value="事业部")
    private String department;

    @ApiModelProperty("Team")
    @ExcelProperty(value="Team")
    private String team;
}
