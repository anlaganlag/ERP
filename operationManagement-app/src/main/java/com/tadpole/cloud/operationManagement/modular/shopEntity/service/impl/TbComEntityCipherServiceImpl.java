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
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityCipherParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityCipherResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityCipher;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComEntityCipherMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityCipherService;
 /**
 * 资源-公司实体银行设备密码器;(Tb_Com_Entity_Cipher)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComEntityCipherServiceImpl extends ServiceImpl<TbComEntityCipherMapper, TbComEntityCipher>  implements TbComEntityCipherService{
    @Resource
    private TbComEntityCipherMapper tbComEntityCipherMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityCipher queryById(BigDecimal pkCode){
        return tbComEntityCipherMapper.selectById(pkCode);
    }
    
    /**
     * 分页查询
     *
     * @param tbComEntityCipher 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComEntityCipherResult> paginQuery(TbComEntityCipherParam tbComEntityCipher, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComEntityCipher> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComEntityCipher.getBusCipherBank())){
            queryWrapper.eq(TbComEntityCipher::getBusCipherBank, tbComEntityCipher.getBusCipherBank());
        }
        if(StrUtil.isNotBlank(tbComEntityCipher.getBusCipherNo())){
            queryWrapper.eq(TbComEntityCipher::getBusCipherNo, tbComEntityCipher.getBusCipherNo());
        }
        if(StrUtil.isNotBlank(tbComEntityCipher.getBusCipherStatus())){
            queryWrapper.eq(TbComEntityCipher::getBusCipherStatus, tbComEntityCipher.getBusCipherStatus());
        }
        //2. 执行分页查询
        Page<TbComEntityCipherResult> pagin = new Page<>(current , size , true);
        IPage<TbComEntityCipherResult> selectResult = tbComEntityCipherMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComEntityCipher 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityCipher insert(TbComEntityCipher tbComEntityCipher){
        tbComEntityCipherMapper.insert(tbComEntityCipher);
        return tbComEntityCipher;
    }
    
    /** 
     * 更新数据
     *
     * @param tbComEntityCipher 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityCipher update(TbComEntityCipher tbComEntityCipher){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComEntityCipher> chainWrapper = new LambdaUpdateChainWrapper<>(tbComEntityCipherMapper);
        if(StrUtil.isNotBlank(tbComEntityCipher.getBusCipherBank())){
            chainWrapper.set(TbComEntityCipher::getBusCipherBank, tbComEntityCipher.getBusCipherBank());
        }
        if(StrUtil.isNotBlank(tbComEntityCipher.getBusCipherNo())){
            chainWrapper.set(TbComEntityCipher::getBusCipherNo, tbComEntityCipher.getBusCipherNo());
        }
        if(StrUtil.isNotBlank(tbComEntityCipher.getBusCipherStatus())){
            chainWrapper.set(TbComEntityCipher::getBusCipherStatus, tbComEntityCipher.getBusCipherStatus());
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComEntityCipher::getPkCode, tbComEntityCipher.getPkCode());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(tbComEntityCipher.getPkCode());
        }else{
            return tbComEntityCipher;
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
        int total = tbComEntityCipherMapper.deleteById(pkCode);
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
         int delCount = tbComEntityCipherMapper.deleteBatchIds(pkCodeList);
         if (pkCodeList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
}