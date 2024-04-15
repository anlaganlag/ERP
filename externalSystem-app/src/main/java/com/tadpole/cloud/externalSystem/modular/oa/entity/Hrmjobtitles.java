package com.tadpole.cloud.externalSystem.modular.oa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("HrmJobTitles")
@ExcelIgnoreUnannotated
public class Hrmjobtitles implements Serializable {

    private static final long serialVersionUID = 1L;


   @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    private String jobtitlemark;

    private String jobtitlename;

    private String jobtitleremark;

    private Integer jobactivityid;

    private Integer jobdepartmentid;

    private String jobresponsibility;

    private String jobcompetency;

    private Integer jobdoc;

    private String jobtitlecode;

    private String outkey;

    private String ecologyPinyinSearch;

    private Date created;

    private Integer creater;

    private Date modified;

    private Integer modifier;

    private String uuid;

    private String canceled;

}