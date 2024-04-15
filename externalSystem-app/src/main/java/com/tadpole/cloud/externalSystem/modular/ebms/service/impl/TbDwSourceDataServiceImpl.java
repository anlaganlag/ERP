package com.tadpole.cloud.externalSystem.modular.ebms.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.api.ebms.entity.TbDwSourceData;
import com.tadpole.cloud.externalSystem.modular.ebms.mapper.TbDwSourceDataMapper;
import com.tadpole.cloud.externalSystem.modular.ebms.model.param.TbDwSourceDataParam;
import com.tadpole.cloud.externalSystem.modular.ebms.model.result.TbDwSourceDataResult;
import com.tadpole.cloud.externalSystem.modular.ebms.service.TbDwSourceDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
 /**
 * TbDWSourceData;(Tb_DW_Source_Data)--服务实现类
 * @author : LSY
 * @create : 2023-8-14
 */
@Slf4j
@Service
@Transactional
public class TbDwSourceDataServiceImpl extends ServiceImpl<TbDwSourceDataMapper, TbDwSourceData>  implements TbDwSourceDataService{
    @Resource
    private TbDwSourceDataMapper tbDwSourceDataMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param dwDataObjNum 主键
     * @return 实例对象
     */
    @DataSource(name = "EBMS")
    @Override
    public TbDwSourceData queryById(String dwDataObjNum){
        return tbDwSourceDataMapper.selectById(dwDataObjNum);
    }
    
    /**
     * 分页查询
     *
     * @param queryParam 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "EBMS")
    @Override
    public Page<TbDwSourceDataResult> paginQuery(TbDwSourceDataParam queryParam, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbDwSourceData> queryWrapper = new LambdaQueryWrapper<>();
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDataObjNum()),TbDwSourceData::getDwDataObjNum, queryParam.getDwDataObjNum());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwPlatNum()),TbDwSourceData::getDwPlatNum, queryParam.getDwPlatNum());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwPlatName()),TbDwSourceData::getDwPlatName, queryParam.getDwPlatName());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwCategoryId()),TbDwSourceData::getDwCategoryId, queryParam.getDwCategoryId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwCateGoryName()),TbDwSourceData::getDwCateGoryName, queryParam.getDwCateGoryName());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDataObjEnName()),TbDwSourceData::getDwDataObjEnName, queryParam.getDwDataObjEnName());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDataObjCnName()),TbDwSourceData::getDwDataObjCnName, queryParam.getDwDataObjCnName());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDataTableObj()),TbDwSourceData::getDwDataTableObj, queryParam.getDwDataTableObj());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDlType()),TbDwSourceData::getDwDlType, queryParam.getDwDlType());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDlExeactDate()),TbDwSourceData::getDwDlExeactDate, queryParam.getDwDlExeactDate());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDlComplateTime()),TbDwSourceData::getDwDlComplateTime, queryParam.getDwDlComplateTime());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwSaveType()),TbDwSourceData::getDwSaveType, queryParam.getDwSaveType());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwHandleDetInfo()),TbDwSourceData::getDwHandleDetInfo, queryParam.getDwHandleDetInfo());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwType()),TbDwSourceData::getDwType, queryParam.getDwType());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDataReportTypeEnum()),TbDwSourceData::getDwDataReportTypeEnum, queryParam.getDwDataReportTypeEnum());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwTaskCreateTime()),TbDwSourceData::getDwTaskCreateTime, queryParam.getDwTaskCreateTime());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getRegion()),TbDwSourceData::getRegion, queryParam.getRegion());
        
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getDwCreateDate()),TbDwSourceData::getDwCreateDate, queryParam.getDwCreateDate());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getDwIsSchedule()),TbDwSourceData::getDwIsSchedule, queryParam.getDwIsSchedule());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getDwDltime()),TbDwSourceData::getDwDltime, queryParam.getDwDltime());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getStatus()),TbDwSourceData::getStatus, queryParam.getStatus());
        //2. 执行分页查询
        Page<TbDwSourceDataResult> pagin = new Page<>(current , size , true);
        IPage<TbDwSourceDataResult> selectResult = tbDwSourceDataMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbDwSourceData 实例对象
     * @return 实例对象
     */
    @DataSource(name = "EBMS")
    @Override
    public TbDwSourceData insert(TbDwSourceData tbDwSourceData){
        tbDwSourceDataMapper.insert(tbDwSourceData);
        return tbDwSourceData;
    }
    
    /** 
     * 更新数据
     *
     * @param tbDwSourceData 实例对象
     * @return 实例对象
     */
    @DataSource(name = "EBMS")
    @Override
    public TbDwSourceData update(TbDwSourceData entityParam){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbDwSourceData> chainWrapper = new LambdaUpdateChainWrapper<>(tbDwSourceDataMapper);
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwPlatNum()),TbDwSourceData::getDwPlatNum, entityParam.getDwPlatNum());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwPlatName()),TbDwSourceData::getDwPlatName, entityParam.getDwPlatName());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwCategoryId()),TbDwSourceData::getDwCategoryId, entityParam.getDwCategoryId());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwCateGoryName()),TbDwSourceData::getDwCateGoryName, entityParam.getDwCateGoryName());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDataObjEnName()),TbDwSourceData::getDwDataObjEnName, entityParam.getDwDataObjEnName());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDataObjCnName()),TbDwSourceData::getDwDataObjCnName, entityParam.getDwDataObjCnName());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDataTableObj()),TbDwSourceData::getDwDataTableObj, entityParam.getDwDataTableObj());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDlType()),TbDwSourceData::getDwDlType, entityParam.getDwDlType());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDlExeactDate()),TbDwSourceData::getDwDlExeactDate, entityParam.getDwDlExeactDate());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDlComplateTime()),TbDwSourceData::getDwDlComplateTime, entityParam.getDwDlComplateTime());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwSaveType()),TbDwSourceData::getDwSaveType, entityParam.getDwSaveType());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwHandleDetInfo()),TbDwSourceData::getDwHandleDetInfo, entityParam.getDwHandleDetInfo());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwType()),TbDwSourceData::getDwType, entityParam.getDwType());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDataReportTypeEnum()),TbDwSourceData::getDwDataReportTypeEnum, entityParam.getDwDataReportTypeEnum());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwTaskCreateTime()),TbDwSourceData::getDwTaskCreateTime, entityParam.getDwTaskCreateTime());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getRegion()),TbDwSourceData::getRegion, entityParam.getRegion());
        
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getDwCreateDate()),TbDwSourceData::getDwCreateDate, entityParam.getDwCreateDate());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getDwIsSchedule()),TbDwSourceData::getDwIsSchedule, entityParam.getDwIsSchedule());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getDwDltime()),TbDwSourceData::getDwDltime, entityParam.getDwDltime());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getStatus()),TbDwSourceData::getStatus, entityParam.getStatus());
        //2. 设置主键，并更新
        chainWrapper.eq(TbDwSourceData::getDwDataObjNum, entityParam.getDwDataObjNum());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(entityParam.getDwDataObjNum());
        }else{
            return entityParam;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param dwDataObjNum 主键
     * @return 是否成功
     */
    @DataSource(name = "EBMS")
    @Override
    public boolean deleteById(String dwDataObjNum){
        int total = tbDwSourceDataMapper.deleteById(dwDataObjNum);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param dwDataObjNumList 主键List
     * @return 是否成功
     */
    @DataSource(name = "EBMS")
    @Override
    public boolean deleteBatchIds(List<String> dwDataObjNumList){
         int delCount = tbDwSourceDataMapper.deleteBatchIds(dwDataObjNumList);
         if (dwDataObjNumList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }


 }