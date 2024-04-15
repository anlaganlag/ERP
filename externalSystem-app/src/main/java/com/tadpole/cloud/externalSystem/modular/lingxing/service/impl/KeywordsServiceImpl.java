package com.tadpole.cloud.externalSystem.modular.lingxing.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.modular.lingxing.mapper.*;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.advertising.*;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.KeywordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * @author: ty
 * @description: 财务Service实现类
 * @date: 2022/4/25
 */
@Slf4j
@Service
public class KeywordsServiceImpl  extends ServiceImpl<KeywordsMapper, Keywords> implements KeywordsService {


    @DataSource(name = "BIDW")
    @Override
    public void saveBatch(List<Keywords> entityList) {
         this.saveBatch(entityList,500);
    }

    @DataSource(name = "BIDW")
    @Override
    public void saveLog(ResposeLog log) {
        this.baseMapper.saveLog(log);
    }

}
