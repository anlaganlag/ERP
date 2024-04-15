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
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityTaxControlUkeyParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityTaxControlUkeyResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityTaxControlUkey;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComEntityTaxControlUkeyMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityTaxControlUkeyService;
 /**
 * 资源-公司实体银行设备税控UKEY;(Tb_Com_Entity_Tax_Control_Ukey)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComEntityTaxControlUkeyServiceImpl extends ServiceImpl<TbComEntityTaxControlUkeyMapper, TbComEntityTaxControlUkey>  implements TbComEntityTaxControlUkeyService{
    @Resource
    private TbComEntityTaxControlUkeyMapper tbComEntityTaxControlUkeyMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityTaxControlUkey queryById(BigDecimal pkCode){
        return tbComEntityTaxControlUkeyMapper.selectById(pkCode);
    }
    
    /**
     * 分页查询
     *
     * @param tbComEntityTaxControlUkey 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComEntityTaxControlUkeyResult> paginQuery(TbComEntityTaxControlUkeyParam tbComEntityTaxControlUkey, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComEntityTaxControlUkey> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComEntityTaxControlUkey.getBusTaxControlUkeyNo())){
            queryWrapper.eq(TbComEntityTaxControlUkey::getBusTaxControlUkeyNo, tbComEntityTaxControlUkey.getBusTaxControlUkeyNo());
        }
        //2. 执行分页查询
        Page<TbComEntityTaxControlUkeyResult> pagin = new Page<>(current , size , true);
        IPage<TbComEntityTaxControlUkeyResult> selectResult = tbComEntityTaxControlUkeyMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComEntityTaxControlUkey 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityTaxControlUkey insert(TbComEntityTaxControlUkey tbComEntityTaxControlUkey){
        tbComEntityTaxControlUkeyMapper.insert(tbComEntityTaxControlUkey);
        return tbComEntityTaxControlUkey;
    }
    
    /** 
     * 更新数据
     *
     * @param tbComEntityTaxControlUkey 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityTaxControlUkey update(TbComEntityTaxControlUkey tbComEntityTaxControlUkey){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComEntityTaxControlUkey> chainWrapper = new LambdaUpdateChainWrapper<>(tbComEntityTaxControlUkeyMapper);
        if(StrUtil.isNotBlank(tbComEntityTaxControlUkey.getBusTaxControlUkeyNo())){
            chainWrapper.set(TbComEntityTaxControlUkey::getBusTaxControlUkeyNo, tbComEntityTaxControlUkey.getBusTaxControlUkeyNo());
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComEntityTaxControlUkey::getPkCode, tbComEntityTaxControlUkey.getPkCode());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(tbComEntityTaxControlUkey.getPkCode());
        }else{
            return tbComEntityTaxControlUkey;
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
        int total = tbComEntityTaxControlUkeyMapper.deleteById(pkCode);
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
         int delCount = tbComEntityTaxControlUkeyMapper.deleteBatchIds(pkCodeList);
         if (pkCodeList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
}