package com.tadpole.cloud.platformSettlement.api.finance.model.params;


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
    * 
    * </p>
*
* @author S20210103
* @since 2023-12-19
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("PERSON_ALLOC")
public class PersonAllocResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("id")
    private String id;


    @ApiModelProperty("期间")
    private String period;


    @ApiModelProperty("")
    private String site;


    @ApiModelProperty("一级部门")
    private String dept1;


    @ApiModelProperty("二级部门")
    private String dept2;


    @ApiModelProperty("三级部门")
    private String dept3;


    @ApiModelProperty("四级部门")
    private String dept4;


    @ApiModelProperty("工号")
    private String personCode;


    @ApiModelProperty("姓名")
    private String personName;


    @ApiModelProperty("岗位")
    private String position;


    @ApiModelProperty("员工状态")
    private String status;


    @ApiModelProperty("平台")
    private String platform;


    @ApiModelProperty("待分摊三级部门")
    private String dept3Alloc;


    @ApiModelProperty("确认状态")
    private BigDecimal confirm;

}
