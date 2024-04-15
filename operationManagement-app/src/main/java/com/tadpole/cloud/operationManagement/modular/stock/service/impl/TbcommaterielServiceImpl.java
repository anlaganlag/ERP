package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TbComMateriel;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.TbcommaterielMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.TbcommaterielParam;
import com.tadpole.cloud.operationManagement.modular.stock.service.ITbcommaterielService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author gal
 * @since 2022-10-25
 */
@Service
public class TbcommaterielServiceImpl extends ServiceImpl<TbcommaterielMapper, TbComMateriel> implements ITbcommaterielService {

    @Resource
    private TbcommaterielMapper mapper;



    @DataSource(name = "EBMS")
    @Override
    public PageResult<TbComMateriel> listBySpec(TbcommaterielParam param) {
        Page pageContext = getPageContext();
        try {

            IPage<TbComMateriel> page = mapper.listBySpec(pageContext, param);
            return new PageResult<>(page);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return new PageResult<>(pageContext);
        }
    }



    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

}
