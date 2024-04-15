package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TbComMateriel;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.TbcommaterielParam;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gal
 * @since 2022-10-25
 */
@Service
public interface ITbcommaterielService extends IService<TbComMateriel> {


    PageResult<TbComMateriel> listBySpec(TbcommaterielParam param);

}
