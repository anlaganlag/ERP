package com.tadpole.cloud.product.api.productproposal.model.result;

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
 * 提案-开发样盘点 出参类
 * </p>
 *
 * @author S20190096
 * @since 2024-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("RD_SAMPLE_INV")
public class RdSampleInvResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    @TableId(value = "SYS_INV_CODE", type = IdType.ASSIGN_ID)
    private String sysInvCode;


    @ApiModelProperty("系统信息-创建时间")
    private Date sysCDate;


    @ApiModelProperty("系统信息-最后更新时间")
    private Date sysLDate;


    @ApiModelProperty("系统信息-部门编号")
    private String sysDeptCode;


    @ApiModelProperty("系统信息-部门名称")
    private String sysDeptName;


    @ApiModelProperty("系统信息-盘点开始时间")
    private Date sysInvStartDate;


    @ApiModelProperty("系统信息-盘点结束时间")
    private Date sysInvEndDate;


    @ApiModelProperty("系统信息-盘点负责人编号")
    private String sysInvPerCode;


    @ApiModelProperty("系统信息-盘点负责人姓名")
    private String sysInvPerName;


    @ApiModelProperty("系统信息-盘点状态 值域{'盘点中','已完结'}")
    private String sysInvStatus;

}
