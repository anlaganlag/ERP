package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgReceiveCompany;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgReceiveCompanyParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgReceiveCompanyResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 收货公司 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
public interface TgReceiveCompanyMapper extends BaseMapper<TgReceiveCompany> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<TgReceiveCompanyResult> queryPage(@Param("page") Page page, @Param("param") TgReceiveCompanyParam param);

    /**
     * 获取收货公司
     * @return
     */
    List<TgReceiveCompany> getEbmsReceiveCompany();

    /**
     * 收货公司名称下拉
     * @return
     */
    List<BaseSelectResult> receiveCompanyNameSelect();
}
