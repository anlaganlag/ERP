package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 海外仓报表入参类
 * </p>
 *
 * @author cyt
 * @since 2022-09-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OverseasReportParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 出货清单号 */
    @ApiModelProperty("出货清单号")
    private String inOrder;

    /** 账号 */
    @ApiModelProperty("账号")
    private List<String> sysShopsNames;

    /** 站点 */
    @ApiModelProperty("站点")
    private List<String> sysSites;

    /** 业务类型 */
    @ApiModelProperty("业务类型")
    private String operateType;

    /** FNSKU */
    @ApiModelProperty("FNSKU")
    private String fnSku;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 箱条码 */
    @ApiModelProperty("箱条码")
    private String packageBarCode;

    /** SKU */
    @ApiModelProperty("SKU")
    private String sku;

    /** 物流单号 */
    @ApiModelProperty("物流单号")
    private String logisticsNum;

    /** 出货仓 */
    @ApiModelProperty("出货仓")
    private String outWarehouseName;

    /** 收货仓 */
    @ApiModelProperty("收货仓")
    private String inWarehouseName;

    /** 运输方式 */
    @ApiModelProperty("运输方式")
    private String suggestTransType;

    /** 签收开始时间 */
    @ApiModelProperty("签收开始时间")
    private String confirmStartTime;

    /** 签收完成时间 */
    @ApiModelProperty("签收完成时间")
    private String confirmEndTime;

    /** 发货开始时间 */
    @ApiModelProperty("发货开始时间")
    private String shipmentStartTime;

    /** 发货完成时间 */
    @ApiModelProperty("发货完成时间")
    private String shipmentEndTime;

    /** 事业部 */
    @ApiModelProperty("事业部集合")
    private List<String> departmentList;

    /** 需求Team */
    @ApiModelProperty("TEAM集合")
    private List<String> teamList;

    /** 需求人员集合 */
    @ApiModelProperty("需求人员集合")
    private List<String> userList;

    /** 运输方式集合 */
    @ApiModelProperty("运输方式集合")
    private List<String> transTypeList;

    /** 出货仓集合 */
    @ApiModelProperty("出货仓集合")
    private List<String> outWarehouseNames;

    /** 收货仓集合 */
    @ApiModelProperty("收货仓集合")
    private List<String> inWarehouseNames;


}