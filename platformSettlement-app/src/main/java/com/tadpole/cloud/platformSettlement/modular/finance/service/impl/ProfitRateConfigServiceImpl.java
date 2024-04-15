package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.ProfitRateConfig;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.ProfitRateConfigMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.ProfitRateConfigParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ProfitRateConfigResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IProfitRateConfigService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  利润率参数管理服务实现类
 * </p>
 *
 * @author ty
 * @since 2022-05-27
 */
@Slf4j
@Service
public class ProfitRateConfigServiceImpl extends ServiceImpl<ProfitRateConfigMapper, ProfitRateConfig> implements IProfitRateConfigService {

    @Autowired
    private ProfitRateConfigMapper profitRateConfigMapper;

    @DataSource(name = "finance")
    @Override
    public ResponseData queryPage(ProfitRateConfigParam param) {
        return ResponseData.success(profitRateConfigMapper.queryPage(param.getPageContext(), param));
    }

    @DataSource(name = "finance")
    @Override
    public List<ProfitRateConfigResult> export(ProfitRateConfigParam param) {
        Page pageContext = param.getPageContext();
        pageContext.setSize(Integer.MAX_VALUE);
        return profitRateConfigMapper.queryPage(pageContext, param).getRecords();
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData importExcel(MultipartFile file, List<String> departmentList, List<String> teamList) {
        log.info("利润率参数管理导入处理开始");
        String account = LoginContext.me().getLoginUser().getName();
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<ProfitRateConfigResult>();
            EasyExcel.read(buffer, ProfitRateConfigResult.class, listener).sheet()
                    .doRead();

            List<ProfitRateConfigResult> dataList = listener.getDataList();
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据为空，导入失败！");
            }

            //异常数据集合
            List<ProfitRateConfigResult> errorList = new ArrayList<>();
            List<ProfitRateConfig> updateList = this.validation(dataList, errorList, account, departmentList, teamList);
            //批量保存更新表数据
            if(CollectionUtil.isNotEmpty(updateList)){
                //批量更新
                this.saveOrUpdateBatch(updateList);
                if(CollectionUtil.isNotEmpty(errorList)){
                    String fileName = this.dealImportErrorList(errorList);
                    //部分导入成功
                    return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "部分导入成功，存在异常数据数据", fileName);
                }
                return ResponseData.success("导入成功！");
            }
            if(CollectionUtil.isNotEmpty(errorList)){
                String fileName = this.dealImportErrorList(errorList);
                //导入失败
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "导入失败，存在异常数据数据", fileName);
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
     * 导入数据校验处理
     * @param dataList
     * @param errorList
     * @param account
     * @return
     */
    private List<ProfitRateConfig> validation(List<ProfitRateConfigResult> dataList, List<ProfitRateConfigResult> errorList, String account, List<String> departmentList, List<String> teamList) {
        //excel校验处理
        Set<String> allDate = new HashSet<>();
        Set<String> repeatDate = new HashSet<>();
        dataList.stream().forEach(i -> {
            if(StringUtils.isEmpty(i.getPlatform())
                    || StringUtils.isEmpty(i.getDepartment())
                    || StringUtils.isEmpty(i.getTeam())
                    || StringUtils.isEmpty(i.getProductType())
                    || StringUtils.isEmpty(i.getAdRateStr())
                    || StringUtils.isEmpty(i.getShareRateStr())
                    || StringUtils.isEmpty(i.getParamStr())
                    || StringUtils.isEmpty(i.getTargetRateStr())
                    || StringUtils.isEmpty(i.getWarningRatePreSettlementStr())
                    || StringUtils.isEmpty(i.getValidDate())
            ){
                //不为空校验
                i.setUploadRemark("平台、事业部、Team、运营大类、广告占比、去年公摊占比、参数、目标利润率、红线利润率、生效日期均不能为空");
            } else {
                StringBuffer uploadRemark = new StringBuffer();
                if(!i.getValidDate().contains("-") || i.getValidDate().length() != 7){
                    uploadRemark.append("生效日期应为YYYY-MM格式!");
                }
                if (!departmentList.contains(i.getDepartment())) {
                    uploadRemark.append("事业部有误!");
                }
                if (!teamList.contains(i.getTeam())) {
                    uploadRemark.append("Team有误!");
                }
                i.setUploadRemark(uploadRemark == null  || uploadRemark.equals("") ? "" : uploadRemark.toString());

                //Excel重复数据处理：平台、事业部、Team、运营大类
                String sb = new StringBuffer().append(i.getPlatform())
                        .append("/")
                        .append(i.getDepartment())
                        .append("/")
                        .append(i.getTeam())
                        .append("/")
                        .append(i.getProductType()).toString();
                if(allDate.contains(sb)){
                    repeatDate.add(sb);
                }
                allDate.add(sb);
            }
        });

        //查询所有数据
        List<ProfitRateConfig> allList = this.list();
        List<String> newList = allList.stream().map(i -> i.getPlatform() + i.getDepartment() + i.getTeam() + i.getProductType() + i.getValidDate()).collect(Collectors.toList());

        List<ProfitRateConfig> updateList = new ArrayList<>();
        Iterator<ProfitRateConfigResult> iterator = dataList.listIterator();
        while(iterator.hasNext()) {
            ProfitRateConfigResult profitRateConfigResult = iterator.next();
            ProfitRateConfig profitRateConfig = new ProfitRateConfig();
            //Excel重复数据处理：平台、事业部、Team、运营大类
            String sb = new StringBuffer().append(profitRateConfigResult.getPlatform())
                    .append("/")
                    .append(profitRateConfigResult.getDepartment())
                    .append("/")
                    .append(profitRateConfigResult.getTeam())
                    .append("/")
                    .append(profitRateConfigResult.getProductType()).toString();
            if(repeatDate.contains(sb)){
                profitRateConfigResult.setUploadRemark(profitRateConfigResult.getUploadRemark() + sb + "数据重复，请排查重复数据！");
                errorList.add(profitRateConfigResult);
            } else {
                profitRateConfig.setPlatform(profitRateConfigResult.getPlatform());
                profitRateConfig.setDepartment(profitRateConfigResult.getDepartment());
                profitRateConfig.setTeam(profitRateConfigResult.getTeam());
                profitRateConfig.setProductType(profitRateConfigResult.getProductType());
                String percentageStr = "%";
                String adRateStr = profitRateConfigResult.getAdRateStr();
                profitRateConfig.setAdRate(adRateStr.contains(percentageStr) ? new BigDecimal(adRateStr.substring(0, adRateStr.indexOf(percentageStr))) : new BigDecimal(adRateStr));
                String shareRateStr = profitRateConfigResult.getShareRateStr();
                profitRateConfig.setShareRate(shareRateStr.contains(percentageStr) ? new BigDecimal(shareRateStr.substring(0, shareRateStr.indexOf(percentageStr))) : new BigDecimal(shareRateStr));
                String otherRateStr = StringUtils.isNotEmpty(profitRateConfigResult.getOtherRateStr()) ? profitRateConfigResult.getOtherRateStr() : "0%";
                profitRateConfig.setOtherRate(otherRateStr.contains(percentageStr) ? new BigDecimal(otherRateStr.substring(0, otherRateStr.indexOf(percentageStr))) : new BigDecimal(otherRateStr));
                String paramStr = profitRateConfigResult.getParamStr();
                profitRateConfig.setParam(paramStr.contains(percentageStr) ? new BigDecimal(paramStr.substring(0, paramStr.indexOf(percentageStr))) : new BigDecimal(paramStr));
                String targetRateStr = profitRateConfigResult.getTargetRateStr();
                profitRateConfig.setTargetRate(targetRateStr.contains(percentageStr) ? new BigDecimal(targetRateStr.substring(0, targetRateStr.indexOf(percentageStr))) : new BigDecimal(targetRateStr));
                String warningRateBIStr = StringUtils.isNotEmpty(profitRateConfigResult.getWarningRateBIStr()) ? profitRateConfigResult.getWarningRateBIStr() : "0%";
                profitRateConfig.setWarningRateBI(warningRateBIStr.contains(percentageStr) ? new BigDecimal(warningRateBIStr.substring(0, warningRateBIStr.indexOf(percentageStr))) : new BigDecimal(warningRateBIStr));
                String warningRatePreSettlementStr = profitRateConfigResult.getWarningRatePreSettlementStr();
                profitRateConfig.setWarningRatePreSettlement(warningRatePreSettlementStr.contains(percentageStr) ? new BigDecimal(warningRatePreSettlementStr.substring(0, warningRatePreSettlementStr.indexOf(percentageStr))) : new BigDecimal(warningRatePreSettlementStr));
                profitRateConfig.setValidDate(profitRateConfigResult.getValidDate());
                profitRateConfig.setCreateBy(account);
                profitRateConfig.setCreateTime(new Date());

                String repeatName = profitRateConfig.getPlatform() + profitRateConfig.getDepartment() + profitRateConfig.getTeam() + profitRateConfig.getProductType() + profitRateConfig.getValidDate();
                if(CollectionUtil.isNotEmpty(newList) && newList.contains(repeatName)){
                    allList.stream().forEach(i -> {
                        String alreadyName = i.getPlatform() + i.getDepartment() + i.getTeam() + i.getProductType() + i.getValidDate();
                        if(alreadyName.equals(repeatName)){
                            profitRateConfig.setId(i.getId());
                        }
                    });

                }
                updateList.add(profitRateConfig);
            }
        }
        return updateList;
    }

    private String dealImportErrorList(List<ProfitRateConfigResult> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, ProfitRateConfigResult.class)
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

    @DataSource(name = "finance")
    @Override
    public ResponseData queryHistoryPage(ProfitRateConfigParam param) {
        return ResponseData.success(profitRateConfigMapper.queryHistoryPage(PageFactory.defaultPage(), param));
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData departmentSelect() {
        QueryWrapper<ProfitRateConfig> entityWrapper = new QueryWrapper<>();
        entityWrapper.select("DEPARTMENT").groupBy("DEPARTMENT");
        List<Map<String, Object>> departmentSelect = this.listMaps(entityWrapper);
        return ResponseData.success(departmentSelect);
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData teamSelect() {
        QueryWrapper<ProfitRateConfig> entityWrapper = new QueryWrapper<>();
        entityWrapper.select("TEAM").groupBy("TEAM");
        List<Map<String, Object>> teamSelect = this.listMaps(entityWrapper);
        return ResponseData.success(teamSelect);
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData productTypeSelect() {
        QueryWrapper<ProfitRateConfig> entityWrapper = new QueryWrapper<>();
        entityWrapper.select("PRODUCT_TYPE").groupBy("PRODUCT_TYPE");
        List<Map<String, Object>> productTypeSelect = this.listMaps(entityWrapper);
        return ResponseData.success(productTypeSelect);
    }
}
