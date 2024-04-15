package com.tadpole.cloud.product.modular.product.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.modular.product.entity.RdPssManage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.modular.product.model.params.RdPlManageParam;
import com.tadpole.cloud.product.modular.product.model.params.RdPssManageParam;
import com.tadpole.cloud.product.modular.product.model.result.RdPlManageResult;
import com.tadpole.cloud.product.modular.product.model.result.RdPssManageResult;

import java.util.List;

/**
 * <p>
 * 产品同款管理 服务类
 * </p>
 *
 * @author S20210221
 * @since 2023-11-25
 */
public interface IRdPssManageService extends IService<RdPssManage> {

    List<RdPssManageResult> listPage(RdPlManageParam param);

    void changeStatus(String code,String status);

    ResponseData edit(RdPssManageParam param) throws IllegalAccessException;

    void disable(RdPlManageParam param);

    void enable(RdPlManageParam param);

    List<RdPssManageResult> listProName();

    List<RdPssManageResult> listStyle();

    List<RdPssManageResult> listMainMaterial();

    List<RdPssManageResult> listBrand();

    List<RdPssManageResult> listModel();

    ResponseData addOrEditVersion(RdPssManageParam param);

    ResponseData revokePssVersion(RdPssManageParam param);
}
