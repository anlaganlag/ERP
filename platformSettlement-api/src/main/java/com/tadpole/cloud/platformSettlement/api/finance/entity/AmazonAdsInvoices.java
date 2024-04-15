package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <Description> <br>
 *
 * @author Amte Ma<br>
 * @version 1.0<br>
 * @date 2023/05/04 <br>
 */
@Data
public class AmazonAdsInvoices implements Serializable {

    private static final long serialVersionUID = 1L;


    /*@TableId(type = IdType.AUTO)
    */
    private Long id;

    @TableId(type = IdType.ASSIGN_ID)
    private String invoiceId;

    /**
     * status
     */
    private String status;

    /**
     * from_date
     */
    private Date fromDate;

    /**
     * to_date
     */
    private Date toDate;

    /**
     * invoice_date
     */
    private Date invoiceDate;

    /**
     * amount
     */
    private double amount;
    private double tax;

    /**
     * currency
     */
    private String currency;

    /**
     * create_time
     */
    private Date createTime;

    /**
     * seller_id
     */
    private String sellerId;

    /**
     * account_name
     */
    private String accountName;

    /**
     * country_code
     */
    private String countryCode;


    @TableField(exist = false)
    private String shopName;
}
