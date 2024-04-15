package com.tadpole.cloud.externalSystem.modular.mabang.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 销售退货单
    * </p>
*
* @author cyt
* @since 2022-08-24
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("SALE_RETURN_ORDER")
public class ExportSaleReturnOrderResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 单号 */
    @ExcelProperty(value ="K3直接调拨单号")
    @ApiModelProperty("K3直接调拨单号")
    private String fBillNo;

    /** k3单号 */
    @ApiModelProperty("k3单号")
    private String billNo;


    /** 年份 */
    @ExcelProperty(value ="年份")
    @ApiModelProperty("年份")
    private String years;

    /** 月份 */
    @ExcelProperty(value ="月份")
    @ApiModelProperty("月份")
    private String month;

    /** 平台名称 */
    @ExcelProperty(value ="平台名称")
    @ApiModelProperty("平台名称")
    private String platName;

    /** 店铺名称 */
    @ExcelProperty(value ="店铺名称")
    @ApiModelProperty("店铺名称")
    private String shopName;

    /** 站点:留3个字符位置，全球有一个国家简码是3个字符 */
    @ExcelProperty(value ="站点")
    @ApiModelProperty("站点:留3个字符位置，全球有一个国家简码是3个字符")
    private String siteCode;

    /** 事业部 */
    @ExcelProperty(value ="事业部")
    @ApiModelProperty("事业部")
    private String department;

    /** Team */
    @ExcelProperty(value ="Team")
    @ApiModelProperty("Team")
    private String team;

    /** 仓库名称:根据仓库编码去K3仓库列表作业查询返回仓库名称，平台发展中心这块业务比较特殊，无法直接在MCMS中组装 */
    @ExcelProperty(value ="仓库名称")
    @ApiModelProperty("仓库名称:根据仓库编码去K3仓库列表作业查询返回仓库名称，平台发展中心这块业务比较特殊，无法直接在MCMS中组装")
    private String warehouseName;

    /** 仓库编码:K3中约定客户编码（不带尾巴的金蝶组织编码）对应的仓库编码是一样 */
    @ExcelProperty(value ="仓库编码")
    @ApiModelProperty("仓库编码:K3中约定客户编码（不带尾巴的金蝶组织编码）对应的仓库编码是一样")
    private String warehouseId;

    /** 平台订单编号:传马帮已发货订单作业的platOrdId字段值 */
    @ExcelProperty(value ="平台订单编号")
    @ApiModelProperty("平台订单编号:传马帮已发货订单作业的platOrdId字段值")
    private String platOrdId;

    /** 平台SKU */
    @ApiModelProperty("平台SKU")
    private String platSku;

    /** 退货入库数量:传马帮已退货订单作业的inQuantity字段值 */
    @ExcelProperty(value ="退货入库数量")
    @ApiModelProperty("退货入库数量:传马帮已退货订单作业的inQuantity字段值")
    private BigDecimal returnQty;

    /** 物料入库状态:子单身物料编码的入库状态 */
    @ExcelProperty(value ="物料入库状态")
    @ApiModelProperty("物料入库状态:子单身物料编码的入库状态")
    private String inboundStatus;

    /** 退货单状态,取值：传马帮已退货订单的退货单状态字段 */
    @ExcelProperty(value ="退货单状态")
    @ApiModelProperty("退货单状态,取值：传马帮已退货订单的退货单状态字段")
    private String rtnOrdStatus;

    /** 登记人名称:传马帮已退货订单的登记人字段 */
    @ExcelProperty(value ="登记人")
    @ApiModelProperty("登记人名称:传马帮已退货订单的登记人字段")
    private String employeeName;

    /** 同步时间 */
    @ExcelProperty(value ="马帮退货单抓取时间")
    @ApiModelProperty("同步时间")
    private Date syncTime;

    /** 同步状态（-1:数据初始化，0 ：同步失败,1：同步成功） */
    @ApiModelProperty("同步状态（-1:数据初始化，0 ：同步失败,1：同步成功）")
    private String syncStatus;

    /** 同步状态中文 */
    @ExcelProperty(value ="推送K3状态")
    @ApiModelProperty("同步状态中文")
    private String syncStatusTxt;

    /** 最后一次同步结果消息内容 */
    @ApiModelProperty("最后一次同步结果消息内容")
    private String syncResultMsg;

    /** 库存SKU */
    @ExcelProperty(value ="物料编码")
    @ApiModelProperty("库存SKU")
    private String stockSku;

    /** 销售组织:根据销售组织编码去K3客户列表作业查询返回销售组织名称，平台发展中心这块业务比较特殊，无法直接在MCMS中组装 */
    @ExcelProperty(value ="销售组织")
    @ApiModelProperty("销售组织:根据销售组织编码去K3客户列表作业查询返回销售组织名称，平台发展中心这块业务比较特殊，无法直接在MCMS中组装")
    private String salOrgName;

    /** 销售组织编码:K3中约定客户编码（不带尾巴的金蝶组织编码）对应的仓库编码是一样 */
    @ExcelProperty(value ="销售组织编码")
    @ApiModelProperty("销售组织编码:K3中约定客户编码（不带尾巴的金蝶组织编码）对应的仓库编码是一样")
    private String salOrgCode;

     /** 创建日期 */
     @ExcelProperty(value ="马帮退货单创建时间")
     @ApiModelProperty("创建日期")
     private Date createDate;

    /** 金蝶组织编码:马帮ERP中完整带小尾巴的金蝶组织编码 */
//    @ExcelProperty(value ="金蝶组织编码")
    @ApiModelProperty("金蝶组织编码")
    private String financeCode;

    /** 物料编码 */
//    @ExcelProperty(value ="物料编码")
    @ApiModelProperty("物料编码")
    private String materialCode;

}