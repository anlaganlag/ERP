package com.tadpole.cloud.externalSystem.modular.oa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2023-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("HrmDepartment")
@ExcelIgnoreUnannotated
public class Hrmdepartment implements Serializable {

    private static final long serialVersionUID = 1L;


   @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    private String departmentmark;

    private String departmentname;

    private Integer subcompanyid1;

    private Integer supdepid;

    private String allsupdepid;

    private String canceled;

    private String departmentcode;

    private Integer coadjutant;

    private String zzjgbmfzr;

    private String zzjgbmfgld;

    private String jzglbmfzr;

    private String jzglbmfgld;

    private String bmfzr;

    private String bmfgld;

    private String outkey;

    @TableField("budgetAtuoMoveOrder")
    private Integer budgetatuomoveorder;

    private String ecologyPinyinSearch;

    private Integer tlevel;

    private Date created;

    private Integer creater;

    private Date modified;

    private Integer modifier;

    private String uuid;

    private Float showorder;

    @TableField("showOrderOfTree")
    private Integer showorderoftree;

}