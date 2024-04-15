package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComTaxNumber;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComTaxNumberParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComTaxNumberResult;

import java.util.List;

 /**
 * 资源-税号管理;(Tb_Com_Tax_Number)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComTaxNumberService extends IService<TbComTaxNumber> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    TbComTaxNumber queryById(String id);
    
    /**
     * 分页查询
     *
     * @param tbComTaxNumber 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComTaxNumberResult> paginQuery(TbComTaxNumberParam tbComTaxNumber, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComTaxNumber 实例对象
     * @return 实例对象
     */
    TbComTaxNumber insert(TbComTaxNumber tbComTaxNumber);
    /** 
     * 更新数据
     *
     * @param tbComTaxNumber 实例对象
     * @return 实例对象
     */
    TbComTaxNumber update(TbComTaxNumber tbComTaxNumber);
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);
    
    /**
     * 通过主键删除数据--批量删除
     * @param idList
     * @return
     */
     boolean deleteBatchIds(List<String> idList);



 }