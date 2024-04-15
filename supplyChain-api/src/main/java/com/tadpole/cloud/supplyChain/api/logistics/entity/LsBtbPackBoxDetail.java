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
 *  BTB订单发货装箱明细实体类
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
@TableName("LS_BTB_PACK_BOX_DETAIL")
@ExcelIgnoreUnannotated
public class LsBtbPackBoxDetail implements Serializable {

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

    /** 商品名称 */
    @ExcelProperty(value ="商品名称*")
    @TableField("PRODUCT_NAME")
    private String productName;

    /** 物料编码 */
    @ExcelProperty(value ="物料编码*")
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /** 数量 */
    @ExcelProperty(value ="数量*")
    @TableField("QUANTITY")
    private BigDecimal quantity;

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