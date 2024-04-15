package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kingdee.bos.webapi.entity.IdentifyInfo;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.tadpole.cloud.externalSystem.config.K3CloudWebapiConfig;
import com.tadpole.cloud.externalSystem.modular.mabang.constants.MabangConstant;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.*;
import com.tadpole.cloud.externalSystem.modular.mabang.enums.OrderStatusEnum;
import com.tadpole.cloud.externalSystem.modular.mabang.enums.ShopListEnum;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.K3CrossTransferMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.MabangOrdersMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.model.k3.K3CrossTransferItemParamMap;
import com.tadpole.cloud.externalSystem.modular.mabang.model.k3.K3CrossTransferParamMap;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3CrossTransferItemParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3CrossTransferParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangOrdersParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ExportK3CrossTransferResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3CrossTransferItemResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3CrossTransferResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IK3CrossTransferItemService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IK3CrossTransferService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangOrdersService;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
* <p>
    * K3跨组织直接调拨单主表 服务实现类
    * </p>
*
* @author lsy
* @since 2022-06-28
*/
@Service
@Slf4j
public class K3CrossTransferServiceImpl extends ServiceImpl<K3CrossTransferMapper, K3CrossTransfer> implements IK3CrossTransferService {

    @Autowired
    K3CloudWebapiConfig k3CloudWebapiConfig;

    @Resource
    private K3CrossTransferMapper mapper;


    @Resource
    private MabangOrdersMapper orderMapper;

    @Autowired
    private IMabangOrdersService mabangOrderService;

    @Autowired
    private IK3CrossTransferItemService transferItemService;

    @Autowired
    private RedisTemplate redisTemplate;

    private static String k3FormId="STK_TransferDirect";

    @DataSource(name = "external")
    @Override
    public PageResult<K3CrossTransferResult> findPageBySpec(K3CrossTransferParam param) {
        String start = " 00:00:00";
        String end = " 23:59:59";
        if(StringUtils.isNotEmpty(param.getStartTime())){
            param.setStartTime(param.getStartTime() + start);
        }
        if(StringUtils.isNotEmpty(param.getEndTime())){
            param.setEndTime(param.getEndTime() + end);
        }
        Page pageContext = param.getPageContext();
        IPage<K3CrossTransferResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);

    }

    @DataSource(name = "external")
    @Override
    public PageResult<ExportK3CrossTransferResult> findPageBySpecV2(K3CrossTransferParam param) {
        String start = " 00:00:00";
        String end = " 23:59:59";
        if(StringUtils.isNotEmpty(param.getStartTime())){
            param.setStartTime(param.getStartTime() + start);
        }
        if(StringUtils.isNotEmpty(param.getEndTime())){
            param.setEndTime(param.getEndTime() + end);
        }
        //空值筛选
        List<String> list = param.getOwnerIdHeadNames();
        if(CollectionUtil.isNotEmpty(list)){
            if(list.contains("空")){
                param.setFOwnerIdHeadName("空");
            }
        }

        Page pageContext = param.getPageContext();
        IPage<ExportK3CrossTransferResult> page = this.baseMapper.findPageBySpecV2(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "external")
    @Override
    public PageResult<K3CrossTransferItemResult> list(K3CrossTransferItemParam param) {

        Page pageContext = param.getPageContext();
        IPage<K3CrossTransferItemResult> page = this.baseMapper.list(pageContext, param);
        return new PageResult<>(page);

    }

    @DataSource(name = "external")
    @Override
    public List<ExportK3CrossTransferResult> export(K3CrossTransferParam param) {
        String start = " 00:00:00";
        String end = " 23:59:59";
        if(StringUtils.isNotEmpty(param.getStartTime())){
            param.setStartTime(param.getStartTime() + start);
        }
        if(StringUtils.isNotEmpty(param.getEndTime())){
            param.setEndTime(param.getEndTime() + end);
        }
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        IPage<ExportK3CrossTransferResult> page = this.baseMapper.findPageBySpecV2(pageContext, param);
        return page.getRecords();
    }

    @DataSource(name = "erpcloud")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getFinanceName(String fNumber) {
        return mapper.getFinanceName(fNumber);
    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> getFownerIdHead() {
        return mapper.getFownerIdHead();
    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOwnerIdName(String ownerId, String ownerName) {
        mapper.updateOwnerIdName(ownerId, ownerName);
    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ZZDistributeMcms> getFownerIdAndMat(int status) {
        return mapper.getFownerIdAndMat(status);
    }

    @Override
    @DataSource(name = "external")
    public List<Map<String, Object>> ownerIdHeadNameSelect() {
        QueryWrapper<K3CrossTransfer> wrapper = new QueryWrapper<>();
        wrapper.select("F_OWNER_ID_HEAD_NAME").groupBy("F_OWNER_ID_HEAD_NAME").orderByAsc("F_OWNER_ID_HEAD_NAME");
        List<Map<String, Object>> selectList = this.listMaps(wrapper);
        if(selectList.contains(null)){
            Map<String, Object> map = new HashMap<>();
            map.put("F_OWNER_ID_HEAD_NAME", "空");
            Collections.replaceAll(selectList, null, map);
        }



        return selectList;
    }

    @Override
    @DataSource(name = "external")
    public List<Map<String, Object>> platformSelect() {
        QueryWrapper<K3CrossTransfer> wrapper = new QueryWrapper<>();
        wrapper.select("PLATFORM_NAME").groupBy("PLATFORM_NAME").orderByAsc("PLATFORM_NAME");
        return this.listMaps(wrapper);
    }

    @DataSource(name = "external")
    @Transactional(rollbackFor = Exception.class)
    public void syncAbnormal(K3CrossTransfer param) {
        try {
        if (ObjectUtil.isNotEmpty(param)) {
            LambdaUpdateWrapper<K3CrossTransfer> updateItemWrapper = new LambdaUpdateWrapper<>();
            updateItemWrapper.eq(K3CrossTransfer::getId, param.getId())
                    .set(K3CrossTransfer::getSyncTime, DateUtil.date())
                    .set(K3CrossTransfer::getSyncStatus, "0")
                    .set(K3CrossTransfer::getUpdateTime, DateUtil.date())
                    .set(K3CrossTransfer::getSyncFailTimes, param.getSyncFailTimes().add(BigDecimal.ONE));
            mapper.update(null, updateItemWrapper);
        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData add(K3CrossTransferItemParam param) {

        //1.马帮发货订单==》》跨组织调拨单
        K3CrossTransfer crossTransfer=new K3CrossTransfer();
        K3CrossTransferItem crossTransferItem=new K3CrossTransferItem();

        //1-1.马帮发货订单 -->待同步且限定平台已发货订单
        MabangOrdersParam mabangOrdersParam = new MabangOrdersParam();
        mabangOrdersParam.setOrderStatus(OrderStatusEnum.SHIPPED.getCode());
        List<MabangOrders> list=mapper.syncMabangOrders(mabangOrdersParam);
        //限定平台状态更新
        mabangOrdersParam.setCreateCrossTransfer(new BigDecimal(ShopListEnum.limit_platform.getCode()));
        mapper.updateLimitPlatformOrders(mabangOrdersParam);

        if(CollectionUtil.isNotEmpty(list)){

            for(int i=0;i<list.size();i++){

                //马帮已发货订单->商品仓库为雁田定制仓
//                 ----2022-09-29 lsy 修改生成跨组织调拨单，item状态为4已作废的sku，不需要推送跨组织调拨单
                List<MabangOrderItem> queryItemList=mapper.syncMabangOrderItem(list.get(i).getErpOrderId());
                List<MabangOrderItem> itemList=   queryItemList.stream().filter(it -> !"4".equals(it.getStatus())).collect(Collectors.toList());

                //查询店铺列表平台、店铺、财务编码信息
                MabangShopList shopList=this.baseMapper.getShoplist(list.get(i).getShopId());
                if(ObjectUtil.isNotNull(shopList)){
                    crossTransfer.setPlatformName(shopList.getPlatformName());
                    crossTransfer.setShopName(shopList.getName());
                    crossTransfer.setFinanceCode(shopList.getFinanceCode());
                }
                crossTransfer.setFBillTypeId("ZJDB01_SYS");
                crossTransfer.setFBizType("NORMAL");
                crossTransfer.setFTransferDirect("GENERAL");
                crossTransfer.setFTransferBizType("OverOrgTransfer");
                crossTransfer.setFUnwBusinesstype("B");
                crossTransfer.setFUnwText("XDS");
                crossTransfer.setFDate(list.get(i).getExpressTime());
                crossTransfer.setFStockOutorgId("002");
                crossTransfer.setFOwnerTypeOutIdHead("BD_OwnerOrg");
                crossTransfer.setFOwnerOutIdHead("002");
                crossTransfer.setFStockOrgId(shopList.getFinanceCode());
                crossTransfer.setFOwnerTypeIdHead("BD_OwnerOrg");
                crossTransfer.setFOwnerIdHead(shopList.getFinanceCode());
                crossTransfer.setFNote(list.get(i).getPlatOrdId());
                crossTransfer.setOrderId(list.get(i).getId());
                crossTransfer.setSyncStatus("-1");
                crossTransfer.setOriginalFinanceCode(shopList.getOriginalFinanceCode());
                crossTransfer.setBizType(ShopListEnum.SYNC_SHIPPED_ORDER.getName());
                this.save(crossTransfer);

                //1-2.发货订单明细
                if(CollectionUtil.isNotEmpty(list)){
                    for(int j=0;j<itemList.size();j++){
                        crossTransferItem.setCrossId(crossTransfer.getId());
                        crossTransferItem.setFRowType("Standard");
                        crossTransferItem.setFBscBase(itemList.get(j).getPlatformSku());
                        crossTransferItem.setFMaterialId(itemList.get(j).getStockSku());
                        crossTransferItem.setFUnitId("Pcs");
                        crossTransferItem.setFQty(itemList.get(j).getQuantity());
                        crossTransferItem.setFSrcStockId("YT05_008");
                        crossTransferItem.setFDestStockId(shopList.getFinanceCode());
                        crossTransferItem.setFSrcStockStatusId("KCZT01_SYS");
                        crossTransferItem.setFDestStockStatusId("KCZT01_SYS");
                        crossTransferItem.setFOwnerOutId("002");
                        crossTransferItem.setFOwnerId(shopList.getFinanceCode());
                        crossTransferItem.setFBscTeam("平台发展组");
                        crossTransferItem.setStockWarehouseId(itemList.get(j).getStockWarehouseId());
                        crossTransferItem.setStockWarehouse(itemList.get(j).getStockWarehouseName());
                        transferItemService.save(crossTransferItem);
                    }
                }

                //2-1.马帮发货订单同步记录状态更新 ==》》已创建
                new LambdaUpdateChainWrapper<>(orderMapper).eq(MabangOrders::getErpOrderId,list.get(i).getErpOrderId())
                        .set(MabangOrders::getCreateCrossTransfer,1).update();
            }

        }
        else{
            return ResponseData.error("K3CrossTransfer ===》》 马帮发货订单没有需要同步的调拨单据！");
        }

        return ResponseData.success();
    }

    @DataSource(name = "external")
    @Override
    public ResponseData syncTransferToErp() throws Exception{

        // 刷数据时有可能冲突 需要加锁来排除冲突
        Object syncCrossTransferK3  = redisTemplate.opsForValue().get(MabangConstant.K3_SYNC_CROSS_TRANSFER);
        if (ObjectUtil.isNotNull(syncCrossTransferK3)) {
            log.info("同步跨组织调拨单到K3本次请求查询到有类似的业务正在作业,本次请求结束,触发时间:{}",new Date());
            return ResponseData.success("同步跨组织调拨单到K3本次请求查询到有类似的业务正在作业,本次请求结束");
        }
        //调用k3接口耗时比较大，大约1分钟处理7条数据，订单数据较大时，redis锁自动过期时间应该适当增加
        redisTemplate.boundValueOps(MabangConstant.K3_SYNC_CROSS_TRANSFER)
                .set(MabangConstant.K3_SYNC_CROSS_TRANSFER, Duration.ofMinutes(60*6));

        //1-1.跨组织调拨单代同步 ==>> k3跨组织调拨单记录
        LambdaQueryWrapper<K3CrossTransfer> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.in(K3CrossTransfer::getSyncStatus,"-1","0","2","3")
                .orderByDesc(K3CrossTransfer::getCreateTime);
        List<K3CrossTransfer> list=this.list(queryWrapper);

        if(CollectionUtil.isNotEmpty(list)){

            for(K3CrossTransfer k3Transfer:list){
                log.info("马帮发货订单{}同步k3直接调拨单--开始--时间{}",k3Transfer.getFNote(), LocalDateTime.now());
                try{
                    JSONObject objectAll = new JSONObject();

                    //直接调拨单主表信息
                    K3CrossTransferParamMap k3Cross = new K3CrossTransferParamMap();
                    k3Cross.setFBillTypeId(k3Transfer.getFBillTypeId());
                    k3Cross.setFBizType(k3Transfer.getFBizType());
                    k3Cross.setFTransferDirect(k3Transfer.getFTransferDirect());
                    k3Cross.setFTransferBizType(k3Transfer.getFTransferBizType());
                    k3Cross.setFStockOutorgId(k3Transfer.getFStockOutorgId());
                    k3Cross.setFStockOrgId(k3Transfer.getFStockOrgId());
                    k3Cross.setFOwnerTypeIdHead(k3Transfer.getFOwnerTypeIdHead());
                    k3Cross.setFOwnerIdHead(k3Transfer.getFOwnerIdHead());
                    k3Cross.setFDate(DateUtil.formatDateTime(k3Transfer.getFDate()));
                    k3Cross.setFNote(k3Transfer.getFNote());
                    k3Cross.setFUnwBusinesstype(k3Transfer.getFUnwBusinesstype());
                    k3Cross.setFUnwText(k3Transfer.getFUnwText());
                    //审核中状态同步
                    if(StrUtil.isNotEmpty(k3Transfer.getFBillNo())){
                        switch(k3Transfer.getSyncStatus()){
                            case "3":
                                cancelAssign(String.valueOf(k3Transfer.getFId()),k3Transfer.getFBillNo());
                                break;
                        }
                        k3Cross.setFId(k3Transfer.getFId());
                        k3Cross.setFBillNo(k3Transfer.getFBillNo());
                    }

                    //同步ID
                    BigDecimal pID=k3Transfer.getId();

                    //1-1.跨组织调拨单代同步-》k3记录
                    LambdaQueryWrapper<K3CrossTransferItem> queryItemWrapper=new LambdaQueryWrapper<>();
                    queryItemWrapper.eq(K3CrossTransferItem::getCrossId,pID);
                    List<K3CrossTransferItem> iList=transferItemService.list(queryItemWrapper);

                    //k3跨组织调拨单明细对象信息
                    ArrayList<K3CrossTransferItemParamMap> itemList = new ArrayList<>();
                    if(CollectionUtil.isNotEmpty(iList)){
                        for(K3CrossTransferItem k3TransferItem:iList){
                            K3CrossTransferItemParamMap k3CrossItem = new K3CrossTransferItemParamMap();
                            k3CrossItem.setFBscBase(k3TransferItem.getFBscBase());
                            k3CrossItem.setFRowType(k3TransferItem.getFRowType());
                            k3CrossItem.setFMaterialId(k3TransferItem.getFMaterialId());
                            k3CrossItem.setFUnitId(k3TransferItem.getFUnitId());
                            k3CrossItem.setFQty(k3TransferItem.getFQty());
                            k3CrossItem.setFSrcStockId(k3TransferItem.getFSrcStockId());
                            k3CrossItem.setFDestStockId(k3TransferItem.getFDestStockId());
                            k3CrossItem.setFSrcStockStatusId(k3TransferItem.getFSrcStockStatusId());
                            k3CrossItem.setFDestStockStatusId(k3TransferItem.getFDestStockStatusId());
                            k3CrossItem.setFOwnerOutId(k3TransferItem.getFOwnerOutId());
                            k3CrossItem.setFBscTeam(k3TransferItem.getFBscTeam());
                            itemList.add(k3CrossItem);
                        }

                    }
                    k3Cross.setFBillEntry(itemList);
                    objectAll.put("Model", k3Cross);

                    String jsonData = JSON.toJSONString(objectAll);
                    //保存直接调拨单
                    saveTransfer(jsonData,k3Transfer);

                }catch(Exception e){
                    log.error("马帮发货订单[{}]同步k3直接调拨单异常，异常信息[{}]",k3Transfer.getFNote(),e.getMessage());
                }
                log.info("马帮发货订单{}同步k3直接调拨单--结束--时间{}",k3Transfer.getFNote(), LocalDateTime.now());
            }
        }
        redisTemplate.delete(MabangConstant.K3_SYNC_CROSS_TRANSFER);

        return ResponseData.success();
    }

    public void saveTransfer(String jsonStr,K3CrossTransfer param) throws Exception{
        try{

            K3CloudApi api=new K3CloudApi(getConfigInfo());
            if (api.CheckAuthInfo().getResponseStatus().isIsSuccess()) {
                String result = api.save(k3FormId, jsonStr);
                //System.out.println(result);
                log.info( "k3直接调拨单接口保存执行完成，" + "返回结果:" + result);

                JSONObject  resultJson = JSON.parseObject(result);

                if (ObjectUtil.isNotNull(resultJson)) {
                    JSONObject  resultValue= JSON.parseObject(resultJson.getString("Result"));
                    if(ObjectUtil.isNotNull(resultValue)){
                        JSONObject  resultResponse= JSON.parseObject(resultValue.getString("ResponseStatus"));
                        if(ObjectUtil.isNotNull(resultResponse)){
                            //2-1.保存同步返回成功状态处理
                            if("true".equals(resultResponse.getString("IsSuccess"))){

                                String fId=resultValue.getString("Id");
                                String fNumber=resultValue.getString("Number");

                                //2-2.k3跨组织表同步状态更新、保存单据编号和ID信息
                                LambdaUpdateWrapper<K3CrossTransfer> updateItemWrapper=new LambdaUpdateWrapper<>();
                                updateItemWrapper.eq(K3CrossTransfer::getId,param.getId())
                                        .set(K3CrossTransfer::getFBillNo,fNumber)
                                        .set(K3CrossTransfer::getFId,fId)
                                        .set(K3CrossTransfer::getSyncTime,DateUtil.date())
                                        .set(K3CrossTransfer::getUpdateTime,DateUtil.date())
                                        //.set(K3CrossTransfer::getSyncStatus,"1")
                                        .set(K3CrossTransfer::getSyncRequstPar,jsonStr)
                                        .set(K3CrossTransfer::getSyncResultMsg,result)
                                        .set(K3CrossTransfer::getSyncSuccessTimes,param.getSyncSuccessTimes().add(BigDecimal.ONE));
                                mapper.update(null,updateItemWrapper);

                                //2-3.跨组织调拨单提交
                                boolean commitFlag=commitTransfer(fId,fNumber);
                                if(commitFlag){
                                    //2-4.跨组织调拨单审核
                                    auditTransfer(fId,fNumber);
                                }
                            }
                            else{
                                //2-5.k3跨组织表请求参数、同步返回结果保存
                                LambdaUpdateWrapper<K3CrossTransfer> updateItemWrapper=new LambdaUpdateWrapper<>();
                                updateItemWrapper.eq(K3CrossTransfer::getId,param.getId())
                                        .set(K3CrossTransfer::getSyncTime,DateUtil.date())
                                        .set(K3CrossTransfer::getSyncStatus,"0")
                                        .set(K3CrossTransfer::getSyncRequstPar,jsonStr)
                                        .set(K3CrossTransfer::getSyncResultMsg,result)
                                        .set(K3CrossTransfer::getUpdateTime,DateUtil.date())
                                        .set(K3CrossTransfer::getSyncFailTimes,param.getSyncFailTimes().add(BigDecimal.ONE));
                                mapper.update(null,updateItemWrapper);
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            //3-1.推送失败->状态处理
            syncAbnormal(param);
            e.printStackTrace();
            log.error("K3CrossTransfer-[save]===》》，返回信息:[{}]",e.getMessage());
        }
    }

    public boolean commitTransfer(String fID,String fNumber) throws Exception {
        boolean flag=false;
        try{
            K3CloudApi api=new K3CloudApi(getConfigInfo());
            if (api.CheckAuthInfo().getResponseStatus().isIsSuccess()) {

                String jsonData = "{\"Numbers\":[\""+fNumber+"\"],\"Ids\":\""+fID+"\"}";

                String result = api.submit(k3FormId, jsonData);
                //System.out.println(result);
                log.info( "k3直接调拨单接口提交执行完成，跨组织调拨单[{}]，返回结果:[{}]",fNumber,result);

                JSONObject  resultJson = JSON.parseObject(result);

                if (ObjectUtil.isNotNull(resultJson)) {
                    JSONObject  resultValue= JSON.parseObject(resultJson.getString("Result"));
                    if(ObjectUtil.isNotNull(resultValue)){
                        JSONObject  resultResponse= JSON.parseObject(resultValue.getString("ResponseStatus"));
                        if(ObjectUtil.isNotNull(resultResponse)){
                            //2-1.保存同步返回成功状态处理
                            if("true".equals(resultResponse.getString("IsSuccess"))){
                                    //2-2.返回成功
                                    flag=true;
                            }
                            else{
                                //2-3.k3跨组织提交返回结果保存
                                flag=false;
                                String sql = "SYNC_RESULT_MSG=SYNC_RESULT_MSG||'";
                                sql=sql+"to_char('||')||"+result + "'";

                                LambdaUpdateWrapper<K3CrossTransfer> updateItemWrapper=new LambdaUpdateWrapper<>();
                                updateItemWrapper.eq(K3CrossTransfer::getFId,fID)
                                        .set(K3CrossTransfer::getSyncTime,DateUtil.date())
                                        //.set(K3CrossTransfer::getSyncResultMsg,result)
                                        .setSql(sql)
                                        .set(K3CrossTransfer::getSyncStatus,"2")
                                        .set(K3CrossTransfer::getUpdateTime,DateUtil.date());
                                mapper.update(null,updateItemWrapper);
                            }
                        }
                    }
                }

            }
        }catch(Exception e){
            e.printStackTrace();
            log.error("调拨单单据:[{}] 提交异常，异常信息:[{}]",fNumber,e.getMessage());
        }
        return flag;
    }

    public boolean auditTransfer(String fID,String fNumber) throws Exception {
        try{
        K3CloudApi api=new K3CloudApi(getConfigInfo());
        if (api.CheckAuthInfo().getResponseStatus().isIsSuccess()) {

            String jsonData = "{\"Numbers\":[\""+fNumber+"\"],\"Ids\":\""+fID+"\"}";

            String result = api.audit(k3FormId, jsonData);
            //System.out.println(result);
            log.info( "k3直接调拨单接口审核执行完成，跨组织调拨单[{}]，返回结果:[{}]",fNumber,result);

            JSONObject  resultJson = JSON.parseObject(result);

            if (ObjectUtil.isNotNull(resultJson)) {
                JSONObject  resultValue= JSON.parseObject(resultJson.getString("Result"));
                if(ObjectUtil.isNotNull(resultValue)){
                    JSONObject  resultResponse= JSON.parseObject(resultValue.getString("ResponseStatus"));
                    if(ObjectUtil.isNotNull(resultResponse)){
                        //2-1.审核同步返回状态处理
                        if("true".equals(resultResponse.getString("IsSuccess"))){
                            LambdaUpdateWrapper<K3CrossTransfer> updateItemWrapper=new LambdaUpdateWrapper<>();
                            updateItemWrapper.eq(K3CrossTransfer::getFId,fID)
                                    .set(K3CrossTransfer::getSyncTime,DateUtil.date())
                                    .set(K3CrossTransfer::getSyncStatus,"1")
                                    .set(K3CrossTransfer::getUpdateTime,DateUtil.date());
                            mapper.update(null,updateItemWrapper);
                        }
                        else{
                            //2-2.k3跨组织审核异常返回结果保存
                            String sql = "SYNC_RESULT_MSG=SYNC_RESULT_MSG||'";
                            sql=sql+"to_char('||')||"+result + "'";

                            LambdaUpdateWrapper<K3CrossTransfer> updateItemWrapper=new LambdaUpdateWrapper<>();
                            updateItemWrapper.eq(K3CrossTransfer::getFId,fID)
                                    .set(K3CrossTransfer::getSyncTime,DateUtil.date())
                                    //.set(K3CrossTransfer::getSyncResultMsg,result)
                                    .setSql(sql)
                                    .set(K3CrossTransfer::getSyncStatus,"3")
                                    .set(K3CrossTransfer::getUpdateTime,DateUtil.date());
                            mapper.update(null,updateItemWrapper);
                        }
                    }
                }
            }

        }
        }catch(Exception e){
            e.printStackTrace();
            log.error("调拨单单据:[{}] 审核异常，异常信息:[{}]",fNumber,e.getMessage());
        }
        return true;
    }

    public boolean cancelAssign(String fID,String fNumber) throws Exception {
        boolean flag=false;
        try{
            K3CloudApi api=new K3CloudApi(getConfigInfo());
            if (api.CheckAuthInfo().getResponseStatus().isIsSuccess()) {

                String jsonData = "{\"Numbers\":[\""+fNumber+"\"],\"Ids\":\""+fID+"\"}";

                String result = api.cancelAssign(k3FormId, jsonData);
                //System.out.println(result);
                log.info( "k3直接调拨单接口撤销执行完成，跨组织调拨单[{}]，返回结果:[{}]",fNumber,result);

                JSONObject  resultJson = JSON.parseObject(result);

                if (ObjectUtil.isNotNull(resultJson)) {
                    JSONObject  resultValue= JSON.parseObject(resultJson.getString("Result"));
                    if(ObjectUtil.isNotNull(resultValue)){
                        JSONObject  resultResponse= JSON.parseObject(resultValue.getString("ResponseStatus"));
                        if(ObjectUtil.isNotNull(resultResponse)){
                            //2-1.撤销同步返回状态处理
                            if("true".equals(resultResponse.getString("IsSuccess"))){
                                flag=true;
                            }
                        }
                    }
                }

            }
        }catch(Exception e){
            e.printStackTrace();
            log.error("调拨单单据:[{}] 撤销异常，异常信息:[{}]",fNumber,e.getMessage());
        }
        return flag;
    }

    @DataSource(name = "external")
    @Override
    public ResponseData checkReponseStatus() throws Exception{
            K3CloudApi api=new K3CloudApi(getConfigInfo());
            if (api.CheckAuthInfo().getResponseStatus().isIsSuccess()) {
                return ResponseData.success("用户账号登陆成功");
            }
            else{
                return ResponseData.error("用户账号登陆异常");
            }
    }

    public IdentifyInfo getConfigInfo() {

        IdentifyInfo identifyInfoAdb=new IdentifyInfo(){};
        //应用id
        identifyInfoAdb.setAppId(k3CloudWebapiConfig.getAppid());

        identifyInfoAdb.setAppSecret(k3CloudWebapiConfig.getAppsec());
        //账套id
        identifyInfoAdb.setdCID(k3CloudWebapiConfig.getAcctid());

        identifyInfoAdb.setUserName(k3CloudWebapiConfig.getUsername());

        identifyInfoAdb.setServerUrl(k3CloudWebapiConfig.getServerurl());
        return identifyInfoAdb;
    }


    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            
        }
    }
}
