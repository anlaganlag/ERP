package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @author: ty
 * @description: 海外仓换标报表入参类
 * @date: 2022/10/18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OverseasChangeReportParam {

    private static final long serialVersionUID = 1L;

    /** 出库单号 */
    @ApiModelProperty("出库单号")
    private String outOrder;

    /** 入库单号 */
    @ApiModelProperty("入库单号")
    private String inOrder;

    /** 操作日期开始时间 */
    @ApiModelProperty("操作日期开始时间 格式：YYYY-MM-DD")
    private String startCreateTime;

    /** 操作日期结束时间 */
    @ApiModelProperty("操作日期结束时间 格式：YYYY-MM-DD")
    private String endCreateTime;

    /** 原账号集合 */
    @ApiModelProperty("原账号集合")
    private List<String> oldSysShopsNames;

    /** 原站点集合 */
    @ApiModelProperty("原站点集合")
    private List<String> oldSysSites;

    /** 原FNSKU */
    @ApiModelProperty("原FNSKU")
    private String oldFnSku;

    /** 原SKU */
    @ApiModelProperty("原SKU")
    private String oldSku;

    /** 新账号集合 */
    @ApiModelProperty("新账号集合")
    private List<String> newSysShopsNames;

    /** 新站点集合 */
    @ApiModelProperty("新站点集合")
    private List<String> newSysSites;

    /** 新FNSKU */
    @ApiModelProperty("新FNSKU")
    private String newFnSku;

    /** 新SKU */
    @ApiModelProperty("新SKU")
    private String newSku;

    /** 事业部集合 */
    @ApiModelProperty("事业部集合")
    private List<String> departments;

    /** Team集合 */
    @ApiModelProperty("Team集合")
    private List<String> teams;

    /** 需求人员集合 */
    @ApiModelProperty("需求人员集合")
    private List<String> needsUsers;
}
