package com.tadpole.cloud.platformSettlement.modular.sales.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 库存周转
 * </p>
 *
 * @author cyt
 * @since 2022-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("INVENTORY_TURNOVER")
public class InventoryTurnoverParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 库存周转ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 库存周转序号 */
    @ApiModelProperty("IDX")
    private Integer idx;

    /** 平台 */
    @ApiModelProperty("PLATFORM")
    private String platform;

    /** 事业部 */
    @ApiModelProperty("DEPARTMENT")
    private String department;

    /** Team */
    @ApiModelProperty("TEAM")
    private String team;

    /** 运营大类 */
    @ApiModelProperty("PRODUCT_TYPE")
    private String productType;

    /** 销售品牌 */
    @ApiModelProperty("COMPANY_BRAND")
    private String companyBrand;

    /** 确认年份 */
    @NotNull
    @ApiModelProperty("YEAR")
    private String year;

    /** 确认月份 */
    @ApiModelProperty("MONTH")
    private String month;

    /** 1月 */
    @DecimalMin(value="0.00",message="最小值为0")
    @ApiModelProperty("MON1")
    private BigDecimal mon1;

    /** 2月 */
    @DecimalMin(value="0.00",message="最小值为0")
    @ApiModelProperty("MON2")
    private BigDecimal mon2;

    /** 3月 */
    @DecimalMin(value="0.00",message="最小值为0")
    @ApiModelProperty("MON3")
    private BigDecimal mon3;

    /** 4月 */
    @DecimalMin(value="0.00",message="最小值为0")
    @ApiModelProperty("MON4")
    private BigDecimal mon4;

    /** 5月 */
    @DecimalMin(value="0.00",message="最小值为0")
    @ApiModelProperty("MON5")
    private BigDecimal mon5;

    /** 6月 */
    @ApiModelProperty("MON6")
    @DecimalMin(value="0.00",message="最小值为0")
    private BigDecimal mon6;

    /** 7月 */
    @ApiModelProperty("MON7")
    @DecimalMin(value="0.00",message="最小值为0")
    private BigDecimal mon7;

    /** 8月 */
    @ApiModelProperty("MON8")
    @DecimalMin(value="0.00",message="最小值为0")
    private BigDecimal mon8;

    /** 9月 */
    @ApiModelProperty("MON9")
    @DecimalMin(value="0.00",message="最小值为0")
    private BigDecimal mon9;

    /** 10月 */
    @ApiModelProperty("MON10")
    @DecimalMin(value="0.00",message="最小值为0")
    private BigDecimal mon10;

    /** 11月 */
    @ApiModelProperty("MON11")
    @DecimalMin(value="0.00",message="最小值为0")
    private BigDecimal mon11;

    /** 12月 */
    @ApiModelProperty("MON12")
    @DecimalMin(value="0.00",message="最小值为0")
    private BigDecimal mon12;

    /** 确认状态 */
    @ApiModelProperty("COMFIRM_STATUS")
    private Integer comfirmStatus;

    /** 确认时间 */
    @ApiModelProperty("COMFIRM_TIME")
    private Date comfirmTime;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 创建人工号 */
    @ApiModelProperty("CREATE_BY_CODE")
    private String createByCode;

    /** 创建人工号 */
    @ApiModelProperty("CREATE_BY_DEPT")
    private String createByDept;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

    private List platforms;

    private List companyBrands;

    private List productTypes;

    private List teams;
}