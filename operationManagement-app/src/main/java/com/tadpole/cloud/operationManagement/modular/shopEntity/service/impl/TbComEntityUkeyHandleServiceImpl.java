package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityUkeyHandle;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComEntityUkeyHandleMapper;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityUkeyHandleParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityUkeyHandleResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityUkeyHandleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
 /**
 * 资源-公司实体银行设备银行UKEY经办信息;(Tb_Com_Entity_Ukey_Handle)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComEntityUkeyHandleServiceImpl extends ServiceImpl<TbComEntityUkeyHandleMapper, TbComEntityUkeyHandle>  implements TbComEntityUkeyHandleService{
    @Resource
    private TbComEntityUkeyHandleMapper tbComEntityUkeyHandleMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityUkeyHandle queryById(BigDecimal pkCode){
        return tbComEntityUkeyHandleMapper.selectById(pkCode);
    }
    
    /**
     * 分页查询
     *
     * @param tbComEntityUkeyHandle 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComEntityUkeyHandleResult> paginQuery(TbComEntityUkeyHandleParam tbComEntityUkeyHandle, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComEntityUkeyHandle> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComEntityUkeyHandle.getBusBankUkeyHandleBank())){
            queryWrapper.eq(TbComEntityUkeyHandle::getBusBankUkeyHandleBank, tbComEntityUkeyHandle.getBusBankUkeyHandleBank());
        }
        if(StrUtil.isNotBlank(tbComEntityUkeyHandle.getBusBankUkeyHandleNo())){
            queryWrapper.eq(TbComEntityUkeyHandle::getBusBankUkeyHandleNo, tbComEntityUkeyHandle.getBusBankUkeyHandleNo());
        }
        if(StrUtil.isNotBlank(tbComEntityUkeyHandle.getBusBankUkeyHandleStatus())){
            queryWrapper.eq(TbComEntityUkeyHandle::getBusBankUkeyHandleStatus, tbComEntityUkeyHandle.getBusBankUkeyHandleStatus());
        }
        //2. 执行分页查询
        Page<TbComEntityUkeyHandleResult> pagin = new Page<>(current , size , true);
        IPage<TbComEntityUkeyHandleResult> selectResult = tbComEntityUkeyHandleMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComEntityUkeyHandle 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityUkeyHandle insert(TbComEntityUkeyHandle tbComEntityUkeyHandle){
        tbComEntityUkeyHandleMapper.insert(tbComEntityUkeyHandle);
        return tbComEntityUkeyHandle;
    }
    
    /** 
     * 更新数据
     *
     * @param tbComEntityUkeyHandle 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityUkeyHandle update(TbComEntityUkeyHandle tbComEntityUkeyHandle){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComEntityUkeyHandle> chainWrapper = new LambdaUpdateChainWrapper<>(tbComEntityUkeyHandleMapper);
        if(StrUtil.isNotBlank(tbComEntityUkeyHandle.getBusBankUkeyHandleBank())){
            chainWrapper.set(TbComEntityUkeyHandle::getBusBankUkeyHandleBank, tbComEntityUkeyHandle.getBusBankUkeyHandleBank());
        }
        if(StrUtil.isNotBlank(tbComEntityUkeyHandle.getBusBankUkeyHandleNo())){
            chainWrapper.set(TbComEntityUkeyHandle::getBusBankUkeyHandleNo, tbComEntityUkeyHandle.getBusBankUkeyHandleNo());
        }
        if(StrUtil.isNotBlank(tbComEntityUkeyHandle.getBusBankUkeyHandleStatus())){
            chainWrapper.set(TbComEntityUkeyHandle::getBusBankUkeyHandleStatus, tbComEntityUkeyHandle.getBusBankUkeyHandleStatus());
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComEntityUkeyHandle::getPkCode, tbComEntityUkeyHandle.getPkCode());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(tbComEntityUkeyHandle.getPkCode());
        }else{
            return tbComEntityUkeyHandle;
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
        int total = tbComEntityUkeyHandleMapper.deleteById(pkCode);
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
         int delCount = tbComEntityUkeyHandleMapper.deleteBatchIds(pkCodeList);
         if (pkCodeList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
}