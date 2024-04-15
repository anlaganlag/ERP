package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.VerifInfoRecord;

import java.util.List;

/**
 * <p>
 * 审核记录信息 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-06-14
 */
public interface IVerifInfoRecordService extends IService<VerifInfoRecord> {


    ResponseData batchPass(List<String> orderIdList);
}
