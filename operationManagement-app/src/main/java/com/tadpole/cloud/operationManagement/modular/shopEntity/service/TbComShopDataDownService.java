package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShop;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopDataDown;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopDataDownParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopDataDownResult;

import java.math.BigDecimal;
import java.util.List;

 /**
 * 资源-店铺数据下载管理;(Tb_Com_Shop_Data_Down)--服务接口
 * @author : LSY
 * @date : 2023-8-11
 */
public interface TbComShopDataDownService extends IService<TbComShopDataDown> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    TbComShopDataDown queryById(BigDecimal id);
    
    /**
     * 分页查询
     *
     * @param tbComShopDataDown 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComShopDataDownResult> paginQuery(TbComShopDataDownParam tbComShopDataDown, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComShopDataDown 实例对象
     * @return 实例对象
     */
    TbComShopDataDown insert(TbComShopDataDown tbComShopDataDown);
    /** 
     * 更新数据
     *
     * @param tbComShopDataDown 实例对象
     * @return 实例对象
     */
    TbComShopDataDown update(TbComShopDataDown tbComShopDataDown);
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal id);
    
    /**
     * 通过主键删除数据--批量删除
     * @param idList
     * @return
     */
     boolean deleteBatchIds(List<BigDecimal> idList);

     /**
      * 店铺数据自动下载任务创建
      * @param applyDet
      */
     ResponseData createTask(TbComShop comShop);

     /**
      * 店铺数据下载任务检查重启，（第一次创建失败，后面运营又需要下载数据的任务开启）
      */
     ResponseData checkRestartTask(String shopNameSimple);

     public ResponseData shopHistoryDataTask(String shopNameSimple,Integer isAllShop);
 }