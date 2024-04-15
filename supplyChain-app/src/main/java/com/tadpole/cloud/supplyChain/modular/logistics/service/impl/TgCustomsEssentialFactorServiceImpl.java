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
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgBaseProduct;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsEssentialFactor;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsEssentialFactorParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgEssentialFactorValidateParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomsEssentialFactorResult;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.TgCustomsEssentialFactorMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgBaseProductService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgCustomsEssentialFactorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 报关要素 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@Slf4j
@Service
public class TgCustomsEssentialFactorServiceImpl extends ServiceImpl<TgCustomsEssentialFactorMapper, TgCustomsEssentialFactor> implements ITgCustomsEssentialFactorService {

    @Resource
    private TgCustomsEssentialFactorMapper mapper;
    @Autowired
    private ITgBaseProductService baseProductService;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(TgCustomsEssentialFactorParam param) {
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData add(TgCustomsEssentialFactorParam param) {
        String name = LoginContext.me().getLoginUser().getName();
        if(StringUtils.isAnyBlank(param.getCustomsCode(), param.getEssentialFactor())){
            return ResponseData.error("所有都为必填项！");
        }
        //判断报关要素是否包含品名
        if(!param.getEssentialFactor().contains("品名")){
            return ResponseData.error("报关要素【品名】不存在！");
        }
        LambdaQueryWrapper<TgCustomsEssentialFactor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TgCustomsEssentialFactor :: getCustomsCode, param.getCustomsCode());
        TgCustomsEssentialFactor customsEssentialFactor = mapper.selectOne(queryWrapper);
        if(customsEssentialFactor != null){
            return ResponseData.error("已存在海关编码！");
        }

        ResponseData resp = validateEssentialFactor(param);
        if(ResponseData.DEFAULT_ERROR_CODE.equals(resp.getCode())){
            return resp;
        } else {
            TgCustomsEssentialFactor saveEntity = new TgCustomsEssentialFactor();
            BeanUtils.copyProperties(param, saveEntity);
            saveEntity.setCreateUser(name);
            mapper.insert(saveEntity);
        }
        return ResponseData.success();
    }

    private ResponseData validateEssentialFactor(TgCustomsEssentialFactorParam param){
        //要素校验规则（1.要素名称 2.要素名称）
        //判断报关要素是否包含品名
        if(!param.getEssentialFactor().contains("品名")){
            return ResponseData.error("报关要素【品名】不存在！");
        }
        List<Integer> orderNoList = new ArrayList<>();//校验序号
        List<TgEssentialFactorValidateParam> essentialFactorList = new ArrayList<>();//要素集合
        String[] essentialFactorArr = param.getEssentialFactor().split("\\s+");
        for (String essentialFactor : essentialFactorArr) {
            String[] splitStr = essentialFactor.split("\\.");
            if(splitStr.length < 2){
                log.error("要素输入格式有误！要素[{}]", param.getEssentialFactor());
                return ResponseData.error("要素输入格式有误！");
            }

            String orderNo = splitStr[0];
            if(!this.isNumeric(orderNo)){
                return ResponseData.error("要素输入格式有误，序号需为正整数！");
            }
            orderNoList.add(Integer.parseInt(orderNo));
            TgEssentialFactorValidateParam essentialFactorParam = new TgEssentialFactorValidateParam();
            essentialFactorParam.setOrderNo(Integer.parseInt(orderNo));
            essentialFactorParam.setName(essentialFactor);
            essentialFactorList.add(essentialFactorParam);
        }

        //是否需要重新排序
        Boolean resetOrder = false;
        for (int i = 0; i < orderNoList.size(); i++) {
            if(orderNoList.get(i) != i+1){
                resetOrder = true;
            }
        }

        //要素重新排序
        if(resetOrder){
            List<TgEssentialFactorValidateParam> sortList = essentialFactorList.stream().sorted(Comparator.comparing(TgEssentialFactorValidateParam :: getOrderNo)).collect(Collectors.toList());
            StringBuffer essentialFactor = new StringBuffer();
            for (TgEssentialFactorValidateParam validateParam : sortList) {
                essentialFactor.append(validateParam.getName()).append(" ");
            }
            param.setEssentialFactor(essentialFactor.substring(0, essentialFactor.length()-1));
        }
        return ResponseData.success();
    }

    /**
     * 判断是否为正整数
     * @param s
     * @return
     */
    private boolean isNumeric(String s) {
        if (s != null && !"".equals(s.trim())){
            return s.matches("^[0-9]*$");
        } else {
            return false;
        }
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData delete(TgCustomsEssentialFactorParam param) {
        String name = LoginContext.me().getLoginUser().getName();
        TgCustomsEssentialFactor essentialFactor = this.getById(param.getId());
        if(essentialFactor == null){
            return ResponseData.error("报关要素不存在，删除失败！");
        }
        String customsCode = essentialFactor.getCustomsCode();
        //删除报关要素
        this.removeById(param.getId());

        //删除报关要素引用的要素数据（产品信息和清关产品合并信息），并更新审核状态为反审：2
        mapper.updateProductEssentialFactor(customsCode, name);
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData edit(TgCustomsEssentialFactorParam param) {
        log.info("报关要素编辑入参[{}]", JSONObject.toJSON(param));
        String userName = LoginContext.me().getLoginUser().getName();
        if(StringUtils.isBlank(param.getEssentialFactor())){
            return ResponseData.error("海关编码为必填项！");
        }
        //通过海关编码查询报关要素
        LambdaQueryWrapper<TgCustomsEssentialFactor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TgCustomsEssentialFactor :: getCustomsCode, param.getCustomsCode());
        TgCustomsEssentialFactor customsEssentialFactor = mapper.selectOne(queryWrapper);
        //校验海关编码是否已经存在
        if(customsEssentialFactor != null && !customsEssentialFactor.getId().equals(param.getId())){
            return ResponseData.error("已存在海关编码！");
        }
        //通过ID获取报关要素
        TgCustomsEssentialFactor tgCustomsEssentialFactor = mapper.selectById(param.getId());
        if(tgCustomsEssentialFactor == null){
            return ResponseData.error("报关要素不存在！");
        }
        //报关要素不同，则需校验要素和更新要素，及更新产品信息中的要素
        Boolean isDiff = Boolean.FALSE;
        if(!tgCustomsEssentialFactor.getEssentialFactor().equals(param.getEssentialFactor())){
            //判断报关要素是否包含品名
            if(!param.getEssentialFactor().contains("品名")){
                return ResponseData.error("报关要素【品名】不存在！");
            }
            ResponseData resp = validateEssentialFactor(param);
            if(ResponseData.DEFAULT_ERROR_CODE.equals(resp.getCode())){
                return resp;
            }
            isDiff = Boolean.TRUE;
            tgCustomsEssentialFactor.setEssentialFactor(param.getEssentialFactor());
        }
        tgCustomsEssentialFactor.setCustomsCode(param.getCustomsCode());
        tgCustomsEssentialFactor.setUpdateUser(userName);
        tgCustomsEssentialFactor.setUpdateTime(DateUtil.date());
        mapper.updateById(tgCustomsEssentialFactor);

        //报关要素中要素有改变，才需要更新报关要素引用的要素数据（产品信息和清关产品合并信息）
        if(isDiff){
            LambdaQueryWrapper<TgBaseProduct> productQuery = new LambdaQueryWrapper<>();
            productQuery.eq(TgBaseProduct :: getCustomsCode, tgCustomsEssentialFactor.getCustomsCode())
                    .isNotNull(TgBaseProduct :: getEssentialFactor);
            List<TgBaseProduct> productList = baseProductService.list(productQuery);
            List<TgBaseProduct> updateProductList = new ArrayList<>();
            for (TgBaseProduct baseProduct : productList) {
                //要素框架名称
                List<String> nameList = new ArrayList<>();
                String[] essentialFactorArr = tgCustomsEssentialFactor.getEssentialFactor().split("\\s+");
                for (String essentialFactor : essentialFactorArr) {
                    String[] nameArr = essentialFactor.split("\\.");
                    nameList.add(nameArr[1]);
                }
                //产品信息和清关产品合并信息的要素
                String[] essentialFactorParamArr = baseProduct.getEssentialFactor().split("\\|");
                //报关要素在框架内的
                StringBuffer sbIn = new StringBuffer();
                //报关要素在框架之外的
                StringBuffer sbOut = new StringBuffer();
                sbOut.append("|");
                for (String essentialFactorParam : essentialFactorParamArr) {
                    int idx = essentialFactorParam.indexOf(":");
                    if(idx == -1){
                        idx = essentialFactorParam.indexOf("：");
                    }
                    String name = essentialFactorParam.substring(0, idx);
                    String val = essentialFactorParam.substring(idx + 1);
                    //校验要素名称是否在框架范围内，在框架范围内按照框架排序，未在框架范围内的则添加到框架范围外，不参与排序，不生成报关单
                    if(nameList.contains(name)){
                        sbIn.append(name).append(":").append(val).append("|");
                    }else{
                        sbOut.append(name).append(":").append(val).append("|");
                    }
                }
                param.setEssentialFactor(sbIn.substring(0, sbIn.length()-1));
                String[] essentialFactorParamValidateArr = param.getEssentialFactor().split("\\|");

                //要素按照报关要素排序
                StringBuffer sb = new StringBuffer();
                for (String name : nameList) {
                    for (String essentialFactorParam : essentialFactorParamValidateArr) {
                        int idx = essentialFactorParam.indexOf(":");
                        if(idx == -1){
                            idx = essentialFactorParam.indexOf("：");
                        }
                        String nameParam = essentialFactorParam.substring(0, idx);
                        String valueParam = essentialFactorParam.substring(idx + 1);
                        if(name.equals(nameParam)){
                            sb.append(nameParam).append(":").append(valueParam).append("|");
                        }
                    }
                }
                TgBaseProduct updateProduct = new TgBaseProduct();
                updateProduct.setId(baseProduct.getId());
                updateProduct.setEssentialFactor(sb.substring(0, sb.length()-1));
                if(StringUtils.isBlank(sbOut.substring(1))){
                    updateProduct.setEssentialFactorTemp("");
                }else{
                    updateProduct.setEssentialFactorTemp(sbOut.substring(0, sbOut.length()-1));
                }
                updateProduct.setUpdateTime(DateUtil.date());
                updateProduct.setUpdateUser(userName);
                updateProductList.add(updateProduct);
            }
            if(CollectionUtil.isNotEmpty(updateProductList)){
                for (TgBaseProduct updateProduct : updateProductList) {
                    LambdaUpdateWrapper<TgBaseProduct> updateWrapper = new LambdaUpdateWrapper();
                    updateWrapper.eq(TgBaseProduct :: getId, updateProduct.getId());
                    baseProductService.update(updateProduct, updateWrapper);
                }
            }
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public List<TgCustomsEssentialFactorResult> export(TgCustomsEssentialFactorParam param) {
        Page pageContext = param.getPageContext();
        pageContext.setCurrent(1);
        pageContext.setSize(Integer.MAX_VALUE);
        return mapper.queryPage(pageContext, param).getRecords();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData importExcel(MultipartFile file) {
        String userName = LoginContext.me().getLoginUser().getName();
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<TgCustomsEssentialFactorParam>();
            EasyExcel.read(buffer, TgCustomsEssentialFactorParam.class, listener).sheet().doRead();

            List<TgCustomsEssentialFactorParam> dataList = listener.getDataList();
            log.info("报关要素导入数据[{}]", JSONObject.toJSON(dataList));
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据为空，导入失败！");
            }
            Set<String> repeatCustomsCode = new HashSet<>();
            Set<String> customsCodeSet = new HashSet<>();
            for (TgCustomsEssentialFactorParam param : dataList) {
                if(customsCodeSet.contains(param.getCustomsCode())){
                    repeatCustomsCode.add(param.getCustomsCode());
                } else {
                    customsCodeSet.add(param.getCustomsCode());
                }
            }
            if(CollectionUtil.isNotEmpty(repeatCustomsCode)){
                return ResponseData.error("导入数据海关编码重复，重复海关编码" + JSONObject.toJSON(repeatCustomsCode) + "，导入失败！");
            }

            List<TgCustomsEssentialFactorParam> errorList = new ArrayList<>();
            this.validExcel(dataList, errorList);

            //正常数据处理
            if(CollectionUtil.isNotEmpty(dataList)){
                //新增或保存报关要素集合
                List<TgCustomsEssentialFactor> saveList = new ArrayList<>();
                //更新产品信息报关要素集合
                List<TgCustomsEssentialFactor> updateEssentialFactorList = new ArrayList<>();
                for (TgCustomsEssentialFactorParam successParam : dataList) {
                    //报关要素不同，则需校验要素和更新要素，及更新产品信息中的要素
                    LambdaQueryWrapper<TgCustomsEssentialFactor> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(TgCustomsEssentialFactor :: getCustomsCode, successParam.getCustomsCode());
                    TgCustomsEssentialFactor customsEssentialFactor = this.getOne(queryWrapper);
                    if(customsEssentialFactor == null){
                        customsEssentialFactor = new TgCustomsEssentialFactor();
                        customsEssentialFactor.setCreateUser(userName);
                        customsEssentialFactor.setCustomsCode(successParam.getCustomsCode());
                        customsEssentialFactor.setEssentialFactor(successParam.getEssentialFactor());
                        saveList.add(customsEssentialFactor);
                    } else {
                        if(!customsEssentialFactor.getEssentialFactor().equals(successParam.getEssentialFactor())){
                            customsEssentialFactor.setUpdateUser(userName);
                            customsEssentialFactor.setUpdateTime(DateUtil.date());
                            customsEssentialFactor.setEssentialFactor(successParam.getEssentialFactor());
                            saveList.add(customsEssentialFactor);
                            updateEssentialFactorList.add(customsEssentialFactor);
                        }
                    }
                }
                //更新报关要素
                if(CollectionUtil.isNotEmpty(saveList)){
                    this.saveOrUpdateBatch(saveList);
                }

                //更新报关要素引用的要素数据（产品信息和清关产品合并信息）
                if(CollectionUtil.isNotEmpty(updateEssentialFactorList)){
                    for (TgCustomsEssentialFactor customsEssentialFactor : updateEssentialFactorList) {
                        LambdaQueryWrapper<TgBaseProduct> productQuery = new LambdaQueryWrapper<>();
                        productQuery.eq(TgBaseProduct :: getCustomsCode, customsEssentialFactor.getCustomsCode())
                                .isNotNull(TgBaseProduct :: getEssentialFactor);
                        List<TgBaseProduct> productList = baseProductService.list(productQuery);
                        List<TgBaseProduct> updateProductList = new ArrayList<>();
                        for (TgBaseProduct baseProduct : productList) {
                            //要素框架名称
                            List<String> nameList = new ArrayList<>();
                            String[] essentialFactorArr = customsEssentialFactor.getEssentialFactor().split("\\s+");
                            for (String essentialFactor : essentialFactorArr) {
                                String[] nameArr = essentialFactor.split("\\.");
                                nameList.add(nameArr[1]);
                            }
                            //产品信息和清关产品合并信息的要素
                            String[] essentialFactorParamArr = baseProduct.getEssentialFactor().split("\\|");
                            //报关要素在框架内的
                            StringBuffer sbIn = new StringBuffer();
                            //报关要素在框架之外的
                            StringBuffer sbOut = new StringBuffer();
                            for (String essentialFactorParam : essentialFactorParamArr) {
                                int idx = essentialFactorParam.indexOf(":");
                                if(idx == -1){
                                    idx = essentialFactorParam.indexOf("：");
                                }
                                String name = essentialFactorParam.substring(0, idx);
                                String val = essentialFactorParam.substring(idx + 1);
                                //校验要素名称是否在框架范围内，在框架范围内按照框架排序，未在框架范围内的则添加到框架范围外，不参与排序，不生成报关单
                                if(nameList.contains(name)){
                                    sbIn.append(name).append(":").append(val).append("|");
                                }else{
                                    sbOut.append(name).append(":").append(val).append("|");
                                }
                            }
                            baseProduct.setEssentialFactor(sbIn.substring(0, sbIn.length()-1));
                            String[] essentialFactorParamValidateArr = baseProduct.getEssentialFactor().split("\\|");

                            //要素按照报关要素排序
                            StringBuffer sb = new StringBuffer();
                            for (String name : nameList) {
                                for (String essentialFactorParam : essentialFactorParamValidateArr) {
                                    int idx = essentialFactorParam.indexOf(":");
                                    if(idx == -1){
                                        idx = essentialFactorParam.indexOf("：");
                                    }
                                    String nameParam = essentialFactorParam.substring(0, idx);
                                    String valueParam = essentialFactorParam.substring(idx + 1);
                                    if(name.equals(nameParam)){
                                        sb.append(nameParam).append(":").append(valueParam).append("|");
                                    }
                                }
                            }
                            TgBaseProduct updateProduct = new TgBaseProduct();
                            updateProduct.setId(baseProduct.getId());
                            updateProduct.setEssentialFactor(sb.substring(0, sb.length()-1));
                            if(StringUtils.isBlank(sbOut.substring(1))){
                                updateProduct.setEssentialFactorTemp("");
                            }else{
                                updateProduct.setEssentialFactorTemp(sbOut.substring(0, sbOut.length()-1));
                            }
                            updateProduct.setUpdateTime(DateUtil.date());
                            updateProduct.setUpdateUser(userName);
                            updateProductList.add(updateProduct);
                        }
                        if(CollectionUtil.isNotEmpty(updateProductList)){
                            for (TgBaseProduct updateProduct : updateProductList) {
                                LambdaUpdateWrapper<TgBaseProduct> updateWrapper = new LambdaUpdateWrapper();
                                updateWrapper.eq(TgBaseProduct :: getId, updateProduct.getId());
                                baseProductService.update(updateProduct, updateWrapper);
                            }
                        }
                    }
                }
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
    private void validExcel(List<TgCustomsEssentialFactorParam> dataList, List<TgCustomsEssentialFactorParam> errorList){
        Iterator<TgCustomsEssentialFactorParam> iterator = dataList.listIterator();
        while(iterator.hasNext()) {
            TgCustomsEssentialFactorParam param = iterator.next();
            param.setUploadRemark(null);
            //验证基础信息
            ResponseData validateResp = this.validateEssentialFactor(param);
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
    private String dealImportErrorList(List<TgCustomsEssentialFactorParam> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, TgCustomsEssentialFactorParam.class)
                    .sheet("导入异常数据").doWrite(errorList);
        } catch (FileNotFoundException e) {
            log.error("报关要素导入异常数据导出异常", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("报关要素导入异常数据导出流关闭异常", e);
                }
            }
        }
        return fileName;
    }

    @Override
    @DataSource(name = "logistics")
    public List<BaseSelectResult> essentialFactorSelect() {
        return mapper.essentialFactorSelect();
    }
}
