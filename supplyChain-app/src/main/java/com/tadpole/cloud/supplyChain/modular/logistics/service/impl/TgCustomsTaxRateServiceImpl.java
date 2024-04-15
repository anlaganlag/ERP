package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCountryInfo;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsTaxRate;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsTaxRateParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomsTaxRateResult;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.TgCustomsTaxRateMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgCountryInfoService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgCustomsTaxRateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 清关税率 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@Slf4j
@Service
public class TgCustomsTaxRateServiceImpl extends ServiceImpl<TgCustomsTaxRateMapper, TgCustomsTaxRate> implements ITgCustomsTaxRateService {

    @Resource
    private TgCustomsTaxRateMapper mapper;
    @Autowired
    private ITgCountryInfoService countryInfoService;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(TgCustomsTaxRateParam param) {
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData add(TgCustomsTaxRateParam param) {
        ResponseData validateResp = this.validateTgCustomsTaxRate(param);
        if(ResponseData.DEFAULT_ERROR_CODE.equals(validateResp.getCode())){
            return validateResp;
        }

        LambdaQueryWrapper<TgCustomsTaxRate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TgCustomsTaxRate :: getCountryCode, param.getCountryCode())
                .eq(TgCustomsTaxRate :: getHsCode, param.getHsCode());
        if(this.count(queryWrapper) > 0){
            return ResponseData.error("已存在此国家和HSCode信息，新增失败！");
        }

        String name = LoginContext.me().getLoginUser().getName();
        TgCustomsTaxRate insertEntity = new TgCustomsTaxRate();
        BeanUtils.copyProperties(param, insertEntity);
        insertEntity.setCreateUser(name);
        this.save(insertEntity);
        return ResponseData.success();
    }

    /**
     * 校验清关税率入参
     * @param param
     * @return
     */
    ResponseData validateTgCustomsTaxRate(TgCustomsTaxRateParam param){
        if(StringUtils.isAnyBlank(
                param.getCountryCode(),
                param.getCountryName(),
                param.getHsCode())
                || param.getChangeTaxRate() == null
                || param.getTaxRate() == null
                || param.getAddTaxRate() == null){
            return ResponseData.error("所有都为必填项！");
        }
        if(BigDecimal.ZERO.compareTo(param.getChangeTaxRate()) > 0
                || BigDecimal.ZERO.compareTo(param.getTaxRate()) > 0
                || BigDecimal.ZERO.compareTo(param.getAddTaxRate()) > 0){
            return ResponseData.error("税率不能为负数！");
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData delete(TgCustomsTaxRateParam param) {
        return this.removeById(param.getId()) ? ResponseData.success() : ResponseData.error("删除失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData edit(TgCustomsTaxRateParam param) {
        ResponseData validateResp = this.validateTgCustomsTaxRate(param);
        if(ResponseData.DEFAULT_ERROR_CODE.equals(validateResp.getCode())){
            return validateResp;
        }

        LambdaQueryWrapper<TgCustomsTaxRate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TgCustomsTaxRate :: getCountryCode, param.getCountryCode())
                .eq(TgCustomsTaxRate :: getHsCode, param.getHsCode());
        TgCustomsTaxRate customsTaxRate = this.getOne(queryWrapper);
        if(customsTaxRate != null && customsTaxRate.getId().compareTo(param.getId()) != 0){
            return ResponseData.error("已存在此国家和HSCode信息，编辑失败！");
        }

        TgCustomsTaxRate updateEntity = new TgCustomsTaxRate();
        BeanUtils.copyProperties(param, updateEntity);
        String name = LoginContext.me().getLoginUser().getName();
        updateEntity.setUpdateUser(name);
        updateEntity.setUpdateTime(DateUtil.date());
        this.updateById(updateEntity);
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public List<TgCustomsTaxRateResult> export(TgCustomsTaxRateParam param) {
        Page pageContext = param.getPageContext();
        pageContext.setCurrent(1);
        pageContext.setSize(Integer.MAX_VALUE);
        return mapper.queryPage(pageContext, param).getRecords();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData importExcel(MultipartFile file) {
        String name = LoginContext.me().getLoginUser().getName();
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<TgCustomsTaxRateParam>();
            EasyExcel.read(buffer, TgCustomsTaxRateParam.class, listener).sheet().doRead();

            List<TgCustomsTaxRateParam> dataList = listener.getDataList();
            log.info("清关税率导入数据[{}]", JSONObject.toJSON(dataList));
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据为空，导入失败！");
            }
            Set<String> repeat = new HashSet<>();
            Set<String> countryHsCodeSet = new HashSet<>();
            for (TgCustomsTaxRateParam param : dataList) {
                String repeatStr = "国家：" + param.getCountryName() + "，HSCode：" + param.getHsCode();
                if(CollectionUtil.isNotEmpty(countryHsCodeSet) && countryHsCodeSet.contains(repeatStr)){
                    repeat.add(repeatStr);
                } else {
                    countryHsCodeSet.add(repeatStr);
                }
            }
            if(CollectionUtil.isNotEmpty(repeat)){
                return ResponseData.error("导入数据国家、HSCode重复，" + JSONObject.toJSON(repeat) + "，导入失败！");
            }

            List<TgCustomsTaxRateParam> errorList = new ArrayList<>();
            this.validExcel(dataList, errorList);

            //正常数据处理
            if(CollectionUtil.isNotEmpty(dataList)){
                List<TgCustomsTaxRate> saveList = new ArrayList<>();
                for (TgCustomsTaxRateParam successParam : dataList) {
                    LambdaQueryWrapper<TgCustomsTaxRate> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(TgCustomsTaxRate :: getCountryCode, successParam.getCountryCode())
                            .eq(TgCustomsTaxRate :: getHsCode, successParam.getHsCode());
                    TgCustomsTaxRate tgCustomsTaxRate = this.getOne(queryWrapper);
                    if(tgCustomsTaxRate == null){
                        tgCustomsTaxRate = new TgCustomsTaxRate();
                        tgCustomsTaxRate.setCreateUser(name);
                        tgCustomsTaxRate.setCountryCode(successParam.getCountryCode());
                        tgCustomsTaxRate.setCountryName(successParam.getCountryName());
                        tgCustomsTaxRate.setHsCode(successParam.getHsCode());
                    } else {
                        tgCustomsTaxRate.setUpdateUser(name);
                        tgCustomsTaxRate.setUpdateTime(DateUtil.date());
                    }
                    tgCustomsTaxRate.setChangeTaxRate(successParam.getChangeTaxRate());
                    tgCustomsTaxRate.setTaxRate(successParam.getTaxRate());
                    tgCustomsTaxRate.setAddTaxRate(successParam.getAddTaxRate());
                    saveList.add(tgCustomsTaxRate);
                }
                this.saveOrUpdateBatch(saveList);
            }

            //异常数据输出异常Excel文件流
            if(CollectionUtil.isNotEmpty(errorList)){
                String fileName = this.dealImportErrorList(errorList);
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "存在导入失败数据！", fileName);
            } else {
                return ResponseData.success();
            }
        } catch (Exception e) {
            log.error("导入Excel处理异常，导入失败！", e);
            return ResponseData.error("导入Excel处理异常，导入失败！");
        } finally {
            if(buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入Excel关闭流异常", e);
                }
            }
        }
    }

    /**
     * 导入Excel校验
     * @param dataList
     * @param errorList
     */
    private void validExcel(List<TgCustomsTaxRateParam> dataList, List<TgCustomsTaxRateParam> errorList){
        Iterator<TgCustomsTaxRateParam> iterator = dataList.listIterator();
        List<TgCountryInfo> tgCountryInfoList = countryInfoService.list();
        List<String> nameList = new ArrayList<>();
        for (TgCountryInfo countryInfo : tgCountryInfoList) {
            nameList.add(countryInfo.getCountryNameCn());
        }
        while(iterator.hasNext()) {
            TgCustomsTaxRateParam param = iterator.next();
            param.setUploadRemark(null);
            if(StringUtils.isBlank(param.getCountryName()) || !nameList.contains(param.getCountryName().trim())){
                param.setUploadRemark("未查询到此国家！");
                //添加异常数据
                errorList.add(param);
                //移除异常数据
                iterator.remove();
                continue;
            }
            for (TgCountryInfo countryInfo : tgCountryInfoList) {
                if(param.getCountryName().trim().equals(countryInfo.getCountryNameCn())){
                    param.setCountryName(countryInfo.getCountryNameCn());
                    param.setCountryCode(countryInfo.getCountryCode());
                }
            }

            //验证基础信息
            ResponseData validateResp = this.validateTgCustomsTaxRate(param);
            if(ResponseData.DEFAULT_ERROR_CODE.equals(validateResp.getCode())){
                param.setUploadRemark(validateResp.getMessage());
                //添加异常数据
                errorList.add(param);
                //移除异常数据
                iterator.remove();
                continue;
            }
        }
    }

    /**
     * 校验错误文件流输出
     * @param errorList
     * @return
     */
    private String dealImportErrorList(List<TgCustomsTaxRateParam> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, TgCustomsTaxRateParam.class)
                    .sheet("导入异常数据").doWrite(errorList);
        } catch (FileNotFoundException e) {
            log.error("清关税率导入异常数据导出异常", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("清关税率导入异常数据导出流关闭异常", e);
                }
            }
        }
        return fileName;
    }

    @Override
    @DataSource(name = "logistics")
    public List<BaseSelectResult> targetCountrySelect() {
        return mapper.targetCountrySelect();
    }

    @Override
    @DataSource(name = "logistics")
    public List<BaseSelectResult> hsCodeSelect(String countryCode) {
        return mapper.hsCodeSelect(countryCode);
    }
}
