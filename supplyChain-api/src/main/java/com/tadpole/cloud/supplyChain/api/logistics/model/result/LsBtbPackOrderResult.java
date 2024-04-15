package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsBtbPackBox;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsBtbPackBoxDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  BTB订单发货出参类
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
@ExcelIgnoreUnannotated
@TableName("LS_BTB_PACK_ORDER")
public class LsBtbPackOrderResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty("BTB订单号")
    private String orderNo;

    @ApiModelProperty("订单状态")
    private String orderStatus;

    @ApiModelProperty("订单日期")
    private Date orderDate;

    @ApiModelProperty("平台")
    private String platform;

    @ApiModelProperty("账号")
    private String sysShopsName;

    @ApiModelProperty("站点")
    private String sysSite;

    /** 公司名称 */
    @ApiModelProperty("公司名称")
    private String companyStreet;

    @ApiModelProperty("收货国家中文名称")
    private String receiveCountryNameCn;

    @ApiModelProperty("收货国家英文名称")
    private String receiveCountryNameEn;

    @ApiModelProperty("收货国家编码")
    private String receiveCountryCode;

    /** 收件人 */
    @ApiModelProperty("收件人")
    private String buyerName;

    /** 联系电话 */
    @ApiModelProperty("联系电话")
    private String phone1;

    /** 城市 */
    @ApiModelProperty("城市")
    private String city;

    /** 州/省/郡 */
    @ApiModelProperty("州/省/郡")
    private String province;

    /** 地址1 */
    @ApiModelProperty("地址1")
    private String street1;

    /** 地址2 */
    @ApiModelProperty("地址2")
    private String street2;

    /** 邮编 */
    @ApiModelProperty("邮编")
    private String postCode;

    @ApiModelProperty("物料编码")
    private String materialCode;

    @ApiModelProperty("总重量KG")
    private BigDecimal totalWeight;

    @ApiModelProperty("总体积CBM")
    private BigDecimal totalVolume;

    @ApiModelProperty("总数量")
    private BigDecimal totalQuantity;

    @ApiModelProperty("物流状态 未发货，部分发货，已发货，物流完结")
    private String logisticsStatus;

    @ApiModelProperty("物流部发货类型：物流部发货，业务部发货")
    private String shipmentType;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

    /**
     * BTB订单发货箱子信息
     */
    @ApiModelProperty("BTB订单发货箱子信息")
    private List<LsBtbPackBox> boxList;

    /**
      * BTB订单发货箱子明细信息
     */
    @ApiModelProperty("BTB订单发货箱子明细信息")
    private List<LsBtbPackBoxDetail> boxDetailList;

    /**
     * 下载通关信息-重量信息
     */
    @ApiModelProperty("BTB订单发货箱子信息")
    private List<LsBtbPackBoxResult> boxResultList;

    /**
     * 下载通关信息-箱子信息
     */
    @ApiModelProperty("BTB订单发货箱子明细信息")
    private List<LsBtbPackBoxDetailResult> boxDetailResultList;

}
