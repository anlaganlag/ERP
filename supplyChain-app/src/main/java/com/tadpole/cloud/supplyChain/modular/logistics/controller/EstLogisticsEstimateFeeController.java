package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import java.util.List;
import javax.annotation.Resource;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.tadpole.cloud.platformSettlement.api.finance.entity.PersonAlloc;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.EstLogisticsEstimateFeeDetMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.model.params.EstLogisticsEstimateFeeDetParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tadpole.cloud.supplyChain.modular.logistics.entity.EstLogisticsEstimateFee;
import com.tadpole.cloud.supplyChain.modular.logistics.service.EstLogisticsEstimateFeeService;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.model.params.EstLogisticsEstimateFeeParam;
import com.tadpole.cloud.supplyChain.modular.logistics.model.result.EstLogisticsEstimateFeeResult;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.EstLogisticsEstimateFeeMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.EstLogisticsEstimateFeeService;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import org.springframework.web.bind.annotation.RequestBody;
import com.alibaba.excel.EasyExcel;
import cn.hutool.core.util.ObjectUtil;

 /**
 * 物流费用测算;(EST_LOGISTICS_ESTIMATE_FEE)表控制层
 * @author : LSY
 * @date : 2024-3-14
 */
@Api(tags = "物流费用测算")
@RestController
@ApiResource(name = "物流费用测算", path="/estLogisticsEstimateFee")
public class EstLogisticsEstimateFeeController{
     public final String baseName = "物流费用测算";
     public final String queryByIdFunName = baseName+"--通过ID查询物流费用测算";
     public final String paginQueryFunName = baseName+"--分页查询物流费用测算";
     public final String paginQueryFunDetName = baseName+"--分页查询物流费用测算含明细";
     public final String addFunName = baseName+"--新增物流费用测算";
     public final String editFunName = baseName+"--更新物流费用测算";
     public final String exportFunName = baseName+"--按查询条件导出物流费用测算";
     public final String deleteByIdFunName = baseName+"--通过主键删除物流费用测算据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除物流费用测算";
    @Resource
    private EstLogisticsEstimateFeeService estLogisticsEstimateFeeService;

     @Resource
     private EstLogisticsEstimateFeeDetMapper estLogisticsEstimateFeeDetMapper;

    
    /** 
     * 通过ID查询单条数据 
     *
     * @param estId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response=EstLogisticsEstimateFee.class)
    @GetResource(name = queryByIdFunName, path = "/queryByestId", requiredLogin = false)
    public ResponseData  queryById(String estId){
        return ResponseData.success(estLogisticsEstimateFeeService.queryById(estId));
    }
    
    /** 
     * 分页查询
     *
     * @param estLogisticsEstimateFeeParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=EstLogisticsEstimateFee.class)
    @PostResource(name = paginQueryFunName, path = "/queryList", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody EstLogisticsEstimateFeeParam estLogisticsEstimateFeeParm ){
        //1.分页参数
       Page page = estLogisticsEstimateFeeParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<EstLogisticsEstimateFeeResult> pageResult = estLogisticsEstimateFeeService.paginQuery(estLogisticsEstimateFeeParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
     @ApiOperation(value =paginQueryFunDetName,response = EstLogisticsEstimateFee.class)
     @PostResource(name = paginQueryFunDetName, path = "/list", requiredLogin = false)
     @BusinessLog(title =paginQueryFunDetName,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData list(@RequestBody EstLogisticsEstimateFeeDetParam param){
         return ResponseData.success(estLogisticsEstimateFeeService.queryList(param));
     }
    
    /** 
     * 新增数据
     *
     * @param estLogisticsEstimateFee 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =EstLogisticsEstimateFee.class)
    @PostResource(name = addFunName, path = "/add",  requiredLogin = false)
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody EstLogisticsEstimateFee estLogisticsEstimateFee){
        return ResponseData.success(estLogisticsEstimateFeeService.insert(estLogisticsEstimateFee));
    }
    
    /** 
     * 更新数据
     *
     * @param estLogisticsEstimateFee 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =EstLogisticsEstimateFee.class)
    @PostResource(name = editFunName, path = "/update",  requiredLogin = false)
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody EstLogisticsEstimateFeeParam estLogisticsEstimateFee){
        EstLogisticsEstimateFee result = estLogisticsEstimateFeeService.update(estLogisticsEstimateFee);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param estId 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById0", requiredLogin = false)
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(String estId){
         if (estLogisticsEstimateFeeService.deleteById(estId)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }


     @ApiOperation(value ="级联删除费用测算")
     @GetResource(name = "级联删除费用测算", path = "/deleteById", requiredLogin = false)
     @BusinessLog(title = "级联删除费用测算",opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData  deleteByCascade(String estId){
         return estLogisticsEstimateFeeService.deleteByCascade(estId);
     }
     /**
     * 批量删除数据
     *
     * @param  estIdList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds", requiredLogin = false)
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<String> estIdList){
      if (Objects.isNull(estIdList) || estIdList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(estLogisticsEstimateFeeService.deleteBatchIds(estIdList));
     }
     /**
     * 导出
     *
     * @param estLogisticsEstimateFeeParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export",   requiredLogin = false)
    @ApiOperation(value = exportFunName, response =EstLogisticsEstimateFeeResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody EstLogisticsEstimateFeeParam estLogisticsEstimateFeeParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<EstLogisticsEstimateFeeResult> pageResult = estLogisticsEstimateFeeService.paginQuery(estLogisticsEstimateFeeParm, current,size);
       List<EstLogisticsEstimateFeeResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        String fileName = "FBA头程费用测算"+ DateUtil.format(DateUtil.date(), "yyyyMMdd") +".xlsx";
        response.addHeader("Content-Disposition", "attachment;filename=" + new String((fileName+".xlsx").getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), EstLogisticsEstimateFeeResult.class).sheet(fileName).doWrite(records);
        return ResponseData.success();
    }


     @ApiOperation(value ="getLastFill")
     @GetResource(name = "getLastFill", path = "/getLastFill", requiredLogin = false)
     @BusinessLog(title = "getLastFill")
     public ResponseData getLastFill(){
         return ResponseData.success(estLogisticsEstimateFeeService.getLastFill());
     }
}