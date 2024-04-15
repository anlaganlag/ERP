package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.StockMonitorMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockMonitorParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockMonitorResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockMonitorService;
import com.tadpole.cloud.platformSettlement.api.sales.entity.StockMonitor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
* <p>
    * 备货监控 服务实现类
    * </p>
*
* @author cyt
* @since 2022-07-21
*/
@Service
public class StockMonitorServiceImpl extends ServiceImpl<StockMonitorMapper, StockMonitor> implements IStockMonitorService {

    @Resource
    private StockMonitorMapper mapper;


    @DataSource(name = "stocking")
    @Override
    public List<StockMonitorResult> findPageBySpec(StockMonitorParam param) {
        List<StockMonitorResult> list = this.baseMapper.customPageList(param);
        return list;
    }

    @DataSource(name = "stocking")
    @Override
    public ResponseData CreateData(List<StockMonitor> stockMonitorHeadList){
        long quarterLeftDays = getQuarterLeftDays();
        this.saveBatch(stockMonitorHeadList);
        this.baseMapper.fillPurchase(quarterLeftDays);
        this.baseMapper.fillTurnOverDay();
        return  ResponseData.success();
    }



    private long getQuarterLeftDays() {
        Date now = DateUtil.date();
        int curYear = DateUtil.year(DateUtil.date());
        int QuarterLastMon = DateUtil.quarter(DateUtil.date())*3;
        String QuarterLastMonDateStr = curYear+"-"+QuarterLastMon+"-01";

        Date quarterLastMonDate = DateUtil.parse(QuarterLastMonDateStr);
        Date quarterEndDate = DateUtil.endOfMonth(quarterLastMonDate);
        return DateUtil.between(quarterEndDate,now, DateUnit.DAY);
    }


    @DataSource(name = "stocking")
    @Override
    public void deleteByDept(String department){
        this.baseMapper.deleteByDept(department);
    }

    @DataSource(name = "erpcloud")
    @Override
    public List<StockMonitor> getErpData(String department){
       return this.baseMapper.getErpData(department);
    }

    @DataSource(name = "stocking")
    @Override
    public List<StockMonitor> getCurPurQty(String department) {
        return this.baseMapper.getCurPurQty(department);
    }


    @DataSource(name = "stocking")
    @Override
    public ResponseData megerCalc(List<StockMonitor> stockMonitorHeadList, List<StockMonitor> erpData, List<StockMonitor> curPurQtyData) {

//        Map<String, List<StockMonitor>> headMap = stockMonitorHeadList.stream().collect(Collectors.groupingBy(s -> s.getDepartment() + s.getTeam() + s.getProductType()));
//        Map<String, List<StockMonitor>> erpMap = erpData.stream().collect(Collectors.groupingBy(s -> s.getDepartment() + s.getTeam() + s.getProductType()));
//        Map<String, List<StockMonitor>> curPurQtyMap = curPurQtyData.stream().collect(Collectors.groupingBy(s -> s.getDepartment() + s.getTeam() + s.getProductType()));
        List<StockMonitor> megerResultData = new ArrayList<>();
        List<StockMonitor> megerAllData = new ArrayList<>();
        megerAllData.addAll(stockMonitorHeadList);
        megerAllData.addAll(erpData);
        megerAllData.addAll(curPurQtyData);
        Map<String, List<StockMonitor>> megerMap = megerAllData.stream().collect(Collectors.groupingBy(s -> s.getDepartment() + s.getTeam() + s.getProductType()));

        for (Map.Entry<String, List<StockMonitor>> entry : megerMap.entrySet()) {
            List<StockMonitor> stockMonitorList = entry.getValue();
            if (stockMonitorList.size() < 2) {
                megerResultData.add(stockMonitorList.get(0));
                continue;
            }
            StockMonitor stockMonitor = stockMonitorList.stream()
                    .sorted(Comparator.comparing(StockMonitor::getYearTarget, Comparator.reverseOrder()))
                    .reduce((a, b) -> {
                        BigDecimal   a1=  ObjectUtil.isNull(a.getYearUsedPurBudget()) ? BigDecimal.ZERO : a.getYearUsedPurBudget();
                        BigDecimal   b1=  ObjectUtil.isNull(b.getYearUsedPurBudget()) ? BigDecimal.ZERO : b.getYearUsedPurBudget();
                        a.setYearUsedPurBudget(a1.add(b1));//年度已使用采买预算

                        BigDecimal   a2=  ObjectUtil.isNull(a.getCurUsedQuarterPurBudget()) ? BigDecimal.ZERO : a.getCurUsedQuarterPurBudget();
                        BigDecimal   b2=  ObjectUtil.isNull(b.getCurUsedQuarterPurBudget()) ? BigDecimal.ZERO : b.getCurUsedQuarterPurBudget();
                        a.setCurUsedQuarterPurBudget(a2.add(b2));//本季度已使用采买预算

                        BigDecimal   a3=  ObjectUtil.isNull(a.getCurPurQty()) ? BigDecimal.ZERO : a.getCurPurQty();
                        BigDecimal   b3=  ObjectUtil.isNull(b.getCurPurQty()) ? BigDecimal.ZERO : b.getCurPurQty();
                        a.setCurPurQty(a3.add(b3));//本次采买数量
                        return a;
                    }).get();

            megerResultData.add(stockMonitor);
        }

        if (ObjectUtil.isEmpty(megerResultData)) {
            return ResponseData.error("没找到数据");
        }

        for (StockMonitor monitor : megerResultData) {

            if (ObjectUtil.isNull(monitor.getYearPurchaseBudget()) || ObjectUtil.isNull(monitor.getCurQuarterPurBudget())  ) {
                //没有设置年度 年度采买预算 和季度采买预算
                continue;
            }
//            18	年度采买预算使用度 ----取整（（年度已使用采买预算+本次采买数量）/年度采买预算）
            BigDecimal yearPurchasetPer = (monitor.getYearUsedPurBudget().add(monitor.getCurPurQty())).divide(monitor.getYearPurchaseBudget());
            monitor.setYearPurchasetPer(yearPurchasetPer);

//            19	本季度采买预算使用度  ----- 取整（（本季度已使用采买预算+本次采买数量）/本季度采买预算）
            BigDecimal curUsedQuarterPurPer = (monitor.getCurUsedQuarterPurBudget().add(monitor.getCurPurQty())).divide(monitor.getCurQuarterPurBudget());
            monitor.setYearPurchasetPer(curUsedQuarterPurPer);

//            20	本季度剩余采买预算  -- 本季度采买预算-本季度已使用采买预算-本次采买数量
            BigDecimal curQuarterLeftPur = monitor.getCurQuarterPurBudget().subtract(monitor.getCurPurQty());
            monitor.setYearPurchasetPer(curQuarterLeftPur);

//            21	平均每周剩余采买预算  -----本季度剩余采购预算*7/本季度剩余天数
            BigDecimal quarterLeftDays =BigDecimal.valueOf(getQuarterLeftDays()+1) ;
            BigDecimal  avgWeekLeftPur = curQuarterLeftPur.multiply(BigDecimal.valueOf(7)).divide(quarterLeftDays);
            monitor.setAvgWeekLeftPur(avgWeekLeftPur);

//            22	当前库存周转天数 --下个步骤计算

//            23	年度剩余采买预算 ------本年度采买预算-本年度已使用采买预算-本次采买数量
            BigDecimal yearLeftPurBudget = monitor.getYearPurchaseBudget().subtract(monitor.getYearUsedPurBudget()).subtract(monitor.getCurPurQty());
            monitor.setYearPurchasetPer(yearLeftPurBudget);

        }

        this.saveBatch(megerResultData);

        //当前库存周转天数
        this.baseMapper.fillTurnOverDay();
        return ResponseData.success();
    }




    @DataSource(name = "stocking")
    @Override
    public void fillTurnOverDay(){
        //当前库存周转天数
        this.baseMapper.fillTurnOverDay();
    }


    @DataSource(name = "stocking")
    @Override
    public ResponseData megerCalcDept(StockMonitor stockMonitorDept, List<StockMonitor> erpData, List<StockMonitor> curPurQtyData) {

        if (ObjectUtil.isNotEmpty(erpData)) {
            stockMonitorDept.setYearUsedPurBudget(erpData.stream().map(StockMonitor::getYearUsedPurBudget).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));//年度已使用采买预算
            stockMonitorDept.setCurUsedQuarterPurBudget(erpData.stream().map(StockMonitor::getCurUsedQuarterPurBudget).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));//本季度已使用采买预算
            stockMonitorDept.setCurPurQty(erpData.stream().map(StockMonitor::getCurPurQty).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));//本次采买数量
        }
        if (ObjectUtil.isNotEmpty(curPurQtyData)) {
            BigDecimal cur = curPurQtyData.stream().map(StockMonitor::getCurPurQty).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);//本次采买数量
            stockMonitorDept.setCurPurQty(stockMonitorDept.getCurPurQty().add(cur));//本次采买数量
        }

        if (ObjectUtil.isNull(stockMonitorDept.getYearPurchaseBudget()) || ObjectUtil.isNull(stockMonitorDept.getCurQuarterPurBudget())  ) {
            //没有设置年度 年度采买预算 和季度采买预算
           return ResponseData.error("没有设置年度 年度采买预算 和季度采买预算");
        }
//            18	年度采买预算使用度 ----取整（（年度已使用采买预算+本次采买数量）/年度采买预算）
        BigDecimal yearPurchasetPer = (stockMonitorDept.getYearUsedPurBudget().add(stockMonitorDept.getCurPurQty())).divide(stockMonitorDept.getYearPurchaseBudget(),4,BigDecimal.ROUND_HALF_UP);
        stockMonitorDept.setYearPurchasetPer(yearPurchasetPer);

//            19	本季度采买预算使用度  ----- 取整（（本季度已使用采买预算+本次采买数量）/本季度采买预算）
        BigDecimal curUsedQuarterPurPer = (stockMonitorDept.getCurUsedQuarterPurBudget().add(stockMonitorDept.getCurPurQty())).divide(stockMonitorDept.getCurQuarterPurBudget(),4,BigDecimal.ROUND_HALF_UP);
        stockMonitorDept.setCurUsedQuarterPurPer(curUsedQuarterPurPer);

//            20	本季度剩余采买预算  -- 本季度采买预算-本季度已使用采买预算-本次采买数量
        BigDecimal curQuarterLeftPur = stockMonitorDept.getCurQuarterPurBudget().subtract(stockMonitorDept.getCurUsedQuarterPurBudget()).subtract(stockMonitorDept.getCurPurQty());
        stockMonitorDept.setCurQuarterLeftPur(curQuarterLeftPur);

//            21	平均每周剩余采买预算  -----本季度剩余采购预算*7/本季度剩余天数
        BigDecimal quarterLeftDays =BigDecimal.valueOf(getQuarterLeftDays()+1) ;
        BigDecimal  avgWeekLeftPur = curQuarterLeftPur.multiply(BigDecimal.valueOf(7)).divide(quarterLeftDays,4,BigDecimal.ROUND_HALF_UP);
        stockMonitorDept.setAvgWeekLeftPur(avgWeekLeftPur);

//            22	当前库存周转天数 --下个步骤计算

//            23	年度剩余采买预算 ------本年度采买预算-本年度已使用采买预算-本次采买数量
        BigDecimal yearLeftPurBudget = stockMonitorDept.getYearPurchaseBudget().subtract(stockMonitorDept.getYearUsedPurBudget()).subtract(stockMonitorDept.getCurPurQty());
        stockMonitorDept.setYearLeftPurBudget(yearLeftPurBudget);

        this.saveOrUpdate(stockMonitorDept);

        this.baseMapper.fillTurnOverDayDept(stockMonitorDept.getDepartment());

        return ResponseData.success();
    }


    @DataSource(name = "stocking")
    @Override
    public ResponseData megerCalc2(List<StockMonitor> stockMonitorHeadList, List<StockMonitor> erpData, List<StockMonitor> curPurQtyData) {

        Map<String, List<StockMonitor>> headMap = stockMonitorHeadList.stream().collect(Collectors.groupingBy(s -> s.getDepartment() + s.getTeam() + s.getProductType()));

        Map<String, List<StockMonitor>> erpMap=null;
        if (ObjectUtil.isNotEmpty(erpData)) {
            erpMap = erpData.stream().collect(Collectors.groupingBy(s -> s.getDepartment() + s.getTeam() + s.getProductType()));
        }

        Map<String, List<StockMonitor>> curPurQtyMap=null;
        if (ObjectUtil.isNotEmpty(curPurQtyData)) {
            curPurQtyMap = curPurQtyData.stream().collect(Collectors.groupingBy(s -> s.getDepartment() + s.getTeam() + s.getProductType()));
        }


        List<StockMonitor> megerResultData = new ArrayList<>();

        for (Map.Entry<String, List<StockMonitor>> entry : headMap.entrySet()) {
            String megerFilds = entry.getKey();
            List<StockMonitor> monitorList = entry.getValue();
            StockMonitor monitor = monitorList.get(0);


            if (ObjectUtil.isNotNull(erpMap) && ObjectUtil.isNotEmpty(erpMap.get(megerFilds))) {
                StockMonitor erp = erpMap.get(megerFilds).get(0);
                monitor.setYearUsedPurBudget(erp.getYearUsedPurBudget());//年度已使用采买预算
                monitor.setCurUsedQuarterPurBudget(erp.getCurUsedQuarterPurBudget());//本季度已使用采买预算
                monitor.setCurPurQty(erp.getCurPurQty());//本次采买数量
            }

            if (ObjectUtil.isNotNull(curPurQtyMap) && ObjectUtil.isNotEmpty(curPurQtyMap.get(megerFilds))) {
                StockMonitor curPurQty = curPurQtyMap.get(megerFilds).get(0);
                monitor.setCurPurQty(monitor.getCurPurQty().add(curPurQty.getCurPurQty()));//本次采买数量
            }

            megerResultData.add(monitor);
        }


        if (ObjectUtil.isEmpty(megerResultData)) {
            return ResponseData.error("没找到数据");
        }

        for (StockMonitor monitor : megerResultData) {

            if (ObjectUtil.isNull(monitor.getYearPurchaseBudget()) || ObjectUtil.isNull(monitor.getCurQuarterPurBudget())) {
                //没有设置年度 年度采买预算 和季度采买预算
                continue;
            }


//            18	年度采买预算使用度 ----取整（（年度已使用采买预算+本次采买数量）/年度采买预算）
                BigDecimal yearPurchasetPer=null;

            if ( ! BigDecimal.ZERO.equals(monitor.getYearPurchaseBudget())) {
                yearPurchasetPer  = (monitor.getYearUsedPurBudget().add(monitor.getCurPurQty())).divide(monitor.getYearPurchaseBudget(),4,BigDecimal.ROUND_HALF_UP);
            }
            monitor.setYearPurchasetPer(yearPurchasetPer);


//            19	本季度采买预算使用度  ----- 取整（（本季度已使用采买预算+本次采买数量）/本季度采买预算）
            BigDecimal curUsedQuarterPurPer=null;
            if ( ! BigDecimal.ZERO.equals(monitor.getCurQuarterPurBudget())) {
                curUsedQuarterPurPer = (monitor.getCurUsedQuarterPurBudget().add(monitor.getCurPurQty())).divide(monitor.getCurQuarterPurBudget(),4,BigDecimal.ROUND_HALF_UP);
            }
            monitor.setCurUsedQuarterPurPer(curUsedQuarterPurPer);

//            20	本季度剩余采买预算  -- 本季度采买预算-本季度已使用采买预算-本次采买数量
            BigDecimal curQuarterLeftPur=null;
            if ( monitor.getCurQuarterPurBudget().compareTo(BigDecimal.ZERO)>0
                || monitor.getCurUsedQuarterPurBudget().compareTo(BigDecimal.ZERO)>0
                ||  monitor.getCurPurQty().compareTo(BigDecimal.ZERO)>0
                   ) {
                curQuarterLeftPur = monitor.getCurQuarterPurBudget().subtract(monitor.getCurUsedQuarterPurBudget()).subtract(monitor.getCurPurQty());
            }
            monitor.setCurQuarterLeftPur(curQuarterLeftPur);

//            21	平均每周剩余采买预算  -----本季度剩余采购预算*7/本季度剩余天数
            BigDecimal avgWeekLeftPur=null;
            if ( ! BigDecimal.ZERO.equals(monitor.getCurQuarterPurBudget())) {
                BigDecimal quarterLeftDays =BigDecimal.valueOf(getQuarterLeftDays()+1) ;
                  avgWeekLeftPur = curQuarterLeftPur.multiply(BigDecimal.valueOf(7)).divide(quarterLeftDays,4,BigDecimal.ROUND_HALF_UP);
            }
            monitor.setAvgWeekLeftPur(avgWeekLeftPur);

//            22	当前库存周转天数 --下个步骤计算

//            23	年度剩余采买预算 ------本年度采买预算-本年度已使用采买预算-本次采买数量
            BigDecimal yearLeftPurBudget=null;

            if ( monitor.getYearPurchaseBudget().compareTo(BigDecimal.ZERO)>0
            || monitor.getYearUsedPurBudget().compareTo(BigDecimal.ZERO)>0
            || monitor.getCurPurQty().compareTo(BigDecimal.ZERO)>0 ) {
                 yearLeftPurBudget = monitor.getYearPurchaseBudget().subtract(monitor.getYearUsedPurBudget()).subtract(monitor.getCurPurQty());
            }
            monitor.setYearLeftPurBudget(yearLeftPurBudget);

            if (BigDecimal.ZERO.equals(monitor.getYearPurchaseBudget())) {
                monitor.setYearPurchaseBudget(null);
            }
            if (BigDecimal.ZERO.equals(monitor.getCurQuarterPurBudget())) {
                monitor.setCurQuarterPurBudget(null);
            }

        }

        this.saveBatch(megerResultData);
        //当前库存周转天数
        this.baseMapper.fillTurnOverDay();

        return ResponseData.success();
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }


}
