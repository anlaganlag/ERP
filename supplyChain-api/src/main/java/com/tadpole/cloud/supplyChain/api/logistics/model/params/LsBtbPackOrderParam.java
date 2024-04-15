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
import java.util.List;

/**
 * <p>
 *  BTB订单发货入参类
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
public class LsBtbPackOrderParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** BTB订单号 */
    @ApiModelProperty("BTB订单号")
    private String orderNo;

    /** 订单状态 */
    @ApiModelProperty("订单状态")
    private String orderStatus;

    /** 订单日期 */
    @ApiModelProperty("订单日期")
    private Date orderDate;

    /** 平台 */
    @ApiModelProperty("平台")
    private String platform;

    /** 账号 */
    @ApiModelProperty("账号")
    private String sysShopsName;

    /** 站点 */
    @ApiModelProperty("站点")
    private String sysSite;

    /** 公司名称 */
    @ApiModelProperty("公司名称")
    private String companyStreet;

    /** 收货国家中文名称 */
    @ApiModelProperty("收货国家中文名称")
    private String receiveCountryNameCn;

    /** 收货国家英文名称 */
    @ApiModelProperty("收货国家英文名称")
    private String receiveCountryNameEn;

    /** 收货国家编码 */
    @ApiModelProperty("收货国家编码")
    private String receiveCountryCode;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 总重量KG */
    @ApiModelProperty("总重量KG")
    private BigDecimal totalWeight;

    /** 总体积CBM */
    @ApiModelProperty("总体积CBM")
    private BigDecimal totalVolume;

    /** 总数量 */
    @ApiModelProperty("总数量")
    private BigDecimal totalQuantity;

    /** 物流状态 未发货，部分发货，已发货，物流完结 */
    @ApiModelProperty("物流状态 未发货，部分发货，已发货，物流完结")
    private String logisticsStatus;

    /** 发货类型：物流部发货，业务部发货 */
    @ApiModelProperty("发货类型：物流部发货，业务部发货")
    private String shipmentType;
    
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

    /** BTB订单号集合 */
    @ApiModelProperty("BTB订单号集合")
    private List<String> orderNoList;

    /** 平台集合 */
    @ApiModelProperty("平台集合")
    private List<String> platformList;

    /** 账号集合 */
    @ApiModelProperty("账号集合")
    private List<String> sysShopsNameList;

    /** 站点集合 */
    @ApiModelProperty("站点集合")
    private List<String> sysSiteList;

    /** 物料编码集合 */
    @ApiModelProperty("物料编码集合")
    private List<String> materialCodeList;

    /** 物流状态集合 未发货，部分发货，已发货，物流完结 */
    @ApiModelProperty("物流状态集合 未发货，部分发货，已发货，物流完结")
    private List<String> logisticsStatusList;

    /** 订单状态集合 */
    @ApiModelProperty("订单状态集合")
    private List<String> orderStatusList;

    /** 订单日期开始时间 */
    @ApiModelProperty("订单日期开始时间")
    private String orderStartDate;

    /** 订单日期结束时间 */
    @ApiModelProperty("订单日期结束时间")
    private String orderEndDate;

}
