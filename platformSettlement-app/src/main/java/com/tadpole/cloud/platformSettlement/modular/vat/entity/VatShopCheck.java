package com.tadpole.cloud.platformSettlement.modular.vat.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
 * <p>
 * 账号检查表
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("VAT_SHOP_CHECK")
@ExcelIgnoreUnannotated
public class VatShopCheck implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 汇率id--自增 */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 期间 */
    @TableField("ACTIVITY_PERIOD")
    private String activityPeriod;

    /** ebms账号 */
    @TableField("EBMS_SHOPS_NAME")
    private String ebmsShopsName;

    /** VAT报告账号 */
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    /** 异常类型 */
    @TableField("ABNORMAL_TYPE")
    private String abnormalType;

    /** 备注 */
    @TableField("REMARK")
    private String remark;

    /** 创建人 */
    @TableField("CREATE_PERSON")
    private String createPerson;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 最后更新时间--默认值：getdate */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 最后更新人名称--默认值：当前修改人名称 */
    @TableField("UPDATE_PERSON")
    private String updatePerson;

}