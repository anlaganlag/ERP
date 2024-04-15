package com.tadpole.cloud.externalSystem.modular.oa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
 * <p>
 *  实体类
 * </p>
 *
 * @author S20190109
 * @since 2023-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("HrmResourceToEbms")
@ExcelIgnoreUnannotated
public class Hrmresourcetoebms implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer hid;

    private String lastname;

    private String subcompanyname;

    private String workcode;

    @TableField("managerName")
    private String managername;

    @TableField("managerCode")
    private String managercode;

    @TableField("eQQ")
    private String eqq;

    private String jobtitlename;

    private String status;

    private Integer departmentid;

    private Integer id;

    private Integer ids;

    private String idf;

    private String idall;

    private String departmentname;

    private String departmentname2;

    private String departmentname1;

    private String canceled;

    private String email;

    private String mobile;

    private String dismissdate;

}