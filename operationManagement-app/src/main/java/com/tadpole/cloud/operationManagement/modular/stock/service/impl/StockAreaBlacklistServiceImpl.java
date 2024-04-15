package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONNull;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.entity.StockAreaBlacklist;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.StockAreaBlacklistMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SpecialApplyInfoV3Param;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockAreaBlacklistParam;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockAreaBlacklistService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* <p>
    * 区域黑名单 服务实现类
    * </p>
*
* @author lsy
* @since 2022-12-19
*/
@Service
public class StockAreaBlacklistServiceImpl extends ServiceImpl<StockAreaBlacklistMapper, StockAreaBlacklist> implements IStockAreaBlacklistService {

    @Resource
    private StockAreaBlacklistMapper stockAreaBlacklistMapper;


    @DataSource(name = "stocking")
    @Override
    public PageResult<StockAreaBlacklist> areaBlacklist(StockAreaBlacklistParam param) {
        LambdaQueryWrapper<StockAreaBlacklist> queryWrapper = getWrapper(param);
        IPage<StockAreaBlacklist> blacklistIPage = stockAreaBlacklistMapper.selectPage(param.getPageContext(), queryWrapper);
        PageResult<StockAreaBlacklist> pageResult = new PageResult<>(blacklistIPage);
        return pageResult;
    }



    @DataSource(name = "stocking")
    @Override
    public List<StockAreaBlacklist> export(StockAreaBlacklistParam param) {
        LambdaQueryWrapper<StockAreaBlacklist> queryWrapper = this.getWrapper(param);
        List<StockAreaBlacklist> blacklists = stockAreaBlacklistMapper.selectList(queryWrapper);
        return blacklists;
    }



    @DataSource(name = "stocking")
    @Override
    public ResponseData checkAreaBlacklist(List<SpecialApplyInfoV3Param> applyInfoV3ParamList) {
        if (CollectionUtil.isEmpty(applyInfoV3ParamList)) {
            return ResponseData.success();
        }
//        平台 区域 事业部 Team 物料编码
        List<String> mergeFildList = applyInfoV3ParamList.stream().map(a -> {
            String mergeFildStr = new StringBuffer()
                    .append(a.getPlatform())
                    .append(a.getArea())
                    .append(a.getDepartment())
                    .append(a.getTeam())
                    .append(a.getMaterialCode()).toString();
            return mergeFildStr;
        }).collect(Collectors.toList());

        List<StockAreaBlacklist> findData=  stockAreaBlacklistMapper.checkAreaBlacklist(mergeFildList);

        if (CollectionUtil.isEmpty(findData)) {
            return ResponseData.success();
        }

        List<String> backListMerge = findData.stream().map(f -> {
            String mergeFildStr = new StringBuffer()
                    .append(f.getPlatform()).append("|")
                    .append(f.getArea()).append("|")
                    .append(f.getDepartment()).append("|")
                    .append(f.getTeam()).append("|")
                    .append(f.getMaterialCode()).toString();
            return mergeFildStr;
        }).collect(Collectors.toList());

        return  ResponseData.error(500,"特殊备货申请数据中含有区域黑名单的物料,不能申请--["+ JSONUtil.toJsonStr(backListMerge) +"]");
    }




    private  LambdaQueryWrapper<StockAreaBlacklist> getWrapper(StockAreaBlacklistParam param) {
        LambdaQueryWrapper<StockAreaBlacklist> queryWrapper = new LambdaQueryWrapper<>();
        if ( ! Objects.isNull(param)) {
            queryWrapper.in(CollectionUtil.isNotEmpty(param.getPlatformList()), StockAreaBlacklist::getPlatform, param.getPlatformList());
            queryWrapper.in(CollectionUtil.isNotEmpty(param.getAreaList()), StockAreaBlacklist::getArea, param.getAreaList());
            queryWrapper.in(CollectionUtil.isNotEmpty(param.getProductTypeList()), StockAreaBlacklist::getProductType, param.getProductTypeList());
            queryWrapper.in(CollectionUtil.isNotEmpty(param.getDepartmentList()), StockAreaBlacklist::getDepartment, param.getDepartmentList());
            queryWrapper.in(CollectionUtil.isNotEmpty(param.getTeamList()), StockAreaBlacklist::getTeam, param.getTeamList());
            queryWrapper.in(CollectionUtil.isNotEmpty(param.getMaterialCodeList()), StockAreaBlacklist::getMaterialCode, param.getMaterialCodeList());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getMaterialCode()), StockAreaBlacklist::getMaterialCode, param.getMaterialCode());
            queryWrapper.eq(StringUtils.isNotEmpty(param.getStatus()), StockAreaBlacklist::getStatus, param.getStatus());
            queryWrapper.orderByDesc(StockAreaBlacklist::getId);
        }
        return queryWrapper;
    }


    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

}
