package com.tadpole.cloud.operationManagement.modular.stock.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
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
* 区域黑名单
*
* @author lsy
* @since 2022-12-19
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("STOCK_AREA_BLACKLIST")
@ExcelIgnoreUnannotated
public class StockAreaBlacklist implements Serializable {

    private static final long serialVersionUID = 1L;


    /** ID */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 平台 */
    @TableField("PLATFORM")
    @ExcelProperty(value ="平台")
    @ApiModelProperty("平台")
    private String platform;

    /** 区域 */
    @TableField("AREA")
    @ExcelProperty(value ="区域")
    @ApiModelProperty("区域")
    private String area;

    /** 运营大类 */
    @TableField("PRODUCT_TYPE")
    @ExcelProperty(value ="运营大类")
    @ApiModelProperty("运营大类")
    private String productType;

    /** 事业部 */
    @TableField("DEPARTMENT")
    @ExcelProperty(value ="事业部")
    @ApiModelProperty("事业部")
    private String department;

    /** Team */
    @TableField("TEAM")
    @ExcelProperty(value ="TEAM")
    @ApiModelProperty("TEAM")
    private String team;

    /** 物料编码 */
    @TableField("MATERIAL_CODE")
    @ExcelProperty(value ="物料编码")
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 状态:黑名单:0 */
    @TableField("STATUS")
    @ExcelProperty(value ="状态")
    @ApiModelProperty("状态")
    private String status;

    /** 创建人 */
    @TableField("CREATED_BY")
    private Date createdBy;

    /** 创建时间 */
    @TableField("CREATED_TIME")
    @ExcelProperty(value ="创建时间")
    @ApiModelProperty("创建时间")
    private Date createdTime;

    /** 更新人 */
    @TableField("UPDATED_BY")
    private Date updatedBy;

    /** 更新时间 */
    @TableField("UPDATED_TIME")
    private Date updatedTime;

}