package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsClaimDetToAmazon;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsClaimToAmazon;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsClaimToAmazonMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsClaimToAmazonParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.DownloadLogisticsAmazonClaimsViewModel;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.LogisticsAmazonClaimsEmailViewModel;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsClaimToAmazonResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsClaimDetToAmazonService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsClaimToAmazonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 亚马逊物流索赔;(tb_logistics_claim_to_amazon)表服务实现类
 *
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsClaimToAmazonServiceImpl extends ServiceImpl<TbLogisticsClaimToAmazonMapper, TbLogisticsClaimToAmazon> implements TbLogisticsClaimToAmazonService {
    @Resource
    private TbLogisticsClaimToAmazonMapper tbLogisticsClaimToAmazonMapper;
    @Resource
    private TbLogisticsClaimDetToAmazonService claimDetToAmazonService;

    /**
     * 通过ID查询单条数据
     *
     * @param pkClaimId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsClaimToAmazon queryById(String pkClaimId) {
        return tbLogisticsClaimToAmazonMapper.selectById(pkClaimId);
    }

    /**
     * 分页查询
     *
     * @param param   筛选条件
     * @param current 当前页码
     * @param size    每页大小
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public Page<TbLogisticsClaimToAmazonResult> paginQuery(TbLogisticsClaimToAmazonParam param, long current, long size) {
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsClaimToAmazon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(param.getBusShipmentId()), TbLogisticsClaimToAmazon::getBusShipmentId, param.getBusShipmentId());//ShipmentId
        queryWrapper.like(ObjectUtil.isNotEmpty(param.getBusCaseId()), TbLogisticsClaimToAmazon::getBusCaseId, param.getBusCaseId()); //CaseID
        if (ObjectUtil.isNotNull(param.getEndDateTime())) {
            queryWrapper.ge(TbLogisticsClaimToAmazon::getBusCaseCreateDate, param.getStartDateTime()); //busCaseCreateDate
            queryWrapper.lt(TbLogisticsClaimToAmazon::getBusCaseCreateDate, DateUtils.addDays(param.getEndDateTime(),1)); //busCaseCreateDate
        }

        if(ObjectUtil.isNotEmpty(param.getBusSKU())){
            LambdaQueryWrapper<TbLogisticsClaimDetToAmazon> detQw = new LambdaQueryWrapper<>();
            detQw.like(TbLogisticsClaimDetToAmazon :: getBusSku, param.getBusSKU());//busSKU
            List<TbLogisticsClaimDetToAmazon> detList = claimDetToAmazonService.list(detQw);
            if(CollectionUtil.isNotEmpty(detList)){
                Set<String> claimIdSet = detList.stream().map(item -> item.getPkClaimId()).collect(Collectors.toSet());
                queryWrapper.in(TbLogisticsClaimToAmazon::getPkClaimId, claimIdSet);
            }
        }

        //2. 执行分页查询
        Page<TbLogisticsClaimToAmazonResult> page = new Page<>(current, size, true);
        IPage<TbLogisticsClaimToAmazonResult> selectResult = tbLogisticsClaimToAmazonMapper.selectByPage(page, queryWrapper);
        page.setPages(selectResult.getPages());
        page.setTotal(selectResult.getTotal());
        page.setRecords(selectResult.getRecords());
        //3. 返回结果
        return page;
    }

    /**
     * 新增数据
     *
     * @param tbLogisticsClaimToAmazon 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsClaimToAmazon insert(TbLogisticsClaimToAmazon tbLogisticsClaimToAmazon) {
        tbLogisticsClaimToAmazonMapper.insert(tbLogisticsClaimToAmazon);
        return tbLogisticsClaimToAmazon;
    }

    /**
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsClaimToAmazon update(TbLogisticsClaimToAmazonParam param) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsClaimToAmazon> wrapper = new LambdaUpdateChainWrapper<TbLogisticsClaimToAmazon>(tbLogisticsClaimToAmazonMapper);
        wrapper.set(ObjectUtil.isNotEmpty(param.getSysCreatePerCode()), TbLogisticsClaimToAmazon::getSysCreatePerCode, param.getSysCreatePerCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getSysCreatePerName()), TbLogisticsClaimToAmazon::getSysCreatePerName, param.getSysCreatePerName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBusShopNameSimple()), TbLogisticsClaimToAmazon::getBusShopNameSimple, param.getBusShopNameSimple());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBusCountryCode()), TbLogisticsClaimToAmazon::getBusCountryCode, param.getBusCountryCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBusShipmentId()), TbLogisticsClaimToAmazon::getBusShipmentId, param.getBusShipmentId());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBusShipmentStatus()), TbLogisticsClaimToAmazon::getBusShipmentStatus, param.getBusShipmentStatus());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBusShipTo()), TbLogisticsClaimToAmazon::getBusShipTo, param.getBusShipTo());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBusCaseId()), TbLogisticsClaimToAmazon::getBusCaseId, param.getBusCaseId());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBusCaseRemarks()), TbLogisticsClaimToAmazon::getBusCaseRemarks, param.getBusCaseRemarks());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBusCasePerCode()), TbLogisticsClaimToAmazon::getBusCasePerCode, param.getBusCasePerCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBusCasePerName()), TbLogisticsClaimToAmazon::getBusCasePerName, param.getBusCasePerName());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsClaimToAmazon::getPkClaimId, param.getPkClaimId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if (ret) {
            return queryById(param.getPkClaimId());
        } else {
            return null;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param pkClaimId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(String pkClaimId) {
        int total = tbLogisticsClaimToAmazonMapper.deleteById(pkClaimId);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param pkClaimIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<String> pkClaimIdList) {
        int delCount = tbLogisticsClaimToAmazonMapper.deleteBatchIds(pkClaimIdList);
        if (pkClaimIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 查看索赔邮件内容模板
     *
     * @param pkClaimID
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public List<LogisticsAmazonClaimsEmailViewModel> queryLogisticsAmazonClaimsEmail(String pkClaimID) {
        return tbLogisticsClaimToAmazonMapper.queryLogisticsAmazonClaimsEmail(pkClaimID);

    }

    /**
     * 下载索赔附件模板
     *
     * @param pkClaimID pkClaimID
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public List<DownloadLogisticsAmazonClaimsViewModel> downloadLogisticsAmazonClaims(String pkClaimID) {
        return tbLogisticsClaimToAmazonMapper.downloadLogisticsAmazonClaims(pkClaimID);
    }

    /**
     * 标记申请索赔
     * @param param
     * @retur
     */
    @DataSource(name = "logistics")
    @Override
    public ResponseData markLogisticsAmazonClaims(TbLogisticsClaimToAmazonParam param) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        param.setBusCasePerCode(loginUser.getAccount());
        param.setBusCasePerName(loginUser.getName());
        param.setBusCaseCreateDate(new Date());
        TbLogisticsClaimToAmazon result = this.update(param);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success("【标记申请索赔】成功");
        }
        return ResponseData.success("【标记申请索赔】失败");
    }
}