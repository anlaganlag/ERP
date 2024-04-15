package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.libs.util.K3GeneratorNoUtil;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.api.ebms.model.resp.EbmsUserInfo;
import com.tadpole.cloud.operationManagement.modular.stock.constants.StockConstant;
import com.tadpole.cloud.operationManagement.modular.stock.consumer.EbmsBaseConsumer;
import com.tadpole.cloud.operationManagement.modular.stock.consumer.SyncToErpConsumer;
import com.tadpole.cloud.operationManagement.modular.stock.entity.ProdPurchaseRequire;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.ProdPurchaseRequireMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.K3PurchaseOrderApplyItem;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.K3PurchaseOrderApplyParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.ProdPurchaseRequireParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.ProdPurchaseRequireExtentsResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IProdPurchaseRequireService;
import com.tadpole.cloud.operationManagement.modular.stock.task.PurchasePushK3Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;


/**
 * <p>
 * 新品采购申请 服务实现类
 * </p>
 *
 * @author gal
 * @since 2022-10-20
 */
@Slf4j
@Service
public class ProdPurchaseRequireServiceImpl extends ServiceImpl<ProdPurchaseRequireMapper, ProdPurchaseRequire> implements IProdPurchaseRequireService {

    @Resource
    private ProdPurchaseRequireMapper mapper;


    @Resource
    EbmsBaseConsumer ebmsService;

    @Resource
    SyncToErpConsumer syncToErpUtil;

    @Resource
    K3GeneratorNoUtil k3GeneratorNoUtil;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    public static String PMC_VERIFY_KEY_HEAD = "PMC_VERIFY_KEY_";


    @Resource
    SyncToErpConsumer syncToErpConsumer;



    @Transactional(rollbackFor = Exception.class)
    @DataSource(name = "product")
    @Override
    public ResponseData purchaseCheck(ProdPurchaseRequireParam param) {

        if ("待采购复核".equals(param.getApplyStatus())) {
            EbmsUserInfo userInfo = ebmsService.getUserInfoByAccount(this.getUserAccount());
            if (ObjectUtil.isNull(userInfo)) {
                return ResponseData.error("当前登录人，在EBMS系统中未找到用户信息");
            }
            param.setFrontPurCode(userInfo.getSysPerCode());
        }
        List<ProdPurchaseRequireExtentsResult> resultList =
                this.mapper.queryPurchaseRequireExtentsOpr(param);
        return ResponseData.success(resultList);
    }

    @Transactional(rollbackFor = Exception.class)
    @DataSource(name = "product")
    @Override
    public ResponseData purchasePmcCheck(ProdPurchaseRequireParam param) {

        if ("待采购复核".equals(param.getApplyStatus())) {
            EbmsUserInfo userInfo = ebmsService.getUserInfoByAccount(this.getUserAccount());
            if (ObjectUtil.isNull(userInfo)) {
                return ResponseData.error("当前登录人，在EBMS系统中未找到用户信息");
            }
            param.setFrontPurCode(userInfo.getSysPerCode());
        }
        List<ProdPurchaseRequireExtentsResult> resultList =
                this.mapper.purchasePmcCheck(param);
        return ResponseData.success(resultList);
    }

    @DataSource(name = "product")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchUpdate(ProdPurchaseRequireParam param) {

        EbmsUserInfo userInfo = ebmsService.getUserInfoByAccount(this.getUserAccount());
        if (ObjectUtil.isNull(userInfo)) {
            return ResponseData.error("当前登录人，在EBMS系统中未找到用户信息");
        }

        //更新校验，只能允许后端操作数据
        ResponseData checkResult = this.checkData(param, userInfo);
        if (! checkResult.getSuccess()) {
            return checkResult;
        }
        if (ObjectUtil.isNull(param)){
            return ResponseData.error("请求参数不能为空");
        }

        LambdaUpdateWrapper<ProdPurchaseRequire> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(ProdPurchaseRequire::getId, param.getIdList());
        updateWrapper.eq(ProdPurchaseRequire::getApplyStatus, "待采购复核");
        updateWrapper.set(ObjectUtil.isNotEmpty(param.getSuggestSupplier()), ProdPurchaseRequire::getSuggestSupplier,param.getSuggestSupplier());//建议供应商
        updateWrapper.set(ObjectUtil.isNotEmpty(param.getSuggestSupplierId()), ProdPurchaseRequire::getSuggestSupplierId,param.getSuggestSupplierId());//建议供应商ID
        updateWrapper.set(ObjectUtil.isNotEmpty(param.getIsIncludeTax()), ProdPurchaseRequire::getIsIncludeTax,param.getIsIncludeTax());//是否含税
        updateWrapper.set(ObjectUtil.isNotEmpty(param.getOrderMethod()), ProdPurchaseRequire::getOrderMethod,param.getOrderMethod());//下单方式
        updateWrapper.set(ObjectUtil.isNotNull(param.getPurExpectDeliveDate()), ProdPurchaseRequire::getPurExpectDeliveDate,param.getPurExpectDeliveDate());//采购预计交期
        updateWrapper.set(ObjectUtil.isNotNull(param.getOprApplyQty3()), ProdPurchaseRequire::getOprApplyQty3,param.getOprApplyQty3());//复核数量
        updateWrapper.set(ObjectUtil.isNotEmpty(param.getCheckExplain()), ProdPurchaseRequire::getCheckExplain,param.getCheckExplain());//复核说明
        updateWrapper.set( ProdPurchaseRequire::getCheckDate,new Date());//复核日期
        updateWrapper.set( ProdPurchaseRequire::getCheckPerName,this.getUserName());//复核人姓名
        updateWrapper.set( ProdPurchaseRequire::getCheckPerCode,this.getUserAccount());//复核人编码
        updateWrapper.set( ProdPurchaseRequire::getCheckDeptCode,userInfo.getSysComDeptCode());//复核人部门编码 取ebms
        updateWrapper.set( ProdPurchaseRequire::getCheckDeptName,userInfo.getSysComDeptFullName());//复核人部门名称 取ebms

        if (this.update(updateWrapper)) {
            return ResponseData.success("批量更新成功");
        }
        return ResponseData.error("批量更新失败");


    }


    @DataSource(name = "product")
    private ResponseData checkData(ProdPurchaseRequireParam param, EbmsUserInfo userInfo) {
        LambdaQueryWrapper<ProdPurchaseRequire> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ProdPurchaseRequire::getId, param.getIdList());
        List<ProdPurchaseRequire> dataList = this.list(queryWrapper);

        if (CollectionUtil.isEmpty(dataList)) {
            return ResponseData.error("传入的ID未找到数据");
        }


        List<ProdPurchaseRequire> checkResult = dataList.stream()
                .filter(d -> ! d.getCheckPerCode().equals(userInfo.getSysPerCode()))
                .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(checkResult)) {
            List<String> matCodeList = checkResult.stream().map(c -> c.getMatCode()).collect(Collectors.toList());
            return ResponseData.error(500, "只允许后端采购人员操作，当前用户没有权限操作--", matCodeList);
        }
        return ResponseData.success();
    }


    @DataSource(name = "product")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData batchComit(ProdPurchaseRequireParam param) {

        EbmsUserInfo userInfo = ebmsService.getUserInfoByAccount(this.getUserAccount());
        if (ObjectUtil.isNull(userInfo)) {
            return ResponseData.error("采购复核--当前登录人，在EBMS系统中未找到用户信息");
        }

        if (!(param.getComitType() >= 0 && param.getComitType() <= 1)) {
            return ResponseData.error("采购复核--ComitType传入有误在，值域【0,1】");
        }

        //提交归档，只能允许后端操作数据
        ResponseData checkResult = this.checkData(param, userInfo);
        if (! checkResult.getSuccess()) {
            return checkResult;
        }

        String applyStatus = param.getComitType() == 0 ? "已归档" : "待PMC审批";


        //查出id的数更新复核数量和采购交期
        List<String> idList = param.getIdList();
        List<ProdPurchaseRequire> requireList = new LambdaQueryChainWrapper<>(mapper)
                .in(ProdPurchaseRequire::getId, idList)
                .list();
        for (ProdPurchaseRequire prodPurchaseRequire : requireList) {
            if (ObjectUtil.isEmpty(prodPurchaseRequire.getOprApplyQty3())) {
                prodPurchaseRequire.setOprApplyQty3(prodPurchaseRequire.getOprApplyQty());
            }
            if (ObjectUtil.isNull(prodPurchaseRequire.getPurExpectDeliveDate())) {
                prodPurchaseRequire.setPurExpectDeliveDate(prodPurchaseRequire.getOprExpectDeliveDate());
            }
        }
        this.updateBatchById(requireList);


        LambdaUpdateWrapper<ProdPurchaseRequire> updateWrapper = new LambdaUpdateWrapper<>();

        updateWrapper.in(ProdPurchaseRequire::getId, idList);
        if (!"已归档".equals(applyStatus)) {
            updateWrapper.isNotNull(ProdPurchaseRequire::getSuggestSupplier)
                    .isNotNull(ProdPurchaseRequire::getSuggestSupplierId)
                    .isNotNull(ProdPurchaseRequire::getOrderMethod)
                    .isNotNull(ProdPurchaseRequire::getIsIncludeTax)
                    .isNotNull(ProdPurchaseRequire::getCheckExplain);
        }
        updateWrapper.eq(ProdPurchaseRequire::getApplyStatus, "待采购复核");
        updateWrapper.set(ProdPurchaseRequire::getApplyStatus, applyStatus);
        updateWrapper.set(ObjectUtil.isNotEmpty(param.getCheckExplain()), ProdPurchaseRequire::getCheckExplain, param.getCheckExplain());//复核说明
        updateWrapper.set(ProdPurchaseRequire::getCheckDate, new Date());//复核日期
        updateWrapper.set(ProdPurchaseRequire::getCheckPerName, this.getUserName());//复核人姓名
        updateWrapper.set(ProdPurchaseRequire::getCheckPerCode, this.getUserAccount());//复核人编码
        updateWrapper.set(ProdPurchaseRequire::getCheckDeptCode, userInfo.getSysComDeptCode());//复核人部门编码 取ebms
        updateWrapper.set(ProdPurchaseRequire::getCheckDeptName, userInfo.getSysComDeptFullName());//复核人部门名称 取ebms



        if (this.update(updateWrapper)) {
            mapper.updateOprApplyQty3(idList);
            return ResponseData.success("批量提交成功");
        }
        return ResponseData.error("批量提交失败");

    }

    @DataSource(name = "product")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData pmcBatchComit(ProdPurchaseRequireParam param) {
        try {
            //重复提交校验
            ResponseData responseData = this.pmcVerifyRepeatCheck(param);
            if (!responseData.getSuccess()) {
                return responseData;
            }

            String applyStatus = param.getComitType() == 0 ? "PMC未通过" : "PMC已通过";
            boolean needSync = applyStatus.equals("PMC已通过");

            LambdaUpdateWrapper<ProdPurchaseRequire> updateWrapper = new LambdaUpdateWrapper<>();
            if (param.getIdList().size()>1000) {
//                return ResponseData.error(500, "PMC批量审批不能超过1000条");
                List<String> idList = param.getIdList().subList(0, 1000);
                param.setIdList(idList);
            }
            updateWrapper.in(ProdPurchaseRequire::getId, param.getIdList());
            updateWrapper.eq(ProdPurchaseRequire::getApplyStatus, "待PMC审批");
            updateWrapper.set(ProdPurchaseRequire::getApplyStatus, applyStatus);
            if (needSync) {
                updateWrapper.set(ProdPurchaseRequire::getSyncStatus, "-1");
            }
            updateWrapper.set(ObjectUtil.isNotEmpty(param.getPmcApplyExplain()), ProdPurchaseRequire::getPmcApplyExplain, param.getPmcApplyExplain());//PMC申请说明
            updateWrapper.set(ProdPurchaseRequire::getPmcApproveDate, new Date());//PMC审批日期
            updateWrapper.set(ProdPurchaseRequire::getPmcApprovePerName, this.getUserName());//复核人姓名
            updateWrapper.set(ProdPurchaseRequire::getPmcApprovePerCode, this.getUserAccount());//复核人编码

            this.update(updateWrapper);

            //推送到k3
            if (needSync) {
                return this.pushK3(param.getIdList());
            }

            return ResponseData.success("PMC批量提交成功");
        } catch (Exception e) {
            log.error("PMC审批参数【{}】\n执行异常：{}", JSONUtil.toJsonStr(param),JSONUtil.toJsonStr(e));
            String erMsg="PMC审批参数执行异常"+JSONUtil.toJsonStr(e);
            return ResponseData.error(500, erMsg);
        }
        finally {
            //释放审核时的redis锁
            this.delPMCVerifyKey(param.getIdList());
        }
    }

    private ResponseData pmcVerifyRepeatCheck(ProdPurchaseRequireParam verifParam) {
        List<String> batchNoList = verifParam.getIdList();
        List<String> succList = new ArrayList<>();
        List<String> failList = new ArrayList<>();

        for (String batchNo : batchNoList) {
            //重复提交校验
            String redisBatchNo = (String) redisTemplate.boundValueOps(PMC_VERIFY_KEY_HEAD +batchNo).get();
            if (ObjectUtil.isEmpty(redisBatchNo)) {
                succList.add(batchNo);

            } else {
                failList.add(batchNo);
            }
        }

        if (ObjectUtil.isNotEmpty(failList)) {
            String msg = "以下PMC审批编号正在审核处理中，请稍后再提交!";
            msg=msg+"【"+JSONUtil.toJsonStr(failList)+"】";
            return ResponseData.error(500,msg,failList);
        }
        //没有重复提交的数据 缓存到redis
        if (ObjectUtil.isNotEmpty(succList)) {
            for (String succBatchNo : succList) {
                redisTemplate.boundValueOps(PMC_VERIFY_KEY_HEAD +succBatchNo).set(succBatchNo, Duration.ofMinutes(5));
            }
        }
        return ResponseData.success();

    }


    private ResponseData delPMCVerifyKey(List<String> idList) {
        if (ObjectUtil.isEmpty(idList)) {
            return ResponseData.success();
        }
        List<String> keyList = idList.stream().map(b -> PMC_VERIFY_KEY_HEAD + b).collect(Collectors.toList());
        Long delete = redisTemplate.delete(keyList);
        return ResponseData.success();
    }


    @DataSource(name = "product")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData syncK3(ProdPurchaseRequireParam param) {

        LambdaQueryWrapper<ProdPurchaseRequire> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProdPurchaseRequire::getId, param.getId());
        queryWrapper.eq(ProdPurchaseRequire::getApplyStatus, "PMC已通过");
        queryWrapper.eq(ProdPurchaseRequire::getSyncStatus, "0");
        List<ProdPurchaseRequire> purchaseList = this.baseMapper.selectList(queryWrapper);
        if (ObjectUtil.isEmpty(purchaseList)) {
            return ResponseData.error("未找到同步失败的记录信息");
        }

        List<String> idlist = new ArrayList<>();
        idlist.add(param.getId());
        return this.pushK3(idlist);
    }


    @DataSource(name = "product")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public PageResult<ProdPurchaseRequireExtentsResult> applyListPage(ProdPurchaseRequireParam reqVO) {
        IPage<ProdPurchaseRequireExtentsResult> page = this.baseMapper.applyListPage(reqVO.getPageContext(), reqVO);
        return new PageResult<>(page);

    }

    @DataSource(name = "product")
    @Override
    public List<ProdPurchaseRequireExtentsResult> exportExcel(ProdPurchaseRequireParam param){

        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        IPage<ProdPurchaseRequireExtentsResult> page = this.baseMapper.applyListPage(pageContext, param);
        List<ProdPurchaseRequireExtentsResult> records = page.getRecords();
        return records;
    }


    private ResponseData pushK3(List<String> idList) {

        String userName = this.getUserName();
        String userAccount = this.getUserAccount();

        LambdaQueryWrapper<ProdPurchaseRequire> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ProdPurchaseRequire::getId, idList);
        queryWrapper.eq(ProdPurchaseRequire::getApplyStatus, "PMC已通过");
        List<ProdPurchaseRequire> purchaseList = this.baseMapper.selectList(queryWrapper);

        Set<String> failDataIdSet = new CopyOnWriteArraySet<>();
        List<FutureTask<ProdPurchaseRequire>> futureTasks=new ArrayList<>();

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.initialize();
        //设置核心线程数
        taskExecutor.setCorePoolSize(50);
        // 设置最大线程数
        taskExecutor.setMaxPoolSize(100);
        // 设置队列容量
        taskExecutor.setQueueCapacity(100);
        // 设置允许的空闲时间（秒）
        taskExecutor.setKeepAliveSeconds(60*5);
        // 等待所有任务结束后在关闭线程池
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        for (ProdPurchaseRequire purchase : purchaseList) {

            //K3采购申请 BILL_NO
            String billNo = "";
            if (ObjectUtil.isEmpty(purchase.getOrderId()) || StockConstant.SYNC_WAIT.equals(purchase.getSyncStatus()) ) { //首次同步
                billNo = k3GeneratorNoUtil.getNoByKey(StockConstant.PURCHASE_ORDER_PREFIX, StockConstant.SYNC_K3_PURCHASE_ORDER_KEY, 6);
                purchase.setOrderId(billNo);
            } else {
                //推送失败 重推
                billNo = purchase.getOrderId();
            }


            //item只有一项
            ArrayList<K3PurchaseOrderApplyItem> itemList = new ArrayList<>();
            K3PurchaseOrderApplyItem applyItem = new K3PurchaseOrderApplyItem();
            applyItem.setFPaezBase(purchase.getTeam());
            applyItem.setFPaezBase2(purchase.getApplyPerCode());
            applyItem.setFMaterialId(purchase.getMatCode());
            applyItem.setFReqQty(purchase.getOprApplyQty3()!= null ? purchase.getOprApplyQty3().intValue() : 0);
//            //applyItem.setFPurchaserId(purchase.getCheckPerCode());//采购复核  默认传复核人员ID
            applyItem.setFPurchaserId(purchase.getFrontPurCode());//前端采购
            applyItem.setFSuggestSupplierId(purchase.getSuggestSupplierId()); //供应商编码
            //备注：
            String note = "HS".equalsIgnoreCase(purchase.getIsIncludeTax()) ? "HS," : "BHS,";
            applyItem.setFEntryNote(note+purchase.getCheckExplain());
            applyItem.setFBscHdate(purchase.getOprExpectDeliveDate());
            itemList.add(applyItem);


            //单头
            K3PurchaseOrderApplyParam orderApplyParam = new K3PurchaseOrderApplyParam();
            orderApplyParam.setFCreateDate(new Date());
            orderApplyParam.setFBillNo(billNo);//billNo
            orderApplyParam.setFApplicationDate(purchase.getPmcApproveDate());
            orderApplyParam.setFCreatorId(userAccount);
            orderApplyParam.setFApplicantId(userAccount);
            orderApplyParam.setFNote("");//计划部备注
            orderApplyParam.setFEntity(itemList);

            String jsonString = JSON.toJSONString(orderApplyParam);
            purchase.setSyncRequestMsg(jsonString);

            JSONArray jsonArray = new JSONArray();
            jsonArray.add(orderApplyParam);
            PurchasePushK3Task purchasePushK3Task = new PurchasePushK3Task(jsonArray,purchase,syncToErpConsumer);
            FutureTask<ProdPurchaseRequire> futureTask = new FutureTask(purchasePushK3Task);
            futureTasks.add(futureTask);

            taskExecutor.execute(futureTask);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }
;
        List<ProdPurchaseRequire> prodPurchaseRequireUpdateList = new ArrayList<>();

        for (int i = 0; i < futureTasks.size(); i++) {
                try {
                    FutureTask<ProdPurchaseRequire> future = futureTasks.get(i);
                     ProdPurchaseRequire purchase = future.get(1, TimeUnit.MINUTES);
                    if (!"1".equals(purchase.getSyncStatus())) {
                        failDataIdSet.add(purchase.getId());
                    }
                    prodPurchaseRequireUpdateList.add(purchase);
                } catch (InterruptedException e) {
                    //线程被中断将会进入此处
                    log.error("同步K3采购订单开始-->多线程编号[{}]--线程被中断异常[{}]",i+1,e);
                } catch (ExecutionException e) {
                    //线程执行异常将会进入此处
                    log.error("同步K3采购订单开始-->多线程编号[{}]--线程执行异常[{}]",i+1,e);
                }catch (TimeoutException e){
                    // 获取结果超时将会进入此处
                    log.error("同步K3采购订单开始-->多线程编号[{}]--获取结果超时异常[{}]",i+1,e);
                }catch (Exception e){
                    // 其他异常
                    log.error("同步K3采购订单开始-->多线程编号[{}]--获取结果一般异常[{}]",i+1,e);
                }
            }
        this.updateBatchById(prodPurchaseRequireUpdateList);
        if (ObjectUtil.isEmpty(failDataIdSet)) {
            return ResponseData.success();
        }
        return ResponseData.error(500, StrUtil.format("以下数据ID: [{}] 对应数据同步到k3失败",failDataIdSet));
    }


    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    private String getUserName() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return loginUser.getName();
    }

    private String getUserAccount() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return loginUser.getAccount();
    }

}
