package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.Account;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FinancialClassification;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.FinancialClassificationMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FinancialClassificationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.FinancialClassificationResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IFinancialClassificationService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
* <p>
* 财务分类表 服务实现类
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Service
@Slf4j
public class FinancialClassificationServiceImpl extends ServiceImpl<FinancialClassificationMapper, FinancialClassification> implements IFinancialClassificationService {

    @Resource
    private FinancialClassificationMapper mapper;

    private static final String STR_FORMAT = "0000";

    @DataSource(name = "finance")
    @Override
    public PageResult<FinancialClassificationResult> findPageBySpec(FinancialClassificationParam param) {

        Page pageContext = getPageContext();

        IPage<FinancialClassificationResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);
    }


    @DataSource(name = "finance")
    @Override
    public List<FinancialClassificationResult> exportFinancialClassificationList(FinancialClassificationParam param) {
        List<FinancialClassificationResult> page = this.baseMapper.exportFinancialClassificationList( param);
        return page;
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertBatch(List<FinancialClassification>   FinancialClassificationList) {
        this.saveBatch(FinancialClassificationList);
    }


    @DataSource(name = "erpcloud")
    @Override
    public List<Account> queryAccount(Account param) {
        return this.baseMapper.queryAccount(param);
    }

    @DataSource(name = "erpcloud")
    @Override
    public List<Account> queryAccountList() {
        return this.baseMapper.queryAccountList();
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(FinancialClassificationParam param) {

        FinancialClassification entity = new FinancialClassification();

        LoginContext current= SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        //流水码拼接编码
        param.setCostCode(param.getCostCode()+".");
        int waterCode = this.getWaterCode(param) + 1;

        String waterCodeString = this.formatSerialNumber(waterCode);

        entity.setWaterCode(waterCode);
        entity.setCostCode(param.getCostCode()+waterCodeString);
        entity.setWaterCode(waterCode);


        entity.setTradeType(param.getTradeType());
        entity.setAmountType(param.getAmountType());
        entity.setCostDescription(param.getCostDescription());
        entity.setClassificationType(param.getClassificationType());
        entity.setCostName(param.getCostName());
        entity.setCostNameCn(param.getCostNameCn());
        entity.setSite(param.getSite());
        entity.setFinancialClassification(param.getFinancialClassification());
        entity.setSubjectCode(param.getSubjectCode());
        entity.setSubjectClassificationOne(param.getSubjectClassificationOne());
        entity.setSubjectClassificationTwo(param.getSubjectClassificationTwo());
        entity.setSubjectName(param.getSubjectName());
        entity.setEditStatus(1);
        entity.setVerifyStatus(0);
        entity.setStatus(0);
        entity.setEditAt(new Date());
        entity.setEditBy(currentUser.getName());

        this.save(entity);

    }

    @DataSource(name = "finance")
    @Override
    public void addDataRange(FinancialClassificationParam param) {

        FinancialClassification entity = new FinancialClassification();

        LoginContext current= SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();


        //流水码拼接编码
        param.setCostCode(param.getCostCode()+".");
        int waterCode = this.getWaterCode(param) + 1;

        String waterCodeString = this.formatSerialNumber(waterCode);

        entity.setWaterCode(waterCode);
        entity.setCostCode(param.getCostCode()+waterCodeString);
        entity.setWaterCode(waterCode);

        entity.setTradeType(param.getTradeType());
        entity.setAmountType(param.getAmountType());
        entity.setCostDescription(param.getCostDescription());

        entity.setClassificationType(param.getClassificationType());
        entity.setCostName(param.getCostName());
        entity.setCostNameCn(param.getCostNameCn());
        entity.setSite(param.getSite());
        entity.setFinancialClassification(param.getFinancialClassification());
        entity.setSubjectCode(param.getSubjectCode());
        entity.setSubjectClassificationOne(param.getSubjectClassificationOne());
        entity.setSubjectClassificationTwo(param.getSubjectClassificationTwo());
        entity.setSubjectName(param.getSubjectName());
        entity.setEditStatus(1);
        entity.setVerifyStatus(0);
        entity.setStatus(0);
        entity.setEditAt(new Date());
        entity.setEditBy(currentUser.getName());
        this.save(entity);
    }

    @DataSource(name = "finance")
    @Override
    public void update(FinancialClassificationParam param) {

        LoginContext current= SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();


        FinancialClassification oldEntity = getOldEntity(param);

        //流水码拼接编码
        param.setCostCode(param.getCostCode()+".");
        int waterCode = this.getWaterCode(param) + 1;

        String waterCodeString = this.formatSerialNumber(waterCode);

        oldEntity.setCostCode(param.getCostCode()+waterCodeString);
        oldEntity.setWaterCode(waterCode);

        oldEntity.setCostNameCn(param.getCostNameCn());
        oldEntity.setSite(param.getSite());
        oldEntity.setFinancialClassification(param.getFinancialClassification());
        oldEntity.setSubjectCode(param.getSubjectCode());
        oldEntity.setSubjectClassificationOne(param.getSubjectClassificationOne());
        oldEntity.setSubjectClassificationTwo(param.getSubjectClassificationTwo());
        oldEntity.setSubjectName(param.getSubjectName());

        oldEntity.setEditStatus(1);
        oldEntity.setVerifyStatus(0);
        oldEntity.setEditAt(new Date());
        oldEntity.setEditBy(currentUser.getName());

        this.updateById(oldEntity);
    }


    @DataSource(name = "finance")
    @Override
    public void changeStatus(FinancialClassificationParam param) {

        LoginContext current= SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        FinancialClassification oldEntity = getOldEntity(param);


        oldEntity.setStatus(param.getStatus());
        oldEntity.setChangeForbidAt(new Date());
        oldEntity.setChangeForbidBy(currentUser.getName());

        //修改同费用名称的未禁用
        if(param.getStatus()==1){
           this.baseMapper.changeStatus(param);
        }

        this.updateById(oldEntity);
    }


    @DataSource(name = "finance")
    @Override
    public void verifySettlement(FinancialClassificationParam param) {

        LoginContext current= SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        FinancialClassification oldEntity = getOldEntity(param);

        oldEntity.setVerifyStatus(param.getVerifyStatus());
        oldEntity.setVerifyAt(new Date());
        oldEntity.setVerifyBy(currentUser.getName());
        //审核通过即启用,禁用同费用名分类
        if(param.getVerifyStatus()==1){
            oldEntity.setStatus(1);
            this.baseMapper.changeStatus(param);
        }

        this.updateById(oldEntity);
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData importExcel(MultipartFile file,List<Account> accountList) {
        log.info("导入Excel处理开始");
        String account = LoginContext.me().getLoginUser().getName();
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<FinancialClassification>();
            EasyExcel.read(buffer, FinancialClassification.class, listener).sheet()
                    .doRead();

            List<FinancialClassification> dataList = listener.getDataList();
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据为空，导入失败！");
            }

            //异常数据集合
            List<FinancialClassification> errorList = new ArrayList<>();
            this.validation(dataList,errorList,account,accountList);

            //批量保存更新表数据
            if(CollectionUtil.isNotEmpty(dataList)){
                if(CollectionUtil.isNotEmpty(errorList)){
                    String fileName = this.dealImportErrorList(errorList);
                    //部分导入成功
                    return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE,  fileName);
                }
                return ResponseData.success("导入成功！");
            }
            if(CollectionUtil.isNotEmpty(errorList)){
                String fileName = this.dealImportErrorList(errorList);
                //导入失败
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE,  fileName);

            }
            return ResponseData.success("导入失败！导入数据为空！");
        } catch (Exception e) {
            log.error("导入Excel处理异常，导入失败！", e);
            return ResponseData.error("导入Excel处理异常，导入失败！");
        } finally {
            if(buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入Excel关闭流异常", e);
                }
            }
        }
    }

    private void validation(List<FinancialClassification> dataList, List<FinancialClassification> errorList, String account,List<Account> accountList) {
        Iterator<FinancialClassification> iterator = dataList.listIterator();
        while(iterator.hasNext()) {
            FinancialClassification financialClassification = iterator.next();
            financialClassification.setCreateBy(account);
            financialClassification.setCreateAt(new Date());

            if(StringUtils.isEmpty(financialClassification.getClassificationType())
                    || StringUtils.isEmpty(financialClassification.getFinancialClassification())
                    || StringUtils.isEmpty(financialClassification.getSubjectClassificationOne())
                    || StringUtils.isEmpty(financialClassification.getSubjectClassificationTwo())
                    || financialClassification.getId()==null
            ){
                //不为空校验
                financialClassification.setUploadRemark("ID、分类类型、财务分类、科目分类一和科目分类二均不能为空");
                errorList.add(financialClassification);
                iterator.remove();
            } else {
                    //获取科目编码
                    String CostCode=null;
                    if (financialClassification.getClassificationType().equals("Settlement")){
                        String  KmCode = mapper.selectMaxByDictCode("SKMFL",
                                financialClassification.getSubjectClassificationOne());
                        String  CwCode= mapper.selectMaxByDictCode("SCWFL",
                                financialClassification.getFinancialClassification());
                        if(StrUtil.isNotEmpty(KmCode) && StrUtil.isNotEmpty(CwCode))
                        {
                            CostCode=KmCode+"."+CwCode;
                        }
                    }
                    if(financialClassification.getClassificationType().equals("DataRange")) {
                        String  KmCode = mapper.selectMaxByDictCode("DKMFL",
                                financialClassification.getSubjectClassificationOne());
                        String CwCode= mapper.selectMaxByDictCode("DCWFL",
                                financialClassification.getFinancialClassification());
                        if(StrUtil.isNotEmpty(KmCode) && StrUtil.isNotEmpty(CwCode))
                        {
                            CostCode=KmCode+"."+CwCode;
                        }
                    }
                    //编码为空判断
                    if(StrUtil.isEmpty(CostCode))
                    {
                        financialClassification.setUploadRemark("未匹配到科目分类一编码或财务分类编码!");
                        errorList.add(financialClassification);
                        iterator.remove();
                    }
                    else {

                        FinancialClassification fc=this.mapper.selectById(financialClassification.getId());
                        if(fc!=null)
                        {
                            //获取最大流水码序号
                            if(StrUtil.isEmpty(fc.getCostCode()))
                            {
                                int waterCode;
                                String watercode = mapper.selectMaxByWaterCode(financialClassification.getSubjectClassificationOne(),
                                        financialClassification.getFinancialClassification(), financialClassification.getClassificationType());
                                if (StrUtil.isNotEmpty(watercode)) {
                                    waterCode = Integer.parseInt(watercode) + 1;
                                    financialClassification.setWaterCode(waterCode);
                                }
                                else{
                                    waterCode = 1;
                                }
                                //流水码拼接编码
                                String waterCodeString = this.formatSerialNumber(waterCode);
                                financialClassification.setCostCode(CostCode + "." + waterCodeString);
                                financialClassification.setWaterCode(waterCode);
                            }
                            else
                            {
                                financialClassification.setWaterCode(fc.getWaterCode());
                                financialClassification.setStatus(fc.getStatus());
                            }
                            //科目编码
                            List<Account> ac=new ArrayList<>();
                            if(StrUtil.isNotEmpty(financialClassification.getSubjectName())){
                                ac=accountList.stream().filter(s->s.getAccountName().equals(financialClassification.getSubjectName())
                                ).collect(Collectors.toList());
                            }
                            else{
                                ac=accountList.stream().filter(s->s.getAccountName().equals(financialClassification.getSubjectClassificationTwo())
                                ).collect(Collectors.toList());
                            }
                            if(ac.size()>0)
                            {
                                financialClassification.setSubjectCode(ac.get(0).getAccountNumber());
                            }
                            else
                            {
                                financialClassification.setUploadRemark("未匹配到科目编码!");
                                errorList.add(financialClassification);
                                iterator.remove();
                            }
                            financialClassification.setEditStatus(1);
                            financialClassification.setVerifyStatus(0);
                            financialClassification.setEditAt(new Date());
                            financialClassification.setEditBy(account);
                            this.updateById(financialClassification);
                        }
                    }
            }
        }
    }

    private String dealImportErrorList(List<FinancialClassification> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, FinancialClassification.class)
                    .sheet("导入结果").doWrite(errorList);
        } catch (FileNotFoundException e) {
            log.error("导入Excel异常数据导出异常", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("导入Excel异常数据导出流关闭异常", e);
                }
            }
        }
        return fileName;
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    @DataSource(name = "finance")
    private int getWaterCode(FinancialClassificationParam param){
        List<FinancialClassificationResult> res = this.baseMapper.getMaxWaterCode(param);
        return res==null?0:res.get(0).getWaterCode();
    }

    //格式化流水号
    private static String formatSerialNumber(int cycleIndex){
        DecimalFormat df = new DecimalFormat(STR_FORMAT);
        return df.format(cycleIndex);
    }

    private FinancialClassification getOldEntity(FinancialClassificationParam param) {
        return this.getById(getKey(param));
    }

    private Serializable getKey(FinancialClassificationParam param) {
        return param.getId();
    }

    private FinancialClassification getEntity(FinancialClassificationParam param) {
        FinancialClassification entity = new FinancialClassification();
        BeanUtil.copyProperties(param, entity);
        return entity;
    }
}
