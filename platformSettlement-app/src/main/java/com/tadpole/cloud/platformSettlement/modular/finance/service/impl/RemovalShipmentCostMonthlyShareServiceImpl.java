package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.RemovalShipmentCostMonthlyShare;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.RemovalShipmentCostMonthlyShareMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.RemovalShipmentCostMonthlyShareParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.RemovalShipmentCostMonthlyShareResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IRemovalShipmentCostMonthlyShareService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
* <p>
* 销毁移除成本月分摊表 服务实现类
* </p>
*
* @author ty
* @since 2022-05-19
*/
@Slf4j
@Service
public class RemovalShipmentCostMonthlyShareServiceImpl extends ServiceImpl<RemovalShipmentCostMonthlyShareMapper, RemovalShipmentCostMonthlyShare> implements IRemovalShipmentCostMonthlyShareService {

    @Autowired
    private RemovalShipmentCostMonthlyShareMapper shipmentCostMonthlyShareMapper;

    @Override
    @DataSource(name = "finance")
    public ResponseData queryPage(RemovalShipmentCostMonthlyShareParam param) {
        param.setOperator(LoginContext.me().getLoginUser().getName());
        if(StringUtils.isNotEmpty(param.getIsListingError()) && "1".equals(param.getIsListingError())){
            //刷Listing
            List<RemovalShipmentCostMonthlyShare> refreshListingList = shipmentCostMonthlyShareMapper.refreshListing(param);
            if(CollectionUtil.isNotEmpty(refreshListingList)){
                this.updateBatchById(refreshListingList);
            }
        }
        if(StringUtils.isNotEmpty(param.getRefreshUnitPrice()) && "1".equals(param.getRefreshUnitPrice())){
            //刷单位成本
            List<RemovalShipmentCostMonthlyShare> refreshUnitPriceList = shipmentCostMonthlyShareMapper.refreshUnitPrice(param);
            if(CollectionUtil.isNotEmpty(refreshUnitPriceList)){
                this.updateBatchById(refreshUnitPriceList);
            }
        }
        return ResponseData.success(shipmentCostMonthlyShareMapper.queryShipmentCostMonthlyShare(PageFactory.defaultPage(), param));
    }

    @Override
    @DataSource(name = "finance")
    public List<RemovalShipmentCostMonthlyShareResult> export(RemovalShipmentCostMonthlyShareParam param) {
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        if(StringUtils.isNotEmpty(param.getIsListingError()) && "1".equals(param.getIsListingError())){
            //刷Listing
            List<RemovalShipmentCostMonthlyShare> refreshListingList = shipmentCostMonthlyShareMapper.refreshListing(param);
            if(CollectionUtil.isNotEmpty(refreshListingList)){
                this.updateBatchById(refreshListingList);
            }
        }
        if(StringUtils.isNotEmpty(param.getRefreshUnitPrice()) && "1".equals(param.getRefreshUnitPrice())){
            //刷单位成本
            List<RemovalShipmentCostMonthlyShare> refreshUnitPriceList = shipmentCostMonthlyShareMapper.refreshUnitPrice(param);
            if(CollectionUtil.isNotEmpty(refreshUnitPriceList)){
                this.updateBatchById(refreshUnitPriceList);
            }
        }
        return shipmentCostMonthlyShareMapper.queryShipmentCostMonthlyShare(pageContext, param).getRecords();
    }

    /**
     * 销毁移除成本月分摊汇总统计
     */
    @Override
    @DataSource(name = "finance")
    public ResponseData totalCost(RemovalShipmentCostMonthlyShareParam param){
        return ResponseData.success(shipmentCostMonthlyShareMapper.totalCost(param));
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData importExcel(MultipartFile file, List<String> departmentList, List<String> teamList) {
        log.info("销毁移除成本月分摊表列表导入处理开始");
        String account = LoginContext.me().getLoginUser().getName();
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<RemovalShipmentCostMonthlyShareResult>();
            EasyExcel.read(buffer, RemovalShipmentCostMonthlyShareResult.class, listener).sheet()
                    .doRead();

            List<RemovalShipmentCostMonthlyShareResult> dataList = listener.getDataList();
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据为空，导入失败！");
            }

            //异常数据集合
            List<RemovalShipmentCostMonthlyShareResult> errorList = new ArrayList<>();
            List<RemovalShipmentCostMonthlyShare> updateList = this.validation(dataList, errorList, account, departmentList, teamList);
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
    private List<RemovalShipmentCostMonthlyShare> validation(List<RemovalShipmentCostMonthlyShareResult> dataList, List<RemovalShipmentCostMonthlyShareResult> errorList, String account, List<String> departmentList, List<String> teamList) {
        List<RemovalShipmentCostMonthlyShare> updateList = new ArrayList<>();
        Iterator<RemovalShipmentCostMonthlyShareResult> iterator = dataList.listIterator();
        while(iterator.hasNext()) {
            RemovalShipmentCostMonthlyShareResult shipmentCostMonthlyShareResult = iterator.next();
            RemovalShipmentCostMonthlyShare removalShipmentCostMonthlyShare = new RemovalShipmentCostMonthlyShare();

            if(shipmentCostMonthlyShareResult.getId() == null
                || shipmentCostMonthlyShareResult.getShareNum() == null
                || StringUtils.isEmpty(shipmentCostMonthlyShareResult.getDepartment())
                || StringUtils.isEmpty(shipmentCostMonthlyShareResult.getTeam())
                || StringUtils.isEmpty(shipmentCostMonthlyShareResult.getProductType())
            ){
                //不为空校验
                shipmentCostMonthlyShareResult.setUploadRemark("行ID、摊销期、事业部、Team、运营大类均不能为空");
                errorList.add(shipmentCostMonthlyShareResult);
            } else {
                //验证事业部，Team信息
                StringBuffer validInfo = new StringBuffer();
                if (BigDecimal.ZERO.compareTo(shipmentCostMonthlyShareResult.getShareNum()) > -1
                        || new BigDecimal(13).compareTo(shipmentCostMonthlyShareResult.getShareNum()) < 1) {
                    validInfo.append("摊销期范围不正确!");
                }
                if (!departmentList.contains(shipmentCostMonthlyShareResult.getDepartment())) {
                    validInfo.append("事业部有误!");
                }
                if (!teamList.contains(shipmentCostMonthlyShareResult.getTeam())) {
                    validInfo.append("Team有误!");
                }
                RemovalShipmentCostMonthlyShare result = this.getById(shipmentCostMonthlyShareResult.getId());
                if(result == null){
                    validInfo.append("该数据在系统不存在！");
                }
                if(result.getConfirmStatus().equals("1")){
                    validInfo.append("该数据在系统已审核通过，不可修改！");
                }
                if (validInfo.length() > 0) {
                    shipmentCostMonthlyShareResult.setUploadRemark(validInfo.toString());
                    errorList.add(shipmentCostMonthlyShareResult);
                } else {
                    removalShipmentCostMonthlyShare.setUpdateBy(account);
                    removalShipmentCostMonthlyShare.setUpdateTime(new Date());
                    removalShipmentCostMonthlyShare.setShareNum(shipmentCostMonthlyShareResult.getShareNum());
                    removalShipmentCostMonthlyShare.setDepartment(shipmentCostMonthlyShareResult.getDepartment());
                    removalShipmentCostMonthlyShare.setTeam(shipmentCostMonthlyShareResult.getTeam());
                    removalShipmentCostMonthlyShare.setProductType(shipmentCostMonthlyShareResult.getProductType());
                    removalShipmentCostMonthlyShare.setId(shipmentCostMonthlyShareResult.getId());

                    //根据id获取开始期间
                    if (BigDecimal.ONE.equals(shipmentCostMonthlyShareResult.getShareNum())) {
                        //摊销期为1期，月分摊额就是对应的总分摊额
                        removalShipmentCostMonthlyShare.setMonthlySharePurchaseCost(result.getAllSharePurchaseCost());
                        removalShipmentCostMonthlyShare.setMonthlyShareLogisticsCost(result.getAllShareLogisticsCost());
                        removalShipmentCostMonthlyShare.setNowSharePurchaseCost(result.getMonthlySharePurchaseCost());
                        removalShipmentCostMonthlyShare.setNowShareLogisticsCost(result.getMonthlyShareLogisticsCost());
                        removalShipmentCostMonthlyShare.setAlreadySharePurchaseCost(BigDecimal.ZERO);
                        removalShipmentCostMonthlyShare.setAlreadyShareLogisticsCost(BigDecimal.ZERO);
                        //摊销期为1期，结束期间就是开始期间
                        removalShipmentCostMonthlyShare.setEndDate(result.getStartDate());
                    } else {
                        //月摊销额-销毁成本-采购成本 = 总分摊额-销毁成本-采购成本 / 摊销期
                        removalShipmentCostMonthlyShare.setMonthlySharePurchaseCost(result.getAllSharePurchaseCost().divide(shipmentCostMonthlyShareResult.getShareNum(), 2, BigDecimal.ROUND_HALF_UP));
                        //初始采购成本本期摊销额
                        removalShipmentCostMonthlyShare.setNowSharePurchaseCost(removalShipmentCostMonthlyShare.getMonthlySharePurchaseCost());
                        //月摊销额-销毁成本-头程物流成本 = 总分摊额-销毁成本头程物流成本 / 摊销期
                        removalShipmentCostMonthlyShare.setMonthlyShareLogisticsCost(result.getAllShareLogisticsCost().divide(shipmentCostMonthlyShareResult.getShareNum(),2,BigDecimal.ROUND_HALF_UP));
                        //初始头程物流成本本期摊销额
                        removalShipmentCostMonthlyShare.setNowShareLogisticsCost(removalShipmentCostMonthlyShare.getMonthlyShareLogisticsCost());
                        removalShipmentCostMonthlyShare.setAlreadySharePurchaseCost(BigDecimal.ZERO);
                        removalShipmentCostMonthlyShare.setAlreadyShareLogisticsCost(BigDecimal.ZERO);
                        //结束期间：根据摊销期计算结束期间，规则：开始期间 + 摊销期 - 1
                        removalShipmentCostMonthlyShare.setEndDate(DateUtil.offsetMonth(result.getStartDate(), shipmentCostMonthlyShareResult.getShareNum().subtract(BigDecimal.ONE).intValue()));
                    }
                    updateList.add(removalShipmentCostMonthlyShare);
                }
            }
        }
        return updateList;
    }

    private String dealImportErrorList(List<RemovalShipmentCostMonthlyShareResult> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, RemovalShipmentCostMonthlyShareResult.class)
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
    public ResponseData batchConfirm(List<Long> param) {
        shipmentCostMonthlyShareMapper.batchConfirm(param, LoginContext.me().getLoginUser().getName());
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData generateRemovalShipment(String fiscalPeriod){
        //执行生成站外费用分摊汇总销毁成本
        shipmentCostMonthlyShareMapper.generateRemovalShipment(fiscalPeriod, LoginContext.me().getLoginUser().getName());

        //执行更新销毁移除成本月份摊
        shipmentCostMonthlyShareMapper.batchUpdateRemovalShipment(fiscalPeriod, LoginContext.me().getLoginUser().getName());
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData destroyRemovalShipment(List<Long> param){
        shipmentCostMonthlyShareMapper.batchDestroy(param, LoginContext.me().getLoginUser().getName());
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    public void generateRemovalShipmentMonShare(List<RemovalShipmentCostMonthlyShare> removalShipmentCostMonthlyShareList) {
        this.saveBatch(removalShipmentCostMonthlyShareList);
    }

    @DataSource(name = "finance")
    @Override
    public Boolean hasNotConfirm(RemovalShipmentCostMonthlyShareParam param) {
        Boolean hasNotConfirm = false;
        if(CollectionUtil.isNotEmpty(shipmentCostMonthlyShareMapper.hasNotConfirm(param))){
            hasNotConfirm = true;
        }
        return hasNotConfirm;
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData updateRemovalShipment(RemovalShipmentCostMonthlyShare param) {
        RemovalShipmentCostMonthlyShare removalShipmentResult = this.getById(param.getId());
        if(removalShipmentResult != null){
            RemovalShipmentCostMonthlyShare removalShipmentUpdate = new RemovalShipmentCostMonthlyShare();
            removalShipmentUpdate.setDepartment(param.getDepartment());
            removalShipmentUpdate.setTeam(param.getTeam());
            removalShipmentUpdate.setShareNum(param.getShareNum());
            removalShipmentUpdate.setProductType(param.getProductType());
            removalShipmentUpdate.setUpdateTime(new Date());
            removalShipmentUpdate.setUpdateBy(LoginContext.me().getLoginUser().getName());

            if (BigDecimal.ONE.equals(param.getShareNum())) {
                //摊销期为1期，月分摊额就是对应的总分摊额
                removalShipmentUpdate.setMonthlySharePurchaseCost(removalShipmentResult.getAllSharePurchaseCost());
                removalShipmentUpdate.setMonthlyShareLogisticsCost(removalShipmentResult.getAllShareLogisticsCost());
                removalShipmentUpdate.setNowSharePurchaseCost(removalShipmentUpdate.getMonthlySharePurchaseCost());
                removalShipmentUpdate.setNowShareLogisticsCost(removalShipmentUpdate.getMonthlyShareLogisticsCost());
                removalShipmentUpdate.setAlreadySharePurchaseCost(BigDecimal.ZERO);
                removalShipmentUpdate.setAlreadyShareLogisticsCost(BigDecimal.ZERO);
                //摊销期为1期，结束期间就是开始期间
                removalShipmentUpdate.setEndDate(removalShipmentResult.getStartDate());
            } else {
                //月摊销额-销毁成本-采购成本 = 总分摊额-销毁成本-采购成本 / 摊销期
                removalShipmentUpdate.setMonthlySharePurchaseCost(removalShipmentResult.getAllSharePurchaseCost().divide(param.getShareNum(), 2, BigDecimal.ROUND_HALF_UP));
                //初始采购成本本期摊销额
                removalShipmentUpdate.setNowSharePurchaseCost(removalShipmentUpdate.getMonthlySharePurchaseCost());
                //月摊销额-销毁成本-头程物流成本 = 总分摊额-销毁成本头程物流成本 / 摊销期
                removalShipmentUpdate.setMonthlyShareLogisticsCost(removalShipmentResult.getAllShareLogisticsCost().divide(param.getShareNum(),2,BigDecimal.ROUND_HALF_UP));
                //初始头程物流成本本期摊销额
                removalShipmentUpdate.setNowShareLogisticsCost(removalShipmentUpdate.getMonthlyShareLogisticsCost());
                removalShipmentUpdate.setAlreadySharePurchaseCost(BigDecimal.ZERO);
                removalShipmentUpdate.setAlreadyShareLogisticsCost(BigDecimal.ZERO);
                //结束期间：根据摊销期计算结束期间，规则：开始期间 + 摊销期 - 1
                removalShipmentUpdate.setEndDate(DateUtil.offsetMonth(removalShipmentResult.getStartDate(), param.getShareNum().subtract(BigDecimal.ONE).intValue()));
            }
            UpdateWrapper<RemovalShipmentCostMonthlyShare> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("ID", param.getId())
                    .eq("CONFIRM_STATUS", "0");
            this.update(removalShipmentUpdate, updateWrapper);
        }
        return ResponseData.success();
    }
}
