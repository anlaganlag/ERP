package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsListToHeadRouteDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsListToHeadRouteParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsListToHeadRouteDetResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsListToHeadRouteDetParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 物流单头程信息-明细;(tb_logistics_list_to_head_route_det)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsListToHeadRouteDetService extends IService<TbLogisticsListToHeadRouteDet> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    TbLogisticsListToHeadRouteDet queryById(BigDecimal id);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsListToHeadRouteDet 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsListToHeadRouteDetResult> paginQuery(TbLogisticsListToHeadRouteDetParam tbLogisticsListToHeadRouteDet, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsListToHeadRouteDet 实例对象
     * @return 实例对象
     */
    TbLogisticsListToHeadRouteDet insert(TbLogisticsListToHeadRouteDet tbLogisticsListToHeadRouteDet);
    /** 
     * 更新数据
     *
     * @param tbLogisticsListToHeadRouteDet 实例对象
     * @return 实例对象
     */
    TbLogisticsListToHeadRouteDet update(TbLogisticsListToHeadRouteDetParam tbLogisticsListToHeadRouteDet);
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
      * 通过LhrCode发货批次号获取头程物流单明细
      * @param lhrCode
      * @return
      */
     List<TbLogisticsListToHeadRouteDet> queryByLhrCode(String lhrCode);

     /**
      * 通过LhrOddNum物流单号--获取头程物流单明细
      * @param lhrOddNumb
      * @return
      */
     List<TbLogisticsListToHeadRouteDet> queryByLhrOddNumb(String lhrOddNumb);

     /**
      * 物流转仓
      * @param param
      * @return
      */
     ResponseData logisticsTransformhouse(TbLogisticsListToHeadRouteParam param);

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
 }