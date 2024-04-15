package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.externalSystem.modular.mabang.consumer.ZZDistributeMcmsConsumer;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3CrossTransferItemParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3CrossTransferParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ExportK3CrossTransferResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3CrossTransferItemResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3CrossTransferResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IK3CrossTransferService;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* <p>
    * K3跨组织直接调拨单主表 前端控制器
    * </p>
*
* @author lsy
* @since 2022-06-28
*/
@RestController
@Slf4j
@ApiResource(name = "k3跨组织调拨单列表", path = "/k3CrossTransfer")
@Api(tags = "k3跨组织调拨单列表")
public class K3CrossTransferController {

    @Autowired
    private IK3CrossTransferService service;

    @Autowired
    private ZZDistributeMcmsConsumer mcmsService;

    /**
     * k3跨组织调拨单列表
     * @param  param
     * @return
     */
    @PostResource(name = "k3跨组织调拨单列表", path = "/findPageBySpec")
    @ApiOperation(value = "k3跨组织调拨单列表", response = K3CrossTransferResult.class)
    public ResponseData findPageBySpec( @RequestBody K3CrossTransferParam param) {
        PageResult<K3CrossTransferResult> list = service.findPageBySpec(param);
        return ResponseData.success(list);
    }

    /**
     * k3跨组织调拨单列表
     * @param param
     * @return
     */
    @PostResource(name = "k3跨组织调拨单列表", path = "/findPageBySpecV2", menuFlag = true, requiredPermission = false)
    @ApiOperation(value = "k3跨组织调拨单列表", response = ExportK3CrossTransferResult.class)
    public ResponseData findPageBySpecV2( @RequestBody K3CrossTransferParam param) {
        PageResult<ExportK3CrossTransferResult> list = service.findPageBySpecV2(param);
        return ResponseData.success(list);
    }

    /**
     * 查看详情
     * @param param
     * @return
     */
    @PostResource(name = "查看详情", path = "/list", menuFlag = true, requiredPermission = false)
    @ApiOperation(value = "查看详情", response = K3CrossTransferItemResult.class)
    public ResponseData list(@RequestBody K3CrossTransferItemParam param) {
        PageResult<K3CrossTransferItemResult> list = service.list(param);
        return ResponseData.success(list);
    }

    /**
     * k3跨组织调拨单列表导出接口
     * @param param
     * @param response
     * @return
     * @throws IOException
     */
    @PostResource(name = "导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出")
    public ResponseData export(@RequestBody K3CrossTransferParam param, HttpServletResponse response) throws IOException {

        List<ExportK3CrossTransferResult> list = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("跨组织调拨单列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), ExportK3CrossTransferResult.class).sheet("跨组织调拨单列表").doWrite(list);
        return ResponseData.success();
    }

    /**
     * 平台下拉
     * @return
     */
    @GetResource(name = "平台下拉", path = "/platformSelect")
    @ApiOperation(value = "平台下拉")
    public ResponseData platformSelect() { return ResponseData.success(service.platformSelect()); }

    /**
     * 调入货主下拉
     * @return
     */
    @GetResource(name = "调入货主下拉", path = "/ownerIdHeadNameSelect")
    @ApiOperation(value = "调入货主下拉")
    public ResponseData ownerIdHeadNameSelect() { return ResponseData.success(service.ownerIdHeadNameSelect()); }

    /**
     * 马帮订单同步到跨组织调拨单
     * @param param
     * @return
     * @throws Exception
     */
    @PostResource(name = "马帮订单同步到跨组织调拨单", path = "/add", requiredPermission = false)
    @ApiOperation(value = "马帮订单同步到跨组织调拨单", response = K3CrossTransferItemResult.class)
    public ResponseData add(@RequestBody K3CrossTransferItemParam param) throws Exception{

        try {
            //1.获取MabangOrders数据
            service.add(param);

            List<String> ownerIdList = service.getFownerIdHead();
            for (String ownerId : ownerIdList) {
                String ownerName = service.getFinanceName(ownerId);
                if (StrUtil.isNotEmpty(ownerName)) {
                    //1-1.调入货主->客户名称
                    service.updateOwnerIdName(ownerId, ownerName);
                }
            }
            //2-1.新增马帮发货订单物料组织
            List<ZZDistributeMcms> zList=new ArrayList<>();
            zList=service.getFownerIdAndMat(0);
            if(CollectionUtil.isNotEmpty(zList)){
                mcmsService.saveMatBatch(zList);
            }

        }catch(Exception e){
            //e.printStackTrace();
            log.error("马帮订单同步跨组织调拨单，异常信息：{}",e);
        }
        return ResponseData.success();
    }

    /**
     * 同步到K3跨组织调拨单
     * @return
     * @throws Exception
     */
    @PostResource(name = "同步到K3跨组织调拨单", path = "/syncTransferToErp", requiredPermission = false)
    @ApiOperation(value = "同步到K3跨组织调拨单", response = K3CrossTransferItemResult.class)
    public ResponseData syncTransferToErp() throws Exception{
        return service.syncTransferToErp();
    }

    /**
     * 校验账套登陆接口
     * @return
     * @throws Exception
     */
    @GetResource(name = "校验账套登陆接口", path = "/checkReponseStatus", requiredPermission = false)
    @ApiOperation(value = "校验账套登陆接口", response = K3CrossTransferItemResult.class)
    public ResponseData checkReponseStatus() throws Exception{
        return service.checkReponseStatus();
    }

}
