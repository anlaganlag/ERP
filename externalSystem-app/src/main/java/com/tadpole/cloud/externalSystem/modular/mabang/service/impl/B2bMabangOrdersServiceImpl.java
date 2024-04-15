package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tadpole.cloud.externalSystem.modular.mabang.constants.MabangConstant;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.*;
import com.tadpole.cloud.externalSystem.modular.mabang.enums.ShopListEnum;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.*;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.B2bMabangOrdersParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.MabangHeadParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.OrderParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.B2bMabangOrdersResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.MabangResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * B2B马帮订单列表;(B2B_MABANG_ORDERS)--服务实现类
 * @author : LSY
 * @create : 2023-9-13
 */
@Slf4j
@Service
@Transactional
public class B2bMabangOrdersServiceImpl extends ServiceImpl<B2bMabangOrdersMapper, B2bMabangOrders>  implements B2bMabangOrdersService {

    @Resource
    private B2bMabangOrdersMapper b2bMabangOrdersMapper;


    @Resource
    private B2bMabangOrderItemMapper b2bMabangOrderItemMapper;


    @Resource
    private B2bMabangOrderItemService itemService;

    @Resource
    private B2bPaymentService b2bPaymentService;

    @Resource
    IMabangRequstService mabangRequstService;

    @Resource
    IMabangShopListService shopListService;


    @Resource
    MabangEmployeeMapper employeeMapper;


    @Resource
    B2bPaymentMapper b2bPaymentMapper;

    @Resource
    B2bPaymentDetailMapper b2bPaymentDetailMapper;


    /** 
     * 通过ID查询单条数据 
     *
     * @param platformOrderId 主键
     * @return 实例对象
     */
    @DataSource(name = "external")
    @Override
    public B2bMabangOrdersResult queryByPlatformOrderId(String platformOrderId){

        MPJLambdaWrapper<B2bMabangOrders> wrapper = new MPJLambdaWrapper<>();
        wrapper.selectAll(B2bMabangOrders.class)
               .selectAll(B2bMabangOrderItem.class)
               .select(MabangShopList::getFinanceCode)
                .leftJoin(B2bMabangOrderItem.class, B2bMabangOrderItem::getOriginOrderId, B2bMabangOrders::getId)
                .leftJoin(MabangShopList.class, MabangShopList::getId, B2bMabangOrders::getShopId)
                .selectCollection(B2bMabangOrderItem.class, B2bMabangOrdersResult::getOrderitemList)
               .eq(B2bMabangOrders::getPlatformOrderId, platformOrderId);
        return b2bMabangOrdersMapper.selectJoinOne(B2bMabangOrdersResult.class, wrapper);
    }
    
    /**
     * 分页查询
     *
     * @param queryParam 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "external")
    @Override
    public Page<B2bMabangOrdersResult> paginQuery(B2bMabangOrdersParam queryParam, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<B2bMabangOrders> queryWrapper = new LambdaQueryWrapper<>();
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getId()),B2bMabangOrders::getId, queryParam.getId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getPlatformOrderId()),B2bMabangOrders::getPlatformOrderId, queryParam.getPlatformOrderId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getOrderStatus()),B2bMabangOrders::getOrderStatus, queryParam.getOrderStatus());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getMyLogisticsChannelId()),B2bMabangOrders::getMyLogisticsChannelId, queryParam.getMyLogisticsChannelId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getTrackNumber()),B2bMabangOrders::getTrackNumber, queryParam.getTrackNumber());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getTrackNumber1()),B2bMabangOrders::getTrackNumber1, queryParam.getTrackNumber1());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getTrackNumber2()),B2bMabangOrders::getTrackNumber2, queryParam.getTrackNumber2());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getBuyerUserId()),B2bMabangOrders::getBuyerUserId, queryParam.getBuyerUserId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getBuyerName()),B2bMabangOrders::getBuyerName, queryParam.getBuyerName());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getShopId()),B2bMabangOrders::getShopId, queryParam.getShopId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getCompanyId()),B2bMabangOrders::getCompanyId, queryParam.getCompanyId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getCountryCode()),B2bMabangOrders::getCountryCode, queryParam.getCountryCode());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getCanSend()),B2bMabangOrders::getCanSend, queryParam.getCanSend());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getIsReturned()),B2bMabangOrders::getIsReturned, queryParam.getIsReturned());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getIsRefund()),B2bMabangOrders::getIsRefund, queryParam.getIsRefund());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getSalesRecordNumber()),B2bMabangOrders::getSalesRecordNumber, queryParam.getSalesRecordNumber());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getPlatformId()),B2bMabangOrders::getPlatformId, queryParam.getPlatformId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getIsUnion()),B2bMabangOrders::getIsUnion, queryParam.getIsUnion());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getIsSplit()),B2bMabangOrders::getIsSplit, queryParam.getIsSplit());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getIsResend()),B2bMabangOrders::getIsResend, queryParam.getIsResend());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getHasGoods()),B2bMabangOrders::getHasGoods, queryParam.getHasGoods());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getHasBattery()),B2bMabangOrders::getHasBattery, queryParam.getHasBattery());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getIsSyncLogisticsDescr()),B2bMabangOrders::getIsSyncLogisticsDescr, queryParam.getIsSyncLogisticsDescr());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getPaypalId()),B2bMabangOrders::getPaypalId, queryParam.getPaypalId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getIsSyncLogistics()),B2bMabangOrders::getIsSyncLogistics, queryParam.getIsSyncLogistics());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getIsSyncPlatform()),B2bMabangOrders::getIsSyncPlatform, queryParam.getIsSyncPlatform());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getIsSyncPlatformDescr()),B2bMabangOrders::getIsSyncPlatformDescr, queryParam.getIsSyncPlatformDescr());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDistrict()),B2bMabangOrders::getDistrict, queryParam.getDistrict());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getPaypalEmail()),B2bMabangOrders::getPaypalEmail, queryParam.getPaypalEmail());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getStreet1()),B2bMabangOrders::getStreet1, queryParam.getStreet1());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getStreet2()),B2bMabangOrders::getStreet2, queryParam.getStreet2());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getIsVirtual()),B2bMabangOrders::getIsVirtual, queryParam.getIsVirtual());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getCity()),B2bMabangOrders::getCity, queryParam.getCity());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getProvince()),B2bMabangOrders::getProvince, queryParam.getProvince());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getPostCode()),B2bMabangOrders::getPostCode, queryParam.getPostCode());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getPhone1()),B2bMabangOrders::getPhone1, queryParam.getPhone1());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getPhone2()),B2bMabangOrders::getPhone2, queryParam.getPhone2());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getEmail()),B2bMabangOrders::getEmail, queryParam.getEmail());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getIsNewOrder()),B2bMabangOrders::getIsNewOrder, queryParam.getIsNewOrder());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDoorcode()),B2bMabangOrders::getDoorcode, queryParam.getDoorcode());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getFbaFlag()),B2bMabangOrders::getFbaFlag, queryParam.getFbaFlag());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getCarrierCode()),B2bMabangOrders::getCarrierCode, queryParam.getCarrierCode());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getShippingService()),B2bMabangOrders::getShippingService, queryParam.getShippingService());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getHasMagnetic()),B2bMabangOrders::getHasMagnetic, queryParam.getHasMagnetic());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getHasPowder()),B2bMabangOrders::getHasPowder, queryParam.getHasPowder());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getHasTort()),B2bMabangOrders::getHasTort, queryParam.getHasTort());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getRemark()),B2bMabangOrders::getRemark, queryParam.getRemark());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getSellerMessage()),B2bMabangOrders::getSellerMessage, queryParam.getSellerMessage());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getCurrencyId()),B2bMabangOrders::getCurrencyId, queryParam.getCurrencyId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getRefundFeeCurrencyId()),B2bMabangOrders::getRefundFeeCurrencyId, queryParam.getRefundFeeCurrencyId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getIsWms()),B2bMabangOrders::getIsWms, queryParam.getIsWms());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getPayType()),B2bMabangOrders::getPayType, queryParam.getPayType());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getVendorId()),B2bMabangOrders::getVendorId, queryParam.getVendorId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getAbnnumber()),B2bMabangOrders::getAbnnumber, queryParam.getAbnnumber());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getCountryNameEn()),B2bMabangOrders::getCountryNameEn, queryParam.getCountryNameEn());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getCountryNameCn()),B2bMabangOrders::getCountryNameCn, queryParam.getCountryNameCn());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getShopName()),B2bMabangOrders::getShopName, queryParam.getShopName());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getMyLogisticsChannelName()),B2bMabangOrders::getMyLogisticsChannelName, queryParam.getMyLogisticsChannelName());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getMyLogisticsId()),B2bMabangOrders::getMyLogisticsId, queryParam.getMyLogisticsId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getMyLogisticsName()),B2bMabangOrders::getMyLogisticsName, queryParam.getMyLogisticsName());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getErpOrderId()),B2bMabangOrders::getErpOrderId, queryParam.getErpOrderId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getBeforeStatus()),B2bMabangOrders::getBeforeStatus, queryParam.getBeforeStatus());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getExtendAttr()),B2bMabangOrders::getExtendAttr, queryParam.getExtendAttr());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getSyncType()),B2bMabangOrders::getSyncType, queryParam.getSyncType());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getSyncStatus()),B2bMabangOrders::getSyncStatus, queryParam.getSyncStatus());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getSyncResultMsg()),B2bMabangOrders::getSyncResultMsg, queryParam.getSyncResultMsg());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getPlatOrdId()),B2bMabangOrders::getPlatOrdId, queryParam.getPlatOrdId());
        
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getOrderWeight()),B2bMabangOrders::getOrderWeight, queryParam.getOrderWeight());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getOrderCost()),B2bMabangOrders::getOrderCost, queryParam.getOrderCost());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getTransportTime()),B2bMabangOrders::getTransportTime, queryParam.getTransportTime());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getQuickPickTime()),B2bMabangOrders::getQuickPickTime, queryParam.getQuickPickTime());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getCreateDate()),B2bMabangOrders::getCreateDate, queryParam.getCreateDate());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getPaidTime()),B2bMabangOrders::getPaidTime, queryParam.getPaidTime());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getOrderFee()),B2bMabangOrders::getOrderFee, queryParam.getOrderFee());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getExpressTime()),B2bMabangOrders::getExpressTime, queryParam.getExpressTime());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getCloseDate()),B2bMabangOrders::getCloseDate, queryParam.getCloseDate());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getFbaStartDateTime()),B2bMabangOrders::getFbaStartDateTime, queryParam.getFbaStartDateTime());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getFbaEndDateTime()),B2bMabangOrders::getFbaEndDateTime, queryParam.getFbaEndDateTime());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getOperTime()),B2bMabangOrders::getOperTime, queryParam.getOperTime());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getPackageWeight()),B2bMabangOrders::getPackageWeight, queryParam.getPackageWeight());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getCurrencyRate()),B2bMabangOrders::getCurrencyRate, queryParam.getCurrencyRate());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getItemTotal()),B2bMabangOrders::getItemTotal, queryParam.getItemTotal());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getShippingFee()),B2bMabangOrders::getShippingFee, queryParam.getShippingFee());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getPlatformFee()),B2bMabangOrders::getPlatformFee, queryParam.getPlatformFee());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getShippingTotalOrigin()),B2bMabangOrders::getShippingTotalOrigin, queryParam.getShippingTotalOrigin());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getItemTotalOrigin()),B2bMabangOrders::getItemTotalOrigin, queryParam.getItemTotalOrigin());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getOriginFax()),B2bMabangOrders::getOriginFax, queryParam.getOriginFax());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getInsuranceFee()),B2bMabangOrders::getInsuranceFee, queryParam.getInsuranceFee());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getInsuranceFeeOrigin()),B2bMabangOrders::getInsuranceFeeOrigin, queryParam.getInsuranceFeeOrigin());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getPaypalFee()),B2bMabangOrders::getPaypalFee, queryParam.getPaypalFee());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getPaypalFeeOrigin()),B2bMabangOrders::getPaypalFeeOrigin, queryParam.getPaypalFeeOrigin());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getItemTotalCost()),B2bMabangOrders::getItemTotalCost, queryParam.getItemTotalCost());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getShippingCost()),B2bMabangOrders::getShippingCost, queryParam.getShippingCost());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getShippingPreCost()),B2bMabangOrders::getShippingPreCost, queryParam.getShippingPreCost());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getPackageFee()),B2bMabangOrders::getPackageFee, queryParam.getPackageFee());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getFbaPerOrderFulfillmentFee()),B2bMabangOrders::getFbaPerOrderFulfillmentFee, queryParam.getFbaPerOrderFulfillmentFee());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getFbaCommission()),B2bMabangOrders::getFbaCommission, queryParam.getFbaCommission());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getPromotionAmount()),B2bMabangOrders::getPromotionAmount, queryParam.getPromotionAmount());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getAllianceFeeOrigin()),B2bMabangOrders::getAllianceFeeOrigin, queryParam.getAllianceFeeOrigin());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getVoucherPriceOrigin()),B2bMabangOrders::getVoucherPriceOrigin, queryParam.getVoucherPriceOrigin());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getSubsidyAmountOrigin()),B2bMabangOrders::getSubsidyAmountOrigin, queryParam.getSubsidyAmountOrigin());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getCodCharge()),B2bMabangOrders::getCodCharge, queryParam.getCodCharge());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getAllianceFee()),B2bMabangOrders::getAllianceFee, queryParam.getAllianceFee());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getFbaPerUnitFulfillmentFee()),B2bMabangOrders::getFbaPerUnitFulfillmentFee, queryParam.getFbaPerUnitFulfillmentFee());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getFbaWeightBasedFee()),B2bMabangOrders::getFbaWeightBasedFee, queryParam.getFbaWeightBasedFee());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getPlatformFeeOrigin()),B2bMabangOrders::getPlatformFeeOrigin, queryParam.getPlatformFeeOrigin());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getVoucherPrice()),B2bMabangOrders::getVoucherPrice, queryParam.getVoucherPrice());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getSubsidyAmount()),B2bMabangOrders::getSubsidyAmount, queryParam.getSubsidyAmount());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getShippingWeight()),B2bMabangOrders::getShippingWeight, queryParam.getShippingWeight());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getSyncTime()),B2bMabangOrders::getSyncTime, queryParam.getSyncTime());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getSyncSuccessTimes()),B2bMabangOrders::getSyncSuccessTimes, queryParam.getSyncSuccessTimes());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getSyncFailTimes()),B2bMabangOrders::getSyncFailTimes, queryParam.getSyncFailTimes());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getCreateTime()),B2bMabangOrders::getCreateTime, queryParam.getCreateTime());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getUpdateTime()),B2bMabangOrders::getUpdateTime, queryParam.getUpdateTime());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getCreateMat()),B2bMabangOrders::getCreateMat, queryParam.getCreateMat());
        //2. 执行分页查询
        Page<B2bMabangOrdersResult> pagin = new Page<>(current , size , true);
        IPage<B2bMabangOrdersResult> selectResult = b2bMabangOrdersMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param b2bMabangOrders 实例对象
     * @return 实例对象
     */
    @DataSource(name = "external")
    @Override
    public B2bMabangOrders insert(B2bMabangOrders b2bMabangOrders){
        b2bMabangOrdersMapper.insert(b2bMabangOrders);
        return b2bMabangOrders;
    }

    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @DataSource(name = "external")
    @Override
    public boolean deleteById(String id){
        int total = b2bMabangOrdersMapper.deleteById(id);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param idList 主键List
     * @return 是否成功
     */
    @DataSource(name = "external")
    @Override
    public boolean deleteBatchIds(List<String> idList){
         int delCount = b2bMabangOrdersMapper.deleteBatchIds(idList);
         if (idList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
    /**
     * 更新作废订单
     *  1.有指定订单则去重,无指定订单则查询财务付款未关闭的订单
     *  2.订单10个一组拼接为字符串作为马帮接口查询条件
     *  3.遍历调用马帮接口按非作废订单和作废订单分别处理,非作废订单更新订单及明细,作废订单未财务确认则删除,已财务确认则不做处理
     *
     * @param updateOrderList 需更新的订单号
     * @return ResponseData
     */
    @DataSource(name = "external")
    @Override
    public ResponseData updateCancelOrder(List<String> updateOrderList) {
        try {
            // 未指定订单则查询财务付款未关闭的订单
            if (ObjectUtil.isEmpty(updateOrderList)) {
                updateOrderList = new LambdaQueryChainWrapper<>(b2bPaymentMapper)
                        .ne(B2bPayment::getMcStatus, "正常关闭")
                        .ne(B2bPayment::getMcStatus, "财务关闭")
                        .list()
                        .stream()
                        .map(B2bPayment::getPlatformOrderId)
                        .distinct()
                        .collect(Collectors.toList());
            } else {// 指定订单则去重
                 updateOrderList = updateOrderList.stream()
                        .distinct()
                         .collect(Collectors.toList());
            }
            //订单10个一组拼接为字符串作为马帮接口查询条件
            int chunkSize = 1;
            List<String> finalOrderList = updateOrderList;
            List<String> orders = finalOrderList
                                            .stream()
                                            .collect(Collectors.groupingBy(index -> finalOrderList.indexOf(index) / chunkSize))
                                            .values()
                                            .stream()
                                            .map(chunk -> String.join(",", chunk))
                                            .collect(Collectors.toList());
            //遍历调用马帮接口按非作废订单和作废订单分别处理
            for (String order : orders) {
                //全部状态订单
                OrderParm allParam = OrderParm.builder().platformOrderIds(order).allstatus("1").canSend("3").build();
                MabangHeadParm mabangHeadSendParam = new MabangHeadParm("order-get-order-list-new", allParam);
                MabangResult allMabangResult = mabangRequstService.getOrderListNew(mabangHeadSendParam);

                //过滤出已作废(状态5)订单
                OrderParm cancelParam = OrderParm.builder().platformOrderIds(order).status("5").build();
                MabangHeadParm mabangHeadCancelParam = new MabangHeadParm("order-get-order-list-new", cancelParam);
                MabangResult cancelMabangResult = mabangRequstService.getOrderListNew(mabangHeadCancelParam);

                String code = allMabangResult.getCode();
                if ("200".equals(code) && ObjectUtil.isNotEmpty(allMabangResult.getData())) {
                    // 调用接口,更新非作废的订单及订单明细
                    List<MabangShopList> otherShopList = shopListService.list()
                                                        .stream()
                                                        .filter(s -> "Other".equals(s.getPlatformName()))
                                                        .collect(Collectors.toList());

                    List<MabangShopList> shopList = otherShopList
                                                    .stream()
                                                    .filter(s -> !"平台发展".equals(s.getName()) && !"BTB".equals(s.getName()))
                                                    .collect(Collectors.toList());

                    List<String> shopNameList = shopList
                                                .stream()
                                                .map(MabangShopList::getName)
                                                .collect(Collectors.toList());
                    this.updateSendOrders(allMabangResult, shopNameList);

                    //  已作废的订单,若财务付款未确认则删除订单明细,订单,付款及明细
                    String canceledOrdersListJson = JSON.toJSONString(((Map<?, ?>) cancelMabangResult.getData()).get("data"));
                    List<B2bMabangOrders> canceledOrders = JSON.parseArray(canceledOrdersListJson, B2bMabangOrders.class);

                    canceledOrders.forEach(canceledOrder-> {
                        String platformOrderId = canceledOrder.getPlatformOrderId();
                        //查询付款明细中是否已确认的条数
                        int isPayConfirmCount =  new LambdaQueryChainWrapper<>(b2bPaymentDetailMapper)
                                    .eq(B2bPaymentDetail::getPlatformOrderId, platformOrderId)
                                    .eq(B2bPaymentDetail::getConfirmStatus, "已确认").count();
                        //若付款明细中已确认的条数等于0,则删除订单明细,订单,付款及明细
                        if (isPayConfirmCount == 0) {
                                List<String> idList = new LambdaQueryChainWrapper<>(b2bMabangOrdersMapper)
                                        .eq(B2bMabangOrders::getPlatformOrderId, platformOrderId)
                                        .list()
                                        .stream()
                                        .map(B2bMabangOrders::getId)
                                        .collect(Collectors.toList());

                                new LambdaUpdateChainWrapper<>(b2bMabangOrderItemMapper)
                                        .in(B2bMabangOrderItem::getOriginOrderId, idList)
                                        .remove();

                                new LambdaUpdateChainWrapper<>(b2bMabangOrdersMapper)
                                        .eq(B2bMabangOrders::getPlatformOrderId, platformOrderId)
                                        .remove();

                                new LambdaUpdateChainWrapper<>(b2bPaymentMapper)
                                        .eq(B2bPayment::getPlatformOrderId, platformOrderId)
                                        .remove();

                                new LambdaUpdateChainWrapper<>(b2bPaymentDetailMapper)
                                        .eq(B2bPaymentDetail::getPlatformOrderId, platformOrderId)
                                        .remove();
                            }
                    });
                }
            }
            return ResponseData.success();
        } catch (Exception e) {
            log.error("更新作废订单异常>updateCancelOrder："+e.getMessage());
            return ResponseData.error(e.getMessage());
        }
    }


    /**
      * 同步配货中的订单列表
      * @param param
      * @return
      */
     @DataSource(name = "external")
     @Override
     public ResponseData preparingStockOrderList(OrderParm param) {
        LocalDateTime yesterdayTime = LocalDateTime.now().minusDays(1l).plusMinutes(1);
         try {
             if (param.getCreateDateStart() == null || param.getCreateDateEnd() == null || param.getStatus() == null) {
                 OrderParm defaultParam = OrderParm.builder()
                         .createDateEnd(DateUtil.date())
                         .createDateStart(Date.from(yesterdayTime.atZone(ZoneId.systemDefault()).toInstant()))
                         .status(MabangConstant.PENDING).build();
                 BeanUtils.copyProperties(defaultParam, param);
             } else {
                 param.setCreateDateStart(param.getCreateDateStart());
                 param.setCreateDateEnd(param.getCreateDateEnd());
                 param.setStatus(ObjectUtil.isNotEmpty(param.getStatus()) ? param.getStatus() : MabangConstant.PENDING);
             }

             //只针对平台是：other,店铺非'平台发展','BTB'的店铺订单保存
             List<MabangShopList> otherShopList = shopListService.list().stream().filter(s -> "Other".equals(s.getPlatformName())).collect(Collectors.toList());

             if (ObjectUtil.isEmpty(otherShopList)) {
                 log.error("B2B订单同步未找到Other平台下的店铺");
                 return ResponseData.error("B2B订单同步未找到Other平台下的店铺");
             }
             List<MabangShopList> shopList = otherShopList.stream().filter(s -> !"平台发展".equals(s.getName()) && !"BTB".equals(s.getName())).collect(Collectors.toList());
             if (ObjectUtil.isEmpty(shopList)) {
                 log.error("B2B订单同步未找到Other平台下,过滤掉'平台发展,BTB'后没有其他店铺");
                 return ResponseData.error("B2B订单同步未找到Other平台下,过滤掉'平台发展,BTB'后没有其他店铺");
             }
             List<String> shopNameList = shopList.stream().map(MabangShopList::getName).collect(Collectors.toList());


             MabangHeadParm mabangHeadParm = new MabangHeadParm("order-get-order-list-new", param);
             MabangResult mabangResult = mabangRequstService.getOrderList(mabangHeadParm);
             //调用是否成功,是否hasNext,nextCursor是什么
             if (mabangResult.getCode().equals("200")) {
                 String nextCursor = (String) (((Map<?, ?>) mabangResult.getData()).get("nextCursor"));
                 Boolean hasNext = (Boolean) (((Map<?, ?>) mabangResult.getData()).get("hasNext"));
                 this.addNew(mabangResult,shopNameList);
                 while (hasNext) {
                     param.setCursor(nextCursor);
                     MabangHeadParm mabangHeadParmPage = new MabangHeadParm("order-get-order-list-new", param);
                     MabangResult mabangResultPage = mabangRequstService.getOrderListNew(mabangHeadParmPage);
                     hasNext = (Boolean) (((Map<?, ?>) mabangResultPage.getData()).get("hasNext"));
                     if (hasNext) {
                         nextCursor = (String) (((Map<?, ?>) mabangResultPage.getData()).get("nextCursor"));
                     }
                     this.addNew(mabangResultPage,shopNameList);
                 }
             }
         } catch (Exception e) {
             log.error("同步马帮B2B订单异常："+JSON.toJSONString(e));
             return ResponseData.error(e.getMessage());
         }


         return ResponseData.success();
     }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(MabangResult param, List<String> shopNameList) {
        try {
            String ordersListJson = JSON.toJSONString(((Map<?, ?>) param.getData()).get("data"));
            List<B2bMabangOrders> B2bOrdersList = JSON.parseArray(ordersListJson, B2bMabangOrders.class);
            if (CollectionUtil.isEmpty(B2bOrdersList)) {
                log.info(DateUtil.date() + ">>B2bMabangOrders.add>>数据为空!");
                return;
            }
            List<String> coustomerName = Arrays.asList("采购中心");

            //遍历单头
            for (B2bMabangOrders b2bMabangOrders : B2bOrdersList) {

                if (! shopNameList.contains(b2bMabangOrders.getShopName())) {
                    log.info("该订单{}所属店铺{}不在B2B订单业务范围内",b2bMabangOrders.getPlatformOrderId(),b2bMabangOrders.getShopName());
                    continue;
                }

                if ( coustomerName.contains(b2bMabangOrders.getBuyerName()) ||  coustomerName.contains(b2bMabangOrders.getBuyerUserId())) {
                    log.info("该订单{}--客户{}不需要产生付款凭证",b2bMabangOrders.getPlatformOrderId(),b2bMabangOrders.getBuyerName());
                    continue;
                }

                try {
                    //拆单导致的单身明细为空
                    boolean isSplitEmpty = false;
                    //拆单或者重发明细id需重置
                    boolean isSplit = false;
                    b2bMabangOrders.setId(b2bMabangOrders.getErpOrderId());
                    b2bMabangOrders.setSyncTime(DateUtil.date());
                    String platOrdId = b2bMabangOrders.getPlatformId().equals(ShopListEnum.Mercadolibre.getCode()) ? b2bMabangOrders.getPlatformOrderId() : b2bMabangOrders.getSalesRecordNumber();
                    b2bMabangOrders.setPlatOrdId(platOrdId);
                    if (b2bMabangOrders.getPlatformOrderId().contains("_")) {
                        isSplit = true;
                    }

                    //遍历单身为空时修改状态为isSplitEmpty 为 true
                    if (CollUtil.isNotEmpty(Arrays.asList(b2bMabangOrders.getOrderItem()))) {
                        List<B2bMabangOrderItem> itemList = JSON.parseArray(JSON.toJSONString(b2bMabangOrders.getOrderItem()), B2bMabangOrderItem.class);
                        int idx = 1;
                        for (B2bMabangOrderItem item : itemList) {
                            item.setId(b2bMabangOrders.getId() + "_" + idx);
                            item.setUpdateTime(DateUtil.date());
                            idx++;

                            if (isSplit) {
                                item.setItemRemark(ObjectUtil.defaultIfEmpty(item.getItemRemark(), () -> item.getItemRemark() + "|" + item.getOriginOrderId(), item.getOriginOrderId()));
                                item.setOriginOrderId(b2bMabangOrders.getErpOrderId());
                            }
                            String stockSku = item.getStockSku();
                            String platformSku = item.getPlatformSku();
                            if (ObjectUtil.isEmpty(platformSku) || (!stockSku.equals(platformSku) && ((stockSku.length() == 8 && platformSku.length() == 8) || (stockSku.length() == 9 && platformSku.length() == 9)))) {
                                item.setPlatformSku(stockSku);
                            }
                        }
                        //B2B订单时抓取创建完成的状态，可能会删减里面的物料信息，因此每次抓取回来的时候直接删除原来的，然后保存最新的
                        itemService.refreshData(itemList);

                        //明细为空则认为是拆单
                    } else {
                        isSplitEmpty = true;
                    }

                    //插入或更新单头
                    if ("RMB".equals(b2bMabangOrders.getCurrencyId())) {
                        b2bMabangOrders.setCurrencyId("CNY");
                    }
                    this.saveOrUpdate(b2bMabangOrders);

                    //生成付款流水信息（1条汇总信息，1条暂存付款明细信息）
                    b2bPaymentService.createOrUpdateB2bPayment(b2bMabangOrders);

                } catch (Exception e) {
                    log.error("B2bMabangOrders.add订单【{}】--插入异常:{}" ,b2bMabangOrders.getPlatformOrderId(), JSONUtil.toJsonStr(e));
                }
            }
        } catch (Exception e) {
            log.error("B2bMabangOrders.add异常>>" + e.getMessage());

        }
    }



    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addNew(MabangResult param, List<String> shopNameList) {
        try {
            String ordersListJson = JSON.toJSONString(((Map<?, ?>) param.getData()).get("data"));
            List<B2bMabangOrders> B2bOrdersList = JSON.parseArray(ordersListJson, B2bMabangOrders.class);
            if (CollectionUtil.isEmpty(B2bOrdersList)) {
                log.info(DateUtil.date() + ">>B2bMabangOrders.add>>数据为空!");
                return;
            }
            List<String> coustomerName = Arrays.asList("采购中心");
            Map<String, String> operIdNameMap = new LambdaQueryChainWrapper<>(employeeMapper)
                    .list().stream().collect(Collectors.toMap(MabangEmployee::getEmployeeId, MabangEmployee::getName));


            //遍历单头
            for (B2bMabangOrders b2bMabangOrders : B2bOrdersList) {

                if (! shopNameList.contains(b2bMabangOrders.getShopName())) {
                    log.info("该订单{}所属店铺{}不在B2B订单业务范围内",b2bMabangOrders.getPlatformOrderId(),b2bMabangOrders.getShopName());
                    continue;
                }

                if ( coustomerName.contains(b2bMabangOrders.getBuyerName()) ||  coustomerName.contains(b2bMabangOrders.getBuyerUserId())) {
                    log.info("该订单{}--客户{}不需要产生付款凭证",b2bMabangOrders.getPlatformOrderId(),b2bMabangOrders.getBuyerName());
                    continue;
                }

                try {
                    //拆单导致的单身明细为空
                    boolean isSplitEmpty = false;
                    //拆单或者重发明细id需重置
                    boolean isSplit = false;
                    b2bMabangOrders.setId(b2bMabangOrders.getErpOrderId());
                    b2bMabangOrders.setSyncTime(DateUtil.date());
                    String platOrdId = b2bMabangOrders.getPlatformId().equals(ShopListEnum.Mercadolibre.getCode()) ? b2bMabangOrders.getPlatformOrderId() : b2bMabangOrders.getSalesRecordNumber();
                    b2bMabangOrders.setPlatOrdId(platOrdId);
                    if (b2bMabangOrders.getPlatformOrderId().contains("_")) {
                        isSplit = true;
                    }

                    //遍历单身为空时修改状态为isSplitEmpty 为 true
                    if (CollUtil.isNotEmpty(Arrays.asList(b2bMabangOrders.getOrderItem()))) {
                        List<B2bMabangOrderItem> itemList = JSON.parseArray(JSON.toJSONString(b2bMabangOrders.getOrderItem()), B2bMabangOrderItem.class);
                        String operId = itemList.get(0).getOperId();
                        String operName = operIdNameMap.get(operId);
                        b2bMabangOrders.setShopEmployeeName(StrUtil.isNotEmpty(operName) ? (operName) : operId);
                        b2bMabangOrders.setShopEmployeeId(operId);
                        int idx = 1;
                        for (B2bMabangOrderItem item : itemList) {
                            item.setId(b2bMabangOrders.getId() + "_" + idx);
                            item.setUpdateTime(DateUtil.date());
                            idx++;

                            if (isSplit) {
                                item.setItemRemark(ObjectUtil.defaultIfEmpty(item.getItemRemark(), () -> item.getItemRemark() + "|" + item.getOriginOrderId(), item.getOriginOrderId()));
                                item.setOriginOrderId(b2bMabangOrders.getErpOrderId());
                            }
                            String stockSku = item.getStockSku();
                            String platformSku = item.getPlatformSku();
                            if (ObjectUtil.isEmpty(platformSku) || (!stockSku.equals(platformSku) && ((stockSku.length() == 8 && platformSku.length() == 8) || (stockSku.length() == 9 && platformSku.length() == 9)))) {
                                item.setPlatformSku(stockSku);
                            }
                        }
                        //B2B订单时抓取创建完成的状态，可能会删减里面的物料信息，因此每次抓取回来的时候直接删除原来的，然后保存最新的
                        itemService.refreshData(itemList);

                        //明细为空则认为是拆单
                    } else {
                        isSplitEmpty = true;
                    }

                    //插入或更新单头
                    if ("RMB".equals(b2bMabangOrders.getCurrencyId())) {
                        b2bMabangOrders.setCurrencyId("CNY");
                    }
                    this.saveOrUpdate(b2bMabangOrders);

                    //生成付款流水信息（1条汇总信息，1条暂存付款明细信息）
                    b2bPaymentService.createOrUpdateB2bPayment(b2bMabangOrders);

                } catch (Exception e) {
                    log.error("B2bMabangOrders.add订单【{}】--插入异常:{}" ,b2bMabangOrders.getPlatformOrderId(), JSONUtil.toJsonStr(e));
                }
            }
        } catch (Exception e) {
            log.error("B2bMabangOrders.add异常>>" + e.getMessage());

        }
    }


    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSendOrders(MabangResult param, List<String> shopNameList) {
        try {
            String ordersListJson = JSON.toJSONString(((Map<?, ?>) param.getData()).get("data"));
            List<B2bMabangOrders> B2bOrdersList = JSON.parseArray(ordersListJson, B2bMabangOrders.class);
            //过滤掉已作废的订单
            B2bOrdersList = B2bOrdersList.stream().filter(item -> !"5".equals(item.getOrderStatus())).collect(Collectors.toList());
            if (CollectionUtil.isEmpty(B2bOrdersList)) {
                log.info(DateUtil.date() + ">>B2bMabangOrders.add>>数据为空!");
                return;
            }
            List<String> coustomerName = Arrays.asList("采购中心");
            Map<String, String> operIdNameMap = new LambdaQueryChainWrapper<>(employeeMapper)
                    .list().stream().collect(Collectors.toMap(MabangEmployee::getEmployeeId, MabangEmployee::getName));


            //遍历单头
            for (B2bMabangOrders b2bMabangOrders : B2bOrdersList) {

                if (! shopNameList.contains(b2bMabangOrders.getShopName())) {
                    log.info("该订单{}所属店铺{}不在B2B订单业务范围内",b2bMabangOrders.getPlatformOrderId(),b2bMabangOrders.getShopName());
                    continue;
                }

                if ( coustomerName.contains(b2bMabangOrders.getBuyerName()) ||  coustomerName.contains(b2bMabangOrders.getBuyerUserId())) {
                    log.info("该订单{}--客户{}不需要产生付款凭证",b2bMabangOrders.getPlatformOrderId(),b2bMabangOrders.getBuyerName());
                    continue;
                }

                try {
                    //拆单导致的单身明细为空
                    boolean isSplitEmpty = false;
                    //拆单或者重发明细id需重置
                    boolean isSplit = false;
                    b2bMabangOrders.setId(b2bMabangOrders.getErpOrderId());
                    b2bMabangOrders.setSyncTime(DateUtil.date());
                    String platOrdId = b2bMabangOrders.getPlatformId().equals(ShopListEnum.Mercadolibre.getCode()) ? b2bMabangOrders.getPlatformOrderId() : b2bMabangOrders.getSalesRecordNumber();
                    b2bMabangOrders.setPlatOrdId(platOrdId);
                    if (b2bMabangOrders.getPlatformOrderId().contains("_")) {
                        isSplit = true;
                    }

                    //遍历单身为空时修改状态为isSplitEmpty 为 true
                    if (CollUtil.isNotEmpty(Arrays.asList(b2bMabangOrders.getOrderItem()))) {
                        List<B2bMabangOrderItem> itemList = JSON.parseArray(JSON.toJSONString(b2bMabangOrders.getOrderItem()), B2bMabangOrderItem.class);
                        String operId = itemList.get(0).getOperId();
                        String operName = operIdNameMap.get(operId);
                        b2bMabangOrders.setShopEmployeeName(StrUtil.isNotEmpty(operName) ? (operName) : operId);
                        b2bMabangOrders.setShopEmployeeId(operId);
                        int idx = 1;
                        for (B2bMabangOrderItem item : itemList) {
                            item.setId(b2bMabangOrders.getId() + "_" + idx);
                            item.setUpdateTime(DateUtil.date());
                            idx++;

                            if (isSplit) {
                                item.setItemRemark(ObjectUtil.defaultIfEmpty(item.getItemRemark(), () -> item.getItemRemark() + "|" + item.getOriginOrderId(), item.getOriginOrderId()));
                                item.setOriginOrderId(b2bMabangOrders.getErpOrderId());
                            }
                            String stockSku = item.getStockSku();
                            String platformSku = item.getPlatformSku();
                            if (ObjectUtil.isEmpty(platformSku) || (!stockSku.equals(platformSku) && ((stockSku.length() == 8 && platformSku.length() == 8) || (stockSku.length() == 9 && platformSku.length() == 9)))) {
                                item.setPlatformSku(stockSku);
                            }
                        }
                        //B2B订单时抓取创建完成的状态，可能会删减里面的物料信息，因此每次抓取回来的时候直接删除原来的，然后保存最新的
                        itemService.refreshData(itemList);

                        //明细为空则认为是拆单
                    } else {
                        isSplitEmpty = true;
                    }

                    //插入或更新单头
                    if ("RMB".equals(b2bMabangOrders.getCurrencyId())) {
                        b2bMabangOrders.setCurrencyId("CNY");
                    }
                    this.saveOrUpdate(b2bMabangOrders);

                    //生成付款流水信息（1条汇总信息，1条暂存付款明细信息）
                    b2bPaymentService.createOrUpdateB2bPayment(b2bMabangOrders);

                } catch (Exception e) {
                    log.error("B2bMabangOrders.add订单【{}】--插入异常:{}" ,b2bMabangOrders.getPlatformOrderId(), JSONUtil.toJsonStr(e));
                }
            }
        } catch (Exception e) {
            log.error("B2bMabangOrders.add异常>>" + e.getMessage());

        }
    }


 }