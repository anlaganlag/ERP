package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.ProdMateriel;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TbComMateriel;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.TbcommaterielParam;

/**
 * <p>
 * 物料信息 服务类
 * </p>
 *
 * @author gal
 * @since 2022-10-20
 */
public interface IProdMaterielService extends IService<ProdMateriel> {


    PageResult<TbComMateriel> listBySpec(TbcommaterielParam param);


}
