package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 海外仓库龄报表入参类
 * </p>
 *
 * @author ty
 * @since 2023-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OverseasWarehouseAgeParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 平台 */
    @ApiModelProperty("平台集合")
    private List<String> platforms;

    /** 账号 */
    @ApiModelProperty("账号集合")
    private List<String> sysShopsNames;

    /** 站点 */
    @ApiModelProperty("站点集合")
    private List<String> sysSites;

    /** 仓库名称 */
    @ApiModelProperty("仓库名称集合")
    private List<String> warehouseNames;

    /** SKU */
    @ApiModelProperty("SKU")
    private String sku;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 六位码 */
    @ApiModelProperty("六位码")
    private String sixCode;

    /** 签收开始时间 */
    @ApiModelProperty("签收开始时间")
    private String signStartTime;

    /** 签收完成时间 */
    @ApiModelProperty("签收完成时间")
    private String signEndTime;

    /** 最小库龄天数 */
    @ApiModelProperty("最小库龄天数")
    private Integer minWarehouseAge;

    /** 最大库龄天数 */
    @ApiModelProperty("最大库龄天数")
    private Integer maxWarehouseAge;
}
