package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.AllocStructure;
import com.tadpole.cloud.platformSettlement.api.finance.entity.PersonAlloc;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.AllocStructureResult;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.AllocStructureMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.AllocStructureParam;
import com.tadpole.cloud.platformSettlement.modular.finance.service.AllocStructureService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.PersonAllocService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ;(ALLOC_STRUCTURE)表服务实现类
 * @author : LSY
 * @date : 2023-12-19
 */
@Service
@Transactional
@Slf4j
public class AllocStructureServiceImpl  extends ServiceImpl<AllocStructureMapper, AllocStructure> implements AllocStructureService{
    @Resource
    private AllocStructureMapper allocStructureMapper;

     @Resource
     private PersonAllocService personAllocService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public AllocStructure queryById(String undefinedId){
        return allocStructureMapper.selectById(undefinedId);
    }
    
    /**
     * 分页查询
     *
     * @param param 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    @DataSource(name = "finance")
    @Override
    public Page<AllocStructureResult> paginQuery(AllocStructureParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<AllocStructure> queryWrapper = new LambdaQueryWrapper<>();
        //2. 执行分页查询
        Page<AllocStructureResult> pagin = new Page<>(current , size , true);
        IPage<AllocStructureResult> selectResult = allocStructureMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        List<AllocStructureResult> records = selectResult.getRecords();

        records.stream().forEach(i -> {
            String platforms = i.getPlatforms();
            if (ObjectUtil.isNotEmpty(i.getPlatforms())) {
                List<String> platformList = Arrays.asList(platforms.split("/"));
                i.setPlatformList(platformList);
            }
        });

        records = records.stream().sorted(
                Comparator.comparing(AllocStructureResult::getPeriod,Comparator.nullsFirst(String::compareTo)).reversed()
                        .thenComparing(AllocStructureResult::getDept3,Comparator.nullsFirst(String::compareTo))
                        .thenComparing(AllocStructureResult::getDept4,Comparator.nullsFirst(String::compareTo))
                        .thenComparing(AllocStructureResult::getId)
        ).collect(Collectors.toList());


        pagin.setRecords(records);
        //3. 返回结果
        return pagin;
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResponseData confirm(AllocStructureParam param) {

        if (ObjectUtil.isEmpty(param.getPeriod())) {
            return ResponseData.error("确认会计期间为空");
        }
        //排除汇总行，错误提示排序保持和页面一致
        QueryWrapper<AllocStructure>  queryWrapper= new QueryWrapper();
        queryWrapper
                .eq("PERIOD", param.getPeriod()).isNotNull("ALLOC_ID")
                .orderByDesc("dept3,id,dept4");
        List<AllocStructure> confirmList = allocStructureMapper.selectList(queryWrapper);
        if (ObjectUtil.isEmpty(confirmList)) {
            return ResponseData.error(StrUtil.format("当前会计区间[{}]无可确认数据",param.getPeriod()));
        }
        if (confirmList.stream().filter(Objects::isNull).anyMatch(i->i.getStatus() ==1)) {
            return ResponseData.error(StrUtil.format("当前会计区间[{}]已确认",param.getPeriod()));
        }



        //校验是否分摊有大于1
        for (AllocStructure i : confirmList) {
            Optional<BigDecimal> amazonRatio = Optional.ofNullable(i.getAmazonRatio());
            Optional<BigDecimal> rakutenRatio = Optional.ofNullable(i.getRakutenRatio());
            Optional<BigDecimal> walmartRatio = Optional.ofNullable(i.getWalmartRatio());
            Optional<BigDecimal> shopifyRatio = Optional.ofNullable(i.getShopifyRatio());
            Optional<BigDecimal> alibabaRatio = Optional.ofNullable(i.getAlibabaRatio());
            Optional<BigDecimal> smtRatio = Optional.ofNullable(i.getSmtRatio());
            Optional<BigDecimal> b2bRatio = Optional.ofNullable(i.getB2bRatio());
            Optional<BigDecimal> shopeeRatio = Optional.ofNullable(i.getShopeeRatio());
            Optional<BigDecimal> lazadaRatio = Optional.ofNullable(i.getLazadaRatio());
            Optional<BigDecimal> temuRatioRatio = Optional.ofNullable(i.getTemuRatio());
            BigDecimal result = alibabaRatio.orElse(BigDecimal.ZERO)
                                .add(smtRatio.orElse(BigDecimal.ZERO))
                                .add(b2bRatio.orElse(BigDecimal.ZERO))
                                .add(shopeeRatio.orElse(BigDecimal.ZERO))
                                .add(lazadaRatio.orElse(BigDecimal.ZERO))
                                .add(amazonRatio.orElse(BigDecimal.ZERO))
                                .add(walmartRatio.orElse(BigDecimal.ZERO))
                                .add(rakutenRatio.orElse(BigDecimal.ZERO))
                                .add(shopifyRatio.orElse(BigDecimal.ZERO))
                                .add(temuRatioRatio.orElse(BigDecimal.ZERO));
            if (result.compareTo(BigDecimal.ONE)!=0) {
                return ResponseData.error(StrUtil.format("{}_{}_{}分摊比率之和 [{}]不等于100%",i.getPeriod(),i.getDept3(),i.getDept4(),result));
            }

        }


        LambdaUpdateWrapper<AllocStructure> updateQw = new LambdaUpdateWrapper<>();
        updateQw
                .eq(AllocStructure :: getPeriod,param.getPeriod())
                .set(ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser()),AllocStructure :: getConfirmBy,LoginContext.me().getLoginUser().getName())
                .set(AllocStructure :: getConfirmDate, DateUtil.date())
                .set(AllocStructure :: getStatus, 1);
        allocStructureMapper.update(null, updateQw);
        return  ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    public Boolean isNoComfirm(AllocStructureParam param){

        LambdaQueryWrapper<AllocStructure> qw = new LambdaQueryWrapper<>();
        qw.in(ObjectUtil.isNotEmpty(param.getConfirmIdList()),AllocStructure :: getId, param.getConfirmIdList())
                .eq(ObjectUtil.isNotEmpty(param.getPeriod()),AllocStructure :: getPeriod,param.getPeriod())
                .eq(AllocStructure :: getStatus, 0);
                    //存在未确认的即没确认
        return allocStructureMapper.selectCount(qw) > 0;
    }


    /** 
     * 新增数据
     *
     * @param allocStructure 实例对象
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public AllocStructure insert(AllocStructure allocStructure){
        allocStructureMapper.insert(allocStructure);
        return allocStructure;
    }


    @DataSource(name = "finance")
    @Override
    public ResponseData importExcel(MultipartFile file) {
        log.info("分摊架构-导入:Excel处理开始");
        BufferedInputStream buffer = null;

        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<AllocStructure>();
            EasyExcel.read(buffer, AllocStructure.class, listener).sheet()
                    .doRead();

            List<AllocStructure> dataList = listener.getDataList();
            if (CollectionUtil.isEmpty(dataList)) {
                return ResponseData.error("人员分摊-导入为空！");
            }

            //异常数据集合
            List<AllocStructure> errorList = new ArrayList<>();
//            this.validation(dataList, errorList, account, departmentList, teamList);


            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date date = new Date();


            //批量保存更新表数据
            if (CollectionUtil.isNotEmpty(dataList)) {
                //明细项的所有分摊值不处理,由数据计算字段处理
                dataList = dataList.stream().filter(i->ObjectUtil.isNotEmpty(i.getAllocId())).map(i->
                        {
                            i.setAmazonAlloc(null);
                            i.setRakutenAlloc(null);
                            i.setWalmartAlloc(null);
                            i.setB2bAlloc(null);
                            i.setAlibabaAlloc(null);
                            i.setLazadaAlloc(null);
                            i.setShopeeAlloc(null);
                            i.setSmtAlloc(null);
                            i.setShopifyRatio(null);
                            i.setUpdateTime(date);
                            i.setUpdateBy(loginUser.getName());
                            if (ObjectUtil.isEmpty(i.getId())) {
                                if (ObjectUtil.isNotEmpty(loginUser)) {
                                    i.setCreateBy(loginUser.getName());
                                }

                                i.setCreateTime(date);

                            }
                            return i;
                        }
                        ).collect(Collectors.toList());
                List<AllocStructure> allocStructureList = dataList.stream()
                        .map(i-> { i.setAmazonRatio(i.getAmazonRatio() == null ? BigDecimal.ZERO : i.getAmazonRatio());
                            i.setRakutenRatio(i.getRakutenRatio() == null ? BigDecimal.ZERO : i.getRakutenRatio());
                            i.setWalmartRatio(i.getWalmartRatio() == null ? BigDecimal.ZERO : i.getWalmartRatio());
                            i.setShopifyRatio(i.getShopifyRatio() == null ? BigDecimal.ZERO : i.getShopifyRatio());
                            i.setB2bRatio(i.getB2bRatio() == null ? BigDecimal.ZERO : i.getB2bRatio());
                            i.setAlibabaRatio(i.getAlibabaRatio() == null ? BigDecimal.ZERO : i.getAlibabaRatio());
                            i.setSmtRatio(i.getSmtRatio() == null ? BigDecimal.ZERO : i.getSmtRatio());
                            i.setLazadaRatio(i.getLazadaRatio() == null ? BigDecimal.ZERO : i.getLazadaRatio());
                            i.setShopeeRatio(i.getShopeeRatio() == null ? BigDecimal.ZERO : i.getShopeeRatio());
                            i.setTemuAlloc(i.getTemuRatio() == null ? BigDecimal.ZERO : i.getTemuRatio());
                            return i;})
                        .filter(i -> i.getAmazonRatio().add(i.getRakutenRatio()).add(i.getWalmartRatio())
                        .add(i.getAlibabaRatio()).add(i.getB2bRatio()).add(i.getLazadaRatio()).add(i.getSmtRatio()).add(i.getSmtRatio()).compareTo(new BigDecimal(1)) != 0).collect(Collectors.toList());
                if (allocStructureList.size()>0) {
                    return ResponseData.error(StrUtil.format("以下id存在分摊比率之和不等于1:[{}]",allocStructureList.stream().map(i->i.getId()).collect(Collectors.toList())).toString());
                }
                this.saveOrUpdateBatch(dataList);
                if (CollectionUtil.isNotEmpty(errorList)) {
                    String fileName = "异常数据";
                    //部分导入成功
                    return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "部分导入成功，存在异常数据数据", fileName);
                }
                return ResponseData.success("导入成功！");
            }
            if (CollectionUtil.isNotEmpty(errorList)) {
                String fileName = "异常数据";
                //导入失败
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "导入失败，存在异常数据数据", fileName);
            }
            return ResponseData.error("导入失败！导入数据为空！");
        } catch (Exception e) {log.error("导入Excel处理异常，导入失败！", e);
            return ResponseData.error("导入Excel处理异常，导入失败！");
        }
    }


     @DataSource(name = "finance")
     @Override
     public ResponseData doAllocStructure(AllocStructure allocStructure){
         if (ObjectUtil.isEmpty(allocStructure.getPeriod())) {
             allocStructure.setPeriod(DateUtil.format(DateUtil.date(),"YYYY-MM"));
         }

         LambdaQueryWrapper<PersonAlloc> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(PersonAlloc :: getPeriod, allocStructure.getPeriod())
                 .eq(PersonAlloc :: getConfirm, 1);
         if (personAllocService.count(queryWrapper) == 0){
             return ResponseData.error(StrUtil.format("[{}]未找到已确认人员数据",allocStructure.getPeriod()));
         }

         if (new LambdaQueryChainWrapper<>(allocStructureMapper)
                 .eq(AllocStructure::getPeriod, allocStructure.getPeriod())
                 .eq(AllocStructure::getStatus, "1").count() > 0) {
             ResponseData.error("当前会计区间分摊架构已确认");
         }

         //按架构全部删除
         new LambdaUpdateChainWrapper<>(allocStructureMapper)
                 .eq(AllocStructure::getPeriod, allocStructure.getPeriod())
                 .eq(AllocStructure::getStatus, 0)
                 .remove();



         queryWrapper.isNull(PersonAlloc::getDept3Alloc).orderByAsc();
         queryWrapper.orderByAsc(PersonAlloc::getPeriod, PersonAlloc::getDept3, PersonAlloc::getDept4);
         //当前会计区间所有已确认的人员数据
         List<PersonAlloc> teamPeoples = personAllocService.list(queryWrapper);

         //有待分摊三级部门的即需要按team平分的数据
         queryWrapper.clear();
         queryWrapper.isNotNull(PersonAlloc::getDept3Alloc)
                 .eq(PersonAlloc::getPeriod,allocStructure.getPeriod())
                 .eq(PersonAlloc::getConfirm,1);
         List<PersonAlloc> departmentPeoples = personAllocService.list(queryWrapper);
         //事业部:该事业待分摊人数
         Map<String, List<PersonAlloc>> toAllocMap = departmentPeoples.stream().collect(Collectors.groupingBy(PersonAlloc::getDept3Alloc));


         //生成待分摊架构,分摊主行
         Map<String, List<PersonAlloc>> teamAllocMap = teamPeoples.stream().collect(Collectors.groupingBy(PersonAlloc::getAllocDimension));
         Date date = new Date();
         List<AllocStructure> dept4DetailList = new ArrayList<>();

         for (Map.Entry<String, List<PersonAlloc>> allocEntry : teamAllocMap.entrySet()) {
             //待分摊行
             AllocStructure allocHead = new AllocStructure();
             List<PersonAlloc> allocDetails = allocEntry.getValue();
             String period = allocDetails.get(0).getPeriod();
             String dept3 = allocDetails.get(0).getDept3();
             allocHead.setPeriod(period);
             allocHead.setDept3(dept3);
             allocHead.setCreateTime(date);
             if (ObjectUtil.isNotEmpty(toAllocMap.get(dept3))) {
                 //待分摊行-待分摊人数
                 allocHead.setToAllocNum(new BigDecimal(toAllocMap.get(dept3).size()));
             } else { allocHead.setToAllocNum(BigDecimal.ZERO) ;}
             this.save(allocHead);
             String allocId = allocHead.getId();
             Map<String, List<PersonAlloc>> dept4Map = allocDetails.stream().collect(Collectors.groupingBy(PersonAlloc::getDept4));
             BigDecimal current = BigDecimal.ZERO;
             BigDecimal notLasSum = BigDecimal.ZERO;
             //map按Team排序保证最后一个Team来作差
             TreeMap<String, List<PersonAlloc>> treeDept4Map = new TreeMap<>(dept4Map);
             for (Map.Entry<String, List<PersonAlloc>> dept4 : treeDept4Map.entrySet()) {
                 BigDecimal teamNums = BigDecimal.valueOf(dept4Map.size());
                 AllocStructure allocDept4 = new AllocStructure();
                 current = current.add(BigDecimal.ONE);
                 // 获取平台列表
                 List<PersonAlloc> persons = dept4.getValue();
                 //平台去重分拆再去重用"/"连接
                 String platforms = Arrays.stream(persons.stream().map(PersonAlloc::getPlatform).distinct().collect(Collectors.joining("/")).split("/")).distinct().collect(Collectors.joining("/"));
                 if (ObjectUtil.isNotEmpty(platforms) && !"null".equals(platforms)) {
                     allocDept4.setPlatforms(platforms);
                 }
                 allocDept4.setAllocId(allocId);
                 allocDept4.setPeriod(period);
                 allocDept4.setDept3(dept3);
                 allocDept4.setDept4(dept4.getKey());
                 //原人数
                 allocDept4.setOriNum(new BigDecimal(persons.size()));
                //分摊值  【待分摊人数】除以【四级部门】的个数
                 BigDecimal allocValue = BigDecimal.ZERO;
                 if (ObjectUtil.isNotEmpty(allocHead.getToAllocNum()) && allocHead.getToAllocNum().compareTo(BigDecimal.ZERO) > 0) {
                     if (current.compareTo(teamNums) < 0) {
                         allocValue = allocHead.getToAllocNum().divide(teamNums, 2, RoundingMode.HALF_UP);
                         notLasSum = notLasSum.add(allocValue);
                     }else{
                         allocValue = allocHead.getToAllocNum().subtract(notLasSum);
                     }

                 }

                 allocDept4.setAllocNum(allocValue);
                 //分摊后人数
                 allocDept4.setAllocedNum(allocDept4.getOriNum().add(allocValue));

                 allocDept4.setCreateTime(date);
                 allocDept4.setStatus(0);

                 dept4DetailList.add(allocDept4);
             }

         }
         this.saveBatch(dept4DetailList);
         this.baseMapper.fillLastMonAllocRation(allocStructure);
         return ResponseData.success();
     }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public AllocStructure update(AllocStructureParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<AllocStructure> wrapper = new LambdaUpdateChainWrapper<AllocStructure>(allocStructureMapper);
        //2. 设置主键，并更新
        wrapper.eq(AllocStructure::getId, param.getId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        wrapper.set(AllocStructure::getUpdateTime, new Date());
        wrapper.set(AllocStructure::getUpdateBy, loginUser.getName());
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param undefinedId 主键
     * @return 是否成功
     */
    @DataSource(name = "finance")
    @Override
    public boolean deleteById(String undefinedId){
        int total = allocStructureMapper.deleteById(undefinedId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param undefinedIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "finance")
    @Override
    public boolean deleteBatchIds(List<String> undefinedIdList) {
        int delCount = allocStructureMapper.deleteBatchIds(undefinedIdList);
        if (undefinedIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResponseData updateBatch(List<AllocStructure> allocStructureList) {
        try {
            allocStructureList = allocStructureList.stream().filter(i->ObjectUtil.isNotEmpty(i.getStatus()) && i.getStatus() == 0).collect(Collectors.toList());

            if (ObjectUtil.isEmpty(allocStructureList)) {
                return ResponseData.error("更新数据为空");
            }

            if (allocStructureList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getPeriod()))) {
                return ResponseData.error("存在会计区间为空的数据");
            }
//            allocStructureList.stream().forEach(i -> i.setStatus(ObjectUtil.isEmpty(i.getStatus())?0:i.getStatus()));
//            if (allocStructureList.stream().anyMatch(i ->  i.getStatus() == 1)) {
//                return ResponseData.error("存在已确人数据");
//            }

            if (new LambdaQueryChainWrapper<>(allocStructureMapper)
                    .eq(AllocStructure::getPeriod, allocStructureList.get(0).getPeriod())
                    .eq(AllocStructure::getStatus, "1").count() > 0) {
                ResponseData.error("当前会计区间存在确认数据");
            }
            if (allocStructureList.stream().anyMatch(i -> ObjectUtil.isNotEmpty(i.getAllocedNum()) && i.getAllocedNum().compareTo(BigDecimal.ZERO) < 0)) {
                return ResponseData.error("分摊人数不能小于0");
            }
            new LambdaUpdateChainWrapper<>(allocStructureMapper)
                    .eq(AllocStructure::getPeriod, allocStructureList.get(0).getPeriod())
                    .eq(AllocStructure::getStatus, "0")
                    .remove();
            //全量保存
            String loginUser = "系统生成";
            if (ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())) {
                loginUser = LoginContext.me().getLoginUser().getName();
            }


            Date date = new Date();
            for (AllocStructure i : allocStructureList) {

                i.setAmazonAlloc(null);

                i.setRakutenAlloc(null);
                i.setWalmartAlloc(null);
                i.setShopifyAlloc(null);

                i.setLazadaAlloc(null);
                i.setShopeeAlloc(null);
                i.setSmtAlloc(null);
                i.setB2bAlloc(null);
                i.setAlibabaAlloc(null);
                i.setTemuAlloc(null);
                i.setUpdateTime(date);
                i.setUpdateBy(loginUser);

                Optional<BigDecimal> amazonRatio = Optional.ofNullable(i.getAmazonRatio());
                Optional<BigDecimal> rakutenRatio = Optional.ofNullable(i.getRakutenRatio());
                Optional<BigDecimal> walmartRatio = Optional.ofNullable(i.getWalmartRatio());
                Optional<BigDecimal> shopifyRatio = Optional.ofNullable(i.getShopifyRatio());

                Optional<BigDecimal> alibabaRatio = Optional.ofNullable(i.getAlibabaRatio());
                Optional<BigDecimal> smtRatio = Optional.ofNullable(i.getSmtRatio());
                Optional<BigDecimal> b2bRatio = Optional.ofNullable(i.getB2bRatio());

                Optional<BigDecimal> shopeeRatio = Optional.ofNullable(i.getShopeeRatio());
                Optional<BigDecimal> lazadaRatio = Optional.ofNullable(i.getLazadaRatio());
                Optional<BigDecimal> temuRatio = Optional.ofNullable(i.getTemuRatio());

                BigDecimal result =
                               alibabaRatio.orElse(BigDecimal.ZERO)
                                 .add(smtRatio.orElse(BigDecimal.ZERO))
                                 .add(b2bRatio.orElse(BigDecimal.ZERO))
                                 .add(shopeeRatio.orElse(BigDecimal.ZERO))
                                 .add(lazadaRatio.orElse(BigDecimal.ZERO))

                                 .add(amazonRatio.orElse(BigDecimal.ZERO))
                                 .add(rakutenRatio.orElse(BigDecimal.ZERO))
                                 .add(walmartRatio.orElse(BigDecimal.ZERO))
                                 .add(shopifyRatio.orElse(BigDecimal.ZERO))
                                 .add(temuRatio.orElse(BigDecimal.ZERO));
                if (result.compareTo(BigDecimal.ONE)>0) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    log.error(StrUtil.format("[{}_{}_{}]分摊比率之和{}大于1",i.getPeriod(),i.getDept3(),i.getDept4(),result));
                    return ResponseData.error(StrUtil.format("[{}_{}_{}]分摊比率之和{}大于1",i.getPeriod(),i.getDept3(),i.getDept4(),result));
                }
            }
            this.saveOrUpdateBatch(allocStructureList);
            return ResponseData.success();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseData.error(e.getMessage());
        }
    }


    @DataSource(name = "finance")
    @Override
    public ResponseData fillLastMonAllocRation(AllocStructure param){
        if (ObjectUtil.isEmpty(param.getPeriod())){
            param.setPeriod(DateUtil.format(new Date(), "yyyy-MM"));
        }
        allocStructureMapper.fillLastMonAllocRation(param);
        return ResponseData.success();
    }
}