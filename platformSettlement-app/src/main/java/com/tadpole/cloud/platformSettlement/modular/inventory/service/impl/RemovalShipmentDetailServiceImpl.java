package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.*;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemovalShipmentDetailParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.DisposeRemoveTrackResult;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.OverseasInWarehouseFBAResult;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.RemovalShipmentDetailResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IRemovalShipmentCostMonthlyShareService;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.RemovalShipmentDetailMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
* <p>
* 移除货件详情报告 服务实现类
* </p>
*
* @author gal
* @since 2022-04-15
*/
@Service
@Slf4j
public class RemovalShipmentDetailServiceImpl extends ServiceImpl<RemovalShipmentDetailMapper, RemovalShipmentDetail> implements IRemovalShipmentDetailService {

    @Autowired
    private RemovalShipmentDetailMapper mapper;
    @Autowired
    private IDisposeRemoveTrackService disposeRemoveTrackService;
    @Autowired
    private IDisposeRemoveService service;
    @Autowired
    private IRemovalShipmentDetailService detailService;
    @Autowired
    private IDisposeRemoveDetailService detailTwoService;
    @Autowired
    private IZZDistributeMcmsService erpService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private IRemovalShipmentCostMonthlyShareService removalShipmentCostMonthlyShareService;
    @Value("${finance_database}")
    private String financeDatabase;
    /**
     * 生成列表标识
     */
    private static final String TOLIST = "TO_REMOVE_LIST1";
    /**
     * 生成销毁移除跟踪表KEY
     */
    private static final String TO_REMOVE_TRACK = "TO_REMOVE_TRACK";

    @DataSource(name = "warehouse")
    @Override
    public PageResult<RemovalShipmentDetailResult> findPageBySpec(RemovalShipmentDetailParam param) {
        return new PageResult<>(mapper.customPageList(PageFactory.defaultPage(), param));
    }

    @DataSource(name = "warehouse")
    @Override
    public List<RemovalShipmentDetailResult> export(RemovalShipmentDetailParam param) {
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        IPage<RemovalShipmentDetailResult> page = this.baseMapper.customPageList(pageContext, param);
        return page.getRecords();
    }

    @DataSource(name = "warehouse")
    @Override
    public String getQuantity(RemovalShipmentDetailParam param) {
        return this.baseMapper.getQuantity(param);
    }

    @DataSource(name = "warehouse")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean orgBatch(List<RemovalShipmentDetailParam> params) {
        return this.baseMapper.orgBatch(params);
    }

    @DataSource(name = "warehouse")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<RemovalShipmentDetailParam> orgList() {
        return this.baseMapper.orgList();
    }

    @DataSource(name = "warehouse")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getMaterial(RemovalShipmentDetailParam param) {
        return this.baseMapper.getMaterial(param);
    }

    @DataSource(name = "erpcloud")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData assignBatchMaterial(List<ZZDistributeMcms> assignList) {
        try
        {
            erpService.saveBatch(assignList);
            return ResponseData.success("生成列表及分配ERP物料成功!");
        }  catch (Exception e) {
            e.printStackTrace();
            return ResponseData.error("生成列表失败!");
        }
    }

    @DataSource(name = "erpcloud")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignMaterial(RemovalShipmentDetailParam param) {
        ZZDistributeMcms ent = new ZZDistributeMcms();
        ent.setMaterialCode(param.getMat());
        ent.setShopCode(param.getSalesOrganizationCode());
        erpService.save(ent);
    }

    @DataSource(name = "warehouse")
    @Override
    public void verify(RemovalShipmentDetailParam param) {
        UpdateWrapper<RemovalShipmentDetail> updateWrapper = new UpdateWrapper<>();
        updateWrapper
            .eq("ID", param.getId())
            .eq("VERIFY_STATUS",0)
            .eq("GENERATE_STATUS",0)
            .set("VERIFY_AT", new Date())
            .set("UPDATE_TIME", new Date())
            .set("VERIFY_BY", LoginContext.me().getLoginUser().getName())
            .set("UPDATE_BY", LoginContext.me().getLoginUser().getName())
            .set("VERIFY_STATUS", 1)
            .set("ORG", param.getOrg())
            .set("INVENTORY_ORGANIZATION_CODE", param.getInventoryOrganizationCode())
            .set("WAREHOUSE_NAME", param.getWarehouseName())
            .set("WAREHOUSE_CODE", param.getWarehouseCode());
        detailService.update(null, updateWrapper);
    }

    @DataSource(name = "warehouse")
    @Override
    public void generateRemovalShipmentMonShare(RemovalShipmentDetailParam param) {
        String rpNewAveragePrice = financeDatabase + ".RP_NEW_AVERAGE_PRICE";
        String rpSpotExchangeRate = financeDatabase + ".RP_SPOT_EXCHANGE_RATE";
        removalShipmentCostMonthlyShareService.generateRemovalShipmentMonShare(this.baseMapper.generateRemovalShipmentMonShare(param, rpNewAveragePrice, rpSpotExchangeRate, LoginContext.me().getLoginUser().getName()));
    }

    @DataSource(name = "warehouse")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reject(RemovalShipmentDetailParam param) {
        UpdateWrapper<RemovalShipmentDetail> updateWrapper = new UpdateWrapper<>();
        updateWrapper
            .eq("id", param.getId())
            .eq("VERIFY_STATUS",0)
            .eq("GENERATE_STATUS",0)
            .set("VERIFY_AT", new Date())
            .set("UPDATE_TIME", new Date())
            .set("VERIFY_BY", LoginContext.me().getLoginUser().getName())
            .set("UPDATE_BY", LoginContext.me().getLoginUser().getName())
            .set("VERIFY_STATUS", 2);
        detailService.update(null, updateWrapper);
    }

    @DataSource(name = "warehouse")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean verifyUpdateBatch(RemovalShipmentDetailParam param) {
        return this.baseMapper.verifyUpdateBatch(param);
    }

    @DataSource(name = "warehouse")
    @Override
    public void rejectBatch(List<RemovalShipmentDetailParam> params) {
        RemovalShipmentDetailParam param =  new RemovalShipmentDetailParam();
        param.setVerifyBy(LoginContext.me().getLoginUser().getName());
        param.setUpdateBy(LoginContext.me().getLoginUser().getName());
        List<String> IdList= params.stream().map(i->i.getId().toString()).collect(Collectors.toList());
        List<List<String>> lists = ListUtil.split(IdList, 1000);
        //正常取时分配
        for (List<String> lst : lists) {
            param.setIdList(lst);
            this.baseMapper.rejectBatch(param);
        }
    }

    @DataSource(name = "warehouse")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData toDisposeRemoveList(RemovalShipmentDetailParam param) {
        if(StringUtils.isBlank(param.getShipmentStartDate()) || StringUtils.isBlank(param.getShipmentEndDate())){
            return ResponseData.error("请选择数据期间");
        }
        param.setShipmentStartDate(DateUtil.beginOfMonth(DateUtil.parse(param.getShipmentStartDate(), DatePattern.SIMPLE_MONTH_PATTERN)).toString());
        param.setShipmentEndDate(DateUtil.endOfMonth(DateUtil.parse(param.getShipmentEndDate(), DatePattern.SIMPLE_MONTH_PATTERN)).toString());
        BoundValueOperations toList = redisTemplate.boundValueOps(TOLIST);
        //从redis里面取值如果非空则为正在生成!
        if (toList.get() != null && StrUtil.isNotEmpty((String)toList.get())){
            return ResponseData.error("正在生成中!");
        }
        try {
            //为空则设为正在生成!
            toList.set("正在生成中!");
            String userName = LoginContext.me().getLoginUser().getName();
            String userAccount = LoginContext.me().getLoginUser().getAccount();

            //获取销毁移除列表单据头数据
            List<RemovalShipmentDetailResult> headerList = this.baseMapper.getListHeader(param);
            if (CollectionUtil.isEmpty(headerList)) {
                return ResponseData.error("无可生成的数据!");
            }
            //列表对应明细id分配Erp物料
            List<String>  detailIdList = new ArrayList<>() ;
            for (RemovalShipmentDetailResult header : headerList) {
                DisposeRemove out = getListEntity(header);
                //生成销售出库头部ENDING_INVENTORY
                out.setUpdateBy(userName);
                out.setUpdateAt(new Date());
                out.setSite(header.getSysSite());
                out.setShopName(header.getSysShopsName());

                //拼接销售出库单据编号
                QueryWrapper<DisposeRemove> wp = new QueryWrapper();
                //获取销毁移除列表明细数据
                header.setShipmentStartDate(param.getShipmentStartDate());
                header.setShipmentEndDate(param.getShipmentEndDate());
                List<DisposeRemoveDetail> detailList = this.baseMapper.getDetailList(header);
                String billCodeBase = "QTCKDXH-" + header.getInventoryOrganizationCode() + "-" + header.getYear() + header.getMonth() + "000";
                wp.likeRight("BILL_CODE", billCodeBase);
                int billCodeIdx = service.count(wp) ;
                wp.clear();
                int detailSizeLimit = 500;
                List<List<DisposeRemoveDetail>> lists = ListUtil.split(detailList, detailSizeLimit);
                for (List<DisposeRemoveDetail> lst: lists) {
                    if ( billCodeIdx == 0 ){
                        out.setBillCode(billCodeBase);
                    } else {out.setBillCode(billCodeBase+'-'+billCodeIdx);}
                    //保存销毁移除列表单据头
                    service.save(out);
                    lst.stream().forEach(i->{i.setOutId(out.getId());i.setUpdateBy(userAccount);});
                    //保存销毁移除列表明细
                    detailTwoService.saveBatch(lst);
                    detailIdList.addAll(lst.stream().map(i->i.getId().toString()).collect(Collectors.toList()));
                    billCodeIdx++;
                }
                header.setUpdateBy(userAccount);
                header.setBillCode(billCodeBase);

                //更新Removal Shipment Detail生成状态
                this.baseMapper.updateSrcDetailList(header);
            }

            //刷listings
            this.baseMapper.updateDetailList();

            //刷存档listings
            this.baseMapper.updateFileDetailList();
            int batchSize = 1000;
            List<ZZDistributeMcms> assignList = new ArrayList<>() ;
            List<List<String>> lists = ListUtil.split(detailIdList, batchSize);
            for (List<String> lst:lists){
                assignList.addAll(this.baseMapper.assignMaterialList(lst));
            }
            return ResponseData.success(assignList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseData.error("生成失败!");
        } finally {
            toList.set("");
        }
    }

    @DataSource(name = "warehouse")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData generateTrack() {
        //redis里面取值则为正在生成!
        if(redisTemplate.hasKey(TO_REMOVE_TRACK)){
            return ResponseData.error("销毁移除跟踪表数据正在生成中，请稍后再试!");
        }
        try{
            redisTemplate.boundValueOps(TO_REMOVE_TRACK).set("销毁移除跟踪表数据正在生成中", Duration.ofSeconds(600));
            //查询上一个月到现在的数据
            Date endDate = DateUtil.date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endDate);
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date startDate = calendar.getTime();

            //销毁移除跟踪表刷每月移除订单表数据
            this.baseMapper.updateRemovalDetailToTrace(startDate, endDate);

            /**
             * 销毁移除跟踪表反向推导每月移除订单表数据，删除无效数据
             * 场景分析：
             *      remove order detail报告下载入库是根据币别来判断站点的，由于数据的状态会变化，
             *      当状态为Pending时，此时没有币别，所以站点为空，
             *      当状态为Completed时币别确定，所以对应的站点就可以确定
             * 详细推导逻辑：
             *      从remove order detail当状态为Pending时站点为空，此时数据流到销毁移除跟踪表的站点也为空，当后续状态变为Completed时，站点确定，
             *      由于两表关联维度包含站点维度，当站点确定时，由于维度关联不上，所以会新增一条记录，此时存在旧的站点为空的数据冗余，故要去除冗余数据
             * 解决方案：
             *      通过两表反向推导找出站点为空的冗余数据，然后去除冗余数据
             */
            this.baseMapper.deleteDisposeRemoveTrack();

            //把remove shipment detail解析出来的站点赋值给LAST_SYS_SITE
            this.baseMapper.updateShipmentLastSysSite();

            /**
             * 根据remove order detail刷新remove shipment detail最新站点信息
             * 场景分析：
             *      remove shipment detail数据审核之后，即使站点信息不准确，也不会更新，此时站点是不准确的，为了更新准确的站点信息，则新增一个字段记录；
             *      当更新站点的时候，根据维度可能会出现一对多的情况，此时记录多个站点
             */
            this.baseMapper.updateShipmentDetailSite(startDate, endDate);

            //销毁移除跟踪表刷移除货件表数据
            this.baseMapper.updateRemovalShipmentToTrace(startDate, endDate);

            //获取销毁移除跟踪表刷移除货件表多站点数据
            List<DisposeRemoveTrackResult> disposeRemoveTrackResultList = this.baseMapper.getMoreSiteTrace();
            if(CollectionUtil.isNotEmpty(disposeRemoveTrackResultList)){
                List<DisposeRemoveTrack> updateTrackList = new ArrayList<>();

                //针对多站点进行数据扣除，扣除规则数量差距小的优先扣除
                //货件维度总的数量
                Map<String, DisposeRemoveTrackResult> groupMap = new HashMap<>();
                for (DisposeRemoveTrackResult trackResult : disposeRemoveTrackResultList) {
                    DisposeRemoveTrackResult nowAmount = new DisposeRemoveTrackResult();
                    if(groupMap.get(trackResult.getGroupKey()) == null){
                        nowAmount.setDisposeAmount(trackResult.getDisposeAmount());
                        nowAmount.setRemoveAmount(trackResult.getRemoveAmount());
                        groupMap.put(trackResult.getGroupKey(), nowAmount);
                    }
                    nowAmount = groupMap.get(trackResult.getGroupKey());

                    BigDecimal nowDisposeAmount = nowAmount.getDisposeAmount();
                    BigDecimal nowRemoveAmount = nowAmount.getRemoveAmount();

                    DisposeRemoveTrack updateTrack = new DisposeRemoveTrack();
                    updateTrack.setAUploadDate(trackResult.getUploadDate());
                    //销毁扣减
                    if(BigDecimal.ZERO.compareTo(trackResult.getOrderDisposedAmount()) < 0 && nowDisposeAmount.compareTo(trackResult.getOrderDisposedAmount()) >= 0){
                        updateTrack.setDisposeAmount(trackResult.getOrderDisposedAmount().toString());
                        updateTrack.setId(trackResult.getId());
                        nowAmount.setDisposeAmount(nowDisposeAmount.subtract(trackResult.getOrderDisposedAmount()));
                    }
                    if(BigDecimal.ZERO.compareTo(nowDisposeAmount) < 0 && nowDisposeAmount.compareTo(trackResult.getOrderDisposedAmount()) < 0){
                        updateTrack.setDisposeAmount(nowDisposeAmount.toString());
                        updateTrack.setId(trackResult.getId());
                        nowAmount.setDisposeAmount(trackResult.getOrderDisposedAmount().subtract(nowDisposeAmount));
                    }
                    //移除扣减
                    if(BigDecimal.ZERO.compareTo(trackResult.getOrderShippedAmount()) < 0 && nowRemoveAmount.compareTo(trackResult.getOrderShippedAmount()) >= 0){
                        updateTrack.setRemoveAmount(trackResult.getOrderShippedAmount().toString());
                        updateTrack.setId(trackResult.getId());
                        nowAmount.setRemoveAmount(nowRemoveAmount.subtract(trackResult.getOrderShippedAmount()));
                    }
                    if(BigDecimal.ZERO.compareTo(nowRemoveAmount) < 0 && nowRemoveAmount.compareTo(trackResult.getOrderShippedAmount()) < 0){
                        updateTrack.setRemoveAmount(nowRemoveAmount.toString());
                        updateTrack.setId(trackResult.getId());
                        nowAmount.setRemoveAmount(trackResult.getOrderShippedAmount().subtract(nowRemoveAmount));
                    }

                    if(updateTrack.getId() != null){
                        updateTrackList.add(updateTrack);
                    }
                }

                //更新多站点货件销毁移除数量
                if(CollectionUtil.isNotEmpty(updateTrackList)){
                    disposeRemoveTrackService.updateBatchById(updateTrackList);
                    /*for (DisposeRemoveTrack updateTrack : updateTrackList) {
                        disposeRemoveTrackService.updateById(updateTrack);
                    }*/
                }
            }

            //销毁移除跟踪表刷listing
            this.baseMapper.updateTrackList();
            //销毁移除跟踪表刷listing(存档)
            this.baseMapper.updateFileTrackList();
            //更新移除订单表生成销毁移除跟踪表状态
            this.baseMapper.updateRemovalDetailStatus(startDate, endDate);
            return ResponseData.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.error("刷新失败!:"+e);
        }finally {
            redisTemplate.delete(TO_REMOVE_TRACK);
        }
    }

    @DataSource(name = "warehouse")
    @Override
    public PageResult<DisposeRemoveTrackDTO> trackList(RemovalShipmentDetailParam param) {
        List<String> orderTypes = param.getOrderTypes();
        List<String> orderStatuss = param.getOrderStatuss();
        if(CollectionUtil.isNotEmpty(orderTypes)){
            if(orderTypes.contains("空")){
                param.setOrderType("空");
            }
        }
        if(CollectionUtil.isNotEmpty(orderStatuss)){
            if(orderStatuss.contains("空")) {
                param.setOrderStatus("空");
            }
        }
        return new PageResult<>(mapper.trackPageList(PageFactory.defaultPage(), param));
    }

    @DataSource(name = "warehouse")
    @Override
    public List<DisposeRemoveTrackDTO> trackExport(RemovalShipmentDetailParam param) {
        List<String> orderTypes = param.getOrderTypes();
        List<String> orderStatuss = param.getOrderStatuss();
        if(CollectionUtil.isNotEmpty(orderTypes)){
            if(orderTypes.contains("空")){
                param.setOrderType("空");
            }
        }
        if(CollectionUtil.isNotEmpty(orderStatuss)){
            if(orderStatuss.contains("空")) {
                param.setOrderStatus("空");
            }
        }
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        return mapper.trackPageList(pageContext, param).getRecords();
    }

    @DataSource(name = "warehouse")
    @Override
    public ResponseData getTrackQuantity(RemovalShipmentDetailParam param) {
        List<String> orderTypes = param.getOrderTypes();
        List<String> orderStatuss = param.getOrderStatuss();
        if(CollectionUtil.isNotEmpty(orderTypes)){
            if(orderTypes.contains("空")){
                param.setOrderType("空");
            }
        }
        if(CollectionUtil.isNotEmpty(orderStatuss)){
            if(orderStatuss.contains("空")) {
                param.setOrderStatus("空");
            }
        }
        return ResponseData.success(this.baseMapper.getTrackQuantity(param));
    }

    @DataSource(name = "warehouse")
    @Override
    public ResponseData refreshListing(){
        //销毁移除跟踪表刷listing
        this.baseMapper.updateTrackList();
        //销毁移除跟踪表刷listing(存档)
        this.baseMapper.updateFileTrackList();
        return ResponseData.success();
    }

    @DataSource(name = "warehouse")
    @Override
    public ResponseData orderTypeSelect() {

        List<Map<String, Object>> selectList = this.baseMapper.orderTypeSelect();
        if(selectList.contains(null)){
            Map<String, Object> map = new HashMap<>();
            map.put("ORDER_TYPE", "空");
            Collections.replaceAll(selectList, null, map);
        }
        return ResponseData.success(selectList);
    }

    @DataSource(name = "warehouse")
    @Override
    public ResponseData orderStatusSelect() {
        List<Map<String, Object>> selectList = this.baseMapper.orderStatusSelect();
        if(selectList.contains(null)){
            Map<String, Object> map = new HashMap<>();
            map.put("ORDER_STATUS", "空");
            Collections.replaceAll(selectList, null, map);
        }
        return ResponseData.success(selectList);
    }

    private DisposeRemove getListEntity(RemovalShipmentDetailResult param) {
        DisposeRemove entity = new DisposeRemove();
        BeanUtil.copyProperties(param, entity);
        return entity;
    }

    @DataSource(name = "warehouse")
    @Override
    public ResponseData refreshSite() {
        this.baseMapper.refreshSite();
        return ResponseData.success();
    }

    @DataSource(name = "warehouse")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData addNewOrg(){
        try {
            //刷组织之前先刷order站点
            this.baseMapper.refreshSite();

            //刷组织名称（Amazon_账号_站点）和仓库组织名称（Amazon_账号_站点_仓库）
            this.baseMapper.addOrgName();

            //根据组织刷库存组织编码
            this.baseMapper.refreshOrgCode();

            //根据仓库组织名称获取仓库组织编码
            this.baseMapper.refreshWareOrgCode();
            return ResponseData.success();
        } catch (Exception e){
            log.error("RemovalShipmentDetail刷组织编码失败!", e);
            e.printStackTrace();
            return ResponseData.error(String.valueOf(e));
        }
    }

    @DataSource(name = "warehouse")
    @Override
    public List<OverseasInWarehouseFBAResult> generateInWarehouseByFBA() {
        return baseMapper.generateInWarehouseByFBA();
    }

    @DataSource(name = "warehouse")
    @Override
    public void updateGenerateHwc(List<BigDecimal> params){
        baseMapper.updateGenerateHwc(params);
    }

    @DataSource(name = "warehouse")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData generateRemovalShipmentDetail(){
        baseMapper.generateRemovalShipmentDetail();

        //取RemovalOrderDetail更新RemovalShipmentDetail的站点
//        baseMapper.updateShipmentDetailSite();
        return ResponseData.success();
    }
}
