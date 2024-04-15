package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.NewAveragePrice;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.NewAveragePriceCheckStatus;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.NewAveragePriceMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.NewAveragePriceParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.NewAveragePriceResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.INewAveragePriceService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
* <p>
* 新核算库存平均单价表 服务实现类
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Service
@Slf4j
public class NewAveragePriceServiceImpl extends ServiceImpl<NewAveragePriceMapper, NewAveragePrice> implements INewAveragePriceService {

    @DataSource(name = "finance")
    @Override
    public PageResult<NewAveragePriceResult> findPageBySpec(NewAveragePriceParam param) {
        Page pageContext = getPageContext();

        IPage<NewAveragePriceResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "finance")
    @Override
    public List<NewAveragePriceResult> queryList(NewAveragePriceParam param) {
        return this.baseMapper.queryList(param);
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData importExcel(MultipartFile file) {
        log.info("导入Excel处理开始");
        String account = LoginContext.me().getLoginUser().getName();
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<NewAveragePrice>();
            EasyExcel.read(buffer, NewAveragePrice.class, listener).sheet()
                    .doRead();

            List<NewAveragePrice> dataList = listener.getDataList();
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据为空，导入失败！");
            }

            //异常数据集合
            List<NewAveragePrice> errorList = new ArrayList<>();
            this.validation(dataList,errorList,account);

            //批量保存更新表数据
            if(CollectionUtil.isNotEmpty(dataList)){
                this.saveBatch(dataList);
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

    @DataSource(name = "finance")
    @Override
    public void edit(NewAveragePriceParam param) {
        NewAveragePrice ss = new NewAveragePrice();

        ss.setId(param.getId());
        ss.setPurchaseUnitPrice(param.getPurchaseUnitPrice());
        ss.setAdditionalUnitPrice(param.getAdditionalUnitPrice());
        ss.setLogisticsUnitPrice(param.getLogisticsUnitPrice());

        this.baseMapper.updateById(ss);
    }

    @DataSource(name = "finance")
    @Override
    public void confirm(NewAveragePriceParam param) {
        NewAveragePrice ss = new NewAveragePrice();

        ss.setId(param.getId());
        ss.setConfirmStatus(new BigDecimal(NewAveragePriceCheckStatus.getEnumValue("已确认")));
        ss.setConfirmBy(LoginContext.me().getLoginUser().getName());
        ss.setConfirmDate(new Date());

        this.baseMapper.updateById(ss);
    }

    @DataSource(name = "finance")
    @Override
    public void delete(NewAveragePriceParam param) {
        QueryWrapper<NewAveragePrice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ID",param.getId()).eq("CONFIRM_STATUS",0);
        this.baseMapper.delete(queryWrapper);
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmBatch(NewAveragePriceParam param) {
        List<NewAveragePriceParam> params = this.baseMapper.confirmAndDeleteBatch(param);
        UpdateWrapper<NewAveragePrice> updateWrapper = new UpdateWrapper<>();

        updateWrapper
                .eq("SHOP_NAME", param.getShopName())
                .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                .eq("CONFIRM_STATUS",0)
                .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE",param.getSites())
                .set("CONFIRM_STATUS",new BigDecimal(NewAveragePriceCheckStatus.getEnumValue("已确认")))
                .set("CONFIRM_BY",LoginContext.me().getLoginUser().getName())
                .set("CONFIRM_DATE",new Date());

        this.baseMapper.update(null,updateWrapper);
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(NewAveragePriceParam param) {
        List<NewAveragePriceParam> params = this.baseMapper.confirmAndDeleteBatch(param);
        for (NewAveragePriceParam p : params) {
            this.delete(p);
        }
    }

    private void validation(List<NewAveragePrice> dataList, List<NewAveragePrice> errorList, String account) {
        Set<String> allDate = new HashSet<>();
        //Excel重复记录
        Set<String> repeatDate = new HashSet<>();

        dataList.stream().forEach(i -> {
            StringBuffer uploadRemark = new StringBuffer();
            if(StringUtils.isEmpty(i.getFiscalPeriod())){
                uploadRemark.append("会计期间不能为空！");
            }
            if(StringUtils.isEmpty(i.getShopName())){
                uploadRemark.append("账号不能为空！");
            }
            if(StringUtils.isEmpty(i.getSite())){
                uploadRemark.append("站点不能为空！");
            }
            if(StringUtils.isEmpty(i.getMaterialCode())){
                uploadRemark.append("物料不能为空！");
            }
            if(i.getLogisticsUnitPrice() == null){
                uploadRemark.append("物流单价不能为空！");
            }
            if(i.getPurchaseUnitPrice() == null){
                uploadRemark.append("采购单价不能为空！");
            }
            if(i.getAdditionalUnitPrice() == null){
                uploadRemark.append("附加单价不能为空！");
            }
            i.setUploadRemark(uploadRemark == null || uploadRemark.equals("") ? "" : uploadRemark.toString());
            String sb = new StringBuffer().append(i.getFiscalPeriod()).append(i.getShopName())
                    .append(i.getSite()).append(i.getMaterialCode()).toString();
            if(allDate.contains(sb)){
                repeatDate.add(sb);
            }
            allDate.add(sb);
        });

        Iterator<NewAveragePrice> iterator = dataList.listIterator();
        while(iterator.hasNext()) {
            NewAveragePrice averagePrice = iterator.next();
            averagePrice.setCreateBy(account);
            averagePrice.setCreateAt(new Date());
            //校验重复数据
            String sb = new StringBuffer().append(averagePrice.getFiscalPeriod()).append(averagePrice.getShopName())
                .append(averagePrice.getSite()).append(averagePrice.getMaterialCode()).toString();
            if(repeatDate.contains(sb)){
            averagePrice.setUploadRemark(averagePrice.getUploadRemark() == null|| averagePrice.getUploadRemark().equals("") ? "数据重复，请排查重复数据！" : averagePrice.getUploadRemark() + "数据重复，请排查重复数据！");
            }
            if(averagePrice.getUploadRemark()==null || averagePrice.getUploadRemark().equals("")){
                QueryWrapper<NewAveragePrice> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("FISCAL_PERIOD", averagePrice.getFiscalPeriod())
                        .eq("SHOP_NAME", averagePrice.getShopName())
                        .eq("SITE", averagePrice.getSite())
                        .eq("MATERIAL_CODE", averagePrice.getMaterialCode());
                if(this.baseMapper.selectCount(queryWrapper) > 0){
                    averagePrice.setUploadRemark("数据库已存在，请排查数据！");
                }
            }
            if(StringUtils.isNotEmpty(averagePrice.getUploadRemark())){
                errorList.add(averagePrice);
                //移除异常的数据
                iterator.remove();
            }

        }
    }

    private String dealImportErrorList(List<NewAveragePrice> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, NewAveragePrice.class)
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
    private Page getPageContext() {
        return PageFactory.defaultPage();
    }
}
