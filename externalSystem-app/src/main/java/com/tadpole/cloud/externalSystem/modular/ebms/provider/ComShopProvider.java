package com.tadpole.cloud.externalSystem.modular.ebms.provider;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tadpole.cloud.externalSystem.api.ebms.ComShopApi;
import com.tadpole.cloud.externalSystem.api.ebms.entity.TbDwSourceData;
import com.tadpole.cloud.externalSystem.api.ebms.model.param.EbmsShopDataDownTaskParam;
import com.tadpole.cloud.externalSystem.modular.ebms.service.TbDwSourceDataService;
import com.tadpole.cloud.externalSystem.modular.ebms.service.TbDwTaskAutoCreateService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ComShopProvider implements ComShopApi {
    @Resource
    private TbDwSourceDataService sourceDataService;
    @Resource
    private TbDwTaskAutoCreateService taskAutoCreateService;

    @DataSource(name="EBMS")
    @Override
    public List<TbDwSourceData> getSourceDataObjList() {
        LambdaQueryWrapper<TbDwSourceData> tWrapper = new LambdaQueryWrapper<>();
        tWrapper.eq(TbDwSourceData::getStatus, 1);
        List<TbDwSourceData> sourceDataList = sourceDataService.getBaseMapper().selectList(tWrapper);
        return sourceDataList;
    }

    @DataSource(name="EBMS")
    @Override
    public ResponseData syncShopDwDataTask(EbmsShopDataDownTaskParam dataDownParam) {
        return taskAutoCreateService.syncShopDwDataTask(dataDownParam);

    }


    @DataSource(name="EBMS")
    @Override
    public ResponseData getMarketplaceIdByPlatformSite(String platform, String site) {
        return taskAutoCreateService.getMarketplaceIdByPlatformSite(platform,site);

    }



}
