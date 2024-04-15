package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.io.Serializable;

/**
* <p>
* datarange主数据
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@TableName("Account_Mcms_View")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Account_Id */
    @TableId("Account_Id")
    private Integer accountId;

    @TableField("Account_Parent_Id")
    private Integer accountParentId;

    /** 科目编码 */
    @TableField("Account_Number")
    private String accountNumber;

    /** 科目名称 */
    @TableField("Account_Name")
    private String accountName;
}