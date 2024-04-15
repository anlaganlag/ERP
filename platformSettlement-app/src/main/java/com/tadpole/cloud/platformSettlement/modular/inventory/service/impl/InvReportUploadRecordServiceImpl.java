package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.InvReportUploadRecord;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.RemovalShipmentDetail;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.InvReportUploadRecordMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.RemovalShipmentDetailMapper;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.InvReportUploadRecordParam;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IInvReportUploadRecordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 上传记录表 服务实现类
 * </p>
 *
 * @author gal
 * @since 2021-11-26
 */
@Service
public class InvReportUploadRecordServiceImpl extends ServiceImpl<InvReportUploadRecordMapper, InvReportUploadRecord> implements IInvReportUploadRecordService {

  @Autowired
  private IInvReportUploadRecordService service;
  @Resource
  private RemovalShipmentDetailMapper removalShipmentDetailMapper;

  private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @DataSource(name = "warehouse")
  @Override
  public PageResult<InvReportUploadRecord> findPageBySpec(InvReportUploadRecordParam param) {
    Page pageContext = param.getPageContext();
    IPage<InvReportUploadRecord> page = this.baseMapper.customPageList(pageContext, param);
    return new PageResult<>(page);
  }

  @DataSource(name = "warehouse")
  @Override
  public void insertFbaShipmentSalesRecord(InvReportUploadRecordParam param) {
    String dateStamp=sdf.format(new Date());
    String response=dateStamp.replaceAll("[[\\s-:punct:]]","");
    LoginContext current = SpringContext.getBean(LoginContext.class);
    LoginUser currentUser = current.getLoginUser();
    InvReportUploadRecord entity = getEntity(param);
    entity.setParseStatus("待解析");
    entity.setCreateBy(currentUser.getName());
    entity.setCreateAt(new Date());
    entity.setReportType("FbaShipmentSales");
    entity.setUploadMark(response);
    this.save(entity);
  }

  @DataSource(name = "warehouse")
  @Override
  public void insertFbaCustomerReturnsRecord(InvReportUploadRecordParam param) {
    String dateStamp=sdf.format(new Date());
    String response=dateStamp.replaceAll("[[\\s-:punct:]]","");
    LoginContext current = SpringContext.getBean(LoginContext.class);
    LoginUser currentUser = current.getLoginUser();
    InvReportUploadRecord entity = getEntity(param);
    entity.setParseStatus("待解析");
    entity.setCreateBy(currentUser.getName());
    entity.setCreateAt(new Date());
    entity.setReportType("FbaCustomerReturns");
    entity.setUploadMark(response);
    this.save(entity);
  }

  @DataSource(name = "warehouse")
  @Override
  public void insertRemovalOrderDetailRecord(InvReportUploadRecordParam param) {
    String dateStamp=sdf.format(new Date());
    String response=dateStamp.replaceAll("[[\\s-:punct:]]","");
    LoginContext current = SpringContext.getBean(LoginContext.class);
    LoginUser currentUser = current.getLoginUser();
    InvReportUploadRecord entity = getEntity(param);
    entity.setParseStatus("待解析");
    entity.setCreateBy(currentUser.getName());
    entity.setCreateAt(new Date());
    entity.setReportType("RemovalOrderDetail");
    entity.setUploadMark(response);
    this.save(entity);
  }

  @DataSource(name = "warehouse")
  @Override
  public void insertRemoveMainRecord(InvReportUploadRecordParam param) {
    String dateStamp=sdf.format(new Date());
    String response=dateStamp.replaceAll("[[\\s-:punct:]]","");
    LoginContext current = SpringContext.getBean(LoginContext.class);
    LoginUser currentUser = current.getLoginUser();
    InvReportUploadRecord entity = getEntity(param);
    entity.setParseStatus("待解析");
    entity.setCreateBy(currentUser.getName());
    entity.setCreateAt(new Date());
    entity.setReportType("RemoveMain");
    entity.setUploadMark(response);
    this.save(entity);
  }

  @DataSource(name = "warehouse")
  @Override
  public void insertFbaInventoryAdjustmentsRecord(InvReportUploadRecordParam param) {
    String dateStamp=sdf.format(new Date());
    String response=dateStamp.replaceAll("[[\\s-:punct:]]","");
    LoginContext current = SpringContext.getBean(LoginContext.class);
    LoginUser currentUser = current.getLoginUser();
    InvReportUploadRecord entity = getEntity(param);
    entity.setParseStatus("待解析");
    entity.setCreateBy(currentUser.getName());
    entity.setCreateAt(new Date());
    entity.setReportType("FbaInventoryAdjustments");
    entity.setUploadMark(response);
    this.save(entity);
  }

  @DataSource(name = "warehouse")
  @Override
  public void insertMonthlyInventoryHistoryRecord(InvReportUploadRecordParam param) {
    String dateStamp=sdf.format(new Date());
    String response=dateStamp.replaceAll("[[\\s-:punct:]]","");
    LoginContext current = SpringContext.getBean(LoginContext.class);
    LoginUser currentUser = current.getLoginUser();
    InvReportUploadRecord entity = getEntity(param);
    entity.setParseStatus("待解析");
    entity.setCreateBy(currentUser.getName());
    entity.setCreateAt(new Date());
    entity.setReportType("MonthlyInventoryHistory");
    entity.setUploadMark(response);
    this.save(entity);
  }

  @DataSource(name = "warehouse")
  @Override
  public void insertRemovalShipmentDetailRecord(InvReportUploadRecordParam param) {
    String dateStamp=sdf.format(new Date());
    String response=dateStamp.replaceAll("[[\\s-:punct:]]","");
    LoginContext current = SpringContext.getBean(LoginContext.class);
    LoginUser currentUser = current.getLoginUser();
    InvReportUploadRecord entity = getEntity(param);
    entity.setParseStatus("待解析");
    entity.setCreateBy(currentUser.getName());
    entity.setCreateAt(new Date());
    entity.setReportType("RemovalShipmentDetail");
    entity.setUploadMark(response);
    this.save(entity);
  }

  @DataSource(name = "warehouse")
  @Override
  public boolean checkFileName(String fileName, InvReportUploadRecordParam param){
    QueryWrapper<RemovalShipmentDetail> qr = new QueryWrapper();
    qr.select("FILE_PATH").eq(StrUtil.isNotEmpty(param.getShopName()),"SYS_SHOPS_NAME",param.getShopName())
            .eq(StrUtil.isNotEmpty(param.getSite()),"SYS_SITE",param.getSite())
            .like("FILE_PATH",fileName);
    List<RemovalShipmentDetail> removalList=removalShipmentDetailMapper.selectList(qr);

    if(removalList.size()>0) {
      return true;
    }
    return false;
  }

  private Page getPageContext() {
    return PageFactory.defaultPage();
  }

  private InvReportUploadRecord getEntity(InvReportUploadRecordParam param) {
    InvReportUploadRecord entity = new InvReportUploadRecord();
    BeanUtil.copyProperties(param, entity);
    return entity;
  }
}
