package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.ebms.entity.TbComMateriel;
import com.tadpole.cloud.externalSystem.modular.ebms.service.ITbcommaterielService;
import com.tadpole.cloud.externalSystem.modular.mabang.constants.MabangConstant;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangMatSync;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangMatSyncParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangMatSyncResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangWarehouseResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangMatSysncService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 马帮物料同步记录表 前端控制器
 * </p>
 *
 * @author gal
 * @since 2022-10-24
 */
@RestController
@ApiResource(name = "马帮物料同步记录", path = "/mabangMatSysnc")
@Api(tags = "马帮物料同步记录")
public class MabangMatSysncController {

    public static final String controllerName = "马帮物料同步记录";

    @Autowired
    private IMabangMatSysncService service;

    @Resource
    private ITbcommaterielService tbcommaterielService;

    @Autowired
    private RedisTemplate redisTemplate;


    @PostResource(name = controllerName + "--列表", path = "/list", menuFlag = true, requiredLogin = false)
    @ApiOperation(value = controllerName + "--列表", response = MabangMatSyncResult.class)
    public ResponseData list(@RequestBody MabangMatSyncParam param) {
        PageResult<MabangMatSyncResult> list = service.listPage(param);
        return ResponseData.success(list);
    }


    @GetResource(name = controllerName + "--同步新增物料-EMBS", path = "/addFromEbms", menuFlag = false, requiredLogin = false)
    @ApiOperation(value = controllerName + "--同步新增物料-EMBS", response = ResponseData.class)
    public ResponseData addFromEbms(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE) Date createTimeStart,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE) Date createTimeEnd,
                                    @RequestParam(required = false) List<String> matList) {

        // 刷数据时有可能冲突 需要加锁来排除冲突
        Object syncCrossTransferK3 = redisTemplate.opsForValue().get(MabangConstant.ERP_MAT_SYNC);
        if (ObjectUtil.isNotNull(syncCrossTransferK3)) {
            return ResponseData.success("同步新增物料到马帮ERP有类似的业务正在作业,本次请求结束");
        }
        //调用k3接口耗时比较大，大约1分钟处理7条数据，订单数据较大时，redis锁自动过期时间应该适当增加
        redisTemplate.boundValueOps(MabangConstant.ERP_MAT_SYNC)
                .set(MabangConstant.ERP_MAT_SYNC, Duration.ofMinutes(15));

        List<TbComMateriel> waitMatList = tbcommaterielService.getWaitAddMatList(createTimeStart, createTimeEnd, matList);

        if (ObjectUtil.isEmpty(waitMatList)) {
            return ResponseData.success("EBMS中没有找到物料创建时间大于2022-09-30的新增物料");
        }
        List<String> matCodeList = waitMatList.stream().map(m -> m.getMatCode()).collect(Collectors.toList());
        //查找同步记录
        List<MabangMatSync> syncList = service.getSyncSuccessMatCode(matCodeList,null);

        List<TbComMateriel> filterResultData = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(syncList)) {
            Set<String> syncCode = syncList.stream().map(s -> s.getMatCode()).collect(Collectors.toSet());
            boolean b = matCodeList.removeAll(syncCode);
            if (ObjectUtil.isEmpty(matCodeList)) {
                return ResponseData.success("过滤后,没有找到需要新增的物料同步");
            }
            filterResultData = waitMatList.stream().filter(m -> matCodeList.contains(m.getMatCode())).collect(Collectors.toList());
        } else {
            filterResultData = waitMatList;
        }

        ResponseData responseData = service.addMaterielFromEbms(filterResultData, null);
        redisTemplate.delete(MabangConstant.ERP_MAT_SYNC);
        return responseData;
    }


    @GetResource(name = controllerName + "--更新物料-EMBS", path = "/updateFromEbms", menuFlag = false, requiredLogin = false)
    @ApiOperation(value = controllerName + "--更新物料-EMBS", response = ResponseData.class)
    public ResponseData updateFromEbms(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE) Date updateTimeStart,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE) Date updateTimeEnd,
                                       @RequestParam(required = false) List<String> matList) {


        List<TbComMateriel> waitMatList = tbcommaterielService.getWaitUpdateMatList(updateTimeStart, updateTimeEnd, matList);

        if (ObjectUtil.isEmpty(waitMatList)) {
            return ResponseData.success("未找到需要更新的物料信息");
        }

        List<String> matCodeList = waitMatList.stream().map(TbComMateriel::getMatCode).collect(Collectors.toList());

        //查找同步记录
        List<MabangMatSync> syncList = service.getSyncList(matCodeList, null);

        List<TbComMateriel> filterResultData = new ArrayList<>();

        if (ObjectUtil.isNotEmpty(syncList)) {
            Map<String, List<TbComMateriel>> waitListMap = waitMatList.stream().collect(Collectors.groupingBy(TbComMateriel::getMatCode));
            Map<String, List<MabangMatSync>> syncListMap = syncList.stream().collect(Collectors.groupingBy(MabangMatSync::getMatCode));

            for (Map.Entry<String, List<TbComMateriel>> entry : waitListMap.entrySet()) {
                List<TbComMateriel> materielList = entry.getValue();
                String matCode = entry.getKey();
                List<MabangMatSync> syncListTemp = syncListMap.get(matCode);
                if (ObjectUtil.isEmpty(syncListTemp)) {
                    //当前物料未找到同步记录，直接同步更新
                    filterResultData.addAll(materielList);

                } else {
                    //embs的更新时间和同步记录的更新时间不相等  不比较毫秒  1665308629xxx
                    if (!(syncListTemp.get(0).getMatUpdateTime().getTime() / 1000 == materielList.get(0).getMatLastUpdateDate().getTime() / 1000)) {
                        filterResultData.addAll(materielList);
                    }
                }
            }

        } else {
            //所有物料未找到同步记录，直接同步更新
            filterResultData = waitMatList;
        }

        if (ObjectUtil.isEmpty(filterResultData)) {
            return ResponseData.success("物料更新信息已经同步，不需要调用马帮erp接口进行更新");
        }

        return service.updateFromEbms(filterResultData, null);
    }


    @GetResource(name = controllerName + "--更新物料仓位", path = "/stockChangeGrid", menuFlag = false, requiredLogin = false)
    @ApiOperation(value = controllerName + "--更新物料仓位", response = ResponseData.class)
    public ResponseData stockChangeGrid() {
        return service.stockChangeGrid();
    }


    @GetResource(name = controllerName + "--全量推送物料到新增仓库", path = "/addAllMatToNewWarehouse", menuFlag = false, requiredLogin = false)
    @ApiOperation(value = controllerName + "--全量推送物料到新增仓库", response = ResponseData.class)
    public ResponseData addAllMatToNewWarehouse(@RequestParam String warehouse) {
        List<MabangWarehouseResult> newWarehouseList = service.getNewWarehouseByName(warehouse);
        if (ObjectUtil.isEmpty(newWarehouseList)) {
            return ResponseData.success();
        }

        List<TbComMateriel> allMatList = tbcommaterielService.getAllMatList();
        List<String> matCodeList = allMatList.stream().map(TbComMateriel::getMatCode).collect(Collectors.toList());
        //查找同步记录
        List<MabangMatSync> syncList = service.getSyncSuccessMatCode(null, newWarehouseList.get(0).getId());
        List<TbComMateriel> filterResultData = new ArrayList<>();

        if (ObjectUtil.isNotEmpty(syncList)) {
            Set<String> syncSuccessCode = syncList.stream().map(MabangMatSync::getMatCode).collect(Collectors.toSet());
            boolean b = matCodeList.removeAll(syncSuccessCode);
            if (ObjectUtil.isEmpty(matCodeList)) {
                return ResponseData.success("过滤后,没有找到需要新增的物料同步");
            }
            filterResultData = allMatList.stream().filter(m -> matCodeList.contains(m.getMatCode())).collect(Collectors.toList());
        } else {
            filterResultData = allMatList;
        }

        return service.addMaterielFromEbms(filterResultData, newWarehouseList);
    }


}
