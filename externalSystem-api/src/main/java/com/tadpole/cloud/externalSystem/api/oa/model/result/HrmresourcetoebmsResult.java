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
@TableName("HrmResourceToEbms")
public class HrmresourcetoebmsResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    private long jobtitle;

    private Integer companyid;

    private Integer hid;


    private String lastname;


    private String subcompanyname;


    private String workcode;


    @ApiModelProperty("")
    private String managername;


    @ApiModelProperty("")
    private String managercode;


    @ApiModelProperty("")
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
