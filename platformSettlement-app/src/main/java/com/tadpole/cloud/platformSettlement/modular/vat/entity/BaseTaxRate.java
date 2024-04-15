package com.tadpole.cloud.platformSettlement.modular.vat.entity;

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
 * 基础信息-税率表
 * </p>
 *
 * @author cyt
 * @since 2022-08-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("BASE_TAX_RATE")
@ExcelIgnoreUnannotated
public class BaseTaxRate implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 税率id--自增 */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 站点 */
    @TableField("SITE")
    private String site;

    /** 税率 */
    @TableField("TAX_RATE")
    private BigDecimal taxRate;

    /** 生效日期 */
    @TableField("EFFECTIVE_DATE")
    private Date effectiveDate;

    /** 失效日期 */
    @TableField("EXPIRATION_DATE")
    private Date expirationDate;

    /** 状态--默认值：1,【0：失效、1：生效】 */
    @TableField("STATUS")
    private BigDecimal status;

    /** 是否删除--默认值：0，【0：启用、1：删除】 */
    @TableField("IS_DELETE")
    private BigDecimal isDelete;

    /** 版本号 */
    @TableField("VERSION_NUMBER")
    private BigDecimal versionNumber;

    /** 创建时间--默认值：getdate,首次创建 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人编号--默认值：创建人编号，首次创建 */
    @TableField("CREATE_PERSON_NO")
    private String createPersonNo;

    /** 创建人名称--默认值：创建人名称，首次创建 */
    @TableField("CREATE_PERSON_NAME")
    private String createPersonName;

    /** 最后更新时间--默认值：getdate */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 最后更新人编号--默认值：当前修改人编号 */
    @TableField("UPDATE_PERSON_NO")
    private String updatePersonNo;

    /** 最后更新人名称--默认值：当前修改人名称 */
    @TableField("UPDATE_PERSON_NAME")
    private String updatePersonName;

}