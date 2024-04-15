package com.tadpole.cloud.externalSystem.api.oa.model.result;

import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 *  出参类
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
@ExcelIgnoreUnannotated
@TableName("HrmJobTitles")
public class HrmjobtitlesResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "id", type = IdType.AUTO)
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
