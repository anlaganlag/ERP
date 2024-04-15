package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
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
import cn.stylefeng.guns.cloud.system.api.base.model.params.RpMaterialParam;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.operationManagement.modular.stock.consumer.BaseSelectConsumer;
import com.tadpole.cloud.operationManagement.modular.stock.consumer.RpMaterialConsumer;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SeasonParameter;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SeasonParameterParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SeasonParameterResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.ISeasonParameterService;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.SeasonParameterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 季节系数参数表 前端控制器
 * </p>
 *
 * @author cyt
 * @since 2022-07-20
 */

@Slf4j
@RestController
@Api(tags = "备货2.0-季节系数设置")
@ApiResource(name = "备货2.0-季节系数设置", path = "/seasonParameter")
public class SeasonParameterController {

    @Autowired
    private ISeasonParameterService service;

    @Autowired
    private BaseSelectConsumer baseSelectConsumer;

    @Autowired
    private RpMaterialConsumer rpMaterialConsumer;


    private final String controllerName = "备货2.0-季节系数设置";


    /**
     * 备货2.0-安全天数-列表
     */
    @PostResource(name = "备货2.0-季节系数-列表", path = "/list", requiredPermission = false , menuFlag = true)
    @ApiOperation("备货2.0-季节系数-列表")
    @BusinessLog(title = controllerName + "_" +"备货2.0-季节系数-列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(@RequestBody SeasonParameterVO seasonParameterVO) {
        try {
            return ResponseData.success(this.service.queryList(seasonParameterVO)) ;
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }





    /**
     * 季节系数 新增
     */
    @PostResource(name = "备货2.0-季节系数设置-新增", path = "/add", requiredPermission = false )
    @ApiOperation("备货2.0-季节系数设置-新增")
    @BusinessLog(title = controllerName + "_" +"备货2.0-季节系数设置-新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody SeasonParameterParam seasonParameterParam) {
        try {

            return service.add(seasonParameterParam);
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }

    /**
     * 季节系数 更新
     */
    @PostResource(name = "备货2.0-季节系数设置-更新", path = "/update", requiredPermission = false )
    @ApiOperation("备货2.0-季节系数设置-更新")
    @BusinessLog(title = controllerName + "_" +"备货2.0-季节系数设置-更新",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData update(@RequestBody SeasonParameter seasonParameter) {
        if (ObjectUtil.isNull(seasonParameter.getId()) ) {
            return ResponseData.error("传入ID不能为空或者小于0");
        }

        LoginUser loginUser = LoginContext.me().getLoginUser();

        seasonParameter.setUpdateTime(new Date());
        seasonParameter.setUpdateBy(loginUser.getName());
        try {
            return service.update(seasonParameter);
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }

    /**
     * 季节系数 批量删除
     */
    @PostResource(name = "备货2.0-季节系数设置-删除", path = "/deleteBatch", requiredPermission = false )
    @ApiOperation("备货2.0-季节系数设置-删除")
    @BusinessLog(title = controllerName + "_" +"备货2.0-季节系数设置-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteBatch(@RequestBody List<BigDecimal> idList) {

        if (ObjectUtil.isEmpty(idList)) {
            return ResponseData.error("id不能为空");
        }
        try {
          return   service.batchDeleteById(idList);

        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }


    /**
     * @param response
     * @param param
     * @throws IOException
     */
    @PostResource(name = "/季节系数导出", path = "/exportExcel" )
    @ApiOperation("季节系数导出")
    @BusinessLog(title = controllerName + "_" +"季节系数导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportExcel(HttpServletResponse response, @RequestBody SeasonParameterVO param) throws Exception {
        List<SeasonParameterResult> resultList = service.exportExcel(param);
        if (ObjectUtil.isEmpty(resultList)) {
            throw new Exception("季节系数导出数据为空!");
        }
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition",
                "attachment;filename=" + new String("季节系数导出.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), SeasonParameterResult.class).sheet("季节系数导出")
                .doWrite(resultList);
    }


    @GetResource(
            name = "下载模板",
            path = "/downloadExcel",
            requiredPermission = false,
            requiredLogin = false)
    @ApiOperation("下载模板")
    @BusinessLog(title = controllerName + "_" +"下载模板",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void downloadExcel(HttpServletResponse response) {
        String path = "/template/季节系数2.0模板.xlsx";
        ExcelUtils excelUtils = new ExcelUtils();
        excelUtils.downloadExcel(response, path);
    }

    @ParamValidator
    @ApiOperation(value = "导入Excel")
    @PostResource(name = "导入Excel", path = "/upload", requiredPermission = false)
    @BusinessLog(title = controllerName + "_" +"导入Excel",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(
            @RequestParam("file") MultipartFile file) {
        if (file == null) {

            return ResponseData.error("季节系数-导入Excel 失败，上传文件为空!");
        }
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<SeasonParameter>();
            EasyExcel.read(buffer, SeasonParameter.class, listener).sheet().doRead();
            List<SeasonParameter> dataList = listener.getDataList();
            if (CollectionUtil.isEmpty(dataList)) {
                return ResponseData.error("数据为空，无法导入！");
            }
            List<String> platList = baseSelectConsumer.getPlatform();
            List<String> deptList = transformString(baseSelectConsumer.getDepartmentSelect());
            List<String> ptList = transformString(rpMaterialConsumer.getProductTypeSelect(new RpMaterialParam()));
            List<String>  teamList= transformString(baseSelectConsumer.getTeamByDeptSelect(""));
            List<String> brandList = transformString(rpMaterialConsumer.getFitBrandSelect());
            List<String> areaList = baseSelectConsumer.getArea();

            service.verifyData(dataList,platList,teamList,ptList,deptList,brandList,areaList);



            return ResponseData.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.error("上传失败");
        }   finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("季节系数-导入Excel 关闭流异常", e);
                }
            }
        }
    }

    public static List<String> transformString(List<Map<String, Object>> list) {
        List<String> resultList = new ArrayList<>();
        if (CollUtil.isEmpty(list)) {
            return resultList;
        }
        for (Map<String, Object> mp : list) {
            if (mp == null) {
                continue;
            }
            Collection values = mp.values();
            for (Object val : values) {
                resultList.add(val.toString());
            }
        }
        return resultList;
    }


}
