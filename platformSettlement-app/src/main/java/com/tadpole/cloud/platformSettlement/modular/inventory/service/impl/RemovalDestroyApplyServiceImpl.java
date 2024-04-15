package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.api.ebms.model.resp.EbmsUserInfo;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.InventoryRejectVO;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.RemovalDestroyApply;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.RemovalDestroyBaseApply;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemovalDestroyApplyImportParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemovalDestroyApplyParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemovalDestroyBaseApplyParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.RemovalDestroyApplyResult;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.RemovalDestroyBaseApplyResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.consumer.EbmsBaseConsumer;
import com.tadpole.cloud.platformSettlement.modular.inventory.enums.ApplicationTypeEnum;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.RemovalDestroyApplyMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.RemovalDestroyBaseApplyMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IRemovalDestroyApplyService;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IRemovalDestroyBaseApplyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* <p>
*  服务实现类
* </p>
*
* @author cyt
* @since 2022-05-24
*/
@Service
@Slf4j
public class RemovalDestroyApplyServiceImpl extends ServiceImpl<RemovalDestroyApplyMapper, RemovalDestroyApply> implements IRemovalDestroyApplyService {

    @Autowired
    private RemovalDestroyApplyMapper mapper;
    @Autowired
    private IRemovalDestroyBaseApplyService service;
    @Autowired
    private EbmsBaseConsumer ebmsBaseConsumer;
    @Resource
    private RemovalDestroyBaseApplyMapper applymapper;

    @DataSource(name = "warehouse")
    @Override
    public PageResult<RemovalDestroyApplyResult> findPageBySpec(RemovalDestroyApplyParam param) {
        Page pageContext = param.getPageContext();
        IPage<RemovalDestroyApplyResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "warehouse")
    @Override
    public List<RemovalDestroyApplyResult> getApplyDetail(RemovalDestroyApplyParam param) {
        return this.baseMapper.getApplyDetail(param);
    }

    @DataSource(name = "warehouse")
    @Override
    public RemovalDestroyApplyResult getQuantity(RemovalDestroyApplyParam param) {
        return this.baseMapper.getQuantity(param);
    }

    @DataSource(name = "warehouse")
    @Override
    public List<Map<String, Object>> getDepartmentSelect() {
        QueryWrapper<RemovalDestroyBaseApply> removalWrapper = new QueryWrapper<>();
        removalWrapper = removalWrapper.select("APPLY_DEPARTMENT").groupBy("APPLY_DEPARTMENT");
        return transformLowerCase(applymapper.selectMaps(removalWrapper));
    }

    @DataSource(name = "warehouse")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateDetailList() throws IOException {
        this.baseMapper.updateDetailList();
    }

    @DataSource(name = "warehouse")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateFileDetailList() throws IOException{
        this.baseMapper.updateFileDetailList();
    }

    @DataSource(name = "warehouse")
    @Override
    public List<RemovalDestroyApplyResult> export(RemovalDestroyApplyParam param) {
        List<RemovalDestroyApplyResult> page = this.baseMapper.getApplyDetail(param);
        return page;
    }

    @DataSource(name = "warehouse")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData importRemovalApply(RemovalDestroyBaseApplyParam param,MultipartFile file) {

        String applyCode=param.getApplyCode();

        //新增流程编号，导入保存基础信息
        RemovalDestroyBaseApply removalApply=new RemovalDestroyBaseApply();

        QueryWrapper<RemovalDestroyBaseApply> qw = new QueryWrapper<>();
        qw.eq("APPLY_CODE", param.getApplyCode());
        if (service.count(qw) == 0) {
            //流程编号生成
            String DateStr= DateUtil.format(DateUtil.date(),"yyyyMMdd");
            String applyNo=this.baseMapper.selectByApplyNo("KCYC");
            String flowCode= getNewApplyNo("KCYC",applyNo,DateStr);
            //返回基础信息
            removalApply.setApplyCode(flowCode);
            removalApply.setApplyTitle(param.getApplyTitle());
            removalApply.setApplyName(param.getApplyName());
            removalApply.setApplyUserAccount(param.getApplyUserAccount());
            removalApply.setApplyUserName(param.getApplyUserName());
            removalApply.setApplyDepartment(param.getApplyDepartment());
            removalApply.setApplyDate(DateUtil.parse(param.getApplyDate()));
            removalApply.setAuditStatus("1");
            removalApply.setIncomeRemark(param.getIncomeRemark());
            removalApply.setCreateBy(LoginContext.me().getLoginUser().getName());
            removalApply.setCreateTime(new Date());
            service.save(removalApply);
        }
        if (StrUtil.isNotEmpty(removalApply.getApplyCode())){
            applyCode=removalApply.getApplyCode();
        }

        //创建ArrayList集合对象
        ArrayList<String> array = new ArrayList<String>();

        //导入库存明细
        BufferedInputStream buffer = null;
        List<RemovalDestroyApply> applyList=new ArrayList<>();
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(file.getInputStream()));
            String lineTxt = null;

            while ((lineTxt = bufferedReader.readLine()) != null) {
                //明细空行
                if(lineTxt.equals("")){ continue; }
                //把读取到的字符串存储到集合中
                array.add(lineTxt);
            }
            bufferedReader.close();

            String[] removalOrderId=array.get(0).split("\\t");
            String[] Site=array.get(7).split("\\t");

            //效验
            if(removalOrderId.length<2 || StrUtil.isEmpty(removalOrderId[1])){
                return ResponseData.error("订单号不能为空！");
            }
            if(Site.length<2 || StrUtil.isEmpty(Site[1])){
                return ResponseData.error("站点不能为空！");
            }

            //正则匹配字符：截取Removal_Order_ID单号中的账号字段
            String shopName=removalOrderId[1];
            Matcher m = Pattern.compile("[0-9]").matcher(shopName);
            if(m.find()){
                int numberIndex=m.start();  //数字起始位置
                shopName=StrUtil.sub(removalOrderId[1],1,numberIndex);
            }

            //遍历集合
            for(int k=14;k<array.size();k++){
                BigDecimal totalPrice=BigDecimal.ZERO;
                BigDecimal unitPrice=BigDecimal.ZERO;
                RemovalDestroyApply applyBean=new RemovalDestroyApply();
                applyBean.setApplyCode(applyCode);
                applyBean.setSysShopsName(shopName);
                applyBean.setSysSite(Site[1]);
                applyBean.setOrderId(removalOrderId[1]);
                applyBean.setCreateBy(LoginContext.me().getLoginUser().getName());
                applyBean.setCreateTime(new Date());

                String[] detail=array.get(k).split("\\t");
                int applyQty=Integer.valueOf(detail[1])+Integer.valueOf(detail[2]);
                applyBean.setMerchantSku(detail[0]);
                applyBean.setApplyQuantity(Integer.valueOf(detail[1])+Integer.valueOf(detail[2]));

                if(detail.length>4 && StrUtil.isNotEmpty(detail[4])){
                    //收购单价计算
                    totalPrice=new BigDecimal(detail[4]);
                    applyBean.setTotalPrice(totalPrice);
                    applyBean.setUnitPrice(totalPrice.divide(new BigDecimal(applyQty),2,BigDecimal.ROUND_HALF_UP));
                }
                if(detail.length>3 && StrUtil.isNotEmpty(detail[3])){
                    //总价计算
                    unitPrice=new BigDecimal(detail[3]);
                    applyBean.setUnitPrice(unitPrice);
                    applyBean.setTotalPrice(unitPrice.multiply(new BigDecimal(applyQty)).setScale(2,BigDecimal.ROUND_HALF_UP));
                }

                applyList.add(applyBean);
            }
            //导入库存明细，删除历史数据
            if(CollectionUtil.isNotEmpty(applyList)){
                mapper.deleteByDestroyApply(applyCode);
            }

            //保存集合
            this.saveBatch(applyList);
            return ResponseData.success(applyCode);
        } catch (Exception e) {
            log.error("导入文件处理异常，导入失败！", e);
            return ResponseData.error("导入文件处理异常，导入失败！");
        } finally {
            if(buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入文件关闭流异常", e);
                }
            }
        }

    }

    @DataSource(name = "warehouse")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData uploadBatchRemoval(RemovalDestroyBaseApplyParam param, MultipartFile file) {
        log.info("库存移除至服务商申请批量导入，请求参数[{}]", JSONObject.toJSON(param));
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<RemovalDestroyApplyImportParam>();
            EasyExcel.read(buffer, RemovalDestroyApplyImportParam.class, listener).sheet()
                    .doRead();

            List<RemovalDestroyApplyImportParam> dataList = listener.getDataList();
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("库存移除至服务商申请批量导入数据为空，导入失败！");
            }
            log.info("库存移除至服务商申请批量导入，导入参数[{}]", JSONObject.toJSON(dataList));
            List<RemovalDestroyApplyImportParam> errorList = new ArrayList<>();
            this.validUploadRemark(dataList, errorList);

            //存在异常数据，则返回异常信息
            if(CollectionUtil.isNotEmpty(errorList)){
                String fileName = this.dealImportErrorList(dataList);
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "存在异常数据，导入失败！", fileName);
            }else{
                //处理数据
                String applyCode = param.getApplyCode();
                //新增流程编号，导入保存基础信息
                RemovalDestroyBaseApply removalApply = new RemovalDestroyBaseApply();
                QueryWrapper<RemovalDestroyBaseApply> qw = new QueryWrapper<>();
                qw.eq("APPLY_CODE", applyCode);
                if (service.count(qw) == 0) {
                    //流程编号生成
                    String DateStr= DateUtil.format(DateUtil.date(),"yyyyMMdd");
                    String applyNo=this.baseMapper.selectByApplyNo("KCYC");
                    String flowCode= getNewApplyNo("KCYC",applyNo,DateStr);
                    //返回基础信息
                    removalApply.setApplyCode(flowCode);
                    removalApply.setApplyTitle(param.getApplyTitle());
                    removalApply.setApplyName(param.getApplyName());
                    removalApply.setApplyUserAccount(param.getApplyUserAccount());
                    removalApply.setApplyUserName(param.getApplyUserName());
                    removalApply.setApplyDepartment(param.getApplyDepartment());
                    removalApply.setApplyDate(DateUtil.parse(param.getApplyDate()));
                    removalApply.setAuditStatus("1");
                    removalApply.setIncomeRemark(param.getIncomeRemark());
                    removalApply.setCreateBy(LoginContext.me().getLoginUser().getName());
                    removalApply.setCreateTime(DateUtil.date());
                    service.save(removalApply);

                    applyCode = removalApply.getApplyCode();
                }

                List<RemovalDestroyApply> applyList = new ArrayList<>();
                for (RemovalDestroyApplyImportParam importParam : dataList) {
                    //收购单价不为空且总价为空，则计算得出总价：【总价】=【收购单价】*【申请数量】
                    if(importParam.getUnitPrice() != null && importParam.getTotalPrice() == null){
                        importParam.setTotalPrice(importParam.getUnitPrice().multiply(new BigDecimal(importParam.getApplyQuantity()).setScale(2, BigDecimal.ROUND_HALF_UP)));
                    }
                    //总价不为空且收购单价为空，则计算得出收购单价：【收购单价】=【总价】/【申请数量】
                    if(importParam.getUnitPrice() == null && importParam.getTotalPrice() != null){
                        importParam.setUnitPrice(importParam.getTotalPrice().divide(new BigDecimal(importParam.getApplyQuantity()), 2, BigDecimal.ROUND_HALF_UP));
                    }
                    //总价不为空且收购单价不为空，默认读取总价，计算得出收购单价：【收购单价】=【总价】/【申请数量】
                    if(importParam.getUnitPrice() != null && importParam.getTotalPrice() != null){
                        importParam.setUnitPrice(importParam.getTotalPrice().divide(new BigDecimal(importParam.getApplyQuantity()), 2, BigDecimal.ROUND_HALF_UP));
                    }
                    RemovalDestroyApply applyBean = new RemovalDestroyApply();
                    BeanUtils.copyProperties(importParam, applyBean);
                    applyBean.setApplyCode(applyCode);
                    applyBean.setCreateBy(LoginContext.me().getLoginUser().getName());
                    applyBean.setCreateTime(DateUtil.date());
                    applyBean.setDepartment(null);
                    applyBean.setTeam(null);
                    applyBean.setProductType(null);
                    applyList.add(applyBean);
                }

                //导入库存明细，删除历史数据
                if(CollectionUtil.isNotEmpty(applyList)){
                    mapper.deleteByDestroyApply(applyCode);
                }

                //保存集合
                this.saveBatch(applyList);
                return ResponseData.success(applyCode);
            }
        } catch (Exception e) {
            log.error("库存移除至服务商申请批量导入Excel处理异常，导入失败！", e);
            return ResponseData.error("库存移除至服务商申请批量导入Excel处理异常，导入失败！");
        } finally {
            if(buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("库存移除至服务商申请批量导入Excel关闭流异常", e);
                }
            }
        }
    }

    /**
     * 库存移除至服务商申请批量导入数据校验处理
     * @param dataList
     * @return
     */
    private List<RemovalDestroyApplyImportParam> validUploadRemark(List<RemovalDestroyApplyImportParam> dataList, List<RemovalDestroyApplyImportParam> errorList) {
        Iterator<RemovalDestroyApplyImportParam> iterator = dataList.listIterator();
        while(iterator.hasNext()) {
            RemovalDestroyApplyImportParam param = iterator.next();
            //验证基础信息
            StringBuffer validInfo = new StringBuffer();
            if(StringUtils.isBlank(param.getSysShopsName())
                    || StringUtils.isBlank(param.getSysSite())
                    || StringUtils.isBlank(param.getMerchantSku())
                    || StringUtils.isBlank(param.getOrderId())
                    || param.getApplyQuantity() == null){
                //不为空校验
                validInfo.append("账号、站点、MerchantSKU、order id和申请数量为必填项");
            }
            if(param.getUnitPrice() == null && param.getTotalPrice() == null){
                validInfo.append("收购单价和总价其中一项为必填项");
            }
            if(StringUtils.isNotEmpty(validInfo)){
                param.setUploadRemark(validInfo.toString());
                errorList.add(param);
            }
        }
        return dataList;
    }

    /**
     * 库存移除至服务商申请批量导入异常数据导出
     * @param errorList
     * @return
     */
    private String dealImportErrorList(List<RemovalDestroyApplyImportParam> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, RemovalDestroyApplyImportParam.class)
                    .sheet("导入结果").doWrite(errorList);
        } catch (FileNotFoundException e) {
            log.error("库存移除至服务商申请批量导入异常数据导出异常", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("库存移除至服务商申请批量导入异常数据导出流关闭异常", e);
                }
            }
        }
        return fileName;
    }

    @DataSource(name = "warehouse")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData importDestroyApply(RemovalDestroyBaseApplyParam param,MultipartFile file) {

        String applyCode=param.getApplyCode();
        RemovalDestroyBaseApply removalApply=new RemovalDestroyBaseApply();

        //保存库存基础信息
        QueryWrapper<RemovalDestroyBaseApply> qw = new QueryWrapper<>();
        qw.eq("APPLY_CODE", param.getApplyCode());
        if (service.count(qw) == 0) {
            //流程编号生成
            String DateStr= DateUtil.format(DateUtil.date(),"yyyyMMdd");
            String applyNo=this.baseMapper.selectByApplyNo("KCXH");
            String flowCode= getNewApplyNo("KCXH",applyNo,DateStr);
            //返回基础信息
            removalApply.setApplyCode(flowCode);
            removalApply.setApplyTitle(param.getApplyTitle());
            removalApply.setApplyName(param.getApplyName());
            removalApply.setApplyUserAccount(param.getApplyUserAccount());
            removalApply.setApplyUserName(param.getApplyUserName());
            removalApply.setApplyDepartment(param.getApplyDepartment());
            removalApply.setApplyDate(DateUtil.parse(param.getApplyDate()));
            removalApply.setAuditStatus("1");
            removalApply.setIncomeRemark(param.getIncomeRemark());
            removalApply.setCreateBy(LoginContext.me().getLoginUser().getName());
            removalApply.setCreateTime(new Date());

            service.save(removalApply);
        }
        if (StrUtil.isNotEmpty(removalApply.getApplyCode())){
            applyCode=removalApply.getApplyCode();
        }

        //创建ArrayList集合对象
        ArrayList<String> array = new ArrayList<String>();

        //导入库存明细
        BufferedInputStream buffer = null;
        List<RemovalDestroyApply> applyList=new ArrayList<>();
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(file.getInputStream()));
            String lineTxt = null;

            while ((lineTxt = bufferedReader.readLine()) != null) {
                //明细空行
                if(lineTxt.equals("")){ continue; }
                //把读取到的字符串存储到集合中
                array.add(lineTxt);
            }
            bufferedReader.close();

            String[] removalOrderId=array.get(0).split("\\t");
            String[] Site=array.get(7).split("\\t");

            //效验
            if(removalOrderId.length<2 || StrUtil.isEmpty(removalOrderId[1])){
                return ResponseData.error("订单号不能为空！");
            }
            if(Site.length<2 || StrUtil.isEmpty(Site[1])){
                return ResponseData.error("站点不能为空！");
            }

            //正则匹配字符：截取Removal_Order_ID单号中的账号字段
            String shopName=removalOrderId[1];
            Matcher m = Pattern.compile("[0-9]").matcher(shopName);
            if(m.find()){
                int numberIndex=m.start();  //数字起始位置
                shopName=StrUtil.sub(removalOrderId[1],1,numberIndex);
            }

            //遍历集合
            for(int k=14;k<array.size();k++){
                RemovalDestroyApply applyBean=new RemovalDestroyApply();
                applyBean.setApplyCode(applyCode);
                applyBean.setSysShopsName(shopName);
                applyBean.setSysSite(Site[1]);
                applyBean.setOrderId(removalOrderId[1]);
                applyBean.setCreateBy(LoginContext.me().getLoginUser().getName());
                applyBean.setCreateTime(new Date());

                String[] detail=array.get(k).split("\\t");
                applyBean.setMerchantSku(detail[0]);
                applyBean.setApplyQuantity(Integer.valueOf(detail[1])+Integer.valueOf(detail[2]));

                applyList.add(applyBean);
            }
            //导入库存明细，删除历史数据
            if(CollectionUtil.isNotEmpty(applyList)){
                mapper.deleteByDestroyApply(applyCode);
            }

            //保存集合
            this.saveBatch(applyList);
            return ResponseData.success(applyCode);
        } catch (Exception e) {
            log.error("导入文件处理异常，导入失败！", e);
            return ResponseData.error("导入文件处理异常，导入失败！");
        } finally {
            if(buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入文件关闭流异常", e);
                }
            }
        }

    }

    @DataSource(name = "warehouse")
    @Override
    public ResponseData edit(RemovalDestroyApplyParam param) {
        if(param.getId()==null || "0".equals(param.getId().toString())){
            return ResponseData.error("ID不能为空！");
        }
        RemovalDestroyApply removalDestroy=new RemovalDestroyApply();
        BeanUtils.copyProperties(param, removalDestroy);
        removalDestroy.setId(param.getId());
        removalDestroy.setUpdateBy(LoginContext.me().getLoginUser().getName());
        removalDestroy.setUpdateTime(new Date());
        this.updateById(removalDestroy);
        return ResponseData.success();
    }

    @DataSource(name = "warehouse")
    @Override
    public ResponseData baseSelect(RemovalDestroyBaseApplyParam param) {

        String account = LoginContext.me().getLoginUser().getAccount();
        RemovalDestroyBaseApply entity=new RemovalDestroyBaseApply();

        RemovalDestroyBaseApplyResult baseApplyResult=new RemovalDestroyBaseApplyResult();
        if(StrUtil.isNotEmpty(param.getApplyCode())){
            QueryWrapper<RemovalDestroyBaseApply> qw=new QueryWrapper<>();
            qw.eq("APPLY_CODE",param.getApplyCode());
            entity=service.getOne(qw);
            if(service.count(qw)>0){
                List<InventoryRejectVO> voList=new ArrayList<>();
                BeanUtils.copyProperties(entity, baseApplyResult);
                //审批流程->驳回意见信息数组集合
                if(StrUtil.isNotEmpty(entity.getRejectRemark()) && entity.getRejectRemark().contains(";")){
                    String[] strArray=entity.getRejectRemark().split(";");
                    for(int i=0;i<strArray.length;i++){
                        String[] reArray=strArray[i].split(",");
                        InventoryRejectVO ent=new InventoryRejectVO();
                        ent.setName(reArray[0]);
                        ent.setJobNumber(reArray[1]);
                        ent.setRejectDate(reArray[2]);
                        if(reArray.length == 4){
                            ent.setRemark(reArray[3]);
                        }
                        voList.add(ent);
                    }
                }
                //降序排序
                Collections.reverse(voList);
                baseApplyResult.setRejectNode(voList);
                //返回基础信息
                return ResponseData.success(baseApplyResult);
            }
        }
        else
        {
            //生成流程编号
            String DateStr= DateUtil.format(DateUtil.date(),"yyyyMMdd");
            String applyType=ApplicationTypeEnum.getValueByKey(param.getApplyType());
            EbmsUserInfo userInfo = ebmsBaseConsumer.getUserInfoByAccount(account);

            String deptName;
            String[] deptStr=userInfo.getSysComDeptFullName().split("\\|");
            if(deptStr.length>2 && deptStr[deptStr.length-2].contains("部")){
                deptName=deptStr[deptStr.length-2];
            }
            else
            {
                deptName=deptStr[deptStr.length-1];
            }

            //流程标题
            String flowName=applyType.concat("-").concat(LoginContext.me().getLoginUser().getName())
                    .concat("-").concat(DateStr);

            entity.setApplyTitle(flowName);
            entity.setApplyName(applyType);
            entity.setApplyUserAccount(LoginContext.me().getLoginUser().getAccount());
            entity.setApplyUserName(LoginContext.me().getLoginUser().getName().concat(LoginContext.me().getLoginUser().getAccount()));
            entity.setApplyDepartment(deptName);
        }

        return ResponseData.success(entity);
    }

    @DataSource(name = "warehouse")
    @Override
    public ResponseData submit(RemovalDestroyBaseApplyParam param) {

        if(StrUtil.isEmpty(param.getApplyCode()))
        {
            ResponseData.error("流程编码不能为空！");
        }

        //保存基础信息
        UpdateWrapper<RemovalDestroyBaseApply> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("APPLY_CODE",param.getApplyCode())
                .set(param.getShareNum()!=null,"SHARE_NUM",param.getShareNum())
                .set(StrUtil.isNotEmpty(param.getBankUserAccount()),"BANK_USER_ACCOUNT",param.getBankUserAccount())
                .set(param.getIncomeAmoumt()!=null,"INCOME_AMOUMT",param.getIncomeAmoumt())
                .set(StrUtil.isNotEmpty(param.getIncomeRemark()),"INCOME_REMARK",param.getIncomeRemark())
                .set(StrUtil.isNotEmpty(param.getCurrency()),"CURRENCY",param.getCurrency())
                .set("UPDATE_TIME",new Date());
        service.update(updateWrapper);

        //更新库存明细
        if(CollectionUtil.isNotEmpty(param.getDetailList()))
        {
            List<RemovalDestroyApply> removalDestroyApplyList=new ArrayList<>();

            for(RemovalDestroyApply i : param.getDetailList()){
                i.setUpdateBy(LoginContext.me().getLoginUser().getName());
                i.setUpdateTime(new Date());
                if(StrUtil.isEmpty(i.getDepartment()) || StrUtil.isEmpty(i.getTeam())
                        || StrUtil.isEmpty(i.getProductType())){
                    return ResponseData.error("事业部、team、运营大类不为空！");
                }
                removalDestroyApplyList.add(i);
            }
            this.updateBatchById(removalDestroyApplyList);
        }

        //提交
        if("提交".equals(param.getSaveMark())){

            //提交下个流程节点
            String nextStatus;
            //查询流程驳回原节点状态
            String rejectStatus=this.baseMapper.selectByRejectStatus(param.getApplyCode());

            //效验审批状态
            if(StrUtil.isEmpty(param.getAuditStatus()))
            {
                return ResponseData.error("流程审批状态不为空！");
            }
            else{
                //逐级审批
                if("0".equals(rejectStatus)){
                    //1-1、库存移除申请流程节点审批
                    //1-2、库存销毁申请流程节点审批
                    if(param.getApplyCode().contains("KCYC") && "3".equals(param.getAuditStatus())){
                        //财务审批后，流程转入归档
                        nextStatus="5";
                    }
                    else if(param.getApplyCode().contains("KCXH") && "2".equals(param.getAuditStatus()))
                    {
                        if(param.getShareNum().compareTo(BigDecimal.ONE)==1){
                            //2-1、摊销期大于1，流程：需要总经理审批
                            nextStatus="4";
                        }
                        else
                        {
                            //2-2、摊销期小于等于1，流程：不需要总经理审批，转入归档
                            nextStatus="5";
                        }
                    }
                    else{
                        nextStatus=String.valueOf(Integer.parseInt(param.getAuditStatus()) + 1);
                    }
                }
                else{
                    //提交，如果有驳回，返回原节点
                    nextStatus=rejectStatus;
                }
            }

            UpdateWrapper<RemovalDestroyBaseApply> updateStatusWrapper=new UpdateWrapper<>();
            updateStatusWrapper
                    .eq("APPLY_CODE",param.getApplyCode())
                    .set("UPDATE_BY",LoginContext.me().getLoginUser().getName())
                    .set("AUDIT_ACCOUNT",LoginContext.me().getLoginUser().getAccount())
                    .set("AUDIT_NAME",LoginContext.me().getLoginUser().getName())
                    .set("REJECT_SUBMIT_NODE","0")
                    .set("UPDATE_TIME",new Date())
                    .set(!"5".equals(param.getAuditStatus()),"AUDIT_STATUS",nextStatus);
            service.update(updateStatusWrapper);
            return ResponseData.success(nextStatus);
        }
        return ResponseData.success("保存成功！");
    }

    @DataSource(name = "warehouse")
    @Override
    public ResponseData delete(RemovalDestroyApplyParam param) {
        if(param.getId()==null || "0".equals(param.getId().toString())){
            return ResponseData.error("ID不能为空！");
        }
        mapper.deleteById(param.getId());
        return ResponseData.success();
    }

    @DataSource(name = "warehouse")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData reject(RemovalDestroyBaseApplyParam param) {
        String rejectSubmitNode="0";
        if(StrUtil.isEmpty(param.getApplyCode())){
            return ResponseData.error("流程编码不能为空！");
        }
        switch (param.getRejectSubmitNode()){
            case "1":
                rejectSubmitNode=param.getAuditStatus();
                break;
        }
        String rejectRemark=LoginContext.me().getLoginUser().getName().concat(",")
                .concat(LoginContext.me().getLoginUser().getAccount()).concat(",")
                .concat(DateUtil.formatDateTime(new Date())).concat(",")
                .concat(param.getRejectRemark()).concat(";");
        UpdateWrapper<RemovalDestroyBaseApply> updateStatusWrapper=new UpdateWrapper<>();
        String sql = "REJECT_REMARK=REJECT_REMARK||'";
        sql=sql+rejectRemark + "'";

        updateStatusWrapper
                .eq("APPLY_CODE",param.getApplyCode())
                .set("AUDIT_STATUS","1")
                .set("REJECT_SUBMIT_NODE",rejectSubmitNode)
                .setSql(sql)
                //.set("REJECT_REMARK",rejectRemark)
                .set("UPDATE_BY",LoginContext.me().getLoginUser().getName())
                .set("UPDATE_TIME",new Date());
        service.update(updateStatusWrapper);

        return ResponseData.success();
    }

    public static String getNewApplyNo(String applyType,String applyNo,String DateStr){

        String newApplyNo = "0001";

        if(StrUtil.isNotEmpty(applyNo)){
            int newApply = Integer.parseInt(applyNo) + 1;
            newApplyNo = String.format("%04d", newApply);
        }
        String flowCode= applyType.concat("-").concat(DateStr).concat("-").concat(newApplyNo);

        return flowCode;
    }

    public List<Map<String, Object>> transformLowerCase(List<Map<String, Object>> orgMap) {

        List<Map<String, Object>> listMaps = new ArrayList<Map<String, Object>>();

        for (Map<String, Object> map : orgMap) {
            if (map == null || map.isEmpty()) {
                continue;
            }
            Map<String, Object> resultMap = new HashMap<>();

            Set<String> keys = map.keySet();
            for(String key:keys) {
                String newKey = key.toLowerCase();

                resultMap.put(newKey, map.get(key));
            }
            listMaps.add(resultMap);
        }
        return  listMaps;
    }
}
