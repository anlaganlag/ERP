package com.tadpole.cloud.operationManagement.modular.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.ProdMaterielOprInfo;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.ProdMaterielOprInfoParam;

import java.util.List;

/**
 * <p>
 * 物料运营信息 服务类
 * </p>
 *
 * @author gal
 * @since 2022-10-20
 */
public interface IProdMaterielOprInfoService extends IService<ProdMaterielOprInfo> {

    List<ProdMaterielOprInfo> getMaterielOprInfo(ProdMaterielOprInfoParam param);
}
