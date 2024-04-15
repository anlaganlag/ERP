package com.tadpole.cloud.externalSystem.modular.lingxing.constants;

/**
 * @author: ty
 * @description: 领星URL常量类
 * @date: 2022/4/21
 */
public class LingXingUrlConstants {

    /**
     * 授权：
     * 获取access-token和refresh-token
     */
    public static final String ACCESS_TOKEN = "/api/auth-server/oauth/access-token";

    /**
     * 授权：
     * 刷新token（token续约，每个refreshToken只能用一次）
     */
    public static final String REFRESH_TOKEN = "/api/auth-server/oauth/refresh";

    /**
     * 基础数据：
     * 查询亚马逊店铺信息
     */
    public static final String SELLER_LISTS = "/data/seller/lists";

    /**
     * 基础数据：
     * 查询ERP账号列表
     */
    public static final String ACCOUNT_LISTS = "/data/account/lists";

    /**
     * 基础数据：
     * 查询汇率
     */
    public static final String CURRENCY_MONTH = "/routing/finance/currency/currencyMonth";

    /**
     * 销售：
     * 查询亚马逊订单列表
     */
    public static final String MWS_ORDER = "/data/mws/orders";

    /**
     * 销售：
     * 查询亚马逊订单详情
     */
    public static final String MWS_ORDER_DETAIL = "/data/mws/orderDetail";

    /**
     * 销售：
     * 查询listing
     */
    public static final String MWS_LISTING = "/data/mws/listing";

    /**
     * 销售：
     * 查询亚马逊售后评价
     */
    public static final String MWS_REVIEWS = "/data/mws/reviews";

    /**
     * 销售：
     * 查询亚马逊自发货订单列表
     */
    public static final String GET_ORDER_LIST = "/routing/order/Order/getOrderList";

    /**
     * 销售：
     * 查询亚马逊自发货订单详情
     */
    public static final String GET_ORDER_DETAIL = "/routing/order/Order/getOrderDetail";

    /**
     * 销售：
     * 批量添加/编辑Listing配对
     */
    public static final String PRODUCT_LINK = "/storage/product/link";

    /**
     * FBA：
     * 查询FBA发货单列表
     */
    public static final String GET_INBOUND_SHIPMENT_LIST = "/storage/shipment/getInboundShipmentList";

    /**
     * FBA：
     * 查询FBA货件
     */
    public static final String SHIPMENT_LIST = "/data/fba_report/shipmentList";

    /**
     * FBA：
     * 生成已发货的发货单
     */
    public static final String CREATE_SENDED_ORDER = "/storage/shipment/createSendedOrder";

    /**
     * FBA：
     * 查询FBA到货接收明细
     */
    public static final String RECEIVED_INVENTORY = "/data/fba_report/receivedInventory";

    /**
     * FBA：
     * 查询FBA长期仓储费
     */
    public static final String STORAGE_FEE_LONG_TERM = "/data/fba_report/storageFeeLongTerm";

    /**
     * FBA：
     * 查询FBA月仓储费
     */
    public static final String STORAGE_FEE_MONTH = "/data/fba_report/storageFeeMonth";

    /**
     * FBA：
     * FBA发货单发货
     */
    public static final String SEND_GOODS = "/storage/shipment/sendGoods";

    /**
     * FBA：
     * 查询FBA发货计划
     */
    public static final String SHIPMENT_PLAN_LISTS = "/data/fba_report/shipmentPlanLists";

    /**
     * FBA：
     * 查询FBA发货单详情
     */
    public static final String GET_INBOUND_SHIPMENT_LIST_MWS_DETAIL = "/routing/storage/shipment/getInboundShipmentListMwsDetail";

    /**
     * FBA：
     * 更新发货单物流信息
     */
    public static final String UPDATE_LIST_LOGISTICS = "/routing/storage/shipment/updateListLogistics";

    /**
     * 财务：
     * 查询回款记录
     * 支持查询系统【财务】>【回款记录】数据
     */
    public static final String FUND_TRANSFERS = "/data/mws/fundTransfers";

    /**
     * 财务：
     * 查询费用管理（商品）
     * 支持查询系统【财务】>【费用管理】中商品维度数据
     */
    public static final String OTHER_FEE_GOODS = "/routing/finance/otherFee/goods";

    /**
     * 财务：
     * 查询费用管理（店铺）
     * 支持查询系统【财务】>【费用管理】中店铺维度数据
     */
    public static final String OTHER_FEE_SELLER = "/routing/finance/otherFee/seller";

    /**
     * 财务：
     * 查询利润报表 - MSKU
     * 支持查询系统【财务】>【利润报表】中MSKU维度统计数据
     */
    public static final String PROFIT_MSKU = "/routing/finance/ProfitState/profitMsku";

    /**
     * 财务：
     * 查询利润报表 - ASIN（父级）
     * 支持查询系统【财务】>【利润报表】>【ASIN】，汇总行数据
     */
    public static final String PROFIT_ASIN = "/routing/finance/ProfitState/profitAsin";

    /**
     * 财务：
     * 查询利润报表 - ASIN（子级）
     * 支持查询系统【财务】>【利润报表】>【ASIN】，“+”号展开数据
     */
    public static final String PROFIT_ASIN_SON = "/routing/finance/ProfitState/profitAsinSon";

    /**
     * 财务：
     * 查询利润报表-结算明细
     * 支持查询系统【财务】>【利润报表】>【结算明细】数据
     */
    public static final String PROFIT_SETTLEMENT = "/routing/finance/ProfitState/profitSettlement";

    /**
     * 亚马逊源表数据：
     * 查询亚马逊源报表-FBA可售库存
     * 查询亚马逊FBA可售库存源表数据FBA Multi-Country Inventory Report，支持按店铺维度去重查询
     */
    public static final String GET_AFN_FULFILLABLE_QUANTITY = "/data/mws_report/getAfnFulfillableQuantity";

    /**
     * 亚马逊源表数据：
     * 查询亚马逊源报表-所有订单
     * 支持查询亚马逊AllOrders报表源数据All Orders Report By last update
     */
    public static final String ALL_ORDERS = "/data/mws_report/allOrders";

    /**
     * 亚马逊源表数据：
     * 查询亚马逊源报表-每日库存
     * 支持查询FBA每日库存源表数据FBA Daily Inventory History Report
     */
    public static final String DAILY_INVENTORY = "/data/mws_report/dailyInventory";

    /**
     * 亚马逊源表数据：
     * 查询亚马逊源报表-FBA订单
     * 支持查询亚马逊FBA订单源报表数据Amazon-Fulfilled Shipments Report
     */
    public static final String FBA_ORDERS = "/data/mws_report/fbaOrders";

    /**
     * 亚马逊源表数据：
     * 查询亚马逊源表——Amazon Fulfilled Shipments源文件下载
     * 支持按文件ID查询Amazon Fulfilled Shipments源文件并下载
     */
    public static final String GET_FULFILLED_SHIPMENT_FILE = "/routing/sourceData/getFulfilledShipmentFile";

    /**
     * 亚马逊源表数据：
     * 查询亚马逊源表-Amazon Fulfilled Shipments文件列表
     * 支持查询亚马逊源表Amazon Fulfilled Shipments文件列表，获取文件ID
     */
    public static final String FULFILLED_SHIPMENT_FILE_LIST = "/routing/sourceData/fulfilledShipmentFileList";

    /**
     * 亚马逊源表数据：
     * 查询亚马逊源表——Inventory Event Detail源文件下载
     * 支持根据文件ID查询亚马逊Inventory Event Detail源文件并下载
     */
    public static final String GET_INVENTORY_EVENT_DETAIL_FILE = "/routing/sourceData/getInventoryEventDetailFile";

    /**
     * 亚马逊源表数据：
     * 查询亚马逊源表-Inventory Event Detail文件列表
     * 支持查询亚马逊Inventory Event Detail源文件列表，获取文件ID
     */
    public static final String INVENTORY_EVENT_DETAIL_FILE_LIST = "/routing/sourceData/inventoryEventDetailFileList";

    /**
     * 亚马逊源表数据：
     * 查询查询亚马逊源表-Settlement文件列表
     * 支持查询亚马逊Settlement源文件列表，获取源表ID用于查询文件详情
     */
    public static final String SETTLEMENT_FILE_LIST = "/routing/sourceData/settlementFileList";

    /**
     * 亚马逊源表数据：
     * 查询亚马逊源表——Settlement源文件下载
     * 支持根据源文件ID下载Settlement源文件
     */
    public static final String GET_SETTLEMENT_FILE = "/routing/sourceData/getSettlementFile";

    /**
     * 亚马逊源表数据：
     * 查询亚马逊源报表-FBA库存
     * 支持查询亚马逊FBA库存源数据，FBA Manage Inventory报表
     */
    public static final String MANAGE_INVENTORY = "/data/mws_report/manageInventory";

    /**
     * 亚马逊源表数据：
     * 查询亚马逊源报表-退货订单
     * FBA customer returns report报表数据查询
     */
    public static final String REFUND_ORDERS = "/data/mws_report/refundOrders";

    /**
     * 亚马逊源表数据：
     * 查询亚马逊源报表-预留库存
     * 支持查询亚马逊FBA库存源数据FBA Reserved Inventory Report
     */
    public static final String RESERVED_INVENTORY = "/data/mws_report/reservedInventory";

    /**
     * 亚马逊源表数据：
     * 查询亚马逊源报表-移除订单
     * Removal order detail report报表查询
     */
    public static final String REMOVAL_ORDERS = "/data/mws_report/removalOrders";

    /**
     * 亚马逊源表数据：
     * 查询亚马逊源报表-交易明细
     * 查询领星插件下载的Transaction报表数据
     */
    public static final String TRANSACTION = "/data/mws_report/transaction";

    /**
     * 广告：
     * 查询广告管理-广告组
     * 查询广告管理中SP/SD广告的广告组维度数据
     */
    public static final String ADVERTISING_GROUP = "/data/ads/adGroups";

    /**
     * 广告：
     * 查询广告管理-用户搜索词
     * 查询广告管理中SP/SB广告的用户搜索词数据
     */
    public static final String USER_SEARCH_WORDS = "/data/ads/queryWords";

    /**
     * 广告：
     * 查询广告管理-商品定位
     * 查询SP/SB/SD广告管理中商品定位维度数据
     */
    public static final String COMMODITY_POSITION = "/data/ads/targets";

    /**
     * 广告：
     * 查询广告管理-广告活动
     * 查询广告管理中SP/SB/SD广告管理中广告活动的数据
     */
    public static final String ADVERTISING_ACTIVITY = "/data/ads/campaigns";

    /**
     * 广告：
     * 查询广告管理-广告
     * 查询广告管理中SP/SD广告的广告维度数据
     */
    public static final String ADVERTISING = "/data/ads/products";

    /**
     * 广告：
     * 查询广告管理-操作日志
     * 查询SP/SB/SD/广告设置/SBV广告的操作日志
     */
    public static final String OPERATION_LOG = "/data/ads_report/logs";

    /**
     * 广告：
     * 查询广告管理-关键词
     * 查询广告管理中SP/SD广告的关键词数据
     */
    public static final String KEYWORDS = "/data/ads/keywords";

    /**
     * 统计：
     * 查询销量、订单量、销售额统计信息
     * 查询按Asin或MSKU查询销量、订单量或销售额
     */
    public static final String STATISTICAL_INFORMATION = "/data/sales_report/asinDailyLists";


}
