package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.AmazonInventoryK3Transfer;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.AmazonInventoryK3TransferMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IAmazonInventoryK3TransferService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

/**
* <p>
* K3跨组织直接调拨单 服务实现类
* </p>
*
* @author S20190161
* @since 2022-12-20
*/
@Service
public class AmazonInventoryK3TransferServiceImpl extends ServiceImpl<AmazonInventoryK3TransferMapper, AmazonInventoryK3Transfer> implements IAmazonInventoryK3TransferService {

    @DataSource(name = "k3cloud")
    @Override
    public List<AmazonInventoryK3Transfer> getErpEuTransfer(){

        LocalDate today = LocalDate.now();
        //当前日期本月的第一天
        LocalDate firstday = LocalDate.of(today.getYear(),today.getMonth(),1);
        return this.baseMapper.getK3Transfer(firstday.plusMonths(-1).toString(),firstday.toString());
    }
    @DataSource(name = "warehouse")
    @Override
    public void save( List<AmazonInventoryK3Transfer> list){

        this.remove(null);
        this.saveBatch(list);
    }
}
