package com.tadpole.cloud.externalSystem.modular.lingxing.mapper;

import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.advertising.Keywords;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.advertising.ResposeLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 销量汇总订单数据 Mapper 接口
 * </p>
 *
 * @author cyt
 * @since 2022-05-17
 */
public interface KeywordsMapper extends BaseMapper<Keywords> {

    void saveLog(@Param("log") ResposeLog log);
}
