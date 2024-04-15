package com.tadpole.cloud.platformSettlement.api.finance.model.params;



import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;


import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

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
@TableName("PERSON_ALLOC")
public class PersonAllocParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** id */
    @ApiModelProperty("id")
    private String id;

    /** 期间 */
    @ApiModelProperty("期间")
    private String period;

    @ApiModelProperty("")
    private String site;

    /** 一级部门 */
    @ApiModelProperty("一级部门")
    private String dept1;

    /** 二级部门 */
    @ApiModelProperty("二级部门")
    private String dept2;

    /** 三级部门 */
    @ApiModelProperty("三级部门")
    private String dept3;

    /** 四级部门 */
    @ApiModelProperty("四级部门")
    private String dept4;

    /** 工号 */
    @ApiModelProperty("工号")
    private String personCode;

    /** 姓名 */
    @ApiModelProperty("姓名")
    private String personName;

    /** 岗位 */
    @ApiModelProperty("岗位")
    private String position;

    /** 员工状态 */
    @ApiModelProperty("员工状态")
    private String status;

    /** 平台 */
    @ApiModelProperty("平台")
    private String platform;

    /** 待分摊三级部门 */
    @ApiModelProperty("待分摊三级部门")
    private String dept3Alloc;

    /** 确认状态 */
    @ApiModelProperty("确认状态")
    private BigDecimal confirm;

}
