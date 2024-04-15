package com.tadpole.cloud.platformSettlement.modular.inventory.controller;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcelFactory;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.MonthlyInventoryHistory;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.MonthlyInventoryHistoryParam;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IErpWarehouseCodeService;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IMonthlyInventoryHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Monthly inventory history报告明细 前端控制器
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */

@Slf4j
@RestController
@ApiResource(name = "Monthly inventory history报告明细", path = "/monthlyInventoryHistory")
@Api(tags = "Monthly inventory history报告明细")
public class MonthlyInventoryHistoryController {

  @Autowired
  private IMonthlyInventoryHistoryService service;
  @Autowired
  private IErpWarehouseCodeService wareService;
  @Autowired
  private RedisTemplate<String, String> redisTemplate;
  /**
   * 生成列表标识
   */
  private static final String TOLIST = "TO_MONTH_LIST";

  @PostResource(name = "Monthly inventory history报告明细", path = "/list", requiredPermission = false)
  @ApiOperation(value = "Monthly inventory history报告明细", response = MonthlyInventoryHistory.class)
  @BusinessLog(title = "Monthly inventory history报告明细-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public ResponseData queryListPage(@RequestBody MonthlyInventoryHistoryParam param) {
    PageResult<MonthlyInventoryHistory> pageBySpec = service.findPageBySpec(param);
    return ResponseData.success(pageBySpec);
  }

  @PostResource(name = "获取数量", path = "/getQuantity", requiredPermission = false)
  @ApiOperation(value = "获取数量")
  @BusinessLog(title = "Monthly inventory history报告明细-汇总数量查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public ResponseData getQuantity(@RequestBody MonthlyInventoryHistoryParam param) {
    return service.getQuantity(param);
  }

  @PostResource(name = "Monthly inventory history报告明细", path = "/export", requiredPermission = false, requiredLogin = false)
  @ApiOperation(value = "Monthly inventory history报告明细")
  @BusinessLog(title = "Monthly inventory history报告明细-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
  public void export(@RequestBody MonthlyInventoryHistoryParam param, HttpServletResponse response) throws IOException {
    List<MonthlyInventoryHistory> resultList = service.export(param);
    response.setContentType("application/vnd.ms-excel");
    response.addHeader("Content-Disposition","attachment;filename=" + new String("Monthly inventory history报告明细.xlsx".getBytes(StandardCharsets.UTF_8),"ISO8859-1"));
    EasyExcelFactory.write(response.getOutputStream(), MonthlyInventoryHistory.class).sheet("Monthly inventory history报告明细").doWrite(resultList);
  }

  @PostResource(name = "审核", path = "/verify", requiredPermission = false, requiredLogin = false)
  @ApiOperation(value = "审核")
  @BusinessLog(title = "Monthly inventory history报告明细-审核",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData verify(@RequestBody MonthlyInventoryHistoryParam param) {
    //期末库存列表数据正在生成，不允许审核
    if(redisTemplate.hasKey(TOLIST)){
      return ResponseData.error("期末库存列表数据正在生成中，请稍后再试!");
    }

    if (param.getSysSite() == null || "".equals(param.getSysSite())) {
      return ResponseData.error("站点为空!");
    }
    String org = param.getPlatform() + "_" + param.getSysShopsName() + "_" + param.getSysSite();
    param.setInventoryOrganization(org);
    param.setWarehouseName(org + "_仓库");
    String orgCode = wareService.getOrganizationCode(org);
    String wareCode = wareService.getOrganizationCode(org + "_仓库");
    if (StrUtil.isEmpty(orgCode) || StrUtil.isEmpty(wareCode) ) {
      return ResponseData.error("无法获取到组织编码!");
    }
    param.setInventoryOrganizationCode(orgCode);
    param.setWarehouseCode(wareCode);


    service.verify(param);
    //获取及设置物料编码以便ERP分配物料
    String material = service.getMaterial(param);
    if (StrUtil.isNotEmpty(material) ) {
      param.setMat(material);
      service.assignMaterial(param);
    }
    return ResponseData.success();
  }

  @PostResource(name = "作废", path = "/reject", requiredPermission = false)
  @ApiOperation(value = "作废")
  @BusinessLog(title = "Monthly inventory history报告明细-作废",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData reject(@RequestBody MonthlyInventoryHistoryParam param) {
    //期末库存列表数据正在生成，不允许审核
    if(redisTemplate.hasKey(TOLIST)){
      return ResponseData.error("期末库存列表数据正在生成中，请稍后再试!");
    }
    service.reject(param);
    return ResponseData.success();
  }

  @PostResource(name = "批量作废", path = "/rejectBatch", requiredPermission = false)
  @ApiOperation(value = "批量作废")
  @BusinessLog(title = "Monthly inventory history报告明细-批量作废",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData rejectBatch(@RequestBody List<MonthlyInventoryHistoryParam> params) {
    //期末库存列表数据正在生成，不允许审核
    if(redisTemplate.hasKey(TOLIST)){
      return ResponseData.error("期末库存列表数据正在生成中，请稍后再试!");
    }
    service.rejectBatch(params);
    return ResponseData.success();
  }

  @PostResource(name = "生成期末库存列表", path = "/toEndingInventoryList", requiredPermission = false)
  @ApiOperation(value = "生成期末库存列表")
  @BusinessLog(title = "Monthly inventory history报告明细-生成期末库存列表",opType = LogAnnotionOpTypeEnum.ADD)
  public ResponseData toEndingInventoryList(@RequestBody MonthlyInventoryHistoryParam param)  {
    //redis里面取值则为正在生成!
    if(redisTemplate.hasKey(TOLIST)){
      return ResponseData.error("期末库存列表数据正在生成中，请稍后再试!");
    }
    try {
      redisTemplate.boundValueOps(TOLIST).set("期末库存列表数据正在生成中", Duration.ofSeconds(900));
      //获取月份Monthly Inventory_History数据入库期末库存汇总和明细表
      ResponseData rd =  service.toEndingInventoryList(param);
      //生成列表成功批量分配物料
      if (rd.getSuccess()){
        List<String> detailIdList = (List< String >) rd.getData();
        //同步ERP物料集合
        List<ZZDistributeMcms> assignList = new ArrayList<>();
        List<List<String>> lists = ListUtil.split(detailIdList, 1000);
        for (List<String> lst : lists) {
          //获取物料编码和库存组织编码
          assignList.addAll(service.assignMaterialList(lst));
        }

        //将物料编码和库存组织编码同步至ERP
        service.assignBatchMaterial(assignList);

        //正常取时分配
        /*for (List<String> lst: lists) {
          if(CollectionUtils.isNotEmpty(lst)){
            param.setIdList(lst);
            //+海外仓库存更新在库数和在途数
            service.updateBatchSea(param);

            //+在途库存更新物流代发数和在途数
            service.updateBatchComing(param);
          }
        }*/
      }
      return rd;
    }catch (Exception e) {
      log.info("生成期末库存列表异常，异常信息[{}]", e);
      return ResponseData.error("生成期末库存列表异常!");
    } finally {
      redisTemplate.delete(TOLIST);
    }
  }

  @PostResource(name = "批量审核", path = "/verifyBatchBySpec", requiredPermission = false)
  @ApiOperation(value = "批量审核")
  @BusinessLog(title = "Monthly inventory history报告明细-批量审核",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData verifyBatchBySpec(@RequestBody MonthlyInventoryHistoryParam param) {
    //期末库存列表数据正在生成，不允许审核
    if(redisTemplate.hasKey(TOLIST)){
      return ResponseData.error("期末库存列表数据正在生成中，请稍后再试!");
    }
    param.setVerifyBy(LoginContext.me().getLoginUser().getAccount());
    param.setUpdateBy(LoginContext.me().getLoginUser().getAccount());
    service.verifyUpdateBatch(param);
    return ResponseData.success();
  }

  @PostResource(name = "修改账号", path = "/editShop", requiredPermission = false)
  @ApiOperation(value = "修改账号")
  @BusinessLog(title = "Monthly inventory history报告明细-修改账号",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData editShop(@RequestBody List<MonthlyInventoryHistoryParam> params) {
    return service.editShop(params);
  }

  @PostResource(name = "修改站点", path = "/editSites", requiredPermission = false)
  @ApiOperation(value = "修改站点")
  @BusinessLog(title = "Monthly inventory history报告明细-修改站点",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData editSites(@RequestBody List<MonthlyInventoryHistoryParam> params) {
    return service.editSites(params);
  }

  @PostResource(name = "新添加组织", path = "/addOrg", requiredPermission = false)
  @ApiOperation(value = "新添加组织")
  @BusinessLog(title = "Monthly inventory history报告明细-新添加组织",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData addOrg() {
    return ResponseData.success(service.addNewOrg());
  }

  /**
   * 获MonthlyInventoryHistory数据任务（定时）
   * @return
   */
  @PostResource(name = "获MonthlyInventoryHistory数据任务", path = "/generateMonthlyInventoryHistory", requiredPermission = false, requiredLogin = false)
  @ApiOperation(value = "获MonthlyInventoryHistory数据任务")
  @BusinessLog(title = "Monthly inventory history报告明细-获MonthlyInventoryHistory数据任务",opType = LogAnnotionOpTypeEnum.ADD)
  public ResponseData generateMonthlyInventoryHistory() {
    return service.generateMonthlyInventoryHistory();
  }
}

