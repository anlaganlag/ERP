package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.toolkit.MPJWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsListToEndRoute;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsListToHeadRoute;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsProvider;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsListToHeadRouteMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsListToHeadRouteParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsBillManageExportResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsListToHeadRouteResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsListToHeadRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
 /**
 * 物流单头程信息;(tb_logistics_list_to_head_route)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsListToHeadRouteServiceImpl  extends ServiceImpl<TbLogisticsListToHeadRouteMapper, TbLogisticsListToHeadRoute> implements TbLogisticsListToHeadRouteService{
    @Resource
    private TbLogisticsListToHeadRouteMapper tbLogisticsListToHeadRouteMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsListToHeadRoute queryById(BigDecimal id){
        return tbLogisticsListToHeadRouteMapper.selectById(id);
    }
    
    /**
     * 分页查询
     *
     * @param param 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public Page<TbLogisticsListToHeadRouteResult> paginQuery(TbLogisticsListToHeadRouteParam param, long current, long size){
        //1. 构建动态查询条件
//        LambdaQueryWrapper<TbLogisticsListToHeadRoute> queryWrapper = new MPJLambdaQueryWrapper<TbLogisticsListToHeadRoute<>();
        MPJLambdaWrapper<TbLogisticsListToHeadRoute> queryWrapper = MPJWrappers.<TbLogisticsListToHeadRoute>lambdaJoin();
        queryWrapper.selectAll(TbLogisticsListToHeadRoute.class);
        queryWrapper.select(TbLogisticsProvider::getLpName);
        queryWrapper.select(TbLogisticsListToEndRoute::getLerSignDate);
        queryWrapper.leftJoin(TbLogisticsProvider.class, TbLogisticsProvider::getLpCode, TbLogisticsListToHeadRoute::getLpCode);
        queryWrapper.leftJoin(TbLogisticsListToEndRoute.class,
                on->on.eq(TbLogisticsListToEndRoute::getLhrCode, TbLogisticsListToHeadRoute::getLhrCode)
                        .eq(TbLogisticsListToEndRoute::getLhrOddNumb,TbLogisticsListToHeadRoute::getLhrOddNumb)
        );
        //物流商名称 :多选精准查询 lpName
        if (ObjectUtil.isNotEmpty(param.getLpName()) || ObjectUtil.isNotEmpty(param.getLpNameList())) {
            queryWrapper.in(ObjectUtil.isNotEmpty( param.getLpNameList()),TbLogisticsProvider::getLpName, param.getLpNameList());
            queryWrapper.eq(ObjectUtil.isNotEmpty( param.getLpName()),TbLogisticsProvider::getLpName, param.getLpName());
        }

        //物流简称 :多选精准查询 lpSimpleName
        if (ObjectUtil.isNotEmpty(param.getLpSimpleName()) || ObjectUtil.isNotEmpty(param.getLpSimpleNameList())) {
            queryWrapper.in(ObjectUtil.isNotEmpty( param.getLpSimpleNameList()),TbLogisticsProvider::getLpSimpleName, param.getLpSimpleNameList());
            queryWrapper.eq(ObjectUtil.isNotEmpty( param.getLpSimpleName()),TbLogisticsProvider::getLpSimpleName, param.getLpSimpleName());
        }
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getElePlatformName()),TbLogisticsListToHeadRoute::getElePlatformName, param.getElePlatformName());//电商平台
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShopNameSimple()),TbLogisticsListToHeadRoute::getShopNameSimple, param.getShopNameSimple());//账号
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCountryCode()),TbLogisticsListToHeadRoute::getCountryCode, param.getCountryCode());//站点
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpSimpleName()),TbLogisticsListToHeadRoute::getLpSimpleName, param.getLpSimpleName());//物流商简称

        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogTraMode2()),TbLogisticsListToHeadRoute::getLogTraMode2, param.getLogTraMode2());//运输方式-->发货方式
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLhrOddNumb()),TbLogisticsListToHeadRoute::getLhrOddNumb, param.getLhrOddNumb());//物流单号 单个
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getLhrOddNumbList()),TbLogisticsListToHeadRoute::getLhrOddNumb, param.getLhrOddNumbList());//物流单号 多个
        queryWrapper.ge(ObjectUtil.isNotNull(param.getLhrSendGoodDateStart()),TbLogisticsListToHeadRoute::getLhrSendGoodDate, param.getLhrSendGoodDateStart());//发货日期 --开始时间
        if (ObjectUtil.isNotNull(param.getLhrSendGoodDateEnd())) {
        queryWrapper.le(TbLogisticsListToHeadRoute::getLhrSendGoodDate, DateUtil.endOfDay(param.getLhrSendGoodDateEnd()));//发货日期 --结束时间
        }

        //2. 执行分页查询
        Page<TbLogisticsListToHeadRouteResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsListToHeadRouteResult> selectResult = tbLogisticsListToHeadRouteMapper.selectJoinPage(pagin ,TbLogisticsListToHeadRouteResult.class, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsListToHeadRoute 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsListToHeadRoute insert(TbLogisticsListToHeadRoute tbLogisticsListToHeadRoute){
        tbLogisticsListToHeadRouteMapper.insert(tbLogisticsListToHeadRoute);
        return tbLogisticsListToHeadRoute;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsListToHeadRoute update(TbLogisticsListToHeadRouteParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsListToHeadRoute> wrapper = new LambdaUpdateChainWrapper<TbLogisticsListToHeadRoute>(tbLogisticsListToHeadRouteMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrCode()),TbLogisticsListToHeadRoute::getLhrCode, param.getLhrCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getSysPerCode()),TbLogisticsListToHeadRoute::getSysPerCode, param.getSysPerCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsListToHeadRoute::getSysPerName, param.getSysPerName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getComWaitArea()),TbLogisticsListToHeadRoute::getComWaitArea, param.getComWaitArea());
         wrapper.set(ObjectUtil.isNotEmpty(param.getElePlatformName()),TbLogisticsListToHeadRoute::getElePlatformName, param.getElePlatformName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShopNameSimple()),TbLogisticsListToHeadRoute::getShopNameSimple, param.getShopNameSimple());
         wrapper.set(ObjectUtil.isNotEmpty(param.getCountryCode()),TbLogisticsListToHeadRoute::getCountryCode, param.getCountryCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackStoreHouseType()),TbLogisticsListToHeadRoute::getPackStoreHouseType, param.getPackStoreHouseType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackStoreHouseName()),TbLogisticsListToHeadRoute::getPackStoreHouseName, param.getPackStoreHouseName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrState()),TbLogisticsListToHeadRoute::getLhrState, param.getLhrState());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrStateNote()),TbLogisticsListToHeadRoute::getLhrStateNote, param.getLhrStateNote());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrDataSynState()),TbLogisticsListToHeadRoute::getLhrDataSynState, param.getLhrDataSynState());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLcCode()),TbLogisticsListToHeadRoute::getLcCode, param.getLcCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLpCode()),TbLogisticsListToHeadRoute::getLpCode, param.getLpCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLpSimpleName()),TbLogisticsListToHeadRoute::getLpSimpleName, param.getLpSimpleName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getComNameCn()),TbLogisticsListToHeadRoute::getComNameCn, param.getComNameCn());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrOddNumb()),TbLogisticsListToHeadRoute::getLhrOddNumb, param.getLhrOddNumb());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLspNum()),TbLogisticsListToHeadRoute::getLspNum, param.getLspNum());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrDesThrCharCode()),TbLogisticsListToHeadRoute::getLhrDesThrCharCode, param.getLhrDesThrCharCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getCountryAreaName()),TbLogisticsListToHeadRoute::getCountryAreaName, param.getCountryAreaName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogRecCountry()),TbLogisticsListToHeadRoute::getLogRecCountry, param.getLogRecCountry());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogRecCity()),TbLogisticsListToHeadRoute::getLogRecCity, param.getLogRecCity());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogRecState()),TbLogisticsListToHeadRoute::getLogRecState, param.getLogRecState());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogRecAddress1()),TbLogisticsListToHeadRoute::getLogRecAddress1, param.getLogRecAddress1());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogRecAddress2()),TbLogisticsListToHeadRoute::getLogRecAddress2, param.getLogRecAddress2());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogRecAddress3()),TbLogisticsListToHeadRoute::getLogRecAddress3, param.getLogRecAddress3());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogRecZip()),TbLogisticsListToHeadRoute::getLogRecZip, param.getLogRecZip());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogRecPerson()),TbLogisticsListToHeadRoute::getLogRecPerson, param.getLogRecPerson());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogRecPersonTel()),TbLogisticsListToHeadRoute::getLogRecPersonTel, param.getLogRecPersonTel());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogRecCompany()),TbLogisticsListToHeadRoute::getLogRecCompany, param.getLogRecCompany());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogTraMode1()),TbLogisticsListToHeadRoute::getLogTraMode1, param.getLogTraMode1());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogHeadRouteLink()),TbLogisticsListToHeadRoute::getLogHeadRouteLink, param.getLogHeadRouteLink());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogTraMode2()),TbLogisticsListToHeadRoute::getLogTraMode2, param.getLogTraMode2());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogSeaTraRoute()),TbLogisticsListToHeadRoute::getLogSeaTraRoute, param.getLogSeaTraRoute());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogSeaTraConType()),TbLogisticsListToHeadRoute::getLogSeaTraConType, param.getLogSeaTraConType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogRedOrBlueList()),TbLogisticsListToHeadRoute::getLogRedOrBlueList, param.getLogRedOrBlueList());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogGoodCharacter()),TbLogisticsListToHeadRoute::getLogGoodCharacter, param.getLogGoodCharacter());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrNote()),TbLogisticsListToHeadRoute::getLhrNote, param.getLhrNote());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrPosition()),TbLogisticsListToHeadRoute::getLhrPosition, param.getLhrPosition());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrCustBroker()),TbLogisticsListToHeadRoute::getLhrCustBroker, param.getLhrCustBroker());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogIsDeferred()),TbLogisticsListToHeadRoute::getLogIsDeferred, param.getLogIsDeferred());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogComfirmBillType()),TbLogisticsListToHeadRoute::getLogComfirmBillType, param.getLogComfirmBillType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrOverseasConsignee()),TbLogisticsListToHeadRoute::getLhrOverseasConsignee, param.getLhrOverseasConsignee());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrPreLogFeeIsConfirm()),TbLogisticsListToHeadRoute::getLhrPreLogFeeIsConfirm, param.getLhrPreLogFeeIsConfirm());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogComfirmBillCurrency()),TbLogisticsListToHeadRoute::getLogComfirmBillCurrency, param.getLogComfirmBillCurrency());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrPreLogUnitPriceType()),TbLogisticsListToHeadRoute::getLhrPreLogUnitPriceType, param.getLhrPreLogUnitPriceType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrPreLogAddAndSundryFeeRemark()),TbLogisticsListToHeadRoute::getLhrPreLogAddAndSundryFeeRemark, param.getLhrPreLogAddAndSundryFeeRemark());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsListToHeadRoute::getId, param.getId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal id){
        int total = tbLogisticsListToHeadRouteMapper.deleteById(id);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param idList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> idList) {
        int delCount = tbLogisticsListToHeadRouteMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

     /**
      * 根据批次号和快递单号 删除物流单头程信息
      * @param lhrCode
      * @param lhrOddNumb
      * @return
      */
     @DataSource(name = "logistics")
     @Override
     public int delByLhrCodeAndLhrOddNumb(String lhrCode, String lhrOddNumb) {
         return  tbLogisticsListToHeadRouteMapper.delete(
                 new LambdaQueryWrapper<TbLogisticsListToHeadRoute>()
                 .eq(TbLogisticsListToHeadRoute::getLhrCode, lhrCode)
                 .eq(TbLogisticsListToHeadRoute::getLhrOddNumb, lhrOddNumb));

     }

     /**
      * 根据批次号和快递单号 更新 物流单头程信息 状态
      * @param lhrCode
      * @param lhrOddNumb
      * @return
      */
     @DataSource(name = "logistics")
     @Override
     public int upByLhrCodeAndLhrOddNumb(String lhrCode, String lhrOddNumb, String state) {
         return tbLogisticsListToHeadRouteMapper.update(null,
                 new LambdaUpdateWrapper<>(TbLogisticsListToHeadRoute.class)
                         .eq(TbLogisticsListToHeadRoute::getLhrCode, lhrCode)
                         .eq(TbLogisticsListToHeadRoute::getLhrOddNumb, lhrOddNumb)
                         .set(TbLogisticsListToHeadRoute::getLhrState, state));
     }
     @DataSource(name = "logistics")
     @Override
     public List<TbLogisticsListToHeadRoute> queryByLhrCode(String lhrCode) {
         return tbLogisticsListToHeadRouteMapper.selectList(
                 new LambdaQueryWrapper<>(TbLogisticsListToHeadRoute.class)
                         .eq(TbLogisticsListToHeadRoute::getLhrCode, lhrCode));
     }

     @DataSource(name = "logistics")
     @Override
     public List<TbLogisticsBillManageExportResult> billManageExport(TbLogisticsListToHeadRouteParam param) {
         //1. 构建动态查询条件
//        LambdaQueryWrapper<TbLogisticsListToHeadRoute> queryWrapper = new MPJLambdaQueryWrapper<TbLogisticsListToHeadRoute<>();
         MPJLambdaWrapper<TbLogisticsListToHeadRoute> queryWrapper = MPJWrappers.<TbLogisticsListToHeadRoute>lambdaJoin();
         queryWrapper.selectAll(TbLogisticsListToHeadRoute.class);
         queryWrapper.select(TbLogisticsProvider::getLpName);
         queryWrapper.select(TbLogisticsListToEndRoute::getLerSignDate,
                 TbLogisticsListToEndRoute::getLerLogComfirmBillCurrency,
                 TbLogisticsListToEndRoute::getLerLogUnitPrice,
                 TbLogisticsListToEndRoute::getLerLogFee,
                 TbLogisticsListToEndRoute::getLerLogFuelFee,
                 TbLogisticsListToEndRoute::getLerLogBusySeasonAddFee,
                 TbLogisticsListToEndRoute::getLerLogAddAndSundryFee,
                 TbLogisticsListToEndRoute::getLerLogCustDlearanceFee,
                 TbLogisticsListToEndRoute::getLerLogCustClearanceFee,
                 TbLogisticsListToEndRoute::getLerLogTaxFee,
                 TbLogisticsListToEndRoute::getLerPreLogFeeTotalNew,
                 TbLogisticsListToEndRoute::getLerLogFeeTotalNew
         );
         queryWrapper.leftJoin(TbLogisticsProvider.class, TbLogisticsProvider::getLpCode, TbLogisticsListToHeadRoute::getLpCode);
         queryWrapper.leftJoin(TbLogisticsListToEndRoute.class,
                 on->on.eq(TbLogisticsListToEndRoute::getLhrCode, TbLogisticsListToHeadRoute::getLhrCode)
                         .eq(TbLogisticsListToEndRoute::getLhrOddNumb,TbLogisticsListToHeadRoute::getLhrOddNumb)
         );
         //物流商名称 :多选精准查询 lpName
         if (ObjectUtil.isNotEmpty(param.getLpName()) || ObjectUtil.isNotEmpty(param.getLpNameList())) {
             queryWrapper.in(ObjectUtil.isNotEmpty( param.getLpNameList()),TbLogisticsProvider::getLpName, param.getLpNameList());
             queryWrapper.eq(ObjectUtil.isNotEmpty( param.getLpName()),TbLogisticsProvider::getLpName, param.getLpName());
         }

         //物流简称 :多选精准查询 lpSimpleName
         if (ObjectUtil.isNotEmpty(param.getLpSimpleName()) || ObjectUtil.isNotEmpty(param.getLpSimpleNameList())) {
             queryWrapper.in(ObjectUtil.isNotEmpty( param.getLpSimpleNameList()),TbLogisticsProvider::getLpSimpleName, param.getLpSimpleNameList());
             queryWrapper.eq(ObjectUtil.isNotEmpty( param.getLpSimpleName()),TbLogisticsProvider::getLpSimpleName, param.getLpSimpleName());
         }
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getElePlatformName()),TbLogisticsListToHeadRoute::getElePlatformName, param.getElePlatformName());//电商平台
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShopNameSimple()),TbLogisticsListToHeadRoute::getShopNameSimple, param.getShopNameSimple());//账号
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCountryCode()),TbLogisticsListToHeadRoute::getCountryCode, param.getCountryCode());//站点
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpSimpleName()),TbLogisticsListToHeadRoute::getLpSimpleName, param.getLpSimpleName());//物流商简称

         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogTraMode2()),TbLogisticsListToHeadRoute::getLogTraMode2, param.getLogTraMode2());//运输方式-->发货方式
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLhrOddNumb()),TbLogisticsListToHeadRoute::getLhrOddNumb, param.getLhrOddNumb());//物流单号 单个
         queryWrapper.in(ObjectUtil.isNotEmpty(param.getLhrOddNumbList()),TbLogisticsListToHeadRoute::getLhrOddNumb, param.getLhrOddNumbList());//物流单号 多个
         queryWrapper.ge(ObjectUtil.isNotNull(param.getLhrSendGoodDateStart()),TbLogisticsListToHeadRoute::getLhrSendGoodDate, param.getLhrSendGoodDateStart());//发货日期 --开始时间
         if (ObjectUtil.isNotNull(param.getLhrSendGoodDateEnd())) {
             queryWrapper.le(TbLogisticsListToHeadRoute::getLhrSendGoodDate, DateUtil.endOfDay(param.getLhrSendGoodDateEnd()));//发货日期 --结束时间
         }

         //2. 执行查询
         List<TbLogisticsBillManageExportResult> resultList = tbLogisticsListToHeadRouteMapper.selectJoinList(TbLogisticsBillManageExportResult.class, queryWrapper);
         return resultList;
     }
 }