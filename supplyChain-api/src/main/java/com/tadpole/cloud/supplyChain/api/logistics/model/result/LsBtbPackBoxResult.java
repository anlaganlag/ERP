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
 *  出参类
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
@ExcelIgnoreUnannotated
@TableName("LS_BTB_PACK_BOX")
public class LsBtbPackBoxResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty("箱条码")
    private String boxBarcode;

    @ExcelProperty(value ="箱号")
    @ApiModelProperty("箱号")
    private String boxNo;

    @ExcelProperty(value ="箱型")
    @ApiModelProperty("箱型")
    private String boxType;

    @ExcelProperty(value ="重量")
    @ApiModelProperty("重量KG")
    private BigDecimal boxWeight;

    @ExcelProperty(value ="长")
    @ApiModelProperty("箱长")
    private BigDecimal boxLength;

    @ExcelProperty(value ="宽")
    @ApiModelProperty("箱宽")
    private BigDecimal boxWidth;

    @ExcelProperty(value ="高")
    @ApiModelProperty("箱高")
    private BigDecimal boxHeight;

    @ExcelProperty(value ="出货清单号")
    @ApiModelProperty("BTB订单号")
    private String orderNo;

    @ApiModelProperty("体积CBM")
    private BigDecimal boxVolume;

    @ApiModelProperty("物流状态")
    private String logisticsStatus;

    @ApiModelProperty("发货日期")
    private Date shipmentDate;

    /** 发货批次号 */
    @ApiModelProperty("发货批次号")
    private String shipmentNum;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

}
