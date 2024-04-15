package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.InventoryAdjustReduce;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.InventoryAdjustReduceDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.InventoryAdjustReduceParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.InventoryAdjustReduceResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.FbaInventoryAdjustmentsMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.InventoryAdjustReduceDetailMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.InventoryAdjustReduceMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IInventoryAdjustReduceDetailService;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IInventoryAdjustReduceService;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.SyncToErpConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * AZ库存调整列表(减少) 服务实现类
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
@Slf4j
@Service
public class InventoryAdjustReduceServiceImpl extends ServiceImpl<InventoryAdjustReduceMapper, InventoryAdjustReduce> implements IInventoryAdjustReduceService {

  @Autowired
  private IInventoryAdjustReduceService service;
  @Autowired
  private IInventoryAdjustReduceDetailService detailService;
  @Resource
  private InventoryAdjustReduceDetailMapper detailMapper;
  @Resource
  private FbaInventoryAdjustmentsMapper ListingMapper;
  @Autowired
  private SyncToErpConsumer syncToErpConsumer;

  @DataSource(name = "warehouse")
  @Override
  public PageResult<InventoryAdjustReduceResult> findPageBySpec(InventoryAdjustReduceParam param) {
    String[] material = param.getMaterialCode() == null || param.getMaterialCode().equals("") ? null
        : param.getMaterialCode().split(",");
    param.setMaterial(material);
    Page pageContext = getPageContext();
    IPage<InventoryAdjustReduceResult> page = this.baseMapper.customPageList(pageContext, param);
    return new PageResult<>(page);
  }

  @DataSource(name = "warehouse")
  @Override
  public String getQuantity(InventoryAdjustReduceParam param) {
    String[] material = param.getMaterialCode() == null || param.getMaterialCode().equals("") ? null
        : param.getMaterialCode().split(",");
    param.setMaterial(material);
    return this.baseMapper.getQuantity(param);
  }

  @DataSource(name = "warehouse")
  @Override
  public List<InventoryAdjustReduceResult> export(InventoryAdjustReduceParam param) {

    Page pageContext = PageFactory.defaultPage();
    pageContext.setSize(Integer.MAX_VALUE);
    IPage<InventoryAdjustReduceResult> page = this.baseMapper.customPageList(pageContext, param);
    return page.getRecords();
  }

  @DataSource(name = "warehouse")
  @Override
  public void syncErp(InventoryAdjustReduceParam param) throws IOException {
    InventoryAdjustReduce out = this.baseMapper.selectById(param.getOutId());
    //任务类型 0：手动同步，1：定时同步
    String FCreatorId = null;
    if(param.getSyncType() != null && param.getSyncType() == 1){
      InventoryAdjustReduceDetail detail = detailService.getOne(
              new QueryWrapper<InventoryAdjustReduceDetail>().eq("out_id", param.getOutId()).eq("IS_CANCEL",0).eq("SYNC_STATUS",0).isNotNull("update_by").apply("rownum={0}",1));
      if (StrUtil.isNotEmpty(detail.getUpdateBy())) {
        FCreatorId = detail.getUpdateBy();
      }
    }else{
      FCreatorId = LoginContext.me().getLoginUser().getAccount();
    }

    param.setSyncStatus(0);
    List<InventoryAdjustReduceDetail> dlist = this.baseMapper.getSyncList(param);
    JSONArray Jarr = new JSONArray();

    JSONObject object = new JSONObject();
    object.put("FBillNo", out.getBillCode());
    object.put("FBillTypeID", "QTCKD01_SYS");
    object.put("FCreatorId", FCreatorId);
    object.put("FStockOrgId", out.getInventoryOrganizationCode());
    object.put("FStockDirect", "普通");
    object.put("FDate", out.getYear() + "-" + out.getMonth() + "-01");
    object.put("FCustId", "店铺虚拟客户");
    object.put("FBizType", "物料领用");
    object.put("FOwnerTypeIdHead", "业务组织");
    object.put("FBaseCurrId", "PRE001");
    object.put("FNote", "Amazon库存调整");
    object.put("FEntity", dlist);

    Jarr.add(object);

    JSONObject obj = syncToErpConsumer.outStock(Jarr);

    if (obj.getString("Code") == null || !obj.getString("Code").equals("0")) {
      String responseMsg = null;
      JSONArray responseResult = JSON.parseArray(obj.getString("Response"));
      if (CollectionUtil.isNotEmpty(responseResult)) {
        responseMsg = responseResult.getJSONObject(0).getString("SubMessage");
      }
      throw new IOException("同步erp失败！" + obj.getString("Message") + "，详细信息:" + responseMsg);
    } else {
      UpdateWrapper<InventoryAdjustReduceDetail> updateWrapper = new UpdateWrapper<>();
      updateWrapper.set("SYNC_STATUS", 1).eq("IS_CANCEL", 0).eq("OUT_ID", param.getOutId());
      detailService.update(null, updateWrapper);
    }

  }

  @DataSource(name = "warehouse")
  @Override
  public List<InventoryAdjustReduceDetail> getSyncList(InventoryAdjustReduceParam param) {
    return this.baseMapper.getSyncList(param);
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void reject(InventoryAdjustReduceParam param) {
    UpdateWrapper<InventoryAdjustReduceDetail> updateWrapper = new UpdateWrapper<>();
    LoginContext current = SpringContext.getBean(LoginContext.class);
    LoginUser currentUser = current.getLoginUser();
    param.setUpdateBy(currentUser.getAccount());
    updateWrapper
        .eq("id", param.getId())
        .set("IS_CANCEL", 1)
        .set("UPDATE_AT", new Date())
        .set("UPDATE_BY", currentUser.getAccount());
    detailMapper.update(null, updateWrapper);
    String billCode = param.getBillCode();
    if (billCode.contains("-") && billCode.substring(billCode.lastIndexOf("-")).length()<4){

      param.setBillCode(billCode.substring(0,billCode.lastIndexOf("-")));
    }
    this.baseMapper.syncReportReject(param);
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void rejectBatch(List<InventoryAdjustReduceParam> params)   {
    InventoryAdjustReduceParam param = new InventoryAdjustReduceParam();
    param.setUpdateBy(LoginContext.me().getLoginUser().getAccount());

    List<String> IdList= params.stream().map(i->i.getId().toString()).collect(Collectors.toList());
    List<List<String>> lists = ListUtil.split(IdList, 1000);

    for (List<String> lst : lists) {
      param.setIdList(lst);
      this.baseMapper.rejectBatch(param);
    }

    for (InventoryAdjustReduceParam item :params) {
      String billCode = item.getBillCode();
      if (billCode.contains("-") && billCode.substring(billCode.lastIndexOf("-")).length()<4
      ){
        item.setBillCode(billCode.substring(0,billCode.lastIndexOf("-")));
      }
      item.setUpdateBy(LoginContext.me().getLoginUser().getAccount());

    }
    this.baseMapper.syncReportBatchReject(params);
  }

  @DataSource(name = "warehouse")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateReduceDetailList() throws IOException {
    ListingMapper.updateReduceDetailList();
  }

  @DataSource(name = "warehouse")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateFileReduceDetailList() throws IOException {
    ListingMapper.updateFileReduceDetailList();
  }

  @DataSource(name = "warehouse")
  @Override
  public List<InventoryAdjustReduceResult> emailList() {
    List<InventoryAdjustReduceResult> list = this.baseMapper.emailList();
    return list;
  }

  @DataSource(name = "warehouse")
  @Override
  public void updateSyncStatus() {
    this.baseMapper.updateSyncStatus();
  }

  @DataSource(name = "warehouse")
  @Override
  public void timerSync() throws IOException {
    log.info("库存调整减少列表-定时同步");
    List<InventoryAdjustReduceParam> list = this.baseMapper.normalList();
    for (InventoryAdjustReduceParam param:list) {
      try {
        param.setSyncType(1);//任务类型 0：手动同步，1：定时同步
        this.syncErp(param);
      } catch (Exception e) {
        e.printStackTrace();
        continue;
      }
    }
  }

  @DataSource(name = "warehouse")
  @Override
  public ResponseData editMat(InventoryAdjustReduceParam param) {
    UpdateWrapper<InventoryAdjustReduceDetail> updateWrapper = new UpdateWrapper<>();
    updateWrapper.eq("ID", param.getId())
        .set("MATERIAL_CODE", param.getMaterialCode())
        .set("Team", param.getTeam())
        .set("Department", param.getDepartment());
    detailService.update(null, updateWrapper);
    return ResponseData.success();
  }

  @DataSource(name = "warehouse")
  @Override
  public void updateCanSyncNormal() {
    this.baseMapper.updateCanSyncNormal();
  }

  private Page getPageContext() {
    return PageFactory.defaultPage();
  }
}
