package com.tadpole.cloud.platformSettlement.api.inventory.model.params;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.List;
import lombok.*;

/**
* <p>
* 庫存列表3.0
* </p>
*
* @author S20190161
* @since 2022-12-20
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("AMAZON_INVENTORY_LIST")
public class AmazonInventoryListParam extends BaseRequest  {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("")
    private String year;

    /** 月份 */
    @ApiModelProperty("月份")
    private String month;

    /** SKU */
    @ApiModelProperty("SKU")
    private String sku;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 运营大类 */
    @ApiModelProperty("运营大类")
    private String productType;

    /** 产品名称 */
    @ApiModelProperty("产品名称")
    private String productName;

    /** 款式 */
    @ApiModelProperty("款式")
    private String style;

    /** 主材料 */
    @ApiModelProperty("主材料")
    private String mainMaterial;

    /** 图案 */
    @ApiModelProperty("图案")
    private String design;

    /** 公司品牌 */
    @ApiModelProperty("公司品牌")
    private String companyBrand;

    /** 适用品牌或对象 */
    @ApiModelProperty("适用品牌或对象")
    private String fitBrand;

    /** 型号 */
    @ApiModelProperty("型号")
    private String model;

    /** 颜色 */
    @ApiModelProperty("颜色")
    private String color;

    /** 尺码 */
    @ApiModelProperty("尺码")
    private String sizes;

    /** 包装数量 */
    @ApiModelProperty("包装数量")
    private String packing;

    /** 版本 */
    @ApiModelProperty("版本")
    private String version;

    /** 适用机型 */
    @ApiModelProperty("适用机型")
    private String type;

    /** 组合属性 */
    @ApiModelProperty("组合属性")
    private String specmodel;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("站点")
    private List<String> sysSites;

    @ApiModelProperty("账号")
    private List<String> sysShopsNames;

    @ApiModelProperty("库存状态")
    private String inventoryStatus;

    @ApiModelProperty("事业部")
    private  List<String> departments;

    @ApiModelProperty("team")
    private  List<String> teams;

    @ApiModelProperty("销售组织")
    private String salesOrg;

    @ApiModelProperty("是否盈亏 1是0否")
    private  Integer surplus;
}
