package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopCountryCode;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopCountryCodeParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopCountryCodeResult;
import java.util.List;

 /**
 * 资源-店铺站点编码;(Tb_Com_Shop_Country_Code)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComShopCountryCodeService extends IService<TbComShopCountryCode> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param countryCode 主键
     * @return 实例对象
     */
    TbComShopCountryCode queryById(String countryCode);
    
    /**
     * 分页查询
     *
     * @param tbComShopCountryCode 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComShopCountryCodeResult> paginQuery(TbComShopCountryCodeParam tbComShopCountryCode, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComShopCountryCode 实例对象
     * @return 实例对象
     */
    TbComShopCountryCode insert(TbComShopCountryCode tbComShopCountryCode);
    /** 
     * 更新数据
     *
     * @param tbComShopCountryCode 实例对象
     * @return 实例对象
     */
    TbComShopCountryCode update(TbComShopCountryCode tbComShopCountryCode);
    /** 
     * 通过主键删除数据
     *
     * @param countryCode 主键
     * @return 是否成功
     */
    boolean deleteById(String countryCode);
    
    /**
     * 通过主键删除数据--批量删除
     * @param countryCodeList
     * @return
     */
     boolean deleteBatchIds(List<String> countryCodeList);
}