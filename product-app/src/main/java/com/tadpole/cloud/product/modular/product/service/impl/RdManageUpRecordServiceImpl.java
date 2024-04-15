package com.tadpole.cloud.product.modular.product.service.impl;

import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.model.objectLog.model.AttributeModel;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.product.modular.product.entity.RdManageUpRecord;
import com.tadpole.cloud.product.modular.product.mapper.RdManageUpRecordMapper;
import com.tadpole.cloud.product.modular.product.model.params.RdManageUpRecordParam;
import com.tadpole.cloud.product.modular.product.model.result.RdManageUpRecordResult;
import com.tadpole.cloud.product.modular.product.service.IRdManageUpRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author S20210221
 * @since 2023-12-08
 */
@Service
public class RdManageUpRecordServiceImpl extends ServiceImpl<RdManageUpRecordMapper, RdManageUpRecord> implements IRdManageUpRecordService {

    @Resource
    private RdManageUpRecordMapper mapper;

    @DataSource(name ="product")
    @Override
    public List<RdManageUpRecordResult> getList(RdManageUpRecordParam param) {

        List<RdManageUpRecordResult> list = this.baseMapper.getList(param);
        for (RdManageUpRecordResult res:list) {
            res.setAttributeModels(JSONUtil.toList(res.getSysModifyContent(), AttributeModel.class));
        }
        return list;
    }
}
