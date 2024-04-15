package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 海外仓入库管理入参类
 * </p>
 *
 * @author cyt
 * @since 2022-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EbmsOverseasInWarehouseParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 主表ID */
    @ApiModelProperty("PARENT_ID")
    private BigDecimal parentId;

    /** 入库单号 */
    @ApiModelProperty("入库单号")
    private String inOrder;

    /** 平台 */
    @ApiModelProperty("平台")
    private String platform;

    /** 账号 */
    @ApiModelProperty("账号")
    private String sysShopsName;

    /** 站点 */
    @ApiModelProperty("站点")
    private String sysSite;

    /** 业务类型 */
    @ApiModelProperty("业务类型")
    private String operateType;

    /** 应入库数量 */
    @ApiModelProperty("应入库数量")
    private BigDecimal shouldInventoryQuantity;

    /** 已到货数量 */
    @ApiModelProperty("已到货数量")
    private BigDecimal alreadyInventoryQuantity;

    /** 未到货数量 */
    @ApiModelProperty("未到货数量")
    private BigDecimal notInventoryQuantity;

    /** SKU种类数量 */
    @ApiModelProperty("SKU种类数量")
    private BigDecimal skuNum;

    /** 总箱数 */
    @ApiModelProperty("总箱数")
    private BigDecimal totalPackageNum;

    /** 出货仓名称 */
    @ApiModelProperty("出货仓名称")
    private String outWarehouseName;

    /** 收货仓名称 */
    @ApiModelProperty("收货仓名称")
    private String inWarehouseName;

    /** 签收状态 待签收，部分签收，全部签收完成 */
    @ApiModelProperty("签收状态")
    private String confirmStatus;

    /** 签收开始时间 */
    @ApiModelProperty("签收开始时间")
    private Date confirmStartTime;

    /** 签收完成时间 */
    @ApiModelProperty("签收完成时间")
    private Date confirmEndTime;

    /** 签收人，多个签收人用英文逗号分割 */
    @ApiModelProperty("签收人")
    private String confirmUser;

    /** 备注 */
    @ApiModelProperty("备注")
    private String remark;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createUser;

    /** 出货清单明细 */
    @ApiModelProperty("出货清单明细")
    private List<EbmsOverseasInWarehouseDetailParam> detailList;

}