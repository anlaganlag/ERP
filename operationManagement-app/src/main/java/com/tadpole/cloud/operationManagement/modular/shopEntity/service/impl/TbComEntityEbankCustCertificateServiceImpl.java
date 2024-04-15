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
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityEbankCustCertificateParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityEbankCustCertificateResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityEbankCustCertificate;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComEntityEbankCustCertificateMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityEbankCustCertificateService;
 /**
 * 资源-公司实体银行设备电子银行客户证书;(Tb_Com_Entity_Ebank_Cust_Certificate)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComEntityEbankCustCertificateServiceImpl extends ServiceImpl<TbComEntityEbankCustCertificateMapper, TbComEntityEbankCustCertificate>  implements TbComEntityEbankCustCertificateService{
    @Resource
    private TbComEntityEbankCustCertificateMapper tbComEntityEbankCustCertificateMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityEbankCustCertificate queryById(BigDecimal pkCode){
        return tbComEntityEbankCustCertificateMapper.selectById(pkCode);
    }
    
    /**
     * 分页查询
     *
     * @param tbComEntityEbankCustCertificate 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComEntityEbankCustCertificateResult> paginQuery(TbComEntityEbankCustCertificateParam tbComEntityEbankCustCertificate, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComEntityEbankCustCertificate> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComEntityEbankCustCertificate.getBusEbankCustomerCertificateBank())){
            queryWrapper.eq(TbComEntityEbankCustCertificate::getBusEbankCustomerCertificateBank, tbComEntityEbankCustCertificate.getBusEbankCustomerCertificateBank());
        }
        if(StrUtil.isNotBlank(tbComEntityEbankCustCertificate.getBusEbankCustomerCertificateNo())){
            queryWrapper.eq(TbComEntityEbankCustCertificate::getBusEbankCustomerCertificateNo, tbComEntityEbankCustCertificate.getBusEbankCustomerCertificateNo());
        }
        if(StrUtil.isNotBlank(tbComEntityEbankCustCertificate.getBusEbankCustomerCertificateStatus())){
            queryWrapper.eq(TbComEntityEbankCustCertificate::getBusEbankCustomerCertificateStatus, tbComEntityEbankCustCertificate.getBusEbankCustomerCertificateStatus());
        }
        //2. 执行分页查询
        Page<TbComEntityEbankCustCertificateResult> pagin = new Page<>(current , size , true);
        IPage<TbComEntityEbankCustCertificateResult> selectResult = tbComEntityEbankCustCertificateMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComEntityEbankCustCertificate 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityEbankCustCertificate insert(TbComEntityEbankCustCertificate tbComEntityEbankCustCertificate){
        tbComEntityEbankCustCertificateMapper.insert(tbComEntityEbankCustCertificate);
        return tbComEntityEbankCustCertificate;
    }
    
    /** 
     * 更新数据
     *
     * @param tbComEntityEbankCustCertificate 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityEbankCustCertificate update(TbComEntityEbankCustCertificate tbComEntityEbankCustCertificate){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComEntityEbankCustCertificate> chainWrapper = new LambdaUpdateChainWrapper<>(tbComEntityEbankCustCertificateMapper);
        if(StrUtil.isNotBlank(tbComEntityEbankCustCertificate.getBusEbankCustomerCertificateBank())){
            chainWrapper.set(TbComEntityEbankCustCertificate::getBusEbankCustomerCertificateBank, tbComEntityEbankCustCertificate.getBusEbankCustomerCertificateBank());
        }
        if(StrUtil.isNotBlank(tbComEntityEbankCustCertificate.getBusEbankCustomerCertificateNo())){
            chainWrapper.set(TbComEntityEbankCustCertificate::getBusEbankCustomerCertificateNo, tbComEntityEbankCustCertificate.getBusEbankCustomerCertificateNo());
        }
        if(StrUtil.isNotBlank(tbComEntityEbankCustCertificate.getBusEbankCustomerCertificateStatus())){
            chainWrapper.set(TbComEntityEbankCustCertificate::getBusEbankCustomerCertificateStatus, tbComEntityEbankCustCertificate.getBusEbankCustomerCertificateStatus());
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComEntityEbankCustCertificate::getPkCode, tbComEntityEbankCustCertificate.getPkCode());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(tbComEntityEbankCustCertificate.getPkCode());
        }else{
            return tbComEntityEbankCustCertificate;
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
        int total = tbComEntityEbankCustCertificateMapper.deleteById(pkCode);
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
         int delCount = tbComEntityEbankCustCertificateMapper.deleteBatchIds(pkCodeList);
         if (pkCodeList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
}