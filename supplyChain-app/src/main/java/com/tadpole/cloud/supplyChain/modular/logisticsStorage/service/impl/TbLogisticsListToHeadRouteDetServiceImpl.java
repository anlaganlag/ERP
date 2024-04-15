package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsListToHeadRouteParam;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsListToHeadRouteDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsListToHeadRouteDetMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsListToHeadRouteDetService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsListToHeadRouteDetResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsListToHeadRouteDetParam;

import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 物流单头程信息-明细;(tb_logistics_list_to_head_route_det)表服务实现类
 *
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsListToHeadRouteDetServiceImpl extends ServiceImpl<TbLogisticsListToHeadRouteDetMapper, TbLogisticsListToHeadRouteDet> implements TbLogisticsListToHeadRouteDetService {
    @Resource
    private TbLogisticsListToHeadRouteDetMapper tbLogisticsListToHeadRouteDetMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsListToHeadRouteDet queryById(BigDecimal id) {
        return tbLogisticsListToHeadRouteDetMapper.selectById(id);
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
    public Page<TbLogisticsListToHeadRouteDetResult> paginQuery(TbLogisticsListToHeadRouteDetParam param, long current, long size) {
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsListToHeadRouteDet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLhrCode()), TbLogisticsListToHeadRouteDet::getLhrCode, param.getLhrCode());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLhrOddNumb()), TbLogisticsListToHeadRouteDet::getLhrOddNumb, param.getLhrOddNumb());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackCode()), TbLogisticsListToHeadRouteDet::getPackCode, param.getPackCode());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxCode()), TbLogisticsListToHeadRouteDet::getPackDetBoxCode, param.getPackDetBoxCode());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShipmentId()), TbLogisticsListToHeadRouteDet::getShipmentId, param.getShipmentId());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLhrdState()), TbLogisticsListToHeadRouteDet::getLhrdState, param.getLhrdState());
        //2. 执行分页查询
        Page<TbLogisticsListToHeadRouteDetResult> pagin = new Page<>(current, size, true);
        IPage<TbLogisticsListToHeadRouteDetResult> selectResult = tbLogisticsListToHeadRouteDetMapper.selectByPage(pagin, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

    /**
     * 新增数据
     *
     * @param tbLogisticsListToHeadRouteDet 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsListToHeadRouteDet insert(TbLogisticsListToHeadRouteDet tbLogisticsListToHeadRouteDet) {
        tbLogisticsListToHeadRouteDetMapper.insert(tbLogisticsListToHeadRouteDet);
        return tbLogisticsListToHeadRouteDet;
    }

    /**
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsListToHeadRouteDet update(TbLogisticsListToHeadRouteDetParam param) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsListToHeadRouteDet> wrapper = new LambdaUpdateChainWrapper<TbLogisticsListToHeadRouteDet>(tbLogisticsListToHeadRouteDetMapper);
        wrapper.set(ObjectUtil.isNotEmpty(param.getLhrCode()), TbLogisticsListToHeadRouteDet::getLhrCode, param.getLhrCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getLhrOddNumb()), TbLogisticsListToHeadRouteDet::getLhrOddNumb, param.getLhrOddNumb());
        wrapper.set(ObjectUtil.isNotEmpty(param.getPackCode()), TbLogisticsListToHeadRouteDet::getPackCode, param.getPackCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxCode()), TbLogisticsListToHeadRouteDet::getPackDetBoxCode, param.getPackDetBoxCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getShipmentId()), TbLogisticsListToHeadRouteDet::getShipmentId, param.getShipmentId());
        wrapper.set(ObjectUtil.isNotEmpty(param.getLhrdState()), TbLogisticsListToHeadRouteDet::getLhrdState, param.getLhrdState());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsListToHeadRouteDet::getId, param.getId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if (ret) {
            return queryById(param.getId());
        } else {
            return null;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal id) {
        int total = tbLogisticsListToHeadRouteDetMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param idList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> idList) {
        int delCount = tbLogisticsListToHeadRouteDetMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 通过LhrCode获取头程物流单明细
     * @param lhrCode
     * @return
     * @param lhrCode
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public List<TbLogisticsListToHeadRouteDet> queryByLhrCode(String lhrCode) {

        LambdaQueryWrapper<TbLogisticsListToHeadRouteDet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TbLogisticsListToHeadRouteDet::getLhrCode, lhrCode);
        return tbLogisticsListToHeadRouteDetMapper.selectList(queryWrapper);
    }

    /**
     * 通过LhrOddNumb获取头程物流单明细
     * @param lhrOddNumb
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public List<TbLogisticsListToHeadRouteDet> queryByLhrOddNumb(String lhrOddNumb) {

        LambdaQueryWrapper<TbLogisticsListToHeadRouteDet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TbLogisticsListToHeadRouteDet::getLhrOddNumb, lhrOddNumb);
        return tbLogisticsListToHeadRouteDetMapper.selectList(queryWrapper);
    }

    /**
     * 物流转仓  --2024-3-4漫谷和实施和仓库沟通取消转仓这操作，实际业务没有使用
     * @param param
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public ResponseData logisticsTransformhouse(TbLogisticsListToHeadRouteParam param) {

        return null;
    }

    /**
     * 根据批次号和快递单号 删除物流单头程信息
     * @param lhrCode
     * @param lhrOddNumb
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public int delByLhrCodeAndLhrOddNumb(String lhrCode, String lhrOddNumb) {

        return    tbLogisticsListToHeadRouteDetMapper.delete(
                new LambdaQueryWrapper<TbLogisticsListToHeadRouteDet>()
                        .eq(TbLogisticsListToHeadRouteDet::getLhrCode, lhrCode)
                        .eq(TbLogisticsListToHeadRouteDet::getLhrOddNumb, lhrOddNumb)
        );

    }

    /**
     * 根据批次号和快递单号 更新 物流单头程信息 状态
     * @param lhrCode
     * @param lhrOddNumb
     * @param state
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public int upByLhrCodeAndLhrOddNumb(String lhrCode, String lhrOddNumb, String state) {


        return    tbLogisticsListToHeadRouteDetMapper.update(
                null,
                new LambdaUpdateWrapper<TbLogisticsListToHeadRouteDet>()
                        .eq(TbLogisticsListToHeadRouteDet::getLhrCode, lhrCode)
                        .eq(TbLogisticsListToHeadRouteDet::getLhrOddNumb, lhrOddNumb)
                        .set(TbLogisticsListToHeadRouteDet::getLhrdState, state)
        );
    }
}