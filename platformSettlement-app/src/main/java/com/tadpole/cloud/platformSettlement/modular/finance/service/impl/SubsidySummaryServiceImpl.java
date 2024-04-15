package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SubsidySummary;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.SubsidyType;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.SubsidySummaryMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SubsidySummaryParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SubsidySummaryResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISubsidySummaryService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
* <p>
* 补贴汇总表 服务实现类
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Service
@Slf4j
public class SubsidySummaryServiceImpl extends ServiceImpl<SubsidySummaryMapper, SubsidySummary> implements ISubsidySummaryService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 批量确认无需分摊调整标识
     */
    @Value("${rediskey.confirmSubsidy}")
    public String confirmSubsidy;

    @DataSource(name = "finance")
    @Override
    public PageResult<SubsidySummaryResult> findPageBySpec(SubsidySummaryParam param) {
        Page pageContext = getPageContext();

        IPage<SubsidySummaryResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData importExcel(MultipartFile file,List<String> departmentList,List<String> teamList) {
        log.info("导入Excel处理开始");
        String account = LoginContext.me().getLoginUser().getName();
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<SubsidySummary>();
            EasyExcel.read(buffer, SubsidySummary.class, listener).sheet()
                    .doRead();

            List<SubsidySummary> dataList = listener.getDataList();
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据为空，导入失败！");
            }

            //异常数据集合 
            List<SubsidySummary> errorList = new ArrayList<>();
            this.validation(dataList,errorList,account,departmentList,teamList);

            //批量保存更新表数据
            if(CollectionUtil.isNotEmpty(dataList)){
                this.saveBatch(dataList);
                if(CollectionUtil.isNotEmpty(errorList)){
                    String fileName = this.dealImportErrorList(errorList);
                    //部分导入成功
                    return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "部分导入成功，存在异常数据数据", fileName);
                }
                return ResponseData.success("导入成功！");
            }
            if(CollectionUtil.isNotEmpty(errorList)){
                String fileName = this.dealImportErrorList(errorList);
                //导入失败
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "导入失败，存在异常数据数据", fileName);
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

    @DataSource(name = "finance")
    @Override
    public void edit(SubsidySummaryParam param) {

        SubsidySummary ss = new SubsidySummary();

        ss.setId(param.getId());
        ss.setAmount(param.getAmount());

        this.baseMapper.updateById(ss);
    }

    @DataSource(name = "finance")
    @Override
    public void confirm(SubsidySummaryParam param) {

        QueryWrapper<SubsidySummary> qs=new QueryWrapper<>();
        qs.eq("id",param.getId());
        SubsidySummary check=this.baseMapper.selectOne(qs);
        if(check.getConfirmStatus().equals(BigDecimal.ZERO)){
            SubsidySummary ss=new SubsidySummary();

            ss.setId(param.getId());
            ss.setConfirmStatus(new BigDecimal(1));
            ss.setConfirmBy(LoginContext.me().getLoginUser().getName());
            ss.setConfirmDate(new Date());

            //todo 更新结算报告
            this.baseMapper.updateToReport(ss);

            this.baseMapper.updateById(ss);

        }
    }

    @DataSource(name = "finance")
    @Override
    public void delete(SubsidySummaryParam param) {
        QueryWrapper<SubsidySummary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ID",param.getId());
        this.baseMapper.delete(queryWrapper);
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData confirmBatch(SubsidySummaryParam param) {

        log.info("补贴汇总表审核开始");
        long start = System.currentTimeMillis();

        //redis操作需绑定key
        BoundValueOperations toList = redisTemplate.boundValueOps(this.confirmSubsidy);

        try{
            //从非空则为正在批量确认中
            if (toList.get() != null && StrUtil.isNotEmpty((String)toList.get())){
                return ResponseData.error("正在批量确认中!");
            }
            //设定正在批量确认
            toList.set("正在批量确认中!");

            List<SubsidySummaryParam> params = this.baseMapper.queryConfirm(param);

            if (CollUtil.isEmpty(params)) {
                return ResponseData.success("无可确认的数据!");
            }

            //批量刷结算报告
            this.baseMapper.updateToReportBatch(param);

            //修改状态
            UpdateWrapper<SubsidySummary> updateWrapper = new UpdateWrapper<>();
            updateWrapper
                    .eq("SHOP_NAME", param.getShopName())
                    .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                    .eq("CONFIRM_STATUS",0)
                    .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE",param.getSites())
                    .set("CONFIRM_STATUS",1)
                    .set("CONFIRM_BY",LoginContext.me().getLoginUser().getName())
                    .set("CONFIRM_DATE",new Date());

            this.baseMapper.update(null,updateWrapper);


            return ResponseData.success(params);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.error("生成失败!:"+e);
        } finally{
            toList.set("");
            log.info("补贴汇总表审核结束，耗时---------->" +(System.currentTimeMillis() - start) + "ms");
        }

    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(SubsidySummaryParam param) {
            this.baseMapper.deleteBatch(param);
    }

    @DataSource(name = "finance")
    @Override
    public List<SubsidySummaryResult> queryList(SubsidySummaryParam param) {
        return this.baseMapper.queryList(param);
    }

    @DataSource(name = "finance")
    @Override
    public SubsidySummaryResult getQuantity(SubsidySummaryParam param) {
        return this.baseMapper.getQuantity(param);
    }

    private void validation(List<SubsidySummary> dataList, List<SubsidySummary> errorList, String account,List<String> departmentList
            ,List<String> teamList) {
        Set<String> allDate = new HashSet<>();
        //Excel重复记录
        Set<String> repeatDate = new HashSet<>();
        //补贴类型
        List<String> typeList = SubsidyType.getNameList();

        dataList.stream().forEach(i -> {
            StringBuffer uploadRemark = new StringBuffer();
            if(StringUtils.isEmpty(i.getFiscalPeriod())){
                uploadRemark.append("会计期间不能为空！");
            }
            if(StringUtils.isEmpty(i.getShopName())){
                uploadRemark.append("账号不能为空！");
            }
            if(StringUtils.isEmpty(i.getSite())){
                uploadRemark.append("站点不能为空！");
            }
            if(StringUtils.isEmpty(i.getAccountingCurrency())){
                uploadRemark.append("核算币别不能为空！");
            }
            if(StringUtils.isEmpty(i.getSku())){
                uploadRemark.append("SKU不能为空！");
            }
            if(StringUtils.isEmpty(i.getDepartment())){
                uploadRemark.append("事业部不能为空！");
            }
            if(StringUtils.isEmpty(i.getTeam())){
                uploadRemark.append("team不能为空！");
            }
            if(StringUtils.isEmpty(i.getMaterialCode())){
                uploadRemark.append("物料编码不能为空！");
            }
            if(StringUtils.isEmpty(i.getSalesBrand())){
                uploadRemark.append("销售品牌不能为空！");
            }
            if(StringUtils.isEmpty(i.getSubsidyType())){
                uploadRemark.append("费用类型不能为空！");
            }else if(!typeList.contains(i.getSubsidyType())){
                uploadRemark.append("费用类型不匹配,广告费补贴或鼓励金！");
            }
            if(i.getAmount() == null){
                uploadRemark.append("金额不能为空！");
            }
            i.setUploadRemark(uploadRemark == null || uploadRemark.equals("") ? "" : uploadRemark.toString());
            String sb = new StringBuffer().append(i.getFiscalPeriod()).append(i.getShopName())
                    .append(i.getSite()).append(i.getSku()).append(i.getDepartment())
                    .append(i.getTeam()).append(i.getMaterialCode()).append(i.getSalesBrand()).append(i.getSubsidyType()).toString();
            if(allDate.contains(sb)){
                repeatDate.add(sb);
            }
            allDate.add(sb);
        });

        Iterator<SubsidySummary> iterator = dataList.listIterator();
        while(iterator.hasNext()) {
            SubsidySummary subsidySummary = iterator.next();

            //验证事业部，Team信息
            StringBuffer validInfo = new StringBuffer();
            if (!departmentList.contains(subsidySummary.getDepartment())) {
                validInfo.append("事业部有误!");
            }
            if (!teamList.contains(subsidySummary.getTeam())) {
                validInfo.append("Team有误!");
            }
            if (validInfo.length() > 0) {
                subsidySummary.setUploadRemark(validInfo.toString());
            }

            //校验重复数据
            String sb = new StringBuffer().append(subsidySummary.getFiscalPeriod()).append(subsidySummary.getShopName())
                    .append(subsidySummary.getSite()).append(subsidySummary.getSku()).append(subsidySummary.getDepartment())
                    .append(subsidySummary.getTeam()).append(subsidySummary.getMaterialCode()).append(subsidySummary.getSalesBrand()).append(subsidySummary.getSubsidyType()).toString();
            if(repeatDate.contains(sb)){
                subsidySummary.setUploadRemark(subsidySummary.getUploadRemark() == null  || subsidySummary.getUploadRemark().equals("")? "数据重复，请排查重复数据！" : subsidySummary.getUploadRemark() + "数据重复，请排查重复数据！");
            }

            if(subsidySummary.getUploadRemark()==null  || subsidySummary.getUploadRemark().equals("")){
                QueryWrapper<SubsidySummary> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("FISCAL_PERIOD", subsidySummary.getFiscalPeriod())
                        .eq("SHOP_NAME", subsidySummary.getShopName())
                        .eq("SITE", subsidySummary.getSite())
                        .eq("ACCOUNTING_CURRENCY", subsidySummary.getAccountingCurrency())
                        .eq("SKU", subsidySummary.getSku())
                        .eq("DEPARTMENT", subsidySummary.getDepartment())
                        .eq("TEAM", subsidySummary.getTeam())
                        .eq("SUBSIDY_TYPE", subsidySummary.getSubsidyType())
                        .eq("MATERIAL_CODE", subsidySummary.getMaterialCode());
                if(this.baseMapper.selectCount(queryWrapper) > 0){
                    subsidySummary.setUploadRemark("数据库已存在，请排查重复数据！");
                }
            }
            if(StringUtils.isNotEmpty(subsidySummary.getUploadRemark())){
                errorList.add(subsidySummary);
                //移除异常的数据
                iterator.remove();
            }
        }
    }

    private String dealImportErrorList(List<SubsidySummary> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, SubsidySummary.class)
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
}
