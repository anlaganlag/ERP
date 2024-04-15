package com.tadpole.cloud.operationManagement.modular.shopEntity.Provider;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.api.shopEntity.LogShopReportApi;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopLogisticsReportResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComTaxNumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
public class TbComShopReportProvider implements LogShopReportApi {

    @Resource
    private TbComShopService tbComShopService;

    @Resource
    private TbComTaxNumService tbComTaxNumService;



    @Override
    public ResponseData logShopReportQuery(TbComShopParam tbComShopParam) throws Exception{
        Page page = tbComShopParam.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComShopLogisticsReportResult> pageResult = tbComShopService.TbComShopLogisticsReportResult(tbComShopParam, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }


    @Override
    public List<TbComShopLogisticsReportResult> export(TbComShopParam tbComShopParam) {
        long current = 1L;
        long size = Integer.MAX_VALUE;
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComShopLogisticsReportResult> pageResult = tbComShopService.TbComShopLogisticsReportResult(tbComShopParam, current, size);
        List<TbComShopLogisticsReportResult> records=  pageResult.getRecords();
        return records;
    }





}
