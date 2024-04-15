package com.tadpole.cloud.platformSettlement.modular.sales.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.SalesVolumeTarget;
import com.tadpole.cloud.platformSettlement.modular.sales.mapper.SalesVolumeTargetMapper;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.SalesVolumeTargetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.SalesVolumeTargetResult;
import com.tadpole.cloud.platformSettlement.modular.sales.service.ISalesTargetService;
import com.tadpole.cloud.platformSettlement.modular.sales.service.ISalesVolumeTargetService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 销售额目标 服务实现类
 * </p>
 *
 * @author gal
 * @since 2022-03-04
 */
@Service
@Slf4j
public class SalesVolumeTargetServiceImpl extends ServiceImpl<SalesVolumeTargetMapper, SalesVolumeTarget> implements ISalesVolumeTargetService {

    @Autowired
    private ISalesTargetService salesTargetService;
    @Autowired
    private SalesVolumeTargetMapper mapper;

    @DataSource(name = "sales")
    @Override
    public List<SalesVolumeTargetResult> findPageBySpec(SalesVolumeTargetParam param) {
        List<SalesVolumeTargetResult> page = this.baseMapper.findPageBySpec(param);
        return page;
    }

    @DataSource(name = "sales")
    @Override
    public ResponseData confirm(SalesVolumeTargetParam param) {
        if  ( StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getVersion())){
            return ResponseData.error("年份和版本不能为空！");
        }
        UpdateWrapper<SalesVolumeTarget> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("year", param.getYear())
                .eq("version", param.getVersion())
                .set("CONFIRM_STATUS",1)
                .set("CONFIRM_DATE", new Date())
                .set("CONFIRM_BY", LoginContext.me().getLoginUser().getName());
        this.mapper.update(null, updateWrapper);
        return ResponseData.success();
    }

    @DataSource(name = "sales")
    @Override
    public List<SalesVolumeTargetResult> export(SalesVolumeTargetParam param) {
        List<SalesVolumeTargetResult> page = this.baseMapper.findPageBySpec(param);
        return page;
    }

    @DataSource(name = "sales")
    @Override
    public ResponseData edit(SalesVolumeTarget param) {
        if(param.getId()==null || "0".equals(param.getId().toString())){
            return ResponseData.error("ID不能为空！");
        }
        SalesVolumeTarget pa=this.mapper.selectById(param.getId());
        if (pa == null) {
            return ResponseData.error("无对应记录！");
        }
        if (!BigDecimal.ZERO.equals(pa.getConfirmStatus())) {
            return ResponseData.error("数据已确认,无法修改！");
        }

        if(param.getSeasonOne()==null
                || param.getSeasonTwo()==null
                || param.getSeasonThree()==null
                || param.getSeasonFour()==null
                || param.getUs()==null
                || param.getCa()==null
                || param.getMx()==null
                || param.getUk()==null
                || param.getDe()==null
                || param.getFr()==null
                || param.getIt()==null
                || param.getEs()==null
                || param.getNl()==null
                || param.getSe()==null
                || param.getJp()==null
                || param.getAu()==null
                || param.getAe()==null
                || param.getSg()==null
                || param.getSa()==null
                || param.getInd()==null
                || param.getPl()==null
                || param.getTr()==null
                || param.getBe()==null
                || param.getEu()==null
        ){
            return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "Q1、Q2、Q3、Q4金额、站点金额均不能为空！");
        }

        //数据校验
        BigDecimal seasonSum=param.getSeasonOne().add(param.getSeasonTwo()).add(param.getSeasonThree())
                .add(param.getSeasonFour());

        BigDecimal siteSum=param.getUs().add(param.getCa()).add(param.getMx())
                .add(param.getUk()).add(param.getDe()).add(param.getFr()).add(param.getIt())
                .add(param.getEs()).add(param.getNl()).add(param.getSe()).add(param.getJp())
                .add(param.getAu()).add(param.getAe()).add(param.getSg()).add(param.getSa())
                .add(param.getInd()).add(param.getPl()).add(param.getTr().add(param.getBe()));

        if(seasonSum.setScale(0, RoundingMode.HALF_UP).compareTo(siteSum.setScale(0, RoundingMode.HALF_UP))!=0)
        {
            return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE,"Q1到Q4销售额和各站点(除去欧洲)销售额之和不相等！");
        }

        SalesVolumeTarget ss = new SalesVolumeTarget();
        BeanUtils.copyProperties(param, ss);
        ss.setId(param.getId());
        ss.setUpdateBy(LoginContext.me().getLoginUser().getName());
        ss.setUpdateAt(new Date());

        this.baseMapper.updateById(ss);
        return ResponseData.success();
    }

    @DataSource(name = "sales")
    @Override
    public SalesVolumeTargetResult getQuantity(SalesVolumeTargetParam param) {
        return this.baseMapper.getQuantity(param);
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getPlatformSelect() {
        QueryWrapper<SalesVolumeTarget> salesTargetWrapper = new QueryWrapper<>();
        salesTargetWrapper = salesTargetWrapper.select("platform").groupBy("platform");
        return transformLowerCase(this.baseMapper.selectMaps(salesTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getProductTypeSelect() {
        QueryWrapper<SalesVolumeTarget> salesTargetWrapper = new QueryWrapper<>();
        salesTargetWrapper = salesTargetWrapper.select("product_type").groupBy("product_type");
        return transformLowerCase(this.baseMapper.selectMaps(salesTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getDepartmentSelect() {
        QueryWrapper<SalesVolumeTarget> salesTargetWrapper = new QueryWrapper<>();
        salesTargetWrapper = salesTargetWrapper.select("department").groupBy("department").orderByAsc("TO_NUMBER(REGEXP_SUBSTR(TRANSLATE(department,'一二三四五六七八九','123456789'),'[0-9]+',1))");
        return transformLowerCase(this.baseMapper.selectMaps(salesTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getTeamSelect() {
        QueryWrapper<SalesVolumeTarget> salesTargetWrapper = new QueryWrapper<>();
        salesTargetWrapper = salesTargetWrapper.select("team").groupBy("team").orderByAsc("TO_NUMBER(REGEXP_SUBSTR(team,'[0-9]+',1))");
        return transformLowerCase(this.baseMapper.selectMaps(salesTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getCompanyBrandSelect() {
        QueryWrapper<SalesVolumeTarget> salesTargetWrapper = new QueryWrapper<>();
        salesTargetWrapper = salesTargetWrapper.select("COMPANY_BRAND").groupBy("COMPANY_BRAND");
        return transformLowerCase(this.baseMapper.selectMaps(salesTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getYearSelect() {
        QueryWrapper<SalesVolumeTarget> salesTargetWrapper = new QueryWrapper<>();
        salesTargetWrapper = salesTargetWrapper.select("year").groupBy("year");
        return transformLowerCase(this.baseMapper.selectMaps(salesTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getVersionSelect(SalesVolumeTargetParam param) {
        QueryWrapper<SalesVolumeTarget> salesTargetWrapper = new QueryWrapper<>();
        if  ( StrUtil.isNotEmpty(param.getYear())){
            salesTargetWrapper = salesTargetWrapper.eq("YEAR",param.getYear())
                    .select("version").groupBy("version").orderByAsc("version");
        }
        return transformLowerCase(this.baseMapper.selectMaps(salesTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public ResponseData importExcel(String year, String version, String currency, MultipartFile file,
                                    List<String> platformList,List<String> departmentTeamList,
                                    List<String> productTypeList,List<String> brandList,List<String> jpShops) {
        if  ( StrUtil.isEmpty(year) || StrUtil.isEmpty(currency)){
            return ResponseData.error("年份和币种不能为空！");
        }
        if (file == null) {
            return ResponseData.error("上传文件为空！");
        }
        if(StringUtil.isEmpty(version)) {
            Map<String, String> versionUnConfirmed = salesTargetService.isVersionUnConfirmed("SALES_VOLUME_TARGET",year);
            if (versionUnConfirmed != null && new BigDecimal(BigInteger.ZERO).equals(versionUnConfirmed.get("CONFIRM_STATUS"))) {
                return ResponseData.error(StrUtil.format("销售额目标当前年份{}存在未确认版本{},不能导入新版本", year, versionUnConfirmed.get("VERSION")));
            }
        }

        log.info("导入Excel处理开始");
        String account = LoginContext.me().getLoginUser().getName();
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<SalesVolumeTarget>();
//            EasyExcel.read(buffer, SalesVolumeTarget.class, listener).sheet()
//                    .doRead();

            ExcelReader excelReader  = EasyExcel.read(file.getInputStream(), SalesVolumeTarget.class, listener).build();
            excelReader.read(EasyExcel.readSheet("销售收入").build());

            List<SalesVolumeTarget> dataList = listener.getDataList();
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据销售收入为空，导入失败！");
            }

            //异常数据集合
            List<SalesVolumeTarget> errorList = new ArrayList<>();
            this.validation(dataList,errorList,account,platformList,departmentTeamList,productTypeList,brandList,year,jpShops);

            //批量保存更新表数据
            if(CollectionUtil.isNotEmpty(errorList)){
                String fileName = this.dealImportErrorList(dataList);
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "导入失败，存在异常数据！", fileName);
            }else{
                //版本为空
                String MaxVersion;
                if(StringUtil.isEmpty(version)){
                    //根据年度，查出数据库中存在最大版本 Vn，版本设置为Vn+1;
                    QueryWrapper<SalesVolumeTarget> qw = new QueryWrapper<>();
                    qw.eq("YEAR", year)
                            .select("case when MAX(VERSION) is not null then 'V'||TO_CHAR(MAX(TO_NUMBER(SUBSTR(VERSION,2)))) end as version");
                    SalesVolumeTarget salesVolumeTarget=this.baseMapper.selectOne(qw);
                    qw.clear();

                    if(salesVolumeTarget!=null)
                    {
                        int MaxVersionNum=Integer.parseInt(salesVolumeTarget.getVersion().replace("V",""))+1;
                        MaxVersion="V"+String.valueOf(MaxVersionNum);
                    }
                    else
                    {
                        MaxVersion="V1";
                    }
                    dataList.stream().forEach((entity)->{
                        entity.setVersion(MaxVersion);
                        entity.setYear(new BigDecimal(year));
                        entity.setCurrency(currency);
                    });

                    this.saveBatch(dataList);

                    return ResponseData.success("导入成功！");
                }else{
                    //查询一条该年度和版本的数据
                    QueryWrapper<SalesVolumeTarget> qw = new QueryWrapper<>();
                    qw.eq("YEAR", year)
                            .eq("VERSION",version);
                    List<SalesVolumeTarget> target=this.baseMapper.selectList(qw);
                    qw.clear();

                    dataList.stream().forEach((entity)->{
                        entity.setVersion(version);
                        entity.setYear(new BigDecimal(year));
                        entity.setCurrency(currency);
                    });

                    if(target.size()>0 && BigDecimal.ZERO.equals(target.get(0).getConfirmStatus())){
                        //删除当前年度版本数据
                        List<BigDecimal> userIds = target.stream().map(SalesVolumeTarget::getId).collect(Collectors.toList());
                        this.baseMapper.deleteBatchIds(userIds);
                        //插入数据库
                        this.saveBatch(dataList);
                    }else if(target.size()>0 && BigDecimal.ONE.equals(target.get(0).getConfirmStatus())){
                        return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE,"导入失败，当前年度版本数据已确认！");
                    }else if(target==null || target.size()==0){
                        //插入数据库
                        this.saveBatch(dataList);
                    }
                }

            }
            return ResponseData.success("导入成功！");
        } catch (Exception e) {
            log.error("销售额目标导入异常"+ e);
            return ResponseData.error("销售额目标导入异常"+ e);
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

    private void validation(List<SalesVolumeTarget> dataList, List<SalesVolumeTarget> errorList, String account
            ,List<String> platformList,List<String> departmentTeamList,List<String> productTypeList
            ,List<String> brandList,String year,List<String> jpShops) {

        Set<String> allDate = new HashSet<>();
        //Excel重复记录
        Set<String> repeatDate = new HashSet<>();

        for (SalesVolumeTarget i : dataList) {
            StringBuffer uploadRemark = new StringBuffer();
            if ("事业五部".equals(i.getDepartment())) {
                if(i.getShopName()!=null && !jpShops.contains(i.getShopName())){
                    uploadRemark.append(StrUtil.format("事业五部账号可选:[{}]",String.join( "、", jpShops)));
                }
            }
            if ("2021".equals(year) && ("事业五部".equals(i.getDepartment()))) {
                continue;
            }
            String Platform=i.getPlatform().toUpperCase();
            if(StringUtils.isEmpty(i.getPlatform())){
                uploadRemark.append("平台不能为空！");
            }
            if(StringUtils.isEmpty(i.getDepartment())){
                uploadRemark.append("事业部不能为空！");
            }
            if(StringUtils.isEmpty(i.getTeam())){
                uploadRemark.append("Team不能为空！");
            }
            if(Platform.trim().equals("AMAZON") && StringUtils.isEmpty(i.getProductType())){
                uploadRemark.append("运营大类不能为空！");
            }
            if(Platform.trim().equals("AMAZON") && StringUtils.isEmpty(i.getCompanyBrand())){
                uploadRemark.append("销售品牌不能为空！");
            }

            i.setUploadRemark(uploadRemark == null || uploadRemark.equals("") ? "" : uploadRemark.toString());
            String sb = new StringBuffer().append(i.getPlatform()).append(i.getDepartment())
                    .append(i.getTeam()).append(i.getProductType()).append(i.getCompanyBrand())
                                                      .append(i.getShopName())
                                                        .append(i.getNewoldProduct())
                                                        .toString();
            if(allDate.contains(sb)){
                repeatDate.add(sb);
            }
            allDate.add(sb);
        }

        Iterator<SalesVolumeTarget> iterator = dataList.listIterator();
        while(iterator.hasNext()) {
            SalesVolumeTarget salesVolumeTarget = iterator.next();
            salesVolumeTarget.setCreateBy(account);
            salesVolumeTarget.setCreateAt(new Date());
            salesVolumeTarget.setUpdateAt(new Date());
            salesVolumeTarget.setUpdateBy(account);

            //验证平台,事业部,Team信息
            StringBuffer validInfo = new StringBuffer();
            String Platform=salesVolumeTarget.getPlatform().toUpperCase();
            if(StringUtils.isEmpty(salesVolumeTarget.getUploadRemark()) && (!"2021".equals(year) || !"事业五部".equals(salesVolumeTarget.getDepartment()))) {
                if (!Platform.equals("B2B") && !platformList.contains(salesVolumeTarget.getPlatform())) {
                    validInfo.append("平台有误!");
                }
                if (!departmentTeamList.contains(salesVolumeTarget.getDepartment())) {
                    validInfo.append("事业部有误!");
                }
                if (Platform.trim().equals("AMAZON") && !departmentTeamList.contains(salesVolumeTarget.getTeam())) {
                    validInfo.append("Team有误!");
                }
                if (Platform.trim().equals("AMAZON") && !productTypeList.contains(salesVolumeTarget.getProductType())) {
                    validInfo.append("运营大类有误!");
                }
                if (Platform.trim().equals("AMAZON") && !brandList.contains(salesVolumeTarget.getCompanyBrand())) {
                    validInfo.append("销售品牌有误!");
                }
                if (validInfo.length() > 0) {
                    salesVolumeTarget.setUploadRemark(validInfo.toString());
                }
            }

            if(salesVolumeTarget.getSeasonOne()==null
                    || salesVolumeTarget.getSeasonTwo()==null
                    || salesVolumeTarget.getSeasonThree()==null
                    || salesVolumeTarget.getSeasonFour()==null
                    || salesVolumeTarget.getUs()==null
                    || salesVolumeTarget.getCa()==null
                    || salesVolumeTarget.getMx()==null
                    || salesVolumeTarget.getUk()==null
                    || salesVolumeTarget.getDe()==null
                    || salesVolumeTarget.getFr()==null
                    || salesVolumeTarget.getIt()==null
                    || salesVolumeTarget.getEs()==null
                    || salesVolumeTarget.getNl()==null
                    || salesVolumeTarget.getSe()==null
                    || salesVolumeTarget.getJp()==null
                    || salesVolumeTarget.getAu()==null
                    || salesVolumeTarget.getAe()==null
                    || salesVolumeTarget.getSg()==null
                    || salesVolumeTarget.getSa()==null
                    || salesVolumeTarget.getInd()==null
                    || salesVolumeTarget.getPl()==null
                    || salesVolumeTarget.getTr()==null
                    || salesVolumeTarget.getBe()==null
                    || salesVolumeTarget.getEu()==null
            ){
                salesVolumeTarget.setUploadRemark("Q1、Q2、Q3、Q4金额、站点金额均不能为空！");
                errorList.add(salesVolumeTarget);
            }
            else{
                //数据校验
                BigDecimal seasonSum=salesVolumeTarget.getSeasonOne().add(salesVolumeTarget.getSeasonTwo()).add(salesVolumeTarget.getSeasonThree())
                        .add(salesVolumeTarget.getSeasonFour());

                BigDecimal siteSum=salesVolumeTarget.getUs().add(salesVolumeTarget.getCa()).add(salesVolumeTarget.getMx())
                        .add(salesVolumeTarget.getUk()).add(salesVolumeTarget.getDe()).add(salesVolumeTarget.getFr()).add(salesVolumeTarget.getIt())
                        .add(salesVolumeTarget.getEs()).add(salesVolumeTarget.getNl()).add(salesVolumeTarget.getSe()).add(salesVolumeTarget.getJp())
                        .add(salesVolumeTarget.getAu()).add(salesVolumeTarget.getAe()).add(salesVolumeTarget.getSg()).add(salesVolumeTarget.getSa())
                        .add(salesVolumeTarget.getInd()).add(salesVolumeTarget.getPl()).add(salesVolumeTarget.getTr().add(salesVolumeTarget.getBe()));
                if(seasonSum.setScale(0, RoundingMode.HALF_UP).compareTo(siteSum.setScale(0, RoundingMode.HALF_UP))!=0)
                {
                    salesVolumeTarget.setUploadRemark("Q1到Q4销售额和各站点(除去欧洲)销售额之和不相等！");

                    errorList.add(salesVolumeTarget);
                    //移除异常的数据
                    //iterator.remove();
                }
            }

            //校验重复数据
            if (!"2021".equals(year) || !("事业五部".equals(salesVolumeTarget.getDepartment()))) {

                String sb = new StringBuffer().append(salesVolumeTarget.getPlatform())
                    .append(salesVolumeTarget.getDepartment())
                    .append(salesVolumeTarget.getTeam()).append(salesVolumeTarget.getProductType())
                    .append(salesVolumeTarget.getCompanyBrand())
                    .append(salesVolumeTarget.getShopName())
                    .append(salesVolumeTarget.getNewoldProduct()).toString();
                if (repeatDate.contains(sb)) {
                    salesVolumeTarget.setUploadRemark(
                        salesVolumeTarget.getUploadRemark() == null || salesVolumeTarget
                            .getUploadRemark().equals("") ? "数据重复，请排查重复数据！"
                            : salesVolumeTarget.getUploadRemark() + "数据重复，请排查重复数据！");
                }

                if (StringUtils.isNotEmpty(salesVolumeTarget.getUploadRemark())) {
                    errorList.add(salesVolumeTarget);
                    //移除异常的数据
                    //iterator.remove();
                }
            }

        }
    }

    private String dealImportErrorList(List<SalesVolumeTarget> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, SalesVolumeTarget.class)
                    .sheet("销售收入").doWrite(errorList);
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
