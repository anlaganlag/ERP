package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.constants.StockConstant;
import com.tadpole.cloud.operationManagement.modular.stock.consumer.SyncToErpConsumer;
import com.tadpole.cloud.operationManagement.modular.stock.entity.PmcVerifInfo;
import com.tadpole.cloud.operationManagement.modular.stock.entity.PurchaseOrders;
import com.tadpole.cloud.operationManagement.modular.stock.entity.StockPmcVerifK3;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TeamVerif;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.StockPmcVerifK3Mapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.K3PurchaseOrderApplyItem;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.K3PurchaseOrderApplyParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockPmcVerifK3Param;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockPmcVerifK3Result;
import com.tadpole.cloud.operationManagement.modular.stock.service.IPmcVerifInfoService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IPurchaseOrdersService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockPmcVerifK3Service;
import com.tadpole.cloud.operationManagement.modular.stock.service.ITeamVerifService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* <p>
    * PMC审核调用k3记录信息 服务实现类
    * </p>
*
* @author lsy
* @since 2022-09-07
*/
@Slf4j
@Service
public class StockPmcVerifK3ServiceImpl extends ServiceImpl<StockPmcVerifK3Mapper, StockPmcVerifK3> implements IStockPmcVerifK3Service {

    @Resource
    private StockPmcVerifK3Mapper mapper;

    @Autowired
    SyncToErpConsumer syncToErpConsumer;

    @Autowired
    IPmcVerifInfoService pmcVerifInfoService;

    @Autowired
    IPurchaseOrdersService purchaseOrdersService;

    @Autowired
    ITeamVerifService teamVerifService;



    /**
     * 同步采购订单到k3
     *
     * @param pmcVerifInfoList
     * @return
     */
    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public StockPmcVerifK3 callK3(List<PmcVerifInfo> pmcVerifInfoList, boolean needSave) {

        boolean syncResult = false;
//        PmcVerifInfo pmcVerifInfo = pmcVerifInfoList.get(0);

        StockPmcVerifK3 pmcVerifK3 = new StockPmcVerifK3();
        K3PurchaseOrderApplyParam orderApplyParam = this.initQuestK3Parm(pmcVerifInfoList,pmcVerifK3);

        if (ObjectUtil.isNull(orderApplyParam)) {
            log.error("初始化请求k3接口的参数异常：未找到Team审核记录信息，");
            return null;
        }

      /*  String billType = "HHLX";//混合类型
        Date operExpectedDate = pmcVerifInfoList.stream().map(p -> p.getOperExpectedDate()).min(Date::compareTo).get();
        BigDecimal qty = pmcVerifInfoList.stream().map(p -> p.getQty()).reduce((a, b) -> a.add(b)).get();
        Set<String> billTypeSet = pmcVerifInfoList.stream().map(p -> p.getBillType()).collect(Collectors.toSet());
        if (billTypeSet.size()==1) {
            billType = billTypeSet.stream().collect(Collectors.toList()).get(0);
        }

        boolean syncResult = false;

        LoginContext current = SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        LambdaQueryWrapper<TeamVerif> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeamVerif::getPurchaseApplyNo, pmcVerifInfo.getPurchaseOrderId());
        List<TeamVerif> teamVerifList = teamVerifService.list(wrapper);

        ArrayList<K3PurchaseOrderApplyItem> itemList = new ArrayList<>();
        if (ObjectUtil.isEmpty(teamVerifList)) {
            return syncResult;
        }

        //item只有一项
        TeamVerif teamVerif = teamVerifList.get(0);
        K3PurchaseOrderApplyItem applyItem = new K3PurchaseOrderApplyItem();
        applyItem.setFPaezBase(teamVerif.getTeam());
        applyItem.setFPaezBase2(teamVerif.getVerifPersonNo());
        applyItem.setFMaterialId(teamVerif.getMaterialCode());
        applyItem.setFReqQty(qty.intValue());
        applyItem.setFPurchaserId(pmcVerifInfo.getPurchasePersonId());
        applyItem.setFSuggestSupplierId(pmcVerifInfo.getAdviceSupplierId());
        applyItem.setFEntryNote(pmcVerifInfo.getVerifReason());
        applyItem.setFBscHdate(operExpectedDate);
        itemList.add(applyItem);


        //单头
        K3PurchaseOrderApplyParam orderApplyParam = new K3PurchaseOrderApplyParam();
        orderApplyParam.setFCreateDate(new Date());
        orderApplyParam.setFBillNo(pmcVerifInfo.getBillNo());
        orderApplyParam.setFApplicationDate(pmcVerifInfo.getCreateTime());
        orderApplyParam.setFCreatorId(currentUser.getAccount());
        orderApplyParam.setFApplicantId(currentUser.getAccount());
        //拼接备注：
        String includeTax = pmcVerifInfo.getIncludeTax() == 0 ? "BHS" : "HS";
        orderApplyParam.setFNote(includeTax+","+pmcVerifInfo.getCreateOrderType()+","+pmcVerifInfo.getRemark());
        orderApplyParam.setFEntity(itemList);*/



        JSONArray jsonArray = new JSONArray();
        jsonArray.add(orderApplyParam);

        //生成同步到k3的记录
//        StockPmcVerifK3 pmcVerifK3 = BeanUtil.copyProperties(pmcVerifInfo, StockPmcVerifK3.class,"createTime","updateTime");
//        pmcVerifK3.setQty(qty);
//        pmcVerifK3.setOperExpectedDate(operExpectedDate);
//        pmcVerifK3.setBillType(billType);
//        pmcVerifK3.setSyncStatus(StockConstant.SYNC_WAIT);
        pmcVerifK3.setSyncRequestMsg(JSON.toJSONString(jsonArray));
//        pmcVerifK3.setId(IdWorker.getIdStr());

        if (needSave) {
            this.baseMapper.insert(pmcVerifK3);
        }

        try {
            JSONObject jsonObject = syncToErpConsumer.syncPurschaseObj(jsonArray);

            if (ObjectUtil.isNotNull(jsonObject)) {
                pmcVerifK3.setSyncResultMsg(JSON.toJSONString(jsonObject.toJSONString()));

                String code1 = jsonObject.getString("Code");
                if (ObjectUtil.isNotEmpty(code1) && code1.equals("0")) {
                    //同步成功修改状态为1
                    pmcVerifK3.setSyncStatus(StockConstant.SYNC_SUCESS);
                    syncResult = true;
                } else {
                    pmcVerifK3.setSyncStatus(StockConstant.SYNC_FAIL);
                }
            }
        } catch (Exception e) {
            log.error("备货3.0调用k3采购订单接口异常:{}", e);
            pmcVerifK3.setSyncResultMsg(JSON.toJSONString(e));
        }

        pmcVerifK3.setSyncTime(new Date());
        pmcVerifK3.setUpdateTime(new Date());


        for (PmcVerifInfo verifInfo : pmcVerifInfoList) {
            verifInfo.setSyncStatus(pmcVerifK3.getSyncStatus());
            verifInfo.setSyncRequestMsg(pmcVerifK3.getSyncRequestMsg());
            verifInfo.setSyncResultMsg(pmcVerifK3.getSyncResultMsg());
            verifInfo.setSyncTime(pmcVerifK3.getSyncTime());
        }

        if (needSave) {
            //更新k3同步记录
            this.baseMapper.updateById(pmcVerifK3);
        }


        //更新pmc信息
        pmcVerifInfoService.updateBatchById(pmcVerifInfoList);
        List<String> purchaseOrderIdList = pmcVerifInfoList.stream().map(pmc -> pmc.getPurchaseOrderId()).collect(Collectors.toList());

        //更新采购单状态
      Integer orderStatus=  syncResult ? StockConstant.ORDER_STATUS_PMC_YES : StockConstant.ORDER_STATUS_PMC_YES_SYNC_FAIL;
        LambdaUpdateWrapper<PurchaseOrders> purchaseOrderWrapper = new LambdaUpdateWrapper<>();
        purchaseOrderWrapper.set(PurchaseOrders::getOrderStatus, orderStatus)
                .set(PurchaseOrders::getUpdateTime,new Date())
                .in(PurchaseOrders::getId,purchaseOrderIdList);
        purchaseOrdersService.update(purchaseOrderWrapper);

        return pmcVerifK3;
    }




   private K3PurchaseOrderApplyParam initQuestK3Parm(List<PmcVerifInfo> pmcVerifInfoList,StockPmcVerifK3 pmcVerifK3) {

       PmcVerifInfo pmcVerifInfo = pmcVerifInfoList.get(0);

       String billType = pmcVerifInfo.getBillType();//默认取第一条数据的备货类型（大多数情况下 都只有一条数据）
       String applyPerson = pmcVerifInfo.getApplyPerson();//默认取第一条数据的申请人（大多数情况下 都只有一条数据）
       String applyPersonId = pmcVerifInfo.getApplyPersonId();//默认取第一条数据的申请人ID（大多数情况下 都只有一条数据）

       Date operExpectedDate = pmcVerifInfoList.stream().map(p -> p.getOperExpectedDate()).min(Date::compareTo).get();
       BigDecimal qty = pmcVerifInfoList.stream().map(p -> p.getQty()).reduce((a, b) -> a.add(b)).get();
       Set<String> billTypeSet = pmcVerifInfoList.stream().map(p -> p.getBillType()).collect(Collectors.toSet());
       if (billTypeSet.size() > 1) {//混合备货
           billType = "HHBH";//混合备货

           if (billTypeSet.contains("XPBH")) {
               PmcVerifInfo pi = pmcVerifInfoList.stream().filter(pmc -> "XPBH".equals(pmc.getBillType())).findFirst().get();
               applyPerson=pi.getApplyPerson();
               applyPersonId=pi.getApplyPersonId();
           }else if (billTypeSet.contains("JJBH")) {
               PmcVerifInfo pi = pmcVerifInfoList.stream().filter(pmc -> "JJBH".equals(pmc.getBillType())).findFirst().get();
               applyPerson=pi.getApplyPerson();
               applyPersonId=pi.getApplyPersonId();
           }else if (billTypeSet.contains("XMBH")) {
               PmcVerifInfo pi = pmcVerifInfoList.stream().filter(pmc -> "XMBH".equals(pmc.getBillType())).findFirst().get();
               applyPerson=pi.getApplyPerson();
               applyPersonId=pi.getApplyPersonId();
           }else if (billTypeSet.contains("RCBH")) {
               PmcVerifInfo pi = pmcVerifInfoList.stream().filter(pmc -> "RCBH".equals(pmc.getBillType())).findFirst().get();
               applyPerson=pi.getApplyPerson();
               applyPersonId=pi.getApplyPersonId();
           }
       }




       LoginContext current = SpringContext.getBean(LoginContext.class);
       LoginUser currentUser = current.getLoginUser();

       LambdaQueryWrapper<TeamVerif> wrapper = new LambdaQueryWrapper<>();
       wrapper.eq(TeamVerif::getPurchaseApplyNo, pmcVerifInfo.getPurchaseOrderId());
       List<TeamVerif> teamVerifList = teamVerifService.list(wrapper);

       ArrayList<K3PurchaseOrderApplyItem> itemList = new ArrayList<>();
       if (ObjectUtil.isEmpty(teamVerifList)) {
           return null;
       }

       //item只有一项
       TeamVerif teamVerif = teamVerifList.get(0);
       K3PurchaseOrderApplyItem applyItem = new K3PurchaseOrderApplyItem();
       applyItem.setFPaezBase(pmcVerifInfo.getTeam());
       //会存在一个人多个账号，新增的账号以两个SS开头，传到金蝶k3的时候需要截取调一个S
       if (applyPersonId.startsWith("SS")) {
           applyItem.setFPaezBase2(applyPersonId.substring(1,applyPersonId.length()));
       } else {
           applyItem.setFPaezBase2(applyPersonId);
       }
       applyItem.setFMaterialId(pmcVerifInfo.getMaterialCode());
       applyItem.setFReqQty(qty.intValue());
       applyItem.setFPurchaserId(pmcVerifInfo.getPurchasePersonId());
       applyItem.setFSuggestSupplierId(pmcVerifInfo.getAdviceSupplierId());
       //拼接pmc推送到k3备注：
       String includeTax = pmcVerifInfo.getIncludeTax() == 0 ? "BHS" : "HS";
       applyItem.setFEntryNote(includeTax+","+pmcVerifInfo.getCreateOrderType());
       applyItem.setFBscHdate(operExpectedDate);
       itemList.add(applyItem);


       //单头
       K3PurchaseOrderApplyParam orderApplyParam = new K3PurchaseOrderApplyParam();
       orderApplyParam.setFCreateDate(new Date());
       orderApplyParam.setFBillNo(pmcVerifInfo.getBillNo());
       orderApplyParam.setFApplicationDate(pmcVerifInfo.getCreateTime());
       orderApplyParam.setFCreatorId(currentUser.getAccount());
       orderApplyParam.setFApplicantId(currentUser.getAccount());

       orderApplyParam.setFNote(pmcVerifInfo.getRemark());
       orderApplyParam.setFEntity(itemList);
       //把请求k3接口的参数记录在字段
       String jsonString = JSON.toJSONString(orderApplyParam);

       ArrayList<String> purchaseOrderIdList = new ArrayList<>();
       for (PmcVerifInfo info : pmcVerifInfoList) {
           info.setSyncRequestMsg(jsonString);
           purchaseOrderIdList.add(info.getPurchaseOrderId());
       }

       //生成同步到k3的记录
       BeanUtil.copyProperties(pmcVerifInfo, pmcVerifK3,"createTime","updateTime");
       pmcVerifK3.setQty(qty);
       pmcVerifK3.setOperExpectedDate(operExpectedDate);
       pmcVerifK3.setBillType(billType);
       pmcVerifK3.setSyncStatus(StockConstant.SYNC_WAIT);
       pmcVerifK3.setSyncRequestMsg(JSON.toJSONString(jsonString));
       pmcVerifK3.setPurchaseOrderIdList(JSON.toJSONString(purchaseOrderIdList));
       pmcVerifK3.setPurchasePerson(pmcVerifInfo.getPurchasePerson());
       pmcVerifK3.setPurchasePersonId(pmcVerifInfo.getPurchasePersonId());
       pmcVerifK3.setCreateOrderType(pmcVerifInfo.getCreateOrderType());
       pmcVerifK3.setOrderLastTime(pmcVerifInfo.getOrderLastTime());
       pmcVerifK3.setApplyPerson(applyPerson);
       pmcVerifK3.setApplyPersonId(applyPersonId);
       pmcVerifK3.setId(IdWorker.getIdStr());

       return orderApplyParam;
   }



    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageResult<StockPmcVerifK3Result> queryPage(StockPmcVerifK3Param reqVO) {

        Page pageContext = reqVO.getPageContext();
        IPage<StockPmcVerifK3Result> page = this.baseMapper.queryPage(pageContext, reqVO);
        return new PageResult<>(page);

    }

    @DataSource(name = "stocking")
    @Override
    public ResponseData edit(StockPmcVerifK3Param requestParm) {
        // 更新记录 字段 以及 本次记录对应的pmc审核明细数据的字段，然后同步调用k3接口

        LambdaQueryWrapper<PmcVerifInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PmcVerifInfo::getBillNo, requestParm.getBillNo());
        List<PmcVerifInfo> pmcVerifInfoList = pmcVerifInfoService.getBaseMapper().selectList(wrapper);

        for (PmcVerifInfo pmcVerifInfo : pmcVerifInfoList) {
                    pmcVerifInfo.setAdviceSupplierId(requestParm.getAdviceSupplierId());
                    pmcVerifInfo.setAdviceSupplier( requestParm.getAdviceSupplier());
                    pmcVerifInfo.setPurchasePerson( requestParm.getPurchasePerson());
                    pmcVerifInfo.setPurchasePersonId( requestParm.getPurchasePersonId());
                    pmcVerifInfo.setIncludeTax( requestParm.getIncludeTax().intValue());
                    pmcVerifInfo.setCreateOrderType( requestParm.getCreateOrderType());
                    pmcVerifInfo.setMatControlPerson( requestParm.getMatControlPerson());
                    pmcVerifInfo.setUpdateTime(new Date());
        }

        if (ObjectUtil.isEmpty(pmcVerifInfoList)) {
            return ResponseData.error(StrUtil.format("无当前单据编号{}",requestParm.getBillNo()));
        }

        StockPmcVerifK3 pmcVerifK3 = this.callK3(pmcVerifInfoList, false);


            StockPmcVerifK3 stockPmcVerifK3 = this.baseMapper.selectById(requestParm.getId());

            stockPmcVerifK3.setAdviceSupplierId(requestParm.getAdviceSupplierId());
            stockPmcVerifK3.setAdviceSupplier(requestParm.getAdviceSupplier());
            stockPmcVerifK3.setPurchasePerson(requestParm.getPurchasePerson());
            stockPmcVerifK3.setPurchasePersonId(requestParm.getPurchasePersonId());
            stockPmcVerifK3.setIncludeTax(requestParm.getIncludeTax());
            stockPmcVerifK3.setCreateOrderType(requestParm.getCreateOrderType());
            stockPmcVerifK3.setSyncRequestMsg(pmcVerifK3.getSyncRequestMsg());
            stockPmcVerifK3.setSyncResultMsg(pmcVerifK3.getSyncResultMsg());
            stockPmcVerifK3.setSyncStatus(pmcVerifK3.getSyncStatus());
            stockPmcVerifK3.setMatControlPerson(pmcVerifK3.getMatControlPerson());
            stockPmcVerifK3.setUpdateTime(new Date());
            stockPmcVerifK3.setSyncTime(new Date());
            this.updateById(stockPmcVerifK3);

        return ResponseData.success();
    }


    @DataSource(name = "stocking")
    @Override
    public List<StockPmcVerifK3Result> export(StockPmcVerifK3Param reqVO) {
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        IPage<StockPmcVerifK3Result> page = this.baseMapper.queryPage(pageContext, reqVO);
        return page.getRecords();
    }


    private Page getPageContext() {
        return PageFactory.defaultPage();
    }
}
