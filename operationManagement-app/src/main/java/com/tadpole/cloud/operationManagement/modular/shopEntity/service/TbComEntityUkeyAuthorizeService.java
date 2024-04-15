package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.math.BigDecimal;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityUkeyAuthorize;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityUkeyAuthorizeParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityUkeyAuthorizeResult;
import java.util.List;

 /**
 * 资源-公司实体银行设备银行UKEY授权信息;(Tb_Com_Entity_Ukey_Authorize)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComEntityUkeyAuthorizeService extends IService<TbComEntityUkeyAuthorize> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    TbComEntityUkeyAuthorize queryById(BigDecimal pkCode);
    
    /**
     * 分页查询
     *
     * @param tbComEntityUkeyAuthorize 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComEntityUkeyAuthorizeResult> paginQuery(TbComEntityUkeyAuthorizeParam tbComEntityUkeyAuthorize, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComEntityUkeyAuthorize 实例对象
     * @return 实例对象
     */
    TbComEntityUkeyAuthorize insert(TbComEntityUkeyAuthorize tbComEntityUkeyAuthorize);
    /** 
     * 更新数据
     *
     * @param tbComEntityUkeyAuthorize 实例对象
     * @return 实例对象
     */
    TbComEntityUkeyAuthorize update(TbComEntityUkeyAuthorize tbComEntityUkeyAuthorize);
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