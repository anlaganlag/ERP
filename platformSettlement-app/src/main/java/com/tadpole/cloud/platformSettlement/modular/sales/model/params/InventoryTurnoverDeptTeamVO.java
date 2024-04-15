package com.tadpole.cloud.platformSettlement.modular.sales.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 库存周转
 * </p>
 *
 * @author cyt
 * @since 2022-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("INVENTORY_TURNOVER")
public class InventoryTurnoverDeptTeamVO implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 事业部 */
    @ApiModelProperty("DEPARTMENT")
    private String department;

    /** Team */
    @ApiModelProperty("TEAM")
    private String team;
}