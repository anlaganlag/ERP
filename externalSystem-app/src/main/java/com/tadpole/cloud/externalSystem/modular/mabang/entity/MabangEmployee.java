package com.tadpole.cloud.externalSystem.modular.mabang.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("MABANG_EMPLOYEE")
@ExcelIgnoreUnannotated
public class MabangEmployee implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 员工编号，主键 */
   @TableId(value = "EMPLOYEE_ID", type = IdType.ASSIGN_ID)
    private String employeeId;

    /** 员工名称 */
    @TableField("NAME")
    private String name;

    /** 员工状态:1未激活;2正常;3停用;4离职 */
    @TableField("STATUS")
    private String status;

    /** 手机区号 */
    @TableField("MOBILE_AREA_CODE")
    private String mobileAreaCode;

    /** 手机号码 */
    @TableField("MOBILE")
    private String mobile;

    /** 创建时间 */
    @TableField("TIME_CREATED")
    private Date timeCreated;

    /** 京东pin(外部标识) */
    @TableField("JDPIN")
    private String jdpin;


    /** 同步方式（0 ：系统同步,1：手动人工同步） */
    @TableField("SYNC_TYPE")
    private String syncType;

    /** 同步时间(本条记录值最后同步时间 */
    @TableField("SYNC_TIME")
    private Date syncTime;

    /** 同步状态（0 ：同步失败,1：同步成功） */
    @TableField("SYNC_STATUS")
    private String syncStatus;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;


}