package com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 修改订单信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MabangOrderChangeParm {
    private String platformOrderId; //订单编号  该项必填
    private String remark; //订单备注
    private String buyerName; //客户姓名
    private String country; //国家
    private String countryCode; //国家二字码
    private String province; //省/州
    private String city; //市
    private String postCode; //邮编
    private String email; //联系邮箱
    private String doorcode; //门牌号
    private String street1; //邮寄地址
    private String street2; //备用地址
    private String orderStatus; //标记订单状态 5 标记作废 4 已完成
    private String phone1; //联系电话1
    private String phone2; //联系电话2
    private String abnnumber; //税号
//    private String stockData; //商品信息，以字符串形式入参
    private String extendAttr; //组装扩展属性
    private String shippingCost; //物流支出运费
    private String myLogisticsId; //物流公司名称
    private String myLogisticsChannelId; //物流渠道编号
    private String otherExpend; //其他支出

    private List<StockData>  stockData ;




    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class StockData{
        private String stockSku;// 库存sku
        private String quantity;// "商品数量,仅支持添加修改
        private String afterStockSku;//"更换的sku", type=1时必填
        private String type;//"1.修改 2作废 3添加", //必填,
        private String erpOrderItemId;//"马帮订单商品明细ID", //当type=1时必填
        private String  orderItemId;//"添加的商品平台交易号",
        private String productId;//"添加的商品平台编号",
        private String itemRemark;//"添加的商品备注",
        private String transactionId;//添加的商品transactionId",
        private String specifics;//"添加的商品多属性",
        private String costPrice;//"添加的商品成本价",
        private String unitWeight;//添加的商品单品重量",
        private String warehouseName;//添加的商品仓库，type为3时，该项必填",
        private String sellPrice;//"添加的商品售价"
    }
}
