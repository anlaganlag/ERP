package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.CwSysDict;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SysDictDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* <p>
* 用户字典表 Mapper 接口
* </p>
*
* @author gal
* @since 2021-10-29
*/
public interface SysDictMapper extends BaseMapper<CwSysDict> {

    List<SysDictDetail> queryDetail(@Param("paramCondition") SysDictDetail param);

    void add(@Param("paramCondition") SysDictDetail param);

    void delete(@Param("paramCondition") SysDictDetail param);

    List<SysDictDetail> getByDictCode(@Param("paramCondition") CwSysDict param);
}
