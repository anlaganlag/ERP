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
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityReceiptCardParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityReceiptCardResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityReceiptCard;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComEntityReceiptCardMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityReceiptCardService;
 /**
 * 资源-公司实体银行设备回单卡;(Tb_Com_Entity_Receipt_Card)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComEntityReceiptCardServiceImpl extends ServiceImpl<TbComEntityReceiptCardMapper, TbComEntityReceiptCard>  implements TbComEntityReceiptCardService{
    @Resource
    private TbComEntityReceiptCardMapper tbComEntityReceiptCardMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityReceiptCard queryById(BigDecimal pkCode){
        return tbComEntityReceiptCardMapper.selectById(pkCode);
    }
    
    /**
     * 分页查询
     *
     * @param tbComEntityReceiptCard 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComEntityReceiptCardResult> paginQuery(TbComEntityReceiptCardParam tbComEntityReceiptCard, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComEntityReceiptCard> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComEntityReceiptCard.getBusReceiptCardBrank())){
            queryWrapper.eq(TbComEntityReceiptCard::getBusReceiptCardBrank, tbComEntityReceiptCard.getBusReceiptCardBrank());
        }
        if(StrUtil.isNotBlank(tbComEntityReceiptCard.getBusReceiptCardNo())){
            queryWrapper.eq(TbComEntityReceiptCard::getBusReceiptCardNo, tbComEntityReceiptCard.getBusReceiptCardNo());
        }
        if(StrUtil.isNotBlank(tbComEntityReceiptCard.getBusReceiptCardStatus())){
            queryWrapper.eq(TbComEntityReceiptCard::getBusReceiptCardStatus, tbComEntityReceiptCard.getBusReceiptCardStatus());
        }
        //2. 执行分页查询
        Page<TbComEntityReceiptCardResult> pagin = new Page<>(current , size , true);
        IPage<TbComEntityReceiptCardResult> selectResult = tbComEntityReceiptCardMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComEntityReceiptCard 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityReceiptCard insert(TbComEntityReceiptCard tbComEntityReceiptCard){
        tbComEntityReceiptCardMapper.insert(tbComEntityReceiptCard);
        return tbComEntityReceiptCard;
    }
    
    /** 
     * 更新数据
     *
     * @param tbComEntityReceiptCard 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityReceiptCard update(TbComEntityReceiptCard tbComEntityReceiptCard){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComEntityReceiptCard> chainWrapper = new LambdaUpdateChainWrapper<>(tbComEntityReceiptCardMapper);
        if(StrUtil.isNotBlank(tbComEntityReceiptCard.getBusReceiptCardBrank())){
            chainWrapper.set(TbComEntityReceiptCard::getBusReceiptCardBrank, tbComEntityReceiptCard.getBusReceiptCardBrank());
        }
        if(StrUtil.isNotBlank(tbComEntityReceiptCard.getBusReceiptCardNo())){
            chainWrapper.set(TbComEntityReceiptCard::getBusReceiptCardNo, tbComEntityReceiptCard.getBusReceiptCardNo());
        }
        if(StrUtil.isNotBlank(tbComEntityReceiptCard.getBusReceiptCardStatus())){
            chainWrapper.set(TbComEntityReceiptCard::getBusReceiptCardStatus, tbComEntityReceiptCard.getBusReceiptCardStatus());
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComEntityReceiptCard::getPkCode, tbComEntityReceiptCard.getPkCode());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(tbComEntityReceiptCard.getPkCode());
        }else{
            return tbComEntityReceiptCard;
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
        int total = tbComEntityReceiptCardMapper.deleteById(pkCode);
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
         int delCount = tbComEntityReceiptCardMapper.deleteBatchIds(pkCodeList);
         if (pkCodeList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
}