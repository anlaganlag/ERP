package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  海外仓库存管理操作记录入参类
 * </p>
 *
 * @author cyt
 * @since 2022-07-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OverseasWarehouseManageRecordParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 主表ID */
    @ApiModelProperty("主表ID")
    private BigDecimal parentId;

    /** 操作日期开始时间 */
    @ApiModelProperty("操作日期开始时间 格式：YYYY-MM-DD")
    private String startCreateTime;

    /** 操作日期结束时间 */
    @ApiModelProperty("操作日期结束时间 格式：YYYY-MM-DD")
    private String endCreateTime;

    /** 操作集合 */
    @ApiModelProperty("操作集合")
    private List<String> operateTypes;

    /** 类型集合 */
    @ApiModelProperty("类型集合")
    private List<String> businessTypes;

    /** 入库单号 */
    @ApiModelProperty("入库单号")
    private String inOrder;

    /** 出库单号 */
    @ApiModelProperty("出库单号")
    private String outOrder;

    /** 盘点原因 */
    @ApiModelProperty("盘点原因")
    private String checkReason;
}