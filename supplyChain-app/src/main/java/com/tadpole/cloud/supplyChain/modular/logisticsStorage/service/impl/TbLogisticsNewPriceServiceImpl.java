package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.toolkit.MPJWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.*;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsNewPriceDetMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsNewPriceMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsNewPriceParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsNewPriceDetResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsNewPriceExportResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsNewPriceResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsAccountService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsNewPriceDetService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsNewPriceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 物流商的价格信息;(tb_logistics_new_price)表服务实现类
 *
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsNewPriceServiceImpl extends ServiceImpl<TbLogisticsNewPriceMapper, TbLogisticsNewPrice> implements TbLogisticsNewPriceService {
    @Resource
    private TbLogisticsNewPriceMapper tbLogisticsNewPriceMapper;
    @Resource
    private TbLogisticsNewPriceDetMapper tbLogisticsNewPriceDetMapper;

    @Resource
    private TbLogisticsNewPriceDetService detService;
    @Resource
    private TbLogisticsAccountService logisticsAccountService;

    /**
     * 通过ID查询单条数据
     *
     * @param pkLogpId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsNewPrice queryById(BigDecimal pkLogpId) {
        return tbLogisticsNewPriceMapper.selectById(pkLogpId);
    }

    /**
     * 分页查询
     *
     * @param param   筛选条件
     * @param current 当前页码
     * @param size    每页大小
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public Page<TbLogisticsNewPriceResult> paginQuery(TbLogisticsNewPriceParam param, long current, long size) {

        //1. 构建动态查询条件
        MPJLambdaWrapper<TbLogisticsNewPrice> queryWrapper = getQueryWrapper(param, false);

        //2. 执行分页查询
        Page<TbLogisticsNewPriceResult> pagin = new Page<>(current, size, true);
        IPage<TbLogisticsNewPriceResult> selectResult = tbLogisticsNewPriceMapper.selectJoinPage(pagin, TbLogisticsNewPriceResult.class, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;

    }

    /**
     * @param param    查询条件
     * @param isExport 是否导出
     * @return
     */
    private static MPJLambdaWrapper<TbLogisticsNewPrice> getQueryWrapper(TbLogisticsNewPriceParam param, boolean isExport) {
        MPJLambdaWrapper<TbLogisticsNewPrice> queryWrapper = MPJWrappers.<TbLogisticsNewPrice>lambdaJoin();
        queryWrapper
                .selectAll(TbLogisticsNewPrice.class)
                .select(TbLogisticsLink::getLogListLinkTemp)
                .select(TbLogisticsProvider::getLpCode, TbLogisticsProvider::getLpName, TbLogisticsProvider::getLpSimpleName)
                .leftJoin(TbLogisticsAccount.class, TbLogisticsAccount::getLcCode, TbLogisticsNewPrice::getBusLcCode)
                .leftJoin(TbLogisticsProvider.class, TbLogisticsProvider::getLpCode, TbLogisticsAccount::getLpCode)
                .leftJoin(TbLogisticsLink.class, TbLogisticsLink::getLogTraMode1, TbLogisticsProvider::getLpName)
                .eq(ObjectUtil.isNotEmpty(param.getBusLcCode()), TbLogisticsAccount::getLcCode, param.getBusLcCode())//物流账号
                .eq(ObjectUtil.isNotEmpty(param.getLpSimpleName()), TbLogisticsProvider::getLpSimpleName, param.getLpSimpleName())//物流商简称
                .eq(ObjectUtil.isNotEmpty(param.getBusLpCountryCode()), TbLogisticsNewPrice::getBusLpCountryCode, param.getBusLpCountryCode())//站点
                .eq(ObjectUtil.isNotEmpty(param.getBusLspNum()), TbLogisticsNewPrice::getBusLspNum, param.getBusLspNum())//国家地区
                .eq(ObjectUtil.isNotEmpty(param.getBusLogTraMode2()), TbLogisticsNewPrice::getBusLogTraMode2, param.getBusLogTraMode2())//运输方式
                .eq(ObjectUtil.isNotEmpty(param.getBusLogSeaTraRoute()), TbLogisticsNewPrice::getBusLogSeaTraRoute, param.getBusLogSeaTraRoute());//物料渠道

        if (isExport) {
            queryWrapper.selectAll(TbLogisticsNewPriceDet.class)
                    .select("case bus_logp_is_inc_tax when 1 then '是' when 0 then '否' else ' ' end as busLogpIsIncTaxStr ")
                    .leftJoin(TbLogisticsNewPriceDet.class, TbLogisticsNewPriceDet::getPkLogpId, TbLogisticsNewPrice::getPkLogpId)
                    .ge(TbLogisticsNewPriceDet::getBusLogpDetStatus, 0);
        }
        return queryWrapper;
    }

    /**
     * 新增数据
     *
     * @param tbLogisticsNewPrice 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsNewPrice insert(TbLogisticsNewPrice tbLogisticsNewPrice) {
        tbLogisticsNewPriceMapper.insert(tbLogisticsNewPrice);
        return tbLogisticsNewPrice;
    }

    /**
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsNewPrice update(TbLogisticsNewPriceParam param) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsNewPrice> wrapper = new LambdaUpdateChainWrapper<TbLogisticsNewPrice>(tbLogisticsNewPriceMapper);
        wrapper.set(ObjectUtil.isNotEmpty(param.getSysPerCode()), TbLogisticsNewPrice::getSysPerCode, param.getSysPerCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getSysPerName()), TbLogisticsNewPrice::getSysPerName, param.getSysPerName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getSysUpdPerCode()), TbLogisticsNewPrice::getSysUpdPerCode, param.getSysUpdPerCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getSysUpdPerName()), TbLogisticsNewPrice::getSysUpdPerName, param.getSysUpdPerName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBusLcCode()), TbLogisticsNewPrice::getBusLcCode, param.getBusLcCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBusLpCountryCode()), TbLogisticsNewPrice::getBusLpCountryCode, param.getBusLpCountryCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBusLspNum()), TbLogisticsNewPrice::getBusLspNum, param.getBusLspNum());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogTraMode1()), TbLogisticsNewPrice::getBusLogTraMode1, param.getBusLogTraMode1());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogTraMode2()), TbLogisticsNewPrice::getBusLogTraMode2, param.getBusLogTraMode2());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogSeaTraRoute()), TbLogisticsNewPrice::getBusLogSeaTraRoute, param.getBusLogSeaTraRoute());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogRedOrBlueList()), TbLogisticsNewPrice::getBusLogRedOrBlueList, param.getBusLogRedOrBlueList());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogGoodCharacter()), TbLogisticsNewPrice::getBusLogGoodCharacter, param.getBusLogGoodCharacter());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogpChargCurrency()), TbLogisticsNewPrice::getBusLogpChargCurrency, param.getBusLogpChargCurrency());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogpChargType()), TbLogisticsNewPrice::getBusLogpChargType, param.getBusLogpChargType());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsNewPrice::getPkLogpId, param.getPkLogpId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if (ret) {
            return queryById(param.getPkLogpId());
        } else {
            return null;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param pkLogpId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal pkLogpId) {
        int total = tbLogisticsNewPriceMapper.deleteById(pkLogpId);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param pkLogpIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> pkLogpIdList) {
        int delCount = tbLogisticsNewPriceMapper.deleteBatchIds(pkLogpIdList);
        if (pkLogpIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 物流价格导出
     *
     * @param param
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public List<TbLogisticsNewPriceExportResult> export(TbLogisticsNewPriceParam param) {
        //1. 构建动态查询条件
        MPJLambdaWrapper<TbLogisticsNewPrice> queryWrapper = getQueryWrapper(param, true);
        return tbLogisticsNewPriceMapper.selectJoinList(TbLogisticsNewPriceExportResult.class, queryWrapper);
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData importExcel(MultipartFile file) {
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<TbLogisticsNewPriceParam>();
            EasyExcel.read(buffer, TbLogisticsNewPriceParam.class, listener).sheet().doRead();

            List<TbLogisticsNewPriceParam> dataList = listener.getDataList();
            log.info("物流价格导入[{}]", JSONObject.toJSON(dataList));
            long start = System.currentTimeMillis();
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据为空，导入失败！");
            }
            //异常数据
            List<TbLogisticsNewPriceParam> errorList = new ArrayList<>();
            this.validExcel(dataList, errorList);

            //异常数据输出异常Excel文件流
            if(CollectionUtil.isNotEmpty(errorList)){
                String fileName = this.dealImportErrorList(errorList);
                log.info("物流价格导入结束，耗时------------------->" + (System.currentTimeMillis() - start) + "ms");
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "存在导入失败数据！", fileName);
            } else {
                LoginUser user = LoginContext.me().getLoginUser();
                Date nowDate = DateUtil.date();
                for (TbLogisticsNewPriceParam priceParam : dataList) {
                    LambdaQueryWrapper<TbLogisticsNewPrice> priceQw = new LambdaQueryWrapper<>();
                    priceQw.eq(TbLogisticsNewPrice::getBusLcCode, priceParam.getBusLcCode())
                            .eq(TbLogisticsNewPrice::getBusLpCountryCode, priceParam.getBusLpCountryCode())
                            .eq(TbLogisticsNewPrice::getBusLspNum, priceParam.getBusLspNum())
                            .eq(TbLogisticsNewPrice::getBusLogTraMode2, priceParam.getBusLogTraMode2())
                            .eq(TbLogisticsNewPrice::getBusLogSeaTraRoute, priceParam.getBusLogSeaTraRoute())
                            .eq(TbLogisticsNewPrice::getBusLogpIsIncTax, priceParam.getBusLogpIsIncTax())
                            .eq(TbLogisticsNewPrice::getBusLogpChargCurrency, priceParam.getBusLogpChargCurrency())
                            .eq(TbLogisticsNewPrice::getBusLogpChargType, priceParam.getBusLogpChargType());
                    if(ObjectUtil.isEmpty(priceParam.getBusLogRedOrBlueList())){
                        priceQw.isNull(TbLogisticsNewPrice::getBusLogRedOrBlueList);
                    }else{
                        priceQw.eq(TbLogisticsNewPrice::getBusLogRedOrBlueList, priceParam.getBusLogRedOrBlueList());
                    }
                    if(ObjectUtil.isEmpty(priceParam.getBusLogGoodCharacter())){
                        priceQw.isNull(TbLogisticsNewPrice::getBusLogGoodCharacter);
                    }else{
                        priceQw.eq(ObjectUtil.isEmpty(priceParam.getBusLogGoodCharacter()), TbLogisticsNewPrice::getBusLogGoodCharacter, priceParam.getBusLogGoodCharacter());
                    }
                    TbLogisticsNewPrice priceResult = this.getOne(priceQw);
                    if (priceResult == null) {
                        //物流价格没数据则新增
                        priceResult = new TbLogisticsNewPrice();
                        BeanUtils.copyProperties(priceParam, priceResult);
                        priceResult.setSysCreateDate(nowDate);
                        priceResult.setSysPerCode(user.getAccount());
                        priceResult.setSysPerName(user.getName());
                        this.save(priceResult);
                    }

                    TbLogisticsNewPriceDet priceDet = new TbLogisticsNewPriceDet();
                    BeanUtils.copyProperties(priceParam, priceDet);
                    priceDet.setPkLogpId(priceResult.getPkLogpId());
                    priceDet.setSysAddDate(nowDate);
                    priceDet.setSysPerCode(user.getAccount());
                    priceDet.setSysPerName(user.getName());
                    priceDet.setBusLogpDetStatus(1);
                    priceDet.setBusLogpDetUseStatus("已禁用");
                    detService.save(priceDet);
                }
                log.info("物流价格导入结束，耗时------------------->" + (System.currentTimeMillis() - start) + "ms");
                return ResponseData.success();
            }
        } catch (Exception e) {
            log.error("物流价格导入处理异常，导入失败！", e);
            return ResponseData.error("物流价格导入处理异常，导入失败！");
        } finally {
            if(buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("物流价格导入关闭流异常", e);
                }
            }
        }
    }

    /**
     * 导入Excel校验
     * @param dataList 导入数据
     * @param errorList 导入校验异常数据
     */
    private void validExcel(List<TbLogisticsNewPriceParam> dataList, List<TbLogisticsNewPriceParam> errorList){
        for (TbLogisticsNewPriceParam priceParam : dataList) {
            priceParam.setUploadRemark("");
            StringBuilder uploadRemarkSb = new StringBuilder();
            if (ObjectUtil.isEmpty(priceParam.getBusLcCode())
                    || ObjectUtil.isEmpty(priceParam.getBusLpCountryCode())
                    || ObjectUtil.isEmpty(priceParam.getBusLspNum())
                    || ObjectUtil.isEmpty(priceParam.getBusLogTraMode2())
                    || ObjectUtil.isEmpty(priceParam.getBusLogSeaTraRoute())
                    || ObjectUtil.isEmpty(priceParam.getBusLogpIsIncTaxDesc())
                    || ObjectUtil.isEmpty(priceParam.getBusLogpChargCurrency())
                    || ObjectUtil.isEmpty(priceParam.getBusLogpChargType())
                    || ObjectUtil.isEmpty(priceParam.getBusLogpDetUnitPrice())
                    || ObjectUtil.isEmpty(priceParam.getBusLogpDetAppStartDate())) {
                uploadRemarkSb.append("物流账号、站点、国家地区、发货方式、物流渠道、是否含税、计费币种、计费方式、单价费用、适用开始日期为必填项！");
            }
            //值域校验
            if("是".equals(priceParam.getBusLogpIsIncTaxDesc())){
                priceParam.setBusLogpIsIncTax(0);
            }else if("否".equals(priceParam.getBusLogpIsIncTaxDesc())){
                priceParam.setBusLogpIsIncTax(0);
            } else {
                uploadRemarkSb.append("是否含税值必须为是或者否！");
            }
            List<String> logisticsAccountList = logisticsAccountService.lcCodeList("正常");
            if(!logisticsAccountList.contains(priceParam.getBusLcCode())){
                uploadRemarkSb.append("不存在此物流账号！");
            }
            if(!"体积".equals(priceParam.getBusLogpChargType()) && !"重量".equals(priceParam.getBusLogpChargType())){
                uploadRemarkSb.append("不存在此计费方式！");
            }

            if(ObjectUtil.isEmpty(priceParam.getBusLogRedOrBlueList())){
                priceParam.setBusLogRedOrBlueList("");
            }
            if(ObjectUtil.isEmpty(priceParam.getBusLogGoodCharacter())){
                priceParam.setBusLogGoodCharacter("");
            }
            String busLogpChargType = priceParam.getBusLogpChargType();
            BigDecimal busLogpDetWeightGreater = priceParam.getBusLogpDetWeightGreater();
            BigDecimal busLogpDetWeightLess = priceParam.getBusLogpDetWeightLess();
            BigDecimal busLogpDetVolumeGreater = priceParam.getBusLogpDetVolumeGreater();
            BigDecimal busLogpDetVolumeLess = priceParam.getBusLogpDetVolumeLess();
            if (ObjectUtil.isNotEmpty(busLogpChargType)) {
                if ("重量".equals(busLogpChargType)) {
                    //校验重量值
                    if (ObjectUtil.isEmpty(busLogpDetWeightGreater) || ObjectUtil.isEmpty(busLogpDetWeightLess)) {
                        uploadRemarkSb.append("重量KG(>)、重量KG(<)为必填项！");
                    } else {
                        if (busLogpDetWeightLess.compareTo(BigDecimal.ZERO) < 0 || busLogpDetWeightGreater.compareTo(BigDecimal.ZERO) < 0) {
                            uploadRemarkSb.append("重量KG(<)、重量KG(>)值不能为负数！");
                        }
                        if (busLogpDetWeightLess.compareTo(busLogpDetWeightGreater) <= 0) {
                            uploadRemarkSb.append("重量KG(<)必须大于重量KG(>)值！");
                        }
                    }
                    //体积置为0
                    priceParam.setBusLogpDetVolumeGreater(BigDecimal.ZERO);
                    priceParam.setBusLogpDetVolumeLess(BigDecimal.ZERO);
                }
                if ("体积".equals(busLogpChargType)) {
                    //校验体积值
                    if (ObjectUtil.isEmpty(busLogpDetVolumeGreater) || ObjectUtil.isEmpty(busLogpDetVolumeLess)) {
                        uploadRemarkSb.append("体积CBM(>)、体积CBM(<)为必填项！");
                    } else {
                        if (busLogpDetVolumeLess.compareTo(BigDecimal.ZERO) < 0 || busLogpDetVolumeGreater.compareTo(BigDecimal.ZERO) < 0) {
                            uploadRemarkSb.append("体积CBM(<)、体积CBM(>)值不能为负数！");
                        }
                        if (busLogpDetVolumeLess.compareTo(busLogpDetVolumeGreater) <= 0) {
                            uploadRemarkSb.append("体积CBM(<)必须大于体积CBM(>)值！");
                        }
                    }
                    //重量置为0
                    priceParam.setBusLogpDetWeightGreater(BigDecimal.ZERO);
                    priceParam.setBusLogpDetWeightLess(BigDecimal.ZERO);
                }
            }

            if (ObjectUtil.isNotEmpty(uploadRemarkSb.toString())) {
                priceParam.setUploadRemark(uploadRemarkSb.toString());
                errorList.add(priceParam);
                continue;
            }
        }

        if(CollectionUtil.isEmpty(errorList)){
            //校验物流价格明细导入范围是否重复，主数据（物流账号 + 站点 + 分区号 + 货运方式1 + 运输方式 + 物流渠道 + 红蓝单 + 货物特性 + 是否含税 + 计费币种 + 计费方式）
            Map<String, List<TbLogisticsNewPriceParam>> weightPriceParamMap = dataList.stream().filter(item -> "重量".equals(item.getBusLogpChargType())).collect(Collectors.groupingBy(
                p -> p.getBusLcCode()
                + "-" + p.getBusLpCountryCode()
                + "-" + p.getBusLspNum()
                + "-" + p.getBusLogTraMode2()
                + "-" + p.getBusLogSeaTraRoute()
                + "-" + p.getBusLogRedOrBlueList()
                + "-" + p.getBusLogGoodCharacter()
                + "-" + p.getBusLogpIsIncTax()
                + "-" + p.getBusLogpChargCurrency()
                + "-" + p.getBusLogpChargType()
            ));
            Map<String, String> validWeightParamResultMap = validImportParamRepeat("重量", weightPriceParamMap);
            Map<String, List<TbLogisticsNewPriceParam>> volumePriceParamMap = dataList.stream().filter(item -> "体积".equals(item.getBusLogpChargType())).collect(Collectors.groupingBy(
                p -> p.getBusLcCode()
                + "-" + p.getBusLpCountryCode()
                + "-" + p.getBusLspNum()
                + "-" + p.getBusLogTraMode2()
                + "-" + p.getBusLogSeaTraRoute()
                + "-" + p.getBusLogRedOrBlueList()
                + "-" + p.getBusLogGoodCharacter()
                + "-" + p.getBusLogpIsIncTax()
                + "-" + p.getBusLogpChargCurrency()
                + "-" + p.getBusLogpChargType()
            ));
            Map<String, String> validVolumeParamResultMap = validImportParamRepeat("体积", volumePriceParamMap);
            //汇总导入数据重复校验结果
            validVolumeParamResultMap.putAll(validWeightParamResultMap);
            for (TbLogisticsNewPriceParam p : dataList) {
                String key = p.getBusLcCode()
                        + "-" + p.getBusLpCountryCode()
                        + "-" + p.getBusLspNum()
                        + "-" + p.getBusLogTraMode2()
                        + "-" + p.getBusLogSeaTraRoute()
                        + "-" + p.getBusLogRedOrBlueList()
                        + "-" + p.getBusLogGoodCharacter()
                        + "-" + p.getBusLogpIsIncTax()
                        + "-" + p.getBusLogpChargCurrency()
                        + "-" + p.getBusLogpChargType()
                        + "-" + p.getBusLogpDetWeightGreater()
                        + "-" + p.getBusLogpDetWeightLess()
                        + "-" + p.getBusLogpDetVolumeGreater()
                        + "-" + p.getBusLogpDetVolumeLess();
                String uploadRemark = validVolumeParamResultMap.get(key);
                if(StringUtils.isNotEmpty(uploadRemark)){
                    p.setUploadRemark(uploadRemark);
                    errorList.add(p);
                }
            }
        }

        if(CollectionUtil.isEmpty(errorList)){
            for (TbLogisticsNewPriceParam param : dataList) {
                param.setOlnyKey(
                    param.getBusLcCode()
                    + "-" + param.getBusLpCountryCode()
                    + "-" + param.getBusLspNum()
                    + "-" + param.getBusLogTraMode2()
                    + "-" + param.getBusLogSeaTraRoute()
                    + "-" + param.getBusLogRedOrBlueList()
                    + "-" + param.getBusLogGoodCharacter()
                    + "-" + param.getBusLogpIsIncTax()
                    + "-" + param.getBusLogpChargCurrency()
                    + "-" + param.getBusLogpChargType()
                );
            }

            //校验入参数据范围是否和表数据范围重叠
            Map<String, List<TbLogisticsNewPriceParam>> priceParamMap = dataList.stream().collect(Collectors.groupingBy(TbLogisticsNewPriceParam::getOlnyKey));
            List<TbLogisticsNewPriceParam> priceResultList = tbLogisticsNewPriceMapper.queryByPriceOnlyKey(priceParamMap.keySet());
            Map<String, List<TbLogisticsNewPriceParam>> dbPriceMap = new HashMap<>();
            if(CollectionUtil.isNotEmpty(priceResultList)){
                dbPriceMap = priceResultList.stream().collect(Collectors.groupingBy(TbLogisticsNewPriceParam :: getOlnyKey));
            }

            //根据入参去匹配
            paramFor:
            for (TbLogisticsNewPriceParam param : dataList) {
                //库里存在的数据，比较值域
                List<TbLogisticsNewPriceParam> dbList = dbPriceMap.get(param.getOlnyKey());
                if(CollectionUtil.isNotEmpty(dbList)){
                    String uploadRemark = "";
                    dbFor:
                    for (TbLogisticsNewPriceParam dbResult : dbList) {
                        String repeatMsg = this.validImportParamDbRepeat(param, dbResult);
                        if(StringUtils.isNotEmpty(repeatMsg)){
                            uploadRemark = repeatMsg;
                            break dbFor;
                        }
                    }
                    if(StringUtils.isNotEmpty(uploadRemark)){
                        param.setUploadRemark(uploadRemark);
                        errorList.add(param);
                    }
                }
            }
        }
    }

    /**
     * 校验导入数据重复
     * @param type
     * @param priceParamMap
     * @return
     */
    Map<String, String> validImportParamRepeat(String type, Map<String, List<TbLogisticsNewPriceParam>> priceParamMap){
        Map<String, String> validParamResultMap = new HashMap<>();
        for (Map.Entry<String, List<TbLogisticsNewPriceParam>> entry : priceParamMap.entrySet()) {
            String priceKey = entry.getKey();
            List<TbLogisticsNewPriceParam> priceList = entry.getValue();
            //重量
            if("重量".equals(type)){
                Collections.sort(priceList, Comparator.comparingDouble(a -> a.getBusLogpDetWeightGreater().doubleValue()));
                Double max = priceList.get(0).getBusLogpDetWeightLess().doubleValue();
                Double weightMin = priceList.get(0).getBusLogpDetWeightGreater().doubleValue();
                Double weightMax = priceList.get(0).getBusLogpDetWeightLess().doubleValue();
                Double volumeMin = priceList.get(0).getBusLogpDetVolumeGreater().doubleValue();
                Double volumeMax = priceList.get(0).getBusLogpDetVolumeLess().doubleValue();
                for (int i = 1; i < priceList.size(); i++) {
                    TbLogisticsNewPriceParam price = priceList.get(i);
                    if (price.getBusLogpDetWeightGreater().doubleValue() < weightMax) {
                        max = Math.max(weightMax, price.getBusLogpDetWeightLess().doubleValue());
                        //有重叠的部分，记录重复数据(上一条和当前这一条)
                        validParamResultMap.put(
                                priceKey+"_"+weightMin +"_"+weightMax+"_"+volumeMin +"_"+volumeMax,
                                "导入文件数据重量存在值域重叠！");
                        validParamResultMap.put(
                                priceKey+"_"+price.getBusLogpDetWeightGreater() +"_"+price.getBusLogpDetWeightLess()+"_"+price.getBusLogpDetVolumeGreater() +"_"+price.getBusLogpDetVolumeLess(),
                                "导入文件数据重量存在值域重叠！");
                    } else {
                        //没有重叠, 添加当前区间到结果里面
                        max = price.getBusLogpDetWeightLess().doubleValue();
                        weightMin = price.getBusLogpDetWeightGreater().doubleValue();
                        weightMax = price.getBusLogpDetWeightLess().doubleValue();
                        volumeMin = price.getBusLogpDetVolumeGreater().doubleValue();
                        volumeMax = price.getBusLogpDetVolumeLess().doubleValue();
                    }
                }
            }

            //体积
            if("体积".equals(type)){
                Collections.sort(priceList, Comparator.comparingDouble(a -> a.getBusLogpDetVolumeGreater().doubleValue()));
                Double max = priceList.get(0).getBusLogpDetVolumeLess().doubleValue();
                Double weightMin = priceList.get(0).getBusLogpDetWeightGreater().doubleValue();
                Double weightMax = priceList.get(0).getBusLogpDetWeightLess().doubleValue();
                Double volumeMin = priceList.get(0).getBusLogpDetVolumeGreater().doubleValue();
                Double volumeMax = priceList.get(0).getBusLogpDetVolumeLess().doubleValue();
                for (int i = 1; i < priceList.size(); i++) {
                    TbLogisticsNewPriceParam price = priceList.get(i);
                    if (price.getBusLogpDetVolumeGreater().doubleValue() < volumeMax) {
                        max = Math.max(volumeMax, price.getBusLogpDetVolumeLess().doubleValue());
                        //有重叠的部分，记录重复数据(上一条和当前这一条)
                        validParamResultMap.put(
                                priceKey+"_"+weightMin +"_"+weightMax+"_"+volumeMin +"_"+volumeMax,
                                "导入文件数据重量存在值域重叠！");
                        validParamResultMap.put(
                                priceKey+"_"+price.getBusLogpDetWeightGreater() +"_"+price.getBusLogpDetWeightLess()+"_"+price.getBusLogpDetVolumeGreater() +"_"+price.getBusLogpDetVolumeLess(),
                                "导入文件数据体积存在值域重叠！");
                    } else {
                        //没有重叠, 添加当前区间到结果里面
                        max = price.getBusLogpDetVolumeLess().doubleValue();
                        weightMin = price.getBusLogpDetWeightGreater().doubleValue();
                        weightMax = price.getBusLogpDetWeightLess().doubleValue();
                        volumeMin = price.getBusLogpDetVolumeGreater().doubleValue();
                        volumeMax = price.getBusLogpDetVolumeLess().doubleValue();
                    }
                }
            }
        }
        return validParamResultMap;
    }

    /**
     * 校验导入数据和数据库数据重复
     * @param param
     * @param dbResult
     * @return
     */
    String validImportParamDbRepeat(TbLogisticsNewPriceParam param, TbLogisticsNewPriceParam dbResult){
        String repeatMsg = "";
        //重量
        if("重量".equals(param.getBusLogpChargType()) && "重量".equals(dbResult.getBusLogpChargType())){
            if((param.getBusLogpDetWeightGreater().compareTo(dbResult.getBusLogpDetWeightGreater()) >= 0
                    && param.getBusLogpDetWeightGreater().compareTo(dbResult.getBusLogpDetWeightLess()) <= 0)
                || (param.getBusLogpDetWeightLess().compareTo(dbResult.getBusLogpDetWeightGreater()) >= 0
                    && param.getBusLogpDetWeightLess().compareTo(dbResult.getBusLogpDetWeightLess()) <= 0
            )){
                repeatMsg = "导入文件数据重量于已存在的数据值域重叠！";
            }
        }

        //体积
        if("体积".equals(param.getBusLogpChargType()) && "体积".equals(dbResult.getBusLogpChargType())){
            if((param.getBusLogpDetVolumeGreater().compareTo(dbResult.getBusLogpDetVolumeGreater()) >= 0
                    && param.getBusLogpDetVolumeGreater().compareTo(dbResult.getBusLogpDetVolumeLess()) <= 0)
                    || (param.getBusLogpDetVolumeLess().compareTo(dbResult.getBusLogpDetVolumeGreater()) >= 0
                    && param.getBusLogpDetVolumeLess().compareTo(dbResult.getBusLogpDetVolumeLess()) <= 0
            )){
                repeatMsg = "导入文件数据重量于已存在的数据值域重叠！";
            }
        }
        return repeatMsg;
    }

    /**
     * 校验错误文件流输出
     * @param errorList
     * @return
     */
    private String dealImportErrorList(List<TbLogisticsNewPriceParam> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, TbLogisticsNewPriceParam.class)
                    .sheet("导入异常数据").doWrite(errorList);
        } catch (FileNotFoundException e) {
            log.error("物流价格导入异常数据导出异常", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("物流价格导入异常数据导出流关闭异常", e);
                }
            }
        }
        return fileName;
    }

    /**
     * 批量添加-物流商的价格信息
     *
     * @param newPriceList
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public ResponseData batchAdd(List<TbLogisticsNewPriceExportResult> newPriceList) {
        if (ObjectUtil.isEmpty(newPriceList)) {
            return ResponseData.error("提交数据为空");
        }
        // 提交数据重复校验 主数据（物流账号 + 站点 + 分区号 + 货运方式1 + 运输方式 + 物流渠道 + 红蓝单 + 货物特性 + 是否含税 + 计费币种 + 计费方式）
        Map<String, List<TbLogisticsNewPriceExportResult>> priceMap = newPriceList.stream().collect(
                Collectors.groupingBy(
                        p -> p.getBusLcCode()
                                + "-" + p.getBusLpCountryCode()
                                + "-" + p.getBusLspNum()
                                + "-" + p.getBusLogTraMode2()
                                + "-" + p.getBusLogSeaTraRoute()
                                + "-" + p.getBusLogRedOrBlueList()
                                + "-" + p.getBusLogGoodCharacter()
                                + "-" + p.getBusLogpIsIncTax()
                                + "-" + p.getBusLogpChargCurrency()
                                + "-" + p.getBusLogpChargType()
                ));


        //查询数据库是否已经有同维度的报价
        Set<String> priceOnlyKeySet = priceMap.keySet();
        List<TbLogisticsNewPriceExportResult> priceList = null;//tbLogisticsNewPriceMapper.queryByPriceOnlyKey(priceOnlyKeySet);
        Map<String, List<TbLogisticsNewPriceExportResult>> havePriceMap = new HashMap<>();

        if (ObjectUtil.isNotEmpty(priceList)) {
            havePriceMap = priceList.stream().collect(Collectors.groupingBy(TbLogisticsNewPriceExportResult::getOlnyKey));
        }

        //有相同维度的报价，比较启用或准备中的报价明细 是否和本次新增的报价明细有重量重叠的数据 +以及本次提交的数据是否有重叠的
        List<TbLogisticsNewPriceExportResult> overlapResult = new ArrayList<>();
        List<String> overlapResultKey = new ArrayList<>();
        Map<String, List<TbLogisticsNewPriceExportResult>> finalHavePriceMap = havePriceMap;
        priceMap.forEach((priceKey, priceValueList) -> {
            if (! finalHavePriceMap.isEmpty()) {
                // 把数据库中的数据 一起添加到list进行区间重叠检查
                priceValueList.addAll(finalHavePriceMap.get(priceKey));
            }
            List<TbLogisticsNewPriceExportResult> results = this.findOverlappingIntervals(priceValueList);
            if (ObjectUtil.isNotEmpty(results)) {
                overlapResult.addAll(results);
                overlapResultKey.add(priceKey);
            }
        });
        if (ObjectUtil.isNotEmpty(overlapResultKey)) {
            return ResponseData.error("以下数据重量区间有重复区域：【" + overlapResultKey.toString() + "】重量有重复区间");
        }

        LoginUser loginUser = LoginContext.me().getLoginUser();

        List<TbLogisticsNewPriceDet> needAddPriceDetList = new ArrayList<>();

        for (Map.Entry<String, List<TbLogisticsNewPriceExportResult>> entry : priceMap.entrySet()) {
            String key = entry.getKey();
            List<TbLogisticsNewPriceExportResult> addPriceList = entry.getValue();

            if (ObjectUtil.isNotEmpty(havePriceMap) && havePriceMap.containsKey(key)) {
                //已经存在价格，直接添加价格明细
                List<TbLogisticsNewPriceExportResult> dbPriceList = havePriceMap.get(key);
                if (ObjectUtil.isNotEmpty(dbPriceList) && ObjectUtil.isNotNull(dbPriceList.get(0).getPkLogpId())) {
                    needAddPriceDetList.addAll(getPriceDetList(addPriceList, dbPriceList.get(0).getPkLogpId(), loginUser));
                    continue;
                }
            }
            //不存在价格 则 新增价格和价格明细
            TbLogisticsNewPrice price = new TbLogisticsNewPrice();
            TbLogisticsNewPriceExportResult newPriceExportResult = addPriceList.get(0);
            BeanUtil.copyProperties(newPriceExportResult, price);
            price.setSysCreateDate(new Date());
            price.setSysPerCode(loginUser.getAccount());
            price.setSysPerName(loginUser.getName());
            //增加价格
            price = this.insert(price);

            needAddPriceDetList.addAll(getPriceDetList(addPriceList, price.getPkLogpId(), loginUser));

        }
        if (ObjectUtil.isNotEmpty(needAddPriceDetList)) {
            //增加价格明细
            detService.saveBatch(needAddPriceDetList);
        }

        return ResponseData.success();
    }



    private static List<TbLogisticsNewPriceDet> getPriceDetList(List<TbLogisticsNewPriceExportResult> addPriceList, BigDecimal logpId, LoginUser loginUser) {
        List<TbLogisticsNewPriceDet> resultList = new ArrayList<>();
        for (TbLogisticsNewPriceExportResult price : addPriceList) {
            TbLogisticsNewPriceDet priceDet = new TbLogisticsNewPriceDet();
            BeanUtil.copyProperties(price, priceDet);
            priceDet.setPkLogpId(logpId);
            priceDet.setBusLogpDetStatus(0);
            priceDet.setSysAddDate(new Date());
            priceDet.setSysPerCode(loginUser.getAccount());
            priceDet.setSysPerName(loginUser.getName());
            resultList.add(priceDet);
        }
        return resultList;
    }


    static List<TbLogisticsNewPriceExportResult> findOverlappingIntervals(List<TbLogisticsNewPriceExportResult> intervals) {
        List<TbLogisticsNewPriceExportResult> result = new ArrayList<>();
        if (intervals.size() < 2) {
            return result;
        }
        Collections.sort(intervals, Comparator.comparingDouble(a -> a.getBusLogpDetWeightGreater().doubleValue()));
        Double start = intervals.get(0).getBusLogpDetWeightGreater().doubleValue();
        Double end = intervals.get(0).getBusLogpDetWeightLess().doubleValue();
        for (int i = 1; i < intervals.size(); i++) {
            TbLogisticsNewPriceExportResult current = intervals.get(i);

            if (current.getBusLogpDetWeightGreater().doubleValue() > current.getBusLogpDetWeightLess().doubleValue()) {
                result.add(current);
                continue;
            }

            if (current.getBusLogpDetWeightGreater().doubleValue() < end) { // 有重叠的部分
                end = Math.max(end, current.getBusLogpDetWeightLess().doubleValue());
                result.add(current);
            } else { // 没有重叠, 添加当前区间到结果里面
                start = current.getBusLogpDetWeightGreater().doubleValue();
                end = current.getBusLogpDetWeightLess().doubleValue();
            }
        }
        return result;
    }

    @Override
    @DataSource(name = "logistics")
    public List<TbLogisticsNewPriceDetResult> getTbLogisticsNewPriceDet(TbLogisticsListToHeadRoute model, Date lhrSendGoodDate, String logGoodCharacter, String lerPreChargType) {

        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
        LocalDate lhrSendGoodDateLocalDate = lhrSendGoodDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        MPJLambdaWrapper<TbLogisticsNewPriceDet> lambdaWrapper =  MPJWrappers.<TbLogisticsNewPriceDet>lambdaJoin();
        lambdaWrapper.selectAll(TbLogisticsNewPriceDet.class)
                .select(TbLogisticsNewPrice::getBusLogpChargCurrency)
        .join("JOIN", TbLogisticsNewPrice.class, on -> on.eq(TbLogisticsNewPriceDet::getPkLogpId, TbLogisticsNewPrice::getPkLogpId))
                .eq(ObjectUtil.isNotEmpty(model.getLcCode()),TbLogisticsNewPrice::getBusLcCode,model.getLcCode())//物流账号
                .eq(ObjectUtil.isNotEmpty(model.getCountryCode()),TbLogisticsNewPrice::getBusLpCountryCode,model.getCountryCode())//站点
                .eq(ObjectUtil.isNotEmpty(model.getLspNum()),TbLogisticsNewPrice::getBusLspNum,model.getLspNum())//国家分区
                .eq(ObjectUtil.isNotEmpty(model.getLogTraMode1()),TbLogisticsNewPrice::getBusLogTraMode1,model.getLogTraMode1())//货运方式1
                .eq(ObjectUtil.isNotEmpty(model.getLogTraMode2()),TbLogisticsNewPrice::getBusLogTraMode2,model.getLogTraMode2())//运输方式
                .eq(ObjectUtil.isNotEmpty(model.getLogSeaTraRoute()),TbLogisticsNewPrice::getBusLogSeaTraRoute,model.getLogSeaTraRoute())//物流渠道
                .eq(ObjectUtil.isNotEmpty(model.getLogRedOrBlueList()),TbLogisticsNewPrice::getBusLogRedOrBlueList,model.getLogRedOrBlueList())//红蓝单
                .eq(ObjectUtil.isNotEmpty(model.getLogpIsIncTax()),TbLogisticsNewPrice::getBusLogpIsIncTax,model.getLogpIsIncTax())//是否含税
                .eq(ObjectUtil.isNotEmpty(logGoodCharacter),TbLogisticsNewPrice::getBusLogGoodCharacter,logGoodCharacter) //货物特性
                .eq(ObjectUtil.isNotEmpty(lerPreChargType),TbLogisticsNewPrice::getBusLogpChargType,lerPreChargType)
                .ge(TbLogisticsNewPriceDet::getBusLogpDetAppStartDate,lhrSendGoodDateLocalDate)//适用开始日期--大于等于开始日期
                .and(i->i.le(TbLogisticsNewPriceDet::getBusLogpDetAppEndDate, lhrSendGoodDateLocalDate).or().isNull(TbLogisticsNewPriceDet::getBusLogpDetAppEndDate)); //适用结束日期--小于等于截止日期或者没有截止日期


       return tbLogisticsNewPriceDetMapper.selectJoinList(TbLogisticsNewPriceDetResult.class, lambdaWrapper);

    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData busLspNumList(String busLpCountryCode) {
        LambdaQueryWrapper<TbLogisticsNewPrice> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(TbLogisticsNewPrice::getBusLspNum);
        wrapper.eq(ObjectUtil.isNotEmpty(busLpCountryCode), TbLogisticsNewPrice::getBusLpCountryCode, busLpCountryCode);
        // 查询单个字段
        List<String> busLspNumList = this.listObjs(wrapper, Object::toString);
        if (ObjectUtil.isNotEmpty(busLspNumList)) {
            busLspNumList= busLspNumList.stream().distinct().collect(Collectors.toList());
        }
      return   ResponseData.success(busLspNumList);
    }
}