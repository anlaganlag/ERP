package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.entity.ProdMateriel;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TbComMateriel;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.ProdMaterielMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.TbcommaterielParam;
import com.tadpole.cloud.operationManagement.modular.stock.service.IProdMaterielService;
import com.tadpole.cloud.operationManagement.modular.stock.service.ITbcommaterielService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* <p>
    * 物料信息 服务实现类
    * </p>
*
* @author gal
* @since 2022-10-20
*/
@Slf4j
@Service
public class ProdMaterielServiceImpl extends ServiceImpl<ProdMaterielMapper, ProdMateriel> implements IProdMaterielService {



    @Autowired
    private ITbcommaterielService tbcommaterielService;




    @DataSource(name = "EBMS")
    @Override
    public PageResult<TbComMateriel> listBySpec(TbcommaterielParam param) {
        return tbcommaterielService.listBySpec(param);
    }
    /**
     * 统一请求ebms接口
     *
     * @param url    接口地址
     * @param param 请求参数
     * @return
     */
}
