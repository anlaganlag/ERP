package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.*;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.RemovalOrderDetailMapper;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemovalOrderDetailParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.RemovalOrderDetailResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 移除订单详情报告 服务实现类
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
@Service
public class RemovalOrderDetailServiceImpl extends ServiceImpl<RemovalOrderDetailMapper, RemovalOrderDetail> implements IRemovalOrderDetailService {

  @Autowired
  private IZZDistributeMcmsService erpService;
  @Autowired
  private IDisposeRemoveDetailService detailService;
  @Autowired
  private IDisposeRemoveService removeService;
  @Autowired
  private IRemoveMainDetialService mainDetialService;

  @DataSource(name = "warehouse")
  @Override
  public PageResult<RemovalOrderDetail> findPageBySpec(RemovalOrderDetailParam param) {
    Page pageContext = getPageContext();
    IPage<RemovalOrderDetail> page = this.baseMapper.customPageList(pageContext, param);
    return new PageResult<>(page);
  }

  @DataSource(name = "warehouse")
  @Override
  public String getQuantity(RemovalOrderDetailParam param) {
    return this.baseMapper.getQuantity(param);
  }

  @DataSource(name = "warehouse")
  @Override
  public List<RemovalOrderDetail> export(RemovalOrderDetailParam param) {

    Page pageContext = PageFactory.defaultPage();
    pageContext.setSize(Integer.MAX_VALUE);
    IPage<RemovalOrderDetail> page = this.baseMapper.customPageList(pageContext, param);
    return page.getRecords();
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void verify(RemovalOrderDetailParam param) {
    LoginContext current = SpringContext.getBean(LoginContext.class);
    LoginUser currentUser = current.getLoginUser();

    UpdateWrapper<RemovalOrderDetail> updateWrapper = new UpdateWrapper<>();

    updateWrapper.eq("id", param.getId())
        .set("VERIFY_AT", new Date())
        .set("VERIFY_BY", currentUser.getAccount())
        .set("UPDATE_BY", currentUser.getAccount())
        .set("VERIFY_STATUS", 1);

    this.baseMapper.update(null, updateWrapper);
  }

  @DataSource(name = "warehouse")
  @Override
  public void reject(RemovalOrderDetailParam param) {
    LoginContext current = SpringContext.getBean(LoginContext.class);
    LoginUser currentUser = current.getLoginUser();

    UpdateWrapper<RemovalOrderDetail> updateWrapper = new UpdateWrapper<>();

    updateWrapper
        .eq("id", param.getId())
        .set("VERIFY_AT", new Date())
        .set("VERIFY_BY", currentUser.getAccount())
        .set("UPDATE_BY", currentUser.getAccount())
        .set("VERIFY_STATUS", 2);
    this.baseMapper.update(null, updateWrapper);
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void toDisposeRemoveList(RemovalOrderDetailParam param) {
    LoginContext current = SpringContext.getBean(LoginContext.class);
    LoginUser currentUser = current.getLoginUser();
    param.setUpdateBy(currentUser.getAccount());

    List<RemovalOrderDetailResult> headerList = this.baseMapper.getListHeader(param);
    for (RemovalOrderDetailResult header : headerList) {
      //生成移除销毁头部
      DisposeRemove out = new DisposeRemove();
      out.setYear(header.getYear());
      out.setMonth(header.getMonth());
      out.setPlatform(header.getPlatform());
      out.setShopName(header.getSysShopsName());
      out.setSite(header.getSysSite());
      out.setOrg(header.getOrg());
      out.setInventoryOrganizationCode(header.getInventoryOrganizationCode());
      out.setWarehouseName(header.getWarehouseName());
      out.setWarehouseCode(header.getWarehouseCode());

      //拼接移除销毁单据编号
      QueryWrapper<DisposeRemove> wp = new QueryWrapper();
      String billCode = "QTCKDXH-" + header.getInventoryOrganizationCode() + "-" +
              header.getYear() +
              header.getMonth() + "000";
      wp.likeRight("BILL_CODE", billCode);
      if (removeService.count(wp) >= 1) {
        billCode += "-" + removeService.count(wp);
      }
      wp.clear();
      out.setBillCode(billCode);
      removeService.save(out);
      header.setOutId(out.getId());
      header.setUpdateBy(currentUser.getAccount());

      //查询原始报告数据插入明细
      List<DisposeRemoveDetail> detailList = this.baseMapper.getDetailList(header);
      detailService.saveBatch(detailList);

      //刷新明细的物料,事业部,Team数据
      this.baseMapper.updateDetailList(header);

      //改源报告生成状态和写入源报告
      this.baseMapper.updateSrcList(header);
    }

  }

  @DataSource(name = "warehouse")
  @Override
  public void verifyBatch(List<RemovalOrderDetailParam> params) {
    for (RemovalOrderDetailParam param : params) {
      this.verify(param);
    }
  }

  @DataSource(name = "warehouse")
  @Override
  public void rejectBatch(List<RemovalOrderDetailParam> params) {
    for (RemovalOrderDetailParam param : params) {
      this.reject(param);
    }
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public String getMaterial(RemovalOrderDetailParam param) {
    return this.baseMapper.getMaterial(param);
  }

  @DataSource(name = "erpcloud")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void assignMaterial(RemovalOrderDetailParam param) {
    ZZDistributeMcms ent = new ZZDistributeMcms();
    ent.setMaterialCode(param.getMat());
    ent.setShopCode(param.getSalesOrganizationCode());
    erpService.save(ent);
  }

  @DataSource(name = "warehouse")
  @Override
  public void syncErpCode(List<ErpOrgCode> codes) {
    for (ErpOrgCode code : codes) {
      this.baseMapper.syncErpCode(code);
    }
  }

  @DataSource(name = "erpcloud")
  @Override
  public List<ErpOrgCode> getErpCode() {
    return this.baseMapper.getErpCode();
  }

  private Page getPageContext() {
    return PageFactory.defaultPage();
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void RemovalOrderDetailSplit(RemovalOrderDetailParam param) {
    //当前月份日期
    SimpleDateFormat ndate=new SimpleDateFormat("yyyy-MM");
    Date now=new Date();
    String nowDate=ndate.format(now);

    //当前时间上个月日期
    Calendar c = Calendar.getInstance();
    c.setTime(new Date());
    c.add(Calendar.MONTH, -1);
    String year=Integer.toString(c.get(Calendar.YEAR));
    String month = Integer.toString(c.get(Calendar.MONTH)+1);

    QueryWrapper<RemovalOrderDetail> queryWrapper = new QueryWrapper();
    queryWrapper.eq("to_char(upload_date, 'yyyy-MM')",
            nowDate).apply("rownum={0}",1);
    RemovalOrderDetail removalOrderDetail=this.getOne(queryWrapper);

    QueryWrapper<RemoveMainDetial> wp = new QueryWrapper();
    wp.eq("YEAR",year)
            .eq("MONTH",month)
            .apply("rownum={0}",1);
    RemoveMainDetial removeMainDetial=mainDetialService.getOne(wp);

    if (removalOrderDetail!=null && removeMainDetial==null)
    {
      //当月移除销毁明细拆分
      this.baseMapper.InsertRemoveMain(param);
      this.baseMapper.InsertRemoveMainDetial(param);
    }
  }

  @DataSource(name = "warehouse")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateDeptDetailList() throws IOException {
    this.baseMapper.updateDeptDetailList();
  }

  @DataSource(name = "warehouse")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateFileDeptDetailList() throws IOException {
    this.baseMapper.updateFileDeptDetailList();
  }

  @DataSource(name = "warehouse")
  @Override
  public void editSites(RemovalOrderDetailParam param) {
    this.baseMapper.editSites(param);
  }

  @DataSource(name = "warehouse")
  @Override
  public void editSite(RemovalOrderDetailParam param) {
    UpdateWrapper<RemovalOrderDetail> updateWrapper = new UpdateWrapper<>();
    updateWrapper.eq("ID",param.getId()).set("SYS_SITE",param.getSite());
    this.baseMapper.update(null,updateWrapper);
  }
}
