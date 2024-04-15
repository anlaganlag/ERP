package com.tadpole.cloud.operationManagement.modular.stock.utils;


import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.modular.stock.entity.OperApplyInfo;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TeamVerif;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 备货计算规则定义 Calculation Rules
 */
@Slf4j
public class StockCalcRules {


    /**
     * 日常备货申请功能:"推荐备货天数"信息项提供批量填充（即修改）功能。 根据填充值计算以下字段：
     * 额外备货天数	销售需求	销售需求覆盖日期	申请区域备货数量	申请区域备货后周转天数
     */
    public static ResponseData batchFillCalc(List<OperApplyInfo> applyInfoList) {

        List<String> errorList = new ArrayList<>();

        String errorStr = "推荐日期+所填的<销售需求天数>,计算出<销售需求覆盖日期> 超出了未来6个月的时间，请重新填写<销售需求天数>|------";

        OperApplyInfo infoResult = applyInfoList.get(0);
        Date bizdate = infoResult.getBizdate();//推荐日期
        LocalDate bizLocalDate = bizdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();//推荐日期月份

        LocalDate lastDate = bizLocalDate.plusMonths(6).with(TemporalAdjusters.lastDayOfMonth());//


        for (OperApplyInfo info : applyInfoList) {

            BigDecimal salesStockDays = ObjectUtil.isNull(info.getTotalBackDays()) ? BigDecimal.ZERO : info.getTotalBackDays();//销售需求天数 = 总备货天数D6
            info.setSalesStockDays(salesStockDays);

            //1--销售需求覆盖日期  == 推荐日期  +  销售需求天数
            LocalDate operCoversSalesDate = bizLocalDate.plusDays(salesStockDays.longValue());//销售需求覆盖日期  == 推荐日期  +  销售需求天数
            if (operCoversSalesDate.compareTo(lastDate) > 0) { // 销售需求覆盖日期 大于最大的区间范围
                //报错:TODO
                String erData = "数据ID:" + info.getId() + "|ASIN:" + info.getAsin() + "|物料编码：" + info.getMaterialCode() + "|所填写的需求天数为:" + salesStockDays;
                errorList.add(errorStr + erData);
                continue;
            } else {
//                info.setOperCoversSalesDate(this.localDateToDate(operCoversSalesDate));
                info.setOperCoversSalesDate(Date.from(operCoversSalesDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            }

            HashMap<Integer, BigDecimal> monthSaleQty = new HashMap<>();

            //读取运营填写的当月以及未来6个月的销量数据
            for (int i = 0; i < 7; i++) {

                switch (i) {
                    case 0:
                        monthSaleQty.put(i, (ObjectUtil.isNull(info.getOperCurMonthQty()) ? BigDecimal.ZERO : info.getOperCurMonthQty()));
                        break;
                    case 1:
                        monthSaleQty.put(i, (ObjectUtil.isNull(info.getOperCurMonthAdd1Qty()) ? BigDecimal.ZERO : info.getOperCurMonthAdd1Qty()));
                        break;
                    case 2:
                        monthSaleQty.put(i, (ObjectUtil.isNull(info.getOperCurMonthAdd2Qty()) ? BigDecimal.ZERO : info.getOperCurMonthAdd2Qty()));
                        break;
                    case 3:
                        monthSaleQty.put(i, (ObjectUtil.isNull(info.getOperCurMonthAdd3Qty()) ? BigDecimal.ZERO : info.getOperCurMonthAdd3Qty()));
                        break;
                    case 4:
                        monthSaleQty.put(i, (ObjectUtil.isNull(info.getOperCurMonthAdd4Qty()) ? BigDecimal.ZERO : info.getOperCurMonthAdd4Qty()));
                        break;
                    case 5:
                        monthSaleQty.put(i, (ObjectUtil.isNull(info.getOperCurMonthAdd5Qty()) ? BigDecimal.ZERO : info.getOperCurMonthAdd5Qty()));
                        break;
                    case 6:
                        monthSaleQty.put(i, (ObjectUtil.isNull(info.getOperCurMonthAdd6Qty()) ? BigDecimal.ZERO : info.getOperCurMonthAdd6Qty()));
                        break;
                }
            }

            //2--额外备货天数

            BigDecimal totalBackDays = info.getTotalBackDays();//总备货天数
            BigDecimal extraDays = salesStockDays.subtract(totalBackDays);//额外备货天数  == 销售需求天数  - 总备货天数 totalBackDays
            info.setExtraDays(extraDays);

            //3-- 销售需求：
            //当前月份① 剩余天数需要的数量curNeedQty + 整月需要的数量intMonthsQty，+ ③覆盖日期所处月份的天数 需要的数量 coversMonthQtt
            int dayOfMonth = bizLocalDate.getDayOfMonth();
            BigDecimal curMonthQty = monthSaleQty.get(0);
            BigDecimal curNeedQty = BigDecimal.ZERO;

            //①当前月份 剩余天数需要的数量
            int calcDay = bizLocalDate.lengthOfMonth() - dayOfMonth;
            curNeedQty = BigDecimal.valueOf(calcDay).multiply(curMonthQty).divide(BigDecimal.valueOf(bizLocalDate.lengthOfMonth()), 0, BigDecimal.ROUND_DOWN);


            //②整月需要的数量,③覆盖日期所处月份的天数 需要的数量
            BigDecimal intMonthsQty = BigDecimal.ZERO;
            BigDecimal coversMonthQty = BigDecimal.ZERO;


            for (int i = 1; i < 7; i++) {
                if (bizLocalDate.plusMonths(i).with(TemporalAdjusters.lastDayOfMonth())
                        .compareTo(operCoversSalesDate.with(TemporalAdjusters.lastDayOfMonth())) < 0) {
                    //②整月需要的数量
                    intMonthsQty = intMonthsQty.add(monthSaleQty.get(Integer.valueOf(i)));
                    continue;
                }
                if (bizLocalDate.plusMonths(i).with(TemporalAdjusters.lastDayOfMonth())
                        .compareTo(operCoversSalesDate.with(TemporalAdjusters.lastDayOfMonth())) == 0) {
                    BigDecimal operCoversSales = monthSaleQty.get(i);
                    //③覆盖日期所处月份的天数 需要的数量
                    coversMonthQty = operCoversSales.multiply(BigDecimal.valueOf(operCoversSalesDate.getDayOfMonth()))
                            .divide(BigDecimal.valueOf(operCoversSalesDate.lengthOfMonth()), 0, BigDecimal.ROUND_DOWN);
                }
                break;
            }
            BigDecimal salesDemand = curNeedQty.add(intMonthsQty).add(coversMonthQty);
            info.setSalesDemand(salesDemand);


            //4-- 申请区域备货数量  = 销售需求 - AZ海外总库存
            BigDecimal stockQtyArea = salesDemand.subtract(ObjectUtil.isNull(info.getTotalVolume()) ? BigDecimal.ZERO : info.getTotalVolume());
            info.setStockQtyArea(stockQtyArea);

            //5--申请区域备货后周转天数  = ( 申请区域备货数量 + AZ海外总库存)/日均销量  =IFERROR((CH5+Z5)/BF5,99999)
            if (ObjectUtil.isNull(info.getDayavgqty()) || BigDecimal.ZERO.compareTo(info.getDayavgqty()) == 0) {
                info.setTurnoverDaysArea(BigDecimal.valueOf(99999));
            } else {
                BigDecimal turnoverDaysAreaQty = stockQtyArea.add(ObjectUtil.isNull(info.getTotalVolume()) ? BigDecimal.ZERO : info.getTotalVolume());
                info.setTurnoverDaysArea(turnoverDaysAreaQty.divide(info.getDayavgqty(), 0, BigDecimal.ROUND_DOWN));
            }

            info.setUpdateTime(new Date());

            log.info("额外备货天数:{},销售需求：{},销售需求覆盖日期:{},申请区域备货数量:{},申请区域备货后周转天数:{}"
                    , info.getExtraDays().intValue()
                    , info.getSalesDemand().intValue()
                    , info.getOperCoversSalesDate()
                    , info.getStockQtyArea().intValue()
                    , info.getTurnoverDaysArea());

        }

        if (ObjectUtil.isNotEmpty(errorList)) {
            return ResponseData.error(JSON.toJSONString(errorList));
        }
        return ResponseData.success();
    }


    /**
     * team审核部分属性值初始化以下值：
     * 1,销售需求覆盖日期
     * 2,额外备货天数
     * 3,销售需求
     *  x  4,申请区域备货数量  取消
     * 5,申请区域备货后周转天数
     * 6,计算AZ超180天库龄数量占比
     * 7,AZ海外总库存供货天数
     *
     * @param teamVerif
     */
    public static void teamInitValue(TeamVerif teamVerif) {


        String errorStr = "推荐日期+所填的<销售需求天数>,计算出<销售需求覆盖日期> 超出了未来6个月的时间，请重新填写<销售需求天数>|------";

        Date bizdate = teamVerif.getBizdate();//推荐日期
        LocalDate bizLocalDate = bizdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();//推荐日期月份

        LocalDate lastDate = bizLocalDate.plusMonths(6).with(TemporalAdjusters.lastDayOfMonth());//

//        BigDecimal salesDemand = teamVerif.getSalesDemand().max(teamVerif.getTotalVolume());

        BigDecimal dayavgqty = teamVerif.getDayavgqty();


        BigDecimal salesStockDays = teamVerif.getSalesStockDays();
        //1--销售需求覆盖日期  == 推荐日期  +  销售需求天数
        LocalDate operCoversSalesDate = bizLocalDate.plusDays(salesStockDays.longValue());//销售需求覆盖日期  == 推荐日期  +  销售需求天数
        if (operCoversSalesDate.compareTo(lastDate) > 0) { // 销售需求覆盖日期 大于最大的区间范围
            String erData = "数据ID:" + teamVerif.getId() + "|ASIN:" + teamVerif.getAsin() + "|物料编码：" + teamVerif.getMaterialCode() + "|所填写的需求天数为:" + salesStockDays.longValue();
            return  ;
//            return   ResponseData.error("销售需求覆盖日期 大于最大的区间范围");
        } else {
            teamVerif.setOperCoversSalesDate(Date.from(operCoversSalesDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }



        HashMap<Integer, BigDecimal> monthSaleQty = new HashMap<>();

        //读取运营填写的当月以及未来6个月的销量数据
        for (int i = 0; i < 7; i++) {

            switch (i) {
                case 0:
                    monthSaleQty.put(i, (ObjectUtil.isNull(teamVerif.getOperCurMonthQty()) ? BigDecimal.ZERO : teamVerif.getOperCurMonthQty()));
                    break;
                case 1:
                    monthSaleQty.put(i, (ObjectUtil.isNull(teamVerif.getOperCurMonthAdd1Qty()) ? BigDecimal.ZERO : teamVerif.getOperCurMonthAdd1Qty()));
                    break;
                case 2:
                    monthSaleQty.put(i, (ObjectUtil.isNull(teamVerif.getOperCurMonthAdd2Qty()) ? BigDecimal.ZERO : teamVerif.getOperCurMonthAdd2Qty()));
                    break;
                case 3:
                    monthSaleQty.put(i, (ObjectUtil.isNull(teamVerif.getOperCurMonthAdd3Qty()) ? BigDecimal.ZERO : teamVerif.getOperCurMonthAdd3Qty()));
                    break;
                case 4:
                    monthSaleQty.put(i, (ObjectUtil.isNull(teamVerif.getOperCurMonthAdd4Qty()) ? BigDecimal.ZERO : teamVerif.getOperCurMonthAdd4Qty()));
                    break;
                case 5:
                    monthSaleQty.put(i, (ObjectUtil.isNull(teamVerif.getOperCurMonthAdd5Qty()) ? BigDecimal.ZERO : teamVerif.getOperCurMonthAdd5Qty()));
                    break;
                case 6:
                    monthSaleQty.put(i, (ObjectUtil.isNull(teamVerif.getOperCurMonthAdd6Qty()) ? BigDecimal.ZERO : teamVerif.getOperCurMonthAdd6Qty()));
                    break;
            }
        }

        //2--额外备货天数

        BigDecimal totalBackDays = teamVerif.getTotalBackDays();//总备货天数
        BigDecimal extraDays = salesStockDays.subtract(totalBackDays);//额外备货天数  == 销售需求天数  - 总备货天数 totalBackDays
        teamVerif.setExtraDays(extraDays);

        //3-- 销售需求：
        /**
         * 默认值 & 实时计算规则：
         * (设：销售需求覆盖日期2.month(-推荐日期.month(-1 为N；
         * 取值来源：
         *   (【本月】销量2 * (（30-推荐日期.day(/30 +
         *   【本月+1】销量2+…+
         *   【本月+N】销量2+
         *   (【本月+N+1】销量2*销售需求覆盖日期2.day(）/30
         */
        //当前月份① 剩余天数需要的数量curNeedQty + 整月需要的数量intMonthsQty，+ ③覆盖日期所处月份的天数 需要的数量 coversMonthQtt
        int dayOfMonth = bizLocalDate.getDayOfMonth();
        BigDecimal curMonthQty = monthSaleQty.get(0);
        BigDecimal curNeedQty = BigDecimal.ZERO;

        //①当前月份 剩余天数需要的数量
        int calcDay = bizLocalDate.lengthOfMonth() - dayOfMonth;
//        curNeedQty = BigDecimal.valueOf(calcDay).multiply(curMonthQty).divide(BigDecimal.valueOf(bizLocalDate.lengthOfMonth()), 0, BigDecimal.ROUND_DOWN);
//        curNeedQty = BigDecimal.valueOf(calcDay).multiply(curMonthQty).divide(BigDecimal.valueOf(30), 0, BigDecimal.ROUND_DOWN);
//        月销量减去当月已过的时间销量
        BigDecimal curNeedQty2 = BigDecimal.valueOf(bizLocalDate.getDayOfMonth()).multiply(curMonthQty).divide(BigDecimal.valueOf(30), 0, BigDecimal.ROUND_DOWN);
        curNeedQty=   monthSaleQty.get(0).subtract(curNeedQty2);


        //②整月需要的数量,③覆盖日期所处月份的天数 需要的数量
        BigDecimal intMonthsQty = BigDecimal.ZERO;
        BigDecimal coversMonthQty = BigDecimal.ZERO;


        for (int i = 1; i < 7; i++) {
            if (bizLocalDate.plusMonths(i).with(TemporalAdjusters.lastDayOfMonth())
                    .compareTo(operCoversSalesDate.with(TemporalAdjusters.lastDayOfMonth())) < 0) {
                //②整月需要的数量
                intMonthsQty = intMonthsQty.add(monthSaleQty.get(Integer.valueOf(i)));
                continue;
            }
            if (bizLocalDate.plusMonths(i).with(TemporalAdjusters.lastDayOfMonth())
                    .compareTo(operCoversSalesDate.with(TemporalAdjusters.lastDayOfMonth())) == 0) {
                BigDecimal operCoversSales = monthSaleQty.get(i);
                //③覆盖日期所处月份的天数 需要的数量
                coversMonthQty = operCoversSales.multiply(BigDecimal.valueOf(operCoversSalesDate.getDayOfMonth()))
//                        .divide(BigDecimal.valueOf(operCoversSalesDate.lengthOfMonth()), 0, BigDecimal.ROUND_DOWN);
                        .divide(BigDecimal.valueOf(30), 0, BigDecimal.ROUND_DOWN);
            }
            break;
        }
        BigDecimal salesDemand = curNeedQty.add(intMonthsQty).add(coversMonthQty);
        teamVerif.setSalesDemand(salesDemand);


        //4---申请区域备货数量  '默认值 & 实时计算规则：销售需求-AZ海外总库存D5   TODO:2.0和3.0计算规则不一样
        BigDecimal totalVolume = teamVerif.getTotalVolume();//AZ海外总库存D5
   /*     if (ObjectUtil.isNull(totalVolume) || totalVolume.compareTo(BigDecimal.ZERO) == 0) {
            teamVerif.setStockQtyArea(salesDemand);
            totalVolume = BigDecimal.ZERO;
        } else {
            if (salesDemand.subtract(totalVolume).compareTo(BigDecimal.ZERO) >= 0) {
                teamVerif.setStockQtyArea(salesDemand.subtract(totalVolume));
            } else {
                teamVerif.setStockQtyArea(BigDecimal.ZERO);
            }
        }*/

        //5--申请区域备货后周转天数    MAX(销售需求2,AZ海外总库存D5)/日均销量D5
        BigDecimal maxValue = salesDemand.compareTo(totalVolume) >= 0 ? salesDemand : totalVolume;
        if (ObjectUtil.isNull(dayavgqty) || dayavgqty.compareTo(BigDecimal.ZERO) == 0) {
            teamVerif.setTurnoverDaysArea(new BigDecimal(99999));
        } else {
            teamVerif.setTurnoverDaysArea(maxValue.divide(dayavgqty, 4, RoundingMode.HALF_UP));
        }

        //6--计算AZ超180天库龄数量占比
        if (teamVerif.getInStockQty().compareTo(BigDecimal.ZERO) != 0) {
            teamVerif.setOverD180InvAgeQtyRate(teamVerif.getOverD180InvAgeQty()
                    .divide(teamVerif.getInStockQty(), 4, BigDecimal.ROUND_HALF_UP));
        } else {
            teamVerif.setOverD180InvAgeQtyRate(new BigDecimal(99999));
        }
        //7--AZ海外总库存供货天数  向下取整（AZ海外总库存D5/（预估销售数量D5/总备货天数D5)）
        if (teamVerif.getPreSaleQty().compareTo(BigDecimal.ZERO) != 0 && teamVerif.getTotalBackDays().compareTo(BigDecimal.ZERO) != 0) {
            teamVerif.setPrepareDays(teamVerif.getTotalVolume().divide(teamVerif.getPreSaleQty().divide(teamVerif.getTotalBackDays(), 8, BigDecimal.ROUND_DOWN), 0, BigDecimal.ROUND_DOWN));
        } else {
            teamVerif.setPrepareDays(new BigDecimal(99999));
        }

    }


    /**
     * 将 LocalDate 转为 Date
     *
     * @param localDate
     * @return java.util.Date
     */
    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


}
