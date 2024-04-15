package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShop;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopRecBrankChangeAcc;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopShroffAccount;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopLogisticsReportResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopReportResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopResult;

import java.util.List;
import java.util.Map;

/**
 * 资源-店铺;(Tb_Com_Shop)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComShopService extends IService<TbComShop> {

    /**
     * 通过ID查询单条数据
     *
     * @param shopName 主键
     * @return 实例对象
     */
    TbComShop queryById(String shopName);

    /**
     * 分页查询
     *
     * @param tbComShop 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComShopResult> paginQuery(TbComShopParam tbComShop, long current, long size);


     Page<TbComShopReportResult> paginQueryShopReport(TbComShopParam tbComShop, long current, long size);
     Page<TbComShopLogisticsReportResult> TbComShopLogisticsReportResult(TbComShopParam tbComShop, long current, long size);


     /**
      * 分页异常查询
      *
      * @param tbComShop 筛选条件
      * @param current 当前页码
      * @param size  每页大小
      * @return
      */
     Page<TbComShopResult> paginErrorQuery(TbComShopParam tbComShop, long current, long size);


     /**
      * 创建店铺组织编码
      *
      * @param tbComShop 实例对象
      * @return 实例对象
      */
     void createOrgCode(List<String> shopNameList);


     ResponseData syncShopInfo(List<String> shopNameList);


     @DataSource(name = "stocking")
     TbComShop queryShopInfo(TbComShopParam param);

     /**
     * 新增数据
     *
     * @param tbComShop 实例对象
     * @return 实例对象
     */
    TbComShop insert(TbComShop tbComShop);
    /**
     * 更新数据
     *
     * @param tbComShop 实例对象
     * @return 实例对象
     */
    TbComShop update(TbComShop tbComShop);
     ResponseData applyShutShop(TbComShop tbComShop);
     ResponseData shutShop(TbComShop tbComShop);

    /**
     * 通过主键删除数据
     *
     * @param shopName 主键
     * @return 是否成功
     */
    boolean deleteById(String shopName);

    /**
     * 通过主键删除数据--批量删除
     * @param shopNameList
     * @return
     */
     boolean deleteBatchIds(List<String> shopNameList);

     /**
      * 店铺名称下拉
      * @param filter 是否过滤
      * @return
      */
     List<String> shopNameList(boolean filter);

     /**
      * 收款银行账号变更
      * @param recBrankChangeAcc 审核通过的变更记录
      * @return
      */
     boolean recBrankChangeAcc( TbComShopRecBrankChangeAcc recBrankChangeAcc);

     /**
      * 更新信用卡账号信息
      * @param sa
      * @return
      */
     boolean updateShroffAccount(TbComShopShroffAccount sa);
    List<Map<String, Object>> getAllShopName();

    /**
     * 账号下拉
     * @return
     */
    List<String> shopList();

    List<String> siteList();

    List<String> shopNameList();

    List<String> platformList();
}
