package com.tadpole.cloud.externalSystem.modular.mabang.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

 /**
 * B2B马帮订单列表;
 * @author : LSY
 * @date : 2023-9-13
 */
@ApiModel(value = "B2B马帮订单列表",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class B2bMabangOrdersParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
    /** 主键 */
    @ApiModelProperty(value = "主键")
    private String id ;
    
    /** 订单编号 */
    @ApiModelProperty(value = "订单编号")
    private String platformOrderId ;
    
    /** 订单状态 2.配货中 3.已发货 4.已完成 5.已作废 */
    @ApiModelProperty(value = "订单状态 2.配货中 3.已发货 4.已完成 5.已作废")
    private String orderStatus ;
    
    /** 物流渠道编号 */
    @ApiModelProperty(value = "物流渠道编号")
    private String myLogisticsChannelId ;
    
    /** 物流单号 */
    @ApiModelProperty(value = "物流单号")
    private String trackNumber ;
    
    /** 内部单号 */
    @ApiModelProperty(value = "内部单号")
    private String trackNumber1 ;
    
    /** 虚拟单号 */
    @ApiModelProperty(value = "虚拟单号")
    private String trackNumber2 ;
    
    /** MCMS含义:订单预估重量-----马帮含义:订单重量 */
    @ApiModelProperty(value = "MCMS含义:订单预估重量-----马帮含义:订单重量")
    private BigDecimal orderWeight ;
    
    /** MCMS含义:客户ID-----马帮含义:买家账号 */
    @ApiModelProperty(value = "MCMS含义:客户ID-----马帮含义:买家账号")
    private String buyerUserId ;
    
    /** MCMS含义:客户姓名-----马帮含义:买家姓名 */
    @ApiModelProperty(value = "MCMS含义:客户姓名-----马帮含义:买家姓名")
    private String buyerName ;
    
    /** 店铺编号 */
    @ApiModelProperty(value = "店铺编号")
    private String shopId ;
    
    /** 企业编号 */
    @ApiModelProperty(value = "企业编号")
    private String companyId ;
    
    /** MCMS含义:国家简码（波黑为3字码，其余二字码）-----马帮含义:国家二字码 */
    @ApiModelProperty(value = "MCMS含义:国家简码（波黑为3字码，其余二字码）-----马帮含义:国家二字码")
    private String countryCode ;
    
    /** 订单成本价 */
    @ApiModelProperty(value = "订单成本价")
    private BigDecimal orderCost ;
    
    /** MCMS含义:订单获取物流跟踪号时间-----马帮含义:交运时间 */
    @ApiModelProperty(value = "MCMS含义:订单获取物流跟踪号时间-----马帮含义:交运时间")
    private Date transportTime ;
    
    /** MCMS含义:订单分配至仓库系统时间-----马帮含义:转wms发货时间 */
    @ApiModelProperty(value = "MCMS含义:订单分配至仓库系统时间-----马帮含义:转wms发货时间")
    private Date quickPickTime ;
    
    /** MCMS含义:订单是否处于待审核状态-----马帮含义:待审核订单 1.否 2.是 */
    @ApiModelProperty(value = "MCMS含义:订单是否处于待审核状态-----马帮含义:待审核订单 1.否 2.是")
    private String canSend ;
    
    /** MCMS含义:订单下载至马帮ERP时间-----马帮含义:订单同步ERP时间 */
    @ApiModelProperty(value = "MCMS含义:订单下载至马帮ERP时间-----马帮含义:订单同步ERP时间")
    private Date createDate ;
    
    /** MCMS含义:是否是退货订单-----马帮含义:1.退货 2.非退货 */
    @ApiModelProperty(value = "MCMS含义:是否是退货订单-----马帮含义:1.退货 2.非退货")
    private String isReturned ;
    
    /** MCMS含义:是否是退款订单-----马帮含义:1.退款 2.非退款 */
    @ApiModelProperty(value = "MCMS含义:是否是退款订单-----马帮含义:1.退款 2.非退款")
    private String isRefund ;
    
    /** MCMS含义:订单客户付款时间-----马帮含义:订单付款时间 */
    @ApiModelProperty(value = "MCMS含义:订单客户付款时间-----马帮含义:订单付款时间")
    private Date paidTime ;
    
    /** 平台交易号 */
    @ApiModelProperty(value = "平台交易号")
    private String salesRecordNumber ;
    
    /** 订单金额 */
    @ApiModelProperty(value = "订单金额")
    private BigDecimal orderFee ;
    
    /** MCMS含义:平台编码-----马帮含义:来源平台 */
    @ApiModelProperty(value = "MCMS含义:平台编码-----马帮含义:来源平台")
    private String platformId ;
    
    /** 发货时间 */
    @ApiModelProperty(value = "发货时间")
    private Date expressTime ;
    
    /** 1.合并订单 2.非合并订单 */
    @ApiModelProperty(value = "1.合并订单 2.非合并订单")
    private String isUnion ;
    
    /** 1.拆分订单 2.非拆分订单 */
    @ApiModelProperty(value = "1.拆分订单 2.非拆分订单")
    private String isSplit ;
    
    /** 1.重发订单 2.非重发订单 */
    @ApiModelProperty(value = "1.重发订单 2.非重发订单")
    private String isResend ;
    
    /** MCMS含义:缺货状态(0正在计算是否缺货
1有货 2缺货 3已补货)---马帮含义:缺货订单 0 正在计算是否缺货 、 1有货、2缺货、3 已补货 */
    @ApiModelProperty(value = "MCMS含义:缺货状态(0正在计算是否缺货 1有货 2缺货 3已补货)---马帮含义:缺货订单 0 正在计算是否缺货 、 1有货、2缺货、3 已补货")
    private String hasGoods ;
    
    /** 是否含电池 1.是 2否 */
    @ApiModelProperty(value = "是否含电池 1.是 2否")
    private String hasBattery ;
    
    /** MCMS含义:订单获取物流跟踪号描述-----马帮含义:交运结果描述 */
    @ApiModelProperty(value = "MCMS含义:订单获取物流跟踪号描述-----马帮含义:交运结果描述")
    private String isSyncLogisticsDescr ;
    
    /** MCMS含义:用户支付的paypal Id-----马帮含义:paypalId */
    @ApiModelProperty(value = "MCMS含义:用户支付的paypal Id-----马帮含义:paypalId")
    private String paypalId ;
    
    /** MCMS含义:订单获取物流跟踪号状态(0未调用;1 开始调用 */
    @ApiModelProperty(value = "MCMS含义:订单获取物流跟踪号状态(0未调用")
    private String isSyncLogistics ;
    
    /** MCMS含义:同步跟踪号至平台状态(1未同步 2等待交运 6不需要同步
7同步失败 9同步成功)---马帮含义:是否同步到电商平台 1未同步 2等待交运 6不需要同步 7同步失败 9同步成功 */
    @ApiModelProperty(value = "MCMS含义:同步跟踪号至平台状态(1未同步 2等待交运 6不需要同步 7同步失败 9同步成功)---马帮含义:是否同步到电商平台 1未同步 2等待交运 6不需要同步 7同步失败 9同步成功")
    private String isSyncPlatform ;
    
    /** MCMS含义:同步跟踪号至平台结果-----马帮含义:同步结果 */
    @ApiModelProperty(value = "MCMS含义:同步跟踪号至平台结果-----马帮含义:同步结果")
    private String isSyncPlatformDescr ;
    
    /** MCMS含义:所属区(例如：龙岗区、福田区)---马帮含义:所属区域 */
    @ApiModelProperty(value = "MCMS含义:所属区(例如：龙岗区、福田区)---马帮含义:所属区域")
    private String district ;
    
    /** MCMS含义:用户支付的paypal账号(这个字段马帮对接群里面的人也搞不清楚到底是卖家paypal账号还是卖家paypal账号)---马帮含义:收款账号 */
    @ApiModelProperty(value = "MCMS含义:用户支付的paypal账号(这个字段马帮对接群里面的人也搞不清楚到底是卖家paypal账号还是卖家paypal账号)---马帮含义:收款账号")
    private String paypalEmail ;
    
    /** MCMS含义:订单关闭时间-----马帮含义:交易关闭时间 */
    @ApiModelProperty(value = "MCMS含义:订单关闭时间-----马帮含义:交易关闭时间")
    private Date closeDate ;
    
    /** 买家地址1 */
    @ApiModelProperty(value = "买家地址1")
    private String street1 ;
    
    /** 买家地址2 */
    @ApiModelProperty(value = "买家地址2")
    private String street2 ;
    
    /** 虚假发货订单 1.是 2.否 */
    @ApiModelProperty(value = "虚假发货订单 1.是 2.否")
    private String isVirtual ;
    
    /** MCMS含义:城市名称-----马帮含义:买家城市 */
    @ApiModelProperty(value = "MCMS含义:城市名称-----马帮含义:买家城市")
    private String city ;
    
    /** MCMS含义:州省名称-----马帮含义:买家省份 */
    @ApiModelProperty(value = "MCMS含义:州省名称-----马帮含义:买家省份")
    private String province ;
    
    /** MCMS含义:邮编号码-----马帮含义:买家邮编 */
    @ApiModelProperty(value = "MCMS含义:邮编号码-----马帮含义:买家邮编")
    private String postCode ;
    
    /** MCMS含义:客户联系电话1-----马帮含义:买家电话1 */
    @ApiModelProperty(value = "MCMS含义:客户联系电话1-----马帮含义:买家电话1")
    private String phone1 ;
    
    /** MCMS含义:客户联系电话2-----马帮含义:买家电话2 */
    @ApiModelProperty(value = "MCMS含义:客户联系电话2-----马帮含义:买家电话2")
    private String phone2 ;
    
    /** MCMS含义:买家邮件地址-----马帮含义:买家邮箱 */
    @ApiModelProperty(value = "MCMS含义:买家邮件地址-----马帮含义:买家邮箱")
    private String email ;
    
    /** 1.待处理订单 2.配货中订单 */
    @ApiModelProperty(value = "1.待处理订单 2.配货中订单")
    private String isNewOrder ;
    
    /** MCMS含义:客户门牌号码-----马帮含义:买家门牌号 */
    @ApiModelProperty(value = "MCMS含义:客户门牌号码-----马帮含义:买家门牌号")
    private String doorcode ;
    
    /** MCMS含义:是否是FBA订单-----马帮含义:是否平台发货订单 1.非 2.是 */
    @ApiModelProperty(value = "MCMS含义:是否是FBA订单-----马帮含义:是否平台发货订单 1.非 2.是")
    private String fbaFlag ;
    
    /** MCMS含义:FBA配送开始时间-----马帮含义:Fba开始配送时间 */
    @ApiModelProperty(value = "MCMS含义:FBA配送开始时间-----马帮含义:Fba开始配送时间")
    private Date fbaStartDateTime ;
    
    /** MCMS含义:FBA配送结束时间-----马帮含义:Fba配送结束时间 */
    @ApiModelProperty(value = "MCMS含义:FBA配送结束时间-----马帮含义:Fba配送结束时间")
    private Date fbaEndDateTime ;
    
    /** Fba承运人  注意：字段首字母大写，字段赋值可能为空 */
    @ApiModelProperty(value = "Fba承运人  注意：字段首字母大写，字段赋值可能为空")
    private String carrierCode ;
    
    /** 操作时间 */
    @ApiModelProperty(value = "操作时间")
    private Date operTime ;
    
    /** 买家自选物流 */
    @ApiModelProperty(value = "买家自选物流")
    private String shippingService ;
    
    /** MCMS含义:包材重量(这是不是包裹重量，别被马帮的英文字段误导)---马帮含义:包材重量 */
    @ApiModelProperty(value = "MCMS含义:包材重量(这是不是包裹重量，别被马帮的英文字段误导)---马帮含义:包材重量")
    private BigDecimal packageWeight ;
    
    /** 是否含磁1含2不含 */
    @ApiModelProperty(value = "是否含磁1含2不含")
    private String hasMagnetic ;
    
    /** 是否含泡沫1含2不含 */
    @ApiModelProperty(value = "是否含泡沫1含2不含")
    private String hasPowder ;
    
    /** 是否侵权1含2不含 */
    @ApiModelProperty(value = "是否侵权1含2不含")
    private String hasTort ;
    
    /** 订单备注 */
    @ApiModelProperty(value = "订单备注")
    private String remark ;
    
    /** 平台备注 */
    @ApiModelProperty(value = "平台备注")
    private String sellerMessage ;
    
    /** 币种 */
    @ApiModelProperty(value = "币种")
    private String currencyId ;
    
    /** MCMS含义:币种汇率-----马帮含义:汇率 */
    @ApiModelProperty(value = "MCMS含义:币种汇率-----马帮含义:汇率")
    private BigDecimal currencyRate ;
    
    /** 商品总售价 */
    @ApiModelProperty(value = "商品总售价")
    private BigDecimal itemTotal ;
    
    /** MCMS含义:运费（收入）-----马帮含义:运费收入 */
    @ApiModelProperty(value = "MCMS含义:运费（收入）-----马帮含义:运费收入")
    private BigDecimal shippingFee ;
    
    /** MCMS含义:平台费(平台佣金）(非亚马逊平台)---马帮含义:平台费 */
    @ApiModelProperty(value = "MCMS含义:平台费(平台佣金）(非亚马逊平台)---马帮含义:平台费")
    private BigDecimal platformFee ;
    
    /** MCMS含义:原始运费收入-----马帮含义:原始运费收入 */
    @ApiModelProperty(value = "MCMS含义:原始运费收入-----马帮含义:原始运费收入")
    private BigDecimal shippingTotalOrigin ;
    
    /** 商品原始总售价 */
    @ApiModelProperty(value = "商品原始总售价")
    private BigDecimal itemTotalOrigin ;
    
    /** 退款金额币种 */
    @ApiModelProperty(value = "退款金额币种")
    private String refundFeeCurrencyId ;
    
    /** 原始税费 */
    @ApiModelProperty(value = "原始税费")
    private BigDecimal originFax ;
    
    /** 保险费 */
    @ApiModelProperty(value = "保险费")
    private BigDecimal insuranceFee ;
    
    /** 原始保险费 */
    @ApiModelProperty(value = "原始保险费")
    private BigDecimal insuranceFeeOrigin ;
    
    /** MCMS含义:paypal费-----马帮含义:Paypal转账费 */
    @ApiModelProperty(value = "MCMS含义:paypal费-----马帮含义:Paypal转账费")
    private BigDecimal paypalFee ;
    
    /** MCMS含义:原始paypal费（原始paypal佣金）-----马帮含义:原始Paypal转账费 */
    @ApiModelProperty(value = "MCMS含义:原始paypal费（原始paypal佣金）-----马帮含义:原始Paypal转账费")
    private BigDecimal paypalFeeOrigin ;
    
    /** 商品总成本 */
    @ApiModelProperty(value = "商品总成本")
    private BigDecimal itemTotalCost ;
    
    /** MCMS含义:真实运费成本-----马帮含义:真实运费 */
    @ApiModelProperty(value = "MCMS含义:真实运费成本-----马帮含义:真实运费")
    private BigDecimal shippingCost ;
    
    /** MCMS含义:预估运费成本-----马帮含义:预估运费 */
    @ApiModelProperty(value = "MCMS含义:预估运费成本-----马帮含义:预估运费")
    private BigDecimal shippingPreCost ;
    
    /** 包材费 */
    @ApiModelProperty(value = "包材费")
    private BigDecimal packageFee ;
    
    /** FBA每笔订单配送服务费 */
    @ApiModelProperty(value = "FBA每笔订单配送服务费")
    private BigDecimal fbaPerOrderFulfillmentFee ;
    
    /** 亚马逊平台佣金 */
    @ApiModelProperty(value = "亚马逊平台佣金")
    private BigDecimal fbaCommission ;
    
    /** 折扣RMB金额 */
    @ApiModelProperty(value = "折扣RMB金额")
    private BigDecimal promotionAmount ;
    
    /** 原始联盟佣金 */
    @ApiModelProperty(value = "原始联盟佣金")
    private BigDecimal allianceFeeOrigin ;
    
    /** 原始优惠券 */
    @ApiModelProperty(value = "原始优惠券")
    private BigDecimal voucherPriceOrigin ;
    
    /** 原始补贴金额 */
    @ApiModelProperty(value = "原始补贴金额")
    private BigDecimal subsidyAmountOrigin ;
    
    /** MCMS含义:货到付款金额-----马帮含义:fba货到付款金额 */
    @ApiModelProperty(value = "MCMS含义:货到付款金额-----马帮含义:fba货到付款金额")
    private BigDecimal codCharge ;
    
    /** 联盟佣金 */
    @ApiModelProperty(value = "联盟佣金")
    private BigDecimal allianceFee ;
    
    /** 亚马逊物流基础服务费 */
    @ApiModelProperty(value = "亚马逊物流基础服务费")
    private BigDecimal fbaPerUnitFulfillmentFee ;
    
    /** fba亚马逊物流配送费 */
    @ApiModelProperty(value = "fba亚马逊物流配送费")
    private BigDecimal fbaWeightBasedFee ;
    
    /** 原始平台费 */
    @ApiModelProperty(value = "原始平台费")
    private BigDecimal platformFeeOrigin ;
    
    /** 优惠券 */
    @ApiModelProperty(value = "优惠券")
    private BigDecimal voucherPrice ;
    
    /** 补贴金额 */
    @ApiModelProperty(value = "补贴金额")
    private BigDecimal subsidyAmount ;
    
    /** MCMS含义:是否已转至仓库系统发货-----马帮含义:1.wms 2.非wms */
    @ApiModelProperty(value = "MCMS含义:是否已转至仓库系统发货-----马帮含义:1.wms 2.非wms")
    private String isWms ;
    
    /** 付款方式 */
    @ApiModelProperty(value = "付款方式")
    private String payType ;
    
    /** VendorID  注意：字段首字母大写，字段赋值可能为空 */
    @ApiModelProperty(value = "VendorID  注意：字段首字母大写，字段赋值可能为空")
    private String vendorId ;
    
    /** MCMS含义:Australian Business Number,澳大利亚商业编号(每一个企业都有自己独一无二的ABN， 且ABN并不等同于税号（TFN），企业也需要申请自己的税号。)---马帮含义:税号 */
    @ApiModelProperty(value = "MCMS含义:Australian Business Number,澳大利亚商业编号(每一个企业都有自己独一无二的ABN， 且ABN并不等同于税号（TFN），企业也需要申请自己的税号。)---马帮含义:税号")
    private String abnnumber ;
    
    /** 国家英文名称 */
    @ApiModelProperty(value = "国家英文名称")
    private String countryNameEn ;
    
    /** 国家中文名称 */
    @ApiModelProperty(value = "国家中文名称")
    private String countryNameCn ;
    
    /** 店铺名称 */
    @ApiModelProperty(value = "店铺名称")
    private String shopName ;
    
    /** 物流渠道名称 */
    @ApiModelProperty(value = "物流渠道名称")
    private String myLogisticsChannelName ;
    
    /** 物流公司编号 */
    @ApiModelProperty(value = "物流公司编号")
    private String myLogisticsId ;
    
    /** 物流公司名称 */
    @ApiModelProperty(value = "物流公司名称")
    private String myLogisticsName ;
    
    /** MCMS含义:马帮ERP系统订单编号-----马帮含义:ERP系统订单编号   注意：字段首字母大写，字段赋值可能为空 */
    @ApiModelProperty(value = "MCMS含义:马帮ERP系统订单编号-----马帮含义:ERP系统订单编号   注意：字段首字母大写，字段赋值可能为空")
    private String erpOrderId ;
    
    /** MCMS含义:包裹实际物流重(物流公司称重)---马帮含义:物流称重 */
    @ApiModelProperty(value = "MCMS含义:包裹实际物流重(物流公司称重)---马帮含义:物流称重")
    private BigDecimal shippingWeight ;
    
    /** 作废前状态，参考orderStatus字段 */
    @ApiModelProperty(value = "作废前状态，参考orderStatus字段")
    private String beforeStatus ;
    
    /** ([{"key":"名称","val":"值"},{"key2":"名称2","val2":"值2"}])   注意：字段首字母大写，字段赋值可能为空 */
    @ApiModelProperty(value = "key\":\"名称\",\"val\":\"值\"},{\"key2\":\"名称2\",\"val2\":\"值2\"   注意：字段首字母大写，字段赋值可能为空")
    private String extendAttr ;
    
    /** 同步方式（0 ：系统同步,1：手动人工同步） */
    @ApiModelProperty(value = "同步方式（0 ：系统同步,1：手动人工同步）")
    private String syncType ;
    
    /** 同步时间 */
    @ApiModelProperty(value = "同步时间")
    private Date syncTime ;
    
    /** 同步状态（0 ：同步失败,1：同步成功） */
    @ApiModelProperty(value = "同步状态（0 ：同步失败,1：同步成功）")
    private String syncStatus ;
    
    /** 同步成功次数 */
    @ApiModelProperty(value = "同步成功次数")
    private BigDecimal syncSuccessTimes ;
    
    /** 同步失败次数 */
    @ApiModelProperty(value = "同步失败次数")
    private BigDecimal syncFailTimes ;
    
    /** 最后一次同步结果消息内容 */
    @ApiModelProperty(value = "最后一次同步结果消息内容")
    private String syncResultMsg ;
    
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createTime ;
    
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime ;
    
    /** 是否创建物料 （0 ：默认0未创建物料,1：已创建物料) */
    @ApiModelProperty(value = "是否创建物料 （0 ：默认0未创建物料,1：已创建物料)")
    private BigDecimal createMat ;
    
    /** 平台订单ID 不同平台适配的真实平台订单ID */
    @ApiModelProperty(value = "平台订单ID 不同平台适配的真实平台订单ID")
    private String platOrdId ;



    @ApiModelProperty(value = "店铺人员编号")
    private String shopEmployeeId ;


    @ApiModelProperty(value = "店铺人员")
    private String shopEmployeeName ;


    @ApiModelProperty("公司名称")
    private String companyStreet;
    

}