package com.tadpole.cloud.platformSettlement.modular.vat.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.VatCheck;
import com.tadpole.cloud.platformSettlement.modular.vat.mapper.VatCheckMapper;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.VatCheckParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.VatCheckResult;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IVatCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * <p>
 * VAT核对表 服务实现类
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
@Service
@Slf4j
public class VatCheckServiceImpl extends ServiceImpl<VatCheckMapper, VatCheck> implements IVatCheckService {

    @Autowired
    private VatCheckMapper mapper;

    @DataSource(name = "finance")
    @Override
    public ResponseData queryListPage(VatCheckParam param) {
        return ResponseData.success(mapper.queryListPage(PageFactory.defaultPage(), param));
    }

    @Override
    @DataSource(name = "finance")
    public VatCheckResult getQuantity(VatCheckParam param) {
        VatCheckResult detailToTal = mapper.listSum(param);
        return detailToTal;
    }

    @Override
    @DataSource(name = "finance")
    public List<VatCheckResult> export(VatCheckParam param) {
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        List<VatCheckResult> records = mapper.queryListPage(pageContext, param).getRecords();
        return records;
    }

    @Override
    @DataSource(name = "finance")
    public ResponseData importExcel(MultipartFile file) {
        log.info("导入Excel处理开始");
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<VatCheck>();
            EasyExcel.read(buffer, VatCheck.class, listener).sheet()
                    .doRead();

            List<VatCheck> dataList = listener.getDataList();
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据为空，导入失败！");
            }

            //异常数据集合
            List<VatCheck> errorList = new ArrayList<>();
            this.validation(dataList,errorList);

            if(CollectionUtil.isNotEmpty(errorList)){
                String fileName = this.dealImportErrorList(errorList);
                //导入失败
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "导入失败，存在异常数据数据", fileName);
            }

            //批量保存更新表数据
            if(CollectionUtil.isNotEmpty(dataList)){
                mapper.commitUpdateBatch(dataList);
                return ResponseData.success("导入成功！");
            }

            return ResponseData.success("导入失败！导入数据为空！");
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
     * 导入VAT核对表数据校验处理
     * @param dataList
     * @param errorList
     * @return
     */
    private void validation(List<VatCheck> dataList, List<VatCheck> errorList) {
        Set<String> allDate = new HashSet<>();
        //Excel重复记录
        Set<String> repeatDate= new HashSet<>();

        Iterator<VatCheck> iterator = dataList.listIterator();
        while(iterator.hasNext()) {
            VatCheck result = iterator.next();
            result.setUpdatePersonNo(LoginContext.me().getLoginUser().getAccount());
            result.setUpdatePersonName(LoginContext.me().getLoginUser().getName());
            result.setUpdateTime(new Date());

            //验证基础信息
            StringBuffer validInfo = new StringBuffer();
            if (StrUtil.isEmpty(result.getActivityPeriod()) ||
                StrUtil.isEmpty(result.getEbmsShopsName()) ||
                StrUtil.isEmpty(result.getSysSite()) ||
                StrUtil.isEmpty(result.getTransactionCurrencyCode())) {
                //不为空校验
                validInfo.append("期间、账号、站点、币别字段值必填！");

                String sb = new StringBuffer().append(result.getActivityPeriod()).append(result.getEbmsShopsName())
                        .append(result.getSysSite()).append(result.getTransactionCurrencyCode()).toString();

                if(allDate.contains(sb)){
                    repeatDate.add(sb);
                    validInfo.append("数据重复，请排查重复数据！");
                }
                allDate.add(sb);
            }
            if(StrUtil.isNotEmpty(validInfo)){
            result.setUploadRemark(validInfo.toString());
            errorList.add(result);
            }
            }
        }

    private String dealImportErrorList(List<VatCheck> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, VatCheck.class)
                    .sheet("导入结果").doWrite(errorList);
        } catch (FileNotFoundException e) {
            log.error("导入Excel异常数据导出异常", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("导入Excel异常数据导出流关闭异常", e);
                }
            }
        }
        return fileName;
    }

    @Override
    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData edit(VatCheckParam param) {
        if(ObjectUtil.isEmpty(param.getId())){
            return ResponseData.error("ID值不能为空！");
        }

        VatCheck ent = new VatCheck();
        ent.setId(param.getId());
        ent.setSalesVatAgent(param.getSalesVatAgent());
        ent.setBtbAgent(param.getBtbAgent());
        ent.setCee(param.getCee());
        ent.setBablance(param.getBablance());
        ent.setRemarkOne(param.getRemarkOne());
        ent.setRemarkTwo(param.getRemarkTwo());
        ent.setUpdatePersonNo(LoginContext.me().getLoginUser().getAccount());
        ent.setUpdatePersonName(LoginContext.me().getLoginUser().getName());
        ent.setUpdateTime(DateUtil.date());
        this.updateById(ent);

        return ResponseData.success("保存成功!");
    }

}
