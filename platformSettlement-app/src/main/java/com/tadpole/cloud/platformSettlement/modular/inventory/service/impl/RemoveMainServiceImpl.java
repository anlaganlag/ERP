package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.*;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.RemoveMainDetialMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.RemoveMainMapper;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemoveMainParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.RemoveMainResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * Amazon销毁移除主表 服务实现类
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
@Service
public class RemoveMainServiceImpl extends ServiceImpl<RemoveMainMapper, RemoveMain> implements IRemoveMainService {

  @Autowired
  private IDisposeRemoveService service;
  @Autowired
  private IRemoveMainDetialService detailService;
  @Autowired
  private IDisposeRemoveDetailService detailTwoService;
  @Autowired
  private IZZDistributeMcmsService erpService;
  @Resource
  private RemoveMainDetialMapper detailMapper;
  @Autowired
  private RedisTemplate<String, String> redisTemplate;
  /**
   * 生成列表标识
   */
  private static final String TOLIST = "TO_REMOVE_LIST";

  @DataSource(name = "warehouse")
  @Override
  public PageResult<RemoveMainResult> findPageBySpec(RemoveMainParam param) {
    Page pageContext = getPageContext();
    String month = param.getMonth();
    if( StrUtil.isNotEmpty(month)){
      String[] yearMonth = month.split("\\/");
      param.setMonth(yearMonth[0]);
      param.setYear(yearMonth[1]);
    }
    IPage<RemoveMainResult> page = this.baseMapper.customPageList(pageContext, param);
    return new PageResult<>(page);
  }

  @DataSource(name = "warehouse")
  @Override
  public String getQuantity(RemoveMainParam param) {
    Page pageContext = getPageContext();
    String month = param.getMonth();
    if( StrUtil.isNotEmpty(month)){
      String[] yearMonth = month.split("\\/");
      param.setMonth(yearMonth[0]);
      param.setYear(yearMonth[1]);
    }
    pageContext.setSize(Integer.MAX_VALUE);
    return this.baseMapper.getQuantity(param);
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void reject(RemoveMainParam param) {

    UpdateWrapper<RemoveMainDetial> updateWrapper = new UpdateWrapper<>();
    updateWrapper
        .eq("id", param.getId())
        .set("VERIFY_AT", new Date())
        .set("VERIFY_BY", LoginContext.me().getLoginUser().getAccount())
        .set("UPDATE_BY", LoginContext.me().getLoginUser().getAccount())
        .set("VERIFY_STATUS", 2);

    detailMapper.update(null, updateWrapper);
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void rejectBatch(List<RemoveMainParam> params) {
    RemoveMainParam param =  new RemoveMainParam();
    param.setVerifyBy(LoginContext.me().getLoginUser().getAccount());
    param.setUpdateBy(LoginContext.me().getLoginUser().getAccount());
    List<String> IdList= params.stream().map(i->i.getId().toString()).collect(Collectors.toList());
    List<List<String>> lists = ListUtil.split(IdList, 1000);
    //正常取时分配
    for (List<String> lst : lists) {
      param.setIdList(lst);
      this.baseMapper.rejectBatch(param);
    }
  }

  @DataSource(name = "warehouse")
  @Override
  public void verify(RemoveMainParam param) {
    UpdateWrapper<RemoveMainDetial> updateWrapper = new UpdateWrapper<>();
    updateWrapper
        .eq("ID", param.getId())
        .set("VERIFY_AT", new Date())
        .set("VERIFY_BY", LoginContext.me().getLoginUser().getAccount())
        .set("VERIFY_STATUS", 1)

        .set("ORG", param.getInventoryOrganization())
        .set("INVENTORY_ORGANIZATION_CODE", param.getInventoryOrganizationCode())
        .set("WAREHOUSE_NAME", param.getWarehouseName())
        .set("WAREHOUSE_CODE", param.getWarehouseCode());
    detailService.update(null, updateWrapper);
  }

  @DataSource(name = "warehouse")
  @Override
  public List<RemoveMainResult> export(RemoveMainParam param) {

    Page pageContext = PageFactory.defaultPage();
    pageContext.setSize(Integer.MAX_VALUE);
    String month = param.getMonth();
    if( StrUtil.isNotEmpty(month)){
      String[] yearMonth = month.split("\\/");
      param.setMonth(yearMonth[0]);
      param.setYear(yearMonth[1]);
    }
    IPage<RemoveMainResult> page = this.baseMapper.customPageList(pageContext, param);
    return page.getRecords();
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public String getMaterial(RemoveMainParam param) {
    return this.baseMapper.getMaterial(param);
  }

  @DataSource(name = "erpcloud")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void assignMaterial(RemoveMainParam param) {
    ZZDistributeMcms ent = new ZZDistributeMcms();
    ent.setMaterialCode(param.getMat());
    ent.setShopCode(param.getSalesOrganizationCode());
    erpService.save(ent);
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData toDisposeRemoveList(RemoveMainParam param) {
    BoundValueOperations toList = redisTemplate.boundValueOps(TOLIST);
    //从redis里面取值如果非空则为正在生成!
    if (toList.get() != null && StrUtil.isNotEmpty((String)toList.get())){
      return ResponseData.error("正在生成中!");
    }
    try {
      //为空则设为正在生成!
      toList.set("正在生成中!");
      List<RemoveMainResult> headerList = this.baseMapper.getListHeader(param);
      if (CollectionUtil.isEmpty(headerList)) {
        return ResponseData.error("无可生成的数据!");
      }
      for (RemoveMainResult header : headerList) {

        DisposeRemove out = getListEntity(header);
        //生成销售出库头部ENDING_INVENTORY
        out.setUpdateBy(LoginContext.me().getLoginUser().getAccount());
        out.setUpdateAt(new  Date());

        //拼接销售出库单据编号
        QueryWrapper<DisposeRemove> wp = new QueryWrapper();
        List<DisposeRemoveDetail> detailList = this.baseMapper.getDetailList(header);
        String billCodeBase = "QTCKDXH-" + header.getInventoryOrganizationCode() + "-" +
            header.getYear() +
            header.getMonth() + "000";
        wp.likeRight("BILL_CODE", billCodeBase);
        int billCodeIdx = service.count(wp) ;
        wp.clear();

        int detailSizeLimit = 500;
          List<List<DisposeRemoveDetail>> lists = ListUtil.split(detailList, detailSizeLimit);
          for (List<DisposeRemoveDetail> lst: lists) {
            if ( billCodeIdx == 0 ){
              out.setBillCode(billCodeBase);
            } else {out.setBillCode(billCodeBase+'-'+billCodeIdx);}
            service.save(out);
            lst.stream().forEach(i->{i.setOutId(out.getId());i.setUpdateBy(LoginContext.me().getLoginUser().getAccount());});
            detailTwoService.saveBatch(lst);
            billCodeIdx++;
          }
        header.setUpdateBy(LoginContext.me().getLoginUser().getAccount());
        header.setBillCode(billCodeBase);
        this.baseMapper.updateSrcDetailList(header);
      }
      this.baseMapper.updateDetailList();
      this.baseMapper.updateFileDetailList();
      return ResponseData.success();

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseData.error("生成失败!");
    } finally {
      toList.set("");
    }
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean orgBatch(List<RemoveMainParam> params) {
    return this.baseMapper.orgBatch(params);
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<RemoveMainParam> orgList() {
    return this.baseMapper.orgList();
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean verifyUpdateBatch(RemoveMainParam param) {
    return this.baseMapper.verifyUpdateBatch(param);
  }

  private DisposeRemove getListEntity(RemoveMainResult param) {
    DisposeRemove entity = new DisposeRemove();
    BeanUtil.copyProperties(param, entity);
    return entity;
  }

  private Page getPageContext() {
    return PageFactory.defaultPage();
  }
}
