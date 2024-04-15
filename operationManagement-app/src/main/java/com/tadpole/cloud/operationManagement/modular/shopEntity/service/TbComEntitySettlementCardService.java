package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.math.BigDecimal;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntitySettlementCard;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntitySettlementCardParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntitySettlementCardResult;
import java.util.List;

 /**
 * 资源-公司实体银行设备结算卡;(Tb_Com_Entity_Settlement_Card)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComEntitySettlementCardService extends IService<TbComEntitySettlementCard> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    TbComEntitySettlementCard queryById(BigDecimal pkCode);
    
    /**
     * 分页查询
     *
     * @param tbComEntitySettlementCard 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComEntitySettlementCardResult> paginQuery(TbComEntitySettlementCardParam tbComEntitySettlementCard, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComEntitySettlementCard 实例对象
     * @return 实例对象
     */
    TbComEntitySettlementCard insert(TbComEntitySettlementCard tbComEntitySettlementCard);
    /** 
     * 更新数据
     *
     * @param tbComEntitySettlementCard 实例对象
     * @return 实例对象
     */
    TbComEntitySettlementCard update(TbComEntitySettlementCard tbComEntitySettlementCard);
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