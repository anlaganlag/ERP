package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.toolkit.MPJWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.*;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityBankEquipmentEditParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityBankEquipmentParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.BankEquipmentCountResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityBankEquipmentResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.*;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 资源-公司实体银行设备;(Tb_Com_Entity_Bank_Equipment)--服务实现类
 *
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComEntityBankEquipmentServiceImpl extends ServiceImpl<TbComEntityBankEquipmentMapper, TbComEntityBankEquipment> implements TbComEntityBankEquipmentService {
    @Resource
    private TbComEntityBankEquipmentMapper tbComEntityBankEquipmentMapper;

    //12个银行设备相关的Mapper
    @Resource
    private TbComEntitySettlementCardMapper tbComEntitySettlementCardMapper;

    @Resource
    private TbComEntityCipherMapper tbComEntityCipherMapper;


    @Resource
    private TbComEntityComDigitalCertificateMapper tbComEntityComDigitalCertificateMapper;


    @Resource
    private TbComEntityCorporateCaCertificateMapper tbComEntityCorporateCaCertificateMapper;


    @Resource
    private TbComEntityEbankCustCertificateMapper tbComEntityEbankCustCertificateMapper;

    @Resource
    private TbComEntityPortCardLegalMapper tbComEntityPortCardLegalMapper;


    @Resource
    private TbComEntityPortCardOperatorMapper tbComEntityPortCardOperatorMapper;


    @Resource
    private TbComEntityReceiptCardMapper tbComEntityReceiptCardMapper;


    @Resource
    private TbComEntityTaxControlPanelMapper tbComEntityTaxControlPanelMapper;

    @Resource
    private TbComEntityTaxControlUkeyMapper tbComEntityTaxControlUkeyMapper;


    @Resource
    private TbComEntityUkeyAuthorizeMapper tbComEntityUkeyAuthorizeMapper;


    @Resource
    private TbComEntityUkeyHandleMapper tbComEntityUkeyHandleMapper;


    @Resource
    private TbComEntityCipherService tbComEntityCipherService;

    @Resource
    private TbComEntityComDigitalCertificateService tbComEntityComDigitalCertificateService;

    @Resource
    private TbComEntityCorporateCaCertificateService tbComEntityCorporateCaCertificateService;

    @Resource
    private TbComEntityEbankCustCertificateService tbComEntityEbankCustCertificateService;

    @Resource
    private TbComEntityPortCardLegalService tbComEntityPortCardLegalService;

    @Resource
    private TbComEntityPortCardOperatorService tbComEntityPortCardOperatorService;

    @Resource
    private TbComEntityReceiptCardService tbComEntityReceiptCardService;

    @Resource
    private TbComEntitySettlementCardService tbComEntitySettlementCardService;

    @Resource
    private TbComEntityTaxControlPanelService tbComEntityTaxControlPanelService;

    @Resource
    private TbComEntityTaxControlUkeyService tbComEntityTaxControlUkeyService;

    @Resource
    private TbComEntityUkeyAuthorizeService tbComEntityUkeyAuthorizeService;

    @Resource
    private TbComEntityUkeyHandleService tbComEntityUkeyHandleService;


    /**
     * 通过ID查询单条数据
     *
     * @param pkBeCode 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityBankEquipment queryById(BigDecimal pkBeCode) {
        return tbComEntityBankEquipmentMapper.selectById(pkBeCode);
    }

    /**
     * 分页查询
     *
     * @param tbComEntityBankEquipment 筛选条件
     * @param current                  当前页码
     * @param size                     每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComEntityBankEquipmentResult> paginQuery(TbComEntityBankEquipmentParam tbComEntityBankEquipment, long current, long size) {
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComEntityBankEquipment> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(tbComEntityBankEquipment.getComNameCn())) {
            queryWrapper.eq(TbComEntityBankEquipment::getComNameCn, tbComEntityBankEquipment.getComNameCn());
        }
        if (StrUtil.isNotBlank(tbComEntityBankEquipment.getBusLastUpdDate())) {
            queryWrapper.eq(TbComEntityBankEquipment::getBusLastUpdDate, tbComEntityBankEquipment.getBusLastUpdDate());
        }
        if (StrUtil.isNotBlank(tbComEntityBankEquipment.getBusLastUpdPerName())) {
            queryWrapper.eq(TbComEntityBankEquipment::getBusLastUpdPerName, tbComEntityBankEquipment.getBusLastUpdPerName());
        }
        if (StrUtil.isNotBlank(tbComEntityBankEquipment.getBusLastUpdPerCode())) {
            queryWrapper.eq(TbComEntityBankEquipment::getBusLastUpdPerCode, tbComEntityBankEquipment.getBusLastUpdPerCode());
        }
        //2. 执行分页查询
        Page<TbComEntityBankEquipmentResult> pagin = new Page<>(current, size, true);
        IPage<TbComEntityBankEquipmentResult> selectResult = tbComEntityBankEquipmentMapper.selectByPage(pagin, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }


    /**
     * 分页查询
     *
     * @param "TbComEntityBankEquipmentParam"
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public ResponseData queryList(TbComEntityBankEquipmentParam param) {
        if (ObjectUtil.isEmpty(param.getComNameCn())) {
            return ResponseData.error("公司名称为空");
        }
        //1. 构建动态查询条件
        MPJLambdaWrapper<TbComEntityBankEquipment> lambdaWrapper = getMPJLambdaWrapper(param);


        //2. 执行分页查询
        List<TbComEntityBankEquipmentResult> selectResult = tbComEntityBankEquipmentMapper.selectJoinList(TbComEntityBankEquipmentResult.class, lambdaWrapper);

        //3. 返回结果
        return ResponseData.success(selectResult);
    }

    private static MPJLambdaWrapper<TbComEntityBankEquipment> getMPJLambdaWrapper(TbComEntityBankEquipmentParam param) {
        MPJLambdaWrapper<TbComEntityBankEquipment> lambdaWrapper = MPJWrappers.<TbComEntityBankEquipment>lambdaJoin()
                .selectAll(TbComEntityBankEquipment.class)
                .select(TbComEntity::getComState,TbComEntity::getComLegPerson)
                .leftJoin(TbComEntity.class, TbComEntity::getComNameCn, TbComEntityCertificateSeal::getComNameCn)
                .leftJoin(TbComEntitySettlementCard.class, TbComEntitySettlementCard::getPkBeCode, TbComEntityBankEquipment::getPkBeCode)
                .leftJoin(TbComEntityReceiptCard.class, TbComEntityReceiptCard::getPkBeCode, TbComEntityBankEquipment::getPkBeCode)
                .leftJoin(TbComEntityEbankCustCertificate.class, TbComEntityEbankCustCertificate::getPkBeCode, TbComEntityBankEquipment::getPkBeCode)
                .leftJoin(TbComEntityCipher.class, TbComEntityCipher::getPkBeCode, TbComEntityBankEquipment::getPkBeCode)
                .leftJoin(TbComEntityUkeyHandle.class, TbComEntityUkeyHandle::getPkBeCode, TbComEntityBankEquipment::getPkBeCode)
                .leftJoin(TbComEntityUkeyAuthorize.class, TbComEntityUkeyAuthorize::getPkBeCode, TbComEntityBankEquipment::getPkBeCode)
                .leftJoin(TbComEntityTaxControlUkey.class, TbComEntityTaxControlUkey::getPkBeCode, TbComEntityBankEquipment::getPkBeCode)
                .leftJoin(TbComEntityTaxControlPanel.class, TbComEntityTaxControlPanel::getPkBeCode, TbComEntityBankEquipment::getPkBeCode)
                .leftJoin(TbComEntityPortCardLegal.class, TbComEntityPortCardLegal::getPkBeCode, TbComEntityBankEquipment::getPkBeCode)
                .leftJoin(TbComEntityPortCardOperator.class, TbComEntityPortCardOperator::getPkBeCode, TbComEntityBankEquipment::getPkBeCode)
                .leftJoin(TbComEntityCorporateCaCertificate.class, TbComEntityCorporateCaCertificate::getPkBeCode, TbComEntityBankEquipment::getPkBeCode)
                .leftJoin(TbComEntityComDigitalCertificate.class, TbComEntityComDigitalCertificate::getPkBeCode, TbComEntityBankEquipment::getPkBeCode)
                .selectCollection(TbComEntitySettlementCard.class, TbComEntityBankEquipmentResult::getSettlementCardList)
                .selectCollection(TbComEntityReceiptCard.class, TbComEntityBankEquipmentResult::getReceiptCardList)
                .selectCollection(TbComEntityEbankCustCertificate.class, TbComEntityBankEquipmentResult::getEbankCustCertificateList)
                .selectCollection(TbComEntityCipher.class, TbComEntityBankEquipmentResult::getCipherList)
                .selectCollection(TbComEntityUkeyHandle.class, TbComEntityBankEquipmentResult::getUkeyHandleList)
                .selectCollection(TbComEntityUkeyAuthorize.class, TbComEntityBankEquipmentResult::getUkeyAuthorizeList)
                .selectCollection(TbComEntityTaxControlUkey.class, TbComEntityBankEquipmentResult::getTaxControlUkeyList)
                .selectCollection(TbComEntityTaxControlPanel.class, TbComEntityBankEquipmentResult::getTaxControlPanelList)
                .selectCollection(TbComEntityPortCardLegal.class, TbComEntityBankEquipmentResult::getPortCardLegalList)
                .selectCollection(TbComEntityPortCardOperator.class, TbComEntityBankEquipmentResult::getPortCardOperatorList)
                .selectCollection(TbComEntityCorporateCaCertificate.class, TbComEntityBankEquipmentResult::getCorporateCACertificateList)
                .selectCollection(TbComEntityComDigitalCertificate.class, TbComEntityBankEquipmentResult::getComDigitalCertificateList)
                .eq(ObjectUtil.isNotEmpty(param.getComNameCn()), TbComEntityBankEquipment::getComNameCn, param.getComNameCn())
                .eq(ObjectUtil.isNotEmpty(param.getComState()), TbComEntity::getComState, param.getComState());
        return lambdaWrapper;
    }



    private static MPJLambdaWrapper<TbComEntityBankEquipment> getMainMPJLambdaWrapper(TbComEntityBankEquipmentParam param) {
        MPJLambdaWrapper<TbComEntityBankEquipment> lambdaWrapper = MPJWrappers.<TbComEntityBankEquipment>lambdaJoin()
                .selectAll(TbComEntityBankEquipment.class)
                .eq(ObjectUtil.isNotEmpty(param.getComNameCn()), TbComEntityBankEquipment::getComNameCn, param.getComNameCn())
                .eq(ObjectUtil.isNotEmpty(param.getComState()), TbComEntity::getComState, param.getComState());
        return lambdaWrapper;
    }

    /**
     * 新增数据
     *
     * @param tbComEntityBankEquipment 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityBankEquipment insert(TbComEntityBankEquipment tbComEntityBankEquipment) {
        tbComEntityBankEquipmentMapper.insert(tbComEntityBankEquipment);
        return tbComEntityBankEquipment;
    }

    /**
     * 更新数据
     *
     * @param tbComEntityBankEquipment 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityBankEquipment update(TbComEntityBankEquipment tbComEntityBankEquipment) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComEntityBankEquipment> chainWrapper = new LambdaUpdateChainWrapper<>(tbComEntityBankEquipmentMapper);
        if (StrUtil.isNotBlank(tbComEntityBankEquipment.getComNameCn())) {
            chainWrapper.set(TbComEntityBankEquipment::getComNameCn, tbComEntityBankEquipment.getComNameCn());
        }
        if (StrUtil.isNotBlank(tbComEntityBankEquipment.getBusLastUpdDate())) {
            chainWrapper.set(TbComEntityBankEquipment::getBusLastUpdDate, tbComEntityBankEquipment.getBusLastUpdDate());
        }
        if (StrUtil.isNotBlank(tbComEntityBankEquipment.getBusLastUpdPerName())) {
            chainWrapper.set(TbComEntityBankEquipment::getBusLastUpdPerName, tbComEntityBankEquipment.getBusLastUpdPerName());
        }
        if (StrUtil.isNotBlank(tbComEntityBankEquipment.getBusLastUpdPerCode())) {
            chainWrapper.set(TbComEntityBankEquipment::getBusLastUpdPerCode, tbComEntityBankEquipment.getBusLastUpdPerCode());
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComEntityBankEquipment::getPkBeCode, tbComEntityBankEquipment.getPkBeCode());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if (ret) {
            return queryById(tbComEntityBankEquipment.getPkBeCode());
        } else {
            return tbComEntityBankEquipment;
        }
    }


    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TbComEntityBankEquipmentEditParam update(TbComEntityBankEquipmentEditParam param) {
        //校验主键不为空和登陆人不为
        BigDecimal pkBeCode = param.getPkBeCode();
        if (ObjectUtil.isEmpty(pkBeCode)) {
            log.error("银行设备编辑update>>>pkBeCode为空");
            throw new RuntimeException("银行设备编辑update>>>pkBeCode为空");
        }
        LoginUser loginUser = LoginContext.me().getLoginUser();
        if (ObjectUtil.isEmpty(loginUser)) {
            log.error("银行设备编辑update>>>无登陆用户");
            throw new RuntimeException("银行设备编辑update>>>无登陆用户");
        }
        String name = loginUser.getName();
        String account = loginUser.getAccount();


        if (ObjectUtil.isEmpty(account) || ObjectUtil.isEmpty(name)) {
            log.error("银行设备编辑update>>>登录人姓名或账号为空{}{}",account,name);
            throw new RuntimeException(StrUtil.format("银行设备编辑update>>>登录人姓名或账号为空{}{}",account,name));
        }

        //根据条件动态更新银行设备主表
        LambdaUpdateChainWrapper<TbComEntityBankEquipment> chainWrapper = new LambdaUpdateChainWrapper<>(tbComEntityBankEquipmentMapper);
        chainWrapper.eq(TbComEntityBankEquipment::getPkBeCode, pkBeCode);
        chainWrapper.set(TbComEntityBankEquipment::getBusLastUpdDate, new Date());
        chainWrapper.set(TbComEntityBankEquipment::getBusLastUpdPerName, name);
        chainWrapper.set(TbComEntityBankEquipment::getBusLastUpdPerCode, account);
        chainWrapper.update();

        //更新12个明细表(先删除再全量保存)
        //1.结算卡列表
        List<TbComEntitySettlementCard> settlementCardList = param.getSettlementCardList();
        settlementCardList.stream().forEach(i ->
        {
            i.setPkBeCode(pkBeCode);
            i.setPkCode(BigDecimal.valueOf(IdWorker.getId()));
        });
            new LambdaUpdateChainWrapper<>(tbComEntitySettlementCardMapper)
                    .eq(TbComEntitySettlementCard::getPkBeCode, pkBeCode)
                    .remove();
            tbComEntitySettlementCardService.saveBatch(settlementCardList);

        //2.密码器列表
        List<TbComEntityCipher> cipherList = param.getCipherList();
        cipherList.stream().forEach(i ->
        {
            i.setPkBeCode(pkBeCode);
            i.setPkCode(BigDecimal.valueOf(IdWorker.getId()));
        });
            new LambdaUpdateChainWrapper<>(tbComEntityCipherMapper)
                    .eq(TbComEntityCipher::getPkBeCode, pkBeCode)
                    .remove();
            tbComEntityCipherService.saveBatch(cipherList);

        //3.公司数字证书列表
        List<TbComEntityComDigitalCertificate> comDigitalCertificateList = param.getComDigitalCertificateList();
        comDigitalCertificateList.stream().forEach(i ->
        {
            i.setPkBeCode(pkBeCode);
            i.setPkCode(BigDecimal.valueOf(IdWorker.getId()));
        });
            new LambdaUpdateChainWrapper<>(tbComEntityComDigitalCertificateMapper)
                    .eq(TbComEntityComDigitalCertificate::getPkBeCode, pkBeCode)
                    .remove();
            tbComEntityComDigitalCertificateService.saveBatch(comDigitalCertificateList);

        //4.法人CA证书列表
        List<TbComEntityCorporateCaCertificate> corporateCACertificateList = param.getCorporateCACertificateList();
        corporateCACertificateList.stream().forEach(i ->
        {
            i.setPkBeCode(pkBeCode);
            i.setPkCode(BigDecimal.valueOf(IdWorker.getId()));
        });

            new LambdaUpdateChainWrapper<>(tbComEntityCorporateCaCertificateMapper)
                    .eq(TbComEntityCorporateCaCertificate::getPkBeCode, pkBeCode)
                    .remove();
            tbComEntityCorporateCaCertificateService.saveBatch(corporateCACertificateList);

        //5.电子银行客户证书列表
        List<TbComEntityEbankCustCertificate> ebankCustCertificateList = param.getEbankCustCertificateList();
        ebankCustCertificateList.stream().forEach(i ->
        {
            i.setPkBeCode(pkBeCode);
            i.setPkCode(BigDecimal.valueOf(IdWorker.getId()));
        });
            new LambdaUpdateChainWrapper<>(tbComEntityEbankCustCertificateMapper)
                    .eq(TbComEntityEbankCustCertificate::getPkBeCode, pkBeCode)
                    .remove();
            tbComEntityEbankCustCertificateService.saveBatch(ebankCustCertificateList);

        //6.口岸卡-法人列表
        List<TbComEntityPortCardLegal> portCardLegalList = param.getPortCardLegalList();
        portCardLegalList.stream().forEach(i ->
        {
            i.setPkBeCode(pkBeCode);
            i.setPkCode(BigDecimal.valueOf(IdWorker.getId()));
        });
            new LambdaUpdateChainWrapper<>(tbComEntityPortCardLegalMapper)
                    .eq(TbComEntityPortCardLegal::getPkBeCode, pkBeCode)
                    .remove();
            tbComEntityPortCardLegalService.saveBatch(portCardLegalList);

        //7.口岸卡-操作员列表
        List<TbComEntityPortCardOperator> portCardOperatorList = param.getPortCardOperatorList();
        portCardOperatorList.stream().forEach(i ->
        {
            i.setPkBeCode(pkBeCode);
            i.setPkCode(BigDecimal.valueOf(IdWorker.getId()));
        });
            new LambdaUpdateChainWrapper<>(tbComEntityPortCardOperatorMapper)
                    .eq(TbComEntityPortCardOperator::getPkBeCode, pkBeCode)
                    .remove();
            tbComEntityPortCardOperatorService.saveBatch(portCardOperatorList);

        //8.回单卡列表
        List<TbComEntityReceiptCard> receiptCardList = param.getReceiptCardList();
        receiptCardList.stream().forEach(i ->
        {
            i.setPkBeCode(pkBeCode);
            i.setPkCode(BigDecimal.valueOf(IdWorker.getId()));
        });
            new LambdaUpdateChainWrapper<>(tbComEntityReceiptCardMapper)
                    .eq(TbComEntityReceiptCard::getPkBeCode, pkBeCode)
                    .remove();
            tbComEntityReceiptCardService.saveBatch(receiptCardList);

        //9.税控盘列表
        List<TbComEntityTaxControlPanel> taxControlPanelList = param.getTaxControlPanelList();
        taxControlPanelList.stream().forEach(i ->
        {
            i.setPkBeCode(pkBeCode);
            i.setPkCode(BigDecimal.valueOf(IdWorker.getId()));
        });
        new LambdaUpdateChainWrapper<>(tbComEntityTaxControlPanelMapper)
                .eq(TbComEntityTaxControlPanel::getPkBeCode, pkBeCode)
                .remove();
        tbComEntityTaxControlPanelService.saveBatch(taxControlPanelList);

        //10.税控UKEY列表
        List<TbComEntityTaxControlUkey> taxControlUkeyList = param.getTaxControlUkeyList();
        taxControlUkeyList.stream().forEach(i ->
        {
            i.setPkBeCode(pkBeCode);
            i.setPkCode(BigDecimal.valueOf(IdWorker.getId()));
        });
            new LambdaUpdateChainWrapper<>(tbComEntityTaxControlUkeyMapper)
                    .eq(TbComEntityTaxControlUkey::getPkBeCode, pkBeCode)
                    .remove();
            tbComEntityTaxControlUkeyService.saveBatch(taxControlUkeyList);

        //11.银行UKEY-授权列表
        List<TbComEntityUkeyAuthorize> ukeyAuthorizeList = param.getUkeyAuthorizeList();
        ukeyAuthorizeList.stream().forEach(i ->
        {
            i.setPkBeCode(pkBeCode);
            i.setPkCode(BigDecimal.valueOf(IdWorker.getId()));
        });
            new LambdaUpdateChainWrapper<>(tbComEntityUkeyAuthorizeMapper)
                    .eq(TbComEntityUkeyAuthorize::getPkBeCode, pkBeCode)
                    .remove();
            tbComEntityUkeyAuthorizeService.saveBatch(ukeyAuthorizeList);

        //12.银行UKEY-经办列表
        List<TbComEntityUkeyHandle> ukeyHandleList = param.getUkeyHandleList();
        ukeyHandleList.stream().forEach(i ->
        {
            i.setPkBeCode(pkBeCode);
            i.setPkCode(BigDecimal.valueOf(IdWorker.getId()));
        });
            new LambdaUpdateChainWrapper<>(tbComEntityUkeyHandleMapper)
                    .eq(TbComEntityUkeyHandle::getPkBeCode, pkBeCode)
                    .remove();
            tbComEntityUkeyHandleService.saveBatch(ukeyHandleList);
        return param;
    }


    /**
     * 通过主键删除数据
     *
     * @param pkBeCode 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(BigDecimal pkBeCode) {
        int total = tbComEntityBankEquipmentMapper.deleteById(pkBeCode);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param pkBeCodeList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> pkBeCodeList) {
        int delCount = tbComEntityBankEquipmentMapper.deleteBatchIds(pkBeCodeList);
        if (pkBeCodeList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 银行设备统计
     *
     * @param param
     * @return
     */
    @DataSource(name = "stocking")
    @Override
    public List<BankEquipmentCountResult> bankEquipmentCount(TbComEntityBankEquipmentParam param) {
        //1. 构建动态查询条件
        MPJLambdaWrapper<TbComEntityBankEquipment> lambdaWrapper = getMPJLambdaWrapper(param);

        List<TbComEntityBankEquipmentResult> resultList = tbComEntityBankEquipmentMapper.selectJoinList(TbComEntityBankEquipmentResult.class, lambdaWrapper);
        List<BankEquipmentCountResult> countResultList = new ArrayList<>();
        if (ObjectUtil.isEmpty(resultList)) {
            return countResultList;
        }
        resultList.forEach(b -> {
            BankEquipmentCountResult result = new BankEquipmentCountResult();
            result.setComNameCN(b.getComNameCn());
            result.setComState(b.getComState());
            //结算卡
            result.setBusSettlementCardCount(ObjectUtil.isEmpty(b.getSettlementCardList()) ? 0 : b.getSettlementCardList().size());
            //回单卡
            result.setBusReceiptCardCount(ObjectUtil.isEmpty(b.getReceiptCardList()) ? 0 : b.getReceiptCardList().size());
            //电子银行客户证书
            result.setBusEbankCustomerCertificateCount(ObjectUtil.isEmpty(b.getEbankCustCertificateList()) ? 0 : b.getEbankCustCertificateList().size());
            //密码器列表
            result.setBusCipherCount(ObjectUtil.isEmpty(b.getCipherList()) ? 0 : b.getCipherList().size());
            //银行UKEY-经办
            result.setBusBankUkeyHandleCount(ObjectUtil.isEmpty(b.getUkeyHandleList()) ? 0 : b.getUkeyHandleList().size());
            //银行UKEY-授权
            result.setBusBankUkeyAuthorizeCount(ObjectUtil.isEmpty(b.getUkeyAuthorizeList()) ? 0 : b.getUkeyAuthorizeList().size());
            //税控UKEY
            result.setBusTaxControlUkeyCount(ObjectUtil.isEmpty(b.getTaxControlUkeyList()) ? 0 : b.getTaxControlUkeyList().size());
            //税控盘
            result.setBusTaxControlPanelCount(ObjectUtil.isEmpty(b.getTaxControlPanelList()) ? 0 : b.getTaxControlPanelList().size());
            //口岸卡-法人
            result.setBusPortCardLegalCount(ObjectUtil.isEmpty(b.getPortCardLegalList()) ? 0 : b.getPortCardLegalList().size());
            //口岸卡-操作员列表
            result.setBusPortCardOperatorCount(ObjectUtil.isEmpty(b.getPortCardOperatorList()) ? 0 : b.getPortCardOperatorList().size());
            //法人CA证书
            result.setBusCorporateCACertificateCount(ObjectUtil.isEmpty(b.getCorporateCACertificateList()) ? 0 : b.getCorporateCACertificateList().size());
            //公司数字证书
            result.setBusComDigitalCertificateCount(ObjectUtil.isEmpty(b.getComDigitalCertificateList()) ? 0 : b.getComDigitalCertificateList().size());

            countResultList.add(result);
        });

        return countResultList;
    }

    @DataSource(name = "stocking")
    @Override
    public Page<TbComEntityBankEquipmentResult> paginQuery(TbComEntityParam param, long current, long size) {

        MPJLambdaWrapper<TbComEntityBankEquipment> lambdaWrapper = MPJWrappers.<TbComEntityBankEquipment>lambdaJoin()
                .selectAll(TbComEntityBankEquipment.class)
                .select(TbComEntity::getComState,TbComEntity::getComLegPerson)
                .leftJoin(TbComEntity.class, TbComEntity::getComNameCn, TbComEntityBankEquipment::getComNameCn)
                .eq(ObjectUtil.isNotEmpty(param.getComNameCn()), TbComEntityBankEquipment::getComNameCn, param.getComNameCn())
                .eq(ObjectUtil.isNotEmpty(param.getComState()), TbComEntity::getComState, param.getComState());
        //2. 执行分页查询
        Page<TbComEntityBankEquipmentResult> pagin = new Page<>(current , size , true);
        IPage<TbComEntityBankEquipmentResult> selectResult = tbComEntityBankEquipmentMapper.selectJoinPage(pagin , TbComEntityBankEquipmentResult.class,lambdaWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;

    }


    public static void main(String[] args) {


        String inputString = "There are 42 apples and 36 oranges in the basket.";

        // Define the regular expression pattern to match numbers
        String pattern = "\\d+"; // \\d+ matches one or more digits

        // Create a Pattern object
        Pattern numberPattern = Pattern.compile(pattern);

        // Create a Matcher object to find the matches in the input string
        Matcher matcher = numberPattern.matcher(inputString);

        // Find the first match in the string
        if (matcher.find()) {
            int firstNumber = Integer.parseInt(matcher.group());

            // Find the second match if it exists
            if (matcher.find()) {
                int secondNumber = Integer.parseInt(matcher.group());
                System.out.println("First number: " + firstNumber);
                System.out.println("Second number: " + secondNumber);
            } else {
                System.out.println("Only one number found: " + firstNumber);
            }
        } else {
            System.out.println("No numbers found in the string.");
        }
    }
}

