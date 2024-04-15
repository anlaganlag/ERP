package com.tadpole.cloud.externalSystem.modular.mabang.model.params;


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

/**
* <p>
    * 物料价格信息
    * </p>
*
* @author lsy
* @since 2023-05-06
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("MATERIAL_PRICE_INFO")
public class MaterialPriceInfoParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID;默认物料编码 */
   @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 物料名称 */
    @ApiModelProperty("物料名称")
    private String productName;

    /** 运营大类 */
    @ApiModelProperty("运营大类")
    private String productType;

    /** 库存核算价格 */
    @ApiModelProperty("库存核算价格")
    private BigDecimal stockPrice;

    /** 库存核算日期 */
    @ApiModelProperty("库存核算日期")
    private Date stockCheckDate;

    /** 最近一次采购价 */
    @ApiModelProperty("最近一次采购价")
    private BigDecimal purchasePrice;

    /** 最近一次采购时间;采购单审核通过日期 */
    @ApiModelProperty("最近一次采购时间;采购单审核通过日期")
    private Date purchaseDate;

    /** 临时价格;没有库存核算价和采购价时需要设置临时价格 */
    @ApiModelProperty("临时价格;没有库存核算价和采购价时需要设置临时价格")
    private BigDecimal temporaryPrice;

    /** 没有可用的价格;0:有可用价格，1：没有可用价格 */
    @ApiModelProperty("没有可用的价格;0:有可用价格，1：没有可用价格")
    private BigDecimal noPrice;

    /** 备注 */
    @ApiModelProperty("备注")
    private String remark;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createdBy;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createdTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updatedBy;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updatedTime;

}
