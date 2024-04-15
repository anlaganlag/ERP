package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbPlatformAccoSiteCode;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbPlatformAccoSiteCodeParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.QueryApplySiteParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbPlatformAccoSiteCodeResult;

import java.util.List;

 /**
 * 资源-平台-账号-站点-对应编码配置;(Tb_Platform_Acco_Site_Code)--服务接口
 * @author : LSY
 * @date : 2023-8-3
 */
public interface TbPlatformAccoSiteCodeService extends IService<TbPlatformAccoSiteCode> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    TbPlatformAccoSiteCode queryById(String undefinedId);
    
    /**
     * 分页查询
     *
     * @param tbPlatformAccoSiteCode 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbPlatformAccoSiteCodeResult> paginQuery(TbPlatformAccoSiteCodeParam tbPlatformAccoSiteCode, long current, long size);
    List<String> queryPlatform();
    List<String> queryApplyShopName(QueryApplySiteParam queryApplySiteParam);
    List<String> queryApplySite(QueryApplySiteParam queryApplySiteParam);

    /**
     * 新增数据
     *
     * @param tbPlatformAccoSiteCode 实例对象
     * @return 实例对象
     */
    TbPlatformAccoSiteCode insert(TbPlatformAccoSiteCode tbPlatformAccoSiteCode);
    /** 
     * 更新数据
     *
     * @param tbPlatformAccoSiteCode 实例对象
     * @return 实例对象
     */
    ResponseData update(TbPlatformAccoSiteCode tbPlatformAccoSiteCode);
    /** 
     * 通过主键删除数据
     *
     * @param undefinedId 主键
     * @return 是否成功
     */
    boolean deleteById(String undefinedId);
    
    /**
     * 通过主键删除数据--批量删除
     * @param undefinedIdList
     * @return
     */
     boolean deleteBatchIds(List<String> undefinedIdList);
}