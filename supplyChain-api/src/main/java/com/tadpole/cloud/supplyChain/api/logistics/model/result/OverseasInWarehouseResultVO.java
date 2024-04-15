package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
*
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
@ExcelIgnoreUnannotated
public class OverseasInWarehouseResultVO implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 主表ID */
    @ApiModelProperty("PARENT_ID")
    private BigDecimal parentId;

    /** 入库单号 */
    @ApiModelProperty("入库单号")
    @ExcelProperty(value ="入库单号")
    private String inOrder;

    /** 平台 */
    @ApiModelProperty("平台")
    @ExcelProperty(value ="平台")
    private String platform;

    /** 账号 */
    @ApiModelProperty("账号")
    @ExcelProperty(value ="账号")
    private String sysShopsName;

    /** 站点 */
    @ApiModelProperty("站点")
    @ExcelProperty(value ="站点")
    private String sysSite;

    /** 业务类型 */
    @ApiModelProperty("业务类型")
    @ExcelProperty(value ="业务类型")
    private String operateType;

    /** 应入库数量 */
    @ApiModelProperty("应入库数量")
    @ExcelProperty(value ="应入库数量")
    private BigDecimal shouldInventoryQuantity;

    /** 已到货数量 */
    @ApiModelProperty("已到货数量")
    @ExcelProperty(value ="已到货数量")
    private BigDecimal alreadyInventoryQuantity;

    /** 未到货数量 */
    @ApiModelProperty("未到货数量")
    @ExcelProperty(value ="未到货数量")
    private BigDecimal notInventoryQuantity;

    /** SKU种类数量 */
    @ApiModelProperty("SKU种类数量")
    @ExcelProperty(value ="SKU种类数量")
    private BigDecimal skuNum;

    /** 总箱数 */
    @ApiModelProperty("总箱数")
    @ExcelProperty(value ="总箱数")
    private BigDecimal totalPackageNum;

    /** 出货仓名称 */
    @ApiModelProperty("出货仓名称")
    @ExcelProperty(value ="出货仓名称")
    private String outWarehouseName;

    /** 收货仓名称 */
    @ApiModelProperty("收货仓名称")
    @ExcelProperty(value ="收货仓名称")
    private String inWarehouseName;

    /** 签收状态 0：待签收，1：部分签收，2：全部签收完成 */
    @ApiModelProperty("签收状态")
    @ExcelProperty(value ="签收状态")
    private String confirmStatus;

    /** 签收开始时间 */
    @ApiModelProperty("签收开始时间")
    @ExcelProperty(value ="签收开始时间")
    private Date confirmStartTime;

    /** 签收完成时间 */
    @ApiModelProperty("签收完成时间")
    @ExcelProperty(value ="签收完成时间")
    private Date confirmEndTime;

    /** 签收人，多个签收人用英文逗号分割 */
    @ApiModelProperty("签收人")
    @ExcelProperty(value ="签收人")
    private String confirmUser;

    /** 备注 */
    @ApiModelProperty("备注")
    @ExcelProperty(value ="备注")
    private String remark;




    /** 箱条码 */
    @ApiModelProperty("箱条码")
    @ExcelProperty(value ="箱条码")
    private String packageBarCode;

    /** 箱号 */
    @ApiModelProperty("箱号")
    @ExcelProperty(value ="箱号")
    private BigDecimal packageNum;

    /** FNSKU */
    @ApiModelProperty("FN_SKU")
    @ExcelProperty(value ="FNSKU")
    private String fnSku;

    /** SKU */
    @ApiModelProperty("SKU")
    @ExcelProperty(value ="SKU")
    private String sku;

    /** 数量 */
    @ApiModelProperty("数量")
    @ExcelProperty(value ="数量")
    private BigDecimal quantity;

    /** 实际到货数量 */
    @ApiModelProperty("实际到货数量")
    @ExcelProperty(value ="实际到货数量")
    private BigDecimal actualQuantity;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    @ExcelProperty(value ="物料编码")
    private String materialCode;

    /** 需求部门 */
    @ApiModelProperty("需求部门")
    @ExcelProperty(value ="需求部门")
    private String department;

    /** 需求Team */
    @ApiModelProperty("需求Team")
    @ExcelProperty(value ="需求Team")
    private String team;

    /** 需求人 */
    @ApiModelProperty("需求人")
    @ExcelProperty(value ="需求人")
    private String needsUser;

    /** 承运商 */
    @ApiModelProperty("承运商")
    @ExcelProperty(value ="承运商")
    private String logisticsCompany;

    /** 建议发货方式 */
    @ApiModelProperty("建议发货方式")
    @ExcelProperty(value ="建议发货方式")
    private String suggestTransType;

    /** 物流单号 */
    @ApiModelProperty("物流单号")
    @ExcelProperty(value ="物流单号")
    private String logisticsNum;

    /** 签收状态 */
    @ApiModelProperty("明细_签收状态")
    @ExcelProperty(value ="签收状态")
    private String detailConfirmStatus;

    /** 签收时间 */
    @ApiModelProperty("明细_签收时间")
    @ExcelProperty(value ="签收时间")
    private Date confirmTime;

    /** 签收人 */
    @ApiModelProperty("明细_签收人")
    private String detailConfirmUser;


}