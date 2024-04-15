package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SysBizConfig;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.SysBizConfigMapper;
import com.tadpole.cloud.operationManagement.modular.stock.service.ISysBizConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
* <p>
    * 系统业务配置表 服务实现类
    * </p>
*
* @author lsy
* @since 2022-08-31
*/
@Service
public class SysBizConfigServiceImpl extends ServiceImpl<SysBizConfigMapper, SysBizConfig> implements ISysBizConfigService {

    @Resource
    private SysBizConfigMapper mapper;




    @DataSource(name = "stocking")
    @Override
//    @Transactional
    public boolean updateActionResult(Integer id, String actionResult) {
        LambdaUpdateWrapper<SysBizConfig> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysBizConfig::getId, id)
                .set(SysBizConfig::getActionResult, actionResult)
                .set(SysBizConfig::getFinishTime, new Date())
                .set(SysBizConfig::getUpdateTime, new Date())
                .set(SysBizConfig::getDoActionTime, new Date());
        return  this.update(wrapper);
    }
}
