package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsListToHeadRoute;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsBillManageExportResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsListToHeadRouteResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsListToHeadRouteParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 物流单头程信息;(tb_logistics_list_to_head_route)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsListToHeadRouteService extends IService<TbLogisticsListToHeadRoute> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    TbLogisticsListToHeadRoute queryById(BigDecimal id);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsListToHeadRoute 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsListToHeadRouteResult> paginQuery(TbLogisticsListToHeadRouteParam tbLogisticsListToHeadRoute, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsListToHeadRoute 实例对象
     * @return 实例对象
     */
    TbLogisticsListToHeadRoute insert(TbLogisticsListToHeadRoute tbLogisticsListToHeadRoute);
    /** 
     * 更新数据
     *
     * @param tbLogisticsListToHeadRoute 实例对象
     * @return 实例对象
     */
    TbLogisticsListToHeadRoute update(TbLogisticsListToHeadRouteParam tbLogisticsListToHeadRoute);
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal id);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param idList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> idList);

     /**
      * 根据批次号和快递单号 删除物流单头程信息
      * @param lhrCode
      * @param lhrOddNumb
      * @return
      */
     int delByLhrCodeAndLhrOddNumb(String lhrCode, String lhrOddNumb);

     /**
      * 根据批次号和快递单号 更新 物流单头程信息 状态
      * @param lhrCode
      * @param lhrOddNumb
      * @param state
      * @return
      */
     int upByLhrCodeAndLhrOddNumb(String lhrCode, String lhrOddNumb, String state);

     /**
      *
      * @param lhrCode
      * @return
      */
     List<TbLogisticsListToHeadRoute> queryByLhrCode(String lhrCode);

     /**
      * 物流单 按查询条件 导出
      * @param tbLogisticsListToHeadRouteParm
      * @return
      */
     List<TbLogisticsBillManageExportResult> billManageExport(TbLogisticsListToHeadRouteParam tbLogisticsListToHeadRouteParm);
 }