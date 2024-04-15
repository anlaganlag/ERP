package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.AmazonInventoryList;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.AmazonInventoryListOrg;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.AmazonInventoryListOrgMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IAmazonInventoryListOrgService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author Amte Ma<br>
 * @version 1.0<br>
 * @date 2023/01/03 <br>
 */
@Service
public class AmazonInventoryListOrgServiceImpl extends ServiceImpl<AmazonInventoryListOrgMapper, AmazonInventoryListOrg> implements IAmazonInventoryListOrgService {

    @DataSource(name = "warehouse")
    @Override
    public List<AmazonInventoryListOrg> getListOrg(AmazonInventoryList a){
        QueryWrapper<AmazonInventoryListOrg> wrapper=new QueryWrapper<>();
        wrapper.eq("YEAR",a.getYear())
                .eq("MONTH",a.getMonth())
                .eq("SHOP_NAME",a.getShopName())
                .eq("SITE",a.getSite())
                .eq("PLATFORM",a.getPlatform())
                .eq("WAREHOUSE_NAME",a.getWarehouseName())
                .eq("INVENTORY_STATUS",a.getInventoryStatus())
                .eq("SKU",a.getSku())
                .eq("MATERIAL_CODE",a.getMaterialCode())
                .eq("DEPARTMENT",a.getDepartment())
                .eq("TEAM",a.getTeam());
        return  this.baseMapper.selectList(wrapper);
    }
}
