package com.tadpole.cloud.platformSettlement.api.finance.model.result;


import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.*;

/**
* <p>
    * Amazon广告费用账单
    * </p>
*
* @author S20190161
* @since 2023-07-13
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("AMAZON_ADS_INVOICES")
public class AmazonAdsInvoicesResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty("账号")
    @TableField(exist = false)
    private String shopName;

    @ExcelIgnore
    private Long id;

    @ExcelProperty("站点")
    private String countryCode;

    @ExcelProperty("StoreName")
    private String accountName;





    @ExcelProperty("账单ID")
    private String invoiceId;


    @ExcelProperty("状态")
    private String status;


    @ExcelProperty("开始日期")
    @DateTimeFormat("yyyy/MM/dd")
    private Date fromDate;


    @ExcelProperty("截止日期")
    @DateTimeFormat("yyyy/MM/dd")
    private Date toDate;


    @ExcelProperty("账单日期")
    @DateTimeFormat("yyyy/MM/dd")
    private Date invoiceDate;


    @ExcelProperty("金额")
    private BigDecimal amount;

    @ExcelProperty("税")
    private BigDecimal tax;

    @ExcelProperty("币种")
    private String currency;

    @ExcelIgnore
    private Date createTime;


    @ExcelProperty("sellerId")
    private String sellerId;




}
