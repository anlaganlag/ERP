package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopNameCode;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopNameCodeParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopNameCodeResult;
import java.util.List;

 /**
 * 资源-店铺编码;(Tb_Com_Shop_Name_Code)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComShopNameCodeService extends IService<TbComShopNameCode> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param shopNameSimple 主键
     * @return 实例对象
     */
    TbComShopNameCode queryById(String shopNameSimple);
    
    /**
     * 分页查询
     *
     * @param tbComShopNameCode 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComShopNameCodeResult> paginQuery(TbComShopNameCodeParam tbComShopNameCode, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComShopNameCode 实例对象
     * @return 实例对象
     */
    TbComShopNameCode insert(TbComShopNameCode tbComShopNameCode);
    /** 
     * 更新数据
     *
     * @param tbComShopNameCode 实例对象
     * @return 实例对象
     */
    TbComShopNameCode update(TbComShopNameCode tbComShopNameCode);
    /** 
     * 通过主键删除数据
     *
     * @param shopNameSimple 主键
     * @return 是否成功
     */
    boolean deleteById(String shopNameSimple);
    
    /**
     * 通过主键删除数据--批量删除
     * @param shopNameSimpleList
     * @return
     */
     boolean deleteBatchIds(List<String> shopNameSimpleList);
}