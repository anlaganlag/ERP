package com.tadpole.cloud.operationManagement.modular.stock.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* <p>
    * PMC审核调用k3记录信息
    * </p>
*
* @author lsy
* @since 2022-09-07
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("STOCK_PMC_VERIF_K3")
public class StockPmcVerifK3Param extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 审核记录信息id */
    @ApiModelProperty("ID")
    private String id;

    /** 采购申请单ID,多个ID使用‘,’分割 */
    @ApiModelProperty("PURCHASE_ORDER_ID_LIST")
    private String purchaseOrderIdList;

    /** 数量--合并汇总数量 */
    @ApiModelProperty("QTY")
    private BigDecimal qty;

    /** 值域{"自动"/"人工"} */
    @ApiModelProperty("VERIF_TYPE")
    private String verifType;

    /** 审批人员工编号 */
    @ApiModelProperty("VERIF_PERSON_NO")
    private String verifPersonNo;

    /** 审批人姓名 */
    @ApiModelProperty("VERIF_PERSON_NAME")
    private String verifPersonName;

    /** PMC审批日期 */
    @ApiModelProperty("VERIF_DATE")
    private Date verifDate;

    /** k3业务单据号，同STOCK_PMC_VERIF_INFO表的billNo一致，一对多关系 */
    @ApiModelProperty("BILL_NO")
    private String billNo;

    /** 备货类型--备货类型:RCBH日常备货,JJBH紧急备货,XMBH项目备货,XPBH新品备货，如有不同类型的则为混合类型 */
    @ApiModelProperty("BILL_TYPE")
    private String billType;

    /** 物料编码--取值来源：每日备货推荐.物料编码 */
    @ApiModelProperty("MATERIAL_CODE")
    private String materialCode;

    /** 运营大类--取值来源：每日备货推荐.运营大类 */
    @ApiModelProperty("PRODUCT_TYPE")
    private String productType;

    /** 产品名称--取值来源：每日备货推荐.产品名称 */
    @ApiModelProperty("PRODUCT_NAME")
    private String productName;

    /** 型号--取值来源：每日备货推荐.型号 */
    @ApiModelProperty("MODEL")
    private String model;

    /** 事业部--取值来源：每日备货推荐.事业部 */
    @ApiModelProperty("DEPARTMENT")
    private String department;

    /** Team--取值来源：每日备货推荐.Team */
    @ApiModelProperty("TEAM")
    private String team;

    /** MOQ--取值来源：每日备货推荐.MOQ */
    @ApiModelProperty("MINPOQTY")
    private Long minpoqty;

    /** MOQ:采购起订量备注 */
    @ApiModelProperty("MINPOQTY_NOTES")
    private String minpoqtyNotes;

    /** 拼单起订量 */
    @ApiModelProperty("SPELL_ORDERNUM")
    private Long spellOrdernum;


    /** 拼单起订量备注 */
    @ApiModelProperty("SPELL_ORDERNUM_REMARK ")
    private String spellOrdernumRemark;



    /** 二级类目--取值来源：每日备货推荐.二级类目 */
    @ApiModelProperty("MATSTYLESECONDLABEL")
    private String matstylesecondlabel;

    /** 运营期望交期，汇总取最小值 */
    @ApiModelProperty("OPER_EXPECTED_DATE")
    private Date operExpectedDate;

    /** 采购人员 */
    @ApiModelProperty("PURCHASE_PERSON")
    private String purchasePerson;

    /** 采购人员ID */
    @ApiModelProperty("PURCHASE_PERSON_ID")
    private String purchasePersonId;

    /** 建议供应商 */
    @ApiModelProperty("ADVICE_SUPPLIER")
    private String adviceSupplier;

    /** 建议供应商ID */
    @ApiModelProperty("ADVICE_SUPPLIER_ID")
    private String adviceSupplierId;

    /** 审核说明,事业部审核会填写 */
    @ApiModelProperty("VERIF_REASON")
    private String verifReason;

    /** 备注 */
    @ApiModelProperty("REMARK")
    private String remark;

    /** 同步时间 */
    @ApiModelProperty("SYNC_TIME")
    private Date syncTime;

    /** 同步状态(-1:待同步，0 ：同步失败,1：同步成功) */
    @ApiModelProperty("SYNC_STATUS")
    private String syncStatus;

    /** 同步结果 */
    @ApiModelProperty("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 同步请求参数 */
    @ApiModelProperty("SYNC_REQUEST_MSG")
    private String syncRequestMsg;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

    /** 申请人ID */
    @ApiModelProperty("APPLY_PERSON_ID")
    private String applyPersonId;

    /** 申请人 */
    @ApiModelProperty("APPLY_PERSON")
    private String applyPerson;

    /** 物料属性合集 */
    @ApiModelProperty("MATERIAL_PROPERTIES")
    private String materialProperties;

    /** 规格型号 */
    @ApiModelProperty("MAT_MODE_SPEC")
    private String matModeSpec;

    /** 平台 */
    @ApiModelProperty("PLATFORM")
    private String platform;

    /** 是否含税:1含税，0不含税 */
    @ApiModelProperty("INCLUDE_TAX")
    private BigDecimal includeTax;

    /** 含税的税率：含税时的税率 */
    @ApiModelProperty("TAX_RATE")
    private BigDecimal taxRate;

    /** 下单方式：急单，正常单，... ... */
    @ApiModelProperty("下单方式：急单，正常单")
    private String createOrderType;

    /** 上次下单时间,最近一次下单时间 */
    @ApiModelProperty("ORDER_LAST_TIME")
    private Date orderLastTime;

    /** PMC审批日期查询开始日期 */
    @ApiModelProperty("PMC审批日期查询开始日期")
    private Date verifDateStart;

    /** PMC审批日期查询结束日期 */
    @ApiModelProperty("PMC审批日期查询结束日期")
    private Date verifDateEnd;

    /** 物料编码--取值来源：每日备货推荐.物料编码 */
    @ApiModelProperty("查询字段-物料编码list")
    private List<String> materialCodeList;

    /** 事业部--取值来源：每日备货推荐.事业部 */
    @ApiModelProperty("查询字段-事业部List")
    private List<String> departmentList;

    /** Team--取值来源：每日备货推荐.Team */
    @ApiModelProperty("查询字段-TeamList")
    private List<String> teamList;
    /** 运营大类--取值来源：每日备货推荐.运营大类 */
    @ApiModelProperty("查询字段-运营大类list")
    private List<String> productTypeList;

    /** 建议供应商 */
    @ApiModelProperty("查询字段--建议供应商List")
    private List<String> adviceSupplierList;


    /** 物控专员 */
    @ApiModelProperty("物控专员")
    private String matControlPerson;

}