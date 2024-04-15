package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.math.BigDecimal;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityPortCardLegal;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityPortCardLegalParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityPortCardLegalResult;
import java.util.List;

 /**
 * 资源-公司实体银行设备口岸卡法人;(Tb_Com_Entity_Port_Card_Legal)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComEntityPortCardLegalService extends IService<TbComEntityPortCardLegal> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    TbComEntityPortCardLegal queryById(BigDecimal pkCode);
    
    /**
     * 分页查询
     *
     * @param tbComEntityPortCardLegal 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComEntityPortCardLegalResult> paginQuery(TbComEntityPortCardLegalParam tbComEntityPortCardLegal, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComEntityPortCardLegal 实例对象
     * @return 实例对象
     */
    TbComEntityPortCardLegal insert(TbComEntityPortCardLegal tbComEntityPortCardLegal);
    /** 
     * 更新数据
     *
     * @param tbComEntityPortCardLegal 实例对象
     * @return 实例对象
     */
    TbComEntityPortCardLegal update(TbComEntityPortCardLegal tbComEntityPortCardLegal);
    /** 
     * 通过主键删除数据
     *
     * @param pkCode 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal pkCode);
    
    /**
     * 通过主键删除数据--批量删除
     * @param pkCodeList
     * @return
     */
     boolean deleteBatchIds(List<BigDecimal> pkCodeList);
}