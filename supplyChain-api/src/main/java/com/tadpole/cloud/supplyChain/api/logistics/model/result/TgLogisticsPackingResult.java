package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsApplyDetail;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsClearanceDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: ty
 * @description: EBMS出货清单
 * @date: 2023/6/20
 */
@Data
public class TgLogisticsPackingResult {

    /** 出货清单号 */
    @ApiModelProperty("出货清单号")
    private String packCode;

    /** 平台 */
    @ApiModelProperty("平台")
    private String platform;

    /** 账号 */
    @ApiModelProperty("账号")
    private String shopName;

    /** 站点 */
    @ApiModelProperty("站点")
    private String site;

    /** 件数 */
    @ApiModelProperty("件数")
    private Integer quantity;

    /** 总重量 */
    @ApiModelProperty("总重量")
    private BigDecimal packTotalWeight;

    /** 装箱日期 */
    @ApiModelProperty("装箱日期")
    private String packDate;

    /** 是否关联 */
    @ApiModelProperty("是否关联")
    private String isDeal;

    /** 是否选中 */
    @ApiModelProperty("是否选中 true：选中，false：未选中")
    private Boolean isSelect;

    /** shipmentID */
    @ApiModelProperty("shipmentID")
    private String shipmentID;

    /** 出货清单号集合 */
    @ApiModelProperty("出货清单号集合")
    private List<String> packCodeList;

    /** 报关单出货清单明细集合 */
    @ApiModelProperty("报关单出货清单明细集合")
    private List<TgCustomsApplyDetail> applyPackDetailList;

    /** 清关单出货清单明细集合 */
    @ApiModelProperty("清关单出货清单明细集合")
    private List<TgCustomsClearanceDetail> clearancePackDetailList;

}
