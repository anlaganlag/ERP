package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.entity.StockParameter;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.StockParameterMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockParameterParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockParameterResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockParameterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* <p>
    * 备货通用参数 服务实现类
    * </p>
*
* @author cyt
* @since 2022-07-19
*/
@Service
public class StockParameterServiceImpl extends ServiceImpl<StockParameterMapper, StockParameter> implements IStockParameterService {

    @Resource
    private StockParameterMapper mapper;

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<StockParameterResult> findPageBySpec(StockParameterParam param) {
        List<StockParameterResult> page = this.baseMapper.customPageList(param);
        return page;
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData update(List<StockParameterParam> params) {
        UpdateWrapper<StockParameter> updateWrapper = new UpdateWrapper<>();
        for (StockParameterParam param : params) {
            updateWrapper
                    .eq("parameter_code", param.getParameterCode())
                    .set("parameter_value", param.getParameterValue());

            this.baseMapper.update(null, updateWrapper);
            updateWrapper.clear();
        }
        return ResponseData.success();


    }

}
