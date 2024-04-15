package com.tadpole.cloud.operationManagement.modular.shipment.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * AmazSKU数据来源EBMS--TbAmazSKU
    * </p>
*
* @author lsy
* @since 2023-02-03
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("INV_PRODUCT_GALLERY")
public class InvProductGalleryResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("SKU")
    private String sku;


    @ApiModelProperty("站点")
    private String sysSite;


    @ApiModelProperty("店铺名简称")
    private String sysShopsName;


    @ApiModelProperty("物料编码")
    private String materialCode;


    @ApiModelProperty("部门")
    private String department;


    @ApiModelProperty("team")
    private String team;


    @ApiModelProperty("asin")
    private String asin;


    @ApiModelProperty("FNSKU")
    private String fnsku;


    @ApiModelProperty("")
    private String labusestate;


    @ApiModelProperty("")
    private String skucode;


    @ApiModelProperty("")
    private String deliveryType;


    @ApiModelProperty("最后一次更新时间")
    private Date lastUpdateDate;


    @ApiModelProperty("销售品牌")
    private String salesBrand;


    @ApiModelProperty("状态")
    private BigDecimal status;


    @ApiModelProperty("")
    private Date etlInsertTm;


    @ApiModelProperty("创建日期")
    private Date createDate;


    @ApiModelProperty("sku类型")
    private String skuType;


    @ApiModelProperty("asin状态")
    private String asinstatu;


    @ApiModelProperty("区域")
    private String area;


    @ApiModelProperty("sku标签类型")
    private String sysLabelType;


    @ApiModelProperty("发货模式")
    private String sysLogiMode;


    @ApiModelProperty("运营大类")
    private String productType;


    @ApiModelProperty("店铺名称+站点")
    private String shopName;

}
