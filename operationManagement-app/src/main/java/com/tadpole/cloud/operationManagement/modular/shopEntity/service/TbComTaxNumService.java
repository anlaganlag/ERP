package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopTaxnCatManage;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComTaxNum;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComTaxAuditParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComTaxNumParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComTaxNumResult;

import java.util.List;

 /**
 * 资源-税号;(Tb_Com_Tax_Num)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComTaxNumService extends IService<TbComTaxNum> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param taxnOverseas 主键
     * @return 实例对象
     */
    ResponseData queryById(String taxnOverseas);
    
    /**
     * 分页查询
     *
     * @param tbComTaxNum 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComTaxNumResult> paginQuery(TbComTaxNumParam tbComTaxNum, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComTaxNum 实例对象
     * @return 实例对象
     */
    TbComTaxNum insert(TbComTaxNum tbComTaxNum);
    /** 
     * 更新数据
     *
     * @param tbComTaxNum 实例对象
     * @return 实例对象
     */
    TbComTaxNum update(TbComTaxNum tbComTaxNum);

     ResponseData updateByShopName(TbComTaxNum tbComTaxNum) throws Exception;

     TbComTaxNum cancelTaxCode(TbComTaxNum tbComTaxNum) throws Exception;

     ResponseData updateTaxNumState(String shopName,String taxnState);


     ResponseData addTaxAudit(TbComTaxAuditParam param) throws Exception;


    /** 
     * 通过主键删除数据
     *
     * @param taxnOverseas 主键
     * @return 是否成功
     */
    boolean deleteById(String taxnOverseas);
    
    /**
     * 通过主键删除数据--批量删除
     * @param taxnOverseasList
     * @return
     */
     boolean deleteBatchIds(List<String> taxnOverseasList);

     /**
      * 更新税号信息
      * @param tbComShopTaxnCatManage
      * @return
      */
     boolean updateTaxNum(TbComShopTaxnCatManage tbComShopTaxnCatManage);

 }