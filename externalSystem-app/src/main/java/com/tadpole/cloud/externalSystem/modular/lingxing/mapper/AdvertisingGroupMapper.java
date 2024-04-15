package com.tadpole.cloud.externalSystem.modular.lingxing.mapper;

import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.advertising.AdvertisingGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 销量汇总订单数据 Mapper 接口
 * </p>
 *
 * @author ty
 * @since 2022-05-06
 */
@Mapper
@Repository
public interface AdvertisingGroupMapper extends BaseMapper<AdvertisingGroup> {


}
