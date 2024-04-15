package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.math.BigDecimal;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityTaxControlPanel;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityTaxControlPanelParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityTaxControlPanelResult;
import java.util.List;

 /**
 * 资源-公司实体银行设备税控盘;(Tb_Com_Entity_Tax_Control_Panel)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComEntityTaxControlPanelService extends IService<TbComEntityTaxControlPanel> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    TbComEntityTaxControlPanel queryById(BigDecimal pkCode);
    
    /**
     * 分页查询
     *
     * @param tbComEntityTaxControlPanel 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComEntityTaxControlPanelResult> paginQuery(TbComEntityTaxControlPanelParam tbComEntityTaxControlPanel, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComEntityTaxControlPanel 实例对象
     * @return 实例对象
     */
    TbComEntityTaxControlPanel insert(TbComEntityTaxControlPanel tbComEntityTaxControlPanel);
    /** 
     * 更新数据
     *
     * @param tbComEntityTaxControlPanel 实例对象
     * @return 实例对象
     */
    TbComEntityTaxControlPanel update(TbComEntityTaxControlPanel tbComEntityTaxControlPanel);
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