package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
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
import com.alibaba.fastjson.JSONObject;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.OverseasInWarehouseFBAResult;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasInWarehouse;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasInWarehouseDetailResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasInWarehouseResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasInWarehouseResultVO;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasInWarehouseTotalResult;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.ErpWarehouseCodeConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.RemovalShipmentDetailConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.ZZDistributeMcmsConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.ConfirmStatusEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.InBusinessTypeEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.LogisticsTrackStatusEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.OperateTypeEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IOverseasInWarehouseService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IOverseasWarehouseManageService;
import com.tadpole.cloud.supplyChain.modular.consumer.BaseSelectConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  海外仓入库管理前端控制器
 * </p>
 *
 * @author cyt
 * @since 2022-07-20
*/
@Slf4j
@RestController
@ApiResource(name = "海外仓入库管理前端控制器", path = "/overseasInWarehouse")
@Api(tags = "海外仓入库管理")
public class OverseasInWarehouseController {

    @Autowired
    private IOverseasInWarehouseService service;
    @Autowired
    private RemovalShipmentDetailConsumer removalShipmentDetailConsumer;
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
     * 批量签收FBA发海外仓
     */
    private static final String BATCH_FBA_SIGN = "BATCH_FBA_SIGN";

    /**
     * 海外仓入库管理查询列表
     * @param param
     * @return
     */
    @ParamValidator
    @PostResource(name = "海外仓入库管理", path = "/queryListPage",  menuFlag = true,requiredPermission = false)
    @ApiOperation(value = "海外仓入库管理查询列表", response = OverseasInWarehouseResult.class)
    @BusinessLog(title = "海外仓入库管理-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody OverseasInWarehouseParam param) {
        PageResult<OverseasInWarehouseResult> list = service.queryListPage(param);
        return ResponseData.success(list);
    }

    /**
     * 海外仓入库管理查询列表数据汇总
     * @param param
     * @return
     */
    @PostResource(name = "海外仓入库管理查询列表数据汇总", path = "/queryPageTotal", requiredPermission = false)
    @ApiOperation(value = "海外仓入库管理查询列表数据汇总", response = OverseasInWarehouseTotalResult.class)
    @BusinessLog(title = "海外仓入库管理-列表数据汇总查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPageTotal(@RequestBody OverseasInWarehouseParam param) {
        return service.queryPageTotal(param);
    }

    /**
     * 海外仓入库管理是否可以全部签收
     * @param param
     * @return
     */
    @PostResource(name = "海外仓入库管理是否可以全部签收", path = "/allowAllSign", requiredPermission = false)
    @ApiOperation(value = "海外仓入库管理是否可以全部签收", response = OverseasInWarehouseTotalResult.class)
    @BusinessLog(title = "海外仓入库管理-是否可以全部签收",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData allowAllSign(@RequestBody OverseasInWarehouseParam param) {
        return service.allowAllSign(param);
    }

    /**
     * 查询入库单详情
     * @param param
     * @return
     */
    @PostResource(name = "查询详情", path = "/queryDetail")
    @ApiOperation(value = "查询详情", response = OverseasInWarehouseDetailResult.class)
    @BusinessLog(title = "海外仓入库管理-查询详情",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryDetail(@RequestBody OverseasInWarehouseDetailParam param) {
        PageResult<OverseasInWarehouseDetailResult> list=service.list(param);
         return ResponseData.success(list);
    }

    /**
     * 编辑收货仓
     * @param param
     * @return
     */
    @PostResource(name = "编辑收货仓", path = "/edit")
    @ApiOperation("编辑收货仓")
    @BusinessLog(title = "海外仓入库管理-编辑收货仓",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody OverseasInWarehouseParam param) {
        if(redisTemplate.hasKey(BATCH_FBA_SIGN)){
            return ResponseData.error("批量签收FBA退海外仓保存中，请稍后再试!");
        }
        return service.edit(param);
    }

    /**
     * 编辑备注
     * @param param
     * @return
     */
    @PostResource(name = "编辑备注", path = "/editRemark")
    @ApiOperation("编辑备注")
    @BusinessLog(title = "海外仓入库管理-编辑备注",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData editRemark(@RequestBody OverseasInWarehouseParam param) {
        return service.editRemark(param);
    }

    /**
     * 签收
     * @param param
     * @return
     */
    @PostResource(name = "签收", path = "/sign")
    @ApiOperation("签收")
    @BusinessLog(title = "海外仓入库管理-签收",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData sign(@RequestBody OverseasInWarehouseDetailParam param) throws Exception{
        log.info("海外仓签收入参[{}]", JSONObject.toJSON(param));
        //防止FBA退海外仓批量签收和单条签收同时操作同一条数据
        Boolean isFbaToAmazon = false;
        if("2".equals(param.getAllSign())){
            if(redisTemplate.hasKey(BATCH_FBA_SIGN)){
                return ResponseData.error("批量签收FBA退海外仓保存中，请稍后再试!");
            }
            if(StrUtil.isEmpty(param.getInWarehouseName())){
                return ResponseData.error("收货仓名称不为空！");
            }
            if("JP".equals(param.getSysSite())){
                if(StringUtils.isBlank(param.getUpdateSysShopsName()) && StringUtils.isBlank(param.getNewSysShopsName())){
                    return ResponseData.error("签收账号不能为空，请先选择签收账号！");
                }
                if(StringUtils.isNotBlank(param.getUpdateSysShopsName()) && StringUtils.isNotBlank(param.getNewSysShopsName())){
                    return ResponseData.error("不能同时选择2个签收账号！");
                }
            }
            isFbaToAmazon = true;
        }

        OverseasInWarehouseParam overseasInWarehouseParam = new OverseasInWarehouseParam();
        if(ObjectUtil.isNotEmpty(param)){
            BeanUtils.copyProperties(param,overseasInWarehouseParam);
            //组织编码
            String outOrgName = param.getPlatform() + "_" + param.getSysShopsName() + "_" + param.getSysSite();
            String inOrgName = param.getPlatform() + "_" + param.getSysShopsName() + "_" + param.getSysSite();
            //亚马逊发海外仓且是JP站点且更新账号为TS
            if(isFbaToAmazon && "JP".equals(param.getSysSite()) && "TS".equals(param.getNewSysShopsName())){
                overseasInWarehouseParam.setSysRemark("[FBA实际移除账号：" + param.getSysShopsName() + "，实际签收账号：" + param.getNewSysShopsName() + "]");
                overseasInWarehouseParam.setNewPlatform("Rakuten");
                String inTsOrgName = overseasInWarehouseParam.getNewPlatform() + "_" + param.getNewSysShopsName() + "_" + param.getSysSite();
                overseasInWarehouseParam.setInTsOrgName(inTsOrgName);
                overseasInWarehouseParam.setInTsOrg(erpWarehouseCodeConsumer.getOrganizationCode(inTsOrgName));
                if(StringUtils.isEmpty(overseasInWarehouseParam.getInTsOrg())){
                    return ResponseData.error("组织编码不存在,签收失败!");
                }
            }

            overseasInWarehouseParam.setInOrgName(inOrgName);
            overseasInWarehouseParam.setOutOrgName(outOrgName);
            overseasInWarehouseParam.setInOrg(erpWarehouseCodeConsumer.getOrganizationCode(inOrgName));
            overseasInWarehouseParam.setOutOrg(erpWarehouseCodeConsumer.getOrganizationCode(outOrgName));
            if(StringUtils.isEmpty(overseasInWarehouseParam.getInOrg()) || StringUtils.isEmpty(overseasInWarehouseParam.getOutOrg())){
                return ResponseData.error("组织编码不存在,签收失败!");
            }

            //分配物料即插入物料及组织
            List<String> codeList=service.matCodeList(param);
            if(CollectionUtil.isNotEmpty(codeList)){
                for(String matCode:codeList){
                    ZZDistributeMcms zParam=new ZZDistributeMcms();
                    zParam.setMaterialCode(matCode);
                    if(isFbaToAmazon && "TS".equals(param.getNewSysShopsName())){
                        zParam.setShopCode(overseasInWarehouseParam.getInTsOrg());
                        zZDistributeMcmsConsumer.add(zParam);
                    }
                    zParam.setShopCode(overseasInWarehouseParam.getOutOrg());
                    zZDistributeMcmsConsumer.add(zParam);
                }
            }
        }
        return service.sign(overseasInWarehouseParam,param);
    }

    /**
     * 海外仓入库管理列表导出接口
     * @param param
     * @param response
     * @return
     * @throws IOException
     */
    @PostResource(name = "导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "海外仓入库管理-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody OverseasInWarehouseParam param, HttpServletResponse response) throws IOException {

        List<OverseasInWarehouseResultVO> list = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("海外仓入库管理列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), OverseasInWarehouseResultVO.class).sheet("海外仓入库管理列表").doWrite(list);
        return ResponseData.success();
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
     */
    @GetResource(name = "站点下拉", path = "/siteSelect")
    @ApiOperation(value = "站点下拉")
    public ResponseData siteSelect() {
        return ResponseData.success(baseSelectConsumer.getSite());
    }

    /**
     * 业务类型下拉
     * @return
     */
    @GetResource(name = "业务类型下拉", path = "/operateTypeSelect")
    @ApiOperation(value = "业务类型下拉")
    public ResponseData operateTypeSelect() {
        return ResponseData.success(InBusinessTypeEnum.getInBusinessType());
    }

    /**
     * 出货仓名称下拉
     * @return
     */
    @GetResource(name = "出货仓名称下拉", path = "/outWarehouseSelect")
    @ApiOperation(value = "出货仓名称下拉")
    public ResponseData outWarehouseSelect() {
        return ResponseData.success(service.outWarehouseSelect());
    }

    /**
     * 收货仓名称下拉
     * @return
     */
    @GetResource(name = "收货仓名称下拉", path = "/inWarehouseSelect")
    @ApiOperation(value = "收货仓名称下拉")
    public ResponseData inWarehouseSelect() {
        return ResponseData.success(service.inWarehouseSelect());
    }

    /**
     * 签收状态下拉
     * @return
     */
    @GetResource(name = "签收状态下拉", path = "/confirmStatusSelect")
    @ApiOperation(value = "签收状态下拉")
    public ResponseData confirmStatusSelect() {
        return ResponseData.success(ConfirmStatusEnum.getConfirmStatus());
    }

    /**
     * 物流跟踪状态下拉
     * @return
     */
    @GetResource(name = "物流跟踪状态下拉", path = "/logisticsTrackStatusSelect")
    @ApiOperation(value = "物流跟踪状态下拉")
    public ResponseData logisticsTrackStatusSelect() {
        return ResponseData.success(LogisticsTrackStatusEnum.getLogisticsTrackStatus());
    }

    /**
     * 收货仓名称下拉（编辑）
     * @return
     */
    @GetResource(name = "收货仓名称下拉（编辑）", path = "/warehouseNameSelect", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "收货仓名称下拉（编辑）")
    public ResponseData warehouseNameSelect() {
        return overseasWarehouseManageService.warehouseNameBySiteSelect(null, null,null);
    }

    /**
     * 获取FBA退海外仓任务（定时/手动）
     * @return
     */
    @PostResource(name = "获取FBA退海外仓任务（定时/手动）", path = "/generateInWarehouseByFBA", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "获取FBA退海外仓任务（定时/手动）")
    @BusinessLog(title = "海外仓入库管理-获取FBA退海外仓",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData generateInWarehouseByFBA() {
        //先从remove order Detail刷取站点，未审核的数据匹配上且站点不同的情况下
        removalShipmentDetailConsumer.refreshSite();
        //获取Removal Shipment Detail数据
        List<OverseasInWarehouseFBAResult> resultList = removalShipmentDetailConsumer.generateInWarehouseByFBA();
        service.generateInWarehouseByFBA(resultList);
        service.refreshOiwMaterialCode();
        return ResponseData.success();
    }

    /**
     * 接收EBMS出货清单接口
     * @return
     */
    @PostResource(name = "接收EBMS出货清单接口", path = "/generateInWarehouseByEBMS", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "接收EBMS出货清单接口")
    @BusinessLog(title = "海外仓入库管理-接收EBMS出货清单接口",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData generateInWarehouseByEBMS(@RequestBody List<EbmsOverseasInWarehouseParam> params) {
        return service.generateInWarehouseByEBMS(params);
    }

    /**
     * 接收EBMS出货清单物流信息接口
     * @return
     */
    @PostResource(name = "接收EBMS出货清单物流信息接口", path = "/updateLogistics", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "接收EBMS出货清单物流信息接口")
    @BusinessLog(title = "海外仓入库管理-EBMS出货清单物流信息",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData updateLogistics(@RequestBody List<EbmsOiwLogisticsParam> params) {
        return service.updateLogistics(params);
    }

    /**
     * 接收EBMS出货清单物流跟踪状态接口
     * @return
     */
    @PostResource(name = "接收EBMS出货清单物流跟踪状态接口", path = "/updateLogisticsStatus", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "接收EBMS出货清单物流跟踪状态接口")
    @BusinessLog(title = "海外仓入库管理-EBMS出货清单物流跟踪状态",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData updateLogisticsStatus(@RequestBody List<EbmsOiwLogisticsParam> params) {
        return service.updateLogisticsStatus(params);
    }

    /**
     * 接收EBMS出货清单物流删除接口
     * @return
     */
    @PostResource(name = "接收EBMS出货清单物流删除接口", path = "/deleteLogistics", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "接收EBMS出货清单物流删除接口")
    @BusinessLog(title = "海外仓入库管理-EBMS出货清单物流删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteLogistics(@RequestBody List<EbmsOiwLogisticsParam> params) {
        return service.deleteLogistics(params);
    }

    /**
     * 海外仓入库单同步到K3组织内调拨单(定时)
     * @return
     */
    @PostResource(name = "海外仓入库单同步到K3组织内调拨单(定时)", path = "/reSyncTransferToErp")
    @ApiOperation(value = "海外仓入库单同步到K3组织内调拨单(定时)")
    public ResponseData reSyncTransferToErp() {return service.reSyncTransferToErp();}

    /**
     * FBA退海外仓签收导入模板下载
     * @param response
     */
    @GetResource(name = "FBA退海外仓签收导入模板下载", path = "/downloadFbaSignExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("FBA退海外仓签收导入模板下载")
    public void downloadFbaSignExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/FBA退海外仓签收导入模板.xlsx");
    }

    /**
     * 批量签收FBA退海外仓-导入
     * @param file
     * @return
     */
    @ParamValidator
    @PostResource(name = "批量签收FBA退海外仓-导入", path = "/uploadFbaSign", requiredPermission = false)
    @ApiOperation(value = "批量签收FBA退海外仓-导入", response = OwInFbaSignParam.class)
    @BusinessLog(title = "海外仓入库管理-批量签收FBA退海外仓-导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData uploadFbaSign(@RequestParam(value = "file", required = true) MultipartFile file) {
        List<String> platformList = baseSelectConsumer.getPlatform();
        List<String> shopList = baseSelectConsumer.getShop();
        List<String> siteList = baseSelectConsumer.getSite();
        List<String> departmentList = baseSelectConsumer.getDepartment();
        List<String> teamList = baseSelectConsumer.getTeam();
        ResponseData rd = overseasWarehouseManageService.warehouseNameBySiteSelect(null,null,"正常");
        if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(rd)){
            ResponseData.error("获取EBMS海外仓库名称异常！");
        }
        List<Map<String, String>> warehouseNameList = (List<Map<String, String>>) rd.getData();
        return service.uploadFbaSign(file, platformList, shopList, siteList, departmentList, teamList, warehouseNameList);
    }

    /**
     * 批量签收FBA退海外仓-保存
     * @param params
     * @return
     */
    @ParamValidator
    @PostResource(name = "批量签收FBA退海外仓-保存", path = "/batchFbaSign", requiredPermission = false)
    @ApiOperation(value = "批量签收FBA退海外仓-保存")
    @BusinessLog(title = "海外仓入库管理-批量签收FBA退海外仓-保存",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData batchFbaSign(@Validated @RequestBody List<OwInFbaSignParam> params) {
        if(CollectionUtils.isEmpty(params)){
            return ResponseData.error("入参数据为空！");
        }
        if(redisTemplate.hasKey(BATCH_FBA_SIGN)){
            return ResponseData.error("批量签收FBA退海外仓保存中，请稍后再试!");
        }
        try {
            redisTemplate.boundValueOps(BATCH_FBA_SIGN).set("批量签收FBA退海外仓保存中", Duration.ofSeconds(600));
            //刷组织编码
            for(OwInFbaSignParam param : params){
                String outOrgName = "Amazon" + "_" + param.getSysShopsName() + "_" + param.getSysSite();
                String inOrgName = param.getPlatform() + "_" + param.getSysShopsName() + "_" + param.getSysSite();
                //组织入库
                param.setInOrg(erpWarehouseCodeConsumer.getOrganizationCode(inOrgName));
                param.setOutOrg(erpWarehouseCodeConsumer.getOrganizationCode(outOrgName));
                param.setInOrgName(inOrgName);
                param.setOutOrgName(outOrgName);

                //分配物料即插入物料及组织
                if(StringUtils.isBlank(param.getUploadRemark())){
                    ZZDistributeMcms zParam = new ZZDistributeMcms();
                    zParam.setMaterialCode(param.getMaterialCode());
                    zParam.setShopCode(param.getInOrg());
                    zZDistributeMcmsConsumer.add(zParam);
                }
            }
            return service.batchFbaSign(params);
        }catch (Exception e) {
            log.info("批量签收FBA退海外仓保存异常，异常信息[{}]", e);
            return ResponseData.error("批量签收FBA退海外仓保存异常!");
        } finally {
            redisTemplate.delete(BATCH_FBA_SIGN);
        }
    }

    /**
     * 批量签收FBA退海外仓（新）
     * @return
     */
    @PostResource(name = "批量签收FBA退海外仓（新）", path = "/batchFbaSignNew", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "批量签收FBA退海外仓（新）")
    @BusinessLog(title = "海外仓入库管理-批量签收FBA退海外仓（新）",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData batchFbaSignNew(@RequestBody List<OwInFbaSignParam> params) {
        if(redisTemplate.hasKey(BATCH_FBA_SIGN)){
            return ResponseData.error("批量签收FBA退海外仓保存中，请稍后再试!");
        }
        try {
            for (OwInFbaSignParam param : params) {
                OverseasInWarehouse inWarehouse = service.getById(param.getId());
                if(inWarehouse == null){
                    return ResponseData.error("不存在此记录，批量签收失败！");
                }
                if(!OperateTypeEnum.AMAZON_TO_OVERSEAS.getName().equals(inWarehouse.getOperateType())){
                    return ResponseData.error("不支持非亚马逊发海外仓的批量签收，批量签收失败！");
                }
                if(ConfirmStatusEnum.ALREADY_CONFIRM.getName().equals(inWarehouse.getConfirmStatus())){
                    return ResponseData.error("此入库单已签收完成，批量签收失败！");
                }
                String outOrgName = inWarehouse.getPlatform() + "_" + inWarehouse.getSysShopsName() + "_" + inWarehouse.getSysSite();
                String inOrgName = inWarehouse.getPlatform() + "_" + inWarehouse.getSysShopsName() + "_" + inWarehouse.getSysSite();
                //组织入库
                param.setInOrg(erpWarehouseCodeConsumer.getOrganizationCode(inOrgName));
                param.setOutOrg(erpWarehouseCodeConsumer.getOrganizationCode(outOrgName));
                param.setInOrgName(inOrgName);
                param.setOutOrgName(outOrgName);

                //分配物料即插入物料及组织
                if(StringUtils.isBlank(param.getUploadRemark())){
                    ZZDistributeMcms zParam = new ZZDistributeMcms();
                    zParam.setMaterialCode(param.getMaterialCode());
                    zParam.setShopCode(param.getInOrg());
                    zZDistributeMcmsConsumer.add(zParam);
                }
            }
            return service.batchFbaSignNew(params);
        }catch (Exception e) {
            log.info("批量签收FBA退海外仓保存异常，异常信息[{}]", e);
            return ResponseData.error("批量签收FBA退海外仓保存异常!");
        } finally {
            redisTemplate.delete(BATCH_FBA_SIGN);
        }
    }
}
