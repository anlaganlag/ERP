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
import com.tadpole.cloud.platformSettlement.modular.sales.entity.*;
import com.tadpole.cloud.platformSettlement.modular.sales.mapper.*;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.SalesTargetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.SalesTargetResult;
import com.tadpole.cloud.platformSettlement.modular.sales.service.ISalesTargetService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 销量目标 服务实现类
 * </p>
 *
 * @author gal
 * @since 2022-03-01
 */
@Service
@Slf4j
public class SalesTargetServiceImpl extends ServiceImpl<SalesTargetMapper, SalesTarget> implements ISalesTargetService {

    @Autowired
    private ISalesTargetService salesTargetService;
    @Autowired
    private SalesTargetMapper mapper;
    @Autowired
    private SalesVolumeTargetMapper salesVolumeTargetMapper;
    @Autowired
    private CollectionTargetMapper collectionTargetMapper;
    @Autowired
    private ProfitTargetMapper profitTargetMapper;
    @Autowired
    private AdvertisingBudgetMapper advertisingBudgetMapper;
    @Autowired
    private NewProductBudgetMapper newProductBudgetMapper;
    @Autowired
    private InventoryDemandMapper inventoryDemandMapper;
    @Resource
    private TargetBoardMapper validMapper;

    @DataSource(name = "sales")
    @Override
    public List<SalesTargetResult> findPageBySpec(SalesTargetParam param) {
        List<SalesTargetResult> page = this.baseMapper.findPageBySpec(param);
        return page;
    }

    @DataSource(name = "sales")
    @Override
    public List<SalesTargetResult> export(SalesTargetParam param) {
        List<SalesTargetResult> page = this.baseMapper.findPageBySpec(param);
        return page;
    }

//    @DataSource(name = "sales")
//    @Override
//    public ResponseData confirm(SalesTargetParam param) {
//
//        String account = loginUserInfo.getUserName();
//        if  (StrUtil.isEmpty(account )) {
//            return ResponseData.error("当前登录用户为空！");
//        }
//        if  ( StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getVersion())){
//            return ResponseData.error("年份和版本不能为空！");
//        }
//        UpdateWrapper<SalesTarget> updateWrapper = new UpdateWrapper<>();
//        updateWrapper
//                .eq("year", param.getYear())
//                .eq("version", param.getVersion())
//                .set("CONFIRM_STATUS",1)
//                .set("CONFIRM_DATE", new Date())
//                .set("CONFIRM_BY", loginUserInfo.getUserName());
//        this.mapper.update(null, updateWrapper);
//        return ResponseData.success();
//    }

    @DataSource(name = "sales")
    @Override
    public ResponseData confirm(SalesTargetParam param) {
        String account = LoginContext.me().getLoginUser().getName();
        if  (StrUtil.isEmpty(account )) {
            return ResponseData.error("当前登录用户为空！");
        }
        Date now = new Date();
        String year = param.getYear();
        String version = param.getVersion();
        if  ( StrUtil.isEmpty(year) || StrUtil.isEmpty(version)){
            return ResponseData.error("年份和版本不能为空！");
        }

        UpdateWrapper<SalesTarget> updateWrapper1 = new UpdateWrapper<>();
        updateWrapper1
                .eq("year", year)
                .eq("version", version)
                .set("CONFIRM_STATUS",1)
                .set("CONFIRM_DATE", now)
                .set("CONFIRM_BY", LoginContext.me().getLoginUser().getName());
        this.mapper.update(null, updateWrapper1);


        UpdateWrapper<SalesVolumeTarget> updateWrapper2 = new UpdateWrapper<>();
        updateWrapper2
                .eq("year", year)
                .eq("version", version)
                .set("CONFIRM_STATUS",1)
                .set("CONFIRM_DATE", now)
                .set("CONFIRM_BY", LoginContext.me().getLoginUser().getName());
        salesVolumeTargetMapper.update(null, updateWrapper2);

        UpdateWrapper<CollectionTarget> updateWrapper3 = new UpdateWrapper<>();
        updateWrapper3
                .eq("year", year)
                .eq("version", version)
                .set("CONFIRM_STATUS",1)
                .set("CONFIRM_DATE", now)
                .set("CONFIRM_BY", LoginContext.me().getLoginUser().getName());
        collectionTargetMapper.update(null, updateWrapper3);

        UpdateWrapper<ProfitTarget> updateWrapper4 = new UpdateWrapper<>();
        updateWrapper4
                .eq("year", year)
                .eq("version", version)
                .set("CONFIRM_STATUS",1)
                .set("CONFIRM_DATE", now)
                .set("CONFIRM_BY", LoginContext.me().getLoginUser().getName());
        profitTargetMapper.update(null, updateWrapper4);

        UpdateWrapper<AdvertisingBudget> updateWrapper5 = new UpdateWrapper<>();
        updateWrapper5
                .eq("year", year)
                .eq("version", version)
                .set("CONFIRM_STATUS",1)
                .set("CONFIRM_DATE", now)
                .set("CONFIRM_BY", LoginContext.me().getLoginUser().getName());
        advertisingBudgetMapper.update(null, updateWrapper5);

        UpdateWrapper<NewProductBudget> updateWrapper6 = new UpdateWrapper<>();
        updateWrapper6
                .eq("year", year)
                .eq("version", version)
                .set("CONFIRM_STATUS",1)
                .set("CONFIRM_DATE", now)
                .set("CONFIRM_BY", LoginContext.me().getLoginUser().getName());
        newProductBudgetMapper.update(null, updateWrapper6);

        UpdateWrapper<InventoryDemand> updateWrapper7 = new UpdateWrapper<>();
        updateWrapper7
                .eq("year", year)
                .eq("version", version)
                .set("CONFIRM_STATUS",1)
                .set("CONFIRM_DATE", now)
                .set("CONFIRM_BY", LoginContext.me().getLoginUser().getName());
        inventoryDemandMapper.update(null, updateWrapper7);
        return ResponseData.success();
    }

    @DataSource(name = "sales")
    @Override
    public ResponseData edit(SalesTarget param) {
        if(param.getId()==null || "0".equals(param.getId().toString())){
            return ResponseData.error("ID不能为空");
        }
        SalesTarget pa=this.mapper.selectById(param.getId());
        if (pa == null) {
            return ResponseData.error("无对应记录");
        }
        if (!BigDecimal.ZERO.equals(pa.getConfirmStatus())) {
            return ResponseData.error("数据已确认,无法修改");
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
            return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "Q1、Q2、Q3、Q4金额、站点金额均不能为空");
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
            return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE,"Q1到Q4销量和各站点(除去欧洲)销量之和不相等！");
        }

        SalesTarget ss = new SalesTarget();
        BeanUtils.copyProperties(param, ss);
        ss.setId(param.getId());
        ss.setUpdateBy(LoginContext.me().getLoginUser().getName());
        ss.setUpdateAt(new Date());

        this.baseMapper.updateById(ss);
        return ResponseData.success();
    }

    @DataSource(name = "sales")
    @Override
    public SalesTargetResult getQuantity(SalesTargetParam param) {
        return this.baseMapper.getQuantity(param);
    }

    @DataSource(name = "EBMS")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public  List<String> selectJpShopNameByEbms(){
        return mapper.selectJpShopNameByEbms();
    }

    @DataSource(name = "sales")
    @Override
    public  String  getMaxVersion (String year){
        return mapper.getMaxVersion(year);
    }

    @DataSource(name = "sales")
    @Override
    public  String  isVersionConfirmed (String year,String version){
         return mapper.isVersionConfirmed(year,version) ;
    }

    @DataSource(name = "sales")
    @Override
    public  Map<String,String>  isVersionUnConfirmed (String table,String year){
        return mapper.isVersionUnConfirmed(table,year) ;
    }

    @DataSource(name = "sales")
    @Override
    public  String isDiffDimension (String year,String version,String currency,MultipartFile file) throws Exception {
        try {
            log.info("校验行数");
            BaseExcelListener listener = new BaseExcelListener<SalesTarget>();

            ExcelReader excelReader = EasyExcel.read(file.getInputStream(), SalesTarget.class, listener).build();
            excelReader.read(EasyExcel.readSheet("销售数量").build());
            List<SalesTarget> dataList1 = listener.getDataList();

            BaseExcelListener listener2 = new BaseExcelListener<SalesVolumeTarget>();
            ExcelReader excelReader2 = EasyExcel.read(file.getInputStream(), SalesVolumeTarget.class, listener2).build();
            excelReader2.read(EasyExcel.readSheet("销售收入").build());
            List<SalesVolumeTarget> dataList2 = listener2.getDataList();

            BaseExcelListener listener3 = new BaseExcelListener<CollectionTarget>();
            ExcelReader excelReader3 = EasyExcel.read(file.getInputStream(), CollectionTarget.class, listener3).build();
            excelReader3.read(EasyExcel.readSheet("回款").build());

            List<CollectionTarget> dataList3 = listener3.getDataList();

            BaseExcelListener listener4 = new BaseExcelListener<ProfitTarget>();
            ExcelReader excelReader4 = EasyExcel.read(file.getInputStream(), ProfitTarget.class, listener4).build();
            excelReader4.read(EasyExcel.readSheet("补贴前利润").build());
            List<ProfitTarget> dataList4 = listener4.getDataList();

            Integer size = dataList1.size();
            Integer size2 = dataList2.size();
            Integer size3 = dataList3.size();
            Integer size4 = dataList4.size();

            List<String> sizes = new ArrayList<>();
            if (!size.equals(size2) || !size.equals(size3) || !size.equals(size4)) {
                sizes.add(String.valueOf(size));
                sizes.add(String.valueOf(size2));
                sizes.add(String.valueOf(size3));
                sizes.add(String.valueOf(size4));
                return StrUtil.format("销售数量、销售收入、回款、补贴前利润数量行数不一致[{}]", String.join("、", sizes));
            }

            Set<String> d8List1 = dataList1.stream().map(SalesTarget::getD8).collect(Collectors.toSet());
            Set<String> d8List2 = dataList2.stream().map(SalesVolumeTarget::getD8).collect(Collectors.toSet());
            Set<String> d8List3 = dataList3.stream().map(CollectionTarget::getD8).collect(Collectors.toSet());
            Set<String> d8List4 = dataList3.stream().map(CollectionTarget::getD8).collect(Collectors.toSet());
            StringBuffer sb1 = new StringBuffer();
            StringBuffer sb2 = new StringBuffer();
            StringBuffer sb3 = new StringBuffer();
            for (String s : d8List1) {
                if (!d8List2.contains(s)) {
                    sb1.append(StrUtil.format("[{}] ", s));
                }
                if (!d8List3.contains(s)) {
                    sb2.append(StrUtil.format("[{}] ", s));
                }
                if (!d8List4.contains(s)) {
                    sb3.append(StrUtil.format("[{}] ", s));
                }
            }
            String msg1 = "";
            String msg2 = "";
            String msg3 = "";
            if (sb1.length()>0) {
                msg1 ="销售额目标缺失维度: "+sb1 + "\n";
            }
            if (sb2.length()>0) {
                msg2 ="回款目标缺失维度: "+sb2 + "\n";
            }
            if (sb3.length()>0) {
                msg3 ="利润目标缺失维度: "+sb3 + "\n";
            }
            return msg1+msg2+msg3;

        } catch (Exception e) {
            throw e;
        }
    }

    @DataSource(name = "sales")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public  void deleCurrent (String year,String version){
        QueryWrapper<SalesTarget> queryWrapper0 = new QueryWrapper<>();
        queryWrapper0.eq("year", year).eq("version",version).eq("confirm_status",0);
        mapper.delete(queryWrapper0);
        QueryWrapper<SalesVolumeTarget> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("year", year).eq("version",version).eq("confirm_status",0);
        salesVolumeTargetMapper.delete(queryWrapper1);
        QueryWrapper<CollectionTarget> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("year", year).eq("version",version).eq("confirm_status",0);
        collectionTargetMapper.delete(queryWrapper2);
        QueryWrapper<ProfitTarget> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("year", year).eq("version",version).eq("confirm_status",0);
        profitTargetMapper.delete(queryWrapper3);
        QueryWrapper<AdvertisingBudget> queryWrapper4 = new QueryWrapper<>();
        queryWrapper4.eq("year", year).eq("version",version).eq("confirm_status",0);
        advertisingBudgetMapper.delete(queryWrapper4);
        QueryWrapper<NewProductBudget> queryWrapper5 = new QueryWrapper<>();
        queryWrapper5.eq("year", year).eq("version",version).eq("confirm_status",0);
        newProductBudgetMapper.delete(queryWrapper5);
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getPlatformSelect() {
        QueryWrapper<SalesTarget> salesTargetWrapper = new QueryWrapper<>();
        salesTargetWrapper = salesTargetWrapper.select("platform").groupBy("platform");
        return transformLowerCase(this.baseMapper.selectMaps(salesTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getProductTypeSelect() {
        QueryWrapper<SalesTarget> salesTargetWrapper = new QueryWrapper<>();
        salesTargetWrapper = salesTargetWrapper.select("product_type").groupBy("product_type");
        return transformLowerCase(this.baseMapper.selectMaps(salesTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getDepartmentSelect() {
        QueryWrapper<SalesTarget> salesTargetWrapper = new QueryWrapper<>();
        salesTargetWrapper = salesTargetWrapper.select("department").groupBy("department").orderByAsc("TO_NUMBER(REGEXP_SUBSTR(TRANSLATE(department,'一二三四五六七八九','123456789'),'[0-9]+',1))");
        return transformLowerCase(this.baseMapper.selectMaps(salesTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getTeamSelect() {
        QueryWrapper<SalesTarget> salesTargetWrapper = new QueryWrapper<>();
        salesTargetWrapper = salesTargetWrapper.select("team").groupBy("team").orderByAsc("TO_NUMBER(REGEXP_SUBSTR(team,'[0-9]+',1))");
        return transformLowerCase(this.baseMapper.selectMaps(salesTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getCompanyBrandSelect() {
        QueryWrapper<SalesTarget> salesTargetWrapper = new QueryWrapper<>();
        salesTargetWrapper = salesTargetWrapper.select("COMPANY_BRAND").groupBy("COMPANY_BRAND");
        return transformLowerCase(this.baseMapper.selectMaps(salesTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getYearSelect() {
        QueryWrapper<SalesTarget> salesTargetWrapper = new QueryWrapper<>();
        salesTargetWrapper = salesTargetWrapper.select("year").groupBy("year");
        return transformLowerCase(this.baseMapper.selectMaps(salesTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getVersionSelect(SalesTargetParam param) {
        QueryWrapper<SalesTarget> salesTargetWrapper = new QueryWrapper<>();
        if  ( StrUtil.isNotEmpty(param.getYear())){
            salesTargetWrapper = salesTargetWrapper.eq("YEAR",param.getYear())
                    .select("version").groupBy("version").orderByAsc("version");
        }
        return transformLowerCase(this.baseMapper.selectMaps(salesTargetWrapper));
    }

    @DataSource(name = "sales")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData importExcel(String year,String version,String currency,MultipartFile file,
                                    List<String> platformList,List<String> departmentTeamList,
                                    List<String> productTypeList,List<String> brandList,List<String> jpShops) throws Exception {
        if  ( StrUtil.isEmpty(year) || StrUtil.isEmpty(currency)){
            return ResponseData.error("年份和币种不能为空！");
        }
        if (file == null) {
            return ResponseData.error("上传文件为空！");
        }
        if(StringUtil.isEmpty(version)) {
            Map<String, String> versionUnConfirmed = salesTargetService.isVersionUnConfirmed("SALES_TARGET",year);
            if (versionUnConfirmed != null && new BigDecimal(BigInteger.ZERO).equals(versionUnConfirmed.get("CONFIRM_STATUS"))) {
                return ResponseData.error(StrUtil.format("销量目标当前年份{}存在未确认版本{},不能导入新版本", year, versionUnConfirmed.get("VERSION")));
            }
        }

        log.info("导入Excel处理开始");
        String account = LoginContext.me().getLoginUser().getName();
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<SalesTarget>();
//            EasyExcel.read(buffer, SalesTarget.class, listener).sheet()
//                    .doRead();

            ExcelReader excelReader  = EasyExcel.read(file.getInputStream(), SalesTarget.class, listener).build();
            excelReader.read(EasyExcel.readSheet("销售数量").build());
            List<SalesTarget> dataList = listener.getDataList();

            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据销售数量为空，导入失败！");
            }



            //异常数据集合
            List<SalesTarget> errorList = new ArrayList<>();
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
                    QueryWrapper<SalesTarget> qw = new QueryWrapper<>();
                    qw.eq("YEAR", year)
                            .select("case when MAX(VERSION) is not null then 'V'||TO_CHAR(MAX(TO_NUMBER(SUBSTR(VERSION,2)))) end as version");
                    SalesTarget salesTarget=this.baseMapper.selectOne(qw);
                    qw.clear();

                    if(salesTarget!=null)
                    {
                        int MaxVersionNum=Integer.parseInt(salesTarget.getVersion().replace("V",""))+1;
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
                    QueryWrapper<SalesTarget> qw = new QueryWrapper<>();
                    qw.eq("YEAR", year)
                            .eq("VERSION",version);
                    List<SalesTarget> target=this.baseMapper.selectList(qw);
                    qw.clear();

                    dataList.stream().forEach((entity)->{
                        entity.setVersion(version);
                        entity.setYear(new BigDecimal(year));
                        entity.setCurrency(currency);
                    });

                    if(target.size()>0 && BigDecimal.ZERO.equals(target.get(0).getConfirmStatus())){
                        //删除当前年度版本数据
                        List<BigDecimal> userIds = target.stream().map(SalesTarget::getId).collect(Collectors.toList());
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
            log.error("销售数量导入异常:"+e);
            throw new Exception("销售数量导入异常:"+e);
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

    @DataSource(name = "sales")
    @Override
    public ResponseData uploadAll(String year,String version,String currency,MultipartFile file,
                                    List<String> platformList,List<String> departmentTeamList,
                                    List<String> productTypeList,List<String> brandList,List<String> jpshops) {
        if  ( StrUtil.isEmpty(year) || StrUtil.isEmpty(currency)){
            return ResponseData.error("年份和币种不能为空！");
        }
        if (file == null) {
            return ResponseData.error("上传文件为空！");
        }
        log.info("一键导入Excel处理开始");
        String account = LoginContext.me().getLoginUser().getName();
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());

            //销量
            BaseExcelListener listener = new BaseExcelListener<SalesTarget>();
            ExcelReader excelReader  = EasyExcel.read(file.getInputStream(), SalesTarget.class, listener).build();
            excelReader.read(EasyExcel.readSheet("销量数量").build());
            List<SalesTarget> dataList = listener.getDataList();

            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据销量为空，导入失败！");
            }


            //销售额
            BaseExcelListener listener1 = new BaseExcelListener<SalesVolumeTarget>();
            ExcelReader excelReader1  = EasyExcel.read(file.getInputStream(), SalesVolumeTarget.class, listener1).build();
            excelReader1.read(EasyExcel.readSheet("销售收入").build());
            List<SalesVolumeTarget> dataList1 = listener1.getDataList();


            if(CollectionUtil.isEmpty(dataList1)){
                return ResponseData.error("导入数据销售额为空，导入失败！");
            }

            //异常数据集合
            List<SalesTarget> errorList = new ArrayList<>();
            this.validation(dataList,errorList,account,platformList,departmentTeamList,productTypeList,brandList,jpshops,year);

            //批量保存更新表数据
            if(CollectionUtil.isNotEmpty(errorList)){
                String fileName = this.dealImportErrorList(dataList);
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "导入失败，存在异常数据！", fileName);
            }else{
                //版本为空
                String MaxVersion;
                if(StringUtil.isEmpty(version)){
                    //根据年度，查出数据库中存在最大版本 Vn，版本设置为Vn+1;
                    QueryWrapper<SalesTarget> qw = new QueryWrapper<>();
                    qw.eq("YEAR", year)
                            .select("case when MAX(VERSION) is not null then 'V'||TO_CHAR(MAX(TO_NUMBER(SUBSTR(VERSION,2)))) end as version");
                    SalesTarget salesTarget=this.baseMapper.selectOne(qw);
                    qw.clear();

                    if(salesTarget!=null)
                    {
                        int MaxVersionNum=Integer.parseInt(salesTarget.getVersion().replace("V",""))+1;
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
                    QueryWrapper<SalesTarget> qw = new QueryWrapper<>();
                    qw.eq("YEAR", year)
                            .eq("VERSION",version);
                    List<SalesTarget> target=this.baseMapper.selectList(qw);
                    qw.clear();

                    dataList.stream().forEach((entity)->{
                        entity.setVersion(version);
                        entity.setYear(new BigDecimal(year));
                        entity.setCurrency(currency);
                    });

                    if(target.size()>0 && BigDecimal.ZERO.equals(target.get(0).getConfirmStatus())){
                        //删除当前年度版本数据
                        List<BigDecimal> userIds = target.stream().map(SalesTarget::getId).collect(Collectors.toList());
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
            log.error("销量目标导入异常"+e);
            return ResponseData.error("销量目标导入异常"+e);
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

    private void validation(List<SalesTarget> dataList, List<SalesTarget> errorList, String account
            ,List<String> platformList,List<String> departmentTeamList,List<String> productTypeList
            ,List<String> brandList,List<String> jpShops,String year) {

        Set<String> allDate = new HashSet<>();
        //Excel重复记录
        Set<String> repeatDate = new HashSet<>();

        for (SalesTarget i : dataList) {
            StringBuffer uploadRemark = new StringBuffer();
            if ("事业五部".equals(i.getDepartment())) {
                if(i.getShopName()!=null && !jpShops.contains(i.getShopName())){
                    uploadRemark.append(StrUtil.format("事业五部账号可选:[{}]",String.join( "、", jpShops)));
                }
            }
            if ("2021".equals(year) && ("事业五部".equals(i.getDepartment()))) {
                continue;
            }
            String platform=i.getPlatform().toUpperCase();
            String newoldProduct = i.getNewoldProduct();

            if(StringUtils.isEmpty(i.getPlatform())){
                uploadRemark.append("平台不能为空！");
            }
            if(StringUtils.isEmpty(platform) && platform.trim().equals("Amazon") && StringUtils.isEmpty(newoldProduct) && ! (newoldProduct.equals("新品") || newoldProduct.equals("旧品"))){
                uploadRemark.append(StrUtil.format("新旧品[{}]有误!,可选值:'新品' '旧品'",newoldProduct));
            }


            if(StringUtils.isEmpty(i.getPlatform())){
                uploadRemark.append("平台不能为空！");
            }
            if(StringUtils.isEmpty(i.getDepartment())){
                uploadRemark.append("事业部不能为空！");
            }
            if(StringUtils.isEmpty(i.getTeam())){
                uploadRemark.append("Team不能为空！");
            }
            if(platform.trim().equals("AMAZON") && StringUtils.isEmpty(i.getProductType())){
                uploadRemark.append("运营大类不能为空！");
            }
            if(platform.trim().equals("AMAZON") && StringUtils.isEmpty(i.getCompanyBrand())){
                uploadRemark.append("销售品牌不能为空！");
            }

            i.setUploadRemark(uploadRemark == null || uploadRemark.equals("") ? "" : uploadRemark.toString());
            String sb = new StringBuffer().append(i.getPlatform())
                                          .append(i.getDepartment())
                                          .append(i.getTeam())
                                          .append(i.getProductType())
                                          .append(i.getCompanyBrand())
                                          .append(i.getShopName())
                                          .append(i.getNewoldProduct())
                                          .toString();
            if(allDate.contains(sb)){
                repeatDate.add(sb);
            }
            allDate.add(sb);
        }

        Iterator<SalesTarget> iterator = dataList.listIterator();
        while(iterator.hasNext()) {
            SalesTarget salesTarget = iterator.next();
            salesTarget.setCreateBy(account);
            salesTarget.setCreateAt(new Date());
            salesTarget.setUpdateAt(new Date());
            salesTarget.setUpdateBy(account);

            //验证平台,事业部,Team信息
            StringBuffer validInfo = new StringBuffer();
            String Platform=salesTarget.getPlatform().toUpperCase();
            if(StringUtils.isEmpty(salesTarget.getUploadRemark()) && (!"2021".equals(year) || !"事业五部".equals(salesTarget.getDepartment()))) {
                if (!Platform.equals("B2B") && !platformList.contains(salesTarget.getPlatform())) {
                    validInfo.append(StrUtil.format("平台有误:[{}]",String.join("、", platformList)));

                }
                if (!departmentTeamList.contains(salesTarget.getDepartment())) {
                    validInfo.append("事业部有误!");
                }
                if (Platform.trim().equals("AMAZON") && !departmentTeamList.contains(salesTarget.getTeam())) {
                    validInfo.append("Team有误!");
                }
                if (Platform.trim().equals("AMAZON") && !productTypeList.contains(salesTarget.getProductType())) {
                    validInfo.append("运营大类有误!");
                }
                if (Platform.trim().equals("AMAZON") && !brandList.contains(salesTarget.getCompanyBrand())) {
                    validInfo.append(StrUtil.format("销售品牌有误:[{}]",String.join("、", brandList)));
                }

                if (validInfo.length() > 0) {
                    salesTarget.setUploadRemark(validInfo.toString());
                }
            }

            if(salesTarget.getSeasonOne()==null
                    || salesTarget.getSeasonTwo()==null
                    || salesTarget.getSeasonThree()==null
                    || salesTarget.getSeasonFour()==null
                    || salesTarget.getUs()==null
                    || salesTarget.getCa()==null
                    || salesTarget.getMx()==null
                    || salesTarget.getUk()==null
                    || salesTarget.getDe()==null
                    || salesTarget.getFr()==null
                    || salesTarget.getIt()==null
                    || salesTarget.getEs()==null
                    || salesTarget.getNl()==null
                    || salesTarget.getSe()==null
                    || salesTarget.getJp()==null
                    || salesTarget.getAu()==null
                    || salesTarget.getAe()==null
                    || salesTarget.getSg()==null
                    || salesTarget.getSa()==null
                    || salesTarget.getInd()==null
                    || salesTarget.getPl()==null
                    || salesTarget.getTr()==null
                    || salesTarget.getBe()==null
                    || salesTarget.getEu()==null
            ){
                salesTarget.setUploadRemark("Q1、Q2、Q3、Q4金额、站点金额均不能为空！");
                errorList.add(salesTarget);
            }
            else{
                //数据校验
                BigDecimal seasonSum=salesTarget.getSeasonOne().add(salesTarget.getSeasonTwo()).add(salesTarget.getSeasonThree())
                        .add(salesTarget.getSeasonFour());

                BigDecimal siteSum=salesTarget.getUs().add(salesTarget.getCa()).add(salesTarget.getMx())
                        .add(salesTarget.getUk()).add(salesTarget.getDe()).add(salesTarget.getFr()).add(salesTarget.getIt())
                        .add(salesTarget.getEs()).add(salesTarget.getNl()).add(salesTarget.getSe()).add(salesTarget.getJp())
                        .add(salesTarget.getAu()).add(salesTarget.getAe()).add(salesTarget.getSg()).add(salesTarget.getSa())
                        .add(salesTarget.getInd()).add(salesTarget.getPl()).add(salesTarget.getTr().add(salesTarget.getBe()));
                if(seasonSum.setScale(0, RoundingMode.HALF_UP).compareTo(siteSum.setScale(0, RoundingMode.HALF_UP))!=0)
                {
                    salesTarget.setUploadRemark("Q1到Q4销量和各站点(除去欧洲)销量之和不相等！");

                    errorList.add(salesTarget);
                    //移除异常的数据
                    //iterator.remove();
                }
            }

            //校验重复数据
            if (!"2021".equals(year) || !("事业五部".equals(salesTarget.getDepartment()))) {
                String sb = new StringBuffer().append(salesTarget.getPlatform())
                    .append(salesTarget.getDepartment())
                    .append(salesTarget.getTeam()).append(salesTarget.getProductType())
                    .append(salesTarget.getCompanyBrand())
                    .append(salesTarget.getShopName())
                    .append(salesTarget.getNewoldProduct()).toString();
                if (repeatDate.contains(sb)) {
                    salesTarget.setUploadRemark(
                        salesTarget.getUploadRemark() == null || salesTarget.getUploadRemark()
                            .equals("") ? "数据重复，请排查重复数据！"
                            : salesTarget.getUploadRemark() + "数据重复，请排查重复数据！");
                }

                if (StringUtils.isNotEmpty(salesTarget.getUploadRemark())) {
                    errorList.add(salesTarget);
                    //移除异常的数据
                    //iterator.remove();
                }
            }

        }
    }

    private String dealImportErrorList(List<SalesTarget> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, SalesTarget.class)
                    .sheet("销售数量").doWrite(errorList);
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

    // 将map值全部转换为小写
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
