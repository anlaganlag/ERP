package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.EbmsLogisticsSettlementParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.EbmsOiwLogisticsParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.EbmsOverseasOutWarehouseLogisticsParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LogisticsSettlementDetailParam;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.LogisticsTrackStatusEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILogisticsSettlementService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IOverseasInWarehouseService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IOverseasOutWarehouseService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.*;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsPackingListDet2Mapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.LogisticsSignParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsBillManageImportParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsListToHeadRouteDetParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsListToHeadRouteParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackingListDet1Result;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbShipemtListClearancModel;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@Slf4j
public class LogisticsBillManageServiceImpl implements LogisticsBillManageService {

    @Resource
    private TbLogisticsListToHeadRouteService tbLogisticsListToHeadRouteService;

    @Resource
    private TbLogisticsListToHeadRouteDetService tbLogisticsListToHeadRouteDetService;

    @Resource
    private TbLogisticsPackingListDet2Service tbLogisticsPackingListDet2Service;
    @Resource
    private TbLogisticsPackingListDet1Service tbLogisticsPackingListDet1Service;

    @Resource
    private IOverseasOutWarehouseService overseasOutWarehouseService;

    @Resource
    private TbLogisticsPackingListService tbLogisticsPackingListService;

    @Resource
    private IOverseasInWarehouseService overseasInWarehouseService;

    @Resource
    private ILogisticsSettlementService logisticsSettlementService;

    @Resource
    private TbBscOverseasWayService tbBscOverseasWayService;

    @Resource
    private TbLogisticsListToEndRouteDetService tbLogisticsListToEndRouteDetService;

    @Resource
    private TbLogisticsListToEndRouteService tbLogisticsListToEndRouteService;

    @Resource
    private TbLogisticsNewPriceService tbLogisticsNewPriceService;

    @Resource
    private TbLogisticsPackingListDet2Mapper tbLogisticsPackingListDet2Mapper;







    /**
     * 获取通关数据
     *
     * @param lhrOddNumb
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public List<TbShipemtListClearancModel> getClearanceData(String lhrOddNumb) {
        LambdaQueryWrapper<TbLogisticsListToHeadRoute> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TbLogisticsListToHeadRoute::getLhrOddNumb, lhrOddNumb);
        List<TbLogisticsListToHeadRoute> headRouterDataList = tbLogisticsListToHeadRouteService.list(wrapper);
        if (ObjectUtil.isEmpty(headRouterDataList)) {
            return null;
        }
        TbLogisticsListToHeadRoute headRouterData = headRouterDataList.get(0);
        String comNameCN = headRouterData.getLogpIsEntry() == 1 ? headRouterData.getLhrCustBroker() : "";

        List<String> packCodeList = this.getPackCodeList(lhrOddNumb);

        List<TbShipemtListClearancModel> result = tbLogisticsPackingListDet2Service.getClearanceData(lhrOddNumb, packCodeList);
        for (TbShipemtListClearancModel model : result) {
            model.setLhrOddNumb(lhrOddNumb);
            model.setComNameCN(comNameCN);
        }
        return result;
    }

    private List<String> getPackCodeList(String lhrOddNumb) {
        List<TbLogisticsListToHeadRouteDet> detList = tbLogisticsListToHeadRouteDetService.queryByLhrOddNumb(lhrOddNumb);
        List<String> packCodeList = detList.stream().map(TbLogisticsListToHeadRouteDet::getPackCode).distinct().collect(Collectors.toList());
        return packCodeList;
    }

    /**
     * 获取通关所需箱件信息
     *
     * @param lhrOddNumb
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public List<TbLogisticsPackingListDet1Result> getClearanceBoxInfoData(String lhrOddNumb) {
        List<String> packCodeList = this.getPackCodeList(lhrOddNumb);
        return tbLogisticsPackingListDet1Service.getClearanceBoxInfoData(lhrOddNumb, packCodeList);
    }

    /**
     * 删除
     *
     * 1--推送物流状态至海外仓出库任务
     * 2--推送物流单删除信息至海外仓入库任务
     * 3--推送删除物流实际结算信息
     * 4--删除物流单
     *
     * @param lhrCode
     * @param lhrOddNumb
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @DataSource(name = "logistics")
    @Override
    public ResponseData delLogisticsOrder(String lhrCode, String lhrOddNumb) {

        //1--推送物流状态至海外仓出库任务
        this.updateLogisticsStatusToMCMS(lhrCode, lhrOddNumb,"");

        //2--推送物流单删除信息至海外仓入库任务
        this.deleteLogisticsToMCMS(lhrCode, lhrOddNumb);

        //3--推送删除物流实际结算信息
        this.deleteLogisticsSettlement(lhrCode);
        //4--删除物流单
        //4.1更新发货单货运方式 和物流方式
        tbBscOverseasWayService.updBscOverseasWayDeliveryType(lhrCode, lhrOddNumb, null, "未发货");

        //4.2根据批次号和快递单号 重置出货清单明细1数据
        tbLogisticsPackingListDet1Service.resetData(lhrCode, lhrOddNumb);

        //4.3根据批次号和快递单号 更新出货清单信息 状态
        tbLogisticsPackingListService.updatePackLogState(lhrCode, lhrOddNumb,"未发货");

        //4.4根据批次号和快递单号 删除物流单尾程信息-明细
        tbLogisticsListToEndRouteDetService.delByLhrCodeAndLhrOddNumb(lhrCode, lhrOddNumb);
        //4.5根据批次号和快递单号 删除物流单尾程信息
        tbLogisticsListToEndRouteService.delByLhrCodeAndLhrOddNumb(lhrCode, lhrOddNumb);
        //4.6根据批次号和快递单号 删除物流单头程信息-明细
        tbLogisticsListToHeadRouteDetService.delByLhrCodeAndLhrOddNumb(lhrCode, lhrOddNumb);
        //4.7根据批次号和快递单号 删除物流单头程信息
        tbLogisticsListToHeadRouteService.delByLhrCodeAndLhrOddNumb(lhrCode, lhrOddNumb);


        return ResponseData.success();
    }

    /**
     * 退回物流单
     * @param lhrCode
     * @param lhrOddNumb
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @DataSource(name = "logistics")
    @Override
    public ResponseData returnLogisticsOrder(String lhrCode, String lhrOddNumb) {

        //发货批次号，物流单号，对应的出货清单号，如果某个出货清单号，对应多个发货批次号，或者对应多个物流单号，则不允许回退操作
        List<TbLogisticsListToHeadRouteDet> routeDetList = this.getToHeadRouteDets(lhrCode, lhrOddNumb);
        List<String> packCodeList = routeDetList.stream().map(TbLogisticsListToHeadRouteDet::getPackCode).distinct().collect(Collectors.toList());
        ResponseData checkedData = this.checkData(lhrCode, lhrOddNumb, packCodeList);
        if (! checkedData.getSuccess()){
            return checkedData;
        }

        String state = "返仓";

        //1--推送物流状态至海外仓出库任务
        this.updateLogisticsStatusToMCMS(lhrCode, lhrOddNumb,"");

        //2--推送物流单删除信息至海外仓入库任务
        this.deleteLogisticsToMCMS(lhrCode, lhrOddNumb);

        //3--推送删除物流实际结算信息
        this.deleteLogisticsSettlement(lhrCode);
        //4--更新物流单
        //4.1更新发货单货运方式 和物流方式
        tbBscOverseasWayService.updBscOverseasWayDeliveryType(lhrCode, lhrOddNumb, null, state);

        //4.2根据批次号和快递单号 重置出货清单明细1数据
        tbLogisticsPackingListDet1Service.resetData(lhrCode, lhrOddNumb);

        //4.3根据批次号和快递单号 更新出货清单信息 状态
        tbLogisticsPackingListService.updatePackLogState(lhrCode, lhrOddNumb,state);

        //4.4根据批次号和快递单号 更新物流单尾程信息-明细
        tbLogisticsListToEndRouteDetService.upByLhrCodeAndLhrOddNumb(lhrCode, lhrOddNumb,state);
        //4.5根据批次号和快递单号 更新物流单尾程信息
        tbLogisticsListToEndRouteService.upByLhrCodeAndLhrOddNumb(lhrCode, lhrOddNumb,state,null);
        //4.6根据批次号和快递单号 更新物流单头程信息-明细
        tbLogisticsListToHeadRouteDetService.upByLhrCodeAndLhrOddNumb(lhrCode, lhrOddNumb,state);
        //4.7根据批次号和快递单号 更新物流单头程信息
        tbLogisticsListToHeadRouteService.upByLhrCodeAndLhrOddNumb(lhrCode, lhrOddNumb,state);



        LambdaUpdateWrapper<TbLogisticsPackingList> updateChainWrapper = new LambdaUpdateWrapper<>();
        updateChainWrapper.in(TbLogisticsPackingList::getPackCode, packCodeList)
                .set(TbLogisticsPackingList::getPackLogState, "已返仓");
        tbLogisticsPackingListService.update( updateChainWrapper);

        LambdaUpdateWrapper<TbLogisticsPackingListDet1> det1UpdateChainWrapper = new LambdaUpdateWrapper<>();
        det1UpdateChainWrapper.in(TbLogisticsPackingListDet1::getPackCode, packCodeList)
                .set(TbLogisticsPackingListDet1::getPackDetBoxLogState, "已返仓");
        tbLogisticsPackingListDet1Service.update( det1UpdateChainWrapper);

        //更新发货清单物流发货状态
        for (String packCode : packCodeList) {
            tbBscOverseasWayService.updBscOverseasWayDeliverStatus(packCode,"已返仓");
        }
        return ResponseData.success("此票单退回返仓成功！");
    }


    /**
     * 更新物流单信息
     * @param request
     * @return
     */
//    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @DataSource(name = "logistics")
    @Override
    public ResponseData updateLogisticsOrder(TbLogisticsListToHeadRouteParam request) {
      
        ResponseData updateResult = tbLogisticsPackingListService.updateLogisticsOrder(request);
        if (!updateResult.getSuccess()){
            return updateResult;
        }
        //推送物流状态至海外仓出库任务
        ResponseData responseData1 = this.updateLogisticsStatusToMCMS(request.getLhrCode(), request.getLhrOddNumb(), request.getLogTraMode1());

        //推送海外仓入库任务物流单信息
        ResponseData responseData = this.pushLogisticsToMCMS(request);

        //推送物流实际结算任务信息
        EbmsLogisticsSettlementParam param= (EbmsLogisticsSettlementParam) updateResult.getData();
        List<EbmsLogisticsSettlementParam> paramList = new ArrayList<>();
        paramList.add(param);
        ResponseData responseData2 = logisticsSettlementService.receiveLogisticsSettlement(paramList);
        return  responseData2 ;
    }

    /**
     * 签收
     * @param signParam
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResponseData sign(LogisticsSignParam signParam) {
        tbLogisticsListToHeadRouteService.upByLhrCodeAndLhrOddNumb(signParam.getLhrCode(), signParam.getLhrOddNumb(), LogisticsTrackStatusEnum.STATUS_6.getName());
        tbLogisticsListToHeadRouteDetService.upByLhrCodeAndLhrOddNumb(signParam.getLhrCode(), signParam.getLhrOddNumb(), LogisticsTrackStatusEnum.STATUS_6.getName());

        tbLogisticsListToEndRouteService.upByLhrCodeAndLhrOddNumb(signParam.getLhrCode(), signParam.getLhrOddNumb(), LogisticsTrackStatusEnum.STATUS_6.getName(),signParam.getLerSignDate());
        tbLogisticsListToEndRouteDetService.upByLhrCodeAndLhrOddNumb(signParam.getLhrCode(), signParam.getLhrOddNumb(), LogisticsTrackStatusEnum.STATUS_6.getName());
        return ResponseData.success();
    }

    @Transactional
    @DataSource(name = "logistics")
    @Override
    public ResponseData importExcel(MultipartFile file) {

        List<TbLogisticsBillManageImportParam> filterList = new ArrayList<>();

        if (ObjectUtil.isNull(file)) {
            return ResponseData.error("文件信息不能为空");
        }

        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<TbLogisticsBillManageImportParam>();
            EasyExcel.read(buffer, TbLogisticsBillManageImportParam.class, listener).headRowNumber(4).sheet()
                    .doRead();

            List<TbLogisticsBillManageImportParam> coverDataList = listener.getDataList();
            if (CollectionUtil.isEmpty(coverDataList)) {
                return ResponseData.error("导入数据为空，导入失败！");
            }

            //过滤必填字段数据
            List<TbLogisticsBillManageImportParam> lhrOddNumbList = coverDataList.stream().filter(d -> ObjectUtil.isEmpty(d.getLhrOddNumb())).collect(Collectors.toList());
            if (CollectionUtil.isEmpty(lhrOddNumbList)) {
                return ResponseData.error("字段[头程物流单号*]，不能为空，导入失败！");
            }

            List<TbLogisticsBillManageImportParam> logComfirmBillVolumeList = coverDataList.stream().filter(d -> ObjectUtil.isEmpty(d.getLogComfirmBillVolume())).collect(Collectors.toList());
            if (CollectionUtil.isEmpty(logComfirmBillVolumeList)) {
                return ResponseData.error("字段[确认计费量*]，不能为空，导入失败！");
            }

            List<TbLogisticsBillManageImportParam> lhrLogUnitPriceList = coverDataList.stream().filter(d -> ObjectUtil.isEmpty(d.getLhrLogUnitPrice())).collect(Collectors.toList());
            if (CollectionUtil.isEmpty(lhrLogUnitPriceList)) {
                return ResponseData.error("字段[单价*]，不能为空，导入失败！");
            }
            filterList = coverDataList;

        } catch (Exception e) {
            log.error("导入Excel处理异常，导入失败！", e);
            return ResponseData.error("导入Excel处理异常，导入失败！");
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入Excel关闭流异常", e);

                }
            }
        }

        //数据校验
        List<String> lhrOddNumbList = filterList.stream().map(TbLogisticsBillManageImportParam::getLhrOddNumb).distinct().collect(Collectors.toList());

        List<TbLogisticsListToHeadRoute> headRouteList = tbLogisticsListToHeadRouteService.list(new LambdaQueryWrapper<TbLogisticsListToHeadRoute>()
                .in(TbLogisticsListToHeadRoute::getLhrOddNumb, lhrOddNumbList));

        if (ObjectUtil.isEmpty(headRouteList)) {
            return ResponseData.error("导入数据[头程物流单号]未找到对应的头程信息，导入失败！");
        }

        List<TbLogisticsListToHeadRoute> lockList = headRouteList.stream().filter(h -> "已锁定".equals(h.getLhrLogStatus())).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(lockList)) {
            StringBuffer sb = new StringBuffer();
            sb.append("头程物流单号:[");
            lockList.forEach(h -> {
                sb.append(h.getLhrOddNumb()).append(",");
            });
            sb.append("]已锁定，不允许进行导入修改");
            return ResponseData.error(sb.toString());
        }

        List<String> dbLhrOddNumbList = headRouteList.stream().map(TbLogisticsListToHeadRoute::getLhrOddNumb).distinct().collect(Collectors.toList());

        lhrOddNumbList.removeAll(dbLhrOddNumbList);
        if (CollectionUtil.isNotEmpty(lhrOddNumbList)) {
            StringBuffer sb = new StringBuffer();
            sb.append("头程物流单号:[");
            lhrOddNumbList.forEach(h -> {
                sb.append(h).append(",");
            });
            sb.append("]系统不存在，不允许进行导入修改");
            return ResponseData.error(sb.toString());
        }

        //处理业务
      return   this.importLogisticsBill(filterList);

    }

    private ResponseData importLogisticsBill(List<TbLogisticsBillManageImportParam> importParamList) {
        for (TbLogisticsBillManageImportParam l : importParamList) {

            try {
                TbLogisticsListToHeadRoute model = tbLogisticsListToHeadRouteService.getOne(new LambdaQueryWrapper<TbLogisticsListToHeadRoute>()
                        .eq(TbLogisticsListToHeadRoute::getLhrOddNumb, l.getLhrOddNumb()));
                // 单价
                model.setLhrLogUnitPrice(l.getLhrLogUnitPrice());

                //确认计费量
                model.setLogComfirmBillVolume(l.getLogComfirmBillVolume());
                model.setLhrLogFeeWeight(l.getLogComfirmBillVolume());

                //燃油附加费
                model.setLhrLogFuelFee(l.getLhrLogFuelFee() != null ? l.getLhrLogFuelFee(): BigDecimal.ZERO);

                //旺季附加费
                model.setLhrLogBusySeasonAddFee(l.getLhrLogBusySeasonAddFee() != null ? l.getLhrLogBusySeasonAddFee() :BigDecimal.ZERO);

                //附加费及杂费
                model.setLhrLogAddAndSundryFee( l.getLhrLogAddAndSundryFee() != null ? l.getLhrLogAddAndSundryFee() : BigDecimal.ZERO);

                //附加费及杂费备注
                model.setLhrPreLogAddAndSundryFeeRemark(l.getLhrPreLogAddAndSundryFeeRemark());

                //报关费
                model.setLhrLogCustDlearanceFee(l.getLhrLogCustDlearanceFee() != null ? l.getLhrLogCustDlearanceFee() :BigDecimal.ZERO);

                //清关费
                model.setLhrLogCustClearanceFee(l.getLhrLogCustClearanceFee() != null ? l.getLhrLogCustClearanceFee() :BigDecimal.ZERO);

                //税费
                model.setLhrLogTaxFee(l.getLhrLogTaxFee() != null ? l.getLhrLogTaxFee() :BigDecimal.ZERO);

                //预估税费
                model.setLhrPreLogTaxFee( l.getLhrLogTaxFee() != null ? l.getLhrLogTaxFee() :BigDecimal.ZERO);

                // 物流费
                model.setLhrLogFee( model.getLhrLogUnitPrice().multiply(model.getLogComfirmBillVolume()).setScale(2, RoundingMode.HALF_UP) );

                // 预估物流费
                model.setLhrPreLogFee(model.getLhrPreLogUnitPrice().multiply(model.getLogComfirmBillVolume()).setScale(2, RoundingMode.HALF_UP));

                //总费用
                model.setLhrLogFeeTotalNew(
                            model.getLhrLogBusySeasonAddFee()
                            .add(model.getLhrLogAddAndSundryFee())
                            .add(model.getLhrLogTaxFee())
                            .add(model.getLhrLogCustDlearanceFee())
                            .add(model.getLhrLogCustClearanceFee())
                            .add(model.getLhrLogFee())
                            .add(model.getLhrLogFuelFee()
                            .setScale(2, RoundingMode.HALF_UP))
                );

                //预估总费用
                model.setLhrPreLogFeeTotalNew(
                                model.getLhrPreLogBusySeasonAddFee()
                                .add(model.getLhrPreLogAddAndSundryFee())
                                .add(model.getLhrPreLogTaxFee())
                                .add(model.getLhrPreLogCustDlearanceFee())
                                .add(model.getLhrPreLogCustClearanceFee())
                                .add(model.getLhrPreLogFee())
                                .add(model.getLhrPreLogFuelFee())
                                .setScale(2, RoundingMode.HALF_UP)
                        );
                //头程物流单更新
                tbLogisticsListToHeadRouteService.updateById(model);


                //尾程单信息更新
                TbLogisticsListToEndRoute modelEnd = tbLogisticsListToEndRouteService.getOne(new LambdaQueryWrapper<TbLogisticsListToEndRoute>()
                        .eq(TbLogisticsListToEndRoute::getLhrOddNumb, l.getLhrOddNumb()));
                // 单价
                modelEnd.setLerLogUnitPrice(model.getLhrLogUnitPrice());

                //确认计费量
                modelEnd.setLerLogFeeWeight(l.getLogComfirmBillVolume());

                //燃油附加费
                modelEnd.setLerLogFuelFee(model.getLhrLogFuelFee());

                //旺季附加费
                modelEnd.setLerLogBusySeasonAddFee(model.getLhrLogBusySeasonAddFee());

                //附加费及杂费
                modelEnd.setLerLogAddAndSundryFee(model.getLhrLogAddAndSundryFee());

                //附加费及杂费备注
                modelEnd.setLerPreLogAddAndSundryFeeRemark(model.getLhrPreLogAddAndSundryFeeRemark());

                //报关费
                modelEnd.setLerLogCustDlearanceFee(model.getLhrLogCustDlearanceFee());

                //清关费
                modelEnd.setLerLogCustClearanceFee(model.getLhrLogCustClearanceFee());

                //税费
                modelEnd.setLerLogTaxFee(model.getLhrLogTaxFee());

                //预估税费
                modelEnd.setLerPreLogTaxFee(model.getLhrPreLogTaxFee());

                // 物流费
                modelEnd.setLerLogFee(model.getLhrLogFee());

                // 预估物流费
                modelEnd.setLerPreLogFee(model.getLhrPreLogFee());

                //总费用
                modelEnd.setLerLogFeeTotalNew(model.getLhrLogFeeTotalNew());

                //预估总费用
                modelEnd.setLerPreLogFeeTotalNew(model.getLhrPreLogFeeTotalNew());

                //尾程物流单更新
                tbLogisticsListToEndRouteService.updateById(modelEnd);


                //推送物流实际结算任务
                List<TbLogisticsListToHeadRouteDetParam> headerRouteDetList = new ArrayList<>();

                List<TbLogisticsListToHeadRouteDet> routeDets = tbLogisticsListToHeadRouteDetService.queryByLhrCode(model.getLhrCode());
                for (TbLogisticsListToHeadRouteDet routeDet : routeDets) {
                    TbLogisticsListToHeadRouteDetParam routeDetParam = new TbLogisticsListToHeadRouteDetParam();
                    BeanUtil.copyProperties(routeDet, routeDetParam);
                    headerRouteDetList.add(routeDetParam);
                }

                ResponseData responseData = tbLogisticsPackingListService.receiveLogisticsSettlement(headerRouteDetList, model, modelEnd, model.getLogComfirmBillVolume());
                if (!responseData.getSuccess()) {
                    return responseData;
                }
            } catch (Exception e) {
                return ResponseData.error("导入更新异常：" + e.getMessage());
            }
        }

        return ResponseData.success("导入更新成功");
    }


    private ResponseData pushLogisticsToMCMS(TbLogisticsListToHeadRouteParam request) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        List<TbLogisticsPackingList> packingLists = tbLogisticsPackingListService.queryByLhrCode(request.getLhrCode());

        List<TbLogisticsPackingListDet2> packingListDet2s = tbLogisticsPackingListDet2Service.queryByLhrCode(request.getLhrCode());
        List<EbmsOiwLogisticsParam> paramList = new ArrayList<>();
        for (TbLogisticsPackingList packingList : packingLists) {
            if ("海外仓".equals(packingList.getPackStoreHouseType())) {
                List<TbLogisticsPackingListDet2> det2List = packingListDet2s.stream().filter(d -> d.getPackCode().equals(packingList.getPackCode())).collect(Collectors.toList());
                for (TbLogisticsPackingListDet2 det2 : det2List) {
                    EbmsOiwLogisticsParam param = new EbmsOiwLogisticsParam();
                    param.setInOrder(det2.getPackCode());
                    param.setPackageNum(BigDecimal.valueOf(det2.getPackDetBoxNum()));
                    param.setLogisticsCompany(request.getLogTraMode1());
                    param.setLogisticsNum(request.getLhrOddNumb());
                    param.setOperator(loginUser.getAccount()+"_"+loginUser.getName());
                    paramList.add(param);
                }
            }
        }
        ResponseData responseData = overseasInWarehouseService.updateLogistics(paramList);
        return responseData;
    }

    private ResponseData checkData(String lhrCode, String lhrOddNumb, List<String> packCodeList) {
        LambdaQueryWrapper<TbLogisticsListToHeadRouteDet> detWrapper = new LambdaQueryWrapper<TbLogisticsListToHeadRouteDet>()
                .in(TbLogisticsListToHeadRouteDet::getPackCode, packCodeList);
        List<TbLogisticsListToHeadRouteDet> checkDataList = tbLogisticsListToHeadRouteDetService.list(detWrapper);
        List<TbLogisticsListToHeadRouteDet> oddNumbRepeatData = checkDataList.stream()
                .filter(d -> ObjectUtil.isNotEmpty(d.getLhrOddNumb()) && !d.getLhrOddNumb().equals(lhrOddNumb)).collect(Collectors.toList());
        if (ObjectUtil.isNotEmpty(oddNumbRepeatData)) {
            return ResponseData.error("对应出货清单存在多个物流单号,不能回退");
        }

        List<TbLogisticsListToHeadRouteDet> lhrCodeRepeatData = checkDataList.stream()
                .filter(d -> ObjectUtil.isNotEmpty(d.getLhrCode()) && !d.getLhrCode().equals(lhrCode)).collect(Collectors.toList());
        if (ObjectUtil.isNotEmpty(oddNumbRepeatData)) {
            return ResponseData.error("对应出货清单存在多个发货批次,不能回退");
        }
        return ResponseData.success();
    }

    private void deleteLogisticsSettlement(String lhrCode) {
        List<LogisticsSettlementDetailParam> paramList = new ArrayList<>();
        LogisticsSettlementDetailParam settlementParam = LogisticsSettlementDetailParam.builder().shipmentNum(lhrCode).build();
        paramList.add(settlementParam);
        logisticsSettlementService.deleteLogisticsSettlement(paramList);
    }

    /**
     * 推送物流单删除信息至海外仓入库任务
     * @param lhrCode
     * @param lhrOddNumb
     * @return
     */
    private ResponseData deleteLogisticsToMCMS(String lhrCode, String lhrOddNumb) {
        List<TbLogisticsListToHeadRouteDet> routeDets = tbLogisticsListToHeadRouteDetService.queryByLhrCode(lhrCode);
        if (ObjectUtil.isEmpty(routeDets)) {
            return ResponseData.success();
        }

        List<EbmsOiwLogisticsParam> paramList = new ArrayList<>();

        List<String> packCodeList = routeDets.stream().map(d -> d.getPackCode()).distinct().collect(Collectors.toList());

        LoginUser loginUser = LoginContext.me().getLoginUser();

        for (String packCode : packCodeList) {
            TbLogisticsPackingList packingList = tbLogisticsPackingListService.queryById(packCode);
            if (! "海外仓".equals(packingList.getPackStoreHouseType())) {
                continue;
            }
            EbmsOiwLogisticsParam param = new EbmsOiwLogisticsParam();
            param.setInOrder(packCode);
            param.setLogisticsNum(lhrOddNumb);
            param.setOperator(loginUser.getAccount()+"_"+loginUser.getName());
            paramList.add(param);
        }
        return overseasInWarehouseService.deleteLogistics(paramList);
    }

    /**
     * 推送物流状态至海外仓出库任务
     *
     * @param lhrCode
     * @param lhrOddNumb
     * @param logTraMode1
     * @param lhrOddNumb
     * @return
     */
    private ResponseData updateLogisticsStatusToMCMS(String lhrCode, String lhrOddNumb,String logTraMode1 ) {
        List<TbLogisticsListToHeadRouteDet> routeDets = this.getToHeadRouteDets(lhrCode, lhrOddNumb);
        if (ObjectUtil.isEmpty(routeDets)) {
            return ResponseData.success();
        }

        List<TbLogisticsListToHeadRouteDet> ckqdList = routeDets.stream().filter(r -> r.getPackCode().contains("CKQD")).collect(Collectors.toList());

        if (ObjectUtil.isEmpty(ckqdList)) {
            return ResponseData.success();
        }

        List<EbmsOverseasOutWarehouseLogisticsParam> params = new ArrayList<>();
        for (TbLogisticsListToHeadRouteDet routeDet : routeDets) {
            EbmsOverseasOutWarehouseLogisticsParam logisticsParam = new EbmsOverseasOutWarehouseLogisticsParam();
            logisticsParam.setLogisticsCompany(logTraMode1);
            logisticsParam.setLogisticsNum(lhrOddNumb);
            logisticsParam.setPackCode(routeDet.getPackCode());
            logisticsParam.setPackDetBoxCode(routeDet.getPackDetBoxCode());
            logisticsParam.setPackDetBoxNum(BigDecimal.valueOf(routeDet.getPackDetBoxNum()));
            params.add(logisticsParam);
        }

       return overseasOutWarehouseService.receiveLogisticsByEBMS(params);
    }

    private List<TbLogisticsListToHeadRouteDet> getToHeadRouteDets(String lhrCode, String lhrOddNumb) {
        List<TbLogisticsListToHeadRouteDet> routeDets = tbLogisticsListToHeadRouteDetService.queryByLhrCode(lhrCode);
        if (ObjectUtil.isNotEmpty(lhrOddNumb)) {
            routeDets=  routeDets.stream().filter(det -> det.getLhrOddNumb().equals(lhrOddNumb)).collect(Collectors.toList());
        }
        return routeDets;
    }
}
