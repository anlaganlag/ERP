package com.tadpole.cloud.operationManagement.modular.shopEntity.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.config.EmailTypeEnum;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopApply;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopApplyParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopApplyResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.consumer.ComShopApiConsumer;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 资源-店铺申请;(Tb_Com_Shop_Apply)--控制层
 * @author : LSY
 * @date : 2023-7-28
 */
@Slf4j
@Api(tags = "资源-店铺申请接口")
@RestController
@ApiResource(name="资源-店铺申请" , path="/tbComShopApply")
public class TbComShopApplyController{
     public final String baseName = "资源-店铺申请";
     public final String queryByIdFunName = baseName+"--通过ID查询单条数据";
     public final String getMarketplaceId = baseName+"--通过平台和站点查询marketplaceId";
     public final String paginQueryFunName = baseName+"--分页查询";
     public final String addFunName = baseName+"--新增数据";
     public final String copyFunName = baseName+"--拷贝数据";
     public final String editFunName = baseName+"--更新数据";
     public final String deleteByIdFunName = baseName+"--通过主键删除数据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除数据";
     public final String emailTypeFunName = baseName+"--邮箱类型List";
     public final String exportFunName = baseName+"--按查询条件导出数据";
    @Resource
    private TbComShopApplyService tbComShopApplyService;

    @Resource
    private ComShopApiConsumer comShopApiConsumer;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysApplyId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response =TbComShopApply.class)
    @GetResource(name = queryByIdFunName, path = "/queryById")
    @BusinessLog(title =queryByIdFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryById(BigDecimal sysApplyId){
        return ResponseData.success(tbComShopApplyService.queryById(sysApplyId));
    }

    @ApiOperation(value =getMarketplaceId,response =TbComShopApply.class)
    @GetResource(name = getMarketplaceId, path = "/getMarketplaceId")
    @BusinessLog(title =getMarketplaceId,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getMarketplaceId(String platform, String site){
        return comShopApiConsumer.getMarketplaceIdByPlatformSite(platform, site);
    }




        /**
         * 分页查询
         *
         * @param tbComShopApplyParam 筛选条件
         * @return 查询结果
         */
    @ApiOperation(value =paginQueryFunName ,response =TbComShopApplyResult.class)
    @PostResource(name = paginQueryFunName, path = "/list", requiredLogin = true, menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbComShopApplyParam tbComShopApplyParam){
        //1.分页参数
        Page page = tbComShopApplyParam.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        Page<TbComShopApplyResult> pageResult = tbComShopApplyService.joinPaginQuery(tbComShopApplyParam, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbComShopApply 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbComShopApply.class)
    @PostResource(name = addFunName, path = "/add")
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TbComShopApply tbComShopApply){
        return ResponseData.success(tbComShopApplyService.insert(tbComShopApply));
    }

    /**
     * 拷贝数据
     *
     * @param tbComShopApply 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =copyFunName,response =TbComShopApply.class)
    @PostResource(name = copyFunName, path = "/copy")
    @BusinessLog(title = copyFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData copy(@RequestBody TbComShopApply tbComShopApply){
        return ResponseData.success(tbComShopApplyService.copy(tbComShopApply));
    }



    
    /** 
     * 更新数据
     *
     * @param tbComShopApply 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbComShopApply.class)
    @PostResource(name = editFunName, path = "/update")
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody TbComShopApplyParam tbComShopApply){
        return ResponseData.success(tbComShopApplyService.update(tbComShopApply));
    }
    
    /** 
     * 通过主键删除数据  店铺申请状态在店铺创建还没提交之前都可以删除，提交之后的都不能删除
     *
     * @param sysApplyId 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById" )
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteById(@RequestParam  BigDecimal sysApplyId){
        return ResponseData.success(tbComShopApplyService.deleteById(sysApplyId));
    }
    
    /**
     * 批量删除数据
     *
     * @param  sysApplyIdList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds" )
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<BigDecimal> sysApplyIdList){
      if (Objects.isNull(sysApplyIdList) || sysApplyIdList.size()==0) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbComShopApplyService.deleteBatchIds(sysApplyIdList));
     }
    
    /**
     * 导出
     *
     * @param tbComShopApplyParam 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbComShopApply.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbComShopApplyParam tbComShopApplyParam, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComShopApplyResult> pageResult = tbComShopApplyService.paginQuery(tbComShopApplyParam, current,size);
       List<TbComShopApplyResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("资源-店铺申请.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbComShopApplyResult.class).sheet("资源-店铺申请").doWrite(records);
        return ResponseData.success();
    }


    /**
     * 获取邮箱类型的下拉LIST
     * @return
     */
     @ApiOperation(value =emailTypeFunName)
     @GetResource(name = emailTypeFunName, path = "/emailType" )
     @BusinessLog(title = emailTypeFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData emailType(){
         List<Map<String, Object>> hashMapList = new ArrayList<>();
         Arrays.stream(EmailTypeEnum.values()).forEach(e->{
             Map<String,Object> map = BeanUtil.beanToMap(e,false,false);
             hashMapList.add(map);
         });
         return ResponseData.success(hashMapList);
     }


}