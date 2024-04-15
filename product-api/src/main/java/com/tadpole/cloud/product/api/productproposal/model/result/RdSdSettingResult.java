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
 * 提案-设置-拿样任务时长设置 出参类
 * </p>
 *
 * @author S20190096
 * @since 2023-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("RD_SD_SETTING")
public class RdSdSettingResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;


    @ApiModelProperty("系统实时时间")
    private Date sysCDate;


    @ApiModelProperty("系统实时时间")
    private Date sysLDate;


    @ApiModelProperty("登陆用户员工编号")
    private String sysPerCode;


    @ApiModelProperty("登陆用户员工姓名")
    private String sysPerName;


    @ApiModelProperty("样品类型 值域{'现货拿样','定制拿样'}")
    private String sysSampleType;


    @ApiModelProperty("反馈时长")
    private BigDecimal sysFebkDuration;


    @ApiModelProperty("拿样时长")
    private BigDecimal sysSamplingDuration;

}
