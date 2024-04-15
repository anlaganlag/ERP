package com.tadpole.cloud.externalSystem.api.ebms.model.param;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 海外仓出库管理入参类
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
public class EbmsOverseasOutWarehouseParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 票单号 */
    @ApiModelProperty("票单号")
    private String packCode;

    /** 装箱日期 */
    @ApiModelProperty("装箱日期")
    private String packDate;

    /** 员工编号 */
    @ApiModelProperty("员工编号")
    private String packPerCode;

    /** 员工姓名 */
    @ApiModelProperty("员工姓名")
    private String packPerName;

    /** 店铺简称 */
    @ApiModelProperty("店铺简称")
    private String shopNameSimple;

    /** 站点 */
    @ApiModelProperty("站点")
    private String countryCode;

    /** shopPlatName */
    @ApiModelProperty("shopPlatName")
    private String shopPlatName;

    /** 出货仓类型 */
    @ApiModelProperty("出货仓类型")
    private String comWarehouseType;

    /** 出货仓名称 */
    @ApiModelProperty("出货仓名称")
    private String comWarehouseName;

    /** 收货仓类型 */
    @ApiModelProperty("收货仓类型")
    private String packStoreHouseType;

    /** 收货仓名称 */
    @ApiModelProperty("收货仓名称 ")
    private String packStoreHouseName;

    /** 票单上传状态 */
    @ApiModelProperty("票单上传状态 ")
    private String packUploadState;

    /** 最后更新日期 */
    @ApiModelProperty("最后更新日期 ")
    private String sysUpdDatetime;

    /** comShopPlatName */
    @ApiModelProperty("comShopPlatName")
    private String comShopPlatName;

    /** comShopNameSimple */
    @ApiModelProperty("comShopNameSimple")
    private String comShopNameSimple;

    /** comCountryCode */
    @ApiModelProperty("comCountryCode")
    private String comCountryCode;

    /** 业务类型 */
    @ApiModelProperty("业务类型")
    private String billType;

    /** 箱件明细信息 */
    private List<EbmsOverseasOutWarehouseDetailParam> tbLogisticsPackingListDet2List ;

    /** 箱规格明细信息 */
    private List<EbmsOverseasOutWarehouseDetailBoxInfoParam> tbLogisticsPackingListDet1List ;
}