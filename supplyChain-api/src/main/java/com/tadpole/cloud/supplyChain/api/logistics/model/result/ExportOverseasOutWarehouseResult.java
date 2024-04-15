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
 * 海外仓入库管理出参类
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
public class ExportOverseasOutWarehouseResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 主表ID */
    @ApiModelProperty("主表ID")
    private BigDecimal parentId;

    /** 出库单号 */
    @ApiModelProperty("出库单号")
    @ExcelProperty(value ="出库单号")
    private String outOrder;

    /** 出库日期 */
    @ApiModelProperty("出库日期")
    @ExcelProperty(value ="出库日期")
    private Date createTime;

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

    /** 应出库数量 */
    @ApiModelProperty("应出库数量")
    @ExcelProperty(value ="应出库数量")
    private BigDecimal shouldOutQuantity;

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

    /** 物流状态 未发货，部分发货，全部发货 */
    @ApiModelProperty("物流状态")
    @ExcelProperty(value ="物流状态")
    private String logisticsStatus;

    /** 备注 */
    @ApiModelProperty("备注")
    @ExcelProperty(value ="备注")
    private String remark;

    /** 操作员工 */
    @ApiModelProperty("操作员工")
    @ExcelProperty(value ="操作员工")
    private String createUser;

    /** 同步ERP状态 */
    @ApiModelProperty("同步ERP状态")

    private String syncErpStatus;

    /** 发货时间 */
    @ApiModelProperty("发货时间")
    private Date logisticsTime;

    /** 发货人 */
    @ApiModelProperty("发货人")
    private String logisticsUser;

    /** 箱条码 */
    @ApiModelProperty("箱条码")
    @ExcelProperty(value ="箱条码")
    private String packageBarCode;

    /** 箱号 */
    @ApiModelProperty("箱号")
    @ExcelProperty(value ="箱号")
    private BigDecimal packageNum;

    /** 箱号上传名称 */
    @ApiModelProperty("箱号上传名称")
    @ExcelProperty(value ="箱号上传名称")
    private String packageNumName;

    /** FNSKU */
    @ApiModelProperty("FNSKU")
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

    /** 需求人员 */
    @ApiModelProperty("需求人员")
    @ExcelProperty(value ="需求人员")
    private String needsUser;

    /** 承运商 */
    @ApiModelProperty("承运商")
    @ExcelProperty(value ="承运商")
    private String logisticsCompany;

    /** 物流单号 */
    @ApiModelProperty("物流单号")
    @ExcelProperty(value ="物流单号")
    private String logisticsNum;

}