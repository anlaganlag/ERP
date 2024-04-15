package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.io.Serializable;

/**
* <p>
* 店铺报告币别
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
@TableName("bank_subject_code_mcms")
public class BankSubjectCodeMcms implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 银行账号 */
    @TableField("FNUMBER")
    private String fnumber;

    /** 科目编码 */
    @TableField("SUBJECT_CODE")
    private String subjectCode;

    /** 科目名称 */
    @TableField("CURRENCY")
    private String subjectName;

    /** 币别 */
    @TableField("CURRENCY")
    private String currency;
}