package com.tadpole.cloud.operationManagement.modular.stock.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
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
 * 备货申请记录
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
@TableName("STOCK_PURCHASE_ORDERS")
public class StockApplicationRecordParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 数据id */
    @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /*** 事业部 */
    @ApiModelProperty("DEPARTMENT")
    private List<String> departments;

    /** TEAM */
    @ApiModelProperty("TEAM")
    private List<String> teams;

    /** 事业部审核时间开始 */
    @ApiModelProperty("sVERIF_DATE")
    private Date sVerifDateStart;

    /** 事业部审核时间结束 */
    @ApiModelProperty("sVERIF_DATE")
    private Date sVerifDateEnd;

    /** 运营大类 */
    @ApiModelProperty("PRODUCT_TYPE")
    private List<String> productTypes;

    /** 物料编码 */
    @ApiModelProperty("MATERIAL_CODE")
    private List<String> materialCodes;

    /** 采购订单状态:值域{"0:待审核"/"1:不备货"/"2:待计划部审批"/"3:计划未通过"/"4:待PMC审批"/"5:PMC未通过"/"6:已通过"}默认值：待审核 */
    @ApiModelProperty("ORDER_STATUS")
    private List<Integer> orderStatus;

    /** 备货类型集合 */
    @ApiModelProperty("备货类型集合")
    private List<String> billTypes;

    /** 产品名称 */
    @ApiModelProperty("PRODUCT_NAME")
    private String productName;

    /** 适用品牌 */
    @ApiModelProperty("BRAND")
    private String brand;

    /** 款式 */
    @ApiModelProperty("STYLE")
    private String style;

    /** 型号 */
    @ApiModelProperty("MODEL")
    private String model;

    /** 颜色 */
    @ApiModelProperty("COLOR")
    private String color;

    /** 二级类目 */
    @ApiModelProperty("MATSTYLESECONDLABEL")
    private String matstylesecondlabel;

    /** 审核人工号list */
    @ApiModelProperty("VERIF_PERSON_NO_LIST")
    private List<String> verifPersonNoList;


    /** 事业部审核说明 */
    @ApiModelProperty("VERIF_REASON_DEPT")
    private String verifReasonDept;

    /** 计划部审核说明 */
    @ApiModelProperty("VERIF_REASON_PLAN")
    private String verifReasonPlan;


    /** 查询周转天数-开始 */
    @ApiModelProperty("TURNOVER_DAYS_START")
    private BigDecimal turnoverDayStart;

    /** 查询周转天数-结束 */
    @ApiModelProperty("TURNOVER_DAYS_END")
    private BigDecimal turnoverDayEnd;






}