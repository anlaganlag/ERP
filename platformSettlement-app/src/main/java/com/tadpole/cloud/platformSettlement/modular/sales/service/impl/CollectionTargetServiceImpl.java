package com.tadpole.cloud.platformSettlement.modular.sales.service.impl;

import cn.hutool.core.bean.BeanUtil;
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
import com.tadpole.cloud.platformSettlement.modular.sales.entity.CollectionTarget;
import com.tadpole.cloud.platformSettlement.modular.sales.mapper.CollectionTargetMapper;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.CollectionTargetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.CollectionTargetResult;
import com.tadpole.cloud.platformSettlement.modular.sales.service.ICollectionTargetService;
import com.tadpole.cloud.platformSettlement.modular.sales.service.ISalesTargetService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * 回款目标 服务实现类
 * </p>
 *
 * @author gal
 * @since 2022-03-04
 */
@Service
@Slf4j
public class CollectionTargetServiceImpl extends ServiceImpl<CollectionTargetMapper, CollectionTarget> implements ICollectionTargetService {

    @Autowired
    private ISalesTargetService salesTargetService;
    @Autowired
    private CollectionTargetMapper mapper;

    @DataSource(name = "sales")
    @Override
    public List<CollectionTargetResult> findPageBySpec(CollectionTargetParam param) {
        List<CollectionTargetResult> page = this.baseMapper.findPageBySpec(param);
        return page;
    }

    @DataSource(name = "sales")
    @Override
    public ResponseData confirm(CollectionTargetParam param) {
        if  ( StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getVersion())){
            return ResponseData.error("年份和版本不能为空！");
        }
        UpdateWrapper<CollectionTarget> updateWrapper = new UpdateWrapper<>();
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
    public List<CollectionTargetResult> export(CollectionTargetParam param) {
        List<CollectionTargetResult> page = this.baseMapper.findPageBySpec(param);
        return page;
    }

    @DataSource(name = "sales")
    @Override
    public ResponseData edit(CollectionTarget param) {
        if(param.getId()==null || "0".equals(param.getId().toString())){
            return ResponseData.error("ID不能为空！");
        }
        CollectionTarget pa=this.mapper.selectById(param.getId());
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
            return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE,"Q1到Q4回款和各站点(除去欧洲)回款之和不相等！");
        }

        param.setUpdateAt(new Date());
        param.setUpdateBy(LoginContext.me().getLoginUser().getName());
        CollectionTarget ent = getEntity(param);
        this.mapper.updateById(ent);
        return ResponseData.success();
    }

    @DataSource(name = "sales")
    @Override
    public CollectionTargetResult getQuantity(CollectionTargetParam param) {
        return this.baseMapper.getQuantity(param);
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getPlatformSelect() {
        QueryWrapper<CollectionTarget> collectionTargetWrapper = new QueryWrapper<>();
        collectionTargetWrapper = collectionTargetWrapper.select("platform").groupBy("platform");
        return transformLowerCase(this.baseMapper.selectMaps(collectionTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getProductTypeSelect() {
        QueryWrapper<CollectionTarget> collectionTargetWrapper = new QueryWrapper<>();
        collectionTargetWrapper = collectionTargetWrapper.select("product_type").groupBy("product_type");
        return transformLowerCase(this.baseMapper.selectMaps(collectionTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getDepartmentSelect() {
        QueryWrapper<CollectionTarget> collectionTargetWrapper = new QueryWrapper<>();
        collectionTargetWrapper = collectionTargetWrapper.select("department").groupBy("department").orderByAsc("TO_NUMBER(REGEXP_SUBSTR(TRANSLATE(department,'一二三四五六七八九','123456789'),'[0-9]+',1))");
        return transformLowerCase(this.baseMapper.selectMaps(collectionTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getTeamSelect() {
        QueryWrapper<CollectionTarget> collectionTargetWrapper = new QueryWrapper<>();
        collectionTargetWrapper = collectionTargetWrapper.select("team").groupBy("team").orderByAsc("TO_NUMBER(REGEXP_SUBSTR(team,'[0-9]+',1))");
        return transformLowerCase(this.baseMapper.selectMaps(collectionTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getCompanyBrandSelect() {
        QueryWrapper<CollectionTarget> collectionTargetWrapper = new QueryWrapper<>();
        collectionTargetWrapper = collectionTargetWrapper.select("COMPANY_BRAND").groupBy("COMPANY_BRAND");
        return transformLowerCase(this.baseMapper.selectMaps(collectionTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getYearSelect() {
        QueryWrapper<CollectionTarget> collectionTargetWrapper = new QueryWrapper<>();
        collectionTargetWrapper = collectionTargetWrapper.select("year").groupBy("year");
        return transformLowerCase(this.baseMapper.selectMaps(collectionTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getVersionSelect(CollectionTargetParam param) {
        QueryWrapper<CollectionTarget> collectionTargetWrapper = new QueryWrapper<>();
        if  ( StrUtil.isNotEmpty(param.getYear())){
            collectionTargetWrapper = collectionTargetWrapper
                    .eq(param.getYear() != null && param.getYear().length() > 0,"YEAR",param.getYear())
                    .select("version").groupBy("version").orderByAsc("version");
        }
        return transformLowerCase(this.baseMapper.selectMaps(collectionTargetWrapper));
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
            Map<String, String> versionUnConfirmed = salesTargetService.isVersionUnConfirmed("COLLECTION_TARGET",year);
            if (versionUnConfirmed != null && new BigDecimal(BigInteger.ZERO).equals(versionUnConfirmed.get("CONFIRM_STATUS"))) {
                return ResponseData.error(StrUtil.format("回款目标当前年份{}存在未确认版本{},不能导入新版本", year, versionUnConfirmed.get("VERSION")));
            }
        }
        log.info("导入Excel处理开始");
        String account = LoginContext.me().getLoginUser().getName();
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<CollectionTarget>();
//            EasyExcel.read(buffer, CollectionTarget.class, listener).sheet()
//                    .doRead();

            ExcelReader excelReader  = EasyExcel.read(file.getInputStream(), CollectionTarget.class, listener).build();
            excelReader.read(EasyExcel.readSheet("回款").build());

            List<CollectionTarget> dataList = listener.getDataList();
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据回款数据为空，导入失败！");
            }

            //异常数据集合
            List<CollectionTarget> errorList = new ArrayList<>();
            this.validation(dataList,errorList,account,platformList,departmentTeamList,productTypeList,brandList,jpShops,year);

            //批量保存更新表数据
            if(CollectionUtil.isNotEmpty(errorList)){
                String fileName = this.dealImportErrorList(dataList);
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "导入失败，存在异常数据！", fileName);
            }else{
                //版本为空
                String MaxVersion;
                if(StringUtil.isEmpty(version)){
                    //根据年度，查出数据库中存在最大版本 Vn，版本设置为Vn+1;
                    QueryWrapper<CollectionTarget> qw = new QueryWrapper<>();
                    qw.eq("YEAR", year)
                            .select("case when MAX(VERSION) is not null then 'V'||TO_CHAR(MAX(TO_NUMBER(SUBSTR(VERSION,2)))) end as version");
                    CollectionTarget collectionTarget=this.baseMapper.selectOne(qw);
                    qw.clear();

                    if(collectionTarget!=null)
                    {
                        int MaxVersionNum=Integer.parseInt(collectionTarget.getVersion().replace("V",""))+1;
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
                    QueryWrapper<CollectionTarget> qw = new QueryWrapper<>();
                    qw.eq("YEAR", year)
                            .eq("VERSION",version);
                    List<CollectionTarget> target=this.baseMapper.selectList(qw);
                    qw.clear();

                    dataList.stream().forEach((entity)->{
                        entity.setVersion(version);
                        entity.setYear(new BigDecimal(year));
                        entity.setCurrency(currency);
                    });

                    if(target.size()>0 && BigDecimal.ZERO.equals(target.get(0).getConfirmStatus())){
                        //删除当前年度版本数据
                        List<BigDecimal> userIds = target.stream().map(CollectionTarget::getId).collect(Collectors.toList());
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
            log.error("回款导入异常:"+ e);
            return ResponseData.error("回款导入异常:"+ e);
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

    private void validation(List<CollectionTarget> dataList, List<CollectionTarget> errorList, String account
            ,List<String> platformList,List<String> departmentTeamList,List<String> productTypeList
            ,List<String> brandList,List<String> jpShops,String year) {

        Set<String> allDate = new HashSet<>();
        //Excel重复记录
        Set<String> repeatDate = new HashSet<>();
        for (CollectionTarget i : dataList) {
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
        };

        Iterator<CollectionTarget> iterator = dataList.listIterator();
        while(iterator.hasNext()) {
            CollectionTarget collectionTarget = iterator.next();
            collectionTarget.setCreateBy(account);
            collectionTarget.setCreateAt(new Date());
            collectionTarget.setUpdateAt(new Date());
            collectionTarget.setUpdateBy(account);

            //验证平台,事业部,Team信息
            StringBuffer validInfo = new StringBuffer();
            String Platform = collectionTarget.getPlatform().toUpperCase();
            if (StringUtils.isEmpty(collectionTarget.getUploadRemark()) && (!"2021".equals(year)
                || !"事业五部".equals(collectionTarget.getDepartment()))) {
                if (!Platform.equals("B2B") && !platformList
                    .contains(collectionTarget.getPlatform())) {
                    validInfo.append("平台有误!");
                }
                if (!departmentTeamList.contains(collectionTarget.getDepartment())) {
                    validInfo.append("事业部有误!");
                }
                if (Platform.trim().equals("AMAZON") && !departmentTeamList
                    .contains(collectionTarget.getTeam())) {
                    validInfo.append("Team有误!");
                }
                if (Platform.trim().equals("AMAZON") && !productTypeList
                    .contains(collectionTarget.getProductType())) {
                    validInfo.append("运营大类有误!");
                }
                if (Platform.trim().equals("AMAZON") && !brandList
                    .contains(collectionTarget.getCompanyBrand())) {
                    validInfo.append("销售品牌有误!");
                }
                if (validInfo.length() > 0) {
                    collectionTarget.setUploadRemark(validInfo.toString());
                }
            }

            if(collectionTarget.getSeasonOne()==null
                    || collectionTarget.getSeasonTwo()==null
                    || collectionTarget.getSeasonThree()==null
                    || collectionTarget.getSeasonFour()==null
                    || collectionTarget.getUs()==null
                    || collectionTarget.getCa()==null
                    || collectionTarget.getMx()==null
                    || collectionTarget.getUk()==null
                    || collectionTarget.getDe()==null
                    || collectionTarget.getFr()==null
                    || collectionTarget.getIt()==null
                    || collectionTarget.getEs()==null
                    || collectionTarget.getNl()==null
                    || collectionTarget.getSe()==null
                    || collectionTarget.getJp()==null
                    || collectionTarget.getAu()==null
                    || collectionTarget.getAe()==null
                    || collectionTarget.getSg()==null
                    || collectionTarget.getSa()==null
                    || collectionTarget.getInd()==null
                    || collectionTarget.getPl()==null
                    || collectionTarget.getTr()==null
                    || collectionTarget.getBe()==null
                    || collectionTarget.getEu()==null
            ){
                collectionTarget.setUploadRemark("Q1、Q2、Q3、Q4金额、站点金额均不能为空！");
                errorList.add(collectionTarget);
            }
            else{
                //数据校验
                BigDecimal seasonSum=collectionTarget.getSeasonOne().add(collectionTarget.getSeasonTwo()).add(collectionTarget.getSeasonThree())
                        .add(collectionTarget.getSeasonFour());

                BigDecimal siteSum=collectionTarget.getUs().add(collectionTarget.getCa()).add(collectionTarget.getMx())
                        .add(collectionTarget.getUk()).add(collectionTarget.getDe()).add(collectionTarget.getFr()).add(collectionTarget.getIt())
                        .add(collectionTarget.getEs()).add(collectionTarget.getNl()).add(collectionTarget.getSe()).add(collectionTarget.getJp())
                        .add(collectionTarget.getAu()).add(collectionTarget.getAe()).add(collectionTarget.getSg()).add(collectionTarget.getSa())
                        .add(collectionTarget.getInd()).add(collectionTarget.getPl()).add(collectionTarget.getTr().add(collectionTarget.getBe()));
                if(seasonSum.setScale(0, RoundingMode.HALF_UP).compareTo(siteSum.setScale(0, RoundingMode.HALF_UP))!=0)
                {
                    collectionTarget.setUploadRemark("Q1到Q4回款和各站点(除去欧洲)回款之和不相等！");

                    errorList.add(collectionTarget);
                    //移除异常的数据
                    //iterator.remove();
                }
            }

            //校验重复数据
            if (!"2021".equals(year) || !("事业五部".equals(collectionTarget.getDepartment()))) {
                String sb = new StringBuffer().append(collectionTarget.getPlatform())
                    .append(collectionTarget.getDepartment())
                    .append(collectionTarget.getTeam()).append(collectionTarget.getProductType())
                    .append(collectionTarget.getCompanyBrand())
                    .append(collectionTarget.getShopName())
                    .append(collectionTarget.getNewoldProduct())
                        .toString();
                if (repeatDate.contains(sb)) {
                    collectionTarget.setUploadRemark(
                        collectionTarget.getUploadRemark() == null || collectionTarget
                            .getUploadRemark().equals("") ? "数据重复，请排查重复数据！"
                            : collectionTarget.getUploadRemark() + "数据重复，请排查重复数据！");
                }

                if (StringUtils.isNotEmpty(collectionTarget.getUploadRemark())) {
                    errorList.add(collectionTarget);
                    //移除异常的数据
                    //iterator.remove();
                }
            }

        }
    }

    private String dealImportErrorList(List<CollectionTarget> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, CollectionTarget.class)
                    .sheet("回款").doWrite(errorList);
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

    private  CollectionTarget getEntity(CollectionTarget param){
        CollectionTarget entity=new CollectionTarget();
        BeanUtil.copyProperties(param, entity);
        return entity;
    }

}
