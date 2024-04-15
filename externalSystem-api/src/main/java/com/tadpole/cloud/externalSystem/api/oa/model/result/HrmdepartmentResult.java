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
 * @since 2023-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("HrmDepartment")
public class HrmdepartmentResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "id", type = IdType.AUTO)
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


    @ApiModelProperty("")
    private Integer budgetatuomoveorder;


    private String ecologyPinyinSearch;


    private Integer tlevel;


    private Date created;


    private Integer creater;


    private Date modified;


    private Integer modifier;


    private String uuid;


    private Float showorder;


    @ApiModelProperty("")
    private Integer showorderoftree;

}
