package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopPlatCode;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopPlatCodeParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopPlatCodeResult;
import java.util.List;

 /**
 * 资源-店铺平台编码;(Tb_Com_Shop_Plat_Code)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComShopPlatCodeService extends IService<TbComShopPlatCode> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param elePlatformName 主键
     * @return 实例对象
     */
    TbComShopPlatCode queryById(String elePlatformName);
    
    /**
     * 分页查询
     *
     * @param tbComShopPlatCode 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComShopPlatCodeResult> paginQuery(TbComShopPlatCodeParam tbComShopPlatCode, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComShopPlatCode 实例对象
     * @return 实例对象
     */
    TbComShopPlatCode insert(TbComShopPlatCode tbComShopPlatCode);
    /** 
     * 更新数据
     *
     * @param tbComShopPlatCode 实例对象
     * @return 实例对象
     */
    TbComShopPlatCode update(TbComShopPlatCode tbComShopPlatCode);
    /** 
     * 通过主键删除数据
     *
     * @param elePlatformName 主键
     * @return 是否成功
     */
    boolean deleteById(String elePlatformName);
    
    /**
     * 通过主键删除数据--批量删除
     * @param elePlatformNameList
     * @return
     */
     boolean deleteBatchIds(List<String> elePlatformNameList);
}