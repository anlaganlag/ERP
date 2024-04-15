package com.tadpole.cloud.product.api.productproposal.model.params;

import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.*;

/**
 * <p>
 * 提案-开发样任务进度明细 入参类
 * </p>
 *
 * @author S20190096
 * @since 2024-02-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_SAMPLE_TASK_PROGRESS_DET")
public class RdSampleTaskProgressDetParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统信息-系统编号 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 系统信息-拿样任务编号 */
    @ApiModelProperty("系统信息-拿样任务编号")
    private String sysTsTaskCode;

    /** 系统信息-创建时间 */
    @ApiModelProperty("系统信息-创建时间")
    private Date sysCDate;

    /** 系统信息-员工编号 */
    @ApiModelProperty("系统信息-员工编号")
    private String sysPerCode;

    /** 系统信息-员工姓名 */
    @ApiModelProperty("系统信息-员工姓名")
    private String sysPerName;

    /** 系统信息-明细内容 */
    @ApiModelProperty("系统信息-明细内容")
    private String sysTsContent;

    /** 系统信息-员工姓名集合 */
    @ApiModelProperty("系统信息-员工姓名集合")
    private List<String> sysPerNameList;

    /** 系统信息-创建时间集合 */
    @ApiModelProperty("系统信息-创建时间集合")
    private List<String> sysCDateList;
}
