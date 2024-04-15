package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;

import cn.hutool.core.util.StrUtil;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntitySettlementCardParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntitySettlementCardResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntitySettlementCard;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComEntitySettlementCardMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntitySettlementCardService;
 /**
 * 资源-公司实体银行设备结算卡;(Tb_Com_Entity_Settlement_Card)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComEntitySettlementCardServiceImpl extends ServiceImpl<TbComEntitySettlementCardMapper, TbComEntitySettlementCard>  implements TbComEntitySettlementCardService{
    @Resource
    private TbComEntitySettlementCardMapper tbComEntitySettlementCardMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntitySettlementCard queryById(BigDecimal pkCode){
        return tbComEntitySettlementCardMapper.selectById(pkCode);
    }
    
    /**
     * 分页查询
     *
     * @param tbComEntitySettlementCard 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComEntitySettlementCardResult> paginQuery(TbComEntitySettlementCardParam tbComEntitySettlementCard, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComEntitySettlementCard> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComEntitySettlementCard.getBusSettlementCardBank())){
            queryWrapper.eq(TbComEntitySettlementCard::getBusSettlementCardBank, tbComEntitySettlementCard.getBusSettlementCardBank());
        }
        if(StrUtil.isNotBlank(tbComEntitySettlementCard.getBusSettlementCardNo())){
            queryWrapper.eq(TbComEntitySettlementCard::getBusSettlementCardNo, tbComEntitySettlementCard.getBusSettlementCardNo());
        }
        if(StrUtil.isNotBlank(tbComEntitySettlementCard.getBusSettlementCardStatus())){
            queryWrapper.eq(TbComEntitySettlementCard::getBusSettlementCardStatus, tbComEntitySettlementCard.getBusSettlementCardStatus());
        }
        //2. 执行分页查询
        Page<TbComEntitySettlementCardResult> pagin = new Page<>(current , size , true);
        IPage<TbComEntitySettlementCardResult> selectResult = tbComEntitySettlementCardMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComEntitySettlementCard 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntitySettlementCard insert(TbComEntitySettlementCard tbComEntitySettlementCard){
        tbComEntitySettlementCardMapper.insert(tbComEntitySettlementCard);
        return tbComEntitySettlementCard;
    }
    
    /** 
     * 更新数据
     *
     * @param tbComEntitySettlementCard 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntitySettlementCard update(TbComEntitySettlementCard tbComEntitySettlementCard){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComEntitySettlementCard> chainWrapper = new LambdaUpdateChainWrapper<>(tbComEntitySettlementCardMapper);
        if(StrUtil.isNotBlank(tbComEntitySettlementCard.getBusSettlementCardBank())){
            chainWrapper.set(TbComEntitySettlementCard::getBusSettlementCardBank, tbComEntitySettlementCard.getBusSettlementCardBank());
        }
        if(StrUtil.isNotBlank(tbComEntitySettlementCard.getBusSettlementCardNo())){
            chainWrapper.set(TbComEntitySettlementCard::getBusSettlementCardNo, tbComEntitySettlementCard.getBusSettlementCardNo());
        }
        if(StrUtil.isNotBlank(tbComEntitySettlementCard.getBusSettlementCardStatus())){
            chainWrapper.set(TbComEntitySettlementCard::getBusSettlementCardStatus, tbComEntitySettlementCard.getBusSettlementCardStatus());
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComEntitySettlementCard::getPkCode, tbComEntitySettlementCard.getPkCode());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(tbComEntitySettlementCard.getPkCode());
        }else{
            return tbComEntitySettlementCard;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param pkCode 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(BigDecimal pkCode){
        int total = tbComEntitySettlementCardMapper.deleteById(pkCode);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param pkCodeList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> pkCodeList){
         int delCount = tbComEntitySettlementCardMapper.deleteBatchIds(pkCodeList);
         if (pkCodeList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
}