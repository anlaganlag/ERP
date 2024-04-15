package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbEPlatStorageCenter;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbEPlatStorageCenterResult;

 /**
 * 电商平台仓储中心;(Tb_E_Plat_Storage_Center)表数据库访问层
 * @author : LSY
 * @date : 2024-1-2
 */
@Mapper
public interface TbEPlatStorageCenterMapper  extends BaseMapper<TbEPlatStorageCenter>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbEPlatStorageCenterResult> selectByPage(IPage<TbEPlatStorageCenterResult> page , @Param(Constants.WRAPPER) Wrapper<TbEPlatStorageCenter> wrapper);
}