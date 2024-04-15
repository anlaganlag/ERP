package com.tadpole.cloud.supplyChain.api.logistics.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *  BTB订单发货实体类
 * </p>
 *
 * @author ty
 * @since 2023-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("LS_BTB_PACK_ORDER")
@ExcelIgnoreUnannotated
public class LsBtbPackOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** BTB订单号 */
    @TableField("ORDER_NO")
    private String orderNo;

    /** 订单状态 */
    @TableField("ORDER_STATUS")
    private String orderStatus;

    /** 订单日期 */
    @TableField("ORDER_DATE")
    private Date orderDate;

    /** 平台 */
    @TableField("PLATFORM")
    private String platform;

    /** 账号 */
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    /** 站点 */
    @TableField("SYS_SITE")
    private String sysSite;

    /** 公司名称 */
    @TableField("COMPANY_STREET")
    private String companyStreet;

    /** 收货国家中文名称 */
    @TableField("RECEIVE_COUNTRY_NAME_CN")
    private String receiveCountryNameCn;

    /** 收货国家英文名称 */
    @TableField("RECEIVE_COUNTRY_NAME_EN")
    private String receiveCountryNameEn;

    /** 收货国家编码 */
    @TableField("RECEIVE_COUNTRY_CODE")
    private String receiveCountryCode;

    /** 收件人 */
    @TableField("BUYER_NAME")
    private String buyerName;

    /** 联系电话 */
    @TableField("PHONE1")
    private String phone1;

    /** 城市 */
    @TableField("CITY")
    private String city;

    /** 州/省/郡 */
    @TableField("PROVINCE")
    private String province;

    /** 地址1 */
    @TableField("STREET1")
    private String street1;

    /** 地址2 */
    @TableField("STREET2")
    private String street2;

    /** 邮编 */
    @TableField("POST_CODE")
    private String postCode;

    /** 物料编码 */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /** 总重量KG */
    @TableField("TOTAL_WEIGHT")
    private BigDecimal totalWeight;

    /** 总体积CBM */
    @TableField("TOTAL_VOLUME")
    private BigDecimal totalVolume;

    /** 总数量 */
    @TableField("TOTAL_QUANTITY")
    private BigDecimal totalQuantity;

    /** 物流状态 未发货，部分发货，已发货，物流完结 */
    @TableField("LOGISTICS_STATUS")
    private String logisticsStatus;

    /** 发货类型：物流部发货，业务部发货 */
    @TableField("SHIPMENT_TYPE")
    private String shipmentType;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @TableField("CREATE_USER")
    private String createUser;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @TableField("UPDATE_USER")
    private String updateUser;

}