package com.tadpole.cloud.operationManagement.modular.shopEntity.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntity;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityService;
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

 /**
 * 资源-公司实体;(Tb_Com_Entity)--控制层
 * @author : LSY
 * @date : 2023-7-28
 */
@Slf4j
@Api(tags = "资源-公司实体接口")
@RestController
@ApiResource(name="资源-公司实体" , path="/tbComEntity")
public class TbComEntityController{
     public final String baseName = "资源-公司实体";
     public final String queryByIdFunName = baseName+"--通过ID查询单条数据";
     public final String paginQueryFunName = baseName+"--分页查询";
     public final String comEntitySelect = baseName+"--公司实体下拉";
     public final String addFunName = baseName+"--新增数据";
     public final String editFunName = baseName+"--更新数据";
     public final String deleteByIdFunName = baseName+"--通过主键删除数据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除数据";
     public final String exportFunName = baseName+"--按查询条件导出数据";
     public final String comNameCnList = baseName+"--公司中文名List下拉接口";
    @Resource
    private TbComEntityService tbComEntityService;

    /**
     * 通过ID查询单条数据
     *
     * @param comNameCn 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response =TbComEntityResult.class)
    @GetResource(name = queryByIdFunName, path = "/queryById")
    @BusinessLog(title =queryByIdFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryById(String comNameCn){
        return ResponseData.success(tbComEntityService.queryById(comNameCn));
    }

    /**
     * 分页查询
     *
     * @param tbComEntityParam 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response =TbComEntityResult.class)
    @PostResource(name = paginQueryFunName, path = "/list", requiredLogin = true, menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbComEntityParam tbComEntityParam){
        //1.分页参数
        Page page = tbComEntityParam.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComEntityResult> pageResult = tbComEntityService.paginQueryJoinTable(tbComEntityParam  ,current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }

    /**
     * 新增数据
     *
     * @param entityParam 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbComEntity.class)
    @PostResource(name = addFunName, path = "/add")
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TbComEntityParam entityParam){
        return tbComEntityService.insert(entityParam);
    }

    /**
     * 更新数据
     *
     * @param tbComEntityParam 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbComEntity.class)
    @PostResource(name = editFunName, path = "/update")
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody TbComEntityParam tbComEntityParam){
        return tbComEntityService.update(tbComEntityParam);
    }

    /**
     * 通过主键删除数据
     *
     * @param comNameCn 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById")
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteById(@RequestParam  String comNameCn){
        return ResponseData.success(tbComEntityService.deleteById(comNameCn));
    }

    /**
     * 批量删除数据
     *
     * @param  comNameCnList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds")
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<String> comNameCnList){
      if (Objects.isNull(comNameCnList) || comNameCnList.size()==0) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbComEntityService.deleteBatchIds(comNameCnList));
     }

    /**
     * 导出
     *
     * @param tbComEntityParam 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbComEntity.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbComEntityParam tbComEntityParam, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComEntityResult> pageResult = tbComEntityService.paginQuery(tbComEntityParam, current,size);
       List<TbComEntityResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("资源-公司实体.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbComEntityResult.class).sheet("资源-公司实体").doWrite(records);
        return ResponseData.success();
    }


     /**
      * 公司名称中文下拉
      *
      * @return 公司名称中文List
      */
     @ApiOperation(value =comNameCnList ,response =TbComEntityResult.class)
     @GetResource(name = comNameCnList, path = "/comNameCnList", menuFlag = true)
     @BusinessLog(title = comNameCnList,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData comNameCnList(@RequestParam(required = false,defaultValue = "true") Boolean isNormal){
             List<String> comNameCnList = tbComEntityService.comNameCnList(isNormal);
         return ResponseData.success(comNameCnList);
     }


     /**
      * 公司名称中文下拉
      *
      * @return 公司名称中文List
      */
     @ApiOperation(value =comNameCnList ,response = TbComShopResult.class)
     @GetResource(name = comNameCnList, path = "/getComShopList", menuFlag = true)
     @BusinessLog(title = comNameCnList,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData getComShopList(){
         return ResponseData.success(tbComEntityService.comNameCnList());
     }



}
