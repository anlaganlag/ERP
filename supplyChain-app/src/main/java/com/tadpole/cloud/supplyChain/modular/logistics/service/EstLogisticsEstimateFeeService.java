package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logistics.entity.EstLogisticsEstimateFee;
import com.tadpole.cloud.supplyChain.modular.logistics.entity.EstLogisticsEstimateFeeDet;
import com.tadpole.cloud.supplyChain.modular.logistics.model.params.EstLogisticsEstimateFeeDetParam;
import com.tadpole.cloud.supplyChain.modular.logistics.model.result.EstLogisticsEstimateFeeResult;
import com.tadpole.cloud.supplyChain.modular.logistics.model.params.EstLogisticsEstimateFeeParam;
import java.util.List;

 /**
 * 物流费用测算;(EST_LOGISTICS_ESTIMATE_FEE)表服务接口
 * @author : LSY
 * @date : 2024-3-14
 */
public interface EstLogisticsEstimateFeeService extends IService<EstLogisticsEstimateFee> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param estId 主键
     * @return 实例对象
     */
    EstLogisticsEstimateFee queryById(String estId);
    
    /**
     * 分页查询
     *
     * @param estLogisticsEstimateFee 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<EstLogisticsEstimateFeeResult> paginQuery(EstLogisticsEstimateFeeParam estLogisticsEstimateFee, long current, long size);






     List<EstLogisticsEstimateFeeResult> queryList(EstLogisticsEstimateFeeDetParam param);

     /**
     * 新增数据
     *
     * @param estLogisticsEstimateFee 实例对象
     * @return 实例对象
     */
    EstLogisticsEstimateFee insert(EstLogisticsEstimateFee estLogisticsEstimateFee);



     @DataSource(name = "logistics")
     EstLogisticsEstimateFeeDet getLastFill();

     ResponseData deleteByCascade(String estId);

     /**
     * 更新数据
     *
     * @param estLogisticsEstimateFee 实例对象
     * @return 实例对象
     */
    EstLogisticsEstimateFee update(EstLogisticsEstimateFeeParam estLogisticsEstimateFee);
    /** 
     * 通过主键删除数据
     *
     * @param estId 主键
     * @return 是否成功
     */
    boolean deleteById(String estId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param estIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<String> estIdList);
}