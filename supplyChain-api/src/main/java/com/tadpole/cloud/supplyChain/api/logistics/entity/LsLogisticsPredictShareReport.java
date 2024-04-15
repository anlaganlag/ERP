package com.tadpole.cloud.supplyChain.api.logistics.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *  物流投入预估分摊报表实体类
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
@TableName("LS_LOGISTICS_PREDICT_SHARE_REPORT")
@ExcelIgnoreUnannotated
public class LsLogisticsPredictShareReport implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 发货日期 */
    @ApiModelProperty("发货日期")
    @TableField("SHIPMENT_DATE")
    private Date shipmentDate;

    /** 发货批次号 */
    @ApiModelProperty("发货批次号")
    @TableField("SHIPMENT_NUM")
    private String shipmentNum;

    /** 平台 */
    @ApiModelProperty("平台")
    @TableField("PLATFORM")
    private String platform;

    /** 账号 */
    @ApiModelProperty("账号")
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    /** 站点 */
    @ApiModelProperty("站点")
    @TableField("SYS_SITE")
    private String sysSite;

    /** 需求部门 */
    @ApiModelProperty("需求部门")
    @TableField("DEPARTMENT")
    private String department;

    /** 需求Team */
    @ApiModelProperty("需求Team")
    @TableField("TEAM")
    private String team;

    /** 发货方式1 */
    @ApiModelProperty("发货方式1")
    @TableField("FREIGHT_COMPANY")
    private String freightCompany;

    /** 运输方式 */
    @ApiModelProperty("运输方式")
    @TableField("TRANSPORT_TYPE")
    private String transportType;

    /** 收获仓 */
    @ApiModelProperty("收获仓")
    @TableField("RECEIVE_WAREHOUSE")
    private String receiveWarehouse;

    /** 单据编号  */
    @ApiModelProperty("单据编号")
    @TableField("ORDER_NO")
    private String orderNo;

    /** 物流单号 */
    @ApiModelProperty("物流单号")
    @ExcelProperty(value ="物流单号*")
    @TableField("LOGISTICS_NO")
    private String logisticsNo;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /** SKU */
    @ApiModelProperty("SKU")
    @TableField("SKU")
    private String sku;

    /** FNSKU */
    @ApiModelProperty("FNSKU")
    @TableField("FNSKU")
    private String fnsku;

    /** 调拨单号 */
    @ApiModelProperty("调拨单号")
    @TableField("PACK_DIRECT_CODE")
    private String packDirectCode;

    /** 数量 */
    @ApiModelProperty("数量")
    @TableField("QUANTITY")
    private Long quantity;

    /** 运费 */
    @ApiModelProperty("运费")
    @TableField("TRANSPORT_COST")
    private BigDecimal transportCost;

    /** 预估税费 */
    @ApiModelProperty("预估税费")
    @ExcelProperty(value ="预估税费*")
    @TableField("PREDICT_TAX_FEE")
    private BigDecimal predictTaxFee;

    /** 预估税费来源 0：系统，1：导入 */
    @TableField("PREDICT_TAX_FEE_TYPE")
    private String predictTaxFeeType;

    /** 包装体积cm³ */
    @ApiModelProperty("包装体积cm³")
    @TableField("MAT_BOX_VOLUME")
    private BigDecimal matBoxVolume;

    /** 体积重g */
    @ApiModelProperty("体积重g")
    @TableField("VOLUME_WEIGHT")
    private BigDecimal volumeWeight;

    /** 毛重g */
    @ApiModelProperty("毛重g")
    @TableField("PACKAGE_WEIGHT")
    private BigDecimal packageWeight;

    /** 计费重g */
    @ApiModelProperty("计费重g")
    @TableField("COUNT_FEE")
    private BigDecimal countFee;

    /** 总计费重KG */
    @ApiModelProperty("总计费重KG")
    @TableField("TOTAL_COUNT_FEE")
    private BigDecimal totalCountFee;

    /** 物流单维度总计费重KG合计 */
    @TableField("SUM_TOTAL_COUNT_FEE")
    private BigDecimal sumTotalCountFee;

    /** 数据来源：BTB、EBMS */
    @TableField("DATA_TYPE")
    private String dataType;

    /** 数据月份 */
    @ApiModelProperty("数据月份")
    @TableField("DATA_MONTH")
    private String dataMonth;

    /** 币别 */
    @ApiModelProperty("币别")
    @TableField("CURRENCY")
    private String currency;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @TableField("CREATE_USER")
    private String createUser;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @TableField("UPDATE_USER")
    private String updateUser;

    /** 导入异常信息备注 */
    @ExcelProperty(value ="导入异常信息备注")
    @ApiModelProperty("导入异常信息备注")
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 11)
    @TableField(exist = false)
    private String uploadRemark;

}