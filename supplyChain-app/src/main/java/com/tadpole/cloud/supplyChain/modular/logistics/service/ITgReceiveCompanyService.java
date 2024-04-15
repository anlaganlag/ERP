package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgReceiveCompany;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgReceiveCompanyParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgReceiveCompanyResult;

import java.util.List;

/**
 * <p>
 * 收货公司 服务类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
public interface ITgReceiveCompanyService extends IService<TgReceiveCompany> {

    /**
     * 分页查询列表
     */
    ResponseData queryPage(TgReceiveCompanyParam param);

    /**
     * 新增
     */
    ResponseData add(TgReceiveCompanyParam param);

    /**
     * 删除
     */
    ResponseData delete(TgReceiveCompanyParam param);

    /**
     * 编辑
     */
    ResponseData edit(TgReceiveCompanyParam param);

    /**
     * 获取收货公司
     */
    ResponseData getReceiveCompany();

    /**
     * 获取EBMS收货公司
     */
    List<TgReceiveCompany> getEbmsReceiveCompany();

    /**
     * 导出
     */
    List<TgReceiveCompanyResult> export(TgReceiveCompanyParam param);

    /**
     * 收货公司名称下拉
     */
    List<BaseSelectResult> receiveCompanyNameSelect();

    /**
     * 收货公司下拉
     */
    List<TgReceiveCompany> receiveCompanySelect();
}
