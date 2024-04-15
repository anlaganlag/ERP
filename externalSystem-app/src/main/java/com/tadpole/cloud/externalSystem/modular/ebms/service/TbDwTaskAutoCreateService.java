package com.tadpole.cloud.externalSystem.modular.ebms.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.externalSystem.api.ebms.model.param.EbmsShopDataDownTaskParam;
import com.tadpole.cloud.externalSystem.modular.ebms.entity.TbDwTaskAutoCreate;
import com.tadpole.cloud.externalSystem.modular.ebms.model.param.TbDwTaskAutoCreateParam;
import com.tadpole.cloud.externalSystem.modular.ebms.model.result.TbDwTaskAutoCreateResult;

import java.math.BigDecimal;
import java.util.List;

 /**
 * TbDwTaskAutoCreate;(Tb_DW_Task_Auto_Create)--服务接口
 * @author : LSY
 * @date : 2023-8-14
 */
public interface TbDwTaskAutoCreateService extends IService<TbDwTaskAutoCreate> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysDwId 主键
     * @return 实例对象
     */
    TbDwTaskAutoCreate queryById(BigDecimal sysDwId);
    
    /**
     * 分页查询
     *
     * @param tbDwTaskAutoCreate 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbDwTaskAutoCreateResult> paginQuery(TbDwTaskAutoCreateParam tbDwTaskAutoCreate, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbDwTaskAutoCreate 实例对象
     * @return 实例对象
     */
    TbDwTaskAutoCreate insert(TbDwTaskAutoCreate tbDwTaskAutoCreate);
    /** 
     * 更新数据
     *
     * @param tbDwTaskAutoCreate 实例对象
     * @return 实例对象
     */
    TbDwTaskAutoCreate update(TbDwTaskAutoCreate tbDwTaskAutoCreate);
    /** 
     * 通过主键删除数据
     *
     * @param sysDwId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal sysDwId);
    
    /**
     * 通过主键删除数据--批量删除
     * @param sysDwIdList
     * @return
     */
     boolean deleteBatchIds(List<BigDecimal> sysDwIdList);


     ResponseData getMarketplaceIdByPlatformSite(String platform, String site);

     ResponseData syncShopDwDataTask(EbmsShopDataDownTaskParam dataDownParam);
 }