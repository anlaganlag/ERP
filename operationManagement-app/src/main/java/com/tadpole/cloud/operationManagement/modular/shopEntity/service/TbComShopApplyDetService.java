package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopApplyDet;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopApplyDetParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopApplyDetResult;

import java.math.BigDecimal;
import java.util.List;

 /**
 * 资源-店铺申请详情表;(Tb_Com_Shop_Apply_Det)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComShopApplyDetService extends IService<TbComShopApplyDet> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysApplyDetId 主键
     * @return 实例对象
     */
    TbComShopApplyDet queryById(BigDecimal sysApplyDetId);
    
    /**
     * 分页查询
     *
     * @param tbComShopApplyDet 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComShopApplyDetResult> paginQuery(TbComShopApplyDetParam tbComShopApplyDet, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComShopApplyDet 实例对象
     * @return 实例对象
     */
    TbComShopApplyDet insert(TbComShopApplyDet tbComShopApplyDet);
    /** 
     * 更新数据
     *
     * @param tbComShopApplyDet 实例对象
     * @return 实例对象
     */
    TbComShopApplyDet update(TbComShopApplyDet tbComShopApplyDet);
    /** 
     * 通过主键删除数据
     *
     * @param sysApplyDetId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal sysApplyDetId);
    
    /**
     * 通过主键删除数据--批量删除
     * @param sysApplyDetIdList
     * @return
     */
     boolean deleteBatchIds(List<BigDecimal> sysApplyDetIdList);

     /**
      * 店铺财务任务处理完后，同步数据
      * @param shopName
      * @return
      */
     ResponseData syncData(BigDecimal sysApplyDetId);
 }