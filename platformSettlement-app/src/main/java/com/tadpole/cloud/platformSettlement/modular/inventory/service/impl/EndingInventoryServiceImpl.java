package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.hutool.core.collection.CollUtil;
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
import com.tadpole.cloud.platformSettlement.api.inventory.entity.EndingInventory;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.EndingInventoryDetail;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.EndingInventoryDetailMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.EndingInventoryMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.MonthlyInventoryHistoryMapper;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.EndingInventoryParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.EndingInventoryResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IEndingInventoryDetailService;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IEndingInventoryService;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 期末库存列表 服务实现类
 * </p>
 *
 * @author gal
 * @since 2021-11-22
 */
@Service
public class EndingInventoryServiceImpl extends ServiceImpl<EndingInventoryMapper, EndingInventory> implements IEndingInventoryService {

  @Autowired
  private IEndingInventoryService service;
  @Resource
  EndingInventoryDetailMapper detailMapper;
  @Resource
  MonthlyInventoryHistoryMapper ListingMapper;
  @Autowired
  IEndingInventoryDetailService detailService;
  @Autowired
  private SyncToErpConsumer syncToErpConsumer;

  @DataSource(name = "warehouse")
  @Override
  public PageResult<EndingInventoryResult> findPageBySpec(EndingInventoryParam param) {
    String[] material = param.getMaterialCode() == null || param.getMaterialCode().equals("") ? null
        : param.getMaterialCode().split(",");
    param.setMaterial(material);
    IPage<EndingInventoryResult> page = this.baseMapper.customPageList(PageFactory.defaultPage(), param);
    return new PageResult<>(page);
  }

  @DataSource(name = "warehouse")
  @Override
  public String getQuantity(EndingInventoryParam param) {
    String[] material = param.getMaterialCode() == null || param.getMaterialCode().equals("") ? null
        : param.getMaterialCode().split(",");
    param.setMaterial(material);
    return this.baseMapper.getQuantity(param);
  }

  @DataSource(name = "warehouse")
  @Override
  public ResponseData syncErp(EndingInventoryParam param) {
    JSONArray Jarr = new JSONArray();
    String[] strs = param.getMonValue().split("-");
    param.setYear(strs[0]);
    param.setMonth(strs[1]);

    Date end = new Date(param.getYear() + "/" + param.getMonth() + "/01");
    final Calendar cal = Calendar.getInstance();

    cal.setTime(end);
    final int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

    //获取单据头数据
    List<EndingInventory> endings = this.baseMapper.queryEnding(param);
    List<EndingInventory> syncEndings = new ArrayList<>();
    String FCreatorId = LoginContext.me().getLoginUser().getAccount();
    if (FCreatorId == null) {
      if (CollUtil.isNotEmpty(endings)) {
        EndingInventoryDetail detail = detailService.getOne(
            new QueryWrapper<EndingInventoryDetail>().select("UPDATE_USER")
                .eq("OUT_ID", endings.get(0).getId())
                .eq("IS_CANCEL", 0)
                .eq("SYNC_STATUS", 0)
                .isNotNull("UPDATE_USER")
                .apply("rownum={0}", 1));
        if (StrUtil.isNotEmpty(detail.getUpdateUser())) {
          FCreatorId = detail.getUpdateUser();
        }
      }
    }

    for (EndingInventory ending : endings) {
      ending.setSyncStatus(0);
      //获取同步ERP的明细数据
      List<EndingInventoryDetail> details = this.baseMapper.getSyncDetail(ending);
      if (details.size() > 0) {
        JSONObject object = new JSONObject();
        object.put("FBillNo", ending.getBillCode());
        object.put("FName", "亚马逊库存盘点");
        object.put("FNote", "亚马逊库存盘点");
        object.put("FCreatorId", FCreatorId);
        object.put("FStockOrgId", ending.getInventoryOrganizationCode());
        object.put("FBackUpType", "CloseDate");
        object.put("FZeroStockInCount", "1");
        object.put("FCheckQtyDefault", "0");
        object.put("FBackUpDATE", param.getMonValue() + "-" + last);
        object.put("FEntity", details);
        Jarr.add(object);
        syncEndings.add(ending);
      }
    }
    //调用ERP盘点方案接口
    JSONObject obj = syncToErpConsumer.endingInventory(Jarr);
    if (obj.getString("Code") == null || !obj.getString("Code").equals("0")) {
      String responseMsg = null;
      JSONArray responseResult = JSON.parseArray(obj.getString("Response"));
      if (CollectionUtil.isNotEmpty(responseResult)) {
        responseMsg = responseResult.getJSONObject(0).getString("SubMessage");
      }
      return ResponseData.error("同步erp失败！" + obj.getString("Message") + "，详细信息:" + responseMsg);
    } else {
      Boolean allPass = true;
      for (int j = 0; j < obj.getJSONArray("Response").size(); j++) {
        if (obj.getJSONArray("Response").getJSONObject(j).getString("SubCode").equals("0")) {
          UpdateWrapper<EndingInventoryDetail> updateWrapper = new UpdateWrapper<>();
          updateWrapper.eq("OUT_ID", syncEndings.get(j).getId()).eq("IS_CANCEL", 0).set("SYNC_STATUS", 1);
          detailMapper.update(null, updateWrapper);
        } else {
          allPass = false;
        }
      }

      if (allPass) {
        return ResponseData.success("同步erp成功！");
      } else {
        return ResponseData.success("部分同步erp成功，部分失败!");
      }
    }
  }

  @DataSource(name = "warehouse")
  @Override
  public List<EndingInventoryResult> export(EndingInventoryParam param) {
    Page pageContext = PageFactory.defaultPage();
    pageContext.setSize(Integer.MAX_VALUE);
    IPage<EndingInventoryResult> page = this.baseMapper.customPageList(pageContext, param);
    return page.getRecords();
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void reject(EndingInventoryParam param) {
    UpdateWrapper<EndingInventoryDetail> updateWrapper = new UpdateWrapper<>();
    LoginContext current = SpringContext.getBean(LoginContext.class);
    LoginUser currentUser = current.getLoginUser();
    param.setUpdateUser(currentUser.getAccount());
    updateWrapper
        .eq("id", param.getId())
        .set("IS_CANCEL", 1)
        .set("UPDATE_TIME", new Date())
        .set("UPDATE_USER", currentUser.getAccount());
    detailMapper.update(null, updateWrapper);
    String billCode = param.getBillCode();
    if (billCode.contains("-") && billCode.substring(billCode.lastIndexOf("-")).length() < 4
    ) {
      param.setBillCode(billCode.substring(0, billCode.lastIndexOf("-")));
    }
    //作废源报告monthly_inventory_history
    this.baseMapper.syncReportReject(param);
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void rejectBatch(List<EndingInventoryParam> params) {
    EndingInventoryParam param = new EndingInventoryParam();
    param.setUpdateUser(LoginContext.me().getLoginUser().getAccount());

    List<String> IdList = params.stream().map(i -> i.getId().toString())
        .collect(Collectors.toList());
    List<List<String>> lists = ListUtil.split(IdList, 1000);

    for (List<String> lst : lists) {
      param.setIdList(lst);
      this.baseMapper.rejectBatch(param);
    }

    for (EndingInventoryParam item : params) {
      String billCode = item.getBillCode();
      if (billCode.contains("-") && billCode.substring(billCode.lastIndexOf("-")).length() < 4
      ) {
        item.setBillCode(billCode.substring(0, billCode.lastIndexOf("-")));
      }
      item.setUpdateUser(LoginContext.me().getLoginUser().getAccount());
    }
    //批量作废源报告：monthly_inventory_history
    this.baseMapper.syncReportBatchReject(params);
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
  public List<EndingInventoryResult> emailList() {
    return this.baseMapper.emailList();
  }

  @DataSource(name = "warehouse")
  @Override
  public void updateSyncStatus() {
    this.baseMapper.updateSyncStatus();
  }

  @DataSource(name = "warehouse")
  @Override
  public void timerSync() throws IOException, ParseException {
    List<EndingInventoryParam> list = this.baseMapper.normalList();
    for (EndingInventoryParam param : list) {
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
  public ResponseData editMat(EndingInventoryParam param) {
    UpdateWrapper<EndingInventoryDetail> updateWrapper = new UpdateWrapper<>();
    updateWrapper.eq("ID", param.getId())
        .eq("IS_CANCEL", 0)
        .eq("SYNC_STATUS", 0)
        .set("MATERIAL_CODE", param.getMaterialCode())
        .set("Team", param.getTeam())
        .set("Department", param.getDepartment());
    detailService.update(null, updateWrapper);
    return ResponseData.success();
  }
}
