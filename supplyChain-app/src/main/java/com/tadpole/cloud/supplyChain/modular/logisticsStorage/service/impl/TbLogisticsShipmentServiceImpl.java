package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShop;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackingList;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsShipment;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsShipmentDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsPackingListDet2Mapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsShipmentMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsShipmentParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsShipmentResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsPackingListService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsShipmentDetService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsShipmentService;
import com.tadpole.cloud.supplyChain.modular.manage.consumer.ShopEntityConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 亚马逊发货申请记录-EBMS形成记录;(tb_logistics_shipment)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsShipmentServiceImpl  extends ServiceImpl<TbLogisticsShipmentMapper, TbLogisticsShipment> implements TbLogisticsShipmentService {
    @Resource
    private TbLogisticsShipmentMapper tbLogisticsShipmentMapper;

     @Resource
     private TbLogisticsShipmentDetService tbLogisticsShipmentDetService;

     @Resource
     TbLogisticsPackingListDet2Mapper tbLogisticsPackingListDet2Mapper;

     @Resource
    TbLogisticsPackingListService tbLogisticsPackingListService;

     @Resource
     private ShopEntityConsumer shopEntityConsumer;


    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsShipment queryById(BigDecimal id){
        return tbLogisticsShipmentMapper.selectById(id);
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
    public Page<TbLogisticsShipmentResult> pageQuery(TbLogisticsShipmentParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsShipment> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackCode()),TbLogisticsShipment::getPackCode, param.getPackCode());//出货清单号
            queryWrapper.like(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsShipment::getSysPerName, param.getSysPerName());//员工姓名
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShopNameSimple()),TbLogisticsShipment::getShopNameSimple, param.getShopNameSimple());//账号
            queryWrapper.like(ObjectUtil.isNotEmpty(param.getPlanName()),TbLogisticsShipment::getPlanName, param.getPlanName());//发货计划名称
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCountryCode()),TbLogisticsShipment::getShipToCountry, param.getCountryCode());//站点
        queryWrapper.orderByDesc(TbLogisticsShipment::getSysUpdDatetime);

        //2. 执行分页查询
        Page<TbLogisticsShipmentResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsShipmentResult> selectResult = tbLogisticsShipmentMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsShipment 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsShipment insert(TbLogisticsShipment tbLogisticsShipment){
        tbLogisticsShipmentMapper.insert(tbLogisticsShipment);
        return tbLogisticsShipment;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsShipment update(TbLogisticsShipmentParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsShipment> wrapper = new LambdaUpdateChainWrapper<TbLogisticsShipment>(tbLogisticsShipmentMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackCode()),TbLogisticsShipment::getPackCode, param.getPackCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getSysPerCode()),TbLogisticsShipment::getSysPerCode, param.getSysPerCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsShipment::getSysPerName, param.getSysPerName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShopNameSimple()),TbLogisticsShipment::getShopNameSimple, param.getShopNameSimple());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPlanName()),TbLogisticsShipment::getPlanName, param.getPlanName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShipToCountry()),TbLogisticsShipment::getShipToCountry, param.getShipToCountry());
         wrapper.set(ObjectUtil.isNotEmpty(param.getAddressName()),TbLogisticsShipment::getAddressName, param.getAddressName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getAddressFieldOne()),TbLogisticsShipment::getAddressFieldOne, param.getAddressFieldOne());
         wrapper.set(ObjectUtil.isNotEmpty(param.getAddressFieldTwo()),TbLogisticsShipment::getAddressFieldTwo, param.getAddressFieldTwo());
         wrapper.set(ObjectUtil.isNotEmpty(param.getAddressCity()),TbLogisticsShipment::getAddressCity, param.getAddressCity());
         wrapper.set(ObjectUtil.isNotEmpty(param.getAddressCountryCode()),TbLogisticsShipment::getAddressCountryCode, param.getAddressCountryCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getAddressStateOrRegion()),TbLogisticsShipment::getAddressStateOrRegion, param.getAddressStateOrRegion());
         wrapper.set(ObjectUtil.isNotEmpty(param.getAddressDistrict()),TbLogisticsShipment::getAddressDistrict, param.getAddressDistrict());
         wrapper.set(ObjectUtil.isNotEmpty(param.getAddressPostalCode()),TbLogisticsShipment::getAddressPostalCode, param.getAddressPostalCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusWhoWillLabel()),TbLogisticsShipment::getBusWhoWillLabel, param.getBusWhoWillLabel());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusWhoWillPrep()),TbLogisticsShipment::getBusWhoWillPrep, param.getBusWhoWillPrep());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusShippingMethod()),TbLogisticsShipment::getBusShippingMethod, param.getBusShippingMethod());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusAppStatus()),TbLogisticsShipment::getBusAppStatus, param.getBusAppStatus());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsShipment::getId, param.getId());
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
        int total = tbLogisticsShipmentMapper.deleteById(id);
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
        int delCount = tbLogisticsShipmentMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

     /**
      * 根据出货清单号查询 发货计划信息
      * @param packCode
      * @return
      */
     @DataSource(name = "logistics")
     @Override
     public TbLogisticsShipment queryByPackCode(String packCode) {
         LambdaQueryWrapper<TbLogisticsShipment> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(TbLogisticsShipment::getPackCode, packCode);//出货清单号
         List<TbLogisticsShipment> shipments = tbLogisticsShipmentMapper.selectList(queryWrapper);
         if (ObjectUtil.isNotEmpty(shipments)) {
             return shipments.get(0);
         }
         return null;
     }

     /**
      * Amazon发货申请删除
      * @param packCode
      * @return
      */
     @DataSource(name = "logistics")
     @Override
     @Transactional
     public boolean deleteByPackCode(String packCode) {
         LambdaQueryWrapper<TbLogisticsShipment> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(TbLogisticsShipment::getPackCode, packCode);//出货清单号
         return tbLogisticsShipmentMapper.delete(queryWrapper) > 0 && tbLogisticsShipmentDetService.deleteByPackCode(packCode) > 0;
     }

     /**
      * 基于出货清单生成Plan
      * @param shipmentList
      * @return
      */
     @DataSource(name = "logistics")
     @Override
     @Transactional
     public ResponseData createShipmentPlanToShipmentList(List<TbLogisticsShipmentParam> shipmentList) {
         LoginUser loginUser = LoginContext.me().getLoginUser();
         //查看planeName是否存在
//         return _db.Tables.TbLogisticsShipments.Where(r => PlanNameList.Contains(r.PlanName) || PackCodeList.Contains(r.PackCode)).Count() > 0;

         List<String> planNameList = shipmentList.stream().map(TbLogisticsShipmentParam::getPlanName).filter(ObjectUtil::isNotEmpty).collect(Collectors.toList());
         List<String> packCodeList = shipmentList.stream().map(TbLogisticsShipmentParam::getPackCode).filter(ObjectUtil::isNotEmpty).collect(Collectors.toList());

         List<TbLogisticsShipment> findedList = tbLogisticsShipmentMapper.selectList(new LambdaQueryWrapper<TbLogisticsShipment>()
                                                                                        .in(TbLogisticsShipment::getPlanName, planNameList)
                                                                                        .or()
                                                                                        .in(TbLogisticsShipment::getPackCode, packCodeList));
         if (ObjectUtil.isNotEmpty(findedList)) {
             return ResponseData.success("PlanName已存在");
         }

         List<TbLogisticsShipment> addList = new ArrayList<>();
         List<TbLogisticsShipmentDet> addDetList = new ArrayList<>();

         for (TbLogisticsShipmentParam shipment : shipmentList) {
             //1.删除
//         _db.Tables.TbLogisticsShipments.Where(r => r.PackCode == l.PackCode).Delete();
             this.deleteByPackCode(shipment.getPackCode());

             // var maxBox = _db.Tables.TbLogisticsPackingListDet2.Where(x => x.PackCode == l.PackCode).OrderByDescending(i => i.PackDetBoxNum).FirstOrDefault();
             Integer maxBoxNo =tbLogisticsPackingListDet2Mapper.getMaxBoxNoByPackCode(shipment.getPackCode());

             //var ComShop = ComShopList.Where(x => x.CountryCode == l.CountryCode && x.ShopNameSimple == l.ShopNameSimple).FirstOrDefault();
             TbComShop comShop = shopEntityConsumer.queryByShopNameSimpleAndCountryCode(shipment.getShopNameSimple(), shipment.getCountryCode());
             if (ObjectUtil.isNull(comShop)) {
                 continue;
             }

             if (comShop.getShopComNameEn() == null) {
                 comShop.setShopComNameEn("");
             }

             TbLogisticsShipment logisticsShipment = TbLogisticsShipment.builder()
                     .packCode(shipment.getPackCode())
                     .sysAddDate(new Date())
                     .sysPerCode(shipment.getSysPerCode())
                     .sysPerName(shipment.getSysPerName())
                     .shopNameSimple(shipment.getShopNameSimple())
                     .planName(shipment.getPlanName())
                     .shipToCountry(shipment.getCountryCode())
                     .addressName(ObjectUtil.isNull(comShop.getShopComAddrCn()) ? "" :comShop.getShopComAddrCn().replace("'","''"))
                     .addressFieldOne(ObjectUtil.isNull(comShop.getShopShipAddrEn1()) ? "" :comShop.getShopShipAddrEn1().replace("'","''"))
                     .addressFieldTwo(ObjectUtil.isNull(comShop.getShopShipAddrEn2()) ? "" :comShop.getShopShipAddrEn2().replace("'","''"))
                     .addressCity(comShop.getShopComCity())
                     .addressCountryCode(comShop.getShopComCountry())
                     .addressStateOrRegion(comShop.getShopComState())
                     .addressDistrict(comShop.getShopComDistrict())
                     .addressPostalCode(comShop.getShopComPosCode())
                     .sysUpdDatetime(new Date())
                     .busIsAgl(shipment.getBusIsAgl())
                     .busWhoWillLabel(shipment.getBusWhoWillLabel())
                     .busWhoWillPrep(shipment.getBusWhoWillPrep())
                     .busShippingMethod(shipment.getBusShippingMethod())
                     .busAppStatus(shipment.getBusAppStatus())
                     .busMaxBoxNum(maxBoxNo)
                     .sysPerCode(loginUser.getAccount())
                     .sysPerName(loginUser.getName())
                     .build();

             addList.add(logisticsShipment);


//             删除明细 上面已经关联删除了
//             _db.Tables.TbLogisticsShipmentDets.Where(r => r.PackCode == la.PackCode).Delete();

             //基于装箱清单生成Plan明细
            List<TbLogisticsShipmentDet> detList= tbLogisticsPackingListDet2Mapper.getSkuCountQtyByPackCode(shipment.getPackCode());
             for (TbLogisticsShipmentDet shipmentDet : detList) {
                 shipmentDet.setPackCode(shipment.getPackCode());
                 shipmentDet.setPlanName(shipment.getPlanName());
             }
             addDetList.addAll(detList);
         }

         if (ObjectUtil.isNotEmpty(addList)) {
             if (this.saveBatch(addList)) {
                 if (ObjectUtil.isNotEmpty(addDetList)) {
                     if (tbLogisticsShipmentDetService.saveBatch(addDetList)) {
                         return ResponseData.success("Plan生成成功");
                     }
                 }
             }
         }

         return ResponseData.error("Plan生成失败");
     }

    @DataSource(name = "logistics")
    @Override
    public ResponseData getLogisticsShipmentListToPlan(String packCode) {
        LambdaQueryWrapper<TbLogisticsPackingList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(packCode),TbLogisticsPackingList::getPackCode, packCode);
        queryWrapper.eq(TbLogisticsPackingList::getPackUploadState, "未绑定");
        List<TbLogisticsPackingList> packingLists = tbLogisticsPackingListService.list(queryWrapper);
        return ResponseData.success(packingLists);
    }
}