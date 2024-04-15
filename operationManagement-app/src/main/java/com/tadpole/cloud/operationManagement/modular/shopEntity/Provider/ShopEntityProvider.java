package com.tadpole.cloud.operationManagement.modular.shopEntity.Provider;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.api.shopEntity.ShopEntityApi;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShop;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComTaxNumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
public class ShopEntityProvider implements ShopEntityApi {

    @Resource
    private TbComShopService tbComShopService;

    @Resource
    private TbComTaxNumService tbComTaxNumService;

    @Override
    public ResponseData queryById(String shopName) throws Exception{
        return ResponseData.success(tbComShopService.queryById(shopName));
    }

    @Override
    public ResponseData paginQuery(TbComShopParam tbComShopParam) throws Exception{
        Page page = tbComShopParam.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComShopResult> pageResult = tbComShopService.paginQuery(tbComShopParam, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }

    @Override
    public ResponseData logShopReportQuery(TbComShopParam tbComShopParam) throws Exception{
        Page page = tbComShopParam.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComShopResult> pageResult = tbComShopService.paginQuery(tbComShopParam, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }


    @Override
    public ResponseData edit(@RequestBody TbComShop tbComShop){
        return ResponseData.success(tbComShopService.update(tbComShop));
    };


    @Override
    public List<TbComShopResult> export(@RequestBody TbComShopParam tbComShopParam){
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComShopResult> pageResult = tbComShopService.paginQuery(tbComShopParam, current,size);
        List<TbComShopResult> records=  pageResult.getRecords();
        return records;

    }

    @Override
    public List<String> shopList() {
        return tbComShopService.shopList();
    }

    @Override
    public List<String> siteList() {
        return tbComShopService.siteList();
    }

    @Override
    public List<String> plaformList() {
        return tbComShopService.platformList();
    }

    @Override
    public List<String> shopNameList() {
        return tbComShopService.shopNameList();
    }
}
