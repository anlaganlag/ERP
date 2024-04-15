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
 *  海外仓库存管理出参类
 * </p>
 *
 * @author cyt
 * @since 2022-07-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class OverseasWarehouseManageResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 平台 */
    @ExcelProperty(value ="平台")
    @ApiModelProperty("平台")
    private String platform;

    /** 账号 */
    @ExcelProperty(value ="账号")
    @ApiModelProperty("账号")
    private String sysShopsName;

    /** 站点 */
    @ExcelProperty(value ="站点")
    @ApiModelProperty("站点")
    private String sysSite;

    /** 仓库名称 */
    @ExcelProperty(value ="仓库名称")
    @ApiModelProperty("仓库名称")
    private String warehouseName;

    /** FNSKU */
    @ExcelProperty(value ="FNSKU")
    @ApiModelProperty("FNSKU")
    private String fnSku;

    /** SKU */
    @ExcelProperty(value ="SKU")
    @ApiModelProperty("SKU")
    private String sku;

    /** 物料编码 */
    @ExcelProperty(value ="物料编码")
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 来货数量 */
    @ExcelProperty(value ="来货数量")
    @ApiModelProperty("来货数量")
    private BigDecimal comeQuantity;

    /** 账存数量 */
    @ExcelProperty(value ="账存数量")
    @ApiModelProperty("账存数量")
    private BigDecimal inventoryQuantity;

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

    /** 事业部 */
    @ApiModelProperty("事业部")
    private String department;

    /** Team */
    @ApiModelProperty("Team")
    private String team;
}