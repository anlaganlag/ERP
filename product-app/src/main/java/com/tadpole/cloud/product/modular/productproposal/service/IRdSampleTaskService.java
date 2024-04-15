package com.tadpole.cloud.product.modular.productproposal.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productproposal.entity.RdSampleTask;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.api.productproposal.model.params.*;
import com.tadpole.cloud.product.api.productproposal.model.result.*;

import java.util.List;

/**
 * <p>
 * 提案-开发样任务 服务类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
public interface IRdSampleTaskService extends IService<RdSampleTask> {

    ResponseData addOrEdit(RdSampleTaskParam param);

    RdSampleTaskResult waitPublic(RdSampleTaskParam param);

    ResponseData checkIsCanCreate(RdSampleTaskParam param);

    ResponseData close(RdSampleTaskParam param);

    RdSampleTaskExtentResult detail(RdSampleTaskParam param);

    List<RdSampleTaskExtentResult> listPage(RdSampleTaskParam param);

    ResponseData closeBySysAuto(RdSampleTaskParam param);

    ResponseData addSample(List<RdSampleManageParam> params);

    ResponseData changeStatus(RdSampleTaskParam param);

    ResponseData getIsTsRead(String sysTsTaskCode);
}
