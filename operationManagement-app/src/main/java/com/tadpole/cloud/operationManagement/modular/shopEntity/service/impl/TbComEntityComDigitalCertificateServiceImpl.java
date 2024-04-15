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
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityComDigitalCertificateParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityComDigitalCertificateResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityComDigitalCertificate;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComEntityComDigitalCertificateMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityComDigitalCertificateService;
 /**
 * 资源-公司实体银行设备公司数字证书;(Tb_Com_Entity_Com_Digital_Certificate)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComEntityComDigitalCertificateServiceImpl extends ServiceImpl<TbComEntityComDigitalCertificateMapper, TbComEntityComDigitalCertificate>  implements TbComEntityComDigitalCertificateService{
    @Resource
    private TbComEntityComDigitalCertificateMapper tbComEntityComDigitalCertificateMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityComDigitalCertificate queryById(BigDecimal pkCode){
        return tbComEntityComDigitalCertificateMapper.selectById(pkCode);
    }
    
    /**
     * 分页查询
     *
     * @param tbComEntityComDigitalCertificate 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComEntityComDigitalCertificateResult> paginQuery(TbComEntityComDigitalCertificateParam tbComEntityComDigitalCertificate, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComEntityComDigitalCertificate> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComEntityComDigitalCertificate.getBusComDigitalCertificateNo())){
            queryWrapper.eq(TbComEntityComDigitalCertificate::getBusComDigitalCertificateNo, tbComEntityComDigitalCertificate.getBusComDigitalCertificateNo());
        }
        //2. 执行分页查询
        Page<TbComEntityComDigitalCertificateResult> pagin = new Page<>(current , size , true);
        IPage<TbComEntityComDigitalCertificateResult> selectResult = tbComEntityComDigitalCertificateMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComEntityComDigitalCertificate 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityComDigitalCertificate insert(TbComEntityComDigitalCertificate tbComEntityComDigitalCertificate){
        tbComEntityComDigitalCertificateMapper.insert(tbComEntityComDigitalCertificate);
        return tbComEntityComDigitalCertificate;
    }
    
    /** 
     * 更新数据
     *
     * @param tbComEntityComDigitalCertificate 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityComDigitalCertificate update(TbComEntityComDigitalCertificate tbComEntityComDigitalCertificate){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComEntityComDigitalCertificate> chainWrapper = new LambdaUpdateChainWrapper<>(tbComEntityComDigitalCertificateMapper);
        if(StrUtil.isNotBlank(tbComEntityComDigitalCertificate.getBusComDigitalCertificateNo())){
            chainWrapper.set(TbComEntityComDigitalCertificate::getBusComDigitalCertificateNo, tbComEntityComDigitalCertificate.getBusComDigitalCertificateNo());
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComEntityComDigitalCertificate::getPkCode, tbComEntityComDigitalCertificate.getPkCode());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(tbComEntityComDigitalCertificate.getPkCode());
        }else{
            return tbComEntityComDigitalCertificate;
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
        int total = tbComEntityComDigitalCertificateMapper.deleteById(pkCode);
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
         int delCount = tbComEntityComDigitalCertificateMapper.deleteBatchIds(pkCodeList);
         if (pkCodeList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
}