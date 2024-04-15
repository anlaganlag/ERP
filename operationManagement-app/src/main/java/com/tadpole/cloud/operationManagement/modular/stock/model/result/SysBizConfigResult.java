package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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
@ExcelIgnoreUnannotated
@TableName("SYS_BIZ_CONFIG")
public class SysBizConfigResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** id */
   @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /** 系统编码 */
    @ApiModelProperty("APP_CODE")
    private String appCode;

    /** 系统描述 */
    @ApiModelProperty("APP_DES")
    private String appDes;

    /** 业务编码 */
    @ApiModelProperty("BIZ_CODE")
    private String bizCode;

    /** 业务描述 */
    @ApiModelProperty("BIZ_DES")
    private String bizDes;

    /** 下一步执行业务编码 */
    @ApiModelProperty("NEXT_BIZ_CODES")
    private String nextBizCodes;

    /** 业务操作类型 */
    @ApiModelProperty("ACTION_TYPE")
    private String actionType;

    /** 业务操作描述 */
    @ApiModelProperty("ACTION_DES")
    private String actionDes;

    /** 业务操作顺序 */
    @ApiModelProperty("ACTION_SORT")
    private Integer actionSort;

    /** 业务操作返回结果,根据业务自定义 */
    @ApiModelProperty("ACTION_RESULT")
    private String actionResult;

    /** 业务操作返回结果描述 */
    @ApiModelProperty("ACTION_RESULT_DES")
    private String actionResultDes;

    /** 业务操作时间 */
    @ApiModelProperty("DO_ACTION_TIME")
    private Date doActionTime;

    /** 业务完成时间 */
    @ApiModelProperty("FINISH_TIME")
    private Date finishTime;

    /** 失败后重复执行次数 */
    @ApiModelProperty("RETRY_TIMES")
    private Integer retryTimes;

    /** 备注 */
    @ApiModelProperty("REMARK")
    private String remark;

    /** 业务是否有效或是否开启，0无效/关闭，1有效/开启 */
    @ApiModelProperty("BIZ_VALID")
    private Integer bizValid;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

}