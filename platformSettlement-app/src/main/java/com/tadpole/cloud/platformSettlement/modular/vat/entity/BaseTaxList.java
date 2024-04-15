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
 * 基础信息-税号列表
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
@TableName("BASE_TAX_LIST")
@ExcelIgnoreUnannotated
public class BaseTaxList implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 税号id--自增 */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 国家缩写--国家简称 */
    @TableField("COUNTRY")
    private String country;

    /** 账号 */
    @TableField("ACCOUNT")
    private String account;
    
    /** 平台 */
    @TableField("ELEPLATFORMNAME")
    private String eleplatformname;

    /** 税号 */
    @TableField("TAX_ID")
    private String taxId;

    /** 是否有税号--默认值：1，【1：是、0：否】 */
    @TableField("IS_TAX_ID")
    private BigDecimal isTaxId;

    /** 税号状态--默认值：1，【1：正常、0：异常】 */
    @TableField("TAX_ID_STATUS")
    private String taxIdStatus;

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