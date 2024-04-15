package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.SalesReturn;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.SalesReturnDetail;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.FbaCustomerReturnsMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.SalesReturnDetailMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.SalesReturnMapper;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.SalesReturnParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.SalesReturnResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.ISalesReturnDetailService;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.ISalesReturnService;
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
 * 销售退货列表 服务实现类
 * </p>
 *
 * @author gal
 * @since 2021-11-22
 */
@Service
public class SalesReturnServiceImpl extends ServiceImpl<SalesReturnMapper, SalesReturn> implements ISalesReturnService {

  @Autowired
  private ISalesReturnDetailService detailService;
  @Resource
  private SalesReturnDetailMapper detailMapper;
  @Resource
  private FbaCustomerReturnsMapper ListingMapper;
  @Autowired
  private SyncToErpConsumer syncToErpConsumer;

  @DataSource(name = "warehouse")
  @Override
  public PageResult<SalesReturnResult> findPageBySpec(SalesReturnParam param) {
    String[] material = StringUtils.isBlank(param.getMaterialCode()) ? null : param.getMaterialCode().split(",");
    param.setMaterial(material);
    return new PageResult<>(this.baseMapper.customPageList(PageFactory.defaultPage(), param));
  }

  @DataSource(name = "warehouse")
  @Override
  public String getQuantity(SalesReturnParam param) {
    String[] material = StringUtils.isBlank(param.getMaterialCode()) ? null : param.getMaterialCode().split(",");
    param.setMaterial(material);
    return this.baseMapper.getQuantity(param);
  }

  @DataSource(name = "warehouse")
  @Override
  public List<SalesReturnResult> export(SalesReturnParam param) {
    Page pageContext = PageFactory.defaultPage();
    pageContext.setSize(Integer.MAX_VALUE);
    return this.baseMapper.customPageList(pageContext, param).getRecords();
  }

  @DataSource(name = "warehouse")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void syncErp(SalesReturnParam param) throws IOException {
    SalesReturn out = this.baseMapper.selectById(param.getOutId());

    //任务类型 0：手动同步，1：定时同步
    String FCreatorId = null;
    if(param.getSyncType() != null && param.getSyncType() == 1){
      SalesReturnDetail detail = detailService.getOne(
              new QueryWrapper<SalesReturnDetail>().eq("out_id", param.getOutId()).eq("IS_CANCEL",0).eq("SYNC_STATUS",0).isNotNull("update_by").apply("rownum={0}",1));
      if (StrUtil.isNotEmpty(detail.getUpdateBy())) {
        FCreatorId = detail.getUpdateBy();
      }
    }else{
      FCreatorId = LoginContext.me().getLoginUser().getAccount();
    }

    param.setSyncStatus(0);
    List<SalesReturnDetail> dlist = this.baseMapper.getSyncList(param);
    JSONArray Jarr = new JSONArray();

    JSONObject object = new JSONObject();
    object.put("FBillNo", out.getBillCode());
    object.put("FDate", out.getYear() + "-" + out.getMonth() + "-01");
    object.put("FCreatorId", FCreatorId);
    object.put("FSaleOrgId", out.getSalesOrganizationCode());
    object.put("FRetcustId", "店铺虚拟客户");
    object.put("FStockOrgId", out.getInventoryOrganizationCode());
    object.put("FHeadNote", "销售退货");
    object.put("FEntity", dlist);

    Jarr.add(object);

    JSONObject obj = syncToErpConsumer.saleReturnStock(Jarr);

    if (obj.getString("Code") == null || !obj.getString("Code").equals("0")) {
      String responseMsg = null;
      JSONArray responseResult = JSON.parseArray(obj.getString("Response"));
      if (CollectionUtil.isNotEmpty(responseResult)) {
        responseMsg = responseResult.getJSONObject(0).getString("SubMessage");
      }
      throw new IOException("同步erp失败！" + obj.getString("Message") + "，详细信息:" + responseMsg);
    } else {
      UpdateWrapper<SalesReturnDetail> updateWrapper = new UpdateWrapper<>();
      updateWrapper.set("SYNC_STATUS", 1).eq("IS_CANCEL", 0).eq("OUT_ID", param.getOutId());
      detailService.update(null, updateWrapper);
    }
  }

  @DataSource(name = "warehouse")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public List<SalesReturnDetail> getSyncList(SalesReturnParam param) {
    return this.baseMapper.getSyncList(param);
  }

  @DataSource(name = "warehouse")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateDetailList() throws IOException {
    ListingMapper.updateDetailList();
  }

  @DataSource(name = "warehouse")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateFileDetailList() throws IOException {
    ListingMapper.updateFileDetailList();
  }

  @DataSource(name = "warehouse")
  @Override
  public List<SalesReturnResult> emailList() {
    return this.baseMapper.emailList();
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData reject(SalesReturnParam param) {
    if(StringUtils.isEmpty(String.valueOf(param.getId()))){
      return ResponseData.error("入参异常，ID不能为空！");
    }
    SalesReturnDetail detail = detailMapper.selectById(param.getId());
    if(detail == null){
      return ResponseData.error("数据不存在，作废失败！");
    }
    if(detail.getIsCancel() != 0 || detail.getSyncStatus() != 0){
      return ResponseData.error("当前数据不支持作废，作废失败！");
    }

    //更新明细表为作废
    String account = LoginContext.me().getLoginUser().getAccount();
    UpdateWrapper<SalesReturnDetail> updateWrapper = new UpdateWrapper<>();
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
    //作废销售退货FBA_CUSTOMER_RETURNS源报告数据
    this.baseMapper.syncReportReject(param);
    return ResponseData.success();
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData rejectBatch(List<SalesReturnParam> params) {
    List<String> idList= params.stream().map(i->i.getId().toString()).collect(Collectors.toList());
    if(idList.size() > 1000){
      return ResponseData.error("单次批量作废数量不能超过1000条！");
    }
    QueryWrapper<SalesReturnDetail> qw = new QueryWrapper();
    qw.eq("IS_CANCEL", 0).eq("SYNC_STATUS", 0).in("ID", idList);
    Integer detailCounts = detailMapper.selectCount(qw);
    if(idList.size() != detailCounts){
      return ResponseData.error("存在不能作废的数据，批量作废失败！");
    }

    //批量作废销售退货列表数据
    SalesReturnParam param = new SalesReturnParam();
    param.setUpdateBy(LoginContext.me().getLoginUser().getAccount());
    param.setIdList(idList);
    this.baseMapper.rejectBatch(param);

    //批量作废销售退货FBA_CUSTOMER_RETURNS源报告数据
    for (SalesReturnParam item :params) {
      String billCode = item.getBillCode();
      if (billCode.contains("-") && billCode.substring(billCode.lastIndexOf("-")).length()<4
      ){
        item.setBillCode(billCode.substring(0,billCode.lastIndexOf("-")));
      }
      item.setUpdateBy(LoginContext.me().getLoginUser().getAccount());
    }
    this.baseMapper.syncReportBatchReject(params);
    return ResponseData.success();
  }

  @DataSource(name = "warehouse")
  @Override
  public void updateSyncStatus() {
    this.baseMapper.updateSyncStatus();
  }

  @DataSource(name = "warehouse")
  @Override
  public void timerSync() throws IOException {
    List<SalesReturnParam> list = this.baseMapper.normalList();
    for (SalesReturnParam param:list) {
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
  public ResponseData editMat(SalesReturnParam param) {
    UpdateWrapper<SalesReturnDetail> updateWrapper = new UpdateWrapper<>();
    updateWrapper.eq("ID", param.getId())
        .set("MATERIAL_CODE", param.getMaterialCode())
        .set("Team", param.getTeam())
        .set("Department", param.getDepartment())
        .set("UPDATE_AT", DateUtil.date())
        .set("UPDATE_BY", LoginContext.me().getLoginUser().getAccount());
    detailService.update(null, updateWrapper);
    return ResponseData.success();
  }

  @DataSource(name = "warehouse")
  @Override
  public void updateCanSyncNormal() {
    this.baseMapper.updateCanSyncNormal();
  }

  @DataSource(name = "warehouse")
  @Override
  public ResponseData disable(SalesReturnParam param) {
    if(StringUtils.isEmpty(String.valueOf(param.getId()))){
      return ResponseData.error("入参异常，ID不能为空！");
    }
    SalesReturnDetail detail = detailMapper.selectById(param.getId());
    if(detail == null){
      return ResponseData.error("数据不存在，无需处理失败！");
    }
    if(detail.getIsCancel() != 0 || detail.getSyncStatus() != 0){
      return ResponseData.error("当前数据不支持无需处理操作，无需处理失败！");
    }

    //更新明细表为无需处理
    String account = LoginContext.me().getLoginUser().getAccount();
    UpdateWrapper<SalesReturnDetail> updateWrapper = new UpdateWrapper<>();
    updateWrapper.eq("ID", param.getId())
            .set("IS_CANCEL", 2)//2：无需处理
            .set("SYNC_STATUS", 2)//2：无需处理
            .set("UPDATE_AT", new Date())
            .set("UPDATE_BY", account);
    detailMapper.update(null, updateWrapper);
    return ResponseData.success();
  }
}
