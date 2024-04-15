package com.tadpole.cloud.platformSettlement.modular.sales.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.TargetBoard;
import com.tadpole.cloud.platformSettlement.modular.sales.mapper.TargetBoardMapper;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.InventoryDemandParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.TargetBoardParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.InventoryDemandResult;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.TargetBoardResult;
import com.tadpole.cloud.platformSettlement.modular.sales.service.IInventoryDemandService;
import com.tadpole.cloud.platformSettlement.modular.sales.service.ITargetBoardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gal
 * @since 2022-03-08
 */
@Service
public class TargetBoardServiceImpl extends ServiceImpl<TargetBoardMapper, TargetBoard> implements ITargetBoardService {

    @Resource
    private TargetBoardMapper mapper;
    @Resource
    private IInventoryDemandService inventoryDemandService;

    @DataSource(name = "sales")
    @Override
    public List<TargetBoardResult> listBySpec(TargetBoardParam param) {
        List<TargetBoardResult> list = mapper.list(param);
        List<TargetBoardResult> listInv = mapper.listInv(param);
        for (TargetBoardResult targetBoardResult : list) {
            for (TargetBoardResult boardResult : listInv) {
                if (targetBoardResult.getD3().equals(boardResult.getD3())) {
                    targetBoardResult.setPurchaseBudget(boardResult.getPurchaseBudget());
                    targetBoardResult.setTargetInventorySalesRatio(boardResult.getTargetInventorySalesRatio());
                }
            }
        }
        Collections.swap(list,list.size()-1,list.size()-2);
        list = list.stream().filter(i->StrUtil.isNotEmpty(i.getPlatform())).collect(Collectors.toList());

        //添加汇总字段
        for ( TargetBoardResult item: list) {
            String platform = item.getPlatform();
            String department = item.getDepartment();
            String team = item.getTeam();
            if (StrUtil.isEmpty(team) && StrUtil.isEmpty(department)) {
                item.setDepartment(platform + "汇总");
                item.setTeam(platform + "汇总");
            } else if (StrUtil.isEmpty(team) && StrUtil.isNotEmpty(department)) {
                item.setTeam(department + "汇总");
            }
        }
        return list;

    }

    @DataSource(name = "sales")
    @Override
    public TargetBoardResult listSum(TargetBoardParam param) {
        TargetBoardResult targetBoardResult = mapper.listSum(param);
        if (targetBoardResult == null) {
            return null;
        }
        InventoryDemandParam inventoryDemandParam = new InventoryDemandParam();
        BeanUtil.copyProperties(param, inventoryDemandParam);
        InventoryDemandResult inventoryDemandResult= inventoryDemandService.listSum(inventoryDemandParam);
        if (inventoryDemandResult == null) {
            return null;
        }
        BigDecimal inventoryYearTarget = inventoryDemandResult.getYearTarget();
        BigDecimal inventoryTargetInventorySalesRatio = inventoryDemandResult.getTargetInventorySalesRatio();

        String season = param.getSeason();
        if (StrUtil.isNotEmpty(season)) {
            switch (season) {
                case "Q1":
                    inventoryYearTarget = inventoryDemandResult.getSeasonOne();
                    break;
                case "Q2":
                    inventoryYearTarget = inventoryDemandResult.getSeasonTwo();
                    break;
                case "Q3":
                    inventoryYearTarget = inventoryDemandResult.getSeasonThree();
                    break;
                case "Q4":
                    inventoryYearTarget = inventoryDemandResult.getSeasonFour();
                    break;
            }
        } else {
            inventoryYearTarget = inventoryDemandResult.getYearTarget();

        }
        targetBoardResult.setPurchaseBudget(inventoryYearTarget);
        targetBoardResult.setTargetInventorySalesRatio(inventoryTargetInventorySalesRatio);
        return targetBoardResult;
    }


    @DataSource(name = "EBMS")
    @Override
    public List<String> departmentTeam() {
        return this.baseMapper.departmentTeam();
    }

    @DataSource(name = "EBMS")
    @Override
    public List<String> platformList() {
        return this.baseMapper.platformList();
    }

}
