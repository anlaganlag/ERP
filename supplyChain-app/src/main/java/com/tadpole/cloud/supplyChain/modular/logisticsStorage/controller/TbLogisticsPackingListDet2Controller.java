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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackingListDet2;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackingListDet2Param;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackingListDet2Result;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsPackingListDet2Service;
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
 * 出货清单明细2-装箱内容-金蝶+海外仓;(tb_logistics_packing_list_det2)表控制层
 * @author : LSY
 * @date : 2023-12-29
 */
@Api(tags = "出货清单明细2-装箱内容-金蝶+海外仓")
@RestController
@ApiResource(name = "出货清单明细2-装箱内容-金蝶+海外仓", path="/tbLogisticsPackingListDet2")
public class TbLogisticsPackingListDet2Controller{
     public final String baseName = "出货清单明细2-装箱内容-金蝶+海外仓";
     public final String queryByIdFunName = baseName+"--通过ID查询出货清单明细2-装箱内容-金蝶+海外仓";
     public final String paginQueryFunName = baseName+"--分页查询出货清单明细2-装箱内容-金蝶+海外仓";
     public final String addFunName = baseName+"--新增出货清单明细2-装箱内容-金蝶+海外仓";
     public final String editFunName = baseName+"--更新出货清单明细2-装箱内容-金蝶+海外仓";
     public final String exportFunName = baseName+"--按查询条件导出出货清单明细2-装箱内容-金蝶+海外仓";
     public final String deleteByIdFunName = baseName+"--通过主键删除出货清单明细2-装箱内容-金蝶+海外仓据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除出货清单明细2-装箱内容-金蝶+海外仓";
     public final String getTbLogisticsPackingListDet2 = baseName+"--根据出货清单号 查询det2明细数据";
    @Resource
    private TbLogisticsPackingListDet2Service tbLogisticsPackingListDet2Service;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param packDetId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response= TbLogisticsPackingListDet2.class)
    @GetResource(name = queryByIdFunName, path = "/queryBypackDetId" )
    public ResponseData  queryById(BigDecimal packDetId){
        return ResponseData.success(tbLogisticsPackingListDet2Service.queryById(packDetId));
    }
    
    /** 
     * 分页查询
     *
     * @param tbLogisticsPackingListDet2Parm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=TbLogisticsPackingListDet2.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbLogisticsPackingListDet2Param tbLogisticsPackingListDet2Parm ){
        //1.分页参数
       Page page = tbLogisticsPackingListDet2Parm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsPackingListDet2Result> pageResult = tbLogisticsPackingListDet2Service.paginQuery(tbLogisticsPackingListDet2Parm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsPackingListDet2 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbLogisticsPackingListDet2.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody TbLogisticsPackingListDet2 tbLogisticsPackingListDet2){
        return ResponseData.success(tbLogisticsPackingListDet2Service.insert(tbLogisticsPackingListDet2));
    }
    
    /** 
     * 更新数据
     *
     * @param tbLogisticsPackingListDet2 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbLogisticsPackingListDet2.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody TbLogisticsPackingListDet2Param tbLogisticsPackingListDet2){
        TbLogisticsPackingListDet2 result = tbLogisticsPackingListDet2Service.update(tbLogisticsPackingListDet2);
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
         if (tbLogisticsPackingListDet2Service.deleteById(packDetId)) {
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
       return ResponseData.success(tbLogisticsPackingListDet2Service.deleteBatchIds(packDetIdList));
     }
     /**
     * 导出
     *
     * @param tbLogisticsPackingListDet2Parm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbLogisticsPackingListDet2Result.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbLogisticsPackingListDet2Param tbLogisticsPackingListDet2Parm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsPackingListDet2Result> pageResult = tbLogisticsPackingListDet2Service.paginQuery(tbLogisticsPackingListDet2Parm, current,size);
       List<TbLogisticsPackingListDet2Result> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("出货清单明细2-装箱内容-金蝶+海外仓.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbLogisticsPackingListDet2Result.class).sheet("出货清单明细2-装箱内容-金蝶+海外仓").doWrite(records);
        return ResponseData.success();
    }

     /**
      * 根据出货清单号 查询det2明细数据
      *
      * @param packCodeList packCodeList
      * @return 实例对象
      */
     @ApiOperation(value =getTbLogisticsPackingListDet2,response =TbLogisticsPackingListDet2.class)
     @PostResource(name = getTbLogisticsPackingListDet2, path = "/getTbLogisticsPackingListDet2" )
     @BusinessLog(title = getTbLogisticsPackingListDet2,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData  getTbLogisticsPackingListDet2(@RequestBody List<String> packCodeList){
         return ResponseData.success(tbLogisticsPackingListDet2Service.queryByPackCode(packCodeList));
     }
}