package com.tadpole.cloud.externalSystem.modular.lingxing.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.externalSystem.modular.lingxing.entity.LxMatConfig;

 /**
 * 领星物料配置表;(LX_MAT_CONFIG)表数据库访问层
 * @author : LSY
 * @date : 2022-12-5
 */
@Mapper
public interface LxMatConfigMapper  extends BaseMapper<LxMatConfig>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<LxMatConfig> selectByPage(IPage<LxMatConfig> page , @Param(Constants.WRAPPER) Wrapper<LxMatConfig> wrapper);
}