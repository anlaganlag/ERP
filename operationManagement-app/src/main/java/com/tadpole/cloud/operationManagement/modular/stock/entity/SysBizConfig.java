package com.tadpole.cloud.operationManagement.modular.stock.entity;

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
    * 系统业务配置表
    * </p>
*
* @author lsy
* @since 2022-08-31
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SYS_BIZ_CONFIG")
@ExcelIgnoreUnannotated
public class SysBizConfig implements Serializable {

    private static final long serialVersionUID = 1L;


    /** id */
   @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /** 系统编码 */
    @TableField("APP_CODE")
    private String appCode;

    /** 系统描述 */
    @TableField("APP_DES")
    private String appDes;

    /** 业务编码 */
    @TableField("BIZ_CODE")
    private String bizCode;

    /** 业务描述 */
    @TableField("BIZ_DES")
    private String bizDes;

    /** 下一步执行业务编码 */
    @TableField("NEXT_BIZ_CODES")
    private String nextBizCodes;

    /** 业务操作类型 */
    @TableField("ACTION_TYPE")
    private String actionType;

    /** 业务操作描述 */
    @TableField("ACTION_DES")
    private String actionDes;

    /** 业务操作顺序 */
    @TableField("ACTION_SORT")
    private Integer actionSort;

    /** 业务操作返回结果,根据业务自定义 */
    @TableField("ACTION_RESULT")
    private String actionResult;

    /** 业务操作返回结果描述 */
    @TableField("ACTION_RESULT_DES")
    private String actionResultDes;

    /** 业务操作时间 */
    @TableField("DO_ACTION_TIME")
    private Date doActionTime;

    /** 业务完成时间 */
    @TableField("FINISH_TIME")
    private Date finishTime;

    /** 失败后重复执行次数 */
    @TableField("RETRY_TIMES")
    private Integer retryTimes;

    /** 备注 */
    @TableField("REMARK")
    private String remark;

    /** 业务是否有效或是否开启，0无效/关闭，1有效/开启 */
    @TableField("BIZ_VALID")
    private Integer bizValid;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

}