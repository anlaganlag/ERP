package com.tadpole.cloud.operationManagement.modular.shopEntity.Provider;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.api.shopEntity.TaxNumApi;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComTaxNum;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComTaxAuditParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComTaxNumParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComTaxNumResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComTaxNumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
public class TaxNumProvider implements TaxNumApi {

    @Resource
    private TbComTaxNumService tbComTaxNumService;



    @Override
    public ResponseData queryById(String id) throws Exception{
        return tbComTaxNumService.queryById(id);
    }

    @Override
    public ResponseData paginQuery(TbComTaxNumParam tbComTaxNumParam) throws Exception{
        Page page = tbComTaxNumParam.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComTaxNumResult> pageResult = tbComTaxNumService.paginQuery(tbComTaxNumParam, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }

    @Override
    public ResponseData updateByShopName(@RequestBody TbComTaxNum TbComTaxNum) throws Exception {
        return tbComTaxNumService.updateByShopName(TbComTaxNum);
    };


    @Override
    public List<TbComTaxNumResult> export(@RequestBody TbComTaxNumParam TbComTaxNumParam){
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComTaxNumResult> pageResult = tbComTaxNumService.paginQuery(TbComTaxNumParam, current,size);
        List<TbComTaxNumResult> records=  pageResult.getRecords();
        return records;

    }

    @Override
    public ResponseData cancelTaxCode(@RequestBody TbComTaxNum tbComTaxNum) throws Exception {
        return ResponseData.success(tbComTaxNumService.cancelTaxCode(tbComTaxNum));
    }


    @Override
    public ResponseData addTaxAudit(@RequestBody TbComTaxAuditParam param) throws Exception {
        return tbComTaxNumService.addTaxAudit(param);
    }
    @Override
    public ResponseData updateTaxNumState(String shopName,String taxnState) {
        return  tbComTaxNumService.updateTaxNumState(shopName, taxnState);
    };


}
