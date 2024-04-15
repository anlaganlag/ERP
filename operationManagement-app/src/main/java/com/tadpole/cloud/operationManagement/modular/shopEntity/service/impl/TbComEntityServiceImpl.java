package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.toolkit.MPJWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntity;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityAccount;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityBankEquipment;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityCertificateSeal;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComEntityCertificateSealMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComEntityMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityAccountService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityBankEquipmentService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityCertificateSealService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 资源-公司实体;(Tb_Com_Entity)--服务实现类
 *
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComEntityServiceImpl extends ServiceImpl<TbComEntityMapper, TbComEntity> implements TbComEntityService {
    @Resource
    private TbComEntityMapper tbComEntityMapper;


    @Resource
    private TbComEntityCertificateSealMapper tbComEntityCertificateSealMapper;
    @Resource
    private TbComEntityAccountService tbComEntityAccountService;

    @Resource
    private TbComEntityBankEquipmentService equipmentService;

    @Resource
    private TbComEntityCertificateSealService certificateSealService;





    /**
     * 通过ID查询单条数据
     *
     * @param comNameCn 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityResult queryById(String comNameCn) {
        TbComEntity tbComEntity = tbComEntityMapper.selectById(comNameCn);
        if (ObjectUtil.isNull(tbComEntity)) {
            return null;
        }
        TbComEntityResult entityResult = BeanUtil.copyProperties(tbComEntity, TbComEntityResult.class);
        LambdaQueryWrapper<TbComEntityAccount> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TbComEntityAccount::getComNameCn, comNameCn);
        List<TbComEntityAccount> accountList = tbComEntityAccountService.getBaseMapper().selectList(lambdaQueryWrapper);
        if (ObjectUtil.isNotEmpty(accountList)) {
            entityResult.setComEntityAccountList(accountList);
        }
        return entityResult;
    }

    /**
     * 分页查询
     *
     * @param tbComEntity 筛选条件
     * @param current     当前页码
     * @param size        每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComEntityResult> paginQuery(TbComEntityParam tbComEntity, long current, long size) {
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(tbComEntity.getComNameEn())) {
            queryWrapper.eq(TbComEntity::getComNameEn, tbComEntity.getComNameEn());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComArea())) {
            queryWrapper.eq(TbComEntity::getComArea, tbComEntity.getComArea());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComType())) {
            queryWrapper.eq(TbComEntity::getComType, tbComEntity.getComType());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComNature())) {
            queryWrapper.eq(TbComEntity::getComNature, tbComEntity.getComNature());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComUniSocCreCode())) {
            queryWrapper.eq(TbComEntity::getComUniSocCreCode, tbComEntity.getComUniSocCreCode());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComVatPayer())) {
            queryWrapper.eq(TbComEntity::getComVatPayer, tbComEntity.getComVatPayer());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComTaxCreRating())) {
            queryWrapper.eq(TbComEntity::getComTaxCreRating, tbComEntity.getComTaxCreRating());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComCustomsNo())) {
            queryWrapper.eq(TbComEntity::getComCustomsNo, tbComEntity.getComCustomsNo());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComCpbBondNo())) {
            queryWrapper.eq(TbComEntity::getComCpbBondNo, tbComEntity.getComCpbBondNo());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComImporterNo())) {
            queryWrapper.eq(TbComEntity::getComImporterNo, tbComEntity.getComImporterNo());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComLegPerson())) {
            queryWrapper.eq(TbComEntity::getComLegPerson, tbComEntity.getComLegPerson());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComSupervisor())) {
            queryWrapper.eq(TbComEntity::getComSupervisor, tbComEntity.getComSupervisor());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComShareholder1())) {
            queryWrapper.eq(TbComEntity::getComShareholder1, tbComEntity.getComShareholder1());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComShareProportion1())) {
            queryWrapper.eq(TbComEntity::getComShareProportion1, tbComEntity.getComShareProportion1());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComShareholder2())) {
            queryWrapper.eq(TbComEntity::getComShareholder2, tbComEntity.getComShareholder2());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComShareProportion2())) {
            queryWrapper.eq(TbComEntity::getComShareProportion2, tbComEntity.getComShareProportion2());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComShareholder3())) {
            queryWrapper.eq(TbComEntity::getComShareholder3, tbComEntity.getComShareholder3());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComShareProportion3())) {
            queryWrapper.eq(TbComEntity::getComShareProportion3, tbComEntity.getComShareProportion3());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComRegCapital())) {
            queryWrapper.eq(TbComEntity::getComRegCapital, tbComEntity.getComRegCapital());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComTermOfOperation())) {
            queryWrapper.eq(TbComEntity::getComTermOfOperation, tbComEntity.getComTermOfOperation());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComTel())) {
            queryWrapper.eq(TbComEntity::getComTel, tbComEntity.getComTel());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComAddrCn())) {
            queryWrapper.eq(TbComEntity::getComAddrCn, tbComEntity.getComAddrCn());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComAddrEn())) {
            queryWrapper.eq(TbComEntity::getComAddrEn, tbComEntity.getComAddrEn());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComZipCode())) {
            queryWrapper.eq(TbComEntity::getComZipCode, tbComEntity.getComZipCode());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComBusiScope())) {
            queryWrapper.eq(TbComEntity::getComBusiScope, tbComEntity.getComBusiScope());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComState())) {
            queryWrapper.eq(TbComEntity::getComState, tbComEntity.getComState());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComBusiLicense())) {
            queryWrapper.eq(TbComEntity::getComBusiLicense, tbComEntity.getComBusiLicense());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComSource())) {
            queryWrapper.eq(TbComEntity::getComSource, tbComEntity.getComSource());
        }
        if (StrUtil.isNotBlank(tbComEntity.getComTaxType())) {
            queryWrapper.eq(TbComEntity::getComTaxType, tbComEntity.getComTaxType());
        }

        if (StrUtil.isNotBlank(tbComEntity.getComNameCn())) {
            queryWrapper.eq(TbComEntity::getComNameCn, tbComEntity.getComNameCn());
        }

        String customSqlSegment = queryWrapper.getCustomSqlSegment();
        //2. 执行分页查询
        Page<TbComEntityResult> pagin = new Page<>(current, size, true);
        IPage<TbComEntityResult> selectResult = tbComEntityMapper.selectByPage(pagin, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

    /**
     * 新增数据
     *
     * @param tbComEntityParam 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public ResponseData insert(TbComEntityParam tbComEntityParam) {
        try {
            TbComEntity existingTbComEntity = new LambdaQueryChainWrapper<>(tbComEntityMapper)
                    .eq(TbComEntity::getComNameCn, tbComEntityParam.getComNameCn()).one();
            if (ObjectUtil.isNotEmpty(existingTbComEntity)) {
                return ResponseData.error(StrUtil.format("公司名重复:[{}]",tbComEntityParam.getComNameCn()));
            }

            List<TbComEntityAccount> accountList = tbComEntityParam.getComEntityAccountList();
            //公司实体账户信息
            if (ObjectUtil.isNotEmpty(accountList)) {
                tbComEntityAccountService.saveBatch(accountList);
            }
            TbComEntity tbComEntity = BeanUtil.copyProperties(tbComEntityParam, TbComEntity.class);
            tbComEntity.setComState("正常");
            tbComEntityMapper.insert(tbComEntity);

            //银行设备、主表
            TbComEntityBankEquipment bankEquipment = TbComEntityBankEquipment.builder().build();
            bankEquipment.setComNameCn(tbComEntity.getComNameCn());
            bankEquipment.setSysCreateTime(new Date());
            bankEquipment.setPkBeCode(BigDecimal.valueOf(IdWorker.getId()));
            equipmentService.insert(bankEquipment);

            //公司印章
            TbComEntityCertificateSeal certificateSeal = TbComEntityCertificateSeal.builder().build();
            certificateSeal.setComNameCn(tbComEntity.getComNameCn());
            certificateSeal.setSysCreateTime(new Date());
            certificateSeal.setPkCode(BigDecimal.valueOf(IdWorker.getId()));
            certificateSealService.insert(certificateSeal);


            //根据对应公司字段更新印章是否存在默认值
            LambdaUpdateChainWrapper<TbComEntityCertificateSeal> sealMapper = new LambdaUpdateChainWrapper<>(tbComEntityCertificateSealMapper);
            //a.营业执照正本和副本
            if (ObjectUtil.isNotEmpty(tbComEntityParam.getComUniSocCreCode())) {
                sealMapper
                        .set(TbComEntityCertificateSeal::getBusBusinessLicenseOrig, BigDecimal.ONE)
                        .set(TbComEntityCertificateSeal::getBusBusinessLicenseDupl, BigDecimal.ONE);
            }
            //b.海关注册编号
            if (ObjectUtil.isNotEmpty(tbComEntityParam.getComCustomsNo())) {
                sealMapper.set(TbComEntityCertificateSeal::getBusCustomsRegistCertificate, BigDecimal.ONE);
            }
            //c.法人私章
            if (ObjectUtil.isNotEmpty(tbComEntityParam.getComLegPerson())) {
                sealMapper.set(TbComEntityCertificateSeal::getBusLegalPersonSeal, BigDecimal.ONE);
            }
            sealMapper
                    .eq(TbComEntityCertificateSeal::getComNameCn,tbComEntityParam.getComNameCn() )
                    .set(TbComEntityCertificateSeal::getComNameCn,tbComEntityParam.getComNameCn() )
                    .update();

            return ResponseData.success(certificateSeal);
        }catch (Exception  e) {
            log.error(e.getMessage());
            log.error("公司实体新增异常stackTrace:\n{}", ExceptionUtils.getStackTrace(e));
            return ResponseData.error(StrUtil.format("公司实体新增异常{}",e.getMessage()));
        }
    }

    /**
     * 更新数据
     *
     * @param tbComEntityParam 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    @Transactional
    public ResponseData update(TbComEntityParam tbComEntityParam) {
        try {
            //校验正常,已注销公司实体不能修改
            TbComEntity tbComEntity = new LambdaQueryChainWrapper<>(tbComEntityMapper)
                    .eq(TbComEntity::getComNameCn, tbComEntityParam.getComNameCn()).one();
            if (ObjectUtil.isEmpty(tbComEntity)) {
                return ResponseData.error(StrUtil.format("当前公司:[{}]不存在", tbComEntityParam.getComNameCn()));
            }
            if ("注销".equals(tbComEntity.getComState())) {
                return ResponseData.error(StrUtil.format("当前公司:[{}]已注销", tbComEntityParam.getComNameCn()));
            }
            //1. 根据条件动态更新
            LambdaUpdateWrapper<TbComEntity> chainWrapper = new LambdaUpdateWrapper<>();
            if (StrUtil.isNotBlank(tbComEntityParam.getComNameEn())) {
                chainWrapper.set(TbComEntity::getComNameEn, tbComEntityParam.getComNameEn());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComArea())) {
                chainWrapper.set(TbComEntity::getComArea, tbComEntityParam.getComArea());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComType())) {
                chainWrapper.set(TbComEntity::getComType, tbComEntityParam.getComType());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComNature())) {
                chainWrapper.set(TbComEntity::getComNature, tbComEntityParam.getComNature());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComUniSocCreCode())) {
                chainWrapper.set(TbComEntity::getComUniSocCreCode, tbComEntityParam.getComUniSocCreCode());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComVatPayer())) {
                chainWrapper.set(TbComEntity::getComVatPayer, tbComEntityParam.getComVatPayer());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComTaxCreRating())) {
                chainWrapper.set(TbComEntity::getComTaxCreRating, tbComEntityParam.getComTaxCreRating());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComCustomsNo())) {
                chainWrapper.set(TbComEntity::getComCustomsNo, tbComEntityParam.getComCustomsNo());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComCpbBondNo())) {
                chainWrapper.set(TbComEntity::getComCpbBondNo, tbComEntityParam.getComCpbBondNo());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComImporterNo())) {
                chainWrapper.set(TbComEntity::getComImporterNo, tbComEntityParam.getComImporterNo());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComLegPerson())) {
                chainWrapper.set(TbComEntity::getComLegPerson, tbComEntityParam.getComLegPerson());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComSupervisor())) {
                chainWrapper.set(TbComEntity::getComSupervisor, tbComEntityParam.getComSupervisor());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComShareholder1())) {
                chainWrapper.set(TbComEntity::getComShareholder1, tbComEntityParam.getComShareholder1());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComShareProportion1())) {
                chainWrapper.set(TbComEntity::getComShareProportion1, tbComEntityParam.getComShareProportion1());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComShareholder2())) {
                chainWrapper.set(TbComEntity::getComShareholder2, tbComEntityParam.getComShareholder2());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComShareProportion2())) {
                chainWrapper.set(TbComEntity::getComShareProportion2, tbComEntityParam.getComShareProportion2());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComShareholder3())) {
                chainWrapper.set(TbComEntity::getComShareholder3, tbComEntityParam.getComShareholder3());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComShareProportion3())) {
                chainWrapper.set(TbComEntity::getComShareProportion3, tbComEntityParam.getComShareProportion3());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComRegCapital())) {
                chainWrapper.set(TbComEntity::getComRegCapital, tbComEntityParam.getComRegCapital());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComTermOfOperation())) {
                chainWrapper.set(TbComEntity::getComTermOfOperation, tbComEntityParam.getComTermOfOperation());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComTel())) {
                chainWrapper.set(TbComEntity::getComTel, tbComEntityParam.getComTel());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComAddrCn())) {
                chainWrapper.set(TbComEntity::getComAddrCn, tbComEntityParam.getComAddrCn());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComAddrEn())) {
                chainWrapper.set(TbComEntity::getComAddrEn, tbComEntityParam.getComAddrEn());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComZipCode())) {
                chainWrapper.set(TbComEntity::getComZipCode, tbComEntityParam.getComZipCode());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComBusiScope())) {
                chainWrapper.set(TbComEntity::getComBusiScope, tbComEntityParam.getComBusiScope());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComState())) {
                chainWrapper.set(TbComEntity::getComState, tbComEntityParam.getComState());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComBusiLicense())) {
                chainWrapper.set(TbComEntity::getComBusiLicense, tbComEntityParam.getComBusiLicense());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComSource())) {
                chainWrapper.set(TbComEntity::getComSource, tbComEntityParam.getComSource());
            }
            if (StrUtil.isNotBlank(tbComEntityParam.getComTaxType())) {
                chainWrapper.set(TbComEntity::getComTaxType, tbComEntityParam.getComTaxType());
            }
            if (ObjectUtil.isNotNull(tbComEntityParam.getComBondValDate())) {
                chainWrapper.set(TbComEntity::getComBondValDate, tbComEntityParam.getComBondValDate());
            }
            //2. 设置主键，并更新
            chainWrapper.eq(TbComEntity::getComNameCn, tbComEntityParam.getComNameCn());
            //
            tbComEntityMapper.update(null, chainWrapper);
            //更新accountList
            LambdaQueryWrapper<TbComEntityAccount> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(TbComEntityAccount::getComNameCn, tbComEntityParam.getComNameCn());
            tbComEntityAccountService.getBaseMapper().delete(lambdaQueryWrapper);
            if (ObjectUtil.isNotEmpty(tbComEntityParam.getComEntityAccountList())) {
                tbComEntityAccountService.saveBatch(tbComEntityParam.getComEntityAccountList());
            }

            //根据对应公司字段更新印章是否存在默认值
                LambdaUpdateChainWrapper<TbComEntityCertificateSeal> sealMapper = new LambdaUpdateChainWrapper<>(tbComEntityCertificateSealMapper);
            //a.营业执照正本和副本
            if (ObjectUtil.isNotEmpty(tbComEntityParam.getBusBusinessLicenseOrig())) {
                sealMapper
                        .set(TbComEntityCertificateSeal::getBusBusinessLicenseOrig, BigDecimal.ONE);
            }

            if (ObjectUtil.isNotEmpty(tbComEntityParam.getBusBusinessLicenseDupl())) {
                sealMapper
                        .set(TbComEntityCertificateSeal::getBusBusinessLicenseDupl, BigDecimal.ONE);
            }
            //b.海关注册编号
            if (ObjectUtil.isNotEmpty(tbComEntityParam.getComCustomsNo())) {
                sealMapper.set(TbComEntityCertificateSeal::getBusCustomsRegistCertificate, BigDecimal.ONE);
            }
            //c.法人私章
            if (ObjectUtil.isNotEmpty(tbComEntityParam.getComLegPerson())) {
                sealMapper.set(TbComEntityCertificateSeal::getBusLegalPersonSeal, BigDecimal.ONE);
            }
            sealMapper
                    .eq(TbComEntityCertificateSeal::getComNameCn,tbComEntityParam.getComNameCn() )
                    .set(TbComEntityCertificateSeal::getComNameCn,tbComEntityParam.getComNameCn() )
                    .update();


            //3. 更新成功了，查询最最对象返回
            return ResponseData.success(tbComEntityParam);
        }catch (Exception e){
            log.error("公司实体更新异常:"+e.getMessage());
            log.error("公司实体更新异常stackTrace:\n{}", ExceptionUtils.getStackTrace(e));
            return ResponseData.error("公司实体更新异常:"+e.getMessage());
        }

    }

    /**
     * 通过主键删除数据
     *
     * @param comNameCn 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(String comNameCn) {
        //逻辑删除 更改公司状态为注销
        LambdaUpdateWrapper<TbComEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TbComEntity::getComNameCn, comNameCn).set(TbComEntity::getComState, "注销");
        int total = tbComEntityMapper.update(null, updateWrapper);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param comNameCnList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<String> comNameCnList) {
        int delCount = tbComEntityMapper.deleteBatchIds(comNameCnList);
        if (comNameCnList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    /**
     * 分页查询
     *
     * @param param 筛选条件
     * @param current     当前页码
     * @param size        每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    @Transactional
    public Page<TbComEntityResult> paginQueryJoinTable(TbComEntityParam param, long current, long size) {


        MPJLambdaWrapper<TbComEntity> lambdaWrapper = MPJWrappers.<TbComEntity>lambdaJoin()
                .selectAll(TbComEntity.class)
                .selectCollection(TbComEntityAccount.class, TbComEntityResult::getComEntityAccountList)
                .leftJoin(TbComEntityAccount.class, TbComEntityAccount::getComNameCn, TbComEntity::getComNameCn)
                .like(ObjectUtil.isNotEmpty(param.getComNameCn()),TbComEntity::getComNameCn,param.getComNameCn())
                .eq(ObjectUtil.isNotEmpty(param.getComArea()),TbComEntity::getComArea,param.getComArea())
                .eq(ObjectUtil.isNotEmpty(param.getComTaxType()),TbComEntity::getComTaxType,param.getComTaxType())
                .eq(ObjectUtil.isNotEmpty(param.getComType()),TbComEntity::getComType,param.getComType())
                .eq(ObjectUtil.isNotEmpty(param.getComVatPayer()),TbComEntity::getComVatPayer,param.getComVatPayer())
                .eq(ObjectUtil.isNotEmpty(param.getComLegPerson()),TbComEntity::getComLegPerson,param.getComLegPerson());
        IPage<TbComEntityResult> iPage = tbComEntityMapper.selectJoinPage(new Page<>(current, size), TbComEntityResult.class,lambdaWrapper );
        iPage.getRecords().forEach(System.out::println);
        Page<TbComEntityResult> pagin = new Page<>();

        pagin.setPages(iPage.getPages());
        pagin.setTotal(iPage.getTotal());
        pagin.setRecords(iPage.getRecords());
        //3. 返回结果
        return pagin;
    }

    @DataSource(name = "stocking")
    @Override
    public List<String> comNameCnList(Boolean isNormal) {
        LambdaQueryWrapper<TbComEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(TbComEntity::getComNameCn);
        List<TbComEntity> entityList = tbComEntityMapper.selectList(queryWrapper);
        if (ObjectUtil.isEmpty(entityList)) {
            return null;
        }
        if (isNormal) {
            return entityList.stream().filter(i->"正常".equals(i.getComState())).map(TbComEntity::getComNameCn).collect(Collectors.toList());
        }
        return entityList.stream().map(TbComEntity::getComNameCn).collect(Collectors.toList());
    }

    @DataSource(name = "stocking")
    @Override
    public List<Object> comNameCnList() {
      return   this.baseMapper.selectList(null).stream().map(
                a->new TbComShopResult(a.getComNameCn(),a.getComAddrCn(),a.getComLegPerson())
        ).collect(Collectors.toList());

    }
}



