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
import com.tadpole.cloud.platformSettlement.modular.sales.entity.ProfitTarget;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.SalesVolumeTarget;
import com.tadpole.cloud.platformSettlement.modular.sales.mapper.ProfitTargetMapper;
import com.tadpole.cloud.platformSettlement.modular.sales.mapper.SalesVolumeTargetMapper;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.ProfitTargetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.ProfitTargetResult;
import com.tadpole.cloud.platformSettlement.modular.sales.service.IProfitTargetService;
import com.tadpole.cloud.platformSettlement.modular.sales.service.ISalesTargetService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 利润目标 服务实现类
 * </p>
 *
 * @author gal
 * @since 2022-03-07
 */
@Service
@Slf4j
public class ProfitTargetServiceImpl extends ServiceImpl<ProfitTargetMapper, ProfitTarget> implements IProfitTargetService {

    @Autowired
    private ISalesTargetService salesTargetService;
    @Autowired
    private ProfitTargetMapper mapper;
    @Resource
    private SalesVolumeTargetMapper salesVolumeTargetMapper;

    @DataSource(name = "sales")
    @Override
    public List<ProfitTargetResult> findPageBySpec(ProfitTargetParam param) {
        List<ProfitTargetResult> page = this.baseMapper.findPageBySpec(param);
        return page;
    }

    @DataSource(name = "sales")
    @Override
    public ResponseData confirm(ProfitTargetParam param) {
        if  ( StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getVersion())){
            return ResponseData.error("年份和版本不能为空！");
        }
        UpdateWrapper<ProfitTarget> updateWrapper = new UpdateWrapper<>();
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
    public List<ProfitTargetResult> export(ProfitTargetParam param) {
        List<ProfitTargetResult> page = this.baseMapper.findPageBySpec(param);
        return page;
    }

    @DataSource(name = "sales")
    @Override
    public ResponseData edit(ProfitTarget param) {
        if(param.getId()==null || "0".equals(param.getId().toString())){
            return ResponseData.error("ID不能为空！");
        }
        ProfitTarget pa=this.mapper.selectById(param.getId());
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

        try {
            //数据校验
            BigDecimal seasonSum = param.getSeasonOne().add(param.getSeasonTwo()).add(param.getSeasonThree())
                    .add(param.getSeasonFour());

            BigDecimal siteSum = param.getUs().add(param.getCa()).add(param.getMx())
                    .add(param.getUk()).add(param.getDe()).add(param.getFr()).add(param.getIt())
                    .add(param.getEs()).add(param.getNl()).add(param.getSe()).add(param.getJp())
                    .add(param.getAu()).add(param.getAe()).add(param.getSg()).add(param.getSa())
                    .add(param.getInd()).add(param.getPl()).add(param.getTr().add(param.getBe()));

            if (seasonSum.setScale(0, RoundingMode.HALF_UP).compareTo(siteSum.setScale(0, RoundingMode.HALF_UP)) != 0) {
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "Q1到Q4利润和各站点(除去欧洲)利润之和不相等！");
            }
            //当前记录匹配销售额列表年度销售额，计算年度利润率
            QueryWrapper<SalesVolumeTarget> qr = new QueryWrapper<>();
            qr
                    .eq("PLATFORM", pa.getPlatform())
                    .eq("DEPARTMENT", pa.getDepartment())
                    .eq("TEAM", pa.getTeam())
                    .eq(StrUtil.isNotEmpty(pa.getShopName()),"SHOP_NAME", pa.getShopName())
                    .eq(StrUtil.isNotEmpty(pa.getProductType()),"PRODUCT_TYPE", pa.getProductType())
                    .eq(StrUtil.isNotEmpty(pa.getCompanyBrand()),"COMPANY_BRAND", pa.getCompanyBrand())
                    .eq("NEWOLD_PRODUCT", pa.getNewoldProduct())
                    .eq("YEAR", pa.getYear())
                    .eq("VERSION", pa.getVersion())
                    .last("AND ROWNUM = 1");
            SalesVolumeTarget salesVolume = salesVolumeTargetMapper.selectOne(qr);
            if (salesVolume == null || salesVolume.getSeasonOne() == null || salesVolume.getSeasonTwo() == null ||
                    salesVolume.getSeasonThree() == null || salesVolume.getSeasonFour() == null) {
                return ResponseData.error(StrUtil.format("未匹配到当前维度{}{}销售收入", param.getYear(), param.getVersion()));
            } else {
                BigDecimal profityearTarget = param.getSeasonOne().add(param.getSeasonTwo()).add(param.getSeasonThree())
                        .add(param.getSeasonFour());
                BigDecimal salesyearTarget = salesVolume.getSeasonOne().add(salesVolume.getSeasonTwo()).add(salesVolume.getSeasonThree())
                        .add(salesVolume.getSeasonFour());
                //计算年度利润率
                BigDecimal profitMargin=salesyearTarget.equals(BigDecimal.ZERO)?BigDecimal.ZERO:profityearTarget.divide(salesyearTarget,4, RoundingMode.HALF_UP);
                param.setProfitMargin(profitMargin);
            }

            param.setUpdateAt(new Date());
            param.setUpdateBy(LoginContext.me().getLoginUser().getName());
            this.mapper.updateById(param);
            return ResponseData.success();
        }catch(Exception e){
            log.error("利润目标修改异常，修改失败！",e);
            return ResponseData.error("利润目标修改异常，修改失败！");
        }
    }

    @DataSource(name = "sales")
    @Override
    public ProfitTargetResult getQuantity(ProfitTargetParam param) {
        return this.baseMapper.getQuantity(param);
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getPlatformSelect() {
        QueryWrapper<ProfitTarget> profitTargetWrapper = new QueryWrapper<>();
        profitTargetWrapper = profitTargetWrapper.select("platform").groupBy("platform");
        return transformLowerCase(this.baseMapper.selectMaps(profitTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getProductTypeSelect() {
        QueryWrapper<ProfitTarget> profitTargetWrapper = new QueryWrapper<>();
        profitTargetWrapper = profitTargetWrapper.select("product_type").groupBy("product_type");
        return transformLowerCase(this.baseMapper.selectMaps(profitTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getDepartmentSelect() {
        QueryWrapper<ProfitTarget> profitTargetWrapper = new QueryWrapper<>();
        profitTargetWrapper = profitTargetWrapper.select("department").groupBy("department").orderByAsc("TO_NUMBER(REGEXP_SUBSTR(TRANSLATE(department,'一二三四五六七八九','123456789'),'[0-9]+',1))");
        return transformLowerCase(this.baseMapper.selectMaps(profitTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getTeamSelect() {
        QueryWrapper<ProfitTarget> profitTargetWrapper = new QueryWrapper<>();
        profitTargetWrapper = profitTargetWrapper.select("team").groupBy("team").orderByAsc("TO_NUMBER(REGEXP_SUBSTR(team,'[0-9]+',1))");
        return transformLowerCase(this.baseMapper.selectMaps(profitTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getCompanyBrandSelect() {
        QueryWrapper<ProfitTarget> profitTargetWrapper = new QueryWrapper<>();
        profitTargetWrapper = profitTargetWrapper.select("COMPANY_BRAND").groupBy("COMPANY_BRAND");
        return transformLowerCase(this.baseMapper.selectMaps(profitTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getYearSelect() {
        QueryWrapper<ProfitTarget> profitTargetWrapper = new QueryWrapper<>();
        profitTargetWrapper = profitTargetWrapper.select("year").groupBy("year");
        return transformLowerCase(this.baseMapper.selectMaps(profitTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getVersionSelect(ProfitTargetParam param) {
        QueryWrapper<ProfitTarget> profitTargetWrapper = new QueryWrapper<>();
        if  ( StrUtil.isNotEmpty(param.getYear())){
            profitTargetWrapper = profitTargetWrapper
                    .eq(param.getYear() != null && param.getYear().length() > 0,"YEAR",param.getYear())
                    .select("version").groupBy("version").orderByAsc("version");
        }
        return transformLowerCase(this.baseMapper.selectMaps(profitTargetWrapper));
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
            return ResponseData.error("上传文件为空!");
        }

        log.info("导入Excel处理开始");
        String account = LoginContext.me().getLoginUser().getName();
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<ProfitTarget>();
//            EasyExcel.read(buffer, ProfitTarget.class, listener).sheet()
//                    .doRead();


            ExcelReader excelReader  = EasyExcel.read(file.getInputStream(), ProfitTarget.class, listener).build();
            excelReader.read(EasyExcel.readSheet("补贴前利润").build());



            List<ProfitTarget> dataList = listener.getDataList();
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据补贴前利润为空，导入失败！");
            }

            //异常数据集合
            List<ProfitTarget> errorList = new ArrayList<>();
            this.validation(dataList,errorList,account,year,version,platformList,departmentTeamList,productTypeList,brandList,jpShops);

            //批量保存更新表数据
            if(CollectionUtil.isNotEmpty(errorList)){
                String fileName = this.dealImportErrorList(dataList);
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "导入失败，存在异常数据！", fileName);
            }
           else{
                //版本为空
                String MaxVersion;
                if(StringUtil.isEmpty(version)){
                    //根据年度，查出数据库中存在最大版本 Vn，版本设置为Vn+1;
                    QueryWrapper<ProfitTarget> qw = new QueryWrapper<>();
                    qw.eq("YEAR", year)
                            .select("case when MAX(VERSION) is not null then 'V'||TO_CHAR(MAX(TO_NUMBER(SUBSTR(VERSION,2)))) end as version");
                    ProfitTarget profitTarget=this.baseMapper.selectOne(qw);
                    qw.clear();

                    if(profitTarget!=null)
                    {
                        int MaxVersionNum=Integer.parseInt(profitTarget.getVersion().replace("V",""))+1;
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
                    QueryWrapper<ProfitTarget> qw = new QueryWrapper<>();
                    qw.eq("YEAR", year)
                            .eq("VERSION",version);
                    List<ProfitTarget> target=this.baseMapper.selectList(qw);
                    qw.clear();

                    dataList.stream().forEach((entity)->{
                        entity.setVersion(version);
                        entity.setYear(new BigDecimal(year));
                        entity.setCurrency(currency);
                    });

                    if(target.size()>0 && BigDecimal.ZERO.equals(target.get(0).getConfirmStatus())){
                        //删除当前年度版本数据
                        List<BigDecimal> userIds = target.stream().map(ProfitTarget::getId).collect(Collectors.toList());
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
            log.error("导入Excel处理异常:", e);
            return ResponseData.error("导入Excel处理异常:");
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

    private void validation(List<ProfitTarget> dataList, List<ProfitTarget> errorList, String account,String year,String version
            ,List<String> platformList,List<String> departmentTeamList,List<String> productTypeList
            ,List<String> brandList,List<String> jpShops) {

        Set<String> allDate = new HashSet<>();
        //Excel重复记录
        Set<String> repeatDate = new HashSet<>();


        for (ProfitTarget i : dataList) {
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

        Iterator<ProfitTarget> iterator = dataList.listIterator();
        while(iterator.hasNext()) {
            ProfitTarget profitTarget = iterator.next();
            profitTarget.setCreateBy(account);
            profitTarget.setCreateAt(new Date());
            profitTarget.setUpdateAt(new Date());
            profitTarget.setUpdateBy(account);

            //验证平台,事业部,Team信息
            StringBuffer validInfo = new StringBuffer();
            String Platform = profitTarget.getPlatform().toUpperCase();
            if (StringUtils.isEmpty(profitTarget.getUploadRemark()) && (!"2021".equals(year)
                || !"事业五部".equals(profitTarget.getDepartment()))) {
                if (!Platform.equals("B2B") && !platformList.contains(profitTarget.getPlatform())) {
                    validInfo.append("平台有误!");
                }
                if (!departmentTeamList.contains(profitTarget.getDepartment())) {
                    validInfo.append("事业部有误!");
                }
                if (Platform.trim().equals("AMAZON") && !departmentTeamList
                    .contains(profitTarget.getTeam())) {
                    validInfo.append("Team有误!");
                }
                if (Platform.trim().equals("AMAZON") && !productTypeList
                    .contains(profitTarget.getProductType())) {
                    validInfo.append("运营大类有误!");
                }
                if (Platform.trim().equals("AMAZON") && !brandList
                    .contains(profitTarget.getCompanyBrand())) {
                    validInfo.append("销售品牌有误!");
                }
                if (validInfo.length() > 0) {
                    profitTarget.setUploadRemark(validInfo.toString());
                }
            }

            if (profitTarget.getSeasonOne() == null
                || profitTarget.getSeasonTwo() == null
                || profitTarget.getSeasonThree() == null
                || profitTarget.getSeasonFour() == null
                || profitTarget.getUs() == null
                || profitTarget.getCa() == null
                || profitTarget.getMx() == null
                || profitTarget.getUk() == null
                || profitTarget.getDe() == null
                || profitTarget.getFr() == null
                || profitTarget.getIt() == null
                || profitTarget.getEs() == null
                || profitTarget.getNl() == null
                || profitTarget.getSe() == null
                || profitTarget.getJp() == null
                || profitTarget.getAu() == null
                || profitTarget.getAe() == null
                || profitTarget.getSg() == null
                || profitTarget.getSa() == null
                || profitTarget.getInd() == null
                || profitTarget.getPl() == null
                || profitTarget.getTr() == null
                || profitTarget.getBe() == null
                || profitTarget.getEu() == null
            ) {
                profitTarget.setUploadRemark("Q1、Q2、Q3、Q4金额、站点金额均不能为空！");
                errorList.add(profitTarget);
            } else {
                //数据校验
                BigDecimal seasonSum = profitTarget.getSeasonOne().add(profitTarget.getSeasonTwo())
                    .add(profitTarget.getSeasonThree())
                    .add(profitTarget.getSeasonFour());

                BigDecimal siteSum = profitTarget.getUs().add(profitTarget.getCa())
                    .add(profitTarget.getMx())
                    .add(profitTarget.getUk()).add(profitTarget.getDe()).add(profitTarget.getFr())
                    .add(profitTarget.getIt())
                    .add(profitTarget.getEs()).add(profitTarget.getNl()).add(profitTarget.getSe())
                    .add(profitTarget.getJp())
                    .add(profitTarget.getAu()).add(profitTarget.getAe()).add(profitTarget.getSg())
                    .add(profitTarget.getSa())
                    .add(profitTarget.getInd()).add(profitTarget.getPl()).add(profitTarget.getTr()).add(profitTarget.getBe());
                if (seasonSum.setScale(0, RoundingMode.HALF_UP)
                    .compareTo(siteSum.setScale(0, RoundingMode.HALF_UP)) != 0) {
                    profitTarget.setUploadRemark("Q1到Q4利润和各站点(除去欧洲)利润之和不相等！");

                    errorList.add(profitTarget);
                    //移除异常的数据
                    //iterator.remove();
                }
            }

            //校验重复数据
            if (!"2021".equals(year) || !("事业五部".equals(profitTarget.getDepartment()))) {
                String sb = new StringBuffer().append(profitTarget.getPlatform())
                    .append(profitTarget.getDepartment())
                    .append(profitTarget.getTeam()).append(profitTarget.getProductType())
                    .append(profitTarget.getCompanyBrand())
                    .append(profitTarget.getShopName())
                    .append(profitTarget.getNewoldProduct()).toString();
                if (repeatDate.contains(sb)) {
                    profitTarget.setUploadRemark(
                        profitTarget.getUploadRemark() == null || profitTarget.getUploadRemark()
                            .equals("") ? "数据重复，请排查重复数据！"
                            : profitTarget.getUploadRemark() + "数据重复，请排查重复数据！");
                }
            }

            //根据事业部、Team、运营大类、销售品牌、年、版本匹配销售额目标表已确认数据
            if (profitTarget.getUploadRemark() == null || profitTarget.getUploadRemark()
                .equals("")) {
                //版本为空，取存在最大版本，否则为V1；
                if (StrUtil.isEmpty(version)) {
                    String maxVersion = mapper.selectMaxVersionByYear(year);
                    version = maxVersion == null ? "V1" : "V" + (
                        Integer.parseInt(StrUtil.strip(maxVersion, "V")) + 1);
                }
                SalesVolumeTarget salesVolume = null;

                if (!"2021".equals(year) || !("事业五部".equals(profitTarget.getDepartment()))) {

                    QueryWrapper<SalesVolumeTarget> qr = new QueryWrapper<>();
                      qr.eq("PLATFORM", profitTarget.getPlatform())
                        .eq("DEPARTMENT", profitTarget.getDepartment())
                        .eq("TEAM", profitTarget.getTeam())
                        .eq(StrUtil.isNotEmpty(profitTarget.getShopName()),"SHOP_NAME", profitTarget.getShopName())
                        .eq(StrUtil.isNotEmpty(profitTarget.getProductType()),"PRODUCT_TYPE", profitTarget.getProductType())
                        .eq(StrUtil.isNotEmpty(profitTarget.getCompanyBrand()),"COMPANY_BRAND", profitTarget.getCompanyBrand())
                        .eq("NEWOLD_PRODUCT", profitTarget.getNewoldProduct())
                        .eq("YEAR", year)
                        .eq("VERSION", version)
                        .last("AND ROWNUM = 1");
                    salesVolume = salesVolumeTargetMapper.selectOne(qr);
                    if (salesVolume == null || salesVolume.getSeasonOne() == null
                        || salesVolume.getSeasonTwo() == null ||
                        salesVolume.getSeasonThree() == null
                        || salesVolume.getSeasonFour() == null) {
                        profitTarget.setUploadRemark(
                            StrUtil.format("未匹配到{}{}当前维度销售收入", year, version));
                    } else {
                        BigDecimal profityearTarget = profitTarget.getSeasonOne()
                            .add(profitTarget.getSeasonTwo()).add(profitTarget.getSeasonThree())
                            .add(profitTarget.getSeasonFour());
                        BigDecimal salesyearTarget = salesVolume.getSeasonOne()
                            .add(salesVolume.getSeasonTwo()).add(salesVolume.getSeasonThree())
                            .add(salesVolume.getSeasonFour());
                        //计算年度利润率
                        BigDecimal profitMargin =
                            salesyearTarget.equals(BigDecimal.ZERO) ? BigDecimal.ZERO
                                : profityearTarget.divide(salesyearTarget, 4, RoundingMode.HALF_UP);
                        profitTarget.setProfitMargin(profitMargin);
                    }
                } else {
                    QueryWrapper<SalesVolumeTarget> qw = new QueryWrapper<>();
                    qw.select("PLATFORM", "DEPARTMENT", "SUM(SEASON_ONE) SEASON_ONE",
                        "SUM(SEASON_TWO) SEASON_TWO", "SUM(SEASON_THREE) SEASON_THREE",
                        "SUM(SEASON_FOUR) SEASON_FOUR")
                        .eq("PLATFORM", profitTarget.getPlatform())
                        .eq("DEPARTMENT", profitTarget.getDepartment())
                        .eq("YEAR", year)
                        .eq("VERSION", version)
                        .groupBy("PLATFORM", "DEPARTMENT");
                    salesVolume = salesVolumeTargetMapper.selectOne(qw);
                    if (salesVolume == null || salesVolume.getSeasonOne() == null
                        || salesVolume.getSeasonTwo() == null ||
                        salesVolume.getSeasonThree() == null
                        || salesVolume.getSeasonFour() == null) {
                        profitTarget.setUploadRemark(
                            StrUtil.format("未匹配到{}{}当前维度销售收入", year, version));
                    } else {
                        BigDecimal profityearTarget = profitTarget.getSeasonOne()
                            .add(profitTarget.getSeasonTwo()).add(profitTarget.getSeasonThree())
                            .add(profitTarget.getSeasonFour());
                        BigDecimal salesyearTarget = salesVolume.getSeasonOne()
                            .add(salesVolume.getSeasonTwo()).add(salesVolume.getSeasonThree())
                            .add(salesVolume.getSeasonFour());
                        //计算年度利润率
                        BigDecimal profitMargin =
                            salesyearTarget.equals(BigDecimal.ZERO) ? BigDecimal.ZERO
                                : profityearTarget.divide(salesyearTarget, 4, RoundingMode.HALF_UP);
                        profitTarget.setProfitMargin(profitMargin);
                    }
                }

            }
            if (StringUtils.isNotEmpty(profitTarget.getUploadRemark())) {
                errorList.add(profitTarget);
                //移除异常的数据
                //iterator.remove();
            }
        }
    }

    private String dealImportErrorList(List<ProfitTarget> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, ProfitTarget.class)
                    .sheet("补贴前利润").doWrite(errorList);
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
