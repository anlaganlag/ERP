package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.AmazonInventoryEuTransfer;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.AmazonInventoryEuTransferMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IAmazonInventoryEuTransferService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

/**
* <p>
* EBMS转仓清单（欧洲站点调拨) 服务实现类
* </p>
*
* @author S20190161
* @since 2022-12-20
*/
@Service
public class AmazonInventoryEuTransferServiceImpl extends ServiceImpl<AmazonInventoryEuTransferMapper, AmazonInventoryEuTransfer> implements IAmazonInventoryEuTransferService {

    @DataSource(name = "EBMS")
    @Override
    public List<AmazonInventoryEuTransfer> getEbmsEuTransfer(){
        LocalDate today = LocalDate.now();
        //当前日期本月的第一天
        LocalDate firstday = LocalDate.of(today.getYear(),today.getMonth(),1);

        return this.baseMapper.getEbmsEuTransfer(firstday.plusMonths(-1).toString(),firstday.toString());
    }

    @DataSource(name = "warehouse")
    @Override
    public void save(List<AmazonInventoryEuTransfer> list) {
        this.remove(null);
        this.saveBatch(list);
    }
}
