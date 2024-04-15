package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsTransferRecord;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsTransferRecordMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsTransferRecordParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.ExportDirectTransfersOrderResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsTransferRecordResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsTransferRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 物流调拨记录;(tb_logistics_transfer_record)表服务实现类
 *
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsTransferRecordServiceImpl extends ServiceImpl<TbLogisticsTransferRecordMapper, TbLogisticsTransferRecord> implements TbLogisticsTransferRecordService {
    @Resource
    private TbLogisticsTransferRecordMapper tbLogisticsTransferRecordMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param sysId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsTransferRecord queryById(BigDecimal sysId) {
        return tbLogisticsTransferRecordMapper.selectById(sysId);
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
    public Page<TbLogisticsTransferRecordResult> paginQuery(TbLogisticsTransferRecordParam param, long current, long size) {
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsTransferRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackCode()), TbLogisticsTransferRecord::getPackCode, param.getPackCode());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getTransfersDirection()), TbLogisticsTransferRecord::getTransfersDirection, param.getTransfersDirection());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getTransfersType()), TbLogisticsTransferRecord::getTransfersType, param.getTransfersType());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getTransfersSource()), TbLogisticsTransferRecord::getTransfersSource, param.getTransfersSource());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getTransfersOperator()), TbLogisticsTransferRecord::getTransfersOperator, param.getTransfersOperator());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getTransfersOperatorCode()), TbLogisticsTransferRecord::getTransfersOperatorCode, param.getTransfersOperatorCode());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getTransferOutOrganizeCode()), TbLogisticsTransferRecord::getTransferOutOrganizeCode, param.getTransferOutOrganizeCode());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getTransferOutOrganizeName()), TbLogisticsTransferRecord::getTransferOutOrganizeName, param.getTransferOutOrganizeName());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getTransferInOrganizeCode()), TbLogisticsTransferRecord::getTransferInOrganizeCode, param.getTransferInOrganizeCode());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getTransferInOrganizeName()), TbLogisticsTransferRecord::getTransferInOrganizeName, param.getTransferInOrganizeName());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getStatus()), TbLogisticsTransferRecord::getStatus, param.getStatus());
        queryWrapper.orderByDesc(TbLogisticsTransferRecord::getTransferDate);
        //2. 执行分页查询
        Page<TbLogisticsTransferRecordResult> pagin = new Page<>(current, size, true);
        IPage<TbLogisticsTransferRecordResult> selectResult = tbLogisticsTransferRecordMapper.selectByPage(pagin, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

    /**
     * 新增数据
     *
     * @param tbLogisticsTransferRecord 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsTransferRecord insert(TbLogisticsTransferRecord tbLogisticsTransferRecord) {
        tbLogisticsTransferRecordMapper.insert(tbLogisticsTransferRecord);
        return tbLogisticsTransferRecord;
    }

    /**
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsTransferRecord update(TbLogisticsTransferRecordParam param) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsTransferRecord> wrapper = new LambdaUpdateChainWrapper<TbLogisticsTransferRecord>(tbLogisticsTransferRecordMapper);
        wrapper.set(ObjectUtil.isNotEmpty(param.getPackCode()), TbLogisticsTransferRecord::getPackCode, param.getPackCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getTransfersDirection()), TbLogisticsTransferRecord::getTransfersDirection, param.getTransfersDirection());
        wrapper.set(ObjectUtil.isNotEmpty(param.getTransfersType()), TbLogisticsTransferRecord::getTransfersType, param.getTransfersType());
        wrapper.set(ObjectUtil.isNotEmpty(param.getTransfersSource()), TbLogisticsTransferRecord::getTransfersSource, param.getTransfersSource());
        wrapper.set(ObjectUtil.isNotEmpty(param.getTransfersOperator()), TbLogisticsTransferRecord::getTransfersOperator, param.getTransfersOperator());
        wrapper.set(ObjectUtil.isNotEmpty(param.getTransfersOperatorCode()), TbLogisticsTransferRecord::getTransfersOperatorCode, param.getTransfersOperatorCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getTransferOutOrganizeCode()), TbLogisticsTransferRecord::getTransferOutOrganizeCode, param.getTransferOutOrganizeCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getTransferOutOrganizeName()), TbLogisticsTransferRecord::getTransferOutOrganizeName, param.getTransferOutOrganizeName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getTransferInOrganizeCode()), TbLogisticsTransferRecord::getTransferInOrganizeCode, param.getTransferInOrganizeCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getTransferInOrganizeName()), TbLogisticsTransferRecord::getTransferInOrganizeName, param.getTransferInOrganizeName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getStatus()), TbLogisticsTransferRecord::getStatus, param.getStatus());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsTransferRecord::getSysId, param.getSysId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if (ret) {
            return queryById(param.getSysId());
        } else {
            return null;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param sysId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal sysId) {
        int total = tbLogisticsTransferRecordMapper.deleteById(sysId);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param sysIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> sysIdList) {
        int delCount = tbLogisticsTransferRecordMapper.deleteBatchIds(sysIdList);
        if (sysIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 查询转仓记录
     *
     * @param packCode
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public List<TbLogisticsTransferRecord> getByPackCode(String packCode) {
        LambdaQueryWrapper<TbLogisticsTransferRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TbLogisticsTransferRecord::getPackCode, packCode);
        return tbLogisticsTransferRecordMapper.selectList(wrapper);
    }

    /**
     * 导出直接调拨单
     *
     * @param packCodeList
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public List<ExportDirectTransfersOrderResult> export(List<String> packCodeList) {

        return  tbLogisticsTransferRecordMapper.export(packCodeList);
    }

    @Override
    @DataSource(name = "logistics")
    public void updateStatus(List<String> packCodeList) {
        LambdaUpdateWrapper<TbLogisticsTransferRecord> up = new LambdaUpdateWrapper<>();
        up.in(TbLogisticsTransferRecord :: getPackCode, packCodeList)
                .eq(TbLogisticsTransferRecord :: getStatus, "未处理")
                .set(TbLogisticsTransferRecord :: getStatus, "已处理");
        this.update(up);
    }

}