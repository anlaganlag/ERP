package com.tadpole.cloud.platformSettlement.modular.sales.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.api.ebms.model.resp.EbmsUserInfo;
import com.tadpole.cloud.platformSettlement.modular.inventory.consumer.EbmsBaseConsumer;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.InventoryTurnover;
import com.tadpole.cloud.platformSettlement.modular.sales.mapper.InventoryTurnoverMapper;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.InventoryTurnoverParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.InventoryTurnoverResult;
import com.tadpole.cloud.platformSettlement.modular.sales.service.IInventoryTurnoverService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * <p>
 * 库存周转 服务实现类
 * </p>
 *
 * @author cyt
 * @since 2022-06-01
 */
@Service
@Slf4j
public class InventoryTurnoverServiceImpl extends ServiceImpl<InventoryTurnoverMapper, InventoryTurnover> implements IInventoryTurnoverService {

    @Autowired
    private InventoryTurnoverMapper mapper;
    @Autowired
    IInventoryTurnoverService service;
    @Resource
    private EbmsBaseConsumer ebmsBaseConsumer;

    @DataSource(name = "sales")
    @Override
    public List<InventoryTurnoverResult> list(InventoryTurnoverParam param, List<String> removeList) {
        if (CollUtil.isNotEmpty(param.getTeams())) {
            for (String team : removeList) {
                if (param.getTeams().contains(team)){
                    param.setDepartment("事业五部");
                    break;
                }
            }
        }
        return mapper.list(param);
    }

    @DataSource(name = "sales")
    @Override
    public void addIdxByOne(Integer idx) {
        mapper.addIdxByOne(idx);
    }

    @DataSource(name = "sales")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData add(InventoryTurnoverParam param) throws ParseException {
        LambdaQueryWrapper<InventoryTurnover> qw = new LambdaQueryWrapper<>();
        qw.eq(InventoryTurnover::getYear, param.getYear()).apply("ROWNUM={0}", 1);
        InventoryTurnover ent = service.getOne(qw);


        LambdaQueryWrapper<InventoryTurnover> repeated = new LambdaQueryWrapper<>();
        repeated.eq(InventoryTurnover::getYear, param.getYear())
                .eq(StrUtil.isNotEmpty(param.getPlatform()),InventoryTurnover::getPlatform, param.getPlatform())
                .eq(StrUtil.isNotEmpty(param.getDepartment()),InventoryTurnover::getDepartment, param.getDepartment())
                .eq(StrUtil.isNotEmpty(param.getTeam()),InventoryTurnover::getTeam, param.getTeam())
                .eq(StrUtil.isNotEmpty(param.getProductType()),InventoryTurnover::getProductType, param.getProductType())
                .eq(StrUtil.isNotEmpty(param.getCompanyBrand()),InventoryTurnover::getCompanyBrand, param.getCompanyBrand())
                .apply("ROWNUM={0}", 1);

        InventoryTurnover repeatedOne = service.getOne(repeated);

        if (repeatedOne != null) {
            return ResponseData.error("数据重复!年份、平台、事业部、Team、运营大类、销售品牌需唯一");
        }


        if (param.getIdx() == null) {
            return ResponseData.error("序号不能空!");
        }
        String account = LoginContext.me().getLoginUser().getAccount();
        if (StringUtils.isEmpty(account)) {
            return ResponseData.error("InventoryTurnover.add>>1>>>未获取到当前登录用户信息，请登录后再试！");
        }
        EbmsUserInfo userInfo = ebmsBaseConsumer.getUserInfoByAccount(account);
        if (ObjectUtil.isEmpty(userInfo)) {
            return ResponseData.error("InventoryTurnover.add>>2>>>当前用户在EBMS系统中获取人员信息失败！");
        }
        try {
            InventoryTurnover inventoryTurnover = new InventoryTurnover();
            BeanUtils.copyProperties(param, inventoryTurnover);
            inventoryTurnover.setCreateTime(DateUtil.date());
            inventoryTurnover.setCreateBy(userInfo.getSysPerName());
            inventoryTurnover.setCreateByCode(userInfo.getSysPerCode());
            inventoryTurnover.setCreateByDept(userInfo.getSysComDeptFullName());
            inventoryTurnover.setMon1(param.getMon1() != null ? param.getMon1().toString() :null);
            inventoryTurnover.setMon2(param.getMon2() != null ? param.getMon2().toString():null);
            inventoryTurnover.setMon3(param.getMon3() != null ? param.getMon3().toString():null);
            inventoryTurnover.setMon4(param.getMon4() != null ? param.getMon4().toString():null);
            inventoryTurnover.setMon5(param.getMon5() != null ? param.getMon5().toString():null);
            inventoryTurnover.setMon6(param.getMon6() != null ? param.getMon6().toString():null);
            inventoryTurnover.setMon7(param.getMon7() != null ? param.getMon7().toString():null);
            inventoryTurnover.setMon8(param.getMon8() != null ? param.getMon8().toString():null);
            inventoryTurnover.setMon9(param.getMon9() != null ? param.getMon9().toString():null);
            inventoryTurnover.setMon10(param.getMon10() != null ? param.getMon10().toString():null);
            inventoryTurnover.setMon11(param.getMon11() != null ? param.getMon11().toString():null);
            inventoryTurnover.setMon12(param.getMon12() != null ? param.getMon12().toString():null);
            if (ent.getComfirmStatus() != null && ent.getComfirmStatus() != 0) {
                inventoryTurnover.setYear(ent.getYear());
                inventoryTurnover.setMonth(ent.getMonth());
                inventoryTurnover.setComfirmStatus(ent.getComfirmStatus());
                inventoryTurnover.setComfirmTime(DateUtil.date());
            }
            //将原后
            this.addIdxByOne(param.getIdx());
            this.save(inventoryTurnover);

            return ResponseData.success();


        } catch (Exception e) {
            return ResponseData.error("InventoryTurnover.add异常>>3>>>" + e.getMessage());
        }
    }

    @DataSource(name = "sales")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData upload(@Valid InventoryTurnoverParam param, MultipartFile file, List<String> platList, List<String> teamList, List<String> ptList, List<String> deptList, List<String> brandList) {
        if (file == null) {
            return ResponseData.error("上传文件不能为空!");
        }

        String account = LoginContext.me().getLoginUser().getAccount();
        if (StringUtils.isEmpty(account)) {
            return ResponseData.error("InventoryTurnover.upload>>1>>>未获取到当前登录用户信息，请登录后再试！");
        }
        EbmsUserInfo userInfo = ebmsBaseConsumer.getUserInfoByAccount(account);
        if (ObjectUtil.isEmpty(userInfo)) {
            return ResponseData.error("InventoryTurnover.upload>>2>>>当前用户在EBMS系统中获取人员信息失败！");
        }
        log.info("库存周转导入开始");


        LambdaQueryWrapper<InventoryTurnover> qw = new LambdaQueryWrapper<>();
        //获取当前年份已确认月份
        qw.eq(InventoryTurnover::getYear, param.getYear()).apply("ROWNUM={0}", 1);
        InventoryTurnover currentYearEnt = service.getOne(qw);
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<InventoryTurnover>();
//            EasyExcel.read(buffer, InventoryTurnover.class, listener).sheet().doRead();

            ExcelReader excelReader  = EasyExcel.read(file.getInputStream(), InventoryTurnover.class, listener).build();
            excelReader.read(EasyExcel.readSheet("库存周转").build());
            List<InventoryTurnover> dataList = listener.getDataList();
            if (CollectionUtil.isEmpty(dataList)) {
                return ResponseData.error("数据为空，无法导入！");
            }
            Integer confirmedStatus = currentYearEnt != null ? currentYearEnt.getComfirmStatus() : 0;

            //异常数据集合
            Boolean isError = this.validationIsError(dataList, confirmedStatus, param.getYear(), platList, teamList, ptList, deptList, brandList);
            if (Boolean.TRUE.equals(isError)) {
                String fileName = this.dealImportErrorList(dataList);
                //导入失败
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "存在异常数据数据", fileName);
            }


            //导入遍历处理数据逻辑
            for (InventoryTurnover i : dataList) {
                //无该年数据
                if (currentYearEnt == null) {
                    i.setCreateTime(DateUtil.date());
                    i.setCreateBy(userInfo.getSysPerName());
                    i.setCreateByCode(userInfo.getSysPerCode());
                    i.setCreateByDept(userInfo.getSysComDeptFullName());
                    i.setYear(param.getYear());
                    this.save(i);
                    //存该当年数据
                } else {
                    //获取已审核通过年月审核状态,
                    i.setYear(currentYearEnt.getYear());
                    i.setMonth(currentYearEnt.getMonth());
                    i.setComfirmStatus(currentYearEnt.getComfirmStatus());
                    i.setComfirmTime(DateUtil.date());
                    //有id更新未确认月份,比如确认2月则1月和2月不满足而不更新,只更新3月及以后
                    if (i.getId() != null) {
                        UpdateWrapper<InventoryTurnover> wp = new UpdateWrapper<>();
                        wp.eq("ID", i.getId())
                                .set(currentYearEnt.getComfirmStatus() < 1 && StrUtil.isNotEmpty(i.getMon1()), "MON1", i.getMon1())
                                .set(currentYearEnt.getComfirmStatus() < 2 && StrUtil.isNotEmpty(i.getMon2()), "MON2", i.getMon2())
                                .set(currentYearEnt.getComfirmStatus() < 3 && StrUtil.isNotEmpty(i.getMon3()), "MON3", i.getMon3())
                                .set(currentYearEnt.getComfirmStatus() < 4 && StrUtil.isNotEmpty(i.getMon4()), "MON4", i.getMon4())
                                .set(currentYearEnt.getComfirmStatus() < 5 && StrUtil.isNotEmpty(i.getMon5()), "MON5", i.getMon5())
                                .set(currentYearEnt.getComfirmStatus() < 6 && StrUtil.isNotEmpty(i.getMon6()), "MON6", i.getMon6())
                                .set(currentYearEnt.getComfirmStatus() < 7 && StrUtil.isNotEmpty(i.getMon7()), "MON7", i.getMon7())
                                .set(currentYearEnt.getComfirmStatus() < 8 && StrUtil.isNotEmpty(i.getMon8()), "MON8", i.getMon8())
                                .set(currentYearEnt.getComfirmStatus() < 9 && StrUtil.isNotEmpty(i.getMon9()), "MON9", i.getMon9())
                                .set(currentYearEnt.getComfirmStatus() < 10 && StrUtil.isNotEmpty(i.getMon10()), "MON10", i.getMon10())
                                .set(currentYearEnt.getComfirmStatus() < 11 && StrUtil.isNotEmpty(i.getMon11()), "MON11", i.getMon11())
                                .set(currentYearEnt.getComfirmStatus() < 12 && StrUtil.isNotEmpty(i.getMon12()), "MON12", i.getMon12())
                                .set("UPDATE_BY", userInfo.getSysPerName())
                                .set("UPDATE_BY_DEPT", userInfo.getSysComDeptFullName())
                                .set("UPDATE_BY_CODE", userInfo.getSysPerCode())
                                .set("UPDATE_TIME", DateUtil.date());
                        this.baseMapper.update(null, wp);
                        //无id且当年有数据
                    } else {
                        i.setCreateTime(DateUtil.date());
                        i.setCreateBy(userInfo.getSysPerName());
                        i.setCreateByCode(userInfo.getSysPerCode());
                        i.setCreateByDept(userInfo.getSysComDeptFullName());
                        i.setYear(param.getYear());
                        this.save(i);
                    }
                }
            }
            return ResponseData.success("导入成功！");
        } catch (Exception e) {
            log.error("库存周转导入异常:", e);
            return ResponseData.error("库存周转导入异常:");
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("库存周转导入关闭流异常", e);
                }
            }
        }
    }

    @DataSource(name = "sales")
    @Override
    public ResponseData delete(InventoryTurnoverParam param) {
        if (param.getId() == null || "0".equals(param.getId().toString())) {
            return ResponseData.error("ID不能为空");
        }
        mapper.deleteById(param.getId());
        return ResponseData.success();
    }

    @DataSource(name = "sales")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData edit(InventoryTurnoverParam param) {
        String account = LoginContext.me().getLoginUser().getAccount();
        if (StringUtils.isEmpty(account)) {
            return ResponseData.error("InventoryTurnover.edit>>1>>>未获取到当前登录用户信息，请登录后再试！");
        }
        EbmsUserInfo userInfo = ebmsBaseConsumer.getUserInfoByAccount(account);
        if (ObjectUtil.isEmpty(userInfo)) {
            return ResponseData.error("InventoryTurnover.edit>>2>>>当前用户在EBMS系统中获取人员信息失败！");
        }
        UpdateWrapper<InventoryTurnover> wp = new UpdateWrapper<>();
        wp.eq("ID", param.getId())
                .set(param.getComfirmStatus() < 1 && param.getMon1() != null, "MON1", param.getMon1())
                .set(param.getComfirmStatus() < 2 && param.getMon2() != null, "MON2", param.getMon2())
                .set(param.getComfirmStatus() < 3 && param.getMon3() != null, "MON3", param.getMon3())
                .set(param.getComfirmStatus() < 4 && param.getMon4() != null, "MON4", param.getMon4())
                .set(param.getComfirmStatus() < 5 && param.getMon5() != null, "MON5", param.getMon5())
                .set(param.getComfirmStatus() < 6 && param.getMon6() != null, "MON6", param.getMon6())
                .set(param.getComfirmStatus() < 7 && param.getMon7() != null, "MON7", param.getMon7())
                .set(param.getComfirmStatus() < 8 && param.getMon8() != null, "MON8", param.getMon8())
                .set(param.getComfirmStatus() < 9 && param.getMon9() != null, "MON9", param.getMon9())
                .set(param.getComfirmStatus() < 1 && param.getMon10() != null, "MON10", param.getMon10())
                .set(param.getComfirmStatus() < 11 && param.getMon11() != null, "MON11", param.getMon11())
                .set(param.getComfirmStatus() < 12 && param.getMon12() != null, "MON12", param.getMon12())
                .set("UPDATE_TIME", DateUtil.date())
                .set("UPDATE_BY", userInfo.getSysPerName())
                .set("UPDATE_BY_DEPT", userInfo.getSysComDeptFullName())
                .set("UPDATE_BY_CODE", userInfo.getSysPerCode());

        this.update(null, wp);
        return ResponseData.success();
    }

    @DataSource(name = "sales")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData monthConfirm(InventoryTurnoverParam param) {
        try {
            String account = LoginContext.me().getLoginUser().getAccount();
            if (StringUtils.isEmpty(account)) {
                return ResponseData.error("InventoryTurnover.monthConfirm>>1>>>未获取到当前登录用户信息，请登录后再试！");
            }
            EbmsUserInfo userInfo = ebmsBaseConsumer.getUserInfoByAccount(account);
            if (ObjectUtil.isEmpty(userInfo)) {
                return ResponseData.error("InventoryTurnover.monthConfirm>>2>>>当前用户在EBMS系统中获取人员信息失败！");
            }
            if (StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getMonth())) {
                return ResponseData.error("年份和月份不能为空");
            }
            if (mapper.confirmedMonthEmpty(param.getYear(), Integer.parseInt(param.getMonth())) != null) {
                return ResponseData.error("已确认月份不能为空");
            }


            LambdaUpdateWrapper<InventoryTurnover> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper
                    .eq(InventoryTurnover::getYear, param.getYear())
                    .set(InventoryTurnover::getMonth, param.getMonth())
                    .set(InventoryTurnover::getUpdateTime, DateUtil.date())
                    .set(InventoryTurnover::getUpdateBy, userInfo.getSysPerName())
                    .set(InventoryTurnover::getUpdateByCode, userInfo.getSysPerCode())
                    .set(InventoryTurnover::getUpdateByDept, userInfo.getSysComDeptFullName())
                    .set(InventoryTurnover::getComfirmStatus, Integer.parseInt(param.getMonth()))
                    .set(InventoryTurnover::getComfirmTime, DateUtil.date());
            this.mapper.update(null, updateWrapper);

        } catch (Exception e) {
            log.error("库存调整>>月份确认异常", e);
        }

        return ResponseData.success();
    }

    @DataSource(name = "sales")
    @Override
    public ResponseData confirmedYearMonth() {
        mapper.confirmedYearMonth();
        return ResponseData.success();
    }

    @DataSource(name = "sales")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchUpdateNotConfirm(List<InventoryTurnover> params) {
        return this.baseMapper.batchUpdateNotConfirm(params);
    }

    @DataSource(name = "sales")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertBatch(List<InventoryTurnover> params) {
        return this.baseMapper.insertBatch(params);
    }

    @DataSource(name = "sales")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByConfirmMonth(InventoryTurnover params) {
        return this.baseMapper.updateByConfirmMonth(params);
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getPlatformSelect() {
        QueryWrapper<InventoryTurnover> wp = new QueryWrapper<>();
        wp = wp.select("platform").groupBy("platform").orderByAsc("platform");
        return transformLowerCase(this.baseMapper.selectMaps(wp));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getDepartmentSelect() {
        QueryWrapper<InventoryTurnover> wp = new QueryWrapper<>();
        wp = wp.select("department").groupBy("department").orderByAsc("TO_NUMBER(REGEXP_SUBSTR(TRANSLATE(department,'一二三四五六七八九','123456789'),'[0-9]+'))");
        return transformLowerCase(this.baseMapper.selectMaps(wp));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getTeamSelect() {
        QueryWrapper<InventoryTurnover> wp = new QueryWrapper<>();
        wp = wp.select("department", "team").groupBy("department", "team").orderByAsc("TO_NUMBER(REGEXP_SUBSTR(team,'[0-9]+'))");
        return transformLowerCase(this.baseMapper.selectMaps(wp));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getCompanyBrandSelect() {
        QueryWrapper<InventoryTurnover> wp = new QueryWrapper<>();
        wp = wp.select("company_brand")
                .groupBy("company_brand").orderByAsc("company_brand");
        return transformLowerCase(this.baseMapper.selectMaps(wp));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getProductTypeSelect() {
        QueryWrapper<InventoryTurnover> wp = new QueryWrapper<>();
        wp = wp.select("product_type")
                .groupBy("product_type").orderByAsc("product_type");
        return transformLowerCase(this.baseMapper.selectMaps(wp));
    }

    @DataSource(name = "sales")
    @Override
    public List<Map<String, Object>> getYearSelect() {
        QueryWrapper<InventoryTurnover> wp = new QueryWrapper<>();
        wp = wp.select("year").groupBy("year").orderByAsc("year");
        return transformLowerCase(this.baseMapper.selectMaps(wp));
    }

    private Boolean validationIsError(List<InventoryTurnover> dataList, Integer confirmStatus, String year, List<String> platList, List<String> teamList, List<String> ptList, List<String> deptList, List<String> brandList) {
        //用于验证重复全部集合的重复集合
        Set<String> allSet = new HashSet<>();

        //是否有错误数据
        boolean flag = Boolean.FALSE;
        for (InventoryTurnover i : dataList) {
            int status = confirmStatus != null ? confirmStatus : 0;
            if (
                    ((status >=1 && i.getMon1() == null ) || ( i.getMon1() != null && Integer.parseInt(i.getMon1()) < 0))
                    ||((status >=2 && i.getMon2() == null ) || ( i.getMon2() != null && Integer.parseInt(i.getMon2()) < 0))
                    ||((status >=3 && i.getMon3() == null ) || ( i.getMon3() != null && Integer.parseInt(i.getMon3()) < 0))
                    ||((status >=4 && i.getMon4() == null ) || ( i.getMon4() != null && Integer.parseInt(i.getMon4()) < 0))
                    ||((status >=5 && i.getMon5() == null ) || ( i.getMon5() != null && Integer.parseInt(i.getMon5()) < 0))
                    ||((status >=6 && i.getMon6() == null ) || ( i.getMon6() != null && Integer.parseInt(i.getMon6()) < 0))
                    ||((status >=7 && i.getMon7() == null ) || ( i.getMon7() != null && Integer.parseInt(i.getMon7()) < 0))
                    ||((status >=8 && i.getMon8() == null ) || ( i.getMon8() != null && Integer.parseInt(i.getMon8()) < 0))
                    ||((status >=9 && i.getMon9() == null ) || ( i.getMon9() != null && Integer.parseInt(i.getMon9()) < 0))
                    ||((status >=10 && i.getMon10() == null ) || ( i.getMon10() != null && Integer.parseInt(i.getMon10()) < 0))
                    ||((status >=11 && i.getMon11() == null ) || ( i.getMon11() != null && Integer.parseInt(i.getMon11()) < 0))
                    ||((status >=12 && i.getMon12() == null ) || ( i.getMon12() != null && Integer.parseInt(i.getMon12()) < 0))
            ){
                i.setUploadRemark(StrUtil.format("已确认{}月,存在为空或为负数据!", status));
                flag = Boolean.TRUE;
                continue;

            }
            String sb = i.getPlatform() + i.getDepartment()
                    + i.getTeam() + i.getProductType() + i.getCompanyBrand();
            if (sb.contains("汇总")||sb.contains("事业五部")) {
                continue;
            }
            if ((i.getIdx() == null || i.getPlatform() == null || i.getDepartment() == null || i.getTeam() == null || i.getProductType() == null || i.getCompanyBrand() == null)) {
                i.setUploadRemark("序号、平台、事业部、Team、运营大类、销售品牌不能为空!");
                flag = Boolean.TRUE;
                continue;
            }
            StringBuffer errorMsg = new StringBuffer();

            if (!deptList.contains(i.getDepartment())) {
                errorMsg.append("事业部无效,");
            }
            if (!platList.contains(i.getPlatform())) {
                errorMsg.append("平台无效,");
            }
            if (!teamList.contains(i.getTeam())) {
                errorMsg.append("Team无效,");
            }
            if (!ptList.contains(i.getProductType())) {
                errorMsg.append("运营大类无效,");
            }
            if (!brandList.contains(i.getCompanyBrand())) {
                errorMsg.append("销售品牌无效,");
            }
            if (errorMsg.length() > 0) {
                i.setUploadRemark(StringUtils.strip(errorMsg.toString(), ","));
                flag = Boolean.TRUE;
                continue;
            }


            //全部集合已存在则在重复集合中添加
            if (allSet.contains(sb)) {
                i.setUploadRemark("平台-事业部-Team-运营大类-销售品牌数据重复!");
                flag = Boolean.TRUE;
                continue;
            }
            allSet.add(sb);

            if (i.getId() == null) {
                LambdaQueryWrapper<InventoryTurnover> repeated = new LambdaQueryWrapper<>();
                repeated.eq(InventoryTurnover::getYear, year)
                        .eq(InventoryTurnover::getPlatform, i.getPlatform())
                        .eq(InventoryTurnover::getDepartment, i.getDepartment())
                        .eq(InventoryTurnover::getTeam, i.getTeam())
                        .eq(StrUtil.isNotEmpty(i.getProductType()),InventoryTurnover::getProductType, i.getProductType())
                        .eq(InventoryTurnover::getCompanyBrand, i.getCompanyBrand())
                        .apply("ROWNUM={0}", 1);

                InventoryTurnover repeatedOne = service.getOne(repeated);

                if (repeatedOne != null) {
                    i.setUploadRemark("数据已存在!!");
                    flag = Boolean.TRUE;
                    continue;
                }
            }
        }
        return flag;
    }

    private String dealImportErrorList(List<InventoryTurnover> errorList) {

        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName, false);
            EasyExcel.write(out, InventoryTurnover.class)
                    .sheet("库存周转").doWrite(errorList);
        } catch (FileNotFoundException e) {
            log.error("导入Excel异常数据导出异常", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("导入Excel异常数据导出流关闭异常", e);
                }
            }
        }
        return fileName;
    }

    public static List<Map<String, Object>> transformLowerCase(List<Map<String, Object>> list) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        if (CollUtil.isEmpty(list)) {
            return resultList;
        }
        for (Map<String, Object> mp : list) {
            if (mp == null) {
                continue;
            }
            Map<String, Object> resultMap = new HashMap<>(1024);
            Set<String> keySet = mp.keySet();
            for (String key : keySet) {
                String newKey = key.toLowerCase();
                resultMap.put(newKey, mp.get(key));
            }
            resultList.add(resultMap);
        }
        return resultList;
    }

    public static List<String> transformString(List<Map<String, Object>> list) {
        List<String> resultList = new ArrayList<>();
        if (CollUtil.isEmpty(list)) {
            return resultList;
        }
        for (Map<String, Object> mp : list) {
            if (mp == null) {
                continue;
            }
            Collection values = mp.values();
            for (Object val : values) {
                resultList.add(val.toString());
            }
        }
        return resultList;
    }

    private InventoryTurnover getEntity(InventoryTurnoverParam param) {
        InventoryTurnover entity = new InventoryTurnover();
        BeanUtil.copyProperties(param, entity);
        return entity;
    }
}
