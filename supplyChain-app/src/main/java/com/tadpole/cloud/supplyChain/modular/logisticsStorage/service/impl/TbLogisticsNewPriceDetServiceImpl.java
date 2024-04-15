package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsNewPriceDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsNewPriceDetMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsNewPriceDetParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsNewPriceDetResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsNewPriceDetService;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 物流商的价格信息-明细;(tb_logistics_new_price_det)表服务实现类
 *
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsNewPriceDetServiceImpl extends ServiceImpl<TbLogisticsNewPriceDetMapper, TbLogisticsNewPriceDet> implements TbLogisticsNewPriceDetService {
    @Resource
    private TbLogisticsNewPriceDetMapper tbLogisticsNewPriceDetMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param pkLogpDetId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsNewPriceDet queryById(BigDecimal pkLogpDetId) {
        return tbLogisticsNewPriceDetMapper.selectById(pkLogpDetId);
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
    public Page<TbLogisticsNewPriceDetResult> paginQuery(TbLogisticsNewPriceDetParam param, long current, long size) {
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsNewPriceDet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogpDetUseStatus()), TbLogisticsNewPriceDet::getBusLogpDetUseStatus, param.getBusLogpDetUseStatus());

        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPkLogpId()), TbLogisticsNewPriceDet::getPkLogpId, param.getPkLogpId());
        //查历史价格记录
        if (ObjectUtil.isNotEmpty(param.getBusLogpDetStatus()) && param.getBusLogpDetStatus() < 0) {
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogpDetWeightGreater()), TbLogisticsNewPriceDet::getBusLogpDetWeightGreater, param.getBusLogpDetWeightGreater());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogpDetWeightLess()), TbLogisticsNewPriceDet::getBusLogpDetWeightLess, param.getBusLogpDetWeightLess());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogpDetVolumeGreater()), TbLogisticsNewPriceDet::getBusLogpDetVolumeGreater, param.getBusLogpDetVolumeGreater());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogpDetVolumeLess()), TbLogisticsNewPriceDet::getBusLogpDetVolumeLess, param.getBusLogpDetVolumeLess());
            queryWrapper.lt(TbLogisticsNewPriceDet::getBusLogpDetStatus, 0);
        } else {
            //0:预备中,1:生效
            queryWrapper.ge(TbLogisticsNewPriceDet::getBusLogpDetStatus, 0);
        }

        //2. 执行分页查询
        Page<TbLogisticsNewPriceDetResult> pagin = new Page<>(current, size, true);
        IPage<TbLogisticsNewPriceDetResult> selectResult = tbLogisticsNewPriceDetMapper.selectByPage(pagin, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

    /**
     * 新增数据
     *
     * @param tbLogisticsNewPriceDet 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsNewPriceDet insert(TbLogisticsNewPriceDet tbLogisticsNewPriceDet) {
        tbLogisticsNewPriceDetMapper.insert(tbLogisticsNewPriceDet);
        return tbLogisticsNewPriceDet;
    }

    /**
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public ResponseData update(TbLogisticsNewPriceDetParam param) {
        //1、现根据物流价格编号查出启用明细
        LoginUser loginUser = LoginContext.me().getLoginUser();
        Date nowDate = DateUtil.date();
        LambdaQueryWrapper<TbLogisticsNewPriceDet> qw = new LambdaQueryWrapper<>();
        qw.eq(TbLogisticsNewPriceDet :: getPkLogpId, param.getPkLogpId())
                .eq(TbLogisticsNewPriceDet :: getBusLogpDetWeightGreater, param.getBusLogpDetWeightGreater())
                .eq(TbLogisticsNewPriceDet :: getBusLogpDetWeightLess, param.getBusLogpDetWeightLess())
                .eq(TbLogisticsNewPriceDet :: getBusLogpDetVolumeGreater, param.getBusLogpDetVolumeGreater())
                .eq(TbLogisticsNewPriceDet :: getBusLogpDetVolumeLess, param.getBusLogpDetVolumeLess())
                .eq(TbLogisticsNewPriceDet :: getBusLogpDetStatus, "1");
        TbLogisticsNewPriceDet priceDet = tbLogisticsNewPriceDetMapper.selectOne(qw);
        if(priceDet == null){
            return ResponseData.error("未查询到可以编辑的数据！");
        }

        //新增更新数据
        TbLogisticsNewPriceDet newPriceDet = new TbLogisticsNewPriceDet();
        BeanUtils.copyProperties(priceDet, newPriceDet);
        BeanUtils.copyProperties(param, newPriceDet);
        priceDet.setBusLogpDetStatus(1);
        priceDet.setBusLogpDetUseStatus("已启用");
        newPriceDet.setSysAddDate(nowDate);
        newPriceDet.setSysPerCode(loginUser.getAccount());
        newPriceDet.setSysPerName(loginUser.getName());
        newPriceDet.setBusLogpDetAppStartDate(nowDate);
        tbLogisticsNewPriceDetMapper.insert(newPriceDet);

        //更改旧数据状态
        priceDet.setBusLogpDetStatus(-1);
        priceDet.setBusLogpDetUseStatus("已禁用");
        priceDet.setSysUpdPerCode(loginUser.getAccount());
        priceDet.setSysUpdPerName(loginUser.getName());
        priceDet.setSysUpdDatetime(nowDate);
        priceDet.setBusLogpDetAppEndDate(DateUtil.offsetDay(nowDate,-1));
        tbLogisticsNewPriceDetMapper.updateById(priceDet);
        return ResponseData.success();
    }

    /**
     * 通过主键删除数据
     *
     * @param pkLogpDetId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal pkLogpDetId) {
        int total = tbLogisticsNewPriceDetMapper.deleteById(pkLogpDetId);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param pkLogpDetIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> pkLogpDetIdList) {
        int delCount = tbLogisticsNewPriceDetMapper.deleteBatchIds(pkLogpDetIdList);
        if (pkLogpDetIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    /**
     * 通过ID+状态  更新价格明细的使用状态
     *
     * @param pkLogpId
     * @param pkLogpDetId
     * @param busLogpDetUseStatus
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public ResponseData updateDetUseStatus(BigDecimal pkLogpId, BigDecimal pkLogpDetId, String busLogpDetUseStatus) {

        if (ObjectUtil.isEmpty(busLogpDetUseStatus) || (!"已禁用".equals(busLogpDetUseStatus) && !"已启用".equals(busLogpDetUseStatus))) {
            return ResponseData.error("busLogpDetUseStatus所传状态值不对");
        }

        TbLogisticsNewPriceDet priceDet = this.queryById(pkLogpDetId);

        if (busLogpDetUseStatus.equals(priceDet.getBusLogpDetUseStatus())) {
            return ResponseData.success();
        }

        LoginUser loginUser = LoginContext.me().getLoginUser();

        LambdaUpdateWrapper<TbLogisticsNewPriceDet> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TbLogisticsNewPriceDet::getPkLogpDetId, pkLogpDetId);

        if ("已禁用".equals(busLogpDetUseStatus)) {
            updateWrapper.set(TbLogisticsNewPriceDet::getBusLogpDetStatus, -1);
            updateWrapper.set(TbLogisticsNewPriceDet::getBusLogpDetUseStatus, busLogpDetUseStatus);

        } else {
            // 启用时检查是否有同区间的价格明细处于启用当中，如果有则启用失败，需要先关闭在启用
            List<TbLogisticsNewPriceDet> priceDetList = this.queryByPkLogpId(pkLogpId, 1, "已启用");
            if (ObjectUtil.isNotEmpty(priceDetList)) {
                List<TbLogisticsNewPriceDet> filterList = priceDetList.stream().filter(
                        pd -> (pd.getBusLogpDetWeightGreater().equals(priceDet.getBusLogpDetWeightGreater())
                                && pd.getBusLogpDetWeightLess().equals(priceDet.getBusLogpDetWeightLess())
                                && pd.getBusLogpDetVolumeGreater().equals(priceDet.getBusLogpDetVolumeGreater())
                                && pd.getBusLogpDetVolumeLess().equals(priceDet.getBusLogpDetVolumeLess())
                        )
                ).collect(Collectors.toList());

                if (ObjectUtil.isNotEmpty(filterList)) {
                    return ResponseData.error("系统已经启用了相同范围的报价，需要先禁用，再启用新的报价！");
                }
            }
            updateWrapper.set(TbLogisticsNewPriceDet::getBusLogpDetStatus, 1);
            updateWrapper.set(TbLogisticsNewPriceDet::getBusLogpDetUseStatus, busLogpDetUseStatus);
            updateWrapper.set(TbLogisticsNewPriceDet::getBusLogpDetAppStartDate, new Date());
        }

        updateWrapper.set(TbLogisticsNewPriceDet::getSysUpdDatetime, new Date());
        updateWrapper.set(TbLogisticsNewPriceDet::getSysUpdPerName, loginUser.getName());
        updateWrapper.set(TbLogisticsNewPriceDet::getSysUpdPerCode, loginUser.getAccount());
        if (this.update(updateWrapper)) {
            return ResponseData.success();
        } else {
            return ResponseData.error("状态跟新失败");
        }
    }

    /**
     * 价格明细状态(-1:失效,0:预备中,1:生效)
     */
    @ApiModelProperty(value = "价格明细状态(-1:失效,0:预备中,1:生效)")
    @TableField("bus_logp_det_status")
    private Integer busLogpDetStatus;

    /**
     * 启用状态;'已启用,已禁用'
     */
    @ApiModelProperty(value = "启用状态")
    @TableField("bus_logp_det_use_status")
    private String busLogpDetUseStatus;

    /**
     * @param pkLogpId
     * @param busLogpDetStatus
     * @param busLogpDetUseStatus
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public List<TbLogisticsNewPriceDet> queryByPkLogpId(BigDecimal pkLogpId, Integer busLogpDetStatus, String busLogpDetUseStatus) {
        LambdaQueryWrapper<TbLogisticsNewPriceDet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TbLogisticsNewPriceDet::getPkLogpId, pkLogpId);
        queryWrapper.eq(ObjectUtil.isNotNull(busLogpDetStatus), TbLogisticsNewPriceDet::getBusLogpDetStatus, busLogpDetStatus);
        queryWrapper.eq(ObjectUtil.isNotEmpty(busLogpDetUseStatus), TbLogisticsNewPriceDet::getBusLogpDetUseStatus, busLogpDetUseStatus);
        return this.list(queryWrapper);
    }

}