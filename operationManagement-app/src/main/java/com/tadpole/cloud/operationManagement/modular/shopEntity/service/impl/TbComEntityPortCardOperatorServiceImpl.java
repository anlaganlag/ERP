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
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityPortCardOperatorParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityPortCardOperatorResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityPortCardOperator;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComEntityPortCardOperatorMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityPortCardOperatorService;
 /**
 * 资源-公司实体银行设备口岸卡操作员;(Tb_Com_Entity_Port_Card_Operator)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComEntityPortCardOperatorServiceImpl extends ServiceImpl<TbComEntityPortCardOperatorMapper, TbComEntityPortCardOperator>  implements TbComEntityPortCardOperatorService{
    @Resource
    private TbComEntityPortCardOperatorMapper tbComEntityPortCardOperatorMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityPortCardOperator queryById(BigDecimal pkCode){
        return tbComEntityPortCardOperatorMapper.selectById(pkCode);
    }
    
    /**
     * 分页查询
     *
     * @param tbComEntityPortCardOperator 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComEntityPortCardOperatorResult> paginQuery(TbComEntityPortCardOperatorParam tbComEntityPortCardOperator, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComEntityPortCardOperator> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComEntityPortCardOperator.getBusPortCardOperatorCardNo())){
            queryWrapper.eq(TbComEntityPortCardOperator::getBusPortCardOperatorCardNo, tbComEntityPortCardOperator.getBusPortCardOperatorCardNo());
        }
        //2. 执行分页查询
        Page<TbComEntityPortCardOperatorResult> pagin = new Page<>(current , size , true);
        IPage<TbComEntityPortCardOperatorResult> selectResult = tbComEntityPortCardOperatorMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComEntityPortCardOperator 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityPortCardOperator insert(TbComEntityPortCardOperator tbComEntityPortCardOperator){
        tbComEntityPortCardOperatorMapper.insert(tbComEntityPortCardOperator);
        return tbComEntityPortCardOperator;
    }
    
    /** 
     * 更新数据
     *
     * @param tbComEntityPortCardOperator 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityPortCardOperator update(TbComEntityPortCardOperator tbComEntityPortCardOperator){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComEntityPortCardOperator> chainWrapper = new LambdaUpdateChainWrapper<>(tbComEntityPortCardOperatorMapper);
        if(StrUtil.isNotBlank(tbComEntityPortCardOperator.getBusPortCardOperatorCardNo())){
            chainWrapper.set(TbComEntityPortCardOperator::getBusPortCardOperatorCardNo, tbComEntityPortCardOperator.getBusPortCardOperatorCardNo());
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComEntityPortCardOperator::getPkCode, tbComEntityPortCardOperator.getPkCode());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(tbComEntityPortCardOperator.getPkCode());
        }else{
            return tbComEntityPortCardOperator;
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
        int total = tbComEntityPortCardOperatorMapper.deleteById(pkCode);
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
         int delCount = tbComEntityPortCardOperatorMapper.deleteBatchIds(pkCodeList);
         if (pkCodeList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
}