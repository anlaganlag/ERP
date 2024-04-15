package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 特殊备货申请V3
    * </p>
*
* @author lsy
* @since 2022-08-31
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("SPECIAL_APPLY_INFO_V3")
public class SpecialApplyInfoV3Result implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 申请数据id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 备货状态--标记此推荐记录是否已进行备货申请操作，值域{"0:数据初始化,1:已提交,2:已合并申请"}默认值：0 */
    @ApiModelProperty("STATUS")
    private BigDecimal status;

    /** 申请人员工编号--默认值：当前操作者员工编号 */
    @ApiModelProperty("APPLY_PERSON_NO")
    private String applyPersonNo;

    /** 申请人姓名--默认值：当前操作者姓名 */
    @ApiModelProperty("APPLY_PERSON_NAME")
    private String applyPersonName;

    /** 申请日期--默认值：getdate */
    @ApiModelProperty("APPLY_DATE")
    private Date applyDate;

    /** 平台--取值来源：每日备货推荐.平台 */
    @ApiModelProperty("PLATFORM")
    private String platform;

    /** 区域-备货区域，备货区域和物理仓关联，目前合并的备货区域只有EU和UX，EU的不同站点间可以互相发货；	墨西哥MX没有可用的物理仓，从US发货，所以就将US和MX合并为北美UX */
    @ApiModelProperty("AREA")
    private String area;

    /** 事业部--取值来源：每日备货推荐.事业部 */
    @ApiModelProperty("DEPARTMENT")
    private String department;

    /** Team--取值来源：每日备货推荐.Team */
    @ApiModelProperty("TEAM")
    private String team;

    /** 物料编码--取值来源：每日备货推荐.物料编码 */
    @ApiModelProperty("MATERIAL_CODE")
    private String materialCode;

    /** 运营大类--取值来源：每日备货推荐.运营大类 */
    @ApiModelProperty("PRODUCT_TYPE")
    private String productType;

    /** 产品名称--取值来源：每日备货推荐.产品名称 */
    @ApiModelProperty("PRODUCT_NAME")
    private String productName;

    /** 备货类型--紧急备货JJBH,项目备货XMBH,特殊备货TSBH */
    @ApiModelProperty("BILL_TYPE")
    private String billType;
    /** JJBH -- 紧急备货
     *  XPBH -- 新品备货
     *  XMBH -- 项目备货
     *  RCBH -- 日常备货
     *             其他
     */
    @ApiModelProperty("billTypeName")
    private String billTypeName;

    /** 销售需求备货天数 */
    @ApiModelProperty("SALES_STOCK_DAYS")
    private BigDecimal salesStockDays;

    /** 销售需求	 */
    @ApiModelProperty("SALES_DEMAND")
    private BigDecimal salesDemand;

    /** 运营期望交期 */
    @ApiModelProperty("OPER_EXPECTED_DATE")
    private Date operExpectedDate;

    /** 申请备货原因 */
    @ApiModelProperty("STOCK_REASON")
    private String stockReason;

    /** 需求运输方式 */
    @ApiModelProperty("DELIVERY_TYPE")
    private String deliveryType;

    /** 当前维度下的其他申请是否都提交--当前维度下的其他申请是否都提交 ： 0 未提交或部分提交，1：全部提交 */
    @ApiModelProperty("ALL_COMIT")
    private BigDecimal allComit;

    /** 当前维度下所有数据都提交了才合并（0,不是，1是） */
    @ApiModelProperty("ALL_COMIT_MERGE")
    private BigDecimal allComitMerge;

    /** 采购申请数量 */
    @ApiModelProperty("APPLY_QTY")
    private BigDecimal applyQty;

    /** 采购申请单号：如果已提交生成采购申请单则反写采购申请单号 */
    @ApiModelProperty("PURCHASE_APPLY_NO")
    private String purchaseApplyNo;

    /** 数据初始化类型：0表格导入，1界面新增 */
    @ApiModelProperty("INIT_TYPE")
    private BigDecimal initType;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

    /** 合并字段值 */
    @ApiModelProperty("MERGE_FIELD")
    private String mergeField;

    /** TEMA审核编号，提交时插入一条数据 */
    @ApiModelProperty("TEAM_VERIF_NO")
    private Integer teamVerifNo;

}