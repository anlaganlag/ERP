package com.tadpole.cloud.externalSystem.api.ebms.model.param;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 海外仓出库管理明细入参类
 * </p>
 *
 * @author cyt
 * @since 2022-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EbmsOverseasOutWarehouseDetailParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 编号 */
    @ApiModelProperty("编号")
    private String packDetID;

    /** 票单号 */
    @ApiModelProperty("票单号")
    private String packCode;

    /** SKU */
    @ApiModelProperty("SKU")
    private String sku;

    /** 数量 */
    @ApiModelProperty("数量")
    private BigDecimal quantity;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String mateCode;

    /** 箱号上传名称 */
    @ApiModelProperty("箱号上传名称")
    private String packDetBoxNumUpload;

    /** 箱号 */
    @ApiModelProperty("箱号")
    private BigDecimal packDetBoxNum;

    /** Fnsku */
    @ApiModelProperty("Fnsku")
    private String fnSKU;

    /** 箱条码 */
    @ApiModelProperty("箱条码")
    private String packDetBoxCode;

    /** 拣货单单号 */
    @ApiModelProperty("拣货单单号")
    private String pickListCode;

    /** 需求部门 */
    @ApiModelProperty("需求部门")
    private String depName;

    /** 需求Team */
    @ApiModelProperty("需求Team")
    private String teamName;

    /** 需求人员 */
    @ApiModelProperty("需求人员")
    private String perName;

    /** 建议发货方式 */
    @ApiModelProperty("建议发货方式")
    private String packSugShipMethod;

}