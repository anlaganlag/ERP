package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackListFileUpload;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsPackListFileUploadMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackListFileUploadParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackListFileUploadResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsPackListFileUploadService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
 /**
 * 亚马逊货件后台生成的excel文件上传信息;(tb_logistics_pack_list_file_upload)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsPackListFileUploadServiceImpl  extends ServiceImpl<TbLogisticsPackListFileUploadMapper, TbLogisticsPackListFileUpload> implements TbLogisticsPackListFileUploadService {
    @Resource
    private TbLogisticsPackListFileUploadMapper tbLogisticsPackListFileUploadMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkShipId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackListFileUpload queryById(BigDecimal pkShipId){
        return tbLogisticsPackListFileUploadMapper.selectById(pkShipId);
    }
    
    /**
     * 分页查询
     *
     * @param param 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public Page<TbLogisticsPackListFileUploadResult> paginQuery(TbLogisticsPackListFileUploadParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsPackListFileUpload> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusShipmentId()),TbLogisticsPackListFileUpload::getBusShipmentId, param.getBusShipmentId());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusCountryCode()),TbLogisticsPackListFileUpload::getBusCountryCode, param.getBusCountryCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusShopNameSimple()),TbLogisticsPackListFileUpload::getBusShopNameSimple, param.getBusShopNameSimple());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusFeedExcelSubmissionId()),TbLogisticsPackListFileUpload::getBusFeedExcelSubmissionId, param.getBusFeedExcelSubmissionId());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusFeedExcelProcesStatus()),TbLogisticsPackListFileUpload::getBusFeedExcelProcesStatus, param.getBusFeedExcelProcesStatus());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusUpExcelResult()),TbLogisticsPackListFileUpload::getBusUpExcelResult, param.getBusUpExcelResult());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusFeedXmlSubmissionId()),TbLogisticsPackListFileUpload::getBusFeedXmlSubmissionId, param.getBusFeedXmlSubmissionId());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusFeedXmlProcesStatus()),TbLogisticsPackListFileUpload::getBusFeedXmlProcesStatus, param.getBusFeedXmlProcesStatus());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusUpXmlResult()),TbLogisticsPackListFileUpload::getBusUpXmlResult, param.getBusUpXmlResult());
        //2. 执行分页查询
        Page<TbLogisticsPackListFileUploadResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsPackListFileUploadResult> selectResult = tbLogisticsPackListFileUploadMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsPackListFileUpload 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackListFileUpload insert(TbLogisticsPackListFileUpload tbLogisticsPackListFileUpload){
        tbLogisticsPackListFileUploadMapper.insert(tbLogisticsPackListFileUpload);
        return tbLogisticsPackListFileUpload;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackListFileUpload update(TbLogisticsPackListFileUploadParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsPackListFileUpload> wrapper = new LambdaUpdateChainWrapper<TbLogisticsPackListFileUpload>(tbLogisticsPackListFileUploadMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusShipmentId()),TbLogisticsPackListFileUpload::getBusShipmentId, param.getBusShipmentId());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusCountryCode()),TbLogisticsPackListFileUpload::getBusCountryCode, param.getBusCountryCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusShopNameSimple()),TbLogisticsPackListFileUpload::getBusShopNameSimple, param.getBusShopNameSimple());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusFeedExcelSubmissionId()),TbLogisticsPackListFileUpload::getBusFeedExcelSubmissionId, param.getBusFeedExcelSubmissionId());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusFeedExcelProcesStatus()),TbLogisticsPackListFileUpload::getBusFeedExcelProcesStatus, param.getBusFeedExcelProcesStatus());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusUpExcelResult()),TbLogisticsPackListFileUpload::getBusUpExcelResult, param.getBusUpExcelResult());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusFeedXmlSubmissionId()),TbLogisticsPackListFileUpload::getBusFeedXmlSubmissionId, param.getBusFeedXmlSubmissionId());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusFeedXmlProcesStatus()),TbLogisticsPackListFileUpload::getBusFeedXmlProcesStatus, param.getBusFeedXmlProcesStatus());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusUpXmlResult()),TbLogisticsPackListFileUpload::getBusUpXmlResult, param.getBusUpXmlResult());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsPackListFileUpload::getPkShipId, param.getPkShipId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getPkShipId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param pkShipId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal pkShipId){
        int total = tbLogisticsPackListFileUploadMapper.deleteById(pkShipId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param pkShipIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> pkShipIdList) {
        int delCount = tbLogisticsPackListFileUploadMapper.deleteBatchIds(pkShipIdList);
        if (pkShipIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}