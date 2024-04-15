package com.tadpole.cloud.externalSystem.modular.lingxing.service;

import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.advertising.Keywords;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.advertising.ResposeLog;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @author: ty
 * @description: 财务Service类
 * @date: 2022/4/25
 */
public interface KeywordsService extends IService<Keywords> {

    void saveBatch(List<Keywords> entityList);

    void saveLog(ResposeLog log);
}
