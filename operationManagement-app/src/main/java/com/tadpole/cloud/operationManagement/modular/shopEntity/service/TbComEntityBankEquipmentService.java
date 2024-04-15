package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityBankEquipment;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityBankEquipmentEditParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityBankEquipmentParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.BankEquipmentCountResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityBankEquipmentResult;

import java.math.BigDecimal;
import java.util.List;

 /**
 * 资源-公司实体银行设备;(Tb_Com_Entity_Bank_Equipment)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComEntityBankEquipmentService extends IService<TbComEntityBankEquipment> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkBeCode 主键
     * @return 实例对象
     */
    TbComEntityBankEquipment queryById(BigDecimal pkBeCode);
    
    /**
     * 分页查询
     *
     * @param tbComEntityBankEquipment 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComEntityBankEquipmentResult> paginQuery(TbComEntityBankEquipmentParam tbComEntityBankEquipment, long current, long size);



     ResponseData queryList(TbComEntityBankEquipmentParam param);


     /**
     * 新增数据
     *
     * @param tbComEntityBankEquipment 实例对象
     * @return 实例对象
     */
    TbComEntityBankEquipment insert(TbComEntityBankEquipment tbComEntityBankEquipment);
    /** 
     * 更新数据
     *
     * @param tbComEntityBankEquipment 实例对象
     * @return 实例对象
     */
    TbComEntityBankEquipment update(TbComEntityBankEquipment tbComEntityBankEquipment);
    TbComEntityBankEquipmentEditParam update(TbComEntityBankEquipmentEditParam tbComEntityBankEquipmentEditParam);
    /**
     * 通过主键删除数据
     *
     * @param pkBeCode 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal pkBeCode);
    
    /**
     * 通过主键删除数据--批量删除
     * @param pkBeCodeList
     * @return
     */
     boolean deleteBatchIds(List<BigDecimal> pkBeCodeList);

     /**
      * 银行设备统计
      * @param param
      * @return
      */
     List<BankEquipmentCountResult> bankEquipmentCount(TbComEntityBankEquipmentParam param);

     /**
      * 银行设备管理查询 主表作为基表
      * @param tbComEntityParam
      * @param current
      * @param size
      * @return
      */
     Page<TbComEntityBankEquipmentResult> paginQuery(TbComEntityParam tbComEntityParam, long current, long size);

 }