package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author: ty
 * @description: 海外仓同步ERP异常管理入参类
 * @date: 2022/11/15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OverseasWarehouseSyncErrorParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 账号 */
    @ApiModelProperty("账号集合")
    private List<String> sysShopsNames;

    /** 站点 */
    @ApiModelProperty("站点集合")
    private List<String> sysSites;

    /** 仓库名称 */
    @ApiModelProperty("仓库名称集合")
    private List<String> warehouseNames;

    /** FNSKU */
    @ApiModelProperty("FNSKU")
    private String fnSku;

    /** SKU */
    @ApiModelProperty("SKU")
    private String sku;

    /** 物流编码 */
    @ApiModelProperty("物流编码")
    private String materialCode;

    /** 单号 */
    @ApiModelProperty("单号")
    private String mcOrder;

    /** 操作开始时间 */
    @ApiModelProperty("操作开始时间")
    private String startCreateTime;

    /** 操作结束时间 */
    @ApiModelProperty("操作结束时间")
    private String endCreateTime;

    /** 操作 */
    @ApiModelProperty("操作")
    private String operateType;
}
