package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.entity.RecomSeasonRatio;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TeamVerif;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.RecomSeasonRatioMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PurchaseOrdersParam;
import com.tadpole.cloud.operationManagement.modular.stock.service.IPurchaseOrdersService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IRecomSeasonRatioService;
import com.tadpole.cloud.operationManagement.modular.stock.service.ITeamVerifService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* <p>
    * 季节系数规则表-最细维度匹配 服务实现类
    * </p>
*
* @author lsy
* @since 2022-06-23
*/
@Service
public class RecomSeasonRatioServiceImpl extends ServiceImpl<RecomSeasonRatioMapper, RecomSeasonRatio> implements IRecomSeasonRatioService {

    @Resource
    private RecomSeasonRatioMapper mapper;

    @Resource
    private ITeamVerifService teamVerifService;

    @Resource
    private IPurchaseOrdersService purchaseOrdersService;


    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RecomSeasonRatio findOneByArea(TeamVerif teamVerif) {

        LambdaQueryWrapper<RecomSeasonRatio> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RecomSeasonRatio::getArea, teamVerif.getArea())
                .eq(RecomSeasonRatio::getMaterialCode,teamVerif.getMaterialCode())
                .eq(RecomSeasonRatio::getPlatform,teamVerif.getPlatform())
                .eq(RecomSeasonRatio::getDepartment,teamVerif.getDepartment())
                .eq(RecomSeasonRatio::getTeam,teamVerif.getTeam())
                .eq(ObjectUtil.isNotNull(teamVerif.getBizdate()),RecomSeasonRatio::getBizdate,teamVerif.getBizdate());

        List<RecomSeasonRatio> recomSeasonRatioList = mapper.selectList(wrapper);

        if (ObjectUtil.isNotEmpty(recomSeasonRatioList)) {
            return recomSeasonRatioList.get(0);
        }

        return null;
    }


    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData findByPurchaseOrderId(PurchaseOrdersParam param) {

        String id = param.getId();
        if (ObjectUtil.isEmpty(id)) {
            ResponseData.error("id不能为空");
        }
        LambdaQueryWrapper<TeamVerif> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeamVerif::getPurchaseApplyNo, id);

//        PurchaseOrders order = purchaseOrdersService.getById(id);
 /*       if (param.getIsSameType()) {
            queryWrapper.eq(TeamVerif::getBillType, order.getBillType());
        } else {
            queryWrapper.ne(TeamVerif::getBillType, order.getBillType());
        }*/
        List<TeamVerif> verifList = teamVerifService.getBaseMapper().selectList(queryWrapper);

        List<RecomSeasonRatio> resultList = new ArrayList<>();

        if (ObjectUtil.isEmpty(verifList)) {
            return ResponseData.error("未找到订单号" + id + "对应的team审核记录信息");
        }
        for (TeamVerif teamVerif : verifList) {
            RecomSeasonRatio seasonRatio = this.findOneByArea(teamVerif);
            if (ObjectUtil.isNotNull(seasonRatio)) {
                resultList.add(seasonRatio);
            }
        }
        return ResponseData.success(resultList);
    }

}
