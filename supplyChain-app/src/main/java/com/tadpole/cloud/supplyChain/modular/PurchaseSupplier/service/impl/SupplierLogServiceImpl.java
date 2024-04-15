package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.service.impl;

import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.model.objectLog.model.AttributeModel;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.google.api.client.json.Json;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity.SupplierLog;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.mapper.SupplierLogMapper;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.params.SupplierLogParam;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.result.SupplierLogResult;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.service.ISupplierLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 供应商-日志 服务实现类
 * </p>
 *
 * @author S20190109
 * @since 2023-11-14
 */
@Service
public class SupplierLogServiceImpl extends ServiceImpl<SupplierLogMapper, SupplierLog> implements ISupplierLogService {

    @Resource
    private SupplierLogMapper mapper;

    @DataSource(name ="product")
    @Override
    public List<SupplierLogResult> listPage(SupplierLogParam param) {

        List<SupplierLogResult> list = this.baseMapper.listPage(param);
        for (SupplierLogResult res:list) {
            res.setAttributeModels(JSONUtil.toList(res.getSysUpdateContent(), AttributeModel.class));
        }

        return list;
    }
}
