package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.toolkit.MPJWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntity;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityCertificateSeal;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityCertificateSealParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.CertificateSealCountResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityCertificateSealResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComEntityCertificateSealMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComEntityMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityCertificateSealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 /**
 * 资源-公司实体证件印章;(Tb_Com_Entity_Certificate_Seal)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComEntityCertificateSealServiceImpl extends ServiceImpl<TbComEntityCertificateSealMapper, TbComEntityCertificateSeal>  implements TbComEntityCertificateSealService{
    @Resource
    private TbComEntityCertificateSealMapper tbComEntityCertificateSealMapper;

     @Resource
     private TbComEntityMapper tbComEntityMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityCertificateSeal queryById(BigDecimal pkCode){
        return tbComEntityCertificateSealMapper.selectById(pkCode);
    }
    
    /**
     * 分页查询
     *
     * @param tbComEntityCertificateSeal 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComEntityCertificateSealResult> paginQuery(TbComEntityCertificateSealParam tbComEntityCertificateSeal, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComEntityCertificateSeal> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComEntityCertificateSeal.getComNameCn())){
            queryWrapper.eq(TbComEntityCertificateSeal::getComNameCn, tbComEntityCertificateSeal.getComNameCn());
        }
        if(StrUtil.isNotBlank(tbComEntityCertificateSeal.getBusLastUpdDate())){
            queryWrapper.eq(TbComEntityCertificateSeal::getBusLastUpdDate, tbComEntityCertificateSeal.getBusLastUpdDate());
        }
        if(StrUtil.isNotBlank(tbComEntityCertificateSeal.getBusLastUpdPerName())){
            queryWrapper.eq(TbComEntityCertificateSeal::getBusLastUpdPerName, tbComEntityCertificateSeal.getBusLastUpdPerName());
        }
        if(StrUtil.isNotBlank(tbComEntityCertificateSeal.getBusLastUpdPerCode())){
            queryWrapper.eq(TbComEntityCertificateSeal::getBusLastUpdPerCode, tbComEntityCertificateSeal.getBusLastUpdPerCode());
        }
        if(StrUtil.isNotBlank(tbComEntityCertificateSeal.getBusApprovalNum())){
            queryWrapper.eq(TbComEntityCertificateSeal::getBusApprovalNum, tbComEntityCertificateSeal.getBusApprovalNum());
        }
        if(StrUtil.isNotBlank(tbComEntityCertificateSeal.getBusRegistrationFormNo())){
            queryWrapper.eq(TbComEntityCertificateSeal::getBusRegistrationFormNo, tbComEntityCertificateSeal.getBusRegistrationFormNo());
        }
        //2. 执行分页查询
        Page<TbComEntityCertificateSealResult> pagin = new Page<>(current , size , true);
        IPage<TbComEntityCertificateSealResult> selectResult = tbComEntityCertificateSealMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }



     @DataSource(name = "stocking")
     @Override
     @Transactional
     public Page<TbComEntityCertificateSealResult> queryPage(TbComEntityCertificateSealParam param, long current, long size){
         MPJLambdaWrapper<TbComEntityCertificateSeal> lambdaWrapper = getSealMPJLambdaWrapper(param);

         //2. 执行分页查询
         Page<TbComEntityCertificateSealResult> pagin = new Page<>(current , size , true);
         IPage<TbComEntityCertificateSealResult> selectResult = tbComEntityCertificateSealMapper.selectJoinPage(pagin ,TbComEntityCertificateSealResult.class, lambdaWrapper);
         pagin.setPages(selectResult.getPages());
         pagin.setTotal(selectResult.getTotal());
         pagin.setRecords(selectResult.getRecords());
         //3. 返回结果
         return pagin;
     }

     private static MPJLambdaWrapper<TbComEntityCertificateSeal> getSealMPJLambdaWrapper(TbComEntityCertificateSealParam param) {
         //1. 构建动态查询条件
         MPJLambdaWrapper<TbComEntityCertificateSeal> lambdaWrapper =  MPJWrappers.<TbComEntityCertificateSeal>lambdaJoin()
                 .select(TbComEntity::getComNameCn,TbComEntity::getComSource,TbComEntity::getComState,TbComEntity::getComUniSocCreCode,TbComEntity::getComCustomsNo,TbComEntity::getComLegPerson)
                 .select(TbComEntityCertificateSeal::getBusBasicAccountInfo,TbComEntityCertificateSeal::getBusForeignTradeRegist,TbComEntityCertificateSeal::getBusCustomsRegistCertificate,TbComEntityCertificateSeal::getBusLegalPersonSeal)
                 .select(TbComEntityCertificateSeal::getPkCode,TbComEntityCertificateSeal::getBusOfficialSeal,TbComEntityCertificateSeal::getBusFinancialChapter,TbComEntityCertificateSeal::getBusInvoiceSeal,TbComEntityCertificateSeal::getBusContractSpecialSeal,TbComEntityCertificateSeal::getBusWarehouseSpecialSeal,TbComEntityCertificateSeal::getBusLastUpdDate)
                 .select(TbComEntityCertificateSeal::getBusBusinessLicenseOrig,TbComEntityCertificateSeal::getBusBusinessLicenseDupl,TbComEntityCertificateSeal::getBusLastUpdPerName,TbComEntityCertificateSeal::getBusLastUpdPerCode)
//                 .selectAll(TbComEntityCertificateSeal.class)
                 .select(TbComEntityCertificateSeal::getBusApprovalNum,TbComEntityCertificateSeal::getBusRegistrationFormNo)
                 .selectAs(TbComEntity::getComUniSocCreCode, "comUniSocCreCodeOrg")
                 .selectAs(TbComEntity::getComUniSocCreCode, "comUniSocCreCodeDupl")

//                 .select("CASE WHEN t.bus_Business_License_Orig = 1 THEN t1.com_uni_soc_cre_code END comUniSocCreCodeOrg")
//                 .select("CASE WHEN t.bus_Business_License_Dupl = 1 THEN t1.com_uni_soc_cre_code END comUniSocCreCodeDupl")
//                 .select("CASE WHEN t.bus_Customs_Regist_Certificate = 1 THEN t1.com_Customs_No END com_Customs_No")
//                 .select("CASE WHEN t.bus_Legal_Person_Seal = 1 THEN t1.com_leg_person END com_leg_person")
//                 .select("CASE WHEN t.bus_basic_account_info = 1 THEN t.bus_Approval_Num END bus_Approval_Num")
//                 .select("CASE WHEN t.bus_foreign_trade_regist = 1 THEN t.bus_Registration_Form_No END bus_Registration_Form_No")
                 .leftJoin(TbComEntity.class,TbComEntity::getComNameCn,TbComEntityCertificateSeal::getComNameCn)
                 .like(ObjectUtil.isNotEmpty(param.getComNameCn()),TbComEntity::getComNameCn, param.getComNameCn())
                 .eq(ObjectUtil.isNotEmpty(param.getComSource()),TbComEntity::getComSource, param.getComSource())
                 .eq(ObjectUtil.isNotEmpty(param.getComState()),TbComEntity::getComState, param.getComState());
         return lambdaWrapper;
     }

     /**
     * 新增数据
     *
     * @param tbComEntityCertificateSeal 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityCertificateSeal insert(TbComEntityCertificateSeal tbComEntityCertificateSeal){
        tbComEntityCertificateSealMapper.insert(tbComEntityCertificateSeal);
        return tbComEntityCertificateSeal;
    }
    
    /** 
     * 更新数据
     *
     * @param entityParam 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityCertificateSeal update(TbComEntityCertificateSeal entityParam){
        //        公司状态为"注销"不可再编辑
        if (ObjectUtil.isEmpty(entityParam.getComNameCn())) {
            log.error("公司印章更新>>公司中文名为空");
            return null;
        }
        TbComEntity tbComEntity = new LambdaQueryChainWrapper<>(tbComEntityMapper)
                .eq(TbComEntity::getComNameCn, entityParam.getComNameCn()).one();
        if ("注销".equals(tbComEntity.getComState())) {
            log.error(StrUtil.format("公司印章更新>>注销公司[{}]不能为修改"),entityParam.getComNameCn());
            return null;
        }
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComEntityCertificateSeal> chainWrapper = new LambdaUpdateChainWrapper<>(tbComEntityCertificateSealMapper);
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getComNameCn()),TbComEntityCertificateSeal::getComNameCn, entityParam.getComNameCn());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getBusApprovalNum()),TbComEntityCertificateSeal::getBusApprovalNum, entityParam.getBusApprovalNum());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getBusRegistrationFormNo()),TbComEntityCertificateSeal::getBusRegistrationFormNo, entityParam.getBusRegistrationFormNo());

        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getBusBusinessLicenseOrig()),TbComEntityCertificateSeal::getBusBusinessLicenseOrig, entityParam.getBusBusinessLicenseOrig());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getBusBusinessLicenseDupl()),TbComEntityCertificateSeal::getBusBusinessLicenseDupl, entityParam.getBusBusinessLicenseDupl());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getBusBasicAccountInfo()),TbComEntityCertificateSeal::getBusBasicAccountInfo, entityParam.getBusBasicAccountInfo());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getBusForeignTradeRegist()),TbComEntityCertificateSeal::getBusForeignTradeRegist, entityParam.getBusForeignTradeRegist());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getBusCustomsRegistCertificate()),TbComEntityCertificateSeal::getBusCustomsRegistCertificate, entityParam.getBusCustomsRegistCertificate());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getBusOfficialSeal()),TbComEntityCertificateSeal::getBusOfficialSeal, entityParam.getBusOfficialSeal());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getBusFinancialChapter()),TbComEntityCertificateSeal::getBusFinancialChapter, entityParam.getBusFinancialChapter());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getBusLegalPersonSeal()),TbComEntityCertificateSeal::getBusLegalPersonSeal, entityParam.getBusLegalPersonSeal());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getBusInvoiceSeal()),TbComEntityCertificateSeal::getBusInvoiceSeal, entityParam.getBusInvoiceSeal());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getBusContractSpecialSeal()),TbComEntityCertificateSeal::getBusContractSpecialSeal, entityParam.getBusContractSpecialSeal());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getBusWarehouseSpecialSeal()),TbComEntityCertificateSeal::getBusWarehouseSpecialSeal, entityParam.getBusWarehouseSpecialSeal());
        //2. 设置主键，并更新
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
        chainWrapper.eq(TbComEntityCertificateSeal::getPkCode, entityParam.getPkCode());
        chainWrapper.set(TbComEntityCertificateSeal::getBusLastUpdDate, new Date());
        chainWrapper.set(TbComEntityCertificateSeal::getBusLastUpdPerName, name);
        chainWrapper.set(TbComEntityCertificateSeal::getBusLastUpdPerCode, account);
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(entityParam.getPkCode());
        }else{
            return entityParam;
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
        int total = tbComEntityCertificateSealMapper.deleteById(pkCode);
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
         int delCount = tbComEntityCertificateSealMapper.deleteBatchIds(pkCodeList);
         if (pkCodeList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }

     @DataSource(name = "stocking")
     @Override
     public List<CertificateSealCountResult> certificateSealCount(TbComEntityCertificateSealParam param) {

         MPJLambdaWrapper<TbComEntityCertificateSeal> lambdaWrapper = getSealMPJLambdaWrapper(param);

         List<TbComEntityCertificateSealResult> sealResultList = tbComEntityCertificateSealMapper.selectJoinList(TbComEntityCertificateSealResult.class, lambdaWrapper);
         if (ObjectUtil.isEmpty(sealResultList)) {
             return null;
         }
         List<CertificateSealCountResult> resultList = new ArrayList<>();
         sealResultList.forEach(r->{
             CertificateSealCountResult result = new CertificateSealCountResult();
             result.setComNameCN(r.getComNameCn());
             result.setComSource(r.getComSource());
             result.setComState(r.getComState());
             //营业执照正副本
             result.setBusBusinessLicenseOrigCount(ObjectUtil.isNotEmpty(r.getBusBusinessLicenseOrig()) ? r.getBusBusinessLicenseOrig() : BigDecimal.ZERO);
             result.setBusBusinessLicenseDuplCount(ObjectUtil.isNotEmpty(r.getBusBusinessLicenseDupl()) ? r.getBusBusinessLicenseDupl() : BigDecimal.ZERO);
             //基本存款账户信息
             result.setBusBasicAccountInfoCount(ObjectUtil.isNotEmpty(r.getBusBasicAccountInfo()) ? r.getBusBasicAccountInfo() : BigDecimal.ZERO); //是否有核准号
             //外经贸备案登记表
             result.setBusForeignTradeRegistCount(ObjectUtil.isNotEmpty(r.getBusForeignTradeRegist()) ? r.getBusForeignTradeRegist() : BigDecimal.ZERO); //备案登记表编号
             //海关注册证书
             result.setBusCustomsRegistCertificateCount(ObjectUtil.isNotEmpty(r.getBusCustomsRegistCertificate()) ? r.getBusCustomsRegistCertificate() : BigDecimal.ZERO);//海关注册编号
             //公章
             result.setBusOfficialSealCount(ObjectUtil.isNull(r.getBusOfficialSeal()) ? BigDecimal.ZERO :r.getBusOfficialSeal());
             //财务章
             result.setBusFinancialChapterCount(ObjectUtil.isNull(r.getBusFinancialChapter()) ? BigDecimal.ZERO :r.getBusFinancialChapter());
             //法人私章
             result.setBusLegalPersonSealCount(ObjectUtil.isNotNull(r.getBusLegalPersonSeal()) ? r.getBusLegalPersonSeal() :BigDecimal.ZERO);
             //发票章
             result.setBusInvoiceSealCount(ObjectUtil.isNull(r.getBusInvoiceSeal()) ? BigDecimal.ZERO :r.getBusInvoiceSeal());
             //合同专用章
             result.setBusContractSpecialSealCount(ObjectUtil.isNull(r.getBusContractSpecialSeal()) ? BigDecimal.ZERO :r.getBusContractSpecialSeal());
             //仓库专用章
             result.setBusWarehouseSpecialSealCount(ObjectUtil.isNull(r.getBusWarehouseSpecialSeal()) ? BigDecimal.ZERO :r.getBusWarehouseSpecialSeal());

             resultList.add(result);
         });

         return resultList;
     }
 }