package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 海外仓库存管理操作记录出参类
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
public class OverseasWarehouseManageRecordResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 主表ID */
    @ApiModelProperty("主表ID")
    private BigDecimal parentId;

    /** 平台 */
    @ApiModelProperty("平台")
    private String platform;

    /** 账号 */
    @ApiModelProperty("账号")
    private String sysShopsName;

    /** 站点 */
    @ApiModelProperty("站点")
    private String sysSite;

    /** 仓库名称 */
    @ApiModelProperty("仓库名称")
    private String warehouseName;

    /** FNSKU */
    @ApiModelProperty("FNSKU")
    private String fnSku;

    /** SKU */
    @ApiModelProperty("SKU")
    private String sku;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 事业部 */
    @ApiModelProperty("事业部")
    private String department;

    /** Team */
    @ApiModelProperty("Team")
    private String team;

    /** 需求人员 */
    @ApiModelProperty("需求人员")
    private String needsUser;

    /** 原账存数量 */
    @ApiModelProperty("原账存数量")
    private BigDecimal inventoryQuantity;

    /** 更新数量 */
    @ApiModelProperty("更新数量")
    private BigDecimal changeInventoryQuantity;

    /** 现账存数量 */
    @ApiModelProperty("现账存数量")
    private BigDecimal nowInventoryQuantity;

    /** 操作类型 换标，盘点，乐天海外仓出库，国内仓发海外仓，亚马逊仓发海外仓，海外仓发亚马逊仓 */
    @ApiModelProperty("操作类型 换标，盘点，乐天海外仓出库，国内仓发海外仓，亚马逊仓发海外仓，海外仓发亚马逊仓")
    private String operateType;

    /** 入库单号 */
    @ApiModelProperty("入库单号")
    private String inOrder;

    /** 出库单号 */
    @ApiModelProperty("出库单号")
    private String outOrder;

    /** 同步EBMS状态 0：未处理，1：处理成功，-1：处理失败 */
    @ApiModelProperty("同步EBMS状态 0：未处理，1：处理成功，-1：处理失败")
    private String syncEbmsStatus;

    /** 同步EBMS时间 */
    @ApiModelProperty("同步EBMS时间")
    private Date syncEbmsTime;

    /** 同步ERP状态 0：未处理，1：处理成功，-1：处理失败 */
    @ApiModelProperty("同步ERP状态 0：未处理，1：处理成功，-1：处理失败")
    private String syncErpStatus;

    /** 同步ERP时间 */
    @ApiModelProperty("同步ERP时间")
    private Date syncErpTime;

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

    /** 操作日期 */
    @ApiModelProperty("操作日期")
    private Date createDate;

    /** 操作日期（YYYY-MM-DD） */
    @ApiModelProperty("操作日期（YYYY-MM-DD）")
    private String createDateStr;

    /** 操作明细 */
    @ApiModelProperty("操作明细")
    private String operateTypeDetail;

    /** 原来货数量 */
    @ApiModelProperty("原来货数量")
    private BigDecimal comeQuantity;

    /** 更新来货数量 */
    @ApiModelProperty("更新来货数量")
    private BigDecimal changeComeQuantity;

    /** 现来货数量 */
    @ApiModelProperty("现来货数量")
    private BigDecimal nowComeQuantity;

    /** 类型：来货，入库，出库，盘盈，盘亏 */
    @ApiModelProperty("类型：来货，入库，出库，盘盈，盘亏")
    private String businessType;

    /** 备注 */
    @ApiModelProperty("备注")
    private String remark;
}