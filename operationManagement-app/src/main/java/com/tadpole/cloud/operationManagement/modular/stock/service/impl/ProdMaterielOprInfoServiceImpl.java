package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.entity.ProdMaterielOprInfo;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.ProdMaterielOprInfoMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.ProdMaterielOprInfoParam;
import com.tadpole.cloud.operationManagement.modular.stock.service.IProdMaterielOprInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* <p>
    * 物料运营信息 服务实现类
    * </p>
*
* @author gal
* @since 2022-10-20
*/
@Service
public class ProdMaterielOprInfoServiceImpl extends ServiceImpl<ProdMaterielOprInfoMapper, ProdMaterielOprInfo> implements IProdMaterielOprInfoService {

    @Autowired
    private ProdMaterielOprInfoMapper mapper;


    @DataSource(name = "product")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ProdMaterielOprInfo> getMaterielOprInfo(ProdMaterielOprInfoParam param) {

        QueryWrapper<ProdMaterielOprInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("MAT_CODE",param.getMatCode());

        List<ProdMaterielOprInfo> result = this.mapper.selectList(queryWrapper);
        return result;
    }

}
