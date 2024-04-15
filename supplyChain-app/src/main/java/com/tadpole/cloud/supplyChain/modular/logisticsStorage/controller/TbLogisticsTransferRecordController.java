package com.tadpole.cloud.supplyChain.modular.logisticsStorage.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsTransferRecord;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsTransferRecordParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.ExportDirectTransfersOrderResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsTransferRecordResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsTransferRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * 物流调拨记录;(tb_logistics_transfer_record)表控制层
 *
 * @author : LSY
 * @date : 2023-12-29
 */
@Slf4j
@Api(tags = "物流调拨记录")
@RestController
@ApiResource(name = "物流调拨记录", path = "/tbLogisticsTransferRecord")
public class TbLogisticsTransferRecordController {
    public final String baseName = "物流调拨记录";
    public final String queryByIdFunName = baseName + "--通过ID查询物流调拨记录";
    public final String paginQueryFunName = baseName + "--分页查询物流调拨记录";
    public final String addFunName = baseName + "--新增物流调拨记录";
    public final String editFunName = baseName + "--更新物流调拨记录";
    public final String exportFunName = baseName + "--按查询条件导出直接调拨单";
    public final String deleteByIdFunName = baseName + "--通过主键删除物流调拨记录据";
    public final String deleteBatchIdsFunName = baseName + "--通过主键批量删除物流调拨记录";
    @Resource
    private TbLogisticsTransferRecordService tbLogisticsTransferRecordService;

    @Autowired
    ResourceLoader resourceLoader;

    /**
     * 通过ID查询单条数据
     *
     * @param sysId 主键
     * @return 实例对象
     */
    @ApiOperation(value = queryByIdFunName, response = TbLogisticsTransferRecord.class)
    @GetResource(name = queryByIdFunName, path = "/queryBysysId" )
    public ResponseData queryById(BigDecimal sysId) {
        return ResponseData.success(tbLogisticsTransferRecordService.queryById(sysId));
    }

    /**
     * 分页查询
     *
     * @param tbLogisticsTransferRecordParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = paginQueryFunName, response = TbLogisticsTransferRecord.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName, opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbLogisticsTransferRecordParam tbLogisticsTransferRecordParm) {
        //1.分页参数
        Page page = tbLogisticsTransferRecordParm.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsTransferRecordResult> pageResult = tbLogisticsTransferRecordService.paginQuery(tbLogisticsTransferRecordParm, current, size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }

    /**
     * 新增数据
     *
     * @param tbLogisticsTransferRecord 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = addFunName, response = TbLogisticsTransferRecord.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName, opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TbLogisticsTransferRecord tbLogisticsTransferRecord) {
        return ResponseData.success(tbLogisticsTransferRecordService.insert(tbLogisticsTransferRecord));
    }

    /**
     * 更新数据
     *
     * @param tbLogisticsTransferRecord 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = editFunName, response = TbLogisticsTransferRecord.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName, opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody TbLogisticsTransferRecordParam tbLogisticsTransferRecord) {
        TbLogisticsTransferRecord result = tbLogisticsTransferRecordService.update(tbLogisticsTransferRecord);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }

    /**
     * 通过主键删除数据
     *
     * @param sysId 主键
     * @return 是否成功
     */
    @ApiOperation(value = deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById" )
    @BusinessLog(title = deleteByIdFunName, opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteById(BigDecimal sysId) {
        if (tbLogisticsTransferRecordService.deleteById(sysId)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }

    /**
     * 批量删除数据
     *
     * @param sysIdList 主键List集合
     * @return 是否成功
     */
    @ApiOperation(value = deleteBatchIdsFunName)
    @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds" )
    @BusinessLog(title = deleteBatchIdsFunName, opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteBatchIds(@RequestBody List<BigDecimal> sysIdList) {
        if (Objects.isNull(sysIdList) || sysIdList.isEmpty()) {
            return ResponseData.error("主键List不能为空");
        }
        return ResponseData.success(tbLogisticsTransferRecordService.deleteBatchIds(sysIdList));
    }

    /**
     * 导出直接调拨单
     *
     * @param packCodeList 出货清单号List
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response = TbLogisticsTransferRecordResult.class)
    @BusinessLog(title = exportFunName, opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody List<String> packCodeList, HttpServletResponse response) throws IOException {
        //1.分页参数

        if (ObjectUtil.isEmpty(packCodeList)) {
            return;
        }
        List<ExportDirectTransfersOrderResult> resultList = tbLogisticsTransferRecordService.export(packCodeList);
        String filename = "直接调拨单.xlsx";
        String modelPath = "classpath:template/直接调拨单.xlsx";//模板所在路径

        org.springframework.core.io.Resource resource = resourceLoader.getResource(modelPath);
        InputStream inputStream = resource.getInputStream();
        ExcelWriter excelWriter = null;

        try {
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String((filename).getBytes(), "iso-8859-1"));
            //填充列表开启自动换行,自动换行表示每次写入一条list数据是都会重新生成一行空行,此选项默认是关闭的,需要提前设置为true
            EasyExcel.write(response.getOutputStream()).withTemplate(inputStream).sheet().doFill(resultList);

            //更新处理状态
            tbLogisticsTransferRecordService.updateStatus(packCodeList);
        } catch (Exception e) {
            log.error("直接调拨单导出异常:{}", JSONUtil.toJsonStr(e));
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error(String.valueOf(e));
            }
        }


    }
}