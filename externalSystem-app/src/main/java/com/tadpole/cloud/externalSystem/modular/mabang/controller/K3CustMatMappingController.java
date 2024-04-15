package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.tadpole.cloud.externalSystem.modular.mabang.constants.MabangConstant;
import com.tadpole.cloud.externalSystem.modular.mabang.consumer.ErpWarehouseCodeConsumer;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3CustMatMappingParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3CustMatMappingResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IK3CustMatMappingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* <p>
    * K3客户物料对应表 前端控制器
    * </p>
*
* @author lsy
* @since 2022-06-20
*/
@RestController
@ApiResource(name="k3CustMatMapping K3客户物料对应",path = "/k3CustMatMapping")
@Api(tags =  "K3 CUST MAT MAPPING K3客户物料对应")
@Slf4j
public class K3CustMatMappingController {

    @Autowired
    private IK3CustMatMappingService service;


    @Autowired
    private ErpWarehouseCodeConsumer erpWarehouseCodeConsumer;


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostResource(name = "K3客户物料对应列表", path = "/list", menuFlag = true,requiredPermission = false)
    @ApiOperation(value = "K3客户物料对应列表", response = K3CustMatMappingResult.class)
    public ResponseData list(@RequestBody K3CustMatMappingParam param) {
        PageResult<K3CustMatMappingResult> list = service.list(param);
        return ResponseData.success(list);
    }

    @PostResource(name = "创建K3客户物料对应", path = "/createMat",requiredPermission = false)
    @ApiOperation(value = "创建K3客户物料对应", response = K3CustMatMappingResult.class)
    public ResponseData createMat() {
        // 创建时有可能冲突 需要加锁来排除冲突
        Object createMatWait  = redisTemplate.opsForValue().get(MabangConstant.K3_SYNC_CREATE_MAT_WAIT);
        if (ObjectUtil.isNotNull(createMatWait)) {
            log.info("K3物料对应表创建物料取消,同步触发时间:{}",new Date());
            return ResponseData.success("K3物料对应表创建物料取消");
        }

        redisTemplate.boundValueOps(MabangConstant.K3_SYNC_CREATE_MAT_WAIT)
                .set(MabangConstant.K3_SYNC_CREATE_MAT_WAIT, Duration.ofMinutes(5));
        try {
            //插入K3客户物料对应表
            service.createMat();
            //修改订单表状态已创建
            service.updateOrdersStatus();
        }catch (Exception e){
            return ResponseData.error("创建K3客户物料对应.createMat异常>>>"+e.getMessage());
        }finally {
            redisTemplate.delete(MabangConstant.K3_SYNC_CREATE_MAT_WAIT);
        }
        return ResponseData.success();
    }

    @PostResource(name = "同步K3客户物料对应", path = "/refresh",requiredPermission = false)
    @ApiOperation(value = "同步K3客户物料对应", response = K3CustMatMappingResult.class)
    public ResponseData syncK3MatMapping() {
        // 刷数据时有可能冲突 需要加锁来排除冲突
        Object createMatWait  = redisTemplate.opsForValue().get(MabangConstant.K3_SYNC_MAT_REFRESH_WAIT);

        if (ObjectUtil.isNotNull(createMatWait)) {
            log.info("K3物料对应表刷数据时料取消,同步触发时间:{}",new Date());
            return ResponseData.success("K3物料对应表同步K3客户物料对应取消");
        }

        redisTemplate.boundValueOps(MabangConstant.K3_SYNC_MAT_REFRESH_WAIT)
                .set(MabangConstant.K3_SYNC_MAT_REFRESH_WAIT, Duration.ofMinutes(5));
        try {
            //获取需同步
            List<K3CustMatMappingResult> syncList = service.getSyncList();
            List<String> finCodeList = service.getFinCodeList();
            Map<String, String> orgMap = erpWarehouseCodeConsumer.getOrgMap();

            for (String finCode : finCodeList) {
                String custId = service.getCustId(finCode);
                if (StrUtil.isNotEmpty(custId)) {
                    //用财务编码复制客户ID
                    service.updateCustId(finCode, custId);
                    //用客户编号获取单据编号BillCode和销售组织Id
                    K3CustMatMappingResult res = service.getInfo(custId);
                    res.setFinanceCode(finCode);
                    String org = orgMap.get(finCode)==null ?"":orgMap.get(finCode);
                    String salOrg = orgMap.get(res.getSaleOrgId())==null ?"采购中心":orgMap.get(res.getSaleOrgId());
                    res.setName(org);
                    res.setSaleOrgId(salOrg);
                    res.setSummaryId(IdWorker.getIdStr());
                    service.refreshInfo(res);
                }else {
                    service.refreshNoCustId(finCode);
                }
            }
        }catch (Exception e){
            log.error("K3物料对应表刷数据时料取消,同步触发时间:{}",new Date());

        }
        redisTemplate.delete(MabangConstant.K3_SYNC_MAT_REFRESH_WAIT);
        return ResponseData.success();

    }


  @GetResource(name = "存储过程同步", path = "/beginProcedureSync", requiredPermission = false)
  @ApiOperation(value = "存储过程同步", response = K3CustMatMappingResult.class)
  public ResponseData beginProcedureSync() {

      // K3同步存储过程时有可能冲突 需要加锁来排除冲突
      Object createMatWait  = redisTemplate.opsForValue().get(MabangConstant.K3_SYNC_PROCEDURE_WAIT);

      if (ObjectUtil.isNotNull(createMatWait)) {
          log.info("K3同步存储过程取消,同步触发时间:{}",new Date());
          return ResponseData.success("K3物料对应表存储过程同步对应取消");

      }

      redisTemplate.boundValueOps(MabangConstant.K3_SYNC_PROCEDURE_WAIT)
              .set(MabangConstant.K3_SYNC_PROCEDURE_WAIT, Duration.ofMinutes(5));
      try {
          List<K3CustMatMappingResult> pushK3List = service.getPushK3List();
          for (K3CustMatMappingResult pushParam : pushK3List) {

              try {
                  service.beginProcedureSync(pushParam);
                  service.syncSuccessStatus(pushParam);

                  //调用存储过程同步异常记录错误信息
              } catch (Exception e) {
                  pushParam.setSyncResultMsg(e.getMessage());
                  service.syncFailedStatus(pushParam);
              }
          }
      }catch (Exception e){
          log.error("K3同步存储过程取消,同步触发时间:{}",new Date());

      }
      redisTemplate.delete(MabangConstant.K3_SYNC_PROCEDURE_WAIT);
      return ResponseData.success();
  }


    @PostResource(name = "K3客户物料对应表导出", path = "/exportExcel", requiredLogin = false)
    @ApiOperation("K3客户物料对应表导出")
    public void export(HttpServletResponse response,@RequestBody K3CustMatMappingParam param) throws IOException {
        try {
            List<K3CustMatMappingResult> results = service.exportList(param);
            exportExcel(response, "K3客户物料对应表");
            EasyExcel.write(response.getOutputStream(), K3CustMatMappingResult.class).sheet("K3客户物料对应表").doWrite(results);
        } catch (Exception ex) {
            response.sendError(500, "K3客户物料对应表导出错误");
        }
    }

    @GetResource(name = "客户下拉", path="/queryNames", requiredPermission = false)
    @ApiOperation(value = "客户下拉", response = ResponseData.class)
    public ResponseData queryNames(){
        return service.queryNames();
    }





    private void exportExcel(HttpServletResponse response, String sheetName) throws IOException {
        String fileName = new String((sheetName + ".xlsx").getBytes("UTF-8"), "ISO8859-1");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
    }






}
