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
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityCorporateCaCertificateParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityCorporateCaCertificateResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityCorporateCaCertificate;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComEntityCorporateCaCertificateMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityCorporateCaCertificateService;
 /**
 * 资源-公司实体银行设备法人CA证书;(Tb_Com_Entity_Corporate_CA_Certificate)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComEntityCorporateCaCertificateServiceImpl extends ServiceImpl<TbComEntityCorporateCaCertificateMapper, TbComEntityCorporateCaCertificate>  implements TbComEntityCorporateCaCertificateService{
    @Resource
    private TbComEntityCorporateCaCertificateMapper tbComEntityCorporateCaCertificateMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityCorporateCaCertificate queryById(BigDecimal pkCode){
        return tbComEntityCorporateCaCertificateMapper.selectById(pkCode);
    }
    
    /**
     * 分页查询
     *
     * @param tbComEntityCorporateCaCertificate 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComEntityCorporateCaCertificateResult> paginQuery(TbComEntityCorporateCaCertificateParam tbComEntityCorporateCaCertificate, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComEntityCorporateCaCertificate> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComEntityCorporateCaCertificate.getBusCorporateCaCertificateLegal())){
            queryWrapper.eq(TbComEntityCorporateCaCertificate::getBusCorporateCaCertificateLegal, tbComEntityCorporateCaCertificate.getBusCorporateCaCertificateLegal());
        }
        if(StrUtil.isNotBlank(tbComEntityCorporateCaCertificate.getBusCorporateCaCertificateNo())){
            queryWrapper.eq(TbComEntityCorporateCaCertificate::getBusCorporateCaCertificateNo, tbComEntityCorporateCaCertificate.getBusCorporateCaCertificateNo());
        }
        //2. 执行分页查询
        Page<TbComEntityCorporateCaCertificateResult> pagin = new Page<>(current , size , true);
        IPage<TbComEntityCorporateCaCertificateResult> selectResult = tbComEntityCorporateCaCertificateMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComEntityCorporateCaCertificate 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityCorporateCaCertificate insert(TbComEntityCorporateCaCertificate tbComEntityCorporateCaCertificate){
        tbComEntityCorporateCaCertificateMapper.insert(tbComEntityCorporateCaCertificate);
        return tbComEntityCorporateCaCertificate;
    }
    
    /** 
     * 更新数据
     *
     * @param tbComEntityCorporateCaCertificate 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityCorporateCaCertificate update(TbComEntityCorporateCaCertificate tbComEntityCorporateCaCertificate){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComEntityCorporateCaCertificate> chainWrapper = new LambdaUpdateChainWrapper<>(tbComEntityCorporateCaCertificateMapper);
        if(StrUtil.isNotBlank(tbComEntityCorporateCaCertificate.getBusCorporateCaCertificateLegal())){
            chainWrapper.set(TbComEntityCorporateCaCertificate::getBusCorporateCaCertificateLegal, tbComEntityCorporateCaCertificate.getBusCorporateCaCertificateLegal());
        }
        if(StrUtil.isNotBlank(tbComEntityCorporateCaCertificate.getBusCorporateCaCertificateNo())){
            chainWrapper.set(TbComEntityCorporateCaCertificate::getBusCorporateCaCertificateNo, tbComEntityCorporateCaCertificate.getBusCorporateCaCertificateNo());
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComEntityCorporateCaCertificate::getPkCode, tbComEntityCorporateCaCertificate.getPkCode());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(tbComEntityCorporateCaCertificate.getPkCode());
        }else{
            return tbComEntityCorporateCaCertificate;
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
        int total = tbComEntityCorporateCaCertificateMapper.deleteById(pkCode);
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
         int delCount = tbComEntityCorporateCaCertificateMapper.deleteBatchIds(pkCodeList);
         if (pkCodeList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
}