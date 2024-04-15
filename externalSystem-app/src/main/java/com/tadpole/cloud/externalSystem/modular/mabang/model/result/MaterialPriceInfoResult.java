package com.tadpole.cloud.externalSystem.modular.mabang.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
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
@ExcelIgnoreUnannotated
@TableName("MATERIAL_PRICE_INFO")
public class MaterialPriceInfoResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "ID", type = IdType.AUTO)
    private String id;


    @ApiModelProperty("物料编码")
    private String materialCode;


    @ApiModelProperty("物料名称")
    private String productName;


    @ApiModelProperty("运营大类")
    private String productType;


    @ApiModelProperty("库存核算价格")
    private BigDecimal stockPrice;


    @ApiModelProperty("库存核算日期")
    private Date stockCheckDate;


    @ApiModelProperty("最近一次采购价")
    private BigDecimal purchasePrice;


    @ApiModelProperty("最近一次采购时间;采购单审核通过日期")
    private Date purchaseDate;


    @ApiModelProperty("临时价格;没有库存核算价和采购价时需要设置临时价格")
    private BigDecimal temporaryPrice;


    @ApiModelProperty("没有可用的价格;0:有可用价格，1：没有可用价格")
    private BigDecimal noPrice;

    @ApiModelProperty("没有可用的价格;0:有可用价格，1：没有可用价格")
    private String noPriceStr;


    @ApiModelProperty("备注")
    private String remark;


    @ApiModelProperty("创建人")
    private String createdBy;


    @ApiModelProperty("创建时间")
    private Date createdTime;


    @ApiModelProperty("更新人")
    private String updatedBy;


    @ApiModelProperty("更新时间")
    private Date updatedTime;

}
