package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageQuery;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.modular.mabang.constants.MabangConstant;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.*;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.K3TransferMabangItemMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.MabangAllocationWarehouseMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangAllocationWarehouseParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.AllocationWarehouseSku;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.CreateAllocationWarehouseParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.MabangHeadParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3TransferMabangItemResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangAllocationWarehouse2Result;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangAllocationWarehouseResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.MabangResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IK3TransferMabangItemService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangAllocationWarehouseService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangRequstService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangWarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 马帮分仓调拨单 服务实现类
 * </p>
 *
 * @author lsy
 * @since 2022-06-15
 */
@Slf4j
@Service
public class MabangAllocationWarehouseServiceImpl extends ServiceImpl<MabangAllocationWarehouseMapper, MabangAllocationWarehouse>
        implements IMabangAllocationWarehouseService {

    @Resource
    private MabangAllocationWarehouseMapper mapper;

    @Resource
    private K3TransferMabangItemMapper itemMapper;

    @Resource
    IMabangRequstService mabangRequstService;

    @Resource
    IMabangWarehouseService mabangWarehouseService;

    @Resource
    IK3TransferMabangItemService transferMabangItemService;


    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData create(String summaryId, List<K3TransferItem> mabangOut) {

        boolean haveException=false;//是否异常
        String exceptionMsg="";//是否异常

        List<MabangAllocationWarehouse> allocationWarehouseList = new ArrayList<>();

        Map<String, List<K3TransferItem>> listMap = mabangOut.stream().collect(Collectors.groupingBy(K3TransferItem::getSrcstockCode));
        int splitTotal = listMap.size();
        int splitNum = 1;

        for (Map.Entry<String, List<K3TransferItem>> entry : listMap.entrySet()) {
            //马帮新增采购订单记录集合
            List<K3TransferMabangItem> transferMabangItemList = new ArrayList<>();

            String srcstockCode = entry.getKey();
            List<K3TransferItem> itemList = entry.getValue();

            if (ObjectUtil.isEmpty(itemList)) {
                continue;
            }
            MabangWarehouse srcWarehouse = mabangWarehouseService.findByName(itemList.get(0).getSrcstockName());
            MabangWarehouse targetWarehouse = mabangWarehouseService.findByName(MabangConstant.ALLOCATION_WAREHOUSE_OUT);

            if (ObjectUtil.isEmpty(srcWarehouse) || ObjectUtil.isEmpty(targetWarehouse)) {
                log.info("根据仓库名称未找到仓库信息");
                continue;
            }

            String idStr = IdWorker.getIdStr();//分仓调拨单在mcms系统的数据id，对应马帮分仓调拨单的 物流编号

            CreateAllocationWarehouseParm allocationWarehouseParm = this.createAllocationWarehouse(srcWarehouse, targetWarehouse, itemList,idStr);

            MabangAllocationWarehouse allocationWarehouse = BeanUtil.copyProperties(allocationWarehouseParm, MabangAllocationWarehouse.class);
            BeanUtil.copyProperties(itemList.get(0), allocationWarehouse);
            Date date = new Date();

            allocationWarehouse.setCreateTime(date);
            allocationWarehouse.setSyncTime(date);
            allocationWarehouse.setSyncStatus(MabangConstant.SYNC_FAIL);
            allocationWarehouse.setId(idStr);//数据id和传给马帮f分仓调拨单备注信息一直
            allocationWarehouse.setSplitNum(String.valueOf(splitNum));
            allocationWarehouse.setSplitTotal(String.valueOf(splitTotal));
            allocationWarehouse.setAllocationCode("待同步成功后回写");
            allocationWarehouse.setSummaryId(summaryId);
            allocationWarehouse.setSyncResultMsg("MCMS数据已经生成等待同步");
            allocationWarehouse.setStartWarehouseName(srcWarehouse.getName());
            allocationWarehouse.setStartWarehouseId(srcWarehouse.getId());
            allocationWarehouse.setTargetWarehouseName(targetWarehouse.getName());
            allocationWarehouse.setTargetWarehouseId(targetWarehouse.getId());

            allocationWarehouseList.add(allocationWarehouse);

            //生成K3调拨单明细项已同步记录

            for (K3TransferItem item : itemList) {

                K3TransferMabangItem k3TransferMabangItem = BeanUtil.copyProperties(item, K3TransferMabangItem.class);
                k3TransferMabangItem.setSummaryId(summaryId);
                k3TransferMabangItem.setSplitNum(String.valueOf(splitNum));
                k3TransferMabangItem.setTransferDirection(MabangConstant.TRANSFER_DIRECTION_OUT);
                k3TransferMabangItem.setAllocationCode("待同步成功后回写");
                k3TransferMabangItem.setAllocationWarehouseId(idStr);
                k3TransferMabangItem.setSyncTime(date);
                k3TransferMabangItem.setSyncStatus(MabangConstant.SYNC_FAIL);
                k3TransferMabangItem.setCreateTime(date);
                k3TransferMabangItem.setId(IdWorker.getIdStr());
                k3TransferMabangItem.setSyncResultMsg("MCMS数据已经生成等待同步");

                transferMabangItemList.add(k3TransferMabangItem);
            }


            //先保存数据，在调用接口
            this.save(allocationWarehouse);
            transferMabangItemService.saveBatch(transferMabangItemList);


            Date updateTime = new Date();
            LambdaUpdateWrapper<K3TransferMabangItem> itemUpdateWrapper = new LambdaUpdateWrapper<>();
            itemUpdateWrapper.eq(K3TransferMabangItem::getAllocationWarehouseId, idStr);
            itemUpdateWrapper.set(K3TransferMabangItem::getUpdateTime, updateTime);
            itemUpdateWrapper.set(K3TransferMabangItem::getSyncTime, updateTime);


            try {
                //调用马帮接口
                MabangHeadParm mabangHeadParm = new MabangHeadParm("hwc-create-allocation-warehouse", allocationWarehouseParm);
                MabangResult result = mabangRequstService.createAllocationWarehouse(mabangHeadParm);
                allocationWarehouse.setSyncResultMsg(JSONUtil.toJsonStr(result));
                allocationWarehouse.setUpdateTime(updateTime);
                allocationWarehouse.setSyncTime(updateTime);

                itemUpdateWrapper.set(K3TransferMabangItem::getSyncResultMsg, JSONUtil.toJsonStr(result));

                //创建分仓调拨单成功
                if (MabangConstant.RESULT_SUCCESS_CODE.equals(String.valueOf(result.getCode()))) {
                    String allocationCode = String.valueOf(((Map) result.getData()).get("allocationCode"));
                    allocationWarehouse.setAllocationCode(allocationCode);
                    allocationWarehouse.setSyncStatus(MabangConstant.SYNC_SUCCESS);
                    allocationWarehouse.setSyncSuccessTimes(BigDecimal.ONE);
                    //更新分仓调拨单項
                    itemUpdateWrapper.set(K3TransferMabangItem::getSyncStatus, MabangConstant.SYNC_SUCCESS)
                            .set(K3TransferMabangItem::getSyncSuccessTimes, BigDecimal.ONE)
                            .set(K3TransferMabangItem::getAllocationCode, allocationCode);

                } else {
                    allocationWarehouse.setSyncFailTimes(BigDecimal.ONE);

                    itemUpdateWrapper.set(K3TransferMabangItem::getSyncFailTimes, BigDecimal.ONE);
                    log.error("创建分仓调拨单失败>>summaryId:{}", summaryId);
                    haveException=true;
                    exceptionMsg = exceptionMsg + "创建分仓调调拨单调用马帮接口失败返回信息:" + JSONUtil.toJsonStr(result)+"---------";
                }

            } catch (Exception e) {

                allocationWarehouse.setSyncFailTimes(BigDecimal.ONE);
                allocationWarehouse.setSyncResultMsg(JSONUtil.toJsonStr(e));

                itemUpdateWrapper.set(K3TransferMabangItem::getSyncFailTimes, BigDecimal.ONE);
                itemUpdateWrapper.set(K3TransferMabangItem::getSyncResultMsg, JSONUtil.toJsonStr(e));
                log.error(JSONUtil.toJsonStr(e));
                haveException=true;
                exceptionMsg = exceptionMsg + "创建分仓调调拨单出现异常:" + JSONUtil.toJsonStr(e)+"---------";

            }
            transferMabangItemService.getBaseMapper().update(null, itemUpdateWrapper);

            this.updateById(allocationWarehouse);

            splitNum += 1;
        }
        if (haveException) {
         return    ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, exceptionMsg, allocationWarehouseList.size());
        }

        return  ResponseData.success(allocationWarehouseList.size());
    }




    @DataSource(name = "external")
    @Override
//    @Transactional(rollbackFor = Exception.class)
    public CreateAllocationWarehouseParm createAllocationWarehouse(MabangWarehouse src, MabangWarehouse target, List<K3TransferItem> itemList,String idStr) {
        String billNo = "";
        if (ObjectUtil.isNotEmpty(itemList)) {
            billNo = itemList.get(0).getBillNo();
        }

        ArrayList<AllocationWarehouseSku> skuArrayList = new ArrayList<>();
        // 同一个分仓调拨单 ，子项的出仓，物料编码 都相同  唯独数量不同时  需要合并
        Map<String, List<K3TransferItem>> listMap = itemList.stream().collect(Collectors.groupingBy(K3TransferItem::getMaterialCode));


        for (Map.Entry<String, List<K3TransferItem>> entry : listMap.entrySet()) {
            List<K3TransferItem> items = entry.getValue();
            K3TransferItem t = items.get(0);
            BigDecimal sumQty = items.stream().map(K3TransferItem::getQty).reduce(BigDecimal::add).get();
//            t.setQty(sumQty);

            // 生成分仓调拨单子项信息
            AllocationWarehouseSku warehouseSku = AllocationWarehouseSku.builder()
                    .sku(t.getMaterialCode())
                    .num(String.valueOf(sumQty))
                    .type("1")//类型: 1库存sku;2组合sku
                    .build();
            skuArrayList.add(warehouseSku);
        }

        CreateAllocationWarehouseParm allocationWarehouseParm = CreateAllocationWarehouseParm.builder()
                .sku(JSONUtil.toJsonStr(skuArrayList))
                .shareMethod("2")//运费分摊方式 1.重量,2体积重
                .transportType(1)//运输方式 1.陆地运输 2.空运 3.海运
                .startWarehouseId(Integer.valueOf(src.getId()))
                .targetWarehouseId(Integer.valueOf(target.getId()))//todo:创建分仓调拨单 的目的仓库  1048062
                .trackNumber(idStr)//分仓调拨单数据id
                .remark(billNo)
                .build();

        return allocationWarehouseParm;
    }


    @DataSource(name = "external")
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(List<MabangAllocationWarehouse> list) {
        return this.saveBatch(list, 1000);
    }


    @DataSource(name = "external")
    @Override
    public PageResult<MabangAllocationWarehouseResult> list(MabangAllocationWarehouseParam param) {
        Page pageContext = getPageContext();
        IPage<MabangAllocationWarehouseResult> page = mapper.list(pageContext, param);
        return new PageResult<>(page);
    }


    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData anitAudit(K3TransferMabangSummary summary) {

        LambdaQueryWrapper<MabangAllocationWarehouse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MabangAllocationWarehouse::getBillNo, summary.getBillNo());
        queryWrapper.eq(MabangAllocationWarehouse::getSummaryId, summary.getId());
        queryWrapper.eq(MabangAllocationWarehouse::getSyncStatus, MabangConstant.SYNC_SUCCESS);
        queryWrapper.eq(MabangAllocationWarehouse::getAntiAudit, MabangConstant.ANTI_AUDIT_UN);

        List<MabangAllocationWarehouse> allocationWarehouseList = mapper.selectList(queryWrapper);
        List<MabangAllocationWarehouse> copyList = new ArrayList<>();

        if (ObjectUtil.isEmpty(allocationWarehouseList)) {
            return ResponseData.success(true);
        }

        HashMap<String, String> codeMap = new HashMap<>();

        for (MabangAllocationWarehouse allocationWarehouse : allocationWarehouseList) {

            String idStr = IdWorker.getIdStr();

            CreateAllocationWarehouseParm allocationWarehouseParm = this.receiveAllocationWarehouse(allocationWarehouse,idStr);

            MabangHeadParm mabangHeadParm = new MabangHeadParm("hwc-create-allocation-warehouse", allocationWarehouseParm);


            MabangAllocationWarehouse copyWarehouse = BeanUtil.copyProperties(allocationWarehouse, MabangAllocationWarehouse.class);

            Date date = new Date();
            copyWarehouse.setCreateTime(date);
            copyWarehouse.setSyncTime(date);
            copyWarehouse.setSyncStatus(MabangConstant.SYNC_FAIL);
            copyWarehouse.setId(idStr);//数据id和传给马帮f分仓调拨单物料编号信息一直
            copyWarehouse.setSplitNum(allocationWarehouse.getSplitNum());
            copyWarehouse.setSplitTotal(allocationWarehouse.getSplitTotal());
            copyWarehouse.setAllocationCode("待反向调用创建分仓调拨单成功后回写");
            copyWarehouse.setSummaryId(allocationWarehouse.getSummaryId());
            copyWarehouse.setSyncResultMsg("反向创建分仓调拨单数据初始化完成");
            copyWarehouse.setAntiAudit(MabangConstant.ANTI_AUDIT_DO);
            copyWarehouse.setRemark("反向创建分仓调拨单数据初始化完成");

            copyWarehouse.setStartWarehouseId(String.valueOf(allocationWarehouseParm.getStartWarehouseId()));
            copyWarehouse.setTargetWarehouseId(String.valueOf(allocationWarehouseParm.getTargetWarehouseId()));
            String startWarehouseName = copyWarehouse.getStartWarehouseName();
            String targetWarehouseName = copyWarehouse.getTargetWarehouseName();
            copyWarehouse.setStartWarehouseName(targetWarehouseName);
            copyWarehouse.setTargetWarehouseName(startWarehouseName);



            mapper.insert(copyWarehouse);


            try {

                MabangResult result = mabangRequstService.createAllocationWarehouse(mabangHeadParm);
                copyWarehouse.setSyncResultMsg(JSONUtil.toJsonStr(result));

                if (MabangConstant.RESULT_SUCCESS_CODE.equals(String.valueOf(result.getCode()))) {
                    String allocationCode = String.valueOf(((Map) result.getData()).get("allocationCode"));

                    copyWarehouse.setSyncStatus(MabangConstant.SYNC_SUCCESS);
                    copyWarehouse.setSyncSuccessTimes(BigDecimal.ONE);
                    copyWarehouse.setAllocationCode(allocationCode);
                    copyWarehouse.setRemark("该分仓调拨单是反审核触发了，对分仓调拨单AllocationCode:" + allocationWarehouse.getAllocationCode() + "反向调拨");

                    //添加反向的 分仓调拨单
//                    copyList.add(copyWarehouse);
                    //更新原来的分仓调拨单
                    allocationWarehouse.setAntiAudit(MabangConstant.ANTI_AUDIT_DO);
                    allocationWarehouse.setReverseAllocationCode(allocationCode);

                    //缓存 AllocationCode---- reverseAllocationCode
                    codeMap.put(allocationWarehouse.getAllocationCode(), allocationWarehouse.getReverseAllocationCode());
                } else {
                    copyWarehouse.setSyncFailTimes(BigDecimal.ONE);
                }
            } catch (Exception e) {
                copyWarehouse.setSyncFailTimes(BigDecimal.ONE);
                copyWarehouse.setSyncResultMsg(JSONUtil.toJsonStr(e));
                log.error("反向创建分仓调拨单异常:{}",JSONUtil.toJsonStr(e));
            }

            copyList.add(copyWarehouse);

        }

        //更新+添加新的反向分仓调拨单
        allocationWarehouseList.addAll(copyList);
        this.saveOrUpdateBatch(allocationWarehouseList);

        //更新之前的 k3同步项 信息
        LambdaQueryWrapper<K3TransferMabangItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(K3TransferMabangItem::getSummaryId, summary.getId());
        wrapper.eq(K3TransferMabangItem::getBillNo, summary.getBillNo());
        wrapper.in(K3TransferMabangItem::getAllocationCode, codeMap.keySet());
        List<K3TransferMabangItem> itemList = transferMabangItemService.getBaseMapper().selectList(wrapper);

        Date updateTime = new Date();
        for (K3TransferMabangItem item : itemList) {
            item.setAntiAudit(MabangConstant.ANTI_AUDIT_DO);
            item.setAntiAuditAction(MabangConstant.ANTI_AUDIT_ACTION_REVERSE_ALLOCATION);
            item.setReverseAllocationCode(codeMap.get(item.getAllocationCode()));
            item.setUpdateTime(updateTime);
        }
        transferMabangItemService.updateBatchById(itemList);

        return ResponseData.success(true);

    }


    @DataSource(name = "external")
    @Override
    public PageResult<K3TransferMabangItemResult> display(String billNo, String allocationCode) {
        Page pageContext = getPageContext();
        IPage<K3TransferMabangItemResult> page = mapper.display(pageContext, billNo, allocationCode);
        //冲回单据
        if (page == null || page.getTotal() == 0) {
            String allocCode = mapper.getAllocationCode(billNo,allocationCode);
            if (StrUtil.isNotEmpty(allocCode)){
                IPage<K3TransferMabangItemResult> reversePage = mapper.display(pageContext, billNo, allocCode);
                return new PageResult<>(reversePage);
            }
        }
        return new PageResult<>(page);
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }


    /**
     * 反审核时 对之前的分仓调拨单 反向再创建一个调拨单
     *
     * @param allocWarehouse
     * @return
     */

    private CreateAllocationWarehouseParm receiveAllocationWarehouse(MabangAllocationWarehouse allocWarehouse,String idStr) {

        CreateAllocationWarehouseParm allocationWarehouseParm = CreateAllocationWarehouseParm.builder()
                .sku(allocWarehouse.getSku())
                .shareMethod(allocWarehouse.getShareMethod())//运费分摊方式 1.重量,2体积重
                .transportType(Integer.valueOf(allocWarehouse.getTransportType()))//运输方式 1.陆地运输 2.空运 3.海运
                .startWarehouseId(Integer.valueOf(allocWarehouse.getTargetWarehouseId()))// 仓库id反转
                .targetWarehouseId(Integer.valueOf(allocWarehouse.getStartWarehouseId()))
                .trackNumber(idStr)
                .remark(allocWarehouse.getBillNo())
                .build();

        return allocationWarehouseParm;
    }

    @DataSource(name = "external")
    @Override
    public PageResult<MabangAllocationWarehouse2Result> mergeList(MabangAllocationWarehouseParam param){
        Page pageContext = getPageContext();
        IPage<MabangAllocationWarehouse2Result> page = mapper.mergeList (pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "external")
    @Override
    public List<MabangAllocationWarehouse2Result> exportExcel(MabangAllocationWarehouseParam param) {
        //设置导出数据为100万；
        PageQuery page = new PageQuery();
        page.setPageSize(Integer.MAX_VALUE);
        page.setPageNo(1);

        Page pageContext = PageFactory.createPage(page);
        Page<MabangAllocationWarehouse2Result> pageResult = this.baseMapper.mergeList(pageContext, param);
        //返回查询结果
        return pageResult.getRecords();
    }
}
