package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsLink;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsLinkResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 物流信息查询;(tb_logistics_link)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsLinkMapper  extends BaseMapper<TbLogisticsLink>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsLinkResult> selectByPage(IPage<TbLogisticsLinkResult> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsLink> wrapper);

    @Select("select * from  tb_logistics_link where log_Tra_Mode1= #{logTraMode1}")
    List<TbLogisticsLink> findByLogTraMode1(String logTraMode1);
}