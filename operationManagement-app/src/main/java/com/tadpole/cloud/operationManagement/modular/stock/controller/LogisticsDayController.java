package com.tadpole.cloud.operationManagement.modular.stock.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.entity.SysDict;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.operationManagement.modular.stock.consumer.BaseSelectConsumer;
import com.tadpole.cloud.operationManagement.modular.stock.consumer.DictServiceConsumer;
import com.tadpole.cloud.operationManagement.modular.stock.entity.LogisticsDay;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.LogisticsDayMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.LogisticsDayParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.LogisticsDayResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.ILogisticsDayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 企业信息控制器
 *
 * @author gal @Date 2021-6-02 22:44:17
 */
@RestController
@ApiResource(name = "物流天数设置2", path = "/LogisticsDay2")
@Api(tags = "物流天数设置2")
@Slf4j
public class LogisticsDayController {

    @Autowired private ILogisticsDayService LogisticsDayService;


    @Autowired
    private DictServiceConsumer dictServiceConsumer;


    @Autowired
    private BaseSelectConsumer baseSelectConsumer;


    @Resource
    private LogisticsDayMapper mapper;

    private final String controllerName = "物流天数设置2";



    /**
     * 分页查询列表
     *
     * @author gal @Date 2021-6-02
     */
    @GetResource(name = "物流天数设置2.0", path = "/list", menuFlag = true)
    @ApiOperation(value = "物流天数设置2.0", response = LogisticsDayResult.class)
    @BusinessLog(title = controllerName + "_" +"物流天数设置2.0",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(LogisticsDayParam param) {
        PageResult<LogisticsDayResult> pageBySpec = LogisticsDayService.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "物流天数待设置", path = "/readySet")
    @ApiOperation(value = "物流天数待设置", response = LogisticsDayResult.class)
    @BusinessLog(title = controllerName + "_" +"物流天数待设置",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData readySet(LogisticsDayParam param) {
        List<SysDict> dictList = this.dictServiceConsumer.getDictListByTypeCode("LOGISTICS_MODE");
        List<String>areaList = baseSelectConsumer.getArea();
        List<LogisticsDayResult> pageBySpec = LogisticsDayService.readySet(areaList,dictList,param);
        return ResponseData.success(pageBySpec);
    }

    /**
     * @param response
     * @param param
     * @throws IOException
     */
    @PostResource(name = "/物流天数导出", path = "/exportExcel" )
    @ApiOperation("物流天数导出")
    @BusinessLog(title = controllerName + "_" +"物流天数导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportExcel(HttpServletResponse response, @RequestBody LogisticsDayParam param) throws Exception {
        List<LogisticsDayResult> resultList = LogisticsDayService.exportExcel(param);
        if (ObjectUtil.isEmpty(resultList)) {
            throw new Exception("物流天数导出数据为空!");
        }
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition",
                "attachment;filename=" + new String("物流天数导出.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), LogisticsDayResult.class).sheet("物流天数导出")
                .doWrite(resultList);
    }

    @ParamValidator
    @ApiOperation(value = "导入Excel")
    @PostResource(name = "导入Excel", path = "/upload", requiredPermission = false)
    @BusinessLog(title = controllerName + "_" +"导入Excel",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(
            @RequestParam("file") MultipartFile file) {
        if (file == null) {
                return ResponseData.error("物流天数-导入Excel 失败，上传文件为空!");
        }
            BufferedInputStream buffer = null;
        try {


            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<LogisticsDay>();
            EasyExcel.read(buffer, LogisticsDay.class, listener).sheet().doRead();
            List<LogisticsDay> dataList = listener.getDataList();
            if (CollectionUtil.isEmpty(dataList)) {
                return ResponseData.error("数据为空，无法导入！");
            }



            LoginContext current = SpringContext.getBean(LoginContext.class);
            LoginUser currentUser = current.getLoginUser();
            String currentOperator = currentUser.getAccount();
            LogisticsDayParam param = new LogisticsDayParam();
            HashSet<LogisticsDay> updateSet = new HashSet<>();
            HashSet<LogisticsDay> insertSet = new HashSet<>();
            List<LogisticsDayResult> existList = LogisticsDayService.exportExcel(param);
            HashSet<String> baseSet = new HashSet<>();
            HashSet<String> logErrSet = new HashSet<>();

            for (LogisticsDay logDay:dataList){
                String base = logDay.getArea() + logDay.getLogisticsMode();
                if (baseSet.contains(base)) {
                    logErrSet.add(base);
                }else{
                    baseSet.add(base);
                }

                logDay.setCreateBy(currentOperator);
                int curPos = 0;
                for (LogisticsDayResult exist: existList) {
                    curPos ++;
                    if (logDay.getArea().equals(exist.getArea()) && logDay.getLogisticsMode().equals(exist.getLogisticsMode())){
                        updateSet.add(logDay);
                        break;
                    }else if (curPos == existList.size()){
                        insertSet.add(logDay);
                    }

                }


            }
            if (CollectionUtil.isNotEmpty(logErrSet)) {
                return ResponseData.error(StrUtil.format("存在重复区域发货方式: {}",logErrSet));
            }

            if (CollectionUtil.isEmpty(existList)) {
                LogisticsDayService.saveOrUpdateBatch(dataList);
            }

            if (CollectionUtil.isNotEmpty(insertSet)) {
                LogisticsDayService.saveOrUpdateBatch(new ArrayList<>(insertSet));
            }

            if (CollectionUtil.isNotEmpty(updateSet)) {
                LogisticsDayService.updateBatch(updateSet);
            }
            return ResponseData.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.error("上传失败");
        }   finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("物流天数-导入Excel 关闭流异常", e);
                }
            }
        }
    }

    /**
     * 新增
     *
     * @author gal @Date 2021-6-02
     */
    @PostResource(name = "新增", path = "/add")
    @ApiOperation("新增")
    @ParamValidator
    @BusinessLog(title = controllerName + "_" +"新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody LogisticsDayParam param) throws ParseException {
        LogisticsDayService.add(param);
        return ResponseData.success();
    }


    /**
     * 新增
     *
     * @author gal @Date 2021-6-02
     */
    @PostResource(name = "批量新增", path = "/addBatch")
    @ApiOperation("批量新增")
    @ParamValidator
    @BusinessLog(title = controllerName + "_" +"批量新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData addBatch(@RequestBody List<LogisticsDayParam> param) throws ParseException {
        return LogisticsDayService.addBatch(param);
    }

    /**
     * 获取公司详情
     *
     * @author gal
     * @date 2019/10/12
     */
    @GetResource(name = "详情", path = "/detail", requiredPermission = false)
    @ApiOperation("详情")
    @BusinessLog(title = controllerName + "_" +"详情",opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@RequestParam String id) {
        return ResponseData.success(this.LogisticsDayService.detail(id));
    }

    /**
     * 修改
     *
     * @author gal @Date 2021-6-02
     */
    @PostResource(name = "修改", path = "/update", requiredPermission = false)
    @ApiOperation("修改")
    @BusinessLog(title = controllerName + "_" +"修改",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData update(@RequestBody LogisticsDayParam param) {
        LogisticsDayService.update(param);
        return ResponseData.success();
    }

    @PostResource(name = "删除", path = "/delete")
    @ApiOperation("删除")
    @BusinessLog(title = controllerName + "_" +"删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody LogisticsDayParam param) {
        LogisticsDayService.delete(param);
        return ResponseData.success();
    }

    @PostResource(name = "批量删除", path = "/deleteBatch", requiredPermission = false)
    @ApiOperation("批量删除")
    @BusinessLog(title = controllerName + "_" +"批量删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteBatch(@RequestBody List<String> list) throws ParseException {
        return LogisticsDayService.deleteBatch(list);
    }

    /**
     * @program: myutil
     * @description: 从本地项目(本地磁盘上)下载静态文件
     * @author: lsy
     * @create: 2020-08-13 16:58
     */
    @GetResource(
            name = "下载模板",
            path = "/downloadExcel",
            requiredPermission = false,
            requiredLogin = false)
    @ApiOperation("下载模板")
    @BusinessLog(title = controllerName + "_" +"下载模板",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void downloadExcel(HttpServletResponse response) {
        String path = "/template/物流天数2.0模板.xlsx";
        ExcelUtils excelUtils = new ExcelUtils();
        excelUtils.downloadExcel(response, path);
    }




}
