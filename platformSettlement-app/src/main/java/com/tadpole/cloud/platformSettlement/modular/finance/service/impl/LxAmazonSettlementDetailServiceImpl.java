package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sourcereport.SettlementFileListReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sourcereport.SettlementFileReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.CwLingxingShopInfo;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LxAmazonSettlement;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LxAmazonSettlementDetail;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LxShopSynRecord;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementAbnormalParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.CwLingxingSettlementResult;
import com.tadpole.cloud.platformSettlement.modular.finance.consumer.LingXingSourceReportConsumer;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.LxShopSynStatus;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.LxShopSynType;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.LingxingDatarangeMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.LxAmazonSettlementDetailMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
* <p>
*  服务实现类
* </p>
*
* @author gal
* @since 2022-05-06
*/
@Slf4j
@Service
public class LxAmazonSettlementDetailServiceImpl extends ServiceImpl<LxAmazonSettlementDetailMapper, LxAmazonSettlementDetail> implements ILxAmazonSettlementDetailService {

    @Autowired
    private LingXingSourceReportConsumer lingXingSourceReportConsumer;

    @Autowired
    private ICwLingxingShopInfoService lingxingShopInfoService;

    @Autowired
    private ISettlementAbnormalService settlementAbnormalService;

    @Resource
    private ILxAmazonSettlementDetailService settlementDetailService;

    @Resource
    private ILxAmazonSettlementService settlementService;

    @Resource
    private LingxingDatarangeMapper lxdatamapper;

    @Resource
    private ILxShopSynRecordService lxShopSynRecordService;

    @Value("${warehouse_database}")
    private String warehouseDatabase;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String SETTLEMENT_ABNORMAL_KEY = "SETTLEMENT_ABNORMAL_KEY";

    @DataSource(name = "finance")
    @Override
    public PageResult<CwLingxingSettlementResult> findPageBySpec(SettlementAbnormalParam param) {
        IPage<CwLingxingSettlementResult> page = this.baseMapper.findPageBySpec(param.getPageContext(), param);
        return new PageResult<>(page);
    }

    @DataSource(name = "finance")
    @Override
    public List<CwLingxingSettlementResult> list(SettlementAbnormalParam param) {
        Page pageContext = param.getPageContext();
        pageContext.setSize(Integer.MAX_VALUE);
        IPage<CwLingxingSettlementResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return page.getRecords();
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData generateSettlementSourceFile(SettlementFileListReq param) {
        LxShopSynRecord lxShopSynRecord = new LxShopSynRecord();
        String synDate = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        lxShopSynRecord.setSynDate(synDate);
        lxShopSynRecord.setSynType(LxShopSynType.SETTLEMENT.getCode());
        if(param.getSid() == null){
            //没有指定，则系统默认执行所有的店铺获取前一天的文件
            String defaultDate = DateUtil.format(DateUtil.yesterday(), DatePattern.NORM_DATE_PATTERN);
            param.setStart_date(defaultDate);
            param.setEnd_date(defaultDate);
        }else {
            //指定店铺sid、开始日期和结束日期，用于手动处理指定店铺和时间下载异常处理
            lxShopSynRecord.setSid(new BigDecimal(param.getSid()));
        }

        //获取需要下载Settlement领星店铺sid
        List<CwLingxingShopInfo> lingXingShopList = lingxingShopInfoService.getLxShopInfoBySynType(lxShopSynRecord);
        if(CollectionUtil.isNotEmpty(lingXingShopList)){
            for (CwLingxingShopInfo lingXingShopInfo : lingXingShopList) {
                param.setSid(lingXingShopInfo.getSid().longValue());
                dealGetLxSettlement(param, lingXingShopInfo, synDate);
            }
        }
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData handleGenerateSettlementSourceFile(String synDate) {

        LxShopSynRecord lxShopSynRecord = new LxShopSynRecord();
        lxShopSynRecord.setSynDate(synDate);
        lxShopSynRecord.setSynType(LxShopSynType.SETTLEMENT.getCode());

        //执行所有的店铺获取前一天的文件
        SettlementFileListReq param = new SettlementFileListReq();
        String defaultDate = DateUtil.format(DateUtil.offsetDay(DateUtil.parse(synDate, DatePattern.PURE_DATE_PATTERN), -1), DatePattern.NORM_DATE_PATTERN);
        param.setStart_date(defaultDate);
        param.setEnd_date(defaultDate);

        //获取需要下载Settlement领星店铺sid
        List<CwLingxingShopInfo> lingXingShopList = lingxingShopInfoService.getLxShopInfoBySynType(lxShopSynRecord);
        if(CollectionUtil.isNotEmpty(lingXingShopList)){
            for (CwLingxingShopInfo lingXingShopInfo : lingXingShopList) {
                param.setSid(lingXingShopInfo.getSid().longValue());
                dealGetLxSettlement(param, lingXingShopInfo, synDate);
            }
        }
        return ResponseData.success();
    }

    /**
     * 领星settlement源文件下载处理
     * @param param
     * @param lingXingShopInfo
     * @param synDate
     */
    private void dealGetLxSettlement(SettlementFileListReq param, CwLingxingShopInfo lingXingShopInfo, String synDate) {
        UpdateWrapper<LxShopSynRecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("SID", lingXingShopInfo.getSid())
                .eq("SYN_DATE", synDate)
                .eq("SYN_TYPE", LxShopSynType.SETTLEMENT.getCode());

        LxShopSynRecord updateShopSynRecord = new LxShopSynRecord();
        updateShopSynRecord.setSynStatus(LxShopSynStatus.ERROR.getCode());//异常

        //根据店铺sid和文件日期获取领星settlement文件列表
        try {
            LingXingBaseRespData fileListRespData = lingXingSourceReportConsumer.settlementFileList(param);
            if(LingXingBaseRespData.DEFAULT_SUCCESS_CODE.equals(fileListRespData.getCode())){
                List<Object> fileListObjList = fileListRespData.getData();
                //判断有没有文件
                if(CollectionUtil.isNotEmpty(fileListObjList)){
                    for (Object fileListObj : fileListObjList) {
                        String jsonString = JSON.toJSONString(fileListObj);
                        JSONObject jsonObject = JSONObject.parseObject(jsonString);

                        //根据文件id请求领星Settlement源文件下载
                        SettlementFileReq settlementFileReq = new SettlementFileReq();
                        settlementFileReq.setId(jsonObject.getLong("id"));
                        LingXingBaseRespData fileRespData = lingXingSourceReportConsumer.getSettlementFile(settlementFileReq);
                        if(LingXingBaseRespData.DEFAULT_SUCCESS_CODE.equals(fileRespData.getCode())){
                            List<Object> fileObjList = fileRespData.getData();
                            if(CollectionUtil.isNotEmpty(fileObjList)){
                                //处理文件信息
                                List<LxAmazonSettlement> settlementTotalList = new ArrayList();
                                List<LxAmazonSettlementDetail> settlementDetailList = new ArrayList();
                                for (Object fileObj : fileObjList) {
                                    String fileJsonString = JSON.toJSONString(fileObj);
                                    JSONObject fileJsonObject = JSONObject.parseObject(fileJsonString);
                                    //获取文件汇总信息，第一行为字段信息，第二行为汇总信息，第三行开始为明细信息
                                    String desFileData = fileJsonObject.getString("des_file_data");
                                    log.info("领星Settlement源文件信息[{}]", desFileData);
                                    String [] lineStrArr = desFileData.split("\\n");//读取行数据
                                    if(lineStrArr.length > 0){
                                        for (int i = 0; i < lineStrArr.length; i++) {
                                            String[] lineColumnStrArr = lineStrArr[i].split("\\t");//读取行的列数据
                                            DateTimeFormatter timeFormatter= DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                                            if(i == 1){
                                                //报告标准日期转换
                                                if(lineColumnStrArr[1].contains("-")){
                                                    timeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                                }
                                                else if(lineColumnStrArr[1].contains("/")){
                                                    timeFormatter=DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                                }
                                                ZoneId zone = ZoneId.systemDefault();
                                                Date startDate = Date.from(LocalDateTime.parse(lineColumnStrArr[1].replace(" UTC",""),timeFormatter).atZone(zone).toInstant());
                                                Date endDate = Date.from(LocalDateTime.parse(lineColumnStrArr[2].replace(" UTC",""),timeFormatter).atZone(zone).toInstant());
                                                Date depositDate = Date.from(LocalDateTime.parse(lineColumnStrArr[3].replace(" UTC",""),timeFormatter).atZone(zone).toInstant());

                                                //入库Settlement汇总表 :LX_AMAZON_SETTLEMENT
                                                LxAmazonSettlement settlementTotal = new LxAmazonSettlement();
                                                settlementTotal.setShopName(lingXingShopInfo.getShopName());
                                                settlementTotal.setSite(lingXingShopInfo.getSite());
                                                settlementTotal.setSettlementId(lineColumnStrArr[0]);
                                                settlementTotal.setSettlementStartDate(startDate);
                                                settlementTotal.setSettlementEndDate(endDate);
                                                settlementTotal.setDepositDate(depositDate);
                                                settlementTotal.setTotalAmount(this.dealSpecialAmount(lineColumnStrArr[4]));
                                                settlementTotal.setCurrency(lineColumnStrArr[5]);
                                                settlementTotalList.add(settlementTotal);
                                            }else if(i > 1){
                                                //标准日期转换
                                                if(lineColumnStrArr[17].contains("-")){
                                                    timeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                                }
                                                else if(lineColumnStrArr[17].contains("/")){
                                                    timeFormatter=DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                                }
                                                ZoneId zone = ZoneId.systemDefault();
                                                Date postedDate = Date.from(LocalDateTime.parse(lineColumnStrArr[16].concat(" 00:00:00"),timeFormatter).atZone(zone).toInstant());
                                                Date postedDateTime = Date.from(LocalDateTime.parse(lineColumnStrArr[17].replace(" UTC",""),timeFormatter).atZone(zone).toInstant());
                                                //入库Settlement明细表 : LX_AMAZON_SETTLEMENT_DETAIL
                                                LxAmazonSettlementDetail settlementDetail = new LxAmazonSettlementDetail();
                                                settlementDetail.setShopName(lingXingShopInfo.getShopName());
                                                settlementDetail.setSite(lingXingShopInfo.getSite());
                                                settlementDetail.setSettlementId(lineColumnStrArr[0]);
                                                settlementDetail.setTransactionType(lineColumnStrArr[6]);
                                                settlementDetail.setOrderId(lineColumnStrArr[7]);
                                                settlementDetail.setMerchantOrderId(lineColumnStrArr[8]);
                                                settlementDetail.setAdjustmentId(lineColumnStrArr[9]);
                                                settlementDetail.setShipmentId(lineColumnStrArr[10]);
                                                settlementDetail.setMarketplaceName(lineColumnStrArr[11]);
                                                settlementDetail.setAmountType(lineColumnStrArr[12]);
                                                settlementDetail.setAmountDescription(lineColumnStrArr[13]);
                                                settlementDetail.setAmount(this.dealSpecialAmount(lineColumnStrArr[14]));
                                                settlementDetail.setFulfillmentId(lineColumnStrArr[15]);
                                                settlementDetail.setCreateTime(new Date());
                                                settlementDetail.setPostedDate(postedDate);
                                                settlementDetail.setPostedDateTime(postedDateTime);
                                                if(lineColumnStrArr.length > 18){
                                                    settlementDetail.setOrderItemCode(lineColumnStrArr[18]);
                                                }
                                                if(lineColumnStrArr.length > 19){
                                                    settlementDetail.setMerchantOrderItemId(lineColumnStrArr[19]);
                                                }
                                                if(lineColumnStrArr.length > 20){
                                                    settlementDetail.setMerchantAdjustmentItemId(lineColumnStrArr[20]);
                                                }
                                                if(lineColumnStrArr.length > 21){
                                                    settlementDetail.setSku(lineColumnStrArr[21]);
                                                }
                                                if(lineColumnStrArr.length > 22 && StringUtils.isNotEmpty(lineColumnStrArr[22])){
                                                    settlementDetail.setQuantityPurchased(Long.parseLong(lineColumnStrArr[22]));
                                                }
                                                settlementDetailList.add(settlementDetail);
                                            }
                                        }
                                    }
                                }
                                //批量入库汇总表
                                if(CollectionUtil.isNotEmpty(settlementTotalList)){
                                    settlementService.saveBatch(settlementTotalList);
                                }

                                //批量入库明细表
                                if(CollectionUtil.isNotEmpty(settlementDetailList)){
                                    settlementDetailService.saveBatch(settlementDetailList);
                                }

                                //SID对应的所有文件处理完成
                                updateShopSynRecord.setSynStatus(LxShopSynStatus.SUCCESS.getCode());
                                //处理完账号站点（sid）的文件信息后，更新sid的任务执行情况
                                updateShopSynRecord.setUpdateDate(new Date());
                                lxShopSynRecordService.update(updateShopSynRecord, updateWrapper);
                            }else {
                                //根据sid下载文件没有文件
                                updateShopSynRecord.setSynStatus(LxShopSynStatus.NONE.getCode());
                                //处理完账号站点（sid）的文件信息后，更新sid的任务执行情况
                                updateShopSynRecord.setUpdateDate(new Date());
                                lxShopSynRecordService.update(updateShopSynRecord, updateWrapper);
                            }
                        }
                    }
                } else {
                    //文件列表没有文件id
                    updateShopSynRecord.setSynStatus(LxShopSynStatus.NONE.getCode());
                    //处理完账号站点（sid）的文件信息后，更新sid的任务执行情况
                    updateShopSynRecord.setUpdateDate(new Date());
                    lxShopSynRecordService.update(updateShopSynRecord, updateWrapper);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 处理带特殊符号金额
     * @param amount
     * @return
     */
    private BigDecimal dealSpecialAmount(String amount){
        String replaceAmount = amount;
        if(StringUtils.isNotEmpty(amount)){
            if(amount.contains(" ")){
                replaceAmount = amount.replaceAll(" ", "");
            }
            if(amount.contains(",")){
                replaceAmount = amount.replaceAll(",", ".");
            }
        }
        return new BigDecimal(replaceAmount);
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<Long,String> generateSettlementFileID(SettlementFileListReq param) throws Exception{

        Map<Long,String> map=new HashMap<>();
        List<Long> shopSids=this.lxdatamapper.getShopSid();

        if(shopSids.size()==0){
           throw new Exception("店铺sid为空");
        }

        for(int i=0;i<shopSids.size();i++)
        {
            try{
                param.setSid(shopSids.get(i));
                LingXingBaseRespData baseRespData=lingXingSourceReportConsumer.settlementFileList(param);
                if(LingXingBaseRespData.DEFAULT_SUCCESS_CODE.equals(baseRespData.getCode()))
                {
                    List<Object> objList = baseRespData.getData();
                    for(Object obj : objList){
                        String jsonObject = JSON.toJSONString(obj);
                        JSONObject jsonObject1=JSON.parseObject(jsonObject);
                        map.put(jsonObject1.getLong("id"),jsonObject1.getString("des_file_name"));
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return map;
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData refresh() {
        if(redisTemplate.hasKey(SETTLEMENT_ABNORMAL_KEY)){
            return ResponseData.error("正在刷新中，请稍后再试！");
        }
        log.info("订单异常情况汇总数据生成（刷新）开始------------------->");
        redisTemplate.boundValueOps(SETTLEMENT_ABNORMAL_KEY).set("AZ结算异常数据生成中", Duration.ofSeconds(1200));
        try {
            log.info("AZ结算异常数据生成（刷新）开始------------------->");
            long start = System.currentTimeMillis();
            //1、先删除原来的数据
            settlementAbnormalService.remove(null);

            //2、重新生成新的数据
            this.baseMapper.refresh();
            log.info("AZ结算异常数据生成（刷新）结束，耗时------------------->" + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e){
            log.error(e.getMessage());
        }finally {
            redisTemplate.delete(SETTLEMENT_ABNORMAL_KEY);
        }
        return ResponseData.success();
    }
}
