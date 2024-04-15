package com.tadpole.cloud.operationManagement.modular.shipment.model.params;


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
@TableName("INV_PRODUCT_GALLERY")
public class InvProductGalleryParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** SKU */
    @ApiModelProperty("SKU")
    private String sku;

    /** SKU */
    @ApiModelProperty("skuList")
    private List<String> skuList;

    /** 站点 */
    @ApiModelProperty("站点")
    private String sysSite;

    /** 站点 */
    @ApiModelProperty("站点List")
    private List<String> sysSiteList;

    /** 店铺名简称 */
    @ApiModelProperty("店铺名简称")
    private String sysShopsName;

    /** 店铺名简称 */
    @ApiModelProperty("店铺名简称List")
    private List<String> sysShopsNameList;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 物料编码 */
    @ApiModelProperty("物料编码List")
    private List<String> materialCodeList;

    /** 部门 */
    @ApiModelProperty("部门")
    private String department;

    /** team */
    @ApiModelProperty("team")
    private String team;

    /** asin */
    @ApiModelProperty("asin")
    private String asin;

    /** asin */
    @ApiModelProperty("asin_list")
    private List<String> asinList;

    /** FNSKU */
    @ApiModelProperty("FNSKU")
    private String fnsku;

    @ApiModelProperty("")
    private String labusestate;

    @ApiModelProperty("")
    private String skucode;

    @ApiModelProperty("")
    private String deliveryType;

    /** 最后一次更新时间 */
    @ApiModelProperty("最后一次更新时间")
    private Date lastUpdateDate;

    /** 销售品牌 */
    @ApiModelProperty("销售品牌")
    private String salesBrand;

    /** 状态 */
    @ApiModelProperty("状态")
    private BigDecimal status;

    @ApiModelProperty("")
    private Date etlInsertTm;

    /** 创建日期 */
    @ApiModelProperty("创建日期")
    private Date createDate;

    /** sku类型 */
    @ApiModelProperty("sku类型")
    private String skuType;

    /** asin状态 */
    @ApiModelProperty("asin状态")
    private String asinstatu;

    /** 区域 */
    @ApiModelProperty("区域")
    private String area;

    /** 区域 */
    @ApiModelProperty("区域List")
    private List<String> areaList;

    /** sku标签类型 */
    @ApiModelProperty("sku标签类型")
    private String sysLabelType;

    /** 发货模式 */
    @ApiModelProperty("发货模式")
    private String sysLogiMode;

    /** 运营大类 */
    @ApiModelProperty("运营大类")
    private String productType;

    /** 店铺名称+站点 */
    @ApiModelProperty("店铺名称+站点")
    private String shopName;

}
