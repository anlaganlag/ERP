package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.result.DeptUserInfo;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasWarehouseManage;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasWarehouseManageResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasWarehouseManageTotalResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.ValidateLabelResult;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.ErpWarehouseCodeConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.ZZDistributeMcmsConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.CheckReasonEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IOverseasWarehouseManageService;
import com.tadpole.cloud.supplyChain.modular.consumer.BaseSelectConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  海外仓库存管理前端控制器
 * </p>
 *
 * @author cyt
 * @since 2022-07-19
 */
@RestController
@ApiResource(name = "海外仓库存管理前端控制器", path = "/overseasWarehouseManage")
@Api(tags = "海外仓库存管理")
public class OverseasWarehouseManageController {

    @Autowired
    private IOverseasWarehouseManageService service;
    @Autowired
    private BaseSelectConsumer baseSelectConsumer;
    @Autowired
    private ErpWarehouseCodeConsumer erpWarehouseCodeConsumer;
    @Autowired
    private IOverseasWarehouseManageService manageService;
    @Autowired
    private ZZDistributeMcmsConsumer zZDistributeMcmsConsumer;

    /**
     * 海外仓管理分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "海外仓库存管理", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "海外仓库存管理分页查询列表", response = OverseasWarehouseManageResult.class)
    @BusinessLog(title = "海外仓库存管理-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody OverseasWarehouseManageParam param) {
        return service.queryPage(param);
    }

    /**
     * 海外仓管理分页查询列表数据汇总
     * @param param
     * @return
     */
    @PostResource(name = "海外仓管理分页查询列表数据汇总", path = "/queryPageTotal")
    @ApiOperation(value = "海外仓管理分页查询列表数据汇总", response = OverseasWarehouseManageTotalResult.class)
    @BusinessLog(title = "海外仓库存管理-列表数据汇总",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPageTotal(@RequestBody OverseasWarehouseManageParam param) {
        return service.queryPageTotal(param);
    }

    /**
     * 海外仓管理导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "海外仓库存管理导出", path = "/export", requiredPermission = false)
    @ApiOperation(value = "海外仓库存管理导出")
    @BusinessLog(title = "海外仓库存管理-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody OverseasWarehouseManageParam param, HttpServletResponse response) throws IOException {
        List<OverseasWarehouseManageResult> resultList = service.export(param);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("海外仓管理列表导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), OverseasWarehouseManageResult.class).sheet("海外仓管理列表导出").doWrite(resultList);
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
     * 仓库名称下拉
     * @return
     */
    @GetResource(name = "仓库名称下拉", path = "/warehouseNameSelect", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "仓库名称下拉")
    public ResponseData warehouseNameSelect() {
        return service.warehouseNameBySiteSelect(null,null,null);
    }

    /**
     * 根据站点级联仓库名称下拉
     * @return
     */
    @GetResource(name = "根据站点级联仓库名称下拉", path = "/warehouseNameBySiteSelect", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "根据站点级联仓库名称下拉")
    public ResponseData warehouseNameBySiteSelect(@RequestParam(value = "site") String site) {
        return service.warehouseNameBySiteSelect(site,null, "正常");
    }

    /**
     * 事业部和Team级联下拉
     * @return
     */
    @GetResource(name = "事业部和Team级联下拉", path = "/selectDeptAndTeam")
    @ApiOperation(value = "事业部和Team级联下拉")
    public ResponseData selectDeptAndTeam() {
        return ResponseData.success(baseSelectConsumer.selectDeptAndTeam());
    }

    /**
     * 事业部下拉
     * @return
     */
    @GetResource(name = "事业部下拉", path = "/departmentSelect")
    @ApiOperation(value = "事业部下拉")
    public ResponseData departmentSelect() {
        return ResponseData.success(baseSelectConsumer.getDepartmentSelect());
    }

    /**
     * Team下拉
     * @return
     */
    @GetResource(name = "Team下拉", path = "/teamSelect")
    @ApiOperation(value = "Team下拉")
    public ResponseData teamSelect() {
        return ResponseData.success(baseSelectConsumer.getTeamSelect());
    }

    /**
     * 需求人员下拉
     * @return
     */
    @PostResource(name = "需求人员下拉", path = "/userSelect")
    @ApiOperation(value = "需求人员下拉")
    public ResponseData userSelect(@RequestBody DeptUserInfo param) {
        return ResponseData.success(baseSelectConsumer.getUserInfoByDept(param));
    }

    /**
     * 换标
     * @param param
     * @return
     */
    @PostResource(name = "换标", path = "/exchange")
    @ApiOperation(value = "换标")
    @BusinessLog(title = "海外仓库存管理-换标",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData exchange(@Validated @RequestBody OverseasWarehouseChangeParam param) {
        OverseasWarehouseManage overseasWarehouseManage = manageService.getManageById(param.getId());
        if(overseasWarehouseManage == null){
            return ResponseData.error("换标失败，不存在此库存信息！");
        }

        /*List<String> supportPlatforms = Arrays.asList("Amazon", "Walmart");
        if(!supportPlatforms.contains(overseasWarehouseManage.getPlatform())){
            return ResponseData.error("换标失败，此平台不支持换标！");
        }*/

        if("Rakuten".equals(param.getPlatform()) && !"TS".equals(param.getSysShopsName())){
            return ResponseData.error("换标失败，Rakuten平台换标新账号必须为TS！");
        }

        if(BigDecimal.ZERO.compareTo(param.getInventoryQuantity()) > 0
                || param.getInventoryQuantity().compareTo(overseasWarehouseManage.getInventoryQuantity()) > 0){
            return ResponseData.error("换标失败，换标数量不能为负数且不能超过库存数量！");
        }

        String newOrg = param.getPlatform() + "_" + param.getSysShopsName() + "_" + param.getSysSite();
        String oldOrg = overseasWarehouseManage.getPlatform() + "_" + overseasWarehouseManage.getSysShopsName() + "_" + overseasWarehouseManage.getSysSite();
        //新换标标签校验
        ValidateLabelParam validateParam = new ValidateLabelParam();
        validateParam.setShopName(newOrg);
        validateParam.setFnSku(param.getFnSku());
        validateParam.setSku(param.getSku());
        List<ValidateLabelResult> labelResult = null;
        if("Amazon".equals(param.getPlatform())){
            labelResult = service.validateLabel(validateParam);
        }else{
            labelResult = service.validateK3Label(validateParam);
        }
        if(CollectionUtils.isEmpty(labelResult)){
            return ResponseData.error("不存在该标签，换标失败！");
        }
        if(labelResult.size() > 1){
            return ResponseData.error("新标校验存在多条记录，换标失败！");
        }
        param.setFnSku(labelResult.get(0).getFnSku());
        //新换标物料通过后台标签校验获取
        param.setMaterialCode(labelResult.get(0).getMaterialCode());
        //是否更换物料编码
        if(overseasWarehouseManage.getMaterialCode().equals(param.getMaterialCode())){
            param.setIsChangeMaterialCode(false);
        }else{
            param.setIsChangeMaterialCode(true);
        }

        if(oldOrg.equals(newOrg)){
            param.setIsChangeOrg(false);
            if(!"Rakuten".equals(param.getPlatform()) && (param.getSku().equals(overseasWarehouseManage.getSku()) || param.getFnSku().equals(overseasWarehouseManage.getFnSku()))){
                return ResponseData.error("换标失败，SKU和FNSKU必须更换！");
            }
        }else{
            param.setIsChangeOrg(true);
        }

        //校验账号对应的仓库名称
        ResponseData responseData = service.warehouseNameBySiteSelect(param.getSysSite(), param.getWarehouseName(),"正常");
        if(ResponseData.DEFAULT_SUCCESS_CODE.equals(responseData.getCode())){
            List<Map<String, Object>> dateList = (List<Map<String, Object>>) responseData.getData();
            if(CollectionUtils.isEmpty(dateList)){
                return ResponseData.error("换标失败，站点对应的仓库名称不存在！");
            }
        }

        //是否跨站点换标
        if(param.getSysSite().equals(overseasWarehouseManage.getSysSite())){
            param.setIsChangeSite(false);
        }else{
            param.setIsChangeSite(true);
        }

        param.setInOrg(erpWarehouseCodeConsumer.getOrganizationCode(newOrg));
        param.setOutOrg(erpWarehouseCodeConsumer.getOrganizationCode(oldOrg));
        param.setInOrgName(newOrg);
        param.setOutOrgName(oldOrg);

        //分配物料即插入物料及组织
        ZZDistributeMcms zParam = new ZZDistributeMcms();
        zParam.setMaterialCode(param.getMaterialCode());
        zParam.setShopCode(erpWarehouseCodeConsumer.getOrganizationCode(newOrg));
        zZDistributeMcmsConsumer.add(zParam);

        return service.exchange(param, overseasWarehouseManage);
    }

    /**
     * 批量换标-导入
     * @param file
     * @return
     */
    @ParamValidator
    @PostResource(name = "批量换标-导入", path = "/upload", requiredPermission = false)
    @ApiOperation(value = "批量换标-导入", response = OverseasWarehouseManageExchangeParam.class)
    @BusinessLog(title = "海外仓库存管理-批量换标-导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(@RequestParam(value = "file", required = true) MultipartFile file) {
        List<String> platformList = baseSelectConsumer.getPlatform();
        List<String> shopList = baseSelectConsumer.getShop();
        List<String> siteList = baseSelectConsumer.getSite();
        List<String> departmentList = baseSelectConsumer.getDepartment();
        List<String> teamList = baseSelectConsumer.getTeam();
        List<Map<String, Object>> userList = baseSelectConsumer.userInfoSelect();
        return service.upload(file, platformList, shopList, siteList, departmentList, teamList, userList);
    }

    /**
     * 批量换标-保存
     * @param params
     * @return
     */
    @ParamValidator
    @PostResource(name = "批量换标-保存", path = "/batchExchange", requiredPermission = false)
    @ApiOperation(value = "批量换标-保存")
    @BusinessLog(title = "海外仓库存管理-批量换标-保存",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData batchExchange(@Validated @RequestBody List<OverseasWarehouseManageExchangeParam> params) {
        if(CollectionUtils.isEmpty(params)){
            return ResponseData.error("入参数据为空！");
        }
        //标签校验
        Boolean isValidPass = true;
        //账存数量
        Map<BigDecimal, BigDecimal> oldIdQuantityMap = new HashMap<>();
        for(OverseasWarehouseManageExchangeParam param : params){
            //验证基础信息
            StringBuffer validInfo = new StringBuffer();
            if(StringUtils.isNotEmpty(param.getUploadRemark())){
                validInfo.append(param.getUploadRemark());
                isValidPass = false;
                continue;
            }

            //根据维度查询海外仓账存信息
            OverseasWarehouseManage selectWarehouseManage = new OverseasWarehouseManage();
            selectWarehouseManage.setPlatform(param.getOldPlatform());
            selectWarehouseManage.setSysShopsName(param.getOldSysShopsName());
            selectWarehouseManage.setSysSite(param.getOldSysSite());
            selectWarehouseManage.setWarehouseName(param.getOldWarehouseName());
            selectWarehouseManage.setSku(param.getOldSku());
            selectWarehouseManage.setMaterialCode(param.getOldMaterialCode());
            selectWarehouseManage.setFnSku(param.getOldFnSku());
            OverseasWarehouseManage overseasWarehouseManage = service.getWarehouseManage(selectWarehouseManage);
            if(overseasWarehouseManage == null){
                validInfo.append("换标失败，不存在此库存信息！");
                param.setUploadRemark(validInfo.toString());
                isValidPass = false;
                continue;
            }else{
                /*List<String> supportPlatforms = Arrays.asList("Amazon", "Walmart");
                if(!supportPlatforms.contains(overseasWarehouseManage.getPlatform())){
                    validInfo.append("换标失败，此平台不支持换标！");
                    param.setUploadRemark(validInfo.toString());
                    isValidPass = false;
                    continue;
                }*/
                if("Rakuten".equals(param.getPlatform()) && !"TS".equals(param.getSysShopsName())){
                    validInfo.append("换标失败，Rakuten平台换标新账号必须为TS！");
                    param.setUploadRemark(validInfo.toString());
                    isValidPass = false;
                    continue;
                }

                //换标账存数量
                BigDecimal changeQuantity = param.getInventoryQuantity();
                //换标前账存数量
                BigDecimal oldQuantity;
                if(oldIdQuantityMap.get(overseasWarehouseManage.getId()) == null){
                    oldQuantity = overseasWarehouseManage.getInventoryQuantity();
                    oldIdQuantityMap.put(overseasWarehouseManage.getId(), overseasWarehouseManage.getInventoryQuantity());
                } else {
                    oldQuantity = oldIdQuantityMap.get(overseasWarehouseManage.getId());
                }
                //BigDecimal oldQuantity = overseasWarehouseManage.getInventoryQuantity();
                if(BigDecimal.ZERO.compareTo(changeQuantity) > 0 || changeQuantity.compareTo(oldQuantity) > 0){
                    validInfo.append("换标失败，换标数量不能为负数且不能超过库存数量！");
                    param.setUploadRemark(validInfo.toString());
                    isValidPass = false;
                    continue;
                }
                param.setOldInventoryQuantity(oldQuantity);
                param.setNewInventoryQuantity(oldQuantity.subtract(changeQuantity));
                //通过导入fnSKU后六位匹配出对应的fnSKU
                param.setOldValidFnSku(overseasWarehouseManage.getFnSku());
                param.setId(overseasWarehouseManage.getId());
                param.setComeQuantity(overseasWarehouseManage.getComeQuantity());

                //扣除只会当前的库存
                oldIdQuantityMap.put(overseasWarehouseManage.getId(), oldQuantity.subtract(changeQuantity));
            }

            String newOrg = param.getPlatform() + "_" + param.getSysShopsName() + "_" + param.getSysSite();
            String oldOrg = param.getOldPlatform() + "_" + param.getOldSysShopsName() + "_" + param.getOldSysSite();
            if(oldOrg.equals(newOrg)){
                param.setIsChangeOrg(false);
                //同组织换标，sku和fnsku必须更换
                if(!"Rakuten".equals(param.getPlatform()) && (param.getOldSku().equals(param.getSku()) || param.getOldFnSku().equals(param.getFnSku()))){
                    validInfo.append("换标失败，SKU和FNSKU必须更换！");
                    param.setUploadRemark(validInfo.toString());
                    isValidPass = false;
                    continue;
                }
            }else{
                param.setIsChangeOrg(true);
            }

            //校验账号对应的仓库名称
            ResponseData responseData = service.warehouseNameBySiteSelect(param.getSysSite(), param.getWarehouseName(), "正常");
            if(ResponseData.DEFAULT_SUCCESS_CODE.equals(responseData.getCode())){
                List<Map<String, Object>> dateList = (List<Map<String, Object>>) responseData.getData();
                if(CollectionUtils.isEmpty(dateList)){
                    validInfo.append("换标失败，站点对应的仓库名称不存在！");
                    param.setUploadRemark(validInfo.toString());
                    isValidPass = false;
                    continue;
                }
            }

            //是否跨站点换标
            if(param.getSysSite().equals(param.getOldSysSite())){
                param.setIsChangeSite(false);
            }else{
                param.setIsChangeSite(true);
            }

            //组织入库
            param.setInOrg(erpWarehouseCodeConsumer.getOrganizationCode(newOrg));
            param.setOutOrg(erpWarehouseCodeConsumer.getOrganizationCode(oldOrg));
            param.setInOrgName(newOrg);
            param.setOutOrgName(oldOrg);

            ValidateLabelParam validateParam = new ValidateLabelParam();
            validateParam.setShopName(newOrg);
            validateParam.setFnSku(param.getFnSku());
            validateParam.setSku(param.getSku());
            List<ValidateLabelResult> labelResult = null;
            if("Amazon".equals(param.getPlatform())){
                labelResult = service.validateLabel(validateParam);
            }else{
                labelResult = service.validateK3Label(validateParam);
            }
            if(CollectionUtils.isEmpty(labelResult)){
                validInfo.append("不存在该标签，换标失败！");
                param.setUploadRemark(validInfo.toString());
                isValidPass = false;
                continue;
            }else{
                if(labelResult.size() > 1){
                    validInfo.append("新标校验存在多条记录，换标失败！");
                    param.setUploadRemark(validInfo.toString());
                    isValidPass = false;
                    continue;
                }
                //根据店铺、sku、fnsku获取物料编码
                param.setMaterialCode(labelResult.get(0).getMaterialCode());
                param.setFnSku(labelResult.get(0).getFnSku());

                //是否更换物料编码
                if(overseasWarehouseManage.getMaterialCode().equals(param.getMaterialCode())){
                    param.setIsChangeMaterialCode(false);
                }else{
                    param.setIsChangeMaterialCode(true);
                }
            }

            //分配物料即插入物料及组织
            ZZDistributeMcms zParam = new ZZDistributeMcms();
            zParam.setMaterialCode(param.getMaterialCode());
            zParam.setShopCode(erpWarehouseCodeConsumer.getOrganizationCode(newOrg));
            zZDistributeMcmsConsumer.add(zParam);
        }
        return service.batchExchange(params, isValidPass);
    }

    /**
     * 盘点
     * @param param
     * @return
     */
    @PostResource(name = "盘点", path = "/check")
    @ApiOperation(value = "盘点")
    @BusinessLog(title = "海外仓库存管理-盘点",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData check(@RequestBody OverseasWarehouseManageParam param) {
        if(param.getId() == null || param.getInventoryQuantity() == null || BigDecimal.ZERO.compareTo(param.getInventoryQuantity()) > 0 || StringUtils.isBlank(param.getCheckReason())){
            return ResponseData.error("盘点失败，入参异常！");
        }
        OverseasWarehouseManage overseasWarehouseManage = manageService.getManageById(param.getId());
        if(overseasWarehouseManage == null){
            return ResponseData.error("盘点失败，不存在此库存信息！");
        }
        if(param.getInventoryQuantity().compareTo(overseasWarehouseManage.getInventoryQuantity()) == 0){
            return ResponseData.error("盘点失败，盘点账存数量与现有数量不能相同！");
        }
        String org = overseasWarehouseManage.getPlatform() + "_" + overseasWarehouseManage.getSysShopsName() + "_" + overseasWarehouseManage.getSysSite();
        String orgCode = erpWarehouseCodeConsumer.getOrganizationCode(org);
        param.setInOrg(orgCode);
        param.setOutOrg(orgCode);
        param.setInOrgName(org);
        param.setOutOrgName(org);
        return service.check(param, overseasWarehouseManage);
    }

    /**
     * 批量盘点-导入
     * @param file
     * @return
     */
    @ParamValidator
    @PostResource(name = "批量盘点-导入", path = "/uploadCheck", requiredPermission = false)
    @ApiOperation(value = "批量盘点-导入", response = OverseasWarehouseManageCheckParam.class)
    @BusinessLog(title = "海外仓库存管理-批量盘点-导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData uploadCheck(@RequestParam(value = "file", required = true) MultipartFile file) {
        List<String> platformList = baseSelectConsumer.getPlatform();
        List<String> shopList = baseSelectConsumer.getShop();
        List<String> siteList = baseSelectConsumer.getSite();
        List<String> departmentList = baseSelectConsumer.getDepartment();
        List<String> teamList = baseSelectConsumer.getTeam();
        return service.uploadCheck(file, platformList, shopList, siteList, departmentList, teamList);
    }

    /**
     * 批量盘点-保存
     * @param params
     * @return
     */
    @ParamValidator
    @PostResource(name = "批量盘点-保存", path = "/batchCheck", requiredPermission = false)
    @ApiOperation(value = "批量盘点-保存")
    @BusinessLog(title = "海外仓库存管理-批量盘点-保存",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData batchCheck(@Validated @RequestBody List<OverseasWarehouseManageCheckParam> params) {
        if(CollectionUtils.isEmpty(params)){
            return ResponseData.error("入参数据为空！");
        }
        for(OverseasWarehouseManageCheckParam param : params){
            String org = param.getPlatform() + "_" + param.getSysShopsName() + "_" + param.getSysSite();
            String orgCode = erpWarehouseCodeConsumer.getOrganizationCode(org);
            param.setInOrg(orgCode);
            param.setOutOrg(orgCode);
            param.setInOrgName(org);
            param.setOutOrgName(org);
        }
        return service.batchCheck(params);
    }

    /*@PostResource(name = "测试调用ERP盘点", path = "/checkERP", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "测试调用ERP盘点")
    public ResponseData checkERP() {
        JSONArray Jarr = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("FBillNo", "PDRK20220919000003");
        object.put("FName", "海外仓盘点");
        object.put("FNote", "盘点");
        object.put("FCreatorId", "S20180229");
        //库存组织
        object.put("FStockOrgId", "701");
        //Inventory：按当前日期即时库存盘点 CloseDate：根据指定日期的库存盘点，同步传入一个截止日期。
//        object.put("FBackUpType", "Inventory");
        object.put("FBackUpType", "CloseDate");
        object.put("FBackUpDATE", "2022-09-01");
        object.put("FZeroStockInCount", "1");
        object.put("FCheckQtyDefault", "0");

        JSONArray detailJarr = new JSONArray();
        JSONObject detailObj = new JSONObject();
        //仓库编码
        detailObj.put("FStockId", "701");
        detailObj.put("FMaterialId", "P035CE01");
        detailObj.put("FCountQty", 1);
        detailObj.put("F_REQUIRETEAM", "Team8");
        detailObj.put("F_REQUIREDEP", "事业四部");
        detailJarr.add(detailObj);
        object.put("FEntity", detailJarr);
        Jarr.add(object);
        JSONObject obj = syncToErpConsumer.endingInventory(Jarr);
        if (obj.getString("Code") != null && obj.getString("Code").equals("0")) {
            return ResponseData.success();
        } else {
            String responseMsg = null;
            JSONArray responseResult = JSON.parseArray(obj.getString("Response"));
            if (CollectionUtil.isNotEmpty(responseResult)) {
                responseMsg = responseResult.getJSONObject(0).getString("SubMessage");

            }
            return ResponseData.error("同步erp盘点接口失败！" + obj.getString("Message") + responseMsg);
        }
    }*/

    /**
     * 每天定时生成海外仓订单号
     * @return
     */
    @PostResource(name = "每天定时生成海外仓订单号", path = "/generateOrder", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "每天定时生成海外仓订单号")
    public ResponseData generateOrder() {
        return service.generateOrder();
    }

    /**
     * 手动生成每天海外仓订单号
     * @param orderType
     * @param key
     * @param noLength
     * @return
     */
    @PostResource(name = "手动生成每天海外仓订单号", path = "/handleGenerateOrder", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "手动生成每天海外仓订单号")
    public ResponseData handleGenerateOrder(@Param("orderType") String orderType, @Param("key") String key, @Param("noLength") Integer noLength) {
        return service.handleGenerateOrder(orderType, key, noLength);
    }

    /**
     * 换标导入模板下载
     * @param response
     */
    @GetResource(name = "换标导入模板下载", path = "/downloadExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("换标导入模板下载")
    public void downloadExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/换标导入模板.xlsx");
    }

    /**
     * 盘点导入模板下载
     * @param response
     */
    @GetResource(name = "盘点导入模板下载", path = "/downloadCheckExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("盘点导入模板下载")
    public void downloadCheckExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/盘点导入模板.xlsx");
    }

    /**
     * 盘点原因下拉
     * @return
     */
    @GetResource(name = "盘点原因下拉", path = "/checkReasonSelect")
    @ApiOperation(value = "盘点原因下拉")
    public ResponseData checkReasonSelect(@RequestParam(value = "type") String type) {
        if("add".equals(type)){
            return ResponseData.success(CheckReasonEnum.getAddCheckReason());
        }else if("sub".equals(type)){
            return ResponseData.success(CheckReasonEnum.getSubCheckReason());
        } else{
            return ResponseData.success(CheckReasonEnum.getCheckReason());
        }
    }
}
