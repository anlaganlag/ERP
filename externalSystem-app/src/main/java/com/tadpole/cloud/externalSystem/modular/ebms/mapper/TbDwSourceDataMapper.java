package com.tadpole.cloud.externalSystem.modular.ebms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.externalSystem.modular.ebms.model.result.TbDwSourceDataResult;
import com.tadpole.cloud.externalSystem.api.ebms.entity.TbDwSourceData;

 /**
 * TbDWSourceData;(Tb_DW_Source_Data)--数据库访问层
 * @author : LSY
 * @date : 2023-8-14
 */
@Mapper
public interface TbDwSourceDataMapper  extends BaseMapper<TbDwSourceData>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbDwSourceDataResult> selectByPage(IPage<TbDwSourceDataResult> page , @Param(Constants.WRAPPER) Wrapper<TbDwSourceData> wrapper);
}