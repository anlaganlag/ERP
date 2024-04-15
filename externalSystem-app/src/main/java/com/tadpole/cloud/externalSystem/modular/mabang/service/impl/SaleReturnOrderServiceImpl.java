package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.libs.util.K3GeneratorNoUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.api.ebms.model.resp.EbmsUserInfo;
import com.tadpole.cloud.externalSystem.modular.ebms.service.IEbmsService;
import com.tadpole.cloud.externalSystem.modular.k3.utils.SyncToErpUtil;
import com.tadpole.cloud.externalSystem.modular.mabang.constants.MabangConstant;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.*;
import com.tadpole.cloud.externalSystem.modular.mabang.enums.OrderStatusEnum;
import com.tadpole.cloud.externalSystem.modular.mabang.enums.ShopListEnum;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.SaleReturnOrderMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3CrossTransferItemParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangReturnOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.SaleReturnOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ExportSaleReturnOrderResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

/**
* <p>
    * 销售退货单 服务实现类
    * </p>
*
* @author lsy
* @since 2022-08-24
*/
@Service
@Slf4j
public class SaleReturnOrderServiceImpl extends ServiceImpl<SaleReturnOrderMapper, SaleReturnOrder> implements ISaleReturnOrderService {

    @Resource
    private SaleReturnOrderMapper mapper;

    @Resource
    private ISaleReturnOrderItemService saleReturnOrderItemService;

    @Resource
    private IK3CrossTransferService k3CrossTransferService;

    @Resource
    private IK3CrossTransferItemService k3CrossTransferItemService;

    @Resource
    private SyncToErpUtil syncToErpUtil;

    @Resource
    private K3GeneratorNoUtil k3GeneratorNoUtil;

    @Resource
     private IEbmsService ebmsService;

    @Resource
    private IMabangReturnOrderService mabangReturnOrderService;



    @DataSource(name = "external")
    @Override
    public ResponseData findPageBySpec(SaleReturnOrderParam param) {

        return ResponseData.success(mapper.findPageBySpec(PageFactory.defaultPage(), param));

    }

    @DataSource(name = "external")
    @Override
    public List<ExportSaleReturnOrderResult> export(SaleReturnOrderParam param) {

        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        IPage<ExportSaleReturnOrderResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return page.getRecords();
    }

    @Override
    @DataSource(name = "external")
    public List<Map<String, Object>> platformSelect() {
        QueryWrapper<SaleReturnOrderItem> wrapper = new QueryWrapper<>();
        wrapper.select("PLAT_NAME").groupBy("PLAT_NAME").orderByAsc("PLAT_NAME");
        List<Map<String, Object>> selectList = saleReturnOrderItemService.listMaps(wrapper);
        return selectList;
    }

    @Override
    @DataSource(name = "external")
    public List<Map<String, Object>> yearSelect() {
        QueryWrapper<SaleReturnOrder> wrapper = new QueryWrapper<>();
        wrapper.select("YEARS").groupBy("YEARS").orderByAsc("YEARS");
        List<Map<String, Object>> selectList = this.listMaps(wrapper);
        return selectList;
    }

    @Override
    @DataSource(name = "external")
    public List<Map<String, Object>> monthSelect() {
        QueryWrapper<SaleReturnOrder> wrapper = new QueryWrapper<>();
        wrapper.select("MONTH").groupBy("MONTH").orderByAsc("MONTH");
        List<Map<String, Object>> selectList = this.listMaps(wrapper);
        return selectList;
    }

    @Override
    @DataSource(name = "external")

    public List<Map<String, Object>> shopSelect(List<String> platformNames) {
        List<Map<String, Object>> selectList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(platformNames)){
            QueryWrapper<SaleReturnOrderItem> wrapper = new QueryWrapper<>();
            wrapper.select("SHOP_NAME").in("PLAT_NAME",platformNames).groupBy("SHOP_NAME").orderByAsc("SHOP_NAME");
            selectList = saleReturnOrderItemService.listMaps(wrapper);
        }
        return selectList;
    }

    @Override
    @DataSource(name = "external")
    public List<Map<String, Object>> siteSelect() {
        QueryWrapper<SaleReturnOrderItem> wrapper = new QueryWrapper<>();
        wrapper.select("SITE_CODE").groupBy("SITE_CODE").orderByAsc("SITE_CODE");
        List<Map<String, Object>> selectList = saleReturnOrderItemService.listMaps(wrapper);
        return selectList;
    }

    @DataSource(name = "erpcloud")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getFinanceName(String fNumber) { return mapper.getFinanceName(fNumber); }

    @DataSource(name = "erpcloud")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getWarehouseName(String fNumber) { return mapper.getWarehouseName(fNumber); }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> getSalOrgName() { return mapper.getSalOrgName(); }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSalOrgName(String ownerId, String ownerName) { mapper.updateSalOrgName(ownerId, ownerName); }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWarehouseName(String ownerId, String ownerName) { mapper.updateWarehouseName(ownerId, ownerName); }



    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData generateSaleReturnOrderByMabangReturnOrders(K3CrossTransferItemParam param) {

        //1-1.马帮退货订单==》》跨组织调拨单
        K3CrossTransfer crossTransfer=new K3CrossTransfer();
        K3CrossTransferItem crossTransferItem=new K3CrossTransferItem();

        //1-2.马帮退货订单==》》销售退货单
        SaleReturnOrder saleReturnOrder = new SaleReturnOrder();
        SaleReturnOrderItem saleReturnOrderItem = new SaleReturnOrderItem();

        //1-3.马帮退货订单 -->待同步
        MabangReturnOrderParam mabangReturnOrderParam = new MabangReturnOrderParam();
        mabangReturnOrderParam.setStatus(OrderStatusEnum.COMPLETED.getName());
        List<MabangReturnOrder> list=mapper.syncMabangReturnOrders(mabangReturnOrderParam);

        if(CollectionUtil.isNotEmpty(list)){

            for(MabangReturnOrder returnOrder : list){
                //马帮退货订单明细
                mabangReturnOrderParam.setPlatOrdId(returnOrder.getPlatOrdId());
                List<MabangReturnOrderItem> itemList=mapper.syncMabangReturnOrderItem(mabangReturnOrderParam);

                //日期格式转换
                String year = String.valueOf(DateUtil.year(returnOrder.getCreateDate()));
                String month = String.valueOf(DateUtil.month(returnOrder.getCreateDate()));

                //查询店铺列表平台、店铺、财务编码信息
                MabangShopList shopList=this.baseMapper.getShoplist(returnOrder.getShopId());
                //业务类型简称
                String unwText = "XSTH"+"-"+shopList.getFinanceCode()+"-002-";

                //2.马帮退货订单->生成销售退货单记录
//                saleReturnOrder.setBillNo(billNo);
                saleReturnOrder.setYears(year);
                saleReturnOrder.setMonth(month);
                saleReturnOrder.setFinanceCode(shopList.getFinanceCode());
                saleReturnOrder.setSalOrgCode(shopList.getFinanceCode());
                saleReturnOrder.setOrderId(returnOrder.getId());
                //saleReturnOrder.setSalOrgName();
                this.save(saleReturnOrder);

                //3.马帮退货订单->同步跨组织调拨单
                if(ObjectUtil.isNotNull(shopList)){
                    crossTransfer.setPlatformName(shopList.getPlatformName());
                    crossTransfer.setShopName(shopList.getName());
                    crossTransfer.setFinanceCode(shopList.getFinanceCode());
                }
                crossTransfer.setFBillTypeId("ZJDB01_SYS");
                crossTransfer.setFBizType("NORMAL");
                crossTransfer.setFTransferDirect("GENERAL");
                crossTransfer.setFTransferBizType("OverOrgTransfer");
                crossTransfer.setFUnwBusinesstype("B");
                crossTransfer.setFUnwText(unwText); //XSTH(销售退货)-调出库存组织Code-调入库存组织Code
                crossTransfer.setFDate(returnOrder.getCreateTime());
                crossTransfer.setFStockOutorgId(shopList.getFinanceCode());
                crossTransfer.setFOwnerTypeOutIdHead("BD_OwnerOrg");
                crossTransfer.setFOwnerOutIdHead(shopList.getFinanceCode());
                crossTransfer.setFStockOrgId("002");
                crossTransfer.setFOwnerTypeIdHead("BD_OwnerOrg");
                crossTransfer.setFOwnerIdHead(shopList.getFinanceCode());
                crossTransfer.setFNote(returnOrder.getPlatOrdId());
                crossTransfer.setOrderId(returnOrder.getId());
                crossTransfer.setSyncStatus("-1");
                crossTransfer.setOriginalFinanceCode(shopList.getOriginalFinanceCode());
                crossTransfer.setBizType(ShopListEnum.SYNC_RETURN_ORDER.getName());
                crossTransfer.setCreateTime(DateUtil.date());
                k3CrossTransferService.save(crossTransfer);

                //马帮退货订单明细
                for(int j=0;j<itemList.size();j++){

                    //2-1.销售退货明细
                    saleReturnOrderItem.setSaleOutOrderId(saleReturnOrder.getId());
                    saleReturnOrderItem.setMaReturnOrderItemId(itemList.get(j).getId());
                    //saleReturnOrderItem.setBillNo("");
                    saleReturnOrderItem.setDepartment("平台发展部");
                    saleReturnOrderItem.setTeam("平台发展组");
                    saleReturnOrderItem.setPlatName(shopList.getPlatformName());
                    saleReturnOrderItem.setShopName(shopList.getName());
                    //站点(产品需求)：金蝶组织编码小尾巴中裁剪出站点，如无小尾巴，则默认：ALL
                    if(shopList.getOriginalFinanceCode().contains("_")){
                        String[] strList = StrUtil.splitToArray(shopList.getOriginalFinanceCode(), "_");
                        if(StrUtil.isNotEmpty(strList[1])){saleReturnOrderItem.setSiteCode(strList[1]);}
                    }else
                    {
                        saleReturnOrderItem.setSiteCode("ALL");
                    }
                    saleReturnOrderItem.setWarehouseId(itemList.get(j).getStockwarehouseid());
                    saleReturnOrderItem.setWarehouseName(itemList.get(j).getStockwarehousename());
                    saleReturnOrderItem.setPlatOrdId(itemList.get(j).getPlatOrdId());
                    saleReturnOrderItem.setPlatSku(itemList.get(j).getStocksku());
                    saleReturnOrderItem.setShippedTime(returnOrder.getCreateDate());
                    saleReturnOrderItem.setReturnQty(itemList.get(j).getQuantity1());  //
                    saleReturnOrderItem.setInboundStatus(String.valueOf(itemList.get(j).getStatus()));
                    saleReturnOrderItem.setRtnOrdStatus(String.valueOf(returnOrder.getStatus()));
                    saleReturnOrderItem.setEmployeeName(returnOrder.getEmployeeName());
                    saleReturnOrderItem.setStockSku(itemList.get(j).getStocksku());
                    saleReturnOrderItemService.save(saleReturnOrderItem);

                    //3-1.k3跨组织调拨单明细
                    crossTransferItem.setCrossId(crossTransfer.getId());
                    crossTransferItem.setFRowType("Standard");

                    String platFormSku = mapper.getPlatSku(itemList.get(j).getPlatOrdId(),itemList.get(j).getStocksku());
                    if(StrUtil.isNotEmpty(platFormSku)){
                        int platSkuLen = StrUtil.length(platFormSku);
                        int materialLen = StrUtil.length(itemList.get(j).getStocksku());
                        //注：(产品需求)当SKU和物料编码的长度都是8位或9位时（含数字和字母），若SKU与物料编码不相同，则默认将平台SKU更改为物料编码
                        if(!platFormSku.equals(itemList.get(j).getStocksku()) && platSkuLen == materialLen){
                            crossTransferItem.setFBscBase(itemList.get(j).getStocksku());
                        }else{
                            crossTransferItem.setFBscBase(platFormSku);
                        }
                    }
                    crossTransferItem.setFMaterialId(itemList.get(j).getStocksku());
                    crossTransferItem.setFUnitId("Pcs");
                    crossTransferItem.setFQty(itemList.get(j).getQuantity());
                    crossTransferItem.setFSrcStockId(shopList.getFinanceCode());
                    crossTransferItem.setFDestStockId("YT05_008");
                    crossTransferItem.setFSrcStockStatusId("KCZT01_SYS");
                    crossTransferItem.setFDestStockStatusId("KCZT01_SYS");
                    crossTransferItem.setFOwnerOutId(shopList.getFinanceCode());
                    crossTransferItem.setFOwnerId("002");
                    crossTransferItem.setFBscTeam("平台发展组");
                    crossTransferItem.setStockWarehouseId(itemList.get(j).getStockwarehouseid());
                    crossTransferItem.setStockWarehouse(itemList.get(j).getStockwarehousename());
                    crossTransferItem.setCreateTime(DateUtil.date());
                    k3CrossTransferItemService.save(crossTransferItem);
                }
            }
        }
        else{
            return ResponseData.error("K3CrossTransfer ===》》 马帮退货订单没有需要同步的调拨单据！");
        }
        return ResponseData.success();
    }


    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData generateOtherInOrder(K3CrossTransferItemParam param) {

        //1-2.马帮退货订单==》》销售退货单
        SaleReturnOrder saleReturnOrder = new SaleReturnOrder();
        SaleReturnOrderItem saleReturnOrderItem = new SaleReturnOrderItem();

        //1-3.马帮退货订单 -->待同步
        MabangReturnOrderParam mabangReturnOrderParam = new MabangReturnOrderParam();
        mabangReturnOrderParam.setStatus(OrderStatusEnum.COMPLETED.getName());
        List<MabangReturnOrder> list=mapper.syncMabangReturnOrders(mabangReturnOrderParam);

        if(CollectionUtil.isNotEmpty(list)){

            for(MabangReturnOrder returnOrder : list){
                //马帮退货订单明细
                mabangReturnOrderParam.setPlatOrdId(returnOrder.getPlatOrdId());
                List<MabangReturnOrderItem> itemList=mapper.syncMabangReturnOrderItem(mabangReturnOrderParam);

                //日期格式转换
                Date createDate = returnOrder.getCreateDate();
                if (ObjectUtil.isNull(createDate)) {
                    createDate = new Date();
                }
                LocalDate   createLocalDate = returnOrder.getCreateDate().toInstant().atOffset(ZoneOffset.ofHours(8)).toLocalDate();
                String month = String.valueOf(createLocalDate.getMonthValue());
                String year = String.valueOf(createLocalDate.getYear());

                //查询店铺列表平台、店铺、财务编码信息
                MabangShopList shopList=this.baseMapper.getShoplist(returnOrder.getShopId());
                //业务类型简称
                String unwText = "XSTH"+"-"+shopList.getFinanceCode()+"-002-";
                String billNo = k3GeneratorNoUtil.getNoByKey("MBTH", MabangConstant.K3_OTHER_IN_ORDER_NO, 6);
                //2.马帮退货订单->生成销售退货单记录
                saleReturnOrder.setBillNo(billNo);
                saleReturnOrder.setYears(year);
                saleReturnOrder.setMonth(month);
                saleReturnOrder.setFinanceCode(shopList.getFinanceCode());
                saleReturnOrder.setSalOrgCode(shopList.getFinanceCode());
                saleReturnOrder.setOrderId(returnOrder.getId());
                saleReturnOrder.setSyncStatus(MabangConstant.SYNC_INIT);
                //saleReturnOrder.setSalOrgName();
                this.save(saleReturnOrder);

                //马帮退货订单明细
                for(int j=0;j<itemList.size();j++){

                    //2-1.销售退货明细
                    saleReturnOrderItem.setSaleOutOrderId(saleReturnOrder.getId());
                    saleReturnOrderItem.setMaReturnOrderItemId(itemList.get(j).getId());
                    saleReturnOrderItem.setBillNo(billNo);
                    saleReturnOrderItem.setDepartment("平台发展部");
                    saleReturnOrderItem.setTeam("平台发展组");
                    saleReturnOrderItem.setPlatName(shopList.getPlatformName());
                    saleReturnOrderItem.setShopName(shopList.getName());
                    //站点(产品需求)：金蝶组织编码小尾巴中裁剪出站点，如无小尾巴，则默认：ALL
                    if(shopList.getOriginalFinanceCode().contains("_")){
                        String[] strList = StrUtil.splitToArray(shopList.getOriginalFinanceCode(), "_");
                        if(StrUtil.isNotEmpty(strList[1])){saleReturnOrderItem.setSiteCode(strList[1]);}
                    }else
                    {
                        saleReturnOrderItem.setSiteCode("ALL");
                    }
                    saleReturnOrderItem.setWarehouseId(itemList.get(j).getStockwarehouseid());
                    saleReturnOrderItem.setWarehouseName(itemList.get(j).getStockwarehousename());
                    saleReturnOrderItem.setPlatOrdId(itemList.get(j).getPlatOrdId());
                    saleReturnOrderItem.setPlatSku(itemList.get(j).getStocksku());
                    saleReturnOrderItem.setShippedTime(returnOrder.getCreateDate());
                    saleReturnOrderItem.setReturnQty(itemList.get(j).getQuantity1());  //
                    saleReturnOrderItem.setInboundStatus(String.valueOf(itemList.get(j).getStatus()));
                    saleReturnOrderItem.setRtnOrdStatus(String.valueOf(returnOrder.getStatus()));
                    saleReturnOrderItem.setEmployeeName(returnOrder.getEmployeeName());
                    saleReturnOrderItem.setStockSku(itemList.get(j).getStocksku());
                    saleReturnOrderItemService.save(saleReturnOrderItem);
                }
                returnOrder.setCreateSaleReturnOrder("1");//已经创建退货记录准备推送到k3
                mabangReturnOrderService.updateById(returnOrder);
            }

        }
        else{
            return ResponseData.error("K3CrossTransfer ===》》 马帮退货订单没有需要同步的调拨单据！");
        }
        return ResponseData.success();
    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData syncOtherInOrder(K3CrossTransferItemParam param) {

        LambdaQueryWrapper<SaleReturnOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(SaleReturnOrder::getSyncStatus, MabangConstant.SYNC_SUCCESS);
        wrapper.orderByDesc(SaleReturnOrder::getCreateTime);
        List<SaleReturnOrder> saleReturnOrderList = this.baseMapper.selectList(wrapper);
        if (ObjectUtil.isEmpty(saleReturnOrderList)) {
            return ResponseData.success();
        }

        for (SaleReturnOrder order : saleReturnOrderList) {
            try {
                ResponseData responseData= this.syncOtherInOrder2Erp(order);
            } catch (Exception e) {
                log.error("同步马帮销售退货单[{}]异常：{}",order.getOrderId(),JSON.toJSONString(e));

            }
        }

        return null;
    }


    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData syncOtherInOrder2Erp(SaleReturnOrder order) {

        if(order == null){
            return ResponseData.error("订单不能为null！");
        }

        JSONArray jarr = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("FBillNo", order.getBillNo());//用MC生成的换标入库单号
        object.put("FCreatorId", "S20180229");//创建人    G20190046
        object.put("FBillTypeID", "标准其他入库单");
        object.put("FStockOrgId", "002");
        object.put("FStockDirect", "普通");
        object.put("FDate", DateUtil.format(order.getCreateTime(), "yyyy/MM/dd"));
//        object.put("FDEPTID", inManageRecord.getDepartment());
        object.put("FDEPTID", "BM054");//平台发展部
        object.put("FOwnerTypeIdHead", "业务组织");
        object.put("FOwnerIdHead", "002");
//        String remark = order.getPlatOrdId();
        object.put("FNote", order.getOrderId());
        object.put("FBaseCurrId", "PRE001");
//        object.put("FSUPPLIERID", "002"); 供应商和部门二选一



        LambdaQueryWrapper<SaleReturnOrderItem> itemWrapper = new LambdaQueryWrapper<>();
        itemWrapper.eq(SaleReturnOrderItem::getBillNo, order.getBillNo());

        List<SaleReturnOrderItem> orderItemList = saleReturnOrderItemService.list(itemWrapper);

        if (ObjectUtil.isEmpty(orderItemList)) {
            return ResponseData.error("订单【"+order.getId()+"----"+order.getOrderId()+"】没有找到具体的明细项");
        }


        List<Map<String, Object>> detailList = new ArrayList<>();
        for (SaleReturnOrderItem orderItem : orderItemList) {
            if (ObjectUtil.isNull(object.get("FCreatorId"))) {
                EbmsUserInfo userInfo = ebmsService.getUserInfoByAccount(orderItem.getEmployeeName());
                if (ObjectUtil.isNotNull(userInfo)&&ObjectUtil.isNotEmpty(userInfo.getSysPerCode())) {
                    object.put("FCreatorId", userInfo.getSysPerCode());
                }
            }
            Map<String, Object> detail = new HashMap<>();
            detail.put("FMaterialId", orderItem.getStockSku());
            detail.put("FUnitID", "Pcs");
            detail.put("FQty", orderItem.getReturnQty());
            detail.put("FStockId", "YT05_008"); // 雁田定制仓
            detail.put("FOwnerTypeId", "业务组织");
            detail.put("FOwnerId", "002"); //采购中心
            detail.put("FStockStatusId", "KCZT01_SYS");
            detail.put("FKeeperTypeId", "保管者类型");
            detail.put("FDistribution", "True");
            detail.put("FKeeperId", "002"); //采购中心
            detail.put("F_REQUIRETEAM", "平台发展组");
            detail.put("F_REQUIREDEP", "BM054");//平台发展部
            detailList.add(detail);
        }
        object.put("FEntity", detailList);
        jarr.add(object);
        String reqParm = JSON.toJSONString(jarr);
        order.setSyncRequestMsg(reqParm);
        JSONObject obj = syncToErpUtil.inStock(jarr);
        String resutStr = JSON.toJSONString(obj);
        order.setSyncResultMsg(resutStr);
        order.setSyncTime(DateUtil.date());

        if (obj.getString("Code") == null || ! "0".equals(obj.getString("Code"))) {
            log.error("销售退货同步ERP其他入库单失败，异常信息[{}]", resutStr);
            order.setSyncStatus(MabangConstant.SYNC_FAIL);
        } else {
            order.setSyncStatus(MabangConstant.SYNC_SUCCESS);
        }

        this.updateById(order);

        return ResponseData.success();
    }
}
