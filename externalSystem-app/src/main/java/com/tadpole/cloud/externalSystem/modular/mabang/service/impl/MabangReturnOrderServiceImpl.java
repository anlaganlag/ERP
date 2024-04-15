package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangReturnOrder;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangReturnOrderItem;
import com.tadpole.cloud.externalSystem.modular.mabang.enums.ShopListEnum;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.MabangReturnOrderMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangReturnOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.MabangHeadParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.OrderParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangReturnOrderResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.MabangResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangRequstService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangReturnOrderItemService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangReturnOrderService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangShopListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* <p>
    * 马帮退货单列表 服务实现类
    * </p>
*
* @author lsy
* @since 2022-08-24
*/
@Service
@Slf4j
public class MabangReturnOrderServiceImpl extends ServiceImpl<MabangReturnOrderMapper, MabangReturnOrder> implements IMabangReturnOrderService {

    @Resource
    private MabangReturnOrderMapper mapper;

    @Resource
    private IMabangReturnOrderService mabangReturnOrderService;

    @Resource
    private IMabangReturnOrderItemService mabangReturnOrderItemService;

    @Resource
    private IMabangShopListService mabangShopListService;


    @Resource
    IMabangRequstService mabangRequstService;


    @DataSource(name = "external")
    @Override
    public PageResult<MabangReturnOrderResult> listBySpec(MabangReturnOrderParam param) {
        Page pageContext = getPageContext();
        IPage<MabangReturnOrderResult> page = mapper.listBySpec(pageContext, param);
        return new PageResult<>(page);
    }


    @DataSource(name = "external")
    @Override
    public List<MabangReturnOrderResult> exportList(MabangReturnOrderParam param) {
        return mapper.exportList(param);
    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(MabangResult param) {
        try {
            String ordersListJson = JSON.toJSONString(((Map<?, ?>) param.getData()).get("data"));
            List<MabangReturnOrder> ordersList = JSON.parseArray(ordersListJson, MabangReturnOrder.class);
            if (CollectionUtil.isEmpty(ordersList)) {
                log.info(DateUtil.date() + "MabangReturnOrders.add>>数据为空!");
                return;
            }
            //遍历单头
            for (MabangReturnOrder mabangReturnOrder : ordersList) {
                try {
                    //使用平台订单作为数据ID
                    String platOrdId = mabangReturnOrder.getPlatformId().equals(ShopListEnum.Mercadolibre.getCode()) ? mabangReturnOrder.getPlatformOrderId() : mabangReturnOrder.getSalesRecordNumber();
                    mabangReturnOrder.setId(platOrdId);
                    mabangReturnOrder.setPlatOrdId(platOrdId);
                    mabangReturnOrder.setSyncTime(DateUtil.date());
                    if (CollUtil.isNotEmpty(Arrays.asList(mabangReturnOrder.getItem()))) {
                        List<MabangReturnOrderItem> itemList = JSON.parseArray(JSON.toJSONString(mabangReturnOrder.getItem()), MabangReturnOrderItem.class);
                        int idx = 1;
                        for (MabangReturnOrderItem item : itemList) {
                            String id = mabangReturnOrder.getId();
                            item.setId(id + "_" + idx);
                            item.setPlatOrdId(id);
                            item.setSalesRecordNumber(id);
                            item.setSyncType(ShopListEnum.SYS_SYN.getCode());
                            item.setUpdateTime(DateUtil.date());
                            idx++;
                        }
                        mabangReturnOrderItemService.saveOrUpdateBatch(itemList);
                    }
                    if (ObjectUtil.isEmpty(mabangReturnOrder.getBuyerName())) {
                        mabangReturnOrder.setBuyerName("BuyerName为空");
                    }
                    //不为海外仓且不拆单为空无需改状态
                    this.saveOrUpdate(mabangReturnOrder);
                } catch (Exception e) {
                    log.error("MabangOrders.add单条插入异常>>" + e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("MabangOrdersMabangOrders.add异常>>" + e.getMessage());

        }
    }


    @DataSource(name = "external")
    @Override
    public ResponseData getReturnOrderList(OrderParm param) {
        try {
            //拉取24小时内已完成订单和已作废订单的默认参数
            OrderParm queryParam = OrderParm.builder().createDateStart(DateUtil.offsetDay(DateUtil.date(), -1)).createDateEnd(DateUtil.date()).status(ShopListEnum.RETURN_FINISHED_STATUS.getCode()).build();
            if (StrUtil.isNotEmpty(param.getStatus()) || param.getCreateDateStart() != null || param.getCreateDateEnd() != null ) {
                BeanUtils.copyProperties(param,queryParam);
            }
            MabangResult mabangFinishedResult = mabangRequstService.returnOrderList(new MabangHeadParm("order-get-return-order-list", queryParam));
            Integer pageCount = (Integer) (((Map<?, ?>) mabangFinishedResult.getData()).get("pageCount"));
            //拉取已完成状态订单
            if (mabangFinishedResult.getCode().equals("200") && pageCount >0) {
                for (int i = 0; i < pageCount; i++) {
                    queryParam.setPage(i + 1);
                    MabangResult mabangResultPage = mabangRequstService.returnOrderList(new MabangHeadParm("order-get-return-order-list", queryParam));
                    this.add(mabangResultPage);
                }
            }
            //重新拉已作废和第一页的数据
            queryParam.setStatus(ShopListEnum.RETURN_REJECTED_STATUS.getCode());
            queryParam.setPage(1);
            MabangResult mabangRejectedResult = mabangRequstService.returnOrderList(new MabangHeadParm("order-get-return-order-list", queryParam));
            pageCount = (Integer) (((Map<?, ?>) mabangRejectedResult.getData()).get("pageCount"));
            //拉取已作废订单
            if (mabangRejectedResult.getCode().equals("200") && pageCount >0 ) {
                for (int i = 0; i < pageCount; i++) {
                    queryParam.setPage(i + 1);
                    MabangResult mabangResultPage = mabangRequstService.returnOrderList(new MabangHeadParm("order-get-return-order-list", queryParam));
                    this.add(mabangResultPage);
                }

            }
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
        return ResponseData.success();
    }



    @DataSource(name = "external")
    @Override
    public ResponseData queryPlatformNames(){
        return  mabangShopListService.queryPlatformNames();
    }

    @DataSource(name = "external")
    @Override
    public ResponseData queryShopName(){
        return  ResponseData.success(mabangShopListService.getShopSelect());
    }

    @DataSource(name = "external")
    @Override
    public ResponseData querySite(){
        List<String> siteList = mabangShopListService.getSiteSelect();
        return  ResponseData.success(siteList);
    }

    @DataSource(name = "external")
    @Override
    public ResponseData queryStatus(){
        return  ResponseData.success(mapper.getStatusSelect());
    }

    @DataSource(name = "external")
    @Override
    public ResponseData getHisReturnOrderList(OrderParm param) {
        Date endDate =ObjectUtil.isEmpty(param.getCreateDateEnd())?DateUtil.date():param.getCreateDateEnd();
        Date startDate = DateUtil.offsetDay(endDate, -1);
        Date beginDate = DateUtil.offsetDay(endDate, -31);
        for (int i = 0; i < 31; i++) {
            OrderParm queryParam = OrderParm.builder().createDateStart(startDate).createDateEnd(endDate).status(ShopListEnum.RETURN_FINISHED_STATUS.getCode()).build();
            MabangResult mabangResult = mabangRequstService.returnOrderList(new MabangHeadParm("order-get-return-order-list", queryParam));
            Integer pageCount = (Integer) (((Map<?, ?>) mabangResult.getData()).get("pageCount"));
            if (mabangResult.getCode().equals("200") && pageCount > 0) {
                for (int j = 0; j < pageCount; j++) {
                    queryParam.setPage(j + 1);
                    MabangResult mabangResultPage = mabangRequstService.returnOrderList(new MabangHeadParm("order-get-return-order-list", queryParam));
                    this.add(mabangResultPage);
                }
            }
            //拉取历史已作废订单
            queryParam.setStatus(ShopListEnum.RETURN_REJECTED_STATUS.getCode());
            queryParam.setPage(1);
            MabangResult mabangRejectedResult = mabangRequstService.returnOrderList(new MabangHeadParm("order-get-return-order-list", queryParam));
            pageCount = (Integer) (((Map<?, ?>) mabangRejectedResult.getData()).get("pageCount"));
            if (mabangRejectedResult.getCode().equals("200") && pageCount > 0 ) {
                for (int j = 0; j < pageCount; j++) {
                    queryParam.setPage(j + 1);
                    MabangResult mabangResultPage = mabangRequstService.returnOrderList(new MabangHeadParm("order-get-return-order-list", queryParam));
                    this.add(mabangResultPage);
                }

            }
            endDate = DateUtil.offsetDay(endDate, -1);
            startDate = DateUtil.offsetDay(startDate, -1);
            if (DateUtil.between(startDate, beginDate, DateUnit.DAY) == 0) {
                break;
            }
        }
        return ResponseData.success();
    }


    private Page getPageContext() {
        return PageFactory.defaultPage();
    }


}
