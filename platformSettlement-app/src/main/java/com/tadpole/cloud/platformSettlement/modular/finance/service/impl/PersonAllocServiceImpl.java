package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.AllocStructure;
import com.tadpole.cloud.platformSettlement.api.finance.entity.PersonAlloc;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.PersonAllocMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.PersonAllocParam;
import com.tadpole.cloud.platformSettlement.modular.finance.model.result.PersonAllocResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.AllocStructureService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.PersonAllocService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ;(PERSON_ALLOC)表服务实现类
 * @author : LSY
 * @date : 2023-12-19
 */
@Service
@Transactional
@Slf4j
public class PersonAllocServiceImpl  extends ServiceImpl<PersonAllocMapper, PersonAlloc> implements PersonAllocService{
    @Resource
    private PersonAllocMapper personAllocMapper;


    @Resource
    private AllocStructureService allocStructureService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public PersonAlloc queryById(String undefinedId){
        return personAllocMapper.selectById(undefinedId);
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
    public Page<PersonAllocResult> paginQuery(PersonAllocParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<PersonAlloc> queryWrapper = new LambdaQueryWrapper<>();
        //2. 执行分页查询
        size = Integer.MAX_VALUE;
        Page<PersonAllocResult> pagin = new Page<>(current , size , true);
        IPage<PersonAllocResult> selectResult = personAllocMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        List<PersonAllocResult> records = selectResult.getRecords();
        records.stream().forEach(i->i.setStatus((ObjectUtil.isNotEmpty(i.getStatus()) && ("1".equals(i.getStatus()) || "正式".equals(i.getStatus()))) ?"正式":"当月离职"));

        records = records.stream().sorted(
                 Comparator.comparing(PersonAllocResult::getPeriod,Comparator.nullsFirst(String::compareTo)).reversed()
                .thenComparing(PersonAllocResult::getDept1,Comparator.nullsFirst(String::compareTo))
                .thenComparing(PersonAllocResult::getDept2,Comparator.nullsFirst(String::compareTo))
                .thenComparing(PersonAllocResult::getDept3,Comparator.nullsFirst(String::compareTo))
                .thenComparing(PersonAllocResult::getDept4,Comparator.nullsFirst(String::compareTo))
                .thenComparing(PersonAllocResult::getPersonCode,Comparator.nullsFirst(String::compareTo)))
                         .collect(Collectors.toList());
        pagin.setRecords(records);
        //3. 返回结果
        return pagin;
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResponseData updateBatch(List<PersonAlloc> personAllocList) {
        personAllocList.stream().forEach(i -> {
                i.setPlatform(ObjectUtil.isEmpty(i.getPlatform())?"":i.getPlatform());
                i.setDept3Alloc(ObjectUtil.isEmpty(i.getDept3Alloc())?"":i.getDept3Alloc());
            });
        this.saveOrUpdateBatch(personAllocList);
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResponseData confirm(List<PersonAlloc> personAllocList) {
        try {

            if (ObjectUtil.isEmpty(personAllocList)) {
                return ResponseData.error("数据为空");
            }
            String period = personAllocList.get(0).getPeriod();

            if (new LambdaQueryChainWrapper<>(personAllocMapper)
                    .eq(PersonAlloc::getPeriod, period)
                    .eq(PersonAlloc::getConfirm, "1").count() > 0) {
                return ResponseData.error("当前会计区间存在确认数据");
            }
            //非事业五部支持组需要平台
            if (personAllocList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getPlatform()))) {
                return ResponseData.error("平台不能为空");
            }
            if (personAllocList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getDept4()))) {
                return ResponseData.error("四级部门不能为空");
            }
//            if (personAllocList.stream().filter(i->"事业五部".equals(i.getDept3()) && i.getDept4().contains("支持")).anyMatch(i -> ObjectUtil.isNotEmpty(i.getPlatform()) || ObjectUtil.isNotEmpty(i.getDept3Alloc()))) {
//                return ResponseData.error("[事业五部支持组]平台和待分摊三级部门必须为空");
//            }


            new LambdaUpdateChainWrapper<>(personAllocMapper)
                    .eq(PersonAlloc::getPeriod, period)
                    .eq(PersonAlloc::getConfirm, "0")
                    .remove();
            //全量保存
            personAllocList.forEach(i ->
                i.setStatus("正式".equals(i.getStatus())?"1":"2"));
            this.saveOrUpdateBatch(personAllocList);

            LambdaUpdateWrapper<PersonAlloc> updateQw = new LambdaUpdateWrapper<>();
            updateQw
                    .eq(PersonAlloc::getPeriod, period)
                    .set(PersonAlloc::getConfirmBy, ObjectUtil.isEmpty(LoginContext.me().getLoginUser()) ? "" : LoginContext.me().getLoginUser().getName())
                    .set(PersonAlloc::getConfirmDate, DateUtil.date())
                    .set(PersonAlloc::getConfirm, 1);
            personAllocMapper.update(null, updateQw);
            //人员确认生成分摊架构
            AllocStructure allocStructure = AllocStructure.builder().period(period).build();
            allocStructureService.doAllocStructure(allocStructure);
            return ResponseData.success();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseData.error(e.getMessage());
        }
    }
    
    /** 
     * 新增数据
     *
     * @param personAlloc 实例对象
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public PersonAlloc insert(PersonAlloc personAlloc){
        personAllocMapper.insert(personAlloc);
        return personAlloc;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public PersonAlloc update(PersonAllocParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<PersonAlloc> wrapper = new LambdaUpdateChainWrapper<PersonAlloc>(personAllocMapper);
        //2. 设置主键，并更新
        wrapper.eq(PersonAlloc::getId, param.getId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        wrapper.set(PersonAlloc::getUpdateTime, new Date());
        wrapper.set(PersonAlloc::getUpdateBy, loginUser.getName());
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
        int total = personAllocMapper.deleteById(undefinedId);
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
        int delCount = personAllocMapper.deleteBatchIds(undefinedIdList);
        if (undefinedIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

     /**
      * 通过主键批量删除数据
      *
      * @param
      * @return 是否成功
      */
     @DataSource(name = "basecloud")
     @Override
     @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
     public List<PersonAlloc> getCurrent() {
         List<PersonAlloc> dataList = personAllocMapper.getCurrent();
         return  dataList;
     }


     @DataSource(name = "sales")
     @Override
     @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
     public HashMap<String, String> getPlatform() {
         List<Map<String, Object>> dataList = personAllocMapper.getPlatform();
         HashMap<String, String> mp = new HashMap<>();
         for (Map<String, Object> data : dataList) {
             String platform = (String)data.get("PLATFORM");
             String deptTeam = (String)data.get("DEPARTMENT||TEAM");
             if (ObjectUtil.isNotEmpty(deptTeam) && ObjectUtil.isNotEmpty(platform)) {
                 mp.put(deptTeam,platform);
             }

         }
         return  mp;
     }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public List<String> getDepart() {
        return personAllocMapper.getDepart();
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public List<String> getTeam() {
        return personAllocMapper.getTeam();
    }






    @DataSource(name = "finance")
     @Override
     @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
     public ResponseData initCurrent(List<PersonAlloc> dataList) {
         String format = DateUtil.format(DateUtil.date(), "YYYY-MM");
         LambdaQueryWrapper<PersonAlloc> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(PersonAlloc :: getPeriod, format)
                 .eq(PersonAlloc :: getConfirm, 1);
         if(this.count(queryWrapper) > 0){
             log.error("当前期间人员分摊表已确认!");
             return ResponseData.error("当前期间人员分摊表已确认!");
             //未确认则按会计区间删除
         } else {
             queryWrapper.clear();
             queryWrapper.eq(PersonAlloc :: getPeriod, format);
             this.remove(queryWrapper);

         }
        //去掉SS2开头的虚拟账号
         dataList = dataList.stream().filter(i -> ObjectUtil.isNotEmpty(i.getPersonCode()) && !i.getPersonCode().startsWith("SS2")).collect(Collectors.toList());

         Date date = new Date();
         dataList.stream().forEach(i->{
             if (ObjectUtil.isEmpty(i.getCreateTime())) {
                 i.setCreateTime(date);
             } else{
                 i.setUpdateTime(date);
             }
         });
         this.saveBatch(dataList);
         this.baseMapper.updatePlatDept3(date);
         return  ResponseData.success();
     }


     @DataSource(name = "finance")
     @Override
     public ResponseData importExcel(MultipartFile file) {
         log.info("人员分摊-导入:Excel处理开始");
         BufferedInputStream buffer = null;

         try {
             buffer = new BufferedInputStream(file.getInputStream());
             BaseExcelListener listener = new BaseExcelListener<PersonAlloc>();
             EasyExcel.read(buffer, PersonAlloc.class, listener).sheet()
                     .doRead();

             List<PersonAlloc> dataList = listener.getDataList();
             if (CollectionUtil.isEmpty(dataList)) {
                 return ResponseData.error("人员分摊-导入为空！");
             }

             //异常数据集合
             List<PersonAlloc> errorList = new ArrayList<>();
//            this.validation(dataList, errorList, account, departmentList, teamList);
             String operator = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())?LoginContext.me().getLoginUser().getName():"系统生成";
             DateTime date = DateUtil.date();
             dataList.forEach(
                     i -> {
                         if (ObjectUtil.isNotEmpty(i.getCreateBy())) {
                             i.setUpdateBy(operator);
                             i.setUpdateTime(date);
                         }else{
                             i.setCreateBy(operator);
                             i.setCreateTime(date);
                         };
                     }

             );
             //批量保存更新表数据
             if (CollectionUtil.isNotEmpty(dataList)) {
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

 }