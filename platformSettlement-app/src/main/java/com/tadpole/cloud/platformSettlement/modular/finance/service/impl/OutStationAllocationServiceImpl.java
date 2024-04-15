package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.OutStationAllocation;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.OutStationAllocationMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.OutStationAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.RemovalShipmentCostMonthlyShareParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.OutStationAllocationResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IOutStationAllocationService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IRemovalShipmentCostMonthlyShareService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import java.util.*;

/**
* <p>
* 站外分摊汇总表 服务实现类
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Service
@Slf4j
public class OutStationAllocationServiceImpl extends ServiceImpl<OutStationAllocationMapper, OutStationAllocation> implements IOutStationAllocationService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private IRemovalShipmentCostMonthlyShareService monthlyShareService;

    /**
     * 批量站外费用分摊标识
     */
    @Value("${rediskey.confirmOutsiteFee}")
    public String confirmOutsiteFee;

    @DataSource(name = "finance")
    @Override
    public PageResult<OutStationAllocationResult> findPageBySpec(OutStationAllocationParam param) {
        return new PageResult<>(this.baseMapper.findPageBySpec(PageFactory.defaultPage(), param));
    }

    @DataSource(name = "finance")
    @Override
    public List<OutStationAllocationResult> queryList(OutStationAllocationParam param) {
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        return this.baseMapper.findPageBySpec(pageContext, param).getRecords();
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData importExcel(MultipartFile file,List<String> departmentList,List<String> teamList) {
        log.info("导入站外费用分摊汇总表Excel处理开始");
        String account = LoginContext.me().getLoginUser().getName();
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<OutStationAllocation>();
            EasyExcel.read(buffer, OutStationAllocation.class, listener).sheet()
                    .doRead();

            List<OutStationAllocation> dataList = listener.getDataList();
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据为空，导入失败！");
            }

            //异常数据集合 
            List<OutStationAllocation> errorList = new ArrayList<>();
            this.validation(dataList,errorList,account,departmentList,teamList);

            //批量保存更新表数据
            if(CollectionUtil.isNotEmpty(dataList)){
                List<List<OutStationAllocation>> subLists = ListUtil.split(dataList, 2000);
                for (List<OutStationAllocation> subList : subLists){
                    this.baseMapper.saveOrUpdateBatchOSA(subList);
                }
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
            log.error("导入站外费用分摊汇总表Excel处理异常，导入失败！", e);
            return ResponseData.error("导入Excel处理异常，导入失败！");
        } finally {
            if(buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入站外费用分摊汇总表Excel关闭流异常", e);
                }
            }
        }
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData edit(OutStationAllocationParam param) {
        OutStationAllocation ss = new OutStationAllocation();
        ss.setId(param.getId());
        ss.setCostAuxiliaryDescription(param.getCostAuxiliaryDescription());
        ss.setVolumeBillQuantity(param.getVolumeBillQuantity());
        ss.setOtherAdvertisements(param.getOtherAdvertisements());
        ss.setBrushingValue(param.getBrushingValue());
        ss.setBrushingServiceCharge(param.getBrushingServiceCharge());
        ss.setLocalLogisticsFee(param.getLocalLogisticsFee());
        ss.setOverseasWarehouseFee(param.getOverseasWarehouseFee());
        ss.setDisposePurchaseFee(param.getDisposePurchaseFee());
        ss.setDisposeLogisticsFee(param.getDisposeLogisticsFee());
        ss.setDomesticUnsalableInventory(param.getDomesticUnsalableInventory());
        ss.setMoldOpeningCost(param.getMoldOpeningCost());
        this.baseMapper.updateById(ss);
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData confirm(OutStationAllocationParam param) {
        QueryWrapper<OutStationAllocation> qs=new QueryWrapper<>();
        qs.eq("id",param.getId());
        OutStationAllocation check=this.baseMapper.selectOne(qs);
        if(check.getConfirmStatus().equals(BigDecimal.ZERO)){
            //确认前判断销毁移除月分摊是否存在相同维度未确认的数据，存在则返回提示信息
            RemovalShipmentCostMonthlyShareParam shareParam  = new RemovalShipmentCostMonthlyShareParam();
            shareParam.setFiscalPeriod(check.getFiscalPeriod());
            shareParam.setSysShopsName(Arrays.asList(new String[]{check.getShopName()}));
            shareParam.setSysSite(Arrays.asList(new String[]{check.getSite()}));
            shareParam.setDepartment(Arrays.asList(new String[]{check.getDepartment()}));
            shareParam.setTeam(Arrays.asList(new String[]{check.getTeam()}));
            shareParam.setProductType(Arrays.asList(new String[]{check.getProductType()}));
            shareParam.setSku(check.getSku());
            shareParam.setMaterialCode(check.getMaterialCode());
            if(monthlyShareService.hasNotConfirm(shareParam)){
                ResponseData.error("存在未确认的销毁移除成本月分摊数据，请先确认！");
            }

            OutStationAllocation ss=new OutStationAllocation();
            ss.setId(param.getId());
            ss.setConfirmStatus(new BigDecimal(1));
            ss.setConfirmBy(LoginContext.me().getLoginUser().getName());
            ss.setConfirmDate(new Date());

            //更新结算报告，逻辑：物料编码为0的情况，才需通过运营大类关联
            this.baseMapper.updateToReport(ss);

            //更新审核状态
            this.baseMapper.updateById(ss);
        }
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    public void delete(OutStationAllocationParam param) {
        QueryWrapper<OutStationAllocation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ID",param.getId()).eq("CONFIRM_STATUS", 0);
        this.baseMapper.delete(queryWrapper);
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData confirmBatch(OutStationAllocationParam param) {
        log.info("站外分摊汇总表审核开始");
        long start = System.currentTimeMillis();
        //redis操作需绑定key
        BoundValueOperations toList = redisTemplate.boundValueOps(this.confirmOutsiteFee);

        try{
            //从非空则为正在批量确认中
            if (toList.get() != null && StrUtil.isNotEmpty((String)toList.get())){
                return ResponseData.error("正在批量确认中!");
            }
            //设定正在批量确认
            toList.set("正在批量确认中!");
            //获取需要确认的数据
            List<OutStationAllocationParam> params = this.baseMapper.queryConfirm(param);
            if (CollUtil.isEmpty(params)) {
                return ResponseData.success("无可确认的数据!");
            }

            //确认前判断销毁移除月分摊是否存在相同维度未确认的数据，存在则返回提示信息
            RemovalShipmentCostMonthlyShareParam shareParam  = new RemovalShipmentCostMonthlyShareParam();
            shareParam.setFiscalPeriod(param.getFiscalPeriod());
            shareParam.setSysShopsName(Arrays.asList(new String[]{param.getShopName()}));
            shareParam.setSysSite(param.getSites());
            if(monthlyShareService.hasNotConfirm(shareParam)){
                ResponseData.error("存在未确认的销毁移除成本月分摊数据，请先确认！");
            }

            //更新结算报告，逻辑：物料编码为0的情况，才需通过运营大类关联
            this.baseMapper.updateToReportBatch(param);

            //修改状态
            UpdateWrapper<OutStationAllocation> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SHOP_NAME", param.getShopName())
                    .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                    .eq("CONFIRM_STATUS",0)
                    .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE",param.getSites())
                    .set("CONFIRM_STATUS",1)
                    .set("CONFIRM_BY",LoginContext.me().getLoginUser().getName())
                    .set("CONFIRM_DATE",new Date());
            this.baseMapper.update(null,updateWrapper);
            return ResponseData.success(params);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.error("生成失败!:"+e);
        } finally{
            toList.set("");
            log.info("站外分摊汇总表审核结束，耗时---------->" +(System.currentTimeMillis() - start) + "ms");
        }

    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(OutStationAllocationParam param) {
        List<OutStationAllocationParam> params = this.baseMapper.queryConfirm(param);
        for (OutStationAllocationParam pa : params) {
            this.delete(pa);
        }
    }

    @DataSource(name = "finance")
    @Override
    public OutStationAllocationResult getQuantity(OutStationAllocationParam param) {
        return this.baseMapper.getQuantity(PageFactory.defaultPage(),param);
    }

    private void validation(List<OutStationAllocation> dataList, List<OutStationAllocation> errorList, String account,
                            List<String> departmentList,List<String> teamList) {
        Iterator<OutStationAllocation> iterator = dataList.listIterator();
        while(iterator.hasNext()) {
            OutStationAllocation outStationAllocation = iterator.next();
            outStationAllocation.setCreateBy(account);
            outStationAllocation.setUpdateUser(account);
            outStationAllocation.setCreateAt(new Date());

            if(StringUtils.isEmpty(outStationAllocation.getFiscalPeriod())
                    || StringUtils.isEmpty(outStationAllocation.getShopName())
                    || StringUtils.isEmpty(outStationAllocation.getSite())
                    || StringUtils.isEmpty(outStationAllocation.getAccountingCurrency())
                    || StringUtils.isEmpty(outStationAllocation.getSku())
                    || StringUtils.isEmpty(outStationAllocation.getDepartment())
                    || StringUtils.isEmpty(outStationAllocation.getTeam())
                    || StringUtils.isEmpty(outStationAllocation.getMaterialCode())
                    || StringUtils.isEmpty(outStationAllocation.getProductType())
            ){
                //不为空校验
                outStationAllocation.setUploadRemark("会计期间、账号、站点、核算币种、SKU、事业部、team、物料编码、运营大类均不能为空");
                errorList.add(outStationAllocation);
                iterator.remove();
            } else {
                //验证事业部，Team信息
                StringBuffer validInfo = new StringBuffer();
                if (!departmentList.contains(outStationAllocation.getDepartment())) {
                    validInfo.append("事业部有误!");
                }
                if (!teamList.contains(outStationAllocation.getTeam())) {
                    validInfo.append("Team有误!");
                }
                if (validInfo.length() > 0) {
                    outStationAllocation.setUploadRemark(validInfo.toString());
                    errorList.add(outStationAllocation);
                    iterator.remove();
                }
            }
        }
    }
    
    private String dealImportErrorList(List<OutStationAllocation> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, OutStationAllocation.class)
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
}
