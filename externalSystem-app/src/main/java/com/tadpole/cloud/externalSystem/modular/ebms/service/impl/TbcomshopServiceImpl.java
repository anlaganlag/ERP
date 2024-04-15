package com.tadpole.cloud.externalSystem.modular.ebms.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.api.ebms.model.param.TbcomshopParam;
import com.tadpole.cloud.externalSystem.api.ebms.model.result.TbcomshopResult;
import com.tadpole.cloud.externalSystem.modular.ebms.entity.Tbcomshop;
import com.tadpole.cloud.externalSystem.modular.ebms.mapper.TbcomshopMapper;
import com.tadpole.cloud.externalSystem.modular.ebms.service.ITbcomshopService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author S20190109
 * @since 2023-05-15
 */
@Service
public class TbcomshopServiceImpl extends ServiceImpl<TbcomshopMapper, Tbcomshop> implements ITbcomshopService {

    @Resource
    private TbcomshopMapper mapper;

    @DataSource(name="EBMS")
    @Override
    public List<TbcomshopResult> getShopList(TbcomshopParam param) {
        return this.baseMapper.getShopList(param);
    }

    @DataSource(name="EBMS")
    @Override
    public List<String> getPlatformList() {
        List<String> returnList = new ArrayList<>();
        QueryWrapper<Tbcomshop> shopWrapper = new QueryWrapper<>();
        shopWrapper.select("elePlatformName").groupBy("elePlatformName").orderByAsc("elePlatformName");
        List<Tbcomshop> platformList = this.list(shopWrapper);
        for (Tbcomshop shop : platformList) {
            returnList.add(shop.getEleplatformname());
        }
        return returnList;
    }
    @DataSource(name="EBMS")
    @Override
    public List<Object> getAllShopNameList() {
        List<String> returnList = new ArrayList<>();
        QueryWrapper<Tbcomshop> query = new QueryWrapper<>();
        query.select("elePlatformName,shopName");
        List<Object> objects = this.baseMapper.selectObjs(query);
        return objects;

    }
}
