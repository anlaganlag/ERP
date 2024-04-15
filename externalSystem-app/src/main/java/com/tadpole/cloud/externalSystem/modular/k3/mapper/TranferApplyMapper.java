package com.tadpole.cloud.externalSystem.modular.k3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @description:
 * @author: Amte Ma
 * @version: 1.0
 * @date: 2023/10/20 <br>
 */
public interface TranferApplyMapper extends BaseMapper {
    String getBscId(@Param("orgName") String orgName, @Param("sku")String sku);

}
