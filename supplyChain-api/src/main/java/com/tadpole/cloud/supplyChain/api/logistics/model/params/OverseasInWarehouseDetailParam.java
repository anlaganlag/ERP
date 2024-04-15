package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasInWarehouseDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 海外仓入库管理明细入参类
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
public class OverseasInWarehouseDetailParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 入库单号 */
    @ApiModelProperty("入库单号")
    private String inOrder;

    /** 平台 */
    @ApiModelProperty("平台")
    private String platform;

    /** 账号 */
    @ApiModelProperty("账号")
    private String sysShopsName;

    /** 签收原账号（只针对亚马逊发海外仓JP站点） */
    @ApiModelProperty("签收原账号（只针对亚马逊发海外仓JP站点）")
    private String updateSysShopsName;

    /** 签收新账号（只针对亚马逊发海外仓JP站点签收TS账号） */
    @ApiModelProperty("签收新账号（只针对亚马逊发海外仓JP站点签收TS账号）")
    private String newSysShopsName;

    /** 站点 */
    @ApiModelProperty("站点")
    private String sysSite;

    /** 收货仓名称 */
    @ApiModelProperty("收货仓名称")
    private String inWarehouseName;

    /** 箱条码 */
    @ApiModelProperty("箱条码")
    private String packageBarCode;

    /** 箱号 */
    @ApiModelProperty("箱号")
    private BigDecimal packageNum;

    /** FNSKU */
    @ApiModelProperty("FN_SKU")
    private String fnSku;

    /** SKU */
    @ApiModelProperty("SKU")
    private String sku;

    /** 数量 */
    @ApiModelProperty("数量")
    private BigDecimal quantity;

    /** 实际到货数量 */
    @ApiModelProperty("实际到货数量")
    private BigDecimal actualQuantity;

    /** 签收新账号签收数量（只针对亚马逊发海外仓JP站点签收TS账号） */
    @ApiModelProperty("签收新账号签收数量（只针对亚马逊发海外仓JP站点签收TS账号）")
    private BigDecimal actualNewQuantity;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 需求部门 */
    @ApiModelProperty("需求部门")
    private String department;

    /** 需求Team */
    @ApiModelProperty("需求Team")
    private String team;

    /** 需求人 */
    @ApiModelProperty("需求人")
    private String needsUser;

    /** 承运商 */
    @ApiModelProperty("承运商")
    private String logisticsCompany;

    /** 建议发货方式 */
    @ApiModelProperty("建议发货方式")
    private String suggestTransType;

    /** 物流单号 */
    @ApiModelProperty("物流单号")
    private String logisticsNum;

    /** 签收状态 */
    @ApiModelProperty("签收状态")
    private String confirmStatus;

    /** 签收时间 */
    @ApiModelProperty("签收时间")
    private Date confirmTime;

    /** 签收人 */
    @ApiModelProperty("签收人")
    private String confirmUser;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updateUser;

    /** 操作类型 */
    @ApiModelProperty("操作类型 0:查询所有明细项 1:查询待签收明细项")
    private String operateType;

    /** 签收类型 */
    @ApiModelProperty("签收类型 0:国内仓发海外仓全部签收 1:国内仓发海外仓部分签收 2:亚马逊发海外仓签收")
    private String allSign;

    /** 入库明细集合 */
    @ApiModelProperty("入库明细集合")
    private List<OverseasInWarehouseDetail> detailList;

    /** 物流状态 */
    @ApiModelProperty("物流状态")
    private List<String> logisticsStatuss;
}