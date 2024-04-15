package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tadpole.cloud.supplyChain.modular.logistics.entity.EstLogisticsEstimateFeeDet;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.EstLogisticsEstimateFeeDetMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.model.params.EstLogisticsEstimateFeeDetParam;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import com.tadpole.cloud.supplyChain.modular.logistics.entity.EstLogisticsEstimateFee;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.EstLogisticsEstimateFeeMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.EstLogisticsEstimateFeeService;
import com.tadpole.cloud.supplyChain.modular.logistics.model.result.EstLogisticsEstimateFeeResult;
import com.tadpole.cloud.supplyChain.modular.logistics.model.params.EstLogisticsEstimateFeeParam;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
 /**
 * 物流费用测算;(EST_LOGISTICS_ESTIMATE_FEE)表服务实现类
 * @author : LSY
 * @date : 2024-3-14
 */
@Service
@Transactional
@Slf4j
public class EstLogisticsEstimateFeeServiceImpl  extends ServiceImpl<EstLogisticsEstimateFeeMapper, EstLogisticsEstimateFee> implements EstLogisticsEstimateFeeService{
    @Resource
    private EstLogisticsEstimateFeeMapper estLogisticsEstimateFeeMapper;

     @Resource
     private EstLogisticsEstimateFeeDetMapper estLogisticsEstimateFeeDetMapper;

    /**
     * 通过ID查询单条数据 
     *
     * @param estId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public EstLogisticsEstimateFee queryById(String estId){
        return estLogisticsEstimateFeeMapper.selectById(estId);
    }
    
    /**
     * 分页查询
     *
     * @param param 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public Page<EstLogisticsEstimateFeeResult> paginQuery(EstLogisticsEstimateFeeParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<EstLogisticsEstimateFee> queryWrapper = new LambdaQueryWrapper<>();
        //2. 执行分页查询
        Page<EstLogisticsEstimateFeeResult> pagin = new Page<>(current , size , true);
        IPage<EstLogisticsEstimateFeeResult> selectResult = estLogisticsEstimateFeeMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

     @DataSource(name = "logistics")
     @Override
     public List<EstLogisticsEstimateFeeResult> queryList(EstLogisticsEstimateFeeDetParam param){


         MPJLambdaWrapper<EstLogisticsEstimateFee> wrapper = new MPJLambdaWrapper<EstLogisticsEstimateFee>();
         wrapper
                 .selectAll(EstLogisticsEstimateFee.class)
                 .leftJoin(EstLogisticsEstimateFeeDet.class, EstLogisticsEstimateFeeDet::getEstId, EstLogisticsEstimateFee::getEstId)
                 .selectCollection(EstLogisticsEstimateFeeDet.class, EstLogisticsEstimateFeeResult::getDetailList)
                 .in(ObjectUtil.isNotEmpty(param.getEstIdList()),EstLogisticsEstimateFeeDet::getEstId,param.getEstIdList())
                 .like(ObjectUtil.isNotEmpty(param.getEstId()),EstLogisticsEstimateFeeDet::getEstId, param.getEstId())
                 .in(ObjectUtil.isNotEmpty(param.getShipmentIdList()),EstLogisticsEstimateFeeDet::getShipmentId,param.getShipmentIdList())
                 .like(ObjectUtil.isNotEmpty(param.getShipmentId()),EstLogisticsEstimateFeeDet::getShipmentId, param.getShipmentId())
                 .isNotNull(EstLogisticsEstimateFeeDet::getEstDate)
//                 .ge(ObjectUtil.isNotEmpty(param.getEstDatStart()),EstLogisticsEstimateFeeDet::getEstDate, startDate)
//                 .le(ObjectUtil.isNotEmpty(param.getEstDatEnd()),EstLogisticsEstimateFeeDet::getEstDate, param.getEstDatEnd())
                 .orderByDesc(EstLogisticsEstimateFee::getEstDate)
                 .orderByAsc( EstLogisticsEstimateFeeDet::getShipmentId);


         List<EstLogisticsEstimateFeeResult> selectResult = estLogisticsEstimateFeeMapper.selectJoinList(EstLogisticsEstimateFeeResult.class,wrapper);
         Date estDateStart = param.getEstDateStart();
         Date estDateEndPre = param.getEstDateEnd();
         DateTime estDateEnd = DateUtil.offsetDay(estDateEndPre, 1);
         if (ObjectUtil.isNotEmpty(estDateStart) && ObjectUtil.isNotEmpty(estDateEnd)) {
             selectResult = selectResult.stream().filter(item -> DateUtil.compare(item.getEstDate(), estDateStart)>=0 && DateUtil.compare(item.getEstDate(),estDateEnd)<0).collect(Collectors.toList());
         }


         return selectResult;
    }

     /**
      * 新增数据
      *
      * @param estLogisticsEstimateFee 实例对象
      * @return 实例对象
      */
     @DataSource(name = "logistics")
     @Override
     public EstLogisticsEstimateFee insert(EstLogisticsEstimateFee estLogisticsEstimateFee){
         estLogisticsEstimateFeeMapper.insert(estLogisticsEstimateFee);
         return estLogisticsEstimateFee;
     }
    @DataSource(name = "logistics")
    @Override
    public EstLogisticsEstimateFeeDet getLastFill(){

        String operator = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser()) ? LoginContext.me().getLoginUser().getName() : "";

        List<EstLogisticsEstimateFeeDet> list = new LambdaQueryChainWrapper<>(estLogisticsEstimateFeeDetMapper)
                .eq(EstLogisticsEstimateFeeDet::getPerName, operator)
                .orderByDesc(EstLogisticsEstimateFeeDet::getUpdatedTime, EstLogisticsEstimateFeeDet::getCreatedTime)
                .list();
        if (ObjectUtil.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }



     @DataSource(name = "logistics")
     @Override
     public ResponseData deleteByCascade(String estId){
        try {
            if (ObjectUtil.isEmpty(estId)) {
                return ResponseData.error("删除测算明细失败，ID不能为空");
            }
            estLogisticsEstimateFeeMapper.deleteById(estId);
            new LambdaUpdateChainWrapper<>(estLogisticsEstimateFeeDetMapper)
                    .eq(EstLogisticsEstimateFeeDet::getEstId, estId)
                    .remove();
            return ResponseData.success("删除成功");
        } catch (Exception e) {
            log.error("删除失败", e);
            return ResponseData.error("删除失败"+e.getMessage());
        }
     }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public EstLogisticsEstimateFee update(EstLogisticsEstimateFeeParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<EstLogisticsEstimateFee> wrapper = new LambdaUpdateChainWrapper<EstLogisticsEstimateFee>(estLogisticsEstimateFeeMapper);
        //2. 设置主键，并更新
        wrapper.eq(EstLogisticsEstimateFee::getEstId, param.getEstId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        wrapper.set(EstLogisticsEstimateFee::getUpdatedTime, new Date());
        wrapper.set(EstLogisticsEstimateFee::getUpdatedBy, loginUser.getName());
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getEstId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param estId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(String estId){
        int total = estLogisticsEstimateFeeMapper.deleteById(estId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param estIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<String> estIdList) {
        int delCount = estLogisticsEstimateFeeMapper.deleteBatchIds(estIdList);
        if (estIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}