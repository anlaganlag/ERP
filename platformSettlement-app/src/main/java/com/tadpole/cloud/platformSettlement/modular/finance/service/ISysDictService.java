package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.CwSysDict;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SysDictDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 用户字典表 服务类
 * </p>
 *
 * @author gal
 * @since 2021-10-29
 */
public interface ISysDictService extends IService<CwSysDict> {

    List<CwSysDict> findPageBySpec(CwSysDict param);

    List<SysDictDetail> queryDetail(SysDictDetail param);

    ResponseData addSysDictDetail(SysDictDetail param) throws IOException;

    void updateSysDictDetail(SysDictDetail param);

    void deleteSysDictDetail(SysDictDetail param);

    List<SysDictDetail> getByDictCode(CwSysDict param);
}
