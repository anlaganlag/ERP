package com.tadpole.cloud.externalSystem.modular.mabang.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
@TableName("SALE_RETURN_ORDER")
public class SaleReturnOrderParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 平台订单编号:传马帮已发货订单作业的platOrdId字段值 */
    @ApiModelProperty("平台订单编号:传马帮已发货订单作业的platOrdId字段值")
    private String platOrdId;

    /** k3退货单号,接口传递给k3 如：XSTHD-209-209-202112000 */
    @ApiModelProperty("k3退货单号,接口传递给k3 如：XSTHD-209-209-202112000")
    private String billNo;

    /** 年份 */
    @ApiModelProperty("年份")
    private String years;

    /** 月份 */
    @ApiModelProperty("月份")
    private String month;

    /** 金蝶组织编码:马帮ERP中完整带小尾巴的金蝶组织编码 */
    @ApiModelProperty("金蝶组织编码:马帮ERP中完整带小尾巴的金蝶组织编码")
    private String financeCode;

    /** 销售组织:根据销售组织编码去K3客户列表作业查询返回销售组织名称，平台发展中心这块业务比较特殊，无法直接在MCMS中组装 */
    @ApiModelProperty("销售组织:根据销售组织编码去K3客户列表作业查询返回销售组织名称，平台发展中心这块业务比较特殊，无法直接在MCMS中组装")
    private String salOrgName;

    /** 销售组织编码:K3中约定客户编码（不带尾巴的金蝶组织编码）对应的仓库编码是一样 */
    @ApiModelProperty("销售组织编码:K3中约定客户编码（不带尾巴的金蝶组织编码）对应的仓库编码是一样")
    private String salOrgCode;

    /** 物料编码:即库存SKU */
    @ApiModelProperty("物料编码:即库存SKU")
    private String stockSku;

    /** 退货数量求和 */
    @ApiModelProperty("退货数量求和")
    private BigDecimal qtySum;

    /** 是否作废Y/N，默认N（代表所有item是否作废） */
    @ApiModelProperty("是否作废Y/N，默认N（代表所有item是否作废）")
    private String isInvalid;

    /** 同步方式（0 ：系统同步,1：手动人工同步） */
    @ApiModelProperty("同步方式（0 ：系统同步,1：手动人工同步）")
    private String syncType;

    /** 同步时间 */
    @ApiModelProperty("同步时间")
    private Date syncTime;

    /** 同步状态（-1：数据初始化，0 ：同步失败,1：同步成功） */
    @ApiModelProperty("同步状态（-1：数据初始化，0 ：同步失败,1：同步成功）")
    private String syncStatus;

    /** 同步失败次数 */
    @ApiModelProperty("同步失败次数")
    private BigDecimal syncFailTimes;

    /** 同步请求消息内容 */
    @ApiModelProperty("同步请求消息内容")
    private String syncRequestMsg;

    /** 最后一次同步结果消息内容 */
    @ApiModelProperty("最后一次同步结果消息内容")
    private String syncResultMsg;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 年份集合 */
    @ApiModelProperty("年份集合")
    private List<String> yearsList;

    /** 月份集合 */
    @ApiModelProperty("月份集合")
    private List<String> monthList;

    /** 平台名称集合 */
    @ApiModelProperty("平台名称集合")
    private List<String> platformNames;

    /** 店铺名称集合 */
    @ApiModelProperty("店铺名称集合")
    private List<String> shopNames;

    /** 站点 */
    @ApiModelProperty("站点")
    private List<String> sites;

    /** 事业部 */
    @ApiModelProperty("事业部")
    private String department;

    /** Team */
    @ApiModelProperty("Team")
    private String team;

    /** 马帮退货单创建日期开始时间 */
    @ApiModelProperty("马帮退货单创建日期开始时间")
    private String startCreateTime;

    /** 马帮退货单创建日期结束时间 */
    @ApiModelProperty("马帮退货单创建日期结束时间")
    private String endCreateTime;

    /** 马帮退货单抓取日期开始时间 */
    @ApiModelProperty("马帮退货单创建日期开始时间")
    private String startSyncTime;

    /** 马帮退货单抓取日期结束时间 */
    @ApiModelProperty("马帮退货单创建日期结束时间")
    private String endSyncTime;

}