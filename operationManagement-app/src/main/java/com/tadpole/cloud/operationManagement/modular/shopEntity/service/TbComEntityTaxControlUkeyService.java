package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.math.BigDecimal;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityTaxControlUkey;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityTaxControlUkeyParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityTaxControlUkeyResult;
import java.util.List;

 /**
 * 资源-公司实体银行设备税控UKEY;(Tb_Com_Entity_Tax_Control_Ukey)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComEntityTaxControlUkeyService extends IService<TbComEntityTaxControlUkey> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    TbComEntityTaxControlUkey queryById(BigDecimal pkCode);
    
    /**
     * 分页查询
     *
     * @param tbComEntityTaxControlUkey 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComEntityTaxControlUkeyResult> paginQuery(TbComEntityTaxControlUkeyParam tbComEntityTaxControlUkey, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComEntityTaxControlUkey 实例对象
     * @return 实例对象
     */
    TbComEntityTaxControlUkey insert(TbComEntityTaxControlUkey tbComEntityTaxControlUkey);
    /** 
     * 更新数据
     *
     * @param tbComEntityTaxControlUkey 实例对象
     * @return 实例对象
     */
    TbComEntityTaxControlUkey update(TbComEntityTaxControlUkey tbComEntityTaxControlUkey);
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