package com.tadpole.cloud.supplyChain.api.logistics.model.params;

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
 *  入参类
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
@TableName("LS_BTB_PACK_BOX_DETAIL")
public class LsBtbPackBoxDetailParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** BTB订单号 */
    @ApiModelProperty("BTB订单号")
    private String orderNo;

    /** 箱条码 */
    @ApiModelProperty("箱条码")
    private String boxBarcode;

    /** 箱号 */
    @ApiModelProperty("箱号")
    private String boxNo;

    /** 商品名称 */
    @ApiModelProperty("商品名称")
    private String productName;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 数量 */
    @ApiModelProperty("数量")
    private BigDecimal quantity;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updateUser;

}
