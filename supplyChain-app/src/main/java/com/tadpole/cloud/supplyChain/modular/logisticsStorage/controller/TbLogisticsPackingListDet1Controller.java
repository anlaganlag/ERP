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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackingListDet1;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackingListDet1Param;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackingListDet1Result;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsPackingListDet1Service;
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
 * 出货清单明细1-箱子长宽高重-金蝶+海外仓;(tb_logistics_packing_list_det1)表控制层
 * @author : LSY
 * @date : 2023-12-29
 */
@Api(tags = "出货清单明细1-箱子长宽高重-金蝶+海外仓")
@RestController
@ApiResource(name = "出货清单明细1-箱子长宽高重-金蝶+海外仓", path="/tbLogisticsPackingListDet1")
public class TbLogisticsPackingListDet1Controller{
     public final String baseName = "出货清单明细1-箱子长宽高重-金蝶+海外仓";
     public final String queryByIdFunName = baseName+"--通过ID查询出货清单明细1-箱子长宽高重-金蝶+海外仓";
     public final String paginQueryFunName = baseName+"--分页查询出货清单明细1-箱子长宽高重-金蝶+海外仓";
     public final String addFunName = baseName+"--新增出货清单明细1-箱子长宽高重-金蝶+海外仓";
     public final String editFunName = baseName+"--更新出货清单明细1-箱子长宽高重-金蝶+海外仓";
     public final String exportFunName = baseName+"--按查询条件导出出货清单明细1-箱子长宽高重-金蝶+海外仓";
     public final String deleteByIdFunName = baseName+"--通过主键删除出货清单明细1-箱子长宽高重-金蝶+海外仓据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除出货清单明细1-箱子长宽高重-金蝶+海外仓";
     public final String getByPackCode = baseName+"--通过出货清单号查询明细数据1";
    @Resource
    private TbLogisticsPackingListDet1Service tbLogisticsPackingListDet1Service;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param packDetId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response= TbLogisticsPackingListDet1.class)
    @GetResource(name = queryByIdFunName, path = "/queryBypackDetId" )
    public ResponseData  queryById(BigDecimal packDetId){
        return ResponseData.success(tbLogisticsPackingListDet1Service.queryById(packDetId));
    }
    
    /** 
     * 分页查询
     *
     * @param tbLogisticsPackingListDet1Parm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=TbLogisticsPackingListDet1.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbLogisticsPackingListDet1Param tbLogisticsPackingListDet1Parm ){
        //1.分页参数
       Page page = tbLogisticsPackingListDet1Parm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsPackingListDet1Result> pageResult = tbLogisticsPackingListDet1Service.paginQuery(tbLogisticsPackingListDet1Parm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsPackingListDet1 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbLogisticsPackingListDet1.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody TbLogisticsPackingListDet1 tbLogisticsPackingListDet1){
        return ResponseData.success(tbLogisticsPackingListDet1Service.insert(tbLogisticsPackingListDet1));
    }
    
    /** 
     * 更新数据
     *
     * @param tbLogisticsPackingListDet1 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbLogisticsPackingListDet1.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody TbLogisticsPackingListDet1Param tbLogisticsPackingListDet1){
        TbLogisticsPackingListDet1 result = tbLogisticsPackingListDet1Service.update(tbLogisticsPackingListDet1);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param packDetId 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById" )
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(BigDecimal packDetId){
         if (tbLogisticsPackingListDet1Service.deleteById(packDetId)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }
     /**
     * 批量删除数据
     *
     * @param  packDetIdList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds" )
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<BigDecimal> packDetIdList){
      if (Objects.isNull(packDetIdList) || packDetIdList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbLogisticsPackingListDet1Service.deleteBatchIds(packDetIdList));
     }
     /**
     * 导出
     *
     * @param tbLogisticsPackingListDet1Parm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbLogisticsPackingListDet1Result.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbLogisticsPackingListDet1Param tbLogisticsPackingListDet1Parm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsPackingListDet1Result> pageResult = tbLogisticsPackingListDet1Service.paginQuery(tbLogisticsPackingListDet1Parm, current,size);
       List<TbLogisticsPackingListDet1Result> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("出货清单明细1-箱子长宽高重-金蝶+海外仓.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbLogisticsPackingListDet1Result.class).sheet("出货清单明细1-箱子长宽高重-金蝶+海外仓").doWrite(records);
        return ResponseData.success();
    }

     /**
      * 通过出货清单号查询 明细1的数据
      *
      * @param packCodeList 出货清单号集合
      * @return 实例对象
      */
     @ApiOperation(value =getByPackCode,response= TbLogisticsPackingListDet1.class)
     @PostResource(name = getByPackCode, path = "/getByPackCode" )
     @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData  getByPackCode(@RequestBody List<String> packCodeList){
         return ResponseData.success(tbLogisticsPackingListDet1Service.getByPackCode(packCodeList));
     }
}