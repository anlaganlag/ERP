package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShop;
import com.tadpole.cloud.platformSettlement.api.finance.entity.StationManualAllocation;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StationManualAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.TotalDestroyFeeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.StationManualAllocationResult;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.StationManualAllocationMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IStationManualAllocationService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ITotalDestroyFeeService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ITotalStorageFeeService;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsFeePaymentResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
* <p>
* 站内手工分摊表 服务实现类
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Service
@Slf4j
public class StationManualAllocationServiceImpl extends ServiceImpl<StationManualAllocationMapper, StationManualAllocation> implements IStationManualAllocationService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Autowired
    private StationManualAllocationMapper stationManualAllocationMapper;

    @Autowired
    ITotalStorageFeeService totalStorageFeeService;

    @Autowired
    ITotalDestroyFeeService totalDestroyFeeService;

    /**
     * 批量确认站内费用自动分摊标识
     */
    @Value("${rediskey.confirmStationManual}")
    public String confirmStationManual;

    /**
     * 导入站内费用手工分摊表处理
     */
    private static final String BATCH_SMA_IMPORT = "BATCH_SMA_IMPORT";

    @DataSource(name = "finance")
    @Override
    public void deleteBatch(StationManualAllocationParam param) {

        QueryWrapper<StationManualAllocation> queryWrapper = new QueryWrapper<>();

        queryWrapper
                .eq("SHOP_NAME", param.getShopName())
                .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                .eq("NVL(CONFIRM_STATUS,0)", 0)
                .in(CollectionUtils.isNotEmpty(param.getSites()), "SITE", param.getSites())
                .isNotNull("ALLOC_ID");

        this.remove(queryWrapper);
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData pullStorageDisposeFee(StationManualAllocationParam param) {
        try {

            if (ObjectUtil.isEmpty(param.getFiscalPeriod())) {
                return ResponseData.success("拉取仓储没指定会计期间");
            }
            //推销毁移除
            TotalDestroyFeeParam totalDestroyFeeParam =TotalDestroyFeeParam.builder().startDur(param.getFiscalPeriod()).endDur(param.getFiscalPeriod()).sysShopsName(param.getShopName())
                    .sysSites(param.getSites()).build();
            ResponseData destroyResponseData = totalDestroyFeeService.pushDestroyManualAllocSql(totalDestroyFeeParam);
            if (destroyResponseData.getCode() != 200) {
                return ResponseData.error(destroyResponseData.getMessage());
            }

            //推仓储费
            ResponseData storageResponseData = totalStorageFeeService.pushStorageToManualAllocSql(param);
            if (storageResponseData.getCode() != 200) {
                    return ResponseData.error(storageResponseData.getMessage());
            }
            return ResponseData.success("拉取费用成功");
        } catch (Exception e) {
            log.error("拉取费用失败：" + JSON.toJSONString(e));
            log.error(e.getMessage());
            return ResponseData.error(e.getMessage());
        }
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData confirmBatch(StationManualAllocationParam param) {

        //redis操作需绑定key
        BoundValueOperations toList = redisTemplate.boundValueOps(this.confirmStationManual);

        try {
            //从非空则为正在批量确认中
            if (toList.get() != null && StrUtil.isNotEmpty((String) toList.get())) {
                return ResponseData.error("正在批量确认中!");
            }
            //设定正在批量确认
            toList.set("正在批量确认中!");

            QueryWrapper<StationManualAllocation> queryWrapper = new QueryWrapper<>();

            queryWrapper
                    .eq("SHOP_NAME", param.getShopName())
                    .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                    .ne("CONFIRM_STATUS", 1)
                    .in(CollectionUtils.isNotEmpty(param.getSites()), "SITE", param.getSites())
                    .isNull("ALLOC_ID");

            List<StationManualAllocation> detailManual = this.list(queryWrapper);
            queryWrapper.clear();

            if (CollUtil.isEmpty(detailManual)) {
                return ResponseData.success("无可确认的数据!");
            }

            //批量保存
            for (StationManualAllocation pa : detailManual) {
                param.setId(pa.getId());
                this.confirm(param);
            }
            return ResponseData.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.error("生成失败!:" + e);
        } finally {
            toList.set("");
        }

    }

    @DataSource(name = "finance")
    @Override
    public void delete(StationManualAllocationParam param) {
        QueryWrapper<StationManualAllocation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ID", param.getId());
        this.baseMapper.delete(queryWrapper);
    }

    @DataSource(name = "finance")
    @Override
    public void fnskuFillDestroyListing(StationManualAllocationParam param) {
        this.baseMapper.fnskuFillDestroyListing(param);
    }


    @DataSource(name = "finance")
    @Override
    public void fillListing(StationManualAllocationParam param) {
        this.baseMapper.fillListing(param);
    }


    @DataSource(name = "finance")
    @Override
    public void fillSalesBrand(StationManualAllocationParam param) {
        this.baseMapper.fillSalesBrand(param);
    }





    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData confirm(StationManualAllocationParam param) {
        String name = "系统生成";

        if (ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())) {
            name = LoginContext.me().getLoginUser().getName();
        }
        StationManualAllocationParam pa = new StationManualAllocationParam();

        //被分摊金额记录
        StationManualAllocation total = this.baseMapper.selectById(param.getId());

        //父ID审核明细记录
        QueryWrapper<StationManualAllocation> qs = new QueryWrapper<>();
        qs.eq("ALLOC_ID", param.getId());

        List<StationManualAllocation> checks = this.baseMapper.selectList(qs);

        BigDecimal storageFeeSum = checks.stream().map(StationManualAllocation::getStorageFee).filter(ObjectUtil::isNotEmpty).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        BigDecimal advertisingSum = checks.stream().map(StationManualAllocation::getAdvertising).filter(ObjectUtil::isNotEmpty).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        BigDecimal disposeFeeSum = checks.stream().map(StationManualAllocation::getDisposeFee).filter(ObjectUtil::isNotEmpty).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        BigDecimal removalDealSum = checks.stream().map(StationManualAllocation::getRemovalDeal).filter(ObjectUtil::isNotEmpty).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

        if (ObjectUtil.isEmpty(total.getStorageFee())) {
            total.setStorageFee(BigDecimal.ZERO);
        }
        if (ObjectUtil.isEmpty(total.getAdvertising())) {
            total.setAdvertising(BigDecimal.ZERO);
        }

        if (ObjectUtil.isEmpty(total.getDisposeFee())) {
            total.setDisposeFee(BigDecimal.ZERO);
        }

        if (ObjectUtil.isEmpty(total.getRemovalDeal())) {
            total.setRemovalDeal(BigDecimal.ZERO);
        }
        if (total.getStorageFee().compareTo(BigDecimal.ZERO) == 0 && total.getAdvertising().compareTo(BigDecimal.ZERO) == 0) {
            return ResponseData.error("会计期间【" + total.getFiscalPeriod() + "】账号【" + total.getShopName() +
                    "】站点【" + total.getSite() + "】,分摊异常:被分摊总记录仓储费、广告费销都为0");
        }

        //审核校验->汇总手工分摊明细金额
        if (total.getStorageFee().compareTo(storageFeeSum) == 0
                && total.getAdvertising().compareTo(advertisingSum) == 0
        ) {
            this.baseMapper.updateToReport(param);
            Date date = new Date();
            //更新分摊明细状态
            UpdateWrapper<StationManualAllocation> updateWrapper = new UpdateWrapper();
            updateWrapper.eq("ALLOC_ID", param.getId())
                         .set("CONFIRM_STATUS", "1")
                         .set("CONFIRM_BY", name)
                         .set("CONFIRM_DATE", date);
            this.baseMapper.update(null, updateWrapper);
            
            //更新被分摊总行状态
            StationManualAllocation parent = new StationManualAllocation();
            parent.setId(param.getId());
            parent.setConfirmStatus(new BigDecimal(1));
            parent.setConfirmBy(name);
            parent.setConfirmDate(date);
            this.baseMapper.updateById(parent);
            return ResponseData.success();
        } else {
            log.error("会计期间【" + total.getFiscalPeriod() + "】账号【" + total.getShopName() +
                    "】站点【" + total.getSite() + "】,被分摊总记录和明细汇总金额不相等(仓储费、广告费销)");
            return ResponseData.error("会计期间【" + total.getFiscalPeriod() + "】账号【" + total.getShopName() +
                    "】站点【" + total.getSite() + "】,被分摊总记录和明细汇总金额不相等(仓储费、广告费销)");
        }
    }

    @DataSource(name = "finance")
    @Override
    public void edit(StationManualAllocationParam param) {
        //修改费用
        UpdateWrapper<StationManualAllocation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", param.getId())
                .set("ADVERTISING", param.getAdvertising())
                .set("STORAGE_FEE", param.getStorageFee());
        this.baseMapper.update(null, updateWrapper);
    }

    @DataSource(name = "finance")
    @Override
    public StationManualAllocationResult getQuantity(StationManualAllocationParam param) {
        return this.baseMapper.getQuantity(param);
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData importExcel(MultipartFile file, List<String> departmentList, List<String> teamList) {
        log.info("导入站内费用手工分摊表Excel处理开始");
        String account = LoginContext.me().getLoginUser().getName();
        BufferedInputStream buffer = null;
        if (redisTemplate.hasKey(BATCH_SMA_IMPORT)) {
            return ResponseData.error("导入站内费用手工分摊表处理中，请稍后再试!");
        }
        try {
            redisTemplate.boundValueOps(BATCH_SMA_IMPORT).set("导入站内费用手工分摊表处理中", Duration.ofSeconds(9000));
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<StationManualAllocation>();
            EasyExcel.read(buffer, StationManualAllocation.class, listener).sheet()
                    .doRead();

            List<StationManualAllocation> dataList = listener.getDataList();
            //仓储费用业务不手动导入,误导入需清空该字段
            dataList.stream().forEach(i-> i.setStorageFee(null));
            if (CollectionUtil.isEmpty(dataList)) {
                return ResponseData.error("导入数据为空，导入失败！");
            }

            //异常数据集合 
            List<StationManualAllocation> errorList = new ArrayList<>();
            this.validation(dataList, errorList, account, departmentList, teamList);

            //批量保存更新表数据
            if (CollectionUtil.isNotEmpty(dataList)) {
                this.saveBatch(dataList);
                if (CollectionUtil.isNotEmpty(errorList)) {
                    String fileName = this.dealImportErrorList(errorList);
                    //部分导入成功
                    return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "部分导入成功，存在异常数据数据", fileName);
                }
                return ResponseData.success("导入成功！");
            }
            if (CollectionUtil.isNotEmpty(errorList)) {

                String fileName = this.dealImportErrorList(errorList);
                //导入失败
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "导入失败，存在异常数据数据", fileName);
            }
            return ResponseData.error("导入失败！导入数据为空！");
        } catch (Exception e) {
            log.error("导入Excel处理异常，导入失败！", e);
            return ResponseData.error("导入Excel处理异常，导入失败！");
        } finally {
            redisTemplate.delete(BATCH_SMA_IMPORT);
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入Excel关闭流异常", e);
                }
            }
        }
    }

    @DataSource(name = "finance")
    @Override
    public PageResult<StationManualAllocationResult> findPageBySpec(StationManualAllocationParam param) {
        return new PageResult<>(this.baseMapper.findPageBySpec(param.getPageContext(), param));
    }


    @DataSource(name = "finance")
    @Override
    public List<StationManualAllocationResult> export(StationManualAllocationParam param) {
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        IPage<StationManualAllocationResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return page.getRecords();
    }

    private void validation(List<StationManualAllocation> dataList, List<StationManualAllocation> errorList, String account, List<String> departmentList
            , List<String> teamList) {
        QueryWrapper<StationManualAllocation> queryWrapper = new QueryWrapper<>();
        List<String> fiscalPeriodList = dataList.stream().map(StationManualAllocation::getFiscalPeriod).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        List<String> shopNameList = dataList.stream().map(StationManualAllocation::getShopName).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        List<String> siteList = dataList.stream().map(StationManualAllocation::getSite).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        queryWrapper
                .in("FISCAL_PERIOD", fiscalPeriodList)
                .in("SHOP_NAME", shopNameList)
                .in("SITE", siteList)
                .ne("ADVERTISING",0);
        List<StationManualAllocation> dbList = this.baseMapper.selectList(queryWrapper);
        List<String> collect = dbList.stream().map(i -> i.getAllocId() + i.getFiscalPeriod() + i.getReportType() + i.getIncomeType() + i.getShopName() + i.getSite() + i.getAccountingCurrency() + i.getSku() +
                i.getDepartment() + i.getTeam() + i.getMaterialCode()).collect(Collectors.toList());
        Iterator<StationManualAllocation> iterator = dataList.listIterator();
        while (iterator.hasNext()) {
            StationManualAllocation manualAllocation = iterator.next();
            manualAllocation.setCreateBy(account);
            manualAllocation.setCreateAt(new Date());

            if (manualAllocation.getAllocId() == null) {
                //只导入明细，过滤掉父类ID为null的数据
                iterator.remove();
            } else if (StringUtils.isEmpty(manualAllocation.getFiscalPeriod())
                    || StringUtils.isEmpty(manualAllocation.getReportType())
                    || StringUtils.isEmpty(manualAllocation.getIncomeType())
                    || StringUtils.isEmpty(manualAllocation.getShopName())
                    || StringUtils.isEmpty(manualAllocation.getSite())
                    || StringUtils.isEmpty(manualAllocation.getAccountingCurrency())
                    || StringUtils.isEmpty(manualAllocation.getSku())
                    || StringUtils.isEmpty(manualAllocation.getDepartment())
                    || StringUtils.isEmpty(manualAllocation.getTeam())
                    || StringUtils.isEmpty(manualAllocation.getMaterialCode())
                    || StringUtils.isEmpty(manualAllocation.getSalesBrand())
                    || manualAllocation.getAdvertising() == null
            ) {
                //不为空校验
                manualAllocation.setUploadRemark("会计期间、报告类型、收入确认类型、账号、站点、核算币种、SKU、事业部、team、物料编码、销售品牌、advertising均不能为空");
                errorList.add(manualAllocation);
                iterator.remove();
            } else {
                //验证事业部，Team信息
                StringBuffer validInfo = new StringBuffer();
                if (!departmentList.contains(manualAllocation.getDepartment())) {
                    validInfo.append("事业部有误!");
                }
                if (!teamList.contains(manualAllocation.getTeam())) {
                    validInfo.append("Team有误!");
                }
                if (validInfo.length() > 0) {
                    manualAllocation.setUploadRemark(validInfo.toString());
                    errorList.add(manualAllocation);
                    iterator.remove();
                } else {
                    String manEntity =  manualAllocation.getAllocId() + manualAllocation.getFiscalPeriod() + manualAllocation.getReportType() + manualAllocation.getIncomeType() + manualAllocation.getShopName() + manualAllocation.getSite() + manualAllocation.getAccountingCurrency() + manualAllocation.getSku() +
                            manualAllocation.getDepartment() + manualAllocation.getTeam() + manualAllocation.getMaterialCode();
                    if (collect.stream().anyMatch(i -> i.equals(manEntity))) {
                        manualAllocation.setUploadRemark("数据重复，请排查重复数据！");
                        errorList.add(manualAllocation);
                        iterator.remove();
                    }
                }
            }
        }
    }


    private String dealImportErrorList(List<StationManualAllocation> errorList) {
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName, false);
            EasyExcel.write(out, StationManualAllocation.class)
                    .sheet("导入结果").doWrite(errorList);
        } catch (FileNotFoundException e) {
            log.error("导入Excel异常数据导出异常", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("导入Excel异常数据导出流关闭异常", e);
                }
            }
        }
        return fileName;
    }


    private Page getPageContext() {
        return PageFactory.defaultPage();
    }


    @Override
    @DataSource(name = "finance")
    public ResponseData mergeAllocLine(StationManualAllocationParam param) {
        QueryWrapper<StationManualAllocation> queryWrapper = new QueryWrapper<>();

        queryWrapper
                .select("fiscal_period, shop_name, site,count(*)")
                .eq("fiscal_period","2022-03")
                .groupBy("fiscal_period", "shop_name", "site")
                .having("count(*)>1");
        List<StationManualAllocation> salesTargets = stationManualAllocationMapper.selectList(queryWrapper);
        return ResponseData.success(salesTargets);


    }

    @Override
    @DataSource(name = "finance")
    public void updateAllocLineStatus() {
        try {
            LambdaQueryWrapper<StationManualAllocation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .isNull(StationManualAllocation::getAllocId)
                    .and(i->i.isNull(StationManualAllocation::getConfirmStatus)
                            .or().eq(StationManualAllocation::getConfirmStatus,"0"));

            List<StationManualAllocation> allocList = this.baseMapper.selectList(queryWrapper);
            //全部数据已分摊
            if (ObjectUtil.isEmpty(allocList)) {
                return;
            }
            //单条无论是settlement还是dateRange还是0都是主行,标记为2
            //多条则取settlement为主行,将非主行行的仓储费和广告费汇总到主行,主行标记为2,非主行标记为3
            Map<String, List<StationManualAllocation>> groupAlloc = allocList.stream()
                    .collect(Collectors.groupingBy(a -> a.getFiscalPeriod() + a.getShopName() + a.getSite()));
            List<StationManualAllocation> upAllocList = new ArrayList<>();
            //原分摊行存档为4
            List<StationManualAllocation> oriAllocList = new ArrayList<>();



            for (Map.Entry<String, List<StationManualAllocation>> dimension : groupAlloc.entrySet()) {
                List<StationManualAllocation> value = dimension.getValue();
                //当前维度只有1条这条就是主分摊行 状态设为2主分摊行
                if (value.size() == 1) {

                    //原分摊行存档为4实现
                    StationManualAllocation oriAllocation = new StationManualAllocation();
                    StationManualAllocation stationManualAllocation = value.get(0);
                    BeanUtil.copyProperties(stationManualAllocation,oriAllocation);
                    oriAllocation.setConfirmStatus(new BigDecimal(4));
                    oriAllocList.add(oriAllocation);

                    stationManualAllocation.setConfirmStatus(new BigDecimal(2));
                    upAllocList.add(stationManualAllocation);
                } else if (value.size() > 1) {
                    BigDecimal storageFee = value.stream().filter(i -> ObjectUtil.isNotEmpty(i.getStorageFee())).map(StationManualAllocation::getStorageFee).reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal advertisingFee = value.stream().filter(i -> ObjectUtil.isNotEmpty(i.getAdvertising())).map(StationManualAllocation::getAdvertising).reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal disposeFee = value.stream().filter(i -> ObjectUtil.isNotEmpty(i.getDisposeFee())).map(StationManualAllocation::getDisposeFee).reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal removalDeal = value.stream().filter(i -> ObjectUtil.isNotEmpty(i.getRemovalDeal())).map(StationManualAllocation::getRemovalDeal).reduce(BigDecimal.ZERO, BigDecimal::add);
                    value.stream().forEach(i -> {
                        //多条如果有settlement就汇总到settlement那条
                        if (i.getReportType().equals("Settlement")) {
                            //原分摊行存档为4实现
                            StationManualAllocation oriAllocation = new StationManualAllocation();
                            BeanUtil.copyProperties(i,oriAllocation);
                            oriAllocation.setConfirmStatus(new BigDecimal(4));
                            oriAllocList.add(oriAllocation);

                            i.setConfirmStatus(new BigDecimal(2));
                            i.setStorageFee(storageFee);
                            i.setAdvertising(advertisingFee);
                            i.setAdvertising(disposeFee);
                            i.setAdvertising(removalDeal);
                            //多条非settlement都是非分摊行
                        } else {
                            i.setConfirmStatus(new BigDecimal(3));
                        }
                    });
                    upAllocList.addAll(value);
                }

            }
            this.updateBatchById(upAllocList);
            this.saveBatch(oriAllocList);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(JSONUtil.toJsonStr(e));
        }

    }


    @Override
    @DataSource(name = "finance")
    public void updateAllocLineStatusNew() {
        try {
            LambdaQueryWrapper<StationManualAllocation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .isNull(StationManualAllocation::getAllocId)
                    .and(i->i.isNull(StationManualAllocation::getConfirmStatus)
                            .or().eq(StationManualAllocation::getConfirmStatus,"0"));

            List<StationManualAllocation> allocList = this.baseMapper.selectList(queryWrapper);
            //全部数据已分摊
            if (ObjectUtil.isEmpty(allocList)) {
                return;
            }
            Map<String, List<StationManualAllocation>> allocLineMap = allocList.stream().collect(Collectors.groupingBy(s -> s.getFiscalPeriod() + s.getShopName() + s.getSite()));


            List<StationManualAllocation> newAllocLines = new ArrayList<>();

            for (Map.Entry<String, List<StationManualAllocation>> dimension : allocLineMap.entrySet()) {
                List<StationManualAllocation> allocNums = dimension.getValue();
                if (allocNums.size() == 1){
                    allocNums.forEach(i -> i.setConfirmStatus(new BigDecimal(2)));
                    newAllocLines.addAll(allocNums);
                } else {
                    BigDecimal storageFeeSum = allocNums.stream().map(StationManualAllocation::getStorageFee).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
                    BigDecimal advertisingFeeSum = allocNums.stream().map(StationManualAllocation::getAdvertising).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
                    for (StationManualAllocation allocNum : allocNums) {
                        if ("Settlement".equals(allocNum.getReportType())) {
                            allocNum.setConfirmStatus(new BigDecimal(2));
                            allocNum.setStorageFee(storageFeeSum);
                            allocNum.setAdvertising(advertisingFeeSum);
                            newAllocLines.add(allocNum);
                        }else {
                            allocNum.setConfirmStatus(new BigDecimal(3));
                            newAllocLines.add(allocNum);
                        }
                    }

                }

            }


        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(JSONUtil.toJsonStr(e));
        }

    }
}
