package com.tadpole.cloud.operationManagement.modular.shopEntity.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComTaxNumberResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComTaxNumber;

 /**
 * 资源-税号管理;(Tb_Com_Tax_Number)--数据库访问层
 * @author : LSY
 * @date : 2023-7-28
 */
@Mapper
public interface TbComTaxNumberMapper  extends BaseMapper<TbComTaxNumber>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbComTaxNumberResult> selectByPage(IPage<TbComTaxNumberResult> page , @Param(Constants.WRAPPER) Wrapper<TbComTaxNumber> wrapper);
}