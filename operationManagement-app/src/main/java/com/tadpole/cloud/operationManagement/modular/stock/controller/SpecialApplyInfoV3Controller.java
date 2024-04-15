package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.tadpole.cloud.operationManagement.modular.stock.consumer.BaseSelectConsumer;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SpecialApplyInfoV3;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SpecialApplyInfoV3ComitDto;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SpecialApplyInfoV3Param;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.BackPrepareRecomBiResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SpecialApplyInfoV3Result;
import com.tadpole.cloud.operationManagement.modular.stock.service.ISpecialApplyInfoV3Service;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockAreaBlacklistService;
import com.tadpole.cloud.operationManagement.modular.stock.service.RpMaterialService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.Material;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 特殊备货申请V3 前端控制器
 * </p>
 *
 * @author lsy
 * @since 2022-08-31
 */

@Slf4j
@RestController
@ApiResource(name = "特殊备货申请V3", path = "/specialApplyInfoV3")
@Api(tags = "特殊备货申请V3")
public class SpecialApplyInfoV3Controller {

    @Resource
    private RpMaterialService matService;

    @Resource
    private ISpecialApplyInfoV3Service service;

    @Autowired
    private BaseSelectConsumer baseSelectConsumer;

    @Autowired
    private IStockAreaBlacklistService stockAreaBlacklistService;

    private final String controllerName = "特殊备货申请V3";

    @PostResource(name = controllerName + "_" +"分页查询", path = "/listPage", menuFlag = false)
    @ApiOperation(value = controllerName + "_" +"分页查询", response = SpecialApplyInfoV3Result.class)
    @BusinessLog(title = controllerName + "_" +"分页查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody SpecialApplyInfoV3Param param) {
        return service.queryListPage(param);
    }

    @PostResource(name = controllerName + "_" +"列表查询", path = "/list", menuFlag = true)
    @ApiOperation(value = controllerName + "_" +"列表查询", response = SpecialApplyInfoV3Result.class)
    @BusinessLog(title = controllerName + "_" +"列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryList(@RequestBody SpecialApplyInfoV3Param param) {

        LoginUser loginUser = LoginContext.me().getLoginUser();
        if (ObjectUtils.isNull(loginUser)) {
            return ResponseData.error(controllerName + "_" +"列表查询错误，原因：未查询到登陆用户！");
        }

        if (loginUser.getTeam().contains("部")) {
            param.setDepartment(loginUser.getTeam());
            param.setTeam(null);
        } else {
            param.setDepartment(loginUser.getDepartment());
            param.setTeam(loginUser.getTeam());
        }
        param.setOperator(loginUser.getAccount());
        return service.queryList(param);
    }

    @PostResource(name = controllerName + "_" +"新增", path = "/add", requiredPermission = false)
    @ApiOperation(value = controllerName + "_" +"新增", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody SpecialApplyInfoV3Param param) {
        if (ObjectUtil.isEmpty(param)) {
            return ResponseData.success(controllerName + "_" +"新增失败，原因：获取数据异常，请确认传入数据。");
        }
        LoginUser loginUser = LoginContext.me().getLoginUser();

        if (ObjectUtil.isNull(loginUser) ||
                ObjectUtil.isEmpty(loginUser.getTeam()) ||
                !(loginUser.getTeam().toLowerCase().contains("team") || loginUser.getTeam().toLowerCase().contains("组"))) {
            return ResponseData.error(controllerName + "_" +"新增失败，原因：未找到申请人所属的Team或者平台发展部对应下属组");
        }
        try {
            Material material = matService.getMaterialInfo(param.getMaterialCode());
            List<String> codes = new ArrayList<>();
            codes.add(param.getMaterialCode());
            ResponseData responseData = matService.isValidateMater(codes, controllerName);
            if (!responseData.getSuccess()) {
                return responseData;
            }

            param.setTeam(loginUser.getTeam());
            param.setDepartment(loginUser.getDepartment());
            return service.add(param, material, controllerName);
        } catch (Exception ex) {
            return ResponseData.error(controllerName + "_" +"新增异常，原因：" + ex.getMessage());
        }
    }

    @ParamValidator
    @ApiOperation(value = controllerName + "_" +"导入", response = ResponseData.class)
    @PostResource(name = controllerName + "_" +"导入", path = "/upload", requiredPermission = false)
    @BusinessLog(title = controllerName + "_" +"导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(@RequestParam(value = "file", required = true) MultipartFile file) {
        if (file == null) {
            return ResponseData.error(controllerName + "_" +"导入失败，原因：未检测到上传文件。");
        }

        LoginUser loginUser = LoginContext.me().getLoginUser();

        if (ObjectUtil.isNull(loginUser) || ObjectUtil.isEmpty(loginUser.getTeam())) {
            return ResponseData.error(controllerName + "_" +",EBMS系统未找到申请人信息（2）精准匹配");
        }

        if (!(loginUser.getTeam().toLowerCase().contains("team") || loginUser.getTeam().toLowerCase().contains("组"))) {
            return ResponseData.error(controllerName + "_" +",未找到申请人所属的Team或者平台发展部对应下属组");
        }

        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<SpecialApplyInfoV3Param>();
            EasyExcel.read(buffer, SpecialApplyInfoV3Param.class, listener).sheet().headRowNumber(2).doRead();

            //导入的集合
            List<SpecialApplyInfoV3Param> dataList = listener.getDataList();

            //基础验证
            if (CollectionUtil.isEmpty(dataList)) {
                return ResponseData.error(controllerName + "_" +"导入失败，原因：数据为空。");
            }
            if (dataList.size() > 500) {
                return ResponseData.error(controllerName + "_" +"导入失败，原因：单次导入数据不能超过500条。");
            }
            //黑名单校验
            ResponseData checkResult = stockAreaBlacklistService.checkAreaBlacklist(dataList);
            if (! checkResult.getSuccess()) {
                return checkResult;
            }
            boolean containAmzPlatform=false;

            //平台 校验
            List<String> platformList = baseSelectConsumer.getPlatform();
            Set<String> excelDataPlatformSet = dataList.stream().map(t -> t.getPlatform()).collect(Collectors.toSet());
            for (String excelDataPlatform : excelDataPlatformSet) {
                if (! platformList.contains(excelDataPlatform)) {
                    return ResponseData.error(controllerName + "_" +"导入失败，原因：导入数据平台名称填写错误【"+excelDataPlatform+"】,正确的值域【"+ JSONUtil.toJsonStr(platformList) +"】");
                }

                if ("Amazon".equals(excelDataPlatform)) {
                    containAmzPlatform=true;
                }
            }

            //部门 验证
            Set<String> departments = dataList.stream().map(t -> t.getDepartment()).collect(Collectors.toSet());
            if (departments.size() > 1 || !departments.contains(loginUser.getDepartment())) {
                return ResponseData.error(controllerName + "_" +"导入失败，原因：导入数据包含多个部门，或者当前登录账户OA对应部门值【"+loginUser.getDepartment()+"】 导入的部门与其不相符。");
            }

            //team 验证
            Set<String> teams = dataList.stream().map(t -> t.getTeam()).collect(Collectors.toSet());
            if (teams.size() > 1 || !teams.contains(loginUser.getTeam())) {
                return ResponseData.error(controllerName + "_" +"导入失败，原因：导入数据包含多个Team，或者当前登录账户OA对应Team的值【"+loginUser.getTeam()+"】 与 导入的Team与其不相符--注意:区分大小写。");
            }

            //区域 验证
            List<String> areaList =  baseSelectConsumer.getArea();
            Set<String> excelDataAreaSet = dataList.stream().map(t -> t.getArea()).collect(Collectors.toSet());
            for (String excelDataArea : excelDataAreaSet) {
                if (! areaList.contains(excelDataArea)) {
                    return ResponseData.error(controllerName + "_" +"导入失败，原因：导入数据Area区域填写错误【"+excelDataArea+"】,正确的值域【"+ JSONUtil.toJsonStr(areaList) +"】,");
                }
                if (containAmzPlatform && "ALL".equals(excelDataArea)) {
                    return ResponseData.error(controllerName + "_" +"导入失败，原因：Amazon平台对应区域不能是ALL");
                }
            }

            //可销售物料验证
            List<String> matCodes = dataList.stream().map(SpecialApplyInfoV3Param::getMaterialCode)
                    .collect(Collectors.toList()).stream().distinct().collect(Collectors.toList()); //物料编码集合并去重

            ResponseData responseData = matService.isValidateMater(matCodes, controllerName);
            if (!responseData.getSuccess()) {
                return responseData;
            }

            //物料属性
            List<Material> materialList = (List<Material>) responseData.getData();

            List<String> mergeFields = new ArrayList<>();//组合 去重字段

            Date now = new Date();
            for (SpecialApplyInfoV3Param p : dataList) {

                String billTypeCode = this.getBillTypeCode(p.getBillType());
                if(ObjectUtil.isEmpty(billTypeCode)) {
                    return ResponseData.error(controllerName + "_" +"导入失败，原因：未检测到备货类型:【" + p.getBillType() + "】。");
                }

                if(now.getTime() >= p.getOperExpectedDate().getTime()){
                    return ResponseData.error(controllerName+"导入失败，原因：检测到【运营期望交期】必须大于当前日期");
                }

                p.setBillType(billTypeCode);
                mergeFields.add(p.getPlatform() + p.getArea() + p.getDepartment() + p.getTeam() + p.getMaterialCode() + p.getBillType());
            }

            //验证：平台+区域+事业部+Team+物料编码+备货类型+备货状态 只允许存在一条未提交记录  ;验证失败：返回-重复数据
            List<SpecialApplyInfoV3Result> resultList = service.uploadValidateData(mergeFields);//返回重复的数据
            if (resultList.size() > 0) {
                return ResponseData.error(500, controllerName + "_" +"导入失败，原因：检测出存在未提交的数据。", resultList);
            }

            List<SpecialApplyInfoV3> lists = new ArrayList<>();
            for (SpecialApplyInfoV3Param p : dataList) {
                //根据 物料编码 补充 物料属性
                Material mat = materialList.stream().filter(f -> f.getMaterialCode().equals(p.getMaterialCode()))
                        .collect(Collectors.toList()).stream().findFirst().orElse(null);
                if (ObjectUtil.isEmpty(mat)) {
                    return ResponseData.error(controllerName + "_" +"导入失败，原因：物料编码【" + p.getMaterialCode() + "】匹配到物料信息。");
                }

                SpecialApplyInfoV3 entity = BeanUtil.copyProperties(p, SpecialApplyInfoV3.class);
                entity.setMergeField(entity.getPlatform() + entity.getDepartment() + entity.getTeam() + entity.getMaterialCode() + entity.getBillType());
                entity.setApplyPersonNo(this.getUserAccount());
                entity.setApplyPersonName(this.getUserName());
                entity.setProductName(mat.getProductName());
                entity.setProductType(mat.getProductType());
                entity.setInitType(BigDecimal.valueOf(0));
                entity.setApplyDate(new Date());
                lists.add(entity);
            }

            //完成数据整合，执行导入。
            return service.upload(lists, controllerName);
        } catch (Exception ex) {
            return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, controllerName + "_" +"导入 失败 ! /r/n " + ex.getMessage());
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error(controllerName + "_" +"导入 关闭流异常", e);
                }
            }
        }
    }

    @PostResource(name = controllerName + "_" +"修改", path = "/edit", requiredPermission = false)
    @ApiOperation(value = controllerName + "_" +"修改", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"修改",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody SpecialApplyInfoV3Param param) {
        if (ObjectUtil.isEmpty(param)) {
            return ResponseData.success(controllerName + "_" +"修改失败，原因：获取数据异常，请确认传入数据。");
        }

        try {
            return service.edit(param, controllerName);
        } catch (Exception ex) {
            return ResponseData.error(controllerName + "_" +"修改异常，原因：" + ex.getMessage());
        }
    }

    @PostResource(name = controllerName + "_" +"删除", path = "/delete", requiredPermission = false)
    @ApiOperation(value = controllerName + "_" +"删除", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody List<String> ids) {

        if (ObjectUtil.isEmpty(ids) || ids.size() <= 0) {
            return ResponseData.success(controllerName + "_" +"修改失败，原因：获取数据异常，请确认传入数据。");
        }
        try {
            return service.delete(ids, controllerName);
        } catch (Exception ex) {
            return ResponseData.error(controllerName + "_" +"修改异常，原因：" + ex.getMessage());
        }
    }


    @PostResource(name = controllerName + "_" +"批量提交", path = "/batchSubmit", requiredPermission = false)
    @ApiOperation(value = controllerName + "_" +"批量提交", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"批量提交",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData batchSubmit(@Validated @RequestBody SpecialApplyInfoV3ComitDto param) {

        if (ObjectUtil.isNull(param)) {
            return ResponseData.error("请求参数不能为空");
        }
        if (ObjectUtil.isNull(param.getIgnoreWarn())) {
            param.setIgnoreWarn(1);//默认忽略提示直接提交
        }
        return service.batchSubmit(param);

    }


    /**
     * 每日备货推荐从BI抽数完成后
     * 刷新特殊备货申请单处于计划部审批的 ，关联的BI推荐数据
     *
     * @param
     * @return
     */
    @GetResource(name = "刷新特殊备货申请关联的BI推荐数据", path = "/flashSpecialApplyData", requiredPermission = false, requiredLogin = true)
    @ApiOperation(value = "刷新特殊备货申请关联的BI推荐数据", response = BackPrepareRecomBiResult.class)
    @BusinessLog(title = controllerName + "_" +"刷新特殊备货申请关联的BI推荐数据",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData flashSpecialApplyData() {
        return service.flashSpecialApplyData(null);
    }


    /**
     * 下载-特殊备货导入模板V3
     *
     * @param response
     */
    @ApiOperation("下载-特殊备货导入模板V3")
    @GetResource(name = "下载-特殊备货导入模板V3", path = "/downloadExcel", requiredPermission = false )
    @BusinessLog(title = controllerName + "_" +"下载-特殊备货导入模板V3",opType = LogAnnotionOpTypeEnum.IMPORT)
    public void downloadExcel(HttpServletResponse response) {
        String path = "/template/specialStockImportTemplateV3.xlsx";
        ExcelUtils excelUtils = new ExcelUtils();
        excelUtils.downloadExcel(response, path);
    }

    private String getBillTypeCode(String billType){
        String billTypeCode= null;
        switch (billType) {
            case "紧急备货":
                billTypeCode = "JJBH";
                break;
            case "项目备货":
                billTypeCode = "XMBH";
                break;
//            case "新品备货":
//                billTypeCode = "XPBH";
//                break;
            default:
                break;
        }
        return billTypeCode;
    }
   private String getUserAccount() {
       LoginUser loginUser = LoginContext.me().getLoginUser();
       return loginUser.getAccount();
    }

    private String getUserName() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return loginUser.getName();
    }
}
