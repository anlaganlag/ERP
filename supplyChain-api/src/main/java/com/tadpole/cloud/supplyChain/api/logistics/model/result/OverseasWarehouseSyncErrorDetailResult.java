package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: ty
 * @description: 海外仓同步ERP异常管理明细
 * @date: 2022/12/21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@HeadStyle(fillForegroundColor = 50)
@HeadFontStyle(fontName = "宋体", fontHeightInPoints = 14, bold = true, color = -1)
@ExcelIgnoreUnannotated
public class OverseasWarehouseSyncErrorDetailResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 主表ID */
    @ApiModelProperty("主表ID")
    private BigDecimal parentId;

    /** 平台 */
    @ExcelProperty(value ="平台", index = 0)
    @ApiModelProperty("平台")
    private String platform;

    /** 账号 */
    @ExcelProperty(value ="账号", index = 1)
    @ApiModelProperty("账号")
    private String sysShopsName;

    /** 站点 */
    @ExcelProperty(value ="站点", index = 2)
    @ApiModelProperty("站点")
    private String sysSite;

    /** 仓库名称 */
    @ExcelProperty(value ="仓库名称", index = 3)
    @ApiModelProperty("仓库名称")
    private String warehouseName;

    /** FNSKU */
    @ExcelProperty(value ="FNSKU", index = 4)
    @ApiModelProperty("FNSKU")
    private String fnSku;

    /** SKU */
    @ExcelProperty(value ="SKU", index = 5)
    @ApiModelProperty("SKU")
    private String sku;

    /** 物料编码 */
    @ExcelProperty(value ="物料编码", index = 6)
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 事业部 */
    @ExcelProperty(value ="事业部", index = 17)
    @ApiModelProperty("事业部")
    private String department;

    /** Team */
    @ExcelProperty(value ="Team", index = 18)
    @ApiModelProperty("Team")
    private String team;

    /** 需求人员 */
    @ExcelProperty(value ="需求人员", index = 19)
    @ApiModelProperty("需求人员")
    private String needsUser;

    /** 原账存数量 */
    @ExcelProperty(value ="原账存数量", index = 12)
    @ApiModelProperty("原账存数量")
    private BigDecimal inventoryQuantity;

    /** 更新数量 */
    @ExcelProperty(value ="更新账存数量", index = 13)
    @ApiModelProperty("更新数量")
    private BigDecimal changeInventoryQuantity;

    /** 现账存数量 */
    @ExcelProperty(value ="现账存数量", index = 14)
    @ApiModelProperty("现账存数量")
    private BigDecimal nowInventoryQuantity;

    /** 操作类型 换标，盘点，乐天海外仓出库，国内仓发海外仓，亚马逊仓发海外仓，海外仓发亚马逊仓 */
    @ExcelProperty(value ="业务操作", index = 7)
    @ApiModelProperty("操作类型 换标，盘点，乐天海外仓出库，国内仓发海外仓，亚马逊仓发海外仓，海外仓发亚马逊仓")
    private String operateType;

    /** 入库单号 */
    @ExcelProperty(value ="入库单号", index = 15)
    @ApiModelProperty("入库单号")
    private String inOrder;

    /** 出库单号 */
    @ExcelProperty(value ="出库单号", index = 16)
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

    /** 同步状态名称 */
    @ExcelProperty(value ="同步状态")
    @ApiModelProperty("同步状态名称")
    private String syncErpStatusName;

    /** 同步ERP时间 */
    @ApiModelProperty("同步ERP时间")
    private Date syncErpTime;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    @ExcelProperty(value ="操作时间", index = 22)
    private Date createTime;

    /** 创建人 */
    @ExcelProperty(value ="操作人", index = 21)
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
    @ExcelProperty(value ="原来货数量", index = 9)
    @ApiModelProperty("原来货数量")
    private BigDecimal comeQuantity;

    /** 更新来货数量 */
    @ExcelProperty(value ="更新来货数量", index = 10)
    @ApiModelProperty("更新来货数量")
    private BigDecimal changeComeQuantity;

    /** 现来货数量 */
    @ExcelProperty(value ="现来货数量", index = 11)
    @ApiModelProperty("现来货数量")
    private BigDecimal nowComeQuantity;

    /** 类型：来货，入库，出库，盘盈，盘亏 */
    @ExcelProperty(value ="类型", index = 8)
    @ApiModelProperty("类型：来货，入库，出库，盘盈，盘亏")
    private String businessType;

    /** 备注 */
    @ExcelProperty(value ="备注", index = 20)
    @ApiModelProperty("备注")
    private String remark;
}
