package com.tadpole.cloud.externalSystem.modular.mabang.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangShopList;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangShopListParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangShopListResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.MabangResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 马帮店铺列表 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-06-09
 */
public interface IMabangShopListService extends IService<MabangShopList> {


    PageResult<MabangShopListResult> list(MabangShopListParam param);
    ResponseData add(MabangResult param);
    ResponseData queryNames();
    ResponseData queryPlatformNames();
    ResponseData queryFinanceCodeList();
    List<MabangShopListResult> exportExcel(MabangShopListParam param);

    List<String> getShopSelect();
    List<String> getSiteSelect();

}
