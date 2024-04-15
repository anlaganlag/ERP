package com.tadpole.cloud.operationManagement.modular.stock.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 审核记录信息
 * </p>
 *
 * @author cyt
 * @since 2022-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("STOCK_PMC_VERIF_INFO")
public class PlanSplitInfoParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    /** 计划部拆单期望交期 */
    @ApiModelProperty("计划部拆单期望交期")
    private Date splitDate;

    /** 计划部拆单数量 */
    @ExcelProperty("计划部拆单数量")
    @ApiModelProperty("计划部拆单数量")
    private BigDecimal splitQty;


}