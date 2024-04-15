package com.tadpole.cloud.platformSettlement.api.inventory.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* <p>
* 移除货件详情报告
* </p>
*
* @author gal
* @since 2022-04-15
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("REMOVAL_SHIPMENT_DETAIL")
public class RemovalShipmentDetailParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private BigDecimal id;

    @ApiModelProperty("REQUEST_DATE")
    private String requestDate;

    @ApiModelProperty("ORDER_ID")
    private String orderId;

    @ApiModelProperty("SHIPMENT_DATE")
    private String shipmentDate;

    @ApiModelProperty("开始时间（YYYYMM）")
    private String shipmentStartDate;

    @ApiModelProperty("结束时间（YYYYMM）")
    private String shipmentEndDate;

    @ApiModelProperty("SKU")
    private String sku;

    @ApiModelProperty("FNSKU")
    private String fnsku;

    @ApiModelProperty("DISPOSITION")
    private String disposition;

    @ApiModelProperty("SHIPPED_QUANTITY")
    private BigDecimal shippedQuantity;

    @ApiModelProperty("CARRIER")
    private String carrier;

    @ApiModelProperty("TRACKING_NUMBER")
    private String trackingNumber;

    @ApiModelProperty("REMOVAL_ORDER_TYPE")
    private String removalOrderType;

    @ApiModelProperty("SYS_SITE")
    private String sysSite;

    /** 账号简称 */
    @ApiModelProperty("SYS_SHOPS_NAME")
    private String sysShopsName;

    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    @ApiModelProperty("USER_NAME")
    private String userName;

    @ApiModelProperty("SYS_SELLER_ID")
    private String sysSellerId;

    /** 报告文件上传日期(生成上传报告任务的日期) */
    @ApiModelProperty("UPLOAD_DATE")
    private Date uploadDate;

    @ApiModelProperty("VERIFY_AT")
    private Date verifyAt;

    @ApiModelProperty("VERIFY_BY")
    private String verifyBy;

    @ApiModelProperty("VERIFY_STATUS")
    private BigDecimal verifyStatus;

    @ApiModelProperty("CREATE_AT")
    private Date createAt;

    @ApiModelProperty("UPDATE_BY")
    private String updateBy;

    @ApiModelProperty("GENERATE_STATUS")
    private BigDecimal generateStatus;

    @ApiModelProperty("BILL_CODE")
    private String billCode;

    @ApiModelProperty("ORG")
    private String org;

    @ApiModelProperty("库存组织编码")
    private String inventoryOrganizationCode;

    @ApiModelProperty("WAREHOUSE_NAME")
    private String warehouseName;

    @ApiModelProperty("WAREHOUSE_CODE")
    private String warehouseCode;

    @ApiModelProperty("SALES_ORGANIZATION")
    private String salesOrganization;

    @ApiModelProperty("SALES_ORGANIZATION_CODE")
    private String salesOrganizationCode;

    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

    @ApiModelProperty("UPLOAD_MARK")
    private String uploadMark;

    @ApiModelProperty("ORIGINAL_TASK_NAME")
    private String originalTaskName;

    @ApiModelProperty("FILE_PATH")
    private String filePath;

    private List skus;

    @ApiModelProperty("账号集合")
    private List sysShopsNames;

    @ApiModelProperty("站点集合")
    private List sysSites;

    @ApiModelProperty("审核状态集合")
    private List verifyStatuss;

    @ApiModelProperty("生成状态集合")
    private List generateStatuss;

    private List removalOrderTypes;

    private String startDate;

    private String endDate;

    private  List idList;

    private String mat;

    @ApiModelProperty("事业部集合")
    private List<String> departments;

    @ApiModelProperty("team集合")
    private List<String> teams;

    @ApiModelProperty("订单类型集合")
    private List<String> orderTypes;

    @ApiModelProperty("订单状态集合")
    private List<String> orderStatuss;

    @ApiModelProperty(value = "订单类型", hidden = true)
    private String orderType;

    @ApiModelProperty(value = "订单状态", hidden = true)
    private String orderStatus;

    @ApiModelProperty("物料编码集合")
    private List<String> materialCodeList;

    @ApiModelProperty("运营大类集合")
    private List<String> productTypeList;

    @ApiModelProperty("产品名称集合")
    private List<String> productNameList;

    @ApiModelProperty("款式集合")
    private List<String> styleList;
}