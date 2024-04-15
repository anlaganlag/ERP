package com.tadpole.cloud.product.api.productproposal.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
 * <p>
 * 提案-定品明细 实体类
 * </p>
 *
 * @author S20210221
 * @since 2024-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_CUST_PRODUCT_DET2")
@ExcelIgnoreUnannotated
public class RdCustProductDet2 implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统信息-系统编号 */
   @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 系统信息-定品申请编号 */
    @TableField("SYS_CP_CODE")
    private String sysCpCode;

    /** 申请信息-产品分类 */
    @TableField("SYS_CP_PRODUCT_CLASS")
    private String sysCpProductClass;

    /** 系统信息-开发样编号 */
    @TableField("SYS_KFY_CODE")
    private String sysKfyCode;

    /** 申请信息-颜色 */
    @TableField("SYS_CP_COLOR")
    private String sysCpColor;

    /** 申请信息-公司品牌 */
    @TableField("SYS_CP_COM_BRAND")
    private String sysCpComBrand;

}