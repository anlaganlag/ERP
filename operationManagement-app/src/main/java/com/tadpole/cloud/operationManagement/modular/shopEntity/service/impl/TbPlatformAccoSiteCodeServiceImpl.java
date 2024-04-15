package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.toolkit.MPJWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShop;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopApply;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopApplyDet;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbPlatformAccoSiteCode;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.QueryApplySiteParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbPlatformAccoSiteCodeParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbPlatformAccoSiteCodeResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbPlatformAccoSiteCodeMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbPlatformAccoSiteCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 资源-平台-账号-站点-对应编码配置;(Tb_Platform_Acco_Site_Code)--服务实现类
 * @author : LSY
 * @create : 2023-8-3
 */
@Slf4j
@Service
@Transactional
public class TbPlatformAccoSiteCodeServiceImpl extends ServiceImpl<TbPlatformAccoSiteCodeMapper, TbPlatformAccoSiteCode>  implements TbPlatformAccoSiteCodeService{
    @Resource
    private TbPlatformAccoSiteCodeMapper tbPlatformAccoSiteCodeMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbPlatformAccoSiteCode queryById(String undefinedId){
        return tbPlatformAccoSiteCodeMapper.selectById(undefinedId);
    }
    
    /**
     * 分页查询
     *
     * @param queryParam 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbPlatformAccoSiteCodeResult> paginQuery(TbPlatformAccoSiteCodeParam queryParam, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbPlatformAccoSiteCode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getName()), TbPlatformAccoSiteCode::getName, queryParam.getName())
                .eq(ObjectUtil.isNotEmpty(queryParam.getCode()), TbPlatformAccoSiteCode::getCode, queryParam.getCode())
                .eq(ObjectUtil.isNotEmpty(queryParam.getType()), TbPlatformAccoSiteCode::getType, queryParam.getType())
                .orderByAsc(TbPlatformAccoSiteCode::getCode)
                .last("NULLS FIRST");

        
        //2. 执行分页查询
        Page<TbPlatformAccoSiteCodeResult> pagin = new Page<>(current , size , true);
        IPage<TbPlatformAccoSiteCodeResult> selectResult = tbPlatformAccoSiteCodeMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }


    @DataSource(name = "stocking")
    @Override
    public List<String> queryPlatform(){
        LambdaQueryWrapper<TbPlatformAccoSiteCode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TbPlatformAccoSiteCode::getType, "平台");
        List<String> platformList = tbPlatformAccoSiteCodeMapper.selectList(queryWrapper).stream().map(TbPlatformAccoSiteCode::getName).collect(Collectors.toList());
        return platformList;
    }


    @DataSource(name = "stocking")
    @Override
    public List<String> queryApplyShopName(QueryApplySiteParam param){

        MPJLambdaWrapper<TbPlatformAccoSiteCode> queryWrapper =  MPJWrappers.<TbPlatformAccoSiteCode>lambdaJoin()
                .select(TbPlatformAccoSiteCode::getName)
                .leftJoin(TbComShop.class,TbComShop::getShopNameSimple,TbPlatformAccoSiteCode::getName);
                if (ObjectUtil.isNotEmpty(param)) {
                    queryWrapper
                        .eq(ObjectUtil.isNotEmpty(param.getPlatform()), TbComShop::getElePlatformName, param.getPlatform());
                }
        queryWrapper.eq(TbPlatformAccoSiteCode::getType,"账号").distinct();
                List<String> applyShopNames = tbPlatformAccoSiteCodeMapper.selectList(queryWrapper).stream().map(TbPlatformAccoSiteCode::getName).sorted().collect(Collectors.toList());
        return  applyShopNames;
    }

    @DataSource(name = "stocking")
    @Override
    public List<String> queryApplySite(QueryApplySiteParam param){
        MPJLambdaWrapper<TbPlatformAccoSiteCode> queryWrapper =  MPJWrappers.<TbPlatformAccoSiteCode>lambdaJoin()
                .select(TbPlatformAccoSiteCode::getName);
        if (ObjectUtil.isNotEmpty(param)){
            if (ObjectUtil.isNotEmpty(param.getPlatform())||ObjectUtil.isNotEmpty(param.getShopName())){
                queryWrapper
                        .leftJoin(TbComShopApplyDet.class, TbComShopApplyDet::getCountryCode, TbPlatformAccoSiteCode::getName)
                        .leftJoin(TbComShopApply.class, TbComShopApply::getSysApplyId, TbComShopApplyDet::getSysApplyId);
            }
                queryWrapper
                        .eq(ObjectUtil.isNotEmpty(param.getPlatform()), TbComShopApply::getElePlatformName, param.getPlatform())
                        .eq(ObjectUtil.isNotEmpty(param.getShopName()),TbComShopApply::getShopNameSimple,param.getShopName());
        }
        queryWrapper.eq(TbPlatformAccoSiteCode::getType,"站点").distinct();

        List<String> applySites = tbPlatformAccoSiteCodeMapper.selectList(queryWrapper).stream().map(TbPlatformAccoSiteCode::getName).collect(Collectors.toList());
        return  applySites;
    }



    /** 
     * 新增数据
     *
     * @param tbPlatformAccoSiteCode 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbPlatformAccoSiteCode insert(TbPlatformAccoSiteCode tbPlatformAccoSiteCode){
        tbPlatformAccoSiteCodeMapper.insert(tbPlatformAccoSiteCode);
        return tbPlatformAccoSiteCode;
    }
    
    /** 
     * 更新数据
     *
     * @param entityParam 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public ResponseData update(TbPlatformAccoSiteCode entityParam){
        String code = entityParam.getCode();
        String name = entityParam.getName();
        String type = entityParam.getType();
        if (ObjectUtil.isEmpty(name)) {
            log.error("店铺异常管理tbPlatformAccoSiteCode>>update>>店铺名称为空{}");
            return ResponseData.error(StrUtil.format("店铺名称为空"));
        }

        if (ObjectUtil.isEmpty(code)) {
            log.error("店铺异常管理tbPlatformAccoSiteCode>>update>>编码为空{}");
            return ResponseData.error(StrUtil.format("编码为空"));
        }

        if (ObjectUtil.isEmpty(type)) {
            log.error("店铺异常管理tbPlatformAccoSiteCode>>update>>修改类型为空{}");
            return ResponseData.error(StrUtil.format("修改类型为空"));
        }

        //所有账号编号
        Set<String> typeCodeSet = new LambdaQueryChainWrapper<>(tbPlatformAccoSiteCodeMapper)
                .eq(TbPlatformAccoSiteCode::getType, type).list().stream().map(TbPlatformAccoSiteCode::getCode).collect(Collectors.toSet());
        //重复编码校验
        if (typeCodeSet.contains(code)) {
            log.error("店铺异常管理tbPlatformAccoSiteCode>>update>>{}编码[{}]重复", type,code);
            return ResponseData.error(StrUtil.format("{}编码[{}]重复", type,code));

        }

        // 根据账号和名字更新编码
        LambdaUpdateChainWrapper<TbPlatformAccoSiteCode> chainWrapper = new LambdaUpdateChainWrapper<>(tbPlatformAccoSiteCodeMapper);
        chainWrapper
        .eq(ObjectUtil.isNotEmpty(name),TbPlatformAccoSiteCode::getName, name)
        .eq(TbPlatformAccoSiteCode::getType, type)
        .set(ObjectUtil.isNotEmpty(code),TbPlatformAccoSiteCode::getCode, code).update();
        return ResponseData.success(entityParam);
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param undefinedId 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(String undefinedId){
        int total = tbPlatformAccoSiteCodeMapper.deleteById(undefinedId);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param undefinedIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<String> undefinedIdList){
         int delCount = tbPlatformAccoSiteCodeMapper.deleteBatchIds(undefinedIdList);
         if (undefinedIdList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
}