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
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityPortCardLegalParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityPortCardLegalResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityPortCardLegal;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComEntityPortCardLegalMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityPortCardLegalService;
 /**
 * 资源-公司实体银行设备口岸卡法人;(Tb_Com_Entity_Port_Card_Legal)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComEntityPortCardLegalServiceImpl extends ServiceImpl<TbComEntityPortCardLegalMapper, TbComEntityPortCardLegal>  implements TbComEntityPortCardLegalService{
    @Resource
    private TbComEntityPortCardLegalMapper tbComEntityPortCardLegalMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityPortCardLegal queryById(BigDecimal pkCode){
        return tbComEntityPortCardLegalMapper.selectById(pkCode);
    }
    
    /**
     * 分页查询
     *
     * @param tbComEntityPortCardLegal 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComEntityPortCardLegalResult> paginQuery(TbComEntityPortCardLegalParam tbComEntityPortCardLegal, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComEntityPortCardLegal> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComEntityPortCardLegal.getBusPortCardLegalCardNo())){
            queryWrapper.eq(TbComEntityPortCardLegal::getBusPortCardLegalCardNo, tbComEntityPortCardLegal.getBusPortCardLegalCardNo());
        }
        //2. 执行分页查询
        Page<TbComEntityPortCardLegalResult> pagin = new Page<>(current , size , true);
        IPage<TbComEntityPortCardLegalResult> selectResult = tbComEntityPortCardLegalMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComEntityPortCardLegal 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityPortCardLegal insert(TbComEntityPortCardLegal tbComEntityPortCardLegal){
        tbComEntityPortCardLegalMapper.insert(tbComEntityPortCardLegal);
        return tbComEntityPortCardLegal;
    }
    
    /** 
     * 更新数据
     *
     * @param tbComEntityPortCardLegal 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityPortCardLegal update(TbComEntityPortCardLegal tbComEntityPortCardLegal){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComEntityPortCardLegal> chainWrapper = new LambdaUpdateChainWrapper<>(tbComEntityPortCardLegalMapper);
        if(StrUtil.isNotBlank(tbComEntityPortCardLegal.getBusPortCardLegalCardNo())){
            chainWrapper.set(TbComEntityPortCardLegal::getBusPortCardLegalCardNo, tbComEntityPortCardLegal.getBusPortCardLegalCardNo());
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComEntityPortCardLegal::getPkCode, tbComEntityPortCardLegal.getPkCode());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(tbComEntityPortCardLegal.getPkCode());
        }else{
            return tbComEntityPortCardLegal;
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
        int total = tbComEntityPortCardLegalMapper.deleteById(pkCode);
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
         int delCount = tbComEntityPortCardLegalMapper.deleteBatchIds(pkCodeList);
         if (pkCodeList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
}