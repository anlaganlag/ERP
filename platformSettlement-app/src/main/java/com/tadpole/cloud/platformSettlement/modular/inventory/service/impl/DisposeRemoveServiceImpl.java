package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.DisposeRemove;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.DisposeRemoveDetail;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.DisposeRemoveDetailMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.DisposeRemoveMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.RemoveMainMapper;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.DisposeRemoveParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.DisposeRemoveResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IDisposeRemoveDetailService;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IDisposeRemoveService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.SyncToErpConsumer;
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
 * 销毁移除列表 服务实现类
 * </p>
 *
 * @author gal
 * @since 2021-11-22
 */
@Service
public class DisposeRemoveServiceImpl extends ServiceImpl<DisposeRemoveMapper, DisposeRemove> implements IDisposeRemoveService {

  @Autowired
  private IDisposeRemoveDetailService removeDetailService;
  @Resource
  private DisposeRemoveDetailMapper detailMapper;
  @Resource
  private RemoveMainMapper ListingMapper;
  @Autowired
  private SyncToErpConsumer syncToErpConsumer;

  @DataSource(name = "warehouse")
  @Override
  public PageResult<DisposeRemoveResult> findPageBySpec(DisposeRemoveParam param) {
    String[] material = param.getMaterialCode() == null || param.getMaterialCode().equals("") ? null
        : param.getMaterialCode().split(",");
    param.setMaterial(material);
    Page pageContext = getPageContext();
    IPage<DisposeRemoveResult> page = this.baseMapper.customPageList(pageContext, param);
    return new PageResult<>(page);
  }

  @DataSource(name = "warehouse")
  @Override
  public String getQuantity(DisposeRemoveParam param) {
    String[] material = param.getMaterialCode() == null || param.getMaterialCode().equals("") ? null
        : param.getMaterialCode().split(",");
    param.setMaterial(material);
    return this.baseMapper.getQuantity(param);
  }

  @DataSource(name = "warehouse")
  @Override
  public List<DisposeRemoveResult> export(DisposeRemoveParam param) {

    Page pageContext = PageFactory.defaultPage();
    pageContext.setSize(Integer.MAX_VALUE);
    IPage<DisposeRemoveResult> page = this.baseMapper.customPageList(pageContext, param);
    return page.getRecords();
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void reject(DisposeRemoveParam param) {
    UpdateWrapper<DisposeRemoveDetail> updateWrapper = new UpdateWrapper<>();

    param.setUpdateBy(LoginContext.me().getLoginUser().getAccount());
    updateWrapper
        .eq("id", param.getId())
        .set("IS_CANCEL", 1)
        .set("UPDATE_AT", new Date())
        .set("UPDATE_BY", LoginContext.me().getLoginUser().getAccount());
    detailMapper.update(null, updateWrapper);
    param.setUpdateBy(LoginContext.me().getLoginUser().getAccount());
    String billCode = param.getBillCode();
    if (billCode.contains("-") && billCode.substring(billCode.lastIndexOf("-")).length()<4){

      param.setBillCode(billCode.substring(0,billCode.lastIndexOf("-")));
    }
    this.baseMapper.syncReportReject(param);
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void rejectBatch(List<DisposeRemoveParam> params) {
    DisposeRemoveParam param = new DisposeRemoveParam();
    param.setUpdateBy(LoginContext.me().getLoginUser().getAccount());

    List<String> IdList= params.stream().map(i->i.getId().toString()).collect(Collectors.toList());
    List<List<String>> lists = ListUtil.split(IdList, 1000);

    for (List<String> lst : lists) {
      param.setIdList(lst);
      this.baseMapper.rejectBatch(param);
    }

    for (DisposeRemoveParam item :params) {
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
  public void syncErp(DisposeRemoveParam param) throws IOException {
    DisposeRemove out = this.baseMapper.selectById(param.getOutId());

    String FCreatorId = LoginContext.me().getLoginUser().getAccount();
    if(FCreatorId==null){
      DisposeRemoveDetail detail = removeDetailService.getOne(
          new QueryWrapper<DisposeRemoveDetail>().eq("out_id", param.getOutId()).eq("IS_CANCEL",0).eq("SYNC_STATUS",0).isNotNull("update_by").apply("rownum={0}",1));
      if (StrUtil.isNotEmpty(detail.getUpdateBy())) {
        FCreatorId = detail.getUpdateBy();
      }
    }

    param.setSyncStatus(0);
    List<DisposeRemoveDetail> dlist = this.baseMapper.getSyncList(param);
    JSONArray Jarr = new JSONArray();

    JSONObject object = new JSONObject();
    object.put("FBillNo", out.getBillCode());
    object.put("FBillTypeID", "QTCKD01_SYS");
    object.put("FStockOrgId", out.getInventoryOrganizationCode());
    object.put("FCreatorId", FCreatorId);
    object.put("FStockDirect", "普通");
    object.put("FDate", out.getYear() + "-" + out.getMonth() + "-01");
    object.put("FCustId", "店铺虚拟客户");
    object.put("FBizType", "物料领用");
    object.put("FOwnerTypeIdHead", "业务组织");
    object.put("FBaseCurrId", "PRE001");
    object.put("FNote", "销毁盘亏");

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
      UpdateWrapper<DisposeRemoveDetail> updateWrapper = new UpdateWrapper<>();
      updateWrapper.set("SYNC_STATUS", 1).eq("IS_CANCEL", 0).eq("OUT_ID", param.getOutId());
      removeDetailService.update(null, updateWrapper);
    }

  }

  @DataSource(name = "warehouse")
  @Override
  public List<DisposeRemoveDetail> getSyncList(DisposeRemoveParam param) {
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
  public List<DisposeRemoveResult> emailList() {
    List<DisposeRemoveResult> list = this.baseMapper.emailList();
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
    List<DisposeRemoveParam> list = this.baseMapper.normalList();
    for (DisposeRemoveParam param:list) {
      try {
        this.syncErp(param);
      } catch (Exception e) {
        e.printStackTrace();
        continue;
      }
    }
  }

  @DataSource(name = "warehouse")
  @Override
  public ResponseData editMat(DisposeRemoveParam param) {
    UpdateWrapper<DisposeRemoveDetail> updateWrapper = new UpdateWrapper<>();
    updateWrapper.eq("ID", param.getId())
        .set("MATERIAL_CODE", param.getMaterialCode())
        .set("Team", param.getTeam())
        .set("Department", param.getDepartment());
    removeDetailService.update(null, updateWrapper);
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
