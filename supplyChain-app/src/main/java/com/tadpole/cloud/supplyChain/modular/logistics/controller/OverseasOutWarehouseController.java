package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasWarehouseManage;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.EbmsOverseasOutWarehouseLogisticsParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasOutWarehouseDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasOutWarehouseParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.ValidateLabelParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.ExportOverseasOutWarehouseResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasOutWarehouseDetailResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasOutWarehouseResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.ValidateLabelResult;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.ErpWarehouseCodeConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.ZZDistributeMcmsConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.LogisticsStatusEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.OutBusinessTypeEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IOverseasOutWarehouseService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IOverseasWarehouseManageService;
import com.tadpole.cloud.supplyChain.modular.consumer.BaseSelectConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  海外仓出库管理前端控制器
 * </p>
 *
 * @author cyt
 * @since 2022-07-20
 */
@Slf4j
@RestController
@ApiResource(name = "海外仓出库管理前端控制器", path = "/overseasOutWarehouse")
@Api(tags = "海外仓出库管理")
public class OverseasOutWarehouseController {

    @Autowired
    private IOverseasOutWarehouseService service;
    @Autowired
    private IOverseasWarehouseManageService overseasWarehouseManageService;
    @Autowired
    private ErpWarehouseCodeConsumer erpWarehouseCodeConsumer;
    @Autowired
    private ZZDistributeMcmsConsumer zZDistributeMcmsConsumer;
    @Autowired
    private BaseSelectConsumer baseSelectConsumer;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 批量导入出库任务
     */
    private static final String BATCH_OUT_WAREHOUSE = "BATCH_OUT_WAREHOUSE";

    /**
     * 海外仓出库管理查询列表
     * @param param
     * @return
     */
    @ParamValidator
    @PostResource(name = "海外仓出库管理", path = "/queryListPage",  menuFlag = true,requiredPermission = false)
    @ApiOperation(value = "海外仓出库管理查询列表", response = OverseasOutWarehouseResult.class)
    @BusinessLog(title = "海外仓出库管理-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody OverseasOutWarehouseParam param) {
        PageResult<OverseasOutWarehouseResult> list = service.queryListPage(param);
        return ResponseData.success(list);
    }

    /**
     * 海外仓出库管理查询列表数据汇总
     * @param param
     * @return
     */
    @PostResource(name = "海外仓出库管理查询列表数据汇总", path = "/queryPageTotal")
    @ApiOperation(value = "海外仓出库管理查询列表数据汇总")
    @BusinessLog(title = "海外仓出库管理-列表数据汇总",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPageTotal(@RequestBody OverseasOutWarehouseParam param) {
        return service.queryPageTotal(param);
    }

    /**
     * 查询出库单详情
     * @param param
     * @return
     */
    @PostResource(name = "查询详情", path = "/queryDetail")
    @ApiOperation(value = "查询详情", response = OverseasOutWarehouseDetailResult.class)
    @BusinessLog(title = "海外仓出库管理-查询详情",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryDetail(@RequestBody OverseasOutWarehouseDetailParam param) {
        List<OverseasOutWarehouseDetailResult> list=service.list(param);
        return ResponseData.success(list);
    }

    /**
     * 出库任务导入
     * @param file
     * @return
     */
    @ParamValidator
    @PostResource(name = "出库任务导入", path = "/outWarehouseUpload",requiredPermission = false)
    @ApiOperation(value = "出库任务导入", response = OverseasOutWarehouseDetailResult.class)
    @BusinessLog(title = "海外仓出库管理-出库任务导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData outWarehouseUpload(@ModelAttribute OverseasOutWarehouseParam param,@RequestParam(value = "file", required = true) MultipartFile file) {
        List<String> departmentList = baseSelectConsumer.getDepartment();
        List<String> teamList = baseSelectConsumer.getTeam();
        return service.importExcel(param, file, departmentList, teamList);
    }

    /**
     * 出库任务保存
     * @param param
     * @return
     */
    @PostResource(name = "新建出库任务保存", path = "/save", requiredPermission = false)
    @ApiOperation(value = "新建出库任务保存")
    @BusinessLog(title = "海外仓出库管理-出库任务保存",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData save(@RequestBody OverseasOutWarehouseParam param)  throws Exception{
        if(redisTemplate.hasKey(BATCH_OUT_WAREHOUSE)){
            return ResponseData.error("批量导入出库任务保存中，请稍后再试!");
        }
        try {
            redisTemplate.boundValueOps(BATCH_OUT_WAREHOUSE).set("批量导入出库任务保存中", Duration.ofSeconds(600));
            //组织编码
            String inOrgName = param.getInPlatform() + "_" + param.getInSysShopsName() + "_" + param.getInSysSite();
            String outOrgName = param.getPlatform() + "_" + param.getSysShopsName() + "_" + param.getSysSite();
            String inOrg = erpWarehouseCodeConsumer.getOrganizationCode(inOrgName);
            if(StringUtils.isBlank(inOrg)){
                return ResponseData.error("收货仓组织编码为空，新建出库任务失败！");
            }
            String outOrg = erpWarehouseCodeConsumer.getOrganizationCode(outOrgName);
            if(StringUtils.isBlank(outOrg)){
                return ResponseData.error("出货仓组织编码为空，新建出库任务失败！");
            }
            param.setInOrgName(inOrgName);
            param.setOutOrgName(outOrgName);
            param.setInOrg(inOrg);
            param.setOutOrg(outOrg);

            //标签校验
            for(OverseasOutWarehouseDetailParam ent : param.getDetailList()){
                //根据维度查询海外仓账存信息
                OverseasWarehouseManage selectWarehouseManage = new OverseasWarehouseManage();
                selectWarehouseManage.setPlatform(param.getPlatform());
                selectWarehouseManage.setSysShopsName(param.getSysShopsName());
                selectWarehouseManage.setSysSite(param.getSysSite());
                selectWarehouseManage.setWarehouseName(param.getOutWarehouseName());
                selectWarehouseManage.setSku(ent.getSku());
                selectWarehouseManage.setFnSku(ent.getFnSku());
                OverseasWarehouseManage warehouseManage = overseasWarehouseManageService.getWarehouseManage(selectWarehouseManage);
                if (ObjectUtil.isEmpty(warehouseManage) ) {
                    return ResponseData.error("该海外仓没有对应SKU可以出库！");
                }else{
                    //根据SKU、FNSKU汇总不同库存的出库数
                    BigDecimal skuGroupQuantity = param.getDetailList().stream().filter(a -> a.getSku().equals(ent.getSku())
                            && a.getFnSku().equals(ent.getFnSku())).map(OverseasOutWarehouseDetailParam::getQuantity).reduce(BigDecimal.ZERO,BigDecimal::add);

                    //sku分组出库数量不能大于现有库存数量
                    if(skuGroupQuantity.compareTo(warehouseManage.getInventoryQuantity()) > 0){
                        return ResponseData.error("出库数量不能大于现有库存数量！");
                    }
                    ent.setMaterialCode(warehouseManage.getMaterialCode());
                }

                //Amazon平台的校验标签
                if("Amazon".equals(param.getInPlatform())){
                    ValidateLabelParam validateParam = new ValidateLabelParam();
                    validateParam.setShopName(inOrgName);
                    validateParam.setFnSku(ent.getFnSku());
                    validateParam.setSku(ent.getSku());
                    List<ValidateLabelResult> labelResult = overseasWarehouseManageService.validateLabel(validateParam);
                    if(CollectionUtils.isEmpty(labelResult)){
                        return ResponseData.error("EBMS标签不存在! ShopName:" + inOrgName+ "，FNSKU："+ ent.getFnSku() +"，SKU:" + ent.getSku());
                    }
                    if(labelResult.size() > 1){
                        return ResponseData.error("新标校验存在多条记录，新标校验失败！");
                    }
                    ent.setFnSku(labelResult.get(0).getFnSku());
                }

                //分配物料即插入物料及组织
                List<ZZDistributeMcms> zParamList = new ArrayList<>();
                ZZDistributeMcms inZParam = new ZZDistributeMcms();
                inZParam.setMaterialCode(ent.getMaterialCode());
                inZParam.setShopCode(inOrgName);
                zParamList.add(inZParam);

                ZZDistributeMcms outZParam = new ZZDistributeMcms();
                outZParam.setMaterialCode(ent.getMaterialCode());
                outZParam.setShopCode(outOrg);
                zParamList.add(outZParam);
                zZDistributeMcmsConsumer.saveMatBatch(zParamList);
            }

            return service.save(param);
        }catch (Exception e) {
            log.info("批量导入出库任务保存异常，异常信息[{}]", e);
            return ResponseData.error("批量导入出库任务保存异常!");
        } finally {
            redisTemplate.delete(BATCH_OUT_WAREHOUSE);
        }
    }

    /**
     * 海外仓出库管理列表导出接口
     * @param param
     * @param response
     * @return
     * @throws IOException
     */
    @PostResource(name = "导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "海外仓出库管理-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody OverseasOutWarehouseParam param, HttpServletResponse response) throws IOException {

        List<ExportOverseasOutWarehouseResult> list = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("海外仓出库管理列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), ExportOverseasOutWarehouseResult.class).sheet("海外仓出库管理列表").doWrite(list);
        return ResponseData.success();
    }

    /**
     * 业务类型下拉
     * @return
     */
    @GetResource(name = "业务类型下拉", path = "/operateTypeSelect")
    @ApiOperation(value = "业务类型下拉")
    public ResponseData operateTypeSelect() { return ResponseData.success(OutBusinessTypeEnum.getOutBusinessType()); }

    /**
     * 出货仓名称下拉
     * @return
     */
    @GetResource(name = "出货仓名称下拉（查询列表）", path = "/outWarehouseSelect")
    @ApiOperation(value = "出货仓名称下拉（查询列表）")
    public ResponseData outWarehouseSelect() { return ResponseData.success(service.outWarehouseSelect()); }

    /**
     * 收货仓名称下拉
     * @return
     */
    @GetResource(name = "收货仓名称下拉（查询列表）", path = "/inWarehouseSelect")
    @ApiOperation(value = "收货仓名称下拉（查询列表）")
    public ResponseData inWarehouseSelect() { return ResponseData.success(service.inWarehouseSelect()); }

    /**
     * 物流状态下拉
     * @return
     */
    @GetResource(name = "物流状态下拉", path = "/confirmStatusSelect")
    @ApiOperation(value = "物流状态下拉")
    public ResponseData confirmStatusSelect() { return ResponseData.success(LogisticsStatusEnum.getLogisticsStatus()); }

    /**
     * 导入模板下载
     * @param response
     */
    @GetResource(name = "模板下载", path = "/downloadExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("模板下载")
    public void downloadExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/装箱单明细导入模板.xlsx");
    }

    /**
     * 平台下拉
     * @return
     */
    @GetResource(name = "平台下拉", path = "/platformSelect")
    @ApiOperation(value = "平台下拉")
    public ResponseData platformSelect() {
        return ResponseData.success(baseSelectConsumer.getPlatform());
    }

    /**
     * 账号下拉
     * @return
     */
    @GetResource(name = "账号下拉", path = "/shopsNameSelect")
    @ApiOperation(value = "账号下拉")
    public ResponseData shopsNameSelect() {
        return ResponseData.success(baseSelectConsumer.getShop());
    }

    /**
     * 站点下拉
     * @return
     *
     */
    @GetResource(name = "站点下拉", path = "/siteSelect")
    @ApiOperation(value = "站点下拉")
    public ResponseData siteSelect() {
        return ResponseData.success(baseSelectConsumer.getSite());
    }

    /**
     * 出货仓名称下拉（新建出库任务）
     * @return
     */
    @GetResource(name = "出货仓名称下拉（新建出库任务）", path = "/warehouseNameSelect", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "出货仓名称下拉（新建出库任务）")
    public ResponseData warehouseNameSelect() {
        return overseasWarehouseManageService.warehouseNameBySiteSelect(null, null,null);
    }

    /**
     * 获取EBMS物流状态信息
     * @return
     */
    @PostResource(name = "获取EBMS物流状态信息", path = "/receiveLogisticsByEBMS", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "获取EBMS物流状态信息")
    @BusinessLog(title = "海外仓出库管理-EBMS物流状态信息",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData receiveLogisticsByEBMS(@RequestBody List<EbmsOverseasOutWarehouseLogisticsParam> params) {
        return service.receiveLogisticsByEBMS(params);
    }

    /**
     * 推送EBMS创建出货清单
     * @return
     */
    @PostResource(name = "推送EBMS创建出货清单", path = "/rePushShipmentList", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "推送EBMS创建出货清单")
    public ResponseData rePushShipmentList() {return service.rePushShipmentList();}

}
