package com.tadpole.cloud.supplyChain.modular.logisticsStorage.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsInputPreShareReport;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsInputPreShareReportParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsInputPreShareReportResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsInputPreShareReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

 /**
 * 物流投入预估分担报告-暂时不需要;(tb_logistics_input_pre_share_report)表控制层
 * @author : LSY
 * @date : 2023-12-29
 */
@Api(tags = "物流投入预估分担报告-暂时不需要")
@RestController
@ApiResource(name = "物流投入预估分担报告-暂时不需要", path="/tbLogisticsInputPreShareReport")
public class TbLogisticsInputPreShareReportController{
     public final String baseName = "物流投入预估分担报告-暂时不需要";
     public final String queryByIdFunName = baseName+"--通过ID查询物流投入预估分担报告-暂时不需要";
     public final String paginQueryFunName = baseName+"--分页查询物流投入预估分担报告-暂时不需要";
     public final String addFunName = baseName+"--新增物流投入预估分担报告-暂时不需要";
     public final String editFunName = baseName+"--更新物流投入预估分担报告-暂时不需要";
     public final String exportFunName = baseName+"--按查询条件导出物流投入预估分担报告-暂时不需要";
     public final String deleteByIdFunName = baseName+"--通过主键删除物流投入预估分担报告-暂时不需要据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除物流投入预估分担报告-暂时不需要";
    @Resource
    private TbLogisticsInputPreShareReportService tbLogisticsInputPreShareReportService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkLogisrId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response= TbLogisticsInputPreShareReport.class)
    @GetResource(name = queryByIdFunName, path = "/queryBypkLogisrId" )
    public ResponseData  queryById(BigDecimal pkLogisrId){
        return ResponseData.success(tbLogisticsInputPreShareReportService.queryById(pkLogisrId));
    }
    
    /** 
     * 分页查询
     *
     * @param tbLogisticsInputPreShareReportParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=TbLogisticsInputPreShareReport.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbLogisticsInputPreShareReportParam tbLogisticsInputPreShareReportParm ){
        //1.分页参数
       Page page = tbLogisticsInputPreShareReportParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsInputPreShareReportResult> pageResult = tbLogisticsInputPreShareReportService.paginQuery(tbLogisticsInputPreShareReportParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsInputPreShareReport 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbLogisticsInputPreShareReport.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody TbLogisticsInputPreShareReport tbLogisticsInputPreShareReport){
        return ResponseData.success(tbLogisticsInputPreShareReportService.insert(tbLogisticsInputPreShareReport));
    }
    
    /** 
     * 更新数据
     *
     * @param tbLogisticsInputPreShareReport 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbLogisticsInputPreShareReport.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody TbLogisticsInputPreShareReportParam tbLogisticsInputPreShareReport){
        TbLogisticsInputPreShareReport result = tbLogisticsInputPreShareReportService.update(tbLogisticsInputPreShareReport);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param pkLogisrId 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById" )
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(BigDecimal pkLogisrId){
         if (tbLogisticsInputPreShareReportService.deleteById(pkLogisrId)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }
     /**
     * 批量删除数据
     *
     * @param  pkLogisrIdList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds" )
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<BigDecimal> pkLogisrIdList){
      if (Objects.isNull(pkLogisrIdList) || pkLogisrIdList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbLogisticsInputPreShareReportService.deleteBatchIds(pkLogisrIdList));
     }
     /**
     * 导出
     *
     * @param tbLogisticsInputPreShareReportParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbLogisticsInputPreShareReportResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbLogisticsInputPreShareReportParam tbLogisticsInputPreShareReportParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsInputPreShareReportResult> pageResult = tbLogisticsInputPreShareReportService.paginQuery(tbLogisticsInputPreShareReportParm, current,size);
       List<TbLogisticsInputPreShareReportResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("物流投入预估分担报告-暂时不需要.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbLogisticsInputPreShareReportResult.class).sheet("物流投入预估分担报告-暂时不需要").doWrite(records);
        return ResponseData.success();
    }
}