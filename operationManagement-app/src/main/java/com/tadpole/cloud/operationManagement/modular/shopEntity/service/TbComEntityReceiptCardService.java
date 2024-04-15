package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.math.BigDecimal;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityReceiptCard;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityReceiptCardParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityReceiptCardResult;
import java.util.List;

 /**
 * 资源-公司实体银行设备回单卡;(Tb_Com_Entity_Receipt_Card)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComEntityReceiptCardService extends IService<TbComEntityReceiptCard> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    TbComEntityReceiptCard queryById(BigDecimal pkCode);
    
    /**
     * 分页查询
     *
     * @param tbComEntityReceiptCard 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComEntityReceiptCardResult> paginQuery(TbComEntityReceiptCardParam tbComEntityReceiptCard, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComEntityReceiptCard 实例对象
     * @return 实例对象
     */
    TbComEntityReceiptCard insert(TbComEntityReceiptCard tbComEntityReceiptCard);
    /** 
     * 更新数据
     *
     * @param tbComEntityReceiptCard 实例对象
     * @return 实例对象
     */
    TbComEntityReceiptCard update(TbComEntityReceiptCard tbComEntityReceiptCard);
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