package com.tadpole.cloud.externalSystem.modular.oa.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *  实体类
 * </p>
 *
 * @author S20190109
 * @since 2023-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("HrmShiftCalendar_Normal")
@ExcelIgnoreUnannotated
public class HrmShiftCalendarNormal implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer Id;

    private String kqdate;

    private Integer serialid;

    private String Remark;

    private String typeDes;

    private String solarTerms;

    private String lunarCalendar;

    private Integer resourceid;

    private Integer locationid;

    private Integer UpdUserId;

    private Date UpDateTime;

}