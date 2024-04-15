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
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityUkeyAuthorizeParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityUkeyAuthorizeResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityUkeyAuthorize;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComEntityUkeyAuthorizeMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityUkeyAuthorizeService;
 /**
 * 资源-公司实体银行设备银行UKEY授权信息;(Tb_Com_Entity_Ukey_Authorize)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComEntityUkeyAuthorizeServiceImpl extends ServiceImpl<TbComEntityUkeyAuthorizeMapper, TbComEntityUkeyAuthorize>  implements TbComEntityUkeyAuthorizeService{
    @Resource
    private TbComEntityUkeyAuthorizeMapper tbComEntityUkeyAuthorizeMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityUkeyAuthorize queryById(BigDecimal pkCode){
        return tbComEntityUkeyAuthorizeMapper.selectById(pkCode);
    }
    
    /**
     * 分页查询
     *
     * @param tbComEntityUkeyAuthorize 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComEntityUkeyAuthorizeResult> paginQuery(TbComEntityUkeyAuthorizeParam tbComEntityUkeyAuthorize, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComEntityUkeyAuthorize> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComEntityUkeyAuthorize.getBusBankUkeyAuthorizeBank())){
            queryWrapper.eq(TbComEntityUkeyAuthorize::getBusBankUkeyAuthorizeBank, tbComEntityUkeyAuthorize.getBusBankUkeyAuthorizeBank());
        }
        if(StrUtil.isNotBlank(tbComEntityUkeyAuthorize.getBusBankUkeyAuthorizeNo())){
            queryWrapper.eq(TbComEntityUkeyAuthorize::getBusBankUkeyAuthorizeNo, tbComEntityUkeyAuthorize.getBusBankUkeyAuthorizeNo());
        }
        if(StrUtil.isNotBlank(tbComEntityUkeyAuthorize.getBusBankUkeyAuthorizeStatus())){
            queryWrapper.eq(TbComEntityUkeyAuthorize::getBusBankUkeyAuthorizeStatus, tbComEntityUkeyAuthorize.getBusBankUkeyAuthorizeStatus());
        }
        //2. 执行分页查询
        Page<TbComEntityUkeyAuthorizeResult> pagin = new Page<>(current , size , true);
        IPage<TbComEntityUkeyAuthorizeResult> selectResult = tbComEntityUkeyAuthorizeMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComEntityUkeyAuthorize 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityUkeyAuthorize insert(TbComEntityUkeyAuthorize tbComEntityUkeyAuthorize){
        tbComEntityUkeyAuthorizeMapper.insert(tbComEntityUkeyAuthorize);
        return tbComEntityUkeyAuthorize;
    }
    
    /** 
     * 更新数据
     *
     * @param tbComEntityUkeyAuthorize 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityUkeyAuthorize update(TbComEntityUkeyAuthorize tbComEntityUkeyAuthorize){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComEntityUkeyAuthorize> chainWrapper = new LambdaUpdateChainWrapper<>(tbComEntityUkeyAuthorizeMapper);
        if(StrUtil.isNotBlank(tbComEntityUkeyAuthorize.getBusBankUkeyAuthorizeBank())){
            chainWrapper.set(TbComEntityUkeyAuthorize::getBusBankUkeyAuthorizeBank, tbComEntityUkeyAuthorize.getBusBankUkeyAuthorizeBank());
        }
        if(StrUtil.isNotBlank(tbComEntityUkeyAuthorize.getBusBankUkeyAuthorizeNo())){
            chainWrapper.set(TbComEntityUkeyAuthorize::getBusBankUkeyAuthorizeNo, tbComEntityUkeyAuthorize.getBusBankUkeyAuthorizeNo());
        }
        if(StrUtil.isNotBlank(tbComEntityUkeyAuthorize.getBusBankUkeyAuthorizeStatus())){
            chainWrapper.set(TbComEntityUkeyAuthorize::getBusBankUkeyAuthorizeStatus, tbComEntityUkeyAuthorize.getBusBankUkeyAuthorizeStatus());
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComEntityUkeyAuthorize::getPkCode, tbComEntityUkeyAuthorize.getPkCode());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(tbComEntityUkeyAuthorize.getPkCode());
        }else{
            return tbComEntityUkeyAuthorize;
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
        int total = tbComEntityUkeyAuthorizeMapper.deleteById(pkCode);
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
         int delCount = tbComEntityUkeyAuthorizeMapper.deleteBatchIds(pkCodeList);
         if (pkCodeList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
}