package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tadpole.cloud.externalSystem.modular.mabang.constants.MabangConstant;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.B2bMabangOrders;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.B2bPayment;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.B2bPaymentDetail;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangShopList;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.B2bMabangOrdersMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.B2bPaymentMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.B2bPaymentParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.B2bMabangOrdersResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.B2bMabangOrdersExportResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.B2bMabangOrdersService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.B2bPaymentDetailService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.B2bPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * B2B客户付款信息;(B2B_PAYMENT)--服务实现类
 * @author : LSY
 */
@Slf4j
@Service
@Transactional
public class B2bPaymentServiceImpl extends ServiceImpl<B2bPaymentMapper, B2bPayment>  implements B2bPaymentService {
    @Resource
    private B2bPaymentMapper b2bPaymentMapper;

    @Resource
    private B2bMabangOrdersMapper b2bMabangOrdersMapper;

    @Resource
    private B2bPaymentDetailService paymentDetailService;

    @Resource
    private B2bMabangOrdersService b2bMabangOrdersService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "external")
    @Override
    public B2bPayment queryById(String id){
        return b2bPaymentMapper.selectById(id);
    }
    
    /**
     * 分页查询
     *
     * @param queryParam 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "external")
    @Override
    public Page<B2bMabangOrdersResult> paginQuery(B2bPaymentParam queryParam, long current, long size){
        //1. 构建动态查询条件

        MPJLambdaWrapper<B2bMabangOrders> wrapper = new MPJLambdaWrapper<>();
        wrapper.selectAll(B2bMabangOrders.class)
                .select(B2bPayment::getMcStatus,B2bPayment::getAmountReceived,B2bPayment::getDiyOrderNo,B2bPayment::getPaymentDetailStatus,B2bPayment::getSalesmanName,B2bPayment::getSalesmanNo)
                .select(MabangShopList::getFinanceCode)
                .leftJoin(MabangShopList.class,MabangShopList::getId,B2bMabangOrders::getShopId)
                .leftJoin(B2bPayment.class,B2bPayment::getPlatformOrderId,B2bMabangOrders::getPlatformOrderId)
                .like(ObjectUtil.isNotEmpty(queryParam.getPlatformOrderId()),B2bPayment::getPlatformOrderId, queryParam.getPlatformOrderId())
                .eq(ObjectUtil.isNotEmpty(queryParam.getDiyOrderNo()),B2bPayment::getDiyOrderNo, queryParam.getDiyOrderNo())
                .in(ObjectUtil.isNotEmpty(queryParam.getSalesmanNoList()),B2bPayment::getSalesmanNo, queryParam.getSalesmanNoList())
                .in(ObjectUtil.isNotEmpty(queryParam.getSalesmanNameList()),B2bPayment::getSalesmanName, queryParam.getSalesmanNameList())
                .eq(ObjectUtil.isNotEmpty(queryParam.getPaymentDetailStatus()),B2bPayment::getPaymentDetailStatus, queryParam.getPaymentDetailStatus())
                .eq(ObjectUtil.isNotEmpty(queryParam.getOrderStatus()),B2bPayment::getOrderStatus, queryParam.getOrderStatus())
                .in(ObjectUtil.isNotEmpty(queryParam.getMcStatusList()),B2bPayment::getMcStatus, queryParam.getMcStatusList())
                .like(ObjectUtil.isNotEmpty(queryParam.getBuyerUserId()),B2bMabangOrders::getBuyerUserId, queryParam.getBuyerUserId())
                .like(ObjectUtil.isNotEmpty(queryParam.getBuyerName()),B2bMabangOrders::getBuyerName, queryParam.getBuyerName())
                .like(ObjectUtil.isNotEmpty(queryParam.getCompanyStreet()),B2bMabangOrders::getCompanyStreet, queryParam.getCompanyStreet())
                .in(ObjectUtil.isNotEmpty(queryParam.getFinanceCodeList()),MabangShopList::getFinanceCode, queryParam.getFinanceCodeList())
                .in(ObjectUtil.isNotEmpty(queryParam.getShopNameList()),B2bMabangOrders::getShopName, queryParam.getShopNameList())
                .eq(ObjectUtil.isNotEmpty(queryParam.getPaymentDetailStatus()),B2bPayment::getPaymentDetailStatus, queryParam.getPaymentDetailStatus())
                .ge(ObjectUtil.isNotNull(queryParam.getCreateDateStart()),B2bMabangOrders::getCreateDate, queryParam.getCreateDateStart())
                .lt(ObjectUtil.isNotNull(queryParam.getCreateDateEnd()),B2bMabangOrders::getCreateDate,ObjectUtil.isNotNull(queryParam.getCreateDateEnd()) ? queryParam.getCreateDateEnd().plusDays(1):null )
                .orderByDesc(B2bMabangOrders::getCreateDate);


        //2. 执行分页查询
        Page<B2bMabangOrdersResult> pagin = new Page<>(current , size,true );

        IPage<B2bMabangOrdersResult> selectResult = b2bMabangOrdersMapper.selectJoinPage(pagin , B2bMabangOrdersResult.class,wrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param b2bPayment 实例对象
     * @return 实例对象
     */
    @DataSource(name = "external")
    @Override
    public B2bPayment insert(B2bPayment b2bPayment){
        b2bPaymentMapper.insert(b2bPayment);
        return b2bPayment;
    }
    
    /** 
     * 更新数据
     *
     * @param entityParam 实例对象
     * @return 实例对象
     */
    @DataSource(name = "external")
    @Override
    public B2bPayment update(B2bPayment entityParam){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<B2bPayment> chainWrapper = new LambdaUpdateChainWrapper<>(b2bPaymentMapper);
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getPlatformOrderId()),B2bPayment::getPlatformOrderId, entityParam.getPlatformOrderId());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDiyOrderNo()),B2bPayment::getDiyOrderNo, entityParam.getDiyOrderNo());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getSalesmanNo()),B2bPayment::getSalesmanNo, entityParam.getSalesmanNo());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getSalesmanName()),B2bPayment::getSalesmanName, entityParam.getSalesmanName());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getPaymentDetailStatus()),B2bPayment::getPaymentDetailStatus, entityParam.getPaymentDetailStatus());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getOrderStatus()),B2bPayment::getOrderStatus, entityParam.getOrderStatus());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getMcStatus()),B2bPayment::getMcStatus, entityParam.getMcStatus());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getCreatedBy()),B2bPayment::getCreatedBy, entityParam.getCreatedBy());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getUpdatedBy()),B2bPayment::getUpdatedBy, entityParam.getUpdatedBy());
        
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getAmountReceived()),B2bPayment::getAmountReceived, entityParam.getAmountReceived());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getAmountUnconfirmed()),B2bPayment::getAmountUnconfirmed, entityParam.getAmountUnconfirmed());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getCreatedTime()),B2bPayment::getCreatedTime, entityParam.getCreatedTime());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getUpdatedTime()),B2bPayment::getUpdatedTime, entityParam.getUpdatedTime());
        //2. 设置主键，并更新
        chainWrapper.eq(B2bPayment::getId, entityParam.getId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(entityParam.getId());
        }else{
            return entityParam;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @DataSource(name = "external")
    @Override
    public boolean deleteById(String id){
        int total = b2bPaymentMapper.deleteById(id);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param idList 主键List
     * @return 是否成功
     */
    @DataSource(name = "external")
    @Override
    public boolean deleteBatchIds(List<String> idList){
         int delCount = b2bPaymentMapper.deleteBatchIds(idList);
         if (idList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }

     @DataSource(name = "external")
     @Override
     public void createOrUpdateB2bPayment(B2bMabangOrders b2bMabangOrders) {
         //先查询之前有没有创建
         String platformOrderId = b2bMabangOrders.getPlatformOrderId();
         B2bPayment b2bPayment = this.baseMapper.selectById(platformOrderId);
         if (ObjectUtil.isNotNull(b2bPayment)) {
             //更新订单在马帮的状态
             b2bPayment.setOrderStatus(b2bMabangOrders.getOrderStatus());
             b2bPayment.setUpdatedBy("System");
             b2bPayment.setUpdatedTime(new Date());
         } else {
             b2bPayment = initB2bPayment(b2bMabangOrders);
         }
         this.saveOrUpdate(b2bPayment);
     }

     @DataSource(name = "external")
     @Override
     public B2bPayment updateAmount(B2bPaymentDetail paymentDetail, int bizTyep) {
         LoginUser loginUserInfo = LoginContext.me().getLoginUser();
         LambdaQueryWrapper<B2bPayment> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(B2bPayment::getPlatformOrderId, paymentDetail.getPlatformOrderId());
         B2bPayment b2bPayment = this.getOne(wrapper, true);
         BigDecimal amountUnconfirmed = ObjectUtil.isNull(b2bPayment.getAmountUnconfirmed()) ? BigDecimal.ZERO : b2bPayment.getAmountUnconfirmed();
         BigDecimal amountReceived = ObjectUtil.isNull(b2bPayment.getAmountReceived()) ? BigDecimal.ZERO : b2bPayment.getAmountReceived();

         //付款明细的汇率是基于订单的应收款币别的汇率 比如欧元与美元之间的汇率
         if (MabangConstant.BIZ_TYPE_FINANCE_CONFIRM == bizTyep) {//财务确认

             amountUnconfirmed=  amountUnconfirmed.add(paymentDetail.getAmountConfirmed().add(paymentDetail.getAmountCommission()).multiply(paymentDetail.getCurrencyRate()).multiply(BigDecimal.valueOf( bizTyep)));//未确认金额减少
             b2bPayment.setAmountUnconfirmed(amountUnconfirmed);

             amountReceived = amountReceived.add(paymentDetail.getAmountConfirmed().add(paymentDetail.getAmountCommission()).multiply(paymentDetail.getCurrencyRate()));//确认金额增加
             b2bPayment.setAmountReceived(amountReceived);

             if (BigDecimal.ZERO.compareTo(amountUnconfirmed)>=0) { //未确认金额小于等于0，--可能会有正负偏差一点
                 b2bPayment.setPaymentDetailStatus(MabangConstant.FINANCE_PAYMENT_DETAIL_STATUS_CONFIRMED);//不含有收款明细待确认
             }

             //判断实收和应收是否相等
             B2bMabangOrdersResult mabangOrdersResult = b2bMabangOrdersService.queryByPlatformOrderId(paymentDetail.getPlatformOrderId());
             BigDecimal itemTotalOrigin = ObjectUtil.isNull(mabangOrdersResult.getItemTotalOrigin()) ? BigDecimal.ZERO : mabangOrdersResult.getItemTotalOrigin();
             if (amountReceived.compareTo(itemTotalOrigin)==0) {//
                 b2bPayment.setMcStatus(MabangConstant.PAYMENT_DETAIL_MC_STATUS_NORMAL_CLOSE);
                 b2bPayment.setPaymentDetailStatus(MabangConstant.FINANCE_PAYMENT_DETAIL_STATUS_CONFIRMED);
             }

         } else if (MabangConstant.BIZ_TYPE_OPERATE_SUBMIT == bizTyep) {//运营提交
             amountUnconfirmed=  amountUnconfirmed.add(paymentDetail.getAmountUnconfirmed().multiply(paymentDetail.getCurrencyRate()));//未确认金额增加
             b2bPayment.setAmountUnconfirmed(amountUnconfirmed);
             //修改状态
             b2bPayment.setPaymentDetailStatus(MabangConstant.FINANCE_PAYMENT_DETAIL_STATUS_UNCONFIRMED);
             b2bPayment.setMcStatus(MabangConstant.PAYMENT_DETAIL_MC_STATUS_NORMAL);
         }

         b2bPayment.setUpdatedTime(new Date());
         b2bPayment.setUpdatedBy(loginUserInfo.getName());
         this.updateById(b2bPayment);

         return b2bPayment;
     }


    @DataSource(name = "external")
    @Override
    public ResponseData orderClose(String platformOrderId, String b2bOrderCloseByOper) {

        LoginContext current= SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        List<B2bPaymentDetail> paymentDetailList = paymentDetailService.queryByPlatformOrderId(platformOrderId);
        if (ObjectUtil.isNotEmpty(paymentDetailList)) {

            List<B2bPaymentDetail> paymentDetails = paymentDetailList.stream()
                    .filter(p -> p.getOperateSubmit().equals(MabangConstant.OPERATE_PAYMENT_DETAIL_STATUS_SAVE)).collect(Collectors.toList());
            if (ObjectUtil.isNotEmpty(paymentDetails)) {
                return ResponseData.error("订单[" + platformOrderId + "]还有运营未提交的付款信息,请处理完毕后关闭！");
            }
            List<B2bPaymentDetail> unCofirmList = paymentDetailList.stream()
                    .filter(p -> p.getConfirmStatus().equals(MabangConstant.FINANCE_PAYMENT_DETAIL_STATUS_UNCONFIRMED)).collect(Collectors.toList());
            if (ObjectUtil.isNotEmpty(unCofirmList)) {
                return ResponseData.error("订单[" + platformOrderId + "]还有财务待确认的付款信息,请处理完毕后关闭！");
            }
        }

        LambdaQueryWrapper<B2bPayment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(B2bPayment::getPlatformOrderId, platformOrderId);

        List<B2bPayment> paymentList = this.baseMapper.selectList(wrapper);
        if (ObjectUtil.isEmpty(paymentList)) {
            return ResponseData.error("订单[" + platformOrderId + "]信息未找到！");
        }
        B2bPayment b2bPayment = paymentList.get(0);
        if ( MabangConstant.B2B_ORDER_CLOSE_BY_OPER.equals(b2bOrderCloseByOper)) { //业务关闭，订单状态只能是未关闭
            if (MabangConstant.PAYMENT_DETAIL_MC_STATUS_NORMAL.equals(b2bPayment.getMcStatus())) {
                b2bPayment.setMcStatus(MabangConstant.PAYMENT_DETAIL_MC_STATUS_BIZ_CLOSE);
            } else {
                return ResponseData.error("订单[" + platformOrderId + "]数据状态为["+b2bPayment.getMcStatus()+"]不能进行业务关闭操作！");
            }

        }else if ( MabangConstant.B2B_ORDER_CLOSE_BY_FINANCE.equals(b2bOrderCloseByOper)){
            if (MabangConstant.PAYMENT_DETAIL_MC_STATUS_BIZ_CLOSE.equals(b2bPayment.getMcStatus())) {
                b2bPayment.setPaymentDetailStatus(MabangConstant.FINANCE_PAYMENT_DETAIL_STATUS_CONFIRMED);
                b2bPayment.setMcStatus(MabangConstant.PAYMENT_DETAIL_MC_STATUS_FINANCE_CLOSE);
            } else {
                return ResponseData.error("订单[" + platformOrderId + "]数据状态为["+b2bPayment.getMcStatus()+"]不能进行财务关闭操作！");
            }
        }

        b2bPayment.setMcStatus(b2bOrderCloseByOper);
        b2bPayment.setPaymentDetailStatus(MabangConstant.FINANCE_PAYMENT_DETAIL_STATUS_CONFIRMED);
        b2bPayment.setUpdatedBy(currentUser.getName());
        b2bPayment.setUpdatedTime(new Date());
        int count = this.baseMapper.updateById(b2bPayment);
        if (count>0) {
            return ResponseData.success();
        }
        return ResponseData.error("订单关闭失败");
    }

    @DataSource(name = "external")
    @Override
    public List<B2bMabangOrdersExportResult> export(B2bPaymentParam queryParam) {
        MPJLambdaWrapper<B2bMabangOrders> wrapper = new MPJLambdaWrapper<>();
        wrapper.selectAll(B2bMabangOrders.class)
                .select(B2bPayment::getMcStatus,B2bPayment::getAmountReceived,B2bPayment::getDiyOrderNo,B2bPayment::getPaymentDetailStatus,B2bPayment::getSalesmanName,B2bPayment::getSalesmanNo)
                .select(MabangShopList::getFinanceCode)
                .selectAll(B2bPaymentDetail.class)
                .leftJoin(MabangShopList.class,MabangShopList::getId,B2bMabangOrders::getShopId)
                .leftJoin(B2bPayment.class,B2bPayment::getPlatformOrderId,B2bMabangOrders::getPlatformOrderId)
                .leftJoin(B2bPaymentDetail.class,B2bPaymentDetail::getPlatformOrderId,B2bMabangOrders::getPlatformOrderId)
                .like(ObjectUtil.isNotEmpty(queryParam.getPlatformOrderId()),B2bPayment::getPlatformOrderId, queryParam.getPlatformOrderId())
                .eq(ObjectUtil.isNotEmpty(queryParam.getDiyOrderNo()),B2bPayment::getDiyOrderNo, queryParam.getDiyOrderNo())
                .in(ObjectUtil.isNotEmpty(queryParam.getSalesmanNoList()),B2bPayment::getSalesmanNo, queryParam.getSalesmanNoList())
                .in(ObjectUtil.isNotEmpty(queryParam.getSalesmanNameList()),B2bPayment::getSalesmanName, queryParam.getSalesmanNameList())
                .eq(ObjectUtil.isNotEmpty(queryParam.getPaymentDetailStatus()),B2bPayment::getPaymentDetailStatus, queryParam.getPaymentDetailStatus())
                .eq(ObjectUtil.isNotEmpty(queryParam.getOrderStatus()),B2bPayment::getOrderStatus, queryParam.getOrderStatus())
                .in(ObjectUtil.isNotEmpty(queryParam.getMcStatusList()),B2bPayment::getMcStatus, queryParam.getMcStatusList())
                .like(ObjectUtil.isNotEmpty(queryParam.getBuyerUserId()),B2bMabangOrders::getBuyerUserId, queryParam.getBuyerUserId())
                .like(ObjectUtil.isNotEmpty(queryParam.getBuyerName()),B2bMabangOrders::getBuyerName, queryParam.getBuyerName())
                .in(ObjectUtil.isNotEmpty(queryParam.getFinanceCodeList()),MabangShopList::getFinanceCode, queryParam.getFinanceCodeList())
                .in(ObjectUtil.isNotEmpty(queryParam.getShopNameList()),B2bMabangOrders::getShopName, queryParam.getShopNameList())
                .eq(ObjectUtil.isNotEmpty(queryParam.getPaymentDetailStatus()),B2bPayment::getPaymentDetailStatus, queryParam.getPaymentDetailStatus())
                .ge(ObjectUtil.isNotNull(queryParam.getCreateDateStart()),B2bMabangOrders::getCreateDate, queryParam.getCreateDateStart())
                .lt(ObjectUtil.isNotNull(queryParam.getCreateDateEnd()),B2bMabangOrders::getCreateDate,ObjectUtil.isNotNull(queryParam.getCreateDateEnd()) ? queryParam.getCreateDateEnd().plusDays(1):null );

        List<B2bMabangOrdersExportResult> selectResult = b2bMabangOrdersMapper.selectJoinList(B2bMabangOrdersExportResult.class, wrapper);
        return selectResult;
    }


    /**
      * 初始化付款信息
      * @param b2bMabangOrders
      * @return B2bPayment
      */
     private B2bPayment initB2bPayment(B2bMabangOrders b2bMabangOrders) {

         String platformOrderId = b2bMabangOrders.getPlatformOrderId();
         B2bPayment b2bPayment = new B2bPayment();
         b2bPayment.setId(platformOrderId);
         b2bPayment.setPlatformOrderId(platformOrderId);
         b2bPayment.setAmountReceived(BigDecimal.ZERO);
         b2bPayment.setAmountUnconfirmed(BigDecimal.ZERO);
         b2bPayment.setPaymentDetailStatus(MabangConstant.FINANCE_PAYMENT_DETAIL_STATUS_CONFIRMED);//运营提交了才算待确认的金额，暂存不算
         b2bPayment.setOrderStatus(b2bMabangOrders.getOrderStatus());
         b2bPayment.setMcStatus(MabangConstant.PAYMENT_DETAIL_MC_STATUS_NORMAL);
         b2bPayment.setSalesmanName(b2bMabangOrders.getShopEmployeeName());
         b2bPayment.setSalesmanNo(b2bMabangOrders.getShopEmployeeId());

         String invNumber = null;
         if (ObjectUtil.isNotEmpty(b2bMabangOrders.getExtendAttr())) {
             JSONArray extendAttr = JSONUtil.parseArray(b2bMabangOrders.getExtendAttr());
             for (Object obj : extendAttr) {
                 String key = (String)JSONUtil.parseObj(obj).get("key");
                 String val = (String)JSONUtil.parseObj(obj).get("val");
                 if ("invNumber".equals(key) && ObjectUtil.isNotEmpty(val)) {
                     invNumber=val;
                 }
                 if ("diyOrderNo".equals(key) && ObjectUtil.isNotEmpty(val)) {
                     b2bPayment.setDiyOrderNo(val);
                 }
                 if ("salesman".equals(key) && ObjectUtil.isNotEmpty(val)) {
                     b2bPayment.setSalesmanName(val);
                     b2bPayment.setSalesmanNo(null);
                     //b2bPayment.setSalesmanNo(b2bMabangOrders.getShopEmployeeId());
                 }

             }
         }
         //初始化一条付款记录
         B2bPaymentDetail paymentDetail = new B2bPaymentDetail();
         paymentDetail.setPlatformOrderId(platformOrderId);
         paymentDetail.setCurrency(b2bMabangOrders.getCurrencyId());
         paymentDetail.setCurrencyRate(BigDecimal.ONE);
         paymentDetail.setPayType(b2bMabangOrders.getPayType());
         paymentDetail.setPayRatioTitle("全款");
         paymentDetail.setPayRatio(BigDecimal.ONE);
         paymentDetail.setAmountUnconfirmed(b2bMabangOrders.getItemTotalOrigin());
         //收款账号
         paymentDetail.setAccountNumber(b2bMabangOrders.getPaypalEmail());
         paymentDetail.setInvNumber(invNumber);
         paymentDetail.setCreatedBy(ObjectUtil.isEmpty(b2bPayment.getSalesmanName()) ? "System":b2bPayment.getSalesmanName());
         paymentDetail.setRemark("马帮获取订单时自动创建一条付款明细，待运营修改后提交");
         paymentDetailService.insert(paymentDetail);
         return b2bPayment;
     }
 }