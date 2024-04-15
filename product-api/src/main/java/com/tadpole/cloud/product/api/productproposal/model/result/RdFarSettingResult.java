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
 * 提案-设置-研发费用自动过审设置 出参类
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
@TableName("RD_FAR_SETTING")
public class RdFarSettingResult implements Serializable, BaseValidatingParam {

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


    @ApiModelProperty("拿样方式 值域{'现货拿样','定制拿样'}")
    private String sysSampleMethod;


    @ApiModelProperty("审核环节")
    private String sysAuditProcess;


    @ApiModelProperty("单次申请金额")
    private BigDecimal sysSingleAppAmount;


    @ApiModelProperty("累积申请金额")
    private BigDecimal sysAppTotalAmount;


    @ApiModelProperty("任务次数")
    private BigDecimal sysTaskQty;

}
