package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.CustomerMaterialInfoParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.RakutenOverseasShipmentResult;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.ErpWarehouseCodeConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.ZZDistributeMcmsConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IOverseasWarehouseManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 乐天海外仓出库上传前端控制器
 * </p>
 *
 * @author cyt
 * @since 2022-07-19
 */
@RestController
@ApiResource(name="乐天海外仓出库管理", path = "/overseasShipment")
@Api(tags =  "乐天海外仓出库管理")
@Slf4j
public class RakutenOverseasShipmentController {

    @Autowired
    private IOverseasWarehouseManageService service;
    @Autowired
    private ErpWarehouseCodeConsumer erpWarehouseCodeConsumer;
    @Autowired
    private ZZDistributeMcmsConsumer zZDistributeMcmsConsumer;

    /**
     * 乐天海外仓出库导入
     * @param file
     * @return
     */
    @ParamValidator
    @PostResource(name = "乐天海外仓出库管理", path = "/shipmentUpload",  menuFlag = true,requiredPermission = false)
    @ApiOperation(value = "乐天海外仓出库导入", response = RakutenOverseasShipmentResult.class)
    @BusinessLog(title = "乐天海外仓出库管理-出库导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData shipmentUpload(@RequestParam(value = "file", required = true) MultipartFile file) {
        return service.importExcel(file);
    }

    /**
     * combination is incorrect.
     * 乐天海外仓出库保存
     * @param params
     * @return
     */
    @PostResource(name = "乐天海外仓出库保存", path = "/save", requiredPermission = false)
    @ApiOperation(value = "乐天海外仓出库保存")
    @BusinessLog(title = "乐天海外仓出库管理-出库保存",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData save(@RequestBody List<RakutenOverseasShipmentResult> params) {
        StringBuffer shopInfo = new StringBuffer();
        List<RakutenOverseasShipmentResult> list=new ArrayList<>();
        for(RakutenOverseasShipmentResult shipment:params){
            //乐天店铺组织
            CustomerMaterialInfoParam customerMaterialInfoParam=new CustomerMaterialInfoParam();
            shopInfo.append(shipment.getPlatform());
            shopInfo.append("_");
            shopInfo.append("TS");
            shopInfo.append("_");
            shopInfo.append(shipment.getSite());
            customerMaterialInfoParam.setShopName(shopInfo.toString());
            customerMaterialInfoParam.setMaterialCode(shipment.getCustomerGoodsCode());
            String team=service.RakutenTeam(customerMaterialInfoParam);
            if(StrUtil.isEmpty(team)){
                String remark="";
                if(StrUtil.isNotEmpty(shipment.getUploadRemark())){
                    remark=shipment.getUploadRemark()+",";
                }
                shipment.setUploadRemark(remark+"客户物料未匹配出Team组！");
            }
            shipment.setTeam(team);

            //组织编码
            shipment.setInOrg(erpWarehouseCodeConsumer.getOrganizationCode(shopInfo.toString()));
            shipment.setOutOrg(erpWarehouseCodeConsumer.getOrganizationCode(shopInfo.toString()));

            //分配物料即插入物料及组织
            ZZDistributeMcms zParam=new ZZDistributeMcms();
            zParam.setMaterialCode(shipment.getCustomerGoodsCode());
            zParam.setShopCode(erpWarehouseCodeConsumer.getOrganizationCode(shopInfo.toString()));
            zZDistributeMcmsConsumer.add(zParam);

            shopInfo.delete(0,shopInfo.length());
            list.add(shipment);
        }
        return service.save(list);
    }

    /**
     * 导入模板下载
     * @param response
     */
    @GetResource(name = "模板下载", path = "/downloadExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("模板下载")
    public void downloadExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/乐天海外仓出库导入模板.xlsx");
    }

    /**
     * 乐天海外仓出库单同步到K3销售出库单(定时)
     * @return
     */
    @PostResource(name = "乐天海外仓出库单同步到K3销售出库单(定时)", path = "/syncAbnormalOutWarehouseToErp")
    @ApiOperation(value = "乐天海外仓出库单同步到K3销售出库单(定时)")
    public ResponseData syncAbnormalOutWarehouseToErp() { return service.syncAbnormalOutWarehouseToErp(); }

}
