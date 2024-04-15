package com.tadpole.cloud.externalSystem.modular.mabang.model.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
    * 马帮员工信息
    * </p>
*
* @author lsy
* @since 2022-06-09
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MabangEmployeeParams implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 员工编号，主键 */
   @ApiModelProperty(value = "EMPLOYEE_ID")
    private String employeeId;

    /** 员工名称 */
    @ApiModelProperty("NAME")
    private String name;

    /** 员工状态:1未激活;2正常;3停用;4离职 */
    @ApiModelProperty("STATUS")
    private String status;

    /** 手机区号 */
    @ApiModelProperty("MOBILE_AREA_CODE")
    private String mobileAreaCode;

    /** 手机号码 */
    @ApiModelProperty("MOBILE")
    private String mobile;

    /** 创建时间 */
    @ApiModelProperty("TIME_CREATED")
    private Date timeCreated;

    /** 京东pin(外部标识) */
    @ApiModelProperty("JDPIN")
    private String jdpin;


    /** 同步方式（0 ：系统同步,1：手动人工同步） */
    @ApiModelProperty("SYNC_TYPE")
    private String syncType;

    /** 同步时间(本条记录值最后同步时间 */
    @ApiModelProperty("SYNC_TIME")
    private Date syncTime;

    /** 同步状态（0 ：同步失败,1：同步成功） */
    @ApiModelProperty("SYNC_STATUS")
    private String syncStatus;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;


}