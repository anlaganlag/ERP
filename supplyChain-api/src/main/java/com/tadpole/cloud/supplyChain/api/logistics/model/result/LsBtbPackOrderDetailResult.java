package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * BTB订单发货明细信息 出参类
 * </p>
 *
 * @author ty
 * @since 2023-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("LS_BTB_PACK_ORDER_DETAIL")
public class LsBtbPackOrderDetailResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("BTB订单明细ID")
    private String orderDetailId;

    @ApiModelProperty("SKU")
    private String sku;

    @ApiModelProperty("物料编码")
    private String materialCode;

    @ApiModelProperty("商品中文名称")
    private String productInfo;

    @ApiModelProperty("数量")
    private Integer quantity;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

}
