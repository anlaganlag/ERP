package com.tadpole.cloud.externalSystem.modular.mabang.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 物料价格信息
    * </p>
*
* @author lsy
* @since 2023-05-06
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("MATERIAL_PRICE_INFO")
@ExcelIgnoreUnannotated
public class MaterialPriceInfo implements Serializable {

    private static final long serialVersionUID = 1L;


    /** ID;默认物料编码 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 物料编码 */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /** 物料名称 */
    @TableField("PRODUCT_NAME")
    private String productName;

    /** 运营大类 */
    @TableField("PRODUCT_TYPE")
    private String productType;

    /** 库存核算价格 */
    @TableField("STOCK_PRICE")
    private BigDecimal stockPrice;

    /** 库存核算日期 */
    @TableField("STOCK_CHECK_DATE")
    private Date stockCheckDate;

    /** 最近一次采购价 */
    @TableField("PURCHASE_PRICE")
    private BigDecimal purchasePrice;

    /** 最近一次采购时间;采购单审核通过日期 */
    @TableField("PURCHASE_DATE")
    private Date purchaseDate;

    /** 临时价格;没有库存核算价和采购价时需要设置临时价格 */
    @TableField("TEMPORARY_PRICE")
    private BigDecimal temporaryPrice;

    /** 没有可用的价格;0:有可用价格，1：没有可用价格 */
    @TableField("NO_PRICE")
    private BigDecimal noPrice;

    /** 备注 */
    @TableField("REMARK")
    private String remark;

    /** 创建人 */
    @TableField("CREATED_BY")
    private String createdBy;

    /** 创建时间 */
    @TableField("CREATED_TIME")
    private Date createdTime;

    /** 更新人 */
    @TableField("UPDATED_BY")
    private String updatedBy;

    /** 更新时间 */
    @TableField("UPDATED_TIME")
    private Date updatedTime;

}