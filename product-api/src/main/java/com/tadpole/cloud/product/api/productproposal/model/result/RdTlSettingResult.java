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
 * 提案-设置-提案等级审批设置 出参类
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
@TableName("RD_TL_SETTING")
public class RdTlSettingResult implements Serializable, BaseValidatingParam {

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


    @ApiModelProperty("提案等级 值域{'S','A','B','C','D'}")
    private String sysTaLevel;


    @ApiModelProperty("首单采购数量")
    private BigDecimal sysFirstOrderPurQty;


    @ApiModelProperty("首单采购金额")
    private BigDecimal sysFirstOrderPurAmount;


    @ApiModelProperty("是否自动审批 值域{'是','否'}")
    private String sysIsAutoApprove;

}
