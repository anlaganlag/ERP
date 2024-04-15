package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;

/**
* <p>
* 设置期初余额
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("CW_INITIAL_BALANCE")
@ExcelIgnoreUnannotated
public class InitialBalance implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(type = IdType.AUTO)
    private BigDecimal id;

    /** 平台 */
    @TableField("PLATFORM_NAME")
    @ExcelProperty(value= "平台")
    private String platformName;

    /** 账号 */
    @TableField("SHOP_NAME")
    @ExcelProperty(value= "账号")
    private String shopName;

    /** 站点 */
    @TableField("SITE")
    @ExcelProperty(value= "站点")
    private String site;

    /** 会计期间 */
    @TableField("FISCAL_PERIOD")
    @ExcelProperty(value= "会计期间")
    private String fiscalPeriod;

    /** 期初余额 */
    @TableField("INITIAL_BALANCE")
    @ExcelProperty(value= "期初余额")
    private BigDecimal initialBalance;

    /** 创建时间 */
    @TableField("CREATE_AT")
    private LocalDateTime createAt;

    /** 修改时间 */
    @TableField("UPDATE_AT")
    private LocalDateTime updateAt;

    /** 创建人 */
    @TableField("CREATE_BY")
    private String createBy;

    /** 修改人 */
    @TableField("UPDATE_BY")
    private String updateBy;
//
//    @ExcelProperty(value= "导入状态")
//    private String uploadStatus;

}