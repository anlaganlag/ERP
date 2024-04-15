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
 *  BTB订单发货装箱信息实体类
 * </p>
 *
 * @author ty
 * @since 2023-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("LS_BTB_PACK_BOX")
@ExcelIgnoreUnannotated
public class LsBtbPackBox implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** BTB订单号 */
    @TableField("ORDER_NO")
    private String orderNo;

    /** 箱条码 */
    @ExcelProperty(value ="箱条码*")
    @TableField("BOX_BARCODE")
    private String boxBarcode;

    /** 箱号 */
    @ExcelProperty(value ="箱号*")
    @TableField("BOX_NO")
    private String boxNo;

    /** 箱型 */
    @ExcelProperty(value ="箱型*")
    @TableField("BOX_TYPE")
    private String boxType;

    /** 箱长 */
    @ExcelProperty(value ="箱长CM*")
    @TableField("BOX_LENGTH")
    private BigDecimal boxLength;

    /** 箱宽 */
    @ExcelProperty(value ="箱宽CM*")
    @TableField("BOX_WIDTH")
    private BigDecimal boxWidth;

    /** 箱高 */
    @ExcelProperty(value ="箱高CM*")
    @TableField("BOX_HEIGHT")
    private BigDecimal boxHeight;

    /** 体积CBM */
    @TableField("BOX_VOLUME")
    private BigDecimal boxVolume;

    /** 重量KG */
    @ExcelProperty(value ="重量KG*")
    @TableField("BOX_WEIGHT")
    private BigDecimal boxWeight;

    /** 物流状态 */
    @TableField("LOGISTICS_STATUS")
    private String logisticsStatus;

    /** 发货日期 */
    @TableField("SHIPMENT_DATE")
    private Date shipmentDate;

    /** 发货批次号 */
    @TableField("SHIPMENT_NUM")
    private String shipmentNum;

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