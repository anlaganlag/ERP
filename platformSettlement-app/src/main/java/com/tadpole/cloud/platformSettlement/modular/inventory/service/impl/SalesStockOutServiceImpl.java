package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.SalesStockOut;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.SalesStockOutDetail;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.FbaShipmentSalesMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.SalesStockOutDetailMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.SalesStockOutMapper;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.SalesStockOutParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.SalesStockOutResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.ISalesStockOutDetailService;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.ISalesStockOutService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.SyncToErpConsumer;
import org.apache.commons.lang3.StringUtils;
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
 * 销售出库列表 服务实现类
 * </p>
 *
 * @author gal
 * @since 2021-11-22
 */
@Service
public class SalesStockOutServiceImpl extends ServiceImpl<SalesStockOutMapper, SalesStockOut> implements ISalesStockOutService {

  @Autowired
  ISalesStockOutDetailService detailService;
  @Resource
  SalesStockOutDetailMapper detailMapper;
  @Resource
  FbaShipmentSalesMapper ListingMapper;
  @Autowired
  private SyncToErpConsumer syncToErpConsumer;

  @DataSource(name = "warehouse")
  @Override
  public PageResult<SalesStockOutResult> findPageBySpec(SalesStockOutParam param) {
    String[] material = StringUtils.isBlank(param.getMaterialCode()) ? null : param.getMaterialCode().split(",");
    param.setMaterial(material);
    return new PageResult<>(this.baseMapper.customPageList(PageFactory.defaultPage(), param));
  }

  @DataSource(name = "warehouse")
  @Override
  public List<SalesStockOutResult> export(SalesStockOutParam param) {
    String[] material = StringUtils.isBlank(param.getMaterialCode()) ? null : param.getMaterialCode().split(",");
    param.setMaterial(material);
    Page pageContext = PageFactory.defaultPage();
    pageContext.setSize(Integer.MAX_VALUE);
    return this.baseMapper.customPageList(pageContext, param).getRecords();
  }

  @DataSource(name = "warehouse")
  @Override
  public List<SalesStockOutResult> emailList() {
    List<SalesStockOutResult> list = this.baseMapper.emailList();
    return list;
  }

  @DataSource(name = "warehouse")
  @Override
  public String getQuantity(SalesStockOutParam param) {
    String[] material = StringUtils.isBlank(param.getMaterialCode()) ? null : param.getMaterialCode().split(",");
    param.setMaterial(material);
    return this.baseMapper.getQuantity(param);
  }

  @DataSource(name = "warehouse")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void syncErp(SalesStockOutParam param) throws IOException {
    SalesStockOut out = this.baseMapper.selectById(param.getOutId());

    //任务类型 0：手动同步，1：定时同步
    String FCreatorId = null;
    if(param.getSyncType() != null && param.getSyncType() == 1){
      SalesStockOutDetail detail = detailService.getOne(new QueryWrapper<SalesStockOutDetail>().eq("out_id", param.getOutId()).eq("IS_CANCEL",0).eq("SYNC_STATUS",0).isNotNull("update_by").apply("ROWNUM={0}",1));
      if (StrUtil.isNotEmpty(detail.getUpdateBy())) {
        FCreatorId = detail.getUpdateBy();
      }
    }else{
      FCreatorId = LoginContext.me().getLoginUser().getAccount();
    }

    param.setSyncStatus(0);
    List<SalesStockOutDetail> dlist = this.baseMapper.getSyncList(param);
    JSONArray Jarr = new JSONArray();

    JSONObject object = new JSONObject();
    object.put("FBillNo", out.getBillCode());
    object.put("FDate", out.getYear() + "-" + out.getMonth() + "-01");
    object.put("FCreatorId", FCreatorId);
    object.put("FSaleOrgId", out.getSalesOrganizationCode());
    object.put("FCustomerID", "店铺虚拟客户");
    object.put("FCorrespondOrgId", "店铺虚拟客户");
    object.put("FStockOrgId", out.getInventoryOrganizationCode());
    object.put("FNote", "销售出库");
    object.put("FPayerID", "店铺虚拟客户");
    object.put("FEntity", dlist);

    Jarr.add(object);

    JSONObject obj = syncToErpConsumer.saleOutStock(Jarr);

    if (obj.getString("Code") == null || !obj.getString("Code").equals("0")) {
      String responseMsg = null;
      JSONArray responseResult = JSON.parseArray(obj.getString("Response"));
      if (ObjectUtil.isNotNull(responseResult)) {
        responseMsg = responseResult.getJSONObject(0).getString("SubMessage");
      }
      throw new IOException("同步erp失败！" + obj.getString("Message") + "，详细信息:" + responseMsg);
    } else {
      UpdateWrapper<SalesStockOutDetail> updateWrapper = new UpdateWrapper<>();
      updateWrapper.set("SYNC_STATUS", 1).eq("IS_CANCEL", 0).eq("OUT_ID", param.getOutId());
      detailService.update(null, updateWrapper);
    }
  }

  @DataSource(name = "warehouse")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public List<SalesStockOutDetail> getSyncList(SalesStockOutParam param) {
    return this.baseMapper.getSyncList(param);
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData reject(SalesStockOutParam param) {
    if(StringUtils.isEmpty(String.valueOf(param.getId()))){
      return ResponseData.error("入参异常，ID不能为空！");
    }
    SalesStockOutDetail detail = detailMapper.selectById(param.getId());
    if(detail == null){
      return ResponseData.error("数据不存在，作废失败！");
    }
    if(detail.getIsCancel() != 0 || detail.getSyncStatus() != 0){
      return ResponseData.error("当前数据不支持作废，作废失败！");
    }

    //更新明细表为作废
    String account = LoginContext.me().getLoginUser().getAccount();
    UpdateWrapper<SalesStockOutDetail> updateWrapper = new UpdateWrapper<>();
    updateWrapper.eq("ID", param.getId())
        .set("IS_CANCEL", 1)
        .set("UPDATE_AT", new Date())
        .set("UPDATE_BY", account);
    detailMapper.update(null, updateWrapper);
    param.setUpdateBy(account);
    String billCode = param.getBillCode();
    if (billCode.contains("-") && billCode.substring(billCode.lastIndexOf("-")).length()<4){
      //拿到单据编码去除流水号
      param.setBillCode(billCode.substring(0,billCode.lastIndexOf("-")));
    }
    //作废销售出库Customer Shipment Sales源报告数据
    this.baseMapper.syncReportReject(param);
    return ResponseData.success();
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData rejectBatch(List<SalesStockOutParam> params) {
    List<String> idList = params.stream().map(i->i.getId().toString()).collect(Collectors.toList());
    if(idList.size() > 1000){
      return ResponseData.error("单次批量作废数量不能超过1000条！");
    }
    QueryWrapper<SalesStockOutDetail> qw = new QueryWrapper();
    qw.eq("IS_CANCEL", 0).eq("SYNC_STATUS", 0).in("ID", idList);
    Integer detailCounts = detailMapper.selectCount(qw);
    if(idList.size() != detailCounts){
      return ResponseData.error("存在不能作废的数据，批量作废失败！");
    }

    //批量作废销售出库列表数据
    SalesStockOutParam param = new SalesStockOutParam();
    param.setUpdateBy(LoginContext.me().getLoginUser().getAccount());
    param.setIdList(idList);
    this.baseMapper.rejectBatch(param);

    //批量作废销售出库Customer Shipment Sales源报告数据
    for (SalesStockOutParam item :params) {
      String billCode = item.getBillCode();
      if (billCode.contains("-") && billCode.substring(billCode.lastIndexOf("-")).length()<4){
        item.setBillCode(billCode.substring(0,billCode.lastIndexOf("-")));
      }
      item.setUpdateBy(LoginContext.me().getLoginUser().getAccount());
    }
    this.baseMapper.syncReportBatchReject(params);
    return ResponseData.success();
  }

  @DataSource(name = "warehouse")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateDetailList() {
    ListingMapper.updateDetailList();
  }

  @DataSource(name = "warehouse")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateFileDetailList() {
    ListingMapper.updateFileDetailList();
  }

  @DataSource(name = "warehouse")
  @Override
  public void updateSyncStatus() {
    this.baseMapper.updateSyncStatus();
  }

  @DataSource(name = "warehouse")
  @Override
  public void updateCanSyncNormal() {
    this.baseMapper.updateCanSyncNormal();
  }

  @DataSource(name = "warehouse")
  @Override
  public ResponseData disable(SalesStockOutParam param) {
    if(StringUtils.isEmpty(String.valueOf(param.getId()))){
      return ResponseData.error("入参异常，ID不能为空！");
    }
    SalesStockOutDetail detail = detailMapper.selectById(param.getId());
    if(detail == null){
      return ResponseData.error("数据不存在，无需处理失败！");
    }
    if(detail.getIsCancel() != 0 || detail.getSyncStatus() != 0){
      return ResponseData.error("当前数据不支持无需处理操作，无需处理失败！");
    }

    //更新明细表为无需处理
    String account = LoginContext.me().getLoginUser().getAccount();
    UpdateWrapper<SalesStockOutDetail> updateWrapper = new UpdateWrapper<>();
    updateWrapper.eq("ID", param.getId())
            .set("IS_CANCEL", 2)//2：无需处理
            .set("SYNC_STATUS", 2)//2：无需处理
            .set("UPDATE_AT", new Date())
            .set("UPDATE_BY", account);
    detailMapper.update(null, updateWrapper);
    return ResponseData.success();
  }

  @DataSource(name = "warehouse")
  @Override
  public void timerSync() throws IOException {
    List<SalesStockOutParam> list = this.baseMapper.normalList();
    for (SalesStockOutParam param:list) {
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
  public ResponseData editMat(SalesStockOutParam param) {
      UpdateWrapper<SalesStockOutDetail> updateWrapper = new UpdateWrapper<>();
      updateWrapper.eq("ID", param.getId())
          .set("MATERIAL_CODE", param.getMaterialCode())
          .set("Team", param.getTeam())
          .set("Department", param.getDepartment())
          .set("UPDATE_AT", DateUtil.date())
          .set("UPDATE_BY", LoginContext.me().getLoginUser().getAccount());
      detailService.update(null, updateWrapper);
      return ResponseData.success();
  }
}
