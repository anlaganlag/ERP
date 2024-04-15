package com.tadpole.cloud.platformSettlement.modular.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * ;
 * @author : LSY
 * @date : 2023-12-19
 */
@ApiModel(value = "",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonAllocParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** id */
    @ApiModelProperty(value = "id")
    private String id ;
 
    /** 期间 */
    @ApiModelProperty(value = "期间")
    private String period ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String site ;
 
    /** 一级部门 */
    @ApiModelProperty(value = "一级部门")
    private String dept1 ;
 
    /** 二级部门 */
    @ApiModelProperty(value = "二级部门")
    private String dept2 ;
 
    /** 三级部门 */
    @ApiModelProperty(value = "三级部门")
    private String dept3 ;
 
    /** 四级部门 */
    @ApiModelProperty(value = "四级部门")
    private String dept4 ;
 
    /** 工号 */
    @ApiModelProperty(value = "工号")
    private String personCode ;
 
    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String personName ;
 
    /** 岗位 */
    @ApiModelProperty(value = "岗位")
    private String position ;
 
    /** 员工状态 */
    @ApiModelProperty(value = "员工状态")
    private String status ;
 
    /** 平台 */
    @ApiModelProperty(value = "平台")
    private String platform ;
 
    /** 待分摊三级部门 */
    @ApiModelProperty(value = "待分摊三级部门")
    private String dept3Alloc ;
 
    /** 确认状态 */
    @ApiModelProperty(value = "确认状态")
    private String confirm ;
 
    /**  */
    @ApiModelProperty(value = "")
    private Date updateTime ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String updateBy ;
 
    /**  */
    @ApiModelProperty(value = "")
    private Date createTime ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String createBy ;


   private List<String> confirmIdList;


}